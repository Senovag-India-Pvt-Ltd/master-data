package com.sericulture.masterdata.model.api.traderTypeMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraderTypeMasterResponse  {

    @Schema(name = "traderTypeMasterId", example = "1 ")
    int traderTypeMasterId;

    @Schema(name="traderTypeMasterName", example = "trader type 1")
    String traderTypeMasterName;

    @Schema(name = "traderTypeNameInKannada",  example = "ಭಾಷೆ")
    String traderTypeNameInKannada;

    @Schema(name = "noOfDeviceAllowed",  example = "1")
    Long noOfDeviceAllowed;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
