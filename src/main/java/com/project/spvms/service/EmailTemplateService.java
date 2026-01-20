package com.project.spvms.service;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

    public String prSubmittedTemplate(String vendorName, Long prId) {
        return """
                Hello %s,

                Your Purchase Request #%d has been successfully submitted.

                Regards,
                SPVMS System
                """.formatted(vendorName, prId);
    }

    public String prApprovedTemplate(Long prId) {
        return """
                Hello,

                Your Purchase Request #%d has been approved.

                Regards,
                SPVMS System
                """.formatted(prId);
    }
}
