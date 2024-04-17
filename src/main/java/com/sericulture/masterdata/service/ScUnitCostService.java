package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;

import com.sericulture.masterdata.model.api.scHeadAccountCategory.ScHeadAccountCategoryResponse;
import com.sericulture.masterdata.model.api.scUnitCost.EditScUnitCostRequest;
import com.sericulture.masterdata.model.api.scUnitCost.ScUnitCostRequest;
import com.sericulture.masterdata.model.api.scUnitCost.ScUnitCostResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.dto.ScHeadAccountCategoryDTO;
import com.sericulture.masterdata.model.dto.ScUnitCostDTO;
import com.sericulture.masterdata.model.entity.ScHeadAccountCategory;
import com.sericulture.masterdata.model.entity.ScUnitCost;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScUnitCostRepository;
import com.sericulture.masterdata.repository.ScUnitCostRepository;
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
public class ScUnitCostService {
    @Autowired
    ScUnitCostRepository scUnitCostRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScUnitCostResponse insertScUnitCostDetails(ScUnitCostRequest scUnitCostRequest){
        ScUnitCostResponse scUnitCostResponse = new ScUnitCostResponse();
        ScUnitCost scUnitCost = mapper.scUnitCostObjectToEntity(scUnitCostRequest, ScUnitCost.class);
        validator.validate(scUnitCost);
        List<ScUnitCost> scUnitCostList = scUnitCostRepository.findByScHeadAccountIdAndScCategoryId(scUnitCostRequest.getScHeadAccountId(),scUnitCostRequest.getScCategoryId());
        if(!scUnitCostList.isEmpty() && scUnitCostList.stream().filter(ScUnitCost::getActive).findAny().isPresent()){
            scUnitCostResponse.setError(true);
            scUnitCostResponse.setError_description("Head Account already exist");
        }
        else if(!scUnitCostList.isEmpty() && scUnitCostList.stream().filter(Predicate.not(ScUnitCost::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scUnitCostResponse.setError(true);
            scUnitCostResponse.setError_description("Head Account already exist with inactive state");
        }else {
            scUnitCostResponse = mapper.scUnitCostEntityToObject(scUnitCostRepository.save(scUnitCost), ScUnitCostResponse.class);
            scUnitCostResponse.setError(false);
        }
        return scUnitCostResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getScUnitCostDetails(final Pageable pageable){
        return convertToMapResponse(scUnitCostRepository.findByActiveOrderByScUnitCostIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scUnitCostRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScUnitCost> activeScUnitCost) {
        Map<String, Object> response = new HashMap<>();

        List<ScUnitCostResponse> scUnitCostResponses= activeScUnitCost.getContent().stream()
                .map(scUnitCost -> mapper.scUnitCostEntityToObject(scUnitCost, ScUnitCostResponse.class)).collect(Collectors.toList());
        response.put("ScUnitCost",scUnitCostResponses);
        response.put("currentPage", activeScUnitCost.getNumber());
        response.put("totalItems", activeScUnitCost.getTotalElements());
        response.put("totalPages", activeScUnitCost.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScUnitCost> activeScUnitCost) {
        Map<String, Object> response = new HashMap<>();

        List<ScUnitCostResponse> scUnitCostResponses = activeScUnitCost.stream()
                .map(scUnitCost  -> mapper.scUnitCostEntityToObject(scUnitCost, ScUnitCostResponse.class)).collect(Collectors.toList());
        response.put("scUnitCost",scUnitCostResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScUnitCostWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scUnitCostRepository.getByActiveOrderByScUnitCostIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScUnitCostDTO> activeScUnitCost) {
        Map<String, Object> response = new HashMap<>();

        List<ScUnitCostResponse> scUnitCostResponses= activeScUnitCost.getContent().stream()
                .map(scUnitCost -> mapper.scUnitCostDTOToObject(scUnitCost,ScUnitCostResponse.class)).collect(Collectors.toList());
        response.put("ScUnitCost",scUnitCostResponses);
        response.put("currentPage", activeScUnitCost.getNumber());
        response.put("totalItems", activeScUnitCost.getTotalElements());
        response.put("totalPages", activeScUnitCost.getTotalPages());
        return response;
    }


    @Transactional
    public ScUnitCostResponse deleteScUnitCostDetails(long id) {
        ScUnitCostResponse scUnitCostResponse= new ScUnitCostResponse();
        ScUnitCost scUnitCost = scUnitCostRepository.findByScUnitCostIdAndActive(id, true);
        if (Objects.nonNull(scUnitCost)) {
            scUnitCost.setActive(false);
            scUnitCostResponse= mapper.scUnitCostEntityToObject(scUnitCostRepository.save(scUnitCost),ScUnitCostResponse.class);
            scUnitCostResponse.setError(false);
        } else {
            scUnitCostResponse.setError(true);
            scUnitCostResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scUnitCostResponse;
    }

    @Transactional
    public ScUnitCostResponse getById(int id){
        ScUnitCostResponse scUnitCostResponse = new ScUnitCostResponse();
        ScUnitCost scUnitCost = scUnitCostRepository.findByScUnitCostIdAndActive(id,true);


        if(scUnitCost == null){
            scUnitCostResponse.setError(true);
            scUnitCostResponse.setError_description("Invalid id");
        }else{
            scUnitCostResponse =  mapper.scUnitCostEntityToObject(scUnitCost, ScUnitCostResponse.class);
            scUnitCostResponse.setError(false);
        }
        log.info("Entity is ",scUnitCost);
        return scUnitCostResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<ScUnitCostDTO> ScUnitCostList = ScUnitCostRepository.getScUnitCost(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(ScUnitCostList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", ScUnitCostList);
//            response = convertListToMapResponse(ScUnitCostList);
//            return response;
//        }
//    }



    @Transactional
    public ScUnitCostResponse getByIdJoin(int id){
        ScUnitCostResponse scUnitCostResponse = new ScUnitCostResponse();
        ScUnitCostDTO scUnitCostDTO = scUnitCostRepository.getByScUnitCostIdAndActive(id,true);
        if(scUnitCostDTO == null){
            scUnitCostResponse.setError(true);
            scUnitCostResponse.setError_description("Invalid id");
        } else {
            scUnitCostResponse = mapper.scUnitCostDTOToObject(scUnitCostDTO, ScUnitCostResponse.class);
            scUnitCostResponse.setError(false);
        }
        log.info("Entity is ", scUnitCostDTO);
        return scUnitCostResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getScUnitCostByScHeadAccountIdAndScCategoryIdAndScSubSchemeDetailsId(Long scHeadAccountId,Long scCategoryId,Long scSubSchemeDetailsId) {
        Map<String, Object> response = new HashMap<>();
        List<ScUnitCost> scUnitCostList = scUnitCostRepository.findByScHeadAccountIdAndScCategoryIdAndScSubSchemeDetailsIdAndActive(scHeadAccountId, scCategoryId,scSubSchemeDetailsId,true);
        if (scUnitCostList.isEmpty()) {
//            throw new ValidationException("Invalid Id");
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            response.put("success", false);
            return response;
        } else {
            log.info("Entity is ", scUnitCostList);
            response = convertListToMapResponse(scUnitCostList);
            response.put("success", true);
            return response;

        }

    }

    private Map<String, Object> convertListToMapResponse(List<ScUnitCost> scUnitCostList) {
        Map<String, Object> response = new HashMap<>();
        List<ScUnitCostResponse> scUnitCostResponses = scUnitCostList.stream()
                .map(scUnitCost -> mapper.scUnitCostEntityToObject(scUnitCost, ScUnitCostResponse.class)).collect(Collectors.toList());
        response.put("scUnitCost", scUnitCostResponses);
        response.put("totalItems", scUnitCostList.size());
        return response;
    }

    @Transactional
    public ScUnitCostResponse updateScUnitCostDetails(EditScUnitCostRequest scUnitCostRequest) {
        ScUnitCostResponse scUnitCostResponse = new ScUnitCostResponse();
        List<ScUnitCost> scUnitCostList = scUnitCostRepository.findByScHeadAccountIdAndScCategoryIdAndScUnitCostIdIsNot(scUnitCostRequest.getScHeadAccountId(), scUnitCostRequest.getScCategoryId(),scUnitCostRequest.getScSubSchemeDetailsId());
        if (scUnitCostList.size() > 0) {
            scUnitCostResponse.setError(true);
            scUnitCostResponse.setError_description("Head Account exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScUnitCost scUnitCost = scUnitCostRepository.findByScUnitCostIdAndActiveIn(scUnitCostRequest.getScUnitCostId(), Set.of(true, false));
            if (Objects.nonNull(scUnitCost)) {
                scUnitCost.setScHeadAccountId(scUnitCostRequest.getScHeadAccountId());
                scUnitCost.setScCategoryId(scUnitCostRequest.getScCategoryId());
                scUnitCost.setScSubSchemeDetailsId(scUnitCostRequest.getScSubSchemeDetailsId());
                scUnitCost.setCentralShare(scUnitCostRequest.getCentralShare());
                scUnitCost.setStateShare(scUnitCostRequest.getStateShare());
                scUnitCost.setBenificiaryShare(scUnitCostRequest.getBenificiaryShare());



                scUnitCost.setActive(true);
                ScUnitCost scUnitCost1 = scUnitCostRepository.save(scUnitCost);
                scUnitCostResponse = mapper.scUnitCostEntityToObject(scUnitCost1, ScUnitCostResponse.class);
                scUnitCostResponse.setError(false);
            } else {
                scUnitCostResponse.setError(true);
                scUnitCostResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scUnitCostResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("scHeadAccount.scHeadAccountName");
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
        Page<ScUnitCostDTO> ScUnitCostDTOS = scUnitCostRepository.getSortedScUnitCost(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",ScUnitCostDTOS);
        return convertPageableDTOToMapResponse(ScUnitCostDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScUnitCostDTO> activeScUnitCost) {
        Map<String, Object> response = new HashMap<>();

        List<ScUnitCostResponse> scUnitCostResponses = activeScUnitCost.getContent().stream()
                .map(scUnitCost -> mapper.scUnitCostDTOToObject(scUnitCost,ScUnitCostResponse.class)).collect(Collectors.toList());
        response.put("ScUnitCost",scUnitCostResponses);
        response.put("currentPage", activeScUnitCost.getNumber());
        response.put("totalItems", activeScUnitCost.getTotalElements());
        response.put("totalPages", activeScUnitCost.getTotalPages());

        return response;
    }
}
