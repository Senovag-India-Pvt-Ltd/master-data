package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.rpRoleAssociation.EditRpRoleAssociationRequest;
import com.sericulture.masterdata.model.api.rpRoleAssociation.RpRoleAssociationRequest;
import com.sericulture.masterdata.model.api.rpRoleAssociation.RpRoleAssociationResponse;
import com.sericulture.masterdata.model.api.rpRolePermission.EditRpRolePermissionRequest;
import com.sericulture.masterdata.model.api.rpRolePermission.RpRolePermissionRequest;
import com.sericulture.masterdata.model.api.rpRolePermission.RpRolePermissionResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.RpRoleAssociation;
import com.sericulture.masterdata.model.entity.RpRolePermission;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RpRoleAssociationRepository;
import com.sericulture.masterdata.repository.RpRolePermissionRepository;
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
public class RpRolePermissionService {

    @Autowired
    RpRolePermissionRepository rpRolePermissionRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public RpPageRootResponse getRpPageRootDetails(String rpPageRootName){
//        RpPageRoot rpPageRoot = null;
//        if(rpPageRoot==null){
//            rpPageRoot = rpPageRootRepository.findByRpPageRootNameAndActive(rpPageRootName,true);
//        }
//        log.info("Entity is ",rpPageRoot);
//        return mapper.rpPageRootEntityToObject(rpPageRoot,RpPageRootResponse.class);
//    }

    @Transactional
    public RpRolePermissionResponse insertRpRolePermissionResponseDetails(RpRolePermissionRequest rpRolePermissionRequest) {
        RpRolePermissionResponse rpRolePermissionResponse = new RpRolePermissionResponse();
        RpRolePermission rpRolePermission = mapper.rpRolePermissionObjectToEntity(rpRolePermissionRequest, RpRolePermission.class);
        validator.validate(rpRolePermission);
//        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist");
//        }
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist with inactive state");
//        }
//        return mapper.rpRolePermissionEntityToObject(rpRolePermissionRepository.save(rpRolePermission), RpRolePermissionResponse.class);
        return rpRolePermissionResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getPaginatedRpRolePermissionDetails(final Pageable pageable) {
        return convertToMapResponse(rpRolePermissionRepository.findByActiveOrderByRpRolePermissionIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(rpRolePermissionRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<RpRolePermission> activeRpRolePermissions) {
        Map<String, Object> response = new HashMap<>();

        List<RpRolePermissionResponse> rpRolePermissionResponses = activeRpRolePermissions.getContent().stream()
                .map(rpRolePermission -> mapper.rpRolePermissionEntityToObject(rpRolePermission, RpRolePermissionResponse.class)).collect(Collectors.toList());
        response.put("rpRolePermission", rpRolePermissionResponses);
        response.put("currentPage", activeRpRolePermissions.getNumber());
        response.put("totalItems", activeRpRolePermissions.getTotalElements());
        response.put("totalPages", activeRpRolePermissions.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RpRolePermission> activeRpRolePermissions) {
        Map<String, Object> response = new HashMap<>();

        List<RpRolePermissionResponse> rpRolePermissionResponses = activeRpRolePermissions.stream()
                .map(rpRolePermission -> mapper.rpRolePermissionEntityToObject(rpRolePermission, RpRolePermissionResponse.class)).collect(Collectors.toList());
        response.put("rpRolePermission", activeRpRolePermissions);
        return response;
    }

    @Transactional
    public RpRolePermissionResponse deleteRpRolePermissionDetails(long id) {
        RpRolePermissionResponse rpRolePermissionResponse = new RpRolePermissionResponse();
        RpRolePermission rpRolePermission = rpRolePermissionRepository.findByRpRolePermissionIdAndActive(id, true);
        if (Objects.nonNull(rpRolePermission)) {
            rpRolePermission.setActive(false);
            rpRolePermissionResponse = mapper.rpRolePermissionEntityToObject(rpRolePermissionRepository.save(rpRolePermission), RpRolePermissionResponse.class);
            rpRolePermissionResponse.setError(false);
        } else {
            rpRolePermissionResponse.setError(true);
            rpRolePermissionResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return rpRolePermissionResponse;
    }

    @Transactional
    public RpRolePermissionResponse getById(int id) {
        RpRolePermissionResponse rpRolePermissionResponse = new RpRolePermissionResponse();
        RpRolePermission rpRolePermission = rpRolePermissionRepository.findByRpRolePermissionIdAndActive(id, true);
        if (rpRolePermission == null) {
            rpRolePermissionResponse.setError(true);
            rpRolePermissionResponse.setError_description("Invalid id");
        } else {
            rpRolePermissionResponse = mapper.rpRolePermissionEntityToObject(rpRolePermission, RpRolePermissionResponse.class);
            rpRolePermissionResponse.setError(false);
        }
        log.info("Entity is ", rpRolePermission);
        return rpRolePermissionResponse;
    }

    @Transactional
    public RpRolePermissionResponse updateRpRolePermissionDetails(EditRpRolePermissionRequest rpRolePermissionRequest) {
        RpRolePermissionResponse rpRolePermissionResponse = new RpRolePermissionResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

            RpRolePermission rpRolePermission = rpRolePermissionRepository.findByRpRolePermissionIdAndActiveIn(rpRolePermissionRequest.getRpRolePermissionId(), Set.of(true, false));
            if (Objects.nonNull(rpRolePermission)) {
                rpRolePermission.setType(rpRolePermissionRequest.getType());
                rpRolePermission.setValue(rpRolePermissionRequest.getValue());
                rpRolePermission.setActive(true);
                RpRolePermission rpRolePermission1 = rpRolePermissionRepository.save(rpRolePermission);
                rpRolePermissionResponse = mapper.rpRolePermissionEntityToObject(rpRolePermission1, RpRolePermissionResponse.class);
                rpRolePermissionResponse.setError(false);
            } else {
                rpRolePermissionResponse.setError(true);
                rpRolePermissionResponse.setError_description("Error occurred while fetching rpRolePermission");
                // throw new ValidationException("Error occurred while fetching village");
            }

        return rpRolePermissionResponse;
    }

}
