package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.InspectionType;
import com.sericulture.masterdata.model.entity.InspectionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface InspectionTypeRepository extends PagingAndSortingRepository<InspectionType,Long> {
    public List<InspectionType> findByName(String name);

    public List<InspectionType> findByNameAndNameInKannada(String name,String nameInKannada);

    public List<InspectionType> findByActiveAndNameAndNameInKannada(boolean a,String name,String nameInKannada);

    public InspectionType findByNameAndActive(String name,boolean isActive);

    public Page<InspectionType> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public InspectionType save(InspectionType inspectionType);

    public InspectionType findByInspectionTypeIdAndActive(long id, boolean isActive);

    public InspectionType findByInspectionTypeIdAndActiveIn(@Param("inspectionTypeId") long inspectionTypeId, @Param("active") Set<Boolean> active);

    public List<InspectionType> findByActive(boolean isActive);
}
