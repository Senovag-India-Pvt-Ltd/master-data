package com.sericulture.masterdata.model.api.traderTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Name;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TraderTypeMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "TraderType must contain only letters and numbers")
    @Schema(name = "traderTypeMasterName", example = "trader type 1", required = true)
    String traderTypeMasterName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "TraderType in kannada must contain only letters and numbers")
    @Schema(name = "traderTypeNameInKannada",  example = "ಭಾಷೆ")
    String traderTypeNameInKannada;
}
