package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetInstitution.EditTsReleaseBudgetInstitutionRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetInstitution.TsReleaseBudgetInstitutionRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetInstitution.TsReleaseBudgetInstitutionResponse;
import com.sericulture.masterdata.model.dto.TsReleaseBudgetInstitutionDTO;
import com.sericulture.masterdata.model.entity.TsReleaseBudgetInstitution;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsReleaseBudgetInstitutionRepository;
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
public class TsReleaseBudgetInstitutionService {
    @Autowired
    TsReleaseBudgetInstitutionRepository tsReleaseBudgetInstitutionRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsReleaseBudgetInstitutionResponse insertTsReleaseBudgetInstitutionDetails(TsReleaseBudgetInstitutionRequest tsReleaseBudgetInstitutionRequest){
        TsReleaseBudgetInstitutionResponse tsReleaseBudgetInstitutionResponse = new TsReleaseBudgetInstitutionResponse();
        TsReleaseBudgetInstitution tsReleaseBudgetInstitution = mapper.tsReleaseBudgetInstitutionObjectToEntity(tsReleaseBudgetInstitutionRequest, TsReleaseBudgetInstitution.class);
        validator.validate(tsReleaseBudgetInstitution);
        List<TsReleaseBudgetInstitution> tsReleaseBudgetInstitutionList = tsReleaseBudgetInstitutionRepository.findByFinancialYearMasterIdAndScHeadAccountIdAndDistrictIdAndTalukId(tsReleaseBudgetInstitutionRequest.getFinancialYearMasterId(),tsReleaseBudgetInstitutionRequest.getScHeadAccountId(),tsReleaseBudgetInstitutionRequest.getDistrictId(),tsReleaseBudgetInstitutionRequest.getTalukId());
        if(!tsReleaseBudgetInstitutionList.isEmpty() && tsReleaseBudgetInstitutionList.stream().filter(TsReleaseBudgetInstitution::getActive).findAny().isPresent()){
            tsReleaseBudgetInstitutionResponse.setError(true);
            tsReleaseBudgetInstitutionResponse.setError_description("TsReleaseBudgetInstitution already exist");
        }
        else if(!tsReleaseBudgetInstitutionList.isEmpty() && tsReleaseBudgetInstitutionList.stream().filter(Predicate.not(TsReleaseBudgetInstitution::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsReleaseBudgetInstitutionResponse.setError(true);
            tsReleaseBudgetInstitutionResponse.setError_description("TsReleaseBudgetInstitution already exist with inactive state");
        }else {
            tsReleaseBudgetInstitutionResponse = mapper.tsReleaseBudgetInstitutionEntityToObject(tsReleaseBudgetInstitutionRepository.save(tsReleaseBudgetInstitution), TsReleaseBudgetInstitutionResponse.class);
            tsReleaseBudgetInstitutionResponse.setError(false);
        }
        return tsReleaseBudgetInstitutionResponse;
    }

    public Map<String,Object> getTsReleaseBudgetInstitutionDetails(final Pageable pageable){
        return convertToMapResponse(tsReleaseBudgetInstitutionRepository.findByActiveOrderByTsReleaseBudgetInstitutionIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsReleaseBudgetInstitutionRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsReleaseBudgetInstitution> activeTsReleaseBudgetInstitution) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetInstitutionResponse> tsReleaseBudgetInstitutionResponses= activeTsReleaseBudgetInstitution.getContent().stream()
                .map(tsReleaseBudgetInstitution -> mapper.tsReleaseBudgetInstitutionEntityToObject(tsReleaseBudgetInstitution, TsReleaseBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetInstitution",tsReleaseBudgetInstitutionResponses);
        response.put("currentPage", activeTsReleaseBudgetInstitution.getNumber());
        response.put("totalItems", activeTsReleaseBudgetInstitution.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetInstitution.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsReleaseBudgetInstitution> activeTsReleaseBudgetInstitution) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetInstitutionResponse> tsReleaseBudgetInstitutionResponses = activeTsReleaseBudgetInstitution.stream()
                .map(tsReleaseBudgetInstitution  -> mapper.tsReleaseBudgetInstitutionEntityToObject(tsReleaseBudgetInstitution, TsReleaseBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetInstitution",tsReleaseBudgetInstitutionResponses);
        return response;
    }

    public Map<String,Object> getPaginatedTsReleaseBudgetInstitutionWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsReleaseBudgetInstitutionRepository.getByActiveOrderByTsReleaseBudgetInstitutionIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsReleaseBudgetInstitutionDTO> activeTsReleaseBudgetInstitution) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetInstitutionResponse> tsReleaseBudgetInstitutionResponses= activeTsReleaseBudgetInstitution.getContent().stream()
                .map(tsReleaseBudgetInstitution -> mapper.tsReleaseBudgetInstitutionDTOToObject(tsReleaseBudgetInstitution,TsReleaseBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetInstitution",tsReleaseBudgetInstitutionResponses);
        response.put("currentPage", activeTsReleaseBudgetInstitution.getNumber());
        response.put("totalItems", activeTsReleaseBudgetInstitution.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetInstitution.getTotalPages());
        return response;
    }


    @Transactional
    public TsReleaseBudgetInstitutionResponse deleteTsReleaseBudgetInstitutionDetails(long id) {
        TsReleaseBudgetInstitutionResponse tsReleaseBudgetInstitutionResponse= new TsReleaseBudgetInstitutionResponse();
        TsReleaseBudgetInstitution tsReleaseBudgetInstitution = tsReleaseBudgetInstitutionRepository.findByTsReleaseBudgetInstitutionIdAndActive(id, true);
        if (Objects.nonNull(tsReleaseBudgetInstitution)) {
            tsReleaseBudgetInstitution.setActive(false);
            tsReleaseBudgetInstitutionResponse= mapper.tsReleaseBudgetInstitutionEntityToObject(tsReleaseBudgetInstitutionRepository.save(tsReleaseBudgetInstitution),TsReleaseBudgetInstitutionResponse.class);
            tsReleaseBudgetInstitutionResponse.setError(false);
        } else {
            tsReleaseBudgetInstitutionResponse.setError(true);
            tsReleaseBudgetInstitutionResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsReleaseBudgetInstitutionResponse;
    }

    public TsReleaseBudgetInstitutionResponse getById(int id){
        TsReleaseBudgetInstitutionResponse tsReleaseBudgetInstitutionResponse = new TsReleaseBudgetInstitutionResponse();
        TsReleaseBudgetInstitution tsReleaseBudgetInstitution = tsReleaseBudgetInstitutionRepository.findByTsReleaseBudgetInstitutionIdAndActive(id,true);


        if(tsReleaseBudgetInstitution == null){
            tsReleaseBudgetInstitutionResponse.setError(true);
            tsReleaseBudgetInstitutionResponse.setError_description("Invalid id");
        }else{
            tsReleaseBudgetInstitutionResponse =  mapper.tsReleaseBudgetInstitutionEntityToObject(tsReleaseBudgetInstitution, TsReleaseBudgetInstitutionResponse.class);
            tsReleaseBudgetInstitutionResponse.setError(false);
        }
        log.info("Entity is ",tsReleaseBudgetInstitution);
        return tsReleaseBudgetInstitutionResponse;
    }


    public TsReleaseBudgetInstitutionResponse getByIdJoin(int id){
        TsReleaseBudgetInstitutionResponse tsReleaseBudgetInstitutionResponse = new TsReleaseBudgetInstitutionResponse();
        TsReleaseBudgetInstitutionDTO tsReleaseBudgetInstitutionDTO = tsReleaseBudgetInstitutionRepository.getByTsReleaseBudgetInstitutionIdAndActive(id,true);
        if(tsReleaseBudgetInstitutionDTO == null){
            tsReleaseBudgetInstitutionResponse.setError(true);
            tsReleaseBudgetInstitutionResponse.setError_description("Invalid id");
        } else {
            tsReleaseBudgetInstitutionResponse = mapper.tsReleaseBudgetInstitutionDTOToObject(tsReleaseBudgetInstitutionDTO, TsReleaseBudgetInstitutionResponse.class);
            tsReleaseBudgetInstitutionResponse.setError(false);
        }
        log.info("Entity is ", tsReleaseBudgetInstitutionDTO);
        return tsReleaseBudgetInstitutionResponse;
    }


    private Map<String, Object> convertListToMapResponse(List<TsReleaseBudgetInstitution> tsReleaseBudgetInstitutionList) {
        Map<String, Object> response = new HashMap<>();
        List<TsReleaseBudgetInstitutionResponse> tsReleaseBudgetInstitutionResponses = tsReleaseBudgetInstitutionList.stream()
                .map(tsReleaseBudgetInstitution -> mapper.tsReleaseBudgetInstitutionEntityToObject(tsReleaseBudgetInstitution, TsReleaseBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetInstitution", tsReleaseBudgetInstitutionResponses);
        response.put("totalItems", tsReleaseBudgetInstitutionList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsReleaseBudgetInstitutionDTO> tsReleaseBudgetInstitutionList) {
        Map<String, Object> response = new HashMap<>();
        List<TsReleaseBudgetInstitutionResponse> tsReleaseBudgetInstitutionResponses = tsReleaseBudgetInstitutionList.stream()
                .map(tsReleaseBudgetInstitution -> mapper.tsReleaseBudgetInstitutionDTOToObject(tsReleaseBudgetInstitution,TsReleaseBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetInstitution",tsReleaseBudgetInstitutionResponses);
        response.put("totalItems", tsReleaseBudgetInstitutionList.size());
        return response;
    }


    @Transactional
    public TsReleaseBudgetInstitutionResponse updateTsReleaseBudgetInstitutionDetails(EditTsReleaseBudgetInstitutionRequest tsReleaseBudgetInstitutionRequest) {
        TsReleaseBudgetInstitutionResponse tsReleaseBudgetInstitutionResponse = new TsReleaseBudgetInstitutionResponse();
        List<TsReleaseBudgetInstitution> tsReleaseBudgetInstitutionList = tsReleaseBudgetInstitutionRepository.findByFinancialYearMasterIdAndScHeadAccountIdAndDistrictIdAndTalukIdAndTsReleaseBudgetInstitutionIdIsNot(tsReleaseBudgetInstitutionRequest.getFinancialYearMasterId(),tsReleaseBudgetInstitutionRequest.getScHeadAccountId(),tsReleaseBudgetInstitutionRequest.getDistrictId(),tsReleaseBudgetInstitutionRequest.getTalukId(),tsReleaseBudgetInstitutionRequest.getTsReleaseBudgetInstitutionId());
        if (tsReleaseBudgetInstitutionList.size() > 0) {
            tsReleaseBudgetInstitutionResponse.setError(true);
            tsReleaseBudgetInstitutionResponse.setError_description("TsReleaseBudgetInstitution exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsReleaseBudgetInstitution tsReleaseBudgetInstitution = tsReleaseBudgetInstitutionRepository.findByTsReleaseBudgetInstitutionIdAndActiveIn(tsReleaseBudgetInstitutionRequest.getTsReleaseBudgetInstitutionId(), Set.of(true, false));
            if (Objects.nonNull(tsReleaseBudgetInstitution)) {
                tsReleaseBudgetInstitution.setFinancialYearMasterId(tsReleaseBudgetInstitutionRequest.getFinancialYearMasterId());
                tsReleaseBudgetInstitution.setScHeadAccountId(tsReleaseBudgetInstitutionRequest.getScHeadAccountId());
                tsReleaseBudgetInstitution.setDistrictId(tsReleaseBudgetInstitutionRequest.getDistrictId());
                tsReleaseBudgetInstitution.setTalukId(tsReleaseBudgetInstitutionRequest.getTalukId());
                tsReleaseBudgetInstitution.setInstitutionType(tsReleaseBudgetInstitutionRequest.getInstitutionType());
                tsReleaseBudgetInstitution.setInstitutionId(tsReleaseBudgetInstitutionRequest.getInstitutionId());
                tsReleaseBudgetInstitution.setDate(tsReleaseBudgetInstitutionRequest.getDate());
                tsReleaseBudgetInstitution.setBudgetAmount(tsReleaseBudgetInstitutionRequest.getBudgetAmount());
                tsReleaseBudgetInstitution.setActive(true);
                TsReleaseBudgetInstitution tsReleaseBudgetInstitution1 = tsReleaseBudgetInstitutionRepository.save(tsReleaseBudgetInstitution);
                tsReleaseBudgetInstitutionResponse = mapper.tsReleaseBudgetInstitutionEntityToObject(tsReleaseBudgetInstitution1, TsReleaseBudgetInstitutionResponse.class);
                tsReleaseBudgetInstitutionResponse.setError(false);
            } else {
                tsReleaseBudgetInstitutionResponse.setError(true);
                tsReleaseBudgetInstitutionResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsReleaseBudgetInstitutionResponse;
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
        Page<TsReleaseBudgetInstitutionDTO> tsReleaseBudgetInstitutionDTOS = tsReleaseBudgetInstitutionRepository.getSortedTsReleaseBudgetInstitution(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsReleaseBudgetInstitutionDTOS);
        return convertPageableDTOToMapResponse(tsReleaseBudgetInstitutionDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsReleaseBudgetInstitutionDTO> activeTsReleaseBudgetInstitution) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetInstitutionResponse> tsReleaseBudgetInstitutionResponses = activeTsReleaseBudgetInstitution.getContent().stream()
                .map(tsReleaseBudgetInstitution -> mapper.tsReleaseBudgetInstitutionDTOToObject(tsReleaseBudgetInstitution,TsReleaseBudgetInstitutionResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetInstitution",tsReleaseBudgetInstitutionResponses);
        response.put("currentPage", activeTsReleaseBudgetInstitution.getNumber());
        response.put("totalItems", activeTsReleaseBudgetInstitution.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetInstitution.getTotalPages());

        return response;
    }
}
