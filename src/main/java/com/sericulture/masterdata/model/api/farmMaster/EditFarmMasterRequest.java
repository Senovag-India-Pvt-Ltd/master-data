package com.sericulture.masterdata.model.api.farmMaster;


import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditFarmMasterRequest extends RequestBody {

    @Schema(name = "farmId", example = "1")
    Long farmId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "FarmMaster name must contain only letters and numbers")
    @Schema(name = "farmName", example = "Karnataka", required = true)
    String farmName;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "FarmMaster in kannada must contain only letters and numbers")
    @Schema(name = "farmNameInKannada", example = "ಭಾಷೆ", required = true)
    String farmNameInKannada;

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;

    @Schema(name = "isBsf", example = "yes")
    String isBsf;

}
