package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.HdModuleMaster;
import com.sericulture.masterdata.model.entity.HdStatusMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface HdStatusMasterRepository extends PagingAndSortingRepository<HdStatusMaster, Long> {

    public List<HdStatusMaster> findByHdStatusName(String hdStatusName);

    public HdStatusMaster findByHdStatusNameAndActive(String hdStatusName, boolean isActive);

    public Page<HdStatusMaster> findByActiveOrderByHdStatusNameAsc(boolean isActive, final Pageable pageable);

    public HdStatusMaster save(HdStatusMaster hdStatusMaster);

    public HdStatusMaster findByHdStatusIdAndActive(long id, boolean isActive);

    public HdStatusMaster findByHdStatusIdAndActiveIn(@Param("hdStatusId") long hdStatusId, @Param("active") Set<Boolean> active);

    public List<HdStatusMaster> findByActiveOrderByHdStatusNameAsc(boolean isActive);
}

