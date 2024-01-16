package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.district.DistrictRequest;
import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.district.EditDistrictRequest;
import com.sericulture.masterdata.model.api.district.DistrictRequest;
import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.district.EditDistrictRequest;
import com.sericulture.masterdata.model.api.district.DistrictRequest;
import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.DistrictDTO;
import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DistrictRepository;
import com.sericulture.masterdata.repository.DistrictRepository;
import com.sericulture.masterdata.repository.DistrictRepository;
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
public class DistrictService {

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DistrictResponse getDistrictDetails(String districtName){
        DistrictResponse districtResponse = new DistrictResponse();
        District district = null;
        if(district==null){
            district = districtRepository.findByDistrictNameAndActive(districtName,true);
            districtResponse = mapper.districtEntityToObject(district, DistrictResponse.class);
            districtResponse.setError(false);
        }else{
            districtResponse.setError(true);
            districtResponse.setError_description("district not found");
        }
        log.info("Entity is ",district);
        return districtResponse;
    }

    @Transactional
    public DistrictResponse insertDistrictDetails(DistrictRequest districtRequest){
        DistrictResponse districtResponse = new DistrictResponse();
        District district = mapper.districtObjectToEntity(districtRequest,District.class);
        validator.validate(district);
        List<District> districtList = districtRepository.findByDistrictNameAndStateId(districtRequest.getDistrictName(), districtRequest.getStateId());
        if(!districtList.isEmpty() && districtList.stream().filter(District::getActive).findAny().isPresent()){
            districtResponse.setError(true);
            districtResponse.setError_description("District name already exist");
        }
        else if(!districtList.isEmpty() && districtList.stream().filter(Predicate.not(District::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            districtResponse.setError(true);
            districtResponse.setError_description("District name already exist with inactive state");
        }else {
            districtResponse = mapper.districtEntityToObject(districtRepository.save(district), DistrictResponse.class);
            districtResponse.setError(false);
        }
        return districtResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDistrictDetails(final Pageable pageable){
        return convertToMapResponse(districtRepository.findByActiveOrderByDistrictIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(districtRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<District> activeDistricts) {
        Map<String, Object> response = new HashMap<>();

        List<DistrictResponse> districtResponses = activeDistricts.getContent().stream()
                .map(district -> mapper.districtEntityToObject(district,DistrictResponse.class)).collect(Collectors.toList());
        response.put("district",districtResponses);
        response.put("currentPage", activeDistricts.getNumber());
        response.put("totalItems", activeDistricts.getTotalElements());
        response.put("totalPages", activeDistricts.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<District> activeDistricts) {
        Map<String, Object> response = new HashMap<>();

        List<DistrictResponse> districtResponses = activeDistricts.stream()
                .map(district -> mapper.districtEntityToObject(district,DistrictResponse.class)).collect(Collectors.toList());
        response.put("district",districtResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDistrictDetailsWithStateName(final Pageable pageable){
        return convertDTOToMapResponse(districtRepository.getByActiveOrderByDistrictIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<DistrictDTO> activeDistricts) {
        Map<String, Object> response = new HashMap<>();

        List<DistrictResponse> districtResponses = activeDistricts.getContent().stream()
                .map(district -> mapper.districtDTOToObject(district,DistrictResponse.class)).collect(Collectors.toList());
        response.put("district",districtResponses);
        response.put("currentPage", activeDistricts.getNumber());
        response.put("totalItems", activeDistricts.getTotalElements());
        response.put("totalPages", activeDistricts.getTotalPages());
        return response;
    }

    @Transactional
    public DistrictResponse deleteDistrictDetails(long id) {
        DistrictResponse districtResponse = new DistrictResponse();
        District district = districtRepository.findByDistrictIdAndActive(id, true);
        if (Objects.nonNull(district)) {
            district.setActive(false);
            districtResponse = mapper.districtEntityToObject(districtRepository.save(district), DistrictResponse.class);
            districtResponse.setError(false);
        } else {
            districtResponse.setError(true);
            districtResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return districtResponse;
    }

    @Transactional
    public DistrictResponse getById(int id){
        DistrictResponse districtResponse = new DistrictResponse();
        District district = districtRepository.findByDistrictIdAndActive(id,true);
        if(district == null){
            districtResponse.setError(true);
            districtResponse.setError_description("Invalid id");
        }else{
            districtResponse =  mapper.districtEntityToObject(district,DistrictResponse.class);
            districtResponse.setError(false);
        }
        log.info("Entity is ",district);
        return districtResponse;
    }

    @Transactional
    public DistrictResponse getByIdJoin(int id){
        DistrictResponse districtResponse = new DistrictResponse();
        DistrictDTO districtDTO = districtRepository.getByDistrictIdAndActive(id,true);
        if(districtDTO == null){
            districtResponse.setError(true);
            districtResponse.setError_description("Invalid id");
        } else {
            districtResponse = mapper.districtDTOToObject(districtDTO, DistrictResponse.class);
            districtResponse.setError(false);
        }
        log.info("Entity is ", districtDTO);
        return districtResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getDistrictByStateId(Long stateId){
        List<District> districtList = districtRepository.findByStateIdAndActiveOrderByDistrictName(stateId,true);
        if(districtList.isEmpty()){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",districtList);
        return convertListToMapResponse(districtList);
    }

    private Map<String, Object> convertListToMapResponse(List<District> districtList) {
        Map<String, Object> response = new HashMap<>();
        List<DistrictResponse> districtResponses = districtList.stream()
                .map(district -> mapper.districtEntityToObject(district,DistrictResponse.class)).collect(Collectors.toList());
        response.put("district",districtResponses);
        response.put("totalItems", districtList.size());
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByName(String searchText){
        if(searchText != null && !searchText.equals("")){
            searchText = "%" + searchText + "%";
        }
        List<DistrictDTO> districtList = districtRepository.searchByDistrictNameAndActive(searchText,true);
        return convertDTOToMapResponse(districtList);
    }

    private Map<String, Object> convertDTOToMapResponse(List<DistrictDTO> districtList) {
        Map<String, Object> response = new HashMap<>();
        List<DistrictResponse> districtResponses = districtList.stream()
                .map(district -> mapper.districtDTOToObject(district,DistrictResponse.class)).collect(Collectors.toList());
        response.put("district",districtResponses);
        response.put("totalItems", districtList.size());
        return response;
    }

    @Transactional
    public DistrictResponse updateDistrictDetails(EditDistrictRequest districtRequest) {
        DistrictResponse districtResponse = new DistrictResponse();
        List<District> districtList = districtRepository.findByDistrictName(districtRequest.getDistrictName());
        if (districtList.size() > 0) {
            districtResponse.setError(true);
            districtResponse.setError_description("District already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            District district = districtRepository.findByDistrictIdAndActiveIn(districtRequest.getDistrictId(), Set.of(true, false));
//            State state = stateRepository.findByStateIdAndActive(districtRequest.getStateId(), true);
//            if (state == null) {
//                districtResponse.setError(true);
//                districtResponse.setError_description("State does not exist.");
//            } else {
                if (Objects.nonNull(district)) {
                    district.setStateId(districtRequest.getStateId());
                    district.setDistrictName(districtRequest.getDistrictName());
                    district.setDistrictNameInKannada(districtRequest.getDistrictNameInKannada());
                    district.setActive(true);
                    District district1 = districtRepository.save(district);
                    districtResponse = mapper.districtEntityToObject(district1, DistrictResponse.class);
                    districtResponse.setError(false);
                } else {
                    districtResponse.setError(true);
                    districtResponse.setError_description("Error occurred while fetching District");
                    // throw new ValidationException("Error occurred while fetching village");
                }
            }

        return districtResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("districtName");
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
        Page<DistrictDTO> districtList = districtRepository.getSortedDistricts(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",districtList);
        return convertPageableDTOToMapResponse(districtList);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<DistrictDTO> activeDistricts) {
        Map<String, Object> response = new HashMap<>();

        List<DistrictResponse> districtResponses = activeDistricts.getContent().stream()
                .map(district -> mapper.districtDTOToObject(district,DistrictResponse.class)).collect(Collectors.toList());
        response.put("district",districtResponses);
        response.put("currentPage", activeDistricts.getNumber());
        response.put("totalItems", activeDistricts.getTotalElements());
        response.put("totalPages", activeDistricts.getTotalPages());

        return response;
    }
}