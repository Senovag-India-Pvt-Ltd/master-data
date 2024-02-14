package com.sericulture.masterdata.model.api.hdSeverityMaster;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdSeverityMasterRequest {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Severity name must contain only letters and numbers")
    @Schema(name = "hdSeverityName", example = "Karnataka", required = true)
    String hdSeverityName;
}
