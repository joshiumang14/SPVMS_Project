package com.project.spvms.repository;

import com.project.spvms.entity.ProcurementRequest;
import com.project.spvms.entity.Vendor;
import com.project.spvms.repository.projection.OrderSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProcurementRequestRepository
        extends JpaRepository<ProcurementRequest, Long> {

    // Old method retained for other sprints (not removed)
    List<ProcurementRequest>
    findByVendorAndItemNameAndCostCenterAndStatus(
            Vendor vendor,
            String itemName,
            String costCenter,
            String status
    );

    // Idempotency method (already correct)
    // vendor + request_hash based lookup
    Optional<ProcurementRequest>
    findByVendorAndRequestHash(Vendor vendor, String requestHash);

    // Dashboard / summary query (unchanged)
    @Query("""
        SELECT
            COUNT(pr) AS totalRequests,
            0 AS approvedRequests,
            0 AS totalExpenditure,
            0 AS savings
        FROM ProcurementRequest pr
    """)
    OrderSummaryProjection fetchProcurementSummary();
}
