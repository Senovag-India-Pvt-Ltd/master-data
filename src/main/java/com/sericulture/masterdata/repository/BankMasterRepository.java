package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.BankMaster;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BankMasterRepository extends PagingAndSortingRepository<BankMaster, Long> {

    public List<BankMaster> findByBankName(String bankName);

    public List<BankMaster> findByBankNameAndBankNameInKannada(String bankName,String bankNameInKannada);

    public List<BankMaster> findByActiveAndBankNameAndBankNameInKannada(boolean a,String bankName,String bankNameInKannada);

    public BankMaster findByBankNameAndActive(String bankName,boolean isActive);

    public Page<BankMaster> findByActiveOrderByBankNameAsc(boolean isActive, final Pageable pageable);

    public BankMaster save(BankMaster bankMaster);

    public BankMaster findByBankMasterIdAndActive(long id, boolean isActive);

    public BankMaster findByBankMasterIdAndActiveIn(@Param("bankMasterId") long bankMasterId, @Param("active") Set<Boolean> active);

    public List<BankMaster> findByActive(boolean isActive);
}
