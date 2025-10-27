package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ConfigureImcb;
import com.sericulture.masterdata.model.entity.ConfigureImcb;
import com.sericulture.masterdata.model.entity.ConfigureSilkIncentive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ConfigureImcbRepository extends PagingAndSortingRepository<ConfigureImcb, Long> {

    Page<ConfigureImcb> findByActiveOrderByImcbIdAsc(boolean isActive, Pageable pageable);

    ConfigureImcb save(ConfigureImcb configureImcb);

    // ✅ Removed BasinEnds — now purely ID-based methods

    ConfigureImcb findByImcbIdAndActive(long imcbId, boolean isActive);

    ConfigureImcb findByImcbIdAndActiveIn(@Param("imcbId") long imcbId, @Param("active") Set<Boolean> active);

    public List<ConfigureImcb> findByImcbTableAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(String imcbTable,long componentTypeId, long componentId, long categoryId, boolean isActive);

    List<ConfigureImcb> findByActive(boolean isActive);

    List<ConfigureImcb> findByActiveAndImcbIdIsNot(boolean isActive, long imcbId);
    
    @Query(nativeQuery = true, value = """
        SELECT
         cim.imcb_id,
         cim.imcb_table,
         cim.category_id,
         cim.component_id,
         cim.component_type_id,
         cim.unit_cost,
         sc.category_name,
         scm.sc_component_name,
         ssd.sub_scheme_name
     FROM
         configure_imcb cim
         LEFT JOIN sc_category sc ON sc.sc_category_id = cim.category_id
         LEFT JOIN sc_component scm ON scm.sc_component_id = cim.component_id
         LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cim.component_type_id
     WHERE
         cim.active = 1
         """)
    Page<Object[]> getByActiveOrderByconfigureImmcbIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query(nativeQuery = true, value = """
            SELECT
         cim.imcb_id,
         cim.imcb_table,
         cim.category_id,
         cim.component_id,
         cim.component_type_id,
         cim.unit_cost,
         sc.category_name,
         scm.sc_component_name,
         ssd.sub_scheme_name
     FROM
         configure_imcb cim
         LEFT JOIN sc_category sc ON sc.sc_category_id = cim.category_id
         LEFT JOIN sc_component scm ON scm.sc_component_id = cim.component_id
         LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cim.component_type_id
     WHERE
         cim.active = 1
         """)
    Page<Object[]> getByConfigureImmcbIdAndActive(@Param("isActive") boolean isActive, final Pageable pageable);


    /**
     * ✅ Paginated list with join (Category, Component, SubScheme)
     */
    @Query(value = """
        SELECT 
            cim.imcb_id AS imcbId,
            cim.imcb_table AS imcbTable,
            cim.category_id AS categoryId,
            cim.component_id AS componentId,
            cim.component_type_id AS componentTypeId,
            cim.unit_cost AS unitCost,
            cim.min AS min,
            cim.max AS max,
            sc.category_name AS categoryName,
            scm.sc_component_name AS scComponentName,
            ssd.sub_scheme_name AS subSchemeName
        FROM configure_imcb cim
        LEFT JOIN sc_category sc ON sc.sc_category_id = cim.category_id
        LEFT JOIN sc_component scm ON scm.sc_component_id = cim.component_id
        LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cim.component_type_id
        WHERE cim.active = 1
        ORDER BY cim.imcb_id ASC
        """,
            countQuery = "SELECT COUNT(*) FROM configure_imcb WHERE active = 1",
            nativeQuery = true)
    Page<Object[]> getConfigureImcbListWithJoin(Pageable pageable);

    /**
     * ✅ Get by ID (with joins)
     */
    @Query(value = """
        SELECT TOP 1
            cim.imcb_id AS imcbId,
            cim.imcb_table AS imcbTable,
            cim.category_id AS categoryId,
            cim.component_id AS componentId,
            cim.component_type_id AS componentTypeId,
            cim.unit_cost AS unitCost,
            cim.min AS min,
            cim.max AS max,
            sc.category_name AS categoryName,
            scm.sc_component_name AS scComponentName,
            ssd.sub_scheme_name AS subSchemeName
        FROM configure_imcb cim
        LEFT JOIN sc_category sc ON sc.sc_category_id = cim.category_id
        LEFT JOIN sc_component scm ON scm.sc_component_id = cim.component_id
        LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cim.component_type_id
        WHERE cim.active = 1 AND cim.imcb_id = :imcbId
        """,
            nativeQuery = true)
    Object getConfigureImcbByIdWithJoin(@Param("imcbId") Long imcbId);



}
