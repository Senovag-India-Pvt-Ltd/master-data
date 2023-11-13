package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.reasonLotRejectMaster.EditReasonLotRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonLotRejectMaster.ReasonLotRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonLotRejectMaster.ReasonLotRejectMasterResponse;
import com.sericulture.masterdata.model.api.relationship.RelationshipResponse;
import com.sericulture.masterdata.model.entity.ReasonLotRejectMaster;
import com.sericulture.masterdata.model.entity.Relationship;
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
        ReasonLotRejectMaster reasonLotRejectMaster = null;
        if(reasonLotRejectMaster==null){
            reasonLotRejectMaster = reasonLotRejectMasterRepository.findByReasonLotRejectNameAndActive(reasonLotRejectMasterName,true);
        }
        log.info("Entity is ",reasonLotRejectMaster);
        return mapper.reasonLotRejectEntityToObject(reasonLotRejectMaster,ReasonLotRejectMasterResponse.class);
    }

    @Transactional
    public ReasonLotRejectMasterResponse insertReasonLotRejectMasterDetails(ReasonLotRejectMasterRequest reasonLotRejectMasterRequest){
        ReasonLotRejectMaster reasonLotRejectMaster = mapper.reasonLotRejectObjectToEntity(reasonLotRejectMasterRequest,ReasonLotRejectMaster.class);
        validator.validate(reasonLotRejectMaster);
        List<ReasonLotRejectMaster> reasonLotRejectMasterList = reasonLotRejectMasterRepository.findByReasonLotRejectName(reasonLotRejectMasterRequest.getReasonLotRejectName());
        if(!reasonLotRejectMasterList.isEmpty() && reasonLotRejectMasterList.stream().filter(ReasonLotRejectMaster::getActive).findAny().isPresent()){
            throw new ValidationException("ReasonLotReject name already exist");
        }
        if(!reasonLotRejectMasterList.isEmpty() && reasonLotRejectMasterList.stream().filter(Predicate.not(ReasonLotRejectMaster::getActive)).findAny().isPresent()){
            throw new ValidationException("ReasonLotReject name already exist with inactive state");
        }
        return mapper.reasonLotRejectEntityToObject(reasonLotRejectMasterRepository.save(reasonLotRejectMaster),ReasonLotRejectMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedReasonLotRejectMasterDetails(final Pageable pageable){
        return convertToMapResponse(reasonLotRejectMasterRepository.findByActiveOrderByReasonLotRejectIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(reasonLotRejectMasterRepository.findByActive(isActive));
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
    public void deleteReasonLotRejectMasterDetails(long id) {
        ReasonLotRejectMaster reasonLotRejectMaster = reasonLotRejectMasterRepository.findByReasonLotRejectIdAndActive(id, true);
        if (Objects.nonNull(reasonLotRejectMaster)) {
            reasonLotRejectMaster.setActive(false);
            reasonLotRejectMasterRepository.save(reasonLotRejectMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public ReasonLotRejectMasterResponse getById(int id){
        ReasonLotRejectMaster reasonLotRejectMaster = reasonLotRejectMasterRepository.findByReasonLotRejectIdAndActive(id,true);
        if(reasonLotRejectMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",reasonLotRejectMaster);
        return mapper.reasonLotRejectEntityToObject(reasonLotRejectMaster,ReasonLotRejectMasterResponse.class);
    }

    @Transactional
    public ReasonLotRejectMasterResponse updateReasonLotRejectMasterDetails(EditReasonLotRejectMasterRequest reasonLotRejectMasterRequest){
        List<ReasonLotRejectMaster> reasonLotRejectMasterList = reasonLotRejectMasterRepository.findByReasonLotRejectName(reasonLotRejectMasterRequest.getReasonLotRejectName());
        if(reasonLotRejectMasterList.size()>0){
            throw new ValidationException("ReasonLotReject already exists with this name, duplicates are not allowed.");
        }

        ReasonLotRejectMaster reasonLotRejectMaster = reasonLotRejectMasterRepository.findByReasonLotRejectIdAndActiveIn(reasonLotRejectMasterRequest.getReasonLotRejectId(), Set.of(true,false));
        if(Objects.nonNull(reasonLotRejectMaster)){
            reasonLotRejectMaster.setReasonLotRejectName(reasonLotRejectMasterRequest.getReasonLotRejectName());
            reasonLotRejectMaster.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching reasonLotReject");
        }
        return mapper.reasonLotRejectEntityToObject(reasonLotRejectMasterRepository.save(reasonLotRejectMaster),ReasonLotRejectMasterResponse.class);
    }

}