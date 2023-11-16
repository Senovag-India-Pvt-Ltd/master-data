package com.sericulture.masterdata.model.api.scHeadAccount;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditScHeadAccountRequest extends RequestBody {
    @Schema(name = "scHeadAccountId", example = "1")
    Integer scHeadAccountId;

    @Schema(name = "scHeadAccountName", example = "scHeadAccount 1 ", required = true)
    String scHeadAccountName;
}
