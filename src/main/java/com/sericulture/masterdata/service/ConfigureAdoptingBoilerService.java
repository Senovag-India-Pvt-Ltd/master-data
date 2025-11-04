package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.configureAdoptingBoiler.ConfigureAdoptingBoilerRequest;
import com.sericulture.masterdata.model.api.configureAdoptingBoiler.ConfigureAdoptingBoilerResponse;
import com.sericulture.masterdata.model.api.configureAdoptingBoiler.EditConfigureAdoptingBoilerRequest;
import com.sericulture.masterdata.model.api.configure_imcb.ConfigureImcbRequest;
import com.sericulture.masterdata.model.api.configure_imcb.ConfigureImcbResponse;
import com.sericulture.masterdata.model.api.configure_imcb.EditConfigureImcbRequest;
import com.sericulture.masterdata.model.entity.ConfigureAdoptingBoiler;
import com.sericulture.masterdata.model.entity.ConfigureImcb;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ConfigureAdoptingBoilerRepository;
import com.sericulture.masterdata.repository.ConfigureImcbRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConfigureAdoptingBoilerService {
    @Autowired
    private ConfigureAdoptingBoilerRepository configureAdoptingBoilerRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private CustomValidator validator;

    /**
     * ✅ Get Configure IMCB details by ID
     */
    public ConfigureAdoptingBoilerResponse getConfigureAdoptingBoilerDetails(long adoptingBoilerId) {
        ConfigureAdoptingBoilerResponse response = new ConfigureAdoptingBoilerResponse();
        ConfigureAdoptingBoiler configureAdoptingBoiler = configureAdoptingBoilerRepository.findByAdoptingBoilerIdAndActive(adoptingBoilerId, true);

        if (configureAdoptingBoiler == null) {
            response.setError(true);
            response.setError_description("Configure IMCB not found");
        } else {
            response = mapper.configureAdoptingBoilerEntityToObject(configureAdoptingBoiler, ConfigureAdoptingBoilerResponse.class);
            response.setError(false);
        }
        return response;
    }

    public Map<String,Object> findByBoilerInKgTableAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(Float boilerInKg,long componentTypeId,long componentId, long categoryId ,boolean isActive){
        return convertListEntityToMapResponse(configureAdoptingBoilerRepository.findByBoilerInKgAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(boilerInKg,componentTypeId,componentId,categoryId,isActive));
    }

    /**
     * ✅ Insert new Configure IMCB record
     */
    @Transactional
    public ConfigureAdoptingBoilerResponse insertConfigureAdoptingBoilerDetails(ConfigureAdoptingBoilerRequest request) {
        ConfigureAdoptingBoilerResponse response = new ConfigureAdoptingBoilerResponse();
        ConfigureAdoptingBoiler entity = mapper.configureAdoptingBoilerObjectToEntity(request, ConfigureAdoptingBoiler.class);

        ConfigureAdoptingBoiler saved = configureAdoptingBoilerRepository.save(entity);
        response = mapper.configureAdoptingBoilerEntityToObject(saved, ConfigureAdoptingBoilerResponse.class);
        response.setError(false);

        return response;
    }


    /**
     * ✅ Pagination
     */
    public Map<String, Object> getPaginatedConfigureAdoptingBoilerDetails(Pageable pageable) {
        return convertToMapResponse(configureAdoptingBoilerRepository.findByActiveOrderByAdoptingBoilerIdAsc(true, pageable));
    }

    /**
     * ✅ Get all active Configure IMCB records
     */
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(configureAdoptingBoilerRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(Page<ConfigureAdoptingBoiler> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureAdoptingBoilerResponse> responses = list.getContent().stream()
                .map(e -> mapper.configureAdoptingBoilerEntityToObject(e, ConfigureAdoptingBoilerResponse.class))
                .collect(Collectors.toList());
        response.put("configureAdoptingBoiler", responses);
        response.put("currentPage", list.getNumber());
        response.put("totalItems", list.getTotalElements());
        response.put("totalPages", list.getTotalPages());
        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(List<ConfigureAdoptingBoiler> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureAdoptingBoilerResponse> responses = list.stream()
                .map(e -> mapper.configureAdoptingBoilerEntityToObject(e, ConfigureAdoptingBoilerResponse.class))
                .collect(Collectors.toList());
        response.put("configureAdoptingBoiler", responses);
        return response;
    }

    /**
     * ✅ Delete (soft delete)
     */
    @Transactional
    public ConfigureAdoptingBoilerResponse deleteConfigureAdoptingBoiler(long id) {
        ConfigureAdoptingBoilerResponse response = new ConfigureAdoptingBoilerResponse();
        ConfigureAdoptingBoiler entity = configureAdoptingBoilerRepository.findByAdoptingBoilerIdAndActive(id, true);

        if (entity != null) {
            entity.setActive(false);
            ConfigureAdoptingBoiler saved = configureAdoptingBoilerRepository.save(entity);
            response = mapper.configureAdoptingBoilerEntityToObject(saved, ConfigureAdoptingBoilerResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        }
        return response;
    }

    /**
     * ✅ Get by ID
     */
    public ConfigureAdoptingBoilerResponse getById(long id) {
        ConfigureAdoptingBoilerResponse response = new ConfigureAdoptingBoilerResponse();
        ConfigureAdoptingBoiler entity = configureAdoptingBoilerRepository.findByAdoptingBoilerIdAndActive(id, true);

        if (entity == null) {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        } else {
            response = mapper.configureAdoptingBoilerEntityToObject(entity, ConfigureAdoptingBoilerResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Get Configure IMCB by ID with JOIN (SQL Server TOP 1)
     */
    public ConfigureAdoptingBoilerResponse getConfigureAdoptingBoilerByIdWithJoin(Long adoptingBoilerId) {
        Object result = configureAdoptingBoilerRepository.getConfigureAdoptingBoilerByIdWithJoin(adoptingBoilerId);

        if (result == null) {
            ConfigureAdoptingBoilerResponse response = new ConfigureAdoptingBoilerResponse();
            response.setError(true);
            response.setError_description("Configure IMCB not found");
            return response;
        }

        Object[] row = (Object[]) result;
        return mapObjectArrayToResponse(row);
    }

    /**
     * ✅ Paginated Configure IMCB List (with joined names)
     */
    public Map<String, Object> getPaginatedConfigureAdoptingBoilerWithJoin(Pageable pageable) {
        Page<Object[]> rawPage = configureAdoptingBoilerRepository.getConfigureAdoptingBoilerListWithJoin(pageable);

        List<ConfigureAdoptingBoilerResponse> responses = rawPage.getContent().stream()
                .map(this::mapObjectArrayToResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("configureAdoptingBoiler", responses);
        response.put("currentPage", rawPage.getNumber());
        response.put("totalItems", rawPage.getTotalElements());
        response.put("totalPages", rawPage.getTotalPages());

        return response;
    }

    /**
     * ✅ FIXED: Corrected index mapping and handled String-to-Number safely
     */
    private ConfigureAdoptingBoilerResponse mapObjectArrayToResponse(Object[] obj) {
        return new ConfigureAdoptingBoilerResponse(
                parseLong(obj[0]),
                parseFloat(obj[1]),
                parseLong(obj[2]),
                parseLong(obj[3]),
                parseLong(obj[4]),
                parseFloat(obj[5]),
                parseFloat(obj[6]),
                parseFloat(obj[7]),

                obj[6] != null ? obj[8].toString() : null,
                obj[7] != null ? obj[9].toString() : null,
                obj[8] != null ? obj[10].toString() : null,
                false,
                null
        );
    }

    /**
     * ✅ Helper methods for safe type conversion (prevent ClassCastException)
     */
    private Long parseLong(Object value) {
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).longValue();
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            log.warn("Failed to parse Long from value: {}", value);
            return null;
        }
    }

    private Float parseFloat(Object value) {
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).floatValue();
        try {
            return Float.parseFloat(value.toString());
        } catch (NumberFormatException e) {
            log.warn("Failed to parse Float from value: {}", value);
            return null;
        }
    }

    /**
     * ✅ Update Configure IMCB
     */
    @Transactional
    public ConfigureAdoptingBoilerResponse updateConfigureAdoptingBoilerDetails(EditConfigureAdoptingBoilerRequest request) {
        ConfigureAdoptingBoilerResponse response = new ConfigureAdoptingBoilerResponse();

//        // ✅ Check duplicate using ID exclusion
//        List<ConfigureAdoptingBoiler> existingList = configureAdoptingBoilerRepository.findByActiveAndadoptingBoilerIdIsNot(true, request.getadoptingBoilerId());
//        if (!existingList.isEmpty()) {
//            response.setError(true);
//            response.setError_description("Configure IMCB already exists");
//            return response;
//        }

        ConfigureAdoptingBoiler entity = configureAdoptingBoilerRepository.findByAdoptingBoilerIdAndActiveIn(request.getAdoptingBoilerId(), Set.of(true, false));
        if (entity != null) {
            entity.setBoilerInKg(request.getBoilerInKg());
            entity.setCategoryId(request.getCategoryId());
            entity.setComponentId(request.getComponentId());
            entity.setComponentTypeId(request.getComponentTypeId());
            entity.setUnitCost(request.getUnitCost());
            entity.setMin(request.getMin());
            entity.setMax(request.getMax());
            entity.setActive(true);

            ConfigureAdoptingBoiler saved = configureAdoptingBoilerRepository.save(entity);
            response = mapper.configureAdoptingBoilerEntityToObject(saved, ConfigureAdoptingBoilerResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        }

        return response;
    }
}
