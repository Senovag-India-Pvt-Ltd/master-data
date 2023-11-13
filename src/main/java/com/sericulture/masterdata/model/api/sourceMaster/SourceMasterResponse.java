package com.sericulture.masterdata.model.api.sourceMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceMasterResponse {

    @Schema(name="sourceMasterId", example = "1")
    int sourceMasterId;

    @Schema(name = "sourceMasterName", example = "sourceName 1")
    String sourceMasterName;
}
