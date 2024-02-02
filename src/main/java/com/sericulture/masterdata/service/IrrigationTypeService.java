package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.irrigationType.EditIrrigationTypeRequest;
import com.sericulture.masterdata.model.api.irrigationType.IrrigationTypeRequest;
import com.sericulture.masterdata.model.api.irrigationType.IrrigationTypeResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.IrrigationType;
import com.sericulture.masterdata.model.entity.Village;
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
        IrrigationTypeResponse irrigationTypeResponse = new IrrigationTypeResponse();
        IrrigationType irrigationType = irrigationTypeRepository.findByIrrigationTypeNameAndActive(irrigationTypeName,true);
        if(irrigationType==null){
            irrigationTypeResponse.setError(true);
            irrigationTypeResponse.setError_description("Irrigation Type not found");
        }else{
            irrigationTypeResponse = mapper.irrigationTypeEntityToObject(irrigationType, IrrigationTypeResponse.class);
            irrigationTypeResponse.setError(false);
        }
        log.info("Entity is ",irrigationType);
        return irrigationTypeResponse;
    }

    @Transactional
    public IrrigationTypeResponse insertIrrigationTypeDetails(IrrigationTypeRequest irrigationTypeRequest){
        IrrigationTypeResponse irrigationTypeResponse = new IrrigationTypeResponse();
        IrrigationType irrigationType = mapper.irrigationTypeObjectToEntity(irrigationTypeRequest,IrrigationType.class);
        validator.validate(irrigationType);
        List<IrrigationType> irrigationTypeList = irrigationTypeRepository.findByIrrigationTypeNameAndIrrigationTypeNameInKannada(irrigationTypeRequest.getIrrigationTypeName(),irrigationTypeRequest.getIrrigationTypeNameInKannada());
        if(!irrigationTypeList.isEmpty() && irrigationTypeList.stream().filter(IrrigationType::getActive).findAny().isPresent()){
            irrigationTypeResponse.setError(true);
            irrigationTypeResponse.setError_description("IrrigationType name already exist");
        }
        else if(!irrigationTypeList.isEmpty() && irrigationTypeList.stream().filter(Predicate.not(IrrigationType::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            irrigationTypeResponse.setError(true);
            irrigationTypeResponse.setError_description("IrrigationType name already exist with inactive state");
        }else {
            irrigationTypeResponse = mapper.irrigationTypeEntityToObject(irrigationTypeRepository.save(irrigationType), IrrigationTypeResponse.class);
            irrigationTypeResponse.setError(false);
        }
        return irrigationTypeResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedIrrigationTypeDetails(final Pageable pageable){
        return convertToMapResponse(irrigationTypeRepository.findByActiveOrderByIrrigationTypeNameAsc( true, pageable));
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
    public IrrigationTypeResponse deleteIrrigationTypeDetails(long id) {
        IrrigationTypeResponse irrigationTypeResponse = new IrrigationTypeResponse();
        IrrigationType irrigationType = irrigationTypeRepository.findByIrrigationTypeIdAndActive(id, true);
        if (Objects.nonNull(irrigationType)) {
            irrigationType.setActive(false);
            irrigationTypeResponse = mapper.irrigationTypeEntityToObject(irrigationTypeRepository.save(irrigationType), IrrigationTypeResponse.class);
            irrigationTypeResponse.setError(false);
        } else {
            irrigationTypeResponse.setError(true);
            irrigationTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return irrigationTypeResponse;
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
    public IrrigationTypeResponse updateIrrigationTypeDetails(EditIrrigationTypeRequest irrigationTypeRequest) {
        IrrigationTypeResponse irrigationTypeResponse = new IrrigationTypeResponse();
        List<IrrigationType> irrigationTypeList = irrigationTypeRepository.findByIrrigationTypeNameAndIrrigationTypeNameInKannada(irrigationTypeRequest.getIrrigationTypeName(),irrigationTypeRequest.getIrrigationTypeNameInKannada());
        if (irrigationTypeList.size() > 0) {
            irrigationTypeResponse.setError(true);
            irrigationTypeResponse.setError_description("IrrigationType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            IrrigationType irrigationType = irrigationTypeRepository.findByIrrigationTypeIdAndActiveIn(irrigationTypeRequest.getIrrigationTypeId(), Set.of(true, false));
            if (Objects.nonNull(irrigationType)) {
                irrigationType.setIrrigationTypeName(irrigationTypeRequest.getIrrigationTypeName());
                irrigationType.setIrrigationTypeNameInKannada(irrigationTypeRequest.getIrrigationTypeNameInKannada());
                irrigationType.setActive(true);
                IrrigationType irrigationType1 = irrigationTypeRepository.save(irrigationType);
                irrigationTypeResponse = mapper.irrigationTypeEntityToObject(irrigationType1, IrrigationTypeResponse.class);
                irrigationTypeResponse.setError(false);
            } else {
                irrigationTypeResponse.setError(true);
                irrigationTypeResponse.setError_description("Error occurred while fetching irrigationType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return irrigationTypeResponse;
    }
}
