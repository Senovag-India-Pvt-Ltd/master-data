package com.sericulture.masterdata.model.api.trOffice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrOfficeResponse {

    @Schema(name="trOfficeId", example = "1")
    int trOfficeId;

    @Schema(name = "trOfficeName", example = "Karnataka")
    String trOfficeName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
