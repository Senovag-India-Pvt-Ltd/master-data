package com.sericulture.masterdata.model.api.designation;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DesignationResponse extends RequestBody {

    @Schema(name = "designationId", example = "1")
    Integer designationId;

    @Schema(name = "name", example = "Admin", required = true)
    String name;

    @Schema(name = "amount", example = "amount", required = true)
    BigDecimal amount;

    @Schema(name = "designationNameInKannada",  example = "ಭಾಷೆ")
    String designationNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}