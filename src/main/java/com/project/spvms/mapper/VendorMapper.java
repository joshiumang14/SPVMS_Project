package com.project.spvms.mapper;

import com.project.spvms.dto.VendorDTO;
import com.project.spvms.entity.Vendor;

public class VendorMapper {

    public static VendorDTO toDTO(Vendor vendor) {
        VendorDTO dto = new VendorDTO();
        dto.setId(vendor.getId());
        dto.setName(vendor.getName());
        dto.setEmail(vendor.getEmail());
        return dto;
    }

    public static Vendor toEntity(VendorDTO dto) {
        Vendor vendor = new Vendor();
        vendor.setName(dto.getName());
        vendor.setEmail(dto.getEmail());
        return vendor;
    }
}
