package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Godown;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GodownRepository extends PagingAndSortingRepository<Godown, Long> {
    public List<Godown> findByGodownName(String godownName);

    public Godown findByGodownNameAndActive(String godownName,boolean isActive);

    public Godown findByMarketMasterIdAndActive(int marketMasterId, boolean isActive);

    public Page<Godown> findByActiveOrderByGodownIdAsc(boolean isActive, final Pageable pageable);

    public Godown save(Godown godown);

    public Godown findByGodownIdAndActive(long id, boolean isActive);

    public Godown findByGodownIdAndActiveIn(@Param("godownId") long godownId, @Param("active") Set<Boolean> active);
}