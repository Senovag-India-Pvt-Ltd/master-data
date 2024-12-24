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
    WITH DirectMatch AS (
                    SELECT DISTINCT\s
                        amount
                    FROM\s
                        dbo.configure_pmkys_amount
                    WHERE\s
                        spacing_id = :spacingId
                        AND hectare_id = :hectareId
                ),
                SpacingFallback AS (
                    SELECT DISTINCT
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
                ),
                HectareFallback AS (
                    SELECT DISTINCT\s
                        c1.amount AS lowest_amount,
                        c2.amount AS highest_amount,
                        CAST(h1.hectare_master_name AS FLOAT) AS lowest_hectare_value,
                        CAST(h2.hectare_master_name AS FLOAT) AS highest_hectare_value,
                        CAST(h_target.hectare_master_name AS FLOAT) AS target_hectare_value
                    FROM\s
                        dbo.configure_pmkys_amount c1
                    INNER JOIN dbo.configure_pmkys_amount c2\s
                        ON c1.spacing_id = c2.spacing_id\s
                        AND c1.amount < c2.amount
                    INNER JOIN dbo.hectare_master h1\s
                        ON h1.hectare_master_id = c1.hectare_id
                    INNER JOIN dbo.hectare_master h2\s
                        ON h2.hectare_master_id = c2.hectare_id
                    INNER JOIN dbo.hectare_master h_target\s
                        ON h_target.hectare_master_id = :hectareId
                    WHERE\s
                        CAST(h_target.hectare_master_name AS FLOAT)\s
                        BETWEEN CAST(h1.hectare_master_name AS FLOAT)\s
                        AND CAST(h2.hectare_master_name AS FLOAT)
                        AND c1.spacing_id = :spacingId
                )
                SELECT TOP 1
                    amount
                FROM DirectMatch
                UNION
                SELECT TOP 1
                    lowest_amount +\s
                    CASE WHEN (highest_area - lowest_area) != 0 THEN ((highest_amount - lowest_amount) / (highest_area - lowest_area)) * (target_area - lowest_area)
                         ELSE 0 END AS calculated_amount
                FROM SpacingFallback
                UNION
                SELECT TOP 1
                    lowest_amount +\s
                    CASE WHEN (highest_hectare_value - lowest_hectare_value) != 0 THEN ((highest_amount - lowest_amount) / (highest_hectare_value - lowest_hectare_value)) * (highest_hectare_value - target_hectare_value)
                         ELSE 0 END AS calculated_amount
                FROM HectareFallback;
            
""")
    List<Object[]> getClosestAmountBySpacingAndHectare(@Param("spacingId") Long spacingId, @Param("hectareId") Long hectareId);


    @Query(nativeQuery = true, value = """
    SELECT
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
