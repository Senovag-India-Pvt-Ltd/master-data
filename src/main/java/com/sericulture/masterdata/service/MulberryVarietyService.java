package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.mulberryVariety.EditMulberryVarietyRequest;
import com.sericulture.masterdata.model.api.mulberryVariety.MulberryVarietyRequest;
import com.sericulture.masterdata.model.api.mulberryVariety.MulberryVarietyResponse;
import com.sericulture.masterdata.model.entity.MulberryVariety;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MulberryVarietyRepository;
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
public class MulberryVarietyService {

    @Autowired
    MulberryVarietyRepository mulberryVarietyRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public MulberryVarietyResponse getMulberryVarietyDetails(String mulberryVarietyName){
        MulberryVariety mulberryVariety = null;
        if(mulberryVariety==null){
            mulberryVariety = mulberryVarietyRepository.findByMulberryVarietyNameAndActive(mulberryVarietyName,true);
        }
        log.info("Entity is ",mulberryVariety);
        return mapper.mulberryVarietyEntityToObject(mulberryVariety,MulberryVarietyResponse.class);
    }

    @Transactional
    public MulberryVarietyResponse insertMulberryVarietyDetails(MulberryVarietyRequest mulberryVarietyRequest){
        MulberryVariety mulberryVariety = mapper.mulberryVarietyObjectToEntity(mulberryVarietyRequest,MulberryVariety.class);
        validator.validate(mulberryVariety);
        List<MulberryVariety> mulberryVarietyList = mulberryVarietyRepository.findByMulberryVarietyName(mulberryVarietyRequest.getMulberryVarietyName());
        if(!mulberryVarietyList.isEmpty() && mulberryVarietyList.stream().filter(MulberryVariety::getActive).findAny().isPresent()){
            throw new ValidationException("MulberryVariety name already exist");
        }
        if(!mulberryVarietyList.isEmpty() && mulberryVarietyList.stream().filter(Predicate.not(MulberryVariety::getActive)).findAny().isPresent()){
            throw new ValidationException("MulberryVariety name already exist with inactive state");
        }
        return mapper.mulberryVarietyEntityToObject(mulberryVarietyRepository.save(mulberryVariety),MulberryVarietyResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMulberryVarietyDetails(final Pageable pageable){
        return convertToMapResponse(mulberryVarietyRepository.findByActiveOrderByMulberryVarietyIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<MulberryVariety> activeMulberryVarietys) {
        Map<String, Object> response = new HashMap<>();

        List<MulberryVarietyResponse> mulberryVarietyResponses = activeMulberryVarietys.getContent().stream()
                .map(mulberryVariety -> mapper.mulberryVarietyEntityToObject(mulberryVariety,MulberryVarietyResponse.class)).collect(Collectors.toList());
        response.put("mulberryVariety",mulberryVarietyResponses);
        response.put("currentPage", activeMulberryVarietys.getNumber());
        response.put("totalItems", activeMulberryVarietys.getTotalElements());
        response.put("totalPages", activeMulberryVarietys.getTotalPages());

        return response;
    }

    @Transactional
    public void deleteMulberryVarietyDetails(long id) {
        MulberryVariety mulberryVariety = mulberryVarietyRepository.findByMulberryVarietyIdAndActive(id, true);
        if (Objects.nonNull(mulberryVariety)) {
            mulberryVariety.setActive(false);
            mulberryVarietyRepository.save(mulberryVariety);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public MulberryVarietyResponse getById(int id){
        MulberryVariety mulberryVariety = mulberryVarietyRepository.findByMulberryVarietyIdAndActive(id,true);
        if(mulberryVariety == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",mulberryVariety);
        return mapper.mulberryVarietyEntityToObject(mulberryVariety,MulberryVarietyResponse.class);
    }

    @Transactional
    public MulberryVarietyResponse updateMulberryVarietyDetails(EditMulberryVarietyRequest mulberryVarietyRequest){
        List<MulberryVariety> mulberryVarietyList = mulberryVarietyRepository.findByMulberryVarietyName(mulberryVarietyRequest.getMulberryVarietyName());
        if(mulberryVarietyList.size()>0){
            throw new ValidationException("MulberryVariety already exists with this name, duplicates are not allowed.");
        }

        MulberryVariety mulberryVariety = mulberryVarietyRepository.findByMulberryVarietyIdAndActiveIn(mulberryVarietyRequest.getMulberryVarietyId(), Set.of(true,false));
        if(Objects.nonNull(mulberryVariety)){
            mulberryVariety.setMulberryVarietyName(mulberryVarietyRequest.getMulberryVarietyName());
            mulberryVariety.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching mulberryVariety");
        }
        return mapper.mulberryVarietyEntityToObject(mulberryVarietyRepository.save(mulberryVariety),MulberryVarietyResponse.class);
    }

}