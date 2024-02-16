package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import com.sericulture.masterdata.model.entity.HdModuleMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface HdModuleMasterRepository extends PagingAndSortingRepository<HdModuleMaster, Long> {
    public List<HdModuleMaster> findByHdModuleName(String hdModuleName);

    public List<HdModuleMaster> findByHdModuleNameAndHdModuleIdIsNot(String hdModuleName,long hdModuleId);


    public HdModuleMaster findByHdModuleNameAndActive(String hdModuleName, boolean isActive);

    public Page<HdModuleMaster> findByActiveOrderByHdModuleNameAsc(boolean isActive, final Pageable pageable);

    public HdModuleMaster save(HdModuleMaster hdModuleMaster);

    public HdModuleMaster findByHdModuleIdAndActive(long id, boolean isActive);

    public HdModuleMaster findByHdModuleIdAndActiveIn(@Param("hdModuleId") long hdModuleId, @Param("active") Set<Boolean> active);

    public List<HdModuleMaster> findByActiveOrderByHdModuleNameAsc(boolean isActive);
}
