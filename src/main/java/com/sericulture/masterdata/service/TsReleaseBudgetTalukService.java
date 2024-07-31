package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetTaluk.EditTsReleaseBudgetTalukRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetTaluk.TsReleaseBudgetTalukRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetTaluk.TsReleaseBudgetTalukResponse;
import com.sericulture.masterdata.model.dto.TsReleaseBudgetTalukDTO;
import com.sericulture.masterdata.model.entity.TsReleaseBudgetTaluk;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsReleaseBudgetTalukRepository;
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
public class TsReleaseBudgetTalukService {
    @Autowired
    TsReleaseBudgetTalukRepository tsReleaseBudgetTalukRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsReleaseBudgetTalukResponse insertTsReleaseBudgetTalukDetails(TsReleaseBudgetTalukRequest tsReleaseBudgetTalukRequest){
        TsReleaseBudgetTalukResponse tsReleaseBudgetTalukResponse = new TsReleaseBudgetTalukResponse();
        TsReleaseBudgetTaluk tsReleaseBudgetTaluk = mapper.tsReleaseBudgetTalukObjectToEntity(tsReleaseBudgetTalukRequest, TsReleaseBudgetTaluk.class);
        validator.validate(tsReleaseBudgetTaluk);
        List<TsReleaseBudgetTaluk> tsReleaseBudgetTalukList = tsReleaseBudgetTalukRepository.findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndScHeadAccountId(tsReleaseBudgetTalukRequest.getFinancialYearMasterId(),tsReleaseBudgetTalukRequest.getDistrictId(),tsReleaseBudgetTalukRequest.getTalukId(),tsReleaseBudgetTalukRequest.getScHeadAccountId());
        if(!tsReleaseBudgetTalukList.isEmpty() && tsReleaseBudgetTalukList.stream().filter(TsReleaseBudgetTaluk::getActive).findAny().isPresent()){
            tsReleaseBudgetTalukResponse.setError(true);
            tsReleaseBudgetTalukResponse.setError_description("TsReleaseBudgetTaluk already exist");
        }
        else if(!tsReleaseBudgetTalukList.isEmpty() && tsReleaseBudgetTalukList.stream().filter(Predicate.not(TsReleaseBudgetTaluk::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsReleaseBudgetTalukResponse.setError(true);
            tsReleaseBudgetTalukResponse.setError_description("TsReleaseBudgetTaluk already exist with inactive state");
        }else {
            tsReleaseBudgetTalukResponse = mapper.tsReleaseBudgetTalukEntityToObject(tsReleaseBudgetTalukRepository.save(tsReleaseBudgetTaluk), TsReleaseBudgetTalukResponse.class);
            tsReleaseBudgetTalukResponse.setError(false);
        }
        return tsReleaseBudgetTalukResponse;
    }

    public Map<String,Object> getTsReleaseBudgetTalukDetails(final Pageable pageable){
        return convertToMapResponse(tsReleaseBudgetTalukRepository.findByActiveOrderByTsReleaseBudgetTalukIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsReleaseBudgetTalukRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsReleaseBudgetTaluk> activeTsReleaseBudgetTaluk) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetTalukResponse> tsReleaseBudgetTalukResponses= activeTsReleaseBudgetTaluk.getContent().stream()
                .map(tsReleaseBudgetTaluk -> mapper.tsReleaseBudgetTalukEntityToObject(tsReleaseBudgetTaluk, TsReleaseBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetTaluk",tsReleaseBudgetTalukResponses);
        response.put("currentPage", activeTsReleaseBudgetTaluk.getNumber());
        response.put("totalItems", activeTsReleaseBudgetTaluk.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetTaluk.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsReleaseBudgetTaluk> activeTsReleaseBudgetTaluk) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetTalukResponse> tsReleaseBudgetTalukResponses = activeTsReleaseBudgetTaluk.stream()
                .map(tsReleaseBudgetTaluk  -> mapper.tsReleaseBudgetTalukEntityToObject(tsReleaseBudgetTaluk, TsReleaseBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetTaluk",tsReleaseBudgetTalukResponses);
        return response;
    }

    public Map<String,Object> getPaginatedTsReleaseBudgetTalukWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsReleaseBudgetTalukRepository.getByActiveOrderByTsReleaseBudgetTalukIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsReleaseBudgetTalukDTO> activeTsReleaseBudgetTaluk) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetTalukResponse> tsReleaseBudgetTalukResponses= activeTsReleaseBudgetTaluk.getContent().stream()
                .map(tsReleaseBudgetTaluk -> mapper.tsReleaseBudgetTalukDTOToObject(tsReleaseBudgetTaluk,TsReleaseBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetTaluk",tsReleaseBudgetTalukResponses);
        response.put("currentPage", activeTsReleaseBudgetTaluk.getNumber());
        response.put("totalItems", activeTsReleaseBudgetTaluk.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetTaluk.getTotalPages());
        return response;
    }


    @Transactional
    public TsReleaseBudgetTalukResponse deleteTsReleaseBudgetTalukDetails(long id) {
        TsReleaseBudgetTalukResponse tsReleaseBudgetTalukResponse= new TsReleaseBudgetTalukResponse();
        TsReleaseBudgetTaluk tsReleaseBudgetTaluk = tsReleaseBudgetTalukRepository.findByTsReleaseBudgetTalukIdAndActive(id, true);
        if (Objects.nonNull(tsReleaseBudgetTaluk)) {
            tsReleaseBudgetTaluk.setActive(false);
            tsReleaseBudgetTalukResponse= mapper.tsReleaseBudgetTalukEntityToObject(tsReleaseBudgetTalukRepository.save(tsReleaseBudgetTaluk),TsReleaseBudgetTalukResponse.class);
            tsReleaseBudgetTalukResponse.setError(false);
        } else {
            tsReleaseBudgetTalukResponse.setError(true);
            tsReleaseBudgetTalukResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsReleaseBudgetTalukResponse;
    }

    public TsReleaseBudgetTalukResponse getById(int id){
        TsReleaseBudgetTalukResponse tsReleaseBudgetTalukResponse = new TsReleaseBudgetTalukResponse();
        TsReleaseBudgetTaluk tsReleaseBudgetTaluk = tsReleaseBudgetTalukRepository.findByTsReleaseBudgetTalukIdAndActive(id,true);


        if(tsReleaseBudgetTaluk == null){
            tsReleaseBudgetTalukResponse.setError(true);
            tsReleaseBudgetTalukResponse.setError_description("Invalid id");
        }else{
            tsReleaseBudgetTalukResponse =  mapper.tsReleaseBudgetTalukEntityToObject(tsReleaseBudgetTaluk, TsReleaseBudgetTalukResponse.class);
            tsReleaseBudgetTalukResponse.setError(false);
        }
        log.info("Entity is ",tsReleaseBudgetTaluk);
        return tsReleaseBudgetTalukResponse;
    }


    public TsReleaseBudgetTalukResponse getByIdJoin(int id){
        TsReleaseBudgetTalukResponse tsReleaseBudgetTalukResponse = new TsReleaseBudgetTalukResponse();
        TsReleaseBudgetTalukDTO tsReleaseBudgetTalukDTO = tsReleaseBudgetTalukRepository.getByTsReleaseBudgetTalukIdAndActive(id,true);
        if(tsReleaseBudgetTalukDTO == null){
            tsReleaseBudgetTalukResponse.setError(true);
            tsReleaseBudgetTalukResponse.setError_description("Invalid id");
        } else {
            tsReleaseBudgetTalukResponse = mapper.tsReleaseBudgetTalukDTOToObject(tsReleaseBudgetTalukDTO, TsReleaseBudgetTalukResponse.class);
            tsReleaseBudgetTalukResponse.setError(false);
        }
        log.info("Entity is ", tsReleaseBudgetTalukDTO);
        return tsReleaseBudgetTalukResponse;
    }


    private Map<String, Object> convertListToMapResponse(List<TsReleaseBudgetTaluk> tsReleaseBudgetTalukList) {
        Map<String, Object> response = new HashMap<>();
        List<TsReleaseBudgetTalukResponse> tsReleaseBudgetTalukResponses = tsReleaseBudgetTalukList.stream()
                .map(tsReleaseBudgetTaluk -> mapper.tsReleaseBudgetTalukEntityToObject(tsReleaseBudgetTaluk, TsReleaseBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetTaluk", tsReleaseBudgetTalukResponses);
        response.put("totalItems", tsReleaseBudgetTalukList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsReleaseBudgetTalukDTO> tsReleaseBudgetTalukList) {
        Map<String, Object> response = new HashMap<>();
        List<TsReleaseBudgetTalukResponse> tsReleaseBudgetTalukResponses = tsReleaseBudgetTalukList.stream()
                .map(tsReleaseBudgetTaluk -> mapper.tsReleaseBudgetTalukDTOToObject(tsReleaseBudgetTaluk,TsReleaseBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetTaluk",tsReleaseBudgetTalukResponses);
        response.put("totalItems", tsReleaseBudgetTalukList.size());
        return response;
    }


    @Transactional
    public TsReleaseBudgetTalukResponse updateTsReleaseBudgetTalukDetails(EditTsReleaseBudgetTalukRequest tsReleaseBudgetTalukRequest) {
        TsReleaseBudgetTalukResponse tsReleaseBudgetTalukResponse = new TsReleaseBudgetTalukResponse();
        List<TsReleaseBudgetTaluk> tsReleaseBudgetTalukList = tsReleaseBudgetTalukRepository.findByFinancialYearMasterIdAndDistrictIdAndTalukIdAndScHeadAccountIdAndTsReleaseBudgetTalukIdIsNot(tsReleaseBudgetTalukRequest.getFinancialYearMasterId(),tsReleaseBudgetTalukRequest.getDistrictId(),tsReleaseBudgetTalukRequest.getTalukId(),tsReleaseBudgetTalukRequest.getScHeadAccountId(), tsReleaseBudgetTalukRequest.getTsReleaseBudgetTalukId());
        if (tsReleaseBudgetTalukList.size() > 0) {
            tsReleaseBudgetTalukResponse.setError(true);
            tsReleaseBudgetTalukResponse.setError_description("TsReleaseBudgetTaluk exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsReleaseBudgetTaluk tsReleaseBudgetTaluk = tsReleaseBudgetTalukRepository.findByTsReleaseBudgetTalukIdAndActiveIn(tsReleaseBudgetTalukRequest.getTsReleaseBudgetTalukId(), Set.of(true, false));
            if (Objects.nonNull(tsReleaseBudgetTaluk)) {
                tsReleaseBudgetTaluk.setFinancialYearMasterId(tsReleaseBudgetTalukRequest.getFinancialYearMasterId());
                tsReleaseBudgetTaluk.setScHeadAccountId(tsReleaseBudgetTalukRequest.getScHeadAccountId());
                tsReleaseBudgetTaluk.setDistrictId(tsReleaseBudgetTalukRequest.getDistrictId());
                tsReleaseBudgetTaluk.setTalukId(tsReleaseBudgetTalukRequest.getTalukId());
                tsReleaseBudgetTaluk.setDate(tsReleaseBudgetTalukRequest.getDate());
                tsReleaseBudgetTaluk.setBudgetAmount(tsReleaseBudgetTalukRequest.getBudgetAmount());



                tsReleaseBudgetTaluk.setActive(true);
                TsReleaseBudgetTaluk tsReleaseBudgetTaluk1 = tsReleaseBudgetTalukRepository.save(tsReleaseBudgetTaluk);
                tsReleaseBudgetTalukResponse = mapper.tsReleaseBudgetTalukEntityToObject(tsReleaseBudgetTaluk1, TsReleaseBudgetTalukResponse.class);
                tsReleaseBudgetTalukResponse.setError(false);
            } else {
                tsReleaseBudgetTalukResponse.setError(true);
                tsReleaseBudgetTalukResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsReleaseBudgetTalukResponse;
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
        Page<TsReleaseBudgetTalukDTO> tsReleaseBudgetTalukDTOS = tsReleaseBudgetTalukRepository.getSortedTsReleaseBudgetTaluk(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsReleaseBudgetTalukDTOS);
        return convertPageableDTOToMapResponse(tsReleaseBudgetTalukDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsReleaseBudgetTalukDTO> activeTsReleaseBudgetTaluk) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetTalukResponse> tsReleaseBudgetTalukResponses = activeTsReleaseBudgetTaluk.getContent().stream()
                .map(tsReleaseBudgetTaluk -> mapper.tsReleaseBudgetTalukDTOToObject(tsReleaseBudgetTaluk,TsReleaseBudgetTalukResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetTaluk",tsReleaseBudgetTalukResponses);
        response.put("currentPage", activeTsReleaseBudgetTaluk.getNumber());
        response.put("totalItems", activeTsReleaseBudgetTaluk.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetTaluk.getTotalPages());

        return response;
    }
}
