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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TraderTypeMasterResponse getTraderTypeMasterDetails(String traderTypeMasterName){
        TraderTypeMaster traderTypeMaster = null;
        if(traderTypeMaster==null){
            traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterNameAndActive(traderTypeMasterName,true);
        }
        log.info("Entity is ",traderTypeMaster);
        return mapper.traderTypeMasterEntityToObject(traderTypeMaster,TraderTypeMasterResponse.class);
    }

    @Transactional
    public TraderTypeMasterResponse insertTraderTypeMasterDetails(TraderTypeMasterRequest traderTypeMasterRequest){
        TraderTypeMaster traderTypeMaster = mapper.traderTypeMasterObjectToEntity(traderTypeMasterRequest,TraderTypeMaster.class);
        validator.validate(traderTypeMaster);
        List<TraderTypeMaster> traderTypeMasterList = traderTypeMasterRepository.findByTraderTypeMasterName(traderTypeMasterRequest.getTraderTypeMasterName());
        if(!traderTypeMasterList.isEmpty() && traderTypeMasterList.stream().filter(TraderTypeMaster::getActive).findAny().isPresent()){
            throw new ValidationException("TraderType name already exist");
        }
        if(!traderTypeMasterList.isEmpty() && traderTypeMasterList.stream().filter(Predicate.not(TraderTypeMaster::getActive)).findAny().isPresent()){
            throw new ValidationException("TraderType name already exist with inactive state");
        }
        return mapper.traderTypeMasterEntityToObject(traderTypeMasterRepository.save(traderTypeMaster),TraderTypeMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTraderTypeMasterDetails(final Pageable pageable){
        return convertToMapResponse(traderTypeMasterRepository.findByActiveOrderByTraderTypeMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(traderTypeMasterRepository.findByActive(isActive));
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
    public void deleteTraderTypeMasterDetails(long id) {
        TraderTypeMaster traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterIdAndActive(id, true);
        if (Objects.nonNull(traderTypeMaster)) {
            traderTypeMaster.setActive(false);
            traderTypeMasterRepository.save(traderTypeMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public TraderTypeMasterResponse getById(int id){
        TraderTypeMaster traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterIdAndActive(id,true);
        if(traderTypeMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",traderTypeMaster);
        return mapper.traderTypeMasterEntityToObject(traderTypeMaster,TraderTypeMasterResponse.class);
    }

    @Transactional
    public TraderTypeMasterResponse updateTraderTypeMasterDetails(EditTraderTypeMasterRequest traderTypeMasterRequest){
        List<TraderTypeMaster> traderTypeMasterList = traderTypeMasterRepository.findByTraderTypeMasterName(traderTypeMasterRequest.getTraderTypeMasterName());
        if(traderTypeMasterList.size()>0){
            throw new ValidationException("Trader already exists with this name, duplicates are not allowed.");
        }

        TraderTypeMaster traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterIdAndActiveIn(traderTypeMasterRequest.getTraderTypeMasterId(), Set.of(true,false));
        if(Objects.nonNull(traderTypeMaster)){
            traderTypeMaster.setTraderTypeMasterName(traderTypeMasterRequest.getTraderTypeMasterName());
            traderTypeMaster.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching state");
        }
        return mapper.traderTypeMasterEntityToObject(traderTypeMasterRepository.save(traderTypeMaster),TraderTypeMasterResponse.class);
    }

}
