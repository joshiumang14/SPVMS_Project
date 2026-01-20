package com.project.spvms.repository;

import com.project.spvms.entity.ProcurementRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcurementRequestRepository
        extends JpaRepository<ProcurementRequest, Long> {
}
