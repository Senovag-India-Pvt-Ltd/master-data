package com.sericulture.masterdata.model.api.scSubSchemeMapping;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditScSubSchemeMappingRequest extends RequestBody {
    @Schema(name = "scSubSchemeMappingId", example = "1")
    Long scSubSchemeMappingId;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Long scSubSchemeDetailsId;
}
