package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.irrigationSource.EditIrrigationSourceRequest;
import com.sericulture.masterdata.model.api.irrigationSource.IrrigationSourceRequest;
import com.sericulture.masterdata.model.api.irrigationSource.IrrigationSourceResponse;
import com.sericulture.masterdata.model.api.landOwnership.LandOwnershipResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.IrrigationSource;
import com.sericulture.masterdata.model.entity.LandOwnership;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.IrrigationSourceRepository;
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
public class IrrigationSourceService {
    @Autowired
    IrrigationSourceRepository irrigationSourceRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public IrrigationSourceResponse getIrrigationSourceDetails(String irrigationSourceName){
        IrrigationSourceResponse irrigationSourceResponse = new IrrigationSourceResponse();
        IrrigationSource irrigationSource = irrigationSourceRepository.findByIrrigationSourceNameAndActive(irrigationSourceName,true);
        if(irrigationSource==null){
            irrigationSourceResponse.setError(true);
            irrigationSourceResponse.setError_description("Irrigation Source not found");
        }else{
            irrigationSourceResponse = mapper.irrigationSourceEntityToObject(irrigationSource, IrrigationSourceResponse.class);
            irrigationSourceResponse.setError(false);
        }
        log.info("Entity is ",irrigationSource);
        return irrigationSourceResponse;
    }

    @Transactional
    public IrrigationSourceResponse insertIrrigationSourceDetails(IrrigationSourceRequest irrigationSourceRequest){
        IrrigationSourceResponse irrigationSourceResponse = new IrrigationSourceResponse();
        IrrigationSource irrigationSource = mapper.irrigationSourceObjectToEntity(irrigationSourceRequest,IrrigationSource.class);
        validator.validate(irrigationSource);
        List<IrrigationSource> irrigationSourceList = irrigationSourceRepository.findByIrrigationSourceNameAndIrrigationSourceNameInKannada(irrigationSourceRequest.getIrrigationSourceName(),irrigationSourceRequest.getIrrigationSourceNameInKannada());
        if(!irrigationSourceList.isEmpty() && irrigationSourceList.stream().filter(IrrigationSource::getActive).findAny().isPresent()){
            irrigationSourceResponse.setError(true);
            irrigationSourceResponse.setError_description("IrrigationSource name already exist");
//        }
//        else if(!irrigationSourceList.isEmpty() && irrigationSourceList.stream().filter(Predicate.not(IrrigationSource::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            irrigationSourceResponse.setError(true);
//            irrigationSourceResponse.setError_description("Village name already exist with inactive state");
        }else {
            irrigationSourceResponse = mapper.irrigationSourceEntityToObject(irrigationSourceRepository.save(irrigationSource), IrrigationSourceResponse.class);
            irrigationSourceResponse.setError(false);
        }
        return irrigationSourceResponse;
    }

    public Map<String,Object> getPaginatedIrrigationSourceDetails(final Pageable pageable){
        return convertToMapResponse(irrigationSourceRepository.findByActiveOrderByIrrigationSourceNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(irrigationSourceRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<IrrigationSource> activeIrrigationSources) {
        Map<String, Object> response = new HashMap<>();

        List<IrrigationSourceResponse> irrigationSourceResponses = activeIrrigationSources.getContent().stream()
                .map(irrigationSource -> mapper.irrigationSourceEntityToObject(irrigationSource, IrrigationSourceResponse.class)).collect(Collectors.toList());
        response.put("irrigationSource",irrigationSourceResponses);
        response.put("currentPage", activeIrrigationSources.getNumber());
        response.put("totalItems", activeIrrigationSources.getTotalElements());
        response.put("totalPages", activeIrrigationSources.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<IrrigationSource> activeIrrigationSources) {
        Map<String, Object> response = new HashMap<>();

        List<IrrigationSourceResponse> irrigationSourceResponses = activeIrrigationSources.stream()
                .map(irrigationSource -> mapper.irrigationSourceEntityToObject(irrigationSource,IrrigationSourceResponse.class)).collect(Collectors.toList());
        response.put("irrigationSource",irrigationSourceResponses);
        return response;
    }

    @Transactional
    public IrrigationSourceResponse deleteIrrigationSourceDetails(long id) {
        IrrigationSourceResponse irrigationSourceResponse = new IrrigationSourceResponse();
        IrrigationSource irrigationSource = irrigationSourceRepository.findByIrrigationSourceIdAndActive(id, true);
        if (Objects.nonNull(irrigationSource)) {
            irrigationSource.setActive(false);
            irrigationSourceResponse = mapper.irrigationSourceEntityToObject(irrigationSourceRepository.save(irrigationSource), IrrigationSourceResponse.class);
            irrigationSourceResponse.setError(false);
        } else {
            irrigationSourceResponse.setError(true);
            irrigationSourceResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return irrigationSourceResponse;
    }

    public IrrigationSourceResponse getById(int id){
        IrrigationSourceResponse irrigationSourceResponse = new IrrigationSourceResponse();
        IrrigationSource irrigationSource = irrigationSourceRepository.findByIrrigationSourceIdAndActive(id,true);
        if(irrigationSource == null){
            irrigationSourceResponse.setError(true);
            irrigationSourceResponse.setError_description("Invalid id");
        }else{
            irrigationSourceResponse =  mapper.irrigationSourceEntityToObject(irrigationSource,IrrigationSourceResponse.class);
            irrigationSourceResponse.setError(false);
        }
        log.info("Entity is ",irrigationSource);
        return irrigationSourceResponse;
    }

    @Transactional
    public IrrigationSourceResponse updateIrrigationSourceDetails(EditIrrigationSourceRequest irrigationSourceRequest) {
        IrrigationSourceResponse irrigationSourceResponse = new IrrigationSourceResponse();
        List<IrrigationSource> irrigationSourceList = irrigationSourceRepository.findByActiveAndIrrigationSourceNameAndIrrigationSourceNameInKannada(true,irrigationSourceRequest.getIrrigationSourceName(),irrigationSourceRequest.getIrrigationSourceNameInKannada());
        if (irrigationSourceList.size() > 0) {
            irrigationSourceResponse.setError(true);
            irrigationSourceResponse.setError_description("IrrigationSource already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            IrrigationSource irrigationSource = irrigationSourceRepository.findByIrrigationSourceIdAndActiveIn(irrigationSourceRequest.getIrrigationSourceId(), Set.of(true, false));
            if (Objects.nonNull(irrigationSource)) {
                irrigationSource.setIrrigationSourceName(irrigationSourceRequest.getIrrigationSourceName());
                irrigationSource.setIrrigationSourceNameInKannada(irrigationSourceRequest.getIrrigationSourceNameInKannada());
                irrigationSource.setActive(true);
                IrrigationSource irrigationSource1 = irrigationSourceRepository.save(irrigationSource);
                irrigationSourceResponse = mapper.irrigationSourceEntityToObject(irrigationSource1, IrrigationSourceResponse.class);
                irrigationSourceResponse.setError(false);
            } else {
                irrigationSourceResponse.setError(true);
                irrigationSourceResponse.setError_description("Error occurred while fetching irrigationSource");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return irrigationSourceResponse;
    }
}


