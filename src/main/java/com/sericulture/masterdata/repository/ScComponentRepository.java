package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ScComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScComponentRepository extends PagingAndSortingRepository<ScComponent, Long> {
    public List<ScComponent> findByScComponentName(String scComponentName);

    public ScComponent findByScComponentNameAndActive(String scComponentName,boolean isActive);

    public Page<ScComponent> findByActiveOrderByScComponentNameAsc(boolean isActive, final Pageable pageable);

    public ScComponent save(ScComponent scComponent);

    public ScComponent findByScComponentIdAndActive(long id, boolean isActive);

    public ScComponent findByScComponentIdAndActiveIn(@Param("scComponentId") long scComponentId, @Param("active") Set<Boolean> active);
    public List<ScComponent> findByActive(boolean isActive);
}
