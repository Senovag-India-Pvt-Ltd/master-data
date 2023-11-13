package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.MulberrySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MulberrySourceRepository extends PagingAndSortingRepository<MulberrySource, Long> {
    public List<MulberrySource> findByMulberrySourceName(String mulberrySourceName);

    public MulberrySource findByMulberrySourceNameAndActive(String mulberrySourceName,boolean isActive);

    public Page<MulberrySource> findByActiveOrderByMulberrySourceIdAsc(boolean isActive, final Pageable pageable);

    public MulberrySource save(MulberrySource mulberrySource);

    public MulberrySource findByMulberrySourceIdAndActive(long id, boolean isActive);

    public MulberrySource findByMulberrySourceIdAndActiveIn(@Param("mulberrySourceId") long mulberrySourceId, @Param("active") Set<Boolean> active);

    public List<MulberrySource> findByActive(boolean isActive);
}