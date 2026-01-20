package com.project.spvms.service;

import com.project.spvms.entity.EmailLog;
import com.project.spvms.entity.ProcurementRequest;
import com.project.spvms.repository.EmailLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailLogRepository emailLogRepository;

    public void queuePRSubmittedMail(
            String toEmail,
            String vendorName,
            ProcurementRequest pr) {

        EmailLog log = new EmailLog();
        log.setToEmail(toEmail);
        log.setSubject("PR Submitted: " + pr.getId());
        log.setBody(
                "Hello " + vendorName +
                        ",\n\nYour PR #" + pr.getId() +
                        " has been submitted.\n\nRegards,\nSPVMS"
        );
        log.setStatus("PENDING");
        log.setRetryCount(0);

        emailLogRepository.save(log);
    }
}
