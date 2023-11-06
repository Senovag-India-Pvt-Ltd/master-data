package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.subsidy.EditSubsidyRequest;
import com.sericulture.masterdata.model.api.subsidy.SubsidyRequest;
import com.sericulture.masterdata.model.api.subsidy.SubsidyResponse;
import com.sericulture.masterdata.model.entity.Subsidy;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.SubsidyRepository;
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
public class SubsidyService {
    @Autowired
    SubsidyRepository subsidyRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SubsidyResponse getSubsidyDetails(String subsidyName){
        Subsidy subsidy = null;
        if(subsidy==null){
            subsidy = subsidyRepository.findBySubsidyNameAndActive(subsidyName,true);
        }
        log.info("Entity is ",subsidy);
        return mapper.subsidyEntityToObject(subsidy,SubsidyResponse.class);
    }

    @Transactional
    public SubsidyResponse insertSubsidyDetails(SubsidyRequest subsidyRequest){
        Subsidy subsidy = mapper.subsidyObjectToEntity(subsidyRequest,Subsidy.class);
        validator.validate(subsidy);
        List<Subsidy> subsidyList = subsidyRepository.findBySubsidyName(subsidyRequest.getSubsidyName());
        if(!subsidyList.isEmpty() && subsidyList.stream().filter(Subsidy::getActive).findAny().isPresent()){
            throw new ValidationException("Subsidy name already exist");
        }
        if(!subsidyList.isEmpty() && subsidyList.stream().filter(Predicate.not(Subsidy::getActive)).findAny().isPresent()){
            throw new ValidationException("Subsidy name already exist with inactive state");
        }
        return mapper.subsidyEntityToObject(subsidyRepository.save(subsidy),SubsidyResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedSubsidyDetails(final Pageable pageable){
        return convertToMapResponse(subsidyRepository.findByActiveOrderBySubsidyIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<Subsidy> activeSubsidys) {
        Map<String, Object> response = new HashMap<>();

        List<SubsidyResponse> subsidyResponses = activeSubsidys.getContent().stream()
                .map(subsidy -> mapper.subsidyEntityToObject(subsidy,SubsidyResponse.class)).collect(Collectors.toList());
        response.put("subsidy",subsidyResponses);
        response.put("currentPage", activeSubsidys.getNumber());
        response.put("totalItems", activeSubsidys.getTotalElements());
        response.put("totalPages", activeSubsidys.getTotalPages());

        return response;
    }

    @Transactional
    public void deleteSubsidyDetails(long id) {
        Subsidy subsidy = subsidyRepository.findBySubsidyIdAndActive(id, true);
        if (Objects.nonNull(subsidy)) {
            subsidy.setActive(false);
            subsidyRepository.save(subsidy);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public SubsidyResponse getById(int id){
        Subsidy subsidy = subsidyRepository.findBySubsidyIdAndActive(id,true);
        if(subsidy == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",subsidy);
        return mapper.subsidyEntityToObject(subsidy,SubsidyResponse.class);
    }

    @Transactional
    public SubsidyResponse updateSubsidyDetails(EditSubsidyRequest subsidyRequest){
        List<Subsidy> subsidyList = subsidyRepository.findBySubsidyName(subsidyRequest.getSubsidyName());
        if(subsidyList.size()>0){
            throw new ValidationException("Subsidy already exists with this name, duplicates are not allowed.");
        }

        Subsidy subsidy = subsidyRepository.findBySubsidyIdAndActiveIn(subsidyRequest.getSubsidyId(), Set.of(true,false));
        if(Objects.nonNull(subsidy)){
            subsidy.setSubsidyName(subsidyRequest.getSubsidyName());
            subsidy.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching machineType");
        }
        return mapper.subsidyEntityToObject(subsidyRepository.save(subsidy),SubsidyResponse.class);
    }
}
