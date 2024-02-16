package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.RpPageRoot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RpPageRootRepository extends PagingAndSortingRepository<RpPageRoot,Long> {
    public List<RpPageRoot> findByRpPageRootName(String rpPageRootName);

    public List<RpPageRoot> findByRpPageRootNameAndRpPageRootIdIsNot(String rpPageRootName, long rpPageRootId);


    public RpPageRoot findByRpPageRootNameAndActive(String rpPageRootName,boolean isActive);

    public Page<RpPageRoot> findByActiveOrderByRpPageRootIdAsc(boolean isActive, final Pageable pageable);

    public RpPageRoot save(RpPageRoot rpPageRoot);

    public RpPageRoot findByRpPageRootIdAndActive(long id, boolean isActive);

    public RpPageRoot findByRpPageRootIdAndActiveIn(@Param("rpPageRootId") long rpPageRootId, @Param("active") Set<Boolean> active);
    public List<RpPageRoot> findByActive(boolean isActive);
}
