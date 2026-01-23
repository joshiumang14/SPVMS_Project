package com.project.spvms.controller;

import com.project.spvms.entity.ProcurementRequest;
import com.project.spvms.service.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pr")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    // Submit PR using vendor ID
    @PostMapping("/submit/{vendorId}")
    public String submitPR(
            @PathVariable Long vendorId,
            @RequestBody ProcurementRequest pr
    ) {
        if (pr.getItemName() == null || pr.getCostCenter() == null) {
            throw new RuntimeException("Invalid request body");
        }
        procurementService.submitPR(vendorId, pr);
        return "PR submitted using vendor ID";
    }


    // Submit PR using vendor email
    @PostMapping("/submit/email")
    public String submitPRByEmail(
            @RequestParam String email,
            @RequestBody ProcurementRequest pr
    ) {
        procurementService.submitPRByVendorEmail(email, pr);
        return "PR submitted using vendor email";
    }
}
