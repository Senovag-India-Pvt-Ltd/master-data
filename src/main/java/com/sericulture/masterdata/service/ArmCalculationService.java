package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.armCalculation.ArmCalculationRequest;
import com.sericulture.masterdata.model.api.armCalculation.ArmCalculationResponse;
import com.sericulture.masterdata.model.api.armCalculation.ArmUnitPriceResponse;
import com.sericulture.masterdata.model.api.armCalculation.EditArmCalculationRequest;
import com.sericulture.masterdata.model.entity.ArmCalculation;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ArmCalculationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArmCalculationService {

    @Autowired
    ArmCalculationRepository armCalculationRepository;

    @Autowired
    Mapper mapper;

    // ── INSERT ────────────────────────────────────────────────────────────────
    @Transactional
    public ArmCalculationResponse insertArmCalculationDetails(ArmCalculationRequest request) {
        ArmCalculation entity = mapper.armCalculationObjectToEntity(request, ArmCalculation.class);
        entity = armCalculationRepository.save(entity);
        return toResponse(entity);
    }

    // ── LIST (paginated) ──────────────────────────────────────────────────────
    public Map<String, Object> getPaginatedArmCalculationDetails(Pageable pageable) {
        Page<ArmCalculation> page = armCalculationRepository.findByActiveOrderByArmCalculationIdDesc(true, pageable);
        Map<String, Object> result = new HashMap<>();
        List<ArmCalculationResponse> list = page.getContent().stream().map(this::toResponse).collect(Collectors.toList());
        result.put("armCalculation", list);
        result.put("currentPage", page.getNumber());
        result.put("totalItems", page.getTotalElements());
        result.put("totalPages", page.getTotalPages());
        return result;
    }

    // ── GET ALL (for dropdowns) ───────────────────────────────────────────────
    public Map<String, Object> getAllByActive(boolean active) {
        List<ArmCalculation> all = armCalculationRepository.findByActive(active);
        Map<String, Object> result = new HashMap<>();
        result.put("armCalculation", all.stream().map(this::toResponse).collect(Collectors.toList()));
        return result;
    }

    // ── GET BY CATEGORY + COMPONENT ──────────────────────────────────────────
    public Map<String, Object> getByCategoryAndComponent(Long scCategoryId, Long componentId) {
        List<ArmCalculation> list = armCalculationRepository
                .findByScCategoryIdAndComponentIdAndActive(scCategoryId, componentId, true);
        Map<String, Object> result = new HashMap<>();
        result.put("armCalculation", list.stream().map(this::toResponse).collect(Collectors.toList()));
        return result;
    }

    // ── GET UNIT PRICE (sum unit cost by armEnds + category) ─────────────────
    public ArmUnitPriceResponse getUnitPriceByEndsAndCategory(String armEnds, Long scCategoryId) {
        ArmUnitPriceResponse resp = new ArmUnitPriceResponse();
        if (armEnds == null || armEnds.isBlank() || scCategoryId == null) {
            resp.setError(true);
            resp.setError_description("armEnds and scCategoryId are required");
            return resp;
        }
        // Use DESC ordering so rows.get(0) is the latest-inserted row (matches the list page display)
        List<ArmCalculation> rows = armCalculationRepository
                .findByArmEndsAndScCategoryIdAndActiveOrderByArmCalculationIdDesc(armEnds, scCategoryId, true);
        if (rows.isEmpty()) {
            resp.setError(true);
            resp.setError_description("No ARM calculation records found for the given armEnds and category");
            return resp;
        }
        BigDecimal totalUnitCost = rows.stream()
                .map(r -> r.getUnitCost() != null ? r.getUnitCost() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Take percentage from latest row (highest ID) — same row the list page header shows
        BigDecimal centralPct = rows.get(0).getCentralPercentage() != null
                ? rows.get(0).getCentralPercentage() : BigDecimal.ZERO;
        BigDecimal statePct = rows.get(0).getStatePercentage() != null
                ? rows.get(0).getStatePercentage() : BigDecimal.ZERO;
        BigDecimal totalPct = centralPct.add(statePct);
        BigDecimal subsidyAmt = totalUnitCost.multiply(totalPct)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        resp.setArmEnds(armEnds);
        resp.setScCategoryId(scCategoryId);
        resp.setComponentCount(rows.size());
        resp.setTotalUnitCost(totalUnitCost);
        resp.setCentralPercentage(centralPct);
        resp.setStatePercentage(statePct);
        resp.setSubsidyAmount(subsidyAmt);
        resp.setError(false);
        return resp;
    }

    // ── GET BY ARM ENDS + CATEGORY ───────────────────────────────────────────
    public Map<String, Object> getByArmEndsAndCategory(String armEnds, Long scCategoryId) {
        List<ArmCalculation> list = armCalculationRepository
                .findByArmEndsAndScCategoryIdAndActive(armEnds, scCategoryId, true);
        Map<String, Object> result = new HashMap<>();
        result.put("armCalculation", list.stream().map(this::toResponse).collect(Collectors.toList()));
        return result;
    }

    // ── GET BY CATEGORY ───────────────────────────────────────────────────────
    public Map<String, Object> getByCategory(Long scCategoryId) {
        List<ArmCalculation> list = armCalculationRepository.findByScCategoryIdAndActive(scCategoryId, true);
        Map<String, Object> result = new HashMap<>();
        result.put("armCalculation", list.stream().map(this::toResponse).collect(Collectors.toList()));
        return result;
    }

    // ── GET BY ID ─────────────────────────────────────────────────────────────
    public ArmCalculationResponse getById(long id) {
        ArmCalculation entity = armCalculationRepository.findByArmCalculationIdAndActive(id, true);
        if (entity == null) {
            ArmCalculationResponse r = new ArmCalculationResponse();
            r.setError(true); r.setError_description("Record not found");
            return r;
        }
        return toResponse(entity);
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    @Transactional
    public ArmCalculationResponse updateArmCalculationDetails(EditArmCalculationRequest request) {
        ArmCalculation entity = armCalculationRepository.findByArmCalculationIdAndActive(request.getArmCalculationId(), true);
        if (entity == null) {
            ArmCalculationResponse r = new ArmCalculationResponse();
            r.setError(true); r.setError_description("Record not found");
            return r;
        }
        entity.setScCategoryId(request.getScCategoryId());
        entity.setComponentId(request.getComponentId());
        entity.setComponentTypeId(request.getComponentTypeId());
        entity.setEquipmentName(request.getEquipmentName());
        entity.setQuantity(request.getQuantity());
        entity.setUnitRate(request.getUnitRate());
        entity.setUnitCost(request.getUnitCost());
        entity.setCentralPercentage(request.getCentralPercentage());
        entity.setStatePercentage(request.getStatePercentage());
        entity.setAdvancePercentage(request.getAdvancePercentage());
        entity.setFirstPayment(request.getFirstPayment());
        entity.setFinalPayment(request.getFinalPayment());
        entity.setArmEnds(request.getArmEnds());
        entity = armCalculationRepository.save(entity);
        return toResponse(entity);
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    @Transactional
    public ArmCalculationResponse deleteArmCalculationDetails(long id) {
        ArmCalculation entity = armCalculationRepository.findByArmCalculationIdAndActive(id, true);
        if (entity == null) {
            ArmCalculationResponse r = new ArmCalculationResponse();
            r.setError(true); r.setError_description("Record not found");
            return r;
        }
        entity.setActive(false);
        armCalculationRepository.save(entity);
        ArmCalculationResponse r = new ArmCalculationResponse();
        r.setError(false);
        return r;
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    private ArmCalculationResponse toResponse(ArmCalculation e) {
        ArmCalculationResponse r = new ArmCalculationResponse();
        r.setArmCalculationId(e.getArmCalculationId());
        r.setScCategoryId(e.getScCategoryId());
        r.setComponentId(e.getComponentId());
        r.setComponentTypeId(e.getComponentTypeId());
        r.setEquipmentName(e.getEquipmentName());
        r.setQuantity(e.getQuantity());
        r.setUnitRate(e.getUnitRate());
        r.setUnitCost(e.getUnitCost());
        r.setCentralPercentage(e.getCentralPercentage());
        r.setStatePercentage(e.getStatePercentage());
        r.setAdvancePercentage(e.getAdvancePercentage());
        r.setFirstPayment(e.getFirstPayment());
        r.setFinalPayment(e.getFinalPayment());
        r.setArmEnds(e.getArmEnds());
        r.setActive(e.getActive());
        r.setError(false);
        // totalAmount = quantity * unitRate
        if (e.getQuantity() != null && e.getUnitRate() != null) {
            r.setTotalAmount(e.getQuantity().multiply(e.getUnitRate()));
        }
        return r;
    }
}
