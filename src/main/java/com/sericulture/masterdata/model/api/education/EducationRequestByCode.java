package com.sericulture.masterdata.model.api.education;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EducationRequestByCode extends RequestBody {
    @Schema(name = "code", example = "BE", required = true)
    private String code;

}
