package com.sericulture.masterdata.model.api.scSubSchemeMapping;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScSubSchemeMappingRequest extends RequestBody {

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;

    @Schema(name = "scSubSchemeDetailsId", example = "1")
    Long scSubSchemeDetailsId;
}
