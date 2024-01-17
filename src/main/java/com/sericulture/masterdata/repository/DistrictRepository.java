package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.DistrictDTO;
import com.sericulture.masterdata.model.entity.BinCounterMaster;
import com.sericulture.masterdata.model.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DistrictRepository extends PagingAndSortingRepository<District, Long> {
    public List<District> findByDistrictNameAndDistrictNameInKannada(String districtName,String districtNameInKannada);

    public List<District> findByDistrictName(String districtName);

    public List<District> findByDistrictNameAndStateId(String districtName, long stateId);

    public District findByDistrictNameAndActive(String districtName,boolean isActive);

    public Page<District> findByActiveOrderByDistrictIdAsc(boolean isActive, final Pageable pageable);

   @Query("select new com.sericulture.masterdata.model.dto.DistrictDTO(" +
           " district.districtId," +
           " district.districtName," +
           " district.districtNameInKannada," +
           " district.stateId," +
           " state.stateName" +
           ") \n" +
           "from District district\n" +
           "left join State state\n" +
           "on district.stateId = state.stateId " +
           "where district.active = :isActive " +
           "ORDER BY district.districtName ASC"
   )
   Page<DistrictDTO> getByActiveOrderByDistrictIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    public District save(District district);

    public District findByDistrictIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.DistrictDTO(" +
            " district.districtId," +
            " district.districtName," +
            " district.districtNameInKannada," +
            " district.stateId," +
            " state.stateName" +
            ") \n" +
            "from District district\n" +
            "left join State state\n" +
            "on district.stateId = state.stateId " +
            "where district.active = :isActive AND district.districtId = :id"
    )
    public DistrictDTO getByDistrictIdAndActive(long id, boolean isActive);

    public List<District> findByStateIdAndActiveOrderByDistrictName(long stateId, boolean isActive);

    public District findByDistrictIdAndActiveIn(@Param("districtId") long districtId, @Param("active") Set<Boolean> active);

    public List<District> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.DistrictDTO(" +
            " district.districtId," +
            " district.districtName," +
            " district.districtNameInKannada," +
            " district.stateId," +
            " state.stateName" +
            ") \n" +
            "from District district\n" +
            "left join State state\n" +
            "on district.stateId = state.stateId " +
            "where district.active = :isActive AND district.districtName LIKE :searchText"
    )
    public List<DistrictDTO> searchByDistrictNameAndActive(String searchText, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.DistrictDTO(" +
            " district.districtId," +
            " district.districtName," +
            " district.districtNameInKannada," +
            " district.stateId," +
            " state.stateName" +
            ") \n" +
            "from District district\n" +
            "left join State state\n" +
            "on district.stateId = state.stateId " +
            "where district.active = :isActive AND " +
            "(:joinColumn = 'district.districtName' AND district.districtName LIKE :searchText) OR " +
            "(:joinColumn = 'state.stateName' AND state.stateName LIKE :searchText)"
    )
    public Page<DistrictDTO> getSortedDistricts(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}