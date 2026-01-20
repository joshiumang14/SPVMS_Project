package com.project.spvms.service;

import com.project.spvms.entity.EmailLog;
import com.project.spvms.repository.EmailLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class EmailRetryScheduler {

    @Autowired
    private EmailLogRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(fixedDelay = 10000) // 10 seconds

    public void retryFailedEmails() {

        List<EmailLog> emails =
                repo.findByStatusAndRetryCountLessThan("PENDING", 3);

        for (EmailLog log : emails) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(log.getToEmail());
                msg.setSubject(log.getSubject());
                msg.setText(log.getBody());

                mailSender.send(msg);
                log.setStatus("SUCCESS");

            } catch (Exception e) {
                log.setRetryCount(log.getRetryCount() + 1);
                log.setStatus("FAILED");
            }
            repo.save(log);
        }
    }
}
