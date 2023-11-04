package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.mulberrySource.EditMulberrySourceRequest;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceRequest;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceResponse;
import com.sericulture.masterdata.model.entity.MulberrySource;
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
        MulberrySource mulberrySource = null;
        if(mulberrySource==null){
            mulberrySource = mulberrySourceRepository.findByMulberrySourceNameAndActive(mulberrySourceName,true);
        }
        log.info("Entity is ",mulberrySource);
        return mapper.mulberrySourceEntityToObject(mulberrySource,MulberrySourceResponse.class);
    }

    @Transactional
    public MulberrySourceResponse insertMulberrySourceDetails(MulberrySourceRequest mulberrySourceRequest){
        MulberrySource mulberrySource = mapper.mulberrySourceObjectToEntity(mulberrySourceRequest,MulberrySource.class);
        validator.validate(mulberrySource);
        List<MulberrySource> mulberrySourceList = mulberrySourceRepository.findByMulberrySourceName(mulberrySourceRequest.getMulberrySourceName());
        if(!mulberrySourceList.isEmpty() && mulberrySourceList.stream().filter(MulberrySource::getActive).findAny().isPresent()){
            throw new ValidationException("MulberrySource name already exist");
        }
        if(!mulberrySourceList.isEmpty() && mulberrySourceList.stream().filter(Predicate.not(MulberrySource::getActive)).findAny().isPresent()){
            throw new ValidationException("MulberrySource name already exist with inactive state");
        }
        return mapper.mulberrySourceEntityToObject(mulberrySourceRepository.save(mulberrySource),MulberrySourceResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMulberrySourceDetails(final Pageable pageable){
        return convertToMapResponse(mulberrySourceRepository.findByActiveOrderByMulberrySourceIdAsc( true, pageable));
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

    @Transactional
    public void deleteMulberrySourceDetails(long id) {
        MulberrySource mulberrySource = mulberrySourceRepository.findByMulberrySourceIdAndActive(id, true);
        if (Objects.nonNull(mulberrySource)) {
            mulberrySource.setActive(false);
            mulberrySourceRepository.save(mulberrySource);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public MulberrySourceResponse getById(int id){
        MulberrySource mulberrySource = mulberrySourceRepository.findByMulberrySourceIdAndActive(id,true);
        if(mulberrySource == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",mulberrySource);
        return mapper.mulberrySourceEntityToObject(mulberrySource,MulberrySourceResponse.class);
    }

    @Transactional
    public MulberrySourceResponse updateMulberrySourceDetails(EditMulberrySourceRequest mulberrySourceRequest){
        List<MulberrySource> mulberrySourceList = mulberrySourceRepository.findByMulberrySourceName(mulberrySourceRequest.getMulberrySourceName());
        if(mulberrySourceList.size()>0){
            throw new ValidationException("MulberrySource already exists with this name, duplicates are not allowed.");
        }

        MulberrySource mulberrySource = mulberrySourceRepository.findByMulberrySourceIdAndActiveIn(mulberrySourceRequest.getMulberrySourceId(), Set.of(true,false));
        if(Objects.nonNull(mulberrySource)){
            mulberrySource.setMulberrySourceName(mulberrySourceRequest.getMulberrySourceName());
            mulberrySource.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching mulberrySource");
        }
        return mapper.mulberrySourceEntityToObject(mulberrySourceRepository.save(mulberrySource),MulberrySourceResponse.class);
    }

}