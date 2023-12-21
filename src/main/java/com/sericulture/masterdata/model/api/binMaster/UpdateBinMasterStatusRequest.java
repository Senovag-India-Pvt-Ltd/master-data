package com.sericulture.masterdata.model.api.binMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UpdateBinMasterStatusRequest extends RequestBody {

    @Schema(name="marketId", example = "1")
    Integer marketId;

    @Schema(name="godownId", example = "1")
    Integer godownId;

    @Schema(name = "smallBinNumber", example = "12S")
    Integer smallBinNumber;

    @Schema(name = "smallBinStatus", example = "12S")
    String smallBinStatus;

    @Schema(name = "bigBinNumber", example = "12S")
    Integer bigBinNumber;

    @Schema(name = "bigBinStatus", example = "12S")
    String bigBinStatus;

}
