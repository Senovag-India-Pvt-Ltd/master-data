package com.sericulture.masterdata.model.api.armCalculation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmCalculationResponse {

    private Long armCalculationId;
    private Long scCategoryId;
    private Long componentId;
    private Long componentTypeId;
    private String equipmentName;
    private BigDecimal quantity;
    private BigDecimal unitRate;
    private BigDecimal totalAmount;   // quantity * unitRate (calculated)
    private BigDecimal unitCost;
    private BigDecimal centralPercentage;
    private BigDecimal statePercentage;
    private String armEnds;
    private Boolean active;

    private boolean error;
    private String error_description;
}
