package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.generationNumberMaster.EditGenerationNumberMasterRequest;
import com.sericulture.masterdata.model.api.generationNumberMaster.GenerationNumberMasterRequest;
import com.sericulture.masterdata.model.api.generationNumberMaster.GenerationNumberMasterResponse;
import com.sericulture.masterdata.model.api.trModeMaster.EditTrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterResponse;
import com.sericulture.masterdata.model.entity.GenerationNumberMaster;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.GenerationNumberMasterRepository;
import com.sericulture.masterdata.repository.TrModeMasterRepository;
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
public class GenerationNumberMasterService {
    @Autowired
    GenerationNumberMasterRepository generationNumberMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;



    @Transactional
    public GenerationNumberMasterResponse insertGenerationNumberMasterDetails(GenerationNumberMasterRequest generationNumberMasterRequest){
        GenerationNumberMasterResponse generationNumberMasterResponse = new GenerationNumberMasterResponse();
        GenerationNumberMaster generationNumberMaster = mapper.generationNumberObjectToEntity(generationNumberMasterRequest,GenerationNumberMaster.class);
        validator.validate(generationNumberMaster);
        List<GenerationNumberMaster> generationNumberMasterList= generationNumberMasterRepository.findByGenerationNumberAndActive(generationNumberMasterRequest.getGenerationNumber(),true);
        if(!generationNumberMasterList.isEmpty() && generationNumberMasterList.stream().filter(GenerationNumberMaster::getActive).findAny().isPresent()){
            generationNumberMasterResponse.setError(true);
            generationNumberMasterResponse.setError_description("generationNumberMaster name already exist");
        }
        else if(!generationNumberMasterList.isEmpty() && generationNumberMasterList.stream().filter(Predicate.not(GenerationNumberMaster::getActive)).findAny().isPresent()){
            generationNumberMasterResponse.setError(true);
            generationNumberMasterResponse.setError_description("generationNumberMaster name already exist with inactive state");
        }else {
            generationNumberMasterResponse = mapper.generationNumberMasterEntityToObject(generationNumberMasterRepository.save(generationNumberMaster), GenerationNumberMasterResponse.class);
            generationNumberMasterResponse.setError(false);
        }
        return generationNumberMasterResponse;
    }

    public Map<String,Object> getPaginatedGenerationNumberMasterDetails(final Pageable pageable){
        return convertToMapResponse(generationNumberMasterRepository.findByActiveOrderByGenerationNumberAsc( true,pageable ));

    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(generationNumberMasterRepository.findByActiveOrderByGenerationNumberAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<GenerationNumberMaster> activeTrGenerationNumberMasters) {
        Map<String, Object> response = new HashMap<>();

        List<GenerationNumberMasterResponse> trModeMasterResponses = activeTrGenerationNumberMasters.getContent().stream()
                .map(generationNumberMaster -> mapper.generationNumberMasterEntityToObject(generationNumberMaster,GenerationNumberMasterResponse.class)).collect(Collectors.toList());
        response.put("generationNumberMaster",trModeMasterResponses);
        response.put("currentPage", activeTrGenerationNumberMasters.getNumber());
        response.put("totalItems", activeTrGenerationNumberMasters.getTotalElements());
        response.put("totalPages", activeTrGenerationNumberMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<GenerationNumberMaster> activeTrGenerationNumberMasters) {
        Map<String, Object> response = new HashMap<>();

        List<GenerationNumberMasterResponse> generationNumberMasterResponses = activeTrGenerationNumberMasters.stream()
                .map(generationNumberMaster -> mapper.generationNumberMasterEntityToObject(generationNumberMaster,GenerationNumberMasterResponse.class)).collect(Collectors.toList());
        response.put("generationNumberMaster",generationNumberMasterResponses);
        return response;
    }

    @Transactional
    public GenerationNumberMasterResponse deleteGenerationNumberMasterDetails(long id) {

        GenerationNumberMasterResponse generationNumberMasterResponse= new GenerationNumberMasterResponse();
        GenerationNumberMaster generationNumberMaster = generationNumberMasterRepository.findByGenerationNumberIdAndActive(id, true);
        if (Objects.nonNull(generationNumberMaster)) {
            generationNumberMaster.setActive(false);
            generationNumberMasterResponse = mapper.generationNumberMasterEntityToObject(generationNumberMasterRepository.save(generationNumberMaster), GenerationNumberMasterResponse.class);
            generationNumberMasterResponse.setError(false);
        } else {
            generationNumberMasterResponse.setError(true);
            generationNumberMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return generationNumberMasterResponse;
    }


    public GenerationNumberMasterResponse getById(int id){
        GenerationNumberMasterResponse generationNumberMasterResponse = new GenerationNumberMasterResponse();
        GenerationNumberMaster generationNumberMaster = generationNumberMasterRepository.findByGenerationNumberIdAndActive(id,true);
        if(generationNumberMaster == null){
            generationNumberMasterResponse.setError(true);
            generationNumberMasterResponse.setError_description("Invalid id");
        }else{
            generationNumberMasterResponse =  mapper.generationNumberMasterEntityToObject(generationNumberMaster,GenerationNumberMasterResponse.class);
            generationNumberMasterResponse.setError(false);
        }
        log.info("Entity is ",generationNumberMaster);
        return generationNumberMasterResponse;
    }

    @Transactional
    public GenerationNumberMasterResponse updateGenerationNumberMasterDetails(EditGenerationNumberMasterRequest generationNumberMasterRequest){

        GenerationNumberMasterResponse generationNumberMasterResponse = new GenerationNumberMasterResponse();
        List<GenerationNumberMaster> generationNumberMasterList = generationNumberMasterRepository.findByActiveAndGenerationNumberAndGenerationNumberIdIsNot(true,generationNumberMasterRequest.getGenerationNumber(),generationNumberMasterRequest.getGenerationNumberId());
        if(generationNumberMasterList.size()>0){
            generationNumberMasterResponse.setError(true);
            generationNumberMasterResponse.setError_description("generationNumberMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            GenerationNumberMaster generationNumberMaster= generationNumberMasterRepository.findByGenerationNumberIdAndActiveIn(generationNumberMasterRequest.getGenerationNumberId(), Set.of(true,false));
            if(Objects.nonNull(generationNumberMaster)){
                generationNumberMaster.setGenerationNumber(generationNumberMasterRequest.getGenerationNumber());
                generationNumberMaster.setActive(true);
                GenerationNumberMaster generationNumberMaster1 = generationNumberMasterRepository.save(generationNumberMaster);
                generationNumberMasterResponse = mapper.generationNumberMasterEntityToObject(generationNumberMaster1, GenerationNumberMasterResponse.class);
                generationNumberMasterResponse.setError(false);
            } else {
                generationNumberMasterResponse.setError(true);
                generationNumberMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return generationNumberMasterResponse;
    }
}
