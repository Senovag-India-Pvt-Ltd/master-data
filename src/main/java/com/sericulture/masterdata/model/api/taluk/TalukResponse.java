package com.sericulture.masterdata.model.api.taluk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TalukResponse {

    @Schema(name="stateId", example = "1")
    int stateId;

    @Schema(name="districtId", example = "1")
    int districtId;

    @Schema(name="talukId", example = "1")
    int talukId;

    @Schema(name = "talukName", example = "Thirthahalli")
    String talukName;
}