package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Caste;
import com.sericulture.masterdata.model.entity.CropInspectionType;
import com.sericulture.masterdata.model.entity.CropInspectionType;
import com.sericulture.masterdata.model.entity.HdBoardCategoryMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CropInspectionTypeRepository extends PagingAndSortingRepository<CropInspectionType, Long> {
    public List<CropInspectionType> findByName(String name);

    public List<CropInspectionType> findByNameAndCropInspectionTypeIdIsNotAndActive( String name,long cropInspectionTypeId, boolean active);

    public CropInspectionType findByNameAndActive(String name,boolean isActive);



    public Page<CropInspectionType> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public CropInspectionType save(CropInspectionType cropInspectionType);

    public CropInspectionType findByCropInspectionTypeIdAndActive(long cropInspectionTypeId, boolean isActive);


    public CropInspectionType findByCropInspectionTypeIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);

    public List<CropInspectionType> findByActive(boolean isActive);
    
}
