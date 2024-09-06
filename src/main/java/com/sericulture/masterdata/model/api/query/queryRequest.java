package com.sericulture.masterdata.model.api.query;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class queryRequest extends RequestBody {
    @Schema(name = "textQueryId", example = "1")
    Long textQueryId;

    @Schema(name = "queryName", example = "Subsidy 1 ", required = true)
    String queryName;


}
