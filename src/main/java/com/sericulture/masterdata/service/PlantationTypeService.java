package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.plantationType.EditPlantationTypeRequest;
import com.sericulture.masterdata.model.api.plantationType.PlantationTypeRequest;
import com.sericulture.masterdata.model.api.plantationType.PlantationTypeResponse;
import com.sericulture.masterdata.model.api.soilType.SoilTypeResponse;
import com.sericulture.masterdata.model.entity.PlantationType;
import com.sericulture.masterdata.model.entity.SoilType;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.PlantationTypeRepository;
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
public class PlantationTypeService {
    @Autowired
    PlantationTypeRepository plantationTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PlantationTypeResponse getPlantationTypeDetails(String plantationTypeName){
        PlantationType plantationType = null;
        if(plantationType==null){
            plantationType = plantationTypeRepository.findByPlantationTypeNameAndActive(plantationTypeName,true);
        }
        log.info("Entity is ",plantationType);
        return mapper.plantationTypeEntityToObject(plantationType,PlantationTypeResponse.class);
    }

    @Transactional
    public PlantationTypeResponse insertPlantationTypeDetails(PlantationTypeRequest plantationTypeRequest){
        PlantationType plantationType = mapper.plantationTypeObjectToEntity(plantationTypeRequest,PlantationType.class);
        validator.validate(plantationType);
        List<PlantationType> plantationTypeList = plantationTypeRepository.findByPlantationTypeName(plantationTypeRequest.getPlantationTypeName());
        if(!plantationTypeList.isEmpty() && plantationTypeList.stream().filter(PlantationType::getActive).findAny().isPresent()){
            throw new ValidationException("PlantationType name already exist");
        }
        if(!plantationTypeList.isEmpty() && plantationTypeList.stream().filter(Predicate.not(PlantationType::getActive)).findAny().isPresent()){
            throw new ValidationException("PlantationType name already exist with inactive state");
        }
        return mapper.plantationTypeEntityToObject(plantationTypeRepository.save(plantationType),PlantationTypeResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedPlantationTypeDetails(final Pageable pageable){
        return convertToMapResponse(plantationTypeRepository.findByActiveOrderByPlantationTypeIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(plantationTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<PlantationType> activePlantationTypes) {
        Map<String, Object> response = new HashMap<>();

        List<PlantationTypeResponse> plantationTypeResponses = activePlantationTypes.getContent().stream()
                .map(plantationType -> mapper.plantationTypeEntityToObject(plantationType,PlantationTypeResponse.class)).collect(Collectors.toList());
        response.put("plantationType",plantationTypeResponses);
        response.put("currentPage", activePlantationTypes.getNumber());
        response.put("totalItems", activePlantationTypes.getTotalElements());
        response.put("totalPages", activePlantationTypes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<PlantationType> activePlantationTypes) {
        Map<String, Object> response = new HashMap<>();

        List<PlantationTypeResponse> plantationTypeResponses = activePlantationTypes.stream()
                .map(plantationType -> mapper.plantationTypeEntityToObject(plantationType,PlantationTypeResponse.class)).collect(Collectors.toList());
        response.put("plantationType",plantationTypeResponses);
        return response;
    }

    @Transactional
    public void deletePlantationTypeDetails(long id) {
        PlantationType plantationType = plantationTypeRepository.findByPlantationTypeIdAndActive(id, true);
        if (Objects.nonNull(plantationType)) {
            plantationType.setActive(false);
            plantationTypeRepository.save(plantationType);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public PlantationTypeResponse getById(int id){
        PlantationType plantationType = plantationTypeRepository.findByPlantationTypeIdAndActive(id,true);
        if(plantationType == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",plantationType);
        return mapper.plantationTypeEntityToObject(plantationType,PlantationTypeResponse.class);
    }

    @Transactional
    public PlantationTypeResponse updatePlantationTypeDetails(EditPlantationTypeRequest plantationTypeRequest){
        List<PlantationType> plantationTypeList = plantationTypeRepository.findByPlantationTypeName(plantationTypeRequest.getPlantationTypeName());
        if(plantationTypeList.size()>0){
            throw new ValidationException("PlantationType already exists with this name, duplicates are not allowed.");
        }

        PlantationType plantationType = plantationTypeRepository.findByPlantationTypeIdAndActiveIn(plantationTypeRequest.getPlantationTypeId(), Set.of(true,false));
        if(Objects.nonNull(plantationType)){
            plantationType.setPlantationTypeName(plantationTypeRequest.getPlantationTypeName());
            plantationType.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching plantation type");
        }
        return mapper.plantationTypeEntityToObject(plantationTypeRepository.save(plantationType),PlantationTypeResponse.class);
    }

}
