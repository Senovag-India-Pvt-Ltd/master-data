package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.tsActivityMaster.EditTsActivityMasterRequest;
import com.sericulture.masterdata.model.api.tsActivityMaster.TsActivityMasterRequest;
import com.sericulture.masterdata.model.api.tsActivityMaster.TsActivityMasterResponse;
import com.sericulture.masterdata.model.entity.TsActivityMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsActivityMasterRepository;
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
public class TsActivityMasterService {

    @Autowired
    TsActivityMasterRepository tsActivityMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TsActivityMasterResponse getTsActivityMasterDetails(String name){
        TsActivityMasterResponse tsActivityMasterResponse = new TsActivityMasterResponse();
        TsActivityMaster tsActivityMaster= tsActivityMasterRepository.findByNameAndActive(name,true);
        if(tsActivityMaster==null){
            tsActivityMasterResponse.setError(true);
            tsActivityMasterResponse.setError_description("Program not found");
        }else{
            tsActivityMasterResponse = mapper.tsActivityMasterEntityToObject(tsActivityMaster,TsActivityMasterResponse.class);
            tsActivityMasterResponse.setError(false);
        }
        log.info("Entity is ",tsActivityMaster);
        return tsActivityMasterResponse;
    }

    @Transactional
    public TsActivityMasterResponse insertTsActivityMasterDetails(TsActivityMasterRequest tsActivityMasterRequest){
        TsActivityMasterResponse tsActivityMasterResponse = new TsActivityMasterResponse();
        TsActivityMaster tsActivityMaster = mapper.tsActivityMasterObjectToEntity(tsActivityMasterRequest,TsActivityMaster.class);
        validator.validate(tsActivityMaster);
        List<TsActivityMaster> tsActivityMasterList = tsActivityMasterRepository.findByNameAndNameInKannada(tsActivityMasterRequest.getName(), tsActivityMasterRequest.getNameInKannada());
        if(!tsActivityMasterList.isEmpty() && tsActivityMasterList.stream().filter( TsActivityMaster::getActive).findAny().isPresent()){
            tsActivityMasterResponse.setError(true);
            tsActivityMasterResponse.setError_description("Activity name already exist");
//        }
//        else if(! tsActivityMasterList.isEmpty() && tsActivityMasterList.stream().filter(Predicate.not( TsActivityMaster::getActive)).findAny().isPresent()){
//            TsActivityMasterResponse.setError(true);
//            TsActivityMasterResponse.setError_description("Tr Program name already exist with inactive state");
        }else {
            tsActivityMasterResponse  = mapper.tsActivityMasterEntityToObject( tsActivityMasterRepository.save(tsActivityMaster), TsActivityMasterResponse.class);
            tsActivityMasterResponse.setError(false);
        }
        return tsActivityMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTsActivityMasterDetails(final Pageable pageable){
        return convertToMapResponse(tsActivityMasterRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsActivityMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsActivityMaster> activeTsActivityMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TsActivityMasterResponse> TsActivityMasterResponses= activeTsActivityMasters.getContent().stream()
                .map(tsActivityMaster -> mapper.tsActivityMasterEntityToObject(tsActivityMaster,TsActivityMasterResponse.class)).collect(Collectors.toList());
        response.put("tsActivityMaster",TsActivityMasterResponses);
        response.put("currentPage", activeTsActivityMasters.getNumber());
        response.put("totalItems", activeTsActivityMasters.getTotalElements());
        response.put("totalPages", activeTsActivityMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsActivityMaster> activeTsActivityMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TsActivityMasterResponse> TsActivityMasterResponses= activeTsActivityMasters.stream()
                .map(tsActivityMaster -> mapper.tsActivityMasterEntityToObject(tsActivityMaster,TsActivityMasterResponse.class)).collect(Collectors.toList());
        response.put("tsActivityMaster",TsActivityMasterResponses);
        return response;
    }

    @Transactional
    public TsActivityMasterResponse deleteTsActivityMasterDetails(long id) {

        TsActivityMasterResponse tsActivityMasterResponse = new TsActivityMasterResponse();
        TsActivityMaster tsActivityMaster= tsActivityMasterRepository.findByTsActivityMasterIdAndActive(id, true);
        if (Objects.nonNull(tsActivityMaster)) {
            tsActivityMaster.setActive(false);
            tsActivityMasterResponse= mapper.tsActivityMasterEntityToObject(tsActivityMasterRepository.save(tsActivityMaster), TsActivityMasterResponse.class);
            tsActivityMasterResponse.setError(false);
        } else {
            tsActivityMasterResponse.setError(true);
            tsActivityMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsActivityMasterResponse;
    }

    @Transactional
    public TsActivityMasterResponse getById(int id){
        TsActivityMasterResponse tsActivityMasterResponse = new TsActivityMasterResponse();
        TsActivityMaster tsActivityMaster= tsActivityMasterRepository.findByTsActivityMasterIdAndActive(id, true);
        if(tsActivityMaster== null){
            tsActivityMasterResponse.setError(true);
            tsActivityMasterResponse.setError_description("Invalid id");
        }else{
            tsActivityMasterResponse =  mapper.tsActivityMasterEntityToObject(tsActivityMaster, TsActivityMasterResponse.class);
            tsActivityMasterResponse.setError(false);
        }
        log.info("Entity is ",tsActivityMaster);
        return tsActivityMasterResponse;
    }

    @Transactional
    public TsActivityMasterResponse updateTsActivityMastersDetails(EditTsActivityMasterRequest tsActivityMasterRequest){

        TsActivityMasterResponse tsActivityMasterResponse = new TsActivityMasterResponse();
        List<TsActivityMaster> tsActivityMasterList = tsActivityMasterRepository. findByActiveAndNameAndNameInKannada(true,tsActivityMasterRequest.getName(), tsActivityMasterRequest.getNameInKannada());
        if(tsActivityMasterList.size()>0){
            tsActivityMasterResponse.setError(true);
            tsActivityMasterResponse.setError_description("Program already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TsActivityMaster tsActivityMaster = tsActivityMasterRepository.findByTsActivityMasterIdAndActiveIn(tsActivityMasterRequest.getTsActivityMasterId(), Set.of(true,false));
            if(Objects.nonNull(tsActivityMaster)){
                tsActivityMaster.setName( tsActivityMasterRequest.getName());
                tsActivityMaster.setNameInKannada( tsActivityMasterRequest.getNameInKannada());
                tsActivityMaster.setActive(true);
                TsActivityMaster tsActivityMaster1= tsActivityMasterRepository.save(tsActivityMaster);
                tsActivityMasterResponse = mapper.tsActivityMasterEntityToObject(tsActivityMaster1, TsActivityMasterResponse.class);
                tsActivityMasterResponse.setError(false);
            } else {
                tsActivityMasterResponse.setError(true);
                tsActivityMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return tsActivityMasterResponse;
    }

}
