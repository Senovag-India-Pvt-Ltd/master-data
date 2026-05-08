package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.schemeDocumentMaster.EditSchemeDocumentMasterRequest;
import com.sericulture.masterdata.model.api.schemeDocumentMaster.SchemeDocumentMasterRequest;
import com.sericulture.masterdata.model.api.schemeDocumentMaster.SchemeDocumentMasterResponse;
import com.sericulture.masterdata.model.entity.SchemeDocumentMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.SchemeDocumentMasterRepository;
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
public class SchemeDocumentMasterService {

    @Autowired
    SchemeDocumentMasterRepository schemeDocumentMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public SchemeDocumentMasterResponse getById(int id) {
        SchemeDocumentMasterResponse schemeDocumentMasterResponse = new SchemeDocumentMasterResponse();
        SchemeDocumentMaster schemeDocumentMaster = schemeDocumentMasterRepository.findBySchemeDocumentIdAndActive(id, true);
        if (schemeDocumentMaster == null) {
            schemeDocumentMasterResponse.setError(true);
            schemeDocumentMasterResponse.setError_description("Scheme document not found");
        } else {
            schemeDocumentMasterResponse = mapper.schemeDocumentMasterEntityToObject(schemeDocumentMaster, SchemeDocumentMasterResponse.class);
            schemeDocumentMasterResponse.setError(false);
        }
        log.info("Entity is ", schemeDocumentMaster);
        return schemeDocumentMasterResponse;
    }

    @Transactional
    public SchemeDocumentMasterResponse insertSchemeDocumentMasterDetails(SchemeDocumentMasterRequest schemeDocumentMasterRequest) {
        SchemeDocumentMasterResponse schemeDocumentMasterResponse = new SchemeDocumentMasterResponse();
        SchemeDocumentMaster schemeDocumentMaster = mapper.schemeDocumentMasterObjectToEntity(schemeDocumentMasterRequest, SchemeDocumentMaster.class);
        if (schemeDocumentMaster.getAllow() == null) {
            schemeDocumentMaster.setAllow(true);
        }
        validator.validate(schemeDocumentMaster);
        List<SchemeDocumentMaster> existingList = schemeDocumentMasterRepository
                .findByActiveAndScSchemeDetailsIdAndScSubSchemeDetailsIdAndDocumentId(
                        true,
                        schemeDocumentMasterRequest.getScSchemeDetailsId(),
                        schemeDocumentMasterRequest.getScSubSchemeDetailsId(),
                        schemeDocumentMasterRequest.getDocumentId());
        if (!existingList.isEmpty()) {
            schemeDocumentMasterResponse.setError(true);
            schemeDocumentMasterResponse.setError_description("Scheme document mapping already exists");
        } else {
            schemeDocumentMasterResponse = mapper.schemeDocumentMasterEntityToObject(
                    schemeDocumentMasterRepository.save(schemeDocumentMaster), SchemeDocumentMasterResponse.class);
            schemeDocumentMasterResponse.setError(false);
        }
        return schemeDocumentMasterResponse;
    }

    public Map<String, Object> getPaginatedSchemeDocumentMasterDetails(final Pageable pageable) {
        return convertToMapResponse(schemeDocumentMasterRepository.findByActiveOrderBySchemeDocumentIdAsc(true, pageable));
    }

    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(schemeDocumentMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<SchemeDocumentMaster> activeList) {
        Map<String, Object> response = new HashMap<>();
        List<SchemeDocumentMasterResponse> responseList = activeList.getContent().stream()
                .map(entity -> mapper.schemeDocumentMasterEntityToObject(entity, SchemeDocumentMasterResponse.class))
                .collect(Collectors.toList());
        response.put("schemeDocumentMaster", responseList);
        response.put("currentPage", activeList.getNumber());
        response.put("totalItems", activeList.getTotalElements());
        response.put("totalPages", activeList.getTotalPages());
        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<SchemeDocumentMaster> activeList) {
        Map<String, Object> response = new HashMap<>();
        List<SchemeDocumentMasterResponse> responseList = activeList.stream()
                .map(entity -> mapper.schemeDocumentMasterEntityToObject(entity, SchemeDocumentMasterResponse.class))
                .collect(Collectors.toList());
        response.put("schemeDocumentMaster", responseList);
        return response;
    }

    @Transactional
    public SchemeDocumentMasterResponse deleteSchemeDocumentMasterDetails(long id) {
        SchemeDocumentMasterResponse schemeDocumentMasterResponse = new SchemeDocumentMasterResponse();
        SchemeDocumentMaster schemeDocumentMaster = schemeDocumentMasterRepository.findBySchemeDocumentIdAndActive(id, true);
        if (Objects.nonNull(schemeDocumentMaster)) {
            schemeDocumentMaster.setActive(false);
            schemeDocumentMasterResponse = mapper.schemeDocumentMasterEntityToObject(
                    schemeDocumentMasterRepository.save(schemeDocumentMaster), SchemeDocumentMasterResponse.class);
            schemeDocumentMasterResponse.setError(false);
        } else {
            schemeDocumentMasterResponse.setError(true);
            schemeDocumentMasterResponse.setError_description("Invalid Id");
        }
        return schemeDocumentMasterResponse;
    }

    @Transactional
    public SchemeDocumentMasterResponse updateSchemeDocumentMasterDetails(EditSchemeDocumentMasterRequest editRequest) {
        SchemeDocumentMasterResponse schemeDocumentMasterResponse = new SchemeDocumentMasterResponse();
        List<SchemeDocumentMaster> duplicateList = schemeDocumentMasterRepository
                .findByScSchemeDetailsIdAndScSubSchemeDetailsIdAndDocumentIdAndSchemeDocumentIdIsNot(
                        editRequest.getScSchemeDetailsId(),
                        editRequest.getScSubSchemeDetailsId(),
                        editRequest.getDocumentId(),
                        editRequest.getSchemeDocumentId());
        if (!duplicateList.isEmpty() && duplicateList.stream().anyMatch(SchemeDocumentMaster::getActive)) {
            schemeDocumentMasterResponse.setError(true);
            schemeDocumentMasterResponse.setError_description("Scheme document mapping already exists, duplicates are not allowed.");
        } else {
            SchemeDocumentMaster schemeDocumentMaster = schemeDocumentMasterRepository
                    .findBySchemeDocumentIdAndActiveIn(editRequest.getSchemeDocumentId(), Set.of(true, false));
            if (Objects.nonNull(schemeDocumentMaster)) {
                schemeDocumentMaster.setScSchemeDetailsId(editRequest.getScSchemeDetailsId());
                schemeDocumentMaster.setScSubSchemeDetailsId(editRequest.getScSubSchemeDetailsId());
                schemeDocumentMaster.setDocumentId(editRequest.getDocumentId());
                schemeDocumentMaster.setAllow(editRequest.getAllow() != null ? editRequest.getAllow() : true);
                schemeDocumentMaster.setActive(true);
                SchemeDocumentMaster saved = schemeDocumentMasterRepository.save(schemeDocumentMaster);
                schemeDocumentMasterResponse = mapper.schemeDocumentMasterEntityToObject(saved, SchemeDocumentMasterResponse.class);
                schemeDocumentMasterResponse.setError(false);
            } else {
                schemeDocumentMasterResponse.setError(true);
                schemeDocumentMasterResponse.setError_description("Error occurred while fetching scheme document");
            }
        }
        return schemeDocumentMasterResponse;
    }
}
