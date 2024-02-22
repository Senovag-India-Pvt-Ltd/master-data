package com.sericulture.masterdata.model.api.trainingDeputationTracker;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditTrainingDeputationTrackerRequest extends RequestBody {

    @Schema(name = "trainingDeputationId", example = "1")
    Long trainingDeputationId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "OfficialName must contain only letters and numbers")
    @Schema(name = "officialName", example = "Kaveri", required=true)
    String officialName;

    @Schema(name = "designationId",  example = "1")
    Long designationId;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "OfficialAddress must contain only letters and numbers")
    @Schema(name = "officialAddress", example = "Udupi")
    String officialAddress;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "mobileNumber must contain only letters and numbers")
    @Schema(name = "mobileNumber", example = "10")
    String mobileNumber;

    @Schema(name = "deputedInstituteId", example = "5")
    Long deputedInstituteId;

    @Schema(name = "deputedFromDate", example = "1")
    Date deputedFromDate;

    @Schema(name = "deputedToDate", example = "1")
    Date deputedToDate;

    @Schema(name = "trProgramMasterId", example = "1")
    Long trProgramMasterId;

    @Schema(name = "trCourseMasterId", example = "1")
    Long trCourseMasterId;

    @Schema(name = "deputedAttended", example = "1")
    int deputedAttended;


    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "DeputedRemarks must contain only letters and numbers")
    @Schema(name = "deputedRemarks", example = "1")
    String deputedRemarks;
}
