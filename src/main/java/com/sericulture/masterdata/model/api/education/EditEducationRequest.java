package com.sericulture.masterdata.model.api.education;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditEducationRequest extends RequestBody {

    @Schema(name = "id", example = "1")
    Integer id;

    @Schema(name = "name", example = "Bachelor of Engineering", required = true)
    String name;
}
