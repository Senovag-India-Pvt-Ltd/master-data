package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
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
    private String stateName;
    private String districtName;
    private String talukName;
    private String roleName;
    private String marketMasterName;
    private String username;
    private Long designationId;

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
            Long designationId
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
    }
}
