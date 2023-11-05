package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.roofType.EditRoofTypeRequest;
import com.sericulture.masterdata.model.api.roofType.RoofTypeRequest;
import com.sericulture.masterdata.model.api.roofType.RoofTypeResponse;
import com.sericulture.masterdata.model.entity.RoofType;
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
        RoofType roofType = null;
        if(roofType==null){
            roofType = roofTypeRepository.findByRoofTypeNameAndActive(roofTypeName,true);
        }
        log.info("Entity is ",roofType);
        return mapper.roofTypeEntityToObject(roofType,RoofTypeResponse.class);
    }

    @Transactional
    public RoofTypeResponse insertRoofTypeDetails(RoofTypeRequest roofTypeRequest){
        RoofType roofType = mapper.roofTypeObjectToEntity(roofTypeRequest,RoofType.class);
        validator.validate(roofType);
        List<RoofType> roofTypeList = roofTypeRepository.findByRoofTypeName(roofTypeRequest.getRoofTypeName());
        if(!roofTypeList.isEmpty() && roofTypeList.stream().filter(RoofType::getActive).findAny().isPresent()){
            throw new ValidationException("RoofType name already exist");
        }
        if(!roofTypeList.isEmpty() && roofTypeList.stream().filter(Predicate.not(RoofType::getActive)).findAny().isPresent()){
            throw new ValidationException("RoofType name already exist with inactive state");
        }
        return mapper.roofTypeEntityToObject(roofTypeRepository.save(roofType),RoofTypeResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRoofTypeDetails(final Pageable pageable){
        return convertToMapResponse(roofTypeRepository.findByActiveOrderByRoofTypeIdAsc( true, pageable));
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

    @Transactional
    public void deleteRoofTypeDetails(long id) {
        RoofType roofType = roofTypeRepository.findByRoofTypeIdAndActive(id, true);
        if (Objects.nonNull(roofType)) {
            roofType.setActive(false);
            roofTypeRepository.save(roofType);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public RoofTypeResponse getById(int id){
        RoofType roofType = roofTypeRepository.findByRoofTypeIdAndActive(id,true);
        if(roofType == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",roofType);
        return mapper.roofTypeEntityToObject(roofType,RoofTypeResponse.class);
    }

    @Transactional
    public RoofTypeResponse updateRoofTypeDetails(EditRoofTypeRequest roofTypeRequest){
        List<RoofType> roofTypeList = roofTypeRepository.findByRoofTypeName(roofTypeRequest.getRoofTypeName());
        if(roofTypeList.size()>0){
            throw new ValidationException("RoofType already exists with this name, duplicates are not allowed.");
        }

        RoofType roofType = roofTypeRepository.findByRoofTypeIdAndActiveIn(roofTypeRequest.getRoofTypeId(), Set.of(true,false));
        if(Objects.nonNull(roofType)){
            roofType.setRoofTypeName(roofTypeRequest.getRoofTypeName());
            roofType.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching roof type");
        }
        return mapper.roofTypeEntityToObject(roofTypeRepository.save(roofType),RoofTypeResponse.class);
    }
}
