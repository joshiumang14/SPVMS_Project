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

    // ================================
    // SUBMIT PR USING VENDOR ID
    // ================================
    public void submitPR(Long vendorId, ProcurementRequest pr) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        pr.setStatus("SUBMITTED");
        pr.setCreatedAt(LocalDateTime.now());

        // SAVE PR (DB generates ID)
        ProcurementRequest savedPR =
                procurementRequestRepository.save(pr);

        // SEND EMAIL with DB ID
        emailService.queuePRSubmittedMail(
                vendor.getEmail(),
                vendor.getName(),
                savedPR
        );
    }

    // ================================
    // SUBMIT PR USING VENDOR EMAIL
    // ================================
    public void submitPRByVendorEmail(String email, ProcurementRequest pr) {

        Vendor vendor = vendorRepository.findByEmail(email);

        if (vendor == null) {
            throw new RuntimeException("Vendor not found for email: " + email);
        }

        pr.setStatus("SUBMITTED");
        pr.setCreatedAt(LocalDateTime.now());

        ProcurementRequest savedPR =
                procurementRequestRepository.save(pr);

        emailService.queuePRSubmittedMail(
                vendor.getEmail(),
                vendor.getName(),
                savedPR
        );
    }

    // ================================
    //  FINANCE APPROVAL (SPRINT-5)
    // ================================
    public void approvePR(ProcurementRequest pr, String approver) {

        // 1️ Update PR status
        pr.setStatus("APPROVED");
        procurementRequestRepository.save(pr);

        // 2 Save approval audit
        FinancialApproval approval = new FinancialApproval();
        approval.setPrId(pr.getId());
        approval.setApprovedBy(approver);
        approval.setApprovedAmount(pr.getTotalCost());
        approval.setApprovedAt(LocalDateTime.now());

        financialApprovalRepository.save(approval);

        // 3 Update expenditure summary
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

        // 4 Update utilized budget
        budgetService.updateUtilizedAmount(
                pr.getCostCenter(),
                pr.getTotalCost()
        );
    }
}
