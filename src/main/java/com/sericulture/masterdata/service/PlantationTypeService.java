package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.plantationType.EditPlantationTypeRequest;
import com.sericulture.masterdata.model.api.plantationType.PlantationTypeRequest;
import com.sericulture.masterdata.model.api.plantationType.PlantationTypeResponse;
import com.sericulture.masterdata.model.api.soilType.SoilTypeResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.PlantationType;
import com.sericulture.masterdata.model.entity.SoilType;
import com.sericulture.masterdata.model.entity.Village;
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
        PlantationTypeResponse plantationTypeResponse = new PlantationTypeResponse();
        PlantationType plantationType = plantationTypeRepository.findByPlantationTypeNameAndActive(plantationTypeName,true);
        if(plantationType==null){
            plantationTypeResponse.setError(true);
            plantationTypeResponse.setError_description("Plantation Type not found");
        }else{
            plantationTypeResponse = mapper.plantationTypeEntityToObject(plantationType, PlantationTypeResponse.class);
            plantationTypeResponse.setError(false);
        }
        log.info("Entity is ",plantationType);
        return plantationTypeResponse;
    }

    @Transactional
    public PlantationTypeResponse insertPlantationTypeDetails(PlantationTypeRequest plantationTypeRequest){
        PlantationTypeResponse plantationTypeResponse = new PlantationTypeResponse();
        PlantationType plantationType = mapper.plantationTypeObjectToEntity(plantationTypeRequest,PlantationType.class);
        validator.validate(plantationType);
        List<PlantationType> plantationTypeList = plantationTypeRepository.findByPlantationTypeNameAndPlantationTypeNameInKannada(plantationTypeRequest.getPlantationTypeName(), plantationTypeRequest.getPlantationTypeNameInKannada());
        if(!plantationTypeList.isEmpty() && plantationTypeList.stream().filter(PlantationType::getActive).findAny().isPresent()){
            plantationTypeResponse.setError(true);
            plantationTypeResponse.setError_description("PlantationType name already exist");
//        }
//        else if(!plantationTypeList.isEmpty() && plantationTypeList.stream().filter(Predicate.not(PlantationType::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            plantationTypeResponse.setError(true);
//            plantationTypeResponse.setError_description("PlantationType name already exist with inactive state");
        }else {
            plantationTypeResponse = mapper.plantationTypeEntityToObject(plantationTypeRepository.save(plantationType), PlantationTypeResponse.class);
            plantationTypeResponse.setError(false);
        }
        return plantationTypeResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedPlantationTypeDetails(final Pageable pageable){
        return convertToMapResponse(plantationTypeRepository.findByActiveOrderByPlantationTypeNameAsc( true, pageable));
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
    public PlantationTypeResponse deletePlantationTypeDetails(long id) {
        PlantationTypeResponse plantationTypeResponse = new PlantationTypeResponse();
        PlantationType plantationType = plantationTypeRepository.findByPlantationTypeIdAndActive(id, true);
        if (Objects.nonNull(plantationType)) {
            plantationType.setActive(false);
            plantationTypeResponse = mapper.plantationTypeEntityToObject(plantationTypeRepository.save(plantationType), PlantationTypeResponse.class);
            plantationTypeResponse.setError(false);
        } else {
            plantationTypeResponse.setError(true);
            plantationTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return plantationTypeResponse;
    }

    @Transactional
    public PlantationTypeResponse getById(int id){
        PlantationTypeResponse plantationTypeResponse = new PlantationTypeResponse();
        PlantationType plantationType = plantationTypeRepository.findByPlantationTypeIdAndActive(id,true);
        if(plantationType == null){
            plantationTypeResponse.setError(true);
            plantationTypeResponse.setError_description("Invalid id");
        }else{
            plantationTypeResponse =  mapper.plantationTypeEntityToObject(plantationType,PlantationTypeResponse.class);
            plantationTypeResponse.setError(false);
        }
        log.info("Entity is ",plantationType);
        return plantationTypeResponse;
    }

    @Transactional
    public PlantationTypeResponse updatePlantationTypeDetails(EditPlantationTypeRequest plantationTypeRequest) {
        PlantationTypeResponse plantationTypeResponse = new PlantationTypeResponse();
        List<PlantationType> plantationTypeList = plantationTypeRepository.findByActiveAndPlantationTypeNameAndPlantationTypeNameInKannada(true,plantationTypeRequest.getPlantationTypeName(), plantationTypeRequest.getPlantationTypeNameInKannada());
        if (plantationTypeList.size() > 0) {
            plantationTypeResponse.setError(true);
            plantationTypeResponse.setError_description("PlantationType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            PlantationType plantationType = plantationTypeRepository.findByPlantationTypeIdAndActiveIn(plantationTypeRequest.getPlantationTypeId(), Set.of(true, false));
            if (Objects.nonNull(plantationType)) {
                plantationType.setPlantationTypeName(plantationTypeRequest.getPlantationTypeName());
                plantationType.setPlantationTypeNameInKannada(plantationTypeRequest.getPlantationTypeNameInKannada());
                plantationType.setActive(true);
                PlantationType plantationType1 = plantationTypeRepository.save(plantationType);
                plantationTypeResponse = mapper.plantationTypeEntityToObject(plantationType1, PlantationTypeResponse.class);
                plantationTypeResponse.setError(false);
            } else {
                plantationTypeResponse.setError(true);
                plantationTypeResponse.setError_description("Error occurred while fetching plantationType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return plantationTypeResponse;
    }
}
