package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    public List<Role> findByRoleName(String roleName);

    public Role findByRoleNameAndActive(String roleName,boolean isActive);

    public Page<Role> findByActiveOrderByRoleIdAsc(boolean isActive, final Pageable pageable);

    public Role save(Role role);

    public Role findByRoleIdAndActive(long id, boolean isActive);

    public Role findByRoleIdAndActiveIn(@Param("roleId") long stateId, @Param("active") Set<Boolean> active);

}
