package com.sericulture.masterdata.model.api.hdStatusMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdStatusMasterRequest extends RequestBody {
    @Schema(name = "hdStatusName", example = "Karnataka", required = true)
    String hdStatusName;
}