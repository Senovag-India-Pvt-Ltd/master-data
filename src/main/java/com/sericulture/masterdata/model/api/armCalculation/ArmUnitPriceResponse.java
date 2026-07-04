package com.sericulture.masterdata.model.api.armCalculation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmUnitPriceResponse {

    private String armEnds;
    private Long scCategoryId;
    private int componentCount;

    private BigDecimal totalUnitCost;
    private BigDecimal centralPercentage;
    private BigDecimal statePercentage;
    private BigDecimal subsidyAmount;

    private boolean error;
    private String error_description;
}
