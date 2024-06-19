package com.sericulture.masterdata.model.api.taluk;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TalukRequest extends RequestBody {
    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Long districtId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Taluk must contain only letters and numbers")
    @Schema(name = "talukName", example = "Thirthahalli", required = true)
    String talukName;


//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Taluk in kannada must contain only letters and numbers")
    @Schema(name = "talukNameInKannada",  example = "ಭಾಷೆ")
    String talukNameInKannada;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "lgTaluk must contain only letters and numbers")
    @Schema(name = "lgTaluk", example = "Shimoga")
    String lgTaluk;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Taluk Code must contain only letters and numbers")
    @Schema(name = "talukCode", example = "1234")
    String talukCode;
}