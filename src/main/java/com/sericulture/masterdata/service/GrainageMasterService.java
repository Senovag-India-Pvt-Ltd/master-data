package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.grainageMaster.EditGrainageMasterRequest;
import com.sericulture.masterdata.model.api.grainageMaster.GrainageMasterRequest;
import com.sericulture.masterdata.model.api.grainageMaster.GrainageMasterResponse;
import com.sericulture.masterdata.model.api.scUnitCost.ScUnitCostResponse;
import com.sericulture.masterdata.model.dto.GrainageMasterDTO;
import com.sericulture.masterdata.model.dto.ScUnitCostDTO;
import com.sericulture.masterdata.model.entity.GrainageMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.GrainageMasterRepository;
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
public class GrainageMasterService {

    @Autowired
    GrainageMasterRepository grainageMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GrainageMasterResponse getGrainageMasterDetails(String grainageMasterName){
        GrainageMasterResponse grainageMasterResponse = new GrainageMasterResponse();
        GrainageMaster grainageMaster = grainageMasterRepository.findByGrainageMasterNameAndActive(grainageMasterName, true);
        if(grainageMaster==null){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("grainageMaster  not Found");
        }else{
            grainageMasterResponse = mapper.grainageMasterEntityToObject(grainageMaster, GrainageMasterResponse.class);
            grainageMasterResponse.setError(false);
        }
        log.info("Entity is ",grainageMaster);
        return grainageMasterResponse;

    }

    @Transactional
    public GrainageMasterResponse insertGrainageMasterDetails(GrainageMasterRequest grainageMasterRequest){
        GrainageMasterResponse grainageMasterResponse = new GrainageMasterResponse();
        GrainageMaster grainageMaster = mapper.grainageMasterObjectToEntity(grainageMasterRequest, GrainageMaster.class);
        validator.validate(grainageMaster);
        List<GrainageMaster> grainageMasterList= grainageMasterRepository.findByGrainageMasterNameAndGrainageMasterNameInKannadaAndActive(grainageMasterRequest.getGrainageMasterName(),grainageMasterRequest.getGrainageMasterNameInKannada(),true);
        if(!grainageMasterList.isEmpty() && grainageMasterList.stream().filter(GrainageMaster::getActive).findAny().isPresent()){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("grainageMaster name already exist");
        }
        else if(!grainageMasterList.isEmpty() && grainageMasterList.stream().filter(Predicate.not(GrainageMaster::getActive)).findAny().isPresent()){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("grainageMaster name already exist with inactive state");
        }else {
            grainageMasterResponse = mapper.grainageMasterEntityToObject(grainageMasterRepository.save(grainageMaster), GrainageMasterResponse.class);
            grainageMasterResponse.setError(false);
        }
        return grainageMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedGrainageMasterDetails(final Pageable pageable){
        return convertToMapResponse(grainageMasterRepository.findByActiveOrderByGrainageMasterNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(grainageMasterRepository.findByActiveOrderByGrainageMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<GrainageMaster> activeGrainageMasters) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageMasterResponse> grainageMasterResponses = activeGrainageMasters.getContent().stream()
                .map(grainageMaster -> mapper.grainageMasterEntityToObject(grainageMaster, GrainageMasterResponse.class)).collect(Collectors.toList());
        response.put("grainageMaster",grainageMasterResponses);
        response.put("currentPage", activeGrainageMasters.getNumber());
        response.put("totalItems", activeGrainageMasters.getTotalElements());
        response.put("totalPages", activeGrainageMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<GrainageMaster> activeGrainageMasters) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageMasterResponse> grainageMasterResponses = activeGrainageMasters.stream()
                .map(grainageMaster -> mapper.grainageMasterEntityToObject(grainageMaster, GrainageMasterResponse.class)).collect(Collectors.toList());
        response.put("grainageMaster",grainageMasterResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedGrainageMasterWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(grainageMasterRepository.getByActiveOrderByGrainageMasterIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<GrainageMasterDTO> activeGrainageMaster) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageMasterResponse> grainageMasterResponses= activeGrainageMaster.getContent().stream()
                .map(grainageMaster -> mapper.grainageMasterDTOToObject(grainageMaster,GrainageMasterResponse.class)).collect(Collectors.toList());
        response.put("grainageMaster",grainageMasterResponses);
        response.put("currentPage", activeGrainageMaster.getNumber());
        response.put("totalItems", activeGrainageMaster.getTotalElements());
        response.put("totalPages", activeGrainageMaster.getTotalPages());
        return response;
    }

    @Transactional
    public GrainageMasterResponse deleteGrainageMasterDetails(long id) {

        GrainageMasterResponse grainageMasterResponse= new GrainageMasterResponse();
        GrainageMaster grainageMaster = grainageMasterRepository.findByGrainageMasterIdAndActive(id, true);
        if (Objects.nonNull(grainageMaster)) {
            grainageMaster.setActive(false);
            grainageMasterResponse = mapper.grainageMasterEntityToObject(grainageMasterRepository.save(grainageMaster), GrainageMasterResponse.class);
            grainageMasterResponse.setError(false);
        } else {
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return grainageMasterResponse;
    }

    @Transactional
    public GrainageMasterResponse getById(int id){
        GrainageMasterResponse grainingMasterResponse = new GrainageMasterResponse();
        GrainageMaster grainageMaster = grainageMasterRepository.findByGrainageMasterIdAndActive(id,true);
        if(grainageMaster == null){
            grainingMasterResponse.setError(true);
            grainingMasterResponse.setError_description("Invalid id");
        }else{
            grainingMasterResponse =  mapper.grainageMasterEntityToObject(grainageMaster, GrainageMasterResponse.class);
            grainingMasterResponse.setError(false);
        }
        log.info("Entity is ",grainageMaster);
        return grainingMasterResponse;
    }

    private Map<String, Object> convertListToMapResponse(List<GrainageMasterDTO> grainageMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<GrainageMasterResponse> grainageMasterResponses = grainageMasterList.stream()
                .map(grainageMaster -> mapper.grainageMasterDTOToObject(grainageMaster,GrainageMasterResponse.class)).collect(Collectors.toList());
        response.put("grainageMaster",grainageMasterResponses);
        response.put("totalItems", grainageMasterResponses.size());
        return response;
    }

    @Transactional
    public GrainageMasterResponse getByIdJoin(int id){
        GrainageMasterResponse grainageMasterResponse = new GrainageMasterResponse();
        GrainageMasterDTO grainageMasterDTO = grainageMasterRepository.getByGrainageMasterIdAndActive(id,true);
        if(grainageMasterDTO == null){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("Invalid id");
        } else {
            grainageMasterResponse = mapper.grainageMasterDTOToObject(grainageMasterDTO, GrainageMasterResponse.class);
            grainageMasterResponse.setError(false);
        }
        log.info("Entity is ", grainageMasterDTO);
        return grainageMasterResponse;
    }

    @Transactional
    public GrainageMasterResponse updateGrainageMasterDetails(EditGrainageMasterRequest grainageMasterRequest){

        GrainageMasterResponse grainageMasterResponse = new GrainageMasterResponse();
        List<GrainageMaster> grainageMasterList = grainageMasterRepository.findByActiveAndGrainageMasterNameAndGrainageMasterNameInKannadaAndGrainageMasterIdIsNot(true,grainageMasterRequest.getGrainageMasterName(),grainageMasterRequest.getGrainageMasterNameInKannada(),grainageMasterRequest.getGrainageMasterId());
        if(grainageMasterList.size()>0){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("grainageMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            GrainageMaster grainageMaster= grainageMasterRepository.findByGrainageMasterIdAndActiveIn(grainageMasterRequest.getGrainageMasterId(), Set.of(true,false));
            if(Objects.nonNull(grainageMaster)){
                grainageMaster.setGrainageMasterName(grainageMasterRequest.getGrainageMasterName());
                grainageMaster.setGrainageMasterNameInKannada(grainageMasterRequest.getGrainageMasterNameInKannada());
                grainageMaster.setUserMasterId(grainageMasterRequest.getUserMasterId());
                grainageMaster.setGrainageType(grainageMasterRequest.getGrainageType());
                grainageMaster.setGrainageNameRepresentation(grainageMasterRequest.getGrainageNameRepresentation());

                grainageMaster.setActive(true);
                GrainageMaster grainingMaster1 = grainageMasterRepository.save(grainageMaster);
                grainageMasterResponse = mapper.grainageMasterEntityToObject(grainingMaster1, GrainageMasterResponse.class);
                grainageMasterResponse.setError(false);
            } else {
                grainageMasterResponse.setError(true);
                grainageMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return grainageMasterResponse;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("grainageMaster.grainageMasterName");
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
        Page<GrainageMasterDTO> grainageMasterDTOS = grainageMasterRepository.getSortedGrainageMaster(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",grainageMasterDTOS);
        return convertPageableDTOToMapResponse(grainageMasterDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<GrainageMasterDTO> activeGrainageMaster) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageMasterResponse> grainageMasterResponses = activeGrainageMaster.getContent().stream()
                .map(grainageMaster -> mapper.grainageMasterDTOToObject(grainageMaster,GrainageMasterResponse.class)).collect(Collectors.toList());
        response.put("grainageMaster",grainageMasterResponses);
        response.put("currentPage", activeGrainageMaster.getNumber());
        response.put("totalItems", activeGrainageMaster.getTotalElements());
        response.put("totalPages", activeGrainageMaster.getTotalPages());

        return response;
    }
}
