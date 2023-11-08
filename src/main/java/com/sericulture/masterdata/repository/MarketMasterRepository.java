package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.MarketMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MarketMasterRepository extends PagingAndSortingRepository<MarketMaster, Long> {
    public List<MarketMaster> findByMarketMasterName(String marketMasterName);

    public MarketMaster findByMarketMasterNameAndActive(String marketMasterName,boolean isActive);

    public Page<MarketMaster> findByActiveOrderByMarketMasterIdAsc(boolean isActive, final Pageable pageable);

    public MarketMaster save(MarketMaster marketMaster);

    public MarketMaster findByMarketMasterIdAndActive(long marketMasterId, boolean isActive);

    public MarketMaster findByMarketMasterIdAndActiveIn(@Param("marketMasterId") long marketMasterId, @Param("active") Set<Boolean> active);
}
