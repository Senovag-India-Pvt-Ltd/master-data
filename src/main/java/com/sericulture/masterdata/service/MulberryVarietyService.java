package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.machineTypeMaster.MachineTypeMasterResponse;
import com.sericulture.masterdata.model.api.mulberryVariety.EditMulberryVarietyRequest;
import com.sericulture.masterdata.model.api.mulberryVariety.MulberryVarietyRequest;
import com.sericulture.masterdata.model.api.mulberryVariety.MulberryVarietyResponse;
import com.sericulture.masterdata.model.api.plantationType.PlantationTypeResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.MulberryVariety;
import com.sericulture.masterdata.model.entity.PlantationType;
import com.sericulture.masterdata.model.entity.Village;
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
        MulberryVarietyResponse mulberryVarietyResponse = new MulberryVarietyResponse();
        MulberryVariety mulberryVariety = null;
        if(mulberryVariety==null){
            mulberryVariety = mulberryVarietyRepository.findByMulberryVarietyNameAndActive(mulberryVarietyName,true);
            mulberryVarietyResponse = mapper.mulberryVarietyEntityToObject(mulberryVariety, MulberryVarietyResponse.class);
            mulberryVarietyResponse.setError(false);
        }else{
            mulberryVarietyResponse.setError(true);
            mulberryVarietyResponse.setError_description("MulberryVariety not found");
        }
        log.info("Entity is ",mulberryVariety);
        return mulberryVarietyResponse;
    }

    @Transactional
    public MulberryVarietyResponse insertMulberryVarietyDetails(MulberryVarietyRequest mulberryVarietyRequest){
        MulberryVarietyResponse mulberryVarietyResponse = new MulberryVarietyResponse();
        MulberryVariety mulberryVariety = mapper.mulberryVarietyObjectToEntity(mulberryVarietyRequest,MulberryVariety.class);
        validator.validate(mulberryVariety);
        List<MulberryVariety> mulberryVarietyList = mulberryVarietyRepository.findByMulberryVarietyName(mulberryVarietyRequest.getMulberryVarietyName());
        if(!mulberryVarietyList.isEmpty() && mulberryVarietyList.stream().filter(MulberryVariety::getActive).findAny().isPresent()){
            mulberryVarietyResponse.setError(true);
            mulberryVarietyResponse.setError_description("MulberryVariety name already exist");
        }
        else if(!mulberryVarietyList.isEmpty() && mulberryVarietyList.stream().filter(Predicate.not(MulberryVariety::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            mulberryVarietyResponse.setError(true);
            mulberryVarietyResponse.setError_description("MulberryVariety name already exist with inactive state");
        }else {
            mulberryVarietyResponse = mapper.mulberryVarietyEntityToObject(mulberryVarietyRepository.save(mulberryVariety), MulberryVarietyResponse.class);
            mulberryVarietyResponse.setError(false);
        }
        return mulberryVarietyResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMulberryVarietyDetails(final Pageable pageable){
        return convertToMapResponse(mulberryVarietyRepository.findByActiveOrderByMulberryVarietyNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(mulberryVarietyRepository.findByActive(isActive));
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

    private Map<String, Object> convertListEntityToMapResponse(final List<MulberryVariety> activeMulberryVarietys) {
        Map<String, Object> response = new HashMap<>();

        List<MulberryVarietyResponse> mulberryVarietyResponses = activeMulberryVarietys.stream()
                .map(mulberryVariety -> mapper.mulberryVarietyEntityToObject(mulberryVariety,MulberryVarietyResponse.class)).collect(Collectors.toList());
        response.put("mulberryVariety",mulberryVarietyResponses);
        return response;
    }

    @Transactional
    public MulberryVarietyResponse deleteMulberryVarietyDetails(long id) {
        MulberryVarietyResponse mulberryVarietyResponse = new MulberryVarietyResponse();
        MulberryVariety mulberryVariety = mulberryVarietyRepository.findByMulberryVarietyIdAndActive(id, true);
        if (Objects.nonNull(mulberryVariety)) {
            mulberryVariety.setActive(false);
            mulberryVarietyResponse = mapper.mulberryVarietyEntityToObject(mulberryVarietyRepository.save(mulberryVariety), MulberryVarietyResponse.class);
            mulberryVarietyResponse.setError(false);
        } else {
            mulberryVarietyResponse.setError(true);
            mulberryVarietyResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return mulberryVarietyResponse;
    }

    @Transactional
    public MulberryVarietyResponse getById(int id){
        MulberryVarietyResponse mulberryVarietyResponse = new MulberryVarietyResponse();
        MulberryVariety mulberryVariety = mulberryVarietyRepository.findByMulberryVarietyIdAndActive(id,true);
        if(mulberryVariety == null){
            mulberryVarietyResponse.setError(true);
            mulberryVarietyResponse.setError_description("Invalid id");
        }else{
            mulberryVarietyResponse =  mapper.mulberryVarietyEntityToObject(mulberryVariety,MulberryVarietyResponse.class);
            mulberryVarietyResponse.setError(false);
        }
        log.info("Entity is ",mulberryVariety);
        return mulberryVarietyResponse;
    }

    @Transactional
    public MulberryVarietyResponse updateMulberryVarietyDetails(EditMulberryVarietyRequest mulberryVarietyRequest){
        MulberryVarietyResponse mulberryVarietyResponse = new MulberryVarietyResponse();
        List<MulberryVariety> mulberryVarietyList = mulberryVarietyRepository.findByMulberryVarietyName(mulberryVarietyRequest.getMulberryVarietyName());
        if(mulberryVarietyList.size()>0){
            mulberryVarietyResponse.setError(true);
            mulberryVarietyResponse.setError_description("MulberryVariety already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {

            MulberryVariety mulberryVariety = mulberryVarietyRepository.findByMulberryVarietyIdAndActiveIn(mulberryVarietyRequest.getMulberryVarietyId(), Set.of(true,false));
            if(Objects.nonNull(mulberryVariety)){
                mulberryVariety.setMulberryVarietyName(mulberryVarietyRequest.getMulberryVarietyName());
                mulberryVariety.setMulberryVarietyNameInKannada(mulberryVarietyRequest.getMulberryVarietyNameInKannada());
                mulberryVariety.setActive(true);
                MulberryVariety mulberryVariety1 = mulberryVarietyRepository.save(mulberryVariety);
                mulberryVarietyResponse = mapper.mulberryVarietyEntityToObject(mulberryVariety1, MulberryVarietyResponse.class);
                mulberryVarietyResponse.setError(false);
            } else {
                mulberryVarietyResponse.setError(true);
                mulberryVarietyResponse.setError_description("Error occurred while fetching mulberryVariety");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return mulberryVarietyResponse;
    }

}