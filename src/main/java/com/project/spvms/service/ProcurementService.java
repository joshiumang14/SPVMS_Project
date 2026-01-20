package com.project.spvms.service;

import com.project.spvms.entity.*;
import com.project.spvms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProcurementService {

    // ===== EXISTING DEPENDENCIES =====
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private EmailService emailService;

    // ===== SPRINT 5 DEPENDENCIES (ADDED) =====
    @Autowired
    private BudgetService budgetService;

    @Autowired
    private FinancialApprovalRepository financialApprovalRepository;

    @Autowired
    private ExpenditureSummaryRepository expenditureSummaryRepository;

    // =========================================================
    //  EXISTING METHOD (UNCHANGED – ID based submission)
    // =========================================================
    public void submitPR(Long vendorId, ProcurementRequest pr) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        //  Sprint 5: Budget validation
        budgetService.validateBudget(
                pr.getCostCenter(),
                pr.getTotalCost()
        );

        pr.setStatus("SUBMITTED");
        pr.setCreatedAt(LocalDateTime.now());
        // save PR entity here

        //  Existing email logic (UNCHANGED)
        emailService.queuePRSubmittedMail(
                vendor.getEmail(),
                vendor.getName(),
                pr
        );
    }

    // =========================================================
    // EXISTING METHOD (UNCHANGED – Email based submission)
    // =========================================================
    public void submitPRByVendorEmail(String email, ProcurementRequest pr) {

        Vendor vendor = vendorRepository.findByEmail(email);

        if (vendor == null) {
            throw new RuntimeException("Vendor not found for email: " + email);
        }

        //  Sprint 5: Budget validation
        budgetService.validateBudget(
                pr.getCostCenter(),
                pr.getTotalCost()
        );

        pr.setStatus("SUBMITTED");
        pr.setCreatedAt(LocalDateTime.now());

        //  Existing email logic (UNCHANGED)
        emailService.queuePRSubmittedMail(
                vendor.getEmail(),
                vendor.getName(),
                pr
        );
    }

    // =========================================================
    // SPRINT 5: FINANCIAL APPROVAL METHOD (NEW)
    // =========================================================
    public void approvePR(ProcurementRequest pr, String approver) {

        pr.setStatus("APPROVED");

        //  Log financial approval
        FinancialApproval approval = new FinancialApproval();
        approval.setPrId(pr.getId());
        approval.setApprovedBy(approver);
        approval.setApprovedAmount(pr.getTotalCost());
        approval.setApprovedAt(LocalDateTime.now());

        financialApprovalRepository.save(approval);

        //  Update expenditure summary
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

        //  Update utilized budget
        budgetService.updateUtilizedAmount(
                pr.getCostCenter(),
                pr.getTotalCost()
        );
    }
}
