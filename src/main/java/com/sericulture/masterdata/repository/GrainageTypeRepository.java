package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.GrainageTypeDTO;
import com.sericulture.masterdata.model.entity.GrainageType;
import com.sericulture.masterdata.model.entity.GrainageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface GrainageTypeRepository extends PagingAndSortingRepository<GrainageType,Long> {
    public Page<GrainageType> findByActiveOrderByGrainageTypeIdAsc(boolean isActive, final Pageable pageable);

    List<GrainageType> findByGrainageMasterId(long grainageMasterId);

    List<GrainageType> findByGrainageMasterIdAndGrainageTypeIdIsNot( long grainageMasterId, long GrainageTypeId);


    public GrainageType save(GrainageType grainageType);

    public GrainageType findByGrainageTypeIdAndActive(long id, boolean isActive);

    public GrainageType findByGrainageTypeIdAndActiveIn(@Param("grainageTypeId") long grainageTypeId, @Param("active") Set<Boolean> active);

    public List<GrainageType> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.GrainageTypeDTO(" +
            " grainageType.grainageTypeId," +
            " grainageType.grainageMasterId," +
            " grainageType.name," +
            " grainageType.nameInKannada," +
            " grainageMaster.grainageMasterName" +
            ") \n" +
            "from GrainageType grainageType\n" +
            "left join GrainageMaster grainageMaster\n" +
            "on grainageType.grainageMasterId = grainageMaster.grainageMasterId " +
            "where grainageType.active = :isActive " +
            "ORDER BY grainageType.grainageTypeId ASC"
    )
    Page<GrainageTypeDTO> getByActiveOrderByGrainageTypeIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.GrainageTypeDTO(" +
            " grainageType.grainageTypeId," +
            " grainageType.grainageMasterId," +
            " grainageType.name," +
            " grainageType.nameInKannada," +
            " grainageMaster.grainageMasterName" +
            ") \n" +
            "from GrainageType grainageType\n" +
            "left join GrainageMaster grainageMaster\n" +
            "on grainageType.grainageMasterId = grainageMaster.grainageMasterId " +
            "where grainageType.active = :isActive AND grainageType.grainageTypeId = :id "
    )
    public GrainageTypeDTO getByGrainageTypeIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.GrainageTypeDTO(" +
            " grainageType.grainageTypeId," +
            " grainageType.grainageMasterId," +
            " grainageType.name," +
            " grainageType.nameInKannada," +
            " grainageMaster.grainageMasterName" +
            ") \n" +
            "from GrainageType grainageType\n" +
            "left join GrainageMaster grainageMaster\n" +
            "on grainageType.grainageMasterId = grainageMaster.grainageMasterId " +
            "where grainageType.active = :isActive AND " +
            "(:joinColumn = 'grainageMaster.grainageMasterName' AND grainageMaster.grainageMasterName LIKE :searchText) OR " +
            "(:joinColumn = 'grainageType.name' AND grainageType.name LIKE :searchText)"
    )
    public Page<GrainageTypeDTO> getSortedGrainageType(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
