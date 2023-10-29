package com.sericulture.masterdata.model.api.caste;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CasteRequestByCode extends RequestBody {
    @Schema(name = "code", example = "123", required = true)
    private String code;

}
