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

        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        long startTime = (long) request.getAttribute("startTime");
        long executionTime = System.currentTimeMillis() - startTime;

        AuditLog log = new AuditLog();
        log.setUserId(request.getHeader("userId") != null
                ? request.getHeader("userId")
                : "SYSTEM");

        log.setAction(request.getMethod());
        log.setRequestPath(request.getRequestURI());
        log.setHttpStatus(response.getStatus());
        log.setIpAddress(request.getRemoteAddr());
        log.setExecutionTime(executionTime);
        log.setTimestamp(LocalDateTime.now());

        auditRepo.save(log);
    }
}
