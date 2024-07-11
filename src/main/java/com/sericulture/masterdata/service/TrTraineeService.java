package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.trTrainee.EditTrTraineeRequest;
import com.sericulture.masterdata.model.api.trTrainee.TrTraineeRequest;
import com.sericulture.masterdata.model.api.trTrainee.TrTraineeResponse;
import com.sericulture.masterdata.model.dto.TrScheduleDTO;
import com.sericulture.masterdata.model.dto.TrTraineeDTO;
import com.sericulture.masterdata.model.entity.TrTrainee;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrTraineeRepository;
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

public class TrTraineeService {

    @Autowired
    TrTraineeRepository trTraineeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public TrTraineeResponse insertTrTraineeDetails(TrTraineeRequest trTraineeRequest){
        TrTraineeResponse trTraineeResponse = new TrTraineeResponse();
        TrTrainee trTrainee = mapper.trTraineeObjectToEntity(trTraineeRequest, TrTrainee.class);
        validator.validate(trTrainee);
        List<TrTrainee> trTraineeList= trTraineeRepository.findByTrTraineeName(trTraineeRequest.getTrTraineeName());
        if(!trTraineeList.isEmpty() && trTraineeList.stream().filter(TrTrainee::getActive).findAny().isPresent()){
            trTraineeResponse.setError(true);
            trTraineeResponse.setError_description("Trainee name already exist");
//        }
//        else if(!trTraineeList.isEmpty() && trTraineeList.stream().filter(Predicate.not(TrTrainee::getActive)).findAny().isPresent()){
//            trTraineeResponse.setError(true);
//            trTraineeResponse.setError_description("Trainee name already exist with inactive state");
        }else{
            // Check for duplicate Trainee Number
            List<TrTrainee> trTraineeListByNumber = trTraineeRepository.findByMobileNumber(trTrainee.getMobileNumber());
            if (!trTraineeListByNumber.isEmpty() && trTraineeListByNumber.stream().anyMatch(TrTrainee::getActive)) {
                trTraineeResponse.setError(true);
                trTraineeResponse.setError_description("Trainee Mobile Number already exists");
            } else if (!trTraineeListByNumber.isEmpty() && trTraineeListByNumber.stream().anyMatch(Predicate.not(TrTrainee::getActive))) {
                trTraineeResponse.setError(true);
                trTraineeResponse.setError_description("Trainee Mobile Number already exists with inactive state");
            } else {
                trTraineeResponse = mapper.trTraineeEntityToObject(trTraineeRepository.save(trTrainee), TrTraineeResponse.class);
                trTraineeResponse.setError(false);
            }
        }
        return trTraineeResponse;
    }

    public Map<String,Object> getPaginatedTrTraineeDetails(final Pageable pageable){
        return convertToMapResponse(trTraineeRepository.findByActiveOrderByTrTraineeNameAsc( true,pageable ));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trTraineeRepository.findByActiveOrderByTrTraineeNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrTrainee> activeTrTrainees) {
        Map<String, Object> response = new HashMap<>();

        List<TrTraineeResponse> trTraineeResponses = activeTrTrainees.getContent().stream()
                .map(trTrainee -> mapper.trTraineeEntityToObject(trTrainee, TrTraineeResponse.class)).collect(Collectors.toList());
        response.put("trTrainee",trTraineeResponses);
        response.put("currentPage", activeTrTrainees.getNumber());
        response.put("totalItems", activeTrTrainees.getTotalElements());
        response.put("totalPages", activeTrTrainees.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrTrainee> activeTrTrainees) {
        Map<String, Object> response = new HashMap<>();

        List<TrTraineeResponse> trTraineeResponses = activeTrTrainees.stream()
                .map(trTrainee -> mapper.trTraineeEntityToObject(trTrainee, TrTraineeResponse.class)).collect(Collectors.toList());
        response.put("trTrainee",trTraineeResponses);
        return response;
    }

    public Map<String,Object> getPaginatedTrTraineeDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(trTraineeRepository.getByActiveOrderByTrTraineeIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TrTraineeDTO> activeTrTrainees) {
        Map<String, Object> response = new HashMap<>();

        List<TrTraineeResponse> trTraineeResponses = activeTrTrainees.getContent().stream()
                .map(trTrainee -> mapper.trTraineeDTOToObject(trTrainee, TrTraineeResponse.class)).collect(Collectors.toList());
        response.put("trTrainee",trTraineeResponses);
        response.put("currentPage", activeTrTrainees.getNumber());
        response.put("totalItems", activeTrTrainees.getTotalElements());
        response.put("totalPages", activeTrTrainees.getTotalPages());
        return response;
    }


    @Transactional
    public TrTraineeResponse deleteTrTraineeDetails(long id) {

        TrTraineeResponse trTraineeResponse= new TrTraineeResponse();
        TrTrainee trTrainee = trTraineeRepository.findByTrTraineeIdAndActive(id, true);
        if (Objects.nonNull(trTrainee)) {
            trTrainee.setActive(false);
            trTraineeResponse = mapper.trTraineeEntityToObject(trTraineeRepository.save(trTrainee), TrTraineeResponse.class);
            trTraineeResponse.setError(false);
        } else {
            trTraineeResponse.setError(true);
            trTraineeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trTraineeResponse;
    }

    public TrTraineeResponse getById(int id){
        TrTraineeResponse trTraineeResponse = new TrTraineeResponse();
        TrTrainee trTrainee = trTraineeRepository.findByTrTraineeIdAndActive(id,true);
        if(trTrainee == null){
            trTraineeResponse.setError(true);
            trTraineeResponse.setError_description("Invalid id");
        }else{
            trTraineeResponse =  mapper.trTraineeEntityToObject(trTrainee, TrTraineeResponse.class);
            trTraineeResponse.setError(false);
        }
        log.info("Entity is ",trTrainee);
        return trTraineeResponse;
    }

    public Map<String,Object> getByTrScheduleId(int TrScheduleId) {
        Map<String, Object> response = new HashMap<>();
        List<TrTrainee> trTraineeList = trTraineeRepository.findByTrScheduleIdAndActive(TrScheduleId, true);
        if (trTraineeList.isEmpty()) {
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            return response;
        } else {
            response = convertListToMapResponse(trTraineeList);
            return response;
//        return convertListToMapResponse(reelerList);
        }
    }
    private Map<String, Object> convertListToMapResponse(List<TrTrainee> trTraineeList) {
        Map<String, Object> response = new HashMap<>();
        List<TrTraineeResponse> trTraineeResponses = trTraineeList.stream()
                .map(trTrainee -> mapper.trTraineeEntityToObject(trTrainee,TrTraineeResponse.class)).collect(Collectors.toList());
        response.put("trTrainee",trTraineeResponses);
        response.put("totalItems", trTraineeList.size());
        return response;
    }

    public TrTraineeResponse getByIdJoin(int id) {
        TrTraineeResponse trTraineeResponse= new TrTraineeResponse();
        TrTraineeDTO trTraineeDTO = trTraineeRepository.getByTrTraineeIdAndActive(id, true);
        if (trTraineeDTO == null) {
            // throw new ValidationException("Invalid Id");
            trTraineeResponse.setError(true);
            trTraineeResponse.setError_description("Invalid id");
        } else {
            trTraineeResponse = mapper.trTraineeDTOToObject(trTraineeDTO, TrTraineeResponse.class);
            trTraineeResponse.setError(false);
        }
        log.info("Entity is ", trTraineeDTO);
        return trTraineeResponse;
    }


    @Transactional
    public TrTraineeResponse updateTrTraineeDetails(EditTrTraineeRequest trTraineeRequest){

        TrTraineeResponse trTraineeResponse = new TrTraineeResponse();
        List<TrTrainee> trTraineeList = trTraineeRepository.findByActiveAndTrTraineeName(true,trTraineeRequest.getTrTraineeName());
        if(trTraineeList.size()>0){
            trTraineeResponse.setError(true);
            trTraineeResponse.setError_description("TrTrainee already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TrTrainee trTrainee= trTraineeRepository.findByTrTraineeIdAndActiveIn(trTraineeRequest.getTrTraineeId(), Set.of(true,false));
            if(Objects.nonNull(trTrainee)){
                trTrainee.setTrScheduleId(trTraineeRequest.getTrScheduleId());
                trTrainee.setTrTraineeName(trTraineeRequest.getTrTraineeName());
                trTrainee.setDesignationId(trTraineeRequest.getDesignationId());
                trTrainee.setTrOfficeId(trTraineeRequest.getTrOfficeId());
                trTrainee.setGender(trTraineeRequest.getGender());
                trTrainee.setMobileNumber(trTraineeRequest.getMobileNumber());
                trTrainee.setPlace(trTraineeRequest.getPlace());
                trTrainee.setStateId(trTraineeRequest.getStateId());
                trTrainee.setDistrictId(trTraineeRequest.getDistrictId());
                trTrainee.setTalukId(trTraineeRequest.getTalukId());
                trTrainee.setHobliId(trTraineeRequest.getHobliId());
                trTrainee.setVillageId(trTraineeRequest.getVillageId());
                trTrainee.setPreTestScore(trTraineeRequest.getPreTestScore());
                trTrainee.setPostTestScore(trTraineeRequest.getPostTestScore());
                trTrainee.setPercentageImproved(trTraineeRequest.getPercentageImproved());

                trTrainee.setActive(true);
                TrTrainee trTrainee1 = trTraineeRepository.save(trTrainee);
                trTraineeResponse = mapper.trTraineeEntityToObject(trTrainee1, TrTraineeResponse.class);
                trTraineeResponse.setError(false);
            } else {
                trTraineeResponse.setError(true);
                trTraineeResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return trTraineeResponse;
    }

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
        Page<TrTraineeDTO> trTraineeDTOS = trTraineeRepository.getSortedTrTrainee(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",trTraineeDTOS);
        return convertPageableDTOToMapResponse(trTraineeDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TrTraineeDTO> activeTrTrainees) {
        Map<String, Object> response = new HashMap<>();

        List<TrTraineeResponse> trTraineeResponses = activeTrTrainees.getContent().stream()
                .map(trTrainee -> mapper.trTraineeDTOToObject(trTrainee, TrTraineeResponse.class)).collect(Collectors.toList());
        response.put("trTrainee",trTraineeResponses);
        response.put("currentPage", activeTrTrainees.getNumber());
        response.put("totalItems", activeTrTrainees.getTotalElements());
        response.put("totalPages", activeTrTrainees.getTotalPages());

        return response;
    }

    public Map<String,Object> getByTrScheduleIdJoin(int trScheduleId){
        Map<String, Object> response = new HashMap<>();
        List<TrTraineeDTO> trTraineeDTO = trTraineeRepository.getByTrScheduleIdAndActive(trScheduleId, true);
        if(trTraineeDTO.isEmpty()){
            response.put("error","Error");
            response.put("error_description","Invalid id");
            return response;
        }else {
            response = convertListDTOToMapResponse(trTraineeDTO);
            return response;

        }

    }

    private Map<String, Object> convertListDTOToMapResponse(List<TrTraineeDTO> trTraineeDTOList) {
        Map<String, Object> response = new HashMap<>();
        List<TrTraineeResponse> trTraineeResponse = trTraineeDTOList.stream()
                .map(trTraineeDTO -> mapper.trTraineeDTOToObject(trTraineeDTO, TrTraineeResponse.class)).collect(Collectors.toList());
        response.put("trTrainee", trTraineeResponse);
        response.put("totalItems", trTraineeDTOList.size());
        return response;
    }
}
