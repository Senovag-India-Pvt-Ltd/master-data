package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScUnitCostDTO;
import com.sericulture.masterdata.model.entity.ScHeadAccount;
import com.sericulture.masterdata.model.entity.ScUnitCost;
import com.sericulture.masterdata.model.entity.ScUnitCost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface ScUnitCostRepository extends PagingAndSortingRepository<ScUnitCost, Long> {

    public Page<ScUnitCost> findByActiveOrderByScUnitCostIdAsc(boolean isActive, final Pageable pageable);

    List<ScUnitCost> findByScHeadAccountIdAndScCategoryId(long scHeadAccountId, long scCategoryId);

    List<ScUnitCost> findByScHeadAccountIdAndScCategoryIdAndScUnitCostIdIsNot(long scHeadAccountId, long scCategoryId, long scUnitCostId);


    public List<ScUnitCost> findByScHeadAccountIdAndScCategoryIdAndScSubSchemeDetailsIdAndActive(long scHeadAccountId,long scCategoryId,long scSubSchemeDetailsId, boolean isActive);

    public ScUnitCost save(ScUnitCost scUnitCost);

    public ScUnitCost findByScUnitCostIdAndActive(long id, boolean isActive);

    public ScUnitCost findByScUnitCostIdAndActiveIn(@Param("scUnitCostId") long scUnitCostId, @Param("active") Set<Boolean> active);

    public List<ScUnitCost> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScUnitCostDTO(" +
            " scUnitCost.scUnitCostId," +
            " scUnitCost.scHeadAccountId," +
            " scUnitCost.scCategoryId," +
            " scUnitCost.scSubSchemeDetailsId," +
            " scUnitCost.centralShare," +
            " scUnitCost.stateShare," +
            " scUnitCost.benificiaryShare," +
            " scUnitCost.unitCost," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScUnitCost scUnitCost\n" +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scUnitCost.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scUnitCost.scCategoryId = scCategory.scCategoryId " +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scUnitCost.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scUnitCost.active = :isActive " +
            "ORDER BY scUnitCost.scUnitCostId ASC"
    )
    Page<ScUnitCostDTO> getByActiveOrderByScUnitCostIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScUnitCostDTO(" +
            " scUnitCost.scUnitCostId," +
            " scUnitCost.scHeadAccountId," +
            " scUnitCost.scCategoryId," +
            " scUnitCost.scSubSchemeDetailsId," +
            " scUnitCost.centralShare," +
            " scUnitCost.stateShare," +
            " scUnitCost.benificiaryShare," +
            " scUnitCost.unitCost," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScUnitCost scUnitCost\n" +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scUnitCost.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scUnitCost.scCategoryId = scCategory.scCategoryId " +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scUnitCost.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scUnitCost.active = :isActive AND scUnitCost.scUnitCostId = :id "
    )
    public ScUnitCostDTO getByScUnitCostIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScUnitCostDTO(" +
            " scUnitCost.scUnitCostId," +
            " scUnitCost.scHeadAccountId," +
            " scUnitCost.scCategoryId," +
            " scUnitCost.scSubSchemeDetailsId," +
            " scUnitCost.centralShare," +
            " scUnitCost.stateShare," +
            " scUnitCost.benificiaryShare," +
            " scUnitCost.unitCost," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScUnitCost scUnitCost\n" +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scUnitCost.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scUnitCost.scCategoryId = scCategory.scCategoryId " +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scUnitCost.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scUnitCost.active = :isActive AND " +
            "(:joinColumn = 'scHeadAccount.scHeadAccountName' AND scHeadAccount.scHeadAccountName LIKE :searchText) OR " +
            "(:joinColumn = 'scCategory.categoryName' AND scCategory.categoryName LIKE :searchText)"
    )
    public Page<ScUnitCostDTO> getSortedScUnitCost(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
