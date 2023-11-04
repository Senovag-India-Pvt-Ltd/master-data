package com.sericulture.masterdata.service;


import com.sericulture.masterdata.model.api.landCategory.EditLandCategoryRequest;
import com.sericulture.masterdata.model.api.landCategory.LandCategoryRequest;
import com.sericulture.masterdata.model.api.landCategory.LandCategoryResponse;
import com.sericulture.masterdata.model.entity.LandCategory;
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
        LandCategory landCategory = null;
        if(landCategory==null){
            landCategory = landCategoryRepository.findByLandCategoryNameAndActive(landCategoryName,true);
        }
        log.info("Entity is ",landCategory);
        return mapper.landCategoryEntityToObject(landCategory,LandCategoryResponse.class);
    }

    @Transactional
    public LandCategoryResponse insertLandCategoryDetails(LandCategoryRequest landCategoryRequest){
        LandCategory landCategory = mapper.landCategoryObjectToEntity(landCategoryRequest,LandCategory.class);
        validator.validate(landCategory);
        List<LandCategory> landCategoryList = landCategoryRepository.findByLandCategoryName(landCategoryRequest.getLandCategoryName());
        if(!landCategoryList.isEmpty() && landCategoryList.stream().filter(LandCategory::getActive).findAny().isPresent()){
            throw new ValidationException("Land Category name already exist");
        }
        if(!landCategoryList.isEmpty() && landCategoryList.stream().filter(Predicate.not(LandCategory::getActive)).findAny().isPresent()){
            throw new ValidationException("Land Category name already exist with inactive state");
        }
        return mapper.landCategoryEntityToObject(landCategoryRepository.save(landCategory),LandCategoryResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedLandCategoryDetails(final Pageable pageable){
        return convertToMapResponse(landCategoryRepository.findByActiveOrderByIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<LandCategory> activeStates) {
        Map<String, Object> response = new HashMap<>();

        List<LandCategoryResponse> landCategoryResponses = activeStates.getContent().stream()
                .map(state -> mapper.landCategoryEntityToObject(state,LandCategoryResponse.class)).collect(Collectors.toList());
        response.put("landCategory",landCategoryResponses);
        response.put("currentPage", activeStates.getNumber());
        response.put("totalItems", activeStates.getTotalElements());
        response.put("totalPages", activeStates.getTotalPages());

        return response;
    }

    @Transactional
    public void deleteLandCategoryDetails(long id) {
        LandCategory landCategory = landCategoryRepository.findByIdAndActive(id, true);
        if (Objects.nonNull(landCategory)) {
            landCategory.setActive(false);
            landCategoryRepository.save(landCategory);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public LandCategoryResponse getById(int id){
        LandCategory landCategory = landCategoryRepository.findByIdAndActive(id,true);
        if(landCategory == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",landCategory);
        return mapper.landCategoryEntityToObject(landCategory,LandCategoryResponse.class);
    }

    @Transactional
    public LandCategoryResponse updateLandCategoryDetails(EditLandCategoryRequest landCategoryRequest){
        List<LandCategory> landCategoryList = landCategoryRepository.findByLandCategoryName(landCategoryRequest.getLandCategoryName());
        if(landCategoryList.size()>0){
            throw new ValidationException("land Category already exists with this name, duplicates are not allowed.");
        }

        LandCategory landCategory = landCategoryRepository.findByIdAndActiveIn(landCategoryRequest.getId(), Set.of(true,false));
        if(Objects.nonNull(landCategory)){
            landCategory.setLandCategoryName(landCategoryRequest.getLandCategoryName());
            landCategory.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching state");
        }
        return mapper.landCategoryEntityToObject(landCategoryRepository.save(landCategory),LandCategoryResponse.class);
    }

}
