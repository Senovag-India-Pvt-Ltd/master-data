package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.traderTypeMaster.EditTraderTypeMasterRequest;
import com.sericulture.masterdata.model.api.traderTypeMaster.TraderTypeMasterRequest;
import com.sericulture.masterdata.model.api.traderTypeMaster.TraderTypeMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.TraderTypeMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TraderTypeMasterRepository;
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
public class TraderTypeMasterService {
    @Autowired
    TraderTypeMasterRepository traderTypeMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TraderTypeMasterResponse insertTraderTypeMasterDetails(TraderTypeMasterRequest traderTypeMasterRequest){
        TraderTypeMasterResponse traderTypeMasterResponse = new TraderTypeMasterResponse();
        TraderTypeMaster traderTypeMaster = mapper.traderTypeMasterObjectToEntity(traderTypeMasterRequest,TraderTypeMaster.class);
        validator.validate(traderTypeMaster);
        List<TraderTypeMaster> traderTypeMasterList = traderTypeMasterRepository.findByTraderTypeMasterNameAndTraderTypeNameInKannada(traderTypeMasterRequest.getTraderTypeMasterName(), traderTypeMasterRequest.getTraderTypeNameInKannada());
        if(!traderTypeMasterList.isEmpty() && traderTypeMasterList.stream().filter(TraderTypeMaster::getActive).findAny().isPresent()){
            traderTypeMasterResponse.setError(true);
            traderTypeMasterResponse.setError_description("TraderType name already exist");
//        }
//        else if(!traderTypeMasterList.isEmpty() && traderTypeMasterList.stream().filter(Predicate.not(TraderTypeMaster::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            traderTypeMasterResponse.setError(true);
//            traderTypeMasterResponse.setError_description("TraderType name already exist with inactive state");
        }else {
            traderTypeMasterResponse = mapper.traderTypeMasterEntityToObject(traderTypeMasterRepository.save(traderTypeMaster), TraderTypeMasterResponse.class);
            traderTypeMasterResponse.setError(false);
        }
        return traderTypeMasterResponse;
    }

    public Map<String,Object> getPaginatedTraderTypeMasterDetails(final Pageable pageable){
        return convertToMapResponse(traderTypeMasterRepository.findByActiveOrderByTraderTypeMasterNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(traderTypeMasterRepository.findByActiveOrderByTraderTypeMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TraderTypeMaster> activeTraderTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TraderTypeMasterResponse> traderTypeMasterResponses = activeTraderTypeMasters.getContent().stream()
                .map(traderTypeMaster -> mapper.traderTypeMasterEntityToObject(traderTypeMaster,TraderTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("traderTypeMaster",traderTypeMasterResponses);
        response.put("currentPage", activeTraderTypeMasters.getNumber());
        response.put("totalItems", activeTraderTypeMasters.getTotalElements());
        response.put("totalPages", activeTraderTypeMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TraderTypeMaster> activeTraderTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TraderTypeMasterResponse> traderTypeMasterResponses = activeTraderTypeMasters.stream()
                .map(traderTypeMaster -> mapper.traderTypeMasterEntityToObject(traderTypeMaster,TraderTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("traderTypeMaster",traderTypeMasterResponses);
        return response;
    }

    @Transactional
    public TraderTypeMasterResponse deleteTraderTypeMasterDetails(long id) {
        TraderTypeMasterResponse traderTypeMasterResponse = new TraderTypeMasterResponse();
        TraderTypeMaster traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterIdAndActive(id, true);
        if (Objects.nonNull(traderTypeMaster)) {
            traderTypeMaster.setActive(false);
            traderTypeMasterResponse = mapper.traderTypeMasterEntityToObject(traderTypeMasterRepository.save(traderTypeMaster), TraderTypeMasterResponse.class);
            traderTypeMasterResponse.setError(false);
        } else {
            traderTypeMasterResponse.setError(true);
            traderTypeMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return traderTypeMasterResponse;
    }


    public TraderTypeMasterResponse getById(int id){
        TraderTypeMasterResponse traderTypeMasterResponse = new TraderTypeMasterResponse();
        TraderTypeMaster traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterIdAndActive(id,true);
        if(traderTypeMaster == null){
            traderTypeMasterResponse.setError(true);
            traderTypeMasterResponse.setError_description("Invalid id");
        }else{
            traderTypeMasterResponse =  mapper.traderTypeMasterEntityToObject(traderTypeMaster,TraderTypeMasterResponse.class);
            traderTypeMasterResponse.setError(false);
        }
        log.info("Entity is ",traderTypeMaster);
        return traderTypeMasterResponse;
    }

    @Transactional
    public TraderTypeMasterResponse updateTraderTypeMasterDetails(EditTraderTypeMasterRequest traderTypeMasterRequest){
        TraderTypeMasterResponse traderTypeMasterResponse = new TraderTypeMasterResponse();
        List<TraderTypeMaster> traderTypeMasterList = traderTypeMasterRepository.findByActiveAndTraderTypeMasterNameAndTraderTypeNameInKannada(true,traderTypeMasterRequest.getTraderTypeMasterName(), traderTypeMasterRequest.getTraderTypeNameInKannada());
        if(traderTypeMasterList.size()>0){
            traderTypeMasterResponse.setError(true);
            traderTypeMasterResponse.setError_description("TraderType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {

        TraderTypeMaster traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterIdAndActiveIn(traderTypeMasterRequest.getTraderTypeMasterId(), Set.of(true,false));
        if(Objects.nonNull(traderTypeMaster)){
            traderTypeMaster.setTraderTypeMasterName(traderTypeMasterRequest.getTraderTypeMasterName());
            traderTypeMaster.setTraderTypeNameInKannada(traderTypeMasterRequest.getTraderTypeNameInKannada());
            traderTypeMaster.setActive(true);
            TraderTypeMaster traderTypeMaster1 = traderTypeMasterRepository.save(traderTypeMaster);
            traderTypeMasterResponse = mapper.traderTypeMasterEntityToObject(traderTypeMaster1, TraderTypeMasterResponse.class);
            traderTypeMasterResponse.setError(false);
        } else {
            traderTypeMasterResponse.setError(true);
            traderTypeMasterResponse.setError_description("Error occurred while fetching Trader Type");
            // throw new ValidationException("Error occurred while fetching village");
        }
        }
        return traderTypeMasterResponse;
    }

}
