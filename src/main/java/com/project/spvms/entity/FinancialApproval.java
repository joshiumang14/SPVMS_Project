package com.project.spvms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_approvals")
public class FinancialApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long prId;
    private String approvedBy;
    private Double approvedAmount;
    private LocalDateTime approvedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPrId() { return prId; }
    public void setPrId(Long prId) { this.prId = prId; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public Double getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(Double approvedAmount) { this.approvedAmount = approvedAmount; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }
}
