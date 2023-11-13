package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.village.EditVillageRequest;
import com.sericulture.masterdata.model.api.village.VillageRequest;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.entity.Hobli;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.VillageRepository;
import com.sericulture.masterdata.repository.StateRepository;
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
public class VillageService {

    @Autowired
    VillageRepository villageRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public VillageResponse getVillageDetails(String villageName){
        Village village = null;
        if(village==null){
            village = villageRepository.findByVillageNameAndActive(villageName,true);
        }
        log.info("Entity is ",village);
        return mapper.villageEntityToObject(village,VillageResponse.class);
    }

    @Transactional
    public VillageResponse insertVillageDetails(VillageRequest villageRequest){
        Village village = mapper.villageObjectToEntity(villageRequest,Village.class);
        validator.validate(village);
        List<Village> villageList = villageRepository.findByVillageName(villageRequest.getVillageName());
        if(!villageList.isEmpty() && villageList.stream().filter(Village::getActive).findAny().isPresent()){
            throw new ValidationException("Village name already exist");
        }
        if(!villageList.isEmpty() && villageList.stream().filter(Predicate.not(Village::getActive)).findAny().isPresent()){
            throw new ValidationException("Village name already exist with inactive state");
        }
        return mapper.villageEntityToObject(villageRepository.save(village),VillageResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedVillageDetails(final Pageable pageable){
        return convertToMapResponse(villageRepository.findByActiveOrderByVillageIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(villageRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Village> activeVillages) {
        Map<String, Object> response = new HashMap<>();

        List<VillageResponse> villageResponses = activeVillages.getContent().stream()
                .map(village -> mapper.villageEntityToObject(village,VillageResponse.class)).collect(Collectors.toList());
        response.put("village",villageResponses);
        response.put("currentPage", activeVillages.getNumber());
        response.put("totalItems", activeVillages.getTotalElements());
        response.put("totalPages", activeVillages.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Village> activeVillages) {
        Map<String, Object> response = new HashMap<>();

        List<VillageResponse> villageResponses = activeVillages.stream()
                .map(village -> mapper.villageEntityToObject(village,VillageResponse.class)).collect(Collectors.toList());
        response.put("village",villageResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedVillageDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(villageRepository.getByActiveOrderByVillageIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<VillageDTO> activeVillages) {
        Map<String, Object> response = new HashMap<>();

        List<VillageResponse> villageResponses = activeVillages.getContent().stream()
                .map(village -> mapper.villageDTOToObject(village,VillageResponse.class)).collect(Collectors.toList());
        response.put("village",villageResponses);
        response.put("currentPage", activeVillages.getNumber());
        response.put("totalItems", activeVillages.getTotalElements());
        response.put("totalPages", activeVillages.getTotalPages());
        return response;
    }

    @Transactional
    public void deleteVillageDetails(long id) {
        Village village = villageRepository.findByVillageIdAndActive(id, true);
        if (Objects.nonNull(village)) {
            village.setActive(false);
            villageRepository.save(village);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public VillageResponse getById(int id){
        Village village = villageRepository.findByVillageIdAndActive(id,true);
        if(village == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",village);
        return mapper.villageEntityToObject(village,VillageResponse.class);
    }
        @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getVillageByHobliId(Long talukId){
        List<Village> villageList = villageRepository.findByHobliIdAndActive(talukId,true);
        if(villageList.isEmpty()){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",villageList);
        return convertListToMapResponse(villageList);
    }

    private Map<String, Object> convertListToMapResponse(List<Village> villageList) {
        Map<String, Object> response = new HashMap<>();
        List<VillageResponse> villageResponses = villageList.stream()
                .map(village -> mapper.villageEntityToObject(village,VillageResponse.class)).collect(Collectors.toList());
        response.put("village",villageResponses);
        response.put("totalItems", villageList.size());
        return response;
    }

    @Transactional
    public VillageResponse updateVillageDetails(EditVillageRequest villageRequest){
        List<Village> villageList = villageRepository.findByVillageName(villageRequest.getVillageName());
        if(villageList.size()>0){
            throw new ValidationException("Village already exists, duplicates are not allowed.");
        }

        Village village = villageRepository.findByVillageIdAndActiveIn(villageRequest.getVillageId(), Set.of(true,false));
        if(Objects.nonNull(village)){
            village.setStateId(villageRequest.getStateId());
            village.setVillageName(villageRequest.getVillageName());
            village.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching village");
        }
        return mapper.villageEntityToObject(villageRepository.save(village),VillageResponse.class);
    }

}