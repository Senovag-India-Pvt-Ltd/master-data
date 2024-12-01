package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.helper.Util;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.entity.UserMaster;
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

    public List<UserMaster> findByActiveAndUserTypeIdAndMarketMasterId(boolean isActive, long userTypeId, long marketMasterId);

    public List<UserMaster> findByTscMasterIdAndActive(long tscMasterId, boolean isActive);


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
            " userMaster.tscMasterId," +
            " tscMaster.name,"+
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
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
            "left join TscMaster tscMaster " +
            "on userMaster.tscMasterId = tscMaster.tscMasterId " +
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
            " userMaster.tscMasterId," +
            " tscMaster.name,"+
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
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
            "left join TscMaster tscMaster " +
            "on userMaster.tscMasterId = tscMaster.tscMasterId " +
            "where userMaster.active = :isActive AND userMaster.userMasterId = :id"
    )
    public UserMasterDTO getByUserMasterIdAndActive(long id, boolean isActive);


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
            " userMaster.tscMasterId," +
            " tscMaster.name,"+
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
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
            "left join TscMaster tscMaster " +
            "on userMaster.tscMasterId = tscMaster.tscMasterId " +
            "where userMaster.active = :isActive AND role.roleName = :roleName"
    )
    List<UserMasterDTO> getByActiveAndRoleName(@Param("isActive") boolean isActive,@Param("roleName")String roleName);

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
            " userMaster.tscMasterId," +
            " tscMaster.name,"+
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
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
            "left join TscMaster tscMaster " +
            "on userMaster.tscMasterId = tscMaster.tscMasterId " +
            "where userMaster.active = :isActive AND " +
            "(:joinColumn = 'userMaster.username' AND userMaster.username LIKE :searchText) OR " +
            "(:joinColumn = 'userMaster.phoneNumber' AND userMaster.phoneNumber LIKE :searchText)"
    )
    public Page<UserMasterDTO> getSortedUsers(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

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
            " userMaster.tscMasterId," +
            " tscMaster.name,"+
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
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
            "left join TscMaster tscMaster " +
            "on userMaster.tscMasterId = tscMaster.tscMasterId " +
            "where userMaster.active = :isActive " +
            "and userMaster.roleId = :roleId " +
            "and userMaster.talukId = :talukId")
    public List <UserMasterDTO> getByRoleIdAndTalukIdAndActive(@Param("roleId") long roleId, @Param("talukId") long talukId, @Param("isActive") boolean isActive);

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
            " userMaster.tscMasterId," +
            " tscMaster.name,"+
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
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
            "left join TscMaster tscMaster " +
            "on userMaster.tscMasterId = tscMaster.tscMasterId " +
            "where userMaster.active = :isActive " +
            "and userMaster.designationId = :designationId " +
            "and userMaster.districtId = :districtId")
    public List <UserMasterDTO> getByDesignationIdAndDistrictIdAndActive(@Param("designationId") long designationId, @Param("districtId") long districtId, @Param("isActive") boolean isActive);

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
            " userMaster.tscMasterId," +
            " tscMaster.name,"+
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
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
            "left join TscMaster tscMaster " +
            "on userMaster.tscMasterId = tscMaster.tscMasterId " +
            "where userMaster.active = :isActive " +
            "and userMaster.designationId = :designationId " +
            "and userMaster.talukId = :talukId " +
            "and userMaster.districtId = :districtId")
    public List <UserMasterDTO> getByDesignationIdAndDistrictIdAndActive(@Param("designationId") long designationId, @Param("districtId") long districtId, @Param("talukId") long talukId, @Param("isActive") boolean isActive);


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
            " userMaster.tscMasterId," +
            " tscMaster.name,"+
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
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
            "left join TscMaster tscMaster " +
            "on userMaster.tscMasterId = tscMaster.tscMasterId " +
            "where userMaster.active = :isActive " +
            "and userMaster.designationId = :designationId " +
            "and userMaster.talukId = :talukId " +
            "and userMaster.districtId = :districtId " +
            "and userMaster.workingInstitutionId = :workingInstitutionId ")
    public List <UserMasterDTO> getByDesignationIdAndDistrictIdAndWorkingInstitutionIdAndActive(@Param("designationId") long designationId, @Param("districtId") long districtId, @Param("talukId") long talukId,  @Param("workingInstitutionId") long workingInstitutionId, @Param("isActive") boolean isActive);

    @Query(nativeQuery = true, value = """
            SELECT
                user_master_id ,
                first_name,
                last_name,
                username
            FROM
                user_master um
            WHERE
                manager_id is NULL
            AND um.active = 1;
            """)
    public List<Object[]> getUserManagerDetails();


    @Query(nativeQuery = true, value = """
            SELECT
              user_master_id ,
              manager_id ,
              first_name,
              last_name,
              username,
              um.phone_number,
              d.district_name,
              ds.name AS designation_name
          FROM
              user_master um
           LEFT JOIN
              district d ON um.district_id = d.district_id
          LEFT JOIN
              designation ds ON um.designation_id = ds.designation_id
          WHERE
              manager_id = :managerId
            AND um.active = 1;
          """)
    public List<Object[]> getDirectReporteeDetails(Long managerId);

    @Query(nativeQuery = true, value = """
              WITH user_hierarchy AS (
              SELECT
                  um.user_master_id,
                  um.manager_id,
                  um.first_name,
                  um.last_name,
                  um.username,
                  um.phone_number,
                  d.district_name,
                  ds.name AS designation_name,
                  1 AS level
              FROM
                  user_master um
              LEFT JOIN
                  district d ON um.district_id = d.district_id
              LEFT JOIN
                  designation ds ON um.designation_id = ds.designation_id
              WHERE
                  um.manager_id = :managerId

              UNION ALL
              SELECT
                  e.user_master_id,
                  e.manager_id,
                  e.first_name,
                  e.last_name,
                  e.username,
                  e.phone_number,
                  NULL AS district_name,
                  NULL AS designation_name,
                  uh.level + 1 AS level
              FROM
                  user_master e
              INNER JOIN
                  user_hierarchy uh ON e.manager_id = uh.user_master_id
          )
          SELECT
              uh.user_master_id,
              uh.manager_id,
              uh.first_name,
              uh.last_name,
              uh.username,
              uh.phone_number,
              COALESCE(d.district_name, '') AS district_name,
              COALESCE(ds.name, '') AS designation_name,
              uh.level
          FROM
              user_hierarchy uh
          LEFT JOIN
              district d ON uh.user_master_id IN (SELECT user_master_id FROM user_master WHERE district_id = d.district_id)
          LEFT JOIN
              designation ds ON uh.user_master_id IN (SELECT user_master_id FROM user_master WHERE designation_id = ds.designation_id)
          ORDER BY
              uh.level, uh.user_master_id;
          """)
    public List<Object[]> getAllReporteeDetails(Long managerId);

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
            " userMaster.tscMasterId," +
            " tscMaster.name," +
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
            " userMaster.ddoCode," +
            " workingInstitution.workingInstitutionName," +
            " workingInstitution.workingInstitutionName" +
            ") " +
            "from UserMaster userMaster " +
            "left join State state on userMaster.stateId = state.stateId " +
            "left join District district on userMaster.districtId = district.districtId " +
            "left join Taluk taluk on userMaster.talukId = taluk.talukId " +
            "left join role_master role on userMaster.roleId = role.roleId " +
            "left join market_master marketMaster on userMaster.marketMasterId = marketMaster.marketMasterId " +
            "left join Designation designation on userMaster.designationId = designation.designationId " +
            "left join WorkingInstitution workingInstitution on userMaster.workingInstitutionId = workingInstitution.workingInstitutionId " +
            "left join TscMaster tscMaster on userMaster.tscMasterId = tscMaster.tscMasterId " +
            "where userMaster.active = :isActive " +
            "and (:designationId IS NULL OR userMaster.designationId = :designationId) " +
            "and (:districtId IS NULL OR userMaster.districtId = :districtId) " +
            "and (:talukId IS NULL OR userMaster.talukId = :talukId) " +
            "and (:mobileNumber IS NULL OR userMaster.phoneNumber = :mobileNumber) " +
            "and (:username IS NULL OR userMaster.username = :username)")
    public List<UserMasterDTO> getByDesignationIdDistrictIdTalukIdMobileNumberAndUsername(
            @Param("designationId") Long designationId,
            @Param("districtId") Long districtId,
            @Param("talukId") Long talukId,
            @Param("mobileNumber") String mobileNumber,
            @Param("username") String username,
            @Param("isActive") boolean isActive
    );


}
