package com.sericulture.masterdata.model.api.state;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateResponse {

    @Schema(name="stateId", example = "1")
    int stateId;

    @Schema(name = "stateName", example = "Karnataka")
    String stateName;
}