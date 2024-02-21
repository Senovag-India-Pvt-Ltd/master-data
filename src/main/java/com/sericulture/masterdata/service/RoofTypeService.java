package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.roofType.EditRoofTypeRequest;
import com.sericulture.masterdata.model.api.roofType.RoofTypeRequest;
import com.sericulture.masterdata.model.api.roofType.RoofTypeResponse;
import com.sericulture.masterdata.model.api.silkwormvariety.SilkWormVarietyResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.RoofType;
import com.sericulture.masterdata.model.entity.SilkWormVariety;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RoofTypeRepository;
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
public class RoofTypeService {
    @Autowired
    RoofTypeRepository roofTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RoofTypeResponse getRoofTypeDetails(String roofTypeName){
        RoofTypeResponse roofTypeResponse = new RoofTypeResponse();
        RoofType roofType = roofTypeRepository.findByRoofTypeNameAndActive(roofTypeName,true);
        if(roofType==null){
            roofTypeResponse.setError(true);
            roofTypeResponse.setError_description("Roof Type not found");
        }else{
            roofTypeResponse = mapper.roofTypeEntityToObject(roofType, RoofTypeResponse.class);
            roofTypeResponse.setError(false);
        }
        log.info("Entity is ",roofType);
        return roofTypeResponse;
    }

    @Transactional
    public RoofTypeResponse insertRoofTypeDetails(RoofTypeRequest roofTypeRequest){
        RoofTypeResponse roofTypeResponse = new RoofTypeResponse();
        RoofType roofType = mapper.roofTypeObjectToEntity(roofTypeRequest,RoofType.class);
        validator.validate(roofType);
        List<RoofType> roofTypeList = roofTypeRepository.findByRoofTypeNameAndRoofTypeNameInKannada(roofTypeRequest.getRoofTypeName(),roofTypeRequest.getRoofTypeNameInKannada());
        if(!roofTypeList.isEmpty() && roofTypeList.stream().filter(RoofType::getActive).findAny().isPresent()){
            roofTypeResponse.setError(true);
            roofTypeResponse.setError_description("RoofType name already exist");
//        }
//        else if(!roofTypeList.isEmpty() && roofTypeList.stream().filter(Predicate.not(RoofType::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            roofTypeResponse.setError(true);
//            roofTypeResponse.setError_description("RoofType name already exist with inactive state");
        }else {
            roofTypeResponse = mapper.roofTypeEntityToObject(roofTypeRepository.save(roofType), RoofTypeResponse.class);
            roofTypeResponse.setError(false);
        }
        return roofTypeResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRoofTypeDetails(final Pageable pageable){
        return convertToMapResponse(roofTypeRepository.findByActiveOrderByRoofTypeNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(roofTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<RoofType> activeRoofTypes) {
        Map<String, Object> response = new HashMap<>();

        List<RoofTypeResponse> roofTypeResponses = activeRoofTypes.getContent().stream()
                .map(roofType -> mapper.roofTypeEntityToObject(roofType,RoofTypeResponse.class)).collect(Collectors.toList());
        response.put("roofType",roofTypeResponses);
        response.put("currentPage", activeRoofTypes.getNumber());
        response.put("totalItems", activeRoofTypes.getTotalElements());
        response.put("totalPages", activeRoofTypes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RoofType> activeRoofTypes) {
        Map<String, Object> response = new HashMap<>();

        List<RoofTypeResponse> roofTypeResponses = activeRoofTypes.stream()
                .map(roofType -> mapper.roofTypeEntityToObject(roofType,RoofTypeResponse.class)).collect(Collectors.toList());
        response.put("roofType",roofTypeResponses);
        return response;
    }

    @Transactional
    public RoofTypeResponse deleteRoofTypeDetails(long id) {
        RoofTypeResponse roofTypeResponse = new RoofTypeResponse();
        RoofType roofType = roofTypeRepository.findByRoofTypeIdAndActive(id, true);
        if (Objects.nonNull(roofType)) {
            roofType.setActive(false);
            roofTypeResponse = mapper.roofTypeEntityToObject(roofTypeRepository.save(roofType), RoofTypeResponse.class);
            roofTypeResponse.setError(false);
        } else {
            roofTypeResponse.setError(true);
            roofTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return roofTypeResponse;
    }

    @Transactional
    public RoofTypeResponse getById(int id){
        RoofTypeResponse roofTypeResponse = new RoofTypeResponse();
        RoofType roofType = roofTypeRepository.findByRoofTypeIdAndActive(id,true);
        if(roofType == null){
            roofTypeResponse.setError(true);
            roofTypeResponse.setError_description("Invalid id");
        }else{
            roofTypeResponse =  mapper.roofTypeEntityToObject(roofType,RoofTypeResponse.class);
            roofTypeResponse.setError(false);
        }
        log.info("Entity is ",roofType);
        return roofTypeResponse;
    }

    @Transactional
    public RoofTypeResponse updateRoofTypeDetails(EditRoofTypeRequest roofTypeRequest) {
        RoofTypeResponse roofTypeResponse = new RoofTypeResponse();
        List<RoofType> roofTypeList = roofTypeRepository.findByRoofTypeNameAndRoofTypeNameInKannadaAndActive(roofTypeRequest.getRoofTypeName(),roofTypeRequest.getRoofTypeNameInKannada(), true);
        if (roofTypeList.size() > 0) {
            roofTypeResponse.setError(true);
            roofTypeResponse.setError_description("RoofType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            RoofType roofType = roofTypeRepository.findByRoofTypeIdAndActiveIn(roofTypeRequest.getRoofTypeId(), Set.of(true, false));
            if (Objects.nonNull(roofType)) {
                roofType.setRoofTypeName(roofTypeRequest.getRoofTypeName());
                roofType.setRoofTypeNameInKannada(roofTypeRequest.getRoofTypeNameInKannada());
                roofType.setActive(true);
                RoofType roofType1 = roofTypeRepository.save(roofType);
                roofTypeResponse = mapper.roofTypeEntityToObject(roofType1, RoofTypeResponse.class);
                roofTypeResponse.setError(false);
            } else {
                roofTypeResponse.setError(true);
                roofTypeResponse.setError_description("Error occurred while fetching RoofType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return roofTypeResponse;
    }
}
