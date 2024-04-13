package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.grainageType.EditGrainageTypeRequest;
import com.sericulture.masterdata.model.api.grainageType.GrainageTypeRequest;
import com.sericulture.masterdata.model.api.grainageType.GrainageTypeResponse;
import com.sericulture.masterdata.model.dto.GrainageTypeDTO;
import com.sericulture.masterdata.model.entity.GrainageType;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.GrainageTypeRepository;
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
public class GrainageTypeService {
    @Autowired
    GrainageTypeRepository grainageTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public GrainageTypeResponse insertGrainageTypeDetails(GrainageTypeRequest grainageTypeRequest){
        GrainageTypeResponse grainageTypeResponse = new GrainageTypeResponse();
        GrainageType grainageType = mapper.grainageTypeObjectToEntity(grainageTypeRequest, GrainageType.class);
        validator.validate(grainageType);
        List<GrainageType> grainageTypeList = grainageTypeRepository.findByGrainageMasterId(grainageTypeRequest.getGrainageMasterId());
        if(!grainageTypeList.isEmpty() && grainageTypeList.stream().filter(GrainageType::getActive).findAny().isPresent()){
            grainageTypeResponse.setError(true);
            grainageTypeResponse.setError_description("GrainageType already exist");
        }
        else if(!grainageTypeList.isEmpty() && grainageTypeList.stream().filter(Predicate.not(GrainageType::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            grainageTypeResponse.setError(true);
            grainageTypeResponse.setError_description("GrainageType already exist with inactive state");
        }else {
            grainageTypeResponse = mapper.grainageTypeEntityToObject(grainageTypeRepository.save(grainageType), GrainageTypeResponse.class);
            grainageTypeResponse.setError(false);
        }
        return grainageTypeResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getGrainageTypeDetails(final Pageable pageable){
        return convertToMapResponse(grainageTypeRepository.findByActiveOrderByGrainageTypeIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(grainageTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<GrainageType> activeGrainageType) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageTypeResponse> grainageTypeResponses= activeGrainageType.getContent().stream()
                .map(grainageType -> mapper.grainageTypeEntityToObject(grainageType, GrainageTypeResponse.class)).collect(Collectors.toList());
        response.put("grainageType",grainageTypeResponses);
        response.put("currentPage", activeGrainageType.getNumber());
        response.put("totalItems", activeGrainageType.getTotalElements());
        response.put("totalPages", activeGrainageType.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<GrainageType> activeGrainageType) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageTypeResponse> grainageTypeResponses = activeGrainageType.stream()
                .map(grainageType  -> mapper.grainageTypeEntityToObject(grainageType, GrainageTypeResponse.class)).collect(Collectors.toList());
        response.put("grainageType",grainageTypeResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedGrainageTypeWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(grainageTypeRepository.getByActiveOrderByGrainageTypeIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<GrainageTypeDTO> activeGrainageType) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageTypeResponse> grainageTypeResponses= activeGrainageType.getContent().stream()
                .map(grainageType -> mapper.grainageTypeDTOToObject(grainageType,GrainageTypeResponse.class)).collect(Collectors.toList());
        response.put("grainageType",grainageTypeResponses);
        response.put("currentPage", activeGrainageType.getNumber());
        response.put("totalItems", activeGrainageType.getTotalElements());
        response.put("totalPages", activeGrainageType.getTotalPages());
        return response;
    }


    @Transactional
    public GrainageTypeResponse deleteGrainageTypeDetails(long id) {
        GrainageTypeResponse grainageTypeResponse= new GrainageTypeResponse();
        GrainageType grainageType = grainageTypeRepository.findByGrainageTypeIdAndActive(id, true);
        if (Objects.nonNull(grainageType)) {
            grainageType.setActive(false);
            grainageTypeResponse= mapper.grainageTypeEntityToObject(grainageTypeRepository.save(grainageType),GrainageTypeResponse.class);
            grainageTypeResponse.setError(false);
        } else {
            grainageTypeResponse.setError(true);
            grainageTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return grainageTypeResponse;
    }

    @Transactional
    public GrainageTypeResponse getById(int id){
        GrainageTypeResponse grainageTypeResponse = new GrainageTypeResponse();
        GrainageType grainageType = grainageTypeRepository.findByGrainageTypeIdAndActive(id,true);


        if(grainageType == null){
            grainageTypeResponse.setError(true);
            grainageTypeResponse.setError_description("Invalid id");
        }else{
            grainageTypeResponse =  mapper.grainageTypeEntityToObject(grainageType, GrainageTypeResponse.class);
            grainageTypeResponse.setError(false);
        }
        log.info("Entity is ",grainageType);
        return grainageTypeResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<GrainageTypeDTO> grainageTypeList = grainageTypeRepository.getGrainageType(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(grainageTypeList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", grainageTypeList);
//            response = convertListToMapResponse(grainageTypeList);
//            return response;
//        }
//    }

    private Map<String, Object> convertListToMapResponse(List<GrainageTypeDTO> grainageTypeList) {
        Map<String, Object> response = new HashMap<>();
        List<GrainageTypeResponse> grainageTypeResponses = grainageTypeList.stream()
                .map(grainageType -> mapper.grainageTypeDTOToObject(grainageType,GrainageTypeResponse.class)).collect(Collectors.toList());
        response.put("grainageType",grainageTypeResponses);
        response.put("totalItems", grainageTypeList.size());
        return response;
    }

    @Transactional
    public GrainageTypeResponse getByIdJoin(int id){
        GrainageTypeResponse grainageTypeResponse = new GrainageTypeResponse();
        GrainageTypeDTO grainageTypeDTO = grainageTypeRepository.getByGrainageTypeIdAndActive(id,true);
        if(grainageTypeDTO == null){
            grainageTypeResponse.setError(true);
            grainageTypeResponse.setError_description("Invalid id");
        } else {
            grainageTypeResponse = mapper.grainageTypeDTOToObject(grainageTypeDTO, GrainageTypeResponse.class);
            grainageTypeResponse.setError(false);
        }
        log.info("Entity is ", grainageTypeDTO);
        return grainageTypeResponse;
    }

    @Transactional
    public GrainageTypeResponse updateGrainageTypeDetails(EditGrainageTypeRequest grainageTypeRequest) {
        GrainageTypeResponse grainageTypeResponse = new GrainageTypeResponse();
        List<GrainageType> grainageTypeList = grainageTypeRepository.findByGrainageMasterIdAndGrainageTypeIdIsNot(grainageTypeRequest.getGrainageMasterId(), grainageTypeRequest.getGrainageTypeId());
        if (grainageTypeList.size() > 0) {
            grainageTypeResponse.setError(true);
            grainageTypeResponse.setError_description("GrainageType exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            GrainageType grainageType = grainageTypeRepository.findByGrainageTypeIdAndActiveIn(grainageTypeRequest.getGrainageTypeId(), Set.of(true, false));
            if (Objects.nonNull(grainageType)) {
                grainageType.setGrainageMasterId(grainageTypeRequest.getGrainageMasterId());
                grainageType.setName(grainageTypeRequest.getName());
                grainageType.setNameInKannada(grainageTypeRequest.getNameInKannada());

                grainageType.setActive(true);
                GrainageType grainageType1 = grainageTypeRepository.save(grainageType);
                grainageTypeResponse = mapper.grainageTypeEntityToObject(grainageType1, GrainageTypeResponse.class);
                grainageTypeResponse.setError(false);
            } else {
                grainageTypeResponse.setError(true);
                grainageTypeResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return grainageTypeResponse;
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
        Page<GrainageTypeDTO> grainageTypeDTOS = grainageTypeRepository.getSortedGrainageType(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",grainageTypeDTOS);
        return convertPageableDTOToMapResponse(grainageTypeDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<GrainageTypeDTO> activeGrainageType) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageTypeResponse> grainageTypeResponses = activeGrainageType.getContent().stream()
                .map(grainageType -> mapper.grainageTypeDTOToObject(grainageType,GrainageTypeResponse.class)).collect(Collectors.toList());
        response.put("grainageType",grainageTypeResponses);
        response.put("currentPage", activeGrainageType.getNumber());
        response.put("totalItems", activeGrainageType.getTotalElements());
        response.put("totalPages", activeGrainageType.getTotalPages());

        return response;
    }
}
