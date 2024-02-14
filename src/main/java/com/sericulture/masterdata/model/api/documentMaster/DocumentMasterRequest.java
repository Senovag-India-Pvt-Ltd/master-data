package com.sericulture.masterdata.model.api.documentMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DocumentMasterRequest extends RequestBody {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Document master name must contain only letters and numbers")
    @Schema(name = "documentMasterName", example = "documentMasterName 1", required = true)
    String documentMasterName;
}
