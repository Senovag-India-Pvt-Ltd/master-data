package com.sericulture.masterdata.model.api.subsidy;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SubsidyRequest extends RequestBody {
    @Schema(name = "subsidyName", example = "Subsidy 1", required = true)
    String subsidyName;
}
