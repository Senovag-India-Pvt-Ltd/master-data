package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hdModuleMaster.EditHdModuleMasterRequest;
import com.sericulture.masterdata.model.api.hdModuleMaster.HdModuleMasterRequest;
import com.sericulture.masterdata.model.api.hdModuleMaster.HdModuleMasterResponse;
import com.sericulture.masterdata.model.api.hdStatusMaster.EditHdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterResponse;
import com.sericulture.masterdata.model.entity.HdModuleMaster;
import com.sericulture.masterdata.model.entity.HdStatusMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdModuleMasterRepository;
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
public class HdStatusMasterService {

    @Autowired
    HdStatusMasterRepository hdStatusMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdStatusMasterResponse getHdStatusMasterDetails(String hdStatusName){
        HdStatusMasterResponse hdStatusMasterResponse = new HdStatusMasterResponse();
        HdStatusMaster hdStatusMaster = hdStatusMasterRepository.findByHdStatusNameAndActive(hdStatusName, true);
        if(hdStatusMaster==null){
            hdStatusMasterResponse.setError(true);
            hdStatusMasterResponse.setError_description("Status not found");
        }else{
            hdStatusMasterResponse = mapper.hdStatusMasterEntityToObject(hdStatusMaster, HdStatusMasterResponse.class);
            hdStatusMasterResponse.setError(false);
        }
        log.info("Entity is ",hdStatusMaster);
        return hdStatusMasterResponse;

    }

    @Transactional
    public HdStatusMasterResponse insertHdStatusMasterDetails(HdStatusMasterRequest hdStatusMasterRequest){
        HdStatusMasterResponse hdStatusMasterResponse = new HdStatusMasterResponse();
        HdStatusMaster hdStatusMaster = mapper.hdStatusMasterObjectToEntity(hdStatusMasterRequest, HdStatusMaster.class);
        validator.validate(hdStatusMaster);
        List<HdStatusMaster> hdStatusMasterList= hdStatusMasterRepository.findByHdStatusName(hdStatusMasterRequest.getHdStatusName());
        if(!hdStatusMasterList.isEmpty() && hdStatusMasterList.stream().filter(HdStatusMaster::getActive).findAny().isPresent()){
            hdStatusMasterResponse.setError(true);
            hdStatusMasterResponse.setError_description("Status name already exist");
//        }
//        else if(!hdStatusMasterList.isEmpty() && hdStatusMasterList.stream().filter(Predicate.not(HdStatusMaster::getActive)).findAny().isPresent()){
//            hdStatusMasterResponse.setError(true);
//            hdStatusMasterResponse.setError_description("Status name already exist with inactive state");
        }else {
            hdStatusMasterResponse = mapper.hdStatusMasterEntityToObject(hdStatusMasterRepository.save(hdStatusMaster), HdStatusMasterResponse.class);
            hdStatusMasterResponse.setError(false);
        }
        return hdStatusMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdStatusMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdStatusMasterRepository.findByActiveOrderByHdStatusNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdStatusMasterRepository.findByActiveOrderByHdStatusNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdStatusMaster> activeHdStatusMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdStatusMasterResponse> hdStatusMasterResponses = activeHdStatusMasters.getContent().stream()
                .map(hdStatusMaster -> mapper.hdStatusMasterEntityToObject(hdStatusMaster, HdStatusMasterResponse.class)).collect(Collectors.toList());
        response.put("hdStatusMaster",hdStatusMasterResponses);
        response.put("currentPage", activeHdStatusMasters.getNumber());
        response.put("totalItems", activeHdStatusMasters.getTotalElements());
        response.put("totalPages", activeHdStatusMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdStatusMaster> activeHdStatusMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdStatusMasterResponse> hdStatusMasterResponses = activeHdStatusMasters.stream()
                .map(hdStatusMaster -> mapper.hdStatusMasterEntityToObject(hdStatusMaster, HdStatusMasterResponse.class)).collect(Collectors.toList());
        response.put("hdStatusMaster",hdStatusMasterResponses);
        return response;
    }

    @Transactional
    public HdStatusMasterResponse deleteHdStatusMasterDetails(long id) {

        HdStatusMasterResponse hdStatusMasterResponse= new HdStatusMasterResponse();
        HdStatusMaster hdStatusMaster = hdStatusMasterRepository.findByHdStatusIdAndActive(id, true);
        if (Objects.nonNull(hdStatusMaster)) {
            hdStatusMaster.setActive(false);
            hdStatusMasterResponse = mapper.hdStatusMasterEntityToObject(hdStatusMasterRepository.save(hdStatusMaster), HdStatusMasterResponse.class);
            hdStatusMasterResponse.setError(false);
        } else {
            hdStatusMasterResponse.setError(true);
            hdStatusMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdStatusMasterResponse;
    }

    @Transactional
    public HdStatusMasterResponse getById(int id){
        HdStatusMasterResponse hdStatusMasterResponse = new HdStatusMasterResponse();
        HdStatusMaster hdStatusMaster = hdStatusMasterRepository.findByHdStatusIdAndActive(id,true);
        if(hdStatusMaster == null){
            hdStatusMasterResponse.setError(true);
            hdStatusMasterResponse.setError_description("Invalid id");
        }else{
            hdStatusMasterResponse =  mapper.hdStatusMasterEntityToObject(hdStatusMaster, HdStatusMasterResponse.class);
            hdStatusMasterResponse.setError(false);
        }
        log.info("Entity is ",hdStatusMaster);
        return hdStatusMasterResponse;
    }

    @Transactional
    public HdStatusMasterResponse updateHdStatusMasterDetails(EditHdStatusMasterRequest hdStatusMasterRequest){

        HdStatusMasterResponse hdStatusMasterResponse = new HdStatusMasterResponse();
        List<HdStatusMaster> hdStatusMasterList =  hdStatusMasterRepository.findByActiveAndHdStatusName(true,hdStatusMasterRequest.getHdStatusName());
        if(hdStatusMasterList.size()>0){
            hdStatusMasterResponse.setError(true);
            hdStatusMasterResponse.setError_description("StatusMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HdStatusMaster hdStatusMaster= hdStatusMasterRepository.findByHdStatusIdAndActiveIn(hdStatusMasterRequest.getHdStatusId(), Set.of(true,false));
            if(Objects.nonNull(hdStatusMaster)){
                hdStatusMaster.setHdStatusName(hdStatusMasterRequest.getHdStatusName());
                hdStatusMaster.setActive(true);
                HdStatusMaster hdStatusMaster1 = hdStatusMasterRepository.save(hdStatusMaster);
                hdStatusMasterResponse = mapper.hdStatusMasterEntityToObject(hdStatusMaster1, HdStatusMasterResponse.class);
                hdStatusMasterResponse.setError(false);
            } else {
                hdStatusMasterResponse.setError(true);
                hdStatusMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdStatusMasterResponse;
    }
}


