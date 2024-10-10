package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.reasonMaster.EditReasonRequest;
import com.sericulture.masterdata.model.api.reasonMaster.ReasonRequest;
import com.sericulture.masterdata.model.api.reasonMaster.ReasonResponse;
import com.sericulture.masterdata.model.entity.Reason;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ReasonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReasonService {
    @Autowired
    ReasonRepository reasonRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;



    @Transactional
    public ReasonResponse insertReasonDetails(ReasonRequest reasonRequest){
        ReasonResponse reasonResponse = new ReasonResponse();
        Reason reason = mapper.reasonObjectToEntity(reasonRequest, Reason.class);
        validator.validate(reason);
        List<Reason> reasonList = reasonRepository.findByName(reasonRequest.getName());
        if(!reasonList.isEmpty() && reasonList.stream(). filter(Reason::getActive).findAny().isPresent()){
            reasonResponse.setError(true);
            reasonResponse.setError_description("Reeler Type name already exist");
//        }
//        else if(!reasonList.isEmpty() && reasonList.stream().filter(Predicate.not(Reason::getActive)).findAny().isPresent()){
//            reasonResponse.setError(true);
//            reasonResponse.setError_description("Reeler Type name already exist with inactive state");
        }else {
            reasonResponse = mapper.reasonEntityToObject(reasonRepository.save(reason), ReasonResponse.class);
            reasonResponse.setError(false);
        }
        return reasonResponse;
    }

    public Map<String,Object> getPaginatedReasonDetails(final Pageable pageable){
        return convertToMapResponse(reasonRepository.findByActiveOrderByNameAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(reasonRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Reason> activeReasons) {
        Map<String, Object> response = new HashMap<>();

        List<ReasonResponse> reasonResponses= activeReasons.getContent().stream()
                .map(reason -> mapper.reasonEntityToObject(reason, ReasonResponse.class)).collect(Collectors.toList());
        response.put("reason",reasonResponses);
        response.put("currentPage", activeReasons.getNumber());
        response.put("totalItems", activeReasons.getTotalElements());
        response.put("totalPages", activeReasons.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Reason> activeReasons) {
        Map<String, Object> response = new HashMap<>();

        List<ReasonResponse> reasonResponses= activeReasons.stream()
                .map(reason -> mapper.reasonEntityToObject(reason, ReasonResponse.class)).collect(Collectors.toList());
        response.put("reason",reasonResponses);
        return response;
    }

    @Transactional
    public ReasonResponse deleteReasonDetails(long id) {

        ReasonResponse reasonResponse  = new ReasonResponse();
        Reason reason  = reasonRepository.findByReasonIdAndActive(id, true);
        if (Objects.nonNull(reason)) {
            reason.setActive(false);
            reasonResponse= mapper.reasonEntityToObject(reasonRepository.save(reason), ReasonResponse.class);
            reasonResponse.setError(false);
        } else {
            reasonResponse.setError(true);
            reasonResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return reasonResponse;
    }

    public ReasonResponse getById(int id){
        ReasonResponse reasonResponse  = new ReasonResponse();
        Reason reason= reasonRepository. findByReasonIdAndActive(id, true);
        if(reason== null){
            reasonResponse.setError(true);
            reasonResponse.setError_description("Invalid id");
        }else{
            reasonResponse =  mapper.reasonEntityToObject(reason, ReasonResponse.class);
            reasonResponse.setError(false);
        }
        log.info("Entity is ",reason);
        return reasonResponse;
    }

    @Transactional
    public ReasonResponse updateReasonsDetails(EditReasonRequest reasonRequest){

        ReasonResponse reasonResponse  = new ReasonResponse();
        List<Reason> reasonList = reasonRepository. findByActiveAndName(true,reasonRequest.getName());
        if(reasonList.size()>0){
            reasonResponse.setError(true);
            reasonResponse.setError_description("Reeler Type , duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            Reason reason = reasonRepository.findByReasonIdAndActiveIn(reasonRequest.getReasonId(), Set.of(true,false));
            if(Objects.nonNull(reason)){
                reason.setName(reasonRequest.getName());

                reason.setActive(true);
                Reason releerTypeMaster1= reasonRepository.save(reason);
                reasonResponse = mapper.reasonEntityToObject(releerTypeMaster1, ReasonResponse.class);
                reasonResponse.setError(false);
            } else {
                reasonResponse.setError(true);
                reasonResponse.setError_description("Error occurred while fetching reeler Type");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return reasonResponse;
    }
}
