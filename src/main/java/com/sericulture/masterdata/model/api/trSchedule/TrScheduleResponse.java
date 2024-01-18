package com.sericulture.masterdata.model.api.trSchedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class TrScheduleResponse {
    @Schema(name = "trScheduleId", example = "1")
    Integer trScheduleId;

    @Schema(name = "trName", example = "Karnataka", required = true)
    String trName;

    @Schema(name = "trInstitutionMasterId", example = "1")
    Integer trInstitutionMasterId;

    @Schema(name = "trGroupMasterId", example = "1")
    Integer trGroupMasterId;

    @Schema(name = "trProgramMasterId", example = "1")
    Integer trProgramMasterId;

    @Schema(name = "trCourseMasterId", example = "1")
    Integer trCourseMasterId;

    @Schema(name = "trModeMasterId", example = "1")
    Integer trModeMasterId;

    @Schema(name = "trStartDate", example = "1")
    Date trStartDate;

    @Schema(name = "trDuration", example = "1")
    Integer trDuration;

    @Schema(name = "trPeriod", example = "1")
    Integer trPeriod;

    @Schema(name = "trDateOfCompletion", example = "1")
    Date trDateOfCompletion;

    @Schema(name = "trUploadPath", example = "Karnataka")
    String trUploadPath;

    @Schema(name = "trNoOfParticipant", example = "1")
    Integer trNoOfParticipant;

    @Schema(name = "trInstitutionMasterName", example = "Karnataka")
    String trInstitutionMasterName;

    @Schema(name = "trGroupMasterName", example = "Karnataka")
    String trGroupMasterName;

    @Schema(name = "trProgramMasterName", example = "Karnataka")
    String trProgramMasterName;

    @Schema(name = "trCourseMasterName", example = "Karnataka")
    String trCourseMasterName;

    @Schema(name = "trModeMasterName", example = "Karnataka")
    String trModeMasterName;

    @Schema(name = "error", example = "true")
    Boolean error;

    @Schema(name = "error_description", example = "Username or password is incorrect")
    String error_description;
}
