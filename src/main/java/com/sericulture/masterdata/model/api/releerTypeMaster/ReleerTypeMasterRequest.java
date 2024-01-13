package com.sericulture.masterdata.model.api.releerTypeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReleerTypeMasterRequest extends RequestBody {

    @Schema(name = "releerTypeMasterName", example = "Karnataka", required = true)
    String releerTypeMasterName;
}
