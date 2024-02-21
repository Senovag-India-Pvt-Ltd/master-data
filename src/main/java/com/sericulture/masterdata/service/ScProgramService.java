package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scProgram.EditScProgramRequest;
import com.sericulture.masterdata.model.api.scProgram.ScProgramRequest;
import com.sericulture.masterdata.model.api.scProgram.ScProgramResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.ScProgram;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScProgramRepository;
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
public class ScProgramService {
    @Autowired
    ScProgramRepository scProgramRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScProgramResponse insertScProgramDetails(ScProgramRequest scProgramRequest){
        ScProgramResponse scProgramResponse = new ScProgramResponse();
        ScProgram scProgram = mapper.scProgramObjectToEntity(scProgramRequest,ScProgram.class);
        validator.validate(scProgram);
        List<ScProgram> scProgramList = scProgramRepository.findByScProgramName(scProgramRequest.getScProgramName());
        if(!scProgramList.isEmpty() && scProgramList.stream().filter(ScProgram::getActive).findAny().isPresent()){
            scProgramResponse.setError(true);
            scProgramResponse.setError_description("ScProgram name already exist");
//        }
//        else if(!scProgramList.isEmpty() && scProgramList.stream().filter(Predicate.not(ScProgram::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            scProgramResponse.setError(true);
//            scProgramResponse.setError_description("ScProgram name already exist with inactive state");
        }else {
            scProgramResponse = mapper.scProgramEntityToObject(scProgramRepository.save(scProgram), ScProgramResponse.class);
            scProgramResponse.setError(false);
        }
        return scProgramResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScProgramDetails(final Pageable pageable){
        return convertToMapResponse(scProgramRepository.findByActiveOrderByScProgramNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scProgramRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScProgram> activeScPrograms) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramResponse> scProgramResponses = activeScPrograms.getContent().stream()
                .map(scProgram -> mapper.scProgramEntityToObject(scProgram,ScProgramResponse.class)).collect(Collectors.toList());
        response.put("scProgram",scProgramResponses);
        response.put("currentPage", activeScPrograms.getNumber());
        response.put("totalItems", activeScPrograms.getTotalElements());
        response.put("totalPages", activeScPrograms.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScProgram> activeScPrograms) {
        Map<String, Object> response = new HashMap<>();

        List<ScProgramResponse> scProgramResponses = activeScPrograms.stream()
                .map(scProgram -> mapper.scProgramEntityToObject(scProgram,ScProgramResponse.class)).collect(Collectors.toList());
        response.put("scProgram",scProgramResponses);
        return response;
    }

    @Transactional
    public ScProgramResponse deleteScProgramDetails(long id) {
        ScProgramResponse scProgramResponse = new ScProgramResponse();
        ScProgram scProgram = scProgramRepository.findByScProgramIdAndActive(id, true);
        if (Objects.nonNull(scProgram)) {
            scProgram.setActive(false);
            scProgramResponse = mapper.scProgramEntityToObject(scProgramRepository.save(scProgram), ScProgramResponse.class);
            scProgramResponse.setError(false);
        } else {
            scProgramResponse.setError(true);
            scProgramResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scProgramResponse;
    }

    @Transactional
    public ScProgramResponse getById(int id){
        ScProgramResponse scProgramResponse = new ScProgramResponse();
        ScProgram scProgram = scProgramRepository.findByScProgramIdAndActive(id,true);
        if(scProgram == null){
            scProgramResponse.setError(true);
            scProgramResponse.setError_description("Invalid id");
        }else{
            scProgramResponse =  mapper.scProgramEntityToObject(scProgram,ScProgramResponse.class);
            scProgramResponse.setError(false);
        }
        log.info("Entity is ",scProgram);
        return scProgramResponse;
    }

    @Transactional
    public ScProgramResponse updateScProgramDetails(EditScProgramRequest scProgramRequest) {
        ScProgramResponse scProgramResponse = new ScProgramResponse();
        List<ScProgram> scProgramList = scProgramRepository.findByActiveAndScProgramName(true,scProgramRequest.getScProgramName());
        if (scProgramList.size() > 0) {
            scProgramResponse.setError(true);
            scProgramResponse.setError_description("ScProgram already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScProgram scProgram = scProgramRepository.findByScProgramIdAndActiveIn(scProgramRequest.getScProgramId(), Set.of(true, false));
            if (Objects.nonNull(scProgram)) {
                scProgram.setScProgramName(scProgramRequest.getScProgramName());
                scProgram.setActive(true);
                ScProgram scProgram1 = scProgramRepository.save(scProgram);
                scProgramResponse = mapper.scProgramEntityToObject(scProgram1, ScProgramResponse.class);
                scProgramResponse.setError(false);
            } else {
                scProgramResponse.setError(true);
                scProgramResponse.setError_description("Error occurred while fetching ScProgram");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return scProgramResponse;
    }
}
