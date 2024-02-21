package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hdAnswerMaster.EditHdAnswerMasterRequest;
import com.sericulture.masterdata.model.api.hdAnswerMaster.HdAnswerMasterRequest;
import com.sericulture.masterdata.model.api.hdAnswerMaster.HdAnswerMasterResponse;
import com.sericulture.masterdata.model.api.hdStatusMaster.EditHdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterResponse;
import com.sericulture.masterdata.model.entity.HdAnswerMaster;
import com.sericulture.masterdata.model.entity.HdStatusMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdAnswerMasterRepository;
import com.sericulture.masterdata.repository.HdStatusMasterRepository;
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
public class HdAnswerMasterService {
    @Autowired
    HdAnswerMasterRepository hdAnswerMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdAnswerMasterResponse getHdAnswerMasterDetails(String hdAnswerName){
        HdAnswerMasterResponse hdAnswerMasterResponse = new HdAnswerMasterResponse();
        HdAnswerMaster hdAnswerMaster =  hdAnswerMasterRepository.findByHdAnswerNameAndActive(hdAnswerName, true);
        if(hdAnswerMaster==null){
            hdAnswerMasterResponse.setError(true);
            hdAnswerMasterResponse.setError_description("Hd AnswerMaster not found");
        }else{
            hdAnswerMasterResponse = mapper.hdAnswerMasterEntityToObject(hdAnswerMaster, HdAnswerMasterResponse.class);
            hdAnswerMasterResponse.setError(false);
        }
        log.info("Entity is ",hdAnswerMaster);
        return hdAnswerMasterResponse;

    }

    @Transactional
    public HdAnswerMasterResponse insertHdAnswerMasterDetails(HdAnswerMasterRequest hdAnswerMasterRequest){
        HdAnswerMasterResponse hdAnswerMasterResponse = new HdAnswerMasterResponse();
        HdAnswerMaster hdAnswerMaster = mapper.hdAnswerMasterObjectToEntity(hdAnswerMasterRequest, HdAnswerMaster.class);
        validator.validate(hdAnswerMaster);
        List<HdAnswerMaster> hdAnswerMasterList= hdAnswerMasterRepository.findByActiveAndHdAnswerName(true,hdAnswerMasterRequest.getHdAnswerName());
        if(!hdAnswerMasterList.isEmpty() && hdAnswerMasterList.stream().filter(HdAnswerMaster::getActive).findAny().isPresent()){
            hdAnswerMasterResponse.setError(true);
            hdAnswerMasterResponse.setError_description("HdAnswer name already exist");
        }
        else if(!hdAnswerMasterList.isEmpty() && hdAnswerMasterList.stream().filter(Predicate.not(HdAnswerMaster::getActive)).findAny().isPresent()){
            hdAnswerMasterResponse.setError(true);
            hdAnswerMasterResponse.setError_description("HdAnswer name already exist with inactive state");
        }else {
            hdAnswerMasterResponse = mapper.hdAnswerMasterEntityToObject(hdAnswerMasterRepository.save(hdAnswerMaster), HdAnswerMasterResponse.class);
            hdAnswerMasterResponse.setError(false);
        }
        return hdAnswerMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdAnswerMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdAnswerMasterRepository.findByActiveOrderByHdAnswerNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdAnswerMasterRepository.findByActiveOrderByHdAnswerNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdAnswerMaster> activeHdAnswerMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdAnswerMasterResponse> hdAnswerMasterResponses = activeHdAnswerMasters.getContent().stream()
                .map(hdAnswerMaster -> mapper.hdAnswerMasterEntityToObject(hdAnswerMaster, HdAnswerMasterResponse.class)).collect(Collectors.toList());
        response.put("hdAnswerMaster",hdAnswerMasterResponses);
        response.put("currentPage", activeHdAnswerMasters.getNumber());
        response.put("totalItems", activeHdAnswerMasters.getTotalElements());
        response.put("totalPages", activeHdAnswerMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdAnswerMaster> activeHdAnswerMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdAnswerMasterResponse> hdAnswerMasterResponses = activeHdAnswerMasters.stream()
                .map(hAnswerMaster -> mapper.hdAnswerMasterEntityToObject(hAnswerMaster, HdAnswerMasterResponse.class)).collect(Collectors.toList());
        response.put("hdAnswerMaster",hdAnswerMasterResponses);
        return response;
    }

    @Transactional
    public HdAnswerMasterResponse deleteHdAnswerMasterDetails(long id) {

        HdAnswerMasterResponse hdAnswerMasterResponse= new HdAnswerMasterResponse();
        HdAnswerMaster hdAnswerMaster = hdAnswerMasterRepository.findByHdAnswerIdAndActive(id, true);
        if (Objects.nonNull(hdAnswerMaster)) {
            hdAnswerMaster.setActive(false);
            hdAnswerMasterResponse = mapper.hdAnswerMasterEntityToObject(hdAnswerMasterRepository.save(hdAnswerMaster), HdAnswerMasterResponse.class);
            hdAnswerMasterResponse.setError(false);
        } else {
            hdAnswerMasterResponse.setError(true);
            hdAnswerMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdAnswerMasterResponse;
    }

    @Transactional
    public HdAnswerMasterResponse getById(int id){
        HdAnswerMasterResponse hdAnswerMasterResponse = new HdAnswerMasterResponse();
        HdAnswerMaster hdAnswerMaster = hdAnswerMasterRepository.findByHdAnswerIdAndActive(id,true);
        if(hdAnswerMaster == null){
            hdAnswerMasterResponse.setError(true);
            hdAnswerMasterResponse.setError_description("Invalid id");
        }else{
            hdAnswerMasterResponse =  mapper.hdAnswerMasterEntityToObject(hdAnswerMaster, HdAnswerMasterResponse.class);
            hdAnswerMasterResponse.setError(false);
        }
        log.info("Entity is ",hdAnswerMaster);
        return hdAnswerMasterResponse;
    }

    @Transactional
    public HdAnswerMasterResponse updateHdAnswerMasterDetails(EditHdAnswerMasterRequest hdAnswerMasterRequest){

        HdAnswerMasterResponse hdAnswerMasterResponse = new HdAnswerMasterResponse();
        List<HdAnswerMaster> hdAnswerMasterList =  hdAnswerMasterRepository.findByActiveAndHdAnswerName(true,hdAnswerMasterRequest.getHdAnswerName());
        if(hdAnswerMasterList.size()>0){
            hdAnswerMasterResponse.setError(true);
            hdAnswerMasterResponse.setError_description("AnswerMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HdAnswerMaster hdAnswerMaster= hdAnswerMasterRepository.findByHdAnswerIdAndActiveIn(hdAnswerMasterRequest.getHdAnswerId(), Set.of(true,false));
            if(Objects.nonNull(hdAnswerMaster)){
                hdAnswerMaster.setHdAnswerName(hdAnswerMasterRequest.getHdAnswerName());
                hdAnswerMaster.setActive(true);
                HdAnswerMaster hdAnswerMaster1 = hdAnswerMasterRepository.save(hdAnswerMaster);
                hdAnswerMasterResponse = mapper.hdAnswerMasterEntityToObject(hdAnswerMaster1, HdAnswerMasterResponse.class);
                hdAnswerMasterResponse.setError(false);
            } else {
                hdAnswerMasterResponse.setError(true);
                hdAnswerMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdAnswerMasterResponse;
    }
}
