package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Designation;
import com.sericulture.masterdata.model.entity.ScHeadAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DesignationRepository extends PagingAndSortingRepository<Designation, Long> {
    public List<Designation> findByNameAndDesignationNameInKannada(String name,String designationNameInKannada);

    public List<Designation> findByNameAndDesignationNameInKannadaAndDesignationIdIsNot(String name,String designationNameInKannada, long designationId);

//    public List<Designation> findByScApprovalStageIdAndActiveOrderByName(Long scApprovalStageId, boolean isActive);

    public Page<Designation> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public Designation save(Designation designation);

    public Designation findByDesignationIdAndActive(long id, boolean isActive);

    public Designation findByDesignationIdAndActiveIn(@Param("designationId") long designationId, @Param("active") Set<Boolean> active);

    public List<Designation> findByActiveOrderByNameAsc(boolean isActive);
}

