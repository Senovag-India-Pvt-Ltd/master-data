package com.sericulture.masterdata.model.api.tsBudgetTsc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TsBudgetTscResponse {

    @Schema(name = "tsBudgetTscId", example = "1")
    Long tsBudgetTscId;


    @Schema(name = "financialYearMasterId", example = "1")
    Long financialYearMasterId;

    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;

    @Schema(name = "date", example = "1")
    Date date;

    @Schema(name = "budgetAmount", example = "1")
    BigDecimal budgetAmount;


    @Schema(name="districtId", example = "1")
    Long districtId;

    @Schema(name = "talukId", example = "1")
    Long talukId;

    @Schema(name = "tscMasterId", example = "1")
    Long tscMasterId;

    @Schema(name = "financialYear", example = "Karnataka", required = true)
    String financialYear;

    @Schema(name = "districtName", example = "Shimoga")
    String districtName;

    @Schema(name = "talukName", example = "Thirthahalli", required = true)
    String talukName;

    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "scHeadAccountName", example = "scHeadAccount 1 ", required = true)
    String scHeadAccountName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}