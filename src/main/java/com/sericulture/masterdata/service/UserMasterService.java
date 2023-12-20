package com.sericulture.masterdata.service;

import com.sericulture.masterdata.controller.GovtSMSServiceController;
import com.sericulture.masterdata.model.api.useMaster.EditUserMasterRequest;
import com.sericulture.masterdata.model.api.useMaster.UserMasterRequest;
import com.sericulture.masterdata.model.api.useMaster.UserMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.dto.govtSmsService.GovtSmsServiceDTO;
import com.sericulture.masterdata.model.entity.UserMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.UserMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserMasterService {

    @Autowired
    UserMasterRepository userMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Autowired
    GovtSMSServiceController govtSMSServiceController;

    @Autowired
    OtpService otpService;

    @Autowired
    private PasswordEncoder encoder;

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public RpPageRootResponse getRpPageRootDetails(String rpPageRootName){
//        RpPageRoot rpPageRoot = null;
//        if(rpPageRoot==null){
//            rpPageRoot = rpPageRootRepository.findByRpPageRootNameAndActive(rpPageRootName,true);
//        }
//        log.info("Entity is ",rpPageRoot);
//        return mapper.rpPageRootEntityToObject(rpPageRoot,RpPageRootResponse.class);
//    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserMasterResponse getLoginDetails(String username, String password){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster1 = userMasterRepository.findByUsername(username);
        if(userMaster1 == null) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("User not found");
        }else {
            if (!encoder.matches(password,userMaster1.getPassword())) {
                 userMasterResponse.setError(true);
                 userMasterResponse.setError_description("Wrong password, please try again!");
            }else {
                userMasterResponse = mapper.userMasterEntityToObject(userMaster1, UserMasterResponse.class);
                userMasterResponse.setError(false);
            }
        }
        log.info("Entity is ",userMaster1);
        return userMasterResponse;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserMasterResponse getByUserNameAndPassword(String username, String password) {
        UserMasterResponse userMasterResponse = new UserMasterResponse();

        // Instead of setting userMaster to null, directly query the repository
        UserMaster userMaster = userMasterRepository.findByUsernameAndPasswordAndActive(username, password, true);

        if (userMaster != null) {
            // User found, map to response
            userMasterResponse = mapper.userMasterEntityToObject(userMaster, UserMasterResponse.class);
            userMasterResponse.setError(false);
        } else {
            // User not found
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("User not found");
        }

        log.info("Entity is {}", userMaster);
        return userMasterResponse;
    }


    @Transactional
    public UserMasterResponse insertUserMasterDetails(UserMasterRequest userMasterRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = mapper.userMasterObjectToEntity(userMasterRequest,UserMaster.class);
        validator.validate(userMaster);
//        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist");
//        }
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist with inactive state");
//        }

        return mapper.userMasterEntityToObject(userMasterRepository.save(userMaster), UserMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedUserMasterDetails(final Pageable pageable){
        return convertToMapResponse(userMasterRepository.findByActiveOrderByUserMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(userMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<UserMaster> activeUserMasters) {
        Map<String, Object> response = new HashMap<>();

        List<UserMasterResponse> userMasters = activeUserMasters.getContent().stream()
                .map(userMaster -> mapper.userMasterEntityToObject(userMaster,UserMasterResponse.class)).collect(Collectors.toList());
        response.put("userMaster",userMasters);
        response.put("currentPage", activeUserMasters.getNumber());
        response.put("totalItems", activeUserMasters.getTotalElements());
        response.put("totalPages", activeUserMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<UserMaster> activeUserMasters) {
        Map<String, Object> response = new HashMap<>();

        List<UserMasterResponse> userMasterResponses = activeUserMasters.stream()
                .map(userMaster -> mapper.userMasterEntityToObject(userMaster,UserMasterResponse.class)).collect(Collectors.toList());
        response.put("userMaster",userMasterResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedUserMasterDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(userMasterRepository.getByActiveOrderByUserMasterIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<UserMasterDTO> activeUserMasters) {
        Map<String, Object> response = new HashMap<>();

        List<UserMasterResponse> userMasterResponses = activeUserMasters.getContent().stream()
                .map(userMaster -> mapper.userMasterDTOToObject(userMaster,UserMasterResponse.class)).collect(Collectors.toList());
        response.put("userMaster",userMasterResponses);
        response.put("currentPage", activeUserMasters.getNumber());
        response.put("totalItems", activeUserMasters.getTotalElements());
        response.put("totalPages", activeUserMasters.getTotalPages());
        return response;
    }

    @Transactional
    public UserMasterResponse getByIdJoin(int id){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMasterDTO userMasterDTO = userMasterRepository.getByUserMasterIdAndActive(id,true);
        if(userMasterDTO == null){
//            throw new ValidationException("Invalid Id");
//        }
//        log.info("Entity is ", userMasterDTO);
//        return mapper.userMasterDTOToObject(userMasterDTO, UserMasterResponse.class);
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Invalid id");
        }else{
            userMasterResponse =  mapper.userMasterDTOToObject(userMasterDTO,UserMasterResponse.class);
            userMasterResponse.setError(false);
        }
        log.info("Entity is ",userMasterDTO);
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse deleteUserMasterDetails(long id) {
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUserMasterIdAndActive(id, true);
        if (Objects.nonNull(userMaster)) {
            userMaster.setActive(false);
            userMasterResponse = mapper.userMasterEntityToObject(userMasterRepository.save(userMaster), UserMasterResponse.class);
            userMasterResponse.setError(false);
        } else {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse getById(int id){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUserMasterIdAndActive(id,true);
        if(userMaster == null){
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Invalid id");
        }else{
            userMasterResponse =  mapper.userMasterEntityToObject(userMaster,UserMasterResponse.class);
            userMasterResponse.setError(false);
        }
        log.info("Entity is ",userMaster);
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse updateUserMasterDetails(EditUserMasterRequest userMasterRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

        UserMaster userMaster = userMasterRepository.findByUserMasterIdAndActiveIn(userMasterRequest.getUserMasterId(), Set.of(true,false));
        if(Objects.nonNull(userMaster)){
            userMaster.setUserMasterId(userMasterRequest.getUserMasterId());
            userMaster.setFirstName(userMasterRequest.getFirstName());
            userMaster.setMiddleName(userMasterRequest.getMiddleName());
            userMaster.setLastName(userMasterRequest.getLastName());
            userMaster.setPassword(userMasterRequest.getPassword());
            userMaster.setEmailID(userMasterRequest.getEmailID());
            userMaster.setStateId(userMasterRequest.getStateId());
            userMaster.setDistrictId(userMasterRequest.getDistrictId());
            userMaster.setTalukId(userMasterRequest.getTalukId());
            userMaster.setRoleId(userMasterRequest.getRoleId());
            userMaster.setMarketMasterId(userMasterRequest.getMarketMasterId());
            userMaster.setPhoneNumber(userMaster.getPhoneNumber());
            userMaster.setUserType(userMaster.getUserType());
            userMaster.setUserTypeId(userMaster.getUserTypeId());
            userMaster.setDeviceId(userMaster.getDeviceId());
            userMaster.setActive(true);
            UserMaster userMaster1 = userMasterRepository.save(userMaster);
            userMasterResponse = mapper.userMasterEntityToObject(userMaster1, UserMasterResponse.class);
            userMasterResponse.setError(false);
        } else {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Error occurred while fetching userMaster");
            // throw new ValidationException("Error occurred while fetching village");
        }

        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse generateOtpByUserName(UserMasterDTO userMasterDTO){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUsernameAndActive(userMasterDTO.getUsername(),true);
        if(userMaster == null){
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Invalid id");
        }else{
            GovtSmsServiceDTO govtSmsServiceDTO = new GovtSmsServiceDTO();
            govtSmsServiceDTO.setUsername("Mobile_1-COMDOS");
            govtSmsServiceDTO.setPassword("COMDOS@1234");
            govtSmsServiceDTO.setMessage("Generate and store otp");
            govtSmsServiceDTO.setSenderId("COMDOS");
            govtSmsServiceDTO.setMobileNumber(userMaster.getPhoneNumber());
            govtSmsServiceDTO.setSecureKey("046bdec5-4bba-69b3-k4e4-01d6b555c9cv");
            govtSmsServiceDTO.setTemplateid("1107170082061011792");
            govtSmsServiceDTO.setUserId(userMaster.getUserMasterId().toString());

            govtSMSServiceController.sendOtpSMS(govtSmsServiceDTO);
            userMasterResponse =  mapper.userMasterEntityToObject(userMaster,UserMasterResponse.class);
            userMasterResponse.setError(false);
        }
        log.info("Entity is ",userMaster);
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse verifyOtp(UserMasterDTO userMasterDTO){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUsernameAndActive(userMasterDTO.getUsername(),true);
        if(userMaster == null){
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Invalid id");
        }else{
            Boolean otpVerificationStatus = otpService.verifyOtp(userMaster.getUserMasterId().toString(), userMasterDTO.getEnteredOtpByUser());
            userMasterResponse = mapper.userMasterEntityToObject(userMaster,UserMasterResponse.class);
            userMasterResponse.setOtpVerified(otpVerificationStatus);
            userMasterResponse.setError(false);
        }
        log.info("Entity is ",userMaster);
        return userMasterResponse;
    }
}