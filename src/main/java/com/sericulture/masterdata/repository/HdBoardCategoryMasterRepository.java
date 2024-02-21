package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.HdBoardCategoryMaster;
import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface HdBoardCategoryMasterRepository extends PagingAndSortingRepository<HdBoardCategoryMaster, Long> {

    public List<HdBoardCategoryMaster> findByHdBoardCategoryName(String hdBoardCategoryName);

    public List<HdBoardCategoryMaster> findByActiveAndHdBoardCategoryName(boolean active,String hdBoardCategoryName);

    public HdBoardCategoryMaster findByHdBoardCategoryNameAndActive(String hdBoardCategoryName, boolean isActive);

    public Page<HdBoardCategoryMaster> findByActiveOrderByHdBoardCategoryNameAsc(boolean isActive, final Pageable pageable);

    public HdBoardCategoryMaster save(HdBoardCategoryMaster hdBoardCategoryMaster);

    public HdBoardCategoryMaster findByHdBoardCategoryIdAndActive(long id, boolean isActive);

    public HdBoardCategoryMaster findByHdBoardCategoryIdAndActiveIn(@Param("hdBoardCategoryId") long hdBoardCategoryId, @Param("active") Set<Boolean> active);

    public List<HdBoardCategoryMaster> findByActiveOrderByHdBoardCategoryNameAsc(boolean isActive);
}

