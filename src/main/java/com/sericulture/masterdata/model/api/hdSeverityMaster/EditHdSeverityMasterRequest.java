package com.sericulture.masterdata.model.api.hdSeverityMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.checkerframework.common.aliasing.qual.NonLeaked;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdSeverityMasterRequest extends RequestBody {
    @Schema(name = "hdSeverityId", example = "1")
    Long hdSeverityId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Severity name must contain only letters and numbers")
    @Schema(name = "hdSeverityName", example = "Karnataka", required = true)
    String hdSeverityName;
}
