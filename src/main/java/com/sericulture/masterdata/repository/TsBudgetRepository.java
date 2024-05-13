package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsBudgetDTO;
import com.sericulture.masterdata.model.entity.ScSubSchemeDetails;
import com.sericulture.masterdata.model.entity.TsBudget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TsBudgetRepository extends PagingAndSortingRepository<TsBudget,Long> {
    public Page<TsBudget> findByActiveOrderByTsBudgetIdAsc(boolean isActive, final Pageable pageable);

    List<TsBudget> findByFinancialYearMasterId(long financialYearMasterId);


//    public List<TsBudget> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsBudget> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsBudget> findByFinancialYearMasterIdAndTsBudgetIdIsNot(long financialYearMasterId, long tsBudgetId);


    public TsBudget save(TsBudget tsBudget);

    public TsBudget findByTsBudgetIdAndActive(long id, boolean isActive);

    public TsBudget findByTsBudgetIdAndActiveIn(@Param("tsBudgetId") long tsBudgetId, @Param("active") Set<Boolean> active);

    public List<TsBudget> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetDTO(" +
            " tsBudget.tsBudgetId," +
            " tsBudget.financialYearMasterId," +
            " tsBudget.date," +
            " tsBudget.centralBudget," +
            " tsBudget.stateBudget," +
            " tsBudget.amount," +
            " financialYearMaster.financialYear" +
            ") \n" +
            "from TsBudget tsBudget\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudget.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "where tsBudget.active = :isActive " +
            "ORDER BY tsBudget.tsBudgetId ASC"
    )
    Page<TsBudgetDTO> getByActiveOrderByTsBudgetIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetDTO(" +
            " tsBudget.tsBudgetId," +
            " tsBudget.financialYearMasterId," +
            " tsBudget.date," +
            " tsBudget.centralBudget," +
            " tsBudget.stateBudget," +
            " tsBudget.amount," +
            " financialYearMaster.financialYear" +
            ") \n" +
            "from TsBudget tsBudget\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudget.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "where tsBudget.active = :isActive AND tsBudget.tsBudgetId = :id "
    )
    public TsBudgetDTO getByTsBudgetIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetDTO(" +
//            " tsBudget.tsBudgetId," +
//            " tsBudget.financialYearMasterId," +
//            " tsBudget.date," +
//            " tsBudget.centralBudget," +
//            " tsBudget.stateBudget," +
//            " tsBudget.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsBudget tsBudget\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsBudget.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsBudget.active = :isActive AND tsBudget.tsBudgetId = :id "
//    )
//    public List<TsBudgetDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetDTO(" +
            " tsBudget.tsBudgetId," +
            " tsBudget.financialYearMasterId," +
            " tsBudget.date," +
            " tsBudget.centralBudget," +
            " tsBudget.stateBudget," +
            " tsBudget.amount," +
            " financialYearMaster.financialYear" +
            ") \n" +
            "from TsBudget tsBudget\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudget.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "where tsBudget.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) "

    )
    public Page<TsBudgetDTO> getSortedTsBudget(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
