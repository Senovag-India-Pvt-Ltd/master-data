package com.sericulture.masterdata.model.api.hdModuleMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdModuleMasterRequest  extends RequestBody {
    @Schema(name = "hdModuleId", example = "1")
    Long hdModuleId;

    @Schema(name = "hdModuleName", example = "Karnataka", required = true)
    String hdModuleName;

}
