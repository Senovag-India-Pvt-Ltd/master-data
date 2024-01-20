package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.marketMaster.EditMarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterResponse;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.EditTrainingDeputationTrackerRequest;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.TrainingDeputationTrackerRequest;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.TrainingDeputationTrackerResponse;
import com.sericulture.masterdata.model.dto.MarketMasterDTO;
import com.sericulture.masterdata.model.dto.TrainingDeputationTrackerDTO;
import com.sericulture.masterdata.model.entity.MarketMaster;
import com.sericulture.masterdata.model.entity.TrainingDeputationTracker;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MarketMasterRepository;
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
public class TrainingDeputationTrackerService {

    @Autowired
    TrainingDeputationTrackerRepository trainingDeputationTrackerRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public TrainingDeputationTrackerResponse insertTrainingDeputationTrackerDetails(TrainingDeputationTrackerRequest trainingDeputationTrackerRequest){
        TrainingDeputationTrackerResponse trainingDeputationTrackerResponse = new TrainingDeputationTrackerResponse();
        TrainingDeputationTracker trainingDeputationTracker = mapper.trainingDeputationTrackerObjectToEntity(trainingDeputationTrackerRequest,TrainingDeputationTracker.class);
        validator.validate(trainingDeputationTracker);
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
        return mapper.trainingDeputationTrackerEntityToObject(trainingDeputationTrackerRepository.save(trainingDeputationTracker),TrainingDeputationTrackerResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrainingDeputationTrackerDetails(final Pageable pageable){
        return convertToMapResponse(trainingDeputationTrackerRepository.findByActiveOrderByTrainingDeputationIdAsc( true, pageable));
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trainingDeputationTrackerRepository.findByActiveOrderByOfficialNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrainingDeputationTracker> activeTrainingDeputationTrackers) {
        Map<String, Object> response = new HashMap<>();

        List<TrainingDeputationTrackerResponse> trainingDeputationTrackerResponses = activeTrainingDeputationTrackers.getContent().stream()
                .map(trainingDeputationTracker -> mapper.trainingDeputationTrackerEntityToObject(trainingDeputationTracker,TrainingDeputationTrackerResponse.class)).collect(Collectors.toList());
        response.put("trainingDeputationTracker",trainingDeputationTrackerResponses);
        response.put("currentPage", activeTrainingDeputationTrackers.getNumber());
        response.put("totalItems", activeTrainingDeputationTrackers.getTotalElements());
        response.put("totalPages", activeTrainingDeputationTrackers.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrainingDeputationTracker> activeTrainingDeputationTrackers) {
        Map<String, Object> response = new HashMap<>();

        List<TrainingDeputationTrackerResponse> trainingDeputationTrackerResponses = activeTrainingDeputationTrackers.stream()
                .map(trainingDeputationTracker -> mapper.trainingDeputationTrackerEntityToObject(trainingDeputationTracker,TrainingDeputationTrackerResponse.class)).collect(Collectors.toList());
        response.put("trainingDeputationTracker",trainingDeputationTrackerResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrainingDeputationTrackerDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(trainingDeputationTrackerRepository.getByActiveOrderByTrainingDeputationIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TrainingDeputationTrackerDTO> activeTrainingDeputationTrackers) {
        Map<String, Object> response = new HashMap<>();

        List<TrainingDeputationTrackerResponse> trainingDeputationTrackerResponses = activeTrainingDeputationTrackers.getContent().stream()
                .map(trainingDeputationTracker -> mapper.trainingDeputationTrackerDTOToObject(trainingDeputationTracker,TrainingDeputationTrackerResponse.class)).collect(Collectors.toList());
        response.put("trainingDeputationTracker",trainingDeputationTrackerResponses);
        response.put("currentPage", activeTrainingDeputationTrackers.getNumber());
        response.put("totalItems", activeTrainingDeputationTrackers.getTotalElements());
        response.put("totalPages", activeTrainingDeputationTrackers.getTotalPages());
        return response;
    }

    @Transactional
    public TrainingDeputationTrackerResponse deleteTrainingDeputationTrackerDetails(long id) {
        TrainingDeputationTrackerResponse trainingDeputationTrackerResponse = new TrainingDeputationTrackerResponse();
        TrainingDeputationTracker trainingDeputationTracker = trainingDeputationTrackerRepository.findByTrainingDeputationIdAndActive(id, true);
        if (Objects.nonNull(trainingDeputationTracker)) {
            trainingDeputationTracker.setActive(false);
            trainingDeputationTrackerResponse = mapper.trainingDeputationTrackerEntityToObject(trainingDeputationTrackerRepository.save(trainingDeputationTracker), TrainingDeputationTrackerResponse.class);
            trainingDeputationTrackerResponse.setError(false);
        } else {
            trainingDeputationTrackerResponse.setError(true);
            trainingDeputationTrackerResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trainingDeputationTrackerResponse;
    }

    @Transactional
    public TrainingDeputationTrackerResponse getById(int id){
        TrainingDeputationTrackerResponse trainingDeputationTrackerResponse = new TrainingDeputationTrackerResponse();
        TrainingDeputationTracker trainingDeputationTracker = trainingDeputationTrackerRepository.findByTrainingDeputationIdAndActive(id,true);
        if(trainingDeputationTracker == null){
            trainingDeputationTrackerResponse.setError(true);
            trainingDeputationTrackerResponse.setError_description("Invalid id");
        }else{
            trainingDeputationTrackerResponse =  mapper.trainingDeputationTrackerEntityToObject(trainingDeputationTracker,TrainingDeputationTrackerResponse.class);
            trainingDeputationTrackerResponse.setError(false);
        }
        log.info("Entity is ",trainingDeputationTracker);
        return trainingDeputationTrackerResponse;
    }

    @Transactional
    public TrainingDeputationTrackerResponse getByIdJoin(int id) {
        TrainingDeputationTrackerResponse trainingDeputationTrackerResponse = new TrainingDeputationTrackerResponse();
        TrainingDeputationTrackerDTO trainingDeputationTrackerDTO = trainingDeputationTrackerRepository.getByTrainingDeputationIdAndActive(id, true);
        if (trainingDeputationTrackerDTO == null) {
            // throw new ValidationException("Invalid Id");
            trainingDeputationTrackerResponse.setError(true);
            trainingDeputationTrackerResponse.setError_description("Invalid id");
        } else {
            trainingDeputationTrackerResponse = mapper.trainingDeputationTrackerDTOToObject(trainingDeputationTrackerDTO, TrainingDeputationTrackerResponse.class);
            trainingDeputationTrackerResponse.setError(false);
        }
        log.info("Entity is ", trainingDeputationTrackerDTO);
        return trainingDeputationTrackerResponse;
    }

    @Transactional
    public TrainingDeputationTrackerResponse updateTrainingDeputationTrackerDetails(EditTrainingDeputationTrackerRequest trainingDeputationTrackerRequest) {
            TrainingDeputationTrackerResponse trainingDeputationTrackerResponse = new TrainingDeputationTrackerResponse();
//        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterName(marketMasterRequest.getMarketMasterName());
//        if (marketMasterList.size() > 0) {
//            marketMasterResponse.setError(true);
//            marketMasterResponse.setError_description("Market already exists, duplicates are not allowed.");
//             throw new ValidationException("Village already exists, duplicates are not allowed.");
//        } else {

        TrainingDeputationTracker trainingDeputationTracker = trainingDeputationTrackerRepository.findByTrainingDeputationIdAndActiveIn(trainingDeputationTrackerRequest.getTrainingDeputationId(), Set.of(true, false));
        if (Objects.nonNull(trainingDeputationTracker)) {
            trainingDeputationTracker.setOfficialName(trainingDeputationTrackerRequest.getOfficialName());
            trainingDeputationTracker.setDesignationId(trainingDeputationTrackerRequest.getDesignationId());
            trainingDeputationTracker.setOfficialAddress(trainingDeputationTrackerRequest.getOfficialAddress());
            trainingDeputationTracker.setMobileNumber(trainingDeputationTrackerRequest.getMobileNumber());
            trainingDeputationTracker.setDeputedInstituteId(trainingDeputationTrackerRequest.getDeputedInstituteId());
            trainingDeputationTracker.setDeputedFromDate(trainingDeputationTrackerRequest.getDeputedFromDate());
            trainingDeputationTracker.setDeputedToDate(trainingDeputationTrackerRequest.getDeputedToDate());
            trainingDeputationTracker.setTrProgramMasterId(trainingDeputationTrackerRequest.getTrProgramMasterId());
            trainingDeputationTracker.setTrCourseMasterId(trainingDeputationTrackerRequest.getTrCourseMasterId());
            trainingDeputationTracker.setDeputedAttended(trainingDeputationTrackerRequest.getDeputedAttended());
            trainingDeputationTracker.setDeputedRemarks(trainingDeputationTrackerRequest.getDeputedRemarks());
            trainingDeputationTracker.setActive(true);
            TrainingDeputationTracker trainingDeputationTracker1 = trainingDeputationTrackerRepository.save(trainingDeputationTracker);
            trainingDeputationTrackerResponse = mapper.trainingDeputationTrackerEntityToObject(trainingDeputationTracker1, TrainingDeputationTrackerResponse.class);
            trainingDeputationTrackerResponse.setError(false);
        } else {
            trainingDeputationTrackerResponse.setError(true);
            trainingDeputationTrackerResponse.setError_description("Error occurred while fetching trainingDeputationTracker");
            // throw new ValidationException("Error occurred while fetching village");
        }
        return trainingDeputationTrackerResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("officialName");
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
        Page<TrainingDeputationTrackerDTO> trainingDeputationTrackerDTOS = trainingDeputationTrackerRepository.getSortedTrainingDeputations(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",trainingDeputationTrackerDTOS);
        return convertPageableDTOToMapResponse(trainingDeputationTrackerDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TrainingDeputationTrackerDTO> activeTrainingDeputationTrackers) {
        Map<String, Object> response = new HashMap<>();

        List<TrainingDeputationTrackerResponse> trainingDeputationTrackerResponses = activeTrainingDeputationTrackers.getContent().stream()
                .map(trainingDeputationTracker -> mapper.trainingDeputationTrackerDTOToObject(trainingDeputationTracker,TrainingDeputationTrackerResponse.class)).collect(Collectors.toList());
        response.put("trainingDeputationTracker",trainingDeputationTrackerResponses);
        response.put("currentPage", activeTrainingDeputationTrackers.getNumber());
        response.put("totalItems", activeTrainingDeputationTrackers.getTotalElements());
        response.put("totalPages", activeTrainingDeputationTrackers.getTotalPages());

        return response;
    }
}
