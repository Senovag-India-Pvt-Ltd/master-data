package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ReelerTypeMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReelerTypeMasterRepository extends PagingAndSortingRepository<ReelerTypeMaster, Long> {
    public List<ReelerTypeMaster> findByReelerTypeMasterName(String reelerTypeMasterName);

    public ReelerTypeMaster findByReelerTypeMasterNameAndActive(String reelerTypeMasterName, boolean isActive);

    public Page<ReelerTypeMaster> findByActiveOrderByReelerTypeMasterNameAsc(boolean isActive, final Pageable pageable);

    public ReelerTypeMaster save(ReelerTypeMaster reelerTypeMaster);

    public ReelerTypeMaster findByReelerTypeMasterIdAndActive(long id, boolean isActive);

    public ReelerTypeMaster findByReelerTypeMasterIdAndActiveIn(@Param("reelerTypeMasterId") long reelerTypeMasterId, @Param("active") Set<Boolean> active);

    public List<ReelerTypeMaster> findByActive(boolean isActive);
}
