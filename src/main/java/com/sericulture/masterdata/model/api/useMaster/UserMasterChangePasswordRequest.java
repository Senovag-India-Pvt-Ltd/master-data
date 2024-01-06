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
public class UserMasterChangePasswordRequest {

    @Schema(name = "userMasterId", example = "1")
    int userMasterId;

    @Schema(name = "currentPassword", example = "123")
    String currentPassword;

    @Schema(name = "newPassword", example = "password")
    String newPassword;

}