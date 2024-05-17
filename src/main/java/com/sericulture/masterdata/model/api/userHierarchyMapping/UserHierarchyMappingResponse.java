package com.sericulture.masterdata.model.api.userHierarchyMapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserHierarchyMappingResponse {

    @Schema(name = "reporteeUserMasterId", example = "1")
    Long reporteeUserMasterId;

    @Schema(name = "reportToUserMasterId", example = "1")
    Long reportToUserMasterId;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
