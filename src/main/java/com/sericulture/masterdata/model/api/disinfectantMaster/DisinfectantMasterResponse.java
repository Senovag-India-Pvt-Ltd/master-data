package com.sericulture.masterdata.model.api.disinfectantMaster;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisinfectantMasterResponse {

    @Schema(name = "disinfectantMasterId", example = "1")
    Integer disinfectantMasterId;

    @Schema(name = "disinfectantMasterName", example = "Karnataka", required = true)
    String disinfectantMasterName;

    @Schema(name = "disinfectantMasterNameInKannada", example = "ಭಾಷೆ", required = true)
    String disinfectantMasterNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
