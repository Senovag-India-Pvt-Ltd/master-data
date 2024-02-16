package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.BinCounterMaster;
import com.sericulture.masterdata.model.entity.BinMaster;
import com.sericulture.masterdata.model.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BinMasterRepository extends PagingAndSortingRepository<BinMaster,Long> {
    public List<BinMaster> findByBinNumber(int binNumber);

    public List<BinMaster> findByGodownIdAndMarketIdAndActive(int godownId,int marketId,boolean isActive);


    public List<BinMaster> findByGodownIdAndMarketIdAndBinMasterIdIsNot(int godownId,int marketId,long binMasterId);

    public List<BinMaster> findByBinNumberAndBinCounterMasterId(int binNumber, int binCounterMasterId);

    public BinMaster findByBinNumberAndActive(int binNumber,boolean isActive);

    public BinMaster findByGodownIdAndMarketIdAndTypeAndBinNumberAndActive(int marketId, int godownId, String type, int binNumber, boolean isActive);

    public Page<BinMaster> findByActiveOrderByBinMasterIdAsc(boolean isActive, final Pageable pageable);

    public BinMaster save(BinMaster binMaster);

    public BinMaster findByBinMasterIdAndActive(long id, boolean isActive);

    public List<BinMaster> findByBinCounterMasterIdAndActive(int binCounterMasterId, boolean isActive);

    public BinMaster findByBinMasterIdAndActiveIn(@Param("binMasterId") long binMasterId, @Param("active") Set<Boolean> active);

    public List<BinMaster> findByActive(boolean isActive);
}
