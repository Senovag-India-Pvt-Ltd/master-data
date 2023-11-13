package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.RaceMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RaceMasterRepository extends PagingAndSortingRepository<RaceMaster, Long>{

    public List<RaceMaster> findByRaceMasterName(String raceMasterName);

    public RaceMaster findByRaceMasterNameAndActive(String raceMasterName,boolean isActive);

    public Page<RaceMaster> findByActiveOrderByRaceMasterIdAsc(boolean isActive, final Pageable pageable);

    public RaceMaster save(RaceMaster raceMaster);

    public RaceMaster findByRaceMasterIdAndActive(long id, boolean isActive);

    public RaceMaster findByRaceMasterIdAndActiveIn(@Param("raceMasterId") long raceMasterId, @Param("active") Set<Boolean> active);

    public List<RaceMaster> findByActive(boolean isActive);

}
