package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scSchemeDetails.EditScSchemeDetailsRequest;
import com.sericulture.masterdata.model.api.scSchemeDetails.ScSchemeDetailsRequest;
import com.sericulture.masterdata.model.api.scSchemeDetails.ScSchemeDetailsResponse;
import com.sericulture.masterdata.model.entity.ScSchemeDetails;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScSchemeDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScSchemeDetailsService {
    @Autowired
    ScSchemeDetailsRepository scSchemeDetailsRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ScSchemeDetailsResponse getScSchemeDetailsDetails(String schemeName){
        ScSchemeDetailsResponse scSchemeDetailsResponse = new ScSchemeDetailsResponse();
        ScSchemeDetails scSchemeDetails= scSchemeDetailsRepository.findBySchemeNameAndActive(schemeName,true);
        if(scSchemeDetails==null){
            scSchemeDetailsResponse.setError(true);
            scSchemeDetailsResponse.setError_description("Program not found");
        }else{
            scSchemeDetailsResponse = mapper.scSchemeDetailsEntityToObject(scSchemeDetails,ScSchemeDetailsResponse.class);
            scSchemeDetailsResponse.setError(false);
        }
        log.info("Entity is ",scSchemeDetails);
        return scSchemeDetailsResponse;
    }

    @Transactional
    public ScSchemeDetailsResponse insertScSchemeDetailsDetails(ScSchemeDetailsRequest scSchemeDetailsRequest){
        ScSchemeDetailsResponse scSchemeDetailsResponse = new ScSchemeDetailsResponse();
        ScSchemeDetails scSchemeDetails = mapper.scSchemeDetailsObjectToEntity(scSchemeDetailsRequest,ScSchemeDetails.class);
        validator.validate(scSchemeDetails);
        List<ScSchemeDetails> scSchemeDetailsList = scSchemeDetailsRepository.findBySchemeNameAndSchemeNameInKannada(scSchemeDetailsRequest.getSchemeName(),scSchemeDetailsRequest.getSchemeNameInKannada());
        if(!scSchemeDetailsList.isEmpty() && scSchemeDetailsList.stream().filter( ScSchemeDetails::getActive).findAny().isPresent()){
            scSchemeDetailsResponse.setError(true);
            scSchemeDetailsResponse.setError_description("Sc Scheme Details name already exist");
//        }
//        else if(! ScSchemeDetailsList.isEmpty() && ScSchemeDetailsList.stream().filter(Predicate.not( ScSchemeDetails::getActive)).findAny().isPresent()){
//            ScSchemeDetailsResponse.setError(true);
//            ScSchemeDetailsResponse.setError_description("Tr Program name already exist with inactive state");
        }else {
            scSchemeDetailsResponse  = mapper.scSchemeDetailsEntityToObject( scSchemeDetailsRepository.save(scSchemeDetails), ScSchemeDetailsResponse.class);
            scSchemeDetailsResponse.setError(false);
        }
        return scSchemeDetailsResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScSchemeDetailsDetails(final Pageable pageable){
        return convertToMapResponse(scSchemeDetailsRepository.findByActiveOrderBySchemeNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scSchemeDetailsRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScSchemeDetails> activeScSchemeDetails) {
        Map<String, Object> response = new HashMap<>();

        List<ScSchemeDetailsResponse> scSchemeDetailsResponses= activeScSchemeDetails.getContent().stream()
                .map(scSchemeDetails -> mapper.scSchemeDetailsEntityToObject(scSchemeDetails,ScSchemeDetailsResponse.class)).collect(Collectors.toList());
        response.put("ScSchemeDetails",scSchemeDetailsResponses);
        response.put("currentPage", activeScSchemeDetails.getNumber());
        response.put("totalItems", activeScSchemeDetails.getTotalElements());
        response.put("totalPages", activeScSchemeDetails.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScSchemeDetails> activeScSchemeDetails) {
        Map<String, Object> response = new HashMap<>();

        List<ScSchemeDetailsResponse> scSchemeDetailsResponses= activeScSchemeDetails.stream()
                .map(scSchemeDetails -> mapper.scSchemeDetailsEntityToObject(scSchemeDetails,ScSchemeDetailsResponse.class)).collect(Collectors.toList());
        response.put("ScSchemeDetails",scSchemeDetailsResponses);
        return response;
    }

    @Transactional
    public ScSchemeDetailsResponse deleteScSchemeDetailsDetails(long id) {

        ScSchemeDetailsResponse scSchemeDetailsResponse = new ScSchemeDetailsResponse();
        ScSchemeDetails scSchemeDetails= scSchemeDetailsRepository.findByScSchemeDetailsIdAndActive(id, true);
        if (Objects.nonNull(scSchemeDetails)) {
            scSchemeDetails.setActive(false);
            scSchemeDetailsResponse= mapper.scSchemeDetailsEntityToObject(scSchemeDetailsRepository.save(scSchemeDetails), ScSchemeDetailsResponse.class);
            scSchemeDetailsResponse.setError(false);
        } else {
            scSchemeDetailsResponse.setError(true);
            scSchemeDetailsResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scSchemeDetailsResponse;
    }

    @Transactional
    public ScSchemeDetailsResponse getById(int id){
        ScSchemeDetailsResponse scSchemeDetailsResponse = new ScSchemeDetailsResponse();
        ScSchemeDetails scSchemeDetails= scSchemeDetailsRepository.findByScSchemeDetailsIdAndActive(id, true);
        if(scSchemeDetails== null){
            scSchemeDetailsResponse.setError(true);
            scSchemeDetailsResponse.setError_description("Invalid id");
        }else{
            scSchemeDetailsResponse =  mapper.scSchemeDetailsEntityToObject(scSchemeDetails, ScSchemeDetailsResponse.class);
            scSchemeDetailsResponse.setError(false);
        }
        log.info("Entity is ",scSchemeDetails);
        return scSchemeDetailsResponse;
    }

    @Transactional
    public ScSchemeDetailsResponse updateScSchemeDetailsDetails(EditScSchemeDetailsRequest scSchemeDetailsRequest){

        ScSchemeDetailsResponse scSchemeDetailsResponse = new ScSchemeDetailsResponse();
        List<ScSchemeDetails> scSchemeDetailsList = scSchemeDetailsRepository. findByActiveAndSchemeNameAndSchemeNameInKannada(true,scSchemeDetailsRequest.getSchemeName(), scSchemeDetailsRequest.getSchemeNameInKannada());
        if(scSchemeDetailsList.size()>0){
            scSchemeDetailsResponse.setError(true);
            scSchemeDetailsResponse.setError_description("Sc Scheme Details exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            ScSchemeDetails scSchemeDetails = scSchemeDetailsRepository.findByScSchemeDetailsIdAndActiveIn(scSchemeDetailsRequest.getScSchemeDetailsId(), Set.of(true,false));
            if(Objects.nonNull(scSchemeDetails)){
                scSchemeDetails.setSchemeName( scSchemeDetailsRequest.getSchemeName());
                scSchemeDetails.setSchemeNameInKannada( scSchemeDetailsRequest.getSchemeNameInKannada());
                scSchemeDetails.setSchemeStartDate( scSchemeDetailsRequest.getSchemeStartDate());
                scSchemeDetails.setSchemeEndDate( scSchemeDetailsRequest.getSchemeEndDate());

                scSchemeDetails.setActive(true);
                ScSchemeDetails scSchemeDetails1= scSchemeDetailsRepository.save(scSchemeDetails);
                scSchemeDetailsResponse = mapper.scSchemeDetailsEntityToObject(scSchemeDetails1, ScSchemeDetailsResponse.class);
                scSchemeDetailsResponse.setError(false);
            } else {
                scSchemeDetailsResponse.setError(true);
                scSchemeDetailsResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return scSchemeDetailsResponse;
    }
}
