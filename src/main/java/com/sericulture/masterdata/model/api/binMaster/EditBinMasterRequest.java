package com.sericulture.masterdata.model.api.binMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditBinMasterRequest extends RequestBody {
    @Schema(name="binMasterId", example = "1")
    Integer binMasterId;

    @Schema(name="binCounterMasterId", example = "1")
    Integer binCounterMasterId;

    @Schema(name="marketId", example = "1")
    Integer marketId;

    @Schema(name="godownId", example = "1")
    Integer godownId;

    @Schema(name = "binNumber", example = "12S")
    Integer binNumber;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Type must contain only letters and numbers")
    @Schema(name = "type", example = "small",required=true)
    String type;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Status must contain only letters and numbers")
    @Schema(name = "status", example = "available",required=true)
    String status;
}
