package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.TraderTypeMaster;
import com.sericulture.masterdata.model.entity.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TraderTypeMasterRepository extends PagingAndSortingRepository<TraderTypeMaster,Long> {
    public List<TraderTypeMaster> findByTraderTypeMasterName(String traderTypeMasterName);

    public TraderTypeMaster findByTraderTypeMasterNameAndActive(String traderTypeMasterName,boolean isActive);

    public Page<TraderTypeMaster> findByActiveOrderByTraderTypeMasterNameAsc(boolean isActive, final Pageable pageable);

    public TraderTypeMaster save(TraderTypeMaster state);

    public TraderTypeMaster findByTraderTypeMasterIdAndActive(long id, boolean isActive);

    public TraderTypeMaster findByTraderTypeMasterIdAndActiveIn(@Param("traderTypeMasterId") long traderTypeMasterId, @Param("active") Set<Boolean> active);

    public List<TraderTypeMaster> findByActiveOrderByTraderTypeMasterNameAsc(boolean isActive);
}
