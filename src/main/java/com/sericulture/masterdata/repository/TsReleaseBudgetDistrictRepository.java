package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsReleaseBudgetDistrictDTO;
import com.sericulture.masterdata.model.entity.TsReleaseBudgetDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TsReleaseBudgetDistrictRepository extends PagingAndSortingRepository<TsReleaseBudgetDistrict,Long> {
    public Page<TsReleaseBudgetDistrict> findByActiveOrderByTsReleaseBudgetDistrictIdAsc(boolean isActive, final Pageable pageable);

    List<TsReleaseBudgetDistrict> findByFinancialYearMasterIdAndDistrictIdAndScHeadAccountId(long financialYearMasterId, long districtId, long scHeadAccountId );


//    public List<TsReleaseBudgetDistrict> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsReleaseBudgetDistrict> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsReleaseBudgetDistrict> findByFinancialYearMasterIdAndDistrictIdAndScHeadAccountIdAndTsReleaseBudgetDistrictIdIsNot(long financialYearMasterId,long districtId,long scHeadAccountId, long tsReleaseBudgetDistrictId);


    public TsReleaseBudgetDistrict save(TsReleaseBudgetDistrict tsReleaseBudgetDistrict);

    public TsReleaseBudgetDistrict findByTsReleaseBudgetDistrictIdAndActive(long id, boolean isActive);

    public TsReleaseBudgetDistrict findByTsReleaseBudgetDistrictIdAndActiveIn(@Param("tsReleaseBudgetDistrictId") long tsReleaseBudgetDistrictId, @Param("active") Set<Boolean> active);

    public List<TsReleaseBudgetDistrict> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetDistrictDTO(" +
            " tsReleaseBudgetDistrict.tsReleaseBudgetDistrictId," +
            " tsReleaseBudgetDistrict.financialYearMasterId," +
            " tsReleaseBudgetDistrict.scHeadAccountId," +
            " tsReleaseBudgetDistrict.districtId," +
            " tsReleaseBudgetDistrict.date," +
            " tsReleaseBudgetDistrict.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetDistrict tsReleaseBudgetDistrict\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetDistrict.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetDistrict.districtId = district.districtId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetDistrict.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetDistrict.active = :isActive " +
            "ORDER BY tsReleaseBudgetDistrict.tsReleaseBudgetDistrictId ASC"
    )
    Page<TsReleaseBudgetDistrictDTO> getByActiveOrderByTsReleaseBudgetDistrictIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetDistrictDTO(" +
            " tsReleaseBudgetDistrict.tsReleaseBudgetDistrictId," +
            " tsReleaseBudgetDistrict.financialYearMasterId," +
            " tsReleaseBudgetDistrict.scHeadAccountId," +
            " tsReleaseBudgetDistrict.districtId," +
            " tsReleaseBudgetDistrict.date," +
            " tsReleaseBudgetDistrict.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetDistrict tsReleaseBudgetDistrict\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetDistrict.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetDistrict.districtId = district.districtId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetDistrict.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetDistrict.active = :isActive AND tsReleaseBudgetDistrict.tsReleaseBudgetDistrictId = :id "
    )
    public TsReleaseBudgetDistrictDTO getByTsReleaseBudgetDistrictIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetDistrictDTO(" +
//            " tsReleaseBudgetDistrict.tsReleaseBudgetDistrictId," +
//            " tsReleaseBudgetDistrict.financialYearMasterId," +
//            " tsReleaseBudgetDistrict.date," +
//            " tsReleaseBudgetDistrict.centralReleaseBudgetDistrict," +
//            " tsReleaseBudgetDistrict.stateReleaseBudgetDistrict," +
//            " tsReleaseBudgetDistrict.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsReleaseBudgetDistrict tsReleaseBudgetDistrict\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsReleaseBudgetDistrict.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsReleaseBudgetDistrict.active = :isActive AND tsReleaseBudgetDistrict.tsReleaseBudgetDistrictId = :id "
//    )
//    public List<TsReleaseBudgetDistrictDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsReleaseBudgetDistrictDTO(" +
            " tsReleaseBudgetDistrict.tsReleaseBudgetDistrictId," +
            " tsReleaseBudgetDistrict.financialYearMasterId," +
            " tsReleaseBudgetDistrict.scHeadAccountId," +
            " tsReleaseBudgetDistrict.districtId," +
            " tsReleaseBudgetDistrict.date," +
            " tsReleaseBudgetDistrict.budgetAmount," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsReleaseBudgetDistrict tsReleaseBudgetDistrict\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsReleaseBudgetDistrict.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsReleaseBudgetDistrict.districtId = district.districtId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsReleaseBudgetDistrict.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsReleaseBudgetDistrict.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) OR " +
            "(:joinColumn = 'district.districtName' AND district.districtName LIKE :searchText)"
    )
    public Page<TsReleaseBudgetDistrictDTO> getSortedTsReleaseBudgetDistrict(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);


}
