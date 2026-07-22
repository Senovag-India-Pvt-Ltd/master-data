package com.sericulture.masterdata.model.api.armCalculation;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArmCalculationRequest {

    private Long scCategoryId;
    private Long componentId;
    private Long componentTypeId;
    private String equipmentName;
    private BigDecimal quantity;
    private BigDecimal unitRate;
    private BigDecimal unitCost;
    private BigDecimal centralPercentage;
    private BigDecimal statePercentage;
    private BigDecimal advancePercentage;
    private BigDecimal firstPayment;
    private BigDecimal finalPayment;
    private String armEnds;
}
