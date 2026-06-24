package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scCategory.EditScCategoryRequest;
import com.sericulture.masterdata.model.api.scCategory.ScCategoryRequest;
import com.sericulture.masterdata.model.api.scCategory.ScCategoryResponse;
import com.sericulture.masterdata.model.entity.ScCategory;
import com.sericulture.masterdata.model.entity.ScCategorySchemeMapping;
import com.sericulture.masterdata.model.entity.ScSchemeDetails;
import com.sericulture.masterdata.model.entity.ScSubSchemeDetails;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScCategoryMappingRepository;
import com.sericulture.masterdata.repository.ScCategoryRepository;
import com.sericulture.masterdata.repository.ScSchemeDetailsRepository;
import com.sericulture.masterdata.repository.ScSubSchemeDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ScCategoryService {

    @Autowired ScCategoryRepository scCategoryRepository;
    @Autowired ScCategoryMappingRepository scCategoryMappingRepository;
    @Autowired ScSchemeDetailsRepository scSchemeDetailsRepository;
    @Autowired ScSubSchemeDetailsRepository scSubSchemeDetailsRepository;
    @Autowired Mapper mapper;
    @Autowired CustomValidator validator;

    // ── INSERT: reuse existing sc_category row, only add a new mapping row ──────
    @Transactional
    public ScCategoryResponse insertScCategoryDetails(ScCategoryRequest req) {
        ScCategoryResponse response = new ScCategoryResponse();

        // 1. Find or create the category record (unique per categoryName)
        String trimmedName = req.getCategoryName() != null ? req.getCategoryName().trim() : "";
        List<ScCategory> existing = scCategoryRepository.findByActive(true).stream()
                .filter(c -> trimmedName.equalsIgnoreCase(
                        c.getCategoryName() != null ? c.getCategoryName().trim() : ""))
                .collect(Collectors.toList());

        ScCategory category;
        if (!existing.isEmpty()) {
            // Category already exists — reuse it (update base info in case it changed)
            category = existing.get(0);
            category.setCategoryName(req.getCategoryName());
            category.setCategoryNameInKannada(req.getCategoryNameInKannada());
            category.setCodeNumber(req.getCodeNumber());
            category.setDescription(req.getDescription());
            category.setCategoryShortName(req.getCategoryShortName());
            category.setCategoryCodeForSanctionOrder(req.getCategoryCodeForSanctionOrder());
            category = scCategoryRepository.save(category);
        } else {
            // New category — check inactive first
            List<ScCategory> inactive = scCategoryRepository.findByActive(false).stream()
                    .filter(c -> trimmedName.equalsIgnoreCase(
                            c.getCategoryName() != null ? c.getCategoryName().trim() : ""))
                    .collect(Collectors.toList());
            if (!inactive.isEmpty()) {
                category = inactive.get(0);
                category.setActive(true);
            } else {
                category = mapper.scCategoryObjectToEntity(req, ScCategory.class);
                validator.validate(category);
            }
            category.setCategoryName(req.getCategoryName());
            category.setCategoryNameInKannada(req.getCategoryNameInKannada());
            category.setCodeNumber(req.getCodeNumber());
            category.setDescription(req.getDescription());
            category.setCategoryShortName(req.getCategoryShortName());
            category.setCategoryCodeForSanctionOrder(req.getCategoryCodeForSanctionOrder());
            category.setDbtCode(req.getDbtCode());
            category = scCategoryRepository.save(category);
        }

        // 2. Upsert the mapping — update dbtCode if it already exists (handles edit scenario)
        ScCategorySchemeMapping existingMapping = scCategoryMappingRepository
                .findByScCategoryIdAndSchemeIdAndSubSchemeIdAndActive(
                        category.getScCategoryId(), req.getSchemeId(), req.getSubSchemeId(), true);

        ScCategorySchemeMapping mapping;
        if (existingMapping != null) {
            // Mapping exists (active) — just update the dbtCode
            existingMapping.setDbtCode(req.getDbtCode());
            mapping = scCategoryMappingRepository.save(existingMapping);
        } else {
            // Check for inactive mapping — reactivate it
            ScCategorySchemeMapping inactiveMapping = scCategoryMappingRepository
                    .findByScCategoryIdAndSchemeIdAndSubSchemeIdAndActive(
                            category.getScCategoryId(), req.getSchemeId(), req.getSubSchemeId(), false);
            if (inactiveMapping != null) {
                inactiveMapping.setActive(true);
                inactiveMapping.setDbtCode(req.getDbtCode());
                mapping = scCategoryMappingRepository.save(inactiveMapping);
            } else {
                // Truly new mapping
                mapping = new ScCategorySchemeMapping();
                mapping.setScCategoryId(category.getScCategoryId());
                mapping.setSchemeId(req.getSchemeId());
                mapping.setSubSchemeId(req.getSubSchemeId());
                mapping.setDbtCode(req.getDbtCode());
                mapping = scCategoryMappingRepository.save(mapping);
            }
        }

        response = mapper.scCategoryEntityToObject(category, ScCategoryResponse.class);
        populateNames(response, mapping.getSchemeId(), mapping.getSubSchemeId());
        response.setDbtCode(mapping.getDbtCode());
        response.setError(false);
        return response;
    }

    // ── LIST (paginated) — returns each sc_category once; schema mappings in field ──
    public Map<String, Object> getPaginatedScCategoryDetails(final Pageable pageable) {
        Page<ScCategory> page = scCategoryRepository.findByActiveOrderByCategoryNameAsc(true, pageable);
        Map<String, Object> result = new HashMap<>();
        List<ScCategoryResponse> list = page.getContent().stream()
                .map(this::buildResponseWithMappings)
                .collect(Collectors.toList());
        result.put("scCategory", list);
        result.put("currentPage", page.getNumber());
        result.put("totalItems", page.getTotalElements());
        result.put("totalPages", page.getTotalPages());
        return result;
    }

    // ── GET-ALL (for dropdowns) — 3 unique categories, no duplicate names ─────
    public Map<String, Object> getAllByActive(boolean isActive) {
        List<ScCategory> all = scCategoryRepository.findByActive(isActive);
        List<ScCategory> distinct = new ArrayList<>(
            all.stream()
               .collect(Collectors.toMap(
                   sc -> sc.getCategoryName() != null ? sc.getCategoryName().trim() : "",
                   sc -> sc,
                   (existing, replacement) -> existing,
                   java.util.LinkedHashMap::new
               ))
               .values()
        );
        return convertListEntityToMapResponse(distinct);
    }

    // ── GET BY SCHEME + SUB-SCHEME — queries via mapping table ───────────────
    public Map<String, Object> getBySchemeId(Long schemeId) {
        List<ScCategorySchemeMapping> mappings =
                scCategoryMappingRepository.findBySchemeIdAndActive(schemeId, true);
        return buildResponsesFromMappings(mappings);
    }

    public Map<String, Object> getBySchemeIdAndSubSchemeId(Long schemeId, Long subSchemeId) {
        List<ScCategorySchemeMapping> mappings =
                scCategoryMappingRepository.findBySchemeIdAndSubSchemeIdAndActive(schemeId, subSchemeId, true);
        return buildResponsesFromMappings(mappings);
    }

    // ── DELETE SCHEME MAPPING (not the whole category) ───────────────────────
    @Transactional
    public ScCategoryResponse deleteMappingDetails(long mappingId) {
        ScCategoryResponse response = new ScCategoryResponse();
        ScCategorySchemeMapping mapping =
                scCategoryMappingRepository.findByScCategoryMappingIdAndActive(mappingId, true);
        if (mapping == null) {
            response.setError(true);
            response.setError_description("Mapping not found");
            return response;
        }
        mapping.setActive(false);
        scCategoryMappingRepository.save(mapping);
        response.setError(false);
        return response;
    }

    // ── DELETE WHOLE CATEGORY + ALL ITS MAPPINGS ─────────────────────────────
    @Transactional
    public ScCategoryResponse deleteScCategoryDetails(long id) {
        ScCategoryResponse response = new ScCategoryResponse();
        ScCategory cat = scCategoryRepository.findByScCategoryIdAndActive(id, true);
        if (cat == null) {
            response.setError(true);
            response.setError_description("Invalid Id");
            return response;
        }
        cat.setActive(false);
        scCategoryRepository.save(cat);
        // also deactivate all mappings for this category
        scCategoryMappingRepository.findByScCategoryIdAndActive(id, true)
                .forEach(m -> { m.setActive(false); scCategoryMappingRepository.save(m); });
        response.setError(false);
        return response;
    }

    // ── GET BY ID ─────────────────────────────────────────────────────────────
    public ScCategoryResponse getById(int id) {
        ScCategoryResponse response = new ScCategoryResponse();
        ScCategory cat = scCategoryRepository.findByScCategoryIdAndActive(id, true);
        if (cat == null) {
            response.setError(true);
            response.setError_description("Invalid id");
            return response;
        }
        response = buildResponseWithMappings(cat);
        response.setError(false);
        return response;
    }

    // ── UPDATE CATEGORY BASE INFO (not mappings) ──────────────────────────────
    @Transactional
    public ScCategoryResponse updateScCategoryDetails(EditScCategoryRequest req) {
        ScCategoryResponse response = new ScCategoryResponse();
        ScCategory cat = scCategoryRepository.findByScCategoryIdAndActiveIn(
                req.getScCategoryId(), Set.of(true, false));
        if (cat == null) {
            response.setError(true);
            response.setError_description("Error occurred while fetching category");
            return response;
        }
        cat.setCategoryName(req.getCategoryName());
        cat.setCategoryNameInKannada(req.getCategoryNameInKannada());
        cat.setCodeNumber(req.getCodeNumber());
        cat.setDescription(req.getDescription());
        cat.setCategoryShortName(req.getCategoryShortName());
        cat.setCategoryCodeForSanctionOrder(req.getCategoryCodeForSanctionOrder());
        cat.setActive(true);

        // If schemeId/subSchemeId/dbtCode provided in request, update the mapping table too
        if (req.getSchemeId() != null && req.getSubSchemeId() != null) {
            cat.setDbtCode(req.getDbtCode());
            // Update or create mapping entry
            ScCategorySchemeMapping mapping = scCategoryMappingRepository
                    .findByScCategoryIdAndSchemeIdAndSubSchemeIdAndActive(
                            cat.getScCategoryId(), req.getSchemeId(), req.getSubSchemeId(), true);
            if (mapping == null) {
                mapping = scCategoryMappingRepository
                        .findByScCategoryIdAndSchemeIdAndSubSchemeIdAndActive(
                                cat.getScCategoryId(), req.getSchemeId(), req.getSubSchemeId(), false);
                if (mapping == null) mapping = new ScCategorySchemeMapping();
                mapping.setScCategoryId(cat.getScCategoryId());
                mapping.setSchemeId(req.getSchemeId());
                mapping.setSubSchemeId(req.getSubSchemeId());
            }
            mapping.setDbtCode(req.getDbtCode());
            mapping.setActive(true);
            scCategoryMappingRepository.save(mapping);
        }

        ScCategory saved = scCategoryRepository.save(cat);
        response = buildResponseWithMappings(saved);
        response.setError(false);
        return response;
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────

    private ScCategoryResponse buildResponseWithMappings(ScCategory cat) {
        ScCategoryResponse r = mapper.scCategoryEntityToObject(cat, ScCategoryResponse.class);
        List<ScCategorySchemeMapping> mappings =
                scCategoryMappingRepository.findByScCategoryIdAndActive(cat.getScCategoryId(), true);
        List<Map<String, Object>> mappingList = mappings.stream().map(m -> {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("scCategoryMappingId", m.getScCategoryMappingId());
            entry.put("schemeId", m.getSchemeId());
            entry.put("subSchemeId", m.getSubSchemeId());
            entry.put("dbtCode", m.getDbtCode());
            // Populate scheme/sub-scheme names
            if (m.getSchemeId() != null) {
                ScSchemeDetails scheme = scSchemeDetailsRepository
                        .findByScSchemeDetailsIdAndActive(m.getSchemeId(), true);
                entry.put("schemeName", scheme != null ? scheme.getSchemeName() : null);
            }
            if (m.getSubSchemeId() != null) {
                ScSubSchemeDetails sub = scSubSchemeDetailsRepository
                        .findByScSubSchemeDetailsIdAndActive(m.getSubSchemeId(), true);
                entry.put("subSchemeName", sub != null ? sub.getSubSchemeName() : null);
            }
            return entry;
        }).collect(Collectors.toList());
        r.setMappings(mappingList);

        // Populate top-level scheme info from first mapping (backward compat)
        if (!mappings.isEmpty()) {
            populateNames(r, mappings.get(0).getSchemeId(), mappings.get(0).getSubSchemeId());
            r.setDbtCode(mappings.get(0).getDbtCode());
        }
        return r;
    }

    private Map<String, Object> buildResponsesFromMappings(List<ScCategorySchemeMapping> mappings) {
        Map<String, Object> result = new HashMap<>();
        List<ScCategoryResponse> list = mappings.stream().map(m -> {
            ScCategory cat = scCategoryRepository.findByScCategoryIdAndActive(m.getScCategoryId(), true);
            if (cat == null) return null;
            ScCategoryResponse r = mapper.scCategoryEntityToObject(cat, ScCategoryResponse.class);
            r.setSchemeId(m.getSchemeId());
            r.setSubSchemeId(m.getSubSchemeId());
            r.setDbtCode(m.getDbtCode());
            populateNames(r, m.getSchemeId(), m.getSubSchemeId());
            return r;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        result.put("scCategory", list);
        return result;
    }

    private void populateNames(ScCategoryResponse response, Long schemeId, Long subSchemeId) {
        if (schemeId != null) {
            ScSchemeDetails scheme = scSchemeDetailsRepository.findByScSchemeDetailsIdAndActive(schemeId, true);
            if (scheme != null) response.setSchemeName(scheme.getSchemeName());
        }
        if (subSchemeId != null) {
            ScSubSchemeDetails sub = scSubSchemeDetailsRepository.findByScSubSchemeDetailsIdAndActive(subSchemeId, true);
            if (sub != null) response.setSubSchemeName(sub.getSubSchemeName());
        }
    }

    private void populateNames(ScCategoryResponse response) {
        populateNames(response, response.getSchemeId(), response.getSubSchemeId());
    }

    private Map<String, Object> convertListEntityToMapResponse(List<ScCategory> list) {
        Map<String, Object> response = new HashMap<>();
        List<ScCategoryResponse> responses = list.stream()
                .map(sc -> {
                    ScCategoryResponse r = mapper.scCategoryEntityToObject(sc, ScCategoryResponse.class);
                    populateNames(r);
                    return r;
                }).collect(Collectors.toList());
        response.put("scCategory", responses);
        return response;
    }
}
