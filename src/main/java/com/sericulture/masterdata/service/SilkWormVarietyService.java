package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.silkwormvariety.EditSilkWormVarietyRequest;
import com.sericulture.masterdata.model.api.silkwormvariety.SilkWormVarietyRequest;
import com.sericulture.masterdata.model.api.silkwormvariety.SilkWormVarietyResponse;
import com.sericulture.masterdata.model.api.soilType.SoilTypeResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.SilkWormVariety;
import com.sericulture.masterdata.model.entity.SoilType;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.SilkWormVarietyRepository;
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
public class SilkWormVarietyService {

    @Autowired
    SilkWormVarietyRepository silkWormVarietyRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SilkWormVarietyResponse getSilkWormVarietyDetails(String silkWormVarietyName){
        SilkWormVarietyResponse silkWormVarietyResponse = new SilkWormVarietyResponse();
        SilkWormVariety silkWormVariety = null;
        if(silkWormVariety==null){
            silkWormVariety = silkWormVarietyRepository.findBySilkWormVarietyNameAndActive(silkWormVarietyName,true);
            silkWormVarietyResponse = mapper.silkWormVarietyEntityToObject(silkWormVariety, SilkWormVarietyResponse.class);
            silkWormVarietyResponse.setError(false);
        }else{
            silkWormVarietyResponse.setError(true);
            silkWormVarietyResponse.setError_description("SilkWormVariety not found");
        }
        log.info("Entity is ",silkWormVariety);
        return silkWormVarietyResponse;
    }

    @Transactional
    public SilkWormVarietyResponse insertSilkWormVarietyDetails(SilkWormVarietyRequest silkWormVarietyRequest){
        SilkWormVarietyResponse silkWormVarietyResponse = new SilkWormVarietyResponse();
        SilkWormVariety silkWormVariety = mapper.silkWormVarietyObjectToEntity(silkWormVarietyRequest,SilkWormVariety.class);
        validator.validate(silkWormVariety);
        List<SilkWormVariety> silkWormVarietyList = silkWormVarietyRepository.findBySilkWormVarietyName(silkWormVarietyRequest.getSilkWormVarietyName());
        if(!silkWormVarietyList.isEmpty() && silkWormVarietyList.stream().filter(SilkWormVariety::getActive).findAny().isPresent()){
            silkWormVarietyResponse.setError(true);
            silkWormVarietyResponse.setError_description("SilkWormVariety name already exist");
        }
        else if(!silkWormVarietyList.isEmpty() && silkWormVarietyList.stream().filter(Predicate.not(SilkWormVariety::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            silkWormVarietyResponse.setError(true);
            silkWormVarietyResponse.setError_description("SilkWormVariety name already exist with inactive state");
        }else {
            silkWormVarietyResponse = mapper.silkWormVarietyEntityToObject(silkWormVarietyRepository.save(silkWormVariety), SilkWormVarietyResponse.class);
            silkWormVarietyResponse.setError(false);
        }
        return silkWormVarietyResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedSilkWormVarietyDetails(final Pageable pageable){
        return convertToMapResponse(silkWormVarietyRepository.findByActiveOrderBySilkWormVarietyIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(silkWormVarietyRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<SilkWormVariety> activeSilkWormVarietys) {
        Map<String, Object> response = new HashMap<>();

        List<SilkWormVarietyResponse> silkWormVarietyResponses = activeSilkWormVarietys.getContent().stream()
                .map(silkWormVariety -> mapper.silkWormVarietyEntityToObject(silkWormVariety,SilkWormVarietyResponse.class)).collect(Collectors.toList());
        response.put("silkWormVariety",silkWormVarietyResponses);
        response.put("currentPage", activeSilkWormVarietys.getNumber());
        response.put("totalItems", activeSilkWormVarietys.getTotalElements());
        response.put("totalPages", activeSilkWormVarietys.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<SilkWormVariety> activeSilkWormVarietys) {
        Map<String, Object> response = new HashMap<>();

        List<SilkWormVarietyResponse> silkWormVarietyResponses = activeSilkWormVarietys.stream()
                .map(silkWormVariety -> mapper.silkWormVarietyEntityToObject(silkWormVariety,SilkWormVarietyResponse.class)).collect(Collectors.toList());
        response.put("silkWormVariety",silkWormVarietyResponses);
        return response;
    }

    @Transactional
    public SilkWormVarietyResponse deleteSilkWormVarietyDetails(long id) {
        SilkWormVarietyResponse silkWormVarietyResponse = new SilkWormVarietyResponse();
        SilkWormVariety silkWormVariety = silkWormVarietyRepository.findBySilkWormVarietyIdAndActive(id, true);
        if (Objects.nonNull(silkWormVariety)) {
            silkWormVariety.setActive(false);
            silkWormVarietyResponse = mapper.silkWormVarietyEntityToObject(silkWormVarietyRepository.save(silkWormVariety), SilkWormVarietyResponse.class);
            silkWormVarietyResponse.setError(false);
        } else {
            silkWormVarietyResponse.setError(true);
            silkWormVarietyResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return silkWormVarietyResponse;
    }

    @Transactional
    public SilkWormVarietyResponse getById(int id){
        SilkWormVarietyResponse silkWormVarietyResponse = new SilkWormVarietyResponse();
        SilkWormVariety silkWormVariety = silkWormVarietyRepository.findBySilkWormVarietyIdAndActive(id,true);
        if(silkWormVariety == null){
            silkWormVarietyResponse.setError(true);
            silkWormVarietyResponse.setError_description("Invalid id");
        }else{
            silkWormVarietyResponse =  mapper.silkWormVarietyEntityToObject(silkWormVariety,SilkWormVarietyResponse.class);
            silkWormVarietyResponse.setError(false);
        }
        log.info("Entity is ",silkWormVariety);
        return silkWormVarietyResponse;
    }

    @Transactional
    public SilkWormVarietyResponse updateSilkWormVarietyDetails(EditSilkWormVarietyRequest silkWormVarietyRequest) {
        SilkWormVarietyResponse silkWormVarietyResponse = new SilkWormVarietyResponse();
        List<SilkWormVariety> silkWormVarietyList = silkWormVarietyRepository.findBySilkWormVarietyName(silkWormVarietyRequest.getSilkWormVarietyName());
        if (silkWormVarietyList.size() > 0) {
            silkWormVarietyResponse.setError(true);
            silkWormVarietyResponse.setError_description("SilkWormVariety already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            SilkWormVariety silkWormVariety = silkWormVarietyRepository.findBySilkWormVarietyIdAndActiveIn(silkWormVarietyRequest.getSilkWormVarietyId(), Set.of(true, false));
            if (Objects.nonNull(silkWormVariety)) {
                silkWormVariety.setSilkWormVarietyName(silkWormVarietyRequest.getSilkWormVarietyName());
                silkWormVariety.setActive(true);
                SilkWormVariety silkWormVariety1 = silkWormVarietyRepository.save(silkWormVariety);
                silkWormVarietyResponse = mapper.silkWormVarietyEntityToObject(silkWormVariety1, SilkWormVarietyResponse.class);
                silkWormVarietyResponse.setError(false);
            } else {
                silkWormVarietyResponse.setError(true);
                silkWormVarietyResponse.setError_description("Error occurred while fetching silkWormVariety");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return silkWormVarietyResponse;
    }
}