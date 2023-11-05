package com.sericulture.masterdata.model.api.reasonBidRejectMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ReasonBidRejectMasterRequest extends RequestBody {

    @Schema(name = "reasonBidRejectName", example = "Reason bid reject name", required = true)
    String reasonBidRejectName;
}
