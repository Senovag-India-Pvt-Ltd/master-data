package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Caste;
import com.sericulture.masterdata.model.entity.ConfigureIcb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface ConfigureIcbRepository extends PagingAndSortingRepository<ConfigureIcb, Long> {

    Page<ConfigureIcb> findByActiveOrderByIcbIdAsc(boolean isActive, Pageable pageable);

    ConfigureIcb save(ConfigureIcb configureIcb);

    ConfigureIcb findByIcbIdAndActive(long icbId, boolean isActive);

    ConfigureIcb findByIcbIdAndActiveIn(@Param("icbId") long icbId, @Param("active") Set<Boolean> active);

    List<ConfigureIcb> findByActive(boolean isActive);

    List<ConfigureIcb> findByActiveAndIcbIdIsNot(boolean isActive, long icbId);

    // ✅ Custom query for paginated listing
    @Query(value = """
    SELECT 
        cicb.icb_id AS icbId,
        cicb.icb_basin_ends AS icbBasinEnds,
        cicb.category_id AS categoryId,
        cicb.component_id AS componentId,
        cicb.component_type_id AS componentTypeId,
        cicb.unit_cost AS unitCost,
        cicb.min AS min,
        cicb.max AS max,
        sc.category_name AS categoryName,
        scm.sc_component_name AS scComponentName,
        ssd.sub_scheme_name AS subSchemeName
    FROM configure_icb cicb
    LEFT JOIN sc_category sc ON sc.sc_category_id = cicb.category_id
    LEFT JOIN sc_component scm ON scm.sc_component_id = cicb.component_id
    LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cicb.component_type_id
    WHERE cicb.active = 1
    ORDER BY cicb.icb_id ASC
""",
            countQuery = "SELECT COUNT(*) FROM configure_icb WHERE active = true",
            nativeQuery = true)
    Page<Object[]> getConfigureIcbListWithJoin(Pageable pageable);

    // ✅ Another native query (if needed for filtering)
    @Query(nativeQuery = true, value = """
        SELECT
         cicb.icb_id,
         cicb.icb_table,
         cicb.category_id,
         cicb.component_id,
         cicb.component_type_id,
         cicb.unit_cost,
         sc.category_name,
         scm.sc_component_name,
         ssd.sub_scheme_name
     FROM
         configure_icb cicb
         LEFT JOIN sc_category sc ON sc.sc_category_id = cicb.category_id
         LEFT JOIN sc_component scm ON scm.sc_component_id = cicb.component_id
         LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cicb.component_type_id
     WHERE
         cicb.active = 1
         """)
    Page<Object[]> getByConfigureIcbIdAndActive(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query(value = """
    SELECT TOP 1
        cicb.icb_id AS icbId,
        cicb.icb_basin_ends AS icbBasinEnds,
        cicb.category_id AS categoryId,
        cicb.component_id AS componentId,
        cicb.component_type_id AS componentTypeId,
        cicb.unit_cost AS unitCost,
        cicb.min AS min,
        cicb.max AS max,
        sc.category_name AS categoryName,
        scm.sc_component_name AS scComponentName,
        ssd.sub_scheme_name AS subSchemeName
    FROM configure_icb cicb
    LEFT JOIN sc_category sc ON sc.sc_category_id = cicb.category_id
    LEFT JOIN sc_component scm ON scm.sc_component_id = cicb.component_id
    LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cicb.component_type_id
    WHERE cicb.active = 1 AND cicb.icb_id = :icbId
""", nativeQuery = true)
    Object getConfigureIcbByIdWithJoin(@Param("icbId") Long icbId);

}