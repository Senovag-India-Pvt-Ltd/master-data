package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.disinfectantMaster.DisinfectantMasterRequest;
import com.sericulture.masterdata.model.api.disinfectantMaster.DisinfectantMasterResponse;
import com.sericulture.masterdata.model.api.disinfectantMaster.EditDisinfectantMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.EditTrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterResponse;
import com.sericulture.masterdata.model.entity.DisinfectantMaster;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DisinfectantMasterRepository;
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

public class DisinfectantMasterService {

    @Autowired
    DisinfectantMasterRepository disinfectantMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DisinfectantMasterResponse getDisinfectantMasterDetails(String disinfectantMasterName){
        DisinfectantMasterResponse disinfectantMasterResponse = new DisinfectantMasterResponse();
        DisinfectantMaster disinfectantMaster = disinfectantMasterRepository.findByDisinfectantMasterNameAndActive(disinfectantMasterName, true);
        if(disinfectantMaster==null){
            disinfectantMasterResponse.setError(true);
            disinfectantMasterResponse.setError_description("disinfectantMaster  not Found");
        }else{
            disinfectantMasterResponse = mapper.disinfectantMasterEntityToObject(disinfectantMaster, DisinfectantMasterResponse.class);
            disinfectantMasterResponse.setError(false);
        }
        log.info("Entity is ",disinfectantMaster);
        return disinfectantMasterResponse;

    }

    @Transactional
    public DisinfectantMasterResponse insertDisinfectantMasterDetails(DisinfectantMasterRequest disinfectantMasterRequest){
        DisinfectantMasterResponse disinfectantMasterResponse = new DisinfectantMasterResponse();
        DisinfectantMaster disinfectantMaster = mapper.disinfectantMasterObjectToEntity(disinfectantMasterRequest,DisinfectantMaster.class);
        validator.validate(disinfectantMaster);
        List<DisinfectantMaster> disinfectantMasterList= disinfectantMasterRepository.findByDisinfectantMasterNameAndDisinfectantMasterNameInKannada(disinfectantMasterRequest.getDisinfectantMasterName(),disinfectantMasterRequest.getDisinfectantMasterNameInKannada());
        if(!disinfectantMasterList.isEmpty() && disinfectantMasterList.stream().filter(DisinfectantMaster::getActive).findAny().isPresent()){
            disinfectantMasterResponse.setError(true);
            disinfectantMasterResponse.setError_description("disinfectantMaster name already exist");
        }
        else if(!disinfectantMasterList.isEmpty() && disinfectantMasterList.stream().filter(Predicate.not(DisinfectantMaster::getActive)).findAny().isPresent()){
            disinfectantMasterResponse.setError(true);
            disinfectantMasterResponse.setError_description("disinfectantMaster name already exist with inactive state");
        }else {
            disinfectantMasterResponse = mapper.disinfectantMasterEntityToObject(disinfectantMasterRepository.save(disinfectantMaster), DisinfectantMasterResponse.class);
            disinfectantMasterResponse.setError(false);
        }
        return disinfectantMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDisinfectantMasterDetails(final Pageable pageable){
        return convertToMapResponse(disinfectantMasterRepository.findByActiveOrderByDisinfectantMasterNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(disinfectantMasterRepository.findByActiveOrderByDisinfectantMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<DisinfectantMaster> activeDisinfectantMasters) {
        Map<String, Object> response = new HashMap<>();

        List<DisinfectantMasterResponse> disinfectantMasterResponses = activeDisinfectantMasters.getContent().stream()
                .map(disinfectantMaster -> mapper.disinfectantMasterEntityToObject(disinfectantMaster,DisinfectantMasterResponse.class)).collect(Collectors.toList());
        response.put("disinfectantMaster",disinfectantMasterResponses);
        response.put("currentPage", activeDisinfectantMasters.getNumber());
        response.put("totalItems", activeDisinfectantMasters.getTotalElements());
        response.put("totalPages", activeDisinfectantMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<DisinfectantMaster> activeDisinfectantMasters) {
        Map<String, Object> response = new HashMap<>();

        List<DisinfectantMasterResponse> disinfectantMasterResponses = activeDisinfectantMasters.stream()
                .map(disinfectantMaster -> mapper.disinfectantMasterEntityToObject(disinfectantMaster,DisinfectantMasterResponse.class)).collect(Collectors.toList());
        response.put("disinfectantMaster",disinfectantMasterResponses);
        return response;
    }

    @Transactional
    public DisinfectantMasterResponse deleteDisinfectantMasterDetails(long id) {

        DisinfectantMasterResponse disinfectantMasterResponse= new DisinfectantMasterResponse();
        DisinfectantMaster disinfectantMaster = disinfectantMasterRepository.findByDisinfectantMasterIdAndActive(id, true);
        if (Objects.nonNull(disinfectantMaster)) {
            disinfectantMaster.setActive(false);
            disinfectantMasterResponse = mapper.disinfectantMasterEntityToObject(disinfectantMasterRepository.save(disinfectantMaster), DisinfectantMasterResponse.class);
            disinfectantMasterResponse.setError(false);
        } else {
            disinfectantMasterResponse.setError(true);
            disinfectantMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return disinfectantMasterResponse;
    }

    @Transactional
    public DisinfectantMasterResponse getById(int id){
        DisinfectantMasterResponse disinfectantMasterResponse = new DisinfectantMasterResponse();
        DisinfectantMaster disinfectantMaster = disinfectantMasterRepository.findByDisinfectantMasterIdAndActive(id,true);
        if(disinfectantMaster == null){
            disinfectantMasterResponse.setError(true);
            disinfectantMasterResponse.setError_description("Invalid id");
        }else{
            disinfectantMasterResponse =  mapper.disinfectantMasterEntityToObject(disinfectantMaster,DisinfectantMasterResponse.class);
            disinfectantMasterResponse.setError(false);
        }
        log.info("Entity is ",disinfectantMaster);
        return disinfectantMasterResponse;
    }

    @Transactional
    public DisinfectantMasterResponse updateDisinfectantMasterDetails(EditDisinfectantMasterRequest disinfectantMasterRequest){

        DisinfectantMasterResponse disinfectantMasterResponse = new DisinfectantMasterResponse();
        List<DisinfectantMaster> disinfectantMasterList = disinfectantMasterRepository.findByDisinfectantMasterNameAndDisinfectantMasterNameInKannadaAndDisinfectantMasterIdIsNot(disinfectantMasterRequest.getDisinfectantMasterName(),disinfectantMasterRequest.getDisinfectantMasterNameInKannada(),disinfectantMasterRequest.getDisinfectantMasterId());
        if(disinfectantMasterList.size()>0){
            disinfectantMasterResponse.setError(true);
            disinfectantMasterResponse.setError_description("disinfectantMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            DisinfectantMaster disinfectantMaster= disinfectantMasterRepository.findByDisinfectantMasterIdAndActiveIn(disinfectantMasterRequest.getDisinfectantMasterId(), Set.of(true,false));
            if(Objects.nonNull(disinfectantMaster)){
                disinfectantMaster.setDisinfectantMasterName(disinfectantMasterRequest.getDisinfectantMasterName());
                disinfectantMaster.setDisinfectantMasterNameInKannada(disinfectantMasterRequest.getDisinfectantMasterNameInKannada());
                disinfectantMaster.setActive(true);
                DisinfectantMaster disinfectantMaster1 = disinfectantMasterRepository.save(disinfectantMaster);
                disinfectantMasterResponse = mapper.disinfectantMasterEntityToObject(disinfectantMaster1, DisinfectantMasterResponse.class);
                disinfectantMasterResponse.setError(false);
            } else {
                disinfectantMasterResponse.setError(true);
                disinfectantMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return disinfectantMasterResponse;
    }
}
