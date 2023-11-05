package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.machineTypeMaster.EditMachineTypeMasterRequest;
import com.sericulture.masterdata.model.api.machineTypeMaster.MachineTypeMasterRequest;
import com.sericulture.masterdata.model.api.machineTypeMaster.MachineTypeMasterResponse;
import com.sericulture.masterdata.model.entity.MachineTypeMaster;
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
        MachineTypeMaster machineTypeMaster = null;
        if(machineTypeMaster==null){
            machineTypeMaster = machineTypeMasterRepository.findByMachineTypeNameAndActive(machineTypeMasterName,true);
        }
        log.info("Entity is ",machineTypeMaster);
        return mapper.machineTypeEntityToObject(machineTypeMaster,MachineTypeMasterResponse.class);
    }

    @Transactional
    public MachineTypeMasterResponse insertMachineTypeMasterDetails(MachineTypeMasterRequest machineTypeMasterRequest){
        MachineTypeMaster machineTypeMaster = mapper.machineTypeObjectToEntity(machineTypeMasterRequest,MachineTypeMaster.class);
        validator.validate(machineTypeMaster);
        List<MachineTypeMaster> machineTypeMasterList = machineTypeMasterRepository.findByMachineTypeName(machineTypeMasterRequest.getMachineTypeName());
        if(!machineTypeMasterList.isEmpty() && machineTypeMasterList.stream().filter(MachineTypeMaster::getActive).findAny().isPresent()){
            throw new ValidationException("MachineType name already exist");
        }
        if(!machineTypeMasterList.isEmpty() && machineTypeMasterList.stream().filter(Predicate.not(MachineTypeMaster::getActive)).findAny().isPresent()){
            throw new ValidationException("MachineType name already exist with inactive state");
        }
        return mapper.machineTypeEntityToObject(machineTypeMasterRepository.save(machineTypeMaster),MachineTypeMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMachineTypeMasterDetails(final Pageable pageable){
        return convertToMapResponse(machineTypeMasterRepository.findByActiveOrderByMachineTypeIdAsc( true, pageable));
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

    @Transactional
    public void deleteMachineTypeMasterDetails(long id) {
        MachineTypeMaster machineTypeMaster = machineTypeMasterRepository.findByMachineTypeIdAndActive(id, true);
        if (Objects.nonNull(machineTypeMaster)) {
            machineTypeMaster.setActive(false);
            machineTypeMasterRepository.save(machineTypeMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public MachineTypeMasterResponse getById(int id){
        MachineTypeMaster machineTypeMaster = machineTypeMasterRepository.findByMachineTypeIdAndActive(id,true);
        if(machineTypeMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",machineTypeMaster);
        return mapper.machineTypeEntityToObject(machineTypeMaster,MachineTypeMasterResponse.class);
    }

    @Transactional
    public MachineTypeMasterResponse updateMachineTypeMasterDetails(EditMachineTypeMasterRequest machineTypeMasterRequest){
        List<MachineTypeMaster> machineTypeMasterList = machineTypeMasterRepository.findByMachineTypeName(machineTypeMasterRequest.getMachineTypeName());
        if(machineTypeMasterList.size()>0){
            throw new ValidationException("MachineType already exists with this name, duplicates are not allowed.");
        }

        MachineTypeMaster machineTypeMaster = machineTypeMasterRepository.findByMachineTypeIdAndActiveIn(machineTypeMasterRequest.getMachineTypeId(), Set.of(true,false));
        if(Objects.nonNull(machineTypeMaster)){
            machineTypeMaster.setMachineTypeName(machineTypeMasterRequest.getMachineTypeName());
            machineTypeMaster.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching machineType");
        }
        return mapper.machineTypeEntityToObject(machineTypeMasterRepository.save(machineTypeMaster),MachineTypeMasterResponse.class);
    }

}