package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.SpacingMaster;
import com.sericulture.masterdata.model.entity.SpacingMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SpacingMasterRepository extends PagingAndSortingRepository<SpacingMaster, Long> {

    public List<SpacingMaster> findBySpacingName(String spacingName);

    public List<SpacingMaster> findBySpacingNameAndSpacingIdIsNot(String spacingName,long spacingId);


    public Page<SpacingMaster> findByActiveOrderBySpacingNameAsc(boolean isActive, final Pageable pageable);

    public SpacingMaster save(SpacingMaster spacingMaster);

    public SpacingMaster findBySpacingIdAndActive(long id, boolean isActive);

    public SpacingMaster findBySpacingIdAndActiveIn(@Param("spacingId") long SpacingMasterId, @Param("active") Set<Boolean> active);

    public List<SpacingMaster> findByActiveOrderBySpacingNameAsc(boolean isActive);
}
