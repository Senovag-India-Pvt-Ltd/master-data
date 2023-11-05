package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.farmerFamily.EditFarmerFamilyRequest;
import com.sericulture.masterdata.model.api.farmerFamily.FarmerFamilyRequest;
import com.sericulture.masterdata.model.api.farmerFamily.FarmerFamilyResponse;
import com.sericulture.masterdata.model.entity.FarmerFamily;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.FarmerFamilyRepository;
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
public class FarmerFamilyService {

    @Autowired
    FarmerFamilyRepository farmerFamilyRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FarmerFamilyResponse getFarmerFamilyDetails(String farmerFamilyName){
        FarmerFamily farmerFamily = null;
        if(farmerFamily==null){
            farmerFamily = farmerFamilyRepository.findByFarmerFamilyNameAndActive(farmerFamilyName,true);
        }
        log.info("Entity is ",farmerFamily);
        return mapper.farmerFamilyEntityToObject(farmerFamily,FarmerFamilyResponse.class);
    }

    @Transactional
    public FarmerFamilyResponse insertFarmerFamilyDetails(FarmerFamilyRequest farmerFamilyRequest){
        FarmerFamily farmerFamily = mapper.farmerFamilyObjectToEntity(farmerFamilyRequest,FarmerFamily.class);
        validator.validate(farmerFamily);
        List<FarmerFamily> farmerFamilyList = farmerFamilyRepository.findByFarmerFamilyName(farmerFamilyRequest.getFarmerFamilyName());
        if(!farmerFamilyList.isEmpty() && farmerFamilyList.stream().filter(FarmerFamily::getActive).findAny().isPresent()){
            throw new ValidationException("FarmerFamily name already exist");
        }
        if(!farmerFamilyList.isEmpty() && farmerFamilyList.stream().filter(Predicate.not(FarmerFamily::getActive)).findAny().isPresent()){
            throw new ValidationException("FarmerFamily name already exist with inactive state");
        }
        return mapper.farmerFamilyEntityToObject(farmerFamilyRepository.save(farmerFamily),FarmerFamilyResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedFarmerFamilyDetails(final Pageable pageable){
        return convertToMapResponse(farmerFamilyRepository.findByActiveOrderByFarmerFamilyIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<FarmerFamily> activeFarmerFamilys) {
        Map<String, Object> response = new HashMap<>();

        List<FarmerFamilyResponse> farmerFamilyResponses = activeFarmerFamilys.getContent().stream()
                .map(farmerFamily -> mapper.farmerFamilyEntityToObject(farmerFamily,FarmerFamilyResponse.class)).collect(Collectors.toList());
        response.put("farmerFamily",farmerFamilyResponses);
        response.put("currentPage", activeFarmerFamilys.getNumber());
        response.put("totalItems", activeFarmerFamilys.getTotalElements());
        response.put("totalPages", activeFarmerFamilys.getTotalPages());

        return response;
    }

    @Transactional
    public void deleteFarmerFamilyDetails(long id) {
        FarmerFamily farmerFamily = farmerFamilyRepository.findByFarmerFamilyIdAndActive(id, true);
        if (Objects.nonNull(farmerFamily)) {
            farmerFamily.setActive(false);
            farmerFamilyRepository.save(farmerFamily);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public FarmerFamilyResponse getById(int id){
        FarmerFamily farmerFamily = farmerFamilyRepository.findByFarmerFamilyIdAndActive(id,true);
        if(farmerFamily == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",farmerFamily);
        return mapper.farmerFamilyEntityToObject(farmerFamily,FarmerFamilyResponse.class);
    }

    @Transactional
    public FarmerFamilyResponse updateFarmerFamilyDetails(EditFarmerFamilyRequest farmerFamilyRequest){
        List<FarmerFamily> farmerFamilyList = farmerFamilyRepository.findByFarmerFamilyName(farmerFamilyRequest.getFarmerFamilyName());
        if(farmerFamilyList.size()>0){
            throw new ValidationException("FarmerFamily already exists with this name, duplicates are not allowed.");
        }

        FarmerFamily farmerFamily = farmerFamilyRepository.findByFarmerFamilyIdAndActiveIn(farmerFamilyRequest.getFarmerFamilyId(), Set.of(true,false));
        if(Objects.nonNull(farmerFamily)){
            farmerFamily.setFarmerFamilyName(farmerFamilyRequest.getFarmerFamilyName());
            farmerFamily.setFarmerId(farmerFamilyRequest.getFarmerId());
            farmerFamily.setRelationshipId(farmerFamilyRequest.getRelationshipId());
            farmerFamily.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching farmerFamily");
        }
        return mapper.farmerFamilyEntityToObject(farmerFamilyRepository.save(farmerFamily),FarmerFamilyResponse.class);
    }

}