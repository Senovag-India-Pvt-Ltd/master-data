package com.sericulture.masterdata.model.api.hectareMaster;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class HectareMasterRequest {

    @Schema(name = "hectareName", example = "Karnataka", required = true)
    String hectareName;
}
