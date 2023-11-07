package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Village;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface VillageRepository extends PagingAndSortingRepository<Village, Long> {
    public List<Village> findByVillageName(String villageName);

    public List<Village> findByVillageNameAndStateId(String villageName, long stateId);

    public Village findByVillageNameAndActive(String villageName,boolean isActive);

    public Page<Village> findByActiveOrderByVillageIdAsc(boolean isActive, final Pageable pageable);

    public Village save(Village village);

    public Village findByVillageIdAndActive(long id, boolean isActive);

    public List<Village> findByHobliIdAndActive(long stateId, boolean isActive);

    public Village findByVillageIdAndActiveIn(@Param("villageId") long villageId, @Param("active") Set<Boolean> active);
}