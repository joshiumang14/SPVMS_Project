package com.project.spvms.controller;

import com.project.spvms.entity.Vendor;
import com.project.spvms.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class VendorAnalyticsController {

    @Autowired
    private VendorRepository repo;

    @GetMapping("/top-vendors")
    public List<Vendor> getTopVendors() {
        return repo.findTopVendors();
    }
}
