package com.sericulture.masterdata.model.api.sericultureTable;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditSericultureTableRequest extends RequestBody {

    @Schema(name = "sericultureTableId", example = "1")
    Long sericultureTableId;

    @Schema(name = "stepId", example = "1")
    Integer stepId;

    @Schema(name = "daysCount", example = "30")
    Integer daysCount;

    @Schema(name = "schemeId", example = "1")
    Long schemeId;

    @Schema(name = "subSchemeId", example = "1")
    Long subSchemeId;
}
