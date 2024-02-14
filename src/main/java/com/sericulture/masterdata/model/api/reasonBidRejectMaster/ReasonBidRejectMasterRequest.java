package com.sericulture.masterdata.model.api.reasonBidRejectMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ReasonBidRejectMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "ReasonBidReject must contain only letters and numbers")
    @Schema(name = "reasonBidRejectName", example = "Reason bid reject name", required = true)
    String reasonBidRejectName;
}
