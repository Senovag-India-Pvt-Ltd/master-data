package com.sericulture.masterdata.service;

import com.sericulture.masterdata.controller.GovtSMSServiceController;
import com.sericulture.masterdata.helper.Util;
import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.useMaster.*;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.dto.govtSmsService.GovtSmsServiceDTO;
import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
    ReelerRepository reelerRepository;

    @Autowired
    ReelerTypeMasterRepository reelerTypeMasterRepository;

    @Autowired
    TraderTypeMasterRepository traderTypeMasterRepository;

    @Autowired
    TraderLicenseRepository traderLicenseRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    ExternalUnitRegistrationRepository externalUnitRegistrationRepository;

    @Value("${otp.sms.username}")
    private String otpUserName;

    @Value("${otp.sms.password}")
    private String otpPassword;

    @Value("${otp.sms.senderId}")
    private String otpSenderId;

    @Value("${otp.sms.secureKey}")
    private String otpSecureKey;

    @Value("${otp.sms.templateId}")
    private String otpTemplateId;


//    @Transactional(isolation = Isolation.READ_COMMITTED)
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
//    @Transactional(isolation = Isolation.READ_COMMITTED)
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

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public  Map<String, Object> getByRoleIdAndTalukId(Long roleId,Long talukId) {
        Map<String, Object> response = new HashMap<>();
        List<UserMasterDTO> userMasterDTOS = userMasterRepository.getByRoleIdAndTalukIdAndActive(roleId,talukId,true);
        if(userMasterDTOS.size()<=0){
            response.put("error","Error");
            response.put("error_description","No records found");
        }else {
            log.info("Entity is ", userMasterDTOS);
            response = convertDTOToMapResponse(userMasterDTOS);
        }
        return response;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public  Map<String, Object> getByDesignationIdAndDistrictId(Long designationId,Long districtId) {
        Map<String, Object> response = new HashMap<>();
        List<UserMasterDTO> userMasterDTOS = userMasterRepository.getByDesignationIdAndDistrictIdAndActive(designationId,districtId,true);
        if(userMasterDTOS.size()<=0){
            response.put("error","Error");
            response.put("error_description","No records found");
        }else {
            log.info("Entity is ", userMasterDTOS);
            response = convertDTOToMapResponse(userMasterDTOS);
        }
        return response;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public  Map<String, Object> getByDesignationIdAndDistrictIdAndTalukId(Long designationId,Long districtId, Long talukId) {
        Map<String, Object> response = new HashMap<>();
        List<UserMasterDTO> userMasterDTOS = userMasterRepository.getByDesignationIdAndDistrictIdAndActive(designationId,districtId,talukId,true);
        if(userMasterDTOS.size()<=0){
            response.put("error","Error");
            response.put("error_description","No records found");
        }else {
            log.info("Entity is ", userMasterDTOS);
            response = convertDTOToMapResponse(userMasterDTOS);
        }
        return response;
    }


    public Map<String, Object> getByDesignationIdAndDistrictIdAndTalukIdAndOptionalParams(
            Long designationId, Long districtId, Long talukId, String mobileNumber, String username) {
        Map<String, Object> response = new HashMap<>();
        List<UserMasterDTO> userMasterDTOS = userMasterRepository
                .getByDesignationIdDistrictIdTalukIdMobileNumberAndUsername(designationId, districtId, talukId, mobileNumber, username, true);

        if (userMasterDTOS.isEmpty()) {
            response.put("error", "Error");
            response.put("error_description", "No records found");
        } else {
            log.info("Entity is {}", userMasterDTOS);
            response = convertDTOToMapResponse(userMasterDTOS);
        }
        return response;
    }


    //    @Transactional(isolation = Isolation.READ_COMMITTED)
    public  Map<String, Object> getByDesignationIdAndDistrictIdAndTalukIdAndWorkingInstitutionId(Long designationId,Long districtId, Long talukId,Long workingInstitutionId) {
        Map<String, Object> response = new HashMap<>();
        List<UserMasterDTO> userMasterDTOS = userMasterRepository.getByDesignationIdAndDistrictIdAndWorkingInstitutionIdAndActive(designationId,districtId,talukId,workingInstitutionId,true);
        if(userMasterDTOS.size()<=0){
            response.put("error","Error");
            response.put("error_description","No records found");
        }else {
            log.info("Entity is ", userMasterDTOS);
            response = convertDTOToMapResponse(userMasterDTOS);
        }
        return response;
    }


    private Map<String, Object> convertDTOToMapResponse(List<UserMasterDTO> userMasterDTOS) {
        Map<String, Object> response = new HashMap<>();
        List<UserMasterResponse> userMasterResponses = userMasterDTOS.stream()
                .map(userMasterDTO -> mapper.userMasterDTOToObject(userMasterDTO,UserMasterResponse.class)).collect(Collectors.toList());
        response.put("userMaster",userMasterResponses);
        response.put("totalItems", userMasterDTOS.size());
        return response;
    }
    public Map<String,Object> getUserByTscMasterId(Long tscMasterId){
        List<UserMaster> userMasterList = userMasterRepository.findByTscMasterIdAndActive(tscMasterId,true);
        if(userMasterList.isEmpty()){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",userMasterList);
        return convertListToMapResponse(userMasterList);
    }

    private Map<String, Object> convertListToMapResponse(List<UserMaster> userMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<UserMasterResponse> userMasterResponses = userMasterList.stream()
                .map(userMaster -> mapper.userMasterEntityToObject(userMaster,UserMasterResponse.class)).collect(Collectors.toList());
        response.put("userMaster",userMasterResponses);
        response.put("totalItems", userMasterList.size());
        return response;
    }



    @Transactional
    public UserMasterResponse insertUserMasterDetails(UserMasterRequest userMasterRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        userMasterRequest.setPassword(encoder.encode(userMasterRequest.getPassword()));
        UserMaster userMaster = mapper.userMasterObjectToEntity(userMasterRequest,UserMaster.class);
        validator.validate(userMaster);
        UserMaster userMasterList = userMasterRepository.findByUsername(userMasterRequest.getUsername());
        if (userMasterList != null && userMasterList .getActive()) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Username already exists");
        } else if (userMasterList != null && !userMasterList.getActive()) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Username already exists with inactive state");
        } else {
            userMasterResponse = mapper.userMasterEntityToObject(userMasterRepository.save(userMaster), UserMasterResponse.class);
            userMasterResponse.setError(false);
        }


        return userMasterResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedUserMasterDetails(final Pageable pageable){
        return convertToMapResponse(userMasterRepository.findByActiveOrderByUserMasterIdAsc( true, pageable));
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
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

//    @Transactional(isolation = Isolation.READ_COMMITTED)
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

    private Map<String, Object> convertDTOListEntityToMapResponse(final List<UserMasterDTO> activeUserMasters) {
        Map<String, Object> response = new HashMap<>();

        List<UserMasterResponse> userMasterResponses = activeUserMasters.stream()
                .map(userMaster -> mapper.userMasterDTOToObject(userMaster,UserMasterResponse.class)).collect(Collectors.toList());
        response.put("userMaster",userMasterResponses);
        return response;
    }

//    @Transactional
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

//    @Transactional
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
            userMaster.setPassword(encoder.encode(userMasterRequest.getPassword()));
            userMaster.setEmailID(userMasterRequest.getEmailID());
            userMaster.setStateId(userMasterRequest.getStateId());
            userMaster.setDistrictId(userMasterRequest.getDistrictId());
            userMaster.setTalukId(userMasterRequest.getTalukId());
            userMaster.setRoleId(userMasterRequest.getRoleId());
            userMaster.setMarketMasterId(userMasterRequest.getMarketMasterId());
            userMaster.setPhoneNumber(userMasterRequest.getPhoneNumber());
            userMaster.setDesignationId(userMasterRequest.getDesignationId());
            userMaster.setUserType(userMasterRequest.getUserType());
            userMaster.setUserTypeId(userMasterRequest.getUserTypeId());
            userMaster.setDeviceId(userMasterRequest.getDeviceId());
            userMaster.setDdoCode(userMasterRequest.getDdoCode());
            userMaster.setKhazaneRecipientId(userMasterRequest.getKhazaneRecipientId());
            userMaster.setWorkingInstitutionId(userMasterRequest.getWorkingInstitutionId());
            userMaster.setTscMasterId(userMasterRequest.getTscMasterId());
            userMaster.setDivisionMasterId(userMasterRequest.getDivisionMasterId());
            userMaster.setAllowAnyUser(userMasterRequest.getAllowAnyUser());

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
            govtSmsServiceDTO.setUsername(otpUserName);
            govtSmsServiceDTO.setPassword(otpPassword);
            govtSmsServiceDTO.setMessage("Generate and store otp");
            govtSmsServiceDTO.setSenderId(otpSenderId);
            govtSmsServiceDTO.setMobileNumber(userMaster.getPhoneNumber());
            govtSmsServiceDTO.setSecureKey(otpSecureKey);
            govtSmsServiceDTO.setTemplateid(otpTemplateId);
            log.info("Otp username:"+otpUserName+"_OtpPassword:"+otpPassword+"_OtpSenderId:"+otpSenderId+"_OtpSecureKey:"+otpSecureKey+"_OtpTemplateId:"+otpTemplateId);
            govtSmsServiceDTO.setUserId(userMaster.getUserMasterId().toString());

            govtSMSServiceController.sendOtpSMS(govtSmsServiceDTO);
            userMasterResponse =  mapper.userMasterEntityToObject(userMaster,UserMasterResponse.class);
            userMasterResponse.setError(false);
        }
        log.info("Entity is ",userMaster);
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse generateOtpByUserNameAndPassword(UserMasterDTO userMasterDTO){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUsernameAndActiveCaseSensitive(userMasterDTO.getUsername(), true);

        if(userMaster == null) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Please check username");
        }else if(!encoder.matches(userMasterDTO.getPassword(),userMaster.getPassword())) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Please check password");
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
    public UserMasterResponse loginWithoutOtp(UserMasterDTO userMasterDTO){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUsernameAndActive(userMasterDTO.getUsername(), true);

        if(userMaster == null) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Please check username");
        }else if(!encoder.matches(userMasterDTO.getPassword(),userMaster.getPassword())) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Please check password");
        }else{
            userMasterResponse =  mapper.userMasterEntityToObject(userMaster,UserMasterResponse.class);
            userMasterResponse.setOtpVerified(true);
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

    @Transactional
    public UserMasterResponse saveReelerUser(SaveReelerUserRequest saveReelerUserRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        Reeler reeler = reelerRepository.findByReelerIdAndActive(saveReelerUserRequest.getReelerId(),  true);
        if (reeler == null) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Error occurred while fetching reeler");
        }else {
            UserMaster userMaster = userMasterRepository.findByUsername(saveReelerUserRequest.getUsername());
            if (userMaster == null) {
                ReelerTypeMaster reelerTypeMaster = reelerTypeMasterRepository.findByReelerTypeMasterIdAndActive(reeler.getReelerTypeMasterId(), true);
                if(reelerTypeMaster == null){
                    userMasterResponse.setError(true);
                    userMasterResponse.setError_description("ReelerType not found");
                }else{
                    userMasterResponse.setMaxReelerUsers(reelerTypeMaster.getNoOfDeviceAllowed());
                    List<UserMaster> currentReelerUsers = userMasterRepository.findByActiveAndUserTypeIdAndMarketMasterId(true, saveReelerUserRequest.getReelerId(),saveReelerUserRequest.getMarketMasterId());
                    userMasterResponse.setCurrentReelerUsers(currentReelerUsers.size());
                    if(currentReelerUsers.size()<reelerTypeMaster.getNoOfDeviceAllowed()) {
                        UserMaster userMaster1 = new UserMaster();
                        userMaster1.setUsername(saveReelerUserRequest.getUsername());
                        userMaster1.setPassword(encoder.encode(saveReelerUserRequest.getPassword()));
                        userMaster1.setPhoneNumber(saveReelerUserRequest.getPhoneNumber());
                        userMaster1.setEmailID(saveReelerUserRequest.getEmailID());
                        userMaster1.setRoleId(0L);
                        userMaster1.setMarketMasterId(saveReelerUserRequest.getMarketMasterId());
                        userMaster1.setDesignationId(saveReelerUserRequest.getDesignationId());
                        userMaster1.setDeviceId(saveReelerUserRequest.getDeviceId());
                        userMaster1.setUserType(2); //For reeler
                        userMaster1.setUserTypeId(reeler.getReelerId());
                        userMaster1.setFirstName(reeler.getReelerName());
                        userMaster1.setStateId(reeler.getStateId());
                        userMaster1.setDistrictId(reeler.getDistrictId());
                        userMaster1.setTalukId(reeler.getTalukId());
                        userMaster1.setActive(true);

                        //Save reeler user
                        UserMaster userMaster2 = userMasterRepository.save(userMaster1);
                        userMasterResponse = mapper.userMasterEntityToObject(userMaster2, UserMasterResponse.class);

                        //Activate reeler
                        reeler.setIsActivated(1); //activated
                        if (saveReelerUserRequest.getWalletAMount() == null) {
                            reeler.setWalletAmount(0.0);
                        } else {
                            if (saveReelerUserRequest.getWalletAMount() > 0.0) {
                                reeler.setWalletAmount(saveReelerUserRequest.getWalletAMount());
                            } else {
                                reeler.setWalletAmount(0.0);
                            }
                        }
                        reeler.setActive(true);
                        reelerRepository.save(reeler);

                        userMasterResponse.setError(false);
                    }else{
                        userMasterResponse.setError(true);
                        userMasterResponse.setError_description("Max number of users already configured");
                    }
                }
            } else {
                userMasterResponse.setError(true);
                userMasterResponse.setError_description("Username already exist");
            }
        }

        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse saveTraderUser(SaveReelerUserRequest saveReelerUserRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        TraderLicense traderLicense = traderLicenseRepository.findByTraderLicenseIdAndActive(saveReelerUserRequest.getTraderLicenseId(),  true);
        if (traderLicense == null) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Error occurred while fetching Trader");
        }else {
            UserMaster userMaster = userMasterRepository.findByUsername(saveReelerUserRequest.getUsername());
            if (userMaster == null) {
                TraderTypeMaster traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterIdAndActive(traderLicense.getTraderTypeMasterId(), true);
                if(traderTypeMaster == null){
                    userMasterResponse.setError(true);
                    userMasterResponse.setError_description("Trader Type not found");
                }else{
                    userMasterResponse.setMaxTraderUsers(traderTypeMaster.getNoOfDeviceAllowed());
                    List<UserMaster> currentTraderUsers = userMasterRepository.findByActiveAndUserTypeIdAndMarketMasterId(true, saveReelerUserRequest.getTraderLicenseId(),saveReelerUserRequest.getMarketMasterId());
                    userMasterResponse.setCurrentReelerUsers(currentTraderUsers.size());
                    if(currentTraderUsers.size()<traderTypeMaster.getNoOfDeviceAllowed()) {
                        UserMaster userMaster1 = new UserMaster();
                        userMaster1.setUsername(saveReelerUserRequest.getUsername());
                        userMaster1.setPassword(encoder.encode(saveReelerUserRequest.getPassword()));
                        userMaster1.setPhoneNumber(saveReelerUserRequest.getPhoneNumber());
                        userMaster1.setEmailID(saveReelerUserRequest.getEmailID());
                        userMaster1.setRoleId(0L);
                        userMaster1.setMarketMasterId(saveReelerUserRequest.getMarketMasterId());
                        userMaster1.setDesignationId(saveReelerUserRequest.getDesignationId());
                        userMaster1.setDeviceId(saveReelerUserRequest.getDeviceId());
                        userMaster1.setUserType(3); //For Trader
                        userMaster1.setUserTypeId(traderLicense.getTraderLicenseId());
                        userMaster1.setFirstName(traderLicense.getFirstName());
                        userMaster1.setStateId(traderLicense.getStateId());
                        userMaster1.setDistrictId(traderLicense.getDistrictId());
                        userMaster1.setActive(true);

                        //Save reeler user
                        UserMaster userMaster2 = userMasterRepository.save(userMaster1);
                        userMasterResponse = mapper.userMasterEntityToObject(userMaster2, UserMasterResponse.class);

                        //Activate reeler
                        traderLicense.setIsActivated(1); //activated
                        if (saveReelerUserRequest.getWalletAMount() == null) {
                            traderLicense.setWalletAmount(0.0);
                        } else {
                            if (saveReelerUserRequest.getWalletAMount() > 0.0) {
                                traderLicense.setWalletAmount(saveReelerUserRequest.getWalletAMount());
                            } else {
                                traderLicense.setWalletAmount(0.0);
                            }
                        }
                        traderLicense.setActive(true);
                        traderLicenseRepository.save(traderLicense);

                        userMasterResponse.setError(false);
                    }else{
                        userMasterResponse.setError(true);
                        userMasterResponse.setError_description("Max number of users already configured");
                    }
                }
            } else {
                userMasterResponse.setError(true);
                userMasterResponse.setError_description("Username already exist");
            }
        }

        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse saveForReelerUser(SaveReelerUserRequest saveReelerUserRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        for(int i=0; i<1000; i++) {
            saveReelerUserRequest.setReelerId(Long.valueOf(4132 + i));
            UUID uuid = UUID.randomUUID();
            saveReelerUserRequest.setUsername("dummy_username_"+uuid);
            saveReelerUserRequest.setPhoneNumber(String.valueOf(uuid));
            Reeler reeler = reelerRepository.findByReelerIdAndActive(saveReelerUserRequest.getReelerId(), true);
            if (reeler == null) {
                userMasterResponse.setError(true);
                userMasterResponse.setError_description("Error occurred while fetching reeler");
            } else {
                UserMaster userMaster = userMasterRepository.findByUsername(saveReelerUserRequest.getUsername());
                if (userMaster == null) {
                    ReelerTypeMaster reelerTypeMaster = reelerTypeMasterRepository.findByReelerTypeMasterIdAndActive(reeler.getReelerTypeMasterId(), true);
                    if (reelerTypeMaster == null) {
                        userMasterResponse.setError(true);
                        userMasterResponse.setError_description("ReelerType not found");
                    } else {
                        userMasterResponse.setMaxReelerUsers(reelerTypeMaster.getNoOfDeviceAllowed());
                        List<UserMaster> currentReelerUsers = userMasterRepository.findByActiveAndUserTypeIdAndMarketMasterId(true, saveReelerUserRequest.getReelerId(),saveReelerUserRequest.getMarketMasterId());
                        userMasterResponse.setCurrentReelerUsers(currentReelerUsers.size());
                        if (currentReelerUsers.size() < reelerTypeMaster.getNoOfDeviceAllowed()) {
                            UserMaster userMaster1 = new UserMaster();
                            userMaster1.setUsername(saveReelerUserRequest.getUsername());
                            userMaster1.setPassword(encoder.encode(saveReelerUserRequest.getPassword()));
                            userMaster1.setPhoneNumber(saveReelerUserRequest.getPhoneNumber());
                            userMaster1.setEmailID(saveReelerUserRequest.getEmailID());
                            userMaster1.setRoleId(0L);
                            userMaster1.setMarketMasterId(saveReelerUserRequest.getMarketMasterId());
                            userMaster1.setDesignationId(saveReelerUserRequest.getDesignationId());
                            userMaster1.setDeviceId(saveReelerUserRequest.getDeviceId());
                            userMaster1.setUserType(2); //For reeler
                            userMaster1.setUserTypeId(reeler.getReelerId());
                            userMaster1.setFirstName(reeler.getReelerName());
                            userMaster1.setStateId(reeler.getStateId());
                            userMaster1.setDistrictId(reeler.getDistrictId());
                            userMaster1.setTalukId(reeler.getTalukId());
                            userMaster1.setActive(true);

                            //Save reeler user
                            UserMaster userMaster2 = userMasterRepository.save(userMaster1);
                            userMasterResponse = mapper.userMasterEntityToObject(userMaster2, UserMasterResponse.class);

                            //Activate reeler
                            reeler.setIsActivated(1); //activated
                            if (saveReelerUserRequest.getWalletAMount() == null) {
                                reeler.setWalletAmount(0.0);
                            } else {
                                if (saveReelerUserRequest.getWalletAMount() > 0.0) {
                                    reeler.setWalletAmount(saveReelerUserRequest.getWalletAMount());
                                } else {
                                    reeler.setWalletAmount(0.0);
                                }
                            }
                            reeler.setActive(true);
                            reelerRepository.save(reeler);

                            userMasterResponse.setError(false);
                        } else {
                            userMasterResponse.setError(true);
                            userMasterResponse.setError_description("Max number of users already configured");
                        }
                    }
                } else {
                    userMasterResponse.setError(true);
                    userMasterResponse.setError_description("Username already exist");
                }
            }

        }
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse editReelerUser(EditReelerUserRequest saveReelerUserRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUserMasterIdAndActive(saveReelerUserRequest.getUserTypeId(), true);
        if (userMaster != null) {
            userMaster.setUsername(saveReelerUserRequest.getUsername());
            userMaster.setPassword(encoder.encode(saveReelerUserRequest.getPassword()));
            userMaster.setPhoneNumber(saveReelerUserRequest.getPhoneNumber());
            userMaster.setEmailID(saveReelerUserRequest.getEmailID());
            userMaster.setRoleId(0L);
            userMaster.setMarketMasterId(saveReelerUserRequest.getMarketMasterId());
            userMaster.setDesignationId(saveReelerUserRequest.getDesignationId());
            userMaster.setDeviceId(saveReelerUserRequest.getDeviceId());
            userMaster.setUserType(2); //For reeler
            userMaster.setActive(true);

            //Edit reeler user
            UserMaster userMaster2 = userMasterRepository.save(userMaster);
            userMasterResponse = mapper.userMasterEntityToObject(userMaster2, UserMasterResponse.class);
            userMasterResponse.setError(false);

        } else {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("User not found");
        }
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse editTraderUser(EditReelerUserRequest saveReelerUserRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUserMasterIdAndActive(saveReelerUserRequest.getUserTypeId(), true);
        if (userMaster != null) {
            userMaster.setUsername(saveReelerUserRequest.getUsername());
            userMaster.setPassword(encoder.encode(saveReelerUserRequest.getPassword()));
            userMaster.setPhoneNumber(saveReelerUserRequest.getPhoneNumber());
            userMaster.setEmailID(saveReelerUserRequest.getEmailID());
            userMaster.setRoleId(0L);
            userMaster.setMarketMasterId(saveReelerUserRequest.getMarketMasterId());
            userMaster.setDesignationId(saveReelerUserRequest.getDesignationId());
            userMaster.setDeviceId(saveReelerUserRequest.getDeviceId());
            userMaster.setUserType(3); //For reeler
            userMaster.setActive(true);

            //Edit reeler user
            UserMaster userMaster2 = userMasterRepository.save(userMaster);
            userMasterResponse = mapper.userMasterEntityToObject(userMaster2, UserMasterResponse.class);
            userMasterResponse.setError(false);

        } else {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("User not found");
        }
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse saveExternalUnitRegistration(SaveReelerUserRequest saveReelerUserRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        ExternalUnitRegistration externalUnitRegistration = externalUnitRegistrationRepository.findByExternalUnitRegistrationIdAndActive(saveReelerUserRequest.getExternalUnitRegistrationId(),  true);
        if (externalUnitRegistration == null) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Error occurred while fetching external unit registration");
        }else {
            UserMaster userMaster = userMasterRepository.findByUsername(saveReelerUserRequest.getUsername());
            if (userMaster == null) {
                UserMaster userMaster1 = new UserMaster();
                userMaster1.setUsername(saveReelerUserRequest.getUsername());
                userMaster1.setPassword(encoder.encode(saveReelerUserRequest.getPassword()));
                userMaster1.setPhoneNumber(saveReelerUserRequest.getPhoneNumber());
                userMaster1.setMarketMasterId(0L);
                userMaster1.setUserType(3); // For external unit
                userMaster1.setUserTypeId(saveReelerUserRequest.getExternalUnitRegistrationId());
                userMaster1.setActive(true);

                //Save external unit user
                UserMaster userMaster2 = userMasterRepository.save(userMaster1);
                userMasterResponse = mapper.userMasterEntityToObject(userMaster2, UserMasterResponse.class);

                userMasterResponse.setError(false);
            } else {
                userMasterResponse.setError(true);
                userMasterResponse.setError_description("Username already exist");
            }
        }
        return userMasterResponse;
    }

    @Transactional
    public UserMasterResponse changePassword(UserMasterChangePasswordRequest userMasterChangePasswordRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        UserMaster userMaster = userMasterRepository.findByUserMasterIdAndActive(userMasterChangePasswordRequest.getUserMasterId(),true);
        if(userMaster == null) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Invalid id");
        }else if(!encoder.matches(userMasterChangePasswordRequest.getCurrentPassword(),userMaster.getPassword())) {
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Current password is incorrect");
        }else{
            userMasterResponse.setError(false);
            userMaster.setPassword(encoder.encode(userMasterChangePasswordRequest.getNewPassword()));
            userMasterResponse = mapper.userMasterEntityToObject(userMasterRepository.save(userMaster), UserMasterResponse.class);
        }
        log.info("Entity is ",userMaster);
        return userMasterResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("username");
        }
        if(searchWithSortRequest.getSortOrder() == null || searchWithSortRequest.getSortOrder().equals("")){
            searchWithSortRequest.setSortOrder("asc");
        }
        if(searchWithSortRequest.getPageNumber() == null || searchWithSortRequest.getPageNumber().equals("")){
            searchWithSortRequest.setPageNumber("0");
        }
        if(searchWithSortRequest.getPageSize() == null || searchWithSortRequest.getPageSize().equals("")){
            searchWithSortRequest.setPageSize("5");
        }
        Sort sort;
        if(searchWithSortRequest.getSortOrder().equals("asc")){
            sort = Sort.by(Sort.Direction.ASC, searchWithSortRequest.getSortColumn());
        }else{
            sort = Sort.by(Sort.Direction.DESC, searchWithSortRequest.getSortColumn());
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(searchWithSortRequest.getPageNumber()), Integer.parseInt(searchWithSortRequest.getPageSize()), sort);
        Page<UserMasterDTO> userMasterDTOS = userMasterRepository.getSortedUsers(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",userMasterDTOS);
        return convertPageableDTOToMapResponse(userMasterDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<UserMasterDTO> activeUsers) {
        Map<String, Object> response = new HashMap<>();

        List<UserMasterResponse> userMasterResponses = activeUsers.getContent().stream()
                .map(userMaster -> mapper.userMasterDTOToObject(userMaster,UserMasterResponse.class)).collect(Collectors.toList());
        response.put("userMaster",userMasterResponses);
        response.put("currentPage", activeUsers.getNumber());
        response.put("totalItems", activeUsers.getTotalElements());
        response.put("totalPages", activeUsers.getTotalPages());

        return response;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllReelerUsers(boolean isActive, long reelerId, long marketMasterId){
        return convertListEntityToMapResponse(userMasterRepository.findByActiveAndUserTypeIdAndMarketMasterId(isActive, reelerId,marketMasterId));
    }

    public Map<String,Object> getAllTraderUsers(boolean isActive, long traderLicenseId, long marketMasterId){
        return convertListEntityToMapResponse(userMasterRepository.findByActiveAndUserTypeIdAndMarketMasterId(isActive, traderLicenseId, marketMasterId));
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getEscalateRoleUsers(String roleName){
        return convertDTOListEntityToMapResponse(userMasterRepository.getByActiveAndRoleName(true, roleName));
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserMasterResponse getConfigureUserDetailsForReeler(boolean isActive, long reelerId, long marketMasterId){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        Reeler reeler = reelerRepository.findByReelerIdAndActive(reelerId, true);
        if(reeler == null){
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Reeler not found");
        }else{
            ReelerTypeMaster reelerTypeMaster = reelerTypeMasterRepository.findByReelerTypeMasterIdAndActive(reeler.getReelerTypeMasterId(), true);
            if(reelerTypeMaster == null){
                userMasterResponse.setError(true);
                userMasterResponse.setError_description("ReelerType not found");
            }else{
                userMasterResponse.setMaxReelerUsers(reelerTypeMaster.getNoOfDeviceAllowed());
                List<UserMaster> currentReelerUsers = userMasterRepository.findByActiveAndUserTypeIdAndMarketMasterId(isActive, reelerId, marketMasterId);
                userMasterResponse.setCurrentReelerUsers(currentReelerUsers.size());
                userMasterResponse.setError(false);
            }
        }
        return userMasterResponse;
    }

    public UserMasterResponse getConfigureUserDetailsForTrader(boolean isActive, long traderLicenseId, long marketMasterId){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
        TraderLicense traderLicense = traderLicenseRepository.findByTraderLicenseIdAndActive(traderLicenseId, true);
        if(traderLicense == null){
            userMasterResponse.setError(true);
            userMasterResponse.setError_description("Trader not found");
        }else{
            TraderTypeMaster traderTypeMaster = traderTypeMasterRepository.findByTraderTypeMasterIdAndActive(traderLicense.getTraderTypeMasterId(), true);
            if(traderTypeMaster == null){
                userMasterResponse.setError(true);
                userMasterResponse.setError_description("Trader Type not found");
            }else{
                userMasterResponse.setMaxTraderUsers(traderTypeMaster.getNoOfDeviceAllowed());
                List<UserMaster> currentReelerUsers = userMasterRepository.findByActiveAndUserTypeIdAndMarketMasterId(isActive, traderLicenseId,marketMasterId);
                userMasterResponse.setCurrentReelerUsers(currentReelerUsers.size());
                userMasterResponse.setError(false);
            }
        }
        return userMasterResponse;
    }

    public List<UserMasterResponse> getUserManagerDetails() {
        List<Object[]> userDetails = userMasterRepository.getUserManagerDetails();
        List<UserMasterResponse> responses = new ArrayList<>();

        for (Object[] arr : userDetails) {
            UserMasterResponse response = UserMasterResponse.builder()
                    .userMasterId(Util.objectToInteger(arr[0]))
                    .firstName(Util.objectToString(arr[1]))
                    .lastName(Util.objectToString(arr[2]))
                    .username(Util.objectToString(arr[3]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<UserMasterResponse> getDirectReporteeDetails() {
        List<Object[]> userDetails = userMasterRepository.getDirectReporteeDetails(Util.getUserMasterId(Util.getTokenValues()));
        List<UserMasterResponse> responses = new ArrayList<>();

        for (Object[] arr : userDetails) {
            UserMasterResponse response = UserMasterResponse.builder()
                    .userMasterId(Util.objectToInteger(arr[0]))
                    .managerId(Util.objectToLong(arr[1]))
                    .firstName(Util.objectToString(arr[2]))
                    .lastName(Util.objectToString(arr[3]))
                    .username(Util.objectToString(arr[4]))
                    .phoneNumber(Util.objectToString(arr[5]))
                    .districtName(Util.objectToString(arr[6]))
                    .name(Util.objectToString(arr[7]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<UserMasterResponse> getAllReporteeDetails() {
        List<Object[]> userDetails = userMasterRepository.getAllReporteeDetails(Util.getUserMasterId(Util.getTokenValues()));
        List<UserMasterResponse> responses = new ArrayList<>();

        for (Object[] arr : userDetails) {
            UserMasterResponse response = UserMasterResponse.builder()
                    .userMasterId(Util.objectToInteger(arr[0]))
                    .managerId(Util.objectToLong(arr[1]))
                    .firstName(Util.objectToString(arr[2]))
                    .lastName(Util.objectToString(arr[3]))
                    .username(Util.objectToString(arr[4]))
                    .phoneNumber(Util.objectToString(arr[5]))
                    .districtName(Util.objectToString(arr[6]))
                    .name(Util.objectToString(arr[7]))
                    .level(Util.objectToString(arr[8]))
                    .build();

            responses.add(response);
        }

        return responses;
    }


    public FileInputStream exportUserReport(boolean isHierarchy) throws Exception {
        // Retrieve data based on the type of export
//        Long managerIdFromToken = Util.getUserMasterId(Util.getTokenValues());
        List<UserMasterResponse> userDetailsList;
        if (isHierarchy) {
            userDetailsList = getAllReporteeDetails();
        } else {
            userDetailsList = getDirectReporteeDetails();
        }

        // Create a new Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User Details");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("User ID");
        headerRow.createCell(1).setCellValue("Manager ID");
        headerRow.createCell(2).setCellValue("First Name");
        headerRow.createCell(3).setCellValue("Last Name");
        headerRow.createCell(4).setCellValue("Username");
        headerRow.createCell(5).setCellValue("Phone Number");
        headerRow.createCell(6).setCellValue("District Name");
        headerRow.createCell(7).setCellValue("Designation");
        if (isHierarchy) {
            headerRow.createCell(8).setCellValue("Hierarchy Level");
        }

        // Populate data rows
        int rowNumber = 1;
        for (UserMasterResponse user : userDetailsList) {
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(user.getUserMasterId());
            row.createCell(1).setCellValue(user.getManagerId());
            row.createCell(2).setCellValue(user.getFirstName());
            row.createCell(3).setCellValue(user.getLastName());
            row.createCell(4).setCellValue(user.getUsername());
            row.createCell(5).setCellValue(user.getPhoneNumber());
            row.createCell(6).setCellValue(user.getDistrictName());
            row.createCell(7).setCellValue(user.getName());
            if (isHierarchy) {
                row.createCell(8).setCellValue(user.getLevel());
            }
        }

        // Auto-size columns
        for (int columnIndex = 0; columnIndex < headerRow.getLastCellNum(); columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }

        // Save file to the user's Downloads directory
        String userHome = System.getProperty("user.home");
        String directoryPath = Paths.get(userHome, "Downloads").toString();
        Files.createDirectories(Paths.get(directoryPath));
        Path filePath = Paths.get(directoryPath, "UserDetailsReport.xlsx");

        try (FileOutputStream fileOut = new FileOutputStream(filePath.toFile())) {
            workbook.write(fileOut);
        }
        workbook.close();

        // Return FileInputStream for the saved file
        return new FileInputStream(filePath.toFile());
    }



    @Transactional
    public UserMasterResponse updateManagerIdDetails(EditUserMasterRequest userMasterRequest){
        UserMasterResponse userMasterResponse = new UserMasterResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

        UserMaster userMaster = userMasterRepository.findByUserMasterIdAndActiveIn(userMasterRequest.getUserMasterId(), Set.of(true,false));
        if(Objects.nonNull(userMaster)){
           userMaster.setManagerId(userMasterRequest.getManagerId());
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

    public ResponseEntity<?> userMasterDetails(Long designationId, Long districtId, Long talukId,
                                               String mobileNumber, String username,
                                               int pageNumber, int pageSize) {

        ResponseWrapper rw = ResponseWrapper.createWrapper(List.class);
        List<UserMasterDetailsResponse> responseList = new ArrayList<>();

        designationId = (designationId != null && designationId == 0) ? null : designationId;
        districtId = (districtId != null && districtId == 0) ? null : districtId;
        talukId = (talukId != null && talukId == 0) ? null : talukId;
        mobileNumber = (mobileNumber != null && mobileNumber.trim().isEmpty()) ? null : mobileNumber;
        username = (username != null && username.trim().isEmpty()) ? null : username;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> applicablePage =
                userMasterRepository.getUserMasterDetails(designationId, districtId, talukId, mobileNumber, username, pageable);

        List<Object[]> applicableList = applicablePage.getContent();
        long totalRecords = applicablePage.getTotalElements();

        mapUserMasterResponse(responseList, applicableList, pageNumber, pageSize);

        rw.setTotalRecords(totalRecords);
        rw.setContent(responseList);
        return ResponseEntity.ok(rw);
    }

    private static void mapUserMasterResponse(List<UserMasterDetailsResponse> responseList,
                                              List<Object[]> applicableList,
                                              int pageNumber,
                                              int pageSize) {
        int serialNumber = pageNumber * pageSize + 1;
        for (Object[] arr : applicableList) {
            UserMasterDetailsResponse response = UserMasterDetailsResponse.builder()
                    .serialNumber(serialNumber++)
                    .firstName(Util.objectToString(arr[0]))
                    .middleName(Util.objectToString(arr[1]))
                    .lastName(Util.objectToString(arr[2]))
                    .password(Util.objectToString(arr[3]))
                    .emailId(Util.objectToString(arr[4]))
                    .tscName(Util.objectToString(arr[5]))
                    .stateName(Util.objectToString(arr[6]))
                    .districtName(Util.objectToString(arr[7]))
                    .talukName(Util.objectToString(arr[8]))
                    .roleName(Util.objectToString(arr[9]))
                    .marketName(Util.objectToString(arr[10]))
                    .username(Util.objectToString(arr[11]))
                    .designationName(Util.objectToString(arr[12]))
                    .phoneNumber(Util.objectToString(arr[13]))
                    .ddoCode(Util.objectToString(arr[14]))
                    .khazaneRecipientId(Util.objectToString(arr[15]))
                    .workingInstitutionName(Util.objectToString(arr[16]))
                    .build();
            responseList.add(response);
        }
    }

    public FileInputStream userMasterReport(Long designationId, Long districtId, Long talukId,
                                            String mobileNumber, String username,
                                            int pageNumber, int pageSize) throws Exception {

        List<UserMasterDetailsResponse> responseList = new ArrayList<>();

        designationId = (designationId != null && designationId == 0) ? null : designationId;
        districtId = (districtId != null && districtId == 0) ? null : districtId;
        talukId = (talukId != null && talukId == 0) ? null : talukId;
        mobileNumber = (mobileNumber != null && mobileNumber.trim().isEmpty()) ? null : mobileNumber;
        username = (username != null && username.trim().isEmpty()) ? null : username;

        Pageable pageable = null; // Fetch all
        Page<Object[]> applicablePage =
                userMasterRepository.getUserMasterDetails(designationId, districtId, talukId, mobileNumber, username, pageable);

        mapUserMasterResponse(responseList, applicablePage.getContent(), pageNumber, pageSize);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User Master Report");

        String[] headers = {
                "Sl.No", "First Name", "Middle Name", "Last Name", "Password", "Email ID",
                "TSC Name", "State", "District", "Taluk", "Role", "Market", "Username",
                "Designation", "Phone Number", "DDO Code", "Khazane Recipient ID", "Working Institution"
        };

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        int dataRow = 1;
        for (UserMasterDetailsResponse u : responseList) {
            Row row = sheet.createRow(dataRow++);
            row.createCell(0).setCellValue(u.getSerialNumber());
            row.createCell(1).setCellValue(u.getFirstName());
            row.createCell(2).setCellValue(u.getMiddleName());
            row.createCell(3).setCellValue(u.getLastName());
            row.createCell(4).setCellValue(u.getPassword());
            row.createCell(5).setCellValue(u.getEmailId());
            row.createCell(6).setCellValue(u.getTscName());
            row.createCell(7).setCellValue(u.getStateName());
            row.createCell(8).setCellValue(u.getDistrictName());
            row.createCell(9).setCellValue(u.getTalukName());
            row.createCell(10).setCellValue(u.getRoleName());
            row.createCell(11).setCellValue(u.getMarketName());
            row.createCell(12).setCellValue(u.getUsername());
            row.createCell(13).setCellValue(u.getDesignationName());
            row.createCell(14).setCellValue(u.getPhoneNumber());
            row.createCell(15).setCellValue(u.getDdoCode());
            row.createCell(16).setCellValue(u.getKhazaneRecipientId());
            row.createCell(17).setCellValue(u.getWorkingInstitutionName());
        }

        for (int col = 0; col < headers.length; col++) {
            sheet.autoSizeColumn(col, true);
        }

        String userHome = System.getProperty("user.home");
        String directoryPath = Paths.get(userHome, "Downloads").toString();
        Files.createDirectories(Paths.get(directoryPath));
        Path filePath = Paths.get(directoryPath, "user_master_report_" + Util.getISTLocalDate() + ".xlsx");

        FileOutputStream fileOut = new FileOutputStream(filePath.toString());
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        return new FileInputStream(filePath.toString());
    }

}