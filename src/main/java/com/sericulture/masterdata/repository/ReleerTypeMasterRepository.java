package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ReleerTypeMaster;
import com.sericulture.masterdata.model.entity.TrCourseMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReleerTypeMasterRepository extends PagingAndSortingRepository<ReleerTypeMaster, Long> {
    public List<ReleerTypeMaster> findByReleerTypeMasterName(String releerTypeMasterName);

    public ReleerTypeMaster findByReleerTypeMasterNameAndActive(String releerTypeMasterName,boolean isActive);

    public Page<ReleerTypeMaster> findByActiveOrderByReleerTypeMasterIdAsc(boolean isActive, final Pageable pageable);

    public ReleerTypeMaster save(ReleerTypeMaster releerTypeMaster);

    public  ReleerTypeMaster findByReleerTypeMasterIdAndActive(long id, boolean isActive);

    public ReleerTypeMaster findByReleerTypeMasterIdAndActiveIn(@Param("releerTypeMasterId") long releerTypeMasterId, @Param("active") Set<Boolean> active);

    public List<ReleerTypeMaster> findByActive(boolean isActive);
}
