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
public class EditSchemeDocumentMasterRequest {

    @Schema(name = "schemeDocumentId", example = "1", required = true)
    Integer schemeDocumentId;

    @Schema(name = "scSchemeDetailsId", example = "1", required = true)
    Integer scSchemeDetailsId;

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Integer scSubSchemeDetailsId;

    @Schema(name = "documentId", example = "1", required = true)
    Integer documentId;

    @Schema(name = "allow", example = "true")
    Boolean allow;
}
