package com.sericulture.masterdata.model.api.silkExchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class SilkExchangeResponse {
    @Schema(name = "silkExchangeId", example = "1")
    Long silkExchangeId;

    @Schema(name = "silkExchangeName", example = "Karnataka", required = true)
    String silkExchangeName;

    @Schema(name = "silkExchNameInKannada",  example = "ಭಾಷೆ")
    String silkExchNameInKannada;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;

}
