package com.sericulture.masterdata.repository;


import com.sericulture.masterdata.model.dto.RendittaMasterDTO;
import com.sericulture.masterdata.model.entity.RendittaMaster;
import com.sericulture.masterdata.model.entity.RendittaMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RendittaMasterRepository extends PagingAndSortingRepository<RendittaMaster, Long> {
    public Page<RendittaMaster> findByActiveOrderByRendittaMasterIdAsc(boolean isActive, final Pageable pageable);

    List<RendittaMaster> findByRaceMasterId(long raceMasterId);

    List<RendittaMaster> findByRaceMasterIdAndRendittaMasterIdIsNot( long raceMasterId, long rendittaMasterId);


    public RendittaMaster save(RendittaMaster rendittaMaster);

    public RendittaMaster findByRendittaMasterIdAndActive(long id, boolean isActive);

    public RendittaMaster findByRendittaMasterIdAndActiveIn(@Param("rendittaMasterId") long rendittaMasterId, @Param("active") Set<Boolean> active);

    public List<RendittaMaster> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.RendittaMasterDTO(" +
            " rendittaMaster.rendittaMasterId," +
            " rendittaMaster.raceMasterId," +
            " rendittaMaster.value," +
            " raceMaster.raceMasterName" +
            ") \n" +
            "from RendittaMaster rendittaMaster\n" +
            "left join RaceMaster raceMaster\n" +
            "on rendittaMaster.raceMasterId = raceMaster.raceMasterId " +
            "where rendittaMaster.active = :isActive " +
            "ORDER BY rendittaMaster.rendittaMasterId ASC"
    )
    Page<RendittaMasterDTO> getByActiveOrderByRendittaMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.RendittaMasterDTO(" +
            " rendittaMaster.rendittaMasterId," +
            " rendittaMaster.raceMasterId," +
            " rendittaMaster.value," +
            " raceMaster.raceMasterName" +
            ") \n" +
            "from RendittaMaster rendittaMaster\n" +
            "left join RaceMaster raceMaster\n" +
            "on rendittaMaster.raceMasterId = raceMaster.raceMasterId " +
            "where rendittaMaster.active = :isActive AND rendittaMaster.rendittaMasterId = :id "
    )
    public RendittaMasterDTO getByRendittaMasterIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.RendittaMasterDTO(" +
            " rendittaMaster.rendittaMasterId," +
            " rendittaMaster.raceMasterId," +
            " rendittaMaster.value," +
            " raceMaster.raceMasterName" +
            ") \n" +
            "from RendittaMaster rendittaMaster\n" +
            "left join RaceMaster raceMaster\n" +
            "on rendittaMaster.raceMasterId = raceMaster.raceMasterId " +
            "where rendittaMaster.active = :isActive AND " +
            "(:joinColumn = 'raceMaster.raceMasterName' AND raceMaster.raceMasterName LIKE :searchText)"
    )
    public Page<RendittaMasterDTO> getSortedRendittaMaster(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
