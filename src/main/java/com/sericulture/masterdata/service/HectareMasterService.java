package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hectareMaster.EditHectareMasterRequest;
import com.sericulture.masterdata.model.api.hectareMaster.HectareMasterRequest;
import com.sericulture.masterdata.model.api.hectareMaster.HectareMasterResponse;
import com.sericulture.masterdata.model.entity.HectareMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HectareMasterRepository;
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
public class HectareMasterService {

    @Autowired
    HectareMasterRepository hectareMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public HectareMasterResponse insertHectareMasterDetails(HectareMasterRequest hectareMasterRequest){
        HectareMasterResponse hectareMasterResponse = new HectareMasterResponse();
        HectareMaster hectareMaster = mapper.hectareMasterObjectToEntity(hectareMasterRequest,HectareMaster.class);
        validator.validate(hectareMaster);
        List<HectareMaster> hectareMasterList= hectareMasterRepository.findByHectareName(hectareMasterRequest.getHectareName());
        if(!hectareMasterList.isEmpty() && hectareMasterList.stream().filter(HectareMaster::getActive).findAny().isPresent()){
            hectareMasterResponse.setError(true);
            hectareMasterResponse.setError_description("HectareMaster name already exist");
//        }
//        else if(!hectareMasterList.isEmpty() && hectareMasterList.stream().filter(Predicate.not(HectareMaster::getActive)).findAny().isPresent()){
//            hectareMasterResponse.setError(true);
//            hectareMasterResponse.setError_description("hectareMaster name already exist with inactive state");
        }else {
            hectareMasterResponse = mapper.hectareMasterEntityToObject(hectareMasterRepository.save(hectareMaster), HectareMasterResponse.class);
            hectareMasterResponse.setError(false);
        }
        return hectareMasterResponse;
    }

    public Map<String,Object> getPaginatedHectareMasterDetails(final Pageable pageable){
        return convertToMapResponse(hectareMasterRepository.findByActiveOrderByHectareNameAsc( true,pageable ));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hectareMasterRepository.findByActiveOrderByHectareNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HectareMaster> activeHectareMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HectareMasterResponse> hectareMasterResponses = activeHectareMasters.getContent().stream()
                .map(hectareMaster -> mapper.hectareMasterEntityToObject(hectareMaster,HectareMasterResponse.class)).collect(Collectors.toList());
        response.put("hectareMaster",hectareMasterResponses);
        response.put("currentPage", activeHectareMasters.getNumber());
        response.put("totalItems", activeHectareMasters.getTotalElements());
        response.put("totalPages", activeHectareMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HectareMaster> activeHectareMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HectareMasterResponse> hectareMasterResponses = activeHectareMasters.stream()
                .map(hectareMaster -> mapper.hectareMasterEntityToObject(hectareMaster,HectareMasterResponse.class)).collect(Collectors.toList());
        response.put("hectareMaster",hectareMasterResponses);
        return response;
    }

    @Transactional
    public HectareMasterResponse deleteHectareMasterDetails(long id) {

        HectareMasterResponse hectareMasterResponse= new HectareMasterResponse();
        HectareMaster hectareMaster = hectareMasterRepository.findByHectareIdAndActive(id, true);
        if (Objects.nonNull(hectareMaster)) {
            hectareMaster.setActive(false);
            hectareMasterResponse = mapper.hectareMasterEntityToObject(hectareMasterRepository.save(hectareMaster), HectareMasterResponse.class);
            hectareMasterResponse.setError(false);
        } else {
            hectareMasterResponse.setError(true);
            hectareMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hectareMasterResponse;
    }

    public HectareMasterResponse getById(int id){
        HectareMasterResponse hectareMasterResponse = new HectareMasterResponse();
        HectareMaster hectareMaster = hectareMasterRepository.findByHectareIdAndActive(id,true);
        if(hectareMaster == null){
            hectareMasterResponse.setError(true);
            hectareMasterResponse.setError_description("Invalid id");
        }else{
            hectareMasterResponse =  mapper.hectareMasterEntityToObject(hectareMaster,HectareMasterResponse.class);
            hectareMasterResponse.setError(false);
        }
        log.info("Entity is ",hectareMaster);
        return hectareMasterResponse;
    }

    @Transactional
    public HectareMasterResponse updateHectareMasterDetails(EditHectareMasterRequest hectareMasterRequest){

        HectareMasterResponse hectareMasterResponse = new HectareMasterResponse();
        List<HectareMaster> hectareMasterList = hectareMasterRepository.findByHectareNameAndHectareIdIsNot(hectareMasterRequest.getHectareName(),hectareMasterRequest.getHectareId());
        if(hectareMasterList.size()>0){
            hectareMasterResponse.setError(true);
            hectareMasterResponse.setError_description("HectareMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HectareMaster hectareMaster= hectareMasterRepository.findByHectareIdAndActiveIn(hectareMasterRequest.getHectareId(), Set.of(true,false));
            if(Objects.nonNull(hectareMaster)){
                hectareMaster.setHectareName(hectareMasterRequest.getHectareName());
                hectareMaster.setActive(true);
                HectareMaster hectareMaster1 = hectareMasterRepository.save(hectareMaster);
                hectareMasterResponse = mapper.hectareMasterEntityToObject(hectareMaster1, HectareMasterResponse.class);
                hectareMasterResponse.setError(false);
            } else {
                hectareMasterResponse.setError(true);
                hectareMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hectareMasterResponse;
    }
}
