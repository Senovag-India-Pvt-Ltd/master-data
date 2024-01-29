package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.HdAnswerMaster;
import com.sericulture.masterdata.model.entity.HdStatusMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HdAnswerMasterRepository extends PagingAndSortingRepository<HdAnswerMaster, Long> {
    public List<HdAnswerMaster> findByHdAnswerName(String hdAnswerName);

    public HdAnswerMaster findByHdAnswerNameAndActive(String hdAnswerName, boolean isActive);

    public Page<HdAnswerMaster> findByActiveOrderByHdAnswerNameAsc(boolean isActive, final Pageable pageable);

    public HdAnswerMaster save(HdAnswerMaster hdAnswerMaster);

    public HdAnswerMaster findByHdAnswerIdAndActive(long id, boolean isActive);

    public HdAnswerMaster findByHdAnswerIdAndActiveIn(@Param("hdAnswerId") long hdAnswerId, @Param("active") Set<Boolean> active);

    public List<HdAnswerMaster> findByActiveOrderByHdAnswerNameAsc(boolean isActive);
}

