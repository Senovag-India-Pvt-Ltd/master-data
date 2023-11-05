package com.sericulture.masterdata.model.api.reasonLotRejectMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditReasonLotRejectMasterRequest extends RequestBody {

    @Schema(name = "reasonLotRejectId", example = "1")
    Integer reasonLotRejectId;

    @Schema(name = "reasonLotRejectName", example = "ReasonLotRejectName 1", required = true)
    String reasonLotRejectName;
}