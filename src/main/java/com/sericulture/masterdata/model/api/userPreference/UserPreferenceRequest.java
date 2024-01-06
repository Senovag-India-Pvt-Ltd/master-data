package com.sericulture.masterdata.model.api.userPreference;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserPreferenceRequest extends RequestBody {
    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;

    @Schema(name = "godownId", example = "1")
    Long godownId;
}
