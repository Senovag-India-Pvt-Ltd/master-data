package com.sericulture.masterdata.service;


import com.sericulture.masterdata.model.api.crateMaster.CrateMasterRequest;
import com.sericulture.masterdata.model.api.crateMaster.CrateMasterResponse;
import com.sericulture.masterdata.model.api.crateMaster.EditCrateMasterRequest;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.rpPagePermission.EditRpPagePermissionRequest;
import com.sericulture.masterdata.model.api.rpPagePermission.RpPagePermissionRequest;
import com.sericulture.masterdata.model.api.rpPagePermission.RpPagePermissionResponse;
import com.sericulture.masterdata.model.dto.CrateMasterDTO;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.entity.CrateMaster;
import com.sericulture.masterdata.model.entity.RpPagePermission;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.CrateMasterRepository;
import com.sericulture.masterdata.repository.RpPagePermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrateMasterService {

    @Autowired
    CrateMasterRepository crateMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public CrateMasterResponse insertCrateMasterDetails(CrateMasterRequest crateMasterRequest){
        CrateMasterResponse crateMasterResponse = new CrateMasterResponse();
        CrateMaster crateMaster = mapper.crateMasterObjectToEntity(crateMasterRequest,CrateMaster.class);
        validator.validate(crateMaster);
//        List<RpPagePermission> rpPagePermissionList = rpPagePermissionRepository.findByRpPagePermissionName(rpPagePermissionRequest.getRpPagePermissionName());
//        if(!rpPagePermissionList.isEmpty() && rpPagePermissionList.stream().filter(RpPagePermission::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPagePermission name already exist");
//        }
//        if(!rpPagePermissionList.isEmpty() && rpPagePermissionList.stream().filter(Predicate.not(RpPagePermission::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPagePermission name already exist with inactive state");
//        }
        return mapper.crateMasterEntityToObject(crateMasterRepository.save(crateMaster),CrateMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedCrateMasterDetails(final Pageable pageable){
        return convertToMapResponse(crateMasterRepository.findByActiveOrderByCrateMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(crateMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<CrateMaster> activeCrateMasters) {
        Map<String, Object> response = new HashMap<>();

        List<CrateMasterResponse> crateMasterResponses= activeCrateMasters.getContent().stream()
                .map(crateMaster -> mapper.crateMasterEntityToObject(crateMaster,CrateMasterResponse.class)).collect(Collectors.toList());
        response.put("crateMaster",crateMasterResponses);
        response.put("currentPage", activeCrateMasters.getNumber());
        response.put("totalItems", activeCrateMasters.getTotalElements());
        response.put("totalPages", activeCrateMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<CrateMaster> activeCrateMasters) {
        Map<String, Object> response = new HashMap<>();

        List<CrateMasterResponse> crateMasterResponses = activeCrateMasters.stream()
                .map(crateMaster -> mapper.crateMasterEntityToObject(crateMaster,CrateMasterResponse.class)).collect(Collectors.toList());
        response.put("crateMaster",crateMasterResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedCrateMasterDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(crateMasterRepository.getByActiveOrderByCrateMasterIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<CrateMasterDTO> activeCrateMasters) {
        Map<String, Object> response = new HashMap<>();

        List<CrateMasterResponse> crateMasterResponses = activeCrateMasters.getContent().stream()
                .map(crateMaster -> mapper.crateMasterDTOToObject(crateMaster,CrateMasterResponse.class)).collect(Collectors.toList());
        response.put("crateMaster",crateMasterResponses);
        response.put("currentPage", activeCrateMasters.getNumber());
        response.put("totalItems", activeCrateMasters.getTotalElements());
        response.put("totalPages", activeCrateMasters.getTotalPages());
        return response;
    }


    @Transactional
    public CrateMasterResponse deleteCrateMasterDetails(long id) {
        CrateMasterResponse crateMasterResponse = new CrateMasterResponse();
        CrateMaster crateMaster = crateMasterRepository.findByCrateMasterIdAndActive(id, true);
        if (Objects.nonNull(crateMaster)) {
            crateMaster.setActive(false);
            crateMasterResponse = mapper.crateMasterEntityToObject(crateMasterRepository.save(crateMaster), CrateMasterResponse.class);
            crateMasterResponse.setError(false);
        } else {
            crateMasterResponse.setError(true);
            crateMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return crateMasterResponse;
    }

    @Transactional
    public CrateMasterResponse getById(int id){
        CrateMasterResponse crateMasterResponse = new CrateMasterResponse();
        CrateMaster crateMaster = crateMasterRepository.findByCrateMasterIdAndActive(id,true);


        if(crateMaster == null){
            crateMasterResponse.setError(true);
            crateMasterResponse.setError_description("Invalid id");
        }else{
            crateMasterResponse =  mapper.crateMasterEntityToObject(crateMaster,CrateMasterResponse.class);
            crateMasterResponse.setError(false);
        }
        log.info("Entity is ",crateMaster);
        return crateMasterResponse;
    }

    @Transactional
    public CrateMasterResponse getByIdJoin(int id){
        CrateMasterResponse crateMasterResponse = new CrateMasterResponse();
        CrateMasterDTO crateMasterDTO = crateMasterRepository.getByCrateMasterIdAndActive(id,true);
        if(crateMasterDTO == null){
            crateMasterResponse.setError(true);
            crateMasterResponse.setError_description("Invalid id");
        } else {
            crateMasterResponse = mapper.crateMasterDTOToObject(crateMasterDTO, CrateMasterResponse.class);
            crateMasterResponse.setError(false);
        }
        log.info("Entity is ", crateMasterDTO);
        return crateMasterResponse;
    }

    @Transactional
    public CrateMasterResponse updateCrateMasterDetails(EditCrateMasterRequest crateMasterRequest) {
        CrateMasterResponse crateMasterResponse = new CrateMasterResponse();
//        List<RpPagePermission> rpPagePermissionList = rpPagePermissionRepository.findByRpPagePermissionName(rpPagePermissionRequest.getRpPagePermissionName());
//        if(rpPagePermissionList.size()>0){
//            throw new ValidationException("RpPagePermission already exists with this name, duplicates are not allowed.");
//        }

        CrateMaster crateMaster = crateMasterRepository.findByCrateMasterIdAndActiveIn(crateMasterRequest.getCrateMasterId(), Set.of(true, false));
        if (Objects.nonNull(crateMaster)) {
            crateMaster.setCrateMasterId(crateMasterRequest.getCrateMasterId());
            crateMaster.setRaceMasterId(crateMasterRequest.getRaceMasterId());
            crateMaster.setApproxWeightPerCrate(crateMasterRequest.getApproxWeightPerCrate());
            crateMaster.setGodownId(crateMasterRequest.getGodownId());
            crateMaster.setMarketId(crateMasterRequest.getMarketId());
            crateMaster.setActive(true);
            CrateMaster crateMaster1 = crateMasterRepository.save(crateMaster);
            crateMasterResponse = mapper.crateMasterEntityToObject(crateMaster1, CrateMasterResponse.class);
            crateMasterResponse.setError(false);
        } else {
            crateMasterResponse.setError(true);
            crateMasterResponse.setError_description("Error occurred while fetching Crate");
            // throw new ValidationException("Error occurred while fetching village");
        }

        return crateMasterResponse;
    }
}
