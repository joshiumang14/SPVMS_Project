package com.project.spvms.repository;


import com.project.spvms.entity.FinancialApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialApprovalRepository
        extends JpaRepository<FinancialApproval, Long> {
}
