package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.raceMaster.EditRaceMasterRequest;
import com.sericulture.masterdata.model.api.raceMaster.RaceMasterRequest;
import com.sericulture.masterdata.model.api.raceMaster.RaceMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.RaceMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RaceMasterRepository;
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
public class RaceMasterService {
    @Autowired
    RaceMasterRepository raceMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RaceMasterResponse getRaceMasterDetails(String raceMasterName){
        RaceMasterResponse raceMasterResponse = new RaceMasterResponse();
        RaceMaster raceMaster = null;
        if(raceMaster==null){
            raceMaster = raceMasterRepository.findByRaceMasterNameAndActive(raceMasterName,true);
            raceMasterResponse = mapper.raceMasterEntityToObject(raceMaster, RaceMasterResponse.class);
            raceMasterResponse.setError(false);
        }else{
            raceMasterResponse.setError(true);
            raceMasterResponse.setError_description("Race not found");
        }
        log.info("Entity is ",raceMaster);
        return raceMasterResponse;
    }

    @Transactional
    public RaceMasterResponse insertRaceMasterDetails(RaceMasterRequest raceMasterRequest){
        RaceMasterResponse raceMasterResponse = new RaceMasterResponse();
        RaceMaster raceMaster = mapper.raceMasterObjectToEntity(raceMasterRequest,RaceMaster.class);
        validator.validate(raceMaster);
        List<RaceMaster> raceMasterList = raceMasterRepository.findByRaceMasterName(raceMasterRequest.getRaceMasterName());
        if(!raceMasterList.isEmpty() && raceMasterList.stream().filter(RaceMaster::getActive).findAny().isPresent()){
            raceMasterResponse.setError(true);
            raceMasterResponse.setError_description("Race name already exist");
        }
        else if(!raceMasterList.isEmpty() && raceMasterList.stream().filter(Predicate.not(RaceMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            raceMasterResponse.setError(true);
            raceMasterResponse.setError_description("Race name already exist with inactive state");
        }else {
            raceMasterResponse = mapper.raceMasterEntityToObject(raceMasterRepository.save(raceMaster), RaceMasterResponse.class);
            raceMasterResponse.setError(false);
        }
        return raceMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRaceMasterDetails(final Pageable pageable){
        return convertToMapResponse(raceMasterRepository.findByActiveOrderByRaceMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(raceMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<RaceMaster> activeRaces) {
        Map<String, Object> response = new HashMap<>();

        List<RaceMasterResponse> raceMasterResponses = activeRaces.getContent().stream()
                .map(raceMaster -> mapper.raceMasterEntityToObject(raceMaster,RaceMasterResponse.class)).collect(Collectors.toList());
        response.put("raceMaster",raceMasterResponses);
        response.put("currentPage", activeRaces.getNumber());
        response.put("totalItems", activeRaces.getTotalElements());
        response.put("totalPages", activeRaces.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RaceMaster> activeRaces) {
        Map<String, Object> response = new HashMap<>();

        List<RaceMasterResponse> raceMasterResponses = activeRaces.stream()
                .map(raceMaster -> mapper.raceMasterEntityToObject(raceMaster,RaceMasterResponse.class)).collect(Collectors.toList());
        response.put("raceMaster",raceMasterResponses);
        return response;
    }

    @Transactional
    public RaceMasterResponse deleteRaceMasterDetails(long id) {
        RaceMasterResponse raceMasterResponse = new RaceMasterResponse();
        RaceMaster raceMaster = raceMasterRepository.findByRaceMasterIdAndActive(id, true);
        if (Objects.nonNull(raceMaster)) {
            raceMaster.setActive(false);
            raceMasterResponse = mapper.raceMasterEntityToObject(raceMasterRepository.save(raceMaster), RaceMasterResponse.class);
            raceMasterResponse.setError(false);
        } else {
            raceMasterResponse.setError(true);
            raceMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return raceMasterResponse;
    }

    @Transactional
    public RaceMasterResponse getById(int id){
        RaceMaster raceMaster = raceMasterRepository.findByRaceMasterIdAndActive(id,true);
        if(raceMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",raceMaster);
        return mapper.raceMasterEntityToObject(raceMaster,RaceMasterResponse.class);
    }

    @Transactional
    public RaceMasterResponse updateRaceMasterDetails(EditRaceMasterRequest raceMasterRequest) {
        RaceMasterResponse raceMasterResponse = new RaceMasterResponse();
        List<RaceMaster> raceMasterList = raceMasterRepository.findByRaceMasterName(raceMasterRequest.getRaceMasterName());
        if (raceMasterList.size() > 0) {
            raceMasterResponse.setError(true);
            raceMasterResponse.setError_description("Race already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            RaceMaster raceMaster = raceMasterRepository.findByRaceMasterIdAndActiveIn(raceMasterRequest.getRaceMasterId(), Set.of(true, false));
            if (Objects.nonNull(raceMaster)) {
                raceMaster.setRaceMasterName(raceMasterRequest.getRaceMasterName());
                raceMaster.setActive(true);
                RaceMaster raceMaster1 = raceMasterRepository.save(raceMaster);
                raceMasterResponse = mapper.raceMasterEntityToObject(raceMaster1, RaceMasterResponse.class);
                raceMasterResponse.setError(false);
            } else {
                raceMasterResponse.setError(true);
                raceMasterResponse.setError_description("Error occurred while fetching Race");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return raceMasterResponse;
    }
}
