package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.silkwormvariety.EditSilkWormVarietyRequest;
import com.sericulture.masterdata.model.api.silkwormvariety.SilkWormVarietyRequest;
import com.sericulture.masterdata.model.api.silkwormvariety.SilkWormVarietyResponse;
import com.sericulture.masterdata.model.entity.SilkWormVariety;
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
        SilkWormVariety silkWormVariety = null;
        if(silkWormVariety==null){
            silkWormVariety = silkWormVarietyRepository.findBySilkWormVarietyNameAndActive(silkWormVarietyName,true);
        }
        log.info("Entity is ",silkWormVariety);
        return mapper.silkWormVarietyEntityToObject(silkWormVariety,SilkWormVarietyResponse.class);
    }

    @Transactional
    public SilkWormVarietyResponse insertSilkWormVarietyDetails(SilkWormVarietyRequest silkWormVarietyRequest){
        SilkWormVariety silkWormVariety = mapper.silkWormVarietyObjectToEntity(silkWormVarietyRequest,SilkWormVariety.class);
        validator.validate(silkWormVariety);
        List<SilkWormVariety> silkWormVarietyList = silkWormVarietyRepository.findBySilkWormVarietyName(silkWormVarietyRequest.getSilkWormVarietyName());
        if(!silkWormVarietyList.isEmpty() && silkWormVarietyList.stream().filter(SilkWormVariety::getActive).findAny().isPresent()){
            throw new ValidationException("SilkWormVariety name already exist");
        }
        if(!silkWormVarietyList.isEmpty() && silkWormVarietyList.stream().filter(Predicate.not(SilkWormVariety::getActive)).findAny().isPresent()){
            throw new ValidationException("SilkWormVariety name already exist with inactive state");
        }
        return mapper.silkWormVarietyEntityToObject(silkWormVarietyRepository.save(silkWormVariety),SilkWormVarietyResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedSilkWormVarietyDetails(final Pageable pageable){
        return convertToMapResponse(silkWormVarietyRepository.findByActiveOrderBySilkWormVarietyIdAsc( true, pageable));
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

    @Transactional
    public void deleteSilkWormVarietyDetails(long id) {
        SilkWormVariety silkWormVariety = silkWormVarietyRepository.findBySilkWormVarietyIdAndActive(id, true);
        if (Objects.nonNull(silkWormVariety)) {
            silkWormVariety.setActive(false);
            silkWormVarietyRepository.save(silkWormVariety);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public SilkWormVarietyResponse getById(int id){
        SilkWormVariety silkWormVariety = silkWormVarietyRepository.findBySilkWormVarietyIdAndActive(id,true);
        if(silkWormVariety == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",silkWormVariety);
        return mapper.silkWormVarietyEntityToObject(silkWormVariety,SilkWormVarietyResponse.class);
    }

    @Transactional
    public SilkWormVarietyResponse updateSilkWormVarietyDetails(EditSilkWormVarietyRequest silkWormVarietyRequest){
        List<SilkWormVariety> silkWormVarietyList = silkWormVarietyRepository.findBySilkWormVarietyName(silkWormVarietyRequest.getSilkWormVarietyName());
        if(silkWormVarietyList.size()>0){
            throw new ValidationException("SilkWormVariety already exists with this name, duplicates are not allowed.");
        }

        SilkWormVariety silkWormVariety = silkWormVarietyRepository.findBySilkWormVarietyIdAndActiveIn(silkWormVarietyRequest.getSilkWormVarietyId(), Set.of(true,false));
        if(Objects.nonNull(silkWormVariety)){
            silkWormVariety.setSilkWormVarietyName(silkWormVarietyRequest.getSilkWormVarietyName());
            silkWormVariety.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching silkWormVariety");
        }
        return mapper.silkWormVarietyEntityToObject(silkWormVarietyRepository.save(silkWormVariety),SilkWormVarietyResponse.class);
    }

}