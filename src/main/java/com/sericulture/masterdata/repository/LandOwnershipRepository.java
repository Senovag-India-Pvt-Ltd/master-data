package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.LandOwnership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface LandOwnershipRepository extends PagingAndSortingRepository<LandOwnership,Long> {
    public List<LandOwnership> findByLandOwnershipName(String landOwnershipName);

    public LandOwnership findByLandOwnershipNameAndActive(String landOwnershipName,boolean isActive);

    public Page<LandOwnership> findByActiveOrderByLandOwnershipIdAsc(boolean isActive, final Pageable pageable);

    public LandOwnership save(LandOwnership landOwnership);

    public LandOwnership findByLandOwnershipIdAndActive(long id, boolean isActive);

    public LandOwnership findByLandOwnershipIdAndActiveIn(@Param("landOwnershipId") long landOwnershipId, @Param("active") Set<Boolean> active);


}
