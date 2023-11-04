package com.sericulture.masterdata.model.api.hobli;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditHobliRequest extends RequestBody {
    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Integer districtId;

    @Schema(name = "talukId", example = "1")
    Integer talukId;

    @Schema(name = "hobliId", example = "1")
    Integer hobliId;

    @Schema(name = "hobliName", example = "Kasaba", required = true)
    String hobliName;
}