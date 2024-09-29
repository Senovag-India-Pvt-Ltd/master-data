package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.CropInspectionType;
import com.sericulture.masterdata.model.entity.MarketTypeMaster;
import com.sericulture.masterdata.model.entity.Mount;
import com.sericulture.masterdata.model.entity.Mount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface MountRepository  extends PagingAndSortingRepository<Mount, Long> {
    public List<Mount> findByName(String name);

    public List<Mount> findByActiveAndName(boolean a,String name);

    public Mount findByNameAndActive(String name,boolean isActive);

    public Page<Mount> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public Mount save(Mount mount);

    public Mount findByMountIdAndActive(long id, boolean isActive);

    public Mount findByMountIdAndActiveIn(@Param("mountId") long mountId, @Param("active") Set<Boolean> active);

    public List<Mount> findByActive(boolean isActive);
}
