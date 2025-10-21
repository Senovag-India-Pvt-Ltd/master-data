package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.configure_silk_incentive.ConfigureSilkIncentiveRequest;
import com.sericulture.masterdata.model.api.configure_silk_incentive.ConfigureSilkIncentiveResponse;
import com.sericulture.masterdata.model.api.configure_silk_incentive.EditConfigureSilkIncentiveRequest;
import com.sericulture.masterdata.model.entity.ConfigureSilkIncentive;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ConfigureSilkIncentiveRepository;
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
public class ConfigureSilkIncentiveService {

    @Autowired
    private ConfigureSilkIncentiveRepository configureSilkIncentiveRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private CustomValidator validator;

    /**
     * ✅ Get Configure IMCB details by ID
     */
    public ConfigureSilkIncentiveResponse getConfigureSilkIncentiveDetails(long silkIncentiveId) {
        ConfigureSilkIncentiveResponse response = new ConfigureSilkIncentiveResponse();
        ConfigureSilkIncentive configureSilkIncentive = configureSilkIncentiveRepository.findBySilkIncentiveIdAndActive(silkIncentiveId, true);

        if (configureSilkIncentive == null) {
            response.setError(true);
            response.setError_description("Configure IMCB not found");
        } else {
            response = mapper.configureSilkIncentiveEntityToObject(configureSilkIncentive, ConfigureSilkIncentiveResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Insert new Configure IMCB record
     */
    @Transactional
    public ConfigureSilkIncentiveResponse insertConfigureSilkIncentiveDetails(ConfigureSilkIncentiveRequest request) {
        ConfigureSilkIncentiveResponse response = new ConfigureSilkIncentiveResponse();
        ConfigureSilkIncentive entity = mapper.configureSilkIncentiveObjectToEntity(request, ConfigureSilkIncentive.class);

        ConfigureSilkIncentive saved = configureSilkIncentiveRepository.save(entity);
        response = mapper.configureSilkIncentiveEntityToObject(saved, ConfigureSilkIncentiveResponse.class);
        response.setError(false);

        return response;
    }

    /**
     * ✅ Pagination
     */
    public Map<String, Object> getPaginatedConfigureSilkIncentiveDetails(Pageable pageable) {
        return convertToMapResponse(configureSilkIncentiveRepository.findByActiveOrderBySilkIncentiveIdAsc(true, pageable));
    }

    /**
     * ✅ Get all active Configure IMCB records
     */
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(configureSilkIncentiveRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(Page<ConfigureSilkIncentive> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureSilkIncentiveResponse> responses = list.getContent().stream()
                .map(e -> mapper.configureSilkIncentiveEntityToObject(e, ConfigureSilkIncentiveResponse.class))
                .collect(Collectors.toList());
        response.put("configureSilkIncentive", responses);
        response.put("currentPage", list.getNumber());
        response.put("totalItems", list.getTotalElements());
        response.put("totalPages", list.getTotalPages());
        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(List<ConfigureSilkIncentive> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureSilkIncentiveResponse> responses = list.stream()
                .map(e -> mapper.configureSilkIncentiveEntityToObject(e, ConfigureSilkIncentiveResponse.class))
                .collect(Collectors.toList());
        response.put("configureSilkIncentive", responses);
        return response;
    }

           public ConfigureSilkIncentiveResponse getConfigureSilkIncentiveByIdWithJoin(Long silkIncentiveId) {
            Object result = configureSilkIncentiveRepository.getConfigureSilkIncentiveByIdWithJoin(silkIncentiveId);
            if (result == null) {
                ConfigureSilkIncentiveResponse response = new ConfigureSilkIncentiveResponse();
                response.setError(true);
                response.setError_description("Configure Silk Incentive not found");
                return response;
            }
            Object[] row = (Object[]) result;
            return mapObjectArrayToResponse(row);
        }

        /** ✅ Paginated list with join */
        public Map<String, Object> getPaginatedConfigureSilkIncentiveWithJoin(Pageable pageable) {
            Page<Object[]> rawPage = configureSilkIncentiveRepository.getConfigureSilkIncentiveListWithJoin(pageable);
            List<ConfigureSilkIncentiveResponse> responses = rawPage.getContent().stream()
                    .map(this::mapObjectArrayToResponse)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("configureSilkIncentive", responses);
            response.put("currentPage", rawPage.getNumber());
            response.put("totalItems", rawPage.getTotalElements());
            response.put("totalPages", rawPage.getTotalPages());
            return response;
        }

        /** ✅ Mapper from Object[] to Response */
        private ConfigureSilkIncentiveResponse mapObjectArrayToResponse(Object[] obj) {
            return new ConfigureSilkIncentiveResponse(
                    obj[0] != null ? ((Number) obj[0]).longValue() : null,
                    obj[1] != null ? ((Number) obj[1]).longValue() : null,
                    obj[2] != null ? ((Number) obj[2]).longValue() : null,
                    obj[3] != null ? ((Number) obj[3]).longValue() : null,
                    obj[4] != null ? ((Number) obj[4]).longValue() : null,
                    obj[5] != null ? ((Number) obj[5]).floatValue() : null,
                    obj[6] != null ? ((Number) obj[6]).floatValue() : null,
                    obj[7] != null ? ((Number) obj[7]).floatValue() : null,
                    obj[9] != null ? obj[9].toString() : null,
                    obj[10] != null ? obj[10].toString() : null,
                    obj[11] != null ? obj[11].toString() : null,
                    false,
                    null
            );
        }


    /**
     * ✅ Delete (soft delete)
     */
    @Transactional
    public ConfigureSilkIncentiveResponse deleteConfigureSilkIncentive(long id) {
        ConfigureSilkIncentiveResponse response = new ConfigureSilkIncentiveResponse();
        ConfigureSilkIncentive entity = configureSilkIncentiveRepository.findBySilkIncentiveIdAndActive(id, true);

        if (entity != null) {
            entity.setActive(false);
            ConfigureSilkIncentive saved = configureSilkIncentiveRepository.save(entity);
            response = mapper.configureSilkIncentiveEntityToObject(saved, ConfigureSilkIncentiveResponse.class);
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
    public ConfigureSilkIncentiveResponse getById(long id) {
        ConfigureSilkIncentiveResponse response = new ConfigureSilkIncentiveResponse();
        ConfigureSilkIncentive entity = configureSilkIncentiveRepository.findBySilkIncentiveIdAndActive(id, true);

        if (entity == null) {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        } else {
            response = mapper.configureSilkIncentiveEntityToObject(entity, ConfigureSilkIncentiveResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Update Configure IMCB
     */
    @Transactional
    public ConfigureSilkIncentiveResponse updateConfigureSilkIncentiveDetails(EditConfigureSilkIncentiveRequest request) {
        ConfigureSilkIncentiveResponse response = new ConfigureSilkIncentiveResponse();

//        // ✅ Check duplicate using ID exclusion
//        List<ConfigureSilkIncentive> existingList = configureSilkIncentiveRepository.findByActiveAndSilkIncentiveIdIsNot(true, request.getSilkIncentiveId());
//        if (!existingList.isEmpty()) {
//            response.setError(true);
//            response.setError_description("Configure IMCB already exists");
//            return response;
//        }

        ConfigureSilkIncentive entity = configureSilkIncentiveRepository.findBySilkIncentiveIdAndActiveIn(request.getSilkIncentiveId(), Set.of(true, false));
        if (entity != null) {
            entity.setMachineTypeId(request.getMachineTypeId());
            entity.setCategoryId(request.getCategoryId());
            entity.setComponentId(request.getComponentId());
            entity.setComponentTypeId(request.getComponentTypeId());
            entity.setAmountPerKg(request.getAmountPerKg());
            entity.setMin(request.getMin());
            entity.setMax(request.getMax());
            entity.setActive(true);

            ConfigureSilkIncentive saved = configureSilkIncentiveRepository.save(entity);
            response = mapper.configureSilkIncentiveEntityToObject(saved, ConfigureSilkIncentiveResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        }

        return response;
    }
}
