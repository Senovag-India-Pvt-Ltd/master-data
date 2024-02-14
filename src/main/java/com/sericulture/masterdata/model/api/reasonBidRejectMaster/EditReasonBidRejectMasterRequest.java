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
public class EditReasonBidRejectMasterRequest extends RequestBody {
    @Schema(name = "reasonBidRejectId", example = "1")
    Integer reasonBidRejectId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "ReasonBidReject must contain only letters and numbers")
    @Schema(name = "reasonBidRejectName", example = "ReasonBidRejectName 1", required = true)
    String reasonBidRejectName;
}
