package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.departmentMaster.DepartmentMasterRequest;
import com.sericulture.masterdata.model.api.departmentMaster.DepartmentMasterResponse;
import com.sericulture.masterdata.model.api.departmentMaster.EditDepartmentMasterRequest;
import com.sericulture.masterdata.model.entity.DepartmentMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DepartmentMasterRepository;
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
public class DepartmentMasterService {

    @Autowired
    DepartmentMasterRepository departmentMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;



    @Transactional
    public DepartmentMasterResponse insertDepartmentMasterDetails(DepartmentMasterRequest departmentMasterRequest){
        DepartmentMasterResponse departmentMasterResponse = new DepartmentMasterResponse();
        DepartmentMaster departmentMaster = mapper.departmentMasterObjectToEntity(departmentMasterRequest,DepartmentMaster.class);
        validator.validate(departmentMaster);
        List<DepartmentMaster> departmentMasterList = departmentMasterRepository.findByDepartmentNameAndDepartmentNameInKannada(departmentMasterRequest.getDepartmentName(),departmentMasterRequest.getDepartmentNameInKannada());
        if(!departmentMasterList.isEmpty() && departmentMasterList.stream().filter(DepartmentMaster::getActive).findAny().isPresent()){
            departmentMasterResponse.setError(true);
            departmentMasterResponse.setError_description("Department name already exist");
//        }
//        else if(!departmentMasterList.isEmpty() && departmentMasterList.stream().filter(Predicate.not(DepartmentMaster::getActive)).findAny().isPresent()){
//            departmentMasterResponse.setError(true);
//            departmentMasterResponse.setError_description("Department name already exist with inactive state");
        }else {
            departmentMasterResponse = mapper.departmentMasterEntityToObject(departmentMasterRepository.save(departmentMaster), DepartmentMasterResponse.class);
            departmentMasterResponse.setError(false);
        }
        return departmentMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDepartmentMasterDetails(final Pageable pageable){
        return convertToMapResponse(departmentMasterRepository.findByActiveOrderByDepartmentNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(departmentMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<DepartmentMaster> activeDepartmentMasters) {
        Map<String, Object> response = new HashMap<>();

        List<DepartmentMasterResponse> departmentMasterResponses = activeDepartmentMasters.getContent().stream()
                .map(departmentMaster -> mapper.departmentMasterEntityToObject(departmentMaster,DepartmentMasterResponse.class)).collect(Collectors.toList());
        response.put("departmentMaster",departmentMasterResponses);
        response.put("currentPage", activeDepartmentMasters.getNumber());
        response.put("totalItems", activeDepartmentMasters.getTotalElements());
        response.put("totalPages", activeDepartmentMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<DepartmentMaster> activeDepartmentMasters) {
        Map<String, Object> response = new HashMap<>();

        List<DepartmentMasterResponse> departmentMasterResponses = activeDepartmentMasters.stream()
                .map(departmentMaster -> mapper.departmentMasterEntityToObject(departmentMaster,DepartmentMasterResponse.class)).collect(Collectors.toList());
        response.put("departmentMaster",departmentMasterResponses);
        return response;
    }

    @Transactional
    public DepartmentMasterResponse deleteDepartmentMasterDetails(long id) {

        DepartmentMasterResponse departmentMasterResponse = new DepartmentMasterResponse();
        DepartmentMaster departmentMaster = departmentMasterRepository.findByDepartmentIdAndActive(id, true);
        if (Objects.nonNull(departmentMaster)) {
            departmentMaster.setActive(false);
            departmentMasterResponse = mapper.departmentMasterEntityToObject(departmentMasterRepository.save(departmentMaster), DepartmentMasterResponse.class);
            departmentMasterResponse.setError(false);
        } else {
            departmentMasterResponse.setError(true);
            departmentMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return departmentMasterResponse;
    }

    @Transactional
    public DepartmentMasterResponse getById(int id){
        DepartmentMasterResponse departmentMasterResponse = new DepartmentMasterResponse();
        DepartmentMaster departmentMaster = departmentMasterRepository.findByDepartmentIdAndActive(id,true);
        if(departmentMaster == null){
            departmentMasterResponse.setError(true);
            departmentMasterResponse.setError_description("Invalid id");
        }else{
            departmentMasterResponse =  mapper.departmentMasterEntityToObject(departmentMaster,DepartmentMasterResponse.class);
            departmentMasterResponse.setError(false);
        }
        log.info("Entity is ",departmentMaster);
        return departmentMasterResponse;
    }

    @Transactional
    public DepartmentMasterResponse updateDepartmentMasterDetails(EditDepartmentMasterRequest departmentMasterRequest){

        DepartmentMasterResponse departmentMasterResponse = new DepartmentMasterResponse();
        List<DepartmentMaster> departmentMasterList = departmentMasterRepository.findByDepartmentNameAndDepartmentNameInKannadaAndDepartmentIdIsNot(departmentMasterRequest.getDepartmentName(),departmentMasterRequest.getDepartmentNameInKannada(),departmentMasterRequest.getDepartmentId());
        if(departmentMasterList.size()>0){
            departmentMasterResponse.setError(true);
            departmentMasterResponse.setError_description("Department already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            DepartmentMaster departmentMaster = departmentMasterRepository.findByDepartmentIdAndActiveIn(departmentMasterRequest.getDepartmentId(), Set.of(true,false));
            if(Objects.nonNull(departmentMaster)){
                departmentMaster.setDepartmentCode(departmentMasterRequest.getDepartmentCode());
                departmentMaster.setDepartmentName(departmentMasterRequest.getDepartmentName());
                departmentMaster.setDepartmentDetails(departmentMasterRequest.getDepartmentDetails());
                departmentMaster.setDepartmentNameInKannada(departmentMasterRequest.getDepartmentNameInKannada());
                departmentMaster.setActive(true);
                DepartmentMaster departmentMaster1 = departmentMasterRepository.save(departmentMaster);
                departmentMasterResponse = mapper.departmentMasterEntityToObject(departmentMaster1, DepartmentMasterResponse.class);
                departmentMasterResponse.setError(false);
            } else {
                departmentMasterResponse.setError(true);
                departmentMasterResponse.setError_description("Error occurred while fetching department");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return departmentMasterResponse;
    }
}
