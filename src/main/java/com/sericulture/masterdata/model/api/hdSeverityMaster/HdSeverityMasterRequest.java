package com.sericulture.masterdata.model.api.hdSeverityMaster;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdSeverityMasterRequest {
    @Schema(name = "hdSeverityName", example = "Karnataka", required = true)
    String hdSeverityName;
}
