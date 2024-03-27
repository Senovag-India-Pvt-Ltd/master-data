package com.sericulture.masterdata.model.api.scApprovingAuthority;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScApprovingAuthorityRequest  extends RequestBody {
    @Schema(name = "minAmount", example = "1")
    BigDecimal minAmount;

    @Schema(name = "maxAmount", example = "1")
    BigDecimal maxAmount;

    @Schema(name = "type", example = "1")
    Long type;

    @Schema(name = "roleId", example = "1")
    Long roleId;
}
