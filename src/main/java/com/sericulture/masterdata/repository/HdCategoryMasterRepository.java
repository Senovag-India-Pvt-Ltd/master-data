package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface HdCategoryMasterRepository extends PagingAndSortingRepository<HdCategoryMaster, Long> {
    public List<HdCategoryMaster> findByHdCategoryName(String hdCategoryName);

    public HdCategoryMaster findByHdCategoryNameAndActive(String hdCategoryName, boolean isActive);

    public Page<HdCategoryMaster> findByActiveOrderByHdCategoryNameAsc(boolean isActive, final Pageable pageable);

    public HdCategoryMaster save(HdCategoryMaster hdCategoryMaster);

    public HdCategoryMaster findByHdCategoryIdAndActive(long id, boolean isActive);

    public HdCategoryMaster findByHdCategoryIdAndActiveIn(@Param("hdCategoryId") long hdCategoryId, @Param("active") Set<Boolean> active);

    public List<HdCategoryMaster> findByActiveOrderByHdCategoryNameAsc(boolean isActive);
}
