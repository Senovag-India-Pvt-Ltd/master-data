package com.sericulture.masterdata.model.api.hdAnswerMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class HdAnswerMasterRequest extends RequestBody {
    @Schema(name = "hdAnswerName", example = "Karnataka", required = true)
    String hdAnswerName;
}
