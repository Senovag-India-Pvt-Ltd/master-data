package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.HdSeverityMaster;
import com.sericulture.masterdata.model.entity.HdStatusMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface HdSeverityMasterRepository extends PagingAndSortingRepository<HdSeverityMaster, Long> {
    public List<HdSeverityMaster> findByHdSeverityName(String hdSeverityName);

    public HdSeverityMaster findByHdSeverityNameAndActive(String hdSeverityName, boolean isActive);

    public Page<HdSeverityMaster> findByActiveOrderByHdSeverityNameAsc(boolean isActive, final Pageable pageable);

    public HdSeverityMaster save(HdSeverityMaster hdSeverityMaster);

    public HdSeverityMaster findByHdSeverityIdAndActive(long id, boolean isActive);

    public HdSeverityMaster findByHdSeverityIdAndActiveIn(@Param("hdSeverityId") long hdSeverityId, @Param("active") Set<Boolean> active);

    public List<HdSeverityMaster> findByActiveOrderByHdSeverityNameAsc(boolean isActive);
}
