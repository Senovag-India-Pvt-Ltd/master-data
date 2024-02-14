package com.sericulture.masterdata.model.api.trSchedule;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EditTrScheduleRequest extends RequestBody {
    @Schema(name = "trScheduleId", example = "1")
    Long trScheduleId;

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;

    @Schema(name = "trStakeholderType", example = "1")
    Long trStakeholderType;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Tr name must contain only letters and numbers")
    @Schema(name = "trName", example = "Karnataka", required = true)
    String trName;

    @Schema(name = "trInstitutionMasterId", example = "1")
    Long trInstitutionMasterId;

    @Schema(name = "trGroupMasterId", example = "1")
    Long trGroupMasterId;

    @Schema(name = "trProgramMasterId", example = "1")
    Long trProgramMasterId;

    @Schema(name = "trCourseMasterId", example = "1")
    Long trCourseMasterId;

    @Schema(name = "trModeMasterId", example = "1")
    Long trModeMasterId;

    @Schema(name = "trStartDate", example = "1")
    Date trStartDate;

    @Schema(name = "trDuration", example = "1")
    Long trDuration;

    @Schema(name = "trPeriod", example = "1")
    Long trPeriod;

    @Schema(name = "trDateOfCompletion", example = "1")
    Date trDateOfCompletion;

    @Pattern(regexp = "^[a-zA-Z0-9/_.\\s]*$", message = "Tr upload path must contain only letters and numbers")
    @Schema(name = "trUploadPath", example = "Karnataka",required = true)
    String trUploadPath;

    @Schema(name = "trNoOfParticipant", example = "1")
    Long trNoOfParticipant;


}
