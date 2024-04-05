package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.TsActivityMaster;
import com.sericulture.masterdata.model.entity.TsActivityMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TsActivityMasterRepository extends PagingAndSortingRepository<TsActivityMaster,Long> {
    public List<TsActivityMaster> findByName(String name);

    public List<TsActivityMaster> findByNameAndNameInKannada(String name,String nameInKannada);

    public List<TsActivityMaster> findByActiveAndNameAndNameInKannada(boolean a,String name,String nameInKannada);

    public TsActivityMaster findByNameAndActive(String name,boolean isActive);

    public Page<TsActivityMaster> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public TsActivityMaster save(TsActivityMaster tsActivityMaster);

    public TsActivityMaster findByTsActivityMasterIdAndActive(long id, boolean isActive);

    public TsActivityMaster findByTsActivityMasterIdAndActiveIn(@Param("tsActivityMasterId") long tsActivityMasterId, @Param("active") Set<Boolean> active);

    public List<TsActivityMaster> findByActive(boolean isActive);
}

