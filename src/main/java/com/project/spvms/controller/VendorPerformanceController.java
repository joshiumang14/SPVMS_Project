package com.project.spvms.controller;

import com.project.spvms.entity.Vendor;
import com.project.spvms.repository.VendorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorPerformanceController {

    private final VendorRepository repo;

    public VendorPerformanceController(VendorRepository repo) {
        this.repo = repo;
    }

    // Performance summary for dashboard/admin
    @GetMapping("/performance")
    public List<Vendor> getPerformanceSummary() {
        return repo.findAll();
    }

    // Optional: top vendors
    @GetMapping("/top")
    public List<Vendor> getTopVendors() {
        return repo.findTopPerformingVendors();
    }
}
