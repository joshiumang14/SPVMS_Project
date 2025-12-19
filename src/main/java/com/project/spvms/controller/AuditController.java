package com.project.spvms.controller;

import com.project.spvms.entity.AuditLog;
import com.project.spvms.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditLogRepository repo;

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return repo.findAll();
    }
}
