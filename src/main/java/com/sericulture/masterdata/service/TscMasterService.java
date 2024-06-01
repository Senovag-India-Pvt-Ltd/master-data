package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.trProgramMaster.EditTrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterResponse;
import com.sericulture.masterdata.model.api.tscMaster.EditTscMasterRequest;
import com.sericulture.masterdata.model.api.tscMaster.TscMasterRequest;
import com.sericulture.masterdata.model.api.tscMaster.TscMasterResponse;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import com.sericulture.masterdata.model.entity.TscMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrProgramMasterRespository;
import com.sericulture.masterdata.repository.TscMasterRepository;
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
public class TscMasterService {

    @Autowired
    TscMasterRepository tscMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TscMasterResponse getTscMasterDetails(String name){
        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        TscMaster tscMaster= tscMasterRepository.findByNameAndActive(name,true);
        if(tscMaster==null){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("Program not found");
        }else{
            tscMasterResponse = mapper.tscMasterEntityToObject(tscMaster,TscMasterResponse.class);
            tscMasterResponse.setError(false);
        }
        log.info("Entity is ",tscMaster);
        return tscMasterResponse;
    }

    @Transactional
    public TscMasterResponse insertTscMasterDetails(TscMasterRequest tscMasterRequest){
        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        TscMaster tscMaster = mapper.tscMasterObjectToEntity(tscMasterRequest,TscMaster.class);
        validator.validate(tscMaster);
        List<TscMaster> tscMasterList = tscMasterRepository.findByActiveAndNameAndNameInKannada(true,tscMasterRequest.getName(), tscMasterRequest.getNameInKannada());
        if(!tscMasterList.isEmpty() && tscMasterList.stream().filter( TscMaster::getActive).findAny().isPresent()){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("TscMaster name already exist");
        }
        else if(! tscMasterList.isEmpty() && tscMasterList.stream().filter(Predicate.not( TscMaster::getActive)).findAny().isPresent()){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("TscMaster name already exist with inactive state");
        }else {
            tscMasterResponse  = mapper.tscMasterEntityToObject( tscMasterRepository.save(tscMaster), TscMasterResponse.class);
            tscMasterResponse.setError(false);
        }
        return tscMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTscMasterDetails(final Pageable pageable){
        return convertToMapResponse(tscMasterRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tscMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TscMaster> activeTscMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TscMasterResponse> tscMasterResponses= activeTscMasters.getContent().stream()
                .map(tscMaster -> mapper.tscMasterEntityToObject(tscMaster,TscMasterResponse.class)).collect(Collectors.toList());
        response.put("tscMaster",tscMasterResponses);
        response.put("currentPage", activeTscMasters.getNumber());
        response.put("totalItems", activeTscMasters.getTotalElements());
        response.put("totalPages", activeTscMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TscMaster> activeTscMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TscMasterResponse> tscMasterResponses= activeTscMasters.stream()
                .map(tscMaster -> mapper.tscMasterEntityToObject(tscMaster,TscMasterResponse.class)).collect(Collectors.toList());
        response.put("tscMaster",tscMasterResponses);
        return response;
    }

    @Transactional
    public TscMasterResponse deleteTscMasterDetails(long id) {

        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        TscMaster tscMaster= tscMasterRepository.findByTscMasterIdAndActive(id, true);
        if (Objects.nonNull(tscMaster)) {
            tscMaster.setActive(false);
            tscMasterResponse= mapper.tscMasterEntityToObject(tscMasterRepository.save(tscMaster), TscMasterResponse.class);
            tscMasterResponse.setError(false);
        } else {
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tscMasterResponse;
    }

    @Transactional
    public TscMasterResponse getById(int id){
        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        TscMaster tscMaster= tscMasterRepository.findByTscMasterIdAndActive(id, true);
        if(tscMaster== null){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("Invalid id");
        }else{
            tscMasterResponse =  mapper.tscMasterEntityToObject(tscMaster, TscMasterResponse.class);
            tscMasterResponse.setError(false);
        }
        log.info("Entity is ",tscMaster);
        return tscMasterResponse;
    }

    @Transactional
    public TscMasterResponse updateTscMastersDetails(EditTscMasterRequest tscMasterRequest){

        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        List<TscMaster> tscMasterList = tscMasterRepository. findByActiveAndNameAndNameInKannadaAndTscMasterIdIsNot(true,tscMasterRequest.getName(), tscMasterRequest.getNameInKannada(),tscMasterRequest.getTscMasterId());
        if(tscMasterList.size()>0){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("TscMaster exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TscMaster tscMaster = tscMasterRepository.findByTscMasterIdAndActiveIn(tscMasterRequest.getTscMasterId(), Set.of(true,false));
            if(Objects.nonNull(tscMaster)){
                tscMaster.setName( tscMasterRequest.getName());
                tscMaster.setNameInKannada( tscMasterRequest.getNameInKannada());
                tscMaster.setActive(true);
                TscMaster tscMaster1= tscMasterRepository.save(tscMaster);
                tscMasterResponse = mapper.tscMasterEntityToObject(tscMaster1, TscMasterResponse.class);
                tscMasterResponse.setError(false);
            } else {
                tscMasterResponse.setError(true);
                tscMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return tscMasterResponse;
    }
}
