package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.SericultureTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SericultureTableRepository extends PagingAndSortingRepository<SericultureTable, Long> {

    List<SericultureTable> findByActive(boolean isActive);

    Page<SericultureTable> findByActiveOrderBySericultureTableIdAsc(boolean isActive, Pageable pageable);

    SericultureTable save(SericultureTable sericultureTable);

    SericultureTable findBySericultureTableIdAndActive(long id, boolean isActive);

    SericultureTable findBySericultureTableIdAndActiveIn(long sericultureTableId, Set<Boolean> active);

    List<SericultureTable> findByActiveAndStepIdAndSericultureTableIdIsNot(boolean active, Integer stepId, long sericultureTableId);

    List<SericultureTable> findBySchemeIdAndSubSchemeIdAndActive(Long schemeId, Long subSchemeId, boolean active);

    SericultureTable findBySchemeIdAndSubSchemeIdAndStepIdAndActive(Long schemeId, Long subSchemeId, Integer stepId, boolean active);
}
