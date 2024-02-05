package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.reelerTypeMaster.EditReelerTypeMasterRequest;
import com.sericulture.masterdata.model.api.reelerTypeMaster.ReelerTypeMasterRequest;
import com.sericulture.masterdata.model.api.reelerTypeMaster.ReelerTypeMasterResponse;
import com.sericulture.masterdata.model.entity.ReelerTypeMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ReelerTypeMasterRepository;
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
public class ReelerTypeMasterService {
    @Autowired
    ReelerTypeMasterRepository reelerTypeMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReelerTypeMasterResponse getReelerTypeMasterDetails(String reelerTypeMasterName){
        ReelerTypeMasterResponse reelerTypeMasterResponse = new ReelerTypeMasterResponse();
        ReelerTypeMaster reelerTypeMaster= reelerTypeMasterRepository. findByReelerTypeMasterNameAndActive(reelerTypeMasterName, true);
        if(reelerTypeMaster==null){
            reelerTypeMasterResponse.setError(true);
            reelerTypeMasterResponse.setError_description("Reeler Type  not found");
        }else{
            reelerTypeMasterResponse = mapper.reelerTypeMasterEntityToObject(reelerTypeMaster, ReelerTypeMasterResponse.class);
            reelerTypeMasterResponse.setError(false);
        }
        log.info("Entity is ",reelerTypeMaster);
        return reelerTypeMasterResponse;
    }

    @Transactional
    public ReelerTypeMasterResponse insertReelerTypeMasterDetails(ReelerTypeMasterRequest reelerTypeMasterRequest){
        ReelerTypeMasterResponse reelerTypeMasterResponse = new ReelerTypeMasterResponse();
        ReelerTypeMaster reelerTypeMaster = mapper.reelerTypeMasterObjectToEntity(reelerTypeMasterRequest, ReelerTypeMaster.class);
        validator.validate(reelerTypeMaster);
        List<ReelerTypeMaster> reelerTypeMasterList = reelerTypeMasterRepository.findByReelerTypeMasterNameAndReelerTypeNameInKannada(reelerTypeMasterRequest.getReelerTypeMasterName(), reelerTypeMasterRequest.getReelerTypeNameInKannada());
        if(!reelerTypeMasterList.isEmpty() && reelerTypeMasterList.stream(). filter(ReelerTypeMaster::getActive).findAny().isPresent()){
            reelerTypeMasterResponse.setError(true);
            reelerTypeMasterResponse.setError_description("Reeler Type name already exist");
        }
        else if(!reelerTypeMasterList.isEmpty() && reelerTypeMasterList.stream().filter(Predicate.not(ReelerTypeMaster::getActive)).findAny().isPresent()){
            reelerTypeMasterResponse.setError(true);
            reelerTypeMasterResponse.setError_description("Reeler Type name already exist with inactive state");
        }else {
            reelerTypeMasterResponse = mapper.reelerTypeMasterEntityToObject(reelerTypeMasterRepository.save(reelerTypeMaster), ReelerTypeMasterResponse.class);
            reelerTypeMasterResponse.setError(false);
        }
        return reelerTypeMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedReelerTypeMasterDetails(final Pageable pageable){
        return convertToMapResponse(reelerTypeMasterRepository.findByActiveOrderByReelerTypeMasterNameAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(reelerTypeMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ReelerTypeMaster> activeReelerTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<ReelerTypeMasterResponse> reelerTypeMasterResponses= activeReelerTypeMasters.getContent().stream()
                .map(reelerTypeMaster -> mapper.reelerTypeMasterEntityToObject(reelerTypeMaster, ReelerTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("reelerTypeMaster",reelerTypeMasterResponses);
        response.put("currentPage", activeReelerTypeMasters.getNumber());
        response.put("totalItems", activeReelerTypeMasters.getTotalElements());
        response.put("totalPages", activeReelerTypeMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ReelerTypeMaster> activeReelerTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<ReelerTypeMasterResponse> reelerTypeMasterResponses= activeReelerTypeMasters.stream()
                .map(reelerTypeMaster -> mapper.reelerTypeMasterEntityToObject(reelerTypeMaster, ReelerTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("reelerTypeMaster",reelerTypeMasterResponses);
        return response;
    }

    @Transactional
    public ReelerTypeMasterResponse deleteReelerTypeMasterDetails(long id) {

        ReelerTypeMasterResponse reelerTypeMasterResponse  = new ReelerTypeMasterResponse();
        ReelerTypeMaster reelerTypeMaster  = reelerTypeMasterRepository.findByReelerTypeMasterIdAndActive(id, true);
        if (Objects.nonNull(reelerTypeMaster)) {
            reelerTypeMaster.setActive(false);
            reelerTypeMasterResponse= mapper.reelerTypeMasterEntityToObject(reelerTypeMasterRepository.save(reelerTypeMaster), ReelerTypeMasterResponse.class);
            reelerTypeMasterResponse.setError(false);
        } else {
            reelerTypeMasterResponse.setError(true);
            reelerTypeMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return reelerTypeMasterResponse;
    }

    @Transactional
    public ReelerTypeMasterResponse getById(int id){
        ReelerTypeMasterResponse reelerTypeMasterResponse  = new ReelerTypeMasterResponse();
        ReelerTypeMaster reelerTypeMaster= reelerTypeMasterRepository. findByReelerTypeMasterIdAndActive(id, true);
        if(reelerTypeMaster== null){
            reelerTypeMasterResponse.setError(true);
            reelerTypeMasterResponse.setError_description("Invalid id");
        }else{
            reelerTypeMasterResponse =  mapper.reelerTypeMasterEntityToObject(reelerTypeMaster, ReelerTypeMasterResponse.class);
            reelerTypeMasterResponse.setError(false);
        }
        log.info("Entity is ",reelerTypeMaster);
        return reelerTypeMasterResponse;
    }

    @Transactional
    public ReelerTypeMasterResponse updateReelerTypeMastersDetails(EditReelerTypeMasterRequest reelerTypeMasterRequest){

        ReelerTypeMasterResponse reelerTypeMasterResponse  = new ReelerTypeMasterResponse();
        List<ReelerTypeMaster> reelerTypeMasterList = reelerTypeMasterRepository. findByReelerTypeMasterNameAndReelerTypeNameInKannada(reelerTypeMasterRequest.getReelerTypeMasterName(),reelerTypeMasterRequest.getReelerTypeNameInKannada());
        if(reelerTypeMasterList.size()>0){
            reelerTypeMasterResponse.setError(true);
            reelerTypeMasterResponse.setError_description("Reeler Type , duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            ReelerTypeMaster reelerTypeMaster = reelerTypeMasterRepository.findByReelerTypeMasterIdAndActiveIn(reelerTypeMasterRequest.getReelerTypeMasterId(), Set.of(true,false));
            if(Objects.nonNull(reelerTypeMaster)){
                reelerTypeMaster.setReelerTypeMasterName(reelerTypeMasterRequest.getReelerTypeMasterName());
                reelerTypeMaster.setReelerTypeNameInKannada(reelerTypeMasterRequest.getReelerTypeNameInKannada());
                reelerTypeMaster.setNoOfDeviceAllowed(reelerTypeMasterRequest.getNoOfDeviceAllowed());
                reelerTypeMaster.setActive(true);
                ReelerTypeMaster releerTypeMaster1= reelerTypeMasterRepository.save(reelerTypeMaster);
                reelerTypeMasterResponse = mapper.reelerTypeMasterEntityToObject(releerTypeMaster1, ReelerTypeMasterResponse.class);
                reelerTypeMasterResponse.setError(false);
            } else {
                reelerTypeMasterResponse.setError(true);
                reelerTypeMasterResponse.setError_description("Error occurred while fetching reeler Type");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return reelerTypeMasterResponse;
    }


}



