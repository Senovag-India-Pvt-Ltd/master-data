package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.UserHierarchyMapping;
import com.sericulture.masterdata.model.entity.UserHierarchyMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserHierarchyMappingRepository extends PagingAndSortingRepository<UserHierarchyMapping,Long> {
    public Page<UserHierarchyMapping> findByActiveOrderByUserHierarchyMappingIdAsc(boolean isActive, final Pageable pageable);

    public UserHierarchyMapping save(UserHierarchyMapping userHierarchyMapping);

    public UserHierarchyMapping findByUserHierarchyMappingIdAndActive(long userHierarchyMappingId, boolean isActive);

    public UserHierarchyMapping findByUserHierarchyMappingIdAndActiveIn(@Param("userHierarchyMappingId") long userHierarchyMappingIdId, @Param("active") Set<Boolean> active);

    public List<UserHierarchyMapping> findByActive(boolean isActive);

//    public UserHierarchyMapping findByUserMasterIdAndActive(long userMasterId, boolean isActive);


}
