package com.sericulture.masterdata.model.api.district;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistrictResponse {

    @Schema(name="districtId", example = "1")
    int districtId;

    @Schema(name="stateId", example = "1")
    int stateId;

    @Schema(name = "districtName", example = "Shimoga")
    String districtName;

    @Schema(name = "stateName", example = "Karnataka")
    String stateName;
}