package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.reasonLotRejectMaster.EditReasonLotRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonLotRejectMaster.ReasonLotRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonLotRejectMaster.ReasonLotRejectMasterResponse;
import com.sericulture.masterdata.model.api.relationship.RelationshipResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.ReasonLotRejectMaster;
import com.sericulture.masterdata.model.entity.Relationship;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ReasonLotRejectMasterRepository;
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
public class ReasonLotRejectMasterService {

    @Autowired
    ReasonLotRejectMasterRepository reasonLotRejectMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReasonLotRejectMasterResponse getReasonLotRejectMasterDetails(String reasonLotRejectMasterName){
        ReasonLotRejectMasterResponse reasonLotRejectMasterResponse = new ReasonLotRejectMasterResponse();
        ReasonLotRejectMaster reasonLotRejectMaster = null;
        if(reasonLotRejectMaster==null){
            reasonLotRejectMaster = reasonLotRejectMasterRepository.findByReasonLotRejectNameAndActive(reasonLotRejectMasterName,true);
            reasonLotRejectMasterResponse = mapper.reasonLotRejectEntityToObject(reasonLotRejectMaster, ReasonLotRejectMasterResponse.class);
            reasonLotRejectMasterResponse.setError(false);
        }else{
            reasonLotRejectMasterResponse.setError(true);
            reasonLotRejectMasterResponse.setError_description("Village not found");
        }
        log.info("Entity is ",reasonLotRejectMaster);
        return reasonLotRejectMasterResponse;
    }

    @Transactional
    public ReasonLotRejectMasterResponse insertReasonLotRejectMasterDetails(ReasonLotRejectMasterRequest reasonLotRejectMasterRequest){
        ReasonLotRejectMasterResponse reasonLotRejectMasterResponse = new ReasonLotRejectMasterResponse();
        ReasonLotRejectMaster reasonLotRejectMaster = mapper.reasonLotRejectObjectToEntity(reasonLotRejectMasterRequest,ReasonLotRejectMaster.class);
        validator.validate(reasonLotRejectMaster);
        List<ReasonLotRejectMaster> reasonLotRejectMasterList = reasonLotRejectMasterRepository.findByReasonLotRejectName(reasonLotRejectMasterRequest.getReasonLotRejectName());
        if(!reasonLotRejectMasterList.isEmpty() && reasonLotRejectMasterList.stream().filter(ReasonLotRejectMaster::getActive).findAny().isPresent()){
            reasonLotRejectMasterResponse.setError(true);
            reasonLotRejectMasterResponse.setError_description("ReasonLotReject name already exist");
        }
        else if(!reasonLotRejectMasterList.isEmpty() && reasonLotRejectMasterList.stream().filter(Predicate.not(ReasonLotRejectMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            reasonLotRejectMasterResponse.setError(true);
            reasonLotRejectMasterResponse.setError_description("ReasonLotReject name already exist with inactive state");
        }else {
            reasonLotRejectMasterResponse = mapper.reasonLotRejectEntityToObject(reasonLotRejectMasterRepository.save(reasonLotRejectMaster), ReasonLotRejectMasterResponse.class);
            reasonLotRejectMasterResponse.setError(false);
        }
        return reasonLotRejectMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedReasonLotRejectMasterDetails(final Pageable pageable){
        return convertToMapResponse(reasonLotRejectMasterRepository.findByActiveOrderByReasonLotRejectNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(reasonLotRejectMasterRepository.findByActiveOrderByReasonLotRejectNameAsc(isActive));
    }


    private Map<String, Object> convertToMapResponse(final Page<ReasonLotRejectMaster> activeReasonLotRejectMasters) {
        Map<String, Object> response = new HashMap<>();

        List<ReasonLotRejectMasterResponse> reasonLotRejectMasterResponses = activeReasonLotRejectMasters.getContent().stream()
                .map(reasonLotRejectMaster -> mapper.reasonLotRejectEntityToObject(reasonLotRejectMaster,ReasonLotRejectMasterResponse.class)).collect(Collectors.toList());
        response.put("reasonLotRejectMaster",reasonLotRejectMasterResponses);
        response.put("currentPage", activeReasonLotRejectMasters.getNumber());
        response.put("totalItems", activeReasonLotRejectMasters.getTotalElements());
        response.put("totalPages", activeReasonLotRejectMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ReasonLotRejectMaster> activeReasonLotRejectMasters) {
        Map<String, Object> response = new HashMap<>();

        List<ReasonLotRejectMasterResponse> reasonLotRejectMasterResponses = activeReasonLotRejectMasters.stream()
                .map(reasonLotRejectMaster -> mapper.reasonLotRejectEntityToObject(reasonLotRejectMaster,ReasonLotRejectMasterResponse.class)).collect(Collectors.toList());
        response.put("reasonLotRejectMaster",reasonLotRejectMasterResponses);
        return response;
    }

    @Transactional
    public ReasonLotRejectMasterResponse deleteReasonLotRejectMasterDetails(long id) {
        ReasonLotRejectMasterResponse reasonLotRejectMasterResponse = new ReasonLotRejectMasterResponse();
        ReasonLotRejectMaster reasonLotRejectMaster = reasonLotRejectMasterRepository.findByReasonLotRejectIdAndActive(id, true);
        if (Objects.nonNull(reasonLotRejectMaster)) {
            reasonLotRejectMaster.setActive(false);
            reasonLotRejectMasterResponse = mapper.reasonLotRejectEntityToObject(reasonLotRejectMasterRepository.save(reasonLotRejectMaster), ReasonLotRejectMasterResponse.class);
            reasonLotRejectMasterResponse.setError(false);
        } else {
            reasonLotRejectMasterResponse.setError(true);
            reasonLotRejectMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return reasonLotRejectMasterResponse;
    }

    @Transactional
    public ReasonLotRejectMasterResponse getById(int id){
        ReasonLotRejectMasterResponse reasonLotRejectMasterResponse = new ReasonLotRejectMasterResponse();
        ReasonLotRejectMaster reasonLotRejectMaster = reasonLotRejectMasterRepository.findByReasonLotRejectIdAndActive(id,true);
        if(reasonLotRejectMaster == null){
            reasonLotRejectMasterResponse.setError(true);
            reasonLotRejectMasterResponse.setError_description("Invalid id");
        }else{
            reasonLotRejectMasterResponse =  mapper.reasonLotRejectEntityToObject(reasonLotRejectMaster,ReasonLotRejectMasterResponse.class);
            reasonLotRejectMasterResponse.setError(false);
        }
        log.info("Entity is ",reasonLotRejectMaster);
        return reasonLotRejectMasterResponse;
    }

    @Transactional
    public ReasonLotRejectMasterResponse updateReasonLotRejectMasterDetails(EditReasonLotRejectMasterRequest reasonLotRejectMasterRequest) {
        ReasonLotRejectMasterResponse reasonLotRejectMasterResponse = new ReasonLotRejectMasterResponse();
        List<ReasonLotRejectMaster> reasonLotRejectMasterList = reasonLotRejectMasterRepository.findByReasonLotRejectName(reasonLotRejectMasterRequest.getReasonLotRejectName());
        if (reasonLotRejectMasterList.size() > 0) {
            reasonLotRejectMasterResponse.setError(true);
            reasonLotRejectMasterResponse.setError_description("ReasonLotRejectMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ReasonLotRejectMaster reasonLotRejectMaster = reasonLotRejectMasterRepository.findByReasonLotRejectIdAndActiveIn(reasonLotRejectMasterRequest.getReasonLotRejectId(), Set.of(true, false));
            if (Objects.nonNull(reasonLotRejectMaster)) {
                reasonLotRejectMaster.setReasonLotRejectName(reasonLotRejectMasterRequest.getReasonLotRejectName());
                reasonLotRejectMaster.setActive(true);
                ReasonLotRejectMaster reasonLotRejectMaster1 = reasonLotRejectMasterRepository.save(reasonLotRejectMaster);
                reasonLotRejectMasterResponse = mapper.reasonLotRejectEntityToObject(reasonLotRejectMaster1, ReasonLotRejectMasterResponse.class);
                reasonLotRejectMasterResponse.setError(false);
            } else {
                reasonLotRejectMasterResponse.setError(true);
                reasonLotRejectMasterResponse.setError_description("Error occurred while fetching reasonLotRejectMaster");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return reasonLotRejectMasterResponse;
    }

}