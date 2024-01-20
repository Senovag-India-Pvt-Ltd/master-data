package com.sericulture.masterdata.model.api.trTraining;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class EditTrTrainingRequest extends RequestBody {
    @Schema(name = "trTrainingId", example = "1")
    Long trTrainingId;

    @Schema(name = "trStakeholderType", example = "1")
    Long trStakeholderType;

    @Schema(name = "trInstitutionMasterId", example = "1")
    Long trInstitutionMasterId;

    @Schema(name = "trGroupMasterId", example = "1")
    Long trGroupMasterId;

    @Schema(name = "trProgramMasterId", example = "1")
    Long trProgramMasterId;

    @Schema(name = "trCourseMasterId", example = "1")
    Long trCourseMasterId;


    @Schema(name = "trTrainingDate", example = "1")
    Date trTrainingDate;

    @Schema(name = "trDuration", example = "1")
    Long trDuration;

    @Schema(name = "trPeriod", example = "1")
    Long trPeriod;

    @Schema(name = "userMasterId", example = "1")
    Long userMasterId;


}
