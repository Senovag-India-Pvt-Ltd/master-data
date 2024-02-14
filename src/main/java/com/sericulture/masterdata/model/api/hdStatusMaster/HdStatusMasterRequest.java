package com.sericulture.masterdata.model.api.hdStatusMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdStatusMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Status name must contain only letters and numbers")
    @Schema(name = "hdStatusName", example = "Karnataka", required = true)
    String hdStatusName;
}
