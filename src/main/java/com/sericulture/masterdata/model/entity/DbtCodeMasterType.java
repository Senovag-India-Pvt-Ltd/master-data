package com.sericulture.masterdata.model.entity;

/**
 * Identifies which master table a {@link ScDbtCodeFinancialYear} row's parent_id points at.
 * SC_CATEGORY_SCHEME_MAPPING -> sc_category_scheme_mapping.sc_category_mapping_id, which is the
 * row actually read for the "category" DBT code during K2/DBT push (not sc_category.dbt_code).
 */
public enum DbtCodeMasterType {
    SC_COMPONENT,
    SC_SUB_SCHEME_DETAILS,
    SCHEME_QUOTA,
    SC_CATEGORY_SCHEME_MAPPING,
    SC_CATEGORY,
    SC_SCHEME_DETAILS
}
