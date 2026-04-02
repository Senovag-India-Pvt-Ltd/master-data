package com.sericulture.masterdata.model.api.externalUnitType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalUnitTypeResponse {
    @Schema(name = "externalUnitTypeId", example = "1")
    Integer externalUnitTypeId;

    @Schema(name = "externalUnitTypeName", example = "external unit type 1")
    String externalUnitTypeName;

    @Schema(name = "externalUnitTypeNameInKannada",  example = "ಭಾಷೆ")
    String externalUnitTypeNameInKannada;

    @Schema(name = "paymentViaBank", example = "true", description = "Payment via bank selected")
    private Boolean paymentViaBank;

    @Schema(name = "paymentViaK2", example = "false", description = "Payment via K2 selected")
    private Boolean paymentViaK2;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}
