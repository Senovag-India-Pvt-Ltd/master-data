package com.sericulture.masterdata.model.api.documentMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DocumentMasterRequest extends RequestBody {

    @Schema(name = "documentMasterName", example = "documentMasterName 1", required = true)
    String documentMasterName;
}
