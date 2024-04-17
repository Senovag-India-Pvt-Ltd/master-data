package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scSubSchemeDetails.EditScSubSchemeDetailsRequest;
import com.sericulture.masterdata.model.api.scSubSchemeDetails.ScSubSchemeDetailsRequest;
import com.sericulture.masterdata.model.api.scSubSchemeDetails.ScSubSchemeDetailsResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.dto.ScSubSchemeDetailsDTO;
import com.sericulture.masterdata.model.entity.ScSubSchemeDetails;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScSubSchemeDetailsRepository;
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
public class ScSubSchemeDetailsService {
    @Autowired
    ScSubSchemeDetailsRepository scSubSchemeDetailsRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScSubSchemeDetailsResponse insertScSubSchemeDetailsDetails(ScSubSchemeDetailsRequest scSubSchemeDetailsRequest){
        ScSubSchemeDetailsResponse scSubSchemeDetailsResponse = new ScSubSchemeDetailsResponse();
        ScSubSchemeDetails scSubSchemeDetails = mapper.scSubSchemeDetailsObjectToEntity(scSubSchemeDetailsRequest, ScSubSchemeDetails.class);
        validator.validate(scSubSchemeDetails);
        List<ScSubSchemeDetails> scSubSchemeDetailsList = scSubSchemeDetailsRepository.findByScSchemeDetailsIdAndSubSchemeName(scSubSchemeDetailsRequest.getScSchemeDetailsId(), scSubSchemeDetailsRequest.getSubSchemeName());
        if(!scSubSchemeDetailsList.isEmpty() && scSubSchemeDetailsList.stream().filter(ScSubSchemeDetails::getActive).findAny().isPresent()){
            scSubSchemeDetailsResponse.setError(true);
            scSubSchemeDetailsResponse.setError_description("ScSubSchemeDetails already exist");
        }
        else if(!scSubSchemeDetailsList.isEmpty() && scSubSchemeDetailsList.stream().filter(Predicate.not(ScSubSchemeDetails::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scSubSchemeDetailsResponse.setError(true);
            scSubSchemeDetailsResponse.setError_description("ScSubSchemeDetails already exist with inactive state");
        }else {
            scSubSchemeDetailsResponse = mapper.scSubSchemeDetailsEntityToObject(scSubSchemeDetailsRepository.save(scSubSchemeDetails), ScSubSchemeDetailsResponse.class);
            scSubSchemeDetailsResponse.setError(false);
        }
        return scSubSchemeDetailsResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getScSubSchemeDetails(final Pageable pageable){
        return convertToMapResponse(scSubSchemeDetailsRepository.findByActiveOrderByScSubSchemeDetailsIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scSubSchemeDetailsRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScSubSchemeDetails> activeScSubSchemeDetails) {
        Map<String, Object> response = new HashMap<>();

        List<ScSubSchemeDetailsResponse> scSubSchemeDetailsResponses= activeScSubSchemeDetails.getContent().stream()
                .map(scSubSchemeDetails -> mapper.scSubSchemeDetailsEntityToObject(scSubSchemeDetails, ScSubSchemeDetailsResponse.class)).collect(Collectors.toList());
        response.put("ScSubSchemeDetails",scSubSchemeDetailsResponses);
        response.put("currentPage", activeScSubSchemeDetails.getNumber());
        response.put("totalItems", activeScSubSchemeDetails.getTotalElements());
        response.put("totalPages", activeScSubSchemeDetails.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScSubSchemeDetails> activeScSubSchemeDetails) {
        Map<String, Object> response = new HashMap<>();

        List<ScSubSchemeDetailsResponse> scSubSchemeDetailsResponses = activeScSubSchemeDetails.stream()
                .map(scSubSchemeDetails  -> mapper.scSubSchemeDetailsEntityToObject(scSubSchemeDetails, ScSubSchemeDetailsResponse.class)).collect(Collectors.toList());
        response.put("scSubSchemeDetails",scSubSchemeDetailsResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScSubSchemeDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scSubSchemeDetailsRepository.getByActiveOrderByScSubSchemeDetailsIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScSubSchemeDetailsDTO> activeScSubSchemeDetails) {
        Map<String, Object> response = new HashMap<>();

        List<ScSubSchemeDetailsResponse> scSubSchemeDetailsResponses= activeScSubSchemeDetails.getContent().stream()
                .map(scSubSchemeDetails -> mapper.scSubSchemeDetailsDTOToObject(scSubSchemeDetails,ScSubSchemeDetailsResponse.class)).collect(Collectors.toList());
        response.put("ScSubSchemeDetails",scSubSchemeDetailsResponses);
        response.put("currentPage", activeScSubSchemeDetails.getNumber());
        response.put("totalItems", activeScSubSchemeDetails.getTotalElements());
        response.put("totalPages", activeScSubSchemeDetails.getTotalPages());
        return response;
    }


    @Transactional
    public ScSubSchemeDetailsResponse deleteScSubSchemeDetailsDetails(long id) {
        ScSubSchemeDetailsResponse scSubSchemeDetailsResponse= new ScSubSchemeDetailsResponse();
        ScSubSchemeDetails scSubSchemeDetails = scSubSchemeDetailsRepository.findByScSubSchemeDetailsIdAndActive(id, true);
        if (Objects.nonNull(scSubSchemeDetails)) {
            scSubSchemeDetails.setActive(false);
            scSubSchemeDetailsResponse= mapper.scSubSchemeDetailsEntityToObject(scSubSchemeDetailsRepository.save(scSubSchemeDetails),ScSubSchemeDetailsResponse.class);
            scSubSchemeDetailsResponse.setError(false);
        } else {
            scSubSchemeDetailsResponse.setError(true);
            scSubSchemeDetailsResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scSubSchemeDetailsResponse;
    }

    @Transactional
    public ScSubSchemeDetailsResponse getById(int id){
        ScSubSchemeDetailsResponse scSubSchemeDetailsResponse = new ScSubSchemeDetailsResponse();
        ScSubSchemeDetails scSubSchemeDetails = scSubSchemeDetailsRepository.findByScSubSchemeDetailsIdAndActive(id,true);


        if(scSubSchemeDetails == null){
            scSubSchemeDetailsResponse.setError(true);
            scSubSchemeDetailsResponse.setError_description("Invalid id");
        }else{
            scSubSchemeDetailsResponse =  mapper.scSubSchemeDetailsEntityToObject(scSubSchemeDetails, ScSubSchemeDetailsResponse.class);
            scSubSchemeDetailsResponse.setError(false);
        }
        log.info("Entity is ",scSubSchemeDetails);
        return scSubSchemeDetailsResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<ScSubSchemeDetailsDTO> scSubSchemeDetailsList = scSubSchemeDetailsRepository.getScSubSchemeDetails(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(scSubSchemeDetailsList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", scSubSchemeDetailsList);
//            response = convertListToMapResponse(scSubSchemeDetailsList);
//            return response;
//        }
//    }



    @Transactional
    public ScSubSchemeDetailsResponse getByIdJoin(int id){
        ScSubSchemeDetailsResponse scSubSchemeDetailsResponse = new ScSubSchemeDetailsResponse();
        ScSubSchemeDetailsDTO scSubSchemeDetailsDTO = scSubSchemeDetailsRepository.getByScSubSchemeDetailsIdAndActive(id,true);
        if(scSubSchemeDetailsDTO == null){
            scSubSchemeDetailsResponse.setError(true);
            scSubSchemeDetailsResponse.setError_description("Invalid id");
        } else {
            scSubSchemeDetailsResponse = mapper.scSubSchemeDetailsDTOToObject(scSubSchemeDetailsDTO, ScSubSchemeDetailsResponse.class);
            scSubSchemeDetailsResponse.setError(false);
        }
        log.info("Entity is ", scSubSchemeDetailsDTO);
        return scSubSchemeDetailsResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getScSubSchemeDetailsByScSchemeDetailsId(Long scSchemeDetailsId) {
        Map<String, Object> response = new HashMap<>();
        List<ScSubSchemeDetails> scSubSchemeDetailsList = scSubSchemeDetailsRepository.findByScSchemeDetailsIdAndActiveOrderBySubSchemeNameAsc(scSchemeDetailsId, true);
        if (scSubSchemeDetailsList.isEmpty()) {
//            throw new ValidationException("Invalid Id");
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            response.put("success", false);
            return response;
        } else {
            log.info("Entity is ", scSubSchemeDetailsList);
            response = convertListToMapResponse(scSubSchemeDetailsList);
            response.put("success", true);
            return response;

        }

    }

    private Map<String, Object> convertListToMapResponse(List<ScSubSchemeDetails> scSubSchemeDetailsList) {
        Map<String, Object> response = new HashMap<>();
        List<ScSubSchemeDetailsResponse> scSubSchemeDetailsResponses = scSubSchemeDetailsList.stream()
                .map(scSubSchemeDetails -> mapper.scSubSchemeDetailsEntityToObject(scSubSchemeDetails, ScSubSchemeDetailsResponse.class)).collect(Collectors.toList());
        response.put("scSubSchemeDetails", scSubSchemeDetailsResponses);
        response.put("totalItems", scSubSchemeDetailsList.size());
        return response;
    }



    @Transactional
    public ScSubSchemeDetailsResponse updateScSubSchemeDetailsDetails(EditScSubSchemeDetailsRequest scSubSchemeDetailsRequest) {
        ScSubSchemeDetailsResponse scSubSchemeDetailsResponse = new ScSubSchemeDetailsResponse();
        List<ScSubSchemeDetails> scSubSchemeDetailsList = scSubSchemeDetailsRepository.findByScSchemeDetailsIdAndSubSchemeNameAndScSubSchemeDetailsIdIsNot(scSubSchemeDetailsRequest.getScSchemeDetailsId(), scSubSchemeDetailsRequest.getSubSchemeName(), scSubSchemeDetailsRequest.getScSubSchemeDetailsId());
        if (scSubSchemeDetailsList.size() > 0) {
            scSubSchemeDetailsResponse.setError(true);
            scSubSchemeDetailsResponse.setError_description("ScSubSchemeDetails exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScSubSchemeDetails scSubSchemeDetails = scSubSchemeDetailsRepository.findByScSubSchemeDetailsIdAndActiveIn(scSubSchemeDetailsRequest.getScSubSchemeDetailsId(), Set.of(true, false));
            if (Objects.nonNull(scSubSchemeDetails)) {
                scSubSchemeDetails.setScSchemeDetailsId(scSubSchemeDetailsRequest.getScSchemeDetailsId());
                scSubSchemeDetails.setSubSchemeName(scSubSchemeDetailsRequest.getSubSchemeName());
                scSubSchemeDetails.setSubSchemeNameInKannada(scSubSchemeDetailsRequest.getSubSchemeNameInKannada());
                scSubSchemeDetails.setSubSchemeType(scSubSchemeDetailsRequest.getSubSchemeType());
                scSubSchemeDetails.setSubSchemeStartDate(scSubSchemeDetailsRequest.getSubSchemeStartDate());
                scSubSchemeDetails.setSubSchemeEndDate(scSubSchemeDetailsRequest.getSubSchemeEndDate());




                scSubSchemeDetails.setActive(true);
                ScSubSchemeDetails scSubSchemeDetails1 = scSubSchemeDetailsRepository.save(scSubSchemeDetails);
                scSubSchemeDetailsResponse = mapper.scSubSchemeDetailsEntityToObject(scSubSchemeDetails1, ScSubSchemeDetailsResponse.class);
                scSubSchemeDetailsResponse.setError(false);
            } else {
                scSubSchemeDetailsResponse.setError(true);
                scSubSchemeDetailsResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scSubSchemeDetailsResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("scSubSchemeDetails.subSchemeName");
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
        Page<ScSubSchemeDetailsDTO> ScSubSchemeDetailsDTOS = scSubSchemeDetailsRepository.getSortedScSubSchemeDetails(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",ScSubSchemeDetailsDTOS);
        return convertPageableDTOToMapResponse(ScSubSchemeDetailsDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScSubSchemeDetailsDTO> activeScSubSchemeDetails) {
        Map<String, Object> response = new HashMap<>();

        List<ScSubSchemeDetailsResponse> scSubSchemeDetailsResponses = activeScSubSchemeDetails.getContent().stream()
                .map(scSubSchemeDetails -> mapper.scSubSchemeDetailsDTOToObject(scSubSchemeDetails,ScSubSchemeDetailsResponse.class)).collect(Collectors.toList());
        response.put("ScSubSchemeDetails",scSubSchemeDetailsResponses);
        response.put("currentPage", activeScSubSchemeDetails.getNumber());
        response.put("totalItems", activeScSubSchemeDetails.getTotalElements());
        response.put("totalPages", activeScSubSchemeDetails.getTotalPages());

        return response;
    }
}
