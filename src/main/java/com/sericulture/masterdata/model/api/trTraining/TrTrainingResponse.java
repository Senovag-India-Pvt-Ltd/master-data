package com.sericulture.masterdata.model.api.trTraining;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrTrainingResponse {

    @Schema(name = "trTraineeId", example = "1")
    Integer trTraineeId;

    @Schema(name = "trTraineeName", example = "Karnataka", required = true)
    String trTraineeName;

    @Schema(name = "designationId", example = "1")
    Integer designationId;

    @Schema(name = "trOfficeId", example = "1")
    Integer trOfficeId;

    @Schema(name = "gender", example = "1")
    Integer gender;

    @Schema(name = "mobileNumber", example = "Karnataka", required = true)
    String mobileNumber;

    @Schema(name = "place", example = "Karnataka", required = true)
    String place;

    @Schema(name = "stateId", example = "1")
    Integer stateId;

    @Schema(name = "districtId", example = "1")
    Integer districtId;

    @Schema(name = "talukId", example = "1")
    Integer talukId;

    @Schema(name = "hobliId", example = "1")
    Integer hobliId;

    @Schema(name = "villageId", example = "1")
    Integer villageId;

    @Schema(name = "preTestScore", example = "1")
    Integer preTestScore;

    @Schema(name = "postTestScore", example = "1")
    Integer postTestScore;

    @Schema(name = "percentageImproved", example = "1")
    BigDecimal percentageImproved;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}

