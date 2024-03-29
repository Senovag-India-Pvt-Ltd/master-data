package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.disinfectantMaster.DisinfectantMasterRequest;
import com.sericulture.masterdata.model.api.disinfectantMaster.DisinfectantMasterResponse;
import com.sericulture.masterdata.model.api.disinfectantMaster.EditDisinfectantMasterRequest;
import com.sericulture.masterdata.model.api.farmMaster.EditFarmMasterRequest;
import com.sericulture.masterdata.model.api.farmMaster.FarmMasterRequest;
import com.sericulture.masterdata.model.api.farmMaster.FarmMasterResponse;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.ScProgramApprovalMappingResponse;
import com.sericulture.masterdata.model.api.scUnitCost.ScUnitCostResponse;
import com.sericulture.masterdata.model.dto.FarmMasterDTO;
import com.sericulture.masterdata.model.dto.ScProgramApprovalMappingDTO;
import com.sericulture.masterdata.model.dto.ScUnitCostDTO;
import com.sericulture.masterdata.model.entity.DisinfectantMaster;
import com.sericulture.masterdata.model.entity.FarmMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DisinfectantMasterRepository;
import com.sericulture.masterdata.repository.FarmMasterRepository;
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
public class FarmMasterService {

    @Autowired
    FarmMasterRepository farmMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public FarmMasterResponse getFarmMasterDetails(String farmName){
        FarmMasterResponse farmMasterResponse = new FarmMasterResponse();
        FarmMaster farmMaster = farmMasterRepository.findByFarmNameAndActive(farmName, true);
        if(farmMaster==null){
            farmMasterResponse.setError(true);
            farmMasterResponse.setError_description("farmMaster  not Found");
        }else{
            farmMasterResponse = mapper.farmMasterEntityToObject(farmMaster, FarmMasterResponse.class);
            farmMasterResponse.setError(false);
        }
        log.info("Entity is ",farmMaster);
        return farmMasterResponse;

    }

    @Transactional
    public FarmMasterResponse insertFarmMasterDetails(FarmMasterRequest farmMasterRequest){
        FarmMasterResponse farmMasterResponse = new FarmMasterResponse();
        FarmMaster farmMaster = mapper.farmMasterObjectToEntity(farmMasterRequest,FarmMaster.class);
        validator.validate(farmMaster);
        List<FarmMaster> farmMasterList= farmMasterRepository.findByFarmNameAndFarmNameInKannada(farmMasterRequest.getFarmName(),farmMasterRequest.getFarmNameInKannada());
        if(!farmMasterList.isEmpty() && farmMasterList.stream().filter(FarmMaster::getActive).findAny().isPresent()){
            farmMasterResponse.setError(true);
            farmMasterResponse.setError_description("FarmMaster name already exist");
        }
        else if(!farmMasterList.isEmpty() && farmMasterList.stream().filter(Predicate.not(FarmMaster::getActive)).findAny().isPresent()){
            farmMasterResponse.setError(true);
            farmMasterResponse.setError_description("FarmMaster name already exist with inactive state");
        }else {
            farmMasterResponse = mapper.farmMasterEntityToObject(farmMasterRepository.save(farmMaster), FarmMasterResponse.class);
            farmMasterResponse.setError(false);
        }
        return farmMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedFarmMasterDetails(final Pageable pageable){
        return convertToMapResponse(farmMasterRepository.findByActiveOrderByFarmNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(farmMasterRepository.findByActiveOrderByFarmNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<FarmMaster> activeFarmMasters) {
        Map<String, Object> response = new HashMap<>();

        List<FarmMasterResponse> farmMasterResponses = activeFarmMasters.getContent().stream()
                .map(farmMaster -> mapper.farmMasterEntityToObject(farmMaster,FarmMasterResponse.class)).collect(Collectors.toList());
        response.put("farmMaster",farmMasterResponses);
        response.put("currentPage", activeFarmMasters.getNumber());
        response.put("totalItems", activeFarmMasters.getTotalElements());
        response.put("totalPages", activeFarmMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<FarmMaster> activeFarmMasters) {
        Map<String, Object> response = new HashMap<>();

        List<FarmMasterResponse> farmMasterResponses = activeFarmMasters.stream()
                .map(farmMaster -> mapper.farmMasterEntityToObject(farmMaster,FarmMasterResponse.class)).collect(Collectors.toList());
        response.put("farmMaster",farmMasterResponses);

        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedFarmMasterWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(farmMasterRepository.getByActiveOrderByFarmMasterIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<FarmMasterDTO> activeFarmMaster) {
        Map<String, Object> response = new HashMap<>();

        List<FarmMasterResponse> farmMasterResponses= activeFarmMaster.getContent().stream()
                .map(farmMaster -> mapper.farmMasterDTOToObject(farmMaster,FarmMasterResponse.class)).collect(Collectors.toList());
        response.put("scProgramApprovalMapping",farmMasterResponses);
        response.put("currentPage", activeFarmMaster.getNumber());
        response.put("totalItems", activeFarmMaster.getTotalElements());
        response.put("totalPages", activeFarmMaster.getTotalPages());
        return response;
    }

    @Transactional
    public FarmMasterResponse deleteFarmMasterDetails(long id) {

        FarmMasterResponse farmMasterResponse= new FarmMasterResponse();
        FarmMaster farmMaster = farmMasterRepository.findByFarmIdAndActive(id, true);
        if (Objects.nonNull(farmMaster)) {
            farmMaster.setActive(false);
            farmMasterResponse = mapper.farmMasterEntityToObject(farmMasterRepository.save(farmMaster), FarmMasterResponse.class);
            farmMasterResponse.setError(false);
        } else {
            farmMasterResponse.setError(true);
            farmMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return farmMasterResponse;
    }

    @Transactional
    public FarmMasterResponse getById(int id){
        FarmMasterResponse farmMasterResponse = new FarmMasterResponse();
        FarmMaster farmMaster = farmMasterRepository.findByFarmIdAndActive(id,true);
        if(farmMaster == null){
            farmMasterResponse.setError(true);
            farmMasterResponse.setError_description("Invalid id");
        }else{
            farmMasterResponse =  mapper.farmMasterEntityToObject(farmMaster,FarmMasterResponse.class);
            farmMasterResponse.setError(false);
        }
        log.info("Entity is ",farmMaster);
        return farmMasterResponse;
    }

    private Map<String, Object> convertListToMapResponse(List<FarmMasterDTO> farmMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<FarmMasterResponse> farmMasterResponses = farmMasterList.stream()
                .map(farmMaster -> mapper.farmMasterDTOToObject(farmMaster,FarmMasterResponse.class)).collect(Collectors.toList());
        response.put("farmMaster",farmMasterResponses);
        response.put("totalItems", farmMasterResponses.size());
        return response;
    }

    @Transactional
    public FarmMasterResponse getByIdJoin(int id){
        FarmMasterResponse farmMasterResponse = new FarmMasterResponse();
        FarmMasterDTO farmMasterDTO = farmMasterRepository.getByFarmMasterIdAndActive(id,true);
        if(farmMasterDTO == null){
            farmMasterResponse.setError(true);
            farmMasterResponse.setError_description("Invalid id");
        } else {
            farmMasterResponse = mapper.farmMasterDTOToObject(farmMasterDTO, FarmMasterResponse.class);
            farmMasterResponse.setError(false);
        }
        log.info("Entity is ", farmMasterDTO);
        return farmMasterResponse;
    }


    @Transactional
    public FarmMasterResponse updateFarmMasterDetails(EditFarmMasterRequest farmMasterRequest){

        FarmMasterResponse farmMasterResponse = new FarmMasterResponse();
        List<FarmMaster> farmMasterList = farmMasterRepository.findByFarmNameAndFarmNameInKannadaAndFarmIdIsNot(farmMasterRequest.getFarmName(),farmMasterRequest.getFarmNameInKannada(),farmMasterRequest.getFarmId());
        if(farmMasterList.size()>0){
            farmMasterResponse.setError(true);
            farmMasterResponse.setError_description("farmMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            FarmMaster farmMaster= farmMasterRepository.findByFarmIdAndActiveIn(farmMasterRequest.getFarmId(), Set.of(true,false));
            if(Objects.nonNull(farmMaster)){
                farmMaster.setFarmName(farmMasterRequest.getFarmName());
                farmMaster.setFarmNameInKannada(farmMasterRequest.getFarmNameInKannada());
                farmMaster.setUserMasterId(farmMasterRequest.getUserMasterId());
                farmMaster.setActive(true);
                FarmMaster farmMaster1 = farmMasterRepository.save(farmMaster);
                farmMasterResponse = mapper.farmMasterEntityToObject(farmMaster1, FarmMasterResponse.class);
                farmMasterResponse.setError(false);
            } else {
                farmMasterResponse.setError(true);
                farmMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return farmMasterResponse;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("farmMaster.farmName");
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
        Page<FarmMasterDTO> farmMasterDTOS = farmMasterRepository.getSortedFarmMaster(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",farmMasterDTOS);
        return convertPageableDTOToMapResponse(farmMasterDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<FarmMasterDTO> activeFarmMaster) {
        Map<String, Object> response = new HashMap<>();

        List<FarmMasterResponse> farmMasterResponses = activeFarmMaster.getContent().stream()
                .map(farmMaster -> mapper.farmMasterDTOToObject(farmMaster,FarmMasterResponse.class)).collect(Collectors.toList());
        response.put("farmMaster",farmMasterResponses);
        response.put("currentPage", activeFarmMaster.getNumber());
        response.put("totalItems", activeFarmMaster.getTotalElements());
        response.put("totalPages", activeFarmMaster.getTotalPages());

        return response;
    }
}
