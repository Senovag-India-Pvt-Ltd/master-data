package com.sericulture.masterdata.model.api.sourceMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SourceMasterRequest extends RequestBody {

    @Schema(name = "sourceMasterName", example = "sourceName 1", required = true)
    String sourceMasterName;

    @Schema(name = "sourceNameInKannada",  example = "ಭಾಷೆ")
    String sourceNameInKannada;
}
