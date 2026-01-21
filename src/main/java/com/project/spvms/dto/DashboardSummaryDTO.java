package com.project.spvms.dto;

import java.math.BigDecimal;

public class DashboardSummaryDTO {

    private long totalVendors;
    private long activeVendors;
    private long totalPurchaseOrders;
    private long approvedPurchaseOrders;
    private BigDecimal totalProcurementCost;
    private BigDecimal totalCostSavings;

    public DashboardSummaryDTO(long totalVendors,
                               long activeVendors,
                               long totalPurchaseOrders,
                               long approvedPurchaseOrders,
                               BigDecimal totalProcurementCost,
                               BigDecimal totalCostSavings) {
        this.totalVendors = totalVendors;
        this.activeVendors = activeVendors;
        this.totalPurchaseOrders = totalPurchaseOrders;
        this.approvedPurchaseOrders = approvedPurchaseOrders;
        this.totalProcurementCost = totalProcurementCost;
        this.totalCostSavings = totalCostSavings;
    }

    public long getTotalVendors() {
        return totalVendors;
    }

    public long getActiveVendors() {
        return activeVendors;
    }

    public long getTotalPurchaseOrders() {
        return totalPurchaseOrders;
    }

    public long getApprovedPurchaseOrders() {
        return approvedPurchaseOrders;
    }

    public BigDecimal getTotalProcurementCost() {
        return totalProcurementCost;
    }

    public BigDecimal getTotalCostSavings() {
        return totalCostSavings;
    }
}
