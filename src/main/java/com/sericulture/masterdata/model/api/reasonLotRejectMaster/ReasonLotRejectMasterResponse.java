package com.sericulture.masterdata.model.api.reasonLotRejectMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReasonLotRejectMasterResponse {

    @Schema(name="reasonLotRejectId", example = "1")
    int reasonLotRejectId;

    @Schema(name = "reasonLotRejectName", example = "reasonLotRejectName 1")
    String reasonLotRejectName;
}