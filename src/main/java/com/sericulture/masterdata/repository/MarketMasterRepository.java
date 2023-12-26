package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.MarketMasterDTO;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.entity.MarketMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MarketMasterRepository extends PagingAndSortingRepository<MarketMaster, Long> {
    public List<MarketMaster> findByMarketMasterName(String marketMasterName);

    public MarketMaster findByMarketMasterNameAndActive(String marketMasterName,boolean isActive);

    public Page<MarketMaster> findByActiveOrderByMarketMasterIdAsc(boolean isActive, final Pageable pageable);

    public MarketMaster save(MarketMaster marketMaster);

    public MarketMaster findByMarketMasterIdAndActive(long marketMasterId, boolean isActive);

    public MarketMaster findByMarketMasterIdAndActiveIn(@Param("marketMasterId") long marketMasterId, @Param("active") Set<Boolean> active);

    public List<MarketMaster> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.MarketMasterDTO(" +
            " marketMaster.marketMasterId," +
            " marketMaster.marketMasterName," +
            " marketMaster.marketMasterAddress," +
            " marketMaster.boxWeight," +
            " marketMaster.lotWeight," +
            " marketMaster.stateId," +
            " marketMaster.districtId," +
            " marketMaster.talukId," +
            " marketMaster.issueBidSlipStartTime," +
            " marketMaster.issueBidSlipEndTime," +
            " marketMaster.auction1StartTime," +
            " marketMaster.auction2StartTime," +
            " marketMaster.auction3StartTime," +
            " marketMaster.auction1EndTime," +
            " marketMaster.auction2EndTime," +
            " marketMaster.auction3EndTime," +
            " marketMaster.marketTypeMasterId," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " marketTypeMaster.marketTypeMasterName" +
            ") \n" +
            "from market_master marketMaster\n" +
            "left join State state\n" +
            "on marketMaster.stateId = state.stateId " +
            "left join District district\n" +
            "on marketMaster.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on marketMaster.talukId = taluk.talukId " +
            "left join MarketTypeMaster marketTypeMaster\n" +
            "on marketMaster.marketTypeMasterId = marketTypeMaster.marketTypeMasterId " +
            "where marketMaster.active = :isActive " +
            "ORDER BY marketMaster.marketMasterId ASC"
    )
    Page<MarketMasterDTO> getByActiveOrderByMarketMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.MarketMasterDTO(" +
            " marketMaster.marketMasterId," +
            " marketMaster.marketMasterName," +
            " marketMaster.marketMasterAddress," +
            " marketMaster.boxWeight," +
            " marketMaster.lotWeight," +
            " marketMaster.stateId," +
            " marketMaster.districtId," +
            " marketMaster.talukId," +
            " marketMaster.issueBidSlipStartTime," +
            " marketMaster.issueBidSlipEndTime," +
            " marketMaster.auction1StartTime," +
            " marketMaster.auction2StartTime," +
            " marketMaster.auction3StartTime," +
            " marketMaster.auction1EndTime," +
            " marketMaster.auction2EndTime," +
            " marketMaster.auction3EndTime," +
            " marketMaster.marketTypeMasterId," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " marketTypeMaster.marketTypeMasterName" +
            ") \n" +
            "from market_master marketMaster\n" +
            "left join State state\n" +
            "on marketMaster.stateId = state.stateId " +
            "left join District district\n" +
            "on marketMaster.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on marketMaster.talukId = taluk.talukId " +
            "left join MarketTypeMaster marketTypeMaster\n" +
            "on marketMaster.marketTypeMasterId = marketTypeMaster.marketTypeMasterId " +
            "where marketMaster.active = :isActive AND marketMaster.marketMasterId = :id "
    )
    public MarketMasterDTO getByMarketMasterIdAndActive(@Param("id") long id, @Param("isActive") boolean isActive);
}
