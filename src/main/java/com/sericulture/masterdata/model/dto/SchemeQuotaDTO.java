package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemeQuotaDTO {
    private Long schemeQuotaId;
    private Long scSchemeDetailsId;
    private String schemeQuotaName;
    private String schemeQuotaType;
    private String schemeQuotaCode;
    private String schemeQuotaPaymentType;
}
