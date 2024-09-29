package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.CropInspectionType;
import com.sericulture.masterdata.model.entity.Reason;
import com.sericulture.masterdata.model.entity.Reason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReasonRepository  extends PagingAndSortingRepository<Reason, Long> {
    public List<Reason> findByName(String name);

    public List<Reason> findByActiveAndName(boolean a,String name);

    public Reason findByNameAndActive(String reasonName, boolean isActive);

    public Page<Reason> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public Reason save(Reason reason);

    public Reason findByReasonIdAndActive(long id, boolean isActive);

    public Reason findByReasonIdAndActiveIn(@Param("reasonId") long reasonId, @Param("active") Set<Boolean> active);

    public List<Reason> findByActive(boolean isActive);
    
}
