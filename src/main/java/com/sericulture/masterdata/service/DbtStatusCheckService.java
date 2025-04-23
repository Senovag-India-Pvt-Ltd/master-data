package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.dbtStatusCheck.DbtStatusCheckRequest;
import com.sericulture.masterdata.model.api.dbtStatusCheck.DbtStatusCheckResponse;
import com.sericulture.masterdata.model.api.dbtStatusCheck.EditDbtStatusCheckRequest;
import com.sericulture.masterdata.model.api.dbtStatusCheck.DbtStatusCheckRequest;
import com.sericulture.masterdata.model.api.dbtStatusCheck.DbtStatusCheckResponse;
import com.sericulture.masterdata.model.api.dbtStatusCheck.EditDbtStatusCheckRequest;
import com.sericulture.masterdata.model.entity.DbtStatusCheck;
import com.sericulture.masterdata.model.entity.DbtStatusCheck;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DbtStatusCheckRepository;
import com.sericulture.masterdata.repository.DbtStatusCheckRepository;
import com.sericulture.masterdata.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DbtStatusCheckService {

    @Autowired
    DbtStatusCheckRepository dbtStatusCheckRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public DbtStatusCheckResponse insertDbtStatusCheckDetails(DbtStatusCheckRequest dbtStatusCheckRequest){
        DbtStatusCheckResponse dbtStatusCheckResponse = new DbtStatusCheckResponse();
        DbtStatusCheck dbtStatusCheck = mapper.dbtStatusCheckObjectToEntity(dbtStatusCheckRequest,DbtStatusCheck.class);
        validator.validate(dbtStatusCheck);
//        List<DbtStatusCheck> dbtStatusCheckList = dbtStatusCheckRepository.findByUsername(dbtStatusCheckRequest.getUsername());
                List<DbtStatusCheck> dbtStatusCheckList = dbtStatusCheckRepository.findByDeptCodeAndSchemeIdAndComponentTypeIdAndComponentIdAndSubComponentIdAndDbtSchemeAndUsernameAndPassword(dbtStatusCheckRequest.getDeptCode(),dbtStatusCheckRequest.getSchemeId(),dbtStatusCheckRequest.getComponentTypeId(),dbtStatusCheckRequest.getComponentId(),dbtStatusCheckRequest.getSubComponentId(),dbtStatusCheckRequest.getDbtScheme(),dbtStatusCheckRequest.getUsername(),dbtStatusCheckRequest.getPassword());
        if(!dbtStatusCheckList.isEmpty() && dbtStatusCheckList.stream().filter(DbtStatusCheck::getActive).findAny().isPresent()){
            dbtStatusCheckResponse.setError(true);
            dbtStatusCheckResponse.setError_description("Provided Data already exist");
        }
        else if(!dbtStatusCheckList.isEmpty() && dbtStatusCheckList.stream().filter(Predicate.not(DbtStatusCheck::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            dbtStatusCheckResponse.setError(true);
            dbtStatusCheckResponse.setError_description("Provided Data already exist with inactive state");
        }else {
            dbtStatusCheckResponse = mapper.dbtStatusCheckEntityToObject(dbtStatusCheckRepository.save(dbtStatusCheck), DbtStatusCheckResponse.class);
            dbtStatusCheckResponse.setError(false);
        }
        return dbtStatusCheckResponse;
    }

    public Map<String,Object> getPaginatedDbtStatusCheckDetails(final Pageable pageable){
        return convertToMapResponse(dbtStatusCheckRepository.findByActiveOrderByDbtStatusCheckIdAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(dbtStatusCheckRepository.findByActiveOrderByUsernameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<DbtStatusCheck> activeDbtStatusChecks) {
        Map<String, Object> response = new HashMap<>();

        List<DbtStatusCheckResponse> dbtStatusCheckResponses = activeDbtStatusChecks.getContent().stream()
                .map(dbtStatusCheck -> mapper.dbtStatusCheckEntityToObject(dbtStatusCheck,DbtStatusCheckResponse.class)).collect(Collectors.toList());
        response.put("dbtStatusCheck",dbtStatusCheckResponses);
        response.put("currentPage", activeDbtStatusChecks.getNumber());
        response.put("totalItems", activeDbtStatusChecks.getTotalElements());
        response.put("totalPages", activeDbtStatusChecks.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<DbtStatusCheck> activeDbtStatusChecks) {
        Map<String, Object> response = new HashMap<>();

        List<DbtStatusCheckResponse> dbtStatusCheckResponses = activeDbtStatusChecks.stream()
                .map(dbtStatusCheck -> mapper.dbtStatusCheckEntityToObject(dbtStatusCheck,DbtStatusCheckResponse.class)).collect(Collectors.toList());
        response.put("dbtStatusCheck",dbtStatusCheckResponses);
        return response;
    }

    @Transactional
    public DbtStatusCheckResponse deleteDbtStatusCheckDetails(long id) {
        DbtStatusCheckResponse dbtStatusCheckResponse = new DbtStatusCheckResponse();
        DbtStatusCheck dbtStatusCheck = dbtStatusCheckRepository.findByDbtStatusCheckIdAndActive(id, true);
        if (Objects.nonNull(dbtStatusCheck)) {
            dbtStatusCheck.setActive(false);
            dbtStatusCheckResponse = mapper.dbtStatusCheckEntityToObject(dbtStatusCheckRepository.save(dbtStatusCheck), DbtStatusCheckResponse.class);
            dbtStatusCheckResponse.setError(false);
        } else {
            dbtStatusCheckResponse.setError(true);
            dbtStatusCheckResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return dbtStatusCheckResponse;
    }

    public DbtStatusCheckResponse getById(int id){
        DbtStatusCheckResponse dbtStatusCheckResponse = new DbtStatusCheckResponse();
        DbtStatusCheck dbtStatusCheck = dbtStatusCheckRepository.findByDbtStatusCheckIdAndActive(id,true);
        if(dbtStatusCheck == null){
            dbtStatusCheckResponse.setError(true);
            dbtStatusCheckResponse.setError_description("Invalid id");
        }else{
            dbtStatusCheckResponse =  mapper.dbtStatusCheckEntityToObject(dbtStatusCheck,DbtStatusCheckResponse.class);
            dbtStatusCheckResponse.setError(false);
        }
        log.info("Entity is ",dbtStatusCheck);
        return dbtStatusCheckResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getDbtStatusCheckByScApprovalStageId(Long scApprovalStageId){
//        List<DbtStatusCheck> dbtStatusCheckList = dbtStatusCheckRepository.findByScApprovalStageIdAndActiveOrderByName(scApprovalStageId,true);
//        if(dbtStatusCheckList.isEmpty()){
//            throw new ValidationException("Invalid Id");
//        }
//        log.info("Entity is ",dbtStatusCheckList);
//        return convertListToMapResponse(dbtStatusCheckList);
//    }
//
//    private Map<String, Object> convertListToMapResponse(List<DbtStatusCheck> dbtStatusCheckList) {
//        Map<String, Object> response = new HashMap<>();
//        List<DbtStatusCheckResponse> dbtStatusCheckResponses = dbtStatusCheckList.stream()
//                .map(dbtStatusCheck -> mapper.dbtStatusCheckEntityToObject(dbtStatusCheck,DbtStatusCheckResponse.class)).collect(Collectors.toList());
//        response.put("dbtStatusCheck",dbtStatusCheckResponses);
//        response.put("totalItems", dbtStatusCheckList.size());
//        return response;
//    }

    @Transactional
    public DbtStatusCheckResponse updateDbtStatusCheckDetails(EditDbtStatusCheckRequest dbtStatusCheckRequest) {
        DbtStatusCheckResponse dbtStatusCheckResponse = new DbtStatusCheckResponse();
        List<DbtStatusCheck> dbtStatusCheckList = dbtStatusCheckRepository.findByUsernameAndDbtStatusCheckIdIsNot(dbtStatusCheckRequest.getUsername(), dbtStatusCheckRequest.getDbtStatusCheckId());
        if (dbtStatusCheckList.size() > 0) {
            dbtStatusCheckResponse.setError(true);
            dbtStatusCheckResponse.setError_description("DbtStatusCheck already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            DbtStatusCheck dbtStatusCheck = dbtStatusCheckRepository.findByDbtStatusCheckIdAndActiveIn(dbtStatusCheckRequest.getDbtStatusCheckId(), Set.of(true, false));
            if (Objects.nonNull(dbtStatusCheck)) {
                dbtStatusCheck.setDeptCode(dbtStatusCheckRequest.getDeptCode());
                dbtStatusCheck.setSchemeId(dbtStatusCheckRequest.getSchemeId());
                dbtStatusCheck.setComponentTypeId(dbtStatusCheckRequest.getComponentTypeId());
                dbtStatusCheck.setComponentId(dbtStatusCheckRequest.getComponentId());
                dbtStatusCheck.setSubComponentId(dbtStatusCheckRequest.getSubComponentId());
                dbtStatusCheck.setDbtScheme(dbtStatusCheckRequest.getDbtScheme());
                dbtStatusCheck.setUsername(dbtStatusCheckRequest.getUsername());
                dbtStatusCheck.setPassword(dbtStatusCheckRequest.getPassword());
                dbtStatusCheck.setActive(true);
                DbtStatusCheck dbtStatusCheck1 = dbtStatusCheckRepository.save(dbtStatusCheck);
                dbtStatusCheckResponse = mapper.dbtStatusCheckEntityToObject(dbtStatusCheck1, DbtStatusCheckResponse.class);
                dbtStatusCheckResponse.setError(false);
            } else {
                dbtStatusCheckResponse.setError(true);
                dbtStatusCheckResponse.setError_description("Error occurred while fetching DbtStatusCheck");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return dbtStatusCheckResponse;
    }

   
   
}
