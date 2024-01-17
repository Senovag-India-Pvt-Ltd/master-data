package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.MarketMaster;
import com.sericulture.masterdata.model.entity.MarketTypeMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MarketTypeMasterRepository extends PagingAndSortingRepository<MarketTypeMaster, Long> {

    public List<MarketTypeMaster> findByMarketTypeMasterNameAndMarketTypeNameInKannada(String marketTypeMasterName,String marketTypeNameInKannada);

    public MarketTypeMaster findByMarketTypeMasterNameAndActive(String marketTypeMasterName,boolean isActive);

    public Page<MarketTypeMaster> findByActiveOrderByMarketTypeMasterIdAsc(boolean isActive, final Pageable pageable);

    public MarketTypeMaster save(MarketTypeMaster marketTypeMaster);

    public MarketTypeMaster findByMarketTypeMasterIdAndActive(long marketTypeMasterId, boolean isActive);

    public MarketTypeMaster findByMarketTypeMasterIdAndActiveIn(@Param("marketTypeMasterId") long marketTypeMasterId, @Param("active") Set<Boolean> active);

    public List<MarketTypeMaster> findByActiveOrderByMarketTypeMasterNameAsc(boolean isActive);
}
