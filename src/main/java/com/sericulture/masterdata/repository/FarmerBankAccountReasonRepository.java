package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.FarmerBankAccountReason;
import com.sericulture.masterdata.model.entity.FarmerBankAccountReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FarmerBankAccountReasonRepository extends PagingAndSortingRepository<FarmerBankAccountReason,Long> {

    public List<FarmerBankAccountReason> findByFarmerBankAccountReason(String farmerBankAccountReason);

    public List<FarmerBankAccountReason> findByActiveAndFarmerBankAccountReason(boolean a,String farmerBankAccountReason);

    public FarmerBankAccountReason findByFarmerBankAccountReasonAndActive(String farmerBankAccountReason,boolean isActive);

    public Page<FarmerBankAccountReason> findByActiveOrderByFarmerBankAccountReasonAsc(boolean isActive, final Pageable pageable);

    public FarmerBankAccountReason save(FarmerBankAccountReason farmerBankAccountReason);

    public FarmerBankAccountReason findByFarmerBankAccountReasonIdAndActive(long id, boolean isActive);

    public FarmerBankAccountReason findByFarmerBankAccountReasonIdAndActiveIn(@Param("farmerBankAccountReasonId") long farmerBankAccountReasonId, @Param("active") Set<Boolean> active);

    public List<FarmerBankAccountReason> findByActive(boolean isActive);

}
