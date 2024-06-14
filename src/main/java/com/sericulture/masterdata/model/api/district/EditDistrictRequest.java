package com.sericulture.masterdata.model.api.district;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditDistrictRequest extends RequestBody {
    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Integer districtId;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "District name must contain only letters and numbers")
    @Schema(name = "districtName", example = "Shimoga", required = true)
    String districtName;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "District name in kannada must contain only letters and numbers")
    @Schema(name = "districtNameInKannada",  example = "ಭಾಷೆ")
    String districtNameInKannada;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "lgDistrict must contain only letters and numbers")
    @Schema(name = "lgDistrict", example = "Shimoga")
    String lgDistrict;

//    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "District Code must contain only letters and numbers")
    @Schema(name = "districtCode", example = "1234")
    String districtCode;
}