package com.sericulture.masterdata.model.api.hdStatusMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class HdStatusMasterResponse {
    @Schema(name = "hdStatusId", example = "1")
    Long hdStatusId;

    @Schema(name = "hdStatusName", example = "Karnataka", required = true)
    String hdStatusName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
