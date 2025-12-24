package com.project.spvms.service;

import com.project.spvms.entity.Vendor;
import com.project.spvms.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendorScoreScheduler {

    private final VendorRepository repo;

    public VendorScoreScheduler(VendorRepository repo) {
        this.repo = repo;
    }

    // Runs every hour
    @Scheduled(cron = "0 0 * * * ?")
    public void updateVendorScores() {

        List<Vendor> vendors = repo.findAll();

        for (Vendor v : vendors) {
            double delivery = v.getDeliveryRate() != null ? v.getDeliveryRate() : 0;
            double quality  = v.getQualityRating() != null ? v.getQualityRating() : 0;
            double price    = v.getPriceScore() != null ? v.getPriceScore() : 0;

            double score = (delivery * 0.4) + (quality * 0.4) + (price * 0.2);
            v.setPerformanceScore(score);
        }

        // single DB hit (optimized)
        repo.saveAll(vendors);
    }
}
