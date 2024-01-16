package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.hobli.EditHobliRequest;
import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.entity.Hobli;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HobliRepository;
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
public class HobliService {

    @Autowired
    HobliRepository hobliRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HobliResponse getHobliDetails(String hobliName){
        HobliResponse hobliResponse = new HobliResponse();
        Hobli hobli = null;
        if(hobli==null){
            hobli = hobliRepository.findByHobliNameAndActive(hobliName,true);
            hobliResponse = mapper.hobliEntityToObject(hobli, HobliResponse.class);
            hobliResponse.setError(false);
        }else{
            hobliResponse.setError(true);
            hobliResponse.setError_description("Hobli not found");
        }
        log.info("Entity is ",hobli);
        return hobliResponse;
    }

    @Transactional
    public HobliResponse insertHobliDetails(HobliRequest hobliRequest){
        HobliResponse hobliResponse = new HobliResponse();
        Hobli hobli = mapper.hobliObjectToEntity(hobliRequest,Hobli.class);
        validator.validate(hobli);
        List<Hobli> hobliList = hobliRepository.findByHobliNameAndTalukId(hobliRequest.getHobliName(), hobliRequest.getTalukId());
        if(!hobliList.isEmpty() && hobliList.stream().filter(Hobli::getActive).findAny().isPresent()){
            hobliResponse.setError(true);
            hobliResponse.setError_description("Hobli name already exist");
        }
        else if(!hobliList.isEmpty() && hobliList.stream().filter(Predicate.not(Hobli::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            hobliResponse.setError(true);
            hobliResponse.setError_description("Hobli name already exist with inactive state");
        }else {
            hobliResponse = mapper.hobliEntityToObject(hobliRepository.save(hobli), HobliResponse.class);
            hobliResponse.setError(false);
        }
        return hobliResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHobliDetails(final Pageable pageable){
        return convertToMapResponse(hobliRepository.findByActiveOrderByHobliIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hobliRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Hobli> activeHoblis) {
        Map<String, Object> response = new HashMap<>();

        List<HobliResponse> hobliResponses = activeHoblis.getContent().stream()
                .map(hobli -> mapper.hobliEntityToObject(hobli,HobliResponse.class)).collect(Collectors.toList());
        response.put("hobli",hobliResponses);
        response.put("currentPage", activeHoblis.getNumber());
        response.put("totalItems", activeHoblis.getTotalElements());
        response.put("totalPages", activeHoblis.getTotalPages());

        return response;
    }
    private Map<String, Object> convertListEntityToMapResponse(final List<Hobli> activeHoblis) {
        Map<String, Object> response = new HashMap<>();

        List<HobliResponse> hobliResponses = activeHoblis.stream()
                .map(hobli -> mapper.hobliEntityToObject(hobli,HobliResponse.class)).collect(Collectors.toList());
        response.put("hobli",hobliResponses);
        return response;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHobliDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(hobliRepository.getByActiveOrderByHobliIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<HobliDTO> activeHoblis) {
        Map<String, Object> response = new HashMap<>();

        List<HobliResponse> hobliResponses = activeHoblis.getContent().stream()
                .map(hobli -> mapper.hobliDTOToObject(hobli,HobliResponse.class)).collect(Collectors.toList());
        response.put("hobli",hobliResponses);
        response.put("currentPage", activeHoblis.getNumber());
        response.put("totalItems", activeHoblis.getTotalElements());
        response.put("totalPages", activeHoblis.getTotalPages());
        return response;
    }

    @Transactional
    public HobliResponse deleteHobliDetails(long id) {
        HobliResponse hobliResponse = new HobliResponse();
        Hobli hobli = hobliRepository.findByHobliIdAndActive(id, true);
        if (Objects.nonNull(hobli)) {
            hobli.setActive(false);
            hobliResponse = mapper.hobliEntityToObject(hobliRepository.save(hobli), HobliResponse.class);
            hobliResponse.setError(false);
        } else {
            hobliResponse.setError(true);
            hobliResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hobliResponse;
    }

    @Transactional
    public HobliResponse getById(int id){
        HobliResponse hobliResponse = new HobliResponse();
        Hobli hobli = hobliRepository.findByHobliIdAndActive(id,true);
        if(hobli == null){
            hobliResponse.setError(true);
            hobliResponse.setError_description("Invalid id");
        }else{
            hobliResponse =  mapper.hobliEntityToObject(hobli,HobliResponse.class);
            hobliResponse.setError(false);
        }
        log.info("Entity is ",hobli);
        return hobliResponse;
    }

    @Transactional
    public HobliResponse getByIdJoin(int id){
        HobliResponse hobliResponse = new HobliResponse();
        HobliDTO hobliDTO = hobliRepository.getByHobliIdAndActive(id,true);
        if(hobliDTO == null){
            hobliResponse.setError(true);
            hobliResponse.setError_description("Invalid id");
        } else {
            hobliResponse = mapper.hobliDTOToObject(hobliDTO, HobliResponse.class);
            hobliResponse.setError(false);
        }
        log.info("Entity is ", hobliDTO);
        return hobliResponse;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getHobliByTalukId(Long talukId){
        Map<String, Object> response = new HashMap<>();
        List<Hobli> hobliList = hobliRepository.findByTalukIdAndActive(talukId,true);
        if(hobliList.isEmpty()){
            response.put("error","Error");
            response.put("error_description","Invalid id");
            return response;
        }else {
            log.info("Entity is ", hobliList);
            response = convertListToMapResponse(hobliList);
            return response;
        }
    }

    private Map<String, Object> convertListToMapResponse(List<Hobli> hobliList) {
        Map<String, Object> response = new HashMap<>();
        List<HobliResponse> hobliResponses = hobliList.stream()
                .map(hobli -> mapper.hobliEntityToObject(hobli,HobliResponse.class)).collect(Collectors.toList());
        response.put("hobli",hobliResponses);
        response.put("totalItems", hobliList.size());
        return response;
    }

    @Transactional
    public HobliResponse updateHobliDetails(EditHobliRequest hobliRequest) {
        HobliResponse hobliResponse = new HobliResponse();
        List<Hobli> hobliList = hobliRepository.findByHobliName(hobliRequest.getHobliName());
        if (hobliList.size() > 0) {
            hobliResponse.setError(true);
            hobliResponse.setError_description("Hobli already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {
            Hobli hobli = hobliRepository.findByHobliIdAndActiveIn(hobliRequest.getHobliId(), Set.of(true, false));
            if (Objects.nonNull(hobli)) {
                hobli.setStateId(hobliRequest.getStateId());
                hobli.setHobliName(hobliRequest.getHobliName());
                hobli.setHobliNameInKannada(hobliRequest.getHobliNameInKannada());
                hobli.setActive(true);
                Hobli hobli1 = hobliRepository.save(hobli);
                hobliResponse = mapper.hobliEntityToObject(hobli1, HobliResponse.class);
                hobliResponse.setError(false);
            } else {
                hobliResponse.setError(true);
                hobliResponse.setError_description("Error occurred while fetching hobli");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hobliResponse;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("hobliName");
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
        Page<HobliDTO> hobliDTOS = hobliRepository.getSortedHoblis(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",hobliDTOS);
        return convertPageableDTOToMapResponse(hobliDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<HobliDTO> activeHoblis) {
        Map<String, Object> response = new HashMap<>();

        List<HobliResponse> hobliResponses = activeHoblis.getContent().stream()
                .map(hobli -> mapper.hobliDTOToObject(hobli,HobliResponse.class)).collect(Collectors.toList());
        response.put("hobli",hobliResponses);
        response.put("currentPage", activeHoblis.getNumber());
        response.put("totalItems", activeHoblis.getTotalElements());
        response.put("totalPages", activeHoblis.getTotalPages());

        return response;
    }
}