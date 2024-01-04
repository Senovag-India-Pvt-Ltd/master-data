package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.district.EditTalukRequest;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.taluk.TalukRequest;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.taluk.TalukRequest;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.taluk.TalukRequest;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.DistrictDTO;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TalukRepository;
import com.sericulture.masterdata.repository.TalukRepository;
import com.sericulture.masterdata.repository.TalukRepository;
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
public class TalukService {

    @Autowired
    TalukRepository talukRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TalukResponse getTalukDetails(String talukName) {
        TalukResponse talukResponse = new TalukResponse();
        Taluk taluk = null;
        if (taluk == null) {
            taluk = talukRepository.findByTalukNameAndActive(talukName, true);
            talukResponse = mapper.talukEntityToObject(taluk, TalukResponse.class);
            talukResponse.setError(false);
        } else {
            talukResponse.setError(true);
            talukResponse.setError_description("Taluk not found");
        }
        log.info("Entity is ", taluk);
        return talukResponse;
    }

    @Transactional
    public TalukResponse insertTalukDetails(TalukRequest talukRequest) {
        TalukResponse talukResponse = new TalukResponse();
        Taluk taluk = mapper.talukObjectToEntity(talukRequest, Taluk.class);
        validator.validate(taluk);
        List<Taluk> talukList = talukRepository.findByTalukName(talukRequest.getTalukName());
        if (!talukList.isEmpty() && talukList.stream().filter(Taluk::getActive).findAny().isPresent()) {
//            throw new ValidationException("Taluk name already exist with this state");
            talukResponse.setError(true);
            talukResponse.setError_description("Taluk name already exist");
        } else if (!talukList.isEmpty() && talukList.stream().filter(Predicate.not(Taluk::getActive)).findAny().isPresent()) {
            //throw new ValidationException("Village name already exist with inactive state");
            talukResponse.setError(true);
            talukResponse.setError_description("Taluk name already exist with inactive state");
        } else {
            talukResponse = mapper.talukEntityToObject(talukRepository.save(taluk), TalukResponse.class);
            talukResponse.setError(false);
        }
        return talukResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getPaginatedTalukDetails(final Pageable pageable) {
        return convertToMapResponse(talukRepository.findByActiveOrderByTalukIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(talukRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Taluk> activeTaluks) {
        Map<String, Object> response = new HashMap<>();

        List<TalukResponse> talukResponses = activeTaluks.getContent().stream()
                .map(taluk -> mapper.talukEntityToObject(taluk, TalukResponse.class)).collect(Collectors.toList());
        response.put("taluk", talukResponses);
        response.put("currentPage", activeTaluks.getNumber());
        response.put("totalItems", activeTaluks.getTotalElements());
        response.put("totalPages", activeTaluks.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Taluk> activeTaluks) {
        Map<String, Object> response = new HashMap<>();

        List<TalukResponse> talukResponses = activeTaluks.stream()
                .map(taluk -> mapper.talukEntityToObject(taluk, TalukResponse.class)).collect(Collectors.toList());
        response.put("taluk", talukResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getPaginatedTalukDetailsWithJoin(final Pageable pageable) {
        return convertDTOToMapResponse(talukRepository.getByActiveOrderByTalukIdAsc(true, pageable));
    }


    private Map<String, Object> convertDTOToMapResponse(final Page<TalukDTO> activeTaluks) {
        Map<String, Object> response = new HashMap<>();

        List<TalukResponse> talukResponses = activeTaluks.getContent().stream()
                .map(taluk -> mapper.talukDTOToObject(taluk, TalukResponse.class)).collect(Collectors.toList());
        response.put("taluk", talukResponses);
        response.put("currentPage", activeTaluks.getNumber());
        response.put("totalItems", activeTaluks.getTotalElements());
        response.put("totalPages", activeTaluks.getTotalPages());
        return response;
    }

    @Transactional
    public TalukResponse deleteTalukDetails(long id) {
        TalukResponse talukResponse = new TalukResponse();
        Taluk taluk = talukRepository.findByTalukIdAndActive(id, true);
        if (Objects.nonNull(taluk)) {
            taluk.setActive(false);
//            talukRepository.save(taluk);
            talukResponse = mapper.talukEntityToObject(talukRepository.save(taluk), TalukResponse.class);
            talukResponse.setError(false);
        } else {
            talukResponse.setError(true);
            talukResponse.setError_description("Invalid Id");
//            throw new ValidationException("Invalid Id");
        }
        return talukResponse;
    }

    @Transactional
    public TalukResponse getById(int id) {
        TalukResponse talukResponse = new TalukResponse();
        Taluk taluk = talukRepository.findByTalukIdAndActive(id, true);
        if (taluk == null) {
//            throw new ValidationException("Invalid Id");
            talukResponse.setError(true);
            talukResponse.setError_description("Invalid id");
        } else {
            talukResponse = mapper.talukEntityToObject(taluk, TalukResponse.class);
            talukResponse.setError(false);
        }
        log.info("Entity is ", taluk);
        return talukResponse;
    }

    @Transactional
    public TalukResponse getByIdJoin(int id) {
        TalukResponse talukResponse = new TalukResponse();
        TalukDTO talukDTO = talukRepository.getByTalukIdAndActive(id, true);
        if (talukDTO == null) {
//            throw new ValidationException("Invalid Id");
            talukResponse.setError(true);
            talukResponse.setError_description("Invalid id");
        } else {
            talukResponse = mapper.talukDTOToObject(talukDTO, TalukResponse.class);
            talukResponse.setError(false);
        }
        log.info("Entity is ", talukDTO);
        return talukResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getTalukByDistrictId(Long districtId) {
        Map<String, Object> response = new HashMap<>();
        List<Taluk> talukList = talukRepository.findByDistrictIdAndActive(districtId, true);
        if (talukList.isEmpty()) {
//            throw new ValidationException("Invalid Id");
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            response.put("success", false);
            return response;
        } else {
            log.info("Entity is ", talukList);
            response = convertListToMapResponse(talukList);
            response.put("success", true);
            return response;

        }

    }

    private Map<String, Object> convertListToMapResponse(List<Taluk> talukList) {
        Map<String, Object> response = new HashMap<>();
        List<TalukResponse> talukResponses = talukList.stream()
                .map(taluk -> mapper.talukEntityToObject(taluk, TalukResponse.class)).collect(Collectors.toList());
        response.put("taluk", talukResponses);
        response.put("totalItems", talukList.size());
        return response;
    }

    @Transactional
    public TalukResponse updateTalukDetails(EditTalukRequest talukRequest) {
        TalukResponse talukResponse = new TalukResponse();
        List<Taluk> talukList = talukRepository.findByTalukName(talukRequest.getTalukName());
        if (talukList.size() > 0) {
            talukResponse.setError(true);
            talukResponse.setError_description("Taluk already exists, duplicates are not allowed.");
        } else {
            Taluk taluk = talukRepository.findByTalukIdAndActiveIn(talukRequest.getTalukId(), Set.of(true, false));
            if (Objects.nonNull(taluk)) {
                taluk.setStateId(talukRequest.getStateId());
                taluk.setDistrictId(talukRequest.getDistrictId());
                taluk.setTalukId(talukRequest.getTalukId());
                taluk.setTalukName(talukRequest.getTalukName());
                Taluk updatedTaluk = talukRepository.save(taluk);
                talukResponse = mapper.talukEntityToObject(updatedTaluk, TalukResponse.class);
                talukResponse.setError(false);
            } else {
                talukResponse.setError(true);
                talukResponse.setError_description("Error occurred while fetching taluk");
            }
        }
        return talukResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("talukName");
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
        Page<TalukDTO> talukDTOS = talukRepository.getSortedTaluks(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",talukDTOS);
        return convertPageableDTOToMapResponse(talukDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TalukDTO> activeTaluks) {
        Map<String, Object> response = new HashMap<>();

        List<TalukResponse> talukResponses = activeTaluks.getContent().stream()
                .map(district -> mapper.talukDTOToObject(district,TalukResponse.class)).collect(Collectors.toList());
        response.put("district",talukResponses);
        response.put("currentPage", activeTaluks.getNumber());
        response.put("totalItems", activeTaluks.getTotalElements());
        response.put("totalPages", activeTaluks.getTotalPages());

        return response;
    }
}
