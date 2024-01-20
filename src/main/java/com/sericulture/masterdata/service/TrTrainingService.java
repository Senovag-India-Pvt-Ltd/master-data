package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.trSchedule.EditTrScheduleRequest;
import com.sericulture.masterdata.model.api.trSchedule.TrScheduleRequest;
import com.sericulture.masterdata.model.api.trSchedule.TrScheduleResponse;
import com.sericulture.masterdata.model.api.trTraining.EditTrTrainingRequest;
import com.sericulture.masterdata.model.api.trTraining.TrTrainingRequest;
import com.sericulture.masterdata.model.api.trTraining.TrTrainingResponse;
import com.sericulture.masterdata.model.dto.TrScheduleDTO;
import com.sericulture.masterdata.model.dto.TrTrainingDTO;
import com.sericulture.masterdata.model.entity.TrSchedule;
import com.sericulture.masterdata.model.entity.TrTraining;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrScheduleRepository;
import com.sericulture.masterdata.repository.TrTrainingRepository;
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

public class TrTrainingService {

    @Autowired
    TrTrainingRepository trTrainingRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TrTrainingResponse getTrTrainingDetails(String trTraineeName){
        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
        TrTraining trTraining = null;
        if(trTraining==null){
            trTraining = trTrainingRepository.findByTrTraineeNameAndActive(trTraineeName, true);
            trTrainingResponse = mapper.trTrainingEntityToObject(trTraining, TrTrainingResponse.class);
            trTrainingResponse.setError(false);
        }else{
            trTrainingResponse.setError(true);
            trTrainingResponse.setError_description("Tr Training not found");
        }
        log.info("Entity is ",trTraining);
        return trTrainingResponse;

    }

    @Transactional
    public TrTrainingResponse insertTrTrainingDetails(TrTrainingRequest trTrainingRequest){
        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
        TrTraining trTraining = mapper.trTrainingObjectToEntity(trTrainingRequest,TrTraining.class);
        validator.validate(trTraining);
        List<TrTraining> trTrainingList= trTrainingRepository.findByTrTraineeName(trTrainingRequest.getTrTraineeName());
        if(!trTrainingList.isEmpty() && trTrainingList.stream().filter(TrTraining::getActive).findAny().isPresent()){
            trTrainingResponse.setError(true);
            trTrainingResponse.setError_description("TrTraining name already exist");
        }
        else if(!trTrainingList.isEmpty() && trTrainingList.stream().filter(Predicate.not(TrTraining::getActive)).findAny().isPresent()){
            trTrainingResponse.setError(true);
            trTrainingResponse.setError_description("trTraining name already exist with inactive state");
        }else {
            trTrainingResponse = mapper.trTrainingEntityToObject(trTrainingRepository.save(trTraining), TrTrainingResponse.class);
            trTrainingResponse.setError(false);
        }
        return trTrainingResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrTrainingDetails(final Pageable pageable){
        return convertToMapResponse(trTrainingRepository.findByActiveOrderByTrTraineeNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trTrainingRepository.findByActiveOrderByTrTraineeNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrTraining> activeTrTrainings) {
        Map<String, Object> response = new HashMap<>();

        List<TrTrainingResponse> trTrainingResponses = activeTrTrainings.getContent().stream()
                .map(trTraining -> mapper.trTrainingEntityToObject(trTraining,TrTrainingResponse.class)).collect(Collectors.toList());
        response.put("trTraining",trTrainingResponses);
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrTrainingDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(trTrainingRepository.getByActiveOrderByTrTraineeIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TrTrainingDTO> activeTrTrainings) {
        Map<String, Object> response = new HashMap<>();

        List<TrTrainingResponse> trTrainingResponses = activeTrTrainings.getContent().stream()
                .map(trTraining -> mapper.trTrainingDTOToObject(trTraining,TrTrainingResponse.class)).collect(Collectors.toList());
        response.put("trTraining",trTrainingResponses);
        response.put("currentPage", activeTrTrainings.getNumber());
        response.put("totalItems", activeTrTrainings.getTotalElements());
        response.put("totalPages", activeTrTrainings.getTotalPages());
        return response;
    }


    @Transactional
    public TrTrainingResponse deleteTrTrainingDetails(long id) {

        TrTrainingResponse trTrainingResponse= new TrTrainingResponse();
        TrTraining trTraining = trTrainingRepository.findByTrTraineeIdAndActive(id, true);
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

    @Transactional
    public TrTrainingResponse getById(int id){
        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
        TrTraining trTraining = trTrainingRepository.findByTrTraineeIdAndActive(id,true);
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

    @Transactional
    public TrTrainingResponse getByIdJoin(int id) {
        TrTrainingResponse trTrainingResponse= new TrTrainingResponse();
        TrTrainingDTO trTrainingDTO = trTrainingRepository.getByTrTraineeIdAndActive(id, true);
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
    public TrTrainingResponse updateTrTrainingDetails(EditTrTrainingRequest trTrainingRequest){

        TrTrainingResponse trTrainingResponse = new TrTrainingResponse();
        List<TrTraining> trTrainingList = trTrainingRepository.findByTrTraineeName(trTrainingRequest.getTrTraineeName());
        if(trTrainingList.size()>0){
            trTrainingResponse.setError(true);
            trTrainingResponse.setError_description("TrTraining already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TrTraining trTraining= trTrainingRepository.findByTrTraineeIdAndActiveIn(trTrainingRequest.getTrTraineeId(), Set.of(true,false));
            if(Objects.nonNull(trTraining)){
                trTraining.setTrTraineeId(trTrainingRequest.getTrTraineeId());
                trTraining.setTrTraineeName(trTrainingRequest.getTrTraineeName());
                trTraining.setDesignationId(trTrainingRequest.getDesignationId());
                trTraining.setTrOfficeId(trTrainingRequest.getTrOfficeId());
                trTraining.setGender(trTrainingRequest.getGender());
                trTraining.setMobileNumber(trTrainingRequest.getMobileNumber());
                trTraining.setPlace(trTrainingRequest.getPlace());
                trTraining.setStateId(trTrainingRequest.getStateId());
                trTraining.setDistrictId(trTrainingRequest.getDistrictId());
                trTraining.setTalukId(trTrainingRequest.getTalukId());
                trTraining.setHobliId(trTrainingRequest.getHobliId());
                trTraining.setVillageId(trTrainingRequest.getVillageId());
                trTraining.setPreTestScore(trTrainingRequest.getPreTestScore());
                trTraining.setPostTestScore(trTrainingRequest.getPostTestScore());
                trTraining.setPercentageImproved(trTrainingRequest.getPercentageImproved());

                trTraining.setActive(true);
                TrTraining trTraining1 = trTrainingRepository.save(trTraining);
                trTrainingResponse = mapper.trTrainingEntityToObject(trTraining1, TrTrainingResponse.class);
                trTrainingResponse.setError(false);
            } else {
                trTrainingResponse.setError(true);
                trTrainingResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return trTrainingResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("trTraineeName");
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
        Page<TrTrainingDTO> trTrainingDTOS = trTrainingRepository.getSortedTrTrainee(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
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
