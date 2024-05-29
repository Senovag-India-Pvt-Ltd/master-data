package com.sericulture.masterdata.model.api.scProgramAccountMapping;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditScProgramAccountMappingRequest extends RequestBody {

    @Schema(name = "scProgramAccountMappingId", example = "1")
    Long scProgramAccountMappingId;

    @Schema(name = "scProgramId", example = "1")
    Long scProgramId;

    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;

    @Schema(name = "scCategoryId", example = "1")
    Long scCategoryId;

}
