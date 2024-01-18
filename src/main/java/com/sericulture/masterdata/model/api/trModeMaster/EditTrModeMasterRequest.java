package com.sericulture.masterdata.model.api.trModeMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditTrModeMasterRequest extends RequestBody {
    @Schema(name = "trModeMasterId", example = "1")
    Integer trModeMasterId;

    @Schema(name = "trModeMasterName", example = "Karnataka", required = true)
    String trModeMasterName;
}

