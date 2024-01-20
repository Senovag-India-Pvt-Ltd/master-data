package com.sericulture.masterdata.model.api.trOffice;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditTrOfficeRequest extends RequestBody {
    @Schema(name = "trOfficeId", example = "1")
    Integer trOfficeId;

    @Schema(name = "trOfficeName", example = "Karnataka", required = true)
    String trOfficeName;
}
