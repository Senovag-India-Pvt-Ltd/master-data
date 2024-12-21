package com.sericulture.masterdata.model.api.configurePmkysAmount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurePmkysAmountResponse{
    @Schema(name = "configurePmkysAmountId", example = "1",required=true)
    Long configurePmkysAmountId;

    @Schema(name = "spacingId", example = "1",required=true)
    Long spacingId;

    @Schema(name = "hectareId", example = "1",required=true)
    Long hectareId;

    @Schema(name = "amount", example = "2",required=true)
    Float amount;

    @Schema(name = "spacingName", example = "2",required=true)
    String spacingName;

    @Schema(name = "hectareName", example = "2",required=true)
    String hectareName;

    @Schema(name = "lowestAmount", example = "2",required=true)
    Float lowestAmount;

    @Schema(name = "highestAmount", example = "2",required=true)
    Float highestAmount;

    @Schema(name = "highestArea", example = "2",required=true)
    Float highestArea;

    @Schema(name = "lowestArea", example = "2",required=true)
    Float lowestArea;

    @Schema(name = "targetArea", example = "2",required=true)
    Float targetArea;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
