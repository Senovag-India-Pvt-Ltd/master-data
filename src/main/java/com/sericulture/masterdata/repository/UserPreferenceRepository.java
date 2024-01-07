package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.dto.UserPreferenceDTO;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.entity.UserMaster;
import com.sericulture.masterdata.model.entity.UserPreference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserPreferenceRepository extends PagingAndSortingRepository<UserPreference, Long> {

    public Page<UserPreference> findByActiveOrderByUserPreferenceIdAsc(boolean isActive, final Pageable pageable);

    public UserPreference save(UserPreference userPreference);

    public UserPreference findByUserPreferenceIdAndActive(long userPreferenceId, boolean isActive);

    public UserPreference findByUserPreferenceIdAndActiveIn(@Param("userPreferenceId") long userPreferenceIdId, @Param("active") Set<Boolean> active);

    public List<UserPreference> findByActive(boolean isActive);

    public UserPreference findByUserMasterIdAndActive(long userMasterId, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.UserPreferenceDTO(" +
            " userPreference.userPreferenceId," +
            " userPreference.userMasterId," +
            " userPreference.godownId," +
            " userMaster.username," +
            " godown.godownName" +
            ") \n" +
            "from UserPreference userPreference\n" +
            "left join UserMaster userMaster\n" +
            "on userPreference.userMasterId = userMaster.userMasterId " +
            "left join godown_master godown\n" +
            "on userPreference.godownId = godown.godownId " +
            "where userPreference.active = :isActive " +
            "ORDER BY userPreference.userPreferenceId ASC"
    )
    Page<UserPreferenceDTO> getByActiveOrderByUserPreferenceIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.UserPreferenceDTO(" +
            " userPreference.userPreferenceId," +
            " userPreference.userMasterId," +
            " userPreference.godownId," +
            " userMaster.username," +
            " godown.godownName" +
            ") \n" +
            "from UserPreference userPreference\n" +
            "left join UserMaster userMaster\n" +
            "on userPreference.userMasterId = userMaster.userMasterId " +
            "left join godown_master godown\n" +
            "on userPreference.godownId = godown.godownId " +
            "where userPreference.active = :isActive AND userPreference.userPreferenceId = :id"
    )
    public UserPreferenceDTO getByUserPreferenceIdAndActive(long id, boolean isActive);

}
