package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ReasonLotRejectMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReasonLotRejectMasterRepository extends PagingAndSortingRepository<ReasonLotRejectMaster, Long> {
    public List<ReasonLotRejectMaster> findByReasonLotRejectName(String reasonLotRejectName);

    public ReasonLotRejectMaster findByReasonLotRejectNameAndActive(String reasonLotRejectName,boolean isActive);

    public Page<ReasonLotRejectMaster> findByActiveOrderByReasonLotRejectIdAsc(boolean isActive, final Pageable pageable);

    public ReasonLotRejectMaster save(ReasonLotRejectMaster reasonLotReject);

    public ReasonLotRejectMaster findByReasonLotRejectIdAndActive(long id, boolean isActive);

    public ReasonLotRejectMaster findByReasonLotRejectIdAndActiveIn(@Param("reasonLotRejectId") long reasonLotRejectId, @Param("active") Set<Boolean> active);

}