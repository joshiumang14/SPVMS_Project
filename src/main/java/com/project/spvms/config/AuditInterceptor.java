package com.project.spvms.config;

import com.project.spvms.entity.AuditLog;
import com.project.spvms.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class AuditInterceptor implements HandlerInterceptor {

    @Autowired
    private AuditLogRepository auditRepo;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        AuditLog log = new AuditLog();
        log.setUserId("SYSTEM");
        log.setAction(request.getMethod());
        log.setRequestPath(request.getRequestURI());
        log.setTimestamp(LocalDateTime.now());

        auditRepo.save(log);
        return true;
    }
}
