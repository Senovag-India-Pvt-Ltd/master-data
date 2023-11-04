package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.MulberryVariety;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MulberryVarietyRepository extends PagingAndSortingRepository<MulberryVariety, Long> {
    public List<MulberryVariety> findByMulberryVarietyName(String mulberryVarietyName);

    public MulberryVariety findByMulberryVarietyNameAndActive(String mulberryVarietyName,boolean isActive);

    public Page<MulberryVariety> findByActiveOrderByMulberryVarietyIdAsc(boolean isActive, final Pageable pageable);

    public MulberryVariety save(MulberryVariety mulberryVariety);

    public MulberryVariety findByMulberryVarietyIdAndActive(long id, boolean isActive);

    public MulberryVariety findByMulberryVarietyIdAndActiveIn(@Param("mulberryVarietyId") long mulberryVarietyId, @Param("active") Set<Boolean> active);
}