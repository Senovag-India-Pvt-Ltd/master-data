package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsBudgetTaluk.EditTsBudgetTalukRequest;
import com.sericulture.masterdata.model.api.tsBudgetTaluk.TsBudgetTalukRequest;
import com.sericulture.masterdata.model.api.tsBudgetTaluk.TsBudgetTalukResponse;
import com.sericulture.masterdata.model.dto.TsBudgetTalukDTO;
import com.sericulture.masterdata.model.entity.TsBudgetTaluk;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsBudgetTalukRepository;
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
public class TsBudgetTalukService {
    @Autowired
    TsBudgetTalukRepository tsBudgetTalukRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsBudgetTalukResponse insertTsBudgetTalukDetails(TsBudgetTalukRequest tsBudgetTalukRequest){
        TsBudgetTalukResponse tsBudgetTalukResponse = new TsBudgetTalukResponse();
        TsBudgetTaluk tsBudgetTaluk = mapper.tsBudgetTalukObjectToEntity(tsBudgetTalukRequest, TsBudgetTaluk.class);
        validator.validate(tsBudgetTaluk);
        List<TsBudgetTaluk> tsBudgetTalukList = tsBudgetTalukRepository.findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndScHeadAccountId(tsBudgetTalukRequest.getFinancialYearMasterId(),tsBudgetTalukRequest.getDistrictId(),tsBudgetTalukRequest.getTalukId(),tsBudgetTalukRequest.getScHeadAccountId());
        if(!tsBudgetTalukList.isEmpty() && tsBudgetTalukList.stream().filter(TsBudgetTaluk::getActive).findAny().isPresent()){
            tsBudgetTalukResponse.setError(true);
            tsBudgetTalukResponse.setError_description("TsBudgetTaluk already exist");
        }
        else if(!tsBudgetTalukList.isEmpty() && tsBudgetTalukList.stream().filter(Predicate.not(TsBudgetTaluk::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsBudgetTalukResponse.setError(true);
            tsBudgetTalukResponse.setError_description("TsBudgetTaluk already exist with inactive state");
        }else {
            tsBudgetTalukResponse = mapper.tsBudgetTalukEntityToObject(tsBudgetTalukRepository.save(tsBudgetTaluk), TsBudgetTalukResponse.class);
            tsBudgetTalukResponse.setError(false);
        }
        return tsBudgetTalukResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getTsBudgetTalukDetails(final Pageable pageable){
        return convertToMapResponse(tsBudgetTalukRepository.findByActiveOrderByTsBudgetTalukIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsBudgetTalukRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsBudgetTaluk> activeTsBudgetTaluk) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetTalukResponse> tsBudgetTalukResponses= activeTsBudgetTaluk.getContent().stream()
                .map(tsBudgetTaluk -> mapper.tsBudgetTalukEntityToObject(tsBudgetTaluk, TsBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTaluk",tsBudgetTalukResponses);
        response.put("currentPage", activeTsBudgetTaluk.getNumber());
        response.put("totalItems", activeTsBudgetTaluk.getTotalElements());
        response.put("totalPages", activeTsBudgetTaluk.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsBudgetTaluk> activeTsBudgetTaluk) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetTalukResponse> tsBudgetTalukResponses = activeTsBudgetTaluk.stream()
                .map(tsBudgetTaluk  -> mapper.tsBudgetTalukEntityToObject(tsBudgetTaluk, TsBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTaluk",tsBudgetTalukResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTsBudgetTalukWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsBudgetTalukRepository.getByActiveOrderByTsBudgetTalukIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsBudgetTalukDTO> activeTsBudgetTaluk) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetTalukResponse> tsBudgetTalukResponses= activeTsBudgetTaluk.getContent().stream()
                .map(tsBudgetTaluk -> mapper.tsBudgetTalukDTOToObject(tsBudgetTaluk,TsBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTaluk",tsBudgetTalukResponses);
        response.put("currentPage", activeTsBudgetTaluk.getNumber());
        response.put("totalItems", activeTsBudgetTaluk.getTotalElements());
        response.put("totalPages", activeTsBudgetTaluk.getTotalPages());
        return response;
    }


    @Transactional
    public TsBudgetTalukResponse deleteTsBudgetTalukDetails(long id) {
        TsBudgetTalukResponse tsBudgetTalukResponse= new TsBudgetTalukResponse();
        TsBudgetTaluk tsBudgetTaluk = tsBudgetTalukRepository.findByTsBudgetTalukIdAndActive(id, true);
        if (Objects.nonNull(tsBudgetTaluk)) {
            tsBudgetTaluk.setActive(false);
            tsBudgetTalukResponse= mapper.tsBudgetTalukEntityToObject(tsBudgetTalukRepository.save(tsBudgetTaluk),TsBudgetTalukResponse.class);
            tsBudgetTalukResponse.setError(false);
        } else {
            tsBudgetTalukResponse.setError(true);
            tsBudgetTalukResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsBudgetTalukResponse;
    }

    @Transactional
    public TsBudgetTalukResponse getById(int id){
        TsBudgetTalukResponse tsBudgetTalukResponse = new TsBudgetTalukResponse();
        TsBudgetTaluk tsBudgetTaluk = tsBudgetTalukRepository.findByTsBudgetTalukIdAndActive(id,true);


        if(tsBudgetTaluk == null){
            tsBudgetTalukResponse.setError(true);
            tsBudgetTalukResponse.setError_description("Invalid id");
        }else{
            tsBudgetTalukResponse =  mapper.tsBudgetTalukEntityToObject(tsBudgetTaluk, TsBudgetTalukResponse.class);
            tsBudgetTalukResponse.setError(false);
        }
        log.info("Entity is ",tsBudgetTaluk);
        return tsBudgetTalukResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetTalukDTO> tsBudgetTalukList = tsBudgetTalukRepository.getTsBudgetTaluk(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(tsBudgetTalukList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", tsBudgetTalukList);
//            response = convertListToMapResponse(tsBudgetTalukList);
//            return response;
//        }
//    }



    @Transactional
    public TsBudgetTalukResponse getByIdJoin(int id){
        TsBudgetTalukResponse tsBudgetTalukResponse = new TsBudgetTalukResponse();
        TsBudgetTalukDTO tsBudgetTalukDTO = tsBudgetTalukRepository.getByTsBudgetTalukIdAndActive(id,true);
        if(tsBudgetTalukDTO == null){
            tsBudgetTalukResponse.setError(true);
            tsBudgetTalukResponse.setError_description("Invalid id");
        } else {
            tsBudgetTalukResponse = mapper.tsBudgetTalukDTOToObject(tsBudgetTalukDTO, TsBudgetTalukResponse.class);
            tsBudgetTalukResponse.setError(false);
        }
        log.info("Entity is ", tsBudgetTalukDTO);
        return tsBudgetTalukResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String, Object> getTsBudgetTalukByFinancialYearMasterId(Long financialYearMasterId) {
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetTalukDTO> tsBudgetTalukList = tsBudgetTalukRepository.getByFinancialYearMasterIdAndActive(financialYearMasterId, true);
//        if (tsBudgetTalukList.isEmpty()) {
////            throw new ValidationException("Invalid Id");
//            response.put("error", "Error");
//            response.put("error_description", "Invalid id");
//            response.put("success", false);
//            return response;
//        } else {
//            log.info("Entity is ", tsBudgetTalukList);
//            response = convertListDTOToMapResponse(tsBudgetTalukList);
//            response.put("success", true);
//            return response;
//
//        }
//
//    }

    private Map<String, Object> convertListToMapResponse(List<TsBudgetTaluk> tsBudgetTalukList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetTalukResponse> tsBudgetTalukResponses = tsBudgetTalukList.stream()
                .map(tsBudgetTaluk -> mapper.tsBudgetTalukEntityToObject(tsBudgetTaluk, TsBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTaluk", tsBudgetTalukResponses);
        response.put("totalItems", tsBudgetTalukList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsBudgetTalukDTO> tsBudgetTalukList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetTalukResponse> tsBudgetTalukResponses = tsBudgetTalukList.stream()
                .map(tsBudgetTaluk -> mapper.tsBudgetTalukDTOToObject(tsBudgetTaluk,TsBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTaluk",tsBudgetTalukResponses);
        response.put("totalItems", tsBudgetTalukList.size());
        return response;
    }


    @Transactional
    public TsBudgetTalukResponse updateTsBudgetTalukDetails(EditTsBudgetTalukRequest tsBudgetTalukRequest) {
        TsBudgetTalukResponse tsBudgetTalukResponse = new TsBudgetTalukResponse();
        List<TsBudgetTaluk> tsBudgetTalukList = tsBudgetTalukRepository.findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndScHeadAccountIdAndTsBudgetTalukIdIsNot(tsBudgetTalukRequest.getFinancialYearMasterId(),tsBudgetTalukRequest.getDistrictId(),tsBudgetTalukRequest.getTalukId(),tsBudgetTalukRequest.getScHeadAccountId(), tsBudgetTalukRequest.getTsBudgetTalukId());
        if (tsBudgetTalukList.size() > 0) {
            tsBudgetTalukResponse.setError(true);
            tsBudgetTalukResponse.setError_description("TsBudgetTaluk exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsBudgetTaluk tsBudgetTaluk = tsBudgetTalukRepository.findByTsBudgetTalukIdAndActiveIn(tsBudgetTalukRequest.getTsBudgetTalukId(), Set.of(true, false));
            if (Objects.nonNull(tsBudgetTaluk)) {
                tsBudgetTaluk.setFinancialYearMasterId(tsBudgetTalukRequest.getFinancialYearMasterId());
                tsBudgetTaluk.setScHeadAccountId(tsBudgetTalukRequest.getScHeadAccountId());
                tsBudgetTaluk.setDate(tsBudgetTalukRequest.getDate());
                tsBudgetTaluk.setBudgetAmount(tsBudgetTalukRequest.getBudgetAmount());
                tsBudgetTaluk.setDistrictId(tsBudgetTalukRequest.getDistrictId());
                tsBudgetTaluk.setTalukId(tsBudgetTalukRequest.getTalukId());


                tsBudgetTaluk.setActive(true);
                TsBudgetTaluk tsBudgetTaluk1 = tsBudgetTalukRepository.save(tsBudgetTaluk);
                tsBudgetTalukResponse = mapper.tsBudgetTalukEntityToObject(tsBudgetTaluk1, TsBudgetTalukResponse.class);
                tsBudgetTalukResponse.setError(false);
            } else {
                tsBudgetTalukResponse.setError(true);
                tsBudgetTalukResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsBudgetTalukResponse;
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
        Page<TsBudgetTalukDTO> tsBudgetTalukDTOS = tsBudgetTalukRepository.getSortedTsBudgetTaluk(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsBudgetTalukDTOS);
        return convertPageableDTOToMapResponse(tsBudgetTalukDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsBudgetTalukDTO> activeTsBudgetTaluk) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetTalukResponse> tsBudgetTalukResponses = activeTsBudgetTaluk.getContent().stream()
                .map(tsBudgetTaluk -> mapper.tsBudgetTalukDTOToObject(tsBudgetTaluk,TsBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTaluk",tsBudgetTalukResponses);
        response.put("currentPage", activeTsBudgetTaluk.getNumber());
        response.put("totalItems", activeTsBudgetTaluk.getTotalElements());
        response.put("totalPages", activeTsBudgetTaluk.getTotalPages());

        return response;
    }
}
