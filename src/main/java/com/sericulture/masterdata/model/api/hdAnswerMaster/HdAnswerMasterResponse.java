package com.sericulture.masterdata.model.api.hdAnswerMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class HdAnswerMasterResponse {
    @Schema(name = "hdAnswerId", example = "1")
    Long hdAnswerId;

    @Schema(name = "hdAnswerName", example = "Karnataka", required = true)
    String hdAnswerName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
