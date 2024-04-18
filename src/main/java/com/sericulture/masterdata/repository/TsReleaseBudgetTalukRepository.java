package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsReleaseBudgetTalukDTO;
import com.sericulture.masterdata.model.entity.TsReleaseBudgetTaluk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TsReleaseBudgetTalukRepository extends PagingAndSortingRepository<TsReleaseBudgetTaluk,Long> {
    public Page<TsReleaseBudgetTaluk> findByActiveOrderByTsReleaseBudgetTalukIdAsc(boolean isActive, final Pageable pageable);

    List<TsReleaseBudgetTaluk> findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndScHeadAccountId(long financialYearMasterId, long districtId, long talukId, long scHeadAccountId);


//    public List<TsReleaseBudgetTaluk> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsReleaseBudgetTaluk> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsReleaseBudgetTaluk> findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndScHeadAccountIdAndTsReleaseBudgetTalukIdIsNot(long financialYearMasterId,long districtId,long talukId,long scHeadAccountId, long tsReleaseBudgetTalukId);


    public TsReleaseBudgetTaluk save(TsReleaseBudgetTaluk tsReleaseBudgetTaluk);

    public TsReleaseBudgetTaluk findByTsReleaseBudgetTalukIdAndActive(long id, boolean isActive);

    public TsReleaseBudgetTaluk findByTsReleaseBudgetTalukIdAndActiveIn(@Param("tsReleaseBudgetTalukId") long tsReleaseBudgetTalukId, @Param("active") Set<Boolean> active);

    public List<TsReleaseBudgetTaluk> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetTalukDTO(" +
            " tsReleaseBudgetTaluk.tsReleaseBudgetTalukId," +
            " tsReleaseBudgetTaluk.financialYearMasterId," +
            " tsReleaseBudgetTaluk.scHeadAccountId," +
            " tsReleaseBudgetTaluk.districtId," +
            " tsReleaseBudgetTaluk.talukId," +
            " tsReleaseBudgetTaluk.date," +
            " tsReleaseBudgetTaluk.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetTaluk tsReleaseBudgetTaluk\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetTaluk.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetTaluk.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsReleaseBudgetTaluk.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetTaluk.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetTaluk.active = :isActive " +
            "ORDER BY tsReleaseBudgetTaluk.tsReleaseBudgetTalukId ASC"
    )
    Page<TsReleaseBudgetTalukDTO> getByActiveOrderByTsReleaseBudgetTalukIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetTalukDTO(" +
            " tsReleaseBudgetTaluk.tsReleaseBudgetTalukId," +
            " tsReleaseBudgetTaluk.financialYearMasterId," +
            " tsReleaseBudgetTaluk.scHeadAccountId," +
            " tsReleaseBudgetTaluk.districtId," +
            " tsReleaseBudgetTaluk.talukId," +
            " tsReleaseBudgetTaluk.date," +
            " tsReleaseBudgetTaluk.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetTaluk tsReleaseBudgetTaluk\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetTaluk.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetTaluk.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsReleaseBudgetTaluk.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetTaluk.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetTaluk.active = :isActive AND tsReleaseBudgetTaluk.tsReleaseBudgetTalukId = :id "
    )
    public TsReleaseBudgetTalukDTO getByTsReleaseBudgetTalukIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetTalukDTO(" +
//            " tsReleaseBudgetTaluk.tsReleaseBudgetTalukId," +
//            " tsReleaseBudgetTaluk.financialYearMasterId," +
//            " tsReleaseBudgetTaluk.date," +
//            " tsReleaseBudgetTaluk.centralReleaseBudgetTaluk," +
//            " tsReleaseBudgetTaluk.stateReleaseBudgetTaluk," +
//            " tsReleaseBudgetTaluk.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsReleaseBudgetTaluk tsReleaseBudgetTaluk\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsReleaseBudgetTaluk.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsReleaseBudgetTaluk.active = :isActive AND tsReleaseBudgetTaluk.tsReleaseBudgetTalukId = :id "
//    )
//    public List<TsReleaseBudgetTalukDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetTalukDTO(" +
            " tsReleaseBudgetTaluk.tsReleaseBudgetTalukId," +
            " tsReleaseBudgetTaluk.financialYearMasterId," +
            " tsReleaseBudgetTaluk.scHeadAccountId," +
            " tsReleaseBudgetTaluk.districtId," +
            " tsReleaseBudgetTaluk.talukId," +
            " tsReleaseBudgetTaluk.date," +
            " tsReleaseBudgetTaluk.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetTaluk tsReleaseBudgetTaluk\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetTaluk.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetTaluk.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsReleaseBudgetTaluk.talukId = taluk.talukId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetTaluk.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetTaluk.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) OR " +
            "(:joinColumn = 'taluk.talukName' AND taluk.talukName LIKE :searchText)"
    )
    public Page<TsReleaseBudgetTalukDTO> getSortedTsReleaseBudgetTaluk(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);


}
