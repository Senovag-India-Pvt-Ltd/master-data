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


    @Transactional
    public UserHierarchyMappingResponse insertUserHierarchyMappingDetails(UserHierarchyMappingRequest userHierarchyMappingRequest){
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
        UserHierarchyMapping userHierarchyMapping = mapper.userHierarchyMappingObjectToEntity(userHierarchyMappingRequest,UserHierarchyMapping.class);
        validator.validate(userHierarchyMapping);
//        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist");
//        }
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist with inactive state");
//        }

        return mapper.userHierarchyMappingEntityToObject(userHierarchyMappingRepository.save(userHierarchyMapping), UserHierarchyMappingResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedUserHierarchyMappingDetails(final Pageable pageable){
        return convertToMapResponse(userHierarchyMappingRepository.findByActiveOrderByUserHierarchyMappingIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
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

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getPaginatedUserHierarchyMappingDetailsWithJoin(final Pageable pageable){
//        return convertDTOToMapResponse(userHierarchyMappingRepository.getByActiveOrderByUserHierarchyMappingIdAsc( true, pageable));
//    }
//
//    private Map<String, Object> convertDTOToMapResponse(final Page<UserHierarchyMappingDTO> activeUserHierarchyMappings) {
//        Map<String, Object> response = new HashMap<>();
//
//        List<UserHierarchyMappingResponse> userHierarchyMappingResponses = activeUserHierarchyMappings.getContent().stream()
//                .map(userHierarchyMapping -> mapper.userHierarchyMappingDTOToObject(userHierarchyMapping,UserHierarchyMappingResponse.class)).collect(Collectors.toList());
//        response.put("userHierarchyMapping",userHierarchyMappingResponses);
//        response.put("currentPage", activeUserHierarchyMappings.getNumber());
//        response.put("totalItems", activeUserHierarchyMappings.getTotalElements());
//        response.put("totalPages", activeUserHierarchyMappings.getTotalPages());
//        return response;
//    }

//    @Transactional
//    public UserHierarchyMappingResponse getByIdJoin(int id){
//        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
//        UserHierarchyMappingDTO userHierarchyMappingDTO = userHierarchyMappingRepository.getByUserHierarchyMappingIdAndActive(id,true);
//        if(userHierarchyMappingDTO == null){
////            throw new ValidationException("Invalid Id");
////        }
////        log.info("Entity is ", userMasterDTO);
////        return mapper.userMasterDTOToObject(userMasterDTO, UserMasterResponse.class);
//            userHierarchyMappingResponse.setError(true);
//            userHierarchyMappingResponse.setError_description("Invalid id");
//        }else{
//            userHierarchyMappingResponse =  mapper.userHierarchyMappingDTOToObject(userHierarchyMappingDTO,UserHierarchyMappingResponse.class);
//            userHierarchyMappingResponse.setError(false);
//        }
//        log.info("Entity is ",userHierarchyMappingDTO);
//        return userHierarchyMappingResponse;
//    }

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

    @Transactional
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

//    @Transactional
//    public UserHierarchyMappingResponse updateUserHierarchyMappingDetails(EditUserHierarchyMappingRequest userHierarchyMappingRequest) {
//        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
//        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByUserMasterIdAndActive(
//                userHierarchyMappingRequest.getUserMasterId(), true);
//        if (Objects.nonNull(userHierarchyMapping)){
//            userHierarchyMapping.setGodownId(userHierarchyMappingRequest.getGodownId());
//            // Other fields to update if needed
//            userHierarchyMapping.setActive(true);
//
//            UserHierarchyMapping updatedUserHierarchyMapping = userHierarchyMappingRepository.save(userHierarchyMapping);
//            userHierarchyMappingResponse = mapper.userHierarchyMappingEntityToObject(updatedUserHierarchyMapping, UserHierarchyMappingResponse.class);
//            userHierarchyMappingResponse.setError(false);
//        }else if(userHierarchyMapping==null){
//            UserHierarchyMapping newUserHierarchyMapping = new UserHierarchyMapping();
//            newUserHierarchyMapping.setUserMasterId(userHierarchyMappingRequest.getUserMasterId());
//            newUserHierarchyMapping.setGodownId(userHierarchyMappingRequest.getGodownId());
//            newUserHierarchyMapping.setActive(true);
//            UserHierarchyMapping savedUserHierarchyMapping = userHierarchyMappingRepository.save(newUserHierarchyMapping);
//            userHierarchyMappingResponse = mapper.userHierarchyMappingEntityToObject(savedUserHierarchyMapping, UserHierarchyMappingResponse.class);
//            userHierarchyMappingResponse.setError(false);
//        }else {
//            userHierarchyMappingResponse.setError(true);
//            userHierarchyMappingResponse.setError_description("Error occurred while fetching userHierarchyMapping");
//        }
//        return userHierarchyMappingResponse;
//    }
}
