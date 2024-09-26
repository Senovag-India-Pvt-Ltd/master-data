package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hectareMaster.EditHectareMasterRequest;
import com.sericulture.masterdata.model.api.spacing.EditSpacingMasterRequest;
import com.sericulture.masterdata.model.api.spacing.SpacingMasterRequest;
import com.sericulture.masterdata.model.api.spacing.SpacingMasterResponse;
import com.sericulture.masterdata.model.entity.SpacingMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.SpacingMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpacingMasterService {

    @Autowired
    SpacingMasterRepository spacingMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public SpacingMasterResponse insertSpacingMasterDetails(SpacingMasterRequest spacingMasterRequest){
        SpacingMasterResponse spacingMasterResponse = new SpacingMasterResponse();
        SpacingMaster spacingMaster = mapper.spacingMasterObjectToEntity(spacingMasterRequest,SpacingMaster.class);
        validator.validate(spacingMaster);
        List<SpacingMaster> spacingMasterList= spacingMasterRepository.findBySpacingName(spacingMasterRequest.getSpacingName());
        if(!spacingMasterList.isEmpty() && spacingMasterList.stream().filter(SpacingMaster::getActive).findAny().isPresent()){
            spacingMasterResponse.setError(true);
            spacingMasterResponse.setError_description("SpacingMaster name already exist");
//        }
//        else if(!spacingMasterList.isEmpty() && spacingMasterList.stream().filter(Predicate.not(SpacingMaster::getActive)).findAny().isPresent()){
//            spacingMasterResponse.setError(true);
//            spacingMasterResponse.setError_description("spacingMaster name already exist with inactive state");
        }else {
            spacingMasterResponse = mapper.spacingMasterEntityToObject(spacingMasterRepository.save(spacingMaster), SpacingMasterResponse.class);
            spacingMasterResponse.setError(false);
        }
        return spacingMasterResponse;
    }

    public Map<String,Object> getPaginatedSpacingMasterDetails(final Pageable pageable){
        return convertToMapResponse(spacingMasterRepository.findByActiveOrderBySpacingNameAsc( true,pageable ));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(spacingMasterRepository.findByActiveOrderBySpacingNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<SpacingMaster> activeSpacingMasters) {
        Map<String, Object> response = new HashMap<>();

        List<SpacingMasterResponse> spacingMasterResponses = activeSpacingMasters.getContent().stream()
                .map(spacingMaster -> mapper.spacingMasterEntityToObject(spacingMaster,SpacingMasterResponse.class)).collect(Collectors.toList());
        response.put("spacingMaster",spacingMasterResponses);
        response.put("currentPage", activeSpacingMasters.getNumber());
        response.put("totalItems", activeSpacingMasters.getTotalElements());
        response.put("totalPages", activeSpacingMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<SpacingMaster> activeSpacingMasters) {
        Map<String, Object> response = new HashMap<>();

        List<SpacingMasterResponse> spacingMasterResponses = activeSpacingMasters.stream()
                .map(spacingMaster -> mapper.spacingMasterEntityToObject(spacingMaster,SpacingMasterResponse.class)).collect(Collectors.toList());
        response.put("spacingMaster",spacingMasterResponses);
        return response;
    }

    @Transactional
    public SpacingMasterResponse deleteSpacingMasterDetails(long id) {

        SpacingMasterResponse spacingMasterResponse= new SpacingMasterResponse();
        SpacingMaster spacingMaster = spacingMasterRepository.findBySpacingIdAndActive(id, true);
        if (Objects.nonNull(spacingMaster)) {
            spacingMaster.setActive(false);
            spacingMasterResponse = mapper.spacingMasterEntityToObject(spacingMasterRepository.save(spacingMaster), SpacingMasterResponse.class);
            spacingMasterResponse.setError(false);
        } else {
            spacingMasterResponse.setError(true);
            spacingMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return spacingMasterResponse;
    }

    public SpacingMasterResponse getById(int id){
        SpacingMasterResponse spacingMasterResponse = new SpacingMasterResponse();
        SpacingMaster spacingMaster = spacingMasterRepository.findBySpacingIdAndActive(id,true);
        if(spacingMaster == null){
            spacingMasterResponse.setError(true);
            spacingMasterResponse.setError_description("Invalid id");
        }else{
            spacingMasterResponse =  mapper.spacingMasterEntityToObject(spacingMaster,SpacingMasterResponse.class);
            spacingMasterResponse.setError(false);
        }
        log.info("Entity is ",spacingMaster);
        return spacingMasterResponse;
    }

    @Transactional
    public SpacingMasterResponse updateSpacingMasterDetails(EditSpacingMasterRequest spacingMasterRequest){

        SpacingMasterResponse spacingMasterResponse = new SpacingMasterResponse();
        List<SpacingMaster> spacingMasterList = spacingMasterRepository.findBySpacingNameAndSpacingIdIsNot(spacingMasterRequest.getSpacingName(),spacingMasterRequest.getSpacingId());
        if(spacingMasterList.size()>0){
            spacingMasterResponse.setError(true);
            spacingMasterResponse.setError_description("SpacingMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            SpacingMaster spacingMaster= spacingMasterRepository.findBySpacingIdAndActiveIn(spacingMasterRequest.getSpacingId(), Set.of(true,false));
            if(Objects.nonNull(spacingMaster)){
                spacingMaster.setSpacingName(spacingMasterRequest.getSpacingName());
                spacingMaster.setActive(true);
                SpacingMaster spacingMaster1 = spacingMasterRepository.save(spacingMaster);
                spacingMasterResponse = mapper.spacingMasterEntityToObject(spacingMaster1, SpacingMasterResponse.class);
                spacingMasterResponse.setError(false);
            } else {
                spacingMasterResponse.setError(true);
                spacingMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return spacingMasterResponse;
    }
}
