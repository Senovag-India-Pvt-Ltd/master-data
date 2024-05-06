package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsBudgetDistrictDTO;
import com.sericulture.masterdata.model.entity.TsBudgetDistrict;
import com.sericulture.masterdata.model.entity.TsBudgetDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TsBudgetDistrictRepository extends PagingAndSortingRepository<TsBudgetDistrict,Long> {
    public Page<TsBudgetDistrict> findByActiveOrderByTsBudgetDistrictIdAsc(boolean isActive, final Pageable pageable);

    List<TsBudgetDistrict> findByFinancialYearMasterIdAndDistrictIdAndScHeadAccountId(long financialYearMasterId,long districtId,long scHeadAccountId );


//    public List<TsBudgetDistrict> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsBudgetDistrict> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsBudgetDistrict> findByFinancialYearMasterIdAndDistrictIdAndScHeadAccountIdAndTsBudgetDistrictIdIsNot(long financialYearMasterId,long districtId,long scHeadAccountId, long tsBudgetDistrictId);


    public TsBudgetDistrict save(TsBudgetDistrict tsBudgetDistrict);

    public TsBudgetDistrict findByTsBudgetDistrictIdAndActive(long id, boolean isActive);

    public TsBudgetDistrict findByTsBudgetDistrictIdAndActiveIn(@Param("tsBudgetDistrictId") long tsBudgetDistrictId, @Param("active") Set<Boolean> active);

    public List<TsBudgetDistrict> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetDistrictDTO(" +
            " tsBudgetDistrict.tsBudgetDistrictId," +
            " tsBudgetDistrict.financialYearMasterId," +
            " tsBudgetDistrict.scHeadAccountId," +
            " tsBudgetDistrict.date," +
            " tsBudgetDistrict.budgetAmount," +
            " tsBudgetDistrict.districtId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetDistrict tsBudgetDistrict\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetDistrict.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetDistrict.districtId = district.districtId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetDistrict.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetDistrict.active = :isActive " +
            "ORDER BY tsBudgetDistrict.tsBudgetDistrictId ASC"
    )
    Page<TsBudgetDistrictDTO> getByActiveOrderByTsBudgetDistrictIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetDistrictDTO(" +
            " tsBudgetDistrict.tsBudgetDistrictId," +
            " tsBudgetDistrict.financialYearMasterId," +
            " tsBudgetDistrict.scHeadAccountId," +
            " tsBudgetDistrict.date," +
            " tsBudgetDistrict.budgetAmount," +
            " tsBudgetDistrict.districtId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetDistrict tsBudgetDistrict\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetDistrict.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetDistrict.districtId = district.districtId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetDistrict.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetDistrict.active = :isActive AND tsBudgetDistrict.tsBudgetDistrictId = :id "
    )
    public TsBudgetDistrictDTO getByTsBudgetDistrictIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetDistrictDTO(" +
//            " tsBudgetDistrict.tsBudgetDistrictId," +
//            " tsBudgetDistrict.financialYearMasterId," +
//            " tsBudgetDistrict.date," +
//            " tsBudgetDistrict.centralBudgetDistrict," +
//            " tsBudgetDistrict.stateBudgetDistrict," +
//            " tsBudgetDistrict.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsBudgetDistrict tsBudgetDistrict\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsBudgetDistrict.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsBudgetDistrict.active = :isActive AND tsBudgetDistrict.tsBudgetDistrictId = :id "
//    )
//    public List<TsBudgetDistrictDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetDistrictDTO(" +
            " tsBudgetDistrict.tsBudgetDistrictId," +
            " tsBudgetDistrict.financialYearMasterId," +
            " tsBudgetDistrict.scHeadAccountId," +
            " tsBudgetDistrict.date," +
            " tsBudgetDistrict.budgetAmount," +
            " tsBudgetDistrict.districtId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetDistrict tsBudgetDistrict\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetDistrict.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetDistrict.districtId = district.districtId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetDistrict.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetDistrict.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) OR " +
            "(:joinColumn = 'district.districtName' AND district.districtName LIKE :searchText)"
    )
    public Page<TsBudgetDistrictDTO> getSortedTsBudgetDistrict(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);


}