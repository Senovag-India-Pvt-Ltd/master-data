package com.sericulture.masterdata.model.api.hdAnswerMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditHdAnswerMasterRequest extends RequestBody {
    @Schema(name = "hdAnswerId", example = "1")
    Long hdAnswerId;

    @Schema(name = "hdAnswerName", example = "Karnataka", required = true)
    String hdAnswerName;
}
