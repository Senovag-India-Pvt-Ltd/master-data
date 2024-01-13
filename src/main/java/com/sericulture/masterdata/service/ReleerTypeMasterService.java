package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.releerTypeMaster.EditReleerTypeMasterRequest;
import com.sericulture.masterdata.model.api.releerTypeMaster.ReleerTypeMasterRequest;
import com.sericulture.masterdata.model.api.releerTypeMaster.ReleerTypeMasterResponse;
import com.sericulture.masterdata.model.api.trCourseMaster.EditTrCourseMasterRequest;
import com.sericulture.masterdata.model.api.trCourseMaster.TrCourseMasterRequest;
import com.sericulture.masterdata.model.api.trCourseMaster.TrCourseMasterResponse;
import com.sericulture.masterdata.model.entity.ReleerTypeMaster;
import com.sericulture.masterdata.model.entity.TrCourseMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ReleerTypeMasterRepository;
import com.sericulture.masterdata.repository.TrCourseMasterRepository;
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
public class ReleerTypeMasterService {
    @Autowired
    ReleerTypeMasterRepository releerTypeMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReleerTypeMasterResponse getReleerTypeMasterDetails(String releerTypeMasterName){
        ReleerTypeMasterResponse releerTypeMasterResponse = new ReleerTypeMasterResponse();
        ReleerTypeMaster releerTypeMaster= null;
        if(releerTypeMaster==null){
            releerTypeMaster= releerTypeMasterRepository. findByReleerTypeMasterNameAndActive(releerTypeMasterName, true);
            releerTypeMasterResponse = mapper.releerTypeMasterEntityToObject(releerTypeMaster, ReleerTypeMasterResponse.class);
            releerTypeMasterResponse.setError(false);
        }else{
            releerTypeMasterResponse.setError(true);
            releerTypeMasterResponse.setError_description("Releer Type Master not found");
        }
        log.info("Entity is ",releerTypeMaster);
        return releerTypeMasterResponse;
    }

    @Transactional
    public ReleerTypeMasterResponse insertReleerTypeMasterDetails(ReleerTypeMasterRequest releerTypeMasterRequest){
        ReleerTypeMasterResponse releerTypeMasterResponse = new ReleerTypeMasterResponse();
        ReleerTypeMaster releerTypeMaster = mapper.releerTypeMasterObjectToEntity(releerTypeMasterRequest, ReleerTypeMaster.class);
        validator.validate(releerTypeMaster);
        List<ReleerTypeMaster> releerTypeMasterList = releerTypeMasterRepository.findByReleerTypeMasterName(releerTypeMasterRequest.getReleerTypeMasterName());
        if(!releerTypeMasterList.isEmpty() && releerTypeMasterList.stream(). filter(ReleerTypeMaster::getActive).findAny().isPresent()){
            releerTypeMasterResponse.setError(true);
            releerTypeMasterResponse.setError_description("Releer Type Master name already exist");
        }
        else if(!releerTypeMasterList.isEmpty() && releerTypeMasterList.stream().filter(Predicate.not(ReleerTypeMaster::getActive)).findAny().isPresent()){
            releerTypeMasterResponse.setError(true);
            releerTypeMasterResponse.setError_description("Releer Type Master name already exist with inactive state");
        }else {
            releerTypeMasterResponse = mapper.releerTypeMasterEntityToObject(releerTypeMasterRepository.save(releerTypeMaster), ReleerTypeMasterResponse.class);
            releerTypeMasterResponse.setError(false);
        }
        return releerTypeMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedReleerTypeMasterDetails(final Pageable pageable){
        return convertToMapResponse(releerTypeMasterRepository.findByActiveOrderByReleerTypeMasterIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(releerTypeMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ReleerTypeMaster> activeReleerTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<ReleerTypeMasterResponse> releerTypeMasterResponses= activeReleerTypeMasters.getContent().stream()
                .map(releerTypeMaster -> mapper.releerTypeMasterEntityToObject(releerTypeMaster, ReleerTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("releerTypeMaster",releerTypeMasterResponses);
        response.put("currentPage", activeReleerTypeMasters.getNumber());
        response.put("totalItems", activeReleerTypeMasters.getTotalElements());
        response.put("totalPages", activeReleerTypeMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ReleerTypeMaster> activeReleerTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<ReleerTypeMasterResponse> releerTypeMasterResponses= activeReleerTypeMasters.stream()
                .map(releerTypeMaster -> mapper.releerTypeMasterEntityToObject(releerTypeMaster, ReleerTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("releerTypeMaster",releerTypeMasterResponses);
        return response;
    }

    @Transactional
    public ReleerTypeMasterResponse deleteRelerTypeMasterDetails(long id) {

        ReleerTypeMasterResponse releerTypeMasterResponse  = new ReleerTypeMasterResponse();
        ReleerTypeMaster releerTypeMaster  = releerTypeMasterRepository.findByReleerTypeMasterIdAndActive(id, true);
        if (Objects.nonNull(releerTypeMaster)) {
            releerTypeMaster.setActive(false);
            releerTypeMasterResponse= mapper.releerTypeMasterEntityToObject(releerTypeMasterRepository.save(releerTypeMaster), ReleerTypeMasterResponse.class);
            releerTypeMasterResponse.setError(false);
        } else {
            releerTypeMasterResponse.setError(true);
            releerTypeMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return releerTypeMasterResponse;
    }

    @Transactional
    public ReleerTypeMasterResponse getById(int id){
        ReleerTypeMasterResponse releerTypeMasterResponse  = new ReleerTypeMasterResponse();
        ReleerTypeMaster releerTypeMaster= releerTypeMasterRepository. findByReleerTypeMasterIdAndActive(id, true);
        if(releerTypeMaster== null){
            releerTypeMasterResponse.setError(true);
            releerTypeMasterResponse.setError_description("Invalid id");
        }else{
            releerTypeMasterResponse =  mapper.releerTypeMasterEntityToObject(releerTypeMaster, ReleerTypeMasterResponse.class);
            releerTypeMasterResponse.setError(false);
        }
        log.info("Entity is ",releerTypeMaster);
        return releerTypeMasterResponse;
    }

    @Transactional
    public ReleerTypeMasterResponse updateReleerTypeMastersDetails(EditReleerTypeMasterRequest releerTypeMasterRequest){

        ReleerTypeMasterResponse releerTypeMasterResponse  = new ReleerTypeMasterResponse();
        List<ReleerTypeMaster> releerTypeMasterList = releerTypeMasterRepository. findByReleerTypeMasterName(releerTypeMasterRequest.getReleerTypeMasterName());
        if(releerTypeMasterList.size()>0){
            releerTypeMasterResponse.setError(true);
            releerTypeMasterResponse.setError_description("releerType Master, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            ReleerTypeMaster releerTypeMaster = releerTypeMasterRepository.findByReleerTypeMasterIdAndActiveIn(releerTypeMasterRequest.getReleerTypeMasterId(), Set.of(true,false));
            if(Objects.nonNull(releerTypeMaster)){
                releerTypeMaster.setReleerTypeMasterName(releerTypeMasterRequest.getReleerTypeMasterName());
                releerTypeMaster.setNoOfDeviceAllowed(releerTypeMasterRequest.getNoOfDeviceAllowed());
                releerTypeMaster.setActive(true);
                ReleerTypeMaster releerTypeMaster1 = releerTypeMasterRepository.save(releerTypeMaster);
                releerTypeMasterResponse = mapper.releerTypeMasterEntityToObject(releerTypeMaster1, ReleerTypeMasterResponse.class);
                releerTypeMasterResponse.setError(false);
            } else {
                releerTypeMasterResponse.setError(true);
                releerTypeMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return releerTypeMasterResponse;
    }


}



