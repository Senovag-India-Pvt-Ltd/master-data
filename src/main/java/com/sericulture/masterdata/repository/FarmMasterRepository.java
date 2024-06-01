package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.FarmMasterDTO;
import com.sericulture.masterdata.model.entity.DisinfectantMaster;
import com.sericulture.masterdata.model.entity.FarmMaster;
import com.sericulture.masterdata.model.entity.ScProgramApprovalMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository

public interface FarmMasterRepository extends PagingAndSortingRepository<FarmMaster, Long> {

    public Page<ScProgramApprovalMapping> findByActiveOrderByFarmIdAsc(boolean isActive, final Pageable pageable);

    public List<FarmMaster> findByActiveAndFarmNameAndFarmNameInKannada(boolean isActive,String farmName, String farmNameInKannada);

    public List<FarmMaster> findByActiveAndFarmNameAndFarmNameInKannadaAndFarmIdIsNot(boolean isActive,String farmName,String farmNameInKannada,long farmId);

    public FarmMaster findByFarmNameAndActive(String farmName,boolean isActive);

    public Page<FarmMaster> findByActiveOrderByFarmNameAsc(boolean isActive, final Pageable pageable);

    public FarmMaster save(FarmMaster farmMaster);

    public FarmMaster findByFarmIdAndActive(long id, boolean isActive);

    public FarmMaster findByFarmIdAndActiveIn(@Param("farmId") long farmId, @Param("active") Set<Boolean> active);

    public List<FarmMaster> findByActiveOrderByFarmNameAsc(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.FarmMasterDTO(" +
            " farmMaster.farmId," +
            " farmMaster.farmName," +
            " farmMaster.farmNameInKannada," +
            " farmMaster.userMasterId," +
            " userMaster.username" +
            ") \n" +
            "from FarmMaster farmMaster\n" +
            "left join UserMaster userMaster\n" +
            "on farmMaster.userMasterId = userMaster.userMasterId " +
            "where farmMaster.active = :isActive " +
            "ORDER BY farmMaster.farmId ASC"
    )
    Page<FarmMasterDTO> getByActiveOrderByFarmMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.FarmMasterDTO(" +
            " farmMaster.farmId," +
            " farmMaster.farmName," +
            " farmMaster.farmNameInKannada," +
            " farmMaster.userMasterId," +
            " userMaster.username" +
            ") \n" +
            "from FarmMaster farmMaster\n" +
            "left join UserMaster userMaster\n" +
            "on farmMaster.userMasterId = userMaster.userMasterId " +
            "where farmMaster.active = :isActive AND farmMaster.farmId = :id "
    )
    public FarmMasterDTO getByFarmMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.FarmMasterDTO(" +
            " farmMaster.farmId," +
            " farmMaster.farmName," +
            " farmMaster.farmNameInKannada," +
            " farmMaster.userMasterId," +
            " userMaster.username" +
            ") \n" +
            "from FarmMaster farmMaster\n" +
            "left join UserMaster userMaster\n" +
            "on farmMaster.userMasterId = userMaster.userMasterId " +
            "where farmMaster.active = :isActive AND " +
            "(:joinColumn = 'farmMaster.farmName' AND farmMaster.farmName LIKE :searchText) OR " +
            "(:joinColumn = 'userMaster.username' AND userMaster.username LIKE :searchText)"
    )
    public Page<FarmMasterDTO> getSortedFarmMaster(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}



