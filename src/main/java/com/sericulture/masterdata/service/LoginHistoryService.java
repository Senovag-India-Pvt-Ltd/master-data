package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.loginHistory.EditLoginHistoryRequest;
import com.sericulture.masterdata.model.api.loginHistory.LoginHistoryRequest;
import com.sericulture.masterdata.model.api.loginHistory.LoginHistoryResponse;
import com.sericulture.masterdata.model.api.rpRolePermission.EditRpRolePermissionRequest;
import com.sericulture.masterdata.model.api.rpRolePermission.RpRolePermissionRequest;
import com.sericulture.masterdata.model.api.rpRolePermission.RpRolePermissionResponse;
import com.sericulture.masterdata.model.api.scProgram.EditScProgramRequest;
import com.sericulture.masterdata.model.api.scProgram.ScProgramRequest;
import com.sericulture.masterdata.model.api.scProgram.ScProgramResponse;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.EditTrainingDeputationTrackerRequest;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.TrainingDeputationTrackerRequest;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.TrainingDeputationTrackerResponse;
import com.sericulture.masterdata.model.dto.TrainingDeputationTrackerDTO;
import com.sericulture.masterdata.model.entity.LoginHistory;
import com.sericulture.masterdata.model.entity.RpRolePermission;
import com.sericulture.masterdata.model.entity.ScProgram;
import com.sericulture.masterdata.model.entity.TrainingDeputationTracker;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.LoginHistoryRepository;
import com.sericulture.masterdata.repository.RpRolePermissionRepository;
import com.sericulture.masterdata.repository.TrainingDeputationTrackerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Service
@Slf4j
public class LoginHistoryService {
    @Autowired
    LoginHistoryRepository loginHistoryRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public LoginHistoryResponse insertLoginHistoryDetails(LoginHistoryRequest loginHistoryRequest){
        LoginHistoryResponse loginHistoryResponse = new LoginHistoryResponse();
        LoginHistory loginHistory = mapper.loginHistoryObjectToEntity(loginHistoryRequest,LoginHistory.class);
        validator.validate(loginHistory);
//        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterNameAndMarketNameInKannada(marketMasterRequest.getMarketMasterName(), marketMasterRequest.getMarketNameInKannada());
//        if(!marketMasterList.isEmpty() && marketMasterList.stream().filter(MarketMaster::getActive).findAny().isPresent()){
//            marketMasterResponse.setError(true);
//            marketMasterResponse.setError_description("Market name already exist");
//        }
//        else if(!marketMasterList.isEmpty() && marketMasterList.stream().filter(Predicate.not(MarketMaster::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            marketMasterResponse.setError(true);
//            marketMasterResponse.setError_description("Market name already exist with inactive state");
//        }else {
//            marketMasterResponse = mapper.marketMasterEntityToObject(marketMasterRepository.save(marketMaster), MarketMasterResponse.class);
//            marketMasterResponse.setError(false);
//        }
        return mapper.loginHistoryEntityToObject(loginHistoryRepository.save(loginHistory),LoginHistoryResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedLoginHistoryDetails(final Pageable pageable){
        return convertToMapResponse(loginHistoryRepository.findByActiveOrderByLoginHistoryIdAsc( true, pageable));
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(loginHistoryRepository.findByActiveOrderByLoginHistoryIdAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<LoginHistory> activeLoginHistorys) {
        Map<String, Object> response = new HashMap<>();

        List<LoginHistoryResponse> loginHistoryResponses = activeLoginHistorys.getContent().stream()
                .map(loginHistory -> mapper.loginHistoryEntityToObject(loginHistory,LoginHistoryResponse.class)).collect(Collectors.toList());
        response.put("loginHistory",loginHistoryResponses);
        response.put("currentPage", activeLoginHistorys.getNumber());
        response.put("totalItems", activeLoginHistorys.getTotalElements());
        response.put("totalPages", activeLoginHistorys.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<LoginHistory> activeLoginHistorys) {
        Map<String, Object> response = new HashMap<>();

        List<LoginHistoryResponse> loginHistoryResponses = activeLoginHistorys.stream()
                .map(loginHistory -> mapper.loginHistoryEntityToObject(loginHistory,LoginHistoryResponse.class)).collect(Collectors.toList());
        response.put("loginHistory",loginHistoryResponses);
        return response;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getPaginatedLoginHistoryDetailsWithJoin(final Pageable pageable){
//        return convertDTOToMapResponse(loginHistoryRepository.g( true, pageable));
//    }
//
//    private Map<String, Object> convertDTOToMapResponse(final Page<TrainingDeputationTrackerDTO> activeTrainingDeputationTrackers) {
//        Map<String, Object> response = new HashMap<>();
//
//        List<TrainingDeputationTrackerResponse> trainingDeputationTrackerResponses = activeTrainingDeputationTrackers.getContent().stream()
//                .map(trainingDeputationTracker -> mapper.trainingDeputationTrackerDTOToObject(trainingDeputationTracker,TrainingDeputationTrackerResponse.class)).collect(Collectors.toList());
//        response.put("trainingDeputationTracker",trainingDeputationTrackerResponses);
//        response.put("currentPage", activeTrainingDeputationTrackers.getNumber());
//        response.put("totalItems", activeTrainingDeputationTrackers.getTotalElements());
//        response.put("totalPages", activeTrainingDeputationTrackers.getTotalPages());
//        return response;
//    }

    @Transactional
    public LoginHistoryResponse deleteLoginHistoryDetails(long id) {
        LoginHistoryResponse loginHistoryResponse = new LoginHistoryResponse();
        LoginHistory loginHistory = loginHistoryRepository.findByLoginHistoryIdAndActive(id, true);
        if (Objects.nonNull(loginHistory)) {
            loginHistory.setActive(false);
            loginHistoryResponse = mapper.loginHistoryEntityToObject(loginHistoryRepository.save(loginHistory), LoginHistoryResponse.class);
            loginHistoryResponse.setError(false);
        } else {
            loginHistoryResponse.setError(true);
            loginHistoryResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return loginHistoryResponse;
    }

    @Transactional
    public LoginHistoryResponse getById(int id){
        LoginHistoryResponse loginHistoryResponse = new LoginHistoryResponse();
        LoginHistory loginHistory = loginHistoryRepository.findByLoginHistoryIdAndActive(id,true);
        if(loginHistory == null){
            loginHistoryResponse.setError(true);
            loginHistoryResponse.setError_description("Invalid id");
        }else{
            loginHistoryResponse =  mapper.loginHistoryEntityToObject(loginHistory,LoginHistoryResponse.class);
            loginHistoryResponse.setError(false);
        }
        log.info("Entity is ",loginHistory);
        return loginHistoryResponse;
    }

//    @Transactional
//    public LoginHistoryResponse getByIdJoin(int id) {
//        LoginHistoryResponse loginHistoryResponse = new LoginHistoryResponse();
//        TrainingDeputationTrackerDTO trainingDeputationTrackerDTO = trainingDeputationTrackerRepository.getByTrainingDeputationIdAndActive(id, true);
//        if (trainingDeputationTrackerDTO == null) {
//            // throw new ValidationException("Invalid Id");
//            trainingDeputationTrackerResponse.setError(true);
//            trainingDeputationTrackerResponse.setError_description("Invalid id");
//        } else {
//            trainingDeputationTrackerResponse = mapper.trainingDeputationTrackerDTOToObject(trainingDeputationTrackerDTO, TrainingDeputationTrackerResponse.class);
//            trainingDeputationTrackerResponse.setError(false);
//        }
//        log.info("Entity is ", trainingDeputationTrackerDTO);
//        return trainingDeputationTrackerResponse;
//    }

    @Transactional
    public LoginHistoryResponse updateLoginHistoryDetails(EditLoginHistoryRequest loginHistoryRequest) {
        LoginHistoryResponse loginHistoryResponse = new LoginHistoryResponse();
//        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterName(marketMasterRequest.getMarketMasterName());
//        if (marketMasterList.size() > 0) {
//            marketMasterResponse.setError(true);
//            marketMasterResponse.setError_description("Market already exists, duplicates are not allowed.");
//             throw new ValidationException("Village already exists, duplicates are not allowed.");
//        } else {

        LoginHistory loginHistory = loginHistoryRepository.findByLoginHistoryIdAndActiveIn(loginHistoryRequest.getLoginHistoryId(), Set.of(true, false));
        if (Objects.nonNull(loginHistory)) {
            loginHistory.setUserMasterId(loginHistoryRequest.getUserMasterId());
            loginHistory.setUsername(loginHistoryRequest.getUsername());
            loginHistory.setLoginTime(loginHistoryRequest.getLoginTime());
            loginHistory.setLogoutTime(loginHistoryRequest.getLogoutTime());
            loginHistory.setLoginStatus(loginHistoryRequest.getLoginStatus());
            loginHistory.setIpAddress(loginHistoryRequest.getIpAddress());
            loginHistory.setDeviceInfo(loginHistoryRequest.getDeviceInfo());
            loginHistory.setErrorDescription(loginHistoryRequest.getErrorDescription());
            loginHistory.setActive(true);
            LoginHistory loginHistory1 = loginHistoryRepository.save(loginHistory);
            loginHistoryResponse = mapper.loginHistoryEntityToObject(loginHistory1, LoginHistoryResponse.class);
            loginHistoryResponse.setError(false);
        } else {
            loginHistoryResponse.setError(true);
            loginHistoryResponse.setError_description("Error occurred while fetching loginHistory");
            // throw new ValidationException("Error occurred while fetching village");
        }
        return loginHistoryResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
//        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
//            searchWithSortRequest.setSearchText("%%");
//        }else{
//            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
//        }
//        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
//            searchWithSortRequest.setSortColumn("officialName");
//        }
//        if(searchWithSortRequest.getSortOrder() == null || searchWithSortRequest.getSortOrder().equals("")){
//            searchWithSortRequest.setSortOrder("asc");
//        }
//        if(searchWithSortRequest.getPageNumber() == null || searchWithSortRequest.getPageNumber().equals("")){
//            searchWithSortRequest.setPageNumber("0");
//        }
//        if(searchWithSortRequest.getPageSize() == null || searchWithSortRequest.getPageSize().equals("")){
//            searchWithSortRequest.setPageSize("5");
//        }
//        Sort sort;
//        if(searchWithSortRequest.getSortOrder().equals("asc")){
//            sort = Sort.by(Sort.Direction.ASC, searchWithSortRequest.getSortColumn());
//        }else{
//            sort = Sort.by(Sort.Direction.DESC, searchWithSortRequest.getSortColumn());
//        }
//        Pageable pageable = PageRequest.of(Integer.parseInt(searchWithSortRequest.getPageNumber()), Integer.parseInt(searchWithSortRequest.getPageSize()), sort);
//        Page<TrainingDeputationTrackerDTO> trainingDeputationTrackerDTOS = trainingDeputationTrackerRepository.getSortedTrainingDeputations(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
//        log.info("Entity is ",trainingDeputationTrackerDTOS);
//        return convertPageableDTOToMapResponse(trainingDeputationTrackerDTOS);
//    }
//
//    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TrainingDeputationTrackerDTO> activeTrainingDeputationTrackers) {
//        Map<String, Object> response = new HashMap<>();
//
//        List<TrainingDeputationTrackerResponse> trainingDeputationTrackerResponses = activeTrainingDeputationTrackers.getContent().stream()
//                .map(trainingDeputationTracker -> mapper.trainingDeputationTrackerDTOToObject(trainingDeputationTracker,TrainingDeputationTrackerResponse.class)).collect(Collectors.toList());
//        response.put("trainingDeputationTracker",trainingDeputationTrackerResponses);
//        response.put("currentPage", activeTrainingDeputationTrackers.getNumber());
//        response.put("totalItems", activeTrainingDeputationTrackers.getTotalElements());
//        response.put("totalPages", activeTrainingDeputationTrackers.getTotalPages());
//
//        return response;
//    }
}
