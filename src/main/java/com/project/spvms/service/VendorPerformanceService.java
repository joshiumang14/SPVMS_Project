package com.project.spvms.service;


import com.project.spvms.entity.Vendor;
import org.springframework.stereotype.Service;

@Service
public class VendorPerformanceService {

    public double calculatePerformanceScore(Vendor vendor) {

        double delivery = vendor.getDeliveryRate() != null ? vendor.getDeliveryRate() : 0;
        double quality = vendor.getQualityRating() != null ? vendor.getQualityRating() : 0;
        double price = vendor.getPriceScore() != null ? vendor.getPriceScore() : 0;

        return (delivery * 0.4)
                + (quality * 0.4)
                + (price * 0.2);
    }
}
