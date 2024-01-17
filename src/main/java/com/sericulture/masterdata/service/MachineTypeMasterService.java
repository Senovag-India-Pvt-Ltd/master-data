package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.machineTypeMaster.EditMachineTypeMasterRequest;
import com.sericulture.masterdata.model.api.machineTypeMaster.MachineTypeMasterRequest;
import com.sericulture.masterdata.model.api.machineTypeMaster.MachineTypeMasterResponse;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.MachineTypeMaster;
import com.sericulture.masterdata.model.entity.MarketMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MachineTypeMasterRepository;
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
public class MachineTypeMasterService {

    @Autowired
    MachineTypeMasterRepository machineTypeMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public MachineTypeMasterResponse getMachineTypeMasterDetails(String machineTypeMasterName){
        MachineTypeMasterResponse machineTypeMasterResponse = new MachineTypeMasterResponse();
        MachineTypeMaster machineTypeMaster = null;
        if(machineTypeMaster==null){
            machineTypeMaster = machineTypeMasterRepository.findByMachineTypeNameAndActive(machineTypeMasterName,true);
            machineTypeMasterResponse = mapper.machineTypeEntityToObject(machineTypeMaster, MachineTypeMasterResponse.class);
            machineTypeMasterResponse.setError(false);
        }else{
            machineTypeMasterResponse.setError(true);
            machineTypeMasterResponse.setError_description("MachineType not found");
        }
        log.info("Entity is ",machineTypeMaster);
        return machineTypeMasterResponse;
    }

    @Transactional
    public MachineTypeMasterResponse insertMachineTypeMasterDetails(MachineTypeMasterRequest machineTypeMasterRequest){
        MachineTypeMasterResponse machineTypeMasterResponse = new MachineTypeMasterResponse();
        MachineTypeMaster machineTypeMaster = mapper.machineTypeObjectToEntity(machineTypeMasterRequest,MachineTypeMaster.class);
        validator.validate(machineTypeMaster);
        List<MachineTypeMaster> machineTypeMasterList = machineTypeMasterRepository.findByMachineTypeNameAndMachineTypeNameInKannada(machineTypeMasterRequest.getMachineTypeName(),machineTypeMasterRequest.getMachineTypeNameInKannada());
        if(!machineTypeMasterList.isEmpty() && machineTypeMasterList.stream().filter(MachineTypeMaster::getActive).findAny().isPresent()){
            machineTypeMasterResponse.setError(true);
            machineTypeMasterResponse.setError_description("MachineType name already exist");
        }
        else if(!machineTypeMasterList.isEmpty() && machineTypeMasterList.stream().filter(Predicate.not(MachineTypeMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            machineTypeMasterResponse.setError(true);
            machineTypeMasterResponse.setError_description("MachineType name already exist with inactive state");
        }else {
            machineTypeMasterResponse = mapper.machineTypeEntityToObject(machineTypeMasterRepository.save(machineTypeMaster), MachineTypeMasterResponse.class);
            machineTypeMasterResponse.setError(false);
        }
        return machineTypeMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMachineTypeMasterDetails(final Pageable pageable){
        return convertToMapResponse(machineTypeMasterRepository.findByActiveOrderByMachineTypeNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(machineTypeMasterRepository.findByActiveOrderByMachineTypeNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<MachineTypeMaster> activeMachineTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<MachineTypeMasterResponse> machineTypeMasterResponses = activeMachineTypeMasters.getContent().stream()
                .map(machineTypeMaster -> mapper.machineTypeEntityToObject(machineTypeMaster,MachineTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("machineTypeMaster",machineTypeMasterResponses);
        response.put("currentPage", activeMachineTypeMasters.getNumber());
        response.put("totalItems", activeMachineTypeMasters.getTotalElements());
        response.put("totalPages", activeMachineTypeMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<MachineTypeMaster> activeMachineTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<MachineTypeMasterResponse> machineTypeMasterResponses = activeMachineTypeMasters.stream()
                .map(machineTypeMaster -> mapper.machineTypeEntityToObject(machineTypeMaster,MachineTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("machineTypeMaster",machineTypeMasterResponses);
        return response;
    }

    @Transactional
    public MachineTypeMasterResponse deleteMachineTypeMasterDetails(long id) {
        MachineTypeMasterResponse machineTypeMasterResponse = new MachineTypeMasterResponse();
        MachineTypeMaster machineTypeMaster = machineTypeMasterRepository.findByMachineTypeIdAndActive(id, true);
        if (Objects.nonNull(machineTypeMaster)) {
            machineTypeMaster.setActive(false);
            machineTypeMasterResponse = mapper.machineTypeEntityToObject(machineTypeMasterRepository.save(machineTypeMaster), MachineTypeMasterResponse.class);
            machineTypeMasterResponse.setError(false);
        } else {
            machineTypeMasterResponse.setError(true);
            machineTypeMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return machineTypeMasterResponse;
    }

    @Transactional
    public MachineTypeMasterResponse getById(int id){
        MachineTypeMasterResponse machineTypeMasterResponse = new MachineTypeMasterResponse();
        MachineTypeMaster machineTypeMaster = machineTypeMasterRepository.findByMachineTypeIdAndActive(id,true);
        if(machineTypeMaster == null){
            machineTypeMasterResponse.setError(true);
            machineTypeMasterResponse.setError_description("Invalid id");
        }else{
            machineTypeMasterResponse =  mapper.machineTypeEntityToObject(machineTypeMaster,MachineTypeMasterResponse.class);
            machineTypeMasterResponse.setError(false);
        }
        log.info("Entity is ",machineTypeMaster);
        return machineTypeMasterResponse;
    }

    @Transactional
    public MachineTypeMasterResponse updateMachineTypeMasterDetails(EditMachineTypeMasterRequest machineTypeMasterRequest) {
        MachineTypeMasterResponse machineTypeMasterResponse = new MachineTypeMasterResponse();
        List<MachineTypeMaster> machineTypeMasterList = machineTypeMasterRepository.findByMachineTypeNameAndMachineTypeNameInKannada(machineTypeMasterRequest.getMachineTypeName(),machineTypeMasterRequest.getMachineTypeNameInKannada());
        if (machineTypeMasterList.size() > 0) {
            machineTypeMasterResponse.setError(true);
            machineTypeMasterResponse.setError_description("MachineType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            MachineTypeMaster machineTypeMaster = machineTypeMasterRepository.findByMachineTypeIdAndActiveIn(machineTypeMasterRequest.getMachineTypeId(), Set.of(true, false));
            if (Objects.nonNull(machineTypeMaster)) {
                machineTypeMaster.setMachineTypeName(machineTypeMasterRequest.getMachineTypeName());
                machineTypeMaster.setMachineTypeNameInKannada(machineTypeMasterRequest.getMachineTypeNameInKannada());
                machineTypeMaster.setActive(true);
                MachineTypeMaster machineTypeMaster1 = machineTypeMasterRepository.save(machineTypeMaster);
                machineTypeMasterResponse = mapper.machineTypeEntityToObject(machineTypeMaster, MachineTypeMasterResponse.class);
                machineTypeMasterResponse.setError(false);
            } else {
                machineTypeMasterResponse.setError(true);
                machineTypeMasterResponse.setError_description("Error occurred while fetching machineType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return machineTypeMasterResponse;
    }
}