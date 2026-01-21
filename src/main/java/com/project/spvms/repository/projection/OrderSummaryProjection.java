package com.project.spvms.repository.projection;

import java.math.BigDecimal;

public interface OrderSummaryProjection {

    long getTotalRequests();        // ✔ matches totalRequests
    long getApprovedRequests();     // ✔ matches approvedRequests
    BigDecimal getTotalExpenditure(); // ✔ matches totalExpenditure
    BigDecimal getSavings();        // ✔ matches savings
}
