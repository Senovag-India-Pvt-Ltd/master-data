package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.configure_imcb.ConfigureImcbRequest;
import com.sericulture.masterdata.model.api.configure_imcb.ConfigureImcbResponse;
import com.sericulture.masterdata.model.api.configure_imcb.EditConfigureImcbRequest;
import com.sericulture.masterdata.model.entity.ConfigureImcb;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ConfigureImcbRepository;
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
public class ConfigureImcbService {

    @Autowired
    private ConfigureImcbRepository configureImcbRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private CustomValidator validator;

    /**
     * ✅ Get Configure IMCB details by ID
     */
    public ConfigureImcbResponse getConfigureImcbDetails(long imcbId) {
        ConfigureImcbResponse response = new ConfigureImcbResponse();
        ConfigureImcb configureImcb = configureImcbRepository.findByImcbIdAndActive(imcbId, true);

        if (configureImcb == null) {
            response.setError(true);
            response.setError_description("Configure IMCB not found");
        } else {
            response = mapper.configureImcbEntityToObject(configureImcb, ConfigureImcbResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Insert new Configure IMCB record
     */
    @Transactional
    public ConfigureImcbResponse insertConfigureImcbDetails(ConfigureImcbRequest request) {
        ConfigureImcbResponse response = new ConfigureImcbResponse();
        ConfigureImcb entity = mapper.configureImcbObjectToEntity(request, ConfigureImcb.class);

        ConfigureImcb saved = configureImcbRepository.save(entity);
        response = mapper.configureImcbEntityToObject(saved, ConfigureImcbResponse.class);
        response.setError(false);

        return response;
    }


    /**
     * ✅ Pagination
     */
    public Map<String, Object> getPaginatedConfigureImcbDetails(Pageable pageable) {
        return convertToMapResponse(configureImcbRepository.findByActiveOrderByImcbIdAsc(true, pageable));
    }

    /**
     * ✅ Get all active Configure IMCB records
     */
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(configureImcbRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(Page<ConfigureImcb> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureImcbResponse> responses = list.getContent().stream()
                .map(e -> mapper.configureImcbEntityToObject(e, ConfigureImcbResponse.class))
                .collect(Collectors.toList());
        response.put("configureImcb", responses);
        response.put("currentPage", list.getNumber());
        response.put("totalItems", list.getTotalElements());
        response.put("totalPages", list.getTotalPages());
        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(List<ConfigureImcb> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureImcbResponse> responses = list.stream()
                .map(e -> mapper.configureImcbEntityToObject(e, ConfigureImcbResponse.class))
                .collect(Collectors.toList());
        response.put("configureImcb", responses);
        return response;
    }

    /**
     * ✅ Delete (soft delete)
     */
    @Transactional
    public ConfigureImcbResponse deleteConfigureImcb(long id) {
        ConfigureImcbResponse response = new ConfigureImcbResponse();
        ConfigureImcb entity = configureImcbRepository.findByImcbIdAndActive(id, true);

        if (entity != null) {
            entity.setActive(false);
            ConfigureImcb saved = configureImcbRepository.save(entity);
            response = mapper.configureImcbEntityToObject(saved, ConfigureImcbResponse.class);
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
    public ConfigureImcbResponse getById(long id) {
        ConfigureImcbResponse response = new ConfigureImcbResponse();
        ConfigureImcb entity = configureImcbRepository.findByImcbIdAndActive(id, true);

        if (entity == null) {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        } else {
            response = mapper.configureImcbEntityToObject(entity, ConfigureImcbResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Get Configure IMCB by ID with JOIN (SQL Server TOP 1)
     */
    public ConfigureImcbResponse getConfigureImcbByIdWithJoin(Long imcbId) {
        Object result = configureImcbRepository.getConfigureImcbByIdWithJoin(imcbId);

        if (result == null) {
            ConfigureImcbResponse response = new ConfigureImcbResponse();
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
    public Map<String, Object> getPaginatedConfigureImcbWithJoin(Pageable pageable) {
        Page<Object[]> rawPage = configureImcbRepository.getConfigureImcbListWithJoin(pageable);

        List<ConfigureImcbResponse> responses = rawPage.getContent().stream()
                .map(this::mapObjectArrayToResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("configureImcb", responses);
        response.put("currentPage", rawPage.getNumber());
        response.put("totalItems", rawPage.getTotalElements());
        response.put("totalPages", rawPage.getTotalPages());

        return response;
    }

    /**
     * ✅ Mapper for Object[] → ConfigureImcbResponse
     */
    private ConfigureImcbResponse mapObjectArrayToResponse(Object[] obj) {
        return new ConfigureImcbResponse(
                obj[0] != null ? ((Number) obj[0]).longValue() : null,   // imcbId
                obj[1] != null ? obj[1].toString() : null,               // imcbTable
                obj[2] != null ? ((Number) obj[2]).longValue() : null,   // categoryId
                obj[3] != null ? ((Number) obj[3]).longValue() : null,   // componentId
                obj[4] != null ? ((Number) obj[4]).longValue() : null,   // componentTypeId
                obj[5] != null ? ((Number) obj[5]).floatValue() : null,  // unitCost
                obj[6] != null ? ((Number) obj[6]).floatValue() : null,  // min
                obj[7] != null ? ((Number) obj[7]).floatValue() : null,  // max
                obj[8] != null ? obj[8].toString() : null,               // categoryName
                obj[9] != null ? obj[9].toString() : null,               // scComponentName
                obj[10] != null ? obj[10].toString() : null,             // subSchemeName
                false,
                null
        );
    }

    /**
     * ✅ Update Configure IMCB
     */
    @Transactional
    public ConfigureImcbResponse updateConfigureImcbDetails(EditConfigureImcbRequest request) {
        ConfigureImcbResponse response = new ConfigureImcbResponse();

//        // ✅ Check duplicate using ID exclusion
//        List<ConfigureImcb> existingList = configureImcbRepository.findByActiveAndImcbIdIsNot(true, request.getImcbId());
//        if (!existingList.isEmpty()) {
//            response.setError(true);
//            response.setError_description("Configure IMCB already exists");
//            return response;
//        }

        ConfigureImcb entity = configureImcbRepository.findByImcbIdAndActiveIn(request.getImcbId(), Set.of(true, false));
        if (entity != null) {
            entity.setImcbTable(request.getImcbTable());
            entity.setCategoryId(request.getCategoryId());
            entity.setComponentId(request.getComponentId());
            entity.setComponentTypeId(request.getComponentTypeId());
            entity.setUnitCost(request.getUnitCost());
            entity.setMin(request.getMin());
            entity.setMax(request.getMax());
            entity.setActive(true);

            ConfigureImcb saved = configureImcbRepository.save(entity);
            response = mapper.configureImcbEntityToObject(saved, ConfigureImcbResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        }

        return response;
    }
}
