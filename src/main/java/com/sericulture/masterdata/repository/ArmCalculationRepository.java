package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ArmCalculation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmCalculationRepository extends PagingAndSortingRepository<ArmCalculation, Long> {

    ArmCalculation save(ArmCalculation armCalculation);

    ArmCalculation findByArmCalculationIdAndActive(long armCalculationId, boolean active);

    Page<ArmCalculation> findByActiveOrderByArmCalculationIdDesc(boolean active, Pageable pageable);

    List<ArmCalculation> findByActive(boolean active);

    List<ArmCalculation> findByScCategoryIdAndComponentIdAndActive(Long scCategoryId, Long componentId, boolean active);

    List<ArmCalculation> findByScCategoryIdAndActive(Long scCategoryId, boolean active);

    List<ArmCalculation> findByArmEndsAndScCategoryIdAndActive(String armEnds, Long scCategoryId, boolean active);
}
