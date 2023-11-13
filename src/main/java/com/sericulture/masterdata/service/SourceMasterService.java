package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.sourceMaster.EditSourceMasterRequest;
import com.sericulture.masterdata.model.api.sourceMaster.SourceMasterRequest;
import com.sericulture.masterdata.model.api.sourceMaster.SourceMasterResponse;
import com.sericulture.masterdata.model.entity.SourceMaster;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.SourceMasterRepository;
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
public class SourceMasterService {
    @Autowired
    SourceMasterRepository sourceMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SourceMasterResponse getSourceMasterDetails(String sourceMasterName){
        SourceMaster sourceMaster = null;
        if(sourceMaster==null){
            sourceMaster = sourceMasterRepository.findBySourceMasterNameAndActive(sourceMasterName,true);
        }
        log.info("Entity is ",sourceMaster);
        return mapper.sourceMasterEntityToObject(sourceMaster,SourceMasterResponse.class);
    }

    @Transactional
    public SourceMasterResponse insertSourceMasterDetails(SourceMasterRequest sourceMasterRequest){
        SourceMaster sourceMaster = mapper.sourceMasterObjectToEntity(sourceMasterRequest,SourceMaster.class);
        validator.validate(sourceMaster);
        List<SourceMaster> sourceMasterList = sourceMasterRepository.findBySourceMasterName(sourceMasterRequest.getSourceMasterName());
        if(!sourceMasterList.isEmpty() && sourceMasterList.stream().filter(SourceMaster::getActive).findAny().isPresent()){
            throw new ValidationException("Source name already exist");
        }
        if(!sourceMasterList.isEmpty() && sourceMasterList.stream().filter(Predicate.not(SourceMaster::getActive)).findAny().isPresent()){
            throw new ValidationException("Source name already exist with inactive state");
        }
        return mapper.sourceMasterEntityToObject(sourceMasterRepository.save(sourceMaster),SourceMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedSourceMasterDetails(final Pageable pageable){
        return convertToMapResponse(sourceMasterRepository.findByActiveOrderBySourceMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(sourceMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<SourceMaster> activeSources) {
        Map<String, Object> response = new HashMap<>();

        List<SourceMasterResponse> sourceMasterResponses = activeSources.getContent().stream()
                .map(sourceMaster -> mapper.sourceMasterEntityToObject(sourceMaster,SourceMasterResponse.class)).collect(Collectors.toList());
        response.put("sourceMaster",sourceMasterResponses);
        response.put("currentPage", activeSources.getNumber());
        response.put("totalItems", activeSources.getTotalElements());
        response.put("totalPages", activeSources.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<SourceMaster> activeSources) {
        Map<String, Object> response = new HashMap<>();

        List<SourceMasterResponse> sourceMasterResponses = activeSources.stream()
                .map(sourceMaster -> mapper.sourceMasterEntityToObject(sourceMaster,SourceMasterResponse.class)).collect(Collectors.toList());
        response.put("sourceMaster",sourceMasterResponses);
        return response;
    }

    @Transactional
    public void deleteSourceMasterDetails(long id) {
        SourceMaster sourceMaster = sourceMasterRepository.findBySourceMasterIdAndActive(id, true);
        if (Objects.nonNull(sourceMaster)) {
            sourceMaster.setActive(false);
            sourceMasterRepository.save(sourceMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public SourceMasterResponse getById(int id){
        SourceMaster sourceMaster = sourceMasterRepository.findBySourceMasterIdAndActive(id,true);
        if(sourceMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",sourceMaster);
        return mapper.sourceMasterEntityToObject(sourceMaster,SourceMasterResponse.class);
    }

    @Transactional
    public SourceMasterResponse updateSourceMasterDetails(EditSourceMasterRequest sourceMasterRequest){
        List<SourceMaster> sourceMasterList = sourceMasterRepository.findBySourceMasterName(sourceMasterRequest.getSourceMasterName());
        if(sourceMasterList.size()>0){
            throw new ValidationException("Source already exists with this name, duplicates are not allowed.");
        }

        SourceMaster sourceMaster = sourceMasterRepository.findBySourceMasterIdAndActiveIn(sourceMasterRequest.getSourceMasterId(), Set.of(true,false));
        if(Objects.nonNull(sourceMaster)){
            sourceMaster.setSourceMasterName(sourceMasterRequest.getSourceMasterName());
            sourceMaster.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching state");
        }
        return mapper.sourceMasterEntityToObject(sourceMasterRepository.save(sourceMaster),SourceMasterResponse.class);
    }
}
