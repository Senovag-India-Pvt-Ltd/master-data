package com.sericulture.masterdata.model.api.binCounterMaster;

import com.sericulture.masterdata.model.api.RequestBody;
import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BinCounterMasterWithBinMasterRequest extends RequestBody {
    BinCounterMasterRequest binCounterMasterRequest;
    List<BinMasterRequest> smallBinMasterRequestList;
    List<BinMasterRequest> bigBinMasterRequestList;
}