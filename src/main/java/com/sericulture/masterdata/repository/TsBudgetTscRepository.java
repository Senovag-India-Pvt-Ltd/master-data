package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsBudgetTscDTO;
import com.sericulture.masterdata.model.entity.TsBudgetTsc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TsBudgetTscRepository extends PagingAndSortingRepository<TsBudgetTsc,Long> {
    public Page<TsBudgetTsc> findByActiveOrderByTsBudgetTscIdAsc(boolean isActive, final Pageable pageable);

    List<TsBudgetTsc> findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndTscMasterId(long financialYearMasterId,long districtId,long talukId,long tscMasterId);


//    public List<TsBudgetTsc> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsBudgetTsc> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsBudgetTsc> findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndTscMasterIdAndTsBudgetTscIdIsNot(long financialYearMasterId,long districtId,long talukId,long tscMasterId, long tsBudgetTscId);


    public TsBudgetTsc save(TsBudgetTsc tsBudgetTsc);

    public TsBudgetTsc findByTsBudgetTscIdAndActive(long id, boolean isActive);

    public TsBudgetTsc findByTsBudgetTscIdAndActiveIn(@Param("tsBudgetTscId") long tsBudgetTscId, @Param("active") Set<Boolean> active);

    public List<TsBudgetTsc> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetTscDTO(" +
            " tsBudgetTsc.tsBudgetTscId," +
            " tsBudgetTsc.financialYearMasterId," +
            " tsBudgetTsc.hoaId," +
            " tsBudgetTsc.date," +
            " tsBudgetTsc.budgetAmount," +
            " tsBudgetTsc.districtId," +
            " tsBudgetTsc.talukId," +
            " tsBudgetTsc.tscMasterId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " tscMaster.name" +
            ") \n" +
            "from TsBudgetTsc tsBudgetTsc\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetTsc.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetTsc.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetTsc.talukId = taluk.talukId " +
            "left join TscMaster tscMaster\n" +
            "on tsBudgetTsc.tscMasterId = tscMaster.tscMasterId " +
            "where tsBudgetTsc.active = :isActive " +
            "ORDER BY tsBudgetTsc.tsBudgetTscId ASC"
    )
    Page<TsBudgetTscDTO> getByActiveOrderByTsBudgetTscIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetTscDTO(" +
            " tsBudgetTsc.tsBudgetTscId," +
            " tsBudgetTsc.financialYearMasterId," +
            " tsBudgetTsc.hoaId," +
            " tsBudgetTsc.date," +
            " tsBudgetTsc.budgetAmount," +
            " tsBudgetTsc.districtId," +
            " tsBudgetTsc.talukId," +
            " tsBudgetTsc.tscMasterId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " tscMaster.name" +
            ") \n" +
            "from TsBudgetTsc tsBudgetTsc\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetTsc.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetTsc.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetTsc.talukId = taluk.talukId " +
            "left join TscMaster tscMaster\n" +
            "on tsBudgetTsc.tscMasterId = tscMaster.tscMasterId " +
            "where tsBudgetTsc.active = :isActive AND tsBudgetTsc.tsBudgetTscId = :id "
    )
    public TsBudgetTscDTO getByTsBudgetTscIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetTscDTO(" +
//            " tsBudgetTsc.tsBudgetTscId," +
//            " tsBudgetTsc.financialYearMasterId," +
//            " tsBudgetTsc.date," +
//            " tsBudgetTsc.centralBudgetTsc," +
//            " tsBudgetTsc.stateBudgetTsc," +
//            " tsBudgetTsc.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsBudgetTsc tsBudgetTsc\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsBudgetTsc.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsBudgetTsc.active = :isActive AND tsBudgetTsc.tsBudgetTscId = :id "
//    )
//    public List<TsBudgetTscDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetTscDTO(" +
            " tsBudgetTsc.tsBudgetTscId," +
            " tsBudgetTsc.financialYearMasterId," +
            " tsBudgetTsc.hoaId," +
            " tsBudgetTsc.date," +
            " tsBudgetTsc.budgetAmount," +
            " tsBudgetTsc.districtId," +
            " tsBudgetTsc.talukId," +
            " tsBudgetTsc.tscMasterId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName," +
            " tscMaster.name" +
            ") \n" +
            "from TsBudgetTsc tsBudgetTsc\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetTsc.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetTsc.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetTsc.talukId = taluk.talukId " +
            "left join TscMaster tscMaster\n" +
            "on tsBudgetTsc.tscMasterId = tscMaster.tscMasterId " +
            "where tsBudgetTsc.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) OR " +
            "(:joinColumn = 'tscMaster.name' AND tscMaster.name LIKE :searchText)"
    )
    public Page<TsBudgetTscDTO> getSortedTsBudgetTsc(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
