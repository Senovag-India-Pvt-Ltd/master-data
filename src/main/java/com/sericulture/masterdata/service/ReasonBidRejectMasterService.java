package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.reasonBidRejectMaster.EditReasonBidRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonBidRejectMaster.ReasonBidRejectMasterRequest;
import com.sericulture.masterdata.model.api.reasonBidRejectMaster.ReasonBidRejectMasterResponse;
import com.sericulture.masterdata.model.entity.ReasonBidRejectMaster;
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
       ReasonBidRejectMaster reasonBidRejectMaster = null;
        if(reasonBidRejectMaster==null){
            reasonBidRejectMaster = reasonBidRejectMasterRepository.findByReasonBidRejectNameAndActive(reasonBidRejectMasterName,true);
        }
        log.info("Entity is ",reasonBidRejectMaster);
        return mapper.reasonBidRejectEntityToObject(reasonBidRejectMaster,ReasonBidRejectMasterResponse.class);
    }

    @Transactional
    public ReasonBidRejectMasterResponse insertReasonBidRejectMasterDetails(ReasonBidRejectMasterRequest reasonBidRejectMasterRequest){
        ReasonBidRejectMaster reasonBidRejectMaster = mapper.reasonBidRejectObjectToEntity(reasonBidRejectMasterRequest,ReasonBidRejectMaster.class);
        validator.validate(reasonBidRejectMaster);
        List<ReasonBidRejectMaster> reasonBidRejectMasterList = reasonBidRejectMasterRepository.findByReasonBidRejectName(reasonBidRejectMasterRequest.getReasonBidRejectName());
        if(!reasonBidRejectMasterList.isEmpty() && reasonBidRejectMasterList.stream().filter(ReasonBidRejectMaster::getActive).findAny().isPresent()){
            throw new ValidationException("ReasonBidReject name already exist");
        }
        if(!reasonBidRejectMasterList.isEmpty() && reasonBidRejectMasterList.stream().filter(Predicate.not(ReasonBidRejectMaster::getActive)).findAny().isPresent()){
            throw new ValidationException("ReasonBidReject name already exist with inactive state");
        }
        return mapper.reasonBidRejectEntityToObject(reasonBidRejectMasterRepository.save(reasonBidRejectMaster),ReasonBidRejectMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedReasonBidRejectMasterDetails(final Pageable pageable){
        return convertToMapResponse(reasonBidRejectMasterRepository.findByActiveOrderByReasonBidRejectIdAsc( true, pageable));
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

    @Transactional
    public void deleteReasonBidRejectMasterDetails(long id) {
        ReasonBidRejectMaster reasonBidRejectMaster = reasonBidRejectMasterRepository.findByReasonBidRejectIdAndActive(id, true);
        if (Objects.nonNull(reasonBidRejectMaster)) {
            reasonBidRejectMaster.setActive(false);
            reasonBidRejectMasterRepository.save(reasonBidRejectMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public ReasonBidRejectMasterResponse getById(int id){
        ReasonBidRejectMaster reasonBidRejectMaster = reasonBidRejectMasterRepository.findByReasonBidRejectIdAndActive(id,true);
        if(reasonBidRejectMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",reasonBidRejectMaster);
        return mapper.reasonBidRejectEntityToObject(reasonBidRejectMaster,ReasonBidRejectMasterResponse.class);
    }

    @Transactional
    public ReasonBidRejectMasterResponse updateReasonBidRejectMasterDetails(EditReasonBidRejectMasterRequest reasonBidRejectMasterRequest){
        List<ReasonBidRejectMaster> reasonBidRejectMasterList = reasonBidRejectMasterRepository.findByReasonBidRejectName(reasonBidRejectMasterRequest.getReasonBidRejectName());
        if(reasonBidRejectMasterList.size()>0){
            throw new ValidationException("ReasonBidReject already exists with this name, duplicates are not allowed.");
        }

        ReasonBidRejectMaster reasonBidRejectMaster = reasonBidRejectMasterRepository.findByReasonBidRejectIdAndActiveIn(reasonBidRejectMasterRequest.getReasonBidRejectId(), Set.of(true,false));
        if(Objects.nonNull(reasonBidRejectMaster)){
            reasonBidRejectMaster.setReasonBidRejectName(reasonBidRejectMasterRequest.getReasonBidRejectName());
            reasonBidRejectMaster.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching reasonLotReject");
        }
        return mapper.reasonBidRejectEntityToObject(reasonBidRejectMasterRepository.save(reasonBidRejectMaster),ReasonBidRejectMasterResponse.class);
    }


}
