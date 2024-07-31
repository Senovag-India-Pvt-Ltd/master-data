package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.workingInstitution.EditWorkingInstitutionRequest;
import com.sericulture.masterdata.model.api.workingInstitution.WorkingInstitutionResponse;
import com.sericulture.masterdata.model.api.wormStageMaster.EditWormStageMasterRequest;
import com.sericulture.masterdata.model.api.wormStageMaster.WormStageMasterRequest;
import com.sericulture.masterdata.model.api.wormStageMaster.WormStageMasterResponse;
import com.sericulture.masterdata.model.entity.WorkingInstitution;
import com.sericulture.masterdata.model.entity.WormStageMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.WormStageMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WormStageMasterService {
    @Autowired
    WormStageMasterRepository wormStageMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public WormStageMasterResponse insertWormStageMasterDetails(WormStageMasterRequest wormStageMasterRequest){
        WormStageMasterResponse wormStageMasterResponse = new WormStageMasterResponse();
        WormStageMaster wormStageMaster = mapper.wormStageMasterObjectToEntity(wormStageMasterRequest,WormStageMaster.class);
        validator.validate(wormStageMaster);
        List<WormStageMaster> wormStageMasterList = wormStageMasterRepository.findByActiveAndWormStageMasterNameAndWormStageMasterNameInKannada(true,wormStageMasterRequest.getWormStageMasterName(),wormStageMasterRequest.getWormStageMasterNameInKannada());
        if(!wormStageMasterList.isEmpty() && wormStageMasterList.stream().filter(WormStageMaster::getActive).findAny().isPresent()){
            wormStageMasterResponse.setError(true);
            wormStageMasterResponse.setError_description("Worm  Stage name already exist");
//        }
//        else if(!workingInstitutionList.isEmpty() && workingInstitutionList.stream().filter(Predicate.not(WorkingInstitution::getActive)).findAny().isPresent()){
//            workingInstitutionResponse.setError(true);
//            workingInstitutionResponse.setError_description("Working Institution name already exist with inactive state");
        }else {
            wormStageMasterResponse = mapper.wormStageMasterEntityToObject(wormStageMasterRepository.save(wormStageMaster), WormStageMasterResponse.class);
            wormStageMasterResponse.setError(false);
        }
        return wormStageMasterResponse;
    }

    public Map<String,Object> getPaginatedWormStageMasterDetails(final Pageable pageable){
        return convertToMapResponse(wormStageMasterRepository.findByActiveOrderByWormStageMasterNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(wormStageMasterRepository.findByActiveOrderByWormStageMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<WormStageMaster> activeWormStageMasters) {
        Map<String, Object> response = new HashMap<>();

        List<WormStageMasterResponse> wormStageMasterResponses = activeWormStageMasters.getContent().stream()
                .map(wormStageMaster -> mapper.wormStageMasterEntityToObject(wormStageMaster,WormStageMasterResponse.class)).collect(Collectors.toList());
        response.put("wormStageMaster",wormStageMasterResponses);
        response.put("currentPage", activeWormStageMasters.getNumber());
        response.put("totalItems", activeWormStageMasters.getTotalElements());
        response.put("totalPages", activeWormStageMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<WormStageMaster> activeWormStageMasters) {
        Map<String, Object> response = new HashMap<>();

        List<WormStageMasterResponse> wormStageMasterResponses = activeWormStageMasters.stream()
                .map(wormStageMaster -> mapper.wormStageMasterEntityToObject(wormStageMaster,WormStageMasterResponse.class)).collect(Collectors.toList());
        response.put("wormStageMaster",wormStageMasterResponses);
        return response;
    }

    @Transactional
    public WormStageMasterResponse deleteWormStageMasterDetails(long id) {

        WormStageMasterResponse wormStageMasterResponse = new WormStageMasterResponse();
        WormStageMaster wormStageMaster = wormStageMasterRepository.findByWormStageMasterIdAndActive(id, true);
        if (Objects.nonNull(wormStageMaster)) {
            wormStageMaster.setActive(false);
            wormStageMasterResponse = mapper.wormStageMasterEntityToObject(wormStageMasterRepository.save(wormStageMaster), WormStageMasterResponse.class);
            wormStageMasterResponse.setError(false);
        } else {
            wormStageMasterResponse.setError(true);
            wormStageMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return wormStageMasterResponse;
    }

    public WormStageMasterResponse getById(int id){
        WormStageMasterResponse wormStageMasterResponse = new WormStageMasterResponse();
        WormStageMaster wormStageMaster = wormStageMasterRepository.findByWormStageMasterIdAndActive(id,true);
        if(wormStageMaster == null){
            wormStageMasterResponse.setError(true);
            wormStageMasterResponse.setError_description("Invalid id");
        }else{
            wormStageMasterResponse =  mapper.wormStageMasterEntityToObject(wormStageMaster,WormStageMasterResponse.class);
            wormStageMasterResponse.setError(false);
        }
        log.info("Entity is ",wormStageMaster);
        return wormStageMasterResponse;
    }

    @Transactional
    public WormStageMasterResponse updateWormStageMasterDetails(EditWormStageMasterRequest wormStageMasterRequest){

        WormStageMasterResponse wormStageMasterResponse = new WormStageMasterResponse();
        List<WormStageMaster> wormStageMasterList = wormStageMasterRepository.findByWormStageMasterNameAndWormStageMasterNameInKannadaAndWormStageMasterIdIsNot(wormStageMasterRequest.getWormStageMasterName(), wormStageMasterRequest.getWormStageMasterNameInKannada(),wormStageMasterRequest.getWormStageMasterId());
        if(wormStageMasterList.size()>0){
            wormStageMasterResponse.setError(true);
            wormStageMasterResponse.setError_description("Worm Stage already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            WormStageMaster wormStageMaster = wormStageMasterRepository.findByWormStageMasterIdAndActiveIn(wormStageMasterRequest.getWormStageMasterId(), Set.of(true,false));
            if(Objects.nonNull(wormStageMaster)){
                wormStageMaster.setWormStageMasterName(wormStageMasterRequest.getWormStageMasterName());
                wormStageMaster.setWormStageMasterNameInKannada(wormStageMasterRequest.getWormStageMasterNameInKannada());
                wormStageMaster.setActive(true);
                WormStageMaster wormStageMaster1 = wormStageMasterRepository.save(wormStageMaster);
                wormStageMasterResponse = mapper.wormStageMasterEntityToObject(wormStageMaster1, WormStageMasterResponse.class);
                wormStageMasterResponse.setError(false);
            } else {
                wormStageMasterResponse.setError(true);
                wormStageMasterResponse.setError_description("Error occurred while fetching Worm Stage");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return wormStageMasterResponse;
    }

}
