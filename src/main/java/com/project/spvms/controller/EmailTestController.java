package com.project.spvms.controller;

import com.project.spvms.entity.ProcurementRequest;
import com.project.spvms.service.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/test-email")
public class EmailTestController {

    @Autowired
    private ProcurementService procurementService;

    @GetMapping
    public String testEmail() {

        Long vendorId = 1L;

        ProcurementRequest pr = new ProcurementRequest();
        pr.setItemName("Test Item");
        pr.setQuantity(1);
        pr.setTotalCost(100.0);
        pr.setCostCenter("IT-OPS");

        procurementService.submitPR(vendorId, pr);

        return "Email triggered successfully";
    }
}
