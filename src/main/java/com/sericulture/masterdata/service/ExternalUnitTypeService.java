package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.externalUnitType.EditExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeResponse;
import com.sericulture.masterdata.model.api.traderTypeMaster.EditTraderTypeMasterRequest;
import com.sericulture.masterdata.model.api.traderTypeMaster.TraderTypeMasterRequest;
import com.sericulture.masterdata.model.api.traderTypeMaster.TraderTypeMasterResponse;
import com.sericulture.masterdata.model.entity.ExternalUnitType;
import com.sericulture.masterdata.model.entity.TraderTypeMaster;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ExternalUnitTypeResponse getExternalUnitTypeDetails(String externalUnitTypeName){
        ExternalUnitType externalUnitType = null;
        if(externalUnitType==null){
            externalUnitType = externalUnitTypeRepository.findByExternalUnitTypeNameAndActive(externalUnitTypeName,true);
        }
        log.info("Entity is ",externalUnitType);
        return mapper.externalUnitTypeEntityToObject(externalUnitType,ExternalUnitTypeResponse.class);
    }

    @Transactional
    public ExternalUnitTypeResponse insertExternalUnitTypeDetails(ExternalUnitTypeRequest externalUnitTypeRequest){
        ExternalUnitType externalUnitType = mapper.externalUnitTypeObjectToEntity(externalUnitTypeRequest,ExternalUnitType.class);
        validator.validate(externalUnitType);
        List<ExternalUnitType> externalUnitTypeList = externalUnitTypeRepository.findByExternalUnitTypeName(externalUnitTypeRequest.getExternalUnitTypeName());
        if(!externalUnitTypeList.isEmpty() && externalUnitTypeList.stream().filter(ExternalUnitType::getActive).findAny().isPresent()){
            throw new ValidationException("External Unit Type name already exist");
        }
        if(!externalUnitTypeList.isEmpty() && externalUnitTypeList.stream().filter(Predicate.not(ExternalUnitType::getActive)).findAny().isPresent()){
            throw new ValidationException("External Unit Type name already exist with inactive state");
        }
        return mapper.externalUnitTypeEntityToObject(externalUnitTypeRepository.save(externalUnitType),ExternalUnitTypeResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedExternalUnitTypeDetails(final Pageable pageable){
        return convertToMapResponse(externalUnitTypeRepository.findByActiveOrderByExternalUnitTypeIdAsc( true, pageable));
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

    @Transactional
    public void deleteExternalUnitTypeDetails(long id) {
        ExternalUnitType externalUnitType = externalUnitTypeRepository.findByExternalUnitTypeIdAndActive(id, true);
        if (Objects.nonNull(externalUnitType)) {
            externalUnitType.setActive(false);
            externalUnitTypeRepository.save(externalUnitType);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public ExternalUnitTypeResponse getById(int id){
        ExternalUnitType externalUnitType = externalUnitTypeRepository.findByExternalUnitTypeIdAndActive(id,true);
        if(externalUnitType == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",externalUnitType);
        return mapper.externalUnitTypeEntityToObject(externalUnitType,ExternalUnitTypeResponse.class);
    }

    @Transactional
    public ExternalUnitTypeResponse updateExternalUnitTypeDetails(EditExternalUnitTypeRequest externalUnitTypeRequest){
        List<ExternalUnitType> externalUnitTypeList = externalUnitTypeRepository.findByExternalUnitTypeName(externalUnitTypeRequest.getExternalUnitTypeName());
        if(externalUnitTypeList.size()>0){
            throw new ValidationException("External Unit Type already exists with this name, duplicates are not allowed.");
        }

        ExternalUnitType externalUnitType = externalUnitTypeRepository.findByExternalUnitTypeIdAndActiveIn(externalUnitTypeRequest.getExternalUnitTypeId(), Set.of(true,false));
        if(Objects.nonNull(externalUnitType)){
            externalUnitType.setExternalUnitTypeName(externalUnitTypeRequest.getExternalUnitTypeName());
            externalUnitType.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching state");
        }
        return mapper.externalUnitTypeEntityToObject(externalUnitTypeRepository.save(externalUnitType),ExternalUnitTypeResponse.class);
    }

}
