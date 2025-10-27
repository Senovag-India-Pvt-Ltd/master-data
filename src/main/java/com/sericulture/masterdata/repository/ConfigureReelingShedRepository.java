package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Caste;
import com.sericulture.masterdata.model.entity.ConfigureIcb;
import com.sericulture.masterdata.model.entity.ConfigureReelingShed;
import com.sericulture.masterdata.model.entity.ConfigureReelingShed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ConfigureReelingShedRepository  extends PagingAndSortingRepository<ConfigureReelingShed, Long> {


    Page<ConfigureReelingShed> findByActiveOrderByReelingShedIdAsc(boolean isActive, Pageable pageable);

    ConfigureReelingShed save(ConfigureReelingShed configureReelingShed);

    public List<ConfigureReelingShed> findByReelingUnitAndReelingSqftAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(String reelingUnit,String reelingSqft, long componentTypeId, long componentId, long categoryId, boolean isActive);

    // ✅ ID-based queries only (removed farmName & farmNameInKannada)
    ConfigureReelingShed findByReelingShedIdAndActive(long reelingShedId, boolean isActive);

    ConfigureReelingShed findByReelingShedIdAndActiveIn(
            @Param("reelingShedId") long reelingShedId,
            @Param("active") Set<Boolean> active
    );

    List<ConfigureReelingShed> findByActive(boolean isActive);

    List<ConfigureReelingShed> findByActiveAndReelingShedIdIsNot(boolean isActive, long reelingShedId);

    @Query(nativeQuery = true, value = """
        SELECT
         crs.reeling_shed_id,
         crs.reeling_unit,
         crs.reelingSqft,
         crs.category_id,
         crs.component_id,
         crs.component_type_id,
         crs.unit_cost,
         sc.category_name,
         scm.sc_component_name,
         ssd.sub_scheme_name
     FROM
         configure_reeling_shed crs
         LEFT JOIN sc_category sc ON sc.sc_category_id = crs.category_id
         LEFT JOIN sc_component scm ON scm.sc_component_id = crs.component_id
         LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = crs.component_type_id
     WHERE
         crs.active = 1
         """)
    Page<Object[]> getByActiveOrderByconfigureReelingShedIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query(nativeQuery = true, value = """
            SELECT
         crs.reeling_shed_id,
         crs.reeling_unit,
         crs.reelingSqft,
         crs.category_id,
         crs.component_id,
         crs.component_type_id,
         crs.unit_cost,
         sc.category_name,
         scm.sc_component_name,
         ssd.sub_scheme_name
     FROM
         configure_reeling_shed crs
         LEFT JOIN sc_category sc ON sc.sc_category_id = crs.category_id
         LEFT JOIN sc_component scm ON scm.sc_component_id = crs.component_id
         LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = crs.component_type_id
     WHERE
         crs.active = 1
         """)
    Page<Object[]> getByConfigureReelingShedIdAndActive(@Param("isActive") boolean isActive, final Pageable pageable);


    // ✅ Paginated joined list
    @Query(value = """
        SELECT 
            crs.reeling_shed_id AS reelingShedId,
            crs.reeling_unit AS reelingUnit,
            crs.reelingSqft AS reelingSqft,
            crs.category_id AS categoryId,
            crs.component_id AS componentId,
            crs.component_type_id AS componentTypeId,
            crs.unit_cost AS unitCost,
            crs.min AS min,
            crs.max AS max,
            sc.category_name AS categoryName,
            scm.sc_component_name AS scComponentName,
            ssd.sub_scheme_name AS subSchemeName
        FROM configure_reeling_shed crs
        LEFT JOIN sc_category sc ON sc.sc_category_id = crs.category_id
        LEFT JOIN sc_component scm ON scm.sc_component_id = crs.component_id
        LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = crs.component_type_id
        WHERE crs.active = 1
        ORDER BY crs.reeling_shed_id ASC
        """,
            countQuery = "SELECT COUNT(*) FROM configure_reeling_shed WHERE active = 1",
            nativeQuery = true)
    Page<Object[]> getConfigureReelingShedListWithJoin(Pageable pageable);


    // ✅ Get by ID with joins
    @Query(value = """
        SELECT TOP 1
            crs.reeling_shed_id AS reelingShedId,
            crs.reeling_unit AS reelingUnit,
            crs.reelingSqft AS reelingSqft,
            crs.category_id AS categoryId,
            crs.component_id AS componentId,
            crs.component_type_id AS componentTypeId,
            crs.unit_cost AS unitCost,
            crs.min AS min,
            crs.max AS max,
            sc.category_name AS categoryName,
            scm.sc_component_name AS scComponentName,
            ssd.sub_scheme_name AS subSchemeName
        FROM configure_reeling_shed crs
        LEFT JOIN sc_category sc ON sc.sc_category_id = crs.category_id
        LEFT JOIN sc_component scm ON scm.sc_component_id = crs.component_id
        LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = crs.component_type_id
        WHERE crs.active = 1 AND crs.reeling_shed_id = :reelingShedId
        """, nativeQuery = true)
    Object getConfigureReelingShedByIdWithJoin(@Param("reelingShedId") Long reelingShedId);

}
