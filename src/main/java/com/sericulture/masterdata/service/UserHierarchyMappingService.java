package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.userHierarchyMapping.EditUserHierarchyMappingRequest;
import com.sericulture.masterdata.model.api.userHierarchyMapping.UserHierarchyMappingRequest;
import com.sericulture.masterdata.model.api.userHierarchyMapping.UserHierarchyMappingResponse;
//import com.sericulture.masterdata.model.dto.UserHierarchyMappingDTO;
import com.sericulture.masterdata.model.entity.UserHierarchyMapping;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.UserHierarchyMappingRepository;
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
public class UserHierarchyMappingService {

    @Autowired
    UserHierarchyMappingRepository userHierarchyMappingRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


//    @Transactional
//    public UserHierarchyMappingResponse insertUserHierarchyMappingDetails(UserHierarchyMappingRequest userHierarchyMappingRequest){
//        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
//        UserHierarchyMapping userHierarchyMapping = mapper.userHierarchyMappingObjectToEntity(userHierarchyMappingRequest,UserHierarchyMapping.class);
//        validator.validate(userHierarchyMapping);
////        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
////        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
////            throw new ValidationException("RpPageRoot name already exist");
////        }
////        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
////            throw new ValidationException("RpPageRoot name already exist with inactive state");
////        }
//
//        return mapper.userHierarchyMappingEntityToObject(userHierarchyMappingRepository.save(userHierarchyMapping), UserHierarchyMappingResponse.class);
//    }

@Transactional
public UserHierarchyMappingResponse insertUserHierarchyMappingDetails(UserHierarchyMappingRequest userHierarchyMappingRequest) {
    UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();

    // Convert request DTO to entity
    UserHierarchyMapping userHierarchyMapping = mapper.userHierarchyMappingObjectToEntity(userHierarchyMappingRequest, UserHierarchyMapping.class);

    // Validate input data
    validator.validate(userHierarchyMapping);

    // Check if a record exists for the given ReporteeUserMasterId
    UserHierarchyMapping existingMapping = userHierarchyMappingRepository.findByReporteeUserMasterIdAndActive(userHierarchyMappingRequest.getReporteeUserMasterId(), true);

    if (existingMapping != null) {
        // Update the existing record
        existingMapping.setReportToUserMasterId(userHierarchyMapping.getReportToUserMasterId());

        // Save updated entity
        userHierarchyMapping = userHierarchyMappingRepository.save(existingMapping);
    } else {
        // Create a new record if no existing mapping is found
        userHierarchyMapping = userHierarchyMappingRepository.save(userHierarchyMapping);
    }

    // Convert entity back to response DTO and return
    return mapper.userHierarchyMappingEntityToObject(userHierarchyMapping, UserHierarchyMappingResponse.class);
}



    public Map<String,Object> getPaginatedUserHierarchyMappingDetails(final Pageable pageable){
        return convertToMapResponse(userHierarchyMappingRepository.findByActiveOrderByUserHierarchyMappingIdAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(userHierarchyMappingRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<UserHierarchyMapping> activeUserHierarchyMappings) {
        Map<String, Object> response = new HashMap<>();

        List<UserHierarchyMappingResponse> userHierarchyMappings = activeUserHierarchyMappings.getContent().stream()
                .map(userHierarchyMapping -> mapper.userHierarchyMappingEntityToObject(userHierarchyMapping,UserHierarchyMappingResponse.class)).collect(Collectors.toList());
        response.put("userHierarchyMapping",userHierarchyMappings);
        response.put("currentPage", activeUserHierarchyMappings.getNumber());
        response.put("totalItems", activeUserHierarchyMappings.getTotalElements());
        response.put("totalPages", activeUserHierarchyMappings.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<UserHierarchyMapping> activeUserHierarchyMappings) {
        Map<String, Object> response = new HashMap<>();

        List<UserHierarchyMappingResponse> userHierarchyMappingResponses = activeUserHierarchyMappings.stream()
                .map(userHierarchyMapping -> mapper.userHierarchyMappingEntityToObject(userHierarchyMapping,UserHierarchyMappingResponse.class)).collect(Collectors.toList());
        response.put("userHierarchyMapping",userHierarchyMappingResponses);
        return response;
    }

    @Transactional
    public UserHierarchyMappingResponse deleteUserHierarchyMappingDetails(long id) {
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByUserHierarchyMappingIdAndActive(id, true);
        if (Objects.nonNull(userHierarchyMapping)) {
            userHierarchyMapping.setActive(false);
            userHierarchyMappingResponse = mapper.userHierarchyMappingEntityToObject(userHierarchyMappingRepository.save(userHierarchyMapping), UserHierarchyMappingResponse.class);
            userHierarchyMappingResponse.setError(false);
        } else {
            userHierarchyMappingResponse.setError(true);
            userHierarchyMappingResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return userHierarchyMappingResponse;
    }

    public UserHierarchyMappingResponse getById(int id){
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByUserHierarchyMappingIdAndActive(id,true);
        if(userHierarchyMapping == null){
            userHierarchyMappingResponse.setError(true);
            userHierarchyMappingResponse.setError_description("Invalid id");
        }else{
            userHierarchyMappingResponse =  mapper.userHierarchyMappingEntityToObject(userHierarchyMapping,UserHierarchyMappingResponse.class);
            userHierarchyMappingResponse.setError(false);
        }
        log.info("Entity is ",userHierarchyMapping);
        return userHierarchyMappingResponse;
    }

    public UserHierarchyMappingResponse getByReporteeUserMasterId(int reporteeUserMasterId){
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByReporteeUserMasterIdAndActive(reporteeUserMasterId,true);
        if(userHierarchyMapping == null){
            userHierarchyMappingResponse.setError(true);
            userHierarchyMappingResponse.setError_description("Invalid id");
        }else{
            userHierarchyMappingResponse =  mapper.userHierarchyMappingEntityToObject(userHierarchyMapping,UserHierarchyMappingResponse.class);
            userHierarchyMappingResponse.setError(false);
        }
        log.info("Entity is ",userHierarchyMapping);
        return userHierarchyMappingResponse;
    }

    @Transactional
    public UserHierarchyMappingResponse updateUserHierarchyMappingDetails(EditUserHierarchyMappingRequest userHierarchyMappingRequest){
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByUserHierarchyMappingIdAndActiveIn(userHierarchyMappingRequest.getUserHierarchyMappingId(), Set.of(true,false));
        if(Objects.nonNull(userHierarchyMapping)){
            userHierarchyMapping.setUserHierarchyMappingId(userHierarchyMappingRequest.getUserHierarchyMappingId());
            userHierarchyMapping.setReporteeUserMasterId(userHierarchyMappingRequest.getReporteeUserMasterId());
            userHierarchyMapping.setReportToUserMasterId(userHierarchyMappingRequest.getReportToUserMasterId());
            userHierarchyMapping.setActive(true);
            UserHierarchyMapping userHierarchyMapping1 = userHierarchyMappingRepository.save(userHierarchyMapping);
            userHierarchyMappingResponse = mapper.userHierarchyMappingEntityToObject(userHierarchyMapping1, UserHierarchyMappingResponse.class);
            userHierarchyMappingResponse.setError(false);
        } else {
            userHierarchyMappingResponse.setError(true);
            userHierarchyMappingResponse.setError_description("Error occurred while fetching userMaster");
            // throw new ValidationException("Error occurred while fetching village");
        }

        return userHierarchyMappingResponse;
    }


}
