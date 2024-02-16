package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.MarketMasterDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
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
    public List<MarketMaster> findByMarketMasterNameAndMarketNameInKannada(String marketMasterName,String marketNameInKannada);

    public MarketMaster findByMarketMasterNameAndActive(String marketMasterName,boolean isActive);

    public Page<MarketMaster> findByActiveOrderByMarketMasterIdAsc(boolean isActive, final Pageable pageable);

    public MarketMaster save(MarketMaster marketMaster);

    public MarketMaster findByMarketMasterIdAndActive(long marketMasterId, boolean isActive);

    public MarketMaster findByMarketMasterIdAndActiveIn(@Param("marketMasterId") long marketMasterId, @Param("active") Set<Boolean> active);

    public List<MarketMaster> findByActiveOrderByMarketMasterNameAsc(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.MarketMasterDTO(" +
            " marketMaster.marketMasterId," +
            " marketMaster.marketMasterName," +
            " marketMaster.marketNameInKannada," +
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
            " marketMaster.auctionAcceptance1StartTime," +
            " marketMaster.auctionAcceptance2StartTime," +
            " marketMaster.auctionAcceptance3StartTime," +
            " marketMaster.auctionAcceptance1EndTime," +
            " marketMaster.auctionAcceptance2EndTime," +
            " marketMaster.auctionAcceptance3EndTime," +
            " marketMaster.serialNumberPrefix," +
            " marketMaster.clientId," +
            " marketMaster.marketTypeMasterId," +
            " marketMaster.marketLatitude," +
            " marketMaster.marketLongitude," +
            " marketMaster.radius," +
            " marketMaster.snorkelRequestPath," +
            " marketMaster.snorkelResponsePath," +
            " marketMaster.clientCode," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " marketTypeMaster.marketTypeMasterName," +
            " marketMaster.reelerMinimumBalance" +
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
            "ORDER BY marketMaster.marketMasterName ASC"
    )
    Page<MarketMasterDTO> getByActiveOrderByMarketMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.MarketMasterDTO(" +
            " marketMaster.marketMasterId," +
            " marketMaster.marketMasterName," +
            " marketMaster.marketNameInKannada," +
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
            " marketMaster.auctionAcceptance1StartTime," +
            " marketMaster.auctionAcceptance2StartTime," +
            " marketMaster.auctionAcceptance3StartTime," +
            " marketMaster.auctionAcceptance1EndTime," +
            " marketMaster.auctionAcceptance2EndTime," +
            " marketMaster.auctionAcceptance3EndTime," +
            " marketMaster.serialNumberPrefix," +
            " marketMaster.clientId," +
            " marketMaster.marketTypeMasterId," +
            " marketMaster.marketLatitude," +
            " marketMaster.marketLongitude," +
            " marketMaster.radius," +
            " marketMaster.snorkelRequestPath," +
            " marketMaster.snorkelResponsePath," +
            " marketMaster.clientCode," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " marketTypeMaster.marketTypeMasterName," +
            " marketMaster.reelerMinimumBalance" +
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

    @Query("select new com.sericulture.masterdata.model.dto.MarketMasterDTO(" +
            " marketMaster.marketMasterId," +
            " marketMaster.marketMasterName," +
            " marketMaster.marketNameInKannada," +
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
            " marketMaster.auctionAcceptance1StartTime," +
            " marketMaster.auctionAcceptance2StartTime," +
            " marketMaster.auctionAcceptance3StartTime," +
            " marketMaster.auctionAcceptance1EndTime," +
            " marketMaster.auctionAcceptance2EndTime," +
            " marketMaster.auctionAcceptance3EndTime," +
            " marketMaster.serialNumberPrefix," +
            " marketMaster.clientId," +
            " marketMaster.marketTypeMasterId," +
            " marketMaster.marketLatitude," +
            " marketMaster.marketLongitude," +
            " marketMaster.radius," +
            " marketMaster.snorkelRequestPath," +
            " marketMaster.snorkelResponsePath," +
            " marketMaster.clientCode," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " marketTypeMaster.marketTypeMasterName," +
            " marketMaster.reelerMinimumBalance" +
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
            "where marketMaster.active = :isActive AND " +
            "(:joinColumn = 'marketMaster.marketMasterName' AND marketMaster.marketMasterName LIKE :searchText) OR " +
            "(:joinColumn = 'marketTypeMaster.marketTypeMasterName' AND marketTypeMaster.marketTypeMasterName LIKE :searchText)"
    )
    public Page<MarketMasterDTO> getSortedMarkets(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
