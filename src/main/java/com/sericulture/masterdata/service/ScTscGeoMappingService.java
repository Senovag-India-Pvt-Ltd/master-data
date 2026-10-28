package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scTscGeoMapping.EditScTscGeoMappingRequest;
import com.sericulture.masterdata.model.api.scTscGeoMapping.ScTscGeoMappingRequest;
import com.sericulture.masterdata.model.api.scTscGeoMapping.ScTscGeoMappingResponse;
import com.sericulture.masterdata.model.dto.ScTscGeoMappingDTO;
import com.sericulture.masterdata.model.entity.ScTscGeoMapping;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScTscGeoMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScTscGeoMappingService {

    @Autowired
    ScTscGeoMappingRepository scTscGeoMappingRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScTscGeoMappingResponse insertScTscGeoMappingDetails(ScTscGeoMappingRequest request) {
        ScTscGeoMappingResponse response = new ScTscGeoMappingResponse();
        ScTscGeoMapping entity = mapper.scTscGeoMappingObjectToEntity(request, ScTscGeoMapping.class);
        validator.validate(entity);

        List<ScTscGeoMapping> duplicates = scTscGeoMappingRepository
                .findByDistrictIdAndTalukIdAndHobliIdAndVillageIdAndActive(
                        request.getDistrictId(), request.getTalukId(), request.getHobliId(), request.getVillageId(), true);

        if (!duplicates.isEmpty()) {
            response.setError(true);
            response.setError_description("A TSC mapping already exists for this exact geography combination.");
        } else {
            ScTscGeoMapping saved = scTscGeoMappingRepository.save(entity);
            response = mapper.scTscGeoMappingEntityToObject(saved, ScTscGeoMappingResponse.class);
            response.setError(false);
        }
        return response;
    }

    @Transactional
    public ScTscGeoMappingResponse updateScTscGeoMappingDetails(EditScTscGeoMappingRequest request) {
        ScTscGeoMappingResponse response = new ScTscGeoMappingResponse();
        List<ScTscGeoMapping> duplicates = scTscGeoMappingRepository
                .findByDistrictIdAndTalukIdAndHobliIdAndVillageIdAndActiveAndScTscGeoMappingIdIsNot(
                        request.getDistrictId(), request.getTalukId(), request.getHobliId(), request.getVillageId(),
                        true, request.getScTscGeoMappingId());

        if (!duplicates.isEmpty()) {
            response.setError(true);
            response.setError_description("A TSC mapping already exists for this exact geography combination.");
            return response;
        }

        ScTscGeoMapping entity = scTscGeoMappingRepository.findByScTscGeoMappingIdAndActiveIn(
                request.getScTscGeoMappingId(), Set.of(true, false));
        if (Objects.isNull(entity)) {
            response.setError(true);
            response.setError_description("Invalid Id");
            return response;
        }

        entity.setDistrictId(request.getDistrictId());
        entity.setTalukId(request.getTalukId());
        entity.setHobliId(request.getHobliId());
        entity.setVillageId(request.getVillageId());
        entity.setTscMasterId(request.getTscMasterId());
        entity.setOfficerUserId(request.getOfficerUserId());
        validator.validate(entity);

        ScTscGeoMapping updated = scTscGeoMappingRepository.save(entity);
        response = mapper.scTscGeoMappingEntityToObject(updated, ScTscGeoMappingResponse.class);
        response.setError(false);
        return response;
    }

    @Transactional
    public ScTscGeoMappingResponse deleteScTscGeoMappingDetails(long id) {
        ScTscGeoMappingResponse response = new ScTscGeoMappingResponse();
        ScTscGeoMapping entity = scTscGeoMappingRepository.findByScTscGeoMappingIdAndActive(id, true);
        if (Objects.nonNull(entity)) {
            entity.setActive(false);
            response = mapper.scTscGeoMappingEntityToObject(scTscGeoMappingRepository.save(entity), ScTscGeoMappingResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid Id");
        }
        return response;
    }

    public ScTscGeoMappingResponse getById(long id) {
        ScTscGeoMappingResponse response = new ScTscGeoMappingResponse();
        ScTscGeoMappingDTO dto = scTscGeoMappingRepository.getByIdAndActive(id, true);
        if (dto == null) {
            response.setError(true);
            response.setError_description("Invalid id");
        } else {
            response = mapper.scTscGeoMappingDTOToObject(dto, ScTscGeoMappingResponse.class);
            response.setError(false);
        }
        return response;
    }

    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(scTscGeoMappingRepository.findByActive(isActive));
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScTscGeoMapping> entities) {
        Map<String, Object> response = new HashMap<>();
        List<ScTscGeoMappingResponse> responses = entities.stream()
                .map(entity -> mapper.scTscGeoMappingEntityToObject(entity, ScTscGeoMappingResponse.class))
                .collect(Collectors.toList());
        response.put("scTscGeoMapping", responses);
        return response;
    }

    public Map<String, Object> getPaginatedScTscGeoMappingDetailsWithJoin(final Pageable pageable) {
        Page<ScTscGeoMappingDTO> page = scTscGeoMappingRepository.getByActiveOrderByDistrictNameAsc(true, pageable);
        Map<String, Object> response = new HashMap<>();
        List<ScTscGeoMappingResponse> responses = page.getContent().stream()
                .map(dto -> mapper.scTscGeoMappingDTOToObject(dto, ScTscGeoMappingResponse.class))
                .collect(Collectors.toList());
        response.put("scTscGeoMapping", responses);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        return response;
    }
}
