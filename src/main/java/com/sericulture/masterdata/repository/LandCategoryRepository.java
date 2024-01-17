package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.LandCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface LandCategoryRepository extends PagingAndSortingRepository<LandCategory, Long> {
    public List<LandCategory> findByLandCategoryNameAndLandCategoryNameInKannada(String landCategoryName,String landCategoryNameInKannada);

    public LandCategory findByLandCategoryNameAndActive(String landCategoryName,boolean isActive);

    public Page<LandCategory> findByActiveOrderByLandCategoryNameAsc(boolean isActive, final Pageable pageable);

    public LandCategory save(LandCategory landCategory);

    public LandCategory findByIdAndActive(long id, boolean isActive);

    public LandCategory findByIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);

    public List<LandCategory> findByActive(boolean isActive);




}
