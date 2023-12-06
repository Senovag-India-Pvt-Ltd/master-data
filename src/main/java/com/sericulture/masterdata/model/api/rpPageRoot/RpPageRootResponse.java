package com.sericulture.masterdata.model.api.rpPageRoot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RpPageRootResponse {

    @Schema(name = "rpPageRootId", example = "1")
    Integer rpPageRootId;

    @Schema(name = "rpPageRootName", example = "rpPageRoot 1 ")
    String rpPageRootName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
