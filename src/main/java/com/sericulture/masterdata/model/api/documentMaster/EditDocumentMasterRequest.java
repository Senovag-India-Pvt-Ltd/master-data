package com.sericulture.masterdata.model.api.documentMaster;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditDocumentMasterRequest {

    @Schema(name = "documentMasterId", example = "1")
    Integer documentMasterId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Document must contain only letters and numbers")
    @Schema(name = "documentMasterName", example = "documentName 1",required=true)
    String documentMasterName;
}
