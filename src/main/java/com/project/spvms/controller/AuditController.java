package com.project.spvms.controller;

import com.project.spvms.entity.AuditLog;
import com.project.spvms.repository.AuditLogRepository;
import com.project.spvms.util.CsvExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin/audit")
public class AuditController {

    @Autowired
    private AuditLogRepository repo;

    @GetMapping
    public Page<AuditLog> getAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return repo.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/export")
    public String exportAuditLogs(
            @RequestParam String from,
            @RequestParam String to) {

        return CsvExporter.export(
                repo.findByTimestampBetween(
                        LocalDateTime.parse(from),
                        LocalDateTime.parse(to)
                )
        );
    }
}

