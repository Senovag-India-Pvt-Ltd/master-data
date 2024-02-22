package com.sericulture.masterdata.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserMasterDTO {

    private Long userMasterId;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "First name must contain only letters and numbers")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Middle name must contain only letters and numbers")
    private String middleName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Last name must contain only letters and numbers")
    private String lastName;
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9.@]*$", message = "Email must contain only letters and numbers")
    private String emailID;
    private Long stateId;
    private Long districtId;
    private Long talukId;
    private Long roleId;
    private Long marketMasterId;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "State name must contain only letters and numbers")
    private String stateName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "District name must contain only letters and numbers")
    private String districtName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Taluk name must contain only letters and numbers")
    private String talukName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Role name must contain only letters and numbers")
    private String roleName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Market master name must contain only letters and numbers")
    private String marketMasterName;
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must contain only letters and numbers")
    private String username;
    private Long designationId;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Name must contain only letters and numbers")
    private String name;

    @Pattern(regexp = "^[+0-9\\s]*$", message = "Phone number must contain only letters and numbers")
    private String phoneNumber;

    private int userType;

    private Long userTypeId;

    @Pattern(regexp = "^[a-zA-Z0-9-\\s]*$", message = "Device Id must contain only letters and numbers")
    private String deviceId;

    private Long workingInstitutionId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Working institution name must contain only letters and numbers")
    private String workingInstitutionName;

    @Pattern(regexp = "^[0-9\\s]*$", message = "OTP must contain only numbers")
    private String enteredOtpByUser; //User entered otp for verification
    // Constructors (default and the one you've specified)
    public UserMasterDTO() {}

    public UserMasterDTO(
            Long userMasterId,
            String firstName,
            String middleName,
            String lastName,
            String password,
            String emailID,
            Long stateId,
            Long districtId,
            Long talukId,
            Long roleId,
            Long marketMasterId,
            String stateName,
            String districtName,
            String talukName,
            String roleName,
            String marketMasterName,
            String username,
            Long designationId,
            String name,
            String phoneNumber,
            int userType,
            Long userTypeId,
            String deviceId,
            Long workingInstitutionId,
            String workingInstitutionName
    ) {
        // Initialize your fields here
        this.userMasterId = userMasterId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.emailID = emailID;
        this.stateId = stateId;
        this.districtId = districtId;
        this.talukId = talukId;
        this.roleId = roleId;
        this.marketMasterId = marketMasterId;
        this.stateName = stateName;
        this.districtName = districtName;
        this.talukName = talukName;
        this.roleName = roleName;
        this.marketMasterName = marketMasterName;
        this.username = username;
        this.designationId = designationId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.userTypeId = userTypeId;
        this.deviceId = deviceId;
        this.workingInstitutionId = workingInstitutionId;
        this.workingInstitutionName = workingInstitutionName;
    }
}
