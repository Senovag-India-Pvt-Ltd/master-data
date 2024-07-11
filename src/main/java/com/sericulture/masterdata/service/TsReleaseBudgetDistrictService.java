package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetDistrict.EditTsReleaseBudgetDistrictRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetDistrict.TsReleaseBudgetDistrictRequest;
import com.sericulture.masterdata.model.api.tsReleaseBudgetDistrict.TsReleaseBudgetDistrictResponse;
import com.sericulture.masterdata.model.dto.TsReleaseBudgetDistrictDTO;
import com.sericulture.masterdata.model.entity.TsReleaseBudgetDistrict;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsReleaseBudgetDistrictRepository;
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
public class TsReleaseBudgetDistrictService {
    @Autowired
    TsReleaseBudgetDistrictRepository tsReleaseBudgetDistrictRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsReleaseBudgetDistrictResponse insertTsReleaseBudgetDistrictDetails(TsReleaseBudgetDistrictRequest tsReleaseBudgetDistrictRequest){
        TsReleaseBudgetDistrictResponse tsReleaseBudgetDistrictResponse = new TsReleaseBudgetDistrictResponse();
        TsReleaseBudgetDistrict tsReleaseBudgetDistrict = mapper.tsReleaseBudgetDistrictObjectToEntity(tsReleaseBudgetDistrictRequest, TsReleaseBudgetDistrict.class);
        validator.validate(tsReleaseBudgetDistrict);
        List<TsReleaseBudgetDistrict> tsReleaseBudgetDistrictList = tsReleaseBudgetDistrictRepository.findByFinancialYearMasterIdAndDistrictIdAndScHeadAccountId(tsReleaseBudgetDistrictRequest.getFinancialYearMasterId(),tsReleaseBudgetDistrictRequest.getDistrictId(),tsReleaseBudgetDistrictRequest.getScHeadAccountId());
        if(!tsReleaseBudgetDistrictList.isEmpty() && tsReleaseBudgetDistrictList.stream().filter(TsReleaseBudgetDistrict::getActive).findAny().isPresent()){
            tsReleaseBudgetDistrictResponse.setError(true);
            tsReleaseBudgetDistrictResponse.setError_description("TsReleaseBudgetDistrict already exist");
        }
        else if(!tsReleaseBudgetDistrictList.isEmpty() && tsReleaseBudgetDistrictList.stream().filter(Predicate.not(TsReleaseBudgetDistrict::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsReleaseBudgetDistrictResponse.setError(true);
            tsReleaseBudgetDistrictResponse.setError_description("TsReleaseBudgetDistrict already exist with inactive state");
        }else {
            tsReleaseBudgetDistrictResponse = mapper.tsReleaseBudgetDistrictEntityToObject(tsReleaseBudgetDistrictRepository.save(tsReleaseBudgetDistrict), TsReleaseBudgetDistrictResponse.class);
            tsReleaseBudgetDistrictResponse.setError(false);
        }
        return tsReleaseBudgetDistrictResponse;
    }

    public Map<String,Object> getTsReleaseBudgetDistrictDetails(final Pageable pageable){
        return convertToMapResponse(tsReleaseBudgetDistrictRepository.findByActiveOrderByTsReleaseBudgetDistrictIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsReleaseBudgetDistrictRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsReleaseBudgetDistrict> activeTsReleaseBudgetDistrict) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetDistrictResponse> tsReleaseBudgetDistrictResponses= activeTsReleaseBudgetDistrict.getContent().stream()
                .map(tsReleaseBudgetDistrict -> mapper.tsReleaseBudgetDistrictEntityToObject(tsReleaseBudgetDistrict, TsReleaseBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetDistrict",tsReleaseBudgetDistrictResponses);
        response.put("currentPage", activeTsReleaseBudgetDistrict.getNumber());
        response.put("totalItems", activeTsReleaseBudgetDistrict.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetDistrict.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsReleaseBudgetDistrict> activeTsReleaseBudgetDistrict) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetDistrictResponse> tsReleaseBudgetDistrictResponses = activeTsReleaseBudgetDistrict.stream()
                .map(tsReleaseBudgetDistrict  -> mapper.tsReleaseBudgetDistrictEntityToObject(tsReleaseBudgetDistrict, TsReleaseBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetDistrict",tsReleaseBudgetDistrictResponses);
        return response;
    }

    public Map<String,Object> getPaginatedTsReleaseBudgetDistrictWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsReleaseBudgetDistrictRepository.getByActiveOrderByTsReleaseBudgetDistrictIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsReleaseBudgetDistrictDTO> activeTsReleaseBudgetDistrict) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetDistrictResponse> tsReleaseBudgetDistrictResponses= activeTsReleaseBudgetDistrict.getContent().stream()
                .map(tsReleaseBudgetDistrict -> mapper.tsReleaseBudgetDistrictDTOToObject(tsReleaseBudgetDistrict,TsReleaseBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetDistrict",tsReleaseBudgetDistrictResponses);
        response.put("currentPage", activeTsReleaseBudgetDistrict.getNumber());
        response.put("totalItems", activeTsReleaseBudgetDistrict.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetDistrict.getTotalPages());
        return response;
    }


    @Transactional
    public TsReleaseBudgetDistrictResponse deleteTsReleaseBudgetDistrictDetails(long id) {
        TsReleaseBudgetDistrictResponse tsReleaseBudgetDistrictResponse= new TsReleaseBudgetDistrictResponse();
        TsReleaseBudgetDistrict tsReleaseBudgetDistrict = tsReleaseBudgetDistrictRepository.findByTsReleaseBudgetDistrictIdAndActive(id, true);
        if (Objects.nonNull(tsReleaseBudgetDistrict)) {
            tsReleaseBudgetDistrict.setActive(false);
            tsReleaseBudgetDistrictResponse= mapper.tsReleaseBudgetDistrictEntityToObject(tsReleaseBudgetDistrictRepository.save(tsReleaseBudgetDistrict),TsReleaseBudgetDistrictResponse.class);
            tsReleaseBudgetDistrictResponse.setError(false);
        } else {
            tsReleaseBudgetDistrictResponse.setError(true);
            tsReleaseBudgetDistrictResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsReleaseBudgetDistrictResponse;
    }

    public TsReleaseBudgetDistrictResponse getById(int id){
        TsReleaseBudgetDistrictResponse tsReleaseBudgetDistrictResponse = new TsReleaseBudgetDistrictResponse();
        TsReleaseBudgetDistrict tsReleaseBudgetDistrict = tsReleaseBudgetDistrictRepository.findByTsReleaseBudgetDistrictIdAndActive(id,true);


        if(tsReleaseBudgetDistrict == null){
            tsReleaseBudgetDistrictResponse.setError(true);
            tsReleaseBudgetDistrictResponse.setError_description("Invalid id");
        }else{
            tsReleaseBudgetDistrictResponse =  mapper.tsReleaseBudgetDistrictEntityToObject(tsReleaseBudgetDistrict, TsReleaseBudgetDistrictResponse.class);
            tsReleaseBudgetDistrictResponse.setError(false);
        }
        log.info("Entity is ",tsReleaseBudgetDistrict);
        return tsReleaseBudgetDistrictResponse;
    }


    public TsReleaseBudgetDistrictResponse getByIdJoin(int id){
        TsReleaseBudgetDistrictResponse tsReleaseBudgetDistrictResponse = new TsReleaseBudgetDistrictResponse();
        TsReleaseBudgetDistrictDTO tsReleaseBudgetDistrictDTO = tsReleaseBudgetDistrictRepository.getByTsReleaseBudgetDistrictIdAndActive(id,true);
        if(tsReleaseBudgetDistrictDTO == null){
            tsReleaseBudgetDistrictResponse.setError(true);
            tsReleaseBudgetDistrictResponse.setError_description("Invalid id");
        } else {
            tsReleaseBudgetDistrictResponse = mapper.tsReleaseBudgetDistrictDTOToObject(tsReleaseBudgetDistrictDTO, TsReleaseBudgetDistrictResponse.class);
            tsReleaseBudgetDistrictResponse.setError(false);
        }
        log.info("Entity is ", tsReleaseBudgetDistrictDTO);
        return tsReleaseBudgetDistrictResponse;
    }

    private Map<String, Object> convertListToMapResponse(List<TsReleaseBudgetDistrict> tsReleaseBudgetDistrictList) {
        Map<String, Object> response = new HashMap<>();
        List<TsReleaseBudgetDistrictResponse> tsReleaseBudgetDistrictResponses = tsReleaseBudgetDistrictList.stream()
                .map(tsReleaseBudgetDistrict -> mapper.tsReleaseBudgetDistrictEntityToObject(tsReleaseBudgetDistrict, TsReleaseBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetDistrict", tsReleaseBudgetDistrictResponses);
        response.put("totalItems", tsReleaseBudgetDistrictList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsReleaseBudgetDistrictDTO> tsReleaseBudgetDistrictList) {
        Map<String, Object> response = new HashMap<>();
        List<TsReleaseBudgetDistrictResponse> tsReleaseBudgetDistrictResponses = tsReleaseBudgetDistrictList.stream()
                .map(tsReleaseBudgetDistrict -> mapper.tsReleaseBudgetDistrictDTOToObject(tsReleaseBudgetDistrict,TsReleaseBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetDistrict",tsReleaseBudgetDistrictResponses);
        response.put("totalItems", tsReleaseBudgetDistrictList.size());
        return response;
    }


    @Transactional
    public TsReleaseBudgetDistrictResponse updateTsReleaseBudgetDistrictDetails(EditTsReleaseBudgetDistrictRequest tsReleaseBudgetDistrictRequest) {
        TsReleaseBudgetDistrictResponse tsReleaseBudgetDistrictResponse = new TsReleaseBudgetDistrictResponse();
        List<TsReleaseBudgetDistrict> tsReleaseBudgetDistrictList = tsReleaseBudgetDistrictRepository.findByFinancialYearMasterIdAndDistrictIdAndScHeadAccountIdAndTsReleaseBudgetDistrictIdIsNot(tsReleaseBudgetDistrictRequest.getFinancialYearMasterId(),tsReleaseBudgetDistrictRequest.getDistrictId(),tsReleaseBudgetDistrictRequest.getScHeadAccountId(), tsReleaseBudgetDistrictRequest.getTsReleaseBudgetDistrictId());
        if (tsReleaseBudgetDistrictList.size() > 0) {
            tsReleaseBudgetDistrictResponse.setError(true);
            tsReleaseBudgetDistrictResponse.setError_description("TsReleaseBudgetDistrict exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsReleaseBudgetDistrict tsReleaseBudgetDistrict = tsReleaseBudgetDistrictRepository.findByTsReleaseBudgetDistrictIdAndActiveIn(tsReleaseBudgetDistrictRequest.getTsReleaseBudgetDistrictId(), Set.of(true, false));
            if (Objects.nonNull(tsReleaseBudgetDistrict)) {
                tsReleaseBudgetDistrict.setFinancialYearMasterId(tsReleaseBudgetDistrictRequest.getFinancialYearMasterId());
                tsReleaseBudgetDistrict.setScHeadAccountId(tsReleaseBudgetDistrictRequest.getScHeadAccountId());
                tsReleaseBudgetDistrict.setDistrictId(tsReleaseBudgetDistrictRequest.getDistrictId());
                tsReleaseBudgetDistrict.setDate(tsReleaseBudgetDistrictRequest.getDate());
                tsReleaseBudgetDistrict.setBudgetAmount(tsReleaseBudgetDistrictRequest.getBudgetAmount());


                tsReleaseBudgetDistrict.setActive(true);
                TsReleaseBudgetDistrict tsReleaseBudgetDistrict1 = tsReleaseBudgetDistrictRepository.save(tsReleaseBudgetDistrict);
                tsReleaseBudgetDistrictResponse = mapper.tsReleaseBudgetDistrictEntityToObject(tsReleaseBudgetDistrict1, TsReleaseBudgetDistrictResponse.class);
                tsReleaseBudgetDistrictResponse.setError(false);
            } else {
                tsReleaseBudgetDistrictResponse.setError(true);
                tsReleaseBudgetDistrictResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsReleaseBudgetDistrictResponse;
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
        Page<TsReleaseBudgetDistrictDTO> tsReleaseBudgetDistrictDTOS = tsReleaseBudgetDistrictRepository.getSortedTsReleaseBudgetDistrict(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsReleaseBudgetDistrictDTOS);
        return convertPageableDTOToMapResponse(tsReleaseBudgetDistrictDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsReleaseBudgetDistrictDTO> activeTsReleaseBudgetDistrict) {
        Map<String, Object> response = new HashMap<>();

        List<TsReleaseBudgetDistrictResponse> tsReleaseBudgetDistrictResponses = activeTsReleaseBudgetDistrict.getContent().stream()
                .map(tsReleaseBudgetDistrict -> mapper.tsReleaseBudgetDistrictDTOToObject(tsReleaseBudgetDistrict,TsReleaseBudgetDistrictResponse.class)).collect(Collectors.toList());
        response.put("tsReleaseBudgetDistrict",tsReleaseBudgetDistrictResponses);
        response.put("currentPage", activeTsReleaseBudgetDistrict.getNumber());
        response.put("totalItems", activeTsReleaseBudgetDistrict.getTotalElements());
        response.put("totalPages", activeTsReleaseBudgetDistrict.getTotalPages());

        return response;
    }

}
