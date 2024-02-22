package com.sericulture.masterdata.model.api.reasonLotRejectMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ReasonLotRejectMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "ReasonLotReject must contain only letters and numbers")
    @Schema(name = "reasonLotRejectName", example = "Reason lot reject name", required = true)
    String reasonLotRejectName;
}