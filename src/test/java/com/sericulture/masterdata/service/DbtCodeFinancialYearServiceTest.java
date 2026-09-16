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
import static org.mockito.Mockito.*;

/**
 * Proves scenario 8 from the financial-year DBT code requirement: the same
 * scheme/component/sub-scheme/quota/category-mapping cannot end up with two conflicting DBT
 * codes for the same financial year, on both add and edit.
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

    @Test
    void insert_duplicateForSameFinancialYear_isRejectedWithClearError() {
        when(scDbtCodeFinancialYearRepository.findByMasterTypeAndParentIdAndFinancialYearMasterIdAndActive(
                "SCHEME_QUOTA", 5L, 2025L, true))
                .thenReturn(new ScDbtCodeFinancialYear());

        DbtCodeFinancialYearResponse response = service.insert(request("SCHEME_QUOTA", 5L, 2025L, "80"));

        assertTrue(response.getError());
        assertNotNull(response.getError_description());
        verify(scDbtCodeFinancialYearRepository, never()).save(any());
    }

    @Test
    void insert_distinctFinancialYearsForSameScheme_bothSucceed() {
        when(scDbtCodeFinancialYearRepository.findByMasterTypeAndParentIdAndFinancialYearMasterIdAndActive(
                "SCHEME_QUOTA", 5L, 2025L, true)).thenReturn(null);
        when(scDbtCodeFinancialYearRepository.save(any())).thenAnswer(inv -> {
            ScDbtCodeFinancialYear e = inv.getArgument(0);
            e.setScDbtCodeFinancialYearId(1L);
            return e;
        });
        FinancialYearMaster fy = new FinancialYearMaster();
        fy.setFinancialYear("2025-2026");
        when(financialYearMasterRepository.findByFinancialYearMasterIdAndActiveIn(2025L, Set.of(true, false)))
                .thenReturn(fy);

        DbtCodeFinancialYearResponse response = service.insert(request("SCHEME_QUOTA", 5L, 2025L, "80"));

        assertFalse(response.getError());
        assertEquals("80", response.getDbtCode());
        verify(scDbtCodeFinancialYearRepository, times(1)).save(any());
    }

    @Test
    void update_intoAFinancialYearAlreadyUsedByAnotherRow_isRejected() {
        ScDbtCodeFinancialYear existing = new ScDbtCodeFinancialYear();
        existing.setScDbtCodeFinancialYearId(10L);
        when(scDbtCodeFinancialYearRepository.findByScDbtCodeFinancialYearIdAndActiveIn(10L, Set.of(true, false)))
                .thenReturn(existing);

        ScDbtCodeFinancialYear conflict = new ScDbtCodeFinancialYear();
        conflict.setScDbtCodeFinancialYearId(20L); // a different row already owns 2026
        when(scDbtCodeFinancialYearRepository.findByMasterTypeAndParentIdAndFinancialYearMasterIdAndActive(
                "SCHEME_QUOTA", 5L, 2026L, true)).thenReturn(conflict);

        EditDbtCodeFinancialYearRequest req = new EditDbtCodeFinancialYearRequest();
        req.setScDbtCodeFinancialYearId(10L);
        req.setMasterType("SCHEME_QUOTA");
        req.setParentId(5L);
        req.setFinancialYearMasterId(2026L);
        req.setDbtCode("90");

        DbtCodeFinancialYearResponse response = service.update(req);

        assertTrue(response.getError());
        verify(scDbtCodeFinancialYearRepository, never()).save(any());
    }
}
