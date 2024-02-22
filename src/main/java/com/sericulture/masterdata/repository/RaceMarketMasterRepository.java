package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.CrateMasterDTO;
import com.sericulture.masterdata.model.dto.RaceMarketMasterDTO;
import com.sericulture.masterdata.model.entity.CrateMaster;
import com.sericulture.masterdata.model.entity.RaceMarketMaster;
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
public interface RaceMarketMasterRepository extends PagingAndSortingRepository<RaceMarketMaster, Long> {
    public Page<RaceMarketMaster> findByActiveOrderByRaceMarketMasterIdAsc(boolean isActive, final Pageable pageable);

    List<RaceMarketMaster> findByMarketMasterIdAndRaceMasterId(long marketMasterId, long raceMasterId);
    List<RaceMarketMaster> findByActiveAndMarketMasterIdAndRaceMasterId(boolean a,long marketMasterId, long raceMasterId);

    List<RaceMarketMaster> findByMarketMasterIdAndRaceMasterIdAndRaceMarketMasterIdIsNot(long marketMasterId, long raceMasterId, long raceMarketMasterId);


    public RaceMarketMaster save(RaceMarketMaster raceMarketMaster);

    public RaceMarketMaster findByRaceMarketMasterIdAndActive(long id, boolean isActive);

    public RaceMarketMaster findByRaceMarketMasterIdAndActiveIn(@Param("raceMarketMasterId") long raceMarketMasterId, @Param("active") Set<Boolean> active);

    public List<RaceMarketMaster> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.RaceMarketMasterDTO(" +
            " raceMarketMaster.raceMarketMasterId," +
            " raceMarketMaster.marketMasterId," +
            " raceMarketMaster.raceMasterId," +
            " marketMaster.marketMasterName," +
            " raceMaster.raceMasterName" +
            ") \n" +
            "from RaceMarketMaster raceMarketMaster\n" +
            "left join market_master marketMaster\n" +
            "on raceMarketMaster.marketMasterId = marketMaster.marketMasterId " +
            "left join RaceMaster raceMaster\n" +
            "on raceMarketMaster.raceMasterId = raceMaster.raceMasterId " +
            "where raceMarketMaster.active = :isActive " +
            "ORDER BY marketMaster.marketMasterName ASC"
    )
    Page<RaceMarketMasterDTO> getByActiveOrderByRaceMarketMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.RaceMarketMasterDTO(" +
            " raceMarketMaster.raceMarketMasterId," +
            " raceMarketMaster.marketMasterId," +
            " raceMarketMaster.raceMasterId," +
            " marketMaster.marketMasterName," +
            " raceMaster.raceMasterName" +
            ") \n" +
            "from RaceMarketMaster raceMarketMaster\n" +
            "left join market_master marketMaster\n" +
            "on raceMarketMaster.marketMasterId = marketMaster.marketMasterId " +
            "left join RaceMaster raceMaster\n" +
            "on raceMarketMaster.raceMasterId = raceMaster.raceMasterId " +
            "where raceMarketMaster.active = :isActive AND raceMarketMaster.raceMarketMasterId = :id "
    )
    public RaceMarketMasterDTO getByRaceMarketMasterIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.RaceMarketMasterDTO(" +
            " raceMarketMaster.raceMarketMasterId," +
            " raceMarketMaster.marketMasterId," +
            " raceMarketMaster.raceMasterId," +
            " marketMaster.marketMasterName," +
            " raceMaster.raceMasterName" +
            ") \n" +
            "from RaceMarketMaster raceMarketMaster\n" +
            "left join market_master marketMaster\n" +
            "on raceMarketMaster.marketMasterId = marketMaster.marketMasterId " +
            "left join RaceMaster raceMaster\n" +
            "on raceMarketMaster.raceMasterId = raceMaster.raceMasterId " +
            "where raceMarketMaster.active = :isActive AND raceMarketMaster.marketMasterId = :marketMasterId "
    )
    public List<RaceMarketMasterDTO> getRaceMaster(long marketMasterId, boolean isActive);
}
