package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ConfigureAdoptingBoiler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ConfigureAdoptingBoilerRepository extends JpaRepository<ConfigureAdoptingBoiler, Long> {


    Page<ConfigureAdoptingBoiler> findByActiveOrderByAdoptingBoilerIdAsc(boolean isActive, Pageable pageable);

    ConfigureAdoptingBoiler save(ConfigureAdoptingBoiler configureAdoptingBoiler);

    // ✅ Removed BasinEnds — now purely ID-based methods

    ConfigureAdoptingBoiler findByAdoptingBoilerIdAndActive(long adoptingBoilerId, boolean isActive);

    ConfigureAdoptingBoiler findByAdoptingBoilerIdAndActiveIn(@Param("adoptingBoilerId") long adoptingBoilerId, @Param("active") Set<Boolean> active);

    public List<ConfigureAdoptingBoiler> findByBoilerInKgAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(Float boilerInKg, long componentTypeId, long componentId, long categoryId, boolean isActive);

    List<ConfigureAdoptingBoiler> findByActive(boolean isActive);

    List<ConfigureAdoptingBoiler> findByActiveAndAdoptingBoilerIdIsNot(boolean isActive, long adoptingBoilerId);

    @Query(value = """
        SELECT
         cab.adopting_boiler_id,
         cab.boiler_in_kg,
         cab.category_id,
         cab.component_id,
         cab.component_type_id,
         cab.unit_cost,
         cab.min,
         cab.max,
         sc.category_name,
         scm.sc_component_name,
         ssd.sub_scheme_name
         FROM configure_adopting_boiler cab
         LEFT JOIN sc_category sc ON sc.sc_category_id = cab.category_id
         LEFT JOIN sc_component scm ON scm.sc_component_id = cab.component_id
         LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cab.component_type_id
     WHERE
         cab.active = 1
        ORDER BY cab.adopting_boiler_id ASC
        """,
            countQuery = "SELECT COUNT(*) FROM configure_adopting_boiler WHERE active = 1",
            nativeQuery = true)
    Page<Object[]> getConfigureAdoptingBoilerListWithJoin(Pageable pageable);

    /**
     * ✅ Get by ID (with joins)
     */
    @Query(value = """
        SELECT TOP 1
            cab.adopting_boiler_id AS adoptingBoilerId,
            cab.boiler_in_kg AS boilerInKg,
            cab.category_id AS categoryId,
            cab.component_id AS componentId,
            cab.component_type_id AS componentTypeId,
            cab.unit_cost AS unitCost,
            cab.min AS min,
            cab.max AS max,
            sc.category_name AS categoryName,
            scm.sc_component_name AS scComponentName,
            ssd.sub_scheme_name AS subSchemeName
         FROM configure_adopting_boiler cab 
        LEFT JOIN sc_category sc ON sc.sc_category_id = cab.category_id
        LEFT JOIN sc_component scm ON scm.sc_component_id = cab.component_id
        LEFT JOIN sc_sub_scheme_details ssd ON ssd.sc_sub_scheme_details_id = cab.component_type_id
        WHERE cab.active = 1 AND cab.adopting_boiler_id = :adoptingBoilerId
        """,
            nativeQuery = true)
    Object getConfigureAdoptingBoilerByIdWithJoin(@Param("adoptingBoilerId") Long adoptingBoilerId);



}
