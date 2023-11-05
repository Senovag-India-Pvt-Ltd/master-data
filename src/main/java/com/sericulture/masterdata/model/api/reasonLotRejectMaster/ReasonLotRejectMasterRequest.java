package com.sericulture.masterdata.model.api.reasonLotRejectMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ReasonLotRejectMasterRequest extends RequestBody {

    @Schema(name = "reasonLotRejectName", example = "Reason lot reject name", required = true)
    String reasonLotRejectName;
}