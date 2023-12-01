package com.sericulture.masterdata.model.api.useMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditUserMasterRequest extends RequestBody {

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;

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

    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Long districtId;

    @Schema(name = "talukId", example = "1")
    Long talukId;

    @Schema(name = "roleId", example = "1")
    Long roleId;

    @Schema(name = "marketId", example = "1")
    Long marketId;
}
