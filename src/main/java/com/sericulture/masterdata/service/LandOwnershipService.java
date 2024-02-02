package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.landOwnership.EditLandOwnershipRequest;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipRequest;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipResponse;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.LandOwnership;
import com.sericulture.masterdata.model.entity.MarketMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.LandOwnershipRepository;
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
public class LandOwnershipService {
    @Autowired
    LandOwnershipRepository landOwnershipRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LandOwnershipResponse getLandOwnershipDetails(String landOwnershipName){
        LandOwnershipResponse landOwnershipResponse = new LandOwnershipResponse();
        LandOwnership landOwnership = landOwnershipRepository.findByLandOwnershipNameAndActive(landOwnershipName,true);
        if(landOwnership==null){
            landOwnershipResponse.setError(true);
            landOwnershipResponse.setError_description("Land Ownership not found");
        }else{
            landOwnershipResponse = mapper.landOwnershipEntityToObject(landOwnership, LandOwnershipResponse.class);
            landOwnershipResponse.setError(false);
        }
        log.info("Entity is ",landOwnership);
        return landOwnershipResponse;
    }

    @Transactional
    public LandOwnershipResponse insertLandOwnershipDetails(LandOwnershipRequest landOwnershipRequest){
        LandOwnershipResponse landOwnershipResponse = new LandOwnershipResponse();
        LandOwnership landOwnership = mapper.landOwnershipObjectToEntity(landOwnershipRequest,LandOwnership.class);
        validator.validate(landOwnership);
        List<LandOwnership> landOwnershipList = landOwnershipRepository.findByLandOwnershipNameAndLandOwnershipNameInKannada(landOwnershipRequest.getLandOwnershipName(),landOwnershipRequest.getLandOwnershipNameInKannada());
        if(!landOwnershipList.isEmpty() && landOwnershipList.stream().filter(LandOwnership::getActive).findAny().isPresent()){
            landOwnershipResponse.setError(true);
            landOwnershipResponse.setError_description("LandOwnership name already exist");
        }
        else if(!landOwnershipList.isEmpty() && landOwnershipList.stream().filter(Predicate.not(LandOwnership::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            landOwnershipResponse.setError(true);
            landOwnershipResponse.setError_description("LandOwnership name already exist with inactive state");
        }else {
            landOwnershipResponse = mapper.landOwnershipEntityToObject(landOwnershipRepository.save(landOwnership), LandOwnershipResponse.class);
            landOwnershipResponse.setError(false);
        }
        return landOwnershipResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedLandOwnershipDetails(final Pageable pageable){
        return convertToMapResponse(landOwnershipRepository.findByActiveOrderByLandOwnershipNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(landOwnershipRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<LandOwnership> activeLandOwnerships) {
        Map<String, Object> response = new HashMap<>();

        List<LandOwnershipResponse> landOwnershipResponses = activeLandOwnerships.getContent().stream()
                .map(landOwnership -> mapper.landOwnershipEntityToObject(landOwnership, LandOwnershipResponse.class)).collect(Collectors.toList());
        response.put("landOwnership",landOwnershipResponses);
        response.put("currentPage", activeLandOwnerships.getNumber());
        response.put("totalItems", activeLandOwnerships.getTotalElements());
        response.put("totalPages", activeLandOwnerships.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<LandOwnership> activeLandOwnerships) {
        Map<String, Object> response = new HashMap<>();

        List<LandOwnershipResponse> landOwnershipResponses = activeLandOwnerships.stream()
                .map(landOwnership -> mapper.landOwnershipEntityToObject(landOwnership,LandOwnershipResponse.class)).collect(Collectors.toList());
        response.put("landOwnership",landOwnershipResponses);
        return response;
    }

    @Transactional
    public LandOwnershipResponse deleteLandOwnershipDetails(long id) {
        LandOwnershipResponse landOwnershipResponse = new LandOwnershipResponse();
        LandOwnership landOwnership = landOwnershipRepository.findByLandOwnershipIdAndActive(id, true);
        if (Objects.nonNull(landOwnership)) {
            landOwnership.setActive(false);
            landOwnershipResponse = mapper.landOwnershipEntityToObject(landOwnershipRepository.save(landOwnership), LandOwnershipResponse.class);
            landOwnershipResponse.setError(false);
        } else {
            landOwnershipResponse.setError(true);
            landOwnershipResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return landOwnershipResponse;
    }

    @Transactional
    public LandOwnershipResponse getById(int id){
        LandOwnershipResponse landOwnershipResponse = new LandOwnershipResponse();
        LandOwnership landOwnership = landOwnershipRepository.findByLandOwnershipIdAndActive(id,true);
        if(landOwnership == null){
            landOwnershipResponse.setError(true);
            landOwnershipResponse.setError_description("Invalid id");
        }else{
            landOwnershipResponse =  mapper.landOwnershipEntityToObject(landOwnership,LandOwnershipResponse.class);
            landOwnershipResponse.setError(false);
        }
        log.info("Entity is ",landOwnership);
        return landOwnershipResponse;
    }

    @Transactional
    public LandOwnershipResponse updateLandOwnershipDetails(EditLandOwnershipRequest landOwnershipRequest) {
        LandOwnershipResponse landOwnershipResponse = new LandOwnershipResponse();
        List<LandOwnership> landOwnershipList = landOwnershipRepository.findByLandOwnershipNameAndLandOwnershipNameInKannada(landOwnershipRequest.getLandOwnershipName(),landOwnershipRequest.getLandOwnershipNameInKannada());
        if (landOwnershipList.size() > 0) {
            landOwnershipResponse.setError(true);
            landOwnershipResponse.setError_description("LandOwnership already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            LandOwnership landOwnership = landOwnershipRepository.findByLandOwnershipIdAndActiveIn(landOwnershipRequest.getLandOwnershipId(), Set.of(true, false));
            if (Objects.nonNull(landOwnership)) {
                landOwnership.setLandOwnershipName(landOwnershipRequest.getLandOwnershipName());
                landOwnership.setLandOwnershipNameInKannada(landOwnershipRequest.getLandOwnershipNameInKannada());
                landOwnership.setActive(true);
                LandOwnership landOwnership1 = landOwnershipRepository.save(landOwnership);
                landOwnershipResponse = mapper.landOwnershipEntityToObject(landOwnership1, LandOwnershipResponse.class);
                landOwnershipResponse.setError(false);
            } else {
                landOwnershipResponse.setError(true);
                landOwnershipResponse.setError_description("Error occurred while fetching landOwnership");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return landOwnershipResponse;
    }
}
