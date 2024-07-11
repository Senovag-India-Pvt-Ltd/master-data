package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scApprovingAuthority.ScApprovingAuthorityResponse;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.EditScHeadAccountCategoryRequest;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.ScHeadAccountCategoryRequest;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.ScHeadAccountCategoryResponse;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.ScHeadAccountCategoryResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.dto.ScApprovingAuthorityDTO;
import com.sericulture.masterdata.model.dto.ScHeadAccountCategoryDTO;
import com.sericulture.masterdata.model.entity.ScHeadAccountCategory;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScHeadAccountCategoryRepository;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScHeadAccountCategoryService {
    @Autowired
    ScHeadAccountCategoryRepository scHeadAccountCategoryRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScHeadAccountCategoryResponse insertScHeadAccountCategoryDetails(ScHeadAccountCategoryRequest scHeadAccountCategoryRequest){
        ScHeadAccountCategoryResponse scHeadAccountCategoryResponse = new ScHeadAccountCategoryResponse();
        ScHeadAccountCategory scHeadAccountCategory = mapper.scHeadAccountCategoryObjectToEntity(scHeadAccountCategoryRequest, ScHeadAccountCategory.class);
        validator.validate(scHeadAccountCategory);
        List<ScHeadAccountCategory> scHeadAccountCategoryList = scHeadAccountCategoryRepository.findByScHeadAccountIdAndScCategoryId(scHeadAccountCategoryRequest.getScHeadAccountId(),scHeadAccountCategoryRequest.getScCategoryId());
        if(!scHeadAccountCategoryList.isEmpty() && scHeadAccountCategoryList.stream().filter(ScHeadAccountCategory::getActive).findAny().isPresent()){
            scHeadAccountCategoryResponse.setError(true);
            scHeadAccountCategoryResponse.setError_description("Head Account  already exist");
        }
        else if(!scHeadAccountCategoryList.isEmpty() && scHeadAccountCategoryList.stream().filter(Predicate.not(ScHeadAccountCategory::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scHeadAccountCategoryResponse.setError(true);
            scHeadAccountCategoryResponse.setError_description("Head Account already exist with inactive state");
        }else {
            scHeadAccountCategoryResponse = mapper.scHeadAccountCategoryEntityToObject(scHeadAccountCategoryRepository.save(scHeadAccountCategory), ScHeadAccountCategoryResponse.class);
            scHeadAccountCategoryResponse.setError(false);
        }
        return scHeadAccountCategoryResponse;
    }

    public Map<String,Object> getScHeadAccountCategoryDetails(final Pageable pageable){
        return convertToMapResponse(scHeadAccountCategoryRepository.findByActiveOrderByScHeadAccountCategoryIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scHeadAccountCategoryRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScHeadAccountCategory> activeScHeadAccountCategory) {
        Map<String, Object> response = new HashMap<>();

        List<ScHeadAccountCategoryResponse> scHeadAccountCategoryResponses= activeScHeadAccountCategory.getContent().stream()
                .map(scHeadAccountCategory -> mapper.scHeadAccountCategoryEntityToObject(scHeadAccountCategory, ScHeadAccountCategoryResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccountCategory",scHeadAccountCategoryResponses);
        response.put("currentPage", activeScHeadAccountCategory.getNumber());
        response.put("totalItems", activeScHeadAccountCategory.getTotalElements());
        response.put("totalPages", activeScHeadAccountCategory.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScHeadAccountCategory> activeScHeadAccountCategory) {
        Map<String, Object> response = new HashMap<>();

        List<ScHeadAccountCategoryResponse> scHeadAccountCategoryResponses = activeScHeadAccountCategory.stream()
                .map(scHeadAccountCategory  -> mapper.scHeadAccountCategoryEntityToObject(scHeadAccountCategory, ScHeadAccountCategoryResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccountCategory",scHeadAccountCategoryResponses);
        return response;
    }

    public Map<String,Object> getPaginatedScHeadAccountCategoryWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scHeadAccountCategoryRepository.getByActiveOrderByScHeadAccountCategoryIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScHeadAccountCategoryDTO> activeScHeadAccountCategory) {
        Map<String, Object> response = new HashMap<>();

        List<ScHeadAccountCategoryResponse> scHeadAccountCategoryResponses= activeScHeadAccountCategory.getContent().stream()
                .map(scHeadAccountCategory -> mapper.scHeadAccountCategoryDTOToObject(scHeadAccountCategory,ScHeadAccountCategoryResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccountCategory",scHeadAccountCategoryResponses);
        response.put("currentPage", activeScHeadAccountCategory.getNumber());
        response.put("totalItems", activeScHeadAccountCategory.getTotalElements());
        response.put("totalPages", activeScHeadAccountCategory.getTotalPages());
        return response;
    }


    @Transactional
    public ScHeadAccountCategoryResponse deleteScHeadAccountCategoryDetails(long id) {
        ScHeadAccountCategoryResponse scHeadAccountCategoryResponse= new ScHeadAccountCategoryResponse();
        ScHeadAccountCategory scHeadAccountCategory = scHeadAccountCategoryRepository.findByScHeadAccountCategoryIdAndActive(id, true);
        if (Objects.nonNull(scHeadAccountCategory)) {
            scHeadAccountCategory.setActive(false);
            scHeadAccountCategoryResponse= mapper.scHeadAccountCategoryEntityToObject(scHeadAccountCategoryRepository.save(scHeadAccountCategory),ScHeadAccountCategoryResponse.class);
            scHeadAccountCategoryResponse.setError(false);
        } else {
            scHeadAccountCategoryResponse.setError(true);
            scHeadAccountCategoryResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scHeadAccountCategoryResponse;
    }

    public ScHeadAccountCategoryResponse getById(int id){
        ScHeadAccountCategoryResponse scHeadAccountCategoryResponse = new ScHeadAccountCategoryResponse();
        ScHeadAccountCategory scHeadAccountCategory = scHeadAccountCategoryRepository.findByScHeadAccountCategoryIdAndActive(id,true);


        if(scHeadAccountCategory == null){
            scHeadAccountCategoryResponse.setError(true);
            scHeadAccountCategoryResponse.setError_description("Invalid id");
        }else{
            scHeadAccountCategoryResponse =  mapper.scHeadAccountCategoryEntityToObject(scHeadAccountCategory, ScHeadAccountCategoryResponse.class);
            scHeadAccountCategoryResponse.setError(false);
        }
        log.info("Entity is ",scHeadAccountCategory);
        return scHeadAccountCategoryResponse;
    }


    public ScHeadAccountCategoryResponse getByIdJoin(int id){
        ScHeadAccountCategoryResponse scHeadAccountCategoryResponse = new ScHeadAccountCategoryResponse();
        ScHeadAccountCategoryDTO scHeadAccountCategoryDTO = scHeadAccountCategoryRepository.getByScHeadAccountCategoryIdAndActive(id,true);
        if(scHeadAccountCategoryDTO == null){
            scHeadAccountCategoryResponse.setError(true);
            scHeadAccountCategoryResponse.setError_description("Invalid id");
        } else {
            scHeadAccountCategoryResponse = mapper.scHeadAccountCategoryDTOToObject(scHeadAccountCategoryDTO, ScHeadAccountCategoryResponse.class);
            scHeadAccountCategoryResponse.setError(false);
        }
        log.info("Entity is ", scHeadAccountCategoryDTO);
        return scHeadAccountCategoryResponse;
    }

    public Map<String, Object> getScHeadAccountCategoryByScHeadAccountId(Long scHeadAccountId) {
        Map<String, Object> response = new HashMap<>();
        List<ScHeadAccountCategoryDTO> scHeadAccountCategoryList = scHeadAccountCategoryRepository.getByScHeadAccountIdAndActive(scHeadAccountId, true);
        if (scHeadAccountCategoryList.isEmpty()) {
//            throw new ValidationException("Invalid Id");
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            response.put("success", false);
            return response;
        } else {
            log.info("Entity is ", scHeadAccountCategoryList);
            response = convertListDTOToMapResponse(scHeadAccountCategoryList);
            response.put("success", true);
            return response;

        }

    }

    private Map<String, Object> convertListToMapResponse(List<ScHeadAccountCategory> scHeadAccountCategoryList) {
        Map<String, Object> response = new HashMap<>();
        List<ScHeadAccountCategoryResponse> scHeadAccountCategoryResponses = scHeadAccountCategoryList.stream()
                .map(scHeadAccountCategory -> mapper.scHeadAccountCategoryEntityToObject(scHeadAccountCategory, ScHeadAccountCategoryResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccountCategory", scHeadAccountCategoryResponses);
        response.put("totalItems", scHeadAccountCategoryList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<ScHeadAccountCategoryDTO> scHeadAccountCategoryList) {
        Map<String, Object> response = new HashMap<>();
        List<ScHeadAccountCategoryResponse> scHeadAccountCategoryResponses = scHeadAccountCategoryList.stream()
                .map(scHeadAccountCategory -> mapper.scHeadAccountCategoryDTOToObject(scHeadAccountCategory,ScHeadAccountCategoryResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccountCategory",scHeadAccountCategoryResponses);
        response.put("totalItems", scHeadAccountCategoryList.size());
        return response;
    }


    @Transactional
    public ScHeadAccountCategoryResponse updateScHeadAccountCategoryDetails(EditScHeadAccountCategoryRequest scHeadAccountCategoryRequest) {
        ScHeadAccountCategoryResponse scHeadAccountCategoryResponse = new ScHeadAccountCategoryResponse();
        List<ScHeadAccountCategory> scHeadAccountCategoryList = scHeadAccountCategoryRepository.findByScHeadAccountIdAndScCategoryIdAndScHeadAccountCategoryIdIsNot(scHeadAccountCategoryRequest.getScHeadAccountId(), scHeadAccountCategoryRequest.getScCategoryId(),scHeadAccountCategoryRequest.getScHeadAccountCategoryId());
        if (scHeadAccountCategoryList.size() > 0) {
            scHeadAccountCategoryResponse.setError(true);
            scHeadAccountCategoryResponse.setError_description("Head Account exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScHeadAccountCategory scHeadAccountCategory = scHeadAccountCategoryRepository.findByScHeadAccountCategoryIdAndActiveIn(scHeadAccountCategoryRequest.getScHeadAccountCategoryId(), Set.of(true, false));
            if (Objects.nonNull(scHeadAccountCategory)) {
                scHeadAccountCategory.setScHeadAccountId(scHeadAccountCategoryRequest.getScHeadAccountId());
                scHeadAccountCategory.setScCategoryId(scHeadAccountCategoryRequest.getScCategoryId());

                scHeadAccountCategory.setActive(true);
                ScHeadAccountCategory scHeadAccountCategory1 = scHeadAccountCategoryRepository.save(scHeadAccountCategory);
                scHeadAccountCategoryResponse = mapper.scHeadAccountCategoryEntityToObject(scHeadAccountCategory1, ScHeadAccountCategoryResponse.class);
                scHeadAccountCategoryResponse.setError(false);
            } else {
                scHeadAccountCategoryResponse.setError(true);
                scHeadAccountCategoryResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scHeadAccountCategoryResponse;
    }

    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("scHeadAccount.scHeadAccountName");
        }
        if(searchWithSortRequest.getSortOrder() == null || searchWithSortRequest.getSortOrder().equals("")){
            searchWithSortRequest.setSortOrder("asc");
        }
        if(searchWithSortRequest.getPageNumber() == null || searchWithSortRequest.getPageNumber().equals("")){
            searchWithSortRequest.setPageNumber("0");
        }
        if(searchWithSortRequest.getPageSize() == null || searchWithSortRequest.getPageSize().equals("")){
            searchWithSortRequest.setPageSize("5");
        }
        Sort sort;
        if(searchWithSortRequest.getSortOrder().equals("asc")){
            sort = Sort.by(Sort.Direction.ASC, searchWithSortRequest.getSortColumn());
        }else{
            sort = Sort.by(Sort.Direction.DESC, searchWithSortRequest.getSortColumn());
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(searchWithSortRequest.getPageNumber()), Integer.parseInt(searchWithSortRequest.getPageSize()), sort);
        Page<ScHeadAccountCategoryDTO> scHeadAccountCategoryDTOS = scHeadAccountCategoryRepository.getSortedScHeadAccountCategory(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",scHeadAccountCategoryDTOS);
        return convertPageableDTOToMapResponse(scHeadAccountCategoryDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScHeadAccountCategoryDTO> activeScHeadAccountCategory) {
        Map<String, Object> response = new HashMap<>();

        List<ScHeadAccountCategoryResponse> scHeadAccountCategoryResponses = activeScHeadAccountCategory.getContent().stream()
                .map(scHeadAccountCategory -> mapper.scHeadAccountCategoryDTOToObject(scHeadAccountCategory,ScHeadAccountCategoryResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccountCategory",scHeadAccountCategoryResponses);
        response.put("currentPage", activeScHeadAccountCategory.getNumber());
        response.put("totalItems", activeScHeadAccountCategory.getTotalElements());
        response.put("totalPages", activeScHeadAccountCategory.getTotalPages());

        return response;
    }
}
