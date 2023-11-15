package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.rpPagePermission.EditRpPagePermissionRequest;
import com.sericulture.masterdata.model.api.rpPagePermission.RpPagePermissionRequest;
import com.sericulture.masterdata.model.api.rpPagePermission.RpPagePermissionResponse;
import com.sericulture.masterdata.model.entity.RpPagePermission;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RpPagePermissionRepository;
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
public class RpPagePermissionService {
    @Autowired
    RpPagePermissionRepository rpPagePermissionRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public RpPagePermissionResponse insertRpPagePermissionDetails(RpPagePermissionRequest rpPagePermissionRequest){
        RpPagePermission rpPagePermission = mapper.rpPagePermissionObjectToEntity(rpPagePermissionRequest,RpPagePermission.class);
        validator.validate(rpPagePermission);
//        List<RpPagePermission> rpPagePermissionList = rpPagePermissionRepository.findByRpPagePermissionName(rpPagePermissionRequest.getRpPagePermissionName());
//        if(!rpPagePermissionList.isEmpty() && rpPagePermissionList.stream().filter(RpPagePermission::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPagePermission name already exist");
//        }
//        if(!rpPagePermissionList.isEmpty() && rpPagePermissionList.stream().filter(Predicate.not(RpPagePermission::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPagePermission name already exist with inactive state");
//        }
        return mapper.rpPagePermissionEntityToObject(rpPagePermissionRepository.save(rpPagePermission),RpPagePermissionResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRpPagePermissionDetails(final Pageable pageable){
        return convertToMapResponse(rpPagePermissionRepository.findByActiveOrderByRpPagePermissionIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(rpPagePermissionRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<RpPagePermission> activeRpPagePermissions) {
        Map<String, Object> response = new HashMap<>();

        List<RpPagePermissionResponse> rpPagePermissionResponses = activeRpPagePermissions.getContent().stream()
                .map(rpPagePermission -> mapper.rpPagePermissionEntityToObject(rpPagePermission,RpPagePermissionResponse.class)).collect(Collectors.toList());
        response.put("rpPagePermission",rpPagePermissionResponses);
        response.put("currentPage", activeRpPagePermissions.getNumber());
        response.put("totalItems", activeRpPagePermissions.getTotalElements());
        response.put("totalPages", activeRpPagePermissions.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RpPagePermission> activeRpPagePermissions) {
        Map<String, Object> response = new HashMap<>();

        List<RpPagePermissionResponse> rpPagePermissionResponses = activeRpPagePermissions.stream()
                .map(rpPagePermission -> mapper.rpPagePermissionEntityToObject(rpPagePermission,RpPagePermissionResponse.class)).collect(Collectors.toList());
        response.put("rpPagePermission",rpPagePermissionResponses);
        return response;
    }

    @Transactional
    public void deleteRpPagePermissionDetails(long id) {
        RpPagePermission rpPagePermission = rpPagePermissionRepository.findByRpPagePermissionIdAndActive(id, true);
        if (Objects.nonNull(rpPagePermission)) {
            rpPagePermission.setActive(false);
            rpPagePermissionRepository.save(rpPagePermission);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public RpPagePermissionResponse getById(int id){
        RpPagePermission rpPagePermission = rpPagePermissionRepository.findByRpPagePermissionIdAndActive(id,true);
        if(rpPagePermission == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",rpPagePermission);
        return mapper.rpPagePermissionEntityToObject(rpPagePermission,RpPagePermissionResponse.class);
    }

    @Transactional
    public RpPagePermissionResponse updateRpPagePermissionDetails(EditRpPagePermissionRequest rpPagePermissionRequest){
//        List<RpPagePermission> rpPagePermissionList = rpPagePermissionRepository.findByRpPagePermissionName(rpPagePermissionRequest.getRpPagePermissionName());
//        if(rpPagePermissionList.size()>0){
//            throw new ValidationException("RpPagePermission already exists with this name, duplicates are not allowed.");
//        }

        RpPagePermission rpPagePermission = rpPagePermissionRepository.findByRpPagePermissionIdAndActiveIn(rpPagePermissionRequest.getRpPagePermissionId(), Set.of(true,false));
        if(Objects.nonNull(rpPagePermission)){
            rpPagePermission.setRoot(rpPagePermissionRequest.getRoot());
            rpPagePermission.setParent(rpPagePermissionRequest.getParent());
            rpPagePermission.setPageName(rpPagePermissionRequest.getPageName());
            rpPagePermission.setRoute(rpPagePermissionRequest.getRoute());
            rpPagePermission.setIsPage(rpPagePermissionRequest.getIsPage());
            rpPagePermission.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching roof type");
        }
        return mapper.rpPagePermissionEntityToObject(rpPagePermissionRepository.save(rpPagePermission),RpPagePermissionResponse.class);
    }
}
