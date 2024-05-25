package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsReleaseBudgetInstitutionDTO;
import com.sericulture.masterdata.model.entity.TsReleaseBudgetInstitution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TsReleaseBudgetInstitutionRepository extends PagingAndSortingRepository<TsReleaseBudgetInstitution,Long> {
    public Page<TsReleaseBudgetInstitution> findByActiveOrderByTsReleaseBudgetInstitutionIdAsc(boolean isActive, final Pageable pageable);

    List<TsReleaseBudgetInstitution> findByFinancialYearMasterIdAndScHeadAccountIdAndDistrictIdAndTalukId(long financialYearMasterId, long scHeadAccountId, long districtId, long talukId);


//    public List<TsReleaseBudgetInstitution> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsReleaseBudgetInstitution> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsReleaseBudgetInstitution> findByFinancialYearMasterIdAndScHeadAccountIdAndDistrictIdAndTalukIdAndTsReleaseBudgetInstitutionIdIsNot(long financialYearMasterId,long districtId,long talukId,long scHeadAccountId, long tsReleaseBudgetInstitutionId);


    public TsReleaseBudgetInstitution save(TsReleaseBudgetInstitution tsReleaseBudgetInstitution);

    public TsReleaseBudgetInstitution findByTsReleaseBudgetInstitutionIdAndActive(long id, boolean isActive);

    public TsReleaseBudgetInstitution findByTsReleaseBudgetInstitutionIdAndActiveIn(@Param("tsReleaseBudgetInstitutionId") long tsReleaseBudgetInstitutionId, @Param("active") Set<Boolean> active);

    public List<TsReleaseBudgetInstitution> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetInstitutionDTO(" +
            " tsReleaseBudgetInstitution.tsReleaseBudgetInstitutionId," +
            " tsReleaseBudgetInstitution.financialYearMasterId," +
            " tsReleaseBudgetInstitution.scHeadAccountId," +
            " tsReleaseBudgetInstitution.districtId," +
            " tsReleaseBudgetInstitution.talukId," +
            " tsReleaseBudgetInstitution.institutionType," +
            " tsReleaseBudgetInstitution.institutionId," +
            " tsReleaseBudgetInstitution.date," +
            " tsReleaseBudgetInstitution.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetInstitution tsReleaseBudgetInstitution\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetInstitution.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetInstitution.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsReleaseBudgetInstitution.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetInstitution.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetInstitution.active = :isActive " +
            "ORDER BY tsReleaseBudgetInstitution.tsReleaseBudgetInstitutionId ASC"
    )
    Page<TsReleaseBudgetInstitutionDTO> getByActiveOrderByTsReleaseBudgetInstitutionIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetInstitutionDTO(" +
            " tsReleaseBudgetInstitution.tsReleaseBudgetInstitutionId," +
            " tsReleaseBudgetInstitution.financialYearMasterId," +
            " tsReleaseBudgetInstitution.scHeadAccountId," +
            " tsReleaseBudgetInstitution.districtId," +
            " tsReleaseBudgetInstitution.talukId," +
            " tsReleaseBudgetInstitution.institutionType," +
            " tsReleaseBudgetInstitution.institutionId," +
            " tsReleaseBudgetInstitution.date," +
            " tsReleaseBudgetInstitution.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetInstitution tsReleaseBudgetInstitution\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetInstitution.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetInstitution.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsReleaseBudgetInstitution.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetInstitution.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetInstitution.active = :isActive AND tsReleaseBudgetInstitution.tsReleaseBudgetInstitutionId = :id "
    )
    public TsReleaseBudgetInstitutionDTO getByTsReleaseBudgetInstitutionIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetInstitutionDTO(" +
//            " tsReleaseBudgetInstitution.tsReleaseBudgetInstitutionId," +
//            " tsReleaseBudgetInstitution.financialYearMasterId," +
//            " tsReleaseBudgetInstitution.date," +
//            " tsReleaseBudgetInstitution.centralReleaseBudgetInstitution," +
//            " tsReleaseBudgetInstitution.stateReleaseBudgetInstitution," +
//            " tsReleaseBudgetInstitution.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsReleaseBudgetInstitution tsReleaseBudgetInstitution\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsReleaseBudgetInstitution.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsReleaseBudgetInstitution.active = :isActive AND tsReleaseBudgetInstitution.tsReleaseBudgetInstitutionId = :id "
//    )
//    public List<TsReleaseBudgetInstitutionDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetInstitutionDTO(" +
            " tsReleaseBudgetInstitution.tsReleaseBudgetInstitutionId," +
            " tsReleaseBudgetInstitution.financialYearMasterId," +
            " tsReleaseBudgetInstitution.scHeadAccountId," +
            " tsReleaseBudgetInstitution.districtId," +
            " tsReleaseBudgetInstitution.talukId," +
            " tsReleaseBudgetInstitution.institutionType," +
            " tsReleaseBudgetInstitution.institutionId," +
            " tsReleaseBudgetInstitution.date," +
            " tsReleaseBudgetInstitution.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetInstitution tsReleaseBudgetInstitution\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetInstitution.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetInstitution.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsReleaseBudgetInstitution.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetInstitution.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetInstitution.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) OR " +
            "(:joinColumn = 'scHeadAccount.scHeadAccountName' AND scHeadAccount.scHeadAccountName LIKE :searchText)"
    )
    public Page<TsReleaseBudgetInstitutionDTO> getSortedTsReleaseBudgetInstitution(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
