package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.configure_icb.ConfigureIcbRequest;
import com.sericulture.masterdata.model.api.configure_icb.EditConfigureIcbRequest;
import com.sericulture.masterdata.model.api.configure_icb.ConfigureIcbResponse;
import com.sericulture.masterdata.model.entity.ConfigureIcb;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ConfigureIcbRepository;
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
public class ConfigureIcbService {

    @Autowired
    private ConfigureIcbRepository configureIcbRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private CustomValidator validator;

    /**
     * ✅ Get Configure ICB by ID
     */
    public ConfigureIcbResponse getConfigureIcbDetails(long icbId) {
        ConfigureIcbResponse response = new ConfigureIcbResponse();
        ConfigureIcb configureIcb = configureIcbRepository.findByIcbIdAndActive(icbId, true);

        if (configureIcb == null) {
            response.setError(true);
            response.setError_description("Configure ICB not found");
        } else {
            response = mapper.configureIcbEntityToObject(configureIcb, ConfigureIcbResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Insert new Configure ICB record
     */
    @Transactional
    public ConfigureIcbResponse insertConfigureIcbDetails(ConfigureIcbRequest request) {
        ConfigureIcbResponse response = new ConfigureIcbResponse();
        ConfigureIcb entity = mapper.configureIcbObjectToEntity(request, ConfigureIcb.class);

        ConfigureIcb saved = configureIcbRepository.save(entity);
        response = mapper.configureIcbEntityToObject(saved, ConfigureIcbResponse.class);
        response.setError(false);

        return response;
    }
    /**
     * ✅ Pagination
     */
    public Map<String, Object> getPaginatedConfigureIcbDetails(Pageable pageable) {
        return convertToMapResponse(configureIcbRepository.findByActiveOrderByIcbIdAsc(true, pageable));
    }

    /**
     * ✅ Get all active Configure ICB records
     */
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(configureIcbRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(Page<ConfigureIcb> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureIcbResponse> responses = list.getContent().stream()
                .map(e -> mapper.configureIcbEntityToObject(e, ConfigureIcbResponse.class))
                .collect(Collectors.toList());
        response.put("configureIcb", responses);
        response.put("currentPage", list.getNumber());
        response.put("totalItems", list.getTotalElements());
        response.put("totalPages", list.getTotalPages());
        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(List<ConfigureIcb> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureIcbResponse> responses = list.stream()
                .map(e -> mapper.configureIcbEntityToObject(e, ConfigureIcbResponse.class))
                .collect(Collectors.toList());
        response.put("configureIcb", responses);
        return response;
    }

    /**
     * ✅ Delete (soft delete)
     */
    @Transactional
    public ConfigureIcbResponse deleteConfigureIcb(long id) {
        ConfigureIcbResponse response = new ConfigureIcbResponse();
        ConfigureIcb entity = configureIcbRepository.findByIcbIdAndActive(id, true);

        if (entity != null) {
            entity.setActive(false);
            ConfigureIcb saved = configureIcbRepository.save(entity);
            response = mapper.configureIcbEntityToObject(saved, ConfigureIcbResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid ID");
        }
        return response;
    }

    /**
     * ✅ Get by ID (simple)
     */
    public ConfigureIcbResponse getById(long id) {
        ConfigureIcbResponse response = new ConfigureIcbResponse();
        ConfigureIcb entity = configureIcbRepository.findByIcbIdAndActive(id, true);

        if (entity == null) {
            response.setError(true);
            response.setError_description("Invalid ID");
        } else {
            response = mapper.configureIcbEntityToObject(entity, ConfigureIcbResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Get Configure ICB by ID with JOIN (using SQL Server TOP 1)
     */
    public ConfigureIcbResponse getConfigureIcbByIdWithJoin(Long icbId) {
        Object result = configureIcbRepository.getConfigureIcbByIdWithJoin(icbId);

        if (result == null) {
            ConfigureIcbResponse response = new ConfigureIcbResponse();
            response.setError(true);
            response.setError_description("Configure ICB not found");
            return response;
        }

        Object[] row = (Object[]) result;
        return mapObjectArrayToResponse(row);
    }

    /**
     * ✅ Paginated Configure ICB List (with joined names)
     */
    public Map<String, Object> getPaginatedConfigureIcbWithJoin(Pageable pageable) {
        Page<Object[]> rawPage = configureIcbRepository.getConfigureIcbListWithJoin(pageable);

        List<ConfigureIcbResponse> responses = rawPage.getContent().stream()
                .map(this::mapObjectArrayToResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("configureIcb", responses);
        response.put("currentPage", rawPage.getNumber());
        response.put("totalItems", rawPage.getTotalElements());
        response.put("totalPages", rawPage.getTotalPages());

        return response;
    }

    /**
     * ✅ Mapper for Object[] → Response
     */
    private ConfigureIcbResponse mapObjectArrayToResponse(Object[] obj) {
        return new ConfigureIcbResponse(
                obj[0] != null ? ((Number) obj[0]).longValue() : null,
                obj[1] != null ? obj[1].toString() : null,
                obj[2] != null ? ((Number) obj[2]).longValue() : null,
                obj[3] != null ? ((Number) obj[3]).longValue() : null,
                obj[4] != null ? ((Number) obj[4]).longValue() : null,
                obj[5] != null ? ((Number) obj[5]).floatValue() : null,
                obj[6] != null ? ((Number) obj[6]).floatValue() : null,
                obj[7] != null ? ((Number) obj[7]).floatValue() : null,
                obj[8] != null ? obj[8].toString() : null,
                obj[9] != null ? obj[9].toString() : null,
                obj[10] != null ? obj[10].toString() : null,
                false,
                null
        );
    }

    /**
     * ✅ Update Configure ICB (based on ID only)
     */
    @Transactional
    public ConfigureIcbResponse updateConfigureIcbDetails(EditConfigureIcbRequest request) {
        ConfigureIcbResponse response = new ConfigureIcbResponse();

        ConfigureIcb entity = configureIcbRepository.findByIcbIdAndActiveIn(request.getIcbId(), Set.of(true, false));
        if (entity != null) {
            entity.setCategoryId(request.getCategoryId());
            entity.setComponentId(request.getComponentId());
            entity.setComponentTypeId(request.getComponentTypeId());
            entity.setUnitCost(request.getUnitCost());
            entity.setMin(request.getMin());
            entity.setMax(request.getMax());
            entity.setActive(true);

            ConfigureIcb saved = configureIcbRepository.save(entity);
            response = mapper.configureIcbEntityToObject(saved, ConfigureIcbResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid ICB ID");
        }
        return response;
    }
}
