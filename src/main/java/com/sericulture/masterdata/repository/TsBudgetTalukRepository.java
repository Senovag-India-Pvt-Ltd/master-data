package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TsBudgetTalukDTO;
import com.sericulture.masterdata.model.entity.TsBudgetTaluk;
import com.sericulture.masterdata.model.entity.TsBudgetTaluk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface TsBudgetTalukRepository extends PagingAndSortingRepository<TsBudgetTaluk,Long> {

    public Page<TsBudgetTaluk> findByActiveOrderByTsBudgetTalukIdAsc(boolean isActive, final Pageable pageable);

    List<TsBudgetTaluk> findByFinancialYearMasterIdAndDistrictIdAndTalukId(long financialYearMasterId,long districtId,long talukId);


//    public List<TsBudgetTaluk> findByFinancialYearMasterIdAndActiveOrderBySubSchemeNameAsc(long scSchemeDetailsId, boolean isActive);

//    public List<TsBudgetTaluk> findByFinancialYearMasterIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);


    List<TsBudgetTaluk> findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndTsBudgetTalukIdIsNot(long financialYearMasterId,long districtId,long talukId, long tsBudgetTalukId);


    public TsBudgetTaluk save(TsBudgetTaluk tsBudgetTaluk);

    public TsBudgetTaluk findByTsBudgetTalukIdAndActive(long id, boolean isActive);

    public TsBudgetTaluk findByTsBudgetTalukIdAndActiveIn(@Param("tsBudgetTalukId") long tsBudgetTalukId, @Param("active") Set<Boolean> active);

    public List<TsBudgetTaluk> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetTalukDTO(" +
            " tsBudgetTaluk.tsBudgetTalukId," +
            " tsBudgetTaluk.financialYearMasterId," +
            " tsBudgetTaluk.hoaId," +
            " tsBudgetTaluk.date," +
            " tsBudgetTaluk.budgetAmount," +
            " tsBudgetTaluk.districtId," +
            " tsBudgetTaluk.talukId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName" +
            ") \n" +
            "from TsBudgetTaluk tsBudgetTaluk\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetTaluk.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetTaluk.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetTaluk.talukId = taluk.talukId " +
            "where tsBudgetTaluk.active = :isActive " +
            "ORDER BY tsBudgetTaluk.tsBudgetTalukId ASC"
    )
    Page<TsBudgetTalukDTO> getByActiveOrderByTsBudgetTalukIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetTalukDTO(" +
            " tsBudgetTaluk.tsBudgetTalukId," +
            " tsBudgetTaluk.financialYearMasterId," +
            " tsBudgetTaluk.hoaId," +
            " tsBudgetTaluk.date," +
            " tsBudgetTaluk.budgetAmount," +
            " tsBudgetTaluk.districtId," +
            " tsBudgetTaluk.talukId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName" +
            ") \n" +
            "from TsBudgetTaluk tsBudgetTaluk\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetTaluk.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetTaluk.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetTaluk.talukId = taluk.talukId " +
            "where tsBudgetTaluk.active = :isActive AND tsBudgetTaluk.tsBudgetTalukId = :id "
    )
    public TsBudgetTalukDTO getByTsBudgetTalukIdAndActive(long id, boolean isActive);


//    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetTalukDTO(" +
//            " tsBudgetTaluk.tsBudgetTalukId," +
//            " tsBudgetTaluk.financialYearMasterId," +
//            " tsBudgetTaluk.date," +
//            " tsBudgetTaluk.centralBudgetTaluk," +
//            " tsBudgetTaluk.stateBudgetTaluk," +
//            " tsBudgetTaluk.amount," +
//            " financialYearMaster.financialYear" +
//            ") \n" +
//            "from TsBudgetTaluk tsBudgetTaluk\n" +
//            "left join FinancialYearMaster financialYearMaster\n" +
//            "on tsBudgetTaluk.financialYearMasterId = financialYearMaster.financialYearMasterId " +
//            "where tsBudgetTaluk.active = :isActive AND tsBudgetTaluk.tsBudgetTalukId = :id "
//    )
//    public List<TsBudgetTalukDTO> getByFinancialYearMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TsBudgetTalukDTO(" +
            " tsBudgetTaluk.tsBudgetTalukId," +
            " tsBudgetTaluk.financialYearMasterId," +
            " tsBudgetTaluk.hoaId," +
            " tsBudgetTaluk.date," +
            " tsBudgetTaluk.budgetAmount," +
            " tsBudgetTaluk.districtId," +
            " tsBudgetTaluk.talukId," +
            " financialYearMaster.financialYear," +
            " district.districtName," +
            " taluk.talukName" +
            ") \n" +
            "from TsBudgetTaluk tsBudgetTaluk\n" +
            "left join FinancialYearMaster financialYearMaster\n" +
            "on tsBudgetTaluk.financialYearMasterId = financialYearMaster.financialYearMasterId " +
            "left join District district\n" +
            "on tsBudgetTaluk.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on tsBudgetTaluk.talukId = taluk.talukId " +
            "where tsBudgetTaluk.active = :isActive AND " +
            "(:joinColumn = 'financialYearMaster.financialYear' AND financialYearMaster.financialYear LIKE :searchText) OR " +
            "(:joinColumn = 'taluk.talukName' AND taluk.talukName LIKE :searchText)"
    )
    public Page<TsBudgetTalukDTO> getSortedTsBudgetTaluk(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);


}
