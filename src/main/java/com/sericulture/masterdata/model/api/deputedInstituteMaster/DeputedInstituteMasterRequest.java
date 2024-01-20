package com.sericulture.masterdata.model.api.deputedInstituteMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DeputedInstituteMasterRequest extends RequestBody {

    @Schema(name = "deputedInstituteName", example = "external unit type 1", required = true)
    String deputedInstituteName;
}
