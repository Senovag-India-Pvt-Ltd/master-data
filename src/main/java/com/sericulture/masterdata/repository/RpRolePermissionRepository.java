package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.RpRoleAssociation;
import com.sericulture.masterdata.model.entity.RpRolePermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RpRolePermissionRepository extends PagingAndSortingRepository<RpRolePermission, Long> {

    public Page<RpRolePermission> findByActiveOrderByRpRolePermissionIdAsc(boolean isActive, final Pageable pageable);

    public RpRolePermission save(RpRolePermission rpRolePermission);

    public RpRolePermission findByRpRolePermissionIdAndActive(long rpRolePermissionId, boolean isActive);

    public RpRolePermission findByRpRolePermissionIdAndActiveIn(@Param("rpRolePermissionId") long rpRolePermissionId, @Param("active") Set<Boolean> active);

    public List<RpRolePermission> findByActive(boolean isActive);
}
