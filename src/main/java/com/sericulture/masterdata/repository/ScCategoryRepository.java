package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ScCategory;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScCategoryRepository extends PagingAndSortingRepository<ScCategory, Long> {

    public List<ScCategory> findByCategoryName(String categoryName);

    public List<ScCategory> findByCategoryNameAndCategoryNameInKannada(String categoryName,String categoryNameInKannada);

    public List<ScCategory> findByCategoryNameAndCategoryNameInKannadaAndScCategoryIdIsNot(String categoryName,String categoryNameInKannada,long scCategoryId);

    public ScCategory findByCategoryNameAndActive(String categoryName,boolean isActive);

    public Page<ScCategory> findByActiveOrderByCategoryNameAsc(boolean isActive, final Pageable pageable);

    public ScCategory save(ScCategory scCategory);

    public ScCategory findByScCategoryIdAndActive(long id, boolean isActive);

    public ScCategory findByScCategoryIdAndActiveIn(@Param("scCategoryId") long scCategoryId, @Param("active") Set<Boolean> active);

    public List<ScCategory> findByActive(boolean isActive);
}
