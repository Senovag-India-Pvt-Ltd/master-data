package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.DbtCodeFinancialYearRequest;
import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.DbtCodeFinancialYearResponse;
import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.EditDbtCodeFinancialYearRequest;
import com.sericulture.masterdata.model.entity.FinancialYearMaster;
import com.sericulture.masterdata.model.entity.ScDbtCodeFinancialYear;
import com.sericulture.masterdata.repository.FinancialYearMasterRepository;
import com.sericulture.masterdata.repository.ScDbtCodeFinancialYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manages per-financial-year DBT code overrides for a scheme/component/sub-scheme/quota/
 * category-mapping item (sc_dbt_code_financial_year). One shared service/controller backs
 * every master type instead of duplicating this CRUD six times.
 */
@Service
@Slf4j
public class DbtCodeFinancialYearService {

    @Autowired
    ScDbtCodeFinancialYearRepository scDbtCodeFinancialYearRepository;

    @Autowired
    FinancialYearMasterRepository financialYearMasterRepository;

    @Autowired
    CustomValidator validator;

    public Map<String, Object> listByMasterTypeAndParent(String masterType, Long parentId) {
        List<ScDbtCodeFinancialYear> rows = scDbtCodeFinancialYearRepository
                .findByMasterTypeAndParentIdAndActiveOrderByFinancialYearMasterIdAsc(masterType, parentId, true);
        Map<String, Object> response = new HashMap<>();
        response.put("dbtCodeFinancialYearList", rows.stream().map(this::toResponse).collect(Collectors.toList()));
        return response;
    }

    @Transactional
    public DbtCodeFinancialYearResponse insert(DbtCodeFinancialYearRequest request) {
        DbtCodeFinancialYearResponse response = new DbtCodeFinancialYearResponse();

        ScDbtCodeFinancialYear existing = scDbtCodeFinancialYearRepository
                .findByMasterTypeAndParentIdAndFinancialYearMasterIdAndActive(
                        request.getMasterType(), request.getParentId(), request.getFinancialYearMasterId(), true);
        if (existing != null) {
            response.setError(true);
            response.setError_description("DBT code already configured for this Financial Year. Edit the existing entry instead.");
            return response;
        }

        ScDbtCodeFinancialYear entity = new ScDbtCodeFinancialYear();
        entity.setMasterType(request.getMasterType());
        entity.setParentId(request.getParentId());
        entity.setFinancialYearMasterId(request.getFinancialYearMasterId());
        entity.setDbtCode(request.getDbtCode());
        validator.validate(entity);

        entity = scDbtCodeFinancialYearRepository.save(entity);
        response = toResponse(entity);
        response.setError(false);
        return response;
    }

    @Transactional
    public DbtCodeFinancialYearResponse update(EditDbtCodeFinancialYearRequest request) {
        DbtCodeFinancialYearResponse response = new DbtCodeFinancialYearResponse();

        ScDbtCodeFinancialYear entity = scDbtCodeFinancialYearRepository
                .findByScDbtCodeFinancialYearIdAndActiveIn(request.getScDbtCodeFinancialYearId(), Set.of(true, false));
        if (entity == null) {
            response.setError(true);
            response.setError_description("Invalid Id");
            return response;
        }

        ScDbtCodeFinancialYear conflict = scDbtCodeFinancialYearRepository
                .findByMasterTypeAndParentIdAndFinancialYearMasterIdAndActive(
                        request.getMasterType(), request.getParentId(), request.getFinancialYearMasterId(), true);
        if (conflict != null && !Objects.equals(conflict.getScDbtCodeFinancialYearId(), request.getScDbtCodeFinancialYearId())) {
            response.setError(true);
            response.setError_description("DBT code already configured for this Financial Year.");
            return response;
        }

        entity.setMasterType(request.getMasterType());
        entity.setParentId(request.getParentId());
        entity.setFinancialYearMasterId(request.getFinancialYearMasterId());
        entity.setDbtCode(request.getDbtCode());
        entity.setActive(true);
        validator.validate(entity);

        entity = scDbtCodeFinancialYearRepository.save(entity);
        response = toResponse(entity);
        response.setError(false);
        return response;
    }

    @Transactional
    public DbtCodeFinancialYearResponse delete(long id) {
        DbtCodeFinancialYearResponse response = new DbtCodeFinancialYearResponse();
        ScDbtCodeFinancialYear entity = scDbtCodeFinancialYearRepository
                .findByScDbtCodeFinancialYearIdAndActiveIn(id, Set.of(true, false));
        if (entity == null) {
            response.setError(true);
            response.setError_description("Invalid Id");
            return response;
        }
        entity.setActive(false);
        response = toResponse(scDbtCodeFinancialYearRepository.save(entity));
        response.setError(false);
        return response;
    }

    private DbtCodeFinancialYearResponse toResponse(ScDbtCodeFinancialYear entity) {
        DbtCodeFinancialYearResponse response = new DbtCodeFinancialYearResponse();
        response.setScDbtCodeFinancialYearId(entity.getScDbtCodeFinancialYearId());
        response.setMasterType(entity.getMasterType());
        response.setParentId(entity.getParentId());
        response.setFinancialYearMasterId(entity.getFinancialYearMasterId());
        response.setDbtCode(entity.getDbtCode());
        FinancialYearMaster fy = financialYearMasterRepository
                .findByFinancialYearMasterIdAndActiveIn(entity.getFinancialYearMasterId(), Set.of(true, false));
        if (fy != null) {
            response.setFinancialYear(fy.getFinancialYear());
        }
        return response;
    }
}
