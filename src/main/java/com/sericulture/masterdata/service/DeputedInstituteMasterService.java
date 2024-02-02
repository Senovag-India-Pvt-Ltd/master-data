package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.deputedInstituteMaster.DeputedInstituteMasterRequest;
import com.sericulture.masterdata.model.api.deputedInstituteMaster.DeputedInstituteMasterResponse;
import com.sericulture.masterdata.model.api.deputedInstituteMaster.EditDeputedInstituteMasterRequest;
import com.sericulture.masterdata.model.api.externalUnitType.EditExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeRequest;
import com.sericulture.masterdata.model.api.externalUnitType.ExternalUnitTypeResponse;
import com.sericulture.masterdata.model.entity.DeputedInstituteMaster;
import com.sericulture.masterdata.model.entity.ExternalUnitType;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DeputedInstituteMasterRepository;
import com.sericulture.masterdata.repository.ExternalUnitTypeRepository;
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
public class DeputedInstituteMasterService {

    @Autowired
    DeputedInstituteMasterRepository deputedInstituteMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DeputedInstituteMasterResponse getDeputedInstituteDetails(String deputedInstituteName){
        DeputedInstituteMasterResponse deputedInstituteMasterResponse = new DeputedInstituteMasterResponse();
        DeputedInstituteMaster deputedInstituteMaster = deputedInstituteMasterRepository.findByDeputedInstituteNameAndActive(deputedInstituteName,true);
        if(deputedInstituteMaster==null){
            deputedInstituteMasterResponse.setError(true);
            deputedInstituteMasterResponse.setError_description("Deputed Institute not found");
        }else{
            deputedInstituteMasterResponse = mapper.deputedInstituteMasterEntityToObject(deputedInstituteMaster, DeputedInstituteMasterResponse.class);
            deputedInstituteMasterResponse.setError(false);
        }
        log.info("Entity is ",deputedInstituteMaster);
        return deputedInstituteMasterResponse;
    }

    @Transactional
    public DeputedInstituteMasterResponse insertDeputedInstituteDetails(DeputedInstituteMasterRequest deputedInstituteMasterRequest){
        DeputedInstituteMasterResponse deputedInstituteMasterResponse = new DeputedInstituteMasterResponse();
        DeputedInstituteMaster deputedInstituteMaster = mapper.deputedInstituteMasterObjectToEntity(deputedInstituteMasterRequest,DeputedInstituteMaster.class);
        validator.validate(deputedInstituteMaster);
        List<DeputedInstituteMaster> deputedInstituteMasterList = deputedInstituteMasterRepository.findByDeputedInstituteName(deputedInstituteMasterRequest.getDeputedInstituteName());
        if(!deputedInstituteMasterList.isEmpty() && deputedInstituteMasterList.stream().filter(DeputedInstituteMaster::getActive).findAny().isPresent()){
            deputedInstituteMasterResponse.setError(true);
            deputedInstituteMasterResponse.setError_description("Deputed Institute name already exist");
        }
        else if(!deputedInstituteMasterList.isEmpty() && deputedInstituteMasterList.stream().filter(Predicate.not(DeputedInstituteMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            deputedInstituteMasterResponse.setError(true);
            deputedInstituteMasterResponse.setError_description("Deputed Institute name already exist with inactive state");
        }else {
            deputedInstituteMasterResponse = mapper.deputedInstituteMasterEntityToObject(deputedInstituteMasterRepository.save(deputedInstituteMaster), DeputedInstituteMasterResponse.class);
            deputedInstituteMasterResponse.setError(false);
        }
        return deputedInstituteMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDeputedInstituteDetails(final Pageable pageable){
        return convertToMapResponse(deputedInstituteMasterRepository.findByActiveOrderByDeputedInstituteNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(deputedInstituteMasterRepository.findByActiveOrderByDeputedInstituteNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<DeputedInstituteMaster> activeDeputedInstitutes) {
        Map<String, Object> response = new HashMap<>();

        List<DeputedInstituteMasterResponse> deputedInstituteMasterResponses = activeDeputedInstitutes.getContent().stream()
                .map(deputedInstituteMaster -> mapper.deputedInstituteMasterEntityToObject(deputedInstituteMaster,DeputedInstituteMasterResponse.class)).collect(Collectors.toList());
        response.put("deputedInstituteMaster",deputedInstituteMasterResponses);
        response.put("currentPage", activeDeputedInstitutes.getNumber());
        response.put("totalItems", activeDeputedInstitutes.getTotalElements());
        response.put("totalPages", activeDeputedInstitutes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<DeputedInstituteMaster> activeDeputedInstitutes) {
        Map<String, Object> response = new HashMap<>();

        List<DeputedInstituteMasterResponse> deputedInstituteMasterResponses = activeDeputedInstitutes.stream()
                .map(deputedInstituteMaster -> mapper.deputedInstituteMasterEntityToObject(deputedInstituteMaster,DeputedInstituteMasterResponse.class)).collect(Collectors.toList());
        response.put("deputedInstituteMaster",deputedInstituteMasterResponses);
        return response;
    }

    @Transactional
    public DeputedInstituteMasterResponse deleteDeputedInstituteDetails(long id) {
        DeputedInstituteMasterResponse deputedInstituteMasterResponse = new DeputedInstituteMasterResponse();
        DeputedInstituteMaster deputedInstituteMaster = deputedInstituteMasterRepository.findByDeputedInstituteIdAndActive(id, true);
        if (Objects.nonNull(deputedInstituteMaster)) {
            deputedInstituteMaster.setActive(false);
            deputedInstituteMasterResponse = mapper.deputedInstituteMasterEntityToObject(deputedInstituteMasterRepository.save(deputedInstituteMaster), DeputedInstituteMasterResponse.class);
            deputedInstituteMasterResponse.setError(false);
        } else {
            deputedInstituteMasterResponse.setError(true);
            deputedInstituteMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return deputedInstituteMasterResponse;
    }

    @Transactional
    public DeputedInstituteMasterResponse getById(int id){
        DeputedInstituteMasterResponse deputedInstituteMasterResponse = new DeputedInstituteMasterResponse();
        DeputedInstituteMaster deputedInstituteMaster = deputedInstituteMasterRepository.findByDeputedInstituteIdAndActive(id,true);
        if(deputedInstituteMaster == null){
            deputedInstituteMasterResponse.setError(true);
            deputedInstituteMasterResponse.setError_description("Invalid id");
        }else{
            deputedInstituteMasterResponse =  mapper.deputedInstituteMasterEntityToObject(deputedInstituteMaster,DeputedInstituteMasterResponse.class);
            deputedInstituteMasterResponse.setError(false);
        }
        log.info("Entity is ",deputedInstituteMaster);
        return deputedInstituteMasterResponse;
    }

    @Transactional
    public DeputedInstituteMasterResponse updateDeputedInstituteDetails(EditDeputedInstituteMasterRequest deputedInstituteMasterRequest) {
        DeputedInstituteMasterResponse deputedInstituteMasterResponse = new DeputedInstituteMasterResponse();
        List<DeputedInstituteMaster> deputedInstituteMasterList = deputedInstituteMasterRepository.findByDeputedInstituteName(deputedInstituteMasterRequest.getDeputedInstituteName());
        if (deputedInstituteMasterList.size() > 0) {
            deputedInstituteMasterResponse.setError(true);
            deputedInstituteMasterResponse.setError_description("Deputed Institute already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            DeputedInstituteMaster deputedInstituteMaster = deputedInstituteMasterRepository.findByDeputedInstituteIdAndActiveIn(deputedInstituteMasterRequest.getDeputedInstituteId(), Set.of(true, false));
            if (Objects.nonNull(deputedInstituteMaster)) {
                deputedInstituteMaster.setDeputedInstituteName(deputedInstituteMasterRequest.getDeputedInstituteName());
                deputedInstituteMaster.setActive(true);
                DeputedInstituteMaster deputedInstituteMaster1 = deputedInstituteMasterRepository.save(deputedInstituteMaster);
                deputedInstituteMasterResponse = mapper.deputedInstituteMasterEntityToObject(deputedInstituteMaster1, DeputedInstituteMasterResponse.class);
                deputedInstituteMasterResponse.setError(false);
            } else {
                deputedInstituteMasterResponse.setError(true);
                deputedInstituteMasterResponse.setError_description("Error occurred while fetching ExternalUnitType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return deputedInstituteMasterResponse;
    }
}
