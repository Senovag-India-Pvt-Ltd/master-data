package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.BinCounterMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BinCounterMasterRepository extends PagingAndSortingRepository<BinCounterMaster, Long> {

    public Page<BinCounterMaster> findByActiveOrderByBinCounterMasterIdAsc(boolean isActive, final Pageable pageable);

    public BinCounterMaster save(BinCounterMaster binCounterMaster);

    public BinCounterMaster findByBinCounterMasterIdAndActive(long binCounterMasterId, boolean isActive);

    public BinCounterMaster findByBinCounterMasterIdAndActiveIn(@Param("binCounterMasterId") long binCounterMasterId, @Param("active") Set<Boolean> active);

    public List<BinCounterMaster> findByActive(boolean isActive);
}
