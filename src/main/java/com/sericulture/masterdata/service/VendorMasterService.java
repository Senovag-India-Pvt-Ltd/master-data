package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.state.EditStateRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.vendorMaster.EditVendorMasterRequest;
import com.sericulture.masterdata.model.api.vendorMaster.VendorMasterRequest;
import com.sericulture.masterdata.model.api.vendorMaster.VendorMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.VendorMaster;
import com.sericulture.masterdata.model.entity.Village;
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

    @Transactional
    public VendorMasterResponse insertVendorMasterDetails(VendorMasterRequest vendorMasterRequest){
        VendorMasterResponse vendorMasterResponse = new VendorMasterResponse();
        VendorMaster vendorMaster = mapper.vendorMasterObjectToEntity(vendorMasterRequest,VendorMaster.class);
        validator.validate(vendorMaster);
        List<VendorMaster> vendorMasterList = vendorMasterRepository.findByVendorMasterName(vendorMasterRequest.getVendorMasterName());
        if(!vendorMasterList.isEmpty() && vendorMasterList.stream().filter(VendorMaster::getActive).findAny().isPresent()){
            vendorMasterResponse.setError(true);
            vendorMasterResponse.setError_description("VendorMaster name already exist");
//        }
//        else if(!vendorMasterList.isEmpty() && vendorMasterList.stream().filter(Predicate.not(VendorMaster::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            vendorMasterResponse.setError(true);
//            vendorMasterResponse.setError_description("VendorMaster name already exist with inactive state");
        }else {
            vendorMasterResponse = mapper.vendorMasterEntityToObject(vendorMasterRepository.save(vendorMaster), VendorMasterResponse.class);
            vendorMasterResponse.setError(false);
        }
        return vendorMasterResponse;
    }

    public Map<String,Object> getPaginatedVendorMasterDetails(final Pageable pageable){
        return convertToMapResponse(vendorMasterRepository.findByActiveOrderByVendorMasterNameAsc( true, pageable));
    }

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
    public VendorMasterResponse deleteVendorMasterDetails(long id) {
        VendorMasterResponse vendorMasterResponse = new VendorMasterResponse();
        VendorMaster vendorMaster = vendorMasterRepository.findByVendorMasterIdAndActive(id, true);
        if (Objects.nonNull(vendorMaster)) {
            vendorMaster.setActive(false);
            vendorMasterResponse = mapper.vendorMasterEntityToObject(vendorMasterRepository.save(vendorMaster), VendorMasterResponse.class);
            vendorMasterResponse.setError(false);
        } else {
            vendorMasterResponse.setError(true);
            vendorMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return vendorMasterResponse;
    }

    public VendorMasterResponse getById(int id){
        VendorMasterResponse vendorMasterResponse = new VendorMasterResponse();
        VendorMaster vendorMaster = vendorMasterRepository.findByVendorMasterIdAndActive(id,true);
        if(vendorMaster == null){
            //throw new ValidationException("Invalid Id");
            vendorMasterResponse.setError(true);
            vendorMasterResponse.setError_description("Invalid id");
        }else{
            vendorMasterResponse =  mapper.vendorMasterEntityToObject(vendorMaster,VendorMasterResponse.class);
            vendorMasterResponse.setError(false);
        }
        log.info("Entity is ",vendorMaster);
        return vendorMasterResponse;
    }

    @Transactional
    public VendorMasterResponse updateVendorMasterDetails(EditVendorMasterRequest vendorMasterRequest) {
        VendorMasterResponse vendorMasterResponse = new VendorMasterResponse();
        List<VendorMaster> vendorMasterList = vendorMasterRepository.findByActiveAndVendorMasterName(true,vendorMasterRequest.getVendorMasterName());
        if (vendorMasterList.size() > 0) {
            vendorMasterResponse.setError(true);
            vendorMasterResponse.setError_description("VendorMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {
            VendorMaster vendorMaster = vendorMasterRepository.findByVendorMasterIdAndActiveIn(vendorMasterRequest.getVendorMasterId(), Set.of(true, false));
            if (Objects.nonNull(vendorMaster)) {
                vendorMaster.setVendorMasterName(vendorMasterRequest.getVendorMasterName());
                vendorMaster.setActive(true);
                VendorMaster vendorMaster1 = vendorMasterRepository.save(vendorMaster);
                vendorMasterResponse = mapper.vendorMasterEntityToObject(vendorMaster1, VendorMasterResponse.class);
                vendorMasterResponse.setError(false);
            } else {
                vendorMasterResponse.setError(true);
                vendorMasterResponse.setError_description("Error occurred while fetching VendorMaster");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return vendorMasterResponse;
    }
}
