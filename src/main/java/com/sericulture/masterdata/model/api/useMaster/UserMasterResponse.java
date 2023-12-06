package com.sericulture.masterdata.model.api.useMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMasterResponse {

    @Schema(name = "userMasterId", example = "1")
    int userMasterId;

    @Schema(name = "firstName", example = "Shraddha")
    String firstName;

    @Schema(name = "middleName", example = "Nagaraja")
    String middleName;

    @Schema(name = "lastName", example = "Kharvi")
    String lastName;

    @Schema(name = "password", example = "12345")
    String password;

    @Schema(name = "emailID", example = "12S")
    String emailID;

    @Schema(name = "stateId", example = "1")
    int stateId;

    @Schema(name = "districtId", example = "1")
    int districtId;

    @Schema(name = "talukId", example = "1")
    int talukId;

    @Schema(name = "roleId", example = "1")
    int roleId;

    @Schema(name = "marketMasterId", example = "1")
    int marketMasterId;

    @Schema(name = "stateName", example = "Karnataka")
    String stateName;

    @Schema(name = "districtId", example = "Udupi")
    String districtName;

    @Schema(name = "talukName", example = "Kundapura")
    String talukName;

    @Schema(name = "roleName", example = "Role name 5")
    String roleName;

    @Schema(name = "marketMasterName", example = "Kaveri")
    String marketMasterName;

    @Schema(name = "username", example = "test")
    String username;

    @Schema(name = "designationId", example = "1")
    Long designationId;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
