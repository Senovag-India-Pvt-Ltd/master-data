package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hdSeverityMaster.EditHdSeverityMasterRequest;
import com.sericulture.masterdata.model.api.hdSeverityMaster.HdSeverityMasterRequest;
import com.sericulture.masterdata.model.api.hdSeverityMaster.HdSeverityMasterResponse;
import com.sericulture.masterdata.model.api.hdStatusMaster.EditHdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterResponse;
import com.sericulture.masterdata.model.entity.HdSeverityMaster;
import com.sericulture.masterdata.model.entity.HdStatusMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdSeverityMasterRepository;
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
public class HdSeverityMasterService {
    @Autowired
    HdSeverityMasterRepository hdSeverityMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdSeverityMasterResponse getHdSeverityMasterDetails(String hdSeverityName){
        HdSeverityMasterResponse hdSeverityMasterResponse = new HdSeverityMasterResponse();
        HdSeverityMaster hdSeverityMaster = null;
        if(hdSeverityMaster==null){
            hdSeverityMaster = hdSeverityMasterRepository.findByHdSeverityNameAndActive(hdSeverityName, true);
            hdSeverityMasterResponse = mapper.hdSeverityMasterEntityToObject(hdSeverityMaster, HdSeverityMasterResponse.class);
            hdSeverityMasterResponse.setError(false);
        }else{
            hdSeverityMasterResponse.setError(true);
            hdSeverityMasterResponse.setError_description("Hd SeverityMaster not found");
        }
        log.info("Entity is ",hdSeverityMaster);
        return hdSeverityMasterResponse;

    }

    @Transactional
    public HdSeverityMasterResponse insertHdSeverityMasterDetails(HdSeverityMasterRequest hdSeverityMasterRequest){
        HdSeverityMasterResponse hdSeverityMasterResponse = new HdSeverityMasterResponse();
        HdSeverityMaster hdSeverityMaster = mapper.hdSeverityMasterObjectToEntity(hdSeverityMasterRequest, HdSeverityMaster.class);
        validator.validate(hdSeverityMaster);
        List<HdSeverityMaster> hdSeverityMasterList= hdSeverityMasterRepository.findByHdSeverityName(hdSeverityMasterRequest.getHdSeverityName());
        if(!hdSeverityMasterList.isEmpty() && hdSeverityMasterList.stream().filter(HdSeverityMaster::getActive).findAny().isPresent()){
            hdSeverityMasterResponse.setError(true);
            hdSeverityMasterResponse.setError_description("Severity name already exist");
        }
        else if(!hdSeverityMasterList.isEmpty() && hdSeverityMasterList.stream().filter(Predicate.not(HdSeverityMaster::getActive)).findAny().isPresent()){
            hdSeverityMasterResponse.setError(true);
            hdSeverityMasterResponse.setError_description("Severity name already exist with inactive state");
        }else {
            hdSeverityMasterResponse = mapper.hdSeverityMasterEntityToObject(hdSeverityMasterRepository.save(hdSeverityMaster), HdSeverityMasterResponse.class);
            hdSeverityMasterResponse.setError(false);
        }
        return hdSeverityMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdSeverityMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdSeverityMasterRepository.findByActiveOrderByHdSeverityNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdSeverityMasterRepository.findByActiveOrderByHdSeverityNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdSeverityMaster> activeHdSeverityMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdSeverityMasterResponse> hdSeverityMasterResponses = activeHdSeverityMasters.getContent().stream()
                .map(hdSeverityMaster -> mapper.hdSeverityMasterEntityToObject(hdSeverityMaster, HdSeverityMasterResponse.class)).collect(Collectors.toList());
        response.put("hdSeverityMaster",hdSeverityMasterResponses);
        response.put("currentPage", activeHdSeverityMasters.getNumber());
        response.put("totalItems", activeHdSeverityMasters.getTotalElements());
        response.put("totalPages", activeHdSeverityMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdSeverityMaster> activeHdSeverityMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdSeverityMasterResponse> hdSeverityMasterResponses = activeHdSeverityMasters.stream()
                .map(hdSeverityMaster -> mapper.hdSeverityMasterEntityToObject(hdSeverityMaster, HdSeverityMasterResponse.class)).collect(Collectors.toList());
        response.put("hdSeverityMaster",hdSeverityMasterResponses);
        return response;
    }

    @Transactional
    public HdSeverityMasterResponse deleteHdSeverityMasterDetails(long id) {

        HdSeverityMasterResponse hdSeverityMasterResponse= new HdSeverityMasterResponse();
        HdSeverityMaster hdSeverityMaster = hdSeverityMasterRepository.findByHdSeverityIdAndActive(id, true);
        if (Objects.nonNull(hdSeverityMaster)) {
            hdSeverityMaster.setActive(false);
            hdSeverityMasterResponse = mapper.hdSeverityMasterEntityToObject(hdSeverityMasterRepository.save(hdSeverityMaster), HdSeverityMasterResponse.class);
            hdSeverityMasterResponse.setError(false);
        } else {
            hdSeverityMasterResponse.setError(true);
            hdSeverityMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdSeverityMasterResponse;
    }

    @Transactional
    public HdSeverityMasterResponse getById(int id){
        HdSeverityMasterResponse hdSeverityMasterResponse = new HdSeverityMasterResponse();
        HdSeverityMaster hdSeverityMaster = hdSeverityMasterRepository.findByHdSeverityIdAndActive(id,true);
        if(hdSeverityMaster == null){
            hdSeverityMasterResponse.setError(true);
            hdSeverityMasterResponse.setError_description("Invalid id");
        }else{
            hdSeverityMasterResponse =  mapper.hdSeverityMasterEntityToObject(hdSeverityMaster, HdSeverityMasterResponse.class);
            hdSeverityMasterResponse.setError(false);
        }
        log.info("Entity is ",hdSeverityMaster);
        return hdSeverityMasterResponse;
    }

    @Transactional
    public HdSeverityMasterResponse updateHdSeverityMasterDetails(EditHdSeverityMasterRequest hdSeverityMasterRequest){

        HdSeverityMasterResponse hdSeverityMasterResponse = new HdSeverityMasterResponse();
        List<HdSeverityMaster> hdSeverityMasterList =  hdSeverityMasterRepository.findByHdSeverityName(hdSeverityMasterRequest.getHdSeverityName());
        if(hdSeverityMasterList.size()>0){
            hdSeverityMasterResponse.setError(true);
            hdSeverityMasterResponse.setError_description("SeverityMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HdSeverityMaster hdSeverityMaster= hdSeverityMasterRepository.findByHdSeverityIdAndActiveIn(hdSeverityMasterRequest.getHdSeverityId(), Set.of(true,false));
            if(Objects.nonNull(hdSeverityMaster)){
                hdSeverityMaster.setHdSeverityName(hdSeverityMasterRequest.getHdSeverityName());
                hdSeverityMaster.setActive(true);
                HdSeverityMaster hdSeverityMaster1 = hdSeverityMasterRepository.save(hdSeverityMaster);
                hdSeverityMasterResponse = mapper.hdSeverityMasterEntityToObject(hdSeverityMaster1, HdSeverityMasterResponse.class);
                hdSeverityMasterResponse.setError(false);
            } else {
                hdSeverityMasterResponse.setError(true);
                hdSeverityMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdSeverityMasterResponse;
    }
}
