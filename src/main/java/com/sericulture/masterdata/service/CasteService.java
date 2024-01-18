package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.caste.CasteRequest;
import com.sericulture.masterdata.model.api.caste.CasteResponse;
import com.sericulture.masterdata.model.api.caste.EditCasteRequest;
import com.sericulture.masterdata.model.api.designation.DesignationRequest;
import com.sericulture.masterdata.model.api.designation.DesignationResponse;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.CasteRepository;
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
public class CasteService {

    @Autowired
    CasteRepository casteRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CasteResponse getCasteDetails(String code, String title){
        CasteResponse casteResponse = new CasteResponse();
        Caste caste = null;
        if(code==null){
            caste = casteRepository.findByTitleAndActive(title,true);
            casteResponse = mapper.casteEntityToObject(caste, CasteResponse.class);
            casteResponse.setError(false);
        }else{
            casteResponse.setError(true);
            casteResponse.setError_description("Caste not found");
        }
        log.info("Entity is ",caste);
        return casteResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CasteResponse getCasteDetailsByTitle(String title){
        Caste caste = casteRepository.findByTitleAndActive(title,true);
        log.info("Entity is ",caste);
        if(caste == null){
            CasteResponse casteResponse = new CasteResponse();
            casteResponse.setError(true);
            casteResponse.setError_description("Caste not found");
            return casteResponse;
        }else{
            return mapper.casteEntityToObject(caste,CasteResponse.class);
        }
    }


    @Transactional
    public CasteResponse insertCasteDetails(CasteRequest casteRequest){
        CasteResponse casteResponse = new CasteResponse();
        Caste caste = mapper.casteObjectToEntity(casteRequest,Caste.class);
        validator.validate(caste);
        List<Caste> casteList = casteRepository.findByTitleAndNameInKannada(casteRequest.getTitle(),casteRequest.getNameInKannada());
        if(!casteList.isEmpty() && casteList.stream().filter(Caste::getActive).findAny().isPresent()){
            casteResponse.setError(true);
            casteResponse.setError_description("Caste name already exist");
        }
        else if(!casteList.isEmpty() && casteList.stream().filter(Predicate.not(Caste::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            casteResponse.setError(true);
            casteResponse.setError_description("Caste name already exist with inactive state");
        }else {
            casteResponse = mapper.casteEntityToObject(casteRepository.save(caste), CasteResponse.class);
            casteResponse.setError(false);
        }
        return casteResponse;
    }



    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedCasteDetails(final Pageable pageable){
        return convertToMapResponse(casteRepository.findByActiveOrderByTitleAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(casteRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Caste> activeCastes) {
        Map<String, Object> response = new HashMap<>();

        List<CasteResponse> casteResponses = activeCastes.getContent().stream()
                .map(caste -> mapper.casteEntityToObject(caste,CasteResponse.class)).collect(Collectors.toList());
        response.put("caste",casteResponses);
        response.put("currentPage", activeCastes.getNumber());
        response.put("totalItems", activeCastes.getTotalElements());
        response.put("totalPages", activeCastes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Caste> activeCastes) {
        Map<String, Object> response = new HashMap<>();

        List<CasteResponse> casteResponses = activeCastes.stream()
                .map(caste -> mapper.casteEntityToObject(caste,CasteResponse.class)).collect(Collectors.toList());
        response.put("caste",casteResponses);
        return response;
    }

    @Transactional
    public CasteResponse deleteCasteDetails(long id) {
        CasteResponse casteResponse = new CasteResponse();
        Caste caste = casteRepository.findByIdAndActive(id, true);
        if (Objects.nonNull(caste)) {
            caste.setActive(false);
            casteResponse = mapper.casteEntityToObject(casteRepository.save(caste), CasteResponse.class);
            casteResponse.setError(false);
        } else {
            casteResponse.setError(true);
            casteResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return casteResponse;
    }

    @Transactional
    public CasteResponse getById(int id){
        CasteResponse casteResponse = new CasteResponse();
        Caste caste = casteRepository.findByIdAndActive(id,true);
        if(caste == null){
            casteResponse.setError(true);
            casteResponse.setError_description("Invalid id");
        }else{
            casteResponse =  mapper.casteEntityToObject(caste,CasteResponse.class);
            casteResponse.setError(false);
        }
        log.info("Entity is ",caste);
        return casteResponse;
    }

    @Transactional
    public CasteResponse updateCasteDetails(EditCasteRequest casteRequest){
        CasteResponse casteResponse = new CasteResponse();
        List<Caste> casteList = casteRepository.findByTitleAndNameInKannada(casteRequest.getTitle(),casteRequest.getNameInKannada());
        if (casteList.size() > 0) {
            casteResponse.setError(true);
            casteResponse.setError_description("Caste already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            Caste caste = casteRepository.findByIdAndActiveIn(casteRequest.getId(), Set.of(true, false));
            if(Objects.nonNull(caste)){
                caste.setCode(casteRequest.getCode());
                caste.setTitle(casteRequest.getTitle());
                caste.setNameInKannada(casteRequest.getNameInKannada());
                caste.setActive(true);
                Caste caste1 = casteRepository.save(caste);
                casteResponse = mapper.casteEntityToObject(caste1, CasteResponse.class);
                casteResponse.setError(false);
            } else {
                casteResponse.setError(true);
                casteResponse.setError_description("Error occurred while fetching caste");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return casteResponse;

    }



}
