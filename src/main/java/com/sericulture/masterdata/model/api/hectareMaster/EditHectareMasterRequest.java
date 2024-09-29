package com.sericulture.masterdata.model.api.hectareMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditHectareMasterRequest extends RequestBody {

    @Schema(name = "hectareId", example = "1")
    Long hectareId;

    @Schema(name = "hectareName", example = "Karnataka", required = true)
    String hectareName;


}
