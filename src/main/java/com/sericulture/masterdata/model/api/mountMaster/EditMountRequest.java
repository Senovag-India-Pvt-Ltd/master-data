package com.sericulture.masterdata.model.api.mountMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditMountRequest extends RequestBody {
    @Schema(name = "mountId", example = "1")
    Long mountId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Name must contain only letters and numbers")
    @Schema(name = "name",  example = "Karnataka",required=true)
    String name;
}
