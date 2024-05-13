package com.sericulture.masterdata.model.api.rendittaMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RendittaMasterResponse {

    @Schema(name = "rendittaMasterId", example = "1")
    Long rendittaMasterId;

    @Schema(name = "raceMasterId", example = "1")
    Long raceMasterId;

    @Schema(name = "value", example = "1")
    BigDecimal value;

    @Schema(name = "raceMasterName", example = "raceName 1")
    String raceMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
