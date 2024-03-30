package com.sericulture.masterdata.model.api.scHeadAccount;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditScHeadAccountRequest extends RequestBody {
    @Schema(name = "scHeadAccountId", example = "1")
    Long scHeadAccountId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "scHeadAccount must contain only letters and numbers")
    @Schema(name = "scHeadAccountName", example = "scHeadAccount 1 ", required = true)
    String scHeadAccountName;

    @Pattern(regexp = "^[a-zA-Z0-9\\s\\u0C80-\\u0CFF]*$", message = "Head Of Account name in kannada must contain only letters and numbers")
    @Schema(name = "scHeadAccountNameInKannada",  example = "ಭಾಷೆ")
    String scHeadAccountNameInKannada;

    @Schema(name = "scSchemeDetailsId", example = "1")
    Long scSchemeDetailsId;
}
