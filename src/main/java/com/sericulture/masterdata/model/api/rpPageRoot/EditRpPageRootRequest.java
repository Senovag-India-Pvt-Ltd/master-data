package com.sericulture.masterdata.model.api.rpPageRoot;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditRpPageRootRequest extends RequestBody {
    @Schema(name = "rpPageRootId", example = "1")
    Integer rpPageRootId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "RpPageRoot must contain only letters and numbers")
    @Schema(name = "rpPageRootName", example = "rpPageRoot 1 ", required = true)
    String rpPageRootName;
}
