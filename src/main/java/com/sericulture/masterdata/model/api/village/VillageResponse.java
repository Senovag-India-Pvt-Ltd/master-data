package com.sericulture.masterdata.model.api.village;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VillageResponse {

    @Schema(name="stateId", example = "1")
    int stateId;

    @Schema(name="districtId", example = "1")
    int districtId;

    @Schema(name="talukId", example = "1")
    int talukId;

    @Schema(name="hobliId", example = "1")
    int hobliId;

    @Schema(name="villageId", example = "1")
    int villageId;

    @Schema(name = "villageName", example = "Hodala")
    String villageName;
}