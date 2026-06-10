package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.sericultureTable.EditSericultureTableRequest;
import com.sericulture.masterdata.model.api.sericultureTable.SericultureTableApprovalStageCheckboxResponse;
import com.sericulture.masterdata.model.api.sericultureTable.SericultureTableRequest;
import com.sericulture.masterdata.model.api.sericultureTable.SericultureTableResponse;
import com.sericulture.masterdata.model.entity.ScApprovalStage;
import com.sericulture.masterdata.model.entity.ScSchemeDetails;
import com.sericulture.masterdata.model.entity.ScSubSchemeDetails;
import com.sericulture.masterdata.model.entity.SericultureTable;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScApprovalStageRepository;
import com.sericulture.masterdata.repository.ScSchemeDetailsRepository;
import com.sericulture.masterdata.repository.ScSubSchemeDetailsRepository;
import com.sericulture.masterdata.repository.SericultureTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SericultureTableService {

    @Autowired
    SericultureTableRepository sericultureTableRepository;

    @Autowired
    ScApprovalStageRepository scApprovalStageRepository;

    @Autowired
    ScSchemeDetailsRepository scSchemeDetailsRepository;

    @Autowired
    ScSubSchemeDetailsRepository scSubSchemeDetailsRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public SericultureTableResponse insertSericultureTableDetails(SericultureTableRequest sericultureTableRequest) {
        SericultureTableResponse sericultureTableResponse = new SericultureTableResponse();

        // Upsert: if a record already exists for the same schemeId + subSchemeId + stepId, update its daysCount
        SericultureTable existing = sericultureTableRepository
                .findBySchemeIdAndSubSchemeIdAndStepIdAndActive(
                        sericultureTableRequest.getSchemeId(),
                        sericultureTableRequest.getSubSchemeId(),
                        sericultureTableRequest.getStepId(),
                        true);

        SericultureTable sericultureTable;
        if (existing != null) {
            existing.setDaysCount(sericultureTableRequest.getDaysCount());
            sericultureTable = existing;
        } else {
            sericultureTable = mapper.sericultureTableObjectToEntity(sericultureTableRequest, SericultureTable.class);
            validator.validate(sericultureTable);
        }

        sericultureTableResponse = mapper.sericultureTableEntityToObject(sericultureTableRepository.save(sericultureTable), SericultureTableResponse.class);
        sericultureTableResponse.setError(false);
        return sericultureTableResponse;
    }

    public Map<String, Object> getPaginatedSericultureTableDetails(final Pageable pageable) {
        return convertToMapResponse(sericultureTableRepository.findByActiveOrderBySericultureTableIdAsc(true, pageable));
    }

    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(sericultureTableRepository.findByActive(isActive));
    }

    private SericultureTableResponse mapWithStageName(SericultureTable sericultureTable) {
        SericultureTableResponse response = mapper.sericultureTableEntityToObject(sericultureTable, SericultureTableResponse.class);
        if (sericultureTable.getStepId() != null) {
            ScApprovalStage stage = scApprovalStageRepository.findByScApprovalStageIdAndActive(sericultureTable.getStepId(), true);
            if (stage != null) {
                response.setApprovalStageName(stage.getStageName());
            }
        }
        if (sericultureTable.getSchemeId() != null) {
            ScSchemeDetails scheme = scSchemeDetailsRepository.findByScSchemeDetailsIdAndActive(sericultureTable.getSchemeId(), true);
            if (scheme != null) {
                response.setSchemeName(scheme.getSchemeName());
            }
        }
        if (sericultureTable.getSubSchemeId() != null) {
            ScSubSchemeDetails subScheme = scSubSchemeDetailsRepository.findByScSubSchemeDetailsIdAndActive(sericultureTable.getSubSchemeId(), true);
            if (subScheme != null) {
                response.setSubSchemeName(subScheme.getSubSchemeName());
            }
        }
        return response;
    }

    private Map<String, Object> convertToMapResponse(final Page<SericultureTable> activeSericultureTables) {
        Map<String, Object> response = new HashMap<>();
        List<SericultureTableResponse> sericultureTableResponses = activeSericultureTables.getContent().stream()
                .map(this::mapWithStageName)
                .collect(Collectors.toList());
        response.put("sericultureTable", sericultureTableResponses);
        response.put("currentPage", activeSericultureTables.getNumber());
        response.put("totalItems", activeSericultureTables.getTotalElements());
        response.put("totalPages", activeSericultureTables.getTotalPages());
        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<SericultureTable> activeSericultureTables) {
        Map<String, Object> response = new HashMap<>();
        List<SericultureTableResponse> sericultureTableResponses = activeSericultureTables.stream()
                .map(this::mapWithStageName)
                .collect(Collectors.toList());
        response.put("sericultureTable", sericultureTableResponses);
        return response;
    }

    @Transactional
    public SericultureTableResponse deleteSericultureTableDetails(long id) {
        SericultureTableResponse sericultureTableResponse = new SericultureTableResponse();
        SericultureTable sericultureTable = sericultureTableRepository.findBySericultureTableIdAndActive(id, true);
        if (Objects.nonNull(sericultureTable)) {
            sericultureTable.setActive(false);
            sericultureTableResponse = mapper.sericultureTableEntityToObject(sericultureTableRepository.save(sericultureTable), SericultureTableResponse.class);
            sericultureTableResponse.setError(false);
        } else {
            sericultureTableResponse.setError(true);
            sericultureTableResponse.setError_description("Invalid Id");
        }
        return sericultureTableResponse;
    }

    public SericultureTableResponse getById(int id) {
        SericultureTableResponse sericultureTableResponse = new SericultureTableResponse();
        SericultureTable sericultureTable = sericultureTableRepository.findBySericultureTableIdAndActive(id, true);
        if (sericultureTable == null) {
            sericultureTableResponse.setError(true);
            sericultureTableResponse.setError_description("Invalid id");
        } else {
            sericultureTableResponse = mapper.sericultureTableEntityToObject(sericultureTable, SericultureTableResponse.class);
            sericultureTableResponse.setError(false);
        }
        log.info("Entity is ", sericultureTable);
        return sericultureTableResponse;
    }

    @Transactional
    public SericultureTableResponse updateSericultureTableDetails(EditSericultureTableRequest editSericultureTableRequest) {
        SericultureTableResponse sericultureTableResponse = new SericultureTableResponse();
        SericultureTable sericultureTable = sericultureTableRepository.findBySericultureTableIdAndActiveIn(
                editSericultureTableRequest.getSericultureTableId(), Set.of(true, false));
        if (Objects.nonNull(sericultureTable)) {
            sericultureTable.setStepId(editSericultureTableRequest.getStepId());
            sericultureTable.setDaysCount(editSericultureTableRequest.getDaysCount());
            sericultureTable.setSchemeId(editSericultureTableRequest.getSchemeId());
            sericultureTable.setSubSchemeId(editSericultureTableRequest.getSubSchemeId());
            sericultureTable.setActive(true);
            SericultureTable saved = sericultureTableRepository.save(sericultureTable);
            sericultureTableResponse = mapper.sericultureTableEntityToObject(saved, SericultureTableResponse.class);
            sericultureTableResponse.setError(false);
        } else {
            sericultureTableResponse.setError(true);
            sericultureTableResponse.setError_description("Error occurred while fetching SericultureTable");
        }
        return sericultureTableResponse;
    }

    public List<SericultureTableApprovalStageCheckboxResponse> getApprovalStagesCheckbox(Long schemeId, Long subSchemeId) {
        List<ScApprovalStage> allStages = scApprovalStageRepository.findByActive(true);
        List<SericultureTable> existing = sericultureTableRepository.findBySchemeIdAndSubSchemeIdAndActive(schemeId, subSchemeId, true);
        Map<Integer, SericultureTable> existingByStepId = new HashMap<>();
        for (SericultureTable st : existing) {
            if (st.getStepId() != null) {
                existingByStepId.put(st.getStepId(), st);
            }
        }
        List<SericultureTableApprovalStageCheckboxResponse> result = new ArrayList<>();
        for (ScApprovalStage stage : allStages) {
            SericultureTableApprovalStageCheckboxResponse item = new SericultureTableApprovalStageCheckboxResponse();
            item.setScApprovalStageId(stage.getScApprovalStageId());
            item.setStageName(stage.getStageName());
            SericultureTable match = existingByStepId.get(stage.getScApprovalStageId().intValue());
            if (match != null) {
                item.setChecked(true);
                item.setSericultureTableId(match.getSericultureTableId());
                item.setDaysCount(match.getDaysCount());
            } else {
                item.setChecked(false);
            }
            result.add(item);
        }
        return result;
    }
}
