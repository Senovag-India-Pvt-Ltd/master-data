package com.sericulture.masterdata.model.api.schemeDocumentMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemeDocumentMasterResponse {

    @Schema(name = "schemeDocumentId", example = "1")
    Integer schemeDocumentId;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Integer scSchemeDetailsId;

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Integer scSubSchemeDetailsId;

    @Schema(name = "documentId", example = "1")
    Integer documentId;

    @Schema(name = "allow", example = "true")
    Boolean allow;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
