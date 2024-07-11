package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.trGroupMaster.EditTrGroupMasterRequest;
import com.sericulture.masterdata.model.api.trGroupMaster.TrGroupMasterRequest;
import com.sericulture.masterdata.model.api.trGroupMaster.TrGroupMasterResponse;
import com.sericulture.masterdata.model.api.trModeMaster.EditTrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterResponse;
import com.sericulture.masterdata.model.entity.TrGroupMaster;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrGroupMasterRepository;
import com.sericulture.masterdata.repository.TrModeMasterRepository;
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
public class TrModeMasterService {

    @Autowired
    TrModeMasterRepository trModeMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public TrModeMasterResponse insertTrModeMasterDetails(TrModeMasterRequest trModeMasterRequest){
        TrModeMasterResponse trModeMasterResponse = new TrModeMasterResponse();
        TrModeMaster trModeMaster = mapper.trModeMasterObjectToEntity(trModeMasterRequest,TrModeMaster.class);
        validator.validate(trModeMaster);
        List<TrModeMaster> trModeMasterList= trModeMasterRepository.findByTrModeMasterName(trModeMasterRequest.getTrModeMasterName());
        if(!trModeMasterList.isEmpty() && trModeMasterList.stream().filter(TrModeMaster::getActive).findAny().isPresent()){
            trModeMasterResponse.setError(true);
            trModeMasterResponse.setError_description("TrModeMaster name already exist");
//        }
//        else if(!trModeMasterList.isEmpty() && trModeMasterList.stream().filter(Predicate.not(TrModeMaster::getActive)).findAny().isPresent()){
//            trModeMasterResponse.setError(true);
//            trModeMasterResponse.setError_description("trModeMaster name already exist with inactive state");
        }else {
            trModeMasterResponse = mapper.trModeMasterEntityToObject(trModeMasterRepository.save(trModeMaster), TrModeMasterResponse.class);
            trModeMasterResponse.setError(false);
        }
        return trModeMasterResponse;
    }

    public Map<String,Object> getPaginatedTrModeMasterDetails(final Pageable pageable){
        return convertToMapResponse(trModeMasterRepository.findByActiveOrderByTrModeMasterNameAsc( true,pageable ));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trModeMasterRepository.findByActiveOrderByTrModeMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrModeMaster> activeTrModeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrModeMasterResponse> trModeMasterResponses = activeTrModeMasters.getContent().stream()
                .map(trModeMaster -> mapper.trModeMasterEntityToObject(trModeMaster,TrModeMasterResponse.class)).collect(Collectors.toList());
        response.put("trModeMaster",trModeMasterResponses);
        response.put("currentPage", activeTrModeMasters.getNumber());
        response.put("totalItems", activeTrModeMasters.getTotalElements());
        response.put("totalPages", activeTrModeMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrModeMaster> activeTrModeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrModeMasterResponse> trModeMasterResponses = activeTrModeMasters.stream()
                .map(trModeMaster -> mapper.trModeMasterEntityToObject(trModeMaster,TrModeMasterResponse.class)).collect(Collectors.toList());
        response.put("trModeMaster",trModeMasterResponses);
        return response;
    }

    @Transactional
    public TrModeMasterResponse deleteTrModeMasterDetails(long id) {

        TrModeMasterResponse trModeMasterResponse= new TrModeMasterResponse();
        TrModeMaster trModeMaster = trModeMasterRepository.findByTrModeMasterIdAndActive(id, true);
        if (Objects.nonNull(trModeMaster)) {
            trModeMaster.setActive(false);
            trModeMasterResponse = mapper.trModeMasterEntityToObject(trModeMasterRepository.save(trModeMaster), TrModeMasterResponse.class);
            trModeMasterResponse.setError(false);
        } else {
            trModeMasterResponse.setError(true);
            trModeMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trModeMasterResponse;
    }

    public TrModeMasterResponse getById(int id){
        TrModeMasterResponse trModeMasterResponse = new TrModeMasterResponse();
        TrModeMaster trModeMaster = trModeMasterRepository.findByTrModeMasterIdAndActive(id,true);
        if(trModeMaster == null){
            trModeMasterResponse.setError(true);
            trModeMasterResponse.setError_description("Invalid id");
        }else{
            trModeMasterResponse =  mapper.trModeMasterEntityToObject(trModeMaster,TrModeMasterResponse.class);
            trModeMasterResponse.setError(false);
        }
        log.info("Entity is ",trModeMaster);
        return trModeMasterResponse;
    }

    @Transactional
    public TrModeMasterResponse updateTrModeMasterDetails(EditTrModeMasterRequest trModeMasterRequest){

        TrModeMasterResponse trModeMasterResponse = new TrModeMasterResponse();
        List<TrModeMaster> trModeMasterList = trModeMasterRepository.findByActiveAndTrModeMasterName(true,trModeMasterRequest.getTrModeMasterName());
        if(trModeMasterList.size()>0){
            trModeMasterResponse.setError(true);
            trModeMasterResponse.setError_description("TrModeMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TrModeMaster trModeMaster= trModeMasterRepository.findByTrModeMasterIdAndActiveIn(trModeMasterRequest.getTrModeMasterId(), Set.of(true,false));
            if(Objects.nonNull(trModeMaster)){
                trModeMaster.setTrModeMasterName(trModeMasterRequest.getTrModeMasterName());
                trModeMaster.setActive(true);
                TrModeMaster trModeMaster1 = trModeMasterRepository.save(trModeMaster);
                trModeMasterResponse = mapper.trModeMasterEntityToObject(trModeMaster1, TrModeMasterResponse.class);
                trModeMasterResponse.setError(false);
            } else {
                trModeMasterResponse.setError(true);
                trModeMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return trModeMasterResponse;
    }
}
