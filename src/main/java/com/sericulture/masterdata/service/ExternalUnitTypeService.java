package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.externalUnitType.EditExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeResponse;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.traderTypeMaster.EditTraderTypeMasterRequest;
import com.sericulture.masterdata.model.api.traderTypeMaster.TraderTypeMasterRequest;
import com.sericulture.masterdata.model.api.traderTypeMaster.TraderTypeMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.ExternalUnitType;
import com.sericulture.masterdata.model.entity.Hobli;
import com.sericulture.masterdata.model.entity.TraderTypeMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.EducationRepository;
import com.sericulture.masterdata.repository.ExternalUnitTypeRepository;
import com.sericulture.masterdata.repository.TraderTypeMasterRepository;
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
public class ExternalUnitTypeService {
    @Autowired
    ExternalUnitTypeRepository externalUnitTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public ExternalUnitTypeResponse getExternalUnitTypeDetails(String externalUnitTypeName){
        ExternalUnitTypeResponse externalUnitTypeResponse = new ExternalUnitTypeResponse();
        ExternalUnitType externalUnitType = externalUnitTypeRepository.findByExternalUnitTypeNameAndActive(externalUnitTypeName,true);
        if(externalUnitType==null){
            externalUnitTypeResponse.setError(true);
            externalUnitTypeResponse.setError_description("ExternalUnitType not found");
        }else{
            externalUnitTypeResponse = mapper.externalUnitTypeEntityToObject(externalUnitType, ExternalUnitTypeResponse.class);
            externalUnitTypeResponse.setError(false);
        }
        log.info("Entity is ",externalUnitType);
        return externalUnitTypeResponse;
    }

    @Transactional
    public ExternalUnitTypeResponse insertExternalUnitTypeDetails(ExternalUnitTypeRequest externalUnitTypeRequest){
        ExternalUnitTypeResponse externalUnitTypeResponse = new ExternalUnitTypeResponse();
        ExternalUnitType externalUnitType = mapper.externalUnitTypeObjectToEntity(externalUnitTypeRequest,ExternalUnitType.class);
        validator.validate(externalUnitType);
        List<ExternalUnitType> externalUnitTypeList = externalUnitTypeRepository.findByExternalUnitTypeNameAndExternalUnitTypeNameInKannadaAndActive(externalUnitTypeRequest.getExternalUnitTypeName(),externalUnitTypeRequest.getExternalUnitTypeNameInKannada(), true);
        if(!externalUnitTypeList.isEmpty() && externalUnitTypeList.stream().filter(ExternalUnitType::getActive).findAny().isPresent()){
            externalUnitTypeResponse.setError(true);
            externalUnitTypeResponse.setError_description("ExternalUnitType name already exist");
        }
        else if(!externalUnitTypeList.isEmpty() && externalUnitTypeList.stream().filter(Predicate.not(ExternalUnitType::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            externalUnitTypeResponse.setError(true);
            externalUnitTypeResponse.setError_description("ExternalUnitType name already exist with inactive state");
        }else {
            externalUnitTypeResponse = mapper.externalUnitTypeEntityToObject(externalUnitTypeRepository.save(externalUnitType), ExternalUnitTypeResponse.class);
            externalUnitTypeResponse.setError(false);
        }
        return externalUnitTypeResponse;
    }

    public Map<String,Object> getPaginatedExternalUnitTypeDetails(final Pageable pageable){
        return convertToMapResponse(externalUnitTypeRepository.findByActiveOrderByExternalUnitTypeNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(externalUnitTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ExternalUnitType> activeExternalUnitTypes) {
        Map<String, Object> response = new HashMap<>();

        List<ExternalUnitTypeResponse> externalUnitTypeResponses = activeExternalUnitTypes.getContent().stream()
                .map(externalUnitType -> mapper.externalUnitTypeEntityToObject(externalUnitType,ExternalUnitTypeResponse.class)).collect(Collectors.toList());
        response.put("externalUnitType",externalUnitTypeResponses);
        response.put("currentPage", activeExternalUnitTypes.getNumber());
        response.put("totalItems", activeExternalUnitTypes.getTotalElements());
        response.put("totalPages", activeExternalUnitTypes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ExternalUnitType> activeExternalUnitTypes) {
        Map<String, Object> response = new HashMap<>();

        List<ExternalUnitTypeResponse> externalUnitTypeResponses = activeExternalUnitTypes.stream()
                .map(externalUnitType -> mapper.externalUnitTypeEntityToObject(externalUnitType,ExternalUnitTypeResponse.class)).collect(Collectors.toList());
        response.put("externalUnitType",externalUnitTypeResponses);
        return response;
    }

    @Transactional
    public ExternalUnitTypeResponse deleteExternalUnitTypeDetails(long id) {
        ExternalUnitTypeResponse externalUnitTypeResponse = new ExternalUnitTypeResponse();
        ExternalUnitType externalUnitType = externalUnitTypeRepository.findByExternalUnitTypeIdAndActive(id, true);
        if (Objects.nonNull(externalUnitType)) {
            externalUnitType.setActive(false);
            externalUnitTypeResponse = mapper.externalUnitTypeEntityToObject(externalUnitTypeRepository.save(externalUnitType), ExternalUnitTypeResponse.class);
            externalUnitTypeResponse.setError(false);
        } else {
            externalUnitTypeResponse.setError(true);
            externalUnitTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return externalUnitTypeResponse;
    }

    public ExternalUnitTypeResponse getById(int id){
        ExternalUnitTypeResponse externalUnitTypeResponse = new ExternalUnitTypeResponse();
        ExternalUnitType externalUnitType = externalUnitTypeRepository.findByExternalUnitTypeIdAndActive(id,true);
        if(externalUnitType == null){
            externalUnitTypeResponse.setError(true);
            externalUnitTypeResponse.setError_description("Invalid id");
        }else{
            externalUnitTypeResponse =  mapper.externalUnitTypeEntityToObject(externalUnitType,ExternalUnitTypeResponse.class);
            externalUnitTypeResponse.setError(false);
        }
        log.info("Entity is ",externalUnitType);
        return externalUnitTypeResponse;
    }

    @Transactional
    public ExternalUnitTypeResponse updateExternalUnitTypeDetails(EditExternalUnitTypeRequest externalUnitTypeRequest) {
        ExternalUnitTypeResponse externalUnitTypeResponse = new ExternalUnitTypeResponse();
        List<ExternalUnitType> externalUnitTypeList = externalUnitTypeRepository.findByExternalUnitTypeNameAndExternalUnitTypeNameInKannadaAndActive(externalUnitTypeRequest.getExternalUnitTypeName(),externalUnitTypeRequest.getExternalUnitTypeNameInKannada(), true);
        if (externalUnitTypeList.size() > 0) {
            externalUnitTypeResponse.setError(true);
            externalUnitTypeResponse.setError_description("ExternalUnitType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ExternalUnitType externalUnitType = externalUnitTypeRepository.findByExternalUnitTypeIdAndActiveIn(externalUnitTypeRequest.getExternalUnitTypeId(), Set.of(true, false));
            if (Objects.nonNull(externalUnitType)) {
                externalUnitType.setExternalUnitTypeName(externalUnitTypeRequest.getExternalUnitTypeName());
                externalUnitType.setExternalUnitTypeNameInKannada(externalUnitTypeRequest.getExternalUnitTypeNameInKannada());
                externalUnitType.setActive(true);
                ExternalUnitType externalUnitType1 = externalUnitTypeRepository.save(externalUnitType);
                externalUnitTypeResponse = mapper.externalUnitTypeEntityToObject(externalUnitType1, ExternalUnitTypeResponse.class);
                externalUnitTypeResponse.setError(false);
            } else {
                externalUnitTypeResponse.setError(true);
                externalUnitTypeResponse.setError_description("Error occurred while fetching ExternalUnitType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return externalUnitTypeResponse;
    }
}
