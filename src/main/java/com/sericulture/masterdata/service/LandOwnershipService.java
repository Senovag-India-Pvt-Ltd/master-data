package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.landOwnership.EditLandOwnershipRequest;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipRequest;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipResponse;
import com.sericulture.masterdata.model.entity.LandOwnership;
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
        LandOwnership landOwnership = null;
        if(landOwnership==null){
            landOwnership = landOwnershipRepository.findByLandOwnershipNameAndActive(landOwnershipName,true);
        }
        log.info("Entity is ",landOwnership);
        return mapper.landOwnershipEntityToObject(landOwnership,LandOwnershipResponse.class);
    }

    @Transactional
    public LandOwnershipResponse insertLandOwnershipDetails(LandOwnershipRequest landOwnershipRequest){
        LandOwnership landOwnership = mapper.landOwnershipObjectToEntity(landOwnershipRequest,LandOwnership.class);
        validator.validate(landOwnership);
        List<LandOwnership> landOwnershipList = landOwnershipRepository.findByLandOwnershipName(landOwnershipRequest.getLandOwnershipName());
        if(!landOwnershipList.isEmpty() && landOwnershipList.stream().filter(LandOwnership::getActive).findAny().isPresent()){
            throw new ValidationException("LandOwnership name already exist");
        }
        if(!landOwnershipList.isEmpty() && landOwnershipList.stream().filter(Predicate.not(LandOwnership::getActive)).findAny().isPresent()){
            throw new ValidationException("LandOwnership name already exist with inactive state");
        }
        return mapper.landOwnershipEntityToObject(landOwnershipRepository.save(landOwnership), LandOwnershipResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedLandOwnershipDetails(final Pageable pageable){
        return convertToMapResponse(landOwnershipRepository.findByActiveOrderByLandOwnershipIdAsc( true, pageable));
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

    @Transactional
    public void deleteLandOwnershipDetails(long id) {
        LandOwnership landOwnership = landOwnershipRepository.findByLandOwnershipIdAndActive(id, true);
        if (Objects.nonNull(landOwnership)) {
            landOwnership.setActive(false);
            landOwnershipRepository.save(landOwnership);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public LandOwnershipResponse getById(int id){
        LandOwnership landOwnership = landOwnershipRepository.findByLandOwnershipIdAndActive(id,true);
        if(landOwnership == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",landOwnership);
        return mapper.landOwnershipEntityToObject(landOwnership,LandOwnershipResponse.class);
    }

    @Transactional
    public LandOwnershipResponse updateLandOwnershipDetails(EditLandOwnershipRequest landOwnershipRequest){
        List<LandOwnership> landOwnershipList = landOwnershipRepository.findByLandOwnershipName(landOwnershipRequest.getLandOwnershipName());
        if(landOwnershipList.size()>0){
            throw new ValidationException("landOwnership already exists for the given code and title, duplicates are not allowed.");
        }

        LandOwnership landOwnership = landOwnershipRepository.findByLandOwnershipIdAndActiveIn(landOwnershipRequest.getLandOwnershipId(), Set.of(true,false));
        if(Objects.nonNull(landOwnership)){
            landOwnership.setLandOwnershipName(landOwnershipRequest.getLandOwnershipName());
            landOwnership.setActive(true);
        }
        return mapper.landOwnershipEntityToObject(landOwnershipRepository.save(landOwnership),LandOwnershipResponse.class);
    }
}
