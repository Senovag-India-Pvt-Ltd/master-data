package com.sericulture.masterdata.model.api.hdModuleMaster;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class HdModuleMasterResponse {

    @Schema(name = "hdModuleId", example = "1")
    Long hdModuleId;

    @Schema(name = "hdModuleName", example = "Karnataka", required = true)
    String hdModuleName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}
