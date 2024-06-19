package com.sericulture.masterdata.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TscMasterDTO {
    private Long tscMasterId;
    private Long districtId;
    private Long talukId;
    private String address;
    private String name;
    private String nameInKannada;
    private String districtName;
    private String talukName;

}
