package com.sericulture.masterdata.model.api.scVendor;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScVendorRequest  extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Sc Vendor name must contain only letters and numbers")
    @Schema(name = "name", example = "Karnataka", required = true)
    String name;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = " Sc Vendor Name In Kannada must contain only letters and numbers")
    @Schema(name = "nameInKannada", example = "ಕನ್ನಡ")
    String nameInKannada;

    @Schema(name = "type", example = "1")
    Long type;
}
