package com.sericulture.masterdata.model.api.hobli;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditHobliRequest extends RequestBody {
    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Integer districtId;

    @Schema(name = "talukId", example = "1")
    Integer talukId;

    @Schema(name = "hobliId", example = "1")
    Integer hobliId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Hobli name must contain only letters and numbers")
    @Schema(name = "hobliName", example = "Kasaba", required = true)
    String hobliName;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Hobli name in kannada must contain only letters and numbers")
    @Schema(name = "hobliNameInKannada",  example = "ಭಾಷೆ")
    String hobliNameInKannada;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Hobli Code must contain only letters and numbers")
    @Schema(name = "hobliCode", example = "1234")
    String hobliCode;
}