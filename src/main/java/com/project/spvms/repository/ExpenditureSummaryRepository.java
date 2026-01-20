package com.project.spvms.repository;


import com.project.spvms.entity.ExpenditureSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ExpenditureSummaryRepository
        extends JpaRepository<ExpenditureSummary, Long> {

    Optional<ExpenditureSummary> findByCostCenter(String costCenter);
}
