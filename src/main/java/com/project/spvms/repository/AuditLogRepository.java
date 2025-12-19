package com.project.spvms.repository;

import com.project.spvms.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByTimestampBetween(
            LocalDateTime from,
            LocalDateTime to
    );
}
