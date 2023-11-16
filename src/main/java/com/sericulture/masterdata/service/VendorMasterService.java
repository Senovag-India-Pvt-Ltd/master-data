package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.state.EditStateRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.vendorMaster.EditVendorMasterRequest;
import com.sericulture.masterdata.model.api.vendorMaster.VendorMasterRequest;
import com.sericulture.masterdata.model.api.vendorMaster.VendorMasterResponse;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.VendorMaster;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.StateRepository;
import com.sericulture.masterdata.repository.VendorMasterRepository;
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
public class VendorMasterService {

    @Autowired
    VendorMasterRepository vendorMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public VendorMasterResponse getVendorMasterDetails(String vendorMasterName){
        VendorMaster vendorMaster = null;
        if(vendorMaster==null){
            vendorMaster = vendorMasterRepository.findByVendorMasterNameAndActive(vendorMasterName,true);
        }
        log.info("Entity is ",vendorMasterName);
        return mapper.vendorMasterEntityToObject(vendorMaster,VendorMasterResponse.class);
    }

    @Transactional
    public VendorMasterResponse insertVendorMasterDetails(VendorMasterRequest vendorMasterRequest){
        VendorMaster vendorMaster = mapper.vendorMasterObjectToEntity(vendorMasterRequest,VendorMaster.class);
        validator.validate(vendorMaster);
        List<VendorMaster> vendorMasterList = vendorMasterRepository.findByVendorMasterName(vendorMasterRequest.getVendorMasterName());
        if(!vendorMasterList.isEmpty() && vendorMasterList.stream().filter(VendorMaster::getActive).findAny().isPresent()){
            throw new ValidationException("Vendor name already exist");
        }
        if(!vendorMasterList.isEmpty() && vendorMasterList.stream().filter(Predicate.not(VendorMaster::getActive)).findAny().isPresent()){
            throw new ValidationException("Vendor name already exist with inactive state");
        }
        return mapper.vendorMasterEntityToObject(vendorMasterRepository.save(vendorMaster),VendorMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedVendorMasterDetails(final Pageable pageable){
        return convertToMapResponse(vendorMasterRepository.findByActiveOrderByVendorMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(vendorMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<VendorMaster> activeVendorMasters) {
        Map<String, Object> response = new HashMap<>();

        List<VendorMasterResponse> vendorMasterResponses = activeVendorMasters.getContent().stream()
                .map(vendorMaster -> mapper.vendorMasterEntityToObject(vendorMaster,VendorMasterResponse.class)).collect(Collectors.toList());
        response.put("vendorMaster",vendorMasterResponses);
        response.put("currentPage", activeVendorMasters.getNumber());
        response.put("totalItems", activeVendorMasters.getTotalElements());
        response.put("totalPages", activeVendorMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<VendorMaster> activeVendorMasters) {
        Map<String, Object> response = new HashMap<>();

        List<VendorMasterResponse> vendorMasterResponses = activeVendorMasters.stream()
                .map(vendorMaster -> mapper.vendorMasterEntityToObject(vendorMaster,VendorMasterResponse.class)).collect(Collectors.toList());
        response.put("vendorMaster",vendorMasterResponses);
        return response;
    }

    @Transactional
    public void deleteVendorMasterDetails(long id) {
        VendorMaster vendorMaster = vendorMasterRepository.findByVendorMasterIdAndActive(id, true);
        if (Objects.nonNull(vendorMaster)) {
            vendorMaster.setActive(false);
            vendorMasterRepository.save(vendorMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public VendorMasterResponse getById(int id){
        VendorMaster vendorMaster = vendorMasterRepository.findByVendorMasterIdAndActive(id,true);
        if(vendorMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",vendorMaster);
        return mapper.vendorMasterEntityToObject(vendorMaster,VendorMasterResponse.class);
    }

    @Transactional
    public VendorMasterResponse updateVendorMasterDetails(EditVendorMasterRequest vendorMasterRequest){
        List<VendorMaster> vendorMasterList = vendorMasterRepository.findByVendorMasterName(vendorMasterRequest.getVendorMasterName());
        if(vendorMasterList.size()>0){
            throw new ValidationException("Vendors already exists with this name, duplicates are not allowed.");
        }

        VendorMaster vendorMaster = vendorMasterRepository.findByVendorMasterIdAndActiveIn(vendorMasterRequest.getVendorMasterId(), Set.of(true,false));
        if(Objects.nonNull(vendorMaster)){
            vendorMaster.setVendorMasterName(vendorMasterRequest.getVendorMasterName());
            vendorMaster.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching state");
        }
        return mapper.vendorMasterEntityToObject(vendorMasterRepository.save(vendorMaster),VendorMasterResponse.class);
    }

}
