package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsBudget.EditTsBudgetRequest;
import com.sericulture.masterdata.model.api.tsBudget.TsBudgetRequest;
import com.sericulture.masterdata.model.api.tsBudget.TsBudgetResponse;
import com.sericulture.masterdata.model.dto.TsBudgetDTO;
import com.sericulture.masterdata.model.entity.TsBudget;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsBudgetRepository;
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
public class TsBudgetService {
    @Autowired
    TsBudgetRepository tsBudgetRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsBudgetResponse insertTsBudgetDetails(TsBudgetRequest tsBudgetRequest){
        TsBudgetResponse tsBudgetResponse = new TsBudgetResponse();
        TsBudget tsBudget = mapper.tsBudgetObjectToEntity(tsBudgetRequest, TsBudget.class);
        validator.validate(tsBudget);
        List<TsBudget> tsBudgetList = tsBudgetRepository.findByFinancialYearMasterId(tsBudgetRequest.getFinancialYearMasterId());
        if(!tsBudgetList.isEmpty() && tsBudgetList.stream().filter(TsBudget::getActive).findAny().isPresent()){
            tsBudgetResponse.setError(true);
            tsBudgetResponse.setError_description("TsBudget already exist");
        }
        else if(!tsBudgetList.isEmpty() && tsBudgetList.stream().filter(Predicate.not(TsBudget::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsBudgetResponse.setError(true);
            tsBudgetResponse.setError_description("TsBudget already exist with inactive state");
        }else {
            tsBudgetResponse = mapper.tsBudgetEntityToObject(tsBudgetRepository.save(tsBudget), TsBudgetResponse.class);
            tsBudgetResponse.setError(false);
        }
        return tsBudgetResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getTsBudgetDetails(final Pageable pageable){
        return convertToMapResponse(tsBudgetRepository.findByActiveOrderByTsBudgetIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsBudgetRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsBudget> activeTsBudget) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetResponse> tsBudgetResponses= activeTsBudget.getContent().stream()
                .map(tsBudget -> mapper.tsBudgetEntityToObject(tsBudget, TsBudgetResponse.class)).collect(Collectors.toList());
        response.put("tsBudget",tsBudgetResponses);
        response.put("currentPage", activeTsBudget.getNumber());
        response.put("totalItems", activeTsBudget.getTotalElements());
        response.put("totalPages", activeTsBudget.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsBudget> activeTsBudget) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetResponse> tsBudgetResponses = activeTsBudget.stream()
                .map(tsBudget  -> mapper.tsBudgetEntityToObject(tsBudget, TsBudgetResponse.class)).collect(Collectors.toList());
        response.put("tsBudget",tsBudgetResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTsBudgetWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsBudgetRepository.getByActiveOrderByTsBudgetIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsBudgetDTO> activeTsBudget) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetResponse> tsBudgetResponses= activeTsBudget.getContent().stream()
                .map(tsBudget -> mapper.tsBudgetDTOToObject(tsBudget,TsBudgetResponse.class)).collect(Collectors.toList());
        response.put("tsBudget",tsBudgetResponses);
        response.put("currentPage", activeTsBudget.getNumber());
        response.put("totalItems", activeTsBudget.getTotalElements());
        response.put("totalPages", activeTsBudget.getTotalPages());
        return response;
    }


    @Transactional
    public TsBudgetResponse deleteTsBudgetDetails(long id) {
        TsBudgetResponse tsBudgetResponse= new TsBudgetResponse();
        TsBudget tsBudget = tsBudgetRepository.findByTsBudgetIdAndActive(id, true);
        if (Objects.nonNull(tsBudget)) {
            tsBudget.setActive(false);
            tsBudgetResponse= mapper.tsBudgetEntityToObject(tsBudgetRepository.save(tsBudget),TsBudgetResponse.class);
            tsBudgetResponse.setError(false);
        } else {
            tsBudgetResponse.setError(true);
            tsBudgetResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsBudgetResponse;
    }

    @Transactional
    public TsBudgetResponse getById(int id){
        TsBudgetResponse tsBudgetResponse = new TsBudgetResponse();
        TsBudget tsBudget = tsBudgetRepository.findByTsBudgetIdAndActive(id,true);


        if(tsBudget == null){
            tsBudgetResponse.setError(true);
            tsBudgetResponse.setError_description("Invalid id");
        }else{
            tsBudgetResponse =  mapper.tsBudgetEntityToObject(tsBudget, TsBudgetResponse.class);
            tsBudgetResponse.setError(false);
        }
        log.info("Entity is ",tsBudget);
        return tsBudgetResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetDTO> tsBudgetList = tsBudgetRepository.getTsBudget(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(tsBudgetList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", tsBudgetList);
//            response = convertListToMapResponse(tsBudgetList);
//            return response;
//        }
//    }



    @Transactional
    public TsBudgetResponse getByIdJoin(int id){
        TsBudgetResponse tsBudgetResponse = new TsBudgetResponse();
        TsBudgetDTO tsBudgetDTO = tsBudgetRepository.getByTsBudgetIdAndActive(id,true);
        if(tsBudgetDTO == null){
            tsBudgetResponse.setError(true);
            tsBudgetResponse.setError_description("Invalid id");
        } else {
            tsBudgetResponse = mapper.tsBudgetDTOToObject(tsBudgetDTO, TsBudgetResponse.class);
            tsBudgetResponse.setError(false);
        }
        log.info("Entity is ", tsBudgetDTO);
        return tsBudgetResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String, Object> getTsBudgetByFinancialYearMasterId(Long financialYearMasterId) {
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetDTO> tsBudgetList = tsBudgetRepository.getByFinancialYearMasterIdAndActive(financialYearMasterId, true);
//        if (tsBudgetList.isEmpty()) {
////            throw new ValidationException("Invalid Id");
//            response.put("error", "Error");
//            response.put("error_description", "Invalid id");
//            response.put("success", false);
//            return response;
//        } else {
//            log.info("Entity is ", tsBudgetList);
//            response = convertListDTOToMapResponse(tsBudgetList);
//            response.put("success", true);
//            return response;
//
//        }
//
//    }

    private Map<String, Object> convertListToMapResponse(List<TsBudget> tsBudgetList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetResponse> tsBudgetResponses = tsBudgetList.stream()
                .map(tsBudget -> mapper.tsBudgetEntityToObject(tsBudget, TsBudgetResponse.class)).collect(Collectors.toList());
        response.put("tsBudget", tsBudgetResponses);
        response.put("totalItems", tsBudgetList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsBudgetDTO> tsBudgetList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetResponse> tsBudgetResponses = tsBudgetList.stream()
                .map(tsBudget -> mapper.tsBudgetDTOToObject(tsBudget,TsBudgetResponse.class)).collect(Collectors.toList());
        response.put("tsBudget",tsBudgetResponses);
        response.put("totalItems", tsBudgetList.size());
        return response;
    }


    @Transactional
    public TsBudgetResponse updateTsBudgetDetails(EditTsBudgetRequest tsBudgetRequest) {
        TsBudgetResponse tsBudgetResponse = new TsBudgetResponse();
        List<TsBudget> tsBudgetList = tsBudgetRepository.findByFinancialYearMasterIdAndTsBudgetIdIsNot(tsBudgetRequest.getFinancialYearMasterId(), tsBudgetRequest.getTsBudgetId());
        if (tsBudgetList.size() > 0) {
            tsBudgetResponse.setError(true);
            tsBudgetResponse.setError_description("TsBudget exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsBudget tsBudget = tsBudgetRepository.findByTsBudgetIdAndActiveIn(tsBudgetRequest.getTsBudgetId(), Set.of(true, false));
            if (Objects.nonNull(tsBudget)) {
                tsBudget.setFinancialYearMasterId(tsBudgetRequest.getFinancialYearMasterId());
                tsBudget.setDate(tsBudgetRequest.getDate());
                tsBudget.setCentralBudget(tsBudgetRequest.getCentralBudget());
                tsBudget.setStateBudget(tsBudgetRequest.getStateBudget());
                tsBudget.setAmount(tsBudgetRequest.getAmount());

                tsBudget.setActive(true);
                TsBudget tsBudget1 = tsBudgetRepository.save(tsBudget);
                tsBudgetResponse = mapper.tsBudgetEntityToObject(tsBudget1, TsBudgetResponse.class);
                tsBudgetResponse.setError(false);
            } else {
                tsBudgetResponse.setError(true);
                tsBudgetResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsBudgetResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("financialYearMaster.financialYear");
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
        Page<TsBudgetDTO> tsBudgetDTOS = tsBudgetRepository.getSortedTsBudget(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsBudgetDTOS);
        return convertPageableDTOToMapResponse(tsBudgetDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsBudgetDTO> activeTsBudget) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetResponse> tsBudgetResponses = activeTsBudget.getContent().stream()
                .map(tsBudget -> mapper.tsBudgetDTOToObject(tsBudget,TsBudgetResponse.class)).collect(Collectors.toList());
        response.put("tsBudget",tsBudgetResponses);
        response.put("currentPage", activeTsBudget.getNumber());
        response.put("totalItems", activeTsBudget.getTotalElements());
        response.put("totalPages", activeTsBudget.getTotalPages());

        return response;
    }
}
