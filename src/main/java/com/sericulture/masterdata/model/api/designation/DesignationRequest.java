package com.sericulture.masterdata.model.api.designation;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DesignationRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Designation must contain only letters and numbers")
    @Schema(name = "name", example = "Admin", required = true)
    String name;

    @Schema(name = "amount", example = "amount", required = true)
    BigDecimal amount;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Designation name in kannada must contain only letters and numbers")
    @Schema(name = "designationNameInKannada",  example = "ಭಾಷೆ")
    String designationNameInKannada;
}