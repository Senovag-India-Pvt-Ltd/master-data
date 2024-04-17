package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsBudgetDistrict.EditTsBudgetDistrictRequest;
import com.sericulture.masterdata.model.api.tsBudgetDistrict.TsBudgetDistrictRequest;
import com.sericulture.masterdata.model.api.tsBudgetDistrict.TsBudgetDistrictResponse;
import com.sericulture.masterdata.model.dto.TsBudgetDistrictDTO;
import com.sericulture.masterdata.model.entity.TsBudgetDistrict;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsBudgetDistrictRepository;
import com.sericulture.masterdata.repository.TsBudgetDistrictRepository;
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
public class TsBudgetDistrictService {
    @Autowired
    TsBudgetDistrictRepository tsBudgetDistrictRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsBudgetDistrictResponse insertTsBudgetDistrictDetails(TsBudgetDistrictRequest tsBudgetDistrictRequest){
        TsBudgetDistrictResponse tsBudgetDistrictResponse = new TsBudgetDistrictResponse();
        TsBudgetDistrict tsBudgetDistrict = mapper.tsBudgetDistrictObjectToEntity(tsBudgetDistrictRequest, TsBudgetDistrict.class);
        validator.validate(tsBudgetDistrict);
        List<TsBudgetDistrict> tsBudgetDistrictList = tsBudgetDistrictRepository.findByFinancialYearMasterIdAndDistrictIdAndScHeadAccountId(tsBudgetDistrictRequest.getFinancialYearMasterId(),tsBudgetDistrictRequest.getDistrictId(),tsBudgetDistrictRequest.getScHeadAccountId());
        if(!tsBudgetDistrictList.isEmpty() && tsBudgetDistrictList.stream().filter(TsBudgetDistrict::getActive).findAny().isPresent()){
            tsBudgetDistrictResponse.setError(true);
            tsBudgetDistrictResponse.setError_description("TsBudgetDistrict already exist");
        }
        else if(!tsBudgetDistrictList.isEmpty() && tsBudgetDistrictList.stream().filter(Predicate.not(TsBudgetDistrict::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsBudgetDistrictResponse.setError(true);
            tsBudgetDistrictResponse.setError_description("TsBudgetDistrict already exist with inactive state");
        }else {
            tsBudgetDistrictResponse = mapper.tsBudgetDistrictEntityToObject(tsBudgetDistrictRepository.save(tsBudgetDistrict), TsBudgetDistrictResponse.class);
            tsBudgetDistrictResponse.setError(false);
        }
        return tsBudgetDistrictResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getTsBudgetDistrictDetails(final Pageable pageable){
        return convertToMapResponse(tsBudgetDistrictRepository.findByActiveOrderByTsBudgetDistrictIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsBudgetDistrictRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsBudgetDistrict> activeTsBudgetDistrict) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetDistrictResponse> tsBudgetDistrictResponses= activeTsBudgetDistrict.getContent().stream()
                .map(tsBudgetDistrict -> mapper.tsBudgetDistrictEntityToObject(tsBudgetDistrict, TsBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetDistrict",tsBudgetDistrictResponses);
        response.put("currentPage", activeTsBudgetDistrict.getNumber());
        response.put("totalItems", activeTsBudgetDistrict.getTotalElements());
        response.put("totalPages", activeTsBudgetDistrict.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsBudgetDistrict> activeTsBudgetDistrict) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetDistrictResponse> tsBudgetDistrictResponses = activeTsBudgetDistrict.stream()
                .map(tsBudgetDistrict  -> mapper.tsBudgetDistrictEntityToObject(tsBudgetDistrict, TsBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetDistrict",tsBudgetDistrictResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTsBudgetDistrictWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsBudgetDistrictRepository.getByActiveOrderByTsBudgetDistrictIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsBudgetDistrictDTO> activeTsBudgetDistrict) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetDistrictResponse> tsBudgetDistrictResponses= activeTsBudgetDistrict.getContent().stream()
                .map(tsBudgetDistrict -> mapper.tsBudgetDistrictDTOToObject(tsBudgetDistrict,TsBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetDistrict",tsBudgetDistrictResponses);
        response.put("currentPage", activeTsBudgetDistrict.getNumber());
        response.put("totalItems", activeTsBudgetDistrict.getTotalElements());
        response.put("totalPages", activeTsBudgetDistrict.getTotalPages());
        return response;
    }


    @Transactional
    public TsBudgetDistrictResponse deleteTsBudgetDistrictDetails(long id) {
        TsBudgetDistrictResponse tsBudgetDistrictResponse= new TsBudgetDistrictResponse();
        TsBudgetDistrict tsBudgetDistrict = tsBudgetDistrictRepository.findByTsBudgetDistrictIdAndActive(id, true);
        if (Objects.nonNull(tsBudgetDistrict)) {
            tsBudgetDistrict.setActive(false);
            tsBudgetDistrictResponse= mapper.tsBudgetDistrictEntityToObject(tsBudgetDistrictRepository.save(tsBudgetDistrict),TsBudgetDistrictResponse.class);
            tsBudgetDistrictResponse.setError(false);
        } else {
            tsBudgetDistrictResponse.setError(true);
            tsBudgetDistrictResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsBudgetDistrictResponse;
    }

    @Transactional
    public TsBudgetDistrictResponse getById(int id){
        TsBudgetDistrictResponse tsBudgetDistrictResponse = new TsBudgetDistrictResponse();
        TsBudgetDistrict tsBudgetDistrict = tsBudgetDistrictRepository.findByTsBudgetDistrictIdAndActive(id,true);


        if(tsBudgetDistrict == null){
            tsBudgetDistrictResponse.setError(true);
            tsBudgetDistrictResponse.setError_description("Invalid id");
        }else{
            tsBudgetDistrictResponse =  mapper.tsBudgetDistrictEntityToObject(tsBudgetDistrict, TsBudgetDistrictResponse.class);
            tsBudgetDistrictResponse.setError(false);
        }
        log.info("Entity is ",tsBudgetDistrict);
        return tsBudgetDistrictResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetDistrictDTO> tsBudgetDistrictList = tsBudgetDistrictRepository.getTsBudgetDistrict(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(tsBudgetDistrictList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", tsBudgetDistrictList);
//            response = convertListToMapResponse(tsBudgetDistrictList);
//            return response;
//        }
//    }



    @Transactional
    public TsBudgetDistrictResponse getByIdJoin(int id){
        TsBudgetDistrictResponse tsBudgetDistrictResponse = new TsBudgetDistrictResponse();
        TsBudgetDistrictDTO tsBudgetDistrictDTO = tsBudgetDistrictRepository.getByTsBudgetDistrictIdAndActive(id,true);
        if(tsBudgetDistrictDTO == null){
            tsBudgetDistrictResponse.setError(true);
            tsBudgetDistrictResponse.setError_description("Invalid id");
        } else {
            tsBudgetDistrictResponse = mapper.tsBudgetDistrictDTOToObject(tsBudgetDistrictDTO, TsBudgetDistrictResponse.class);
            tsBudgetDistrictResponse.setError(false);
        }
        log.info("Entity is ", tsBudgetDistrictDTO);
        return tsBudgetDistrictResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String, Object> getTsBudgetDistrictByFinancialYearMasterId(Long financialYearMasterId) {
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetDistrictDTO> tsBudgetDistrictList = tsBudgetDistrictRepository.getByFinancialYearMasterIdAndActive(financialYearMasterId, true);
//        if (tsBudgetDistrictList.isEmpty()) {
////            throw new ValidationException("Invalid Id");
//            response.put("error", "Error");
//            response.put("error_description", "Invalid id");
//            response.put("success", false);
//            return response;
//        } else {
//            log.info("Entity is ", tsBudgetDistrictList);
//            response = convertListDTOToMapResponse(tsBudgetDistrictList);
//            response.put("success", true);
//            return response;
//
//        }
//
//    }

    private Map<String, Object> convertListToMapResponse(List<TsBudgetDistrict> tsBudgetDistrictList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetDistrictResponse> tsBudgetDistrictResponses = tsBudgetDistrictList.stream()
                .map(tsBudgetDistrict -> mapper.tsBudgetDistrictEntityToObject(tsBudgetDistrict, TsBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetDistrict", tsBudgetDistrictResponses);
        response.put("totalItems", tsBudgetDistrictList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsBudgetDistrictDTO> tsBudgetDistrictList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetDistrictResponse> tsBudgetDistrictResponses = tsBudgetDistrictList.stream()
                .map(tsBudgetDistrict -> mapper.tsBudgetDistrictDTOToObject(tsBudgetDistrict,TsBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetDistrict",tsBudgetDistrictResponses);
        response.put("totalItems", tsBudgetDistrictList.size());
        return response;
    }


    @Transactional
    public TsBudgetDistrictResponse updateTsBudgetDistrictDetails(EditTsBudgetDistrictRequest tsBudgetDistrictRequest) {
        TsBudgetDistrictResponse tsBudgetDistrictResponse = new TsBudgetDistrictResponse();
        List<TsBudgetDistrict> tsBudgetDistrictList = tsBudgetDistrictRepository.findByFinancialYearMasterIdAndDistrictIdAndScHeadAccountIdAndTsBudgetDistrictIdIsNot(tsBudgetDistrictRequest.getFinancialYearMasterId(),tsBudgetDistrictRequest.getDistrictId(),tsBudgetDistrictRequest.getScHeadAccountId(), tsBudgetDistrictRequest.getTsBudgetDistrictId());
        if (tsBudgetDistrictList.size() > 0) {
            tsBudgetDistrictResponse.setError(true);
            tsBudgetDistrictResponse.setError_description("TsBudgetDistrict exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsBudgetDistrict tsBudgetDistrict = tsBudgetDistrictRepository.findByTsBudgetDistrictIdAndActiveIn(tsBudgetDistrictRequest.getTsBudgetDistrictId(), Set.of(true, false));
            if (Objects.nonNull(tsBudgetDistrict)) {
                tsBudgetDistrict.setFinancialYearMasterId(tsBudgetDistrictRequest.getFinancialYearMasterId());
                tsBudgetDistrict.setScHeadAccountId(tsBudgetDistrictRequest.getScHeadAccountId());
                tsBudgetDistrict.setDate(tsBudgetDistrictRequest.getDate());
                tsBudgetDistrict.setBudgetAmount(tsBudgetDistrictRequest.getBudgetAmount());
                tsBudgetDistrict.setDistrictId(tsBudgetDistrictRequest.getDistrictId());


                tsBudgetDistrict.setActive(true);
                TsBudgetDistrict tsBudgetDistrict1 = tsBudgetDistrictRepository.save(tsBudgetDistrict);
                tsBudgetDistrictResponse = mapper.tsBudgetDistrictEntityToObject(tsBudgetDistrict1, TsBudgetDistrictResponse.class);
                tsBudgetDistrictResponse.setError(false);
            } else {
                tsBudgetDistrictResponse.setError(true);
                tsBudgetDistrictResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsBudgetDistrictResponse;
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
        Page<TsBudgetDistrictDTO> tsBudgetDistrictDTOS = tsBudgetDistrictRepository.getSortedTsBudgetDistrict(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsBudgetDistrictDTOS);
        return convertPageableDTOToMapResponse(tsBudgetDistrictDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsBudgetDistrictDTO> activeTsBudgetDistrict) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetDistrictResponse> tsBudgetDistrictResponses = activeTsBudgetDistrict.getContent().stream()
                .map(tsBudgetDistrict -> mapper.tsBudgetDistrictDTOToObject(tsBudgetDistrict,TsBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetDistrict",tsBudgetDistrictResponses);
        response.put("currentPage", activeTsBudgetDistrict.getNumber());
        response.put("totalItems", activeTsBudgetDistrict.getTotalElements());
        response.put("totalPages", activeTsBudgetDistrict.getTotalPages());

        return response;
    }
    
}
