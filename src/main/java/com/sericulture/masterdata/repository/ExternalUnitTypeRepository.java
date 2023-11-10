package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ExternalUnitType;
import com.sericulture.masterdata.model.entity.TraderTypeMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExternalUnitTypeRepository extends PagingAndSortingRepository<ExternalUnitType,Long> {
    public List<ExternalUnitType> findByExternalUnitTypeName(String externalUnitTypeName);

    public ExternalUnitType findByExternalUnitTypeNameAndActive(String externalUnitTypeName,boolean isActive);

    public Page<ExternalUnitType> findByActiveOrderByExternalUnitTypeIdAsc(boolean isActive, final Pageable pageable);

    public ExternalUnitType save(ExternalUnitType externalUnitType);

    public ExternalUnitType findByExternalUnitTypeIdAndActive(long id, boolean isActive);

    public ExternalUnitType findByExternalUnitTypeIdAndActiveIn(@Param("externalUnitTypeId") long externalUnitTypeId, @Param("active") Set<Boolean> active);


}