package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.crateMaster.CrateMasterRequest;
import com.sericulture.masterdata.model.api.crateMaster.CrateMasterResponse;
import com.sericulture.masterdata.model.api.crateMaster.EditCrateMasterRequest;
import com.sericulture.masterdata.model.api.raceMarketMaster.EditRaceMarketMasterRequest;
import com.sericulture.masterdata.model.api.raceMarketMaster.RaceMarketMasterRequest;
import com.sericulture.masterdata.model.api.raceMarketMaster.RaceMarketMasterResponse;
import com.sericulture.masterdata.model.api.raceMaster.RaceMasterResponse;
import com.sericulture.masterdata.model.dto.CrateMasterDTO;
import com.sericulture.masterdata.model.dto.RaceMarketMasterDTO;
import com.sericulture.masterdata.model.entity.CrateMaster;
import com.sericulture.masterdata.model.entity.RaceMarketMaster;
import com.sericulture.masterdata.model.entity.RaceMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.CrateMasterRepository;
import com.sericulture.masterdata.repository.RaceMarketMasterRepository;
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
public class RaceMarketMasterService {
    @Autowired
    RaceMarketMasterRepository raceMarketMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public RaceMarketMasterResponse insertRaceMarketMasterDetails(RaceMarketMasterRequest raceMarketMasterRequest){
        RaceMarketMasterResponse raceMarketMasterResponse = new RaceMarketMasterResponse();
        RaceMarketMaster raceMarketMaster = mapper.raceMarketMasterObjectToEntity(raceMarketMasterRequest, RaceMarketMaster.class);
        validator.validate(raceMarketMaster);
        List<RaceMarketMaster> raceMarketMasterList = raceMarketMasterRepository.findByMarketMasterIdAndRaceMasterId(raceMarketMasterRequest.getMarketMasterId(),raceMarketMasterRequest.getRaceMasterId());
        if(!raceMarketMasterList.isEmpty() && raceMarketMasterList.stream().filter(RaceMarketMaster::getActive).findAny().isPresent()){
            raceMarketMasterResponse.setError(true);
            raceMarketMasterResponse.setError_description("Race name already exist");
//        }
//        else if(!raceMarketMasterList.isEmpty() && raceMarketMasterList.stream().filter(Predicate.not(RaceMarketMaster::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            raceMarketMasterResponse.setError(true);
//            raceMarketMasterResponse.setError_description("Race name already exist with inactive state");
        }else {
            raceMarketMasterResponse = mapper.raceMarketMasterEntityToObject(raceMarketMasterRepository.save(raceMarketMaster), RaceMarketMasterResponse.class);
            raceMarketMasterResponse.setError(false);
        }
        return raceMarketMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRaceMarketMasterDetails(final Pageable pageable){
        return convertToMapResponse(raceMarketMasterRepository.findByActiveOrderByRaceMarketMasterIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(raceMarketMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<RaceMarketMaster> activeRaceMarketMaster) {
        Map<String, Object> response = new HashMap<>();

        List<RaceMarketMasterResponse> raceMarketMasterResponses= activeRaceMarketMaster.getContent().stream()
                .map(raceMarketMaster -> mapper.raceMarketMasterEntityToObject(raceMarketMaster, RaceMarketMasterResponse.class)).collect(Collectors.toList());
        response.put("raceMarketMaster",raceMarketMasterResponses);
        response.put("currentPage", activeRaceMarketMaster.getNumber());
        response.put("totalItems", activeRaceMarketMaster.getTotalElements());
        response.put("totalPages", activeRaceMarketMaster.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RaceMarketMaster> activeRaceMarketMasters) {
        Map<String, Object> response = new HashMap<>();

        List<RaceMarketMasterResponse> raceMarketMasterResponses = activeRaceMarketMasters.stream()
                .map(raceMarketMaster  -> mapper.raceMarketMasterEntityToObject(raceMarketMaster, RaceMarketMasterResponse.class)).collect(Collectors.toList());
        response.put("activeRaceMarketMaster",raceMarketMasterResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRaceMarketMasterDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(raceMarketMasterRepository.getByActiveOrderByRaceMarketMasterIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<RaceMarketMasterDTO> activeRaceMarketMasters) {
        Map<String, Object> response = new HashMap<>();

        List<RaceMarketMasterResponse> raceMarketMasterResponses= activeRaceMarketMasters.getContent().stream()
                .map(raceMarketMaster -> mapper.raceMarketMasterDTOToObject(raceMarketMaster,RaceMarketMasterResponse.class)).collect(Collectors.toList());
        response.put("raceMarketMaster",raceMarketMasterResponses);
        response.put("currentPage", activeRaceMarketMasters.getNumber());
        response.put("totalItems", activeRaceMarketMasters.getTotalElements());
        response.put("totalPages", activeRaceMarketMasters.getTotalPages());
        return response;
    }


    @Transactional
    public RaceMarketMasterResponse deleteRaceMarketMasterDetails(long id) {
        RaceMarketMasterResponse raceMarketMasterResponse= new RaceMarketMasterResponse();
        RaceMarketMaster raceMarketMaster = raceMarketMasterRepository.findByRaceMarketMasterIdAndActive(id, true);
        if (Objects.nonNull(raceMarketMaster)) {
            raceMarketMaster.setActive(false);
            raceMarketMasterResponse= mapper.raceMarketMasterEntityToObject(raceMarketMasterRepository.save(raceMarketMaster),RaceMarketMasterResponse.class);
            raceMarketMasterResponse.setError(false);
        } else {
            raceMarketMasterResponse.setError(true);
            raceMarketMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return raceMarketMasterResponse;
    }

    @Transactional
    public RaceMarketMasterResponse getById(int id){
        RaceMarketMasterResponse raceMarketMasterResponse = new RaceMarketMasterResponse();
        RaceMarketMaster raceMarketMaster = raceMarketMasterRepository.findByRaceMarketMasterIdAndActive(id,true);


        if(raceMarketMaster == null){
            raceMarketMasterResponse.setError(true);
            raceMarketMasterResponse.setError_description("Invalid id");
        }else{
            raceMarketMasterResponse =  mapper.raceMarketMasterEntityToObject(raceMarketMaster, RaceMarketMasterResponse.class);
            raceMarketMasterResponse.setError(false);
        }
        log.info("Entity is ",raceMarketMaster);
        return raceMarketMasterResponse;
    }

    @Transactional
    public RaceMarketMasterResponse getByIdJoin(int id){
        RaceMarketMasterResponse raceMarketMasterResponse = new RaceMarketMasterResponse();
        RaceMarketMasterDTO raceMarketMasterDTO = raceMarketMasterRepository.getByRaceMarketMasterIdAndActive(id,true);
        if(raceMarketMasterDTO == null){
            raceMarketMasterResponse.setError(true);
            raceMarketMasterResponse.setError_description("Invalid id");
        } else {
            raceMarketMasterResponse = mapper.raceMarketMasterDTOToObject(raceMarketMasterDTO, RaceMarketMasterResponse.class);
            raceMarketMasterResponse.setError(false);
        }
        log.info("Entity is ", raceMarketMasterDTO);
        return raceMarketMasterResponse;
    }

    @Transactional
    public RaceMarketMasterResponse updateRaceMarketMasterDetails(EditRaceMarketMasterRequest raceMarketMasterRequest) {
        RaceMarketMasterResponse raceMarketMasterResponse = new RaceMarketMasterResponse();
        List<RaceMarketMaster> raceMarketMasterList = raceMarketMasterRepository.findByActiveAndMarketMasterIdAndRaceMasterId(true,raceMarketMasterRequest.getMarketMasterId(), raceMarketMasterRequest.getRaceMasterId());
        if (raceMarketMasterList.size() > 0) {
            raceMarketMasterResponse.setError(true);
            raceMarketMasterResponse.setError_description("Race already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            RaceMarketMaster raceMarketMaster = raceMarketMasterRepository.findByRaceMarketMasterIdAndActiveIn(raceMarketMasterRequest.getRaceMarketMasterId(), Set.of(true, false));
            if (Objects.nonNull(raceMarketMaster)) {
                raceMarketMaster.setMarketMasterId(raceMarketMasterRequest.getMarketMasterId());
                raceMarketMaster.setRaceMasterId(raceMarketMasterRequest.getRaceMasterId());
                raceMarketMaster.setActive(true);
                RaceMarketMaster raceMarketMaster1 = raceMarketMasterRepository.save(raceMarketMaster);
                raceMarketMasterResponse = mapper.raceMarketMasterEntityToObject(raceMarketMaster1, RaceMarketMasterResponse.class);
                raceMarketMasterResponse.setError(false);
            } else {
                raceMarketMasterResponse.setError(true);
                raceMarketMasterResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return raceMarketMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getByMarketMasterId(long marketMasterId){
        Map<String, Object> response = new HashMap<>();
        List<RaceMarketMasterDTO> raceMarketMasterList = raceMarketMasterRepository.getRaceMaster(marketMasterId,true);
        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
        if(raceMarketMasterList.isEmpty()){
            response.put("error","Error");
            response.put("error_description","Invalid id");
            return response;
        }else {
            log.info("Entity is ", raceMarketMasterList);
            response = convertListToMapResponse(raceMarketMasterList);
            return response;
        }
    }

    private Map<String, Object> convertListToMapResponse(List<RaceMarketMasterDTO> raceMarketMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<RaceMarketMasterResponse> raceMasterResponses = raceMarketMasterList.stream()
                .map(raceMarketMaster -> mapper.raceMarketMasterDTOToObject(raceMarketMaster,RaceMarketMasterResponse.class)).collect(Collectors.toList());
        response.put("raceMaster",raceMasterResponses);
        response.put("totalItems", raceMasterResponses.size());
        return response;
    }
}


