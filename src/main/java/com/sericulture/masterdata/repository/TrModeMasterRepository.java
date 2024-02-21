package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.TrGroupMaster;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrModeMasterRepository extends PagingAndSortingRepository<TrModeMaster, Long> {
    public List<TrModeMaster> findByTrModeMasterName(String trModeMasterName);

    public List<TrModeMaster> findByActiveAndTrModeMasterName(boolean a,String trModeMasterName);


    public TrModeMaster findByTrModeMasterNameAndActive(String trModeMasterName,boolean isActive);

    public Page<TrModeMaster> findByActiveOrderByTrModeMasterNameAsc(boolean isActive, final Pageable pageable);

    public TrModeMaster save(TrModeMaster trModeMaster);

    public TrModeMaster findByTrModeMasterIdAndActive(long id, boolean isActive);

    public TrModeMaster findByTrModeMasterIdAndActiveIn(@Param("trModeMasterId") long trModeMasterId, @Param("active") Set<Boolean> active);

    public List<TrModeMaster> findByActiveOrderByTrModeMasterNameAsc(boolean isActive);


}
