package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.entity.ScApprovalStage;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScApprovalStageRepository extends PagingAndSortingRepository<ScApprovalStage, Long> {

    public List<ScApprovalStage> findByStageName(String stageName);

//    public List<ScApprovalStage> findByScProgramIdAndActiveOrderByStageName(long scProgramId, boolean isActive);

    public List<ScApprovalStage> findByStageNameAndStageNameInKannada(String stageName,String stageNameInKannada);

    public List<ScApprovalStage> findByStageNameAndStageNameInKannadaAndScApprovalStageIdIsNot(String stageName,String stageNameInKannada,long scApprovalStageId);

    public ScApprovalStage findByStageNameAndActive(String trProgramMasterName,boolean isActive);

    public Page<ScApprovalStage> findByActiveOrderByStageNameAsc(boolean isActive, final Pageable pageable);

    public ScApprovalStage save(ScApprovalStage scApprovalStage);

    public ScApprovalStage findByScApprovalStageIdAndActive(long id, boolean isActive);

    public ScApprovalStage findByScApprovalStageIdAndActiveIn(@Param("scApprovalStageId") long scApprovalStageId, @Param("active") Set<Boolean> active);

    public List<ScApprovalStage> findByActive(boolean isActive);
}
