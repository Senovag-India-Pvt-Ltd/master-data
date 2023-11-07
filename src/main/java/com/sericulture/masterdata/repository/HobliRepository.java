package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Hobli;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HobliRepository extends PagingAndSortingRepository<Hobli, Long> {
    public List<Hobli> findByHobliName(String hobliName);

    public List<Hobli> findByHobliNameAndHobliId(String hobliName, long stateId);

    public Hobli findByHobliNameAndActive(String hobliName,boolean isActive);

    public Page<Hobli> findByActiveOrderByHobliIdAsc(boolean isActive, final Pageable pageable);

    public Hobli save(Hobli hobli);

    public Hobli findByHobliIdAndActive(long id, boolean isActive);

    public List<Hobli> findByTalukIdAndActive(long talukId, boolean isActive);

    public Hobli findByHobliIdAndActiveIn(@Param("hobliId") long hobliId, @Param("active") Set<Boolean> active);
}