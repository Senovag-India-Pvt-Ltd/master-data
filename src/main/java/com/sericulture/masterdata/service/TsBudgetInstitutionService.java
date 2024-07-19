package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsBudgetInstitution.EditTsBudgetInstitutionRequest;
import com.sericulture.masterdata.model.api.tsBudgetInstitution.TsBudgetInstitutionRequest;
import com.sericulture.masterdata.model.api.tsBudgetInstitution.TsBudgetInstitutionResponse;
import com.sericulture.masterdata.model.dto.TsBudgetInstitutionDTO;
import com.sericulture.masterdata.model.entity.TsBudgetInstitution;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsBudgetInstitutionRepository;
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
public class TsBudgetInstitutionService {
    @Autowired
    TsBudgetInstitutionRepository tsBudgetInstitutionRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsBudgetInstitutionResponse insertTsBudgetInstitutionDetails(TsBudgetInstitutionRequest tsBudgetInstitutionRequest){
        TsBudgetInstitutionResponse tsBudgetInstitutionResponse = new TsBudgetInstitutionResponse();
        TsBudgetInstitution tsBudgetInstitution = mapper.tsBudgetInstitutionObjectToEntity(tsBudgetInstitutionRequest, TsBudgetInstitution.class);
        validator.validate(tsBudgetInstitution);
        List<TsBudgetInstitution> tsBudgetInstitutionList = tsBudgetInstitutionRepository.findByFinancialYearMasterIdAndScHeadAccountIdAndDistrictIdAndTalukId(tsBudgetInstitutionRequest.getFinancialYearMasterId(),tsBudgetInstitutionRequest.getScHeadAccountId(),tsBudgetInstitutionRequest.getDistrictId(),tsBudgetInstitutionRequest.getTalukId());
        if(!tsBudgetInstitutionList.isEmpty() && tsBudgetInstitutionList.stream().filter(TsBudgetInstitution::getActive).findAny().isPresent()){
            tsBudgetInstitutionResponse.setError(true);
            tsBudgetInstitutionResponse.setError_description("TsBudgetInstitution already exist");
        }
        else if(!tsBudgetInstitutionList.isEmpty() && tsBudgetInstitutionList.stream().filter(Predicate.not(TsBudgetInstitution::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsBudgetInstitutionResponse.setError(true);
            tsBudgetInstitutionResponse.setError_description("TsBudgetInstitution already exist with inactive state");
        }else {
            tsBudgetInstitutionResponse = mapper.tsBudgetInstitutionEntityToObject(tsBudgetInstitutionRepository.save(tsBudgetInstitution), TsBudgetInstitutionResponse.class);
            tsBudgetInstitutionResponse.setError(false);
        }
        return tsBudgetInstitutionResponse;
    }

    public Map<String,Object> getTsBudgetInstitutionDetails(final Pageable pageable){
        return convertToMapResponse(tsBudgetInstitutionRepository.findByActiveOrderByTsBudgetInstitutionIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsBudgetInstitutionRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsBudgetInstitution> activeTsBudgetInstitution) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetInstitutionResponse> tsBudgetInstitutionResponses= activeTsBudgetInstitution.getContent().stream()
                .map(tsBudgetInstitution -> mapper.tsBudgetInstitutionEntityToObject(tsBudgetInstitution, TsBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetInstitution",tsBudgetInstitutionResponses);
        response.put("currentPage", activeTsBudgetInstitution.getNumber());
        response.put("totalItems", activeTsBudgetInstitution.getTotalElements());
        response.put("totalPages", activeTsBudgetInstitution.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsBudgetInstitution> activeTsBudgetInstitution) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetInstitutionResponse> tsBudgetInstitutionResponses = activeTsBudgetInstitution.stream()
                .map(tsBudgetInstitution  -> mapper.tsBudgetInstitutionEntityToObject(tsBudgetInstitution, TsBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetInstitution",tsBudgetInstitutionResponses);
        return response;
    }

    public Map<String,Object> getPaginatedTsBudgetInstitutionWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsBudgetInstitutionRepository.getByActiveOrderByTsBudgetInstitutionIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsBudgetInstitutionDTO> activeTsBudgetInstitution) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetInstitutionResponse> tsBudgetInstitutionResponses= activeTsBudgetInstitution.getContent().stream()
                .map(tsBudgetInstitution -> mapper.tsBudgetInstitutionDTOToObject(tsBudgetInstitution,TsBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetInstitution",tsBudgetInstitutionResponses);
        response.put("currentPage", activeTsBudgetInstitution.getNumber());
        response.put("totalItems", activeTsBudgetInstitution.getTotalElements());
        response.put("totalPages", activeTsBudgetInstitution.getTotalPages());
        return response;
    }


    @Transactional
    public TsBudgetInstitutionResponse deleteTsBudgetInstitutionDetails(long id) {
        TsBudgetInstitutionResponse tsBudgetInstitutionResponse= new TsBudgetInstitutionResponse();
        TsBudgetInstitution tsBudgetInstitution = tsBudgetInstitutionRepository.findByTsBudgetInstitutionIdAndActive(id, true);
        if (Objects.nonNull(tsBudgetInstitution)) {
            tsBudgetInstitution.setActive(false);
            tsBudgetInstitutionResponse= mapper.tsBudgetInstitutionEntityToObject(tsBudgetInstitutionRepository.save(tsBudgetInstitution),TsBudgetInstitutionResponse.class);
            tsBudgetInstitutionResponse.setError(false);
        } else {
            tsBudgetInstitutionResponse.setError(true);
            tsBudgetInstitutionResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsBudgetInstitutionResponse;
    }

    public TsBudgetInstitutionResponse getById(int id){
        TsBudgetInstitutionResponse tsBudgetInstitutionResponse = new TsBudgetInstitutionResponse();
        TsBudgetInstitution tsBudgetInstitution = tsBudgetInstitutionRepository.findByTsBudgetInstitutionIdAndActive(id,true);


        if(tsBudgetInstitution == null){
            tsBudgetInstitutionResponse.setError(true);
            tsBudgetInstitutionResponse.setError_description("Invalid id");
        }else{
            tsBudgetInstitutionResponse =  mapper.tsBudgetInstitutionEntityToObject(tsBudgetInstitution, TsBudgetInstitutionResponse.class);
            tsBudgetInstitutionResponse.setError(false);
        }
        log.info("Entity is ",tsBudgetInstitution);
        return tsBudgetInstitutionResponse;
    }


    public TsBudgetInstitutionResponse getByIdJoin(int id){
        TsBudgetInstitutionResponse tsBudgetInstitutionResponse = new TsBudgetInstitutionResponse();
        TsBudgetInstitutionDTO tsBudgetInstitutionDTO = tsBudgetInstitutionRepository.getByTsBudgetInstitutionIdAndActive(id,true);
        if(tsBudgetInstitutionDTO == null){
            tsBudgetInstitutionResponse.setError(true);
            tsBudgetInstitutionResponse.setError_description("Invalid id");
        } else {
            tsBudgetInstitutionResponse = mapper.tsBudgetInstitutionDTOToObject(tsBudgetInstitutionDTO, TsBudgetInstitutionResponse.class);
            tsBudgetInstitutionResponse.setError(false);
        }
        log.info("Entity is ", tsBudgetInstitutionDTO);
        return tsBudgetInstitutionResponse;
    }

    private Map<String, Object> convertListToMapResponse(List<TsBudgetInstitution> tsBudgetInstitutionList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetInstitutionResponse> tsBudgetInstitutionResponses = tsBudgetInstitutionList.stream()
                .map(tsBudgetInstitution -> mapper.tsBudgetInstitutionEntityToObject(tsBudgetInstitution, TsBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetInstitution", tsBudgetInstitutionResponses);
        response.put("totalItems", tsBudgetInstitutionList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsBudgetInstitutionDTO> tsBudgetInstitutionList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetInstitutionResponse> tsBudgetInstitutionResponses = tsBudgetInstitutionList.stream()
                .map(tsBudgetInstitution -> mapper.tsBudgetInstitutionDTOToObject(tsBudgetInstitution,TsBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetInstitution",tsBudgetInstitutionResponses);
        response.put("totalItems", tsBudgetInstitutionList.size());
        return response;
    }


    @Transactional
    public TsBudgetInstitutionResponse updateTsBudgetInstitutionDetails(EditTsBudgetInstitutionRequest tsBudgetInstitutionRequest) {
        TsBudgetInstitutionResponse tsBudgetInstitutionResponse = new TsBudgetInstitutionResponse();
        List<TsBudgetInstitution> tsBudgetInstitutionList = tsBudgetInstitutionRepository.findByFinancialYearMasterIdAndScHeadAccountIdAndDistrictIdAndTalukIdAndTsBudgetInstitutionIdIsNot(tsBudgetInstitutionRequest.getFinancialYearMasterId(),tsBudgetInstitutionRequest.getScHeadAccountId(),tsBudgetInstitutionRequest.getDistrictId(),tsBudgetInstitutionRequest.getTalukId(),tsBudgetInstitutionRequest.getTsBudgetInstitutionId());
        if (tsBudgetInstitutionList.size() > 0) {
            tsBudgetInstitutionResponse.setError(true);
            tsBudgetInstitutionResponse.setError_description("TsBudgetInstitution exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsBudgetInstitution tsBudgetInstitution = tsBudgetInstitutionRepository.findByTsBudgetInstitutionIdAndActiveIn(tsBudgetInstitutionRequest.getTsBudgetInstitutionId(), Set.of(true, false));
            if (Objects.nonNull(tsBudgetInstitution)) {
                tsBudgetInstitution.setFinancialYearMasterId(tsBudgetInstitutionRequest.getFinancialYearMasterId());
                tsBudgetInstitution.setScHeadAccountId(tsBudgetInstitutionRequest.getScHeadAccountId());
                tsBudgetInstitution.setDistrictId(tsBudgetInstitutionRequest.getDistrictId());
                tsBudgetInstitution.setTalukId(tsBudgetInstitutionRequest.getTalukId());
                tsBudgetInstitution.setInstitutionType(tsBudgetInstitutionRequest.getInstitutionType());
                tsBudgetInstitution.setInstitutionId(tsBudgetInstitutionRequest.getInstitutionId());
                tsBudgetInstitution.setDate(tsBudgetInstitutionRequest.getDate());
                tsBudgetInstitution.setBudgetAmount(tsBudgetInstitutionRequest.getBudgetAmount());
                tsBudgetInstitution.setActive(true);
                TsBudgetInstitution tsBudgetInstitution1 = tsBudgetInstitutionRepository.save(tsBudgetInstitution);
                tsBudgetInstitutionResponse = mapper.tsBudgetInstitutionEntityToObject(tsBudgetInstitution1, TsBudgetInstitutionResponse.class);
                tsBudgetInstitutionResponse.setError(false);
            } else {
                tsBudgetInstitutionResponse.setError(true);
                tsBudgetInstitutionResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsBudgetInstitutionResponse;
    }

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
        Page<TsBudgetInstitutionDTO> tsBudgetInstitutionDTOS = tsBudgetInstitutionRepository.getSortedTsBudgetInstitution(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsBudgetInstitutionDTOS);
        return convertPageableDTOToMapResponse(tsBudgetInstitutionDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsBudgetInstitutionDTO> activeTsBudgetInstitution) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetInstitutionResponse> tsBudgetInstitutionResponses = activeTsBudgetInstitution.getContent().stream()
                .map(tsBudgetInstitution -> mapper.tsBudgetInstitutionDTOToObject(tsBudgetInstitution,TsBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetInstitution",tsBudgetInstitutionResponses);
        response.put("currentPage", activeTsBudgetInstitution.getNumber());
        response.put("totalItems", activeTsBudgetInstitution.getTotalElements());
        response.put("totalPages", activeTsBudgetInstitution.getTotalPages());

        return response;
    }
}
