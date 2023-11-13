package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ReasonBidRejectMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReasonBidRejectMasterRepository extends PagingAndSortingRepository<ReasonBidRejectMaster, Long> {
    public List<ReasonBidRejectMaster> findByReasonBidRejectName(String reasonBidRejectName);

    public ReasonBidRejectMaster findByReasonBidRejectNameAndActive(String reasonBidRejectName,boolean isActive);

    public Page<ReasonBidRejectMaster> findByActiveOrderByReasonBidRejectIdAsc(boolean isActive, final Pageable pageable);

    public ReasonBidRejectMaster save(ReasonBidRejectMaster reasonBidReject);

    public ReasonBidRejectMaster findByReasonBidRejectIdAndActive(long id, boolean isActive);

    public ReasonBidRejectMaster findByReasonBidRejectIdAndActiveIn(@Param("reasonBidRejectId") long reasonLotRejectId, @Param("active") Set<Boolean> active);

    public List<ReasonBidRejectMaster> findByActive(boolean isActive);
}
