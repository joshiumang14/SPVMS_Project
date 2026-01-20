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

    //  Submit PR using Vendor ID
    @PostMapping("/submit/{vendorId}")
    public String submitPR(@PathVariable Long vendorId,
                           @RequestBody ProcurementRequest pr) {

        //  NO pr.setId()
        procurementService.submitPR(vendorId, pr);
        return "PR submitted using vendor ID";
    }

    // Submit PR using Vendor Email
    @PostMapping("/submit/email")
    public String submitPRByEmail(@RequestParam String email,
                                  @RequestBody ProcurementRequest pr) {

        //  NO pr.setId()
        procurementService.submitPRByVendorEmail(email, pr);
        return "PR submitted for vendor email: " + email;
    }
}
