package com.sericulture.masterdata.model.api.useMaster;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMasterDetailsResponse {
    private int serialNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String emailId;
    private String tscName;
    private String stateName;
    private String districtName;
    private String talukName;
    private String roleName;
    private String marketName;
    private String username;
    private String designationName;
    private String phoneNumber;
    private String ddoCode;
    private String khazaneRecipientId;
    private String workingInstitutionName;
}
