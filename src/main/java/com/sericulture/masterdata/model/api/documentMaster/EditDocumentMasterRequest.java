package com.sericulture.masterdata.model.api.documentMaster;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditDocumentMasterRequest {

    @Schema(name = "documentMasterId", example = "1")
    Integer documentMasterId;

    @Schema(name = "documentMasterName", example = "documentName 1",required=true)
    String documentMasterName;
}
