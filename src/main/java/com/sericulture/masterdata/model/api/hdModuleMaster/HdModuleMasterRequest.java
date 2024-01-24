package com.sericulture.masterdata.model.api.hdModuleMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdModuleMasterRequest extends RequestBody {
    @Schema(name = "hdModuleName", example = "Karnataka", required = true)
    String hdModuleName;
}
