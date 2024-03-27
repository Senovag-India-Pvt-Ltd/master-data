package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScHeadAccountCategoryDTO;
import com.sericulture.masterdata.model.entity.ScHeadAccountCategory;
import com.sericulture.masterdata.model.entity.ScHeadAccountCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScHeadAccountCategoryRepository extends PagingAndSortingRepository<ScHeadAccountCategory, Long> {

    public Page<ScHeadAccountCategory> findByActiveOrderByScHeadAccountCategoryIdAsc(boolean isActive, final Pageable pageable);

    List<ScHeadAccountCategory> findByScHeadAccountIdAndScCategoryId(long scHeadAccountId, long scCategoryId);

    List<ScHeadAccountCategory> findByScHeadAccountIdAndScCategoryIdAndScHeadAccountCategoryIdIsNot(long scHeadAccountId, long scCategoryId, long scHeadAccountCategoryId);


    public ScHeadAccountCategory save(ScHeadAccountCategory scHeadAccountCategory);

    public ScHeadAccountCategory findByScHeadAccountCategoryIdAndActive(long id, boolean isActive);

    public ScHeadAccountCategory findByScHeadAccountCategoryIdAndActiveIn(@Param("scHeadAccountCategoryId") long scHeadAccountCategoryId, @Param("active") Set<Boolean> active);

    public List<ScHeadAccountCategory> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScHeadAccountCategoryDTO(" +
            " scHeadAccountCategory.scHeadAccountCategoryId," +
            " scHeadAccountCategory.scHeadAccountId," +
            " scHeadAccountCategory.scCategoryId," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName" +
            ") \n" +
            "from ScHeadAccountCategory scHeadAccountCategory\n" +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scHeadAccountCategory.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scHeadAccountCategory.scCategoryId = scCategory.scCategoryId " +
            "where scHeadAccountCategory.active = :isActive " +
            "ORDER BY scHeadAccountCategory.scHeadAccountCategoryId ASC"
    )
    Page<ScHeadAccountCategoryDTO> getByActiveOrderByScHeadAccountCategoryIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScHeadAccountCategoryDTO(" +
            " scHeadAccountCategory.scHeadAccountCategoryId," +
            " scHeadAccountCategory.scHeadAccountId," +
            " scHeadAccountCategory.scCategoryId," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName" +
            ") \n" +
            "from ScHeadAccountCategory scHeadAccountCategory\n" +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scHeadAccountCategory.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scHeadAccountCategory.scCategoryId = scCategory.scCategoryId " +
            "where scHeadAccountCategory.active = :isActive AND scHeadAccountCategory.scHeadAccountCategoryId = :id "
    )
    public ScHeadAccountCategoryDTO getByScHeadAccountCategoryIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScHeadAccountCategoryDTO(" +
            " scHeadAccountCategory.scHeadAccountCategoryId," +
            " scHeadAccountCategory.scHeadAccountId," +
            " scHeadAccountCategory.scCategoryId," +
            " scHeadAccount.scHeadAccountName," +
            " scCategory.categoryName" +
            ") \n" +
            "from ScHeadAccountCategory scHeadAccountCategory\n" +
            "left join ScHeadAccount scHeadAccount\n" +
            "on scHeadAccountCategory.scHeadAccountId = scHeadAccount.scHeadAccountId " +
            "left join ScCategory scCategory\n" +
            "on scHeadAccountCategory.scCategoryId = scCategory.scCategoryId " +
            "where scHeadAccountCategory.active = :isActive AND " +
            "(:joinColumn = 'scHeadAccount.scHeadAccountName' AND scHeadAccount.scHeadAccountName LIKE :searchText) OR " +
            "(:joinColumn = 'scCategory.categoryName' AND scCategory.categoryName LIKE :searchText)"
    )
    public Page<ScHeadAccountCategoryDTO> getSortedScHeadAccountCategory(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}

