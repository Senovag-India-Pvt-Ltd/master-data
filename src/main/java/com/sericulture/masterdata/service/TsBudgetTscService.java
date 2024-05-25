package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsBudgetTsc.EditTsBudgetTscRequest;
import com.sericulture.masterdata.model.api.tsBudgetTsc.TsBudgetTscRequest;
import com.sericulture.masterdata.model.api.tsBudgetTsc.TsBudgetTscResponse;
import com.sericulture.masterdata.model.dto.TsBudgetTscDTO;
import com.sericulture.masterdata.model.entity.TsBudgetTsc;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsBudgetTscRepository;
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
public class TsBudgetTscService {
    @Autowired
    TsBudgetTscRepository tsBudgetTscRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsBudgetTscResponse insertTsBudgetTscDetails(TsBudgetTscRequest tsBudgetTscRequest){
        TsBudgetTscResponse tsBudgetTscResponse = new TsBudgetTscResponse();
        TsBudgetTsc tsBudgetTsc = mapper.tsBudgetTscObjectToEntity(tsBudgetTscRequest, TsBudgetTsc.class);
        validator.validate(tsBudgetTsc);
        List<TsBudgetTsc> tsBudgetTscList = tsBudgetTscRepository.findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndTscMasterIdAndScHeadAccountId(tsBudgetTscRequest.getFinancialYearMasterId(),tsBudgetTscRequest.getDistrictId(),tsBudgetTscRequest.getTalukId(),tsBudgetTscRequest.getTscMasterId(),tsBudgetTscRequest.getScHeadAccountId());
        if(!tsBudgetTscList.isEmpty() && tsBudgetTscList.stream().filter(TsBudgetTsc::getActive).findAny().isPresent()){
            tsBudgetTscResponse.setError(true);
            tsBudgetTscResponse.setError_description("TsBudgetTsc already exist");
        }
        else if(!tsBudgetTscList.isEmpty() && tsBudgetTscList.stream().filter(Predicate.not(TsBudgetTsc::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsBudgetTscResponse.setError(true);
            tsBudgetTscResponse.setError_description("TsBudgetTsc already exist with inactive state");
        }else {
            tsBudgetTscResponse = mapper.tsBudgetTscEntityToObject(tsBudgetTscRepository.save(tsBudgetTsc), TsBudgetTscResponse.class);
            tsBudgetTscResponse.setError(false);
        }
        return tsBudgetTscResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getTsBudgetTscDetails(final Pageable pageable){
        return convertToMapResponse(tsBudgetTscRepository.findByActiveOrderByTsBudgetTscIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsBudgetTscRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsBudgetTsc> activeTsBudgetTsc) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetTscResponse> tsBudgetTscResponses= activeTsBudgetTsc.getContent().stream()
                .map(tsBudgetTsc -> mapper.tsBudgetTscEntityToObject(tsBudgetTsc, TsBudgetTscResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTsc",tsBudgetTscResponses);
        response.put("currentPage", activeTsBudgetTsc.getNumber());
        response.put("totalItems", activeTsBudgetTsc.getTotalElements());
        response.put("totalPages", activeTsBudgetTsc.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsBudgetTsc> activeTsBudgetTsc) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetTscResponse> tsBudgetTscResponses = activeTsBudgetTsc.stream()
                .map(tsBudgetTsc  -> mapper.tsBudgetTscEntityToObject(tsBudgetTsc, TsBudgetTscResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTsc",tsBudgetTscResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTsBudgetTscWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsBudgetTscRepository.getByActiveOrderByTsBudgetTscIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsBudgetTscDTO> activeTsBudgetTsc) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetTscResponse> tsBudgetTscResponses= activeTsBudgetTsc.getContent().stream()
                .map(tsBudgetTsc -> mapper.tsBudgetTscDTOToObject(tsBudgetTsc,TsBudgetTscResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTsc",tsBudgetTscResponses);
        response.put("currentPage", activeTsBudgetTsc.getNumber());
        response.put("totalItems", activeTsBudgetTsc.getTotalElements());
        response.put("totalPages", activeTsBudgetTsc.getTotalPages());
        return response;
    }


    @Transactional
    public TsBudgetTscResponse deleteTsBudgetTscDetails(long id) {
        TsBudgetTscResponse tsBudgetTscResponse= new TsBudgetTscResponse();
        TsBudgetTsc tsBudgetTsc = tsBudgetTscRepository.findByTsBudgetTscIdAndActive(id, true);
        if (Objects.nonNull(tsBudgetTsc)) {
            tsBudgetTsc.setActive(false);
            tsBudgetTscResponse= mapper.tsBudgetTscEntityToObject(tsBudgetTscRepository.save(tsBudgetTsc),TsBudgetTscResponse.class);
            tsBudgetTscResponse.setError(false);
        } else {
            tsBudgetTscResponse.setError(true);
            tsBudgetTscResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsBudgetTscResponse;
    }

    @Transactional
    public TsBudgetTscResponse getById(int id){
        TsBudgetTscResponse tsBudgetTscResponse = new TsBudgetTscResponse();
        TsBudgetTsc tsBudgetTsc = tsBudgetTscRepository.findByTsBudgetTscIdAndActive(id,true);


        if(tsBudgetTsc == null){
            tsBudgetTscResponse.setError(true);
            tsBudgetTscResponse.setError_description("Invalid id");
        }else{
            tsBudgetTscResponse =  mapper.tsBudgetTscEntityToObject(tsBudgetTsc, TsBudgetTscResponse.class);
            tsBudgetTscResponse.setError(false);
        }
        log.info("Entity is ",tsBudgetTsc);
        return tsBudgetTscResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetTscDTO> tsBudgetTscList = tsBudgetTscRepository.getTsBudgetTsc(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(tsBudgetTscList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", tsBudgetTscList);
//            response = convertListToMapResponse(tsBudgetTscList);
//            return response;
//        }
//    }



    @Transactional
    public TsBudgetTscResponse getByIdJoin(int id){
        TsBudgetTscResponse tsBudgetTscResponse = new TsBudgetTscResponse();
        TsBudgetTscDTO tsBudgetTscDTO = tsBudgetTscRepository.getByTsBudgetTscIdAndActive(id,true);
        if(tsBudgetTscDTO == null){
            tsBudgetTscResponse.setError(true);
            tsBudgetTscResponse.setError_description("Invalid id");
        } else {
            tsBudgetTscResponse = mapper.tsBudgetTscDTOToObject(tsBudgetTscDTO, TsBudgetTscResponse.class);
            tsBudgetTscResponse.setError(false);
        }
        log.info("Entity is ", tsBudgetTscDTO);
        return tsBudgetTscResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String, Object> getTsBudgetTscByFinancialYearMasterId(Long financialYearMasterId) {
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetTscDTO> tsBudgetTscList = tsBudgetTscRepository.getByFinancialYearMasterIdAndActive(financialYearMasterId, true);
//        if (tsBudgetTscList.isEmpty()) {
////            throw new ValidationException("Invalid Id");
//            response.put("error", "Error");
//            response.put("error_description", "Invalid id");
//            response.put("success", false);
//            return response;
//        } else {
//            log.info("Entity is ", tsBudgetTscList);
//            response = convertListDTOToMapResponse(tsBudgetTscList);
//            response.put("success", true);
//            return response;
//
//        }
//
//    }

    private Map<String, Object> convertListToMapResponse(List<TsBudgetTsc> tsBudgetTscList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetTscResponse> tsBudgetTscResponses = tsBudgetTscList.stream()
                .map(tsBudgetTsc -> mapper.tsBudgetTscEntityToObject(tsBudgetTsc, TsBudgetTscResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTsc", tsBudgetTscResponses);
        response.put("totalItems", tsBudgetTscList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsBudgetTscDTO> tsBudgetTscList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetTscResponse> tsBudgetTscResponses = tsBudgetTscList.stream()
                .map(tsBudgetTsc -> mapper.tsBudgetTscDTOToObject(tsBudgetTsc,TsBudgetTscResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTsc",tsBudgetTscResponses);
        response.put("totalItems", tsBudgetTscList.size());
        return response;
    }


    @Transactional
    public TsBudgetTscResponse updateTsBudgetTscDetails(EditTsBudgetTscRequest tsBudgetTscRequest) {
        TsBudgetTscResponse tsBudgetTscResponse = new TsBudgetTscResponse();
        List<TsBudgetTsc> tsBudgetTscList = tsBudgetTscRepository.findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndTscMasterIdAndScHeadAccountIdAndTsBudgetTscIdIsNot(tsBudgetTscRequest.getFinancialYearMasterId(),tsBudgetTscRequest.getDistrictId(),tsBudgetTscRequest.getTalukId(),tsBudgetTscRequest.getTscMasterId(),tsBudgetTscRequest.getScHeadAccountId(), tsBudgetTscRequest.getTsBudgetTscId());
        if (tsBudgetTscList.size() > 0) {
            tsBudgetTscResponse.setError(true);
            tsBudgetTscResponse.setError_description("TsBudgetTsc exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsBudgetTsc tsBudgetTsc = tsBudgetTscRepository.findByTsBudgetTscIdAndActiveIn(tsBudgetTscRequest.getTsBudgetTscId(), Set.of(true, false));
            if (Objects.nonNull(tsBudgetTsc)) {
                tsBudgetTsc.setFinancialYearMasterId(tsBudgetTscRequest.getFinancialYearMasterId());
                tsBudgetTsc.setScHeadAccountId(tsBudgetTscRequest.getScHeadAccountId());
                tsBudgetTsc.setDate(tsBudgetTscRequest.getDate());
                tsBudgetTsc.setBudgetAmount(tsBudgetTscRequest.getBudgetAmount());
                tsBudgetTsc.setDistrictId(tsBudgetTscRequest.getDistrictId());
                tsBudgetTsc.setTalukId(tsBudgetTscRequest.getTalukId());
                tsBudgetTsc.setTscMasterId(tsBudgetTscRequest.getTscMasterId());

                tsBudgetTsc.setActive(true);
                TsBudgetTsc tsBudgetTsc1 = tsBudgetTscRepository.save(tsBudgetTsc);
                tsBudgetTscResponse = mapper.tsBudgetTscEntityToObject(tsBudgetTsc1, TsBudgetTscResponse.class);
                tsBudgetTscResponse.setError(false);
            } else {
                tsBudgetTscResponse.setError(true);
                tsBudgetTscResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsBudgetTscResponse;
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
        Page<TsBudgetTscDTO> tsBudgetTscDTOS = tsBudgetTscRepository.getSortedTsBudgetTsc(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsBudgetTscDTOS);
        return convertPageableDTOToMapResponse(tsBudgetTscDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsBudgetTscDTO> activeTsBudgetTsc) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetTscResponse> tsBudgetTscResponses = activeTsBudgetTsc.getContent().stream()
                .map(tsBudgetTsc -> mapper.tsBudgetTscDTOToObject(tsBudgetTsc,TsBudgetTscResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetTsc",tsBudgetTscResponses);
        response.put("currentPage", activeTsBudgetTsc.getNumber());
        response.put("totalItems", activeTsBudgetTsc.getTotalElements());
        response.put("totalPages", activeTsBudgetTsc.getTotalPages());

        return response;
    }
}
