package com.sericulture.masterdata.model.api.scApprovingAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScApprovingAuthorityResponse {

    @Schema(name = "scApprovingAuthorityId", example = "1")
    Long scApprovingAuthorityId;

    @Schema(name = "minAmount", example = "1")
    BigDecimal minAmount;

    @Schema(name = "maxAmount", example = "1")
    BigDecimal maxAmount;

    @Schema(name = "type", example = "1")
    Long type;

    @Schema(name = "roleId", example = "1")
    Long roleId;

    @Schema(name = "roleName", example = "1")
    String roleName;


    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
