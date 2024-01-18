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

    @Schema(name = "name", example = "1")
    String name;

    @Schema(name = "phoneNumber", example = "9988776655")
    String phoneNumber;

    @Schema(name = "otpVerified", example = "true")
    Boolean otpVerified;

    @Schema(name = "userType", example = "1")
    int userType;

    @Schema(name = "userTypeId", example = "1")
    Long userTypeId;

    @Schema(name = "deviceId", example = "998s3gf6478776655")
    String deviceId;

    @Schema(name = "workingInstitutionId", example = "1")
    int workingInstitutionId;

    @Schema(name = "workingInstitutionName", example = "Government")
    String workingInstitutionName;

    @Schema(name = "currentReelerUsers", example = "1")
    int currentReelerUsers;

    @Schema(name = "maxReelerUsers", example = "1")
    Long maxReelerUsers;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
