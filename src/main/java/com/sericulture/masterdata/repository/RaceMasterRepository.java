package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.DistrictDTO;
import com.sericulture.masterdata.model.dto.GodownDTO;
import com.sericulture.masterdata.model.dto.RaceMasterDTO;
import com.sericulture.masterdata.model.entity.Godown;
import com.sericulture.masterdata.model.entity.RaceMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RaceMasterRepository extends PagingAndSortingRepository<RaceMaster, Long>{

    public List<RaceMaster> findByRaceMasterName(String raceMasterName);

    public RaceMaster findByRaceMasterNameAndActive(String raceMasterName,boolean isActive);

    public Page<RaceMaster> findByActiveOrderByRaceMasterIdAsc(boolean isActive, final Pageable pageable);

    public List<RaceMaster> findByMarketMasterIdAndActive(int marketMasterId, boolean isActive);

    public RaceMaster save(RaceMaster raceMaster);

    List<RaceMaster> findByRaceMasterNameAndMarketMasterId(String raceMasterName, long marketMasterId);

    public RaceMaster findByRaceMasterIdAndActive(long id, boolean isActive);

    public RaceMaster findByRaceMasterIdAndActiveIn(@Param("raceMasterId") long raceMasterId, @Param("active") Set<Boolean> active);

    public List<RaceMaster> findByActiveOrderByRaceMasterNameAsc(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.RaceMasterDTO(" +
            " raceMaster.raceMasterId," +
            " raceMaster.raceMasterName," +
            " raceMaster.raceNameInKannada," +
            " raceMaster.marketMasterId," +
            " marketMaster.marketMasterName" +
            ") \n" +
            "from RaceMaster raceMaster\n" +
            "left join market_master marketMaster\n" +
            "on raceMaster.marketMasterId = marketMaster.marketMasterId " +
            "where raceMaster.active = :isActive AND raceMaster.raceMasterId = :id"
    )
    public RaceMasterDTO getByRaceMasterIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.RaceMasterDTO(" +
            " raceMaster.raceMasterId," +
            " raceMaster.raceMasterName," +
            " raceMaster.raceNameInKannada," +
            " raceMaster.marketMasterId," +
            " marketMaster.marketMasterName" +
            ") \n" +
            "from RaceMaster raceMaster\n" +
            "left join market_master marketMaster\n" +
            "on raceMaster.marketMasterId = marketMaster.marketMasterId " +
            "where raceMaster.active = :isActive " +
            "ORDER BY raceMaster.raceMasterName ASC"
    )
    Page<RaceMasterDTO> getByActiveOrderByRaceMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.RaceMasterDTO(" +
            " raceMaster.raceMasterId," +
            " raceMaster.raceMasterName," +
            " raceMaster.raceNameInKannada," +
            " raceMaster.marketMasterId," +
            " marketMaster.marketMasterName" +
            ") \n" +
            "from RaceMaster raceMaster\n" +
            "left join market_master marketMaster\n" +
            "on raceMaster.marketMasterId = marketMaster.marketMasterId " +
            "where raceMaster.active = :isActive AND " +
            "(:joinColumn = 'raceMaster.raceMasterName' AND raceMaster.raceMasterName LIKE :searchText) OR " +
            "(:joinColumn = 'marketMaster.marketMasterName' AND marketMaster.marketMasterName LIKE :searchText)"
    )
    public Page<RaceMasterDTO> getSortedRaceMasters(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
