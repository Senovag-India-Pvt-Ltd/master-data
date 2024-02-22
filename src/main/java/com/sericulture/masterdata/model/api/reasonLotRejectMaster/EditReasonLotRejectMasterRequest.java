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
public class EditReasonLotRejectMasterRequest extends RequestBody {

    @Schema(name = "reasonLotRejectId", example = "1")
    Integer reasonLotRejectId;


    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "ReasonLotReject must contain only letters and numbers")
    @Schema(name = "reasonLotRejectName", example = "ReasonLotRejectName 1", required = true)
    String reasonLotRejectName;
}