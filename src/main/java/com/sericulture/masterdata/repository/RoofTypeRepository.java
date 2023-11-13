package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.RoofType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoofTypeRepository extends PagingAndSortingRepository<RoofType, Long> {
    public List<RoofType> findByRoofTypeName(String roofTypeName);

    public RoofType findByRoofTypeNameAndActive(String roofTypeName,boolean isActive);

    public Page<RoofType> findByActiveOrderByRoofTypeIdAsc(boolean isActive, final Pageable pageable);

    public RoofType save(RoofType roofType);

    public RoofType findByRoofTypeIdAndActive(long id, boolean isActive);

    public RoofType findByRoofTypeIdAndActiveIn(@Param("roofTypeId") long roofTypeId, @Param("active") Set<Boolean> active);
    public List<RoofType> findByActive(boolean isActive);
}
