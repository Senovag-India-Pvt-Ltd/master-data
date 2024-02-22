package com.sericulture.masterdata.model.api.hdAnswerMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdAnswerMasterRequest extends RequestBody {
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "HD Answer name must contain only letters and numbers")
    @Schema(name = "hdAnswerName", example = "Karnataka", required = true)
    String hdAnswerName;
}
