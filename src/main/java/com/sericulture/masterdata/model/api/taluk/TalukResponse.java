package com.sericulture.masterdata.model.api.taluk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
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

    @Schema(name = "stateName", example = "Karnataka")
    String stateName;

    @Schema(name = "districtName", example = "Thirthahalli")
    String districtName;

    @Schema(name = "talukName", example = "Thirthahalli")
    String talukName;

    @Schema(name = "lgTaluk", example = "Shimoga")
    String lgTaluk;

    @Schema(name = "talukNameInKannada",  example = "ಭಾಷೆ")
    String talukNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}