package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.GovtAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GovtAccountRepository extends PagingAndSortingRepository<GovtAccount, Long> {

    GovtAccount findByGovtAccountNumberAndActive(String govtAccountNumber, boolean isActive);

    List<GovtAccount> findByGovtAccountNumberAndActiveIn(String govtAccountNumber, Set<Boolean> active);

    GovtAccount save(GovtAccount govtAccount);

    GovtAccount findByGovtAccountIdAndActive(long id, boolean isActive);

    GovtAccount findByGovtAccountIdAndActiveIn(@Param("govtAccountId") long govtAccountId, @Param("active") Set<Boolean> active);

    List<GovtAccount> findByActive(boolean isActive);
}