package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.divisionMaster.DivisionMasterRequest;
import com.sericulture.masterdata.model.api.divisionMaster.DivisionMasterResponse;
import com.sericulture.masterdata.model.api.divisionMaster.EditDivisionMasterRequest;
import com.sericulture.masterdata.model.entity.DivisionMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DivisionMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
@Slf4j
public class DivisionMasterService {

    @Autowired
    DivisionMasterRepository divisionMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DivisionMasterResponse getDivisionMasterDetails(String name){
        DivisionMasterResponse divisionMasterResponse = new DivisionMasterResponse();
        DivisionMaster divisionMaster= divisionMasterRepository.findByNameAndActive(name,true);
        if(divisionMaster==null){
            divisionMasterResponse.setError(true);
            divisionMasterResponse.setError_description("Program not found");
        }else{
            divisionMasterResponse = mapper.divisionMasterEntityToObject(divisionMaster,DivisionMasterResponse.class);
            divisionMasterResponse.setError(false);
        }
        log.info("Entity is ",divisionMaster);
        return divisionMasterResponse;
    }

    @Transactional
    public DivisionMasterResponse insertDivisionMasterDetails(DivisionMasterRequest divisionMasterRequest){
        DivisionMasterResponse divisionMasterResponse = new DivisionMasterResponse();
        DivisionMaster divisionMaster= mapper.divisionMasterObjectToEntity(divisionMasterRequest,DivisionMaster.class);
        validator.validate(divisionMaster);
        List<DivisionMaster> divisionMasterList = divisionMasterRepository.findByNameAndNameInKannada(divisionMasterRequest.getName(), divisionMasterRequest.getNameInKannada());
        if(!divisionMasterList.isEmpty() && divisionMasterList.stream().filter( DivisionMaster::getActive).findAny().isPresent()){
            divisionMasterResponse.setError(true);
            divisionMasterResponse.setError_description("Division  name already exist");
//        }
//        else if(! divisionMasterList.isEmpty() && divisionMasterList.stream().filter(Predicate.not( DivisionMaster::getActive)).findAny().isPresent()){
//            DivisionMasterResponse.setError(true);
//            DivisionMasterResponse.setError_description("Tr Program name already exist with inactive state");
        }else {
            divisionMasterResponse  = mapper.divisionMasterEntityToObject( divisionMasterRepository.save(divisionMaster), DivisionMasterResponse.class);
            divisionMasterResponse.setError(false);
        }
        return divisionMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDivisionMasterDetails(final Pageable pageable){
        return convertToMapResponse(divisionMasterRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(divisionMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<DivisionMaster> activeDivisionMasters) {
        Map<String, Object> response = new HashMap<>();

        List<DivisionMasterResponse> divisionMasterResponses= activeDivisionMasters.getContent().stream()
                .map(divisionMaster -> mapper.divisionMasterEntityToObject(divisionMaster,DivisionMasterResponse.class)).collect(Collectors.toList());
        response.put("DivisionMaster",divisionMasterResponses);
        response.put("currentPage", activeDivisionMasters.getNumber());
        response.put("totalItems", activeDivisionMasters.getTotalElements());
        response.put("totalPages", activeDivisionMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<DivisionMaster> activeDivisionMasters) {
        Map<String, Object> response = new HashMap<>();

        List<DivisionMasterResponse> divisionMasterResponses= activeDivisionMasters.stream()
                .map(divisionMaster -> mapper.divisionMasterEntityToObject(divisionMaster,DivisionMasterResponse.class)).collect(Collectors.toList());
        response.put("DivisionMaster",divisionMasterResponses);
        return response;
    }

    @Transactional
    public DivisionMasterResponse deleteDivisionMasterDetails(long id) {

        DivisionMasterResponse divisionMasterResponse = new DivisionMasterResponse();
        DivisionMaster divisionMaster= divisionMasterRepository.findByDivisionMasterIdAndActive(id, true);
        if (Objects.nonNull(divisionMaster)) {
            divisionMaster.setActive(false);
            divisionMasterResponse= mapper.divisionMasterEntityToObject(divisionMasterRepository.save(divisionMaster), DivisionMasterResponse.class);
            divisionMasterResponse.setError(false);
        } else {
            divisionMasterResponse.setError(true);
            divisionMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return divisionMasterResponse;
    }

    @Transactional
    public DivisionMasterResponse getById(int id){
        DivisionMasterResponse divisionMasterResponse = new DivisionMasterResponse();
        DivisionMaster divisionMaster= divisionMasterRepository.findByDivisionMasterIdAndActive(id, true);
        if(divisionMaster== null){
            divisionMasterResponse.setError(true);
            divisionMasterResponse.setError_description("Invalid id");
        }else{
            divisionMasterResponse =  mapper.divisionMasterEntityToObject(divisionMaster, DivisionMasterResponse.class);
            divisionMasterResponse.setError(false);
        }
        log.info("Entity is ",divisionMaster);
        return divisionMasterResponse;
    }

    @Transactional
    public DivisionMasterResponse updateDivisionMastersDetails(EditDivisionMasterRequest divisionMasterRequest){

        DivisionMasterResponse divisionMasterResponse = new DivisionMasterResponse();
        List<DivisionMaster> divisionMasterList = divisionMasterRepository. findByActiveAndNameAndNameInKannada(true,divisionMasterRequest.getName(), divisionMasterRequest.getNameInKannada());
        if(divisionMasterList.size()>0){
            divisionMasterResponse.setError(true);
            divisionMasterResponse.setError_description("Program already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            DivisionMaster divisionMaster = divisionMasterRepository.findByDivisionMasterIdAndActiveIn(divisionMasterRequest.getDivisionMasterId(), Set.of(true,false));
            if(Objects.nonNull(divisionMaster)){
                divisionMaster.setName( divisionMasterRequest.getName());
                divisionMaster.setNameInKannada( divisionMasterRequest.getNameInKannada());
                divisionMaster.setActive(true);
                DivisionMaster divisionMaster1= divisionMasterRepository.save(divisionMaster);
                divisionMasterResponse = mapper.divisionMasterEntityToObject(divisionMaster1, DivisionMasterResponse.class);
                divisionMasterResponse.setError(false);
            } else {
                divisionMasterResponse.setError(true);
                divisionMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return divisionMasterResponse;
    }


}
