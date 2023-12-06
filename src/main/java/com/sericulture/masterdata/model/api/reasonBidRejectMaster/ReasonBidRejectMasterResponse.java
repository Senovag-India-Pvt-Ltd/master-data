package com.sericulture.masterdata.model.api.reasonBidRejectMaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReasonBidRejectMasterResponse {

    @Schema(name="reasonBidRejectId", example = "1")
    int reasonBidRejectId;

    @Schema(name = "reasonBidRejectName", example = "reasonBidRejectName 1")
    String reasonBidRejectName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
