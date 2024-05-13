package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RendittaMasterDTO {

        private Long rendittaMasterId;
        private Long raceMasterId;
        private BigDecimal value;
        private String raceMasterName;
}
