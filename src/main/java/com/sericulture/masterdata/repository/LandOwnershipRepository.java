package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.LandOwnership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LandOwnershipRepository extends PagingAndSortingRepository<LandOwnership,Long> {
    public List<LandOwnership> findByLandOwnershipName(String landOwnershipName);

    public LandOwnership findByLandOwnershipNameAndActive(String landOwnershipName,boolean isActive);

    public Page<LandOwnership> findByActiveOrderByLandOwnershipNameAsc(boolean isActive, final Pageable pageable);

    public LandOwnership save(LandOwnership landOwnership);

    public LandOwnership findByLandOwnershipIdAndActive(long id, boolean isActive);

    public LandOwnership findByLandOwnershipIdAndActiveIn(@Param("landOwnershipId") long landOwnershipId, @Param("active") Set<Boolean> active);

    public List<LandOwnership> findByActive(boolean isActive);
}
