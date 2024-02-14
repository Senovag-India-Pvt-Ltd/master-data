package com.sericulture.masterdata.model.api.hdStatusMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdStatusMasterRequest extends RequestBody {
    @Schema(name = "hdStatusId", example = "1")
    Long hdStatusId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Status name must contain only letters and numbers")
    @Schema(name = "hdStatusName", example = "Karnataka", required = true)
    String hdStatusName;
}
