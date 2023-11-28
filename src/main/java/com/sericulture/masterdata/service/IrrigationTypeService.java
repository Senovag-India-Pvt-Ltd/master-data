package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.irrigationType.EditIrrigationTypeRequest;
import com.sericulture.masterdata.model.api.irrigationType.IrrigationTypeRequest;
import com.sericulture.masterdata.model.api.irrigationType.IrrigationTypeResponse;
import com.sericulture.masterdata.model.entity.IrrigationType;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.IrrigationTypeRepository;
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
public class IrrigationTypeService {
    @Autowired
    IrrigationTypeRepository irrigationTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public IrrigationTypeResponse getIrrigationTypeDetails(String irrigationTypeName){
        IrrigationType irrigationType = null;
        if(irrigationType==null){
            irrigationType = irrigationTypeRepository.findByIrrigationTypeNameAndActive(irrigationTypeName,true);
        }
        log.info("Entity is ",irrigationType);
        return mapper.irrigationTypeEntityToObject(irrigationType,IrrigationTypeResponse.class);
    }

    @Transactional
    public IrrigationTypeResponse insertIrrigationTypeDetails(IrrigationTypeRequest irrigationTypeRequest){
        IrrigationType irrigationType = mapper.irrigationTypeObjectToEntity(irrigationTypeRequest,IrrigationType.class);
        validator.validate(irrigationType);
        List<IrrigationType> irrigationTypeList = irrigationTypeRepository.findByIrrigationTypeName(irrigationTypeRequest.getIrrigationTypeName());
        if(!irrigationTypeList.isEmpty() && irrigationTypeList.stream().filter(IrrigationType::getActive).findAny().isPresent()){
            throw new ValidationException("IrrigationType name already exist");
        }
        if(!irrigationTypeList.isEmpty() && irrigationTypeList.stream().filter(Predicate.not(IrrigationType::getActive)).findAny().isPresent()){
            throw new ValidationException("IrrigationType name already exist with inactive state");
        }
        return mapper.irrigationTypeEntityToObject(irrigationTypeRepository.save(irrigationType), IrrigationTypeResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedIrrigationTypeDetails(final Pageable pageable){
        return convertToMapResponse(irrigationTypeRepository.findByActiveOrderByIrrigationTypeIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(irrigationTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<IrrigationType> activeIrrigationTypes) {
        Map<String, Object> response = new HashMap<>();

        List<IrrigationTypeResponse> irrigationTypeResponses = activeIrrigationTypes.getContent().stream()
                .map(irrigationType -> mapper.irrigationTypeEntityToObject(irrigationType, IrrigationTypeResponse.class)).collect(Collectors.toList());
        response.put("irrigationType",irrigationTypeResponses);
        response.put("currentPage", activeIrrigationTypes.getNumber());
        response.put("totalItems", activeIrrigationTypes.getTotalElements());
        response.put("totalPages", activeIrrigationTypes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<IrrigationType> activeIrrigationTypes) {
        Map<String, Object> response = new HashMap<>();

        List<IrrigationTypeResponse> irrigationTypeResponses = activeIrrigationTypes.stream()
                .map(irrigationType -> mapper.irrigationTypeEntityToObject(irrigationType,IrrigationTypeResponse.class)).collect(Collectors.toList());
        response.put("irrigationType",irrigationTypeResponses);
        return response;
    }

    @Transactional
    public void deleteIrrigationTypeDetails(long id) {
        IrrigationType irrigationType = irrigationTypeRepository.findByIrrigationTypeIdAndActive(id, true);
        if (Objects.nonNull(irrigationType)) {
            irrigationType.setActive(false);
            irrigationTypeRepository.save(irrigationType);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public IrrigationTypeResponse getById(int id){
        IrrigationType irrigationType = irrigationTypeRepository.findByIrrigationTypeIdAndActive(id,true);
        if(irrigationType == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",irrigationType);
        return mapper.irrigationTypeEntityToObject(irrigationType,IrrigationTypeResponse.class);
    }

    @Transactional
    public IrrigationTypeResponse updateIrrigationTypeDetails(EditIrrigationTypeRequest irrigationTypeRequest){
        List<IrrigationType> irrigationTypeList = irrigationTypeRepository.findByIrrigationTypeName(irrigationTypeRequest.getIrrigationTypeName());
        if(irrigationTypeList.size()>0){
            throw new ValidationException("irrigationType already exists for the given code and title, duplicates are not allowed.");
        }

        IrrigationType irrigationType = irrigationTypeRepository.findByIrrigationTypeIdAndActiveIn(irrigationTypeRequest.getIrrigationTypeId(), Set.of(true,false));
        if(Objects.nonNull(irrigationType)){
            irrigationType.setIrrigationTypeName(irrigationTypeRequest.getIrrigationTypeName());
            irrigationType.setActive(true);
        }
        return mapper.irrigationTypeEntityToObject(irrigationTypeRepository.save(irrigationType),IrrigationTypeResponse.class);
    }
}
