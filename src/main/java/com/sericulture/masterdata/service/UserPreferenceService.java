package com.sericulture.masterdata.service;

import com.sericulture.masterdata.controller.GovtSMSServiceController;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.useMaster.*;
import com.sericulture.masterdata.model.api.userPreference.EditUserPreferenceRequest;
import com.sericulture.masterdata.model.api.userPreference.UserPreferenceRequest;
import com.sericulture.masterdata.model.api.userPreference.UserPreferenceResponse;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.dto.UserPreferenceDTO;
import com.sericulture.masterdata.model.dto.govtSmsService.GovtSmsServiceDTO;
import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ReelerRepository;
import com.sericulture.masterdata.repository.UserMasterRepository;
import com.sericulture.masterdata.repository.UserPreferenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserPreferenceService {

    @Autowired
    UserPreferenceRepository userPreferenceRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public UserPreferenceResponse insertUserPreferenceDetails(UserPreferenceRequest userPreferenceRequest){
        UserPreferenceResponse userPreferenceResponse = new UserPreferenceResponse();
        UserPreference userPreference = mapper.userPreferenceObjectToEntity(userPreferenceRequest,UserPreference.class);
        validator.validate(userPreference);
//        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist");
//        }
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist with inactive state");
//        }

        return mapper.userPreferenceEntityToObject(userPreferenceRepository.save(userPreference), UserPreferenceResponse.class);
    }

    public Map<String,Object> getPaginatedUserPreferenceDetails(final Pageable pageable){
        return convertToMapResponse(userPreferenceRepository.findByActiveOrderByUserPreferenceIdAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(userPreferenceRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<UserPreference> activeUserPreferences) {
        Map<String, Object> response = new HashMap<>();

        List<UserPreferenceResponse> userPreferences = activeUserPreferences.getContent().stream()
                .map(userPreference -> mapper.userPreferenceEntityToObject(userPreference,UserPreferenceResponse.class)).collect(Collectors.toList());
        response.put("userPreference",userPreferences);
        response.put("currentPage", activeUserPreferences.getNumber());
        response.put("totalItems", activeUserPreferences.getTotalElements());
        response.put("totalPages", activeUserPreferences.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<UserPreference> activeUserPreferences) {
        Map<String, Object> response = new HashMap<>();

        List<UserPreferenceResponse> userPreferenceResponses = activeUserPreferences.stream()
                .map(userPreference -> mapper.userPreferenceEntityToObject(userPreference,UserPreferenceResponse.class)).collect(Collectors.toList());
        response.put("userPreference",userPreferenceResponses);
        return response;
    }

    public Map<String,Object> getPaginatedUserPreferenceDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(userPreferenceRepository.getByActiveOrderByUserPreferenceIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<UserPreferenceDTO> activeUserPreferences) {
        Map<String, Object> response = new HashMap<>();

        List<UserPreferenceResponse> userPreferenceResponses = activeUserPreferences.getContent().stream()
                .map(userPreference -> mapper.userPreferenceDTOToObject(userPreference,UserPreferenceResponse.class)).collect(Collectors.toList());
        response.put("userPreference",userPreferenceResponses);
        response.put("currentPage", activeUserPreferences.getNumber());
        response.put("totalItems", activeUserPreferences.getTotalElements());
        response.put("totalPages", activeUserPreferences.getTotalPages());
        return response;
    }

    public UserPreferenceResponse getByIdJoin(int id){
        UserPreferenceResponse userPreferenceResponse = new UserPreferenceResponse();
        UserPreferenceDTO userPreferenceDTO = userPreferenceRepository.getByUserPreferenceIdAndActive(id,true);
        if(userPreferenceDTO == null){
//            throw new ValidationException("Invalid Id");
//        }
//        log.info("Entity is ", userMasterDTO);
//        return mapper.userMasterDTOToObject(userMasterDTO, UserMasterResponse.class);
            userPreferenceResponse.setError(true);
            userPreferenceResponse.setError_description("Invalid id");
        }else{
            userPreferenceResponse =  mapper.userPreferenceDTOToObject(userPreferenceDTO,UserPreferenceResponse.class);
            userPreferenceResponse.setError(false);
        }
        log.info("Entity is ",userPreferenceDTO);
        return userPreferenceResponse;
    }

    @Transactional
    public UserPreferenceResponse deleteUserPreferenceDetails(long id) {
        UserPreferenceResponse userPreferenceResponse = new UserPreferenceResponse();
        UserPreference userPreference = userPreferenceRepository.findByUserPreferenceIdAndActive(id, true);
        if (Objects.nonNull(userPreference)) {
            userPreference.setActive(false);
            userPreferenceResponse = mapper.userPreferenceEntityToObject(userPreferenceRepository.save(userPreference), UserPreferenceResponse.class);
            userPreferenceResponse.setError(false);
        } else {
            userPreferenceResponse.setError(true);
            userPreferenceResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return userPreferenceResponse;
    }

    public UserPreferenceResponse getById(int id){
        UserPreferenceResponse userPreferenceResponse = new UserPreferenceResponse();
        UserPreference userPreference = userPreferenceRepository.findByUserPreferenceIdAndActive(id,true);
        if(userPreference == null){
            userPreferenceResponse.setError(true);
            userPreferenceResponse.setError_description("Invalid id");
        }else{
            userPreferenceResponse =  mapper.userPreferenceEntityToObject(userPreference,UserPreferenceResponse.class);
            userPreferenceResponse.setError(false);
        }
        log.info("Entity is ",userPreference);
        return userPreferenceResponse;
    }


    @Transactional
    public UserPreferenceResponse updateUserPreferenceDetails(EditUserPreferenceRequest userPreferenceRequest) {
        UserPreferenceResponse userPreferenceResponse = new UserPreferenceResponse();
        UserPreference userPreference = userPreferenceRepository.findByUserMasterIdAndActive(
                userPreferenceRequest.getUserMasterId(), true);
        if (Objects.nonNull(userPreference)){
            userPreference.setGodownId(userPreferenceRequest.getGodownId());
            // Other fields to update if needed
            userPreference.setActive(true);

            UserPreference updatedUserPreference = userPreferenceRepository.save(userPreference);
            userPreferenceResponse = mapper.userPreferenceEntityToObject(updatedUserPreference, UserPreferenceResponse.class);
            userPreferenceResponse.setError(false);
        }else if(userPreference==null){
            UserPreference newUserPreference = new UserPreference();
            newUserPreference.setUserMasterId(userPreferenceRequest.getUserMasterId());
            newUserPreference.setGodownId(userPreferenceRequest.getGodownId());
            newUserPreference.setActive(true);
            UserPreference savedUserPreference = userPreferenceRepository.save(newUserPreference);
            userPreferenceResponse = mapper.userPreferenceEntityToObject(savedUserPreference, UserPreferenceResponse.class);
            userPreferenceResponse.setError(false);
        }else {
                userPreferenceResponse.setError(true);
                userPreferenceResponse.setError_description("Error occurred while fetching userPreference");
            }
        return userPreferenceResponse;
    }


}
