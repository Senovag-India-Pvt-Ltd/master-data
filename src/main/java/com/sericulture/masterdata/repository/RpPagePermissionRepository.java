package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.RpPagePermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RpPagePermissionRepository extends PagingAndSortingRepository<RpPagePermission, Long> {
    public Page<RpPagePermission> findByActiveOrderByRpPagePermissionIdAsc(boolean isActive, final Pageable pageable);

    public RpPagePermission save(RpPagePermission rpPagePermission);

    public RpPagePermission findByRpPagePermissionIdAndActive(long id, boolean isActive);

    public RpPagePermission findByRpPagePermissionIdAndActiveIn(@Param("rpPagePermissionId") long stateId, @Param("active") Set<Boolean> active);

    public List<RpPagePermission> findByActiveOrderByPageNameAsc(boolean isActive);
}