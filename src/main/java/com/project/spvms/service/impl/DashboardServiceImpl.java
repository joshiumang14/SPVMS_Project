package com.project.spvms.service.impl;

import com.project.spvms.dto.DashboardSummaryDTO;
import com.project.spvms.repository.ProcurementRequestRepository;
import com.project.spvms.repository.VendorRepository;
import com.project.spvms.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final VendorRepository vendorRepository;
    private final ProcurementRequestRepository procurementRequestRepository;

    @Override
    @Cacheable("dashboardSummary")
    public DashboardSummaryDTO getDashboardSummary() {

        var vendor = vendorRepository.fetchVendorSummary();
        var order = procurementRequestRepository.fetchProcurementSummary();

        return new DashboardSummaryDTO(
                vendor.getTotalVendors(),
                vendor.getActiveVendors(),
                order.getTotalRequests(),
                order.getApprovedRequests(),
                order.getTotalExpenditure(),
                order.getSavings()
        );
    }
}
