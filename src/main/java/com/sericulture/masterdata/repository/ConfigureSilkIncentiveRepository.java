package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Caste;
import com.sericulture.masterdata.model.entity.ConfigureSilkIncentive;
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
public interface ConfigureSilkIncentiveRepository  extends PagingAndSortingRepository<ConfigureSilkIncentive, Long> {

    Page<ConfigureSilkIncentive> findByActiveOrderBySilkIncentiveIdAsc(boolean isActive, Pageable pageable);

    ConfigureSilkIncentive save(ConfigureSilkIncentive configureSilkIncentive);

    ConfigureSilkIncentive findBySilkIncentiveIdAndActive(long silkIncentiveId, boolean isActive);

    ConfigureSilkIncentive findBySilkIncentiveIdAndActiveIn(
            @Param("silkIncentiveId") long silkIncentiveId,
            @Param("active") Set<Boolean> active
    );

    List<ConfigureSilkIncentive> findByActive(boolean isActive);

    List<ConfigureSilkIncentive> findByActiveAndSilkIncentiveIdIsNot(boolean isActive, long silkIncentiveId);

    @Query(nativeQuery = true, value = """
            SELECT
             crs.reeling_shed_id,
             crs.reeling_unit,
             crs.sqft,
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
    Page<Object[]> getByActiveOrderByconfigureSilkIncentiveIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query(nativeQuery = true, value = """
        SELECT
         csi.silk_incentive_id,
         csi.machine_type_id,
         csi.category_id,
         csi.component_id,
         csi.component_type_id,
         csi.amount_per_kg,
         mtm.machine_type_name,
         sc.category_name,
         scm.sc_component_name,
         ssd.sub_scheme_name
     FROM
         configure_silk_incentive csi
         LEFT JOIN machine_type_master mtm ON mtm.machine_type_id = csi.machine_type_id
         LEFT JOIN sc_category sc ON sc.sc_category_id = csi.category_id
         LEFT JOIN sc_component scm ON scm.sc_component_id = csi.component_id
         LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = csi.component_type_id
     WHERE
         csi.active = 1
         """)
    Page<Object[]> getByConfigureSilkIncentiveIdAndActive(@Param("isActive") boolean isActive, final Pageable pageable);

    // ✅ Paginated joined list
    @Query(value = """
        SELECT
            csi.silk_incentive_id AS silkIncentiveId,
            csi.machine_type_id AS machineTypeId,
            csi.category_id AS categoryId,
            csi.component_id AS componentId,
            csi.component_type_id AS componentTypeId,
            csi.amount_per_kg AS amountPerKg,
            csi.min AS min,
            csi.max AS max,
            mtm.machine_type_name AS machineTypeName,
            sc.category_name AS categoryName,
            scm.sc_component_name AS scComponentName,
            ssd.sub_scheme_name AS subSchemeName
        FROM configure_silk_incentive csi
        LEFT JOIN machine_type_master mtm ON mtm.machine_type_id = csi.machine_type_id
        LEFT JOIN sc_category sc ON sc.sc_category_id = csi.category_id
        LEFT JOIN sc_component scm ON scm.sc_component_id = csi.component_id
        LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = csi.component_type_id
        WHERE csi.active = 1
        ORDER BY csi.silk_incentive_id ASC
        """,
            countQuery = "SELECT COUNT(*) FROM configure_silk_incentive WHERE active = 1",
            nativeQuery = true)
    Page<Object[]> getConfigureSilkIncentiveListWithJoin(Pageable pageable);


    // ✅ Get by ID with join
    @Query(value = """
        SELECT TOP 1
            csi.silk_incentive_id AS silkIncentiveId,
            csi.machine_type_id AS machineTypeId,
            csi.category_id AS categoryId,
            csi.component_id AS componentId,
            csi.component_type_id AS componentTypeId,
            csi.amount_per_kg AS amountPerKg,
            csi.min AS min,
            csi.max AS max,
            mtm.machine_type_name AS machineTypeName,
            sc.category_name AS categoryName,
            scm.sc_component_name AS scComponentName,
            ssd.sub_scheme_name AS subSchemeName
        FROM configure_silk_incentive csi
        LEFT JOIN machine_type_master mtm ON mtm.machine_type_id = csi.machine_type_id
        LEFT JOIN sc_category sc ON sc.sc_category_id = csi.category_id
        LEFT JOIN sc_component scm ON scm.sc_component_id = csi.component_id
        LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = csi.component_type_id
        WHERE csi.active = 1 AND csi.silk_incentive_id = :silkIncentiveId
        """, nativeQuery = true)
    Object getConfigureSilkIncentiveByIdWithJoin(@Param("silkIncentiveId") Long silkIncentiveId);
}

