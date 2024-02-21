package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.trGroupMaster.EditTrGroupMasterRequest;
import com.sericulture.masterdata.model.api.trGroupMaster.TrGroupMasterRequest;
import com.sericulture.masterdata.model.api.trGroupMaster.TrGroupMasterResponse;
import com.sericulture.masterdata.model.api.trInstitutionMaster.EditTrInstitutionMasterRequest;
import com.sericulture.masterdata.model.api.trInstitutionMaster.TrInstitutionMasterRequest;
import com.sericulture.masterdata.model.api.trInstitutionMaster.TrInstitutionMasterResponse;
import com.sericulture.masterdata.model.entity.TrGroupMaster;
import com.sericulture.masterdata.model.entity.TrInstitutionMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrGroupMasterRepository;
import com.sericulture.masterdata.repository.TrInstitutionMasterRepository;
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

public class TrGroupMasterService {
    @Autowired
    TrGroupMasterRepository trGroupMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TrGroupMasterResponse getTrGroupMasterDetails(String trGroupMasterName){
        TrGroupMasterResponse trGroupMasterResponse = new TrGroupMasterResponse();
        TrGroupMaster trGroupMaster =  trGroupMasterRepository.findByTrGroupMasterNameAndActive(trGroupMasterName, true);
        if(trGroupMaster==null){
            trGroupMasterResponse.setError(true);
            trGroupMasterResponse.setError_description("Training Group is not found");
        }else{
            trGroupMasterResponse = mapper.trGroupMasterEntityToObject(trGroupMaster, TrGroupMasterResponse.class);
            trGroupMasterResponse.setError(false);
        }
        log.info("Entity is ",trGroupMaster);
        return trGroupMasterResponse;

    }

    @Transactional
    public TrGroupMasterResponse insertTrGroupMasterDetails(TrGroupMasterRequest trGroupMasterRequest){
        TrGroupMasterResponse trGroupMasterResponse = new TrGroupMasterResponse();
        TrGroupMaster trGroupMaster = mapper.trGroupMasterObjectToEntity(trGroupMasterRequest,TrGroupMaster.class);
        validator.validate(trGroupMaster);
        List<TrGroupMaster> trGroupMasterList= trGroupMasterRepository.findByTrGroupMasterNameAndTrGroupNameInKannada(trGroupMasterRequest.getTrGroupMasterName(), trGroupMasterRequest.getTrGroupNameInKannada());
        if(!trGroupMasterList.isEmpty() && trGroupMasterList.stream().filter(TrGroupMaster::getActive).findAny().isPresent()){
            trGroupMasterResponse.setError(true);
            trGroupMasterResponse.setError_description("TrGroupMaster name already exist");
//        }
//        else if(!trGroupMasterList.isEmpty() && trGroupMasterList.stream().filter(Predicate.not(TrGroupMaster::getActive)).findAny().isPresent()){
//            trGroupMasterResponse.setError(true);
//            trGroupMasterResponse.setError_description("trGroupMaster name already exist with inactive state");
        }else {
            trGroupMasterResponse = mapper.trGroupMasterEntityToObject(trGroupMasterRepository.save(trGroupMaster), TrGroupMasterResponse.class);
            trGroupMasterResponse.setError(false);
        }
        return trGroupMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrGroupMasterDetails(final Pageable pageable){
        return convertToMapResponse(trGroupMasterRepository.findByActiveOrderByTrGroupMasterNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trGroupMasterRepository.findByActiveOrderByTrGroupMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrGroupMaster> activeTrGroupMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrGroupMasterResponse> trGroupMasterResponses = activeTrGroupMasters.getContent().stream()
                .map(trGroupMaster -> mapper.trGroupMasterEntityToObject(trGroupMaster,TrGroupMasterResponse.class)).collect(Collectors.toList());
        response.put("trGroupMaster",trGroupMasterResponses);
        response.put("currentPage", activeTrGroupMasters.getNumber());
        response.put("totalItems", activeTrGroupMasters.getTotalElements());
        response.put("totalPages", activeTrGroupMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrGroupMaster> activeTrGroupMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrGroupMasterResponse> trGroupMasterResponses = activeTrGroupMasters.stream()
                .map(trGroupMaster -> mapper.trGroupMasterEntityToObject(trGroupMaster,TrGroupMasterResponse.class)).collect(Collectors.toList());
        response.put("trGroupMaster",trGroupMasterResponses);
        return response;
    }

    @Transactional
    public TrGroupMasterResponse deleteTrGroupMasterDetails(long id) {

        TrGroupMasterResponse trGroupMasterResponse= new TrGroupMasterResponse();
        TrGroupMaster trGroupMaster = trGroupMasterRepository.findByTrGroupMasterIdAndActive(id, true);
        if (Objects.nonNull(trGroupMaster)) {
            trGroupMaster.setActive(false);
            trGroupMasterResponse = mapper.trGroupMasterEntityToObject(trGroupMasterRepository.save(trGroupMaster), TrGroupMasterResponse.class);
            trGroupMasterResponse.setError(false);
        } else {
            trGroupMasterResponse.setError(true);
            trGroupMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trGroupMasterResponse;
    }

    @Transactional
    public TrGroupMasterResponse getById(int id){
        TrGroupMasterResponse trGroupMasterResponse = new TrGroupMasterResponse();
        TrGroupMaster trGroupMaster = trGroupMasterRepository.findByTrGroupMasterIdAndActive(id,true);
        if(trGroupMaster == null){
            trGroupMasterResponse.setError(true);
            trGroupMasterResponse.setError_description("Invalid id");
        }else{
            trGroupMasterResponse =  mapper.trGroupMasterEntityToObject(trGroupMaster,TrGroupMasterResponse.class);
            trGroupMasterResponse.setError(false);
        }
        log.info("Entity is ",trGroupMaster);
        return trGroupMasterResponse;
    }

    @Transactional
    public TrGroupMasterResponse updateTrGroupMasterDetails(EditTrGroupMasterRequest trGroupMasterRequest){

        TrGroupMasterResponse trGroupMasterResponse = new TrGroupMasterResponse();
        List<TrGroupMaster> trGroupMasterList = trGroupMasterRepository.findByActiveAndTrGroupMasterNameAndTrGroupNameInKannada(true,trGroupMasterRequest.getTrGroupMasterName(), trGroupMasterRequest.getTrGroupNameInKannada());
        if(trGroupMasterList.size()>0){
            trGroupMasterResponse.setError(true);
            trGroupMasterResponse.setError_description("TrGroupMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TrGroupMaster trGroupMaster= trGroupMasterRepository.findByTrGroupMasterIdAndActiveIn(trGroupMasterRequest.getTrGroupMasterId(), Set.of(true,false));
            if(Objects.nonNull(trGroupMaster)){
                trGroupMaster.setTrGroupMasterName(trGroupMasterRequest.getTrGroupMasterName());
                trGroupMaster.setTrGroupNameInKannada(trGroupMasterRequest.getTrGroupNameInKannada());
                trGroupMaster.setActive(true);
                TrGroupMaster trGroupMaster1 = trGroupMasterRepository.save(trGroupMaster);
                trGroupMasterResponse = mapper.trGroupMasterEntityToObject(trGroupMaster1, TrGroupMasterResponse.class);
                trGroupMasterResponse.setError(false);
            } else {
                trGroupMasterResponse.setError(true);
                trGroupMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return trGroupMasterResponse;
    }
}
