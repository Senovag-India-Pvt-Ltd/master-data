package com.sericulture.masterdata.model.api.hdModuleMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdModuleMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Module name must contain only letters and numbers")
    @Schema(name = "hdModuleName", example = "Karnataka", required = true)
    String hdModuleName;
}
