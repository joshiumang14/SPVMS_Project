package com.project.spvms.util;

import com.project.spvms.entity.AuditLog;

import java.util.List;

public class CsvExporter {

    public static String export(List<AuditLog> logs) {

        StringBuilder sb = new StringBuilder();
        sb.append("USER,ACTION,PATH,STATUS,IP,EXEC_TIME,TIME\n");

        for (AuditLog log : logs) {
            sb.append(log.getUserId()).append(",")
                    .append(log.getAction()).append(",")
                    .append(log.getRequestPath()).append(",")
                    .append(log.getHttpStatus()).append(",")
                    .append(log.getIpAddress()).append(",")
                    .append(log.getExecutionTime()).append(",")
                    .append(log.getTimestamp()).append("\n");
        }
        return sb.toString();
    }
}
