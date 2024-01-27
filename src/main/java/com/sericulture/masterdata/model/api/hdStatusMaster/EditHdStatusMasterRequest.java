package com.sericulture.masterdata.model.api.hdStatusMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdStatusMasterRequest extends RequestBody {
    @Schema(name = "hdStatusId", example = "1")
    Long hdStatusId;

    @Schema(name = "hdStatusName", example = "Karnataka", required = true)
    String hdStatusName;
}
