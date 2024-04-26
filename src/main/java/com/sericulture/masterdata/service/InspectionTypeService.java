package com.sericulture.masterdata.service;


import com.sericulture.masterdata.model.api.inspectionType.EditInspectionTypeRequest;
import com.sericulture.masterdata.model.api.inspectionType.InspectionTypeRequest;
import com.sericulture.masterdata.model.api.inspectionType.InspectionTypeResponse;
import com.sericulture.masterdata.model.entity.InspectionType;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.InspectionTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InspectionTypeService {
    @Autowired
    InspectionTypeRepository inspectionTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public InspectionTypeResponse getInspectionTypeDetails(String name){
        InspectionTypeResponse inspectionTypeResponse = new InspectionTypeResponse();
        InspectionType inspectionType = inspectionTypeRepository.findByNameAndActive(name,true);
        if(inspectionType==null){
            inspectionTypeResponse.setError(true);
            inspectionTypeResponse.setError_description("Inspection Type not found");
        }else{
            inspectionTypeResponse = mapper.inspectionTypeEntityToObject(inspectionType, InspectionTypeResponse.class);
            inspectionTypeResponse.setError(false);
        }
        log.info("Entity is ",inspectionType);
        return inspectionTypeResponse;
    }

    @Transactional
    public InspectionTypeResponse insertInspectionTypeDetails(InspectionTypeRequest inspectionTypeRequest){
        InspectionTypeResponse inspectionTypeResponse = new InspectionTypeResponse();
        InspectionType inspectionType = mapper.inspectionTypeObjectToEntity(inspectionTypeRequest,InspectionType.class);
        validator.validate(inspectionType);
        List<InspectionType> inspectionTypeList = inspectionTypeRepository.findByNameAndNameInKannada(inspectionTypeRequest.getName(),inspectionTypeRequest.getNameInKannada());
        if(!inspectionTypeList.isEmpty() && inspectionTypeList.stream().filter(InspectionType::getActive).findAny().isPresent()){
            inspectionTypeResponse.setError(true);
            inspectionTypeResponse.setError_description("InspectionType name already exist");
//        }
//        else if(!inspectionTypeList.isEmpty() && inspectionTypeList.stream().filter(Predicate.not(InspectionType::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            InspectionTypeResponse.setError(true);
//            InspectionTypeResponse.setError_description("InspectionType name already exist with inactive state");
        }else {
            inspectionTypeResponse = mapper.inspectionTypeEntityToObject(inspectionTypeRepository.save(inspectionType), InspectionTypeResponse.class);
            inspectionTypeResponse.setError(false);
        }
        return inspectionTypeResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedInspectionTypeDetails(final Pageable pageable){
        return convertToMapResponse(inspectionTypeRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(inspectionTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<InspectionType> activeInspectionTypes) {
        Map<String, Object> response = new HashMap<>();

        List<InspectionTypeResponse> inspectionTypeResponses = activeInspectionTypes.getContent().stream()
                .map(inspectionType -> mapper.inspectionTypeEntityToObject(inspectionType,InspectionTypeResponse.class)).collect(Collectors.toList());
        response.put("inspectionType",inspectionTypeResponses);
        response.put("currentPage", activeInspectionTypes.getNumber());
        response.put("totalItems", activeInspectionTypes.getTotalElements());
        response.put("totalPages", activeInspectionTypes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<InspectionType> activeInspectionTypes) {
        Map<String, Object> response = new HashMap<>();

        List<InspectionTypeResponse> inspectionTypeResponses = activeInspectionTypes.stream()
                .map(inspectionType -> mapper.inspectionTypeEntityToObject(inspectionType,InspectionTypeResponse.class)).collect(Collectors.toList());
        response.put("inspectionType",inspectionTypeResponses);
        return response;
    }

    @Transactional
    public InspectionTypeResponse deleteInspectionTypeDetails(long id) {
        InspectionTypeResponse inspectionTypeResponse = new InspectionTypeResponse();
        InspectionType inspectionType = inspectionTypeRepository.findByInspectionTypeIdAndActive(id, true);
        if (Objects.nonNull(inspectionType)) {
            inspectionType.setActive(false);
            inspectionTypeResponse = mapper.inspectionTypeEntityToObject(inspectionTypeRepository.save(inspectionType), InspectionTypeResponse.class);
            inspectionTypeResponse.setError(false);
        } else {
            inspectionTypeResponse.setError(true);
            inspectionTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return inspectionTypeResponse;
    }

    @Transactional
    public InspectionTypeResponse getById(int id){
        InspectionTypeResponse inspectionTypeResponse = new InspectionTypeResponse();
        InspectionType inspectionType = inspectionTypeRepository.findByInspectionTypeIdAndActive(id,true);
        if(inspectionType == null){
            inspectionTypeResponse.setError(true);
            inspectionTypeResponse.setError_description("Invalid id");
        }else{
            inspectionTypeResponse =  mapper.inspectionTypeEntityToObject(inspectionType,InspectionTypeResponse.class);
            inspectionTypeResponse.setError(false);
        }
        log.info("Entity is ",inspectionType);
        return inspectionTypeResponse;
    }

    @Transactional
    public InspectionTypeResponse updateInspectionTypeDetails(EditInspectionTypeRequest inspectionTypeRequest) {
        InspectionTypeResponse inspectionTypeResponse = new InspectionTypeResponse();
        List<InspectionType> inspectionTypeList = inspectionTypeRepository.findByActiveAndNameAndNameInKannada(true,inspectionTypeRequest.getName(),inspectionTypeRequest.getNameInKannada());
        if (inspectionTypeList.size() > 0) {
            inspectionTypeResponse.setError(true);
            inspectionTypeResponse.setError_description("InspectionType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            InspectionType inspectionType = inspectionTypeRepository.findByInspectionTypeIdAndActiveIn(inspectionTypeRequest.getInspectionTypeId(), Set.of(true, false));
            if (Objects.nonNull(inspectionType)) {
                inspectionType.setName(inspectionTypeRequest.getName());
                inspectionType.setNameInKannada(inspectionTypeRequest.getNameInKannada());
                inspectionType.setValue(inspectionTypeRequest.getValue());
                inspectionType.setVersion(inspectionTypeRequest.getVersion());

                inspectionType.setActive(true);
                InspectionType inspectionType1 = inspectionTypeRepository.save(inspectionType);
                inspectionTypeResponse = mapper.inspectionTypeEntityToObject(inspectionType1, InspectionTypeResponse.class);
                inspectionTypeResponse.setError(false);
            } else{
                inspectionTypeResponse.setError(true);
                inspectionTypeResponse.setError_description("Error occurred while fetching InspectionType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return inspectionTypeResponse;
    }
}
