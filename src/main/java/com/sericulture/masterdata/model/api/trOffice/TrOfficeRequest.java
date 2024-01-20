package com.sericulture.masterdata.model.api.trOffice;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class TrOfficeRequest extends RequestBody {
    @Schema(name = "trOfficeName", example = "Karnataka", required = true)
    String trOfficeName;
}
