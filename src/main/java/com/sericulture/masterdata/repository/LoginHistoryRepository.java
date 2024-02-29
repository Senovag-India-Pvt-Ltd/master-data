package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.LoginHistory;
import com.sericulture.masterdata.model.entity.ReasonBidRejectMaster;
import com.sericulture.masterdata.model.entity.TrainingDeputationTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface LoginHistoryRepository extends PagingAndSortingRepository<LoginHistory, Long> {
    public Page<LoginHistory> findByActiveOrderByLoginHistoryIdAsc(boolean isActive, final Pageable pageable);

    public LoginHistory save(LoginHistory loginHistory);

    public LoginHistory findByLoginHistoryIdAndActive(long loginHistoryId, boolean isActive);

    public LoginHistory findByLoginHistoryIdAndActiveIn(@Param("loginHistoryId") long loginHistoryId, @Param("active") Set<Boolean> active);

    public List<LoginHistory> findByActiveOrderByLoginHistoryIdAsc(boolean isActive);

}




