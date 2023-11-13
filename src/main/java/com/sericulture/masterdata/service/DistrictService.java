package com.sericulture.masterdata.service;

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
import org.springframework.data.domain.Pageable;
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
        District district = null;
        if(district==null){
            district = districtRepository.findByDistrictNameAndActive(districtName,true);
        }
        log.info("Entity is ",district);
        return mapper.districtEntityToObject(district,DistrictResponse.class);
    }

    @Transactional
    public DistrictResponse insertDistrictDetails(DistrictRequest districtRequest){
        District district = mapper.districtObjectToEntity(districtRequest,District.class);
        validator.validate(district);
        List<District> districtList = districtRepository.findByDistrictNameAndStateId(districtRequest.getDistrictName(), districtRequest.getStateId());
        if(!districtList.isEmpty() && districtList.stream().filter(District::getActive).findAny().isPresent()){
            throw new ValidationException("District name already exist with this state");
        }
        if(!districtList.isEmpty() && districtList.stream().filter(Predicate.not(District::getActive)).findAny().isPresent()){
            throw new ValidationException("District name already exist with inactive district with this state");
        }
        State state = stateRepository.findByStateIdAndActive(district.getStateId(), true);
        if(state == null){
            throw new ValidationException("State does not exist.");
        }
        return mapper.districtEntityToObject(districtRepository.save(district),DistrictResponse.class);
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
    public void deleteDistrictDetails(long id) {
        District district = districtRepository.findByDistrictIdAndActive(id, true);
        if (Objects.nonNull(district)) {
            district.setActive(false);
            districtRepository.save(district);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public DistrictResponse getById(int id){
        District district = districtRepository.findByDistrictIdAndActive(id,true);
        if(district == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",district);
        return mapper.districtEntityToObject(district,DistrictResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getDistrictByStateId(Long stateId){
        List<District> districtList = districtRepository.findByStateIdAndActive(stateId,true);
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

    @Transactional
    public DistrictResponse updateDistrictDetails(EditDistrictRequest districtRequest){
        List<District> districtList = districtRepository.findByDistrictName(districtRequest.getDistrictName());
        if(districtList.size()>0){
            throw new ValidationException("District already exists with this state, duplicates are not allowed.");
        }

        District district = districtRepository.findByDistrictIdAndActiveIn(districtRequest.getDistrictId(), Set.of(true,false));
        State state = stateRepository.findByStateIdAndActive(districtRequest.getStateId(), true);
        if(state == null){
            throw new ValidationException("State does not exist.");
        }
        if(Objects.nonNull(district)){
            district.setStateId(districtRequest.getStateId());
            district.setDistrictName(districtRequest.getDistrictName());
            district.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching district");
        }
        return mapper.districtEntityToObject(districtRepository.save(district),DistrictResponse.class);
    }

}