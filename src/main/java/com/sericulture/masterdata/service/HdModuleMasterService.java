package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hdBoardCategoryMaster.EditHdBoardCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdBoardCategoryMaster.HdBoardCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdBoardCategoryMaster.HdBoardCategoryMasterResponse;
import com.sericulture.masterdata.model.api.hdModuleMaster.EditHdModuleMasterRequest;
import com.sericulture.masterdata.model.api.hdModuleMaster.HdModuleMasterRequest;
import com.sericulture.masterdata.model.api.hdModuleMaster.HdModuleMasterResponse;
import com.sericulture.masterdata.model.entity.HdBoardCategoryMaster;
import com.sericulture.masterdata.model.entity.HdModuleMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdBoardCategoryMasterRepository;
import com.sericulture.masterdata.repository.HdModuleMasterRepository;
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
public class HdModuleMasterService {
    @Autowired
    HdModuleMasterRepository hdModuleMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdModuleMasterResponse getHdModuleMasterDetails(String hdModuleName){
        HdModuleMasterResponse hdModuleMasterResponse = new HdModuleMasterResponse();
        HdModuleMaster hdModuleMaster = null;
        if(hdModuleMaster==null){
            hdModuleMaster = hdModuleMasterRepository.findByHdModuleNameAndActive(hdModuleName, true);
            hdModuleMasterResponse = mapper.hdModuleMasterEntityToObject(hdModuleMaster, HdModuleMasterResponse.class);
            hdModuleMasterResponse.setError(false);
        }else{
            hdModuleMasterResponse.setError(true);
            hdModuleMasterResponse.setError_description("Hd ModuleMaster not found");
        }
        log.info("Entity is ",hdModuleMaster);
        return hdModuleMasterResponse;

    }

    @Transactional
    public HdModuleMasterResponse insertHdModuleMasterDetails(HdModuleMasterRequest hdModuleMasterRequest){
        HdModuleMasterResponse hdModuleMasterResponse = new HdModuleMasterResponse();
        HdModuleMaster hdModuleMaster = mapper.hdModuleMasterObjectToEntity(hdModuleMasterRequest, HdModuleMaster.class);
        validator.validate(hdModuleMaster);
        List<HdModuleMaster> hdModuleMasterList= hdModuleMasterRepository.findByHdModuleName(hdModuleMasterRequest.getHdModuleName());
        if(!hdModuleMasterList.isEmpty() && hdModuleMasterList.stream().filter(HdModuleMaster::getActive).findAny().isPresent()){
            hdModuleMasterResponse.setError(true);
            hdModuleMasterResponse.setError_description("Module name already exist");
        }
        else if(!hdModuleMasterList.isEmpty() && hdModuleMasterList.stream().filter(Predicate.not(HdModuleMaster::getActive)).findAny().isPresent()){
            hdModuleMasterResponse.setError(true);
            hdModuleMasterResponse.setError_description("Module name already exist with inactive state");
        }else {
            hdModuleMasterResponse = mapper.hdModuleMasterEntityToObject(hdModuleMasterRepository.save(hdModuleMaster), HdModuleMasterResponse.class);
            hdModuleMasterResponse.setError(false);
        }
        return hdModuleMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdModuleMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdModuleMasterRepository.findByActiveOrderByHdModuleNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdModuleMasterRepository.findByActiveOrderByHdModuleNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdModuleMaster> activeHdModuleMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdModuleMasterResponse> hdModuleMasterResponses = activeHdModuleMasters.getContent().stream()
                .map(hdModuleMaster -> mapper.hdModuleMasterEntityToObject(hdModuleMaster, HdModuleMasterResponse.class)).collect(Collectors.toList());
        response.put("hdModuleMaster",hdModuleMasterResponses);
        response.put("currentPage", activeHdModuleMasters.getNumber());
        response.put("totalItems", activeHdModuleMasters.getTotalElements());
        response.put("totalPages", activeHdModuleMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdModuleMaster> activeHdModuleMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdModuleMasterResponse> hdModuleMasterResponses = activeHdModuleMasters.stream()
                .map(hdModuleMaster -> mapper.hdModuleMasterEntityToObject(hdModuleMaster, HdModuleMasterResponse.class)).collect(Collectors.toList());
        response.put("hdModuleMaster",hdModuleMasterResponses);
        return response;
    }

    @Transactional
    public HdModuleMasterResponse deleteHdModuleMasterDetails(long id) {

        HdModuleMasterResponse hdModuleMasterResponse= new HdModuleMasterResponse();
        HdModuleMaster hdModuleMaster = hdModuleMasterRepository.findByHdModuleIdAndActive(id, true);
        if (Objects.nonNull(hdModuleMaster)) {
            hdModuleMaster.setActive(false);
            hdModuleMasterResponse = mapper.hdModuleMasterEntityToObject(hdModuleMasterRepository.save(hdModuleMaster), HdModuleMasterResponse.class);
            hdModuleMasterResponse.setError(false);
        } else {
            hdModuleMasterResponse.setError(true);
            hdModuleMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdModuleMasterResponse;
    }

    @Transactional
    public HdModuleMasterResponse getById(int id){
        HdModuleMasterResponse hdModuleMasterResponse = new HdModuleMasterResponse();
        HdModuleMaster hdModuleMaster = hdModuleMasterRepository.findByHdModuleIdAndActive(id,true);
        if(hdModuleMaster == null){
            hdModuleMasterResponse.setError(true);
            hdModuleMasterResponse.setError_description("Invalid id");
        }else{
            hdModuleMasterResponse =  mapper.hdModuleMasterEntityToObject(hdModuleMaster, HdModuleMasterResponse.class);
            hdModuleMasterResponse.setError(false);
        }
        log.info("Entity is ",hdModuleMaster);
        return hdModuleMasterResponse;
    }

    @Transactional
    public HdModuleMasterResponse updateHdModuleMasterDetails(EditHdModuleMasterRequest hdModuleMasterRequest){

        HdModuleMasterResponse hdModuleMasterResponse = new HdModuleMasterResponse();
        List<HdModuleMaster> hdModuleMasterList =  hdModuleMasterRepository.findByHdModuleName(hdModuleMasterRequest.getHdModuleName());
        if(hdModuleMasterList.size()>0){
            hdModuleMasterResponse.setError(true);
            hdModuleMasterResponse.setError_description("ModuleMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HdModuleMaster hdModuleMaster= hdModuleMasterRepository.findByHdModuleIdAndActiveIn(hdModuleMasterRequest.getHdModuleId(), Set.of(true,false));
            if(Objects.nonNull(hdModuleMaster)){
                hdModuleMaster.setHdModuleName(hdModuleMasterRequest.getHdModuleName());
                hdModuleMaster.setActive(true);
                HdModuleMaster hdModuleMaster1 = hdModuleMasterRepository.save(hdModuleMaster);
                hdModuleMasterResponse = mapper.hdModuleMasterEntityToObject(hdModuleMaster1, HdModuleMasterResponse.class);
                hdModuleMasterResponse.setError(false);
            } else {
                hdModuleMasterResponse.setError(true);
                hdModuleMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdModuleMasterResponse;
    }
}


