package com.project.spvms.service;

import com.project.spvms.entity.Vendor;
import com.project.spvms.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class VendorScoreScheduler {

    @Autowired
    private VendorRepository repo;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateVendorScores() {
        List<Vendor> vendors = repo.findAll();

        for (Vendor v : vendors) {
            double score =
                    (v.getDeliveryRate() * 0.4) +
                            (v.getQualityRating() * 0.4) +
                            (v.getPriceScore() * 0.2);
            v.setPerformanceScore(score);
            repo.save(v);
        }
    }
}
