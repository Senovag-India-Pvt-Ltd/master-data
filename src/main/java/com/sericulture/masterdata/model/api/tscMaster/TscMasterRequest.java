package com.sericulture.masterdata.model.api.tscMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TscMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tsc Master name must contain only letters and numbers")
    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Schema(name = "districtId", example = "1")
    Long districtId;

    @Schema(name = "talukId", example = "1")
    Long talukId;

    @Schema(name = "address", example = "")
    String address;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Tsc Master name in kannada must contain only letters and numbers")
    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;
}
