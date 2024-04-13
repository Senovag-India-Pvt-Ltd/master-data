package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.farmType.EditFarmTypeRequest;
import com.sericulture.masterdata.model.api.farmType.FarmTypeRequest;
import com.sericulture.masterdata.model.api.farmType.FarmTypeResponse;
import com.sericulture.masterdata.model.dto.FarmTypeDTO;
import com.sericulture.masterdata.model.entity.FarmType;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.FarmTypeRepository;
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
public class FarmTypeService {
    @Autowired
    FarmTypeRepository farmTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public FarmTypeResponse insertFarmTypeDetails(FarmTypeRequest farmTypeRequest){
        FarmTypeResponse farmTypeResponse = new FarmTypeResponse();
        FarmType farmType = mapper.farmTypeObjectToEntity(farmTypeRequest, FarmType.class);
        validator.validate(farmType);
        List<FarmType> farmTypeList = farmTypeRepository.findByFarmId(farmTypeRequest.getFarmId());
        if(!farmTypeList.isEmpty() && farmTypeList.stream().filter(FarmType::getActive).findAny().isPresent()){
            farmTypeResponse.setError(true);
            farmTypeResponse.setError_description("FarmType already exist");
        }
        else if(!farmTypeList.isEmpty() && farmTypeList.stream().filter(Predicate.not(FarmType::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            farmTypeResponse.setError(true);
            farmTypeResponse.setError_description("FarmType already exist with inactive state");
        }else {
            farmTypeResponse = mapper.farmTypeEntityToObject(farmTypeRepository.save(farmType), FarmTypeResponse.class);
            farmTypeResponse.setError(false);
        }
        return farmTypeResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getFarmTypeDetails(final Pageable pageable){
        return convertToMapResponse(farmTypeRepository.findByActiveOrderByFarmTypeIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(farmTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<FarmType> activeFarmType) {
        Map<String, Object> response = new HashMap<>();

        List<FarmTypeResponse> farmTypeResponses= activeFarmType.getContent().stream()
                .map(farmType -> mapper.farmTypeEntityToObject(farmType, FarmTypeResponse.class)).collect(Collectors.toList());
        response.put("farmType",farmTypeResponses);
        response.put("currentPage", activeFarmType.getNumber());
        response.put("totalItems", activeFarmType.getTotalElements());
        response.put("totalPages", activeFarmType.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<FarmType> activeFarmType) {
        Map<String, Object> response = new HashMap<>();

        List<FarmTypeResponse> farmTypeResponses = activeFarmType.stream()
                .map(farmType  -> mapper.farmTypeEntityToObject(farmType, FarmTypeResponse.class)).collect(Collectors.toList());
        response.put("farmType",farmTypeResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedFarmTypeWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(farmTypeRepository.getByActiveOrderByFarmTypeIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<FarmTypeDTO> activeFarmType) {
        Map<String, Object> response = new HashMap<>();

        List<FarmTypeResponse> farmTypeResponses= activeFarmType.getContent().stream()
                .map(farmType -> mapper.farmTypeDTOToObject(farmType,FarmTypeResponse.class)).collect(Collectors.toList());
        response.put("farmType",farmTypeResponses);
        response.put("currentPage", activeFarmType.getNumber());
        response.put("totalItems", activeFarmType.getTotalElements());
        response.put("totalPages", activeFarmType.getTotalPages());
        return response;
    }


    @Transactional
    public FarmTypeResponse deleteFarmTypeDetails(long id) {
        FarmTypeResponse farmTypeResponse= new FarmTypeResponse();
        FarmType farmType = farmTypeRepository.findByFarmTypeIdAndActive(id, true);
        if (Objects.nonNull(farmType)) {
            farmType.setActive(false);
            farmTypeResponse= mapper.farmTypeEntityToObject(farmTypeRepository.save(farmType),FarmTypeResponse.class);
            farmTypeResponse.setError(false);
        } else {
            farmTypeResponse.setError(true);
            farmTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return farmTypeResponse;
    }

    @Transactional
    public FarmTypeResponse getById(int id){
        FarmTypeResponse farmTypeResponse = new FarmTypeResponse();
        FarmType farmType = farmTypeRepository.findByFarmTypeIdAndActive(id,true);


        if(farmType == null){
            farmTypeResponse.setError(true);
            farmTypeResponse.setError_description("Invalid id");
        }else{
            farmTypeResponse =  mapper.farmTypeEntityToObject(farmType, FarmTypeResponse.class);
            farmTypeResponse.setError(false);
        }
        log.info("Entity is ",farmType);
        return farmTypeResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<FarmTypeDTO> farmTypeList = farmTypeRepository.getFarmType(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(farmTypeList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", farmTypeList);
//            response = convertListToMapResponse(farmTypeList);
//            return response;
//        }
//    }

    private Map<String, Object> convertListToMapResponse(List<FarmTypeDTO> farmTypeList) {
        Map<String, Object> response = new HashMap<>();
        List<FarmTypeResponse> farmTypeResponses = farmTypeList.stream()
                .map(farmType -> mapper.farmTypeDTOToObject(farmType,FarmTypeResponse.class)).collect(Collectors.toList());
        response.put("farmType",farmTypeResponses);
        response.put("totalItems", farmTypeList.size());
        return response;
    }

    @Transactional
    public FarmTypeResponse getByIdJoin(int id){
        FarmTypeResponse farmTypeResponse = new FarmTypeResponse();
        FarmTypeDTO farmTypeDTO = farmTypeRepository.getByFarmTypeIdAndActive(id,true);
        if(farmTypeDTO == null){
            farmTypeResponse.setError(true);
            farmTypeResponse.setError_description("Invalid id");
        } else {
            farmTypeResponse = mapper.farmTypeDTOToObject(farmTypeDTO, FarmTypeResponse.class);
            farmTypeResponse.setError(false);
        }
        log.info("Entity is ", farmTypeDTO);
        return farmTypeResponse;
    }

    @Transactional
    public FarmTypeResponse updateFarmTypeDetails(EditFarmTypeRequest farmTypeRequest) {
        FarmTypeResponse farmTypeResponse = new FarmTypeResponse();
        List<FarmType> farmTypeList = farmTypeRepository.findByFarmIdAndFarmTypeIdIsNot(farmTypeRequest.getFarmId(), farmTypeRequest.getFarmTypeId());
        if (farmTypeList.size() > 0) {
            farmTypeResponse.setError(true);
            farmTypeResponse.setError_description("FarmType exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            FarmType farmType = farmTypeRepository.findByFarmTypeIdAndActiveIn(farmTypeRequest.getFarmTypeId(), Set.of(true, false));
            if (Objects.nonNull(farmType)) {
                farmType.setFarmId(farmTypeRequest.getFarmId());
                farmType.setName(farmTypeRequest.getName());
                farmType.setNameInKannada(farmTypeRequest.getNameInKannada());

                farmType.setActive(true);
                FarmType farmType1 = farmTypeRepository.save(farmType);
                farmTypeResponse = mapper.farmTypeEntityToObject(farmType1, FarmTypeResponse.class);
                farmTypeResponse.setError(false);
            } else {
                farmTypeResponse.setError(true);
                farmTypeResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return farmTypeResponse;
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
        Page<FarmTypeDTO> farmTypeDTOS = farmTypeRepository.getSortedFarmType(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",farmTypeDTOS);
        return convertPageableDTOToMapResponse(farmTypeDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<FarmTypeDTO> activeFarmType) {
        Map<String, Object> response = new HashMap<>();

        List<FarmTypeResponse> farmTypeResponses = activeFarmType.getContent().stream()
                .map(farmType -> mapper.farmTypeDTOToObject(farmType,FarmTypeResponse.class)).collect(Collectors.toList());
        response.put("farmType",farmTypeResponses);
        response.put("currentPage", activeFarmType.getNumber());
        response.put("totalItems", activeFarmType.getTotalElements());
        response.put("totalPages", activeFarmType.getTotalPages());

        return response;
    }
}
