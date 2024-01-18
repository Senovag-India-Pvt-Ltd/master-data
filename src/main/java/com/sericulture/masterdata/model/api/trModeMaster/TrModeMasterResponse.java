package com.sericulture.masterdata.model.api.trModeMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrModeMasterResponse {
    @Schema(name="trModeMasterId", example = "1")
    int trModeMasterId;

    @Schema(name = "trModeMasterName", example = "Karnataka")
    String trModeMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
