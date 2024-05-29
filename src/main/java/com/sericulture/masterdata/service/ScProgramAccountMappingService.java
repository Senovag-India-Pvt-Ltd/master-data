package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scProgramAccountMapping.EditScProgramAccountMappingRequest;
import com.sericulture.masterdata.model.api.scProgramAccountMapping.ScProgramAccountMappingRequest;
import com.sericulture.masterdata.model.api.scProgramAccountMapping.ScProgramAccountMappingResponse;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.EditScProgramApprovalMappingRequest;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.ScProgramApprovalMappingRequest;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.ScProgramApprovalMappingResponse;
import com.sericulture.masterdata.model.dto.ScProgramAccountMappingDTO;
import com.sericulture.masterdata.model.dto.ScProgramApprovalMappingDTO;
import com.sericulture.masterdata.model.entity.ScProgramAccountMapping;
import com.sericulture.masterdata.model.entity.ScProgramApprovalMapping;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScProgramAccountMappingRepository;
import com.sericulture.masterdata.repository.ScProgramApprovalMappingRepository;
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
public class ScProgramAccountMappingService {

    @Autowired
    ScProgramAccountMappingRepository scProgramAccountMappingRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;
    @Transactional
    public ScProgramAccountMappingResponse insertScProgramAccountMappingDetails(ScProgramAccountMappingRequest scProgramAccountMappingRequest){
        ScProgramAccountMappingResponse scProgramAccountMappingResponse = new ScProgramAccountMappingResponse();
        ScProgramAccountMapping scProgramAccountMapping = mapper.scProgramAccountMappingObjectToEntity(scProgramAccountMappingRequest, ScProgramAccountMapping.class);
        validator.validate(scProgramAccountMapping);
        List<ScProgramAccountMapping> scProgramAccountMappingList = scProgramAccountMappingRepository.findByScProgramIdAndScHeadAccountId(scProgramAccountMappingRequest.getScProgramId(),scProgramAccountMappingRequest.getScHeadAccountId());
        if(!scProgramAccountMappingList.isEmpty() && scProgramAccountMappingList.stream().filter(ScProgramAccountMapping::getActive).findAny().isPresent()){
            scProgramAccountMappingResponse.setError(true);
            scProgramAccountMappingResponse.setError_description("ScProgramAccountMapping already exist");
        }
        else if(!scProgramAccountMappingList.isEmpty() && scProgramAccountMappingList.stream().filter(Predicate.not(ScProgramAccountMapping::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scProgramAccountMappingResponse.setError(true);
            scProgramAccountMappingResponse.setError_description("ScProgramAccountMapping already exist with inactive state");
        }else {
            scProgramAccountMappingResponse = mapper.scProgramAccountMappingEntityToObject(scProgramAccountMappingRepository.save(scProgramAccountMapping), ScProgramAccountMappingResponse.class);
            scProgramAccountMappingResponse.setError(false);
        }
        return scProgramAccountMappingResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getScProgramAccountMappingDetails(final Pageable pageable){
        return convertToMapResponse(scProgramAccountMappingRepository.findByActiveOrderByScProgramAccountMappingIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scProgramAccountMappingRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScProgramAccountMapping> activeScProgramAccountMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramAccountMappingResponse> scProgramAccountMappingResponses= activeScProgramAccountMapping.getContent().stream()
                .map(scProgramAccountMapping -> mapper.scProgramAccountMappingEntityToObject(scProgramAccountMapping, ScProgramAccountMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramAccountMapping",scProgramAccountMappingResponses);
        response.put("currentPage", activeScProgramAccountMapping.getNumber());
        response.put("totalItems", activeScProgramAccountMapping.getTotalElements());
        response.put("totalPages", activeScProgramAccountMapping.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScProgramAccountMapping> activeScProgramAccountMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramAccountMappingResponse> scProgramAccountMappingResponses = activeScProgramAccountMapping.stream()
                .map(scProgramAccountMapping  -> mapper.scProgramAccountMappingEntityToObject(scProgramAccountMapping, ScProgramAccountMappingResponse.class)).collect(Collectors.toList());
        response.put("activeScProgramAccountMapping",scProgramAccountMappingResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScProgramAccountMappingWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scProgramAccountMappingRepository.getByActiveOrderByScProgramAccountMappingIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScProgramAccountMappingDTO> activeScProgramAccountMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramAccountMappingResponse> scProgramAccountMappingResponses= activeScProgramAccountMapping.getContent().stream()
                .map(scProgramAccountMapping -> mapper.scProgramAccountMappingDTOToObject(scProgramAccountMapping,ScProgramAccountMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramAccountMapping",scProgramAccountMappingResponses);
        response.put("currentPage", activeScProgramAccountMapping.getNumber());
        response.put("totalItems", activeScProgramAccountMapping.getTotalElements());
        response.put("totalPages", activeScProgramAccountMapping.getTotalPages());
        return response;
    }


    @Transactional
    public ScProgramAccountMappingResponse deleteScProgramAccountMappingDetails(long id) {
        ScProgramAccountMappingResponse scProgramAccountMappingResponse= new ScProgramAccountMappingResponse();
        ScProgramAccountMapping scProgramAccountMapping = scProgramAccountMappingRepository.findByScProgramAccountMappingIdAndActive(id, true);
        if (Objects.nonNull(scProgramAccountMapping)) {
            scProgramAccountMapping.setActive(false);
            scProgramAccountMappingResponse= mapper.scProgramAccountMappingEntityToObject(scProgramAccountMappingRepository.save(scProgramAccountMapping),ScProgramAccountMappingResponse.class);
            scProgramAccountMappingResponse.setError(false);
        } else {
            scProgramAccountMappingResponse.setError(true);
            scProgramAccountMappingResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scProgramAccountMappingResponse;
    }

    @Transactional
    public ScProgramAccountMappingResponse getById(int id){
        ScProgramAccountMappingResponse scProgramAccountMappingResponse = new ScProgramAccountMappingResponse();
        ScProgramAccountMapping scProgramAccountMapping = scProgramAccountMappingRepository.findByScProgramAccountMappingIdAndActive(id,true);


        if(scProgramAccountMapping == null){
            scProgramAccountMappingResponse.setError(true);
            scProgramAccountMappingResponse.setError_description("Invalid id");
        }else{
            scProgramAccountMappingResponse =  mapper.scProgramAccountMappingEntityToObject(scProgramAccountMapping, ScProgramAccountMappingResponse.class);
            scProgramAccountMappingResponse.setError(false);
        }
        log.info("Entity is ",scProgramAccountMapping);
        return scProgramAccountMappingResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<ScProgramApprovalMappingDTO> scProgramApprovalMappingList = scProgramApprovalMappingRepository.getScProgramApprovalMapping(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(scProgramApprovalMappingList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", scProgramApprovalMappingList);
//            response = convertListToMapResponse(scProgramApprovalMappingList);
//            return response;
//        }
//    }

    private Map<String, Object> convertListToMapResponse(List<ScProgramAccountMappingDTO> scProgramAccountMappingList) {
        Map<String, Object> response = new HashMap<>();
        List<ScProgramAccountMappingResponse> scProgramAccountMappingResponses = scProgramAccountMappingList.stream()
                .map(scProgramAccountMapping -> mapper.scProgramAccountMappingDTOToObject(scProgramAccountMapping,ScProgramAccountMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramAccountMapping",scProgramAccountMappingResponses);
        response.put("totalItems", scProgramAccountMappingResponses.size());
        return response;
    }

    @Transactional
    public ScProgramAccountMappingResponse getByIdJoin(int id){
        ScProgramAccountMappingResponse scProgramAccountMappingResponse = new ScProgramAccountMappingResponse();
        ScProgramAccountMappingDTO scProgramAccountMappingDTO = scProgramAccountMappingRepository.getByScProgramAccountMappingIdAndActive(id,true);
        if(scProgramAccountMappingDTO == null){
            scProgramAccountMappingResponse.setError(true);
            scProgramAccountMappingResponse.setError_description("Invalid id");
        } else {
            scProgramAccountMappingResponse = mapper.scProgramAccountMappingDTOToObject(scProgramAccountMappingDTO, ScProgramAccountMappingResponse.class);
            scProgramAccountMappingResponse.setError(false);
        }
        log.info("Entity is ", scProgramAccountMappingDTO);
        return scProgramAccountMappingResponse;
    }

    @Transactional
    public ScProgramAccountMappingResponse updateScProgramAccountMappingDetails(EditScProgramAccountMappingRequest scProgramAccountMappingRequest) {
        ScProgramAccountMappingResponse scProgramAccountMappingResponse = new ScProgramAccountMappingResponse();
        List<ScProgramAccountMapping> scProgramAccountMappingList = scProgramAccountMappingRepository.findByScProgramIdAndScHeadAccountIdAndScProgramAccountMappingIdIsNot(scProgramAccountMappingRequest.getScProgramId(), scProgramAccountMappingRequest.getScHeadAccountId(),scProgramAccountMappingRequest.getScProgramAccountMappingId());
        if (scProgramAccountMappingList.size() > 0) {
            scProgramAccountMappingResponse.setError(true);
            scProgramAccountMappingResponse.setError_description("ScProgramAccountMapping exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScProgramAccountMapping scProgramAccountMapping = scProgramAccountMappingRepository.findByScProgramAccountMappingIdAndActiveIn(scProgramAccountMappingRequest.getScProgramAccountMappingId(), Set.of(true, false));
            if (Objects.nonNull(scProgramAccountMapping)) {
                scProgramAccountMapping.setScProgramId(scProgramAccountMappingRequest.getScProgramId());
                scProgramAccountMapping.setScHeadAccountId(scProgramAccountMappingRequest.getScHeadAccountId());
                scProgramAccountMapping.setScCategoryId(scProgramAccountMappingRequest.getScCategoryId());

                scProgramAccountMapping.setActive(true);
                ScProgramAccountMapping scProgramAccountMapping1 = scProgramAccountMappingRepository.save(scProgramAccountMapping);
                scProgramAccountMappingResponse = mapper.scProgramAccountMappingEntityToObject(scProgramAccountMapping1, ScProgramAccountMappingResponse.class);
                scProgramAccountMappingResponse.setError(false);
            } else {
                scProgramAccountMappingResponse.setError(true);
                scProgramAccountMappingResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scProgramAccountMappingResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("scProgram.scProgramName");
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
        Page<ScProgramAccountMappingDTO> scProgramAccountMappingDTOS = scProgramAccountMappingRepository.getSortedScProgramAccountMapping(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",scProgramAccountMappingDTOS);
        return convertPageableDTOToMapResponse(scProgramAccountMappingDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScProgramAccountMappingDTO> activeScProgramAccountMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramAccountMappingResponse> scProgramAccountMappingResponses = activeScProgramAccountMapping.getContent().stream()
                .map(scProgramAccountMapping -> mapper.scProgramAccountMappingDTOToObject(scProgramAccountMapping,ScProgramAccountMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramAccountMapping",scProgramAccountMappingResponses);
        response.put("currentPage", activeScProgramAccountMapping.getNumber());
        response.put("totalItems", activeScProgramAccountMapping.getTotalElements());
        response.put("totalPages", activeScProgramAccountMapping.getTotalPages());

        return response;
    }
}
