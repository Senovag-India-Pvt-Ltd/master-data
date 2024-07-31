package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.trTraining.EditTrTrainingRequest;
import com.sericulture.masterdata.model.api.trTraining.TrTrainingRequest;
import com.sericulture.masterdata.model.api.trTraining.TrTrainingResponse;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.EditTrainingDeputationTrackerRequest;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.TrainingDeputationTrackerRequest;
import com.sericulture.masterdata.model.api.trainingDeputationTracker.TrainingDeputationTrackerResponse;
import com.sericulture.masterdata.model.dto.TrTrainingDTO;
import com.sericulture.masterdata.model.dto.TrainingDeputationTrackerDTO;
import com.sericulture.masterdata.model.entity.TrTraining;
import com.sericulture.masterdata.model.entity.TrainingDeputationTracker;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrTrainingRepository;
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
import java.util.stream.Collectors;


@Service
@Slf4j
public class TrTrainingService {

    @Autowired
    TrTrainingRepository trTrainingRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public TrTrainingResponse insertTrTrainingDetails(TrTrainingRequest trTrainingRequest){
        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
        TrTraining trTraining = mapper.trTrainingObjectToEntity(trTrainingRequest,TrTraining.class);
        validator.validate(trTraining);
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
        return mapper.trTrainingEntityToObject(trTrainingRepository.save(trTraining),TrTrainingResponse.class);
    }

    public Map<String,Object> getPaginatedTrTrainingDetails(final Pageable pageable){
        return convertToMapResponse(trTrainingRepository.findByActiveOrderByTrTrainingIdAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trTrainingRepository.findByActiveOrderByTrTrainingIdAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrTraining> activeTrTrainings) {
        Map<String, Object> response = new HashMap<>();

        List<TrTrainingResponse> trTrainingResponses = activeTrTrainings.getContent().stream()
                .map(trTraining -> mapper.trTrainingEntityToObject(trTraining,TrTrainingResponse.class)).collect(Collectors.toList());
        response.put("trainingDeputationTracker",trTrainingResponses);
        response.put("currentPage", activeTrTrainings.getNumber());
        response.put("totalItems", activeTrTrainings.getTotalElements());
        response.put("totalPages", activeTrTrainings.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrTraining> activeTrTrainings) {
        Map<String, Object> response = new HashMap<>();

        List<TrTrainingResponse> trTrainingResponses = activeTrTrainings.stream()
                .map(trTraining -> mapper.trTrainingEntityToObject(trTraining,TrTrainingResponse.class)).collect(Collectors.toList());
        response.put("trTraining",trTrainingResponses);
        return response;
    }

    public Map<String,Object> getPaginatedTrTrainingDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(trTrainingRepository.getByActiveOrderByTrTrainingIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TrTrainingDTO> activeTrTrainings) {
        Map<String, Object> response = new HashMap<>();

        List<TrTrainingResponse>trTrainingResponses = activeTrTrainings.getContent().stream()
                .map(trTraining -> mapper.trTrainingDTOToObject(trTraining,TrTrainingResponse.class)).collect(Collectors.toList());
        response.put("trTraining",trTrainingResponses);
        response.put("currentPage", activeTrTrainings.getNumber());
        response.put("totalItems", activeTrTrainings.getTotalElements());
        response.put("totalPages", activeTrTrainings.getTotalPages());
        return response;
    }

    @Transactional
    public TrTrainingResponse deleteTrTrainingDetails(long id) {
        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
        TrTraining trTraining = trTrainingRepository.findByTrTrainingIdAndActive(id, true);
        if (Objects.nonNull(trTraining)) {
            trTraining.setActive(false);
            trTrainingResponse = mapper.trTrainingEntityToObject(trTrainingRepository.save(trTraining), TrTrainingResponse.class);
            trTrainingResponse.setError(false);
        } else {
            trTrainingResponse.setError(true);
            trTrainingResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trTrainingResponse;
    }

    public TrTrainingResponse getById(int id){
        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
        TrTraining trTraining = trTrainingRepository.findByTrTrainingIdAndActive(id,true);
        if(trTraining == null){
            trTrainingResponse.setError(true);
            trTrainingResponse.setError_description("Invalid id");
        }else{
            trTrainingResponse =  mapper.trTrainingEntityToObject(trTraining,TrTrainingResponse.class);
            trTrainingResponse.setError(false);
        }
        log.info("Entity is ",trTraining);
        return trTrainingResponse;
    }

    public TrTrainingResponse getByIdJoin(int id) {
        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
        TrTrainingDTO  trTrainingDTO = trTrainingRepository.getByTrTrainingIdAndActive(id, true);
        if (trTrainingDTO == null) {
            // throw new ValidationException("Invalid Id");
            trTrainingResponse.setError(true);
            trTrainingResponse.setError_description("Invalid id");
        } else {
            trTrainingResponse = mapper.trTrainingDTOToObject(trTrainingDTO, TrTrainingResponse.class);
            trTrainingResponse.setError(false);
        }
        log.info("Entity is ", trTrainingDTO);
        return trTrainingResponse;
    }

    @Transactional
    public TrTrainingResponse updateTrTrainingDetails(EditTrTrainingRequest trTrainingRequest) {
        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
//        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterName(marketMasterRequest.getMarketMasterName());
//        if (marketMasterList.size() > 0) {
//            marketMasterResponse.setError(true);
//            marketMasterResponse.setError_description("Market already exists, duplicates are not allowed.");
//             throw new ValidationException("Village already exists, duplicates are not allowed.");
//        } else {

        TrTraining trTraining = trTrainingRepository.findByTrTrainingIdAndActiveIn(trTrainingRequest.getTrTrainingId(), Set.of(true, false));
        if (Objects.nonNull(trTraining)) {
            trTraining.setTrTrainingId(trTrainingRequest.getTrTrainingId());
            trTraining.setTrStakeholderType(trTrainingRequest.getTrStakeholderType());
            trTraining.setTrInstitutionMasterId(trTrainingRequest.getTrInstitutionMasterId());
            trTraining.setTrGroupMasterId(trTrainingRequest.getTrGroupMasterId());
            trTraining.setTrProgramMasterId(trTrainingRequest.getTrProgramMasterId());
            trTraining.setTrCourseMasterId(trTrainingRequest.getTrCourseMasterId());
            trTraining.setTrTrainingDate(trTrainingRequest.getTrTrainingDate());
            trTraining.setTrDuration(trTrainingRequest.getTrDuration());
            trTraining.setTrPeriod(trTrainingRequest.getTrPeriod());
            trTraining.setUserMasterId(trTrainingRequest.getUserMasterId());



            trTraining.setActive(true);
            TrTraining trTraining1 = trTrainingRepository.save(trTraining);
            trTrainingResponse = mapper.trTrainingEntityToObject(trTraining1, TrTrainingResponse.class);
            trTrainingResponse.setError(false);
        } else {
            trTrainingResponse.setError(true);
            trTrainingResponse.setError_description("Error occurred while fetching TrTraining");
            // throw new ValidationException("Error occurred while fetching village");
        }
        return trTrainingResponse;
    }

    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("trTrainingId");
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
        Page<TrTrainingDTO> trTrainingDTOS = trTrainingRepository.getSortedTrTrainings(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",trTrainingDTOS);
        return convertPageableDTOToMapResponse(trTrainingDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TrTrainingDTO> activeTrTrainings) {
        Map<String, Object> response = new HashMap<>();

        List<TrTrainingResponse> trTrainingResponses = activeTrTrainings.getContent().stream()
                .map(trTraining -> mapper.trTrainingDTOToObject(trTraining,TrTrainingResponse.class)).collect(Collectors.toList());
        response.put("trTraining",trTrainingResponses);
        response.put("currentPage", activeTrTrainings.getNumber());
        response.put("totalItems", activeTrTrainings.getTotalElements());
        response.put("totalPages", activeTrTrainings.getTotalPages());

        return response;
    }
}
