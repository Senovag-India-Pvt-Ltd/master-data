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

    @Schema(name = "userMasterId", example = "1", required = true)
    int userMasterId;

    @Schema(name = "firstName", example = "Shraddha", required = true)
    String firstName;

    @Schema(name = "middleName", example = "Nagaraja", required = true)
    String middleName;

    @Schema(name = "lastName", example = "Kharvi", required = true)
    String lastName;

    @Schema(name = "password", example = "12345", required = true)
    String password;

    @Schema(name = "emailID", example = "12S", required = true)
    String emailID;

    @Schema(name = "stateId", example = "1", required = true)
    int stateId;

    @Schema(name = "districtId", example = "1", required = true)
    int districtId;

    @Schema(name = "talukId", example = "1", required = true)
    int talukId;

    @Schema(name = "roleId", example = "1", required = true)
    int roleId;

    @Schema(name = "marketId", example = "1", required = true)
    int marketId;
}
