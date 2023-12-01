package com.sericulture.masterdata.model.api.designation;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditDesignationRequest extends RequestBody {

    @Schema(name = "designationId", example = "1")
    Integer designationId;

    @Schema(name = "name", example = "Admin", required = true)
    String name;
}