package com.project.spvms.repository;

import com.project.spvms.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    @Query("SELECT v FROM Vendor v ORDER BY v.performanceScore DESC")
    List<Vendor> findTopVendors();

    // Top performing vendors
    @Query("SELECT v FROM Vendor v ORDER BY v.performanceScore DESC")
    List<Vendor> findTopPerformingVendors();
}
