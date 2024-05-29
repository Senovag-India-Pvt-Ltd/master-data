package com.sericulture.masterdata.model.api.hobli;

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
public class HobliResponse {

    @Schema(name="stateId", example = "1")
    int stateId;

    @Schema(name="districtId", example = "1")
    int districtId;

    @Schema(name="talukId", example = "1")
    int talukId;

    @Schema(name="hobliId", example = "1")
    int hobliId;

    @Schema(name="stateName", example = "karnaa")
    String stateName;

    @Schema(name="districtName", example = "Udupi")
    String districtName;

    @Schema(name="talukName", example = "kundapurr")
    String talukName;

    @Schema(name = "hobliName", example = "Kasaba")
    String hobliName;

    @Schema(name = "hobliNameInKannada",  example = "ಭಾಷೆ")
    String hobliNameInKannada;

    @Schema(name = "hobliCode", example = "1234")
    String hobliCode;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}