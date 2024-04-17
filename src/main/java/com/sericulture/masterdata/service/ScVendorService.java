package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scVendor.EditScVendorRequest;
import com.sericulture.masterdata.model.api.scVendor.ScVendorRequest;
import com.sericulture.masterdata.model.api.scVendor.ScVendorResponse;
import com.sericulture.masterdata.model.entity.ScVendor;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScVendorRepository;
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
public class ScVendorService {

    @Autowired
    ScVendorRepository scVendorRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ScVendorResponse getScVendorDetails(String name){
        ScVendorResponse scVendorResponse = new ScVendorResponse();
        ScVendor scVendor= scVendorRepository.findByNameAndActive(name,true);
        if(scVendor==null){
            scVendorResponse.setError(true);
            scVendorResponse.setError_description("Program not found");
        }else{
            scVendorResponse = mapper.scVendorEntityToObject(scVendor,ScVendorResponse.class);
            scVendorResponse.setError(false);
        }
        log.info("Entity is ",scVendor);
        return scVendorResponse;
    }

    @Transactional
    public ScVendorResponse insertScVendorDetails(ScVendorRequest scVendorRequest){
        ScVendorResponse scVendorResponse = new ScVendorResponse();
        ScVendor scVendor = mapper.scVendorObjectToEntity(scVendorRequest,ScVendor.class);
        validator.validate(scVendor);
        List<ScVendor> scVendorList = scVendorRepository.findByNameAndNameInKannadaAndType(scVendorRequest.getName(), scVendorRequest.getNameInKannada(), scVendorRequest.getType());
        if(!scVendorList.isEmpty() && scVendorList.stream().filter( ScVendor::getActive).findAny().isPresent()){
            scVendorResponse.setError(true);
            scVendorResponse.setError_description("Sc Vendor name already exist");
//        }
//        else if(! ScVendorList.isEmpty() && ScVendorList.stream().filter(Predicate.not( ScVendor::getActive)).findAny().isPresent()){
//            ScVendorResponse.setError(true);
//            ScVendorResponse.setError_description("Tr Program name already exist with inactive state");
        }else {
            scVendorResponse  = mapper.scVendorEntityToObject( scVendorRepository.save(scVendor), ScVendorResponse.class);
            scVendorResponse.setError(false);
        }
        return scVendorResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScVendorDetails(final Pageable pageable){
        return convertToMapResponse(scVendorRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scVendorRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScVendor> activeScVendors) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorResponse> scVendorResponses= activeScVendors.getContent().stream()
                .map(scVendor -> mapper.scVendorEntityToObject(scVendor,ScVendorResponse.class)).collect(Collectors.toList());
        response.put("ScVendor",scVendorResponses);
        response.put("currentPage", activeScVendors.getNumber());
        response.put("totalItems", activeScVendors.getTotalElements());
        response.put("totalPages", activeScVendors.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScVendor> activeScVendors) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorResponse> scVendorResponses= activeScVendors.stream()
                .map(ScVendor -> mapper.scVendorEntityToObject(ScVendor,ScVendorResponse.class)).collect(Collectors.toList());
        response.put("ScVendor",scVendorResponses);
        return response;
    }

    @Transactional
    public ScVendorResponse deleteScVendorDetails(long id) {

        ScVendorResponse scVendorResponse = new ScVendorResponse();
        ScVendor scVendor= scVendorRepository.findByScVendorIdAndActive(id, true);
        if (Objects.nonNull(scVendor)) {
            scVendor.setActive(false);
            scVendorResponse= mapper.scVendorEntityToObject(scVendorRepository.save(scVendor), ScVendorResponse.class);
            scVendorResponse.setError(false);
        } else {
            scVendorResponse.setError(true);
            scVendorResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scVendorResponse;
    }

    @Transactional
    public ScVendorResponse getById(int id){
        ScVendorResponse scVendorResponse = new ScVendorResponse();
        ScVendor scVendor= scVendorRepository.findByScVendorIdAndActive(id, true);
        if(scVendor== null){
            scVendorResponse.setError(true);
            scVendorResponse.setError_description("Invalid id");
        }else{
            scVendorResponse =  mapper.scVendorEntityToObject(scVendor, ScVendorResponse.class);
            scVendorResponse.setError(false);
        }
        log.info("Entity is ",scVendor);
        return scVendorResponse;
    }

    @Transactional
    public ScVendorResponse updateScVendorsDetails(EditScVendorRequest scVendorRequest){

        ScVendorResponse scVendorResponse = new ScVendorResponse();
        List<ScVendor> scVendorList = scVendorRepository. findByActiveAndNameAndNameInKannadaAndType(true,scVendorRequest.getName(), scVendorRequest.getNameInKannada(), scVendorRequest.getType());
        if(scVendorList.size()>0){
            scVendorResponse.setError(true);
            scVendorResponse.setError_description("Sc Vendor exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            ScVendor scVendor = scVendorRepository.findByScVendorIdAndActiveIn(scVendorRequest.getScVendorId(), Set.of(true,false));
            if(Objects.nonNull(scVendor)){
                scVendor.setName( scVendorRequest.getName());
                scVendor.setNameInKannada( scVendorRequest.getNameInKannada());
                scVendor.setType(scVendorRequest.getType());
                scVendor.setActive(true);
                ScVendor scVendor1= scVendorRepository.save(scVendor);
                scVendorResponse = mapper.scVendorEntityToObject(scVendor1, ScVendorResponse.class);
                scVendorResponse.setError(false);
            } else {
                scVendorResponse.setError(true);
                scVendorResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return scVendorResponse;
    }

}
