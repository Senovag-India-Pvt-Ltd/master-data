package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.designation.DesignationResponse;
import com.sericulture.masterdata.model.api.scCategory.EditScCategoryRequest;
import com.sericulture.masterdata.model.api.scCategory.ScCategoryRequest;
import com.sericulture.masterdata.model.api.scCategory.ScCategoryResponse;
import com.sericulture.masterdata.model.api.trProgramMaster.EditTrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterResponse;
import com.sericulture.masterdata.model.entity.Designation;
import com.sericulture.masterdata.model.entity.ScCategory;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScCategoryRepository;
import com.sericulture.masterdata.repository.TrProgramMasterRespository;
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
public class ScCategoryService {

    @Autowired
    ScCategoryRepository scCategoryRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ScCategoryResponse getScCategoryDetails(String categoryName){
        ScCategoryResponse scCategoryResponse = new ScCategoryResponse();
        ScCategory scCategory= scCategoryRepository.findByCategoryNameAndActive(categoryName,true);
        if(scCategory==null){
            scCategoryResponse.setError(true);
            scCategoryResponse.setError_description("Program not found");
        }else{
            scCategoryResponse = mapper.scCategoryEntityToObject(scCategory,ScCategoryResponse.class);
            scCategoryResponse.setError(false);
        }
        log.info("Entity is ",scCategory);
        return scCategoryResponse;
    }

    @Transactional
    public ScCategoryResponse insertScCategoryDetails(ScCategoryRequest scCategoryRequest){
        ScCategoryResponse scCategoryResponse = new ScCategoryResponse();
        ScCategory scCategory = mapper.scCategoryObjectToEntity(scCategoryRequest,ScCategory.class);
        validator.validate(scCategory);
        List<ScCategory> scCategoryList = scCategoryRepository.findByCategoryNameAndCategoryNameInKannada(scCategoryRequest.getCategoryName(), scCategoryRequest.getCategoryNameInKannada());
        if(!scCategoryList.isEmpty() && scCategoryList.stream().filter( ScCategory::getActive).findAny().isPresent()){
            scCategoryResponse.setError(true);
            scCategoryResponse.setError_description("ScCategory name already exist");
        }
        else if(! scCategoryList.isEmpty() && scCategoryList.stream().filter(Predicate.not( ScCategory::getActive)).findAny().isPresent()){
            scCategoryResponse.setError(true);
            scCategoryResponse.setError_description("ScCategory name already exist with inactive state");
        }else {
            scCategoryResponse  = mapper.scCategoryEntityToObject( scCategoryRepository.save(scCategory), ScCategoryResponse.class);
            scCategoryResponse.setError(false);
        }
        return scCategoryResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScCategoryDetails(final Pageable pageable){
        return convertToMapResponse(scCategoryRepository.findByActiveOrderByCategoryNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scCategoryRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScCategory> activeScCategorys) {
        Map<String, Object> response = new HashMap<>();

        List<ScCategoryResponse> scCategoryResponses= activeScCategorys.getContent().stream()
                .map(scCategory -> mapper.scCategoryEntityToObject(scCategory,ScCategoryResponse.class)).collect(Collectors.toList());
        response.put("scCategory",scCategoryResponses);
        response.put("currentPage", activeScCategorys.getNumber());
        response.put("totalItems", activeScCategorys.getTotalElements());
        response.put("totalPages", activeScCategorys.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScCategory> activeScCategorys) {
        Map<String, Object> response = new HashMap<>();

        List<ScCategoryResponse> scCategoryResponses= activeScCategorys.stream()
                .map(scCategory -> mapper.scCategoryEntityToObject(scCategory,ScCategoryResponse.class)).collect(Collectors.toList());
        response.put("scCategory",scCategoryResponses);
        return response;
    }

    @Transactional
    public ScCategoryResponse deleteScCategoryDetails(long id) {

        ScCategoryResponse scCategoryResponse = new ScCategoryResponse();
        ScCategory scCategory= scCategoryRepository.findByScCategoryIdAndActive(id, true);
        if (Objects.nonNull(scCategory)) {
            scCategory.setActive(false);
            scCategoryResponse= mapper.scCategoryEntityToObject(scCategoryRepository.save(scCategory), ScCategoryResponse.class);
            scCategoryResponse.setError(false);
        } else {
            scCategoryResponse.setError(true);
            scCategoryResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scCategoryResponse;
    }

    @Transactional
    public ScCategoryResponse getById(int id){
        ScCategoryResponse scCategoryResponse = new ScCategoryResponse();
        ScCategory scCategory= scCategoryRepository.findByScCategoryIdAndActive(id, true);
        if(scCategory== null){
            scCategoryResponse.setError(true);
            scCategoryResponse.setError_description("Invalid id");
        }else{
            scCategoryResponse =  mapper.scCategoryEntityToObject(scCategory, ScCategoryResponse.class);
            scCategoryResponse.setError(false);
        }
        log.info("Entity is ",scCategory);
        return scCategoryResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getScCategoryByScHeadAccountId(Long scHeadAccountId){
//        List<ScCategory> scCategoryList = scCategoryRepository.findByScHeadAccountIdAndActiveOrderByCategoryName(scHeadAccountId,true);
//        if(scCategoryList.isEmpty()){
//            throw new ValidationException("Invalid Id");
//        }
//        log.info("Entity is ",scCategoryList);
//        return convertListToMapResponse(scCategoryList);
//    }
//
//    private Map<String, Object> convertListToMapResponse(List<ScCategory> scCategoryList) {
//        Map<String, Object> response = new HashMap<>();
//        List<ScCategoryResponse> scCategoryResponses = scCategoryList.stream()
//                .map(scCategory -> mapper.scCategoryEntityToObject(scCategory,ScCategoryResponse.class)).collect(Collectors.toList());
//        response.put("scCategory",scCategoryResponses);
//        response.put("totalItems", scCategoryList.size());
//        return response;
//    }

    @Transactional
    public ScCategoryResponse updateScCategoryDetails(EditScCategoryRequest scCategoryRequest){

        ScCategoryResponse scCategoryResponse = new ScCategoryResponse();
        List<ScCategory> scCategoryList = scCategoryRepository. findByCategoryNameAndCategoryNameInKannadaAndScCategoryIdIsNot(scCategoryRequest.getCategoryName(), scCategoryRequest.getCategoryNameInKannada(),scCategoryRequest.getScCategoryId());
        if(scCategoryList.size()>0){
            scCategoryResponse.setError(true);
            scCategoryResponse.setError_description("scCategory exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            ScCategory scCategory = scCategoryRepository.findByScCategoryIdAndActiveIn(scCategoryRequest.getScCategoryId(), Set.of(true,false));
            if(Objects.nonNull(scCategory)){
                scCategory.setCategoryName( scCategoryRequest.getCategoryName());
                scCategory.setCategoryNameInKannada( scCategoryRequest.getCategoryNameInKannada());
                scCategory.setCodeNumber( scCategoryRequest.getCodeNumber());
                scCategory.setDescription( scCategoryRequest.getDescription());


                scCategory.setActive(true);
                ScCategory scCategory1= scCategoryRepository.save(scCategory);
                scCategoryResponse = mapper.scCategoryEntityToObject(scCategory1, ScCategoryResponse.class);
                scCategoryResponse.setError(false);
            } else {
                scCategoryResponse.setError(true);
                scCategoryResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return scCategoryResponse;
    }


}
