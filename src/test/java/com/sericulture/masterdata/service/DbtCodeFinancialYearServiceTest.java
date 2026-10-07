package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.DbtCodeFinancialYearRequest;
import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.DbtCodeFinancialYearResponse;
import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.EditDbtCodeFinancialYearRequest;
import com.sericulture.masterdata.model.entity.FinancialYearMaster;
import com.sericulture.masterdata.model.entity.ScDbtCodeFinancialYear;
import com.sericulture.masterdata.repository.FinancialYearMasterRepository;
import com.sericulture.masterdata.repository.ScDbtCodeFinancialYearRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Proves scenario 8 from the financial-year DBT code requirement: the same
 * scheme/component/sub-scheme/quota/category-mapping cannot end up with two conflicting DBT
 * codes for the same financial year, on both add and edit — while the same code for different
 * financial years, and re-adding / editing into a year whose entry was deleted, are allowed.
 */
@ExtendWith(MockitoExtension.class)
class DbtCodeFinancialYearServiceTest {

    @Mock
    ScDbtCodeFinancialYearRepository scDbtCodeFinancialYearRepository;

    @Mock
    FinancialYearMasterRepository financialYearMasterRepository;

    @Mock
    CustomValidator validator;

    @InjectMocks
    DbtCodeFinancialYearService service;

    private DbtCodeFinancialYearRequest request(String masterType, Long parentId, Long fyId, String code) {
        DbtCodeFinancialYearRequest r = new DbtCodeFinancialYearRequest();
        r.setMasterType(masterType);
        r.setParentId(parentId);
        r.setFinancialYearMasterId(fyId);
        r.setDbtCode(code);
        return r;
    }

    private ScDbtCodeFinancialYear row(Long id, Long fyId, String code, boolean active) {
        ScDbtCodeFinancialYear e = new ScDbtCodeFinancialYear();
        e.setScDbtCodeFinancialYearId(id);
        e.setMasterType("SCHEME_QUOTA");
        e.setParentId(5L);
        e.setFinancialYearMasterId(fyId);
        e.setDbtCode(code);
        e.setActive(active);
        return e;
    }

    private void saveReturnsArgument() {
        when(scDbtCodeFinancialYearRepository.save(any())).thenAnswer(inv -> {
            ScDbtCodeFinancialYear e = inv.getArgument(0);
            if (e.getScDbtCodeFinancialYearId() == null) e.setScDbtCodeFinancialYearId(1L);
            return e;
        });
    }

    private void anyFinancialYearName() {
        FinancialYearMaster fy = new FinancialYearMaster();
        fy.setFinancialYear("2026-2027");
        lenient().when(financialYearMasterRepository.findByFinancialYearMasterIdAndActiveIn(anyLong(), any()))
                .thenReturn(fy);
    }

    @Test
    void insert_duplicateForSameFinancialYear_isRejectedWithClearError() {
        when(scDbtCodeFinancialYearRepository.findFirstByMasterTypeAndParentIdAndFinancialYearMasterId(
                "SCHEME_QUOTA", 5L, 2025L)).thenReturn(row(1L, 2025L, "80", true));

        DbtCodeFinancialYearResponse response = service.insert(request("SCHEME_QUOTA", 5L, 2025L, "80"));

        assertTrue(response.getError());
        assertNotNull(response.getError_description());
        verify(scDbtCodeFinancialYearRepository, never()).save(any());
    }

    @Test
    void insert_sameCodeForAnotherFinancialYear_isAllowed() {
        // 2025-26 already has 147; adding 147 for 2026-27 must succeed
        when(scDbtCodeFinancialYearRepository.findFirstByMasterTypeAndParentIdAndFinancialYearMasterId(
                "SCHEME_QUOTA", 5L, 2026L)).thenReturn(null);
        saveReturnsArgument();
        anyFinancialYearName();

        DbtCodeFinancialYearResponse response = service.insert(request("SCHEME_QUOTA", 5L, 2026L, "147"));

        assertFalse(response.getError());
        assertEquals("147", response.getDbtCode());
        verify(scDbtCodeFinancialYearRepository, times(1)).save(any());
    }

    @Test
    void insert_afterDelete_reusesTheDeletedRow() {
        ScDbtCodeFinancialYear deleted = row(7L, 2026L, "147", false);
        when(scDbtCodeFinancialYearRepository.findFirstByMasterTypeAndParentIdAndFinancialYearMasterId(
                "SCHEME_QUOTA", 5L, 2026L)).thenReturn(deleted);
        saveReturnsArgument();
        anyFinancialYearName();

        DbtCodeFinancialYearResponse response = service.insert(request("SCHEME_QUOTA", 5L, 2026L, "147"));

        assertFalse(response.getError());
        assertEquals(7L, response.getScDbtCodeFinancialYearId());   // same row, no new insert
        assertTrue(deleted.getActive());
    }

    @Test
    void update_intoAFinancialYearAlreadyUsedByAnotherActiveRow_isRejected() {
        when(scDbtCodeFinancialYearRepository.findByScDbtCodeFinancialYearIdAndActiveIn(10L, Set.of(true, false)))
                .thenReturn(row(10L, 2025L, "80", true));
        when(scDbtCodeFinancialYearRepository.findFirstByMasterTypeAndParentIdAndFinancialYearMasterId(
                "SCHEME_QUOTA", 5L, 2026L)).thenReturn(row(20L, 2026L, "90", true));

        DbtCodeFinancialYearResponse response = service.update(edit(10L, 2026L, "90"));

        assertTrue(response.getError());
        verify(scDbtCodeFinancialYearRepository, never()).save(any());
    }

    @Test
    void update_intoAFinancialYearWhoseEntryWasDeleted_isAllowed() {
        ScDbtCodeFinancialYear editing = row(10L, 2025L, "147", true);
        ScDbtCodeFinancialYear deleted = row(20L, 2026L, "90", false);
        when(scDbtCodeFinancialYearRepository.findByScDbtCodeFinancialYearIdAndActiveIn(10L, Set.of(true, false)))
                .thenReturn(editing);
        when(scDbtCodeFinancialYearRepository.findFirstByMasterTypeAndParentIdAndFinancialYearMasterId(
                "SCHEME_QUOTA", 5L, 2026L)).thenReturn(deleted);
        saveReturnsArgument();
        anyFinancialYearName();

        DbtCodeFinancialYearResponse response = service.update(edit(10L, 2026L, "147"));

        assertFalse(response.getError());
        assertEquals(20L, response.getScDbtCodeFinancialYearId());
        assertTrue(deleted.getActive());
        assertEquals("147", deleted.getDbtCode());
        assertFalse(editing.getActive());
    }

    @Test
    void update_inactiveRow_isAllowedAndReactivated() {
        ScDbtCodeFinancialYear inactive = row(10L, 2026L, "147", false);
        when(scDbtCodeFinancialYearRepository.findByScDbtCodeFinancialYearIdAndActiveIn(10L, Set.of(true, false)))
                .thenReturn(inactive);
        when(scDbtCodeFinancialYearRepository.findFirstByMasterTypeAndParentIdAndFinancialYearMasterId(
                "SCHEME_QUOTA", 5L, 2026L)).thenReturn(inactive);
        saveReturnsArgument();
        anyFinancialYearName();

        DbtCodeFinancialYearResponse response = service.update(edit(10L, 2026L, "148"));

        assertFalse(response.getError());
        assertTrue(inactive.getActive());
        assertEquals("148", inactive.getDbtCode());
    }

    private EditDbtCodeFinancialYearRequest edit(Long id, Long fyId, String code) {
        EditDbtCodeFinancialYearRequest req = new EditDbtCodeFinancialYearRequest();
        req.setScDbtCodeFinancialYearId(id);
        req.setMasterType("SCHEME_QUOTA");
        req.setParentId(5L);
        req.setFinancialYearMasterId(fyId);
        req.setDbtCode(code);
        return req;
    }
}
