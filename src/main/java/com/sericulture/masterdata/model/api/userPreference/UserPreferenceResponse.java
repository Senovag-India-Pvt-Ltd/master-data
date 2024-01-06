package com.sericulture.masterdata.model.api.userPreference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPreferenceResponse {

    @Schema(name = "userPreferenceId", example = "1")
    int userPreferenceId;

    @Schema(name = "userMasterId", example = "1")
    int userMasterId;

    @Schema(name = "godownId", example = "1")
    int godownId;

    @Schema(name = "username", example = "Shraddha")
    String username;

    @Schema(name = "godownName", example = "Kaveri")
    String godownName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
