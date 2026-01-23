package com.project.spvms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "procurement_requests",
        uniqueConstraints = {
                // Ensures: same vendor + same JSON = same PR
                @UniqueConstraint(
                        columnNames = {"vendor_id", "request_hash"}
                )
        }
)
public class ProcurementRequest {

    // Primary key (PR ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each PR belongs to one vendor
    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    // Item details
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @Column(name = "cost_center", nullable = false)
    private String costCenter;

    // PR status: SUBMITTED / APPROVED etc.
    @Column(nullable = false)
    private String status;

    // Hash generated from JSON fields
    // Used for idempotency check
    @Column(name = "request_hash", nullable = false, length = 64)
    private String requestHash;

    // Creation timestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestHash() {
        return requestHash;
    }

    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
