package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scProgram.EditScProgramRequest;
import com.sericulture.masterdata.model.api.scProgram.ScProgramRequest;
import com.sericulture.masterdata.model.api.scProgram.ScProgramResponse;
import com.sericulture.masterdata.model.entity.ScProgram;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ScProgramResponse getScProgramDetails(String scProgramName){
        ScProgram scProgram = null;
        if(scProgram==null){
            scProgram = scProgramRepository.findByScProgramNameAndActive(scProgramName,true);
        }
        log.info("Entity is ",scProgram);
        return mapper.scProgramEntityToObject(scProgram,ScProgramResponse.class);
    }

    @Transactional
    public ScProgramResponse insertScProgramDetails(ScProgramRequest scProgramRequest){
        ScProgram scProgram = mapper.scProgramObjectToEntity(scProgramRequest,ScProgram.class);
        validator.validate(scProgram);
        List<ScProgram> scProgramList = scProgramRepository.findByScProgramName(scProgramRequest.getScProgramName());
        if(!scProgramList.isEmpty() && scProgramList.stream().filter(ScProgram::getActive).findAny().isPresent()){
            throw new ValidationException("ScProgram name already exist");
        }
        if(!scProgramList.isEmpty() && scProgramList.stream().filter(Predicate.not(ScProgram::getActive)).findAny().isPresent()){
            throw new ValidationException("ScProgram name already exist with inactive state");
        }
        return mapper.scProgramEntityToObject(scProgramRepository.save(scProgram),ScProgramResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScProgramDetails(final Pageable pageable){
        return convertToMapResponse(scProgramRepository.findByActiveOrderByScProgramIdAsc( true, pageable));
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
    public void deleteScProgramDetails(long id) {
        ScProgram scProgram = scProgramRepository.findByScProgramIdAndActive(id, true);
        if (Objects.nonNull(scProgram)) {
            scProgram.setActive(false);
            scProgramRepository.save(scProgram);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public ScProgramResponse getById(int id){
        ScProgram scProgram = scProgramRepository.findByScProgramIdAndActive(id,true);
        if(scProgram == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",scProgram);
        return mapper.scProgramEntityToObject(scProgram,ScProgramResponse.class);
    }

    @Transactional
    public ScProgramResponse updateScProgramDetails(EditScProgramRequest scProgramRequest){
        List<ScProgram> scProgramList = scProgramRepository.findByScProgramName(scProgramRequest.getScProgramName());
        if(scProgramList.size()>0){
            throw new ValidationException("ScProgram already exists with this name, duplicates are not allowed.");
        }

        ScProgram scProgram = scProgramRepository.findByScProgramIdAndActiveIn(scProgramRequest.getScProgramId(), Set.of(true,false));
        if(Objects.nonNull(scProgram)){
            scProgram.setScProgramName(scProgramRequest.getScProgramName());
            scProgram.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching Program");
        }
        return mapper.scProgramEntityToObject(scProgramRepository.save(scProgram),ScProgramResponse.class);
    }
}
