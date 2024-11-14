package com.sericulture.masterdata.service;


import com.sericulture.masterdata.model.api.mulberryTargetType.EditMulberryTargetTypeRequest;
import com.sericulture.masterdata.model.api.mulberryTargetType.MulberryTargetTypeRequest;
import com.sericulture.masterdata.model.api.mulberryTargetType.MulberryTargetTypeResponse;
import com.sericulture.masterdata.model.entity.MulberryTargetType;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MarketTypeMasterRepository;
import com.sericulture.masterdata.repository.MulberryTargetTypeRepository;
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
public class MulberryTargetTypeService {

    @Autowired
    MulberryTargetTypeRepository mulberryTargetTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public MulberryTargetTypeResponse insertMulberryTargetTypeDetails(MulberryTargetTypeRequest mulberryTargetTypeRequest){
        MulberryTargetTypeResponse mulberryTargetTypeResponse = new MulberryTargetTypeResponse();
        MulberryTargetType mulberryTargetType = mapper.mulberryTargetTypeObjectToEntity(mulberryTargetTypeRequest,MulberryTargetType.class);
        validator.validate(mulberryTargetType);
        List<MulberryTargetType> mulberryTargetTypeList = mulberryTargetTypeRepository.findByMulberryTargetTypeNameAndMulberryTargetTypeNameInKannadaAndActive(mulberryTargetTypeRequest.getMulberryTargetTypeName(),mulberryTargetTypeRequest.getMulberryTargetTypeNameInKannada(),true);
        if(!mulberryTargetTypeList.isEmpty() && mulberryTargetTypeList.stream().filter(MulberryTargetType::getActive).findAny().isPresent()){
            mulberryTargetTypeResponse.setError(true);
            mulberryTargetTypeResponse.setError_description("MulberryTargetType name already exist");
//        }
//        else if(!MulberryTargetTypeList.isEmpty() && MulberryTargetTypeList.stream().filter(Predicate.not(MulberryTargetType::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            MulberryTargetTypeResponse.setError(true);
//            MulberryTargetTypeResponse.setError_description("MulberryTargetType name already exist with inactive state");
        }else {
            mulberryTargetTypeResponse = mapper.mulberryTargetTypeEntityToObject(mulberryTargetTypeRepository.save(mulberryTargetType), MulberryTargetTypeResponse.class);
            mulberryTargetTypeResponse.setError(false);
        }
        return mulberryTargetTypeResponse;
    }

    public Map<String,Object> getPaginatedMulberryTargetTypeDetails(final Pageable pageable){
        return convertToMapResponse(mulberryTargetTypeRepository.findByActiveOrderByMulberryTargetTypeNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(mulberryTargetTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<MulberryTargetType> activeMulberryTargetTypes) {
        Map<String, Object> response = new HashMap<>();

        List<MulberryTargetTypeResponse> mulberryTargetTypeResponses = activeMulberryTargetTypes.getContent().stream()
                .map(mulberryTargetType -> mapper.mulberryTargetTypeEntityToObject(mulberryTargetType,MulberryTargetTypeResponse.class)).collect(Collectors.toList());
        response.put("mulberryTargetType",mulberryTargetTypeResponses);
        response.put("currentPage", activeMulberryTargetTypes.getNumber());
        response.put("totalItems", activeMulberryTargetTypes.getTotalElements());
        response.put("totalPages", activeMulberryTargetTypes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<MulberryTargetType> activeMulberryTargetTypes) {
        Map<String, Object> response = new HashMap<>();

        List<MulberryTargetTypeResponse> mulberryTargetTypeResponses = activeMulberryTargetTypes.stream()
                .map(mulberryTargetType -> mapper.mulberryTargetTypeEntityToObject(mulberryTargetType,MulberryTargetTypeResponse.class)).collect(Collectors.toList());
        response.put("mulberryTargetType",mulberryTargetTypeResponses);
        return response;
    }

    @Transactional
    public MulberryTargetTypeResponse deleteMulberryTargetTypeDetails(long id) {
        MulberryTargetTypeResponse mulberryTargetTypeResponse = new MulberryTargetTypeResponse();
        MulberryTargetType mulberryTargetType = mulberryTargetTypeRepository.findByMulberryTargetTypeIdAndActive(id, true);
        if (Objects.nonNull(mulberryTargetType)) {
            mulberryTargetType.setActive(false);
            mulberryTargetTypeResponse = mapper.mulberryTargetTypeEntityToObject(mulberryTargetTypeRepository.save(mulberryTargetType), MulberryTargetTypeResponse.class);
            mulberryTargetTypeResponse.setError(false);
        } else {
            mulberryTargetTypeResponse.setError(true);
            mulberryTargetTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return mulberryTargetTypeResponse;
    }

    public MulberryTargetTypeResponse getById(int id){
        MulberryTargetTypeResponse mulberryTargetTypeResponse = new MulberryTargetTypeResponse();
        MulberryTargetType mulberryTargetType = mulberryTargetTypeRepository.findByMulberryTargetTypeIdAndActive(id,true);
        if(mulberryTargetType == null){
            mulberryTargetTypeResponse.setError(true);
            mulberryTargetTypeResponse.setError_description("Invalid id");
        }else{
            mulberryTargetTypeResponse =  mapper.mulberryTargetTypeEntityToObject(mulberryTargetType,MulberryTargetTypeResponse.class);
            mulberryTargetTypeResponse.setError(false);
        }
        log.info("Entity is ",mulberryTargetType);
        return mulberryTargetTypeResponse;
    }

    @Transactional
    public MulberryTargetTypeResponse updateMulberryTargetTypeDetails(EditMulberryTargetTypeRequest mulberryTargetTypeRequest){
        MulberryTargetTypeResponse mulberryTargetTypeResponse = new MulberryTargetTypeResponse();
        List<MulberryTargetType> mulberryTargetTypeList = mulberryTargetTypeRepository.findByMulberryTargetTypeNameAndMulberryTargetTypeNameInKannadaAndActive(mulberryTargetTypeRequest.getMulberryTargetTypeName(),mulberryTargetTypeRequest.getMulberryTargetTypeNameInKannada(),true);
        if(mulberryTargetTypeList.size()>0){
            mulberryTargetTypeResponse.setError(true);
            mulberryTargetTypeResponse.setError_description("MulberryTargetType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {

            MulberryTargetType mulberryTargetType = mulberryTargetTypeRepository.findByMulberryTargetTypeIdAndActiveIn(mulberryTargetTypeRequest.getMulberryTargetTypeId(), Set.of(true,false));
            if(Objects.nonNull(mulberryTargetType)){
                mulberryTargetType.setMulberryTargetTypeName(mulberryTargetTypeRequest.getMulberryTargetTypeName());
                mulberryTargetType.setMulberryTargetTypeNameInKannada(mulberryTargetTypeRequest.getMulberryTargetTypeNameInKannada());
                mulberryTargetType.setActive(true);
                MulberryTargetType mulberryTargetType1 = mulberryTargetTypeRepository.save(mulberryTargetType);
                mulberryTargetTypeResponse = mapper.mulberryTargetTypeEntityToObject(mulberryTargetType1, MulberryTargetTypeResponse.class);
                mulberryTargetTypeResponse.setError(false);
            } else {
                mulberryTargetTypeResponse.setError(true);
                mulberryTargetTypeResponse.setError_description("Error occurred while fetching MulberryTargetType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return mulberryTargetTypeResponse;
    }

}
