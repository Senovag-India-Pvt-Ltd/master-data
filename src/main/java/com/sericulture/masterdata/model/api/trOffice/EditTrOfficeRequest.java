package com.sericulture.masterdata.model.api.trOffice;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditTrOfficeRequest extends RequestBody {
    @Schema(name = "trOfficeId", example = "1")
    Integer trOfficeId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr Office name must contain only letters and numbers")
    @Schema(name = "trOfficeName", example = "Karnataka", required = true)
    String trOfficeName;
}
