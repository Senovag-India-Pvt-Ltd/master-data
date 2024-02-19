package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.grainageMaster.EditGrainageMasterRequest;
import com.sericulture.masterdata.model.api.grainageMaster.GrainageMasterRequest;
import com.sericulture.masterdata.model.api.grainageMaster.GrainageMasterResponse;
import com.sericulture.masterdata.model.entity.GrainageMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.GrainageMasterRepository;
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
public class GrainageMasterService {

    @Autowired
    GrainageMasterRepository grainageMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GrainageMasterResponse getGrainageMasterDetails(String grainageMasterName){
        GrainageMasterResponse grainageMasterResponse = new GrainageMasterResponse();
        GrainageMaster grainageMaster = grainageMasterRepository.findByGrainageMasterNameAndActive(grainageMasterName, true);
        if(grainageMaster==null){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("grainageMaster  not Found");
        }else{
            grainageMasterResponse = mapper.grainageMasterEntityToObject(grainageMaster, GrainageMasterResponse.class);
            grainageMasterResponse.setError(false);
        }
        log.info("Entity is ",grainageMaster);
        return grainageMasterResponse;

    }

    @Transactional
    public GrainageMasterResponse insertGrainageMasterDetails(GrainageMasterRequest grainageMasterRequest){
        GrainageMasterResponse grainageMasterResponse = new GrainageMasterResponse();
        GrainageMaster grainageMaster = mapper.grainageMasterObjectToEntity(grainageMasterRequest, GrainageMaster.class);
        validator.validate(grainageMaster);
        List<GrainageMaster> grainageMasterList= grainageMasterRepository.findByGrainageMasterNameAndGrainageMasterNameInKannada(grainageMasterRequest.getGrainageMasterName(),grainageMasterRequest.getGrainageMasterNameInKannada());
        if(!grainageMasterList.isEmpty() && grainageMasterList.stream().filter(GrainageMaster::getActive).findAny().isPresent()){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("grainageMaster name already exist");
        }
        else if(!grainageMasterList.isEmpty() && grainageMasterList.stream().filter(Predicate.not(GrainageMaster::getActive)).findAny().isPresent()){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("grainageMaster name already exist with inactive state");
        }else {
            grainageMasterResponse = mapper.grainageMasterEntityToObject(grainageMasterRepository.save(grainageMaster), GrainageMasterResponse.class);
            grainageMasterResponse.setError(false);
        }
        return grainageMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedGrainageMasterDetails(final Pageable pageable){
        return convertToMapResponse(grainageMasterRepository.findByActiveOrderByGrainageMasterNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(grainageMasterRepository.findByActiveOrderByGrainageMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<GrainageMaster> activeGrainageMasters) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageMasterResponse> grainageMasterResponses = activeGrainageMasters.getContent().stream()
                .map(grainageMaster -> mapper.grainageMasterEntityToObject(grainageMaster, GrainageMasterResponse.class)).collect(Collectors.toList());
        response.put("grainageMaster",grainageMasterResponses);
        response.put("currentPage", activeGrainageMasters.getNumber());
        response.put("totalItems", activeGrainageMasters.getTotalElements());
        response.put("totalPages", activeGrainageMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<GrainageMaster> activeGrainageMasters) {
        Map<String, Object> response = new HashMap<>();

        List<GrainageMasterResponse> grainageMasterResponses = activeGrainageMasters.stream()
                .map(grainageMaster -> mapper.grainageMasterEntityToObject(grainageMaster, GrainageMasterResponse.class)).collect(Collectors.toList());
        response.put("grainageMaster",grainageMasterResponses);
        return response;
    }

    @Transactional
    public GrainageMasterResponse deleteGrainageMasterDetails(long id) {

        GrainageMasterResponse grainageMasterResponse= new GrainageMasterResponse();
        GrainageMaster grainageMaster = grainageMasterRepository.findByGrainageMasterIdAndActive(id, true);
        if (Objects.nonNull(grainageMaster)) {
            grainageMaster.setActive(false);
            grainageMasterResponse = mapper.grainageMasterEntityToObject(grainageMasterRepository.save(grainageMaster), GrainageMasterResponse.class);
            grainageMasterResponse.setError(false);
        } else {
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return grainageMasterResponse;
    }

    @Transactional
    public GrainageMasterResponse getById(int id){
        GrainageMasterResponse grainingMasterResponse = new GrainageMasterResponse();
        GrainageMaster grainageMaster = grainageMasterRepository.findByGrainageMasterIdAndActive(id,true);
        if(grainageMaster == null){
            grainingMasterResponse.setError(true);
            grainingMasterResponse.setError_description("Invalid id");
        }else{
            grainingMasterResponse =  mapper.grainageMasterEntityToObject(grainageMaster, GrainageMasterResponse.class);
            grainingMasterResponse.setError(false);
        }
        log.info("Entity is ",grainageMaster);
        return grainingMasterResponse;
    }

    @Transactional
    public GrainageMasterResponse updateGrainageMasterDetails(EditGrainageMasterRequest grainageMasterRequest){

        GrainageMasterResponse grainageMasterResponse = new GrainageMasterResponse();
        List<GrainageMaster> grainageMasterList = grainageMasterRepository.findByGrainageMasterNameAndGrainageMasterNameInKannadaAndGrainageMasterIdIsNot(grainageMasterRequest.getGrainageMasterName(),grainageMasterRequest.getGrainageMasterNameInKannada(),grainageMasterRequest.getGrainageMasterId());
        if(grainageMasterList.size()>0){
            grainageMasterResponse.setError(true);
            grainageMasterResponse.setError_description("grainageMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            GrainageMaster grainageMaster= grainageMasterRepository.findByGrainageMasterIdAndActiveIn(grainageMasterRequest.getGrainageMasterId(), Set.of(true,false));
            if(Objects.nonNull(grainageMaster)){
                grainageMaster.setGrainageMasterName(grainageMasterRequest.getGrainageMasterName());
                grainageMaster.setGrainageMasterNameInKannada(grainageMasterRequest.getGrainageMasterNameInKannada());
                grainageMaster.setActive(true);
                GrainageMaster grainingMaster1 = grainageMasterRepository.save(grainageMaster);
                grainageMasterResponse = mapper.grainageMasterEntityToObject(grainingMaster1, GrainageMasterResponse.class);
                grainageMasterResponse.setError(false);
            } else {
                grainageMasterResponse.setError(true);
                grainageMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return grainageMasterResponse;
    }
}
