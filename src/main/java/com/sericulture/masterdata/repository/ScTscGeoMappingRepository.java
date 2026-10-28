package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScTscGeoMappingDTO;
import com.sericulture.masterdata.model.entity.ScTscGeoMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScTscGeoMappingRepository extends PagingAndSortingRepository<ScTscGeoMapping, Long> {

    public ScTscGeoMapping save(ScTscGeoMapping scTscGeoMapping);

    public ScTscGeoMapping findByScTscGeoMappingIdAndActive(long id, boolean isActive);

    public ScTscGeoMapping findByScTscGeoMappingIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);

    public List<ScTscGeoMapping> findByActive(boolean isActive);

    public List<ScTscGeoMapping> findByDistrictIdAndTalukIdAndHobliIdAndVillageIdAndActive(
            Long districtId, Long talukId, Long hobliId, Long villageId, boolean isActive);

    public List<ScTscGeoMapping> findByDistrictIdAndTalukIdAndHobliIdAndVillageIdAndActiveAndScTscGeoMappingIdIsNot(
            Long districtId, Long talukId, Long hobliId, Long villageId, boolean isActive, Long scTscGeoMappingId);

    // All active rows for a taluk (village-specific, hobli-specific and taluk-level fallback rows
    // all come back together) -- the caller picks the most specific match. Used by the Phase 3
    // auto-assignment lookup so this table doesn't need touching again for that.
    public List<ScTscGeoMapping> findByDistrictIdAndTalukIdAndActive(Long districtId, Long talukId, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScTscGeoMappingDTO(" +
            " m.scTscGeoMappingId," +
            " m.districtId, district.districtName," +
            " m.talukId, taluk.talukName," +
            " m.hobliId, hobli.hobliName," +
            " m.villageId, village.villageName," +
            " m.tscMasterId, tsc.name," +
            " m.officerUserId, officer.firstName" +
            ") " +
            "from ScTscGeoMapping m " +
            "left join District district on m.districtId = district.districtId " +
            "left join Taluk taluk on m.talukId = taluk.talukId " +
            "left join Hobli hobli on m.hobliId = hobli.hobliId " +
            "left join Village village on m.villageId = village.villageId " +
            "left join TscMaster tsc on m.tscMasterId = tsc.tscMasterId " +
            "left join UserMaster officer on m.officerUserId = officer.userMasterId " +
            "where m.active = :isActive " +
            "order by district.districtName asc, taluk.talukName asc"
    )
    Page<ScTscGeoMappingDTO> getByActiveOrderByDistrictNameAsc(@Param("isActive") boolean isActive, Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScTscGeoMappingDTO(" +
            " m.scTscGeoMappingId," +
            " m.districtId, district.districtName," +
            " m.talukId, taluk.talukName," +
            " m.hobliId, hobli.hobliName," +
            " m.villageId, village.villageName," +
            " m.tscMasterId, tsc.name," +
            " m.officerUserId, officer.firstName" +
            ") " +
            "from ScTscGeoMapping m " +
            "left join District district on m.districtId = district.districtId " +
            "left join Taluk taluk on m.talukId = taluk.talukId " +
            "left join Hobli hobli on m.hobliId = hobli.hobliId " +
            "left join Village village on m.villageId = village.villageId " +
            "left join TscMaster tsc on m.tscMasterId = tsc.tscMasterId " +
            "left join UserMaster officer on m.officerUserId = officer.userMasterId " +
            "where m.active = :isActive and m.scTscGeoMappingId = :id"
    )
    ScTscGeoMappingDTO getByIdAndActive(@Param("id") long id, @Param("isActive") boolean isActive);
}
