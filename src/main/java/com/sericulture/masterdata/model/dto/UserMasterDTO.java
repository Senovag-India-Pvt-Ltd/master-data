package com.sericulture.masterdata.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMasterDTO {

    private Long userMasterId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String emailID;
    private Long stateId;
    private Long districtId;
    private Long talukId;
    private Long roleId;
    private Long marketMasterId;
    private Long tscMasterId;
    private String tscName;
    private String stateName;
    private String districtName;
    private String talukName;
    private String roleName;
    private String marketMasterName;
    private String username;
    private Long designationId;
    private String name;
    private String phoneNumber;
    private int userType;
    private Long userTypeId;
    private String deviceId;
    private Long workingInstitutionId;
    private String ddoCode;
    private String khazaneRecipientId;
    private String workingInstitutionName;
    private String enteredOtpByUser;
    private Long divisionMasterId;
    private Boolean allowAnyUser;
    private String nameInKannada;
}
