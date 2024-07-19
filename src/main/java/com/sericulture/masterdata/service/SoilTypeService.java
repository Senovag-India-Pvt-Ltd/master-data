package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.soilType.EditSoilTypeRequest;
import com.sericulture.masterdata.model.api.soilType.SoilTypeRequest;
import com.sericulture.masterdata.model.api.soilType.SoilTypeResponse;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.SoilType;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.Village;
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


    @Transactional
    public SoilTypeResponse insertSoilTypeDetails(SoilTypeRequest soilTypeRequest){
        SoilTypeResponse soilTypeResponse = new SoilTypeResponse();
        SoilType soilType = mapper.soilTypeObjectToEntity(soilTypeRequest,SoilType.class);
        validator.validate(soilType);
        List<SoilType> soilTypeList = soilTypeRepository.findBySoilTypeNameAndSoilTypeNameInKannada(soilTypeRequest.getSoilTypeName(),soilTypeRequest.getSoilTypeNameInKannada());
        if(!soilTypeList.isEmpty() && soilTypeList.stream().filter(SoilType::getActive).findAny().isPresent()){
            soilTypeResponse.setError(true);
            soilTypeResponse.setError_description("SoilType name already exist");
//        }
//        else if(!soilTypeList.isEmpty() && soilTypeList.stream().filter(Predicate.not(SoilType::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            soilTypeResponse.setError(true);
//            soilTypeResponse.setError_description("SoilType name already exist with inactive state");
        }else {
            soilTypeResponse = mapper.soilTypeEntityToObject(soilTypeRepository.save(soilType), SoilTypeResponse.class);
            soilTypeResponse.setError(false);
        }
        return soilTypeResponse;
    }

    public Map<String,Object> getPaginatedSoilTypeDetails(final Pageable pageable){
        return convertToMapResponse(soilTypeRepository.findByActiveOrderBySoilTypeNameAsc( true, pageable));
    }

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
    public SoilTypeResponse deleteSoilTypeDetails(long id) {
        SoilTypeResponse soilTypeResponse = new SoilTypeResponse();
        SoilType soilType = soilTypeRepository.findBySoilTypeIdAndActive(id, true);
        if (Objects.nonNull(soilType)) {
            soilType.setActive(false);
            soilTypeResponse = mapper.soilTypeEntityToObject(soilTypeRepository.save(soilType), SoilTypeResponse.class);
            soilTypeResponse.setError(false);
        } else {
            soilTypeResponse.setError(true);
            soilTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return soilTypeResponse;
    }

    public SoilTypeResponse getById(int id){
        SoilTypeResponse soilTypeResponse = new SoilTypeResponse();
        SoilType soilType = soilTypeRepository.findBySoilTypeIdAndActive(id,true);
        if(soilType == null){
            soilTypeResponse.setError(true);
            soilTypeResponse.setError_description("Invalid id");
        }else{
            soilTypeResponse =  mapper.soilTypeEntityToObject(soilType,SoilTypeResponse.class);
            soilTypeResponse.setError(false);
        }
        log.info("Entity is ",soilType);
        return soilTypeResponse;
    }

    @Transactional
    public SoilTypeResponse updateSoilTypeDetails(EditSoilTypeRequest soilTypeRequest) {
        SoilTypeResponse soilTypeResponse = new SoilTypeResponse();
        List<SoilType> soilTypeList = soilTypeRepository.findByActiveAndSoilTypeNameAndSoilTypeNameInKannada(true,soilTypeRequest.getSoilTypeName(), soilTypeRequest.getSoilTypeNameInKannada());
        if (soilTypeList.size() > 0) {
            soilTypeResponse.setError(true);
            soilTypeResponse.setError_description("SoilType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            SoilType soilType = soilTypeRepository.findBySoilTypeIdAndActiveIn(soilTypeRequest.getSoilTypeId(), Set.of(true, false));
            if (Objects.nonNull(soilType)) {
                soilType.setSoilTypeName(soilTypeRequest.getSoilTypeName());
                soilType.setSoilTypeNameInKannada(soilTypeRequest.getSoilTypeNameInKannada());
                soilType.setActive(true);
                SoilType soilType1 = soilTypeRepository.save(soilType);
                soilTypeResponse = mapper.soilTypeEntityToObject(soilType1, SoilTypeResponse.class);
                soilTypeResponse.setError(false);
            } else {
                soilTypeResponse.setError(true);
                soilTypeResponse.setError_description("Error occurred while fetching SoilType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return soilTypeResponse;
    }
}
