package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsBudgetHoaDTO;
import com.sericulture.masterdata.model.entity.TsBudgetHoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TsBudgetHoaRepository extends PagingAndSortingRepository<TsBudgetHoa,Long> {
    public Page<TsBudgetHoa> findByActiveOrderByTsBudgetHoaIdAsc(boolean isActive, final Pageable pageable);

    List<TsBudgetHoa> findByFinancialYearMasterIdAndScHeadAccountId(long financialYearMasterId,long scHeadAccountId);


//    public List<TsBudgetHoa> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsBudgetHoa> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsBudgetHoa> findByFinancialYearMasterIdAndScHeadAccountIdAndTsBudgetHoaIdIsNot(long financialYearMasterId,long scHeadAccountId, long tsBudgetHoaId);


    public TsBudgetHoa save(TsBudgetHoa tsBudgetHoa);

    public TsBudgetHoa findByTsBudgetHoaIdAndActive(long id, boolean isActive);

    public TsBudgetHoa findByTsBudgetHoaIdAndActiveIn(@Param("tsBudgetHoaId") long tsBudgetHoaId, @Param("active") Set<Boolean> active);

    public List<TsBudgetHoa> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetHoaDTO(" +
            " tsBudgetHoa.tsBudgetHoaId," +
            " tsBudgetHoa.financialYearMasterId," +
            " tsBudgetHoa.scHeadAccountId," +
            " tsBudgetHoa.date," +
            " tsBudgetHoa.budgetAmount," +
            " financialYearMaster.financialYear," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetHoa tsBudgetHoa\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetHoa.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetHoa.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetHoa.active = :isActive " +
            "ORDER BY tsBudgetHoa.tsBudgetHoaId ASC"
    )
    Page<TsBudgetHoaDTO> getByActiveOrderByTsBudgetHoaIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetHoaDTO(" +
            " tsBudgetHoa.tsBudgetHoaId," +
            " tsBudgetHoa.financialYearMasterId," +
            " tsBudgetHoa.scHeadAccountId," +
            " tsBudgetHoa.date," +
            " tsBudgetHoa.budgetAmount," +
            " financialYearMaster.financialYear," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetHoa tsBudgetHoa\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetHoa.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetHoa.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetHoa.active = :isActive AND tsBudgetHoa.tsBudgetHoaId = :id "
    )
    public TsBudgetHoaDTO getByTsBudgetHoaIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetHoaDTO(" +
//            " tsBudgetHoa.tsBudgetHoaId," +
//            " tsBudgetHoa.financialYearMasterId," +
//            " tsBudgetHoa.date," +
//            " tsBudgetHoa.centralBudgetHoa," +
//            " tsBudgetHoa.stateBudgetHoa," +
//            " tsBudgetHoa.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsBudgetHoa tsBudgetHoa\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsBudgetHoa.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsBudgetHoa.active = :isActive AND tsBudgetHoa.tsBudgetHoaId = :id "
//    )
//    public List<TsBudgetHoaDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetHoaDTO(" +
            " tsBudgetHoa.tsBudgetHoaId," +
            " tsBudgetHoa.financialYearMasterId," +
            " tsBudgetHoa.scHeadAccountId," +
            " tsBudgetHoa.date," +
            " tsBudgetHoa.budgetAmount," +
            " financialYearMaster.financialYear," +
            " scHeadAccount.scHeadAccountName" +
            ") \n" +
            "from TsBudgetHoa tsBudgetHoa\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetHoa.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join ScHeadAccount scHeadAccount\n" +
            "on tsBudgetHoa.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "where tsBudgetHoa.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) "

    )
    public Page<TsBudgetHoaDTO> getSortedTsBudgetHoa(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
