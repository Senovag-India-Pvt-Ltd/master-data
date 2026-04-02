package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.UserHierarchyMapping;
import com.sericulture.masterdata.model.entity.UserHierarchyMapping;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
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

    public UserHierarchyMapping findByReporteeUserMasterIdAndActive(long reporteeUserMasterId, boolean isActive);

    public UserHierarchyMapping findByUserHierarchyMappingIdAndActiveIn(@Param("userHierarchyMappingId") long userHierarchyMappingIdId, @Param("active") Set<Boolean> active);

    public List<UserHierarchyMapping> findByActive(boolean isActive);

    List<UserHierarchyMapping> findByReportToUserMasterIdAndActive(Long managerId, boolean active);

    List<UserHierarchyMapping> findByReportToUserMasterIdIsNullAndActive(boolean active);
//    public UserHierarchyMapping findByUserMasterIdAndActive(long userMasterId, boolean isActive);
@Query(value = """
    SELECT * FROM user_master um
    WHERE (:designationId IS NULL OR um.designation_id = :designationId)
      AND (:districtId IS NULL OR um.district_id = :districtId)
      AND (:userId IS NULL OR um.user_master_id = :userId)
""", nativeQuery = true)
List<Object[]> findUsersWithFilters(
        @Param("designationId") Long designationId,
        @Param("districtId") Long districtId,
        @Param("userId") Long userId
);

    @Query(value = """
    UPDATE user_hierarchy_mapping
    SET report_to_user_master_id = :newManagerId
    WHERE user_hierarchy_mapping_id = :employeeId
""", nativeQuery = true)
    void updateManager(
            @Param("employeeId") Long employeeId,
            @Param("newManagerId") Long newManagerId
    );

    @Query(value = """
    SELECT
        uhm.user_hierarchy_mapping_id AS userHierarchyMappingId,
        emp.user_master_id AS employeeId,
        emp.userName AS employeeName,
        emp.designation_id AS employeeDesignationId,
        emp.district_id AS employeeDistrictId,
        mgr.user_master_id AS managerId,
        mgr.userName AS managerName,
        mgr.designation_id AS managerDesignationId,
        mgr.district_id AS managerDistrictId
    FROM user_hierarchy_mapping uhm
    JOIN user_master emp
        ON emp.user_master_id = uhm.reportee_user_master_id
    JOIN user_master mgr
        ON mgr.user_master_id = uhm.report_to_user_master_id
    WHERE uhm.active = 1
""", nativeQuery = true)
    List<Object[]> getEmployeeManagerList();


    @Query(value = """
    SELECT emp.user_master_id AS employeeId,
           emp.userName AS employeeName,
           mgr.user_master_id AS managerId,
           mgr.userName AS managerName
    FROM user_hierarchy_mapping uhm
    JOIN user_master emp ON emp.user_master_id = uhm.reportee_user_master_id
    JOIN user_master mgr ON mgr.user_master_id = uhm.report_to_user_master_id
    WHERE uhm.active = 1
""", nativeQuery = true)
    List<Object[]> getCompletedList();

    @Query(value = """
    SELECT emp.user_master_id AS employeeId,
           emp.userName AS employeeName
    FROM user_master emp
    LEFT JOIN user_hierarchy_mapping uhm
           ON emp.user_master_id = uhm.reportee_user_master_id
           AND uhm.active = 1
    WHERE uhm.reportee_user_master_id IS NULL
""", nativeQuery = true)
    List<Object[]> getPendingList();


}
