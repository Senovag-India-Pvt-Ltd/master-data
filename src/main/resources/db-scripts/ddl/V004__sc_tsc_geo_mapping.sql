-- =====================================================================
-- TSC geo-mapping master (Seva Sindhu integration, Phase 1)
-- ---------------------------------------------------------------------
-- Maps a District/Taluk/Hobli/Village combination to a TSC (Technical
-- Service Center) and, optionally, a specific officer (user_master).
-- This lets a scheme application be auto-routed to the right TSC based
-- purely on its geography, instead of a purely manual Helpdesk
-- assignment every time.
--
-- Matching grain: a row may specify hobli_id and/or village_id as NULL
-- to represent a coarser mapping. Lookup logic (in the application code)
-- prefers the most specific active row that matches -- an exact village
-- match first, falling back to a hobli-level row (village_id NULL) for
-- the same taluk when no village-level row exists. This lets mappings
-- be rolled out village-by-village without blocking auto-assignment
-- everywhere until every village is mapped.
--
-- This table is purely additive and has no bearing on any existing
-- table -- sc_application_form, farmer, tsc_master etc. are untouched.
--
-- ddl-auto is 'none' for the master-data module, so this must be run
-- manually. The script is idempotent -- safe to re-run.
-- (SQL Server syntax.)
-- =====================================================================

IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.sc_tsc_geo_mapping') AND type = N'U')
BEGIN
    CREATE TABLE dbo.sc_tsc_geo_mapping (
        sc_tsc_geo_mapping_id BIGINT NOT NULL PRIMARY KEY,
        district_id BIGINT NOT NULL,
        taluk_id BIGINT NOT NULL,
        hobli_id BIGINT NULL,
        village_id BIGINT NULL,
        tsc_master_id BIGINT NOT NULL,
        officer_user_id BIGINT NULL,
        CREATED_BY VARCHAR(255) NULL,
        MODIFIED_BY VARCHAR(255) NULL,
        CREATED_DATE DATETIME NULL,
        MODIFIED_DATE DATETIME NULL,
        ACTIVE TINYINT NULL
    );
END
GO

-- Uniqueness applies to ACTIVE rows only: delete is a soft delete (ACTIVE = 0),
-- so a plain UNIQUE constraint would block re-adding a geography after its
-- mapping was deleted. Drops the earlier unfiltered constraint if a previous
-- version of this script already created it.
IF EXISTS (SELECT 1 FROM sys.key_constraints WHERE name = N'UQ_sc_tsc_geo_mapping' AND parent_object_id = OBJECT_ID(N'dbo.sc_tsc_geo_mapping'))
BEGIN
    ALTER TABLE dbo.sc_tsc_geo_mapping DROP CONSTRAINT UQ_sc_tsc_geo_mapping;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'UX_sc_tsc_geo_mapping_active' AND object_id = OBJECT_ID(N'dbo.sc_tsc_geo_mapping'))
BEGIN
    CREATE UNIQUE INDEX UX_sc_tsc_geo_mapping_active
        ON dbo.sc_tsc_geo_mapping (district_id, taluk_id, hobli_id, village_id)
        WHERE ACTIVE = 1;
END
GO

IF NOT EXISTS (SELECT 1 FROM sys.sequences WHERE name = N'sc_tsc_geo_mapping_seq')
BEGIN
    CREATE SEQUENCE dbo.sc_tsc_geo_mapping_seq AS BIGINT START WITH 1 INCREMENT BY 1;
END
GO
