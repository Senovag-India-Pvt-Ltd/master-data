package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scSubSchemeMapping.EditScSubSchemeMappingRequest;
import com.sericulture.masterdata.model.api.scSubSchemeMapping.ScSubSchemeMappingRequest;
import com.sericulture.masterdata.model.api.scSubSchemeMapping.ScSubSchemeMappingResponse;
import com.sericulture.masterdata.model.dto.ScSubSchemeMappingDTO;
import com.sericulture.masterdata.model.entity.ScSubSchemeMapping;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScSubSchemeMappingRepository;
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
public class ScSubSchemeMappingService {
    @Autowired
    ScSubSchemeMappingRepository scSubSchemeMappingRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScSubSchemeMappingResponse insertScSubSchemeMappingDetails(ScSubSchemeMappingRequest scSubSchemeMappingRequest){
        ScSubSchemeMappingResponse scSubSchemeMappingResponse = new ScSubSchemeMappingResponse();
        ScSubSchemeMapping scSubSchemeMapping = mapper.scSubSchemeMappingObjectToEntity(scSubSchemeMappingRequest, ScSubSchemeMapping.class);
        validator.validate(scSubSchemeMapping);
        List<ScSubSchemeMapping> scSubSchemeMappingList = scSubSchemeMappingRepository.findByScSchemeDetailsIdAndScSubSchemeDetailsId(scSubSchemeMappingRequest.getScSchemeDetailsId(),scSubSchemeMappingRequest.getScSubSchemeDetailsId());
        if(!scSubSchemeMappingList.isEmpty() && scSubSchemeMappingList.stream().filter(ScSubSchemeMapping::getActive).findAny().isPresent()){
            scSubSchemeMappingResponse.setError(true);
            scSubSchemeMappingResponse.setError_description("ScSubSchemeMapping already exist");
        }
        else if(!scSubSchemeMappingList.isEmpty() && scSubSchemeMappingList.stream().filter(Predicate.not(ScSubSchemeMapping::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scSubSchemeMappingResponse.setError(true);
            scSubSchemeMappingResponse.setError_description("ScSubSchemeMapping already exist with inactive state");
        }else {
            scSubSchemeMappingResponse = mapper.scSubSchemeMappingEntityToObject(scSubSchemeMappingRepository.save(scSubSchemeMapping), ScSubSchemeMappingResponse.class);
            scSubSchemeMappingResponse.setError(false);
        }
        return scSubSchemeMappingResponse;
    }


    public Map<String,Object> getScSubSchemeMappingDetails(final Pageable pageable){
        return convertToMapResponse(scSubSchemeMappingRepository.findByActiveOrderByScSubSchemeMappingIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scSubSchemeMappingRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScSubSchemeMapping> activeScSubSchemeMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScSubSchemeMappingResponse> scSubSchemeMappingResponses= activeScSubSchemeMapping.getContent().stream()
                .map(scSubSchemeMapping -> mapper.scSubSchemeMappingEntityToObject(scSubSchemeMapping, ScSubSchemeMappingResponse.class)).collect(Collectors.toList());
        response.put("ScSubSchemeMapping",scSubSchemeMappingResponses);
        response.put("currentPage", activeScSubSchemeMapping.getNumber());
        response.put("totalItems", activeScSubSchemeMapping.getTotalElements());
        response.put("totalPages", activeScSubSchemeMapping.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScSubSchemeMapping> activeScSubSchemeMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScSubSchemeMappingResponse> scSubSchemeMappingResponses = activeScSubSchemeMapping.stream()
                .map(scSubSchemeMapping  -> mapper.scSubSchemeMappingEntityToObject(scSubSchemeMapping, ScSubSchemeMappingResponse.class)).collect(Collectors.toList());
        response.put("scSubSchemeMapping",scSubSchemeMappingResponses);
        return response;
    }

    public Map<String,Object> getPaginatedScSubSchemeMappingWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scSubSchemeMappingRepository.getByActiveOrderByScSubSchemeMappingIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScSubSchemeMappingDTO> activeScSubSchemeMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScSubSchemeMappingResponse> scSubSchemeMappingResponses= activeScSubSchemeMapping.getContent().stream()
                .map(scSubSchemeMapping -> mapper.scSubSchemeMappingDTOToObject(scSubSchemeMapping,ScSubSchemeMappingResponse.class)).collect(Collectors.toList());
        response.put("ScSubSchemeMapping",scSubSchemeMappingResponses);
        response.put("currentPage", activeScSubSchemeMapping.getNumber());
        response.put("totalItems", activeScSubSchemeMapping.getTotalElements());
        response.put("totalPages", activeScSubSchemeMapping.getTotalPages());
        return response;
    }


    @Transactional
    public ScSubSchemeMappingResponse deleteScSubSchemeMappingDetails(long id) {
        ScSubSchemeMappingResponse scSubSchemeMappingResponse= new ScSubSchemeMappingResponse();
        ScSubSchemeMapping scSubSchemeMapping = scSubSchemeMappingRepository.findByScSubSchemeMappingIdAndActive(id, true);
        if (Objects.nonNull(scSubSchemeMapping)) {
            scSubSchemeMapping.setActive(false);
            scSubSchemeMappingResponse= mapper.scSubSchemeMappingEntityToObject(scSubSchemeMappingRepository.save(scSubSchemeMapping),ScSubSchemeMappingResponse.class);
            scSubSchemeMappingResponse.setError(false);
        } else {
            scSubSchemeMappingResponse.setError(true);
            scSubSchemeMappingResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scSubSchemeMappingResponse;
    }

    public ScSubSchemeMappingResponse getById(int id){
        ScSubSchemeMappingResponse scSubSchemeMappingResponse = new ScSubSchemeMappingResponse();
        ScSubSchemeMapping scSubSchemeMapping = scSubSchemeMappingRepository.findByScSubSchemeMappingIdAndActive(id,true);


        if(scSubSchemeMapping == null){
            scSubSchemeMappingResponse.setError(true);
            scSubSchemeMappingResponse.setError_description("Invalid id");
        }else{
            scSubSchemeMappingResponse =  mapper.scSubSchemeMappingEntityToObject(scSubSchemeMapping, ScSubSchemeMappingResponse.class);
            scSubSchemeMappingResponse.setError(false);
        }
        log.info("Entity is ",scSubSchemeMapping);
        return scSubSchemeMappingResponse;
    }


    public ScSubSchemeMappingResponse getByIdJoin(int id){
        ScSubSchemeMappingResponse scSubSchemeMappingResponse = new ScSubSchemeMappingResponse();
        ScSubSchemeMappingDTO scSubSchemeMappingDTO = scSubSchemeMappingRepository.getByScSubSchemeMappingIdAndActive(id,true);
        if(scSubSchemeMappingDTO == null){
            scSubSchemeMappingResponse.setError(true);
            scSubSchemeMappingResponse.setError_description("Invalid id");
        } else {
            scSubSchemeMappingResponse = mapper.scSubSchemeMappingDTOToObject(scSubSchemeMappingDTO, ScSubSchemeMappingResponse.class);
            scSubSchemeMappingResponse.setError(false);
        }
        log.info("Entity is ", scSubSchemeMappingDTO);
        return scSubSchemeMappingResponse;
    }

    public Map<String, Object> getScSchemeDetailsIdAndScSubSchemeDetailsId(Long scSchemeDetailsId,Long scSubSchemeDetailsId) {
        Map<String, Object> response = new HashMap<>();
        List<ScSubSchemeMapping> scSubSchemeMappingList = scSubSchemeMappingRepository.findByScSchemeDetailsIdAndScSubSchemeDetailsIdAndActive(scSchemeDetailsId,scSubSchemeDetailsId,true);
        if (scSubSchemeMappingList.isEmpty()) {
//            throw new ValidationException("Invalid Id");
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            response.put("success", false);
            return response;
        } else {
            log.info("Entity is ", scSubSchemeMappingList);
            response = convertListToMapResponse(scSubSchemeMappingList);
            response.put("success", true);
            return response;

        }

    }

    private Map<String, Object> convertListToMapResponse(List<ScSubSchemeMapping> scSubSchemeMappingList) {
        Map<String, Object> response = new HashMap<>();
        List<ScSubSchemeMappingResponse> scSubSchemeMappingResponses = scSubSchemeMappingList.stream()
                .map(scSubSchemeMapping -> mapper.scSubSchemeMappingEntityToObject(scSubSchemeMapping, ScSubSchemeMappingResponse.class)).collect(Collectors.toList());
        response.put("scSubSchemeMapping", scSubSchemeMappingResponses);
        response.put("totalItems", scSubSchemeMappingList.size());
        return response;
    }

    @Transactional
    public ScSubSchemeMappingResponse updateScSubSchemeMappingDetails(EditScSubSchemeMappingRequest scSubSchemeMappingRequest) {
        ScSubSchemeMappingResponse scSubSchemeMappingResponse = new ScSubSchemeMappingResponse();
        List<ScSubSchemeMapping> scSubSchemeMappingList = scSubSchemeMappingRepository.findByScSchemeDetailsIdAndScSubSchemeDetailsIdAndScSubSchemeMappingIdIsNot(scSubSchemeMappingRequest.getScSchemeDetailsId(),scSubSchemeMappingRequest.getScSubSchemeDetailsId(), scSubSchemeMappingRequest.getScSubSchemeMappingId());
        if (scSubSchemeMappingList.size() > 0) {
            scSubSchemeMappingResponse.setError(true);
            scSubSchemeMappingResponse.setError_description("ScSubSchemeMapping exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScSubSchemeMapping scSubSchemeMapping = scSubSchemeMappingRepository.findByScSubSchemeMappingIdAndActiveIn(scSubSchemeMappingRequest.getScSubSchemeMappingId(), Set.of(true, false));
            if (Objects.nonNull(scSubSchemeMapping)) {
                scSubSchemeMapping.setScSchemeDetailsId(scSubSchemeMappingRequest.getScSchemeDetailsId());
                scSubSchemeMapping.setScSubSchemeDetailsId(scSubSchemeMappingRequest.getScSubSchemeDetailsId());

                scSubSchemeMapping.setActive(true);
                ScSubSchemeMapping scSubSchemeMapping1 = scSubSchemeMappingRepository.save(scSubSchemeMapping);
                scSubSchemeMappingResponse = mapper.scSubSchemeMappingEntityToObject(scSubSchemeMapping1, ScSubSchemeMappingResponse.class);
                scSubSchemeMappingResponse.setError(false);
            } else {
                scSubSchemeMappingResponse.setError(true);
                scSubSchemeMappingResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scSubSchemeMappingResponse;
    }


    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("scSchemeDetails.schemeName");
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
        Page<ScSubSchemeMappingDTO> ScSubSchemeMappingDTOS = scSubSchemeMappingRepository.getSortedScSubSchemeMapping(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",ScSubSchemeMappingDTOS);
        return convertPageableDTOToMapResponse(ScSubSchemeMappingDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScSubSchemeMappingDTO> activeScSubSchemeMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScSubSchemeMappingResponse> scSubSchemeMappingResponses = activeScSubSchemeMapping.getContent().stream()
                .map(scSubSchemeMapping -> mapper.scSubSchemeMappingDTOToObject(scSubSchemeMapping,ScSubSchemeMappingResponse.class)).collect(Collectors.toList());
        response.put("ScSubSchemeMapping",scSubSchemeMappingResponses);
        response.put("currentPage", activeScSubSchemeMapping.getNumber());
        response.put("totalItems", activeScSubSchemeMapping.getTotalElements());
        response.put("totalPages", activeScSubSchemeMapping.getTotalPages());

        return response;
    }

}
