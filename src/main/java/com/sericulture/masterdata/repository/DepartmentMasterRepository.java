package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.DepartmentMaster;
import com.sericulture.masterdata.model.entity.DocumentMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DepartmentMasterRepository extends PagingAndSortingRepository<DepartmentMaster, Long> {

    public List<DepartmentMaster> findByDepartmentNameAndDepartmentNameInKannada(String departmentName, String departmentNameInKannada);

    public List<DepartmentMaster> findByDepartmentNameAndDepartmentNameInKannadaAndDepartmentIdIsNot(String departmentName,String departmentNameInKannada, long departmentId);

//    public List<Department> findByScApprovalStageIdAndActiveOrderByName(Long scApprovalStageId, boolean isActive);

    public Page<DepartmentMaster> findByActiveOrderByDepartmentNameAsc(boolean isActive, final Pageable pageable);

    public DepartmentMaster save(DepartmentMaster departmentMaster);

    public DepartmentMaster findByDepartmentIdAndActive(long id, boolean isActive);

    public DepartmentMaster findByDepartmentIdAndActiveIn(@Param("departmentId") long departmentId, @Param("active") Set<Boolean> active);

    public List<DepartmentMaster> findByActiveOrderByDepartmentNameAsc(boolean isActive);

    public List<DepartmentMaster> findByActive(boolean isActive);
}
