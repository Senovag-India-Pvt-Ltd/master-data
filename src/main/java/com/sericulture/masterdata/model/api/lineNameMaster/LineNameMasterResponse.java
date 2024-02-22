package com.sericulture.masterdata.model.api.lineNameMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineNameMasterResponse {
    @Schema(name = "lineNameId", example = "1")
    Long lineNameId;

    @Schema(name = "lineName", example = "Karnataka", required = true)
    String lineName;

    @Schema(name = "lineNameInKannada", example = "ಭಾಷೆ", required = true)
    String lineNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
