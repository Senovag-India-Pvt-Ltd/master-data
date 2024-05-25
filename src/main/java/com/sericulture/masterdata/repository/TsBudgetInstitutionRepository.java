package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsBudgetInstitutionDTO;
import com.sericulture.masterdata.model.entity.TsBudgetInstitution;
import com.sericulture.masterdata.model.entity.TsBudgetInstitution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TsBudgetInstitutionRepository extends PagingAndSortingRepository<TsBudgetInstitution,Long> {
    public Page<TsBudgetInstitution> findByActiveOrderByTsBudgetInstitutionIdAsc(boolean isActive, final Pageable pageable);

    List<TsBudgetInstitution> findByFinancialYearMasterIdAndScHeadAccountIdAndDistrictIdAndTalukId(long financialYearMasterId,long scHeadAccountId, long districtId, long talukId);


//    public List<TsBudgetInstitution> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsBudgetInstitution> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsBudgetInstitution> findByFinancialYearMasterIdAndScHeadAccountIdAndDistrictIdAndTalukIdAndTsBudgetInstitutionIdIsNot(long financialYearMasterId,long districtId,long talukId,long scHeadAccountId, long tsBudgetInstitutionId);


    public TsBudgetInstitution save(TsBudgetInstitution tsBudgetInstitution);

    public TsBudgetInstitution findByTsBudgetInstitutionIdAndActive(long id, boolean isActive);

    public TsBudgetInstitution findByTsBudgetInstitutionIdAndActiveIn(@Param("tsBudgetInstitutionId") long tsBudgetInstitutionId, @Param("active") Set<Boolean> active);

    public List<TsBudgetInstitution> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetInstitutionDTO(" +
            " tsBudgetInstitution.tsBudgetInstitutionId," +
            " tsBudgetInstitution.financialYearMasterId," +
            " tsBudgetInstitution.scHeadAccountId," +
            " tsBudgetInstitution.districtId," +
            " tsBudgetInstitution.talukId," +
            " tsBudgetInstitution.institutionType," +
            " tsBudgetInstitution.institutionId," +
            " tsBudgetInstitution.date," +
            " tsBudgetInstitution.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetInstitution tsBudgetInstitution\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetInstitution.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetInstitution.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetInstitution.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetInstitution.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetInstitution.active = :isActive " +
            "ORDER BY tsBudgetInstitution.tsBudgetInstitutionId ASC"
    )
    Page<TsBudgetInstitutionDTO> getByActiveOrderByTsBudgetInstitutionIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetInstitutionDTO(" +
            " tsBudgetInstitution.tsBudgetInstitutionId," +
            " tsBudgetInstitution.financialYearMasterId," +
            " tsBudgetInstitution.scHeadAccountId," +
            " tsBudgetInstitution.districtId," +
            " tsBudgetInstitution.talukId," +
            " tsBudgetInstitution.institutionType," +
            " tsBudgetInstitution.institutionId," +
            " tsBudgetInstitution.date," +
            " tsBudgetInstitution.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetInstitution tsBudgetInstitution\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetInstitution.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetInstitution.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetInstitution.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetInstitution.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetInstitution.active = :isActive AND tsBudgetInstitution.tsBudgetInstitutionId = :id "
    )
    public TsBudgetInstitutionDTO getByTsBudgetInstitutionIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetInstitutionDTO(" +
//            " tsBudgetInstitution.tsBudgetInstitutionId," +
//            " tsBudgetInstitution.financialYearMasterId," +
//            " tsBudgetInstitution.date," +
//            " tsBudgetInstitution.centralBudgetInstitution," +
//            " tsBudgetInstitution.stateBudgetInstitution," +
//            " tsBudgetInstitution.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsBudgetInstitution tsBudgetInstitution\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsBudgetInstitution.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsBudgetInstitution.active = :isActive AND tsBudgetInstitution.tsBudgetInstitutionId = :id "
//    )
//    public List<TsBudgetInstitutionDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetInstitutionDTO(" +
            " tsBudgetInstitution.tsBudgetInstitutionId," +
            " tsBudgetInstitution.financialYearMasterId," +
            " tsBudgetInstitution.scHeadAccountId," +
            " tsBudgetInstitution.districtId," +
            " tsBudgetInstitution.talukId," +
            " tsBudgetInstitution.institutionType," +
            " tsBudgetInstitution.institutionId," +
            " tsBudgetInstitution.date," +
            " tsBudgetInstitution.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetInstitution tsBudgetInstitution\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetInstitution.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetInstitution.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetInstitution.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetInstitution.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetInstitution.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) OR " +
            "(:joinColumn = 'scHeadAccount.scHeadAccountName' AND scHeadAccount.scHeadAccountName LIKE :searchText)"
    )
    public Page<TsBudgetInstitutionDTO> getSortedTsBudgetInstitution(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
