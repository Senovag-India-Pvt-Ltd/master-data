package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.FarmTypeDTO;
import com.sericulture.masterdata.model.entity.FarmType;
import com.sericulture.masterdata.model.entity.FarmType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface FarmTypeRepository extends PagingAndSortingRepository<FarmType,Long> {
    public Page<FarmType> findByActiveOrderByFarmTypeIdAsc(boolean isActive, final Pageable pageable);

    List<FarmType> findByFarmId(long farmId);

    List<FarmType> findByFarmIdAndFarmTypeIdIsNot( long farmId, long farmTypeId);


    public FarmType save(FarmType farmType);

    public FarmType findByFarmTypeIdAndActive(long id, boolean isActive);

    public FarmType findByFarmTypeIdAndActiveIn(@Param("farmTypeId") long farmTypeId, @Param("active") Set<Boolean> active);

    public List<FarmType> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.FarmTypeDTO(" +
            " farmType.farmTypeId," +
            " farmType.farmId," +
            " farmType.name," +
            " farmType.nameInKannada," +
            " farmMaster.farmName" +
            ") \n" +
            "from FarmType farmType\n" +
            "left join FarmMaster farmMaster\n" +
            "on farmType.farmId = farmMaster.farmId " +
            "where farmType.active = :isActive " +
            "ORDER BY farmType.farmTypeId ASC"
    )
    Page<FarmTypeDTO> getByActiveOrderByFarmTypeIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.FarmTypeDTO(" +
            " farmType.farmTypeId," +
            " farmType.farmId," +
            " farmType.name," +
            " farmType.nameInKannada," +
            " farmMaster.farmName" +
            ") \n" +
            "from FarmType farmType\n" +
            "left join FarmMaster farmMaster\n" +
            "on farmType.farmId = farmMaster.farmId " +
            "where farmType.active = :isActive AND farmType.farmTypeId = :id "
    )
    public FarmTypeDTO getByFarmTypeIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.FarmTypeDTO(" +
            " farmType.farmTypeId," +
            " farmType.farmId," +
            " farmType.name," +
            " farmType.nameInKannada," +
            " farmMaster.farmName" +
            ") \n" +
            "from FarmType farmType\n" +
            "left join FarmMaster farmMaster\n" +
            "on farmType.farmId = farmMaster.farmId " +
            "where farmType.active = :isActive AND " +
            "(:joinColumn = 'farmType.name' AND farmType.name LIKE :searchText) OR " +
            "(:joinColumn = 'farmMaster.farmName' AND farmMaster.farmName LIKE :searchText)"
    )
    public Page<FarmTypeDTO> getSortedFarmType(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
