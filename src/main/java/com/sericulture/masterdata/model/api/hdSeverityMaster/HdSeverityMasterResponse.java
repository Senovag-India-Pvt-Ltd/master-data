package com.sericulture.masterdata.model.api.hdSeverityMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class HdSeverityMasterResponse {
    @Schema(name = "hdSeverityId", example = "1")
    Long hdSeverityId;

    @Schema(name = "hdSeverityName", example = "Karnataka", required = true)
    String hdSeverityName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
