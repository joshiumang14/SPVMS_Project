package com.project.spvms.service;

import com.project.spvms.entity.EmailLog;
import com.project.spvms.entity.ProcurementRequest;
import com.project.spvms.repository.EmailLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailLogRepository emailLogRepository;

    // Queue PR submission mail
    public void queuePRSubmittedMail(
            String toEmail,
            String vendorName,
            ProcurementRequest pr
    ) {

        EmailLog log = new EmailLog();

        log.setToEmail(toEmail);
        log.setSubject("PR Submitted: " + pr.getId());
        log.setBody(
                "Hello " + vendorName + ",\n\n" +
                        "Your PR #" + pr.getId() + " has been submitted.\n\n" +
                        "Regards,\nSPVMS"
        );

        log.setStatus("PENDING");
        log.setRetryCount(0);
        log.setSentAt(LocalDateTime.now());

        emailLogRepository.save(log);

        sendEmail(log);
    }

    // Actual email sending
    private void sendEmail(EmailLog log) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(log.getToEmail());
        message.setSubject(log.getSubject());
        message.setText(log.getBody());

        mailSender.send(message);

        log.setStatus("SUCCESS");
        emailLogRepository.save(log);
    }
}
