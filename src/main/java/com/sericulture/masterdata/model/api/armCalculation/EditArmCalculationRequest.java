package com.sericulture.masterdata.model.api.armCalculation;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditArmCalculationRequest {

    private Long armCalculationId;
    private Long scCategoryId;
    private Long componentId;
    private Long componentTypeId;
    private String equipmentName;
    private BigDecimal quantity;
    private BigDecimal unitRate;
    private BigDecimal unitCost;
    private BigDecimal centralPercentage;
    private BigDecimal statePercentage;
    private String armEnds;
}
