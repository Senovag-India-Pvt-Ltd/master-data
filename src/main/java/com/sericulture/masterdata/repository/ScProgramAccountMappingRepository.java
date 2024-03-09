package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScProgramAccountMappingDTO;
import com.sericulture.masterdata.model.dto.ScProgramApprovalMappingDTO;
import com.sericulture.masterdata.model.entity.ScProgramAccountMapping;
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
public interface ScProgramAccountMappingRepository extends PagingAndSortingRepository<ScProgramAccountMapping, Long> {
    public Page<ScProgramAccountMapping> findByActiveOrderByScProgramAccountMappingIdAsc(boolean isActive, final Pageable pageable);

    List<ScProgramAccountMapping> findByScProgramIdAndScHeadAccountId(long scProgramId, long scHeadAccountId);

    List<ScProgramAccountMapping> findByScProgramIdAndScHeadAccountIdAndScProgramAccountMappingIdIsNot(long scProgramId, long scHeadAccountId, long scProgramAccountMappingId);


    public ScProgramAccountMapping save(ScProgramAccountMapping scProgramAccountMapping);

    public ScProgramAccountMapping findByScProgramAccountMappingIdAndActive(long id, boolean isActive);

    public ScProgramAccountMapping findByScProgramAccountMappingIdAndActiveIn(@Param("scProgramAccountMappingId") long scProgramAccountMappingId, @Param("active") Set<Boolean> active);

    public List<ScProgramAccountMapping> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScProgramAccountMappingDTO(" +
            " scProgramAccountMapping.scProgramAccountMappingId," +
            " scProgramAccountMapping.scProgramId," +
            " scProgramAccountMapping.scHeadAccountId," +
            " scProgramAccountMapping.scCategoryId," +
            " scProgram.scProgramName," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName" +
            ") \n" +
            "from ScProgramAccountMapping scProgramAccountMapping\n" +
            "left join ScProgram scProgram\n" +
            "on scProgramAccountMapping.scProgramId = scProgram.scProgramId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scProgramAccountMapping.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scProgramAccountMapping.scCategoryId = scCategory.scCategoryId " +
            "where scProgramAccountMapping.active = :isActive " +
            "ORDER BY scProgramAccountMapping.scProgramAccountMappingId ASC"
    )
    Page<ScProgramAccountMappingDTO> getByActiveOrderByScProgramAccountMappingIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScProgramAccountMappingDTO(" +
            " scProgramAccountMapping.scProgramAccountMappingId," +
            " scProgramAccountMapping.scProgramId," +
            " scProgramAccountMapping.scHeadAccountId," +
            " scProgramAccountMapping.scCategoryId," +
            " scProgram.scProgramName," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName" +
            ") \n" +
            "from ScProgramAccountMapping scProgramAccountMapping\n" +
            "left join ScProgram scProgram\n" +
            "on scProgramAccountMapping.scProgramId = scProgram.scProgramId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scProgramAccountMapping.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scProgramAccountMapping.scCategoryId = scCategory.scCategoryId " +
            "where scProgramAccountMapping.active = :isActive AND scProgramAccountMapping.scProgramAccountMappingId = :id "
    )
    public ScProgramAccountMappingDTO getByScProgramAccountMappingIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScProgramAccountMappingDTO(" +
            " scProgramAccountMapping.scProgramAccountMappingId," +
            " scProgramAccountMapping.scProgramId," +
            " scProgramAccountMapping.scHeadAccountId," +
            " scProgramAccountMapping.scCategoryId," +
            " scProgram.scProgramName," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName" +
            ") \n" +
            "from ScProgramAccountMapping scProgramAccountMapping\n" +
            "left join ScProgram scProgram\n" +
            "on scProgramAccountMapping.scProgramId = scProgram.scProgramId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scProgramAccountMapping.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scProgramAccountMapping.scCategoryId = scCategory.scCategoryId " +
            "where scProgramAccountMapping.active = :isActive AND " +
            "(:joinColumn = 'scProgram.scProgramName' AND scProgram.scProgramName LIKE :searchText) OR " +
            "(:joinColumn = 'scHeadAccount.scHeadAccountName' AND scHeadAccount.scHeadAccountName LIKE :searchText)"
    )
    public Page<ScProgramAccountMappingDTO> getSortedScProgramAccountMapping(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}


