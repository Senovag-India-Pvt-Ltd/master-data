package com.sericulture.masterdata.model.api.hdQuestionMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class HdQuestionMasterResponse {
    @Schema(name = "hdQuestionId", example = "1")
    Long hdQuestionId;

    @Schema(name = "hdQuestionName", example = "Karnataka", required = true)
    String hdQuestionName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}
