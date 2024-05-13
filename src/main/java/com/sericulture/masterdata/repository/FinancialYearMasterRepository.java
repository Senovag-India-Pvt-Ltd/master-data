package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.FinancialYearMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FinancialYearMasterRepository extends PagingAndSortingRepository<FinancialYearMaster, Long> {
    public List<FinancialYearMaster> findByFinancialYear(String financialYear);

    public List<FinancialYearMaster> findByFinancialYearAndIsDefault(String financialYear,boolean isDefault);

    public List<FinancialYearMaster> findByActiveAndFinancialYearAndIsDefault(boolean a,String financialYear,boolean isDefault);

    public FinancialYearMaster findByFinancialYearAndActive(String financialYear,boolean isActive);

    public Page<FinancialYearMaster> findByActiveOrderByFinancialYearAsc(boolean isActive, final Pageable pageable);

    public FinancialYearMaster save(FinancialYearMaster financialYearMaster);

    public FinancialYearMaster findByFinancialYearMasterIdAndActive(long id, boolean isActive);

    public FinancialYearMaster findByFinancialYearMasterIdAndActiveIn(@Param("financialYearMasterId") long financialYearMasterId, @Param("active") Set<Boolean> active);

    public List<FinancialYearMaster> findByActive(boolean isActive);

    public FinancialYearMaster findByIsDefaultAndActive(boolean isDefault, boolean isActive);
}
