package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.sourceMaster.EditSourceMasterRequest;
import com.sericulture.masterdata.model.api.sourceMaster.SourceMasterRequest;
import com.sericulture.masterdata.model.api.sourceMaster.SourceMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.SourceMaster;
import com.sericulture.masterdata.model.entity.Village;
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
        SourceMasterResponse sourceMasterResponse = new SourceMasterResponse();
        SourceMaster sourceMaster = sourceMasterRepository.findBySourceMasterNameAndActive(sourceMasterName,true);
        if(sourceMaster==null){
            sourceMasterResponse.setError(true);
            sourceMasterResponse.setError_description("Source not found");
        }else{
            sourceMasterResponse = mapper.sourceMasterEntityToObject(sourceMaster, SourceMasterResponse.class);
            sourceMasterResponse.setError(false);
        }
        log.info("Entity is ",sourceMaster);
        return sourceMasterResponse;
    }

    @Transactional
    public SourceMasterResponse insertSourceMasterDetails(SourceMasterRequest sourceMasterRequest){
        SourceMasterResponse sourceMasterResponse = new SourceMasterResponse();
        SourceMaster sourceMaster = mapper.sourceMasterObjectToEntity(sourceMasterRequest,SourceMaster.class);
        validator.validate(sourceMaster);
        List<SourceMaster> sourceMasterList = sourceMasterRepository.findBySourceMasterNameAndSourceNameInKannada(sourceMasterRequest.getSourceMasterName(),sourceMasterRequest.getSourceNameInKannada());
        if(!sourceMasterList.isEmpty() && sourceMasterList.stream().filter(SourceMaster::getActive).findAny().isPresent()){
            sourceMasterResponse.setError(true);
            sourceMasterResponse.setError_description("Source name already exist");
        }
        else if(!sourceMasterList.isEmpty() && sourceMasterList.stream().filter(Predicate.not(SourceMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            sourceMasterResponse.setError(true);
            sourceMasterResponse.setError_description("Source name already exist with inactive state");
        }else {
            sourceMasterResponse = mapper.sourceMasterEntityToObject(sourceMasterRepository.save(sourceMaster), SourceMasterResponse.class);
            sourceMasterResponse.setError(false);
        }
        return sourceMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedSourceMasterDetails(final Pageable pageable){
        return convertToMapResponse(sourceMasterRepository.findByActiveOrderBySourceMasterNameAsc( true, pageable));
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
    public SourceMasterResponse deleteSourceMasterDetails(long id) {
        SourceMasterResponse sourceMasterResponse = new SourceMasterResponse();
        SourceMaster sourceMaster = sourceMasterRepository.findBySourceMasterIdAndActive(id, true);
        if (Objects.nonNull(sourceMaster)) {
            sourceMaster.setActive(false);
            sourceMasterResponse = mapper.sourceMasterEntityToObject(sourceMasterRepository.save(sourceMaster), SourceMasterResponse.class);
            sourceMasterResponse.setError(false);
        } else {
            sourceMasterResponse.setError(true);
            sourceMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return sourceMasterResponse;
    }

    @Transactional
    public SourceMasterResponse getById(int id){
        SourceMasterResponse sourceMasterResponse = new SourceMasterResponse();
        SourceMaster sourceMaster = sourceMasterRepository.findBySourceMasterIdAndActive(id,true);
        if(sourceMaster == null){
            sourceMasterResponse.setError(true);
            sourceMasterResponse.setError_description("Invalid id");
        }else{
            sourceMasterResponse =  mapper.sourceMasterEntityToObject(sourceMaster,SourceMasterResponse.class);
            sourceMasterResponse.setError(false);
        }
        log.info("Entity is ",sourceMaster);
        return sourceMasterResponse;
    }

    @Transactional
    public SourceMasterResponse updateSourceMasterDetails(EditSourceMasterRequest sourceMasterRequest){
            SourceMasterResponse sourceMasterResponse = new SourceMasterResponse();
            List<SourceMaster> sourceMasterList = sourceMasterRepository.findBySourceMasterNameAndSourceNameInKannadaAndSourceMasterIdIsNot(sourceMasterRequest.getSourceMasterName(),sourceMasterRequest.getSourceNameInKannada(),sourceMasterRequest.getSourceMasterId());
            if(sourceMasterList.size()>0){
                sourceMasterResponse.setError(true);
                sourceMasterResponse.setError_description("Source already exists, duplicates are not allowed.");
                // throw new ValidationException("Village already exists, duplicates are not allowed.");
            }else {
            SourceMaster sourceMaster = sourceMasterRepository.findBySourceMasterIdAndActiveIn(sourceMasterRequest.getSourceMasterId(), Set.of(true,false));
            if(Objects.nonNull(sourceMaster)){
                sourceMaster.setSourceMasterName(sourceMasterRequest.getSourceMasterName());
                sourceMaster.setSourceNameInKannada(sourceMasterRequest.getSourceNameInKannada());
                sourceMaster.setActive(true);
                SourceMaster sourceMaster1 = sourceMasterRepository.save(sourceMaster);
                sourceMasterResponse = mapper.sourceMasterEntityToObject(sourceMaster1, SourceMasterResponse.class);
                sourceMasterResponse.setError(false);
            } else {
                sourceMasterResponse.setError(true);
                sourceMasterResponse.setError_description("Error occurred while fetching Source");
                // throw new ValidationException("Error occurred while fetching village");
            }
            }
            return sourceMasterResponse;
    }
}
