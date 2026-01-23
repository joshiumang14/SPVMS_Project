package com.project.spvms.service;

import com.project.spvms.entity.ExpenditureSummary;
import com.project.spvms.entity.FinancialApproval;
import com.project.spvms.entity.ProcurementRequest;
import com.project.spvms.entity.Vendor;
import com.project.spvms.repository.ExpenditureSummaryRepository;
import com.project.spvms.repository.FinancialApprovalRepository;
import com.project.spvms.repository.ProcurementRequestRepository;
import com.project.spvms.repository.VendorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProcurementService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ProcurementRequestRepository procurementRequestRepository;

    @Autowired
    private FinancialApprovalRepository financialApprovalRepository;

    @Autowired
    private ExpenditureSummaryRepository expenditureSummaryRepository;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private EmailService emailService;

    // Submit PR using vendor ID
    public void submitPR(Long vendorId, ProcurementRequest pr) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        processPR(vendor, pr);
    }

    // Submit PR using vendor email
    public void submitPRByVendorEmail(String email, ProcurementRequest pr) {

        Vendor vendor = vendorRepository.findByEmail(email);

        if (vendor == null) {
            throw new RuntimeException("Vendor not found for email: " + email);
        }

        processPR(vendor, pr);
    }

    // Core PR processing logic
    private void processPR(Vendor vendor, ProcurementRequest pr) {

        // FIXED: vendor-scoped deterministic hash
        // Same vendor + same JSON -> same hash
        // Different vendor + same JSON -> different hash
        String requestHash = generateRequestHash(vendor, pr);

        ProcurementRequest finalPR;

        try {
            // Idempotency check
            finalPR =
                    procurementRequestRepository
                            .findByVendorAndRequestHash(vendor, requestHash)
                            .orElseGet(() -> {
                                // Create new PR only if not found
                                pr.setVendor(vendor);
                                pr.setStatus("SUBMITTED");
                                pr.setCreatedAt(LocalDateTime.now());
                                pr.setRequestHash(requestHash);
                                return procurementRequestRepository.save(pr);
                            });
        } catch (DataIntegrityViolationException ex) {
            // Handles race condition (parallel requests)
            // Fetch already-created PR instead of failing
            finalPR =
                    procurementRequestRepository
                            .findByVendorAndRequestHash(vendor, requestHash)
                            .orElseThrow(() ->
                                    new RuntimeException("Failed to safely process procurement request")
                            );
        }

        // Always send mail with correct PR ID
        emailService.queuePRSubmittedMail(
                vendor.getEmail(),
                vendor.getName(),
                finalPR
        );
    }

    // FIXED Hash generator
    // vendor-scoped idempotency key
    // Same vendor + same JSON -> same hash
    // Different vendor + same JSON -> different hash
    private String generateRequestHash(Vendor vendor, ProcurementRequest pr) {

        if (pr.getItemName() == null ||
                pr.getCostCenter() == null ||
                pr.getTotalCost() == null) {

            throw new RuntimeException(
                    "Invalid PR data: itemName, costCenter, and totalCost are required"
            );
        }

        // FIX: vendor is now part of hash source
        String raw =
                vendor.getId() + "|" +                          // vendor scoping
                        pr.getItemName().trim().toLowerCase() + "|" +
                        pr.getCostCenter().trim().toLowerCase() + "|" +
                        pr.getStatus() + "|" +
                        pr.getQuantity() + "|" +
                        pr.getTotalCost();

        try {
            java.security.MessageDigest digest =
                    java.security.MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(
                    raw.getBytes(java.nio.charset.StandardCharsets.UTF_8)
            );

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate request hash", e);
        }
    }

    // Finance approval logic (Sprint 5) - unchanged
    public void approvePR(ProcurementRequest pr, String approver) {

        pr.setStatus("APPROVED");
        procurementRequestRepository.save(pr);

        FinancialApproval approval = new FinancialApproval();
        approval.setPrId(pr.getId());
        approval.setApprovedBy(approver);
        approval.setApprovedAmount(pr.getTotalCost());
        approval.setApprovedAt(LocalDateTime.now());
        financialApprovalRepository.save(approval);

        ExpenditureSummary summary =
                expenditureSummaryRepository
                        .findByCostCenter(pr.getCostCenter())
                        .orElse(new ExpenditureSummary());

        summary.setCostCenter(pr.getCostCenter());
        summary.setTotalSpent(
                (summary.getTotalSpent() == null ? 0 : summary.getTotalSpent())
                        + pr.getTotalCost()
        );
        summary.setLastUpdated(LocalDateTime.now());
        expenditureSummaryRepository.save(summary);

        budgetService.updateUtilizedAmount(
                pr.getCostCenter(),
                pr.getTotalCost()
        );
    }
}
