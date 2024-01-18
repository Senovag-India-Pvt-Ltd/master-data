package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.CrateMasterDTO;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.entity.CrateMaster;
import com.sericulture.masterdata.model.entity.VendorMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CrateMasterRepository extends PagingAndSortingRepository<CrateMaster, Long> {



    public Page<CrateMaster> findByActiveOrderByCrateMasterIdAsc(boolean isActive, final Pageable pageable);

    public CrateMaster save(CrateMaster crateMaster);

    public CrateMaster findByCrateMasterIdAndActive(long id, boolean isActive);

    public CrateMaster findByCrateMasterIdAndActiveIn(@Param("crateMasterId") long crateMasterId, @Param("active") Set<Boolean> active);

    public List<CrateMaster> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.CrateMasterDTO(" +
            " crateMaster.crateMasterId," +
            " crateMaster.raceMasterId," +
            " crateMaster.marketId," +
            " crateMaster.godownId," +
            " crateMaster.approxWeightPerCrate," +
            " raceMaster.raceMasterName," +
            " marketMaster.marketMasterName," +
            " godown.godownName" +
            ") \n" +
            "from CrateMaster crateMaster\n" +
            "left join RaceMaster raceMaster\n" +
            "on crateMaster.raceMasterId = raceMaster.raceMasterId " +
            "left join market_master marketMaster\n" +
            "on crateMaster.marketId = marketMaster.marketMasterId " +
            "left join godown_master godown\n" +
            "on crateMaster.godownId = godown.godownId " +
            "where crateMaster.active = :isActive " +
            "ORDER BY marketMaster.marketMasterName ASC"
    )
    Page<CrateMasterDTO> getByActiveOrderByCrateMasterIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.CrateMasterDTO(" +
            " crateMaster.crateMasterId," +
            " crateMaster.raceMasterId," +
            " crateMaster.marketId," +
            " crateMaster.godownId," +
            " crateMaster.approxWeightPerCrate," +
            " raceMaster.raceMasterName," +
            " marketMaster.marketMasterName," +
            " godown.godownName" +
            ") \n" +
            "from CrateMaster crateMaster\n" +
            "left join RaceMaster raceMaster\n" +
            "on crateMaster.raceMasterId = raceMaster.raceMasterId " +
            "left join market_master marketMaster\n" +
            "on crateMaster.marketId = marketMaster.marketMasterId " +
            "left join godown_master godown\n" +
            "on crateMaster.godownId = godown.godownId " +
            "where crateMaster.active = :isActive AND crateMaster.crateMasterId = :id "
    )
    public CrateMasterDTO getByCrateMasterIdAndActive(long id, boolean isActive);
}
