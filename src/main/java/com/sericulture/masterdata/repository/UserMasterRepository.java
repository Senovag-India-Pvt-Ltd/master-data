package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.BinMaster;
import com.sericulture.masterdata.model.entity.RpRolePermission;
import com.sericulture.masterdata.model.entity.UserMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserMasterRepository extends PagingAndSortingRepository<UserMaster,Long> {

    public Page<UserMaster> findByActiveOrderByUserMasterIdAsc(boolean isActive, final Pageable pageable);

    public UserMaster save(UserMaster userMaster);

    public UserMaster findByUserMasterIdAndActive(long userMasterId, boolean isActive);

    public UserMaster findByUserMasterIdAndActiveIn(@Param("userMasterId") long userMasterId, @Param("active") Set<Boolean> active);

    public List<UserMaster> findByActive(boolean isActive);
}
