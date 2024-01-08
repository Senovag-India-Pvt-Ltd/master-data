package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.WorkingInstitution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WorkingInstitutionRepository extends PagingAndSortingRepository<WorkingInstitution,Long> {
    public List<WorkingInstitution> findByWorkingInstitutionName(String workingInstitutionName);

    public WorkingInstitution findByWorkingInstitutionNameAndActive(String workingInstitutionName,boolean isActive);

    public Page<WorkingInstitution> findByActiveOrderByWorkingInstitutionIdAsc(boolean isActive, final Pageable pageable);

    public WorkingInstitution save(WorkingInstitution workingInstitution);

    public WorkingInstitution findByWorkingInstitutionIdAndActive(long id, boolean isActive);

    public WorkingInstitution findByWorkingInstitutionIdAndActiveIn(@Param("workingInstitutionId") long workingInstitutionId, @Param("active") Set<Boolean> active);

    public List<WorkingInstitution> findByActive(boolean isActive);
}
