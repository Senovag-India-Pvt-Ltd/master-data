package com.sericulture.masterdata.repository;

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
            " userMaster.designationId" +
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
            "where userMaster.active = :isActive " +
            "ORDER BY userMaster.userMasterId ASC"
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
            " userMaster.designationId" +
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
            "where userMaster.active = :isActive AND userMaster.userMasterId = :id"
    )
    public UserMasterDTO getByUserMasterIdAndActive(long id, boolean isActive);
}