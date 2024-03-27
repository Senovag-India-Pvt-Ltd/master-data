package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScApprovingAuthorityDTO {

    private Long scApprovingAuthorityId;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Long type;
    private Long roleId;
    private String roleName;

}
