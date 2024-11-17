package com.sericulture.masterdata.model.api.mulberryTargetType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MulberryTargetTypeResponse {

    @Schema(name = "mulberryTargetTypeId", example = "1")
    Long mulberryTargetTypeId;

    @Schema(name = "mulberryTargetTypeName", example = "Commercial Market", required = true)
    String mulberryTargetTypeName;

    @Schema(name = "mulberryTargetTypeNameInKannada", example = "Commercial Market", required = true)
    String mulberryTargetTypeNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
