package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.BinCounterMaster;
import com.sericulture.masterdata.model.entity.RpRoleAssociation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RpRoleAssociationRepository extends PagingAndSortingRepository<RpRoleAssociation,Long> {
    public Page<RpRoleAssociation> findByActiveOrderByRpRoleAssociationIdAsc(boolean isActive, final Pageable pageable);

    public RpRoleAssociation save(RpRoleAssociation rpRoleAssociation);

    public RpRoleAssociation findByRpRoleAssociationIdAndActive(long rpRoleAssociationId, boolean isActive);

    public RpRoleAssociation findByRpRoleAssociationIdAndActiveIn(@Param("rpRoleAssociationId") long rpRoleAssociationId, @Param("active") Set<Boolean> active);

    public List<RpRoleAssociation> findByActive(boolean isActive);

    public  List<RpRoleAssociation> findByRoleIdAndRpRolePermissionIdAndActive(Long roleId, Long rolePermissionId, boolean isActive);
}
