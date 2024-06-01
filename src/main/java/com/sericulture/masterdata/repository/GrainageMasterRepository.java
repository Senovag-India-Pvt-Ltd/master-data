package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.GrainageMasterDTO;
import com.sericulture.masterdata.model.entity.GrainageMaster;
import com.sericulture.masterdata.model.entity.GrainageMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GrainageMasterRepository extends PagingAndSortingRepository<GrainageMaster, Long> {

    public List<GrainageMaster> findByGrainageMasterNameAndGrainageMasterNameInKannadaAndActive(String grainageMasterName, String grainageMasterNameInKannada,boolean isActive);

    public List<GrainageMaster> findByActiveAndGrainageMasterNameAndGrainageMasterNameInKannadaAndGrainageMasterIdIsNot(boolean isActive,String grainageMasterName, String grainageMasterNameInKannada, long grainageMasterId);

    public GrainageMaster findByGrainageMasterNameAndActive(String grainageMasterName, boolean isActive);

    public Page<GrainageMaster> findByActiveOrderByGrainageMasterNameAsc(boolean isActive, final Pageable pageable);

    public GrainageMaster save(GrainageMaster grainageMaster);

    public GrainageMaster findByGrainageMasterIdAndActive(long id, boolean isActive);

    public GrainageMaster findByGrainageMasterIdAndActiveIn(@Param("grainageMasterId") long grainageMasterId, @Param("active") Set<Boolean> active);

    public List<GrainageMaster> findByActiveOrderByGrainageMasterNameAsc(boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.GrainageMasterDTO(" +
            " grainageMaster.grainageMasterId," +
            " grainageMaster.grainageMasterName," +
            " grainageMaster.grainageMasterNameInKannada," +
            " grainageMaster.grainageNameRepresentation," +
            " grainageMaster.grainageType," +
            " grainageMaster.userMasterId," +
            " userMaster.username" +
            ") \n" +
            "from GrainageMaster grainageMaster\n" +
            "left join UserMaster userMaster\n" +
            "on grainageMaster.userMasterId = userMaster.userMasterId " +
            "where grainageMaster.active = :isActive " +
            "ORDER BY grainageMaster.grainageMasterId ASC"
    )
    Page<GrainageMasterDTO> getByActiveOrderByGrainageMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.GrainageMasterDTO(" +
            " grainageMaster.grainageMasterId," +
            " grainageMaster.grainageMasterName," +
            " grainageMaster.grainageMasterNameInKannada," +
            " grainageMaster.grainageNameRepresentation," +
            " grainageMaster.grainageType," +
            " grainageMaster.userMasterId," +
            " userMaster.username" +
            ") \n" +
            "from GrainageMaster grainageMaster\n" +
            "left join UserMaster userMaster\n" +
            "on grainageMaster.userMasterId = userMaster.userMasterId " +
            "where grainageMaster.active = :isActive AND grainageMaster.grainageMasterId = :id "
    )
    public GrainageMasterDTO getByGrainageMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.GrainageMasterDTO(" +
            " grainageMaster.grainageMasterId," +
            " grainageMaster.grainageMasterName," +
            " grainageMaster.grainageMasterNameInKannada," +
            " grainageMaster.grainageNameRepresentation," +
            " grainageMaster.grainageType," +
            " grainageMaster.userMasterId," +
            " userMaster.username" +
            ") \n" +
            "from GrainageMaster grainageMaster\n" +
            "left join UserMaster userMaster\n" +
            "on grainageMaster.userMasterId = userMaster.userMasterId " +
            "where grainageMaster.active = :isActive AND " +
            "(:joinColumn = 'grainageMaster.grainageMasterName' AND grainageMaster.grainageMasterName LIKE :searchText) OR " +
            "(:joinColumn = 'userMaster.username' AND userMaster.username LIKE :searchText)"
    )
    public Page<GrainageMasterDTO> getSortedGrainageMaster(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}