package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.caste.CasteRequest;
import com.sericulture.masterdata.model.api.caste.CasteResponse;
import com.sericulture.masterdata.model.api.caste.EditCasteRequest;
import com.sericulture.masterdata.model.entity.Caste;
import com.sericulture.masterdata.model.entity.State;
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
        Caste caste = null;
        if(code==null){
            caste = casteRepository.findByTitleAndActive(title,true);
        }
        else {
            caste = casteRepository.findByCodeAndActive(code,true);
        }
        log.info("Entity is ",caste);
        return mapper.casteEntityToObject(caste,CasteResponse.class);
    }

    @Transactional
    public CasteResponse insertCasteDetails(CasteRequest casteRequest){
        Caste caste = mapper.casteObjectToEntity(casteRequest,Caste.class);
        validator.validate(caste);
        List<Caste> casteList = casteRepository.findByCode(casteRequest.getCode());
        String type = "code";
        if(casteList==null || casteList.isEmpty()){
            casteList = casteRepository.findByTitle(caste.getTitle());
            type = "title";
        }
        if(!casteList.isEmpty() && casteList.stream().filter(Caste::getActive).findAny().isPresent()){
            return mapper.casteEntityToObject(caste,CasteResponse.class);
        }
        if(!casteList.isEmpty() && casteList.stream().filter(Predicate.not(Caste::getActive)).findAny().isPresent()){
            throw new ValidationException("Caste"+type+" alredy exist");
        }
        return mapper.casteEntityToObject(casteRepository.save(caste),CasteResponse.class);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedCasteDetails(final Pageable pageable){
        return convertToMapResponse(casteRepository.findByActiveOrderByIdAsc( true, pageable));
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

    @Transactional
    public void deleteCasteDetails(long id) {
        Caste caste = casteRepository.findByIdAndActive(id, true);
        if (Objects.nonNull(caste)) {
            caste.setActive(false);
            casteRepository.save(caste);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public CasteResponse getById(int id){
        Caste caste = casteRepository.findByIdAndActive(id,true);
        if(caste == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",caste);
        return mapper.casteEntityToObject(caste,CasteResponse.class);
    }

    @Transactional
    public CasteResponse updateCasteDetails(EditCasteRequest casteRequest){
        Caste caste = casteRepository.findByCodeAndTitle(casteRequest.getCode(),casteRequest.getTitle());
        if(caste!=null){
            throw new ValidationException("caste already exists for the given code and title, duplicates are not allowed.");
        }

         caste = casteRepository.findByIdAndActiveIn(casteRequest.getId(), Set.of(true,false));
        if(Objects.nonNull(caste)){
            caste.setCode(casteRequest.getCode());
            caste.setTitle(casteRequest.getTitle());
            caste.setActive(true);
        }
        return mapper.casteEntityToObject(casteRepository.save(caste),CasteResponse.class);


    }



}
