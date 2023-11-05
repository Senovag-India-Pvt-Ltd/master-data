package com.sericulture.masterdata.model.api.reasonBidRejectMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditReasonBidRejectMasterRequest extends RequestBody {
    @Schema(name = "reasonBidRejectId", example = "1")
    Integer reasonBidRejectId;

    @Schema(name = "reasonBidRejectName", example = "ReasonBidRejectName 1", required = true)
    String reasonBidRejectName;
}
