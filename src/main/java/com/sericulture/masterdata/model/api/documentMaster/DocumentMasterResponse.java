package com.sericulture.masterdata.model.api.documentMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentMasterResponse {

    @Schema(name = "documentMasterId", example = "1")
    Integer documentMasterId;

    @Schema(name = "documentMasterName", example = "documentMasterName 1")
    String documentMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
