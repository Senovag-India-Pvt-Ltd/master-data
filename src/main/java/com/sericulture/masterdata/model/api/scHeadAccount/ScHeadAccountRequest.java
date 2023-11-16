package com.sericulture.masterdata.model.api.scHeadAccount;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScHeadAccountRequest extends RequestBody {
    @Schema(name = "scHeadAccountName", example = "scHeadAccount 1 ", required = true)
    String scHeadAccountName;
}
