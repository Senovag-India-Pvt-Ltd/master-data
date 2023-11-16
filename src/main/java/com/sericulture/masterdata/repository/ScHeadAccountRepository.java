package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ScHeadAccount;
import com.sericulture.masterdata.model.entity.ScProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScHeadAccountRepository extends PagingAndSortingRepository<ScHeadAccount, Long> {
    public List<ScHeadAccount> findByScHeadAccountName(String scHeadAccountName);

    public ScHeadAccount findByScHeadAccountNameAndActive(String scHeadAccountName,boolean isActive);

    public Page<ScHeadAccount> findByActiveOrderByScHeadAccountIdAsc(boolean isActive, final Pageable pageable);

    public ScHeadAccount save(ScHeadAccount scHeadAccount);

    public ScHeadAccount findByScHeadAccountIdAndActive(long id, boolean isActive);

    public ScHeadAccount findByScHeadAccountIdAndActiveIn(@Param("scHeadAccountId") long scHeadAccountId, @Param("active") Set<Boolean> active);
    public List<ScHeadAccount> findByActive(boolean isActive);
}
