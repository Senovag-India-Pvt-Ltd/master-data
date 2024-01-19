package com.sericulture.masterdata.model.api.trainingDeputationTracker;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TrainingDeputationTrackerRequest extends RequestBody {

    @Schema(name = "officialName", example = "Kaveri", required=true)
    String officialName;

    @Schema(name = "designationId",  example = "1")
    Integer designationId;

    @Schema(name = "officialAddress", example = "Udupi")
    String officialAddress;

    @Schema(name = "mobileNumber", example = "10")
    String mobileNumber;

    @Schema(name = "deputedInstituteId", example = "5")
    Integer deputedInstituteId;

    @Schema(name = "deputedFromDate", example = "1")
    Date deputedFromDate;

    @Schema(name = "deputedToDate", example = "1")
    Date deputedToDate;

    @Schema(name = "trProgramMasterId", example = "1")
    Integer trProgramMasterId;

    @Schema(name = "trCourseMasterId", example = "1")
    Integer trCourseMasterId;

    @Schema(name = "deputedAttended", example = "1")
    int deputedAttended;

    @Schema(name = "deputedRemarks", example = "1")
    String deputedRemarks;
}
