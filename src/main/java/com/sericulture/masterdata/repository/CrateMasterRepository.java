package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.CrateMaster;
import com.sericulture.masterdata.model.entity.VendorMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CrateMasterRepository extends PagingAndSortingRepository<CrateMaster, Long> {



    public Page<CrateMaster> findByActiveOrderByCrateMasterIdAsc(boolean isActive, final Pageable pageable);

    public CrateMaster save(CrateMaster crateMaster);

    public CrateMaster findByCrateMasterIdAndActive(long id, boolean isActive);

    public CrateMaster findByCrateMasterIdAndActiveIn(@Param("crateMasterId") long crateMasterId, @Param("active") Set<Boolean> active);

    public List<CrateMaster> findByActive(boolean isActive);
}
