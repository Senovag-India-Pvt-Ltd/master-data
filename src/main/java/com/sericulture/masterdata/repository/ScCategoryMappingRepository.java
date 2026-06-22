package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ScCategorySchemeMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScCategoryMappingRepository extends CrudRepository<ScCategorySchemeMapping, Long> {

    List<ScCategorySchemeMapping> findByScCategoryIdAndActive(Long scCategoryId, boolean active);

    List<ScCategorySchemeMapping> findBySchemeIdAndActive(Long schemeId, boolean active);

    List<ScCategorySchemeMapping> findBySchemeIdAndSubSchemeIdAndActive(Long schemeId, Long subSchemeId, boolean active);

    ScCategorySchemeMapping findByScCategoryIdAndSchemeIdAndSubSchemeIdAndActive(
            Long scCategoryId, Long schemeId, Long subSchemeId, boolean active);

    ScCategorySchemeMapping findByScCategoryMappingIdAndActive(Long id, boolean active);
}
