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
    private String workingInstitutionName;
    private String enteredOtpByUser; //User entered otp for verification
//    public UserMasterDTO() {}
//
//    public UserMasterDTO(
//            Long userMasterId,
//            String firstName,
//            String middleName,
//            String lastName,
//            String password,
//            String emailID,
//            Long stateId,
//            Long districtId,
//            Long talukId,
//            Long roleId,
//            Long marketMasterId,
//            String stateName,
//            String districtName,
//            String talukName,
//            String roleName,
//            String marketMasterName,
//            String username,
//            Long designationId,
//            String name,
//            String phoneNumber,
//            int userType,
//            Long userTypeId,
//            String deviceId,
//            Long workingInstitutionId,
//            String ddoCode,
//            String workingInstitutionName
//    ) {
//        // Initialize your fields here
//        this.userMasterId = userMasterId;
//        this.firstName = firstName;
//        this.middleName = middleName;
//        this.lastName = lastName;
//        this.password = password;
//        this.emailID = emailID;
//        this.stateId = stateId;
//        this.districtId = districtId;
//        this.talukId = talukId;
//        this.roleId = roleId;
//        this.marketMasterId = marketMasterId;
//        this.stateName = stateName;
//        this.districtName = districtName;
//        this.talukName = talukName;
//        this.roleName = roleName;
//        this.marketMasterName = marketMasterName;
//        this.username = username;
//        this.designationId = designationId;
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.userType = userType;
//        this.userTypeId = userTypeId;
//        this.deviceId = deviceId;
//        this.workingInstitutionId = workingInstitutionId;
//        this.ddoCode = ddoCode;
//        this.workingInstitutionName = workingInstitutionName;
//    }
}
