package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hdBoardCategoryMaster.EditHdBoardCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdBoardCategoryMaster.HdBoardCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdBoardCategoryMaster.HdBoardCategoryMasterResponse;
import com.sericulture.masterdata.model.api.hdCategoryMaster.EditHdCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterResponse;
import com.sericulture.masterdata.model.entity.HdBoardCategoryMaster;
import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdBoardCategoryMasterRepository;
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
public class HdBoardCategoryMasterService {
    @Autowired
    HdBoardCategoryMasterRepository hdBoardCategoryMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdBoardCategoryMasterResponse getHdBoardCategoryMasterDetails(String hdBoardCategoryName){
        HdBoardCategoryMasterResponse hdBoardCategoryMasterResponse = new HdBoardCategoryMasterResponse();
        HdBoardCategoryMaster hdBoardCategoryMaster = hdBoardCategoryMasterRepository.findByHdBoardCategoryNameAndActive(hdBoardCategoryName, true);
        if(hdBoardCategoryMaster==null){
            hdBoardCategoryMasterResponse.setError(true);
            hdBoardCategoryMasterResponse.setError_description("Hd BoardCategoryMaster not found");
        }else{
            hdBoardCategoryMasterResponse = mapper.hdBoardCategoryMasterEntityToObject(hdBoardCategoryMaster, HdBoardCategoryMasterResponse.class);
            hdBoardCategoryMasterResponse.setError(false);
        }
        log.info("Entity is ",hdBoardCategoryMaster);
        return hdBoardCategoryMasterResponse;

    }

    @Transactional
    public HdBoardCategoryMasterResponse insertHdBoardCategoryMasterDetails(HdBoardCategoryMasterRequest hdBoardCategoryMasterRequest){
        HdBoardCategoryMasterResponse hdBoardCategoryMasterResponse = new HdBoardCategoryMasterResponse();
        HdBoardCategoryMaster hdBoardCategoryMaster = mapper.hdBoardCategoryMasterObjectToEntity(hdBoardCategoryMasterRequest, HdBoardCategoryMaster.class);
        validator.validate(hdBoardCategoryMaster);
        List<HdBoardCategoryMaster> hdBoardCategoryMasterList= hdBoardCategoryMasterRepository.findByHdBoardCategoryName(hdBoardCategoryMasterRequest.getHdBoardCategoryName());
        if(!hdBoardCategoryMasterList.isEmpty() && hdBoardCategoryMasterList.stream().filter(HdBoardCategoryMaster::getActive).findAny().isPresent()){
            hdBoardCategoryMasterResponse.setError(true);
            hdBoardCategoryMasterResponse.setError_description("BoardCategory name already exist");
//        }
//        else if(!hdBoardCategoryMasterList.isEmpty() && hdBoardCategoryMasterList.stream().filter(Predicate.not(HdBoardCategoryMaster::getActive)).findAny().isPresent()){
//            hdBoardCategoryMasterResponse.setError(true);
//            hdBoardCategoryMasterResponse.setError_description("BoardCategory name already exist with inactive state");
        }else {
            hdBoardCategoryMasterResponse = mapper.hdBoardCategoryMasterEntityToObject(hdBoardCategoryMasterRepository.save(hdBoardCategoryMaster), HdBoardCategoryMasterResponse.class);
            hdBoardCategoryMasterResponse.setError(false);
        }
        return hdBoardCategoryMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdBoardCategoryMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdBoardCategoryMasterRepository.findByActiveOrderByHdBoardCategoryNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdBoardCategoryMasterRepository.findByActiveOrderByHdBoardCategoryNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdBoardCategoryMaster> activeHdBoardCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdBoardCategoryMasterResponse> hdBoardCategoryMasterResponses = activeHdBoardCategoryMasters.getContent().stream()
                .map(hdBoardCategoryMaster -> mapper.hdBoardCategoryMasterEntityToObject(hdBoardCategoryMaster, HdBoardCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdBoardCategoryMaster",hdBoardCategoryMasterResponses);
        response.put("currentPage", activeHdBoardCategoryMasters.getNumber());
        response.put("totalItems", activeHdBoardCategoryMasters.getTotalElements());
        response.put("totalPages", activeHdBoardCategoryMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdBoardCategoryMaster> activeHdBoardCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdBoardCategoryMasterResponse> hdBoardCategoryMasterResponses = activeHdBoardCategoryMasters.stream()
                .map(hdBoardCategoryMaster -> mapper.hdBoardCategoryMasterEntityToObject(hdBoardCategoryMaster, HdBoardCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdBoardCategoryMaster",hdBoardCategoryMasterResponses);
        return response;
    }

    @Transactional
    public HdBoardCategoryMasterResponse deleteHdBoardCategoryMasterDetails(long id) {

        HdBoardCategoryMasterResponse hdBoardCategoryMasterResponse= new HdBoardCategoryMasterResponse();
        HdBoardCategoryMaster hdBoardCategoryMaster = hdBoardCategoryMasterRepository.findByHdBoardCategoryIdAndActive(id, true);
        if (Objects.nonNull(hdBoardCategoryMaster)) {
            hdBoardCategoryMaster.setActive(false);
            hdBoardCategoryMasterResponse = mapper.hdBoardCategoryMasterEntityToObject(hdBoardCategoryMasterRepository.save(hdBoardCategoryMaster), HdBoardCategoryMasterResponse.class);
            hdBoardCategoryMasterResponse.setError(false);
        } else {
            hdBoardCategoryMasterResponse.setError(true);
            hdBoardCategoryMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdBoardCategoryMasterResponse;
    }

    @Transactional
    public HdBoardCategoryMasterResponse getById(int id){
        HdBoardCategoryMasterResponse hdBoardCategoryMasterResponse = new HdBoardCategoryMasterResponse();
        HdBoardCategoryMaster hdBoardCategoryMaster = hdBoardCategoryMasterRepository.findByHdBoardCategoryIdAndActive(id,true);
        if(hdBoardCategoryMaster == null){
            hdBoardCategoryMasterResponse.setError(true);
            hdBoardCategoryMasterResponse.setError_description("Invalid id");
        }else{
            hdBoardCategoryMasterResponse =  mapper.hdBoardCategoryMasterEntityToObject(hdBoardCategoryMaster, HdBoardCategoryMasterResponse.class);
            hdBoardCategoryMasterResponse.setError(false);
        }
        log.info("Entity is ",hdBoardCategoryMaster);
        return hdBoardCategoryMasterResponse;
    }

    @Transactional
    public HdBoardCategoryMasterResponse updateHdBoardCategoryMasterDetails(EditHdBoardCategoryMasterRequest hdBoardCategoryMasterRequest){

        HdBoardCategoryMasterResponse hdBoardCategoryMasterResponse = new HdBoardCategoryMasterResponse();
        List<HdBoardCategoryMaster> hdBoardCategoryMasterList =  hdBoardCategoryMasterRepository.findByActiveAndHdBoardCategoryName(true,hdBoardCategoryMasterRequest.getHdBoardCategoryName());
        if(hdBoardCategoryMasterList.size()>0){
            hdBoardCategoryMasterResponse.setError(true);
            hdBoardCategoryMasterResponse.setError_description("Board categoryMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HdBoardCategoryMaster hdBoardCategoryMaster= hdBoardCategoryMasterRepository.findByHdBoardCategoryIdAndActiveIn(hdBoardCategoryMasterRequest.getHdBoardCategoryId(), Set.of(true,false));
            if(Objects.nonNull(hdBoardCategoryMaster)){
                hdBoardCategoryMaster.setHdBoardCategoryName(hdBoardCategoryMasterRequest.getHdBoardCategoryName());
                hdBoardCategoryMaster.setActive(true);
                HdBoardCategoryMaster hdBoardCategoryMaster1 = hdBoardCategoryMasterRepository.save(hdBoardCategoryMaster);
                hdBoardCategoryMasterResponse = mapper.hdBoardCategoryMasterEntityToObject(hdBoardCategoryMaster1, HdBoardCategoryMasterResponse.class);
                hdBoardCategoryMasterResponse.setError(false);
            } else {
                hdBoardCategoryMasterResponse.setError(true);
                hdBoardCategoryMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdBoardCategoryMasterResponse;
    }
}
