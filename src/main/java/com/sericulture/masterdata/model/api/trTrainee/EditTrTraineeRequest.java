package com.sericulture.masterdata.model.api.trTrainee;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditTrTraineeRequest extends RequestBody {

    @Schema(name = "trScheduleId", example = "1")
    Long trScheduleId;

    @Schema(name = "trTraineeId", example = "1")
    Long trTraineeId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr trainee name must contain only letters and numbers")
    @Schema(name = "trTraineeName", example = "Karnataka", required = true)
    String trTraineeName;

    @Schema(name = "designationId", example = "1")
    Long designationId;

    @Schema(name = "trOfficeId", example = "1")
    Long trOfficeId;

    @Schema(name = "gender", example = "1")
    Long gender;

    @Pattern(regexp = "^[0-9+\\s]*$", message = "Mobile number must contain only numbers")
    @Schema(name = "mobileNumber", example = "Karnataka", required = true)
    String mobileNumber;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Place must contain only letters and numbers")
    @Schema(name = "place", example = "Karnataka", required = true)
    String place;

    @Schema(name = "stateId", example = "1")
    Long stateId;

    @Schema(name = "districtId", example = "1")
    Long districtId;

    @Schema(name = "talukId", example = "1")
    Long talukId;

    @Schema(name = "hobliId", example = "1")
    Long hobliId;

    @Schema(name = "villageId", example = "1")
    Long villageId;

    @Schema(name = "preTestScore", example = "1")
    Long preTestScore;

    @Schema(name = "postTestScore", example = "1")
    Long postTestScore;

    @Schema(name = "percentageImproved", example = "1")
    BigDecimal percentageImproved;


}
