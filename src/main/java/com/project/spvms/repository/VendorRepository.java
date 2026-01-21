package com.project.spvms.repository;

import com.project.spvms.entity.Vendor;
import com.project.spvms.repository.projection.VendorSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    @Query("SELECT v FROM Vendor v ORDER BY v.performanceScore DESC")
    List<Vendor> findTopVendors();

    @Query("SELECT v FROM Vendor v ORDER BY v.performanceScore DESC")
    List<Vendor> findTopPerformingVendors();

    Vendor findByEmail(String email);

    @Query("""
    SELECT 
        COUNT(v) AS totalVendors,
        0 AS activeVendors
    FROM Vendor v
""")
    VendorSummaryProjection fetchVendorSummary();

}
