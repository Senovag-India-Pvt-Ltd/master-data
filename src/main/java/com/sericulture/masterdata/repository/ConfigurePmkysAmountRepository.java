package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ConfigurePmkysAmount;
import com.sericulture.masterdata.model.entity.ConfigurePmkysAmount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ConfigurePmkysAmountRepository extends PagingAndSortingRepository<ConfigurePmkysAmount, Long> {


    public ConfigurePmkysAmount save(ConfigurePmkysAmount ConfigurePmkysAmount);

    public ConfigurePmkysAmount findByConfigurePmkysAmountIdAndActive(long id, boolean isActive);


    public ConfigurePmkysAmount findByConfigurePmkysAmountIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);

    public List<ConfigurePmkysAmount> findByActive(boolean isActive);

    @Query(nativeQuery = true, value = """
    WITH DirectMatch AS (
          SELECT
              amount
          FROM configure_pmkys_amount
          WHERE
              spacing_id = :spacingId
              AND hectare_id = :hectareId
              And cpa.active = 1
      )
      SELECT *
      FROM DirectMatch;
    """)
    List<Object[]> getAmountBySpacingAndHectare(@Param("spacingId") Long spacingId,@Param("hectareId") Long hectareId);

    @Query(nativeQuery = true, value = """
   WITH SpacingFallback AS (
   SELECT\s
       c1.amount AS lowest_amount,
       c2.amount AS highest_amount,
       CAST(s1.length AS FLOAT) * CAST(s1.breadth AS FLOAT) AS lowest_area,
       CAST(s2.length AS FLOAT) * CAST(s2.breadth AS FLOAT) AS highest_area,
       CAST(s_target.length AS FLOAT) * CAST(s_target.breadth AS FLOAT) AS target_area
   FROM\s
       dbo.configure_pmkys_amount c1
   INNER JOIN dbo.configure_pmkys_amount c2\s
       ON c1.hectare_id = c2.hectare_id\s
       AND c1.amount < c2.amount
   INNER JOIN dbo.spacing_master s1\s
       ON s1.spacing_master_id = c1.spacing_id
   INNER JOIN dbo.spacing_master s2\s
       ON s2.spacing_master_id = c2.spacing_id
   INNER JOIN dbo.spacing_master s_target\s
       ON s_target.spacing_master_id = :spacingId
   WHERE\s
       (CAST(s_target.length AS FLOAT) * CAST(s_target.breadth AS FLOAT))\s
       BETWEEN (CAST(s1.length AS FLOAT) * CAST(s1.breadth AS FLOAT))\s
       AND (CAST(s2.length AS FLOAT) * CAST(s2.breadth AS FLOAT))
       AND c1.hectare_id = :hectareId
)
SELECT * FROM SpacingFallback;
    """)
    List<Object[]> getClosestRecordsSpacingAndHectare(@Param("spacingId") Long spacingId,@Param("hectareId") Long hectareId);

    @Query(nativeQuery = true, value = """
    SELECT\s
        cpa.configure_pmkys_amount_id,
        cpa.spacing_id,
        cpa.hectare_id,
        cpa.amount,
        sm.spacing_master_name,
        hm.hectare_master_name\s
    FROM\s
        configure_pmkys_amount cpa\s
        LEFT JOIN spacing_master sm ON sm.spacing_master_id = cpa.spacing_id\s
        LEFT JOIN hectare_master hm ON hm.hectare_master_id = cpa.hectare_id\s
    WHERE\s
        cpa.configure_pmkys_amount_id = :configurePmkysAmountId
        And cpa.active = 1
    """)
    List<Object[]> getListOfConfigurePmkysAmount(@Param("configurePmkysAmountId") Long configurePmkysAmountId);


    @Query(nativeQuery = true, value = """
    SELECT\s
        cpa.configure_pmkys_amount_id,
        cpa.spacing_id,
        cpa.hectare_id,
        cpa.amount,
        sm.spacing_master_name,
        hm.hectare_master_name\s
    FROM\s
        configure_pmkys_amount cpa\s
        LEFT JOIN spacing_master sm ON sm.spacing_master_id = cpa.spacing_id\s
        LEFT JOIN hectare_master hm ON hm.hectare_master_id = cpa.hectare_id\s
        WHERE\s
              cpa.active = 1
    """)
    Page<Object[]> getListOfConfigurePmkysAmountDetails(Pageable pageable);
}
