package com.sericulture.masterdata.model.api.armCalculation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

// Central%/State%/Advance%/First Payment%/Final Payment%/Min/Max are the same across every
// component in a given (armEnds, scCategoryId) group -- this bulk-applies them to every
// active component in that group instead of editing each row individually.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArmGroupSettingsRequest {

    @NotBlank
    private String armEnds;

    @NotNull
    private Long scCategoryId;

    private BigDecimal centralPercentage;
    private BigDecimal statePercentage;
    private BigDecimal advancePercentage;
    private BigDecimal firstPayment;
    private BigDecimal finalPayment;
    private BigDecimal projectCostMin;
    private BigDecimal projectCostMax;
}
