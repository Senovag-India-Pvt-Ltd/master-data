package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.BaseEntity;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.TrCourseMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrCourseMasterRepository extends PagingAndSortingRepository<TrCourseMaster, Long> {

    public List<TrCourseMaster> findByTrCourseMasterName(String trCourseMasterName);

    public TrCourseMaster findByTrCourseMasterNameAndActive(String trCourseMasterName,boolean isActive);

    public Page<TrCourseMaster> findByActiveOrderByTrCourseMasterNameAsc(boolean isActive, final Pageable pageable);

    public TrCourseMaster save(TrCourseMaster trCourseMaster);

    public TrCourseMaster findByTrCourseMasterIdAndActive(long id, boolean isActive);

    public TrCourseMaster findByTrCourseMasterIdAndActiveIn(@Param("trCourseMasterId") long trCourseMasterId, @Param("active") Set<Boolean> active);

    public List<TrCourseMaster> findByActive(boolean isActive);
}
