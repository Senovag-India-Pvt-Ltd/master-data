package com.sericulture.masterdata.model.api.caste;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CasteRequestByTitle extends RequestBody {
    @Schema(name = "title", example = "GM", required = true)
    private String title;

}
