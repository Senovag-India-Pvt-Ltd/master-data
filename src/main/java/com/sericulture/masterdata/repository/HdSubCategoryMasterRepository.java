package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import com.sericulture.masterdata.model.entity.HdSubCategoryMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HdSubCategoryMasterRepository extends PagingAndSortingRepository<HdSubCategoryMaster, Long> {

    public List<HdSubCategoryMaster> findByHdSubCategoryName(String hdSubCategoryName);

    public HdSubCategoryMaster findByHdSubCategoryNameAndActive(String hdSubCategoryName, boolean isActive);

    public Page<HdSubCategoryMaster> findByActiveOrderByHdSubCategoryNameAsc(boolean isActive, final Pageable pageable);

    public HdSubCategoryMaster save(HdSubCategoryMaster hdSubCategoryMaster);

    public HdSubCategoryMaster findByHdSubCategoryIdAndActive(long id, boolean isActive);

    public HdSubCategoryMaster findByHdSubCategoryIdAndActiveIn(@Param("hdSubCategoryId") long hdSubCategoryId, @Param("active") Set<Boolean> active);

    public List<HdSubCategoryMaster> findByActiveOrderByHdSubCategoryNameAsc(boolean isActive);
}


