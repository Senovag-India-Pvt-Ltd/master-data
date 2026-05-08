package com.sericulture.masterdata.model.api.schemeDocumentMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SchemeDocumentMasterRequest extends RequestBody {

    @Schema(name = "scSchemeDetailsId", example = "1", required = true)
    Integer scSchemeDetailsId;

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Integer scSubSchemeDetailsId;

    @Schema(name = "documentId", example = "1", required = true)
    Integer documentId;

    @Schema(name = "allow", example = "true")
    Boolean allow;
}
