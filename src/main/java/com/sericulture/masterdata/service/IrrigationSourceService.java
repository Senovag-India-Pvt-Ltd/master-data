package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.irrigationSource.EditIrrigationSourceRequest;
import com.sericulture.masterdata.model.api.irrigationSource.IrrigationSourceRequest;
import com.sericulture.masterdata.model.api.irrigationSource.IrrigationSourceResponse;
import com.sericulture.masterdata.model.entity.IrrigationSource;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public IrrigationSourceResponse getIrrigationSourceDetails(String irrigationSourceName){
        IrrigationSource irrigationSource = null;
        if(irrigationSource==null){
            irrigationSource = irrigationSourceRepository.findByIrrigationSourceNameAndActive(irrigationSourceName,true);
        }
        log.info("Entity is ",irrigationSource);
        return mapper.irrigationSourceEntityToObject(irrigationSource,IrrigationSourceResponse.class);
    }

    @Transactional
    public IrrigationSourceResponse insertIrrigationSourceDetails(IrrigationSourceRequest irrigationSourceRequest){
        IrrigationSource irrigationSource = mapper.irrigationSourceObjectToEntity(irrigationSourceRequest,IrrigationSource.class);
        validator.validate(irrigationSource);
        List<IrrigationSource> irrigationSourceList = irrigationSourceRepository.findByIrrigationSourceName(irrigationSourceRequest.getIrrigationSourceName());
        if(!irrigationSourceList.isEmpty() && irrigationSourceList.stream().filter(IrrigationSource::getActive).findAny().isPresent()){
            throw new ValidationException("IrrigationSource name already exist");
        }
        if(!irrigationSourceList.isEmpty() && irrigationSourceList.stream().filter(Predicate.not(IrrigationSource::getActive)).findAny().isPresent()){
            throw new ValidationException("IrrigationSource name already exist with inactive state");
        }
        return mapper.irrigationSourceEntityToObject(irrigationSourceRepository.save(irrigationSource), IrrigationSourceResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedIrrigationSourceDetails(final Pageable pageable){
        return convertToMapResponse(irrigationSourceRepository.findByActiveOrderByIrrigationSourceIdAsc( true, pageable));
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

    @Transactional
    public void deleteIrrigationSourceDetails(long id) {
        IrrigationSource irrigationSource = irrigationSourceRepository.findByIrrigationSourceIdAndActive(id, true);
        if (Objects.nonNull(irrigationSource)) {
            irrigationSource.setActive(false);
            irrigationSourceRepository.save(irrigationSource);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public IrrigationSourceResponse getById(int id){
        IrrigationSource irrigationSource = irrigationSourceRepository.findByIrrigationSourceIdAndActive(id,true);
        if(irrigationSource == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",irrigationSource);
        return mapper.irrigationSourceEntityToObject(irrigationSource,IrrigationSourceResponse.class);
    }

    @Transactional
    public IrrigationSourceResponse updateIrrigationSourceDetails(EditIrrigationSourceRequest irrigationSourceRequest){
        List<IrrigationSource> irrigationSourceList = irrigationSourceRepository.findByIrrigationSourceName(irrigationSourceRequest.getIrrigationSourceName());
        if(irrigationSourceList.size()>0){
            throw new ValidationException("irrigationSource already exists for the given code and title, duplicates are not allowed.");
        }

        IrrigationSource irrigationSource = irrigationSourceRepository.findByIrrigationSourceIdAndActiveIn(irrigationSourceRequest.getIrrigationSourceId(), Set.of(true,false));
        if(Objects.nonNull(irrigationSource)){
            irrigationSource.setIrrigationSourceName(irrigationSourceRequest.getIrrigationSourceName());
            irrigationSource.setActive(true);
        }
        return mapper.irrigationSourceEntityToObject(irrigationSourceRepository.save(irrigationSource),IrrigationSourceResponse.class);
    }

}


