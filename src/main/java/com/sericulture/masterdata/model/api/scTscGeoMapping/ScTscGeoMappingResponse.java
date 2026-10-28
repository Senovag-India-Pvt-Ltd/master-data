package com.sericulture.masterdata.model.api.scTscGeoMapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScTscGeoMappingResponse {

    @Schema(name = "scTscGeoMappingId", example = "1")
    Long scTscGeoMappingId;

    @Schema(name = "districtId", example = "1")
    Long districtId;

    @Schema(name = "districtName", example = "Shimoga")
    String districtName;

    @Schema(name = "talukId", example = "1")
    Long talukId;

    @Schema(name = "talukName", example = "Thirthahalli")
    String talukName;

    @Schema(name = "hobliId", example = "1")
    Long hobliId;

    @Schema(name = "hobliName", example = "Agrahara")
    String hobliName;

    @Schema(name = "villageId", example = "1")
    Long villageId;

    @Schema(name = "villageName", example = "Kaimara")
    String villageName;

    @Schema(name = "tscMasterId", example = "1")
    Long tscMasterId;

    @Schema(name = "tscName", example = "TSC Thirthahalli")
    String tscName;

    @Schema(name = "officerUserId", example = "1")
    Long officerUserId;

    @Schema(name = "officerName", example = "Ramesh K")
    String officerName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "TSC mapping already exists for this geography")
    String error_description;
}
