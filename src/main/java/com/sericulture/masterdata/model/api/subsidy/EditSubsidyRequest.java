package com.sericulture.masterdata.model.api.subsidy;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditSubsidyRequest extends RequestBody {
    @Schema(name = "subsidyId", example = "1")
    Integer subsidyId;

    @Schema(name = "subsidyName", example = "Subsidy 1 ", required = true)
    String subsidyName;
}
