package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.LandCategory;
import com.sericulture.masterdata.model.entity.SoilType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SoilTypeRepository extends PagingAndSortingRepository<SoilType,Long> {
    public List<SoilType> findBySoilTypeName(String soilTypeName);

    public SoilType findBySoilTypeNameAndActive(String soilTypeName,boolean isActive);

    public Page<SoilType> findByActiveOrderBySoilTypeIdAsc(boolean isActive, final Pageable pageable);

    public SoilType save(SoilType soilType);

    public SoilType findBySoilTypeIdAndActive(long id, boolean isActive);

    public SoilType findBySoilTypeIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);

}
