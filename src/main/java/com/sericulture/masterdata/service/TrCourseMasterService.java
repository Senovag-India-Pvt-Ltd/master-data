package com.sericulture.masterdata.service;


import com.sericulture.masterdata.model.api.state.EditStateRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.trCourseMaster.EditTrCourseMasterRequest;
import com.sericulture.masterdata.model.api.trCourseMaster.TrCourseMasterRequest;
import com.sericulture.masterdata.model.api.trCourseMaster.TrCourseMasterResponse;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.TrCourseMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.StateRepository;
import com.sericulture.masterdata.repository.TrCourseMasterRepository;
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


public class TrCourseMasterService {

    @Autowired
    TrCourseMasterRepository trCourseMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public TrCourseMasterResponse insertTrCourseMasterDetails(TrCourseMasterRequest trCourseMasterRequest){
        TrCourseMasterResponse trCourseMasterResponse = new TrCourseMasterResponse();
        TrCourseMaster trCourseMaster = mapper.trCourseMasterObjectToEntity(trCourseMasterRequest,TrCourseMaster.class);
        validator.validate(trCourseMaster);
        List<TrCourseMaster> trCourseMasterList = trCourseMasterRepository.findByTrCourseMasterNameAndTrCourseNameInKannada(trCourseMasterRequest.getTrCourseMasterName(), trCourseMasterRequest.getTrCourseNameInKannada());
        if(!trCourseMasterList.isEmpty() && trCourseMasterList.stream().filter(TrCourseMaster::getActive).findAny().isPresent()){
            trCourseMasterResponse.setError(true);
            trCourseMasterResponse.setError_description("Tr course name already exist");
//        }
//        else if(!trCourseMasterList.isEmpty() && trCourseMasterList.stream().filter(Predicate.not(TrCourseMaster::getActive)).findAny().isPresent()){
//            trCourseMasterResponse.setError(true);
//            trCourseMasterResponse.setError_description("Tr Course name already exist with inactive state");
        }else {
            trCourseMasterResponse = mapper.trCourseMasterEntityToObject(trCourseMasterRepository.save(trCourseMaster), TrCourseMasterResponse.class);
            trCourseMasterResponse.setError(false);
        }
        return trCourseMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrCourseMasterDetails(final Pageable pageable){
        return convertToMapResponse(trCourseMasterRepository.findByActiveOrderByTrCourseMasterNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trCourseMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrCourseMaster> activeTrCourseMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrCourseMasterResponse> trCourseMasterResponses= activeTrCourseMasters.getContent().stream()
                .map(trCourseMaster -> mapper.trCourseMasterEntityToObject(trCourseMaster,TrCourseMasterResponse.class)).collect(Collectors.toList());
        response.put("trCourseMaster",trCourseMasterResponses);
        response.put("currentPage", activeTrCourseMasters.getNumber());
        response.put("totalItems", activeTrCourseMasters.getTotalElements());
        response.put("totalPages", activeTrCourseMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrCourseMaster> activeTrCourseMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrCourseMasterResponse> trCourseMasterResponses= activeTrCourseMasters.stream()
                .map(trCourseMaster -> mapper.trCourseMasterEntityToObject(trCourseMaster,TrCourseMasterResponse.class)).collect(Collectors.toList());
        response.put("trCourseMaster",trCourseMasterResponses);
        return response;
    }

    @Transactional
    public TrCourseMasterResponse deleteTrCourseMasterDetails(long id) {

        TrCourseMasterResponse trCourseMasterResponse = new TrCourseMasterResponse();
        TrCourseMaster trCourseMaster = trCourseMasterRepository.findByTrCourseMasterIdAndActive(id, true);
        if (Objects.nonNull(trCourseMaster)) {
            trCourseMaster.setActive(false);
            trCourseMasterResponse= mapper.trCourseMasterEntityToObject(trCourseMasterRepository.save(trCourseMaster), TrCourseMasterResponse.class);
            trCourseMasterResponse.setError(false);
        } else {
            trCourseMasterResponse.setError(true);
            trCourseMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trCourseMasterResponse;
    }

    @Transactional
    public TrCourseMasterResponse getById(int id){
        TrCourseMasterResponse trCourseMasterResponse = new TrCourseMasterResponse();
        TrCourseMaster trCourseMaster= trCourseMasterRepository.findByTrCourseMasterIdAndActive(id,true);
        if(trCourseMaster== null){
            trCourseMasterResponse.setError(true);
            trCourseMasterResponse.setError_description("Invalid id");
        }else{
            trCourseMasterResponse =  mapper.trCourseMasterEntityToObject(trCourseMaster,TrCourseMasterResponse.class);
            trCourseMasterResponse.setError(false);
        }
        log.info("Entity is ",trCourseMaster);
        return trCourseMasterResponse;
    }

    @Transactional
    public TrCourseMasterResponse updateTrCourseMastersDetails(EditTrCourseMasterRequest trCourseMasterRequest){

        TrCourseMasterResponse trCourseMasterResponse = new TrCourseMasterResponse();
        List<TrCourseMaster> trCourseMasterList = trCourseMasterRepository.findByActiveAndTrCourseMasterNameAndTrCourseNameInKannada(true,trCourseMasterRequest.getTrCourseMasterName(), trCourseMasterRequest.getTrCourseNameInKannada());
        if(trCourseMasterList.size()>0){
            trCourseMasterResponse.setError(true);
            trCourseMasterResponse.setError_description(" Training Course already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TrCourseMaster trCourseMaster= trCourseMasterRepository.findByTrCourseMasterIdAndActiveIn(trCourseMasterRequest.getTrCourseMasterId(), Set.of(true,false));
            if(Objects.nonNull(trCourseMaster)){
                trCourseMaster.setTrCourseMasterName(trCourseMasterRequest.getTrCourseMasterName());
                trCourseMaster.setTrCourseNameInKannada(trCourseMasterRequest.getTrCourseNameInKannada());
                trCourseMaster.setActive(true);
                TrCourseMaster trCourseMaster1 = trCourseMasterRepository.save(trCourseMaster);
                trCourseMasterResponse = mapper.trCourseMasterEntityToObject(trCourseMaster1, TrCourseMasterResponse.class);
                trCourseMasterResponse.setError(false);
            } else {
                trCourseMasterResponse.setError(true);
                trCourseMasterResponse.setError_description("Error occurred while fetching Training course");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return trCourseMasterResponse;
    }


}


