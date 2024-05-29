package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface VillageRepository extends PagingAndSortingRepository<Village, Long> {
    public List<Village> findByVillageNameAndVillageNameInKannada(String villageName,String villageNameInKannada);

    public List<Village> findByActiveAndVillageNameAndVillageNameInKannadaAndVillageIdIsNot(boolean a,String villageName,String villageNameInKannada,long villageId);

    public List<Village> findByVillageNameAndStateId(String villageName, long stateId);

    public Village findByVillageNameAndActive(String villageName,boolean isActive);

    public Village findByLgVillage(String lgVillage);

    @Query("select new com.sericulture.masterdata.model.dto.VillageDTO(" +
            " village.villageId," +
            " village.villageName," +
            " village.villageNameInKannada," +
            " village.stateId," +
            " village.districtId," +
            " village.talukId," +
            " village.hobliId," +
            " village.lgVillage," +
            " village.villageCode," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName" +
            ") \n" +
            "from Village village\n" +
            "left join State state\n" +
            "on village.stateId = state.stateId " +
            "left join District district\n" +
            "on village.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on village.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on village.hobliId = hobli.hobliId " +
            "where village.active = :isActive " +
            "ORDER BY village.villageName ASC"
    )
    Page<VillageDTO> getByActiveOrderByVillageIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.VillageDTO(" +
            " village.villageId," +
            " village.villageName," +
            " village.villageNameInKannada," +
            " village.stateId," +
            " village.districtId," +
            " village.talukId," +
            " village.hobliId," +
            " village.lgVillage," +
            " village.villageCode," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName" +
            ") \n" +
            "from Village village\n" +
            "left join State state\n" +
            "on village.stateId = state.stateId " +
            "left join District district\n" +
            "on village.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on village.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on village.hobliId = hobli.hobliId " +
            "where village.active = :isActive AND village.villageId = :id "
    )
    public VillageDTO getByVillageIdAndActive(long id, boolean isActive);


    public Page<Village> findByActiveOrderByVillageIdAsc(boolean isActive, final Pageable pageable);

    public Village save(Village village);

    public Village findByVillageIdAndActive(long id, boolean isActive);

    public List<Village> findByHobliIdAndActive(long stateId, boolean isActive);

    public Village findByVillageIdAndActiveIn(@Param("villageId") long villageId, @Param("active") Set<Boolean> active);

    public List<Village> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.VillageDTO(" +
            " village.villageId," +
            " village.villageName," +
            " village.villageNameInKannada," +
            " village.stateId," +
            " village.districtId," +
            " village.talukId," +
            " village.hobliId," +
            " village.lgVillage," +
            " village.villageCode," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName" +
            ") \n" +
            "from Village village\n" +
            "left join State state\n" +
            "on village.stateId = state.stateId " +
            "left join District district\n" +
            "on village.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on village.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on village.hobliId = hobli.hobliId " +
            "where village.active = :isActive AND " +
            "(:joinColumn = 'village.villageName' AND village.villageName LIKE :searchText) OR " +
            "(:joinColumn = 'state.stateName' AND state.stateName LIKE :searchText) OR " +
            "(:joinColumn = 'district.districtName' AND district.districtName LIKE :searchText) OR " +
            "(:joinColumn = 'taluk.talukName' AND taluk.talukName LIKE :searchText) OR " +
            "(:joinColumn = 'hobli.hobliName' AND hobli.hobliName LIKE :searchText)"
    )
    public Page<VillageDTO> getSortedVillages(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}