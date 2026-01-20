package com.project.spvms.repository;

import com.project.spvms.entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    List<EmailLog> findByStatusAndRetryCountLessThan(
            String status, int retryCount
    );
}
