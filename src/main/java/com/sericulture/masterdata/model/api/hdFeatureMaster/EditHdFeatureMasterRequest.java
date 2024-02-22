package com.sericulture.masterdata.model.api.hdFeatureMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdFeatureMasterRequest extends RequestBody {
    @Schema(name = "hdFeatureId", example = "1")
    Long hdFeatureId;

    @Schema(name = "hdModuleId", example = "1")
    Long hdModuleId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD feature name must contain only letters and numbers")
    @Schema(name = "hdFeatureName", example = "raceName 1", required = true)
    String hdFeatureName;
}
