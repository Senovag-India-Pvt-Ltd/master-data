package com.sericulture.masterdata.model.api.userHierarchyMapping;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserHierarchyMappingRequest extends RequestBody {

    @Schema(name = "reporteeUserMasterId", example = "1")
    Long reporteeUserMasterId;

    @Schema(name = "reportToUserMasterId", example = "1")
    Long reportToUserMasterId;
}
