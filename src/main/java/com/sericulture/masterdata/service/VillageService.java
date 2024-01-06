package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.village.EditVillageRequest;
import com.sericulture.masterdata.model.api.village.VillageRequest;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.entity.Hobli;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.VillageRepository;
import com.sericulture.masterdata.repository.StateRepository;
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
public class VillageService {

    @Autowired
    VillageRepository villageRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public VillageResponse getVillageDetails(String villageName){
        VillageResponse villageResponse = new VillageResponse();
        Village village = null;
        if(village==null){
            village = villageRepository.findByVillageNameAndActive(villageName,true);
            villageResponse = mapper.villageEntityToObject(village,VillageResponse.class);
            villageResponse.setError(false);
        }else{
            villageResponse.setError(true);
            villageResponse.setError_description("Village not found");
        }
        log.info("Entity is ",village);
        return villageResponse;
    }

    @Transactional
    public VillageResponse insertVillageDetails(VillageRequest villageRequest){
        VillageResponse villageResponse = new VillageResponse();
        Village village = mapper.villageObjectToEntity(villageRequest,Village.class);
        validator.validate(village);
        List<Village> villageList = villageRepository.findByVillageName(villageRequest.getVillageName());
        if(!villageList.isEmpty() && villageList.stream().filter(Village::getActive).findAny().isPresent()){
           // throw new ValidationException("Village name already exist");
            villageResponse.setError(true);
            villageResponse.setError_description("Village name already exist");
        }
        else if(!villageList.isEmpty() && villageList.stream().filter(Predicate.not(Village::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            villageResponse.setError(true);
            villageResponse.setError_description("Village name already exist with inactive state");
        }else {
            villageResponse = mapper.villageEntityToObject(villageRepository.save(village), VillageResponse.class);
            villageResponse.setError(false);
        }
        return villageResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedVillageDetails(final Pageable pageable){
        return convertToMapResponse(villageRepository.findByActiveOrderByVillageIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(villageRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Village> activeVillages) {
        Map<String, Object> response = new HashMap<>();

        List<VillageResponse> villageResponses = activeVillages.getContent().stream()
                .map(village -> mapper.villageEntityToObject(village,VillageResponse.class)).collect(Collectors.toList());
        response.put("village",villageResponses);
        response.put("currentPage", activeVillages.getNumber());
        response.put("totalItems", activeVillages.getTotalElements());
        response.put("totalPages", activeVillages.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Village> activeVillages) {
        Map<String, Object> response = new HashMap<>();

        List<VillageResponse> villageResponses = activeVillages.stream()
                .map(village -> mapper.villageEntityToObject(village,VillageResponse.class)).collect(Collectors.toList());
        response.put("village",villageResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedVillageDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(villageRepository.getByActiveOrderByVillageIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<VillageDTO> activeVillages) {
        Map<String, Object> response = new HashMap<>();

        List<VillageResponse> villageResponses = activeVillages.getContent().stream()
                .map(village -> mapper.villageDTOToObject(village,VillageResponse.class)).collect(Collectors.toList());
        response.put("village",villageResponses);
        response.put("currentPage", activeVillages.getNumber());
        response.put("totalItems", activeVillages.getTotalElements());
        response.put("totalPages", activeVillages.getTotalPages());
        return response;
    }

    @Transactional
    public VillageResponse deleteVillageDetails(long id) {
        VillageResponse villageResponse = new VillageResponse();
        Village village = villageRepository.findByVillageIdAndActive(id, true);
        if (Objects.nonNull(village)) {
            village.setActive(false);
            //villageRepository.save(village);
            villageResponse = mapper.villageEntityToObject(villageRepository.save(village), VillageResponse.class);
            villageResponse.setError(false);
        } else {
            villageResponse.setError(true);
            villageResponse.setError_description("Invalid Id");
           // throw new ValidationException("Invalid Id");
        }
        return villageResponse;
    }

    @Transactional
    public VillageResponse getById(int id){
        VillageResponse villageResponse = new VillageResponse();
        Village village = villageRepository.findByVillageIdAndActive(id,true);
        if(village == null){
            //throw new ValidationException("Invalid Id");
            villageResponse.setError(true);
            villageResponse.setError_description("Invalid id");
        }else{
            villageResponse =  mapper.villageEntityToObject(village,VillageResponse.class);
            villageResponse.setError(false);
        }
        log.info("Entity is ",village);
        return villageResponse;
    }

    @Transactional
    public VillageResponse getByIdJoin(int id) {
        VillageResponse villageResponse = new VillageResponse();
        VillageDTO villageDTO = villageRepository.getByVillageIdAndActive(id, true);
        if (villageDTO == null) {
           // throw new ValidationException("Invalid Id");
            villageResponse.setError(true);
            villageResponse.setError_description("Invalid id");
        } else {
            villageResponse = mapper.villageDTOToObject(villageDTO, VillageResponse.class);
            villageResponse.setError(false);
        }
        log.info("Entity is ", villageDTO);
        return villageResponse;
    }
        @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getVillageByHobliId(Long hobliId){
        Map<String, Object> response = new HashMap<>();
        List<Village> villageList = villageRepository.findByHobliIdAndActive(hobliId,true);
        if(villageList.isEmpty()){
//            throw new ValidationException("Invalid Id");
            response.put("error","Error");
            response.put("error_description","Invalid id");
            return response;
        }else {
            log.info("Entity is ", villageList);
            response = convertListToMapResponse(villageList);
            return response;
        }
    }

    private Map<String, Object> convertListToMapResponse(List<Village> villageList) {
        Map<String, Object> response = new HashMap<>();
        List<VillageResponse> villageResponses = villageList.stream()
                .map(village -> mapper.villageEntityToObject(village,VillageResponse.class)).collect(Collectors.toList());
        response.put("village",villageResponses);
        response.put("totalItems", villageList.size());
        return response;
    }

    @Transactional
    public VillageResponse updateVillageDetails(EditVillageRequest villageRequest){
        VillageResponse villageResponse = new VillageResponse();
        List<Village> villageList = villageRepository.findByVillageName(villageRequest.getVillageName());
        if(villageList.size()>0){
            villageResponse.setError(true);
            villageResponse.setError_description("Village already exists, duplicates are not allowed.");
           // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {

            Village village = villageRepository.findByVillageIdAndActiveIn(villageRequest.getVillageId(), Set.of(true, false));
            if (Objects.nonNull(village)) {
                village.setStateId(villageRequest.getStateId());
                village.setDistrictId(villageRequest.getDistrictId());
                village.setTalukId(villageRequest.getTalukId());
                village.setHobliId(villageRequest.getHobliId());
                village.setVillageName(villageRequest.getVillageName());
                village.setActive(true);
                Village village1 = villageRepository.save(village);
                villageResponse = mapper.villageEntityToObject(village1, VillageResponse.class);
                villageResponse.setError(false);
            } else {
                villageResponse.setError(true);
                villageResponse.setError_description("Error occurred while fetching village");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return villageResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public VillageResponse getDetailsByVillageName(String villageName) {
        VillageResponse villageResponse = new VillageResponse();
        Village village = villageRepository.findByVillageNameAndActive(villageName, true);
        if (village == null) {
            villageResponse.setError(true);
            villageResponse.setError_description("Village not found");
            return villageResponse;
        } else {
            villageResponse = mapper.villageDTOToObject(villageRepository.getByVillageIdAndActive(village.getVillageId(), true), VillageResponse.class);
            villageResponse.setError(false);
            return villageResponse;
            // return mapper.villageDTOToObject(villageRepository.getByVillageIdAndActive(village.getVillageId(), true), VillageResponse.class);
        }
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("villageName");
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
        Page<VillageDTO> villageDTOS = villageRepository.getSortedVillages(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",villageDTOS);
        return convertPageableDTOToMapResponse(villageDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<VillageDTO> activeVillages) {
        Map<String, Object> response = new HashMap<>();

        List<VillageResponse> villageResponses = activeVillages.getContent().stream()
                .map(hobli -> mapper.villageDTOToObject(hobli,VillageResponse.class)).collect(Collectors.toList());
        response.put("hobli",villageResponses);
        response.put("currentPage", activeVillages.getNumber());
        response.put("totalItems", activeVillages.getTotalElements());
        response.put("totalPages", activeVillages.getTotalPages());

        return response;
    }
}