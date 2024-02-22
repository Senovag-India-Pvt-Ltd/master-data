package com.sericulture.masterdata.model.api.reelerTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditReelerTypeMasterRequest extends RequestBody {
    @Schema(name = "reelerTypeMasterId", example = "1")
    Long reelerTypeMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "ReelerType must contain only letters and numbers")
    @Schema(name = "reelerTypeMasterName", example = "Karnataka", required = true)
    String reelerTypeMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "ReelerType in kannada must contain only letters and numbers")
    @Schema(name = "reelerTypeNameInKannada",  example = "ಭಾಷೆ")
    String reelerTypeNameInKannada;

    @Schema(name = "noOfDeviceAllowed", example = "3")
    private Long noOfDeviceAllowed;
}
