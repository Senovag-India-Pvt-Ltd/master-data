package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScVendorBankDTO {
    private Long scVendorBankId;
    private String bankName;
    private String ifscCode;
    private String branch;
    private String accountNumber;
    private String upi;
    private Long scVendorId;
    private String name;



}
