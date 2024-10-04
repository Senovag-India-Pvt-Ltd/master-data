package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.scApprovalStage.EditScApprovalStageRequest;
import com.sericulture.masterdata.model.api.scApprovalStage.ScApprovalStageRequest;
import com.sericulture.masterdata.model.api.scApprovalStage.ScApprovalStageResponse;
import com.sericulture.masterdata.model.api.trProgramMaster.EditTrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterResponse;
import com.sericulture.masterdata.model.dto.RaceMarketMasterDTO;
import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.entity.ScApprovalStage;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScApprovalStageRepository;
import com.sericulture.masterdata.repository.TrProgramMasterRespository;
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
public class ScApprovalStageService {

    @Autowired
    ScApprovalStageRepository scApprovalStageRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;



    @Transactional
    public ScApprovalStageResponse insertScApprovalStageDetails(ScApprovalStageRequest scApprovalStageRequest){
        ScApprovalStageResponse scApprovalStageResponse = new ScApprovalStageResponse();
        ScApprovalStage scApprovalStage = mapper.scApprovalStageObjectToEntity(scApprovalStageRequest,ScApprovalStage.class);
        validator.validate(scApprovalStage);
        List<ScApprovalStage> scApprovalStageList = scApprovalStageRepository.findByStageNameAndStageNameInKannadaAndActive(scApprovalStageRequest.getStageName(), scApprovalStageRequest.getStageNameInKannada(),true);
        if(!scApprovalStageList.isEmpty() && scApprovalStageList.stream().filter( ScApprovalStage::getActive).findAny().isPresent()){
            scApprovalStageResponse.setError(true);
            scApprovalStageResponse.setError_description("SC approval name already exist");
        }
        else if(! scApprovalStageList.isEmpty() && scApprovalStageList.stream().filter(Predicate.not( ScApprovalStage::getActive)).findAny().isPresent()){
            scApprovalStageResponse.setError(true);
            scApprovalStageResponse.setError_description("Sc Approval name already exist with inactive state");
        }else {
            scApprovalStageResponse  = mapper.scApprovalStageEntityToObject( scApprovalStageRepository.save(scApprovalStage), ScApprovalStageResponse.class);
            scApprovalStageResponse.setError(false);
        }
        return scApprovalStageResponse;
    }

    public Map<String,Object> getPaginatedScApprovalStageDetails(final Pageable pageable){
        return convertToMapResponse(scApprovalStageRepository.findByActiveOrderByStageNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scApprovalStageRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScApprovalStage> activeScApprovalStages) {
        Map<String, Object> response = new HashMap<>();

        List<ScApprovalStageResponse> scApprovalStageResponses= activeScApprovalStages.getContent().stream()
                .map(scApprovalStage -> mapper.scApprovalStageEntityToObject(scApprovalStage,ScApprovalStageResponse.class)).collect(Collectors.toList());
        response.put("scApprovalStage",scApprovalStageResponses);
        response.put("currentPage", activeScApprovalStages.getNumber());
        response.put("totalItems", activeScApprovalStages.getTotalElements());
        response.put("totalPages", activeScApprovalStages.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScApprovalStage> activeScApprovalStages) {
        Map<String, Object> response = new HashMap<>();

        List<ScApprovalStageResponse> scApprovalStageResponses= activeScApprovalStages.stream()
                .map(scApprovalStage -> mapper.scApprovalStageEntityToObject(scApprovalStage,ScApprovalStageResponse.class)).collect(Collectors.toList());
        response.put("scApprovalStage",scApprovalStageResponses);
        return response;
    }

    @Transactional
    public ScApprovalStageResponse deleteScApprovalStageDetails(long id) {

        ScApprovalStageResponse scApprovalStageResponse = new ScApprovalStageResponse();
        ScApprovalStage scApprovalStage= scApprovalStageRepository.findByScApprovalStageIdAndActive(id, true);
        if (Objects.nonNull(scApprovalStage)) {
            scApprovalStage.setActive(false);
            scApprovalStageResponse= mapper.scApprovalStageEntityToObject(scApprovalStageRepository.save(scApprovalStage), ScApprovalStageResponse.class);
            scApprovalStageResponse.setError(false);
        } else {
            scApprovalStageResponse.setError(true);
            scApprovalStageResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scApprovalStageResponse;
    }

    public ScApprovalStageResponse getById(int id){
        ScApprovalStageResponse scApprovalStageResponse = new ScApprovalStageResponse();
        ScApprovalStage scApprovalStage= scApprovalStageRepository.findByScApprovalStageIdAndActive(id, true);
        if(scApprovalStage== null){
            scApprovalStageResponse.setError(true);
            scApprovalStageResponse.setError_description("Invalid id");
        }else{
            scApprovalStageResponse =  mapper.scApprovalStageEntityToObject(scApprovalStage, ScApprovalStageResponse.class);
            scApprovalStageResponse.setError(false);
        }
        log.info("Entity is ",scApprovalStage);
        return scApprovalStageResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getScApprovalStageByScProgramId(Long scProgramId){
//        List<ScApprovalStage> scApprovalStageList = scApprovalStageRepository.findByScProgramIdAndActiveOrderByStageName(scProgramId,true);
//        if(scApprovalStageList.isEmpty()){
//            throw new ValidationException("Invalid Id");
//        }
//        log.info("Entity is ",scApprovalStageList);
//        return convertListToMapResponse(scApprovalStageList);
//    }
//
//    private Map<String, Object> convertListToMapResponse(List<ScApprovalStage> scApprovalStageList) {
//        Map<String, Object> response = new HashMap<>();
//        List<ScApprovalStageResponse> scApprovalStageResponses = scApprovalStageList.stream()
//                .map(scApprovalStage -> mapper.scApprovalStageEntityToObject(scApprovalStage,ScApprovalStageResponse.class)).collect(Collectors.toList());
//        response.put("scApprovalStage",scApprovalStageResponses);
//        response.put("totalItems", scApprovalStageList.size());
//        return response;
//    }
    @Transactional
    public ScApprovalStageResponse updateScApprovalStageDetails(EditScApprovalStageRequest scApprovalStageRequest){

        ScApprovalStageResponse scApprovalStageResponse = new ScApprovalStageResponse();
            List<ScApprovalStage> scApprovalStageList = scApprovalStageRepository. findByStageNameAndStageNameInKannadaAndScApprovalStageIdIsNot(scApprovalStageRequest.getStageName(), scApprovalStageRequest.getStageNameInKannada(),scApprovalStageRequest.getScApprovalStageId());
        if(scApprovalStageList.size()>0){
            scApprovalStageResponse.setError(true);
            scApprovalStageResponse.setError_description("Sc Approval exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            ScApprovalStage scApprovalStage = scApprovalStageRepository.findByScApprovalStageIdAndActiveIn(scApprovalStageRequest.getScApprovalStageId(), Set.of(true,false));
            if(Objects.nonNull(scApprovalStage)){
                scApprovalStage.setStageName( scApprovalStageRequest.getStageName());
                scApprovalStage.setStageNameInKannada(scApprovalStageRequest.getStageNameInKannada());
                scApprovalStage.setWorkFlowType(scApprovalStageRequest.getWorkFlowType());
                scApprovalStage.setAction(scApprovalStageRequest.getAction());
                scApprovalStage.setActive(true);
                ScApprovalStage scApprovalStage1= scApprovalStageRepository.save(scApprovalStage);
                scApprovalStageResponse = mapper.scApprovalStageEntityToObject(scApprovalStage1, ScApprovalStageResponse.class);
                scApprovalStageResponse.setError(false);
            } else {
                scApprovalStageResponse.setError(true);
                scApprovalStageResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return scApprovalStageResponse;
    }

}
