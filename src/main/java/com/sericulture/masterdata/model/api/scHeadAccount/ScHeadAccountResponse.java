package com.sericulture.masterdata.model.api.scHeadAccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScHeadAccountResponse {

    @Schema(name = "scHeadAccountId", example = "1")
    Integer scHeadAccountId;

    @Schema(name = "scHeadAccountName", example = "scHeadAccount 1 ")
    String scHeadAccountName;
}
