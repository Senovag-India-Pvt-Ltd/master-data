package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.configure_reeling_shed.ConfigureReelingShedRequest;
import com.sericulture.masterdata.model.api.configure_reeling_shed.ConfigureReelingShedResponse;
import com.sericulture.masterdata.model.api.configure_reeling_shed.EditConfigureReelingShedRequest;
import com.sericulture.masterdata.model.entity.ConfigureReelingShed;
import com.sericulture.masterdata.model.entity.ConfigureReelingShed;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ConfigureReelingShedRepository;
import com.sericulture.masterdata.repository.ConfigureReelingShedRepository;
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
public class ConfigureReelingShedService {

    @Autowired
    private ConfigureReelingShedRepository configureReelingShedRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private CustomValidator validator;

    /**
     * ✅ Get Configure IMCB details by ID
     */
    public ConfigureReelingShedResponse getConfigureReelingShedDetails(long reelingShedId) {
        ConfigureReelingShedResponse response = new ConfigureReelingShedResponse();
        ConfigureReelingShed configureReelingShed = configureReelingShedRepository.findByReelingShedIdAndActive(reelingShedId, true);

        if (configureReelingShed == null) {
            response.setError(true);
            response.setError_description("Configure IMCB not found");
        } else {
            response = mapper.configureReelingShedEntityToObject(configureReelingShed, ConfigureReelingShedResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Insert new Configure IMCB record
     */
    @Transactional
    public ConfigureReelingShedResponse insertConfigureReelingShedDetails(ConfigureReelingShedRequest request) {
        ConfigureReelingShedResponse response = new ConfigureReelingShedResponse();
        ConfigureReelingShed entity = mapper.configureReelingShedObjectToEntity(request, ConfigureReelingShed.class);

        ConfigureReelingShed saved = configureReelingShedRepository.save(entity);
        response = mapper.configureReelingShedEntityToObject(saved, ConfigureReelingShedResponse.class);
        response.setError(false);

        return response;
    }
    /**
     * ✅ Pagination
     */
    public Map<String, Object> getPaginatedConfigureReelingShedDetails(Pageable pageable) {
        return convertToMapResponse(configureReelingShedRepository.findByActiveOrderByReelingShedIdAsc(true, pageable));
    }

    /**
     * ✅ Get all active Configure IMCB records
     */
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(configureReelingShedRepository.findByActive(isActive));
    }

    public Map<String,Object> findByReelingUnitAndSqftAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(Long machineTypeId,String reelingSqft,long componentTypeId,long componentId, long categoryId ,boolean isActive){
        return convertListEntityToMapResponse(configureReelingShedRepository.findByMachineTypeIdAndReelingSqftAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(machineTypeId,reelingSqft,componentTypeId,componentId,categoryId,isActive));
    }

    private Map<String, Object> convertToMapResponse(Page<ConfigureReelingShed> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureReelingShedResponse> responses = list.getContent().stream()
                .map(e -> mapper.configureReelingShedEntityToObject(e, ConfigureReelingShedResponse.class))
                .collect(Collectors.toList());
        response.put("configureReelingShed", responses);
        response.put("currentPage", list.getNumber());
        response.put("totalItems", list.getTotalElements());
        response.put("totalPages", list.getTotalPages());
        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(List<ConfigureReelingShed> list) {
        Map<String, Object> response = new HashMap<>();
        List<ConfigureReelingShedResponse> responses = list.stream()
                .map(e -> mapper.configureReelingShedEntityToObject(e, ConfigureReelingShedResponse.class))
                .collect(Collectors.toList());
        response.put("configureReelingShed", responses);
        return response;
    }

    /**
     * ✅ Delete (soft delete)
     */
    @Transactional
    public ConfigureReelingShedResponse deleteConfigureReelingShed(long id) {
        ConfigureReelingShedResponse response = new ConfigureReelingShedResponse();
        ConfigureReelingShed entity = configureReelingShedRepository.findByReelingShedIdAndActive(id, true);

        if (entity != null) {
            entity.setActive(false);
            ConfigureReelingShed saved = configureReelingShedRepository.save(entity);
            response = mapper.configureReelingShedEntityToObject(saved, ConfigureReelingShedResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        }
        return response;
    }

        public ConfigureReelingShedResponse getConfigureReelingShedByIdWithJoin(Long reelingShedId) {
            Object result = configureReelingShedRepository.getConfigureReelingShedByIdWithJoin(reelingShedId);
            if (result == null) {
                ConfigureReelingShedResponse response = new ConfigureReelingShedResponse();
                response.setError(true);
                response.setError_description("Configure Reeling Shed not found");
                return response;
            }
            Object[] row = (Object[]) result;
            return mapObjectArrayToResponse(row);
        }

        /** ✅ Paginated list with join */
        public Map<String, Object> getPaginatedConfigureReelingShedWithJoin(Pageable pageable) {
            Page<Object[]> rawPage = configureReelingShedRepository.getConfigureReelingShedListWithJoin(pageable);
            List<ConfigureReelingShedResponse> responses = rawPage.getContent().stream()
                    .map(this::mapObjectArrayToResponse)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("configureReelingShed", responses);
            response.put("currentPage", rawPage.getNumber());
            response.put("totalItems", rawPage.getTotalElements());
            response.put("totalPages", rawPage.getTotalPages());
            return response;
        }

        /** ✅ Mapper from Object[] to Response */
        private ConfigureReelingShedResponse mapObjectArrayToResponse(Object[] obj) {
            return new ConfigureReelingShedResponse(
                    obj[0] != null ? ((Number) obj[0]).longValue() : null,
                    obj[1] != null ? obj[1].toString() : null,
                    obj[2] != null ? obj[2].toString() : null,
                    obj[3] != null ? ((Number) obj[3]).longValue() : null,
                    obj[4] != null ? ((Number) obj[4]).longValue() : null,
                    obj[5] != null ? ((Number) obj[5]).longValue() : null,
                    obj[6] != null ? ((Number) obj[6]).longValue() : null,
                    obj[7] != null ? ((Number) obj[7]).floatValue() : null,
                    obj[8] != null ? ((Number) obj[8]).floatValue() : null,
                    obj[9] != null ? ((Number) obj[9]).floatValue() : null,
                    obj[10] != null ? obj[10].toString() : null,
                    obj[11] != null ? obj[11].toString() : null,
                    obj[12] != null ? obj[12].toString() : null,
                    obj[13] != null ? obj[13].toString() : null,
                    false,
                    null
            );
        }


    /**
     * ✅ Get by ID
     */
    public ConfigureReelingShedResponse getById(long id) {
        ConfigureReelingShedResponse response = new ConfigureReelingShedResponse();
        ConfigureReelingShed entity = configureReelingShedRepository.findByReelingShedIdAndActive(id, true);

        if (entity == null) {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        } else {
            response = mapper.configureReelingShedEntityToObject(entity, ConfigureReelingShedResponse.class);
            response.setError(false);
        }
        return response;
    }

    /**
     * ✅ Update Configure IMCB
     */
    @Transactional
    public ConfigureReelingShedResponse updateConfigureReelingShedDetails(EditConfigureReelingShedRequest request) {
        ConfigureReelingShedResponse response = new ConfigureReelingShedResponse();

//        // ✅ Check duplicate using ID exclusion
//        List<ConfigureReelingShed> existingList = configureReelingShedRepository.findByActiveAndReelingShedIdIsNot(true, request.getReelingShedId());
//        if (!existingList.isEmpty()) {
//            response.setError(true);
//            response.setError_description("Configure IMCB already exists");
//            return response;
//        }

        ConfigureReelingShed entity = configureReelingShedRepository.findByReelingShedIdAndActiveIn(request.getReelingShedId(), Set.of(true, false));
        if (entity != null) {
            entity.setReelingUnit(request.getReelingUnit());
            entity.setReelingSqft(request.getReelingSqft());
            entity.setCategoryId(request.getCategoryId());
            entity.setComponentId(request.getComponentId());
            entity.setComponentTypeId(request.getComponentTypeId());
            entity.setUnitCost(request.getUnitCost());
            entity.setMin(request.getMin());
            entity.setMax(request.getMax());
            entity.setMachineTypeId(request.getMachineTypeId());
            entity.setActive(true);

            ConfigureReelingShed saved = configureReelingShedRepository.save(entity);
            response = mapper.configureReelingShedEntityToObject(saved, ConfigureReelingShedResponse.class);
            response.setError(false);
        } else {
            response.setError(true);
            response.setError_description("Invalid IMCB ID");
        }

        return response;
    }
}
