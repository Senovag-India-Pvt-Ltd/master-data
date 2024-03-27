package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScVendorContactDTO {
    private Long scVendorContactId;
    private String vendorAddress;
    private String phone;
    private String email;
    private Long scVendorId;
    private String name;

}
