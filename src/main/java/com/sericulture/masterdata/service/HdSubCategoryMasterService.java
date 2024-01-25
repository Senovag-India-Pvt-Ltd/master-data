package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.EditHdCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterResponse;
import com.sericulture.masterdata.model.api.hdSubCategoryMaster.EditHdSubCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdSubCategoryMaster.HdSubCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdSubCategoryMaster.HdSubCategoryMasterResponse;
import com.sericulture.masterdata.model.dto.HdCategoryMasterDTO;
import com.sericulture.masterdata.model.dto.HdSubCategoryMasterDTO;
import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import com.sericulture.masterdata.model.entity.HdSubCategoryMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdCategoryMasterRepository;
import com.sericulture.masterdata.repository.HdSubCategoryMasterRepository;
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
public class HdSubCategoryMasterService {
    @Autowired
    HdSubCategoryMasterRepository hdSubCategoryMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdSubCategoryMasterResponse getHdSubCategoryMasterDetails(String hdSubCategoryName) {
        HdSubCategoryMasterResponse hdSubCategoryMasterResponse = new HdSubCategoryMasterResponse();
        HdSubCategoryMaster hdSubCategoryMaster = null;
        if (hdSubCategoryMaster == null) {
            hdSubCategoryMaster = hdSubCategoryMasterRepository.findByHdSubCategoryNameAndActive(hdSubCategoryName, true);
            hdSubCategoryMasterResponse = mapper.hdSubCategoryMasterEntityToObject(hdSubCategoryMaster, HdSubCategoryMasterResponse.class);
            hdSubCategoryMasterResponse.setError(false);
        } else {
            hdSubCategoryMasterResponse.setError(true);
            hdSubCategoryMasterResponse.setError_description("Hd Sub CategoryMaster not found");
        }
        log.info("Entity is ", hdSubCategoryMaster);
        return hdSubCategoryMasterResponse;

    }

    @Transactional
    public HdSubCategoryMasterResponse insertHdSubCategoryMasterDetails(HdSubCategoryMasterRequest hdSubCategoryMasterRequest) {
        HdSubCategoryMasterResponse hdSubCategoryMasterResponse = new HdSubCategoryMasterResponse();
        HdSubCategoryMaster hdSubCategoryMaster = mapper.hdSubCategoryMasterObjectToEntity(hdSubCategoryMasterRequest, HdSubCategoryMaster.class);
        validator.validate(hdSubCategoryMaster);
        List<HdSubCategoryMaster> hdSubCategoryMasterList = hdSubCategoryMasterRepository.findByHdSubCategoryNameAndHdCategoryId(hdSubCategoryMasterRequest.getHdSubCategoryName(),hdSubCategoryMasterRequest.getHdCategoryId());
        if (!hdSubCategoryMasterList.isEmpty() && hdSubCategoryMasterList.stream().filter(HdSubCategoryMaster::getActive).findAny().isPresent()) {
            hdSubCategoryMasterResponse.setError(true);
            hdSubCategoryMasterResponse.setError_description("Hd SubCategory name already exist");
        } else if (!hdSubCategoryMasterList.isEmpty() && hdSubCategoryMasterList.stream().filter(Predicate.not(HdSubCategoryMaster::getActive)).findAny().isPresent()) {
            hdSubCategoryMasterResponse.setError(true);
            hdSubCategoryMasterResponse.setError_description("Hd SubCategory name already exist with inactive state");
        } else {
            hdSubCategoryMasterResponse = mapper.hdSubCategoryMasterEntityToObject(hdSubCategoryMasterRepository.save(hdSubCategoryMaster), HdSubCategoryMasterResponse.class);
            hdSubCategoryMasterResponse.setError(false);
        }
        return hdSubCategoryMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getPaginatedHdSubCategoryMasterDetails(final Pageable pageable) {
        return convertToMapResponse(hdSubCategoryMasterRepository.findByActiveOrderByHdSubCategoryIdAsc(true, pageable));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(hdSubCategoryMasterRepository.findByActiveOrderByHdSubCategoryNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdSubCategoryMaster> activeHdSubCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdSubCategoryMasterResponse> hdSubCategoryMasterResponses = activeHdSubCategoryMasters.getContent().stream()
                .map(hdSubCategoryMaster -> mapper.hdSubCategoryMasterEntityToObject(hdSubCategoryMaster, HdSubCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdSubCategoryMaster", hdSubCategoryMasterResponses);
        response.put("currentPage", activeHdSubCategoryMasters.getNumber());
        response.put("totalItems", activeHdSubCategoryMasters.getTotalElements());
        response.put("totalPages", activeHdSubCategoryMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdSubCategoryMaster> activeHdSubCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdSubCategoryMasterResponse> hdSubCategoryMasterResponses = activeHdSubCategoryMasters.stream()
                .map(hdSubCategoryMaster -> mapper.hdSubCategoryMasterEntityToObject(hdSubCategoryMaster, HdSubCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdSubCategoryMaster", hdSubCategoryMasterResponses);
        return response;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> getPaginatedHdSubCategoryMasterDetailsWithJoin(final Pageable pageable) {
        return convertDTOToMapResponse(hdSubCategoryMasterRepository.getByActiveOrderByHdSubCategoryIdAsc(true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<HdSubCategoryMasterDTO> activeHdSubCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdSubCategoryMasterResponse> hdSubCategoryMasterResponses = activeHdSubCategoryMasters.getContent().stream()
                .map(hdSubCategoryMaster -> mapper.hdSubCategoryMasterDTOToObject(hdSubCategoryMaster, HdSubCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdSubCategoryMaster", hdSubCategoryMasterResponses);
        response.put("currentPage", activeHdSubCategoryMasters.getNumber());
        response.put("totalItems", activeHdSubCategoryMasters.getTotalElements());
        response.put("totalPages", activeHdSubCategoryMasters.getTotalPages());
        return response;
    }

    @Transactional
    public HdSubCategoryMasterResponse deleteHdSubCategoryMasterDetails(long id) {

        HdSubCategoryMasterResponse hdSubCategoryMasterResponse = new HdSubCategoryMasterResponse();
        HdSubCategoryMaster hdSubCategoryMaster = hdSubCategoryMasterRepository.findByHdSubCategoryIdAndActive(id, true);
        if (Objects.nonNull(hdSubCategoryMaster)) {
            hdSubCategoryMaster.setActive(false);
            hdSubCategoryMasterResponse = mapper.hdSubCategoryMasterEntityToObject(hdSubCategoryMasterRepository.save(hdSubCategoryMaster), HdSubCategoryMasterResponse.class);
            hdSubCategoryMasterResponse.setError(false);
        } else {
            hdSubCategoryMasterResponse.setError(true);
            hdSubCategoryMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdSubCategoryMasterResponse;
    }

    @Transactional
    public HdSubCategoryMasterResponse getById(int id) {
        HdSubCategoryMasterResponse hdSubCategoryMasterResponse = new HdSubCategoryMasterResponse();
        HdSubCategoryMaster hdSubCategoryMaster = hdSubCategoryMasterRepository.findByHdSubCategoryIdAndActive(id, true);
        if (hdSubCategoryMaster == null) {
            hdSubCategoryMasterResponse.setError(true);
            hdSubCategoryMasterResponse.setError_description("Invalid id");
        } else {
            hdSubCategoryMasterResponse = mapper.hdSubCategoryMasterEntityToObject(hdSubCategoryMaster, HdSubCategoryMasterResponse.class);
            hdSubCategoryMasterResponse.setError(false);
        }
        log.info("Entity is ", hdSubCategoryMaster);
        return hdSubCategoryMasterResponse;
    }

    @Transactional
    public HdSubCategoryMasterResponse getByIdJoin(int id) {
        HdSubCategoryMasterResponse hdSubCategoryMasterResponse = new HdSubCategoryMasterResponse();
        HdSubCategoryMasterDTO hdSubCategoryMasterDTO = hdSubCategoryMasterRepository.getByHdSubCategoryIdAndActive(id, true);
        if (hdSubCategoryMasterDTO == null) {
            hdSubCategoryMasterResponse.setError(true);
            hdSubCategoryMasterResponse.setError_description("Invalid id");
        } else {
            hdSubCategoryMasterResponse = mapper.hdSubCategoryMasterDTOToObject(hdSubCategoryMasterDTO, HdSubCategoryMasterResponse.class);
            hdSubCategoryMasterResponse.setError(false);
        }
        log.info("Entity is ", hdSubCategoryMasterDTO);
        return hdSubCategoryMasterResponse;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getByHdCategoryId(int hdCategoryId){
        Map<String, Object> response = new HashMap<>();
        List<HdSubCategoryMaster> hdSubCategoryMasterList = hdSubCategoryMasterRepository.findByHdCategoryIdAndActive(hdCategoryId,true);
        if(hdSubCategoryMasterList.isEmpty()){
            response.put("error","Error");
            response.put("error_description","Invalid id");
            return response;
        }else {
            log.info("Entity is ", hdSubCategoryMasterList);
            response = convertListToMapResponse(hdSubCategoryMasterList);
            return response;
        }
    }
    private Map<String, Object> convertListToMapResponse(List<HdSubCategoryMaster> hdSubCategoryMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<HdSubCategoryMasterResponse> hdSubCategoryMasterResponses = hdSubCategoryMasterList.stream()
                .map(hdSubCategoryMaster -> mapper.hdSubCategoryMasterEntityToObject(hdSubCategoryMaster, HdSubCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdSubCategoryMaster", hdSubCategoryMasterResponses);
        response.put("totalItems", hdSubCategoryMasterResponses.size());
        return response;
    }

    @Transactional
    public HdSubCategoryMasterResponse updateHdSubCategoryMasterDetails(EditHdSubCategoryMasterRequest hdSubCategoryMasterRequest) {

        HdSubCategoryMasterResponse hdSubCategoryMasterResponse = new HdSubCategoryMasterResponse();
        List<HdSubCategoryMaster> hdSubCategoryMasterList = hdSubCategoryMasterRepository.findByHdSubCategoryName(hdSubCategoryMasterRequest.getHdSubCategoryName());
        if (hdSubCategoryMasterList.size() > 0) {
            hdSubCategoryMasterResponse.setError(true);
            hdSubCategoryMasterResponse.setError_description("hd SubcategoryMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {


            HdSubCategoryMaster hdSubCategoryMaster = hdSubCategoryMasterRepository.findByHdSubCategoryIdAndActiveIn(hdSubCategoryMasterRequest.getHdSubCategoryId(), Set.of(true, false));
            if (Objects.nonNull(hdSubCategoryMaster)) {
                hdSubCategoryMaster.setHdSubCategoryName(hdSubCategoryMasterRequest.getHdSubCategoryName());
                hdSubCategoryMaster.setHdCategoryId(hdSubCategoryMasterRequest.getHdCategoryId());
                hdSubCategoryMaster.setHdBoardCategoryId(hdSubCategoryMasterRequest.getHdBoardCategoryId());

                hdSubCategoryMaster.setActive(true);
                HdSubCategoryMaster hdSubCategoryMaster1 = hdSubCategoryMasterRepository.save(hdSubCategoryMaster);
                hdSubCategoryMasterResponse = mapper.hdSubCategoryMasterEntityToObject(hdSubCategoryMaster1, HdSubCategoryMasterResponse.class);
                hdSubCategoryMasterResponse.setError(false);
            } else {
                hdSubCategoryMasterResponse.setError(true);
                hdSubCategoryMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdSubCategoryMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest) {
        if (searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")) {
            searchWithSortRequest.setSearchText("%%");
        } else {
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if (searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")) {
            searchWithSortRequest.setSortColumn("hdSubCategoryName");
        }
        if (searchWithSortRequest.getSortOrder() == null || searchWithSortRequest.getSortOrder().equals("")) {
            searchWithSortRequest.setSortOrder("asc");
        }
        if (searchWithSortRequest.getPageNumber() == null || searchWithSortRequest.getPageNumber().equals("")) {
            searchWithSortRequest.setPageNumber("0");
        }
        if (searchWithSortRequest.getPageSize() == null || searchWithSortRequest.getPageSize().equals("")) {
            searchWithSortRequest.setPageSize("5");
        }
        Sort sort;
        if (searchWithSortRequest.getSortOrder().equals("asc")) {
            sort = Sort.by(Sort.Direction.ASC, searchWithSortRequest.getSortColumn());
        } else {
            sort = Sort.by(Sort.Direction.DESC, searchWithSortRequest.getSortColumn());
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(searchWithSortRequest.getPageNumber()), Integer.parseInt(searchWithSortRequest.getPageSize()), sort);
        Page<HdSubCategoryMasterDTO> hdSubCategoryMasterDTOS = hdSubCategoryMasterRepository.getSortedHdSubCategoryMasters(searchWithSortRequest.getJoinColumn(), searchWithSortRequest.getSearchText(), true, pageable);
        log.info("Entity is ", hdSubCategoryMasterDTOS);
        return convertPageableDTOToMapResponse(hdSubCategoryMasterDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<HdSubCategoryMasterDTO> activeHdSubCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdSubCategoryMasterResponse> hdSubCategoryMasterResponses = activeHdSubCategoryMasters.getContent().stream()
                .map(hdSubCategoryMaster -> mapper.hdSubCategoryMasterDTOToObject(hdSubCategoryMaster, HdSubCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdSubCategoryMaster", hdSubCategoryMasterResponses);
        response.put("currentPage", activeHdSubCategoryMasters.getNumber());
        response.put("totalItems", activeHdSubCategoryMasters.getTotalElements());
        response.put("totalPages", activeHdSubCategoryMasters.getTotalPages());

        return response;
    }
}
