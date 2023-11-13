package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.soilType.EditSoilTypeRequest;
import com.sericulture.masterdata.model.api.soilType.SoilTypeRequest;
import com.sericulture.masterdata.model.api.soilType.SoilTypeResponse;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.entity.SoilType;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.SoilTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SoilTypeService {
    @Autowired
    SoilTypeRepository soilTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SoilTypeResponse getSoilTypeDetails(String soilTypeName){
        SoilType soilType = null;
        if(soilType==null){
            soilType = soilTypeRepository.findBySoilTypeNameAndActive(soilTypeName,true);
        }
        log.info("Entity is ",soilType);
        return mapper.soilTypeEntityToObject(soilType,SoilTypeResponse.class);
    }

    @Transactional
    public SoilTypeResponse insertSoilTypeDetails(SoilTypeRequest soilTypeRequest){
        SoilType soilType = mapper.soilTypeObjectToEntity(soilTypeRequest,SoilType.class);
        validator.validate(soilType);
        List<SoilType> soilTypeList = soilTypeRepository.findBySoilTypeName(soilTypeRequest.getSoilTypeName());
        if(!soilTypeList.isEmpty() && soilTypeList.stream().filter(SoilType::getActive).findAny().isPresent()){
            throw new ValidationException("SoilType name already exist");
        }
        if(!soilTypeList.isEmpty() && soilTypeList.stream().filter(Predicate.not(SoilType::getActive)).findAny().isPresent()){
            throw new ValidationException("SoilType name already exist with inactive state");
        }
        return mapper.soilTypeEntityToObject(soilTypeRepository.save(soilType), SoilTypeResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedSoilTypeDetails(final Pageable pageable){
        return convertToMapResponse(soilTypeRepository.findByActiveOrderBySoilTypeIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(soilTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<SoilType> activeSoilTypes) {
        Map<String, Object> response = new HashMap<>();

        List<SoilTypeResponse> soilTypeResponses = activeSoilTypes.getContent().stream()
                .map(soilType -> mapper.soilTypeEntityToObject(soilType, SoilTypeResponse.class)).collect(Collectors.toList());
        response.put("soilType",soilTypeResponses);
        response.put("currentPage", activeSoilTypes.getNumber());
        response.put("totalItems", activeSoilTypes.getTotalElements());
        response.put("totalPages", activeSoilTypes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<SoilType> activeSoilTypes) {
        Map<String, Object> response = new HashMap<>();

        List<SoilTypeResponse> soilTypeResponses = activeSoilTypes.stream()
                .map(soilType -> mapper.soilTypeEntityToObject(soilType,SoilTypeResponse.class)).collect(Collectors.toList());
        response.put("soilType",soilTypeResponses);
        return response;
    }

    @Transactional
    public void deleteSoilTypeDetails(long id) {
        SoilType soilType = soilTypeRepository.findBySoilTypeIdAndActive(id, true);
        if (Objects.nonNull(soilType)) {
            soilType.setActive(false);
            soilTypeRepository.save(soilType);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public SoilTypeResponse getById(int id){
        SoilType soilType = soilTypeRepository.findBySoilTypeIdAndActive(id,true);
        if(soilType == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",soilType);
        return mapper.soilTypeEntityToObject(soilType,SoilTypeResponse.class);
    }

    @Transactional
    public SoilTypeResponse updateSoilTypeDetails(EditSoilTypeRequest soilTypeRequest){
        List<SoilType> soilTypeList = soilTypeRepository.findBySoilTypeName(soilTypeRequest.getSoilTypeName());
        if(soilTypeList.size()>0){
            throw new ValidationException("Soil Type already exists, duplicates are not allowed.");
        }

        SoilType soilType = soilTypeRepository.findBySoilTypeIdAndActiveIn(soilTypeRequest.getSoilTypeId(), Set.of(true,false));
        if(Objects.nonNull(soilType)){
            soilType.setSoilTypeName(soilTypeRequest.getSoilTypeName());
            soilType.setActive(true);
        }
        return mapper.soilTypeEntityToObject(soilTypeRepository.save(soilType),SoilTypeResponse.class);
    }
}
