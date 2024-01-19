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
public class TrainingDeputationTrackerResponse{

    @Schema(name = "trainingDeputationId", example = "1")
    Long trainingDeputationId;

    @Schema(name = "officialName", example = "Kaveri", required=true)
    String officialName;

    @Schema(name = "designationId",  example = "1")
    Long designationId;

    @Schema(name = "officialAddress", example = "Udupi")
    String officialAddress;

    @Schema(name = "mobileNumber", example = "15665650")
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

    @Schema(name = "deputedRemarks", example = "Kaveri")
    String deputedRemarks;

    @Schema(name = "name", example = "Kaveri")
    String name;

    @Schema(name = "trCourseMasterName", example = "Kaveri")
    String trCourseMasterName;

    @Schema(name = "trProgramMasterName", example = "Kaveri")
    String trProgramMasterName;

    @Schema(name = "deputedInstituteName", example = "Kaveri")
    String deputedInstituteName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
