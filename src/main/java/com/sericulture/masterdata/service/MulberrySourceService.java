package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.mulberrySource.EditMulberrySourceRequest;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceRequest;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceResponse;
import com.sericulture.masterdata.model.api.mulberryVariety.MulberryVarietyResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.MulberrySource;
import com.sericulture.masterdata.model.entity.MulberryVariety;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MulberrySourceRepository;
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
public class MulberrySourceService {

    @Autowired
    MulberrySourceRepository mulberrySourceRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public MulberrySourceResponse getMulberrySourceDetails(String mulberrySourceName){
        MulberrySourceResponse mulberrySourceResponse = new MulberrySourceResponse();
        MulberrySource mulberrySource = null;
        if(mulberrySource==null){
            mulberrySource = mulberrySourceRepository.findByMulberrySourceNameAndActive(mulberrySourceName,true);
            mulberrySourceResponse = mapper.mulberrySourceEntityToObject(mulberrySource, MulberrySourceResponse.class);
            mulberrySourceResponse.setError(false);
        }else{
            mulberrySourceResponse.setError(true);
            mulberrySourceResponse.setError_description("MulberrySource not found");
        }
        log.info("Entity is ",mulberrySource);
        return mulberrySourceResponse;
    }

    @Transactional
    public MulberrySourceResponse insertMulberrySourceDetails(MulberrySourceRequest mulberrySourceRequest){
        MulberrySourceResponse mulberrySourceResponse = new MulberrySourceResponse();
        MulberrySource mulberrySource = mapper.mulberrySourceObjectToEntity(mulberrySourceRequest,MulberrySource.class);
        validator.validate(mulberrySource);
        List<MulberrySource> mulberrySourceList = mulberrySourceRepository.findByMulberrySourceName(mulberrySourceRequest.getMulberrySourceName());
        if(!mulberrySourceList.isEmpty() && mulberrySourceList.stream().filter(MulberrySource::getActive).findAny().isPresent()){
            mulberrySourceResponse.setError(true);
            mulberrySourceResponse.setError_description("MulberrySource name already exist");
        }
        else if(!mulberrySourceList.isEmpty() && mulberrySourceList.stream().filter(Predicate.not(MulberrySource::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            mulberrySourceResponse.setError(true);
            mulberrySourceResponse.setError_description("MulberrySource name already exist with inactive state");
        }else {
            mulberrySourceResponse = mapper.mulberrySourceEntityToObject(mulberrySourceRepository.save(mulberrySource), MulberrySourceResponse.class);
            mulberrySourceResponse.setError(false);
        }
        return mulberrySourceResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMulberrySourceDetails(final Pageable pageable){
        return convertToMapResponse(mulberrySourceRepository.findByActiveOrderByMulberrySourceNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(mulberrySourceRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<MulberrySource> activeMulberrySources) {
        Map<String, Object> response = new HashMap<>();

        List<MulberrySourceResponse> mulberrySourceResponses = activeMulberrySources.getContent().stream()
                .map(mulberrySource -> mapper.mulberrySourceEntityToObject(mulberrySource,MulberrySourceResponse.class)).collect(Collectors.toList());
        response.put("mulberrySource",mulberrySourceResponses);
        response.put("currentPage", activeMulberrySources.getNumber());
        response.put("totalItems", activeMulberrySources.getTotalElements());
        response.put("totalPages", activeMulberrySources.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<MulberrySource> activeMulberrySources) {
        Map<String, Object> response = new HashMap<>();

        List<MulberrySourceResponse> mulberrySourceResponses = activeMulberrySources.stream()
                .map(mulberrySource -> mapper.mulberrySourceEntityToObject(mulberrySource,MulberrySourceResponse.class)).collect(Collectors.toList());
        response.put("mulberrySource",mulberrySourceResponses);
        return response;
    }

    @Transactional
    public MulberrySourceResponse deleteMulberrySourceDetails(long id) {
        MulberrySourceResponse mulberrySourceResponse = new MulberrySourceResponse();
        MulberrySource mulberrySource = mulberrySourceRepository.findByMulberrySourceIdAndActive(id, true);
        if (Objects.nonNull(mulberrySource)) {
            mulberrySource.setActive(false);
            mulberrySourceResponse = mapper.mulberrySourceEntityToObject(mulberrySourceRepository.save(mulberrySource), MulberrySourceResponse.class);
            mulberrySourceResponse.setError(false);
        } else {
            mulberrySourceResponse.setError(true);
            mulberrySourceResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return mulberrySourceResponse;
    }

    @Transactional
    public MulberrySourceResponse getById(int id){
        MulberrySourceResponse mulberrySourceResponse = new MulberrySourceResponse();
        MulberrySource mulberrySource = mulberrySourceRepository.findByMulberrySourceIdAndActive(id,true);
        if(mulberrySource == null){
            mulberrySourceResponse.setError(true);
            mulberrySourceResponse.setError_description("Invalid id");
        }else{
            mulberrySourceResponse =  mapper.mulberrySourceEntityToObject(mulberrySource,MulberrySourceResponse.class);
            mulberrySourceResponse.setError(false);
        }
        log.info("Entity is ",mulberrySource);
        return mulberrySourceResponse;
    }

    @Transactional
    public MulberrySourceResponse updateMulberrySourceDetails(EditMulberrySourceRequest mulberrySourceRequest) {
        MulberrySourceResponse mulberrySourceResponse = new MulberrySourceResponse();
        List<MulberrySource> mulberrySourceList = mulberrySourceRepository.findByMulberrySourceName(mulberrySourceRequest.getMulberrySourceName());
        if (mulberrySourceList.size() > 0) {
            mulberrySourceResponse.setError(true);
            mulberrySourceResponse.setError_description("MulberrySource already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            MulberrySource mulberrySource = mulberrySourceRepository.findByMulberrySourceIdAndActiveIn(mulberrySourceRequest.getMulberrySourceId(), Set.of(true, false));
            if (Objects.nonNull(mulberrySource)) {
                mulberrySource.setMulberrySourceName(mulberrySourceRequest.getMulberrySourceName());
                mulberrySource.setMulberrySourceNameInKannada(mulberrySourceRequest.getMulberrySourceNameInKannada());
                mulberrySource.setActive(true);
                MulberrySource mulberrySource1 = mulberrySourceRepository.save(mulberrySource);
                mulberrySourceResponse = mapper.mulberrySourceEntityToObject(mulberrySource1, MulberrySourceResponse.class);
                mulberrySourceResponse.setError(false);
            } else {
                mulberrySourceResponse.setError(true);
                mulberrySourceResponse.setError_description("Error occurred while fetching MulberrySource");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return mulberrySourceResponse;
    }
}