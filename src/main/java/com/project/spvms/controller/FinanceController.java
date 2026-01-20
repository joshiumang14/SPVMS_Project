package com.project.spvms.controller;


import com.project.spvms.entity.ProcurementRequest;
import com.project.spvms.repository.ProcurementRequestRepository;
import com.project.spvms.service.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    @Autowired
    private ProcurementService procurementService;

    @Autowired
    private ProcurementRequestRepository prRepository;

    // ==================================================
    // SPRINT 5: FINANCIAL APPROVAL ENDPOINT
    // ==================================================
    @PreAuthorize("hasRole('FINANCE')")
    @PostMapping("/approve/{prId}")
    public String approvePR(@PathVariable Long prId) {

        ProcurementRequest pr = prRepository.findById(prId)
                .orElseThrow(() -> new RuntimeException("PR not found"));

        procurementService.approvePR(pr, "FINANCE_USER");

        return "Procurement Request Approved";
    }
}
