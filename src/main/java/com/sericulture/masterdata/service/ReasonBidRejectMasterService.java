package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.reasonBidRejectMaster.EditReasonBidRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonBidRejectMaster.ReasonBidRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonBidRejectMaster.ReasonBidRejectMasterResponse;
import com.sericulture.masterdata.model.api.reasonLotRejectMaster.ReasonLotRejectMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.ReasonBidRejectMaster;
import com.sericulture.masterdata.model.entity.ReasonLotRejectMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ReasonBidRejectMasterRepository;
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
public class ReasonBidRejectMasterService {
    @Autowired
    ReasonBidRejectMasterRepository reasonBidRejectMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReasonBidRejectMasterResponse getReasonBidRejectMasterDetails(String reasonBidRejectMasterName){
        ReasonBidRejectMasterResponse reasonBidRejectMasterResponse = new ReasonBidRejectMasterResponse();
       ReasonBidRejectMaster reasonBidRejectMaster = null;
        if(reasonBidRejectMaster==null){
            reasonBidRejectMaster = reasonBidRejectMasterRepository.findByReasonBidRejectNameAndActive(reasonBidRejectMasterName,true);
            reasonBidRejectMasterResponse = mapper.reasonBidRejectEntityToObject(reasonBidRejectMaster, ReasonBidRejectMasterResponse.class);
            reasonBidRejectMasterResponse.setError(false);
        }else{
            reasonBidRejectMasterResponse.setError(true);
            reasonBidRejectMasterResponse.setError_description("ReasonBidReject not found");
        }
        log.info("Entity is ",reasonBidRejectMaster);
        return reasonBidRejectMasterResponse;
    }

    @Transactional
    public ReasonBidRejectMasterResponse insertReasonBidRejectMasterDetails(ReasonBidRejectMasterRequest reasonBidRejectMasterRequest){
        ReasonBidRejectMasterResponse reasonBidRejectMasterResponse = new ReasonBidRejectMasterResponse();
        ReasonBidRejectMaster reasonBidRejectMaster = mapper.reasonBidRejectObjectToEntity(reasonBidRejectMasterRequest,ReasonBidRejectMaster.class);
        validator.validate(reasonBidRejectMaster);
        List<ReasonBidRejectMaster> reasonBidRejectMasterList = reasonBidRejectMasterRepository.findByReasonBidRejectName(reasonBidRejectMasterRequest.getReasonBidRejectName());
        if(!reasonBidRejectMasterList.isEmpty() && reasonBidRejectMasterList.stream().filter(ReasonBidRejectMaster::getActive).findAny().isPresent()){
            reasonBidRejectMasterResponse.setError(true);
            reasonBidRejectMasterResponse.setError_description("ReasonBidRejectMaster name already exist");
        }
        else if(!reasonBidRejectMasterList.isEmpty() && reasonBidRejectMasterList.stream().filter(Predicate.not(ReasonBidRejectMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            reasonBidRejectMasterResponse.setError(true);
            reasonBidRejectMasterResponse.setError_description("ReasonBidRejectMaster name already exist with inactive state");
        }else {
            reasonBidRejectMasterResponse = mapper.reasonBidRejectEntityToObject(reasonBidRejectMasterRepository.save(reasonBidRejectMaster), ReasonBidRejectMasterResponse.class);
            reasonBidRejectMasterResponse.setError(false);
        }
        return reasonBidRejectMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedReasonBidRejectMasterDetails(final Pageable pageable){
        return convertToMapResponse(reasonBidRejectMasterRepository.findByActiveOrderByReasonBidRejectIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(reasonBidRejectMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ReasonBidRejectMaster> activeReasonBidRejectMasters) {
        Map<String, Object> response = new HashMap<>();

        List<ReasonBidRejectMasterResponse> reasonBidRejectMasterResponses = activeReasonBidRejectMasters.getContent().stream()
                .map(reasonBidRejectMaster -> mapper.reasonBidRejectEntityToObject(reasonBidRejectMaster,ReasonBidRejectMasterResponse.class)).collect(Collectors.toList());
        response.put("reasonBidRejectMaster",reasonBidRejectMasterResponses);
        response.put("currentPage", activeReasonBidRejectMasters.getNumber());
        response.put("totalItems", activeReasonBidRejectMasters.getTotalElements());
        response.put("totalPages", activeReasonBidRejectMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ReasonBidRejectMaster> activeReasonBidRejectMasters) {
        Map<String, Object> response = new HashMap<>();

        List<ReasonBidRejectMasterResponse> reasonBidRejectMasterResponses = activeReasonBidRejectMasters.stream()
                .map(reasonBidRejectMaster -> mapper.reasonBidRejectEntityToObject(reasonBidRejectMaster,ReasonBidRejectMasterResponse.class)).collect(Collectors.toList());
        response.put("reasonBidRejectMaster",reasonBidRejectMasterResponses);
        return response;
    }

    @Transactional
    public ReasonBidRejectMasterResponse deleteReasonBidRejectMasterDetails(long id) {
        ReasonBidRejectMasterResponse reasonBidRejectMasterResponse = new ReasonBidRejectMasterResponse();
        ReasonBidRejectMaster reasonBidRejectMaster = reasonBidRejectMasterRepository.findByReasonBidRejectIdAndActive(id, true);
        if (Objects.nonNull(reasonBidRejectMaster)) {
            reasonBidRejectMaster.setActive(false);
            reasonBidRejectMasterResponse = mapper.reasonBidRejectEntityToObject(reasonBidRejectMasterRepository.save(reasonBidRejectMaster), ReasonBidRejectMasterResponse.class);
            reasonBidRejectMasterResponse.setError(false);
        } else {
            reasonBidRejectMasterResponse.setError(true);
            reasonBidRejectMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return reasonBidRejectMasterResponse;
    }

    @Transactional
    public ReasonBidRejectMasterResponse getById(int id){
        ReasonBidRejectMasterResponse reasonBidRejectMasterResponse = new ReasonBidRejectMasterResponse();
        ReasonBidRejectMaster reasonBidRejectMaster = reasonBidRejectMasterRepository.findByReasonBidRejectIdAndActive(id,true);
        if(reasonBidRejectMaster == null){
            reasonBidRejectMasterResponse.setError(true);
            reasonBidRejectMasterResponse.setError_description("Invalid id");
        }else{
            reasonBidRejectMasterResponse =  mapper.reasonBidRejectEntityToObject(reasonBidRejectMaster,ReasonBidRejectMasterResponse.class);
            reasonBidRejectMasterResponse.setError(false);
        }
        log.info("Entity is ",reasonBidRejectMaster);
        return reasonBidRejectMasterResponse;
    }

    @Transactional
    public ReasonBidRejectMasterResponse updateReasonBidRejectMasterDetails(EditReasonBidRejectMasterRequest reasonBidRejectMasterRequest) {
        ReasonBidRejectMasterResponse reasonBidRejectMasterResponse = new ReasonBidRejectMasterResponse();
        List<ReasonBidRejectMaster> reasonBidRejectMasterList = reasonBidRejectMasterRepository.findByReasonBidRejectName(reasonBidRejectMasterRequest.getReasonBidRejectName());
        if (reasonBidRejectMasterList.size() > 0) {
            reasonBidRejectMasterResponse.setError(true);
            reasonBidRejectMasterResponse.setError_description("ReasonBidRejectMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ReasonBidRejectMaster reasonBidRejectMaster = reasonBidRejectMasterRepository.findByReasonBidRejectIdAndActiveIn(reasonBidRejectMasterRequest.getReasonBidRejectId(), Set.of(true, false));
            if (Objects.nonNull(reasonBidRejectMaster)) {
                reasonBidRejectMaster.setReasonBidRejectName(reasonBidRejectMasterRequest.getReasonBidRejectName());
                reasonBidRejectMaster.setActive(true);
                ReasonBidRejectMaster reasonBidRejectMaster1 = reasonBidRejectMasterRepository.save(reasonBidRejectMaster);
                reasonBidRejectMasterResponse = mapper.reasonBidRejectEntityToObject(reasonBidRejectMaster1, ReasonBidRejectMasterResponse.class);
                reasonBidRejectMasterResponse.setError(false);
            } else {
                reasonBidRejectMasterResponse.setError(true);
                reasonBidRejectMasterResponse.setError_description("Error occurred while fetching reasonBidRejectMaster");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return reasonBidRejectMasterResponse;
    }

}
