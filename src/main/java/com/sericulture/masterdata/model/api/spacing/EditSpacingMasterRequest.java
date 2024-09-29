package com.sericulture.masterdata.model.api.spacing;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditSpacingMasterRequest extends RequestBody {

    @Schema(name = "spacingId", example = "1")
    Long spacingId;

    @Schema(name = "spacingName", example = "Karnataka", required = true)
    String spacingName;


}
