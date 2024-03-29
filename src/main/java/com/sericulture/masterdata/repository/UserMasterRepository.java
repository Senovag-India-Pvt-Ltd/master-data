package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.entity.UserMaster;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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

    UserMaster findByUsername(String username);

    public List<UserMaster> findByActiveAndUserTypeId(boolean isActive, long userTypeId);

    public List<UserMaster> findByActiveAndRoleId(boolean isActive, long roleId);

    public UserMaster findByUserMasterIdAndActiveIn(@Param("userMasterId") long userMasterId, @Param("active") Set<Boolean> active);

    public List<UserMaster> findByActive(boolean isActive);

    public UserMaster findByUsernameAndPasswordAndActive(String username, String password, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.UserMasterDTO(" +
            " userMaster.userMasterId," +
            " userMaster.firstName," +
            " userMaster.middleName," +
            " userMaster.lastName," +
            " userMaster.password," +
            " userMaster.emailID," +
            " userMaster.stateId," +
            " userMaster.districtId," +
            " userMaster.talukId," +
            " userMaster.roleId," +
            " userMaster.marketMasterId," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " role.roleName," +
            " marketMaster.marketMasterName," +
            " userMaster.username," +
            " userMaster.designationId," +
            " designation.name," +
            " userMaster.phoneNumber," +
            " userMaster.userType," +
            " userMaster.userTypeId," +
            " userMaster.deviceId," +
            " userMaster.workingInstitutionId," +
            " workingInstitution.workingInstitutionName" +
            ") \n" +
            "from UserMaster userMaster\n" +
            "left join State state\n" +
            "on userMaster.stateId = state.stateId " +
            "left join District district\n" +
            "on userMaster.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on userMaster.talukId = taluk.talukId " +
            "left join role_master role\n" +
            "on userMaster.roleId = role.roleId " +
            "left join market_master marketMaster\n" +
            "on userMaster.marketMasterId = marketMaster.marketMasterId " +
            "left join Designation designation\n" +
            "on userMaster.designationId = designation.designationId " +
            "left join WorkingInstitution workingInstitution\n" +
            "on userMaster.workingInstitutionId = workingInstitution.workingInstitutionId " +
            "where userMaster.active = :isActive " +
            "ORDER BY userMaster.username ASC"
    )
    Page<UserMasterDTO> getByActiveOrderByUserMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.UserMasterDTO(" +
            " userMaster.userMasterId," +
            " userMaster.firstName," +
            " userMaster.middleName," +
            " userMaster.lastName," +
            " userMaster.password," +
            " userMaster.emailID," +
            " userMaster.stateId," +
            " userMaster.districtId," +
            " userMaster.talukId," +
            " userMaster.roleId," +
            " userMaster.marketMasterId," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " role.roleName," +
            " marketMaster.marketMasterName," +
            " userMaster.username," +
            " userMaster.designationId," +
            " designation.name," +
            " userMaster.phoneNumber," +
            " userMaster.userType," +
            " userMaster.userTypeId," +
            " userMaster.deviceId," +
            " userMaster.workingInstitutionId," +
            " workingInstitution.workingInstitutionName" +
            ") \n" +
            "from UserMaster userMaster\n" +
            "left join State state\n" +
            "on userMaster.stateId = state.stateId " +
            "left join District district\n" +
            "on userMaster.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on userMaster.talukId = taluk.talukId " +
            "left join role_master role\n" +
            "on userMaster.roleId = role.roleId " +
            "left join market_master marketMaster\n" +
            "on userMaster.marketMasterId = marketMaster.marketMasterId " +
            "left join Designation designation\n" +
            "on userMaster.designationId = designation.designationId " +
            "left join WorkingInstitution workingInstitution\n" +
            "on userMaster.workingInstitutionId = workingInstitution.workingInstitutionId " +
            "where userMaster.active = :isActive AND userMaster.userMasterId = :id"
    )
    public UserMasterDTO getByUserMasterIdAndActive(long id, boolean isActive);

    public UserMaster findByUsernameAndActive(String userName, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.UserMasterDTO(" +
            " userMaster.userMasterId," +
            " userMaster.firstName," +
            " userMaster.middleName," +
            " userMaster.lastName," +
            " userMaster.password," +
            " userMaster.emailID," +
            " userMaster.stateId," +
            " userMaster.districtId," +
            " userMaster.talukId," +
            " userMaster.roleId," +
            " userMaster.marketMasterId," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " role.roleName," +
            " marketMaster.marketMasterName," +
            " userMaster.username," +
            " userMaster.designationId," +
            " designation.name," +
            " userMaster.phoneNumber," +
            " userMaster.userType," +
            " userMaster.userTypeId," +
            " userMaster.deviceId," +
            " userMaster.workingInstitutionId," +
            " workingInstitution.workingInstitutionName" +
            ") \n" +
            "from UserMaster userMaster\n" +
            "left join State state\n" +
            "on userMaster.stateId = state.stateId " +
            "left join District district\n" +
            "on userMaster.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on userMaster.talukId = taluk.talukId " +
            "left join role_master role\n" +
            "on userMaster.roleId = role.roleId " +
            "left join market_master marketMaster\n" +
            "on userMaster.marketMasterId = marketMaster.marketMasterId " +
            "left join Designation designation\n" +
            "on userMaster.designationId = designation.designationId " +
            "left join WorkingInstitution workingInstitution\n" +
            "on userMaster.workingInstitutionId = workingInstitution.workingInstitutionId " +
            "where userMaster.active = :isActive AND " +
            "(:joinColumn = 'userMaster.username' AND userMaster.username LIKE :searchText) OR " +
            "(:joinColumn = 'userMaster.phoneNumber' AND userMaster.phoneNumber LIKE :searchText)"
    )
    public Page<UserMasterDTO> getSortedUsers(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
