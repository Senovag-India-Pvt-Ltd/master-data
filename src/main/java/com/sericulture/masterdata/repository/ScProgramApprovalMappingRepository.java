package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.RaceMarketMasterDTO;
import com.sericulture.masterdata.model.dto.ScProgramApprovalMappingDTO;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.entity.RaceMarketMaster;
import com.sericulture.masterdata.model.entity.ScProgramApprovalMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScProgramApprovalMappingRepository extends PagingAndSortingRepository<ScProgramApprovalMapping, Long> {

    public Page<ScProgramApprovalMapping> findByActiveOrderByScProgramApprovalMappingIdAsc(boolean isActive, final Pageable pageable);

    List<ScProgramApprovalMapping> findByScProgramIdAndScApprovalStageId(long scProgramId, long scApprovalStageId);

    List<ScProgramApprovalMapping> findByScProgramIdAndScApprovalStageIdAndScProgramApprovalMappingIdIsNot(long scProgramId, long scApprovalStageId, long scProgramApprovalMappingId);


    public ScProgramApprovalMapping save(ScProgramApprovalMapping scProgramApprovalMapping);

    public ScProgramApprovalMapping findByScProgramApprovalMappingIdAndActive(long id, boolean isActive);

    public ScProgramApprovalMapping findByScProgramApprovalMappingIdAndActiveIn(@Param("scProgramApprovalMappingId") long scProgramApprovalMappingId, @Param("active") Set<Boolean> active);

    public List<ScProgramApprovalMapping> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScProgramApprovalMappingDTO(" +
            " scProgramApprovalMapping.scProgramApprovalMappingId," +
            " scProgramApprovalMapping.scProgramId," +
            " scProgramApprovalMapping.scApprovalStageId," +
            " scProgramApprovalMapping.designationId," +
            " scProgram.scProgramName," +
            " scApprovalStage.stageName," +
            " designation.name" +
            ") \n" +
            "from ScProgramApprovalMapping scProgramApprovalMapping\n" +
            "left join ScProgram scProgram\n" +
            "on scProgramApprovalMapping.scProgramId = scProgram.scProgramId " +
            "left join ScApprovalStage scApprovalStage\n" +
            "on scProgramApprovalMapping.scApprovalStageId = scApprovalStage.scApprovalStageId " +
            "left join Designation designation\n" +
            "on scProgramApprovalMapping.designationId = designation.designationId " +
            "where scProgramApprovalMapping.active = :isActive " +
            "ORDER BY scProgramApprovalMapping.scProgramApprovalMappingId ASC"
    )
    Page<ScProgramApprovalMappingDTO> getByActiveOrderByScProgramApprovalMappingIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScProgramApprovalMappingDTO(" +
            " scProgramApprovalMapping.scProgramApprovalMappingId," +
            " scProgramApprovalMapping.scProgramId," +
            " scProgramApprovalMapping.scApprovalStageId," +
            " scProgramApprovalMapping.designationId," +
            " scProgram.scProgramName," +
            " scApprovalStage.stageName," +
            " designation.name" +
            ") \n" +
            "from ScProgramApprovalMapping scProgramApprovalMapping\n" +
            "left join ScProgram scProgram\n" +
            "on scProgramApprovalMapping.scProgramId = scProgram.scProgramId " +
            "left join ScApprovalStage scApprovalStage\n" +
            "on scProgramApprovalMapping.scApprovalStageId = scApprovalStage.scApprovalStageId " +
            "left join Designation designation\n" +
            "on scProgramApprovalMapping.designationId = designation.designationId " +
            "where scProgramApprovalMapping.active = :isActive AND scProgramApprovalMapping.scProgramApprovalMappingId = :id "
    )
    public ScProgramApprovalMappingDTO getByScProgramApprovalMappingIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScProgramApprovalMappingDTO(" +
            " scProgramApprovalMapping.scProgramApprovalMappingId," +
            " scProgramApprovalMapping.scProgramId," +
            " scProgramApprovalMapping.scApprovalStageId," +
            " scProgramApprovalMapping.designationId," +
            " scProgram.scProgramName," +
            " scApprovalStage.stageName," +
            " designation.name" +
            ") \n" +
            "from ScProgramApprovalMapping scProgramApprovalMapping\n" +
            "left join ScProgram scProgram\n" +
            "on scProgramApprovalMapping.scProgramId = scProgram.scProgramId " +
            "left join ScApprovalStage scApprovalStage\n" +
            "on scProgramApprovalMapping.scApprovalStageId = scApprovalStage.scApprovalStageId " +
            "left join Designation designation\n" +
            "on scProgramApprovalMapping.designationId = designation.designationId " +
            "where scProgramApprovalMapping.active = :isActive AND " +
            "(:joinColumn = 'scProgram.scProgramName' AND scProgram.scProgramName LIKE :searchText) OR " +
            "(:joinColumn = 'scApprovalStage.stageName' AND scApprovalStage.stageName LIKE :searchText)"
    )
    public Page<ScProgramApprovalMappingDTO> getSortedScProgramApprovalMapping(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}

