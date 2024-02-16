package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.state.EditStateRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.trInstitutionMaster.EditTrInstitutionMasterRequest;
import com.sericulture.masterdata.model.api.trInstitutionMaster.TrInstitutionMasterRequest;
import com.sericulture.masterdata.model.api.trInstitutionMaster.TrInstitutionMasterResponse;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.TrInstitutionMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.StateRepository;
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
public class TrInstitutionMasterService {
    @Autowired
    TrInstitutionMasterRepository trInstitutionMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TrInstitutionMasterResponse getTrInstitutionMasterDetails(String trInstitutionMasterName){
        TrInstitutionMasterResponse trInstitutionMasterResponse = new TrInstitutionMasterResponse();
        TrInstitutionMaster trInstitutionMaster = trInstitutionMasterRepository.findByTrInstitutionMasterNameAndActive(trInstitutionMasterName, true);
        if(trInstitutionMaster==null){
            trInstitutionMasterResponse.setError(true);
            trInstitutionMasterResponse.setError_description("Training Institution not found");
        }else{
            trInstitutionMasterResponse = mapper.trInstitutionMasterEntityToObject(trInstitutionMaster, TrInstitutionMasterResponse.class);
            trInstitutionMasterResponse.setError(false);
        }
        log.info("Entity is ",trInstitutionMaster);
        return trInstitutionMasterResponse;

    }

    @Transactional
    public TrInstitutionMasterResponse insertTrInstitutionMasterDetails(TrInstitutionMasterRequest trInstitutionMasterRequest){
        TrInstitutionMasterResponse trInstitutionMasterResponse = new TrInstitutionMasterResponse();
        TrInstitutionMaster trInstitutionMaster = mapper.trInstitutionMasterObjectToEntity(trInstitutionMasterRequest,TrInstitutionMaster.class);
        validator.validate(trInstitutionMaster);
        List<TrInstitutionMaster> trInstitutionMasterList = trInstitutionMasterRepository.findByTrInstitutionMasterNameAndTrInstitutionNameInKannada(trInstitutionMasterRequest.getTrInstitutionMasterName(), trInstitutionMasterRequest.getTrInstitutionNameInKannada());
        if(!trInstitutionMasterList.isEmpty() && trInstitutionMasterList.stream().filter(TrInstitutionMaster::getActive).findAny().isPresent()){
            trInstitutionMasterResponse.setError(true);
            trInstitutionMasterResponse.setError_description("TrInstitutionMaster name already exist");
        }
        else if(!trInstitutionMasterList.isEmpty() && trInstitutionMasterList.stream().filter(Predicate.not(TrInstitutionMaster::getActive)).findAny().isPresent()){
            trInstitutionMasterResponse.setError(true);
            trInstitutionMasterResponse.setError_description("trInstitutionMaster name already exist with inactive state");
        }else {
            trInstitutionMasterResponse = mapper.trInstitutionMasterEntityToObject(trInstitutionMasterRepository.save(trInstitutionMaster), TrInstitutionMasterResponse.class);
            trInstitutionMasterResponse.setError(false);
        }
        return trInstitutionMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrInstitutionMasterDetails(final Pageable pageable){
        return convertToMapResponse(trInstitutionMasterRepository.findByActiveOrderByTrInstitutionMasterNameAsc( true,pageable ));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trInstitutionMasterRepository.findByActiveOrderByTrInstitutionMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrInstitutionMaster> activeTrInstitutionMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrInstitutionMasterResponse> trInstitutionMasterResponses = activeTrInstitutionMasters.getContent().stream()
                .map(trInstitutionMaster -> mapper.trInstitutionMasterEntityToObject(trInstitutionMaster,TrInstitutionMasterResponse.class)).collect(Collectors.toList());
        response.put("trInstitutionMaster",trInstitutionMasterResponses);
        response.put("currentPage", activeTrInstitutionMasters.getNumber());
        response.put("totalItems", activeTrInstitutionMasters.getTotalElements());
        response.put("totalPages", activeTrInstitutionMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrInstitutionMaster> activeTrInstitutionMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrInstitutionMasterResponse> trInstitutionMasterResponses = activeTrInstitutionMasters.stream()
                .map(trInstitutionMaster -> mapper.trInstitutionMasterEntityToObject(trInstitutionMaster,TrInstitutionMasterResponse.class)).collect(Collectors.toList());
        response.put("trInstitutionMaster",trInstitutionMasterResponses);
        return response;
    }

    @Transactional
    public TrInstitutionMasterResponse deleteTrInstitutionMasterDetails(long id) {

        TrInstitutionMasterResponse trInstitutionMasterResponse= new TrInstitutionMasterResponse();
        TrInstitutionMaster trInstitutionMaster = trInstitutionMasterRepository.findByTrInstitutionMasterIdAndActive(id, true);
        if (Objects.nonNull(trInstitutionMaster)) {
            trInstitutionMaster.setActive(false);
            trInstitutionMasterResponse = mapper.trInstitutionMasterEntityToObject(trInstitutionMasterRepository.save(trInstitutionMaster), TrInstitutionMasterResponse.class);
            trInstitutionMasterResponse.setError(false);
        } else {
           trInstitutionMasterResponse.setError(true);
            trInstitutionMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trInstitutionMasterResponse;
    }

    @Transactional
    public TrInstitutionMasterResponse getById(int id){
        TrInstitutionMasterResponse trInstitutionMasterResponse = new TrInstitutionMasterResponse();
        TrInstitutionMaster trInstitutionMaster = trInstitutionMasterRepository.findByTrInstitutionMasterIdAndActive(id,true);
        if(trInstitutionMaster == null){
            trInstitutionMasterResponse.setError(true);
            trInstitutionMasterResponse.setError_description("Invalid id");
        }else{
            trInstitutionMasterResponse =  mapper.trInstitutionMasterEntityToObject(trInstitutionMaster,TrInstitutionMasterResponse.class);
            trInstitutionMasterResponse.setError(false);
        }
        log.info("Entity is ",trInstitutionMaster);
        return trInstitutionMasterResponse;
    }

    @Transactional
    public TrInstitutionMasterResponse updateTrInstitutionMasterDetails(EditTrInstitutionMasterRequest trInstitutionMasterRequest){

        TrInstitutionMasterResponse trInstitutionMasterResponse = new TrInstitutionMasterResponse();
        List<TrInstitutionMaster> trInstitutionMasterList = trInstitutionMasterRepository.findByTrInstitutionMasterNameAndTrInstitutionNameInKannadaAndTrInstitutionMasterIdIsNot(trInstitutionMasterRequest.getTrInstitutionMasterName(), trInstitutionMasterRequest.getTrInstitutionNameInKannada(),trInstitutionMasterRequest.getTrInstitutionMasterId());
        if(trInstitutionMasterList.size()>0){
            trInstitutionMasterResponse.setError(true);
            trInstitutionMasterResponse.setError_description("TrInstitutionMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TrInstitutionMaster trInstitutionMaster= trInstitutionMasterRepository.findByTrInstitutionMasterIdAndActiveIn(trInstitutionMasterRequest.getTrInstitutionMasterId(), Set.of(true,false));
            if(Objects.nonNull(trInstitutionMaster)){
                trInstitutionMaster.setTrInstitutionMasterName(trInstitutionMasterRequest.getTrInstitutionMasterName());
                trInstitutionMaster.setTrInstitutionNameInKannada(trInstitutionMasterRequest.getTrInstitutionNameInKannada());
                trInstitutionMaster.setActive(true);
                TrInstitutionMaster trInstitutionMaster1 = trInstitutionMasterRepository.save(trInstitutionMaster);
                trInstitutionMasterResponse = mapper.trInstitutionMasterEntityToObject(trInstitutionMaster1, TrInstitutionMasterResponse.class);
                trInstitutionMasterResponse.setError(false);
            } else {
                trInstitutionMasterResponse.setError(true);
                trInstitutionMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return trInstitutionMasterResponse;
    }
}
