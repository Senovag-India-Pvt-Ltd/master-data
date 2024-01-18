package com.sericulture.masterdata.model.api.trModeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

public class TrModeMasterRequest extends RequestBody {
    @Schema(name = "trModeMasterName", example = "Karnataka", required = true)
    String trModeMasterName;
}
