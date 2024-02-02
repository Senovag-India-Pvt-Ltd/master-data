package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.subsidy.EditSubsidyRequest;
import com.sericulture.masterdata.model.api.subsidy.SubsidyRequest;
import com.sericulture.masterdata.model.api.subsidy.SubsidyResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.Subsidy;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.entity.Village;
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
        SubsidyResponse subsidyResponse = new SubsidyResponse();
        Subsidy subsidy = subsidyRepository.findBySubsidyNameAndActive(subsidyName,true);
        if(subsidy==null){
            subsidyResponse.setError(true);
            subsidyResponse.setError_description("Subsidy not found");
        }else{
            subsidyResponse = mapper.subsidyEntityToObject(subsidy, SubsidyResponse.class);
            subsidyResponse.setError(false);
        }
        log.info("Entity is ",subsidy);
        return subsidyResponse;
    }

    @Transactional
    public SubsidyResponse insertSubsidyDetails(SubsidyRequest subsidyRequest){
        SubsidyResponse subsidyResponse = new SubsidyResponse();
        Subsidy subsidy = mapper.subsidyObjectToEntity(subsidyRequest,Subsidy.class);
        validator.validate(subsidy);
        List<Subsidy> subsidyList = subsidyRepository.findBySubsidyNameAndSubsidyNameInKannada(subsidyRequest.getSubsidyName(), subsidyRequest.getSubsidyNameInKannada());
        if(!subsidyList.isEmpty() && subsidyList.stream().filter(Subsidy::getActive).findAny().isPresent()){
            subsidyResponse.setError(true);
            subsidyResponse.setError_description("Subsidy name already exist");
        }
        else if(!subsidyList.isEmpty() && subsidyList.stream().filter(Predicate.not(Subsidy::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            subsidyResponse.setError(true);
            subsidyResponse.setError_description("Subsidy name already exist with inactive state");
        }else {
            subsidyResponse = mapper.subsidyEntityToObject(subsidyRepository.save(subsidy), SubsidyResponse.class);
            subsidyResponse.setError(false);
        }
        return subsidyResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedSubsidyDetails(final Pageable pageable){
        return convertToMapResponse(subsidyRepository.findByActiveOrderBySubsidyNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(subsidyRepository.findByActive(isActive));
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

    private Map<String, Object> convertListEntityToMapResponse(final List<Subsidy> activeSubsidys) {
        Map<String, Object> response = new HashMap<>();

        List<SubsidyResponse> subsidyResponses = activeSubsidys.stream()
                .map(subsidy -> mapper.subsidyEntityToObject(subsidy,SubsidyResponse.class)).collect(Collectors.toList());
        response.put("subsidy",subsidyResponses);
        return response;
    }


    @Transactional
    public SubsidyResponse deleteSubsidyDetails(long id) {
        SubsidyResponse subsidyResponse = new SubsidyResponse();
        Subsidy subsidy = subsidyRepository.findBySubsidyIdAndActive(id, true);
        if (Objects.nonNull(subsidy)) {
            subsidy.setActive(false);
            subsidyResponse = mapper.subsidyEntityToObject(subsidyRepository.save(subsidy), SubsidyResponse.class);
            subsidyResponse.setError(false);
        } else {
            subsidyResponse.setError(true);
            subsidyResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return subsidyResponse;
    }

    @Transactional
    public SubsidyResponse getById(int id){
        SubsidyResponse subsidyResponse = new SubsidyResponse();
        Subsidy subsidy = subsidyRepository.findBySubsidyIdAndActive(id,true);
        if(subsidy == null){
            subsidyResponse.setError(true);
            subsidyResponse.setError_description("Invalid id");
        }else{
            subsidyResponse =  mapper.subsidyEntityToObject(subsidy,SubsidyResponse.class);
            subsidyResponse.setError(false);
        }
        log.info("Entity is ",subsidy);
        return subsidyResponse;
    }

    @Transactional
    public SubsidyResponse updateSubsidyDetails(EditSubsidyRequest subsidyRequest) {
        SubsidyResponse subsidyResponse = new SubsidyResponse();
        List<Subsidy> subsidyList = subsidyRepository.findBySubsidyNameAndSubsidyNameInKannada(subsidyRequest.getSubsidyName(),subsidyRequest.getSubsidyNameInKannada());
        if (subsidyList.size() > 0) {
            subsidyResponse.setError(true);
            subsidyResponse.setError_description("Subsidy already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            Subsidy subsidy = subsidyRepository.findBySubsidyIdAndActiveIn(subsidyRequest.getSubsidyId(), Set.of(true, false));
            if (Objects.nonNull(subsidy)) {
                subsidy.setSubsidyName(subsidyRequest.getSubsidyName());
                subsidy.setSubsidyNameInKannada(subsidyRequest.getSubsidyNameInKannada());
                subsidy.setActive(true);
                Subsidy subsidy1 = subsidyRepository.save(subsidy);
                subsidyResponse = mapper.subsidyEntityToObject(subsidy1, SubsidyResponse.class);
                subsidyResponse.setError(false);
            } else {
                subsidyResponse.setError(true);
                subsidyResponse.setError_description("Error occurred while fetching subsidy");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return subsidyResponse;
    }

}
