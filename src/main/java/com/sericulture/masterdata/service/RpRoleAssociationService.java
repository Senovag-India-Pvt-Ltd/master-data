package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.rpPagePermission.RpPagePermissionResponse;
import com.sericulture.masterdata.model.api.rpRoleAssociation.EditRpRoleAssociationRequest;
import com.sericulture.masterdata.model.api.rpRoleAssociation.RpRoleAssociationRequest;
import com.sericulture.masterdata.model.api.rpRoleAssociation.RpRoleAssociationResponse;
import com.sericulture.masterdata.model.api.rpRoleAssociation.SaveRoleAssociationRequest;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.RpRoleAssociation;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RpRoleAssociationRepository;
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
public class RpRoleAssociationService {
    @Autowired
    RpRoleAssociationRepository rpRoleAssociationRepository;

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
    public RpRoleAssociationResponse insertRpRoleAssociationDetails(RpRoleAssociationRequest rpRoleAssociationRequest) {
        RpRoleAssociationResponse rpRoleAssociationResponse = new RpRoleAssociationResponse();
        RpRoleAssociation rpRoleAssociation = mapper.rpRoleAssociationObjectToEntity(rpRoleAssociationRequest, RpRoleAssociation.class);
        validator.validate(rpRoleAssociation);
//        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist");
//        }
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist with inactive state");
//        }
        return mapper.rpRoleAssociationEntityToObject(rpRoleAssociationRepository.save(rpRoleAssociation), RpRoleAssociationResponse.class);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getPaginatedRpRoleAssociationDetails(final Pageable pageable) {
        return convertToMapResponse(rpRoleAssociationRepository.findByActiveOrderByRpRoleAssociationIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(rpRoleAssociationRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<RpRoleAssociation> activeRpRoleAssociations) {
        Map<String, Object> response = new HashMap<>();

        List<RpRoleAssociationResponse> rpRoleAssociationResponses = activeRpRoleAssociations.getContent().stream()
                .map(rpRoleAssociation -> mapper.rpRoleAssociationEntityToObject(rpRoleAssociation, RpRoleAssociationResponse.class)).collect(Collectors.toList());
        response.put("rpRoleAssociation", rpRoleAssociationResponses);
        response.put("currentPage", activeRpRoleAssociations.getNumber());
        response.put("totalItems", activeRpRoleAssociations.getTotalElements());
        response.put("totalPages", activeRpRoleAssociations.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RpRoleAssociation> activeRpRoleAssociations) {
        Map<String, Object> response = new HashMap<>();

        List<RpRoleAssociationResponse> rpRoleAssociationResponses = activeRpRoleAssociations.stream()
                .map(rpRoleAssociation -> mapper.rpRoleAssociationEntityToObject(rpRoleAssociation, RpRoleAssociationResponse.class)).collect(Collectors.toList());
        response.put("rpRoleAssociation", activeRpRoleAssociations);
        return response;
    }

    @Transactional
    public RpRoleAssociationResponse deleteRpRoleAssociationDetails(long id) {
        RpRoleAssociationResponse rpRoleAssociationResponse = new RpRoleAssociationResponse();
        RpRoleAssociation rpRoleAssociation = rpRoleAssociationRepository.findByRpRoleAssociationIdAndActive(id, true);
        if (Objects.nonNull(rpRoleAssociation)) {
            rpRoleAssociation.setActive(false);
            rpRoleAssociationResponse = mapper.rpRoleAssociationEntityToObject(rpRoleAssociationRepository.save(rpRoleAssociation), RpRoleAssociationResponse.class);
            rpRoleAssociationResponse.setError(false);
        } else {
            rpRoleAssociationResponse.setError(true);
            rpRoleAssociationResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return rpRoleAssociationResponse;
    }

    @Transactional
    public RpRoleAssociationResponse getById(int id) {
        RpRoleAssociationResponse rpRoleAssociationResponse = new RpRoleAssociationResponse();
        RpRoleAssociation rpRoleAssociation = rpRoleAssociationRepository.findByRpRoleAssociationIdAndActive(id, true);
        if (rpRoleAssociation == null) {
            rpRoleAssociationResponse.setError(true);
            rpRoleAssociationResponse.setError_description("Invalid id");
        }else{
            rpRoleAssociationResponse =  mapper.rpRoleAssociationEntityToObject(rpRoleAssociation,RpRoleAssociationResponse.class);
            rpRoleAssociationResponse.setError(false);
        }
        log.info("Entity is ",rpRoleAssociation);
        return rpRoleAssociationResponse;
    }

    @Transactional
    public RpRoleAssociationResponse updateRpRoleAssociationDetails(EditRpRoleAssociationRequest rpRoleAssociationRequest) {
        RpRoleAssociationResponse rpRoleAssociationResponse = new RpRoleAssociationResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

        RpRoleAssociation rpRoleAssociation = rpRoleAssociationRepository.findByRpRoleAssociationIdAndActiveIn(rpRoleAssociationRequest.getRpRoleAssociationId(), Set.of(true, false));
        if (Objects.nonNull(rpRoleAssociation)) {
            rpRoleAssociation.setRoleId(rpRoleAssociationRequest.getRoleId());
            rpRoleAssociation.setValue(rpRoleAssociationRequest.getValue());
            rpRoleAssociation.setRpRolePermissionId(rpRoleAssociationRequest.getRpRolePermissionId());
            rpRoleAssociation.setActive(true);
            RpRoleAssociation rpRoleAssociation1 = rpRoleAssociationRepository.save(rpRoleAssociation);
            rpRoleAssociationResponse = mapper.rpRoleAssociationEntityToObject(rpRoleAssociation1, RpRoleAssociationResponse.class);
            rpRoleAssociationResponse.setError(false);
        } else {
            rpRoleAssociationResponse.setError(true);
            rpRoleAssociationResponse.setError_description("Error occurred while fetching RpRoleAssociation");
            // throw new ValidationException("Error occurred while fetching village");
        }

    return rpRoleAssociationResponse;
}

    @Transactional
    public RpRoleAssociationResponse saveMultipleRpRoleAssociationDetails(SaveRoleAssociationRequest saveRoleAssociationRequest) {
        RpRoleAssociationResponse rpRoleAssociationResponse = new RpRoleAssociationResponse();
        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRoleIdAndRpRolePermissionIdAndActive(saveRoleAssociationRequest.getRoleId(), saveRoleAssociationRequest.getRpRolePermissionId(), true);
        if(rpRoleAssociationList.size()>0){
            for(RpRoleAssociation rpRoleAssociation: rpRoleAssociationList){
                rpRoleAssociationRepository.deleteById(rpRoleAssociation.getRpRoleAssociationId());
            }
        }
        for (int i = 0; i < saveRoleAssociationRequest.getValues().size(); i++) {
            RpRoleAssociation rpRoleAssociation = new RpRoleAssociation();
            rpRoleAssociation.setRoleId(saveRoleAssociationRequest.getRoleId());
            rpRoleAssociation.setRpRolePermissionId(saveRoleAssociationRequest.getRpRolePermissionId());
            rpRoleAssociation.setValue(saveRoleAssociationRequest.getValues().get(i));
            rpRoleAssociationRepository.save(rpRoleAssociation);
        }
        rpRoleAssociationResponse.setSuccess(true);
        return rpRoleAssociationResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public  Map<String, Object> getByRoleIdAndRpRolePermissionId(Long roleId, Long rolePermissionId) {
        Map<String, Object> response = new HashMap<>();
        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRoleIdAndRpRolePermissionIdAndActive(roleId, rolePermissionId, true);
        if(rpRoleAssociationList.size()<=0){
            response.put("error","Error");
            response.put("error_description","No records found");
        }else {
            log.info("Entity is ", rpRoleAssociationList);
            response = convertListToMapResponse(rpRoleAssociationList);
        }
        return response;
    }

    private Map<String, Object> convertListToMapResponse(List<RpRoleAssociation> rpRoleAssociationList) {
        Map<String, Object> response = new HashMap<>();
        List<RpRoleAssociationResponse> rpRoleAssociationResponses = rpRoleAssociationList.stream()
                .map(rpRoleAssociation -> mapper.rpRoleAssociationEntityToObject(rpRoleAssociation,RpRoleAssociationResponse.class)).collect(Collectors.toList());
        response.put("rpRoleAssociation",rpRoleAssociationResponses);
        response.put("totalItems", rpRoleAssociationList.size());
        return response;
    }
}
