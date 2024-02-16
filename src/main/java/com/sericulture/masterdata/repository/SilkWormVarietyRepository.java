package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.SilkWormVariety;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SilkWormVarietyRepository extends PagingAndSortingRepository<SilkWormVariety, Long> {
    public List<SilkWormVariety> findBySilkWormVarietyNameAndSilkWormVarietyNameInKannada(String silkWormVarietyName, String silkWormVarietyNameInKannada);

    public List<SilkWormVariety> findBySilkWormVarietyNameAndSilkWormVarietyNameInKannadaAndSilkWormVarietyIdIsNot(String silkWormVarietyName, String silkWormVarietyNameInKannada, long silkWormVarietyId);


    public SilkWormVariety findBySilkWormVarietyNameAndActive(String silkWormVarietyName,boolean isActive);

    public Page<SilkWormVariety> findByActiveOrderBySilkWormVarietyNameAsc(boolean isActive, final Pageable pageable);

    public SilkWormVariety save(SilkWormVariety silkWormVariety);

    public SilkWormVariety findBySilkWormVarietyIdAndActive(long id, boolean isActive);

    public SilkWormVariety findBySilkWormVarietyIdAndActiveIn(@Param("silkWormVarietyId") long silkWormVarietyId, @Param("active") Set<Boolean> active);

    public List<SilkWormVariety> findByActive(boolean isActive);
}
