package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.trCourseMaster.EditTrCourseMasterRequest;
import com.sericulture.masterdata.model.api.trCourseMaster.TrCourseMasterRequest;
import com.sericulture.masterdata.model.api.trCourseMaster.TrCourseMasterResponse;
import com.sericulture.masterdata.model.api.trProgramMaster.EditTrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterResponse;
import com.sericulture.masterdata.model.entity.TrCourseMaster;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrCourseMasterRepository;
import com.sericulture.masterdata.repository.TrProgramMasterRespository;
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
public class TrProgramMasterService {
    @Autowired
    TrProgramMasterRespository trProgramMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TrProgramMasterResponse getTrProgramMasterDetails(String trProgramMasterName){
        TrProgramMasterResponse trProgramMasterResponse = new TrProgramMasterResponse();
        TrProgramMaster trProgramMaster= null;
        if(trProgramMaster==null){
            trProgramMaster= trProgramMasterRepository.findByTrProgramMasterNameAndActive(trProgramMasterName,true);
            trProgramMasterResponse = mapper.trProgramMasterEntityToObject(trProgramMaster,TrProgramMasterResponse.class);
            trProgramMasterResponse.setError(false);
        }else{
            trProgramMasterResponse.setError(true);
            trProgramMasterResponse.setError_description("Program not found");
        }
        log.info("Entity is ",trProgramMaster);
        return trProgramMasterResponse;
    }

    @Transactional
    public TrProgramMasterResponse insertTrProgramMasterDetails(TrProgramMasterRequest trProgramMasterRequest){
        TrProgramMasterResponse trProgramMasterResponse = new TrProgramMasterResponse();
        TrProgramMaster trProgramMaster = mapper.trProgramMasterObjectToEntity(trProgramMasterRequest,TrProgramMaster.class);
        validator.validate(trProgramMaster);
        List<TrProgramMaster> trProgramMasterList = trProgramMasterRepository.findByTrProgramMasterName(trProgramMasterRequest.getTrProgramMasterName());
        if(!trProgramMasterList.isEmpty() && trProgramMasterList.stream().filter( TrProgramMaster::getActive).findAny().isPresent()){
            trProgramMasterResponse.setError(true);
            trProgramMasterResponse.setError_description("Tr Program name already exist");
        }
        else if(! trProgramMasterList.isEmpty() && trProgramMasterList.stream().filter(Predicate.not( TrProgramMaster::getActive)).findAny().isPresent()){
            trProgramMasterResponse.setError(true);
            trProgramMasterResponse.setError_description("Tr Program name already exist with inactive state");
        }else {
            trProgramMasterResponse  = mapper.trProgramMasterEntityToObject( trProgramMasterRepository.save(trProgramMaster), TrProgramMasterResponse.class);
            trProgramMasterResponse.setError(false);
        }
        return trProgramMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrProgramMasterDetails(final Pageable pageable){
        return convertToMapResponse(trProgramMasterRepository.findByActiveOrderByTrProgramMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trProgramMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrProgramMaster> activeTrProgramMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrProgramMasterResponse> trProgramMasterResponses= activeTrProgramMasters.getContent().stream()
                .map(trProgramMaster -> mapper.trProgramMasterEntityToObject(trProgramMaster,TrProgramMasterResponse.class)).collect(Collectors.toList());
        response.put("trProgramMaster",trProgramMasterResponses);
        response.put("currentPage", activeTrProgramMasters.getNumber());
        response.put("totalItems", activeTrProgramMasters.getTotalElements());
        response.put("totalPages", activeTrProgramMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrProgramMaster> activeTrProgramMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TrProgramMasterResponse> trProgramMasterResponses= activeTrProgramMasters.stream()
                .map(trProgramMaster -> mapper.trProgramMasterEntityToObject(trProgramMaster,TrProgramMasterResponse.class)).collect(Collectors.toList());
        response.put("trProgramMaster",trProgramMasterResponses);
        return response;
    }

    @Transactional
    public TrProgramMasterResponse deleteTrProgramMasterDetails(long id) {

        TrProgramMasterResponse trProgramMasterResponse = new TrProgramMasterResponse();
        TrProgramMaster trProgramMaster= trProgramMasterRepository.findByTrProgramMasterIdAndActive(id, true);
        if (Objects.nonNull(trProgramMaster)) {
            trProgramMaster.setActive(false);
            trProgramMasterResponse= mapper.trProgramMasterEntityToObject(trProgramMasterRepository.save(trProgramMaster), TrProgramMasterResponse.class);
            trProgramMasterResponse.setError(false);
        } else {
            trProgramMasterResponse.setError(true);
            trProgramMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trProgramMasterResponse;
    }

    @Transactional
    public TrProgramMasterResponse getById(int id){
        TrProgramMasterResponse trProgramMasterResponse = new TrProgramMasterResponse();
        TrProgramMaster trProgramMaster= trProgramMasterRepository.findByTrProgramMasterIdAndActive(id, true);
        if(trProgramMaster== null){
            trProgramMasterResponse.setError(true);
            trProgramMasterResponse.setError_description("Invalid id");
        }else{
            trProgramMasterResponse =  mapper.trProgramMasterEntityToObject(trProgramMaster, TrProgramMasterResponse.class);
            trProgramMasterResponse.setError(false);
        }
        log.info("Entity is ",trProgramMaster);
        return trProgramMasterResponse;
    }

    @Transactional
    public TrProgramMasterResponse updateTrProgramMastersDetails(EditTrProgramMasterRequest  trProgramMasterRequest){

        TrProgramMasterResponse trProgramMasterResponse = new TrProgramMasterResponse();
        List<TrProgramMaster> trProgramMasterList = trProgramMasterRepository. findByTrProgramMasterName(trProgramMasterRequest.getTrProgramMasterName());
        if(trProgramMasterList.size()>0){
            trProgramMasterResponse.setError(true);
            trProgramMasterResponse.setError_description("Program already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TrProgramMaster trProgramMaster = trProgramMasterRepository.findByTrProgramMasterIdAndActiveIn(trProgramMasterRequest.getTrProgramMasterId(), Set.of(true,false));
            if(Objects.nonNull(trProgramMaster)){
                trProgramMaster.setTrProgramMasterName( trProgramMasterRequest.getTrProgramMasterName());
                trProgramMaster.setActive(true);
                TrProgramMaster trProgramMaster1= trProgramMasterRepository.save(trProgramMaster);
                trProgramMasterResponse = mapper.trProgramMasterEntityToObject(trProgramMaster1, TrProgramMasterResponse.class);
                trProgramMasterResponse.setError(false);
            } else {
                trProgramMasterResponse.setError(true);
                trProgramMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return trProgramMasterResponse;
    }


}

