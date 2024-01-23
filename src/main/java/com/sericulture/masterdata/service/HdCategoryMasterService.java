package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterResponse;
import com.sericulture.masterdata.model.api.hdCategoryMaster.EditHdCategoryMasterRequest;
import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdCategoryMasterRepository;
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
public class HdCategoryMasterService {
    @Autowired
    HdCategoryMasterRepository hdCategoryMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdCategoryMasterResponse getHdCategoryMasterDetails(String hdCategoryName){
        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        HdCategoryMaster hdCategoryMaster = null;
        if(hdCategoryMaster==null){
            hdCategoryMaster = hdCategoryMasterRepository.findByHdCategoryNameAndActive(hdCategoryName, true);
            hdCategoryMasterResponse = mapper.hdCategoryMasterEntityToObject(hdCategoryMaster, HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        }else{
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Hd CategoryMaster not found");
        }
        log.info("Entity is ",hdCategoryMaster);
        return hdCategoryMasterResponse;

    }

    @Transactional
    public HdCategoryMasterResponse insertHdCategoryMasterDetails(HdCategoryMasterRequest hdCategoryMasterRequest){
        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        HdCategoryMaster hdCategoryMaster = mapper.hdCategoryMasterObjectToEntity(hdCategoryMasterRequest, HdCategoryMaster.class);
        validator.validate(hdCategoryMaster);
        List<HdCategoryMaster> hdCategoryMasterList= hdCategoryMasterRepository.findByHdCategoryName(hdCategoryMasterRequest.getHdCategoryName());
        if(!hdCategoryMasterList.isEmpty() && hdCategoryMasterList.stream().filter(HdCategoryMaster::getActive).findAny().isPresent()){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Category name already exist");
        }
        else if(!hdCategoryMasterList.isEmpty() && hdCategoryMasterList.stream().filter(Predicate.not(HdCategoryMaster::getActive)).findAny().isPresent()){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Category name already exist with inactive state");
        }else {
            hdCategoryMasterResponse = mapper.hdCategoryMasterEntityToObject(hdCategoryMasterRepository.save(hdCategoryMaster), HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        }
        return hdCategoryMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdCategoryMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdCategoryMasterRepository.findByActiveOrderByHdCategoryNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdCategoryMasterRepository.findByActiveOrderByHdCategoryNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdCategoryMaster> activeHdCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdCategoryMasterResponse> hdCategoryMasterResponses = activeHdCategoryMasters.getContent().stream()
                .map(hdCategoryMaster -> mapper.hdCategoryMasterEntityToObject(hdCategoryMaster, HdCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdCategoryMaster",hdCategoryMasterResponses);
        response.put("currentPage", activeHdCategoryMasters.getNumber());
        response.put("totalItems", activeHdCategoryMasters.getTotalElements());
        response.put("totalPages", activeHdCategoryMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdCategoryMaster> activeHdCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdCategoryMasterResponse> hdCategoryMasterResponses = activeHdCategoryMasters.stream()
                .map(hdCategoryMaster -> mapper.hdCategoryMasterEntityToObject(hdCategoryMaster, HdCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdCategoryMaster",hdCategoryMasterResponses);
        return response;
    }

    @Transactional
    public HdCategoryMasterResponse deleteHdCategoryMasterDetails(long id) {

        HdCategoryMasterResponse hdCategoryMasterResponse= new HdCategoryMasterResponse();
        HdCategoryMaster hdCategoryMaster = hdCategoryMasterRepository.findByHdCategoryIdAndActive(id, true);
        if (Objects.nonNull(hdCategoryMaster)) {
            hdCategoryMaster.setActive(false);
            hdCategoryMasterResponse = mapper.hdCategoryMasterEntityToObject(hdCategoryMasterRepository.save(hdCategoryMaster), HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        } else {
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdCategoryMasterResponse;
    }

    @Transactional
    public HdCategoryMasterResponse getById(int id){
        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        HdCategoryMaster hdCategoryMaster = hdCategoryMasterRepository.findByHdCategoryIdAndActive(id,true);
        if(hdCategoryMaster == null){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Invalid id");
        }else{
            hdCategoryMasterResponse =  mapper.hdCategoryMasterEntityToObject(hdCategoryMaster, HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        }
        log.info("Entity is ",hdCategoryMaster);
        return hdCategoryMasterResponse;
    }

    @Transactional
    public HdCategoryMasterResponse updateHdCategoryMasterDetails(EditHdCategoryMasterRequest hdCategoryMasterRequest){

        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        List<HdCategoryMaster> hdCategoryMasterList =  hdCategoryMasterRepository.findByHdCategoryName(hdCategoryMasterRequest.getHdCategoryName());
        if(hdCategoryMasterList.size()>0){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("categoryMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HdCategoryMaster hdCategoryMaster= hdCategoryMasterRepository.findByHdCategoryIdAndActiveIn(hdCategoryMasterRequest.getHdCategoryId(), Set.of(true,false));
            if(Objects.nonNull(hdCategoryMaster)){
                hdCategoryMaster.setHdCategoryName(hdCategoryMasterRequest.getHdCategoryName());
                hdCategoryMaster.setActive(true);
                HdCategoryMaster hdCategoryMaster1 = hdCategoryMasterRepository.save(hdCategoryMaster);
                hdCategoryMasterResponse = mapper.hdCategoryMasterEntityToObject(hdCategoryMaster1, HdCategoryMasterResponse.class);
                hdCategoryMasterResponse.setError(false);
            } else {
                hdCategoryMasterResponse.setError(true);
                hdCategoryMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdCategoryMasterResponse;
    }
}
