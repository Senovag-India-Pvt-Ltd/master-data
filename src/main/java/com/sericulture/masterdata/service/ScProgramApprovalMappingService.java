package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.raceMarketMaster.EditRaceMarketMasterRequest;
import com.sericulture.masterdata.model.api.raceMarketMaster.RaceMarketMasterRequest;
import com.sericulture.masterdata.model.api.raceMarketMaster.RaceMarketMasterResponse;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.EditScProgramApprovalMappingRequest;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.ScProgramApprovalMappingRequest;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.ScProgramApprovalMappingResponse;
import com.sericulture.masterdata.model.api.useMaster.UserMasterResponse;
import com.sericulture.masterdata.model.dto.RaceMarketMasterDTO;
import com.sericulture.masterdata.model.dto.ScProgramApprovalMappingDTO;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.entity.RaceMarketMaster;
import com.sericulture.masterdata.model.entity.ScProgramApprovalMapping;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RaceMarketMasterRepository;
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
public class ScProgramApprovalMappingService {
    @Autowired
    ScProgramApprovalMappingRepository scProgramApprovalMappingRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScProgramApprovalMappingResponse insertScProgramApprovalMappingDetails(ScProgramApprovalMappingRequest scProgramApprovalMappingRequest){
        ScProgramApprovalMappingResponse scProgramApprovalMappingResponse = new ScProgramApprovalMappingResponse();
        ScProgramApprovalMapping scProgramApprovalMapping = mapper.scProgramApprovalMappingObjectToEntity(scProgramApprovalMappingRequest, ScProgramApprovalMapping.class);
        validator.validate(scProgramApprovalMapping);
        List<ScProgramApprovalMapping> scProgramApprovalMappingList = scProgramApprovalMappingRepository.findByScProgramIdAndScApprovalStageId(scProgramApprovalMappingRequest.getScProgramId(),scProgramApprovalMappingRequest.getScApprovalStageId());
        if(!scProgramApprovalMappingList.isEmpty() && scProgramApprovalMappingList.stream().filter(ScProgramApprovalMapping::getActive).findAny().isPresent()){
            scProgramApprovalMappingResponse.setError(true);
            scProgramApprovalMappingResponse.setError_description("ScProgramApprovalMapping already exist");
        }
        else if(!scProgramApprovalMappingList.isEmpty() && scProgramApprovalMappingList.stream().filter(Predicate.not(ScProgramApprovalMapping::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scProgramApprovalMappingResponse.setError(true);
            scProgramApprovalMappingResponse.setError_description("ScProgramApprovalMapping already exist with inactive state");
        }else {
            scProgramApprovalMappingResponse = mapper.scProgramApprovalMappingEntityToObject(scProgramApprovalMappingRepository.save(scProgramApprovalMapping), ScProgramApprovalMappingResponse.class);
            scProgramApprovalMappingResponse.setError(false);
        }
        return scProgramApprovalMappingResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getScProgramApprovalMappingDetails(final Pageable pageable){
        return convertToMapResponse(scProgramApprovalMappingRepository.findByActiveOrderByScProgramApprovalMappingIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scProgramApprovalMappingRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScProgramApprovalMapping> activeScProgramApprovalMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramApprovalMappingResponse> scProgramApprovalMappingResponses= activeScProgramApprovalMapping.getContent().stream()
                .map(scProgramApprovalMapping -> mapper.scProgramApprovalMappingEntityToObject(scProgramApprovalMapping, ScProgramApprovalMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramApprovalMapping",scProgramApprovalMappingResponses);
        response.put("currentPage", activeScProgramApprovalMapping.getNumber());
        response.put("totalItems", activeScProgramApprovalMapping.getTotalElements());
        response.put("totalPages", activeScProgramApprovalMapping.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScProgramApprovalMapping> activeScProgramApprovalMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramApprovalMappingResponse> scProgramApprovalMappingResponses = activeScProgramApprovalMapping.stream()
                .map(scProgramApprovalMapping  -> mapper.scProgramApprovalMappingEntityToObject(scProgramApprovalMapping, ScProgramApprovalMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramApprovalMapping",scProgramApprovalMappingResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScProgramApprovalMappingWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scProgramApprovalMappingRepository.getByActiveOrderByScProgramApprovalMappingIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScProgramApprovalMappingDTO> activeScProgramApprovalMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramApprovalMappingResponse> scProgramApprovalMappingResponses= activeScProgramApprovalMapping.getContent().stream()
                .map(scProgramApprovalMapping -> mapper.scProgramApprovalMappingDTOToObject(scProgramApprovalMapping,ScProgramApprovalMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramApprovalMapping",scProgramApprovalMappingResponses);
        response.put("currentPage", activeScProgramApprovalMapping.getNumber());
        response.put("totalItems", activeScProgramApprovalMapping.getTotalElements());
        response.put("totalPages", activeScProgramApprovalMapping.getTotalPages());
        return response;
    }


    @Transactional
    public ScProgramApprovalMappingResponse deleteScProgramApprovalMappingDetails(long id) {
        ScProgramApprovalMappingResponse scProgramApprovalMappingResponse= new ScProgramApprovalMappingResponse();
        ScProgramApprovalMapping scProgramApprovalMapping = scProgramApprovalMappingRepository.findByScProgramApprovalMappingIdAndActive(id, true);
        if (Objects.nonNull(scProgramApprovalMapping)) {
            scProgramApprovalMapping.setActive(false);
            scProgramApprovalMappingResponse= mapper.scProgramApprovalMappingEntityToObject(scProgramApprovalMappingRepository.save(scProgramApprovalMapping),ScProgramApprovalMappingResponse.class);
            scProgramApprovalMappingResponse.setError(false);
        } else {
            scProgramApprovalMappingResponse.setError(true);
            scProgramApprovalMappingResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scProgramApprovalMappingResponse;
    }

    @Transactional
    public ScProgramApprovalMappingResponse getById(int id){
        ScProgramApprovalMappingResponse scProgramApprovalMappingResponse = new ScProgramApprovalMappingResponse();
        ScProgramApprovalMapping scProgramApprovalMapping = scProgramApprovalMappingRepository.findByScProgramApprovalMappingIdAndActive(id,true);


        if(scProgramApprovalMapping == null){
            scProgramApprovalMappingResponse.setError(true);
            scProgramApprovalMappingResponse.setError_description("Invalid id");
        }else{
            scProgramApprovalMappingResponse =  mapper.scProgramApprovalMappingEntityToObject(scProgramApprovalMapping, ScProgramApprovalMappingResponse.class);
            scProgramApprovalMappingResponse.setError(false);
        }
        log.info("Entity is ",scProgramApprovalMapping);
        return scProgramApprovalMappingResponse;
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

    private Map<String, Object> convertListToMapResponse(List<ScProgramApprovalMappingDTO> scProgramApprovalMappingList) {
        Map<String, Object> response = new HashMap<>();
        List<ScProgramApprovalMappingResponse> scProgramApprovalMappingResponses = scProgramApprovalMappingList.stream()
                .map(scProgramApprovalMapping -> mapper.scProgramApprovalMappingDTOToObject(scProgramApprovalMapping,ScProgramApprovalMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramApprovalMapping",scProgramApprovalMappingResponses);
        response.put("totalItems", scProgramApprovalMappingResponses.size());
        return response;
    }

    @Transactional
    public ScProgramApprovalMappingResponse getByIdJoin(int id){
        ScProgramApprovalMappingResponse scProgramApprovalMappingResponse = new ScProgramApprovalMappingResponse();
        ScProgramApprovalMappingDTO scProgramApprovalMappingDTO = scProgramApprovalMappingRepository.getByScProgramApprovalMappingIdAndActive(id,true);
        if(scProgramApprovalMappingDTO == null){
            scProgramApprovalMappingResponse.setError(true);
            scProgramApprovalMappingResponse.setError_description("Invalid id");
        } else {
            scProgramApprovalMappingResponse = mapper.scProgramApprovalMappingDTOToObject(scProgramApprovalMappingDTO, ScProgramApprovalMappingResponse.class);
            scProgramApprovalMappingResponse.setError(false);
        }
        log.info("Entity is ", scProgramApprovalMappingDTO);
        return scProgramApprovalMappingResponse;
    }

    @Transactional
    public ScProgramApprovalMappingResponse updateScProgramApprovalMappingDetails(EditScProgramApprovalMappingRequest scProgramApprovalMappingRequest) {
        ScProgramApprovalMappingResponse scProgramApprovalMappingResponse = new ScProgramApprovalMappingResponse();
        List<ScProgramApprovalMapping> scProgramApprovalMappingList = scProgramApprovalMappingRepository.findByScProgramIdAndScApprovalStageIdAndScProgramApprovalMappingIdIsNot(scProgramApprovalMappingRequest.getScProgramId(), scProgramApprovalMappingRequest.getScApprovalStageId(),scProgramApprovalMappingRequest.getScProgramApprovalMappingId());
        if (scProgramApprovalMappingList.size() > 0) {
            scProgramApprovalMappingResponse.setError(true);
            scProgramApprovalMappingResponse.setError_description("ScProgramApprovalMapping exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScProgramApprovalMapping scProgramApprovalMapping = scProgramApprovalMappingRepository.findByScProgramApprovalMappingIdAndActiveIn(scProgramApprovalMappingRequest.getScProgramApprovalMappingId(), Set.of(true, false));
            if (Objects.nonNull(scProgramApprovalMapping)) {
                scProgramApprovalMapping.setScProgramId(scProgramApprovalMappingRequest.getScProgramId());
                scProgramApprovalMapping.setScApprovalStageId(scProgramApprovalMappingRequest.getScApprovalStageId());
                scProgramApprovalMapping.setDesignationId(scProgramApprovalMappingRequest.getDesignationId());
                scProgramApprovalMapping.setOrders(scProgramApprovalMappingRequest.getOrders());


                scProgramApprovalMapping.setActive(true);
                ScProgramApprovalMapping scProgramApprovalMapping1 = scProgramApprovalMappingRepository.save(scProgramApprovalMapping);
                scProgramApprovalMappingResponse = mapper.scProgramApprovalMappingEntityToObject(scProgramApprovalMapping1, ScProgramApprovalMappingResponse.class);
                scProgramApprovalMappingResponse.setError(false);
            } else {
                scProgramApprovalMappingResponse.setError(true);
                scProgramApprovalMappingResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scProgramApprovalMappingResponse;
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
        Page<ScProgramApprovalMappingDTO> scProgramApprovalMappingDTOS = scProgramApprovalMappingRepository.getSortedScProgramApprovalMapping(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",scProgramApprovalMappingDTOS);
        return convertPageableDTOToMapResponse(scProgramApprovalMappingDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScProgramApprovalMappingDTO> activeScProgramApprovalMapping) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramApprovalMappingResponse> scProgramApprovalMappingResponses = activeScProgramApprovalMapping.getContent().stream()
                .map(scProgramApprovalMapping -> mapper.scProgramApprovalMappingDTOToObject(scProgramApprovalMapping,ScProgramApprovalMappingResponse.class)).collect(Collectors.toList());
        response.put("scProgramApprovalMapping",scProgramApprovalMappingResponses);
        response.put("currentPage", activeScProgramApprovalMapping.getNumber());
        response.put("totalItems", activeScProgramApprovalMapping.getTotalElements());
        response.put("totalPages", activeScProgramApprovalMapping.getTotalPages());

        return response;
    }
}
