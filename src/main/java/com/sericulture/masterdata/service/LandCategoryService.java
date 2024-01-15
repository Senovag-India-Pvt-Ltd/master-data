package com.sericulture.masterdata.service;


import com.sericulture.masterdata.model.api.landCategory.EditLandCategoryRequest;
import com.sericulture.masterdata.model.api.landCategory.LandCategoryRequest;
import com.sericulture.masterdata.model.api.landCategory.LandCategoryResponse;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.LandCategory;
import com.sericulture.masterdata.model.entity.LandOwnership;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.LandCategoryRepository;
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
public class LandCategoryService {
    @Autowired
    LandCategoryRepository landCategoryRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LandCategoryResponse getLandCategoryDetails(String landCategoryName){
        LandCategoryResponse landCategoryResponse = new LandCategoryResponse();
        LandCategory landCategory = null;
        if(landCategory==null){
            landCategory = landCategoryRepository.findByLandCategoryNameAndActive(landCategoryName,true);
            landCategoryResponse = mapper.landCategoryEntityToObject(landCategory, LandCategoryResponse.class);
            landCategoryResponse.setError(false);
        }else{
            landCategoryResponse.setError(true);
            landCategoryResponse.setError_description("LandCategory not found");
        }
        log.info("Entity is ",landCategory);
        return landCategoryResponse;
    }

    @Transactional
    public LandCategoryResponse insertLandCategoryDetails(LandCategoryRequest landCategoryRequest){
        LandCategoryResponse landCategoryResponse = new LandCategoryResponse();
        LandCategory landCategory = mapper.landCategoryObjectToEntity(landCategoryRequest,LandCategory.class);
        validator.validate(landCategory);
        List<LandCategory> landCategoryList = landCategoryRepository.findByLandCategoryName(landCategoryRequest.getLandCategoryName());
        if(!landCategoryList.isEmpty() && landCategoryList.stream().filter(LandCategory::getActive).findAny().isPresent()){
            landCategoryResponse.setError(true);
            landCategoryResponse.setError_description("landCategory name already exist");
        }
        else if(!landCategoryList.isEmpty() && landCategoryList.stream().filter(Predicate.not(LandCategory::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            landCategoryResponse.setError(true);
            landCategoryResponse.setError_description("landCategory name already exist with inactive state");
        }else {
            landCategoryResponse = mapper.landCategoryEntityToObject(landCategoryRepository.save(landCategory), LandCategoryResponse.class);
            landCategoryResponse.setError(false);
        }
        return landCategoryResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedLandCategoryDetails(final Pageable pageable){
        return convertToMapResponse(landCategoryRepository.findByActiveOrderByLandCategoryNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(landCategoryRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<LandCategory> activeLandCategorys) {
        Map<String, Object> response = new HashMap<>();

        List<LandCategoryResponse> landCategoryResponses = activeLandCategorys.getContent().stream()
                .map(state -> mapper.landCategoryEntityToObject(state,LandCategoryResponse.class)).collect(Collectors.toList());
        response.put("landCategory",landCategoryResponses);
        response.put("currentPage", activeLandCategorys.getNumber());
        response.put("totalItems", activeLandCategorys.getTotalElements());
        response.put("totalPages", activeLandCategorys.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<LandCategory> activeLandCategorys) {
        Map<String, Object> response = new HashMap<>();

        List<LandCategoryResponse> landCategoryResponses = activeLandCategorys.stream()
                .map(landCategory -> mapper.landCategoryEntityToObject(landCategory,LandCategoryResponse.class)).collect(Collectors.toList());
        response.put("landCategory",landCategoryResponses);
        return response;
    }

    @Transactional
    public LandCategoryResponse deleteLandCategoryDetails(long id) {
        LandCategoryResponse landCategoryResponse = new LandCategoryResponse();
        LandCategory landCategory = landCategoryRepository.findByIdAndActive(id, true);
        if (Objects.nonNull(landCategory)) {
            landCategory.setActive(false);
            landCategoryResponse = mapper.landCategoryEntityToObject(landCategoryRepository.save(landCategory), LandCategoryResponse.class);
            landCategoryResponse.setError(false);
        } else {
            landCategoryResponse.setError(true);
            landCategoryResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return landCategoryResponse;
    }

    @Transactional
    public LandCategoryResponse getById(int id){
        LandCategoryResponse landCategoryResponse = new LandCategoryResponse();
        LandCategory landCategory = landCategoryRepository.findByIdAndActive(id,true);
        if(landCategory == null){
            landCategoryResponse.setError(true);
            landCategoryResponse.setError_description("Invalid id");
        }else{
            landCategoryResponse =  mapper.landCategoryEntityToObject(landCategory,LandCategoryResponse.class);
            landCategoryResponse.setError(false);
        }
        log.info("Entity is ",landCategory);
        return landCategoryResponse;
    }

    @Transactional
    public LandCategoryResponse updateLandCategoryDetails(EditLandCategoryRequest landCategoryRequest) {
        LandCategoryResponse landCategoryResponse = new LandCategoryResponse();
        List<LandCategory> landCategoryList = landCategoryRepository.findByLandCategoryName(landCategoryRequest.getLandCategoryName());
        if (landCategoryList.size() > 0) {
            landCategoryResponse.setError(true);
            landCategoryResponse.setError_description("LandCategory already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            LandCategory landCategory = landCategoryRepository.findByIdAndActiveIn(landCategoryRequest.getId(), Set.of(true, false));
            if (Objects.nonNull(landCategory)) {
                landCategory.setLandCategoryName(landCategoryRequest.getLandCategoryName());
                landCategory.setActive(true);
                LandCategory landCategory1 = landCategoryRepository.save(landCategory);
                landCategoryResponse = mapper.landCategoryEntityToObject(landCategory1, LandCategoryResponse.class);
                landCategoryResponse.setError(false);
            } else{
                landCategoryResponse.setError(true);
                landCategoryResponse.setError_description("Error occurred while fetching landCategory");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return landCategoryResponse;
    }
}
