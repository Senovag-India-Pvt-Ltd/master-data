package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ScDbtCodeFinancialYear;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface ScDbtCodeFinancialYearRepository extends PagingAndSortingRepository<ScDbtCodeFinancialYear, Long> {

    ScDbtCodeFinancialYear save(ScDbtCodeFinancialYear entity);

    List<ScDbtCodeFinancialYear> findByMasterTypeAndParentIdAndActiveOrderByFinancialYearMasterIdAsc(String masterType, Long parentId, Boolean active);

    ScDbtCodeFinancialYear findByScDbtCodeFinancialYearIdAndActiveIn(Long id, Set<Boolean> activeStates);

    ScDbtCodeFinancialYear findByMasterTypeAndParentIdAndFinancialYearMasterIdAndActive(String masterType, Long parentId, Long financialYearMasterId, Boolean active);
}
