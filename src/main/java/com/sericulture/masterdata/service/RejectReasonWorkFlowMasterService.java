package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.rejectReasonWorkflowMaster.EditRejectReasonWorkFlowMasterRequest;
import com.sericulture.masterdata.model.api.rejectReasonWorkflowMaster.RejectReasonWorkFlowMasterRequest;
import com.sericulture.masterdata.model.api.rejectReasonWorkflowMaster.RejectReasonWorkFlowMasterResponse;
import com.sericulture.masterdata.model.entity.RejectReasonWorkFlowMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RejectReasonWorkFlowMasterRepository;
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
public class RejectReasonWorkFlowMasterService {

    @Autowired
    RejectReasonWorkFlowMasterRepository rejectReasonWorkFlowMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;



    @Transactional
    public RejectReasonWorkFlowMasterResponse insertRejectReasonWorkFlowMasterDetails(RejectReasonWorkFlowMasterRequest rejectReasonWorkFlowMasterRequest){
        RejectReasonWorkFlowMasterResponse rejectReasonWorkFlowMasterResponse = new RejectReasonWorkFlowMasterResponse();
        RejectReasonWorkFlowMaster rejectReasonWorkFlowMaster = mapper.rejectReasonWorkflowMasterObjectToEntity(rejectReasonWorkFlowMasterRequest, RejectReasonWorkFlowMaster.class);
        validator.validate(rejectReasonWorkFlowMaster);
        List<RejectReasonWorkFlowMaster> rejectReasonWorkFlowMasterList = rejectReasonWorkFlowMasterRepository.findByReason(rejectReasonWorkFlowMasterRequest.getReason());
        if(!rejectReasonWorkFlowMasterList.isEmpty() && rejectReasonWorkFlowMasterList.stream().filter(RejectReasonWorkFlowMaster::getActive).findAny().isPresent()){
            rejectReasonWorkFlowMasterResponse.setError(true);
            rejectReasonWorkFlowMasterResponse.setError_description("RejectReasonWorkFlow name already exist");
//        }
//        else if(!rejectReasonWorkFlowMasterList.isEmpty() && rejectReasonWorkFlowMasterList.stream().filter(Predicate.not(RejectReasonWorkFlowMaster::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            rejectReasonWorkFlowMasterResponse.setError(true);
//            rejectReasonWorkFlowMasterResponse.setError_description("RejectReasonWorkFlow name already exist with inactive state");
        }else {
            rejectReasonWorkFlowMasterResponse = mapper.rejectReasonWorkflowMasterEntityToObject(rejectReasonWorkFlowMasterRepository.save(rejectReasonWorkFlowMaster), RejectReasonWorkFlowMasterResponse.class);
            rejectReasonWorkFlowMasterResponse.setError(false);
        }
        return rejectReasonWorkFlowMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRejectReasonWorkFlowMasterDetails(final Pageable pageable){
        return convertToMapResponse(rejectReasonWorkFlowMasterRepository.findByActiveOrderByReasonAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(rejectReasonWorkFlowMasterRepository.findByActiveOrderByReasonAsc(isActive));
    }


    private Map<String, Object> convertToMapResponse(final Page<RejectReasonWorkFlowMaster> activeRejectReasonWorkFlowMasters) {
        Map<String, Object> response = new HashMap<>();

        List<RejectReasonWorkFlowMasterResponse> rejectReasonWorkFlowMasterResponses = activeRejectReasonWorkFlowMasters.getContent().stream()
                .map(rejectReasonWorkFlowMaster -> mapper.rejectReasonWorkflowMasterEntityToObject(rejectReasonWorkFlowMaster,RejectReasonWorkFlowMasterResponse.class)).collect(Collectors.toList());
        response.put("rejectReasonWorkFlowMaster",rejectReasonWorkFlowMasterResponses);
        response.put("currentPage", activeRejectReasonWorkFlowMasters.getNumber());
        response.put("totalItems", activeRejectReasonWorkFlowMasters.getTotalElements());
        response.put("totalPages", activeRejectReasonWorkFlowMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RejectReasonWorkFlowMaster> activeRejectReasonWorkFlowMasters) {
        Map<String, Object> response = new HashMap<>();

        List<RejectReasonWorkFlowMasterResponse> rejectReasonWorkFlowMasterResponses = activeRejectReasonWorkFlowMasters.stream()
                .map(rejectReasonWorkFlowMaster -> mapper.rejectReasonWorkflowMasterEntityToObject(rejectReasonWorkFlowMaster,RejectReasonWorkFlowMasterResponse.class)).collect(Collectors.toList());
        response.put("rejectReasonWorkFlowMaster",rejectReasonWorkFlowMasterResponses);
        return response;
    }

    @Transactional
    public RejectReasonWorkFlowMasterResponse deleteRejectReasonWorkFlowMasterDetails(long rejectReasonWorkFlowMasterId) {
        RejectReasonWorkFlowMasterResponse rejectReasonWorkFlowMasterResponse = new RejectReasonWorkFlowMasterResponse();
        RejectReasonWorkFlowMaster rejectReasonWorkFlowMaster = rejectReasonWorkFlowMasterRepository.findByRejectReasonWorkFlowMasterIdAndActive(rejectReasonWorkFlowMasterId, true);
        if (Objects.nonNull(rejectReasonWorkFlowMaster)) {
            rejectReasonWorkFlowMaster.setActive(false);
            rejectReasonWorkFlowMasterResponse = mapper.rejectReasonWorkflowMasterEntityToObject(rejectReasonWorkFlowMasterRepository.save(rejectReasonWorkFlowMaster), RejectReasonWorkFlowMasterResponse.class);
            rejectReasonWorkFlowMasterResponse.setError(false);
        } else {
            rejectReasonWorkFlowMasterResponse.setError(true);
            rejectReasonWorkFlowMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return rejectReasonWorkFlowMasterResponse;
    }

    @Transactional
    public RejectReasonWorkFlowMasterResponse getById(long rejectReasonWorkFlowMasterId){
        RejectReasonWorkFlowMasterResponse rejectReasonWorkFlowMasterResponse = new RejectReasonWorkFlowMasterResponse();
        RejectReasonWorkFlowMaster rejectReasonWorkFlowMaster = rejectReasonWorkFlowMasterRepository.findByRejectReasonWorkFlowMasterIdAndActive(rejectReasonWorkFlowMasterId,true);
        if(rejectReasonWorkFlowMaster == null){
            rejectReasonWorkFlowMasterResponse.setError(true);
            rejectReasonWorkFlowMasterResponse.setError_description("Invalid id");
        }else{
            rejectReasonWorkFlowMasterResponse =  mapper.rejectReasonWorkflowMasterEntityToObject(rejectReasonWorkFlowMaster,RejectReasonWorkFlowMasterResponse.class);
            rejectReasonWorkFlowMasterResponse.setError(false);
        }
        log.info("Entity is ",rejectReasonWorkFlowMaster);
        return rejectReasonWorkFlowMasterResponse;
    }

    @Transactional
    public RejectReasonWorkFlowMasterResponse updateRejectReasonWorkFlowMasterDetails(EditRejectReasonWorkFlowMasterRequest rejectReasonWorkFlowMasterRequest) {
        RejectReasonWorkFlowMasterResponse rejectReasonWorkFlowMasterResponse = new RejectReasonWorkFlowMasterResponse();
        List<RejectReasonWorkFlowMaster> rejectReasonWorkFlowMasterList = rejectReasonWorkFlowMasterRepository.findByActiveAndReason(true,rejectReasonWorkFlowMasterRequest.getReason());
        if (rejectReasonWorkFlowMasterList.size() > 0) {
            rejectReasonWorkFlowMasterResponse.setError(true);
            rejectReasonWorkFlowMasterResponse.setError_description("RejectReasonWorkFlowMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            RejectReasonWorkFlowMaster rejectReasonWorkFlowMaster = rejectReasonWorkFlowMasterRepository.findByRejectReasonWorkFlowMasterIdAndActiveIn(rejectReasonWorkFlowMasterRequest.getRejectReasonWorkFlowMasterId(), Set.of(true, false));
            if (Objects.nonNull(rejectReasonWorkFlowMaster)) {
                rejectReasonWorkFlowMaster.setReason(rejectReasonWorkFlowMasterRequest.getReason());
                rejectReasonWorkFlowMaster.setActive(true);
                RejectReasonWorkFlowMaster rejectReasonWorkFlowMaster1 = rejectReasonWorkFlowMasterRepository.save(rejectReasonWorkFlowMaster);
                rejectReasonWorkFlowMasterResponse = mapper.rejectReasonWorkflowMasterEntityToObject(rejectReasonWorkFlowMaster1, RejectReasonWorkFlowMasterResponse.class);
                rejectReasonWorkFlowMasterResponse.setError(false);
            } else {
                rejectReasonWorkFlowMasterResponse.setError(true);
                rejectReasonWorkFlowMasterResponse.setError_description("Error occurred while fetching rejectReasonWorkFlowMaster");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return rejectReasonWorkFlowMasterResponse;
    }
}
