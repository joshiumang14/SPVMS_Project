package com.project.spvms.service;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {


    public String prSubmittedTemplate(String vendorName, Long prId) {
        return """
        Hello %s,

        Your PR #%d has been submitted.

        Regards,
        SPVMS
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
