package com.sericulture.masterdata.repository;


import com.sericulture.masterdata.model.dto.GodownDTO;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.entity.Godown;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GodownRepository extends PagingAndSortingRepository<Godown, Long> {
    public List<Godown> findByGodownName(String godownName);
    List<Godown> findByGodownNameAndMarketMasterIdAndGodownIdIsNot(String godownName, long marketMasterId,long godownId);


    public Godown findByGodownNameAndActive(String godownName,boolean isActive);

    public List<Godown> findByMarketMasterIdAndActive(int marketMasterId, boolean isActive);

    public Page<Godown> findByActiveOrderByGodownIdAsc(boolean isActive, final Pageable pageable);

    public Godown save(Godown godown);

    public Godown findByGodownIdAndActive(long id, boolean isActive);

    public Godown findByGodownIdAndActiveIn(@Param("godownId") long godownId, @Param("active") Set<Boolean> active);

    List<Godown> findByGodownNameAndMarketMasterId(String godownName, long marketMasterId);
    List<Godown> findByGodownNameAndMarketMasterIdAndActive(String godownName, long marketMasterId, boolean active);

    public List<Godown> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.GodownDTO(" +
            " godown.godownId," +
            " godown.godownName," +
            " godown.godownNameInKannada," +
            " godown.marketMasterId," +
            " marketMaster.marketMasterName" +
            ") \n" +
            "from godown_master godown\n" +
            "left join market_master marketMaster\n" +
            "on godown.marketMasterId = marketMaster.marketMasterId " +
            "where godown.active = :isActive " +
            "ORDER BY godown.godownName ASC"
    )
    Page<GodownDTO> getByActiveOrderByGodownIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.GodownDTO(" +
            " godown.godownId," +
            " godown.godownName," +
            " godown.godownNameInKannada," +
            " godown.marketMasterId," +
            " marketMaster.marketMasterName" +
            ") \n" +
            "from godown_master godown\n" +
            "left join market_master marketMaster\n" +
            "on godown.marketMasterId = marketMaster.marketMasterId " +
            "where godown.active = :isActive AND godown.godownId = :id "
    )
    public GodownDTO getByGodownIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.GodownDTO(" +
            " godown.godownId," +
            " godown.godownName," +
            " godown.godownNameInKannada," +
            " godown.marketMasterId," +
            " marketMaster.marketMasterName" +
            ") \n" +
            "from godown_master godown\n" +
            "left join market_master marketMaster\n" +
            "on godown.marketMasterId = marketMaster.marketMasterId " +
            "where godown.active = :isActive AND " +
            "(:joinColumn = 'godown.godownName' AND godown.godownName LIKE :searchText) OR " +
            "(:joinColumn = 'marketMaster.marketMasterName' AND marketMaster.marketMasterName LIKE :searchText)"
    )
    public Page<GodownDTO> getSortedGodowns(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}