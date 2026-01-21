package com.project.spvms.repository;

import com.project.spvms.entity.ProcurementRequest;
import com.project.spvms.repository.projection.OrderSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProcurementRequestRepository
        extends JpaRepository<ProcurementRequest, Long> {

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
