package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StateRepository extends PagingAndSortingRepository<State, Long> {
    public List<State> findByStateName(String stateName);

    public State findByStateNameAndActive(String stateName,boolean isActive);

    public Page<State> findByActiveOrderByStateIdAsc(boolean isActive, final Pageable pageable);

    public State save(State state);

    public State findByStateIdAndActive(long id, boolean isActive);

    public State findByStateIdAndActiveIn(@Param("stateId") long stateId, @Param("active") Set<Boolean> active);

    public List<State> findByActive(boolean isActive);

}
