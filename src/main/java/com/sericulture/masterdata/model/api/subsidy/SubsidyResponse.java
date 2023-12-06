package com.sericulture.masterdata.model.api.subsidy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubsidyResponse {
    @Schema(name="subsidyId", example = "1")
    int subsidyId;

    @Schema(name = "subsidyName", example = "Subsidy 1")
    String subsidyName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
