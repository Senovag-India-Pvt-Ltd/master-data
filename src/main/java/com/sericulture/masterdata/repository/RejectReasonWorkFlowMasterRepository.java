package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.RejectReasonWorkFlowMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RejectReasonWorkFlowMasterRepository extends PagingAndSortingRepository<RejectReasonWorkFlowMaster,Long> {

    public List<RejectReasonWorkFlowMaster> findByReason(String reason);

    public List<RejectReasonWorkFlowMaster> findByActiveAndReason(boolean a,String reason);


//    public RejectReasonWorkFlowMaster findByReasonAndActive(String reason,boolean isActive);

    public Page<RejectReasonWorkFlowMaster> findByActiveOrderByReasonAsc(boolean isActive, final Pageable pageable);

    public RejectReasonWorkFlowMaster save(RejectReasonWorkFlowMaster rejectReasonWorkFlowMaster);

    public RejectReasonWorkFlowMaster findByRejectReasonWorkFlowMasterIdAndActive(long rejectReasonWorkFlowMasterId, boolean isActive);

    public RejectReasonWorkFlowMaster findByRejectReasonWorkFlowMasterIdAndActiveIn(@Param("rejectReasonWorkFlowMasterId") long rejectReasonWorkFlowMasterId, @Param("active") Set<Boolean> active);

    public List<RejectReasonWorkFlowMaster> findByActiveOrderByReasonAsc(boolean isActive);
}
