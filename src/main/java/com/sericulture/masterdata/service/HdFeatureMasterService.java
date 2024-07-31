package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.hdFeatureMaster.EditHdFeatureMasterRequest;
import com.sericulture.masterdata.model.api.hdFeatureMaster.HdFeatureMasterRequest;
import com.sericulture.masterdata.model.api.hdFeatureMaster.HdFeatureMasterResponse;
import com.sericulture.masterdata.model.api.raceMaster.EditRaceMasterRequest;
import com.sericulture.masterdata.model.api.raceMaster.RaceMasterRequest;
import com.sericulture.masterdata.model.api.raceMaster.RaceMasterResponse;
import com.sericulture.masterdata.model.dto.HdFeatureMasterDTO;
import com.sericulture.masterdata.model.dto.RaceMasterDTO;
import com.sericulture.masterdata.model.entity.HdFeatureMaster;
import com.sericulture.masterdata.model.entity.RaceMaster;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdFeatureMasterRepository;
import com.sericulture.masterdata.repository.RaceMasterRepository;
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
public class HdFeatureMasterService {

    @Autowired
    HdFeatureMasterRepository hdFeatureMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public HdFeatureMasterResponse getHdFeatureMasterDetails(String hdFeatureName){
        HdFeatureMasterResponse hdFeatureMasterResponse = new HdFeatureMasterResponse();
        HdFeatureMaster hdFeatureMaster = hdFeatureMasterRepository.findByHdFeatureNameAndActive(hdFeatureName,true);
        if(hdFeatureMaster==null){
            hdFeatureMasterResponse.setError(true);
            hdFeatureMasterResponse.setError_description("Feature not found");
        }else{
            hdFeatureMasterResponse = mapper.hdFeatureMasterEntityToObject(hdFeatureMaster, HdFeatureMasterResponse.class);
            hdFeatureMasterResponse.setError(false);
        }
        log.info("Entity is ",hdFeatureMaster);
        return hdFeatureMasterResponse;
    }

    @Transactional
    public HdFeatureMasterResponse insertHdFeatureMasterDetails(HdFeatureMasterRequest hdFeatureMasterRequest){
        HdFeatureMasterResponse hdFeatureMasterResponse = new HdFeatureMasterResponse();
        HdFeatureMaster hdFeatureMaster = mapper.hdFeatureMasterObjectToEntity(hdFeatureMasterRequest,HdFeatureMaster.class);
        validator.validate(hdFeatureMaster);
        List<HdFeatureMaster> hdFeatureMasterList = hdFeatureMasterRepository.findByHdFeatureNameAndHdModuleId(hdFeatureMasterRequest.getHdFeatureName(),hdFeatureMasterRequest.getHdModuleId());
        if(!hdFeatureMasterList.isEmpty() && hdFeatureMasterList.stream().filter(HdFeatureMaster::getActive).findAny().isPresent()){
            hdFeatureMasterResponse.setError(true);
            hdFeatureMasterResponse.setError_description("FeatureMaster name already exist");
//        }
//        else if(!hdFeatureMasterList.isEmpty() && hdFeatureMasterList.stream().filter(Predicate.not(HdFeatureMaster::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            hdFeatureMasterResponse.setError(true);
//            hdFeatureMasterResponse.setError_description("FeatureMaster name already exist with inactive state");
        }else {
            hdFeatureMasterResponse = mapper.hdFeatureMasterEntityToObject(hdFeatureMasterRepository.save(hdFeatureMaster), HdFeatureMasterResponse.class);
            hdFeatureMasterResponse.setError(false);
        }
        return hdFeatureMasterResponse;
    }

    public Map<String,Object> getPaginatedHdFeatureMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdFeatureMasterRepository.findByActiveOrderByHdFeatureIdAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdFeatureMasterRepository.findByActiveOrderByHdFeatureNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdFeatureMaster> activeHdFeatureMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdFeatureMasterResponse> hdFeatureMasterResponses = activeHdFeatureMasters.getContent().stream()
                .map(hdFeatureMaster -> mapper.hdFeatureMasterEntityToObject(hdFeatureMaster,HdFeatureMasterResponse.class)).collect(Collectors.toList());
        response.put("hdFeatureMaster",hdFeatureMasterResponses);
        response.put("currentPage", activeHdFeatureMasters.getNumber());
        response.put("totalItems", activeHdFeatureMasters.getTotalElements());
        response.put("totalPages", activeHdFeatureMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdFeatureMaster> activeHdFeatureMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdFeatureMasterResponse> hdFeatureMasterResponses = activeHdFeatureMasters.stream()
                .map(hdFeatureMaster -> mapper.hdFeatureMasterEntityToObject(hdFeatureMaster,HdFeatureMasterResponse.class)).collect(Collectors.toList());
        response.put("hdFeatureMaster",hdFeatureMasterResponses);
        return response;
    }

    public Map<String,Object> getPaginatedHdFeatureMasterDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(hdFeatureMasterRepository.getByActiveOrderByHdFeatureIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<HdFeatureMasterDTO> activeHdFeatureMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdFeatureMasterResponse> hdFeatureMasterResponses = activeHdFeatureMasters.getContent().stream()
                .map(hdFeatureMaster -> mapper.hdFeatureMasterDTOToObject(hdFeatureMaster,HdFeatureMasterResponse.class)).collect(Collectors.toList());
        response.put("hdFeatureMaster",hdFeatureMasterResponses);
        response.put("currentPage", activeHdFeatureMasters.getNumber());
        response.put("totalItems", activeHdFeatureMasters.getTotalElements());
        response.put("totalPages", activeHdFeatureMasters.getTotalPages());
        return response;
    }


    @Transactional
    public HdFeatureMasterResponse deleteHdFeatureMasterDetails(long id) {
        HdFeatureMasterResponse hdFeatureMasterResponse = new HdFeatureMasterResponse();
        HdFeatureMaster hdFeatureMaster = hdFeatureMasterRepository.findByHdFeatureIdAndActive(id, true);
        if (Objects.nonNull(hdFeatureMaster)) {
            hdFeatureMaster.setActive(false);
            hdFeatureMasterResponse = mapper.hdFeatureMasterEntityToObject(hdFeatureMasterRepository.save(hdFeatureMaster), HdFeatureMasterResponse.class);
            hdFeatureMasterResponse.setError(false);
        } else {
            hdFeatureMasterResponse.setError(true);
            hdFeatureMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdFeatureMasterResponse;
    }

    public HdFeatureMasterResponse getById(int id){
        HdFeatureMaster hdFeatureMaster = hdFeatureMasterRepository.findByHdFeatureIdAndActive(id,true);
        if(hdFeatureMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",hdFeatureMaster);
        return mapper.hdFeatureMasterEntityToObject(hdFeatureMaster,HdFeatureMasterResponse.class);
    }

    public HdFeatureMasterResponse getByIdJoin(int id){
        HdFeatureMasterResponse hdFeatureMasterResponse = new HdFeatureMasterResponse();
        HdFeatureMasterDTO hdFeatureMasterDTO = hdFeatureMasterRepository.getByHdFeatureIdAndActive(id,true);
        if(hdFeatureMasterDTO == null){
            hdFeatureMasterResponse.setError(true);
            hdFeatureMasterResponse.setError_description("Invalid id");
        } else {
            hdFeatureMasterResponse = mapper.hdFeatureMasterDTOToObject(hdFeatureMasterDTO, HdFeatureMasterResponse.class);
            hdFeatureMasterResponse.setError(false);
        }
        log.info("Entity is ", hdFeatureMasterDTO);
        return hdFeatureMasterResponse;
    }

    public Map<String,Object> getByHdModuleId(int hdModuleId){
        Map<String, Object> response = new HashMap<>();
        List<HdFeatureMaster> hdFeatureMasterList = hdFeatureMasterRepository.findByHdModuleIdAndActive(hdModuleId,true);
        if(hdFeatureMasterList.isEmpty()){
            response.put("error","Error");
            response.put("error_description","Invalid id");
            return response;
        }else {
            log.info("Entity is ", hdFeatureMasterList);
            response = convertListToMapResponse(hdFeatureMasterList);
            return response;
        }
    }

    private Map<String, Object> convertListToMapResponse(List<HdFeatureMaster> hdFeatureMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<HdFeatureMasterResponse> hdFeatureMasterResponses = hdFeatureMasterList.stream()
                .map(hdFeatureMaster -> mapper.hdFeatureMasterEntityToObject(hdFeatureMaster,HdFeatureMasterResponse.class)).collect(Collectors.toList());
        response.put("hdFeatureMaster",hdFeatureMasterResponses);
        response.put("totalItems", hdFeatureMasterResponses.size());
        return response;
    }

    @Transactional
    public HdFeatureMasterResponse updateHdFeatureMasterDetails(EditHdFeatureMasterRequest hdFeatureMasterRequest) {
        HdFeatureMasterResponse hdFeatureMasterResponse = new HdFeatureMasterResponse();
//        List<RaceMaster> raceMasterList = raceMasterRepository.findByRaceMasterNameAndMarketMasterId(raceMasterRequest.getRaceMasterName(),raceMasterRequest.getMarketMasterId());
//        if (raceMasterList.size() > 0) {
//            raceMasterResponse.setError(true);
//            raceMasterResponse.setError_description("Race already exists, duplicates are not allowed.");
//            // throw new ValidationException("Village already exists, duplicates are not allowed.");
//        } else {

        HdFeatureMaster hdFeatureMaster = hdFeatureMasterRepository.findByHdFeatureIdAndActiveIn(hdFeatureMasterRequest.getHdFeatureId(), Set.of(true, false));
        if (Objects.nonNull(hdFeatureMaster)) {
            hdFeatureMaster.setHdFeatureName(hdFeatureMasterRequest.getHdFeatureName());
            hdFeatureMaster.setHdModuleId(hdFeatureMasterRequest.getHdModuleId());
            hdFeatureMaster.setActive(true);
            HdFeatureMaster hdFeatureMaster1 = hdFeatureMasterRepository.save(hdFeatureMaster);
            hdFeatureMasterResponse = mapper.hdFeatureMasterEntityToObject(hdFeatureMaster1, HdFeatureMasterResponse.class);
            hdFeatureMasterResponse.setError(false);
        } else {
            hdFeatureMasterResponse.setError(true);
            hdFeatureMasterResponse.setError_description("Error occurred while fetching Race");
            // throw new ValidationException("Error occurred while fetching village");
        }
        return hdFeatureMasterResponse;
    }

    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("hdFeatureName");
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
        Page<HdFeatureMasterDTO> hdFeatureMasterDTOS = hdFeatureMasterRepository.getSortedHdFeatureMasters(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",hdFeatureMasterDTOS);
        return convertPageableDTOToMapResponse(hdFeatureMasterDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<HdFeatureMasterDTO> activeHdFeatureMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdFeatureMasterResponse> hdFeatureMasterResponses = activeHdFeatureMasters.getContent().stream()
                .map(hdFeatureMaster -> mapper.hdFeatureMasterDTOToObject(hdFeatureMaster,HdFeatureMasterResponse.class)).collect(Collectors.toList());
        response.put("hdFeatureMaster",hdFeatureMasterResponses);
        response.put("currentPage", activeHdFeatureMasters.getNumber());
        response.put("totalItems", activeHdFeatureMasters.getTotalElements());
        response.put("totalPages", activeHdFeatureMasters.getTotalPages());

        return response;
    }
}
