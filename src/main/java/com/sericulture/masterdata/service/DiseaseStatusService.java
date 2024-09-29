package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.diseaseStatus.DiseaseStatusRequest;
import com.sericulture.masterdata.model.api.diseaseStatus.DiseaseStatusResponse;
import com.sericulture.masterdata.model.api.diseaseStatus.EditDiseaseStatusRequest;
import com.sericulture.masterdata.model.entity.DiseaseStatus;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DiseaseStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DiseaseStatusService {

    @Autowired
    DiseaseStatusRepository diseaseStatusRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public DiseaseStatusResponse insertDiseaseStatusDetails(DiseaseStatusRequest diseaseStatusRequest){
        DiseaseStatusResponse diseaseStatusResponse = new DiseaseStatusResponse();
        DiseaseStatus diseaseStatus = mapper.diseaseStatusObjectToEntity(diseaseStatusRequest,DiseaseStatus.class);
        validator.validate(diseaseStatus);
        List<DiseaseStatus> diseaseStatusList = diseaseStatusRepository.findByName(diseaseStatusRequest.getName());
        if(!diseaseStatusList.isEmpty() && diseaseStatusList.stream().filter(DiseaseStatus::getActive).findAny().isPresent()){
            diseaseStatusResponse.setError(true);
            diseaseStatusResponse.setError_description("DiseaseStatus name already exist");
        }
        else if(!diseaseStatusList.isEmpty() && diseaseStatusList.stream().filter(Predicate.not(DiseaseStatus::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            diseaseStatusResponse.setError(true);
            diseaseStatusResponse.setError_description("DiseaseStatus name already exist with inactive state");
        }else {
            diseaseStatusResponse = mapper.diseaseStatusEntityToObject(diseaseStatusRepository.save(diseaseStatus), DiseaseStatusResponse.class);
            diseaseStatusResponse.setError(false);
        }
        return diseaseStatusResponse;
    }

    public Map<String,Object> getPaginatedDiseaseStatusDetails(final Pageable pageable){
        return convertToMapResponse(diseaseStatusRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(diseaseStatusRepository.findByActiveOrderByNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<DiseaseStatus> activeDiseaseStatuss) {
        Map<String, Object> response = new HashMap<>();

        List<DiseaseStatusResponse> diseaseStatusResponses = activeDiseaseStatuss.getContent().stream()
                .map(diseaseStatus -> mapper.diseaseStatusEntityToObject(diseaseStatus,DiseaseStatusResponse.class)).collect(Collectors.toList());
        response.put("diseaseStatus",diseaseStatusResponses);
        response.put("currentPage", activeDiseaseStatuss.getNumber());
        response.put("totalItems", activeDiseaseStatuss.getTotalElements());
        response.put("totalPages", activeDiseaseStatuss.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<DiseaseStatus> activeDiseaseStatuss) {
        Map<String, Object> response = new HashMap<>();

        List<DiseaseStatusResponse> diseaseStatusResponses = activeDiseaseStatuss.stream()
                .map(diseaseStatus -> mapper.diseaseStatusEntityToObject(diseaseStatus,DiseaseStatusResponse.class)).collect(Collectors.toList());
        response.put("diseaseStatus",diseaseStatusResponses);
        return response;
    }

    @Transactional
    public DiseaseStatusResponse deleteDiseaseStatusDetails(long id) {
        DiseaseStatusResponse diseaseStatusResponse = new DiseaseStatusResponse();
        DiseaseStatus diseaseStatus = diseaseStatusRepository.findByDiseaseStatusIdAndActive(id, true);
        if (Objects.nonNull(diseaseStatus)) {
            diseaseStatus.setActive(false);
            diseaseStatusResponse = mapper.diseaseStatusEntityToObject(diseaseStatusRepository.save(diseaseStatus), DiseaseStatusResponse.class);
            diseaseStatusResponse.setError(false);
        } else {
            diseaseStatusResponse.setError(true);
            diseaseStatusResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return diseaseStatusResponse;
    }

    public DiseaseStatusResponse getById(int id){
        DiseaseStatusResponse diseaseStatusResponse = new DiseaseStatusResponse();
        DiseaseStatus diseaseStatus = diseaseStatusRepository.findByDiseaseStatusIdAndActive(id,true);
        if(diseaseStatus == null){
            diseaseStatusResponse.setError(true);
            diseaseStatusResponse.setError_description("Invalid id");
        }else{
            diseaseStatusResponse =  mapper.diseaseStatusEntityToObject(diseaseStatus,DiseaseStatusResponse.class);
            diseaseStatusResponse.setError(false);
        }
        log.info("Entity is ",diseaseStatus);
        return diseaseStatusResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getDiseaseStatusByScApprovalStageId(Long scApprovalStageId){
//        List<DiseaseStatus> diseaseStatusList = diseaseStatusRepository.findByScApprovalStageIdAndActiveOrderByName(scApprovalStageId,true);
//        if(diseaseStatusList.isEmpty()){
//            throw new ValidationException("Invalid Id");
//        }
//        log.info("Entity is ",diseaseStatusList);
//        return convertListToMapResponse(diseaseStatusList);
//    }
//
//    private Map<String, Object> convertListToMapResponse(List<DiseaseStatus> diseaseStatusList) {
//        Map<String, Object> response = new HashMap<>();
//        List<DiseaseStatusResponse> diseaseStatusResponses = diseaseStatusList.stream()
//                .map(diseaseStatus -> mapper.diseaseStatusEntityToObject(diseaseStatus,DiseaseStatusResponse.class)).collect(Collectors.toList());
//        response.put("diseaseStatus",diseaseStatusResponses);
//        response.put("totalItems", diseaseStatusList.size());
//        return response;
//    }

    @Transactional
    public DiseaseStatusResponse updateDiseaseStatusDetails(EditDiseaseStatusRequest diseaseStatusRequest) {
        DiseaseStatusResponse diseaseStatusResponse = new DiseaseStatusResponse();
        List<DiseaseStatus> diseaseStatusList = diseaseStatusRepository.findByNameAndDiseaseStatusIdIsNot(diseaseStatusRequest.getName(), diseaseStatusRequest.getDiseaseStatusId());
        if (diseaseStatusList.size() > 0) {
            diseaseStatusResponse.setError(true);
            diseaseStatusResponse.setError_description("DiseaseStatus already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            DiseaseStatus diseaseStatus = diseaseStatusRepository.findByDiseaseStatusIdAndActiveIn(diseaseStatusRequest.getDiseaseStatusId(), Set.of(true, false));
            if (Objects.nonNull(diseaseStatus)) {
                diseaseStatus.setName(diseaseStatusRequest.getName());
                diseaseStatus.setDescription(diseaseStatusRequest.getDescription());
                diseaseStatus.setActive(true);
                DiseaseStatus diseaseStatus1 = diseaseStatusRepository.save(diseaseStatus);
                diseaseStatusResponse = mapper.diseaseStatusEntityToObject(diseaseStatus1, DiseaseStatusResponse.class);
                diseaseStatusResponse.setError(false);
            } else {
                diseaseStatusResponse.setError(true);
                diseaseStatusResponse.setError_description("Error occurred while fetching DiseaseStatus");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return diseaseStatusResponse;
    }
}
