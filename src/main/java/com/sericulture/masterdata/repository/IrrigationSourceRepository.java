package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.IrrigationSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface IrrigationSourceRepository extends PagingAndSortingRepository<IrrigationSource,Long> {
    public List<IrrigationSource> findByIrrigationSourceName(String irrigationSourceName);

    public IrrigationSource findByIrrigationSourceNameAndActive(String irrigationSourceName,boolean isActive);

    public Page<IrrigationSource> findByActiveOrderByIrrigationSourceIdAsc(boolean isActive, final Pageable pageable);

    public IrrigationSource save(IrrigationSource irrigationSource);

    public IrrigationSource findByIrrigationSourceIdAndActive(long id, boolean isActive);

    public IrrigationSource findByIrrigationSourceIdAndActiveIn(@Param("irrigationSourceId") long irrigationSourceId, @Param("active") Set<Boolean> active);


}
