package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.workingInstitution.EditWorkingInstitutionRequest;
import com.sericulture.masterdata.model.api.workingInstitution.WorkingInstitutionRequest;
import com.sericulture.masterdata.model.api.workingInstitution.WorkingInstitutionResponse;
import com.sericulture.masterdata.model.entity.WorkingInstitution;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.WorkingInstitutionRepository;
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

@Slf4j
@Service
public class WorkingInstitutionService {

    @Autowired
    WorkingInstitutionRepository workingInstitutionRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;



    @Transactional
    public WorkingInstitutionResponse insertWorkingInstitutionDetails(WorkingInstitutionRequest workingInstitutionRequest){
        WorkingInstitutionResponse workingInstitutionResponse = new WorkingInstitutionResponse();
        WorkingInstitution workingInstitution = mapper.workingInstitutionObjectToEntity(workingInstitutionRequest,WorkingInstitution.class);
        validator.validate(workingInstitution);
        List<WorkingInstitution> workingInstitutionList = workingInstitutionRepository.findByWorkingInstitutionNameAndWorkingInstitutionNameInKannada(workingInstitutionRequest.getWorkingInstitutionName(),workingInstitutionRequest.getWorkingInstitutionNameInKannada());
        if(!workingInstitutionList.isEmpty() && workingInstitutionList.stream().filter(WorkingInstitution::getActive).findAny().isPresent()){
            workingInstitutionResponse.setError(true);
            workingInstitutionResponse.setError_description("Working Institution name already exist");
//        }
//        else if(!workingInstitutionList.isEmpty() && workingInstitutionList.stream().filter(Predicate.not(WorkingInstitution::getActive)).findAny().isPresent()){
//            workingInstitutionResponse.setError(true);
//            workingInstitutionResponse.setError_description("Working Institution name already exist with inactive state");
        }else {
            workingInstitutionResponse = mapper.workingInstitutionEntityToObject(workingInstitutionRepository.save(workingInstitution), WorkingInstitutionResponse.class);
            workingInstitutionResponse.setError(false);
        }
        return workingInstitutionResponse;
    }

    public Map<String,Object> getPaginatedWorkingInstitutionDetails(final Pageable pageable){
        return convertToMapResponse(workingInstitutionRepository.findByActiveOrderByWorkingInstitutionNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(workingInstitutionRepository.findByActiveOrderByWorkingInstitutionNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<WorkingInstitution> activeWorkingInstitutions) {
        Map<String, Object> response = new HashMap<>();

        List<WorkingInstitutionResponse> workingInstitutionResponses = activeWorkingInstitutions.getContent().stream()
                .map(workingInstitution -> mapper.workingInstitutionEntityToObject(workingInstitution,WorkingInstitutionResponse.class)).collect(Collectors.toList());
        response.put("workingInstitution",workingInstitutionResponses);
        response.put("currentPage", activeWorkingInstitutions.getNumber());
        response.put("totalItems", activeWorkingInstitutions.getTotalElements());
        response.put("totalPages", activeWorkingInstitutions.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<WorkingInstitution> activeWorkingInstitutions) {
        Map<String, Object> response = new HashMap<>();

        List<WorkingInstitutionResponse> workingInstitutionResponses = activeWorkingInstitutions.stream()
                .map(workingInstitution -> mapper.workingInstitutionEntityToObject(workingInstitution,WorkingInstitutionResponse.class)).collect(Collectors.toList());
        response.put("workingInstitution",workingInstitutionResponses);
        return response;
    }

    @Transactional
    public WorkingInstitutionResponse deleteWorkingInstitutionDetails(long id) {

        WorkingInstitutionResponse workingInstitutionResponse = new WorkingInstitutionResponse();
        WorkingInstitution workingInstitution = workingInstitutionRepository.findByWorkingInstitutionIdAndActive(id, true);
        if (Objects.nonNull(workingInstitution)) {
            workingInstitution.setActive(false);
            workingInstitutionResponse = mapper.workingInstitutionEntityToObject(workingInstitutionRepository.save(workingInstitution), WorkingInstitutionResponse.class);
            workingInstitutionResponse.setError(false);
        } else {
            workingInstitutionResponse.setError(true);
            workingInstitutionResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return workingInstitutionResponse;
    }

    public WorkingInstitutionResponse getById(int id){
        WorkingInstitutionResponse workingInstitutionResponse = new WorkingInstitutionResponse();
        WorkingInstitution workingInstitution = workingInstitutionRepository.findByWorkingInstitutionIdAndActive(id,true);
        if(workingInstitution == null){
            workingInstitutionResponse.setError(true);
            workingInstitutionResponse.setError_description("Invalid id");
        }else{
            workingInstitutionResponse =  mapper.workingInstitutionEntityToObject(workingInstitution,WorkingInstitutionResponse.class);
            workingInstitutionResponse.setError(false);
        }
        log.info("Entity is ",workingInstitution);
        return workingInstitutionResponse;
    }

    @Transactional
    public WorkingInstitutionResponse updateWorkingInstitutionDetails(EditWorkingInstitutionRequest workingInstitutionRequest){

        WorkingInstitutionResponse workingInstitutionResponse = new WorkingInstitutionResponse();
        List<WorkingInstitution> workingInstitutionList = workingInstitutionRepository.findByActiveAndWorkingInstitutionNameAndWorkingInstitutionNameInKannada(true,workingInstitutionRequest.getWorkingInstitutionName(), workingInstitutionRequest.getWorkingInstitutionNameInKannada());
        if(workingInstitutionList.size()>0){
            workingInstitutionResponse.setError(true);
            workingInstitutionResponse.setError_description("Working Institution already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            WorkingInstitution workingInstitution = workingInstitutionRepository.findByWorkingInstitutionIdAndActiveIn(workingInstitutionRequest.getWorkingInstitutionId(), Set.of(true,false));
            if(Objects.nonNull(workingInstitution)){
                workingInstitution.setWorkingInstitutionName(workingInstitutionRequest.getWorkingInstitutionName());
                workingInstitution.setWorkingInstitutionNameInKannada(workingInstitutionRequest.getWorkingInstitutionNameInKannada());
                workingInstitution.setActive(true);
                WorkingInstitution workingInstitution1 = workingInstitutionRepository.save(workingInstitution);
                workingInstitutionResponse = mapper.workingInstitutionEntityToObject(workingInstitution1, WorkingInstitutionResponse.class);
                workingInstitutionResponse.setError(false);
            } else {
                workingInstitutionResponse.setError(true);
                workingInstitutionResponse.setError_description("Error occurred while fetching workingInstitution");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return workingInstitutionResponse;
    }

}
