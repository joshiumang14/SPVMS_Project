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

    // ✅ Existing endpoint (ID-based)
    @PostMapping("/submit/{vendorId}")
    public String submitPR(@PathVariable Long vendorId) {

        ProcurementRequest pr = new ProcurementRequest();
        pr.setId(3001L);
        pr.setStatus("SUBMITTED");

        procurementService.submitPR(vendorId, pr);
        return "PR submitted using vendor ID";
    }

    // ✅ NEW endpoint (EMAIL-based)
    @PostMapping("/submit/email")
    public String submitPRByEmail(@RequestParam String email) {

        ProcurementRequest pr = new ProcurementRequest();
        pr.setId(4002L);
        pr.setStatus("SUBMITTED");

        procurementService.submitPRByVendorEmail(email, pr);
        return "PR submitted for vendor email: " + email;
    }
}
