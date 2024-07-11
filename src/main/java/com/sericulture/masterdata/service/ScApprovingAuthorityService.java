package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scApprovingAuthority.EditScApprovingAuthorityRequest;
import com.sericulture.masterdata.model.api.scApprovingAuthority.ScApprovingAuthorityRequest;
import com.sericulture.masterdata.model.api.scApprovingAuthority.ScApprovingAuthorityResponse;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.ScHeadAccountCategoryResponse;
import com.sericulture.masterdata.model.dto.ScApprovingAuthorityDTO;
import com.sericulture.masterdata.model.dto.ScHeadAccountCategoryDTO;
import com.sericulture.masterdata.model.entity.ScApprovingAuthority;
import com.sericulture.masterdata.model.entity.ScHeadAccountCategory;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScApprovingAuthorityRepository;
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
public class ScApprovingAuthorityService {

    @Autowired
    ScApprovingAuthorityRepository scApprovingAuthorityRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScApprovingAuthorityResponse insertScApprovingAuthorityDetails(ScApprovingAuthorityRequest scApprovingAuthorityRequest){
        ScApprovingAuthorityResponse scApprovingAuthorityResponse = new ScApprovingAuthorityResponse();
        ScApprovingAuthority scApprovingAuthority = mapper.scApprovingAuthorityObjectToEntity(scApprovingAuthorityRequest, ScApprovingAuthority.class);
        validator.validate(scApprovingAuthority);
        List<ScApprovingAuthority> scApprovingAuthorityList = scApprovingAuthorityRepository.findByRoleId(scApprovingAuthorityRequest.getRoleId());
        if(!scApprovingAuthorityList.isEmpty() && scApprovingAuthorityList.stream().filter(ScApprovingAuthority::getActive).findAny().isPresent()){
            scApprovingAuthorityResponse.setError(true);
            scApprovingAuthorityResponse.setError_description("ScApprovingAuthority already exist");
        }
        else if(!scApprovingAuthorityList.isEmpty() && scApprovingAuthorityList.stream().filter(Predicate.not(ScApprovingAuthority::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scApprovingAuthorityResponse.setError(true);
            scApprovingAuthorityResponse.setError_description("ScApprovingAuthority already exist with inactive state");
        }else {
            scApprovingAuthorityResponse = mapper.scApprovingAuthorityEntityToObject(scApprovingAuthorityRepository.save(scApprovingAuthority), ScApprovingAuthorityResponse.class);
            scApprovingAuthorityResponse.setError(false);
        }
        return scApprovingAuthorityResponse;
    }

    public Map<String,Object> getScApprovingAuthorityDetails(final Pageable pageable){
        return convertToMapResponse(scApprovingAuthorityRepository.findByActiveOrderByScApprovingAuthorityIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scApprovingAuthorityRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScApprovingAuthority> activeScApprovingAuthority) {
        Map<String, Object> response = new HashMap<>();

        List<ScApprovingAuthorityResponse> scApprovingAuthorityResponses= activeScApprovingAuthority.getContent().stream()
                .map(scApprovingAuthority -> mapper.scApprovingAuthorityEntityToObject(scApprovingAuthority, ScApprovingAuthorityResponse.class)).collect(Collectors.toList());
        response.put("ScApprovingAuthority",scApprovingAuthorityResponses);
        response.put("currentPage", activeScApprovingAuthority.getNumber());
        response.put("totalItems", activeScApprovingAuthority.getTotalElements());
        response.put("totalPages", activeScApprovingAuthority.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScApprovingAuthority> activeScApprovingAuthority) {
        Map<String, Object> response = new HashMap<>();

        List<ScApprovingAuthorityResponse> scApprovingAuthorityResponses = activeScApprovingAuthority.stream()
                .map(scApprovingAuthority  -> mapper.scApprovingAuthorityEntityToObject(scApprovingAuthority, ScApprovingAuthorityResponse.class)).collect(Collectors.toList());
        response.put("ScApprovingAuthority",scApprovingAuthorityResponses);
        return response;
    }

    public Map<String,Object> getPaginatedScApprovingAuthorityWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scApprovingAuthorityRepository.getByActiveOrderByScApprovingAuthorityIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScApprovingAuthorityDTO> activeScApprovingAuthority) {
        Map<String, Object> response = new HashMap<>();

        List<ScApprovingAuthorityResponse> scApprovingAuthorityResponses= activeScApprovingAuthority.getContent().stream()
                .map(ScApprovingAuthority -> mapper.scApprovingAuthorityDTOToObject(ScApprovingAuthority,ScApprovingAuthorityResponse.class)).collect(Collectors.toList());
        response.put("ScApprovingAuthority",scApprovingAuthorityResponses);
        response.put("currentPage", activeScApprovingAuthority.getNumber());
        response.put("totalItems", activeScApprovingAuthority.getTotalElements());
        response.put("totalPages", activeScApprovingAuthority.getTotalPages());
        return response;
    }


    @Transactional
    public ScApprovingAuthorityResponse deleteScApprovingAuthorityDetails(long id) {
        ScApprovingAuthorityResponse scApprovingAuthorityResponse= new ScApprovingAuthorityResponse();
        ScApprovingAuthority scApprovingAuthority = scApprovingAuthorityRepository.findByScApprovingAuthorityIdAndActive(id, true);
        if (Objects.nonNull(scApprovingAuthority)) {
            scApprovingAuthority.setActive(false);
            scApprovingAuthorityResponse= mapper.scApprovingAuthorityEntityToObject(scApprovingAuthorityRepository.save(scApprovingAuthority),ScApprovingAuthorityResponse.class);
            scApprovingAuthorityResponse.setError(false);
        } else {
            scApprovingAuthorityResponse.setError(true);
            scApprovingAuthorityResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scApprovingAuthorityResponse;
    }

    public ScApprovingAuthorityResponse getById(int id){
        ScApprovingAuthorityResponse scApprovingAuthorityResponse = new ScApprovingAuthorityResponse();
        ScApprovingAuthority scApprovingAuthority = scApprovingAuthorityRepository.findByScApprovingAuthorityIdAndActive(id,true);


        if(scApprovingAuthority == null){
            scApprovingAuthorityResponse.setError(true);
            scApprovingAuthorityResponse.setError_description("Invalid id");
        }else{
            scApprovingAuthorityResponse =  mapper.scApprovingAuthorityEntityToObject(scApprovingAuthority, ScApprovingAuthorityResponse.class);
            scApprovingAuthorityResponse.setError(false);
        }
        log.info("Entity is ",scApprovingAuthority);
        return scApprovingAuthorityResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<ScApprovingAuthorityDTO> ScApprovingAuthorityList = scApprovingAuthorityRepository.getScApprovingAuthority(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(ScApprovingAuthorityList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", ScApprovingAuthorityList);
//            response = convertListToMapResponse(ScApprovingAuthorityList);
//            return response;
//        }
//    }



    public ScApprovingAuthorityResponse getByIdJoin(int id){
        ScApprovingAuthorityResponse scApprovingAuthorityResponse = new ScApprovingAuthorityResponse();
        ScApprovingAuthorityDTO scApprovingAuthorityDTO = scApprovingAuthorityRepository.getByScApprovingAuthorityIdAndActive(id,true);
        if(scApprovingAuthorityDTO == null){
            scApprovingAuthorityResponse.setError(true);
            scApprovingAuthorityResponse.setError_description("Invalid id");
        } else {
            scApprovingAuthorityResponse = mapper.scApprovingAuthorityDTOToObject(scApprovingAuthorityDTO, ScApprovingAuthorityResponse.class);
            scApprovingAuthorityResponse.setError(false);
        }
        log.info("Entity is ", scApprovingAuthorityDTO);
        return scApprovingAuthorityResponse;
    }

    public Map<String, Object> getScApprovingAuthorityByRoleId(Long roleId) {
        Map<String, Object> response = new HashMap<>();
        List<ScApprovingAuthorityDTO> scApprovingAuthorityList = scApprovingAuthorityRepository.getByRoleIdAndActive(roleId, true);
        if (scApprovingAuthorityList.isEmpty()) {
//            throw new ValidationException("Invalid Id");
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            response.put("success", false);
            return response;
        } else {
            log.info("Entity is ", scApprovingAuthorityList);
            response = convertListDTOToMapResponse(scApprovingAuthorityList);
            response.put("success", true);
            return response;

        }

    }

    private Map<String, Object> convertListToMapResponse(List<ScApprovingAuthority> scApprovingAuthorityList) {
        Map<String, Object> response = new HashMap<>();
        List<ScApprovingAuthorityResponse> scApprovingAuthorityResponses = scApprovingAuthorityList.stream()
                .map(scApprovingAuthority -> mapper.scApprovingAuthorityEntityToObject(scApprovingAuthority, ScApprovingAuthorityResponse.class)).collect(Collectors.toList());
        response.put("scApprovingAuthority", scApprovingAuthorityResponses);
        response.put("totalItems", scApprovingAuthorityList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<ScApprovingAuthorityDTO> scApprovingAuthorityList) {
        Map<String, Object> response = new HashMap<>();
        List<ScApprovingAuthorityResponse> scApprovingAuthorityResponses = scApprovingAuthorityList.stream()
                .map(scApprovingAuthority -> mapper.scApprovingAuthorityDTOToObject(scApprovingAuthority,ScApprovingAuthorityResponse.class)).collect(Collectors.toList());
        response.put("scApprovingAuthority",scApprovingAuthorityResponses);
        response.put("totalItems", scApprovingAuthorityList.size());
        return response;
    }


    @Transactional
    public ScApprovingAuthorityResponse updateScApprovingAuthorityDetails(EditScApprovingAuthorityRequest scApprovingAuthorityRequest) {
        ScApprovingAuthorityResponse scApprovingAuthorityResponse = new ScApprovingAuthorityResponse();
        List<ScApprovingAuthority> ScApprovingAuthorityList = scApprovingAuthorityRepository.findByRoleIdAndScApprovingAuthorityIdIsNot(scApprovingAuthorityRequest.getRoleId(), scApprovingAuthorityRequest.getScApprovingAuthorityId());
        if (ScApprovingAuthorityList.size() > 0) {
            scApprovingAuthorityResponse.setError(true);
            scApprovingAuthorityResponse.setError_description("ScApprovingAuthority exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScApprovingAuthority scApprovingAuthority = scApprovingAuthorityRepository.findByScApprovingAuthorityIdAndActiveIn(scApprovingAuthorityRequest.getScApprovingAuthorityId(), Set.of(true, false));
            if (Objects.nonNull(scApprovingAuthority)) {
                scApprovingAuthority.setMinAmount(scApprovingAuthorityRequest.getMinAmount());
                scApprovingAuthority.setMaxAmount(scApprovingAuthorityRequest.getMaxAmount());
                scApprovingAuthority.setRoleId(scApprovingAuthorityRequest.getRoleId());
                scApprovingAuthority.setType(scApprovingAuthorityRequest.getType());

                scApprovingAuthority.setActive(true);
                ScApprovingAuthority scApprovingAuthority1 = scApprovingAuthorityRepository.save(scApprovingAuthority);
                scApprovingAuthorityResponse = mapper.scApprovingAuthorityEntityToObject(scApprovingAuthority1, ScApprovingAuthorityResponse.class);
                scApprovingAuthorityResponse.setError(false);
            } else {
                scApprovingAuthorityResponse.setError(true);
                scApprovingAuthorityResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scApprovingAuthorityResponse;
    }

    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("role.roleName");
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
        Page<ScApprovingAuthorityDTO> scApprovingAuthorityDTOS = scApprovingAuthorityRepository.getSortedScApprovingAuthority(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",scApprovingAuthorityDTOS);
        return convertPageableDTOToMapResponse(scApprovingAuthorityDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScApprovingAuthorityDTO> activeScApprovingAuthority) {
        Map<String, Object> response = new HashMap<>();

        List<ScApprovingAuthorityResponse> scApprovingAuthorityResponses = activeScApprovingAuthority.getContent().stream()
                .map(scApprovingAuthority -> mapper.scApprovingAuthorityDTOToObject(scApprovingAuthority,ScApprovingAuthorityResponse.class)).collect(Collectors.toList());
        response.put("ScApprovingAuthority",scApprovingAuthorityResponses);
        response.put("currentPage", activeScApprovingAuthority.getNumber());
        response.put("totalItems", activeScApprovingAuthority.getTotalElements());
        response.put("totalPages", activeScApprovingAuthority.getTotalPages());

        return response;
    }
}
