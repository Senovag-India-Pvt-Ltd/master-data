package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.rpPageRoot.EditRpPageRootRequest;
import com.sericulture.masterdata.model.api.rpPageRoot.RpPageRootRequest;
import com.sericulture.masterdata.model.api.rpPageRoot.RpPageRootResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.RpPageRoot;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RpPageRootRepository;
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
public class RpPageRootService {
    @Autowired
    RpPageRootRepository rpPageRootRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public RpPageRootResponse insertRpPageRootDetails(RpPageRootRequest rpPageRootRequest){
        RpPageRootResponse rpPageRootResponse = new RpPageRootResponse();
        RpPageRoot rpPageRoot = mapper.rpPageRootObjectToEntity(rpPageRootRequest,RpPageRoot.class);
        validator.validate(rpPageRoot);
        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
            rpPageRootResponse.setError(true);
            rpPageRootResponse.setError_description("RpPageRoot name already exist");
//        }
//        else if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            rpPageRootResponse.setError(true);
//            rpPageRootResponse.setError_description("RpPageRoot name already exist with inactive state");
        }else {
            rpPageRootResponse = mapper.rpPageRootEntityToObject(rpPageRootRepository.save(rpPageRoot), RpPageRootResponse.class);
            rpPageRootResponse.setError(false);
        }
        return rpPageRootResponse;
    }

    public Map<String,Object> getPaginatedRpPageRootDetails(final Pageable pageable){
        return convertToMapResponse(rpPageRootRepository.findByActiveOrderByRpPageRootIdAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(rpPageRootRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<RpPageRoot> activeRpPageRoots) {
        Map<String, Object> response = new HashMap<>();

        List<RpPageRootResponse> rpPageRootResponses = activeRpPageRoots.getContent().stream()
                .map(rpPageRoot -> mapper.rpPageRootEntityToObject(rpPageRoot,RpPageRootResponse.class)).collect(Collectors.toList());
        response.put("rpPageRoot",rpPageRootResponses);
        response.put("currentPage", activeRpPageRoots.getNumber());
        response.put("totalItems", activeRpPageRoots.getTotalElements());
        response.put("totalPages", activeRpPageRoots.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RpPageRoot> activeRpPageRoots) {
        Map<String, Object> response = new HashMap<>();

        List<RpPageRootResponse> rpPageRootResponses = activeRpPageRoots.stream()
                .map(rpPageRoot -> mapper.rpPageRootEntityToObject(rpPageRoot,RpPageRootResponse.class)).collect(Collectors.toList());
        response.put("rpPageRoot",rpPageRootResponses);
        return response;
    }

    @Transactional
    public RpPageRootResponse deleteRpPageRootDetails(long id) {
        RpPageRootResponse rpPageRootResponse = new RpPageRootResponse();
        RpPageRoot rpPageRoot = rpPageRootRepository.findByRpPageRootIdAndActive(id, true);
        if (Objects.nonNull(rpPageRoot)) {
            rpPageRoot.setActive(false);
            rpPageRootResponse = mapper.rpPageRootEntityToObject(rpPageRootRepository.save(rpPageRoot), RpPageRootResponse.class);
            rpPageRootResponse.setError(false);
        } else {
            rpPageRootResponse.setError(true);
            rpPageRootResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return rpPageRootResponse;
    }

    public RpPageRootResponse getById(int id){
        RpPageRootResponse rpPageRootResponse = new RpPageRootResponse();
        RpPageRoot rpPageRoot = rpPageRootRepository.findByRpPageRootIdAndActive(id,true);
        if(rpPageRoot == null){
            rpPageRootResponse.setError(true);
            rpPageRootResponse.setError_description("Invalid id");
        }else{
            rpPageRootResponse =  mapper.rpPageRootEntityToObject(rpPageRoot,RpPageRootResponse.class);
            rpPageRootResponse.setError(false);
        }
        log.info("Entity is ",rpPageRoot);
        return rpPageRootResponse;
    }

    @Transactional
    public RpPageRootResponse updateRpPageRootDetails(EditRpPageRootRequest rpPageRootRequest) {
        RpPageRootResponse rpPageRootResponse = new RpPageRootResponse();
        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByActiveAndRpPageRootName(true,rpPageRootRequest.getRpPageRootName());
        if (rpPageRootList.size() > 0) {
            rpPageRootResponse.setError(true);
            rpPageRootResponse.setError_description("RpPageRoot already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            RpPageRoot rpPageRoot = rpPageRootRepository.findByRpPageRootIdAndActiveIn(rpPageRootRequest.getRpPageRootId(), Set.of(true, false));
            if (Objects.nonNull(rpPageRoot)) {
                rpPageRoot.setRpPageRootName(rpPageRootRequest.getRpPageRootName());
                rpPageRoot.setActive(true);
                RpPageRoot rpPageRoot1 = rpPageRootRepository.save(rpPageRoot);
                rpPageRootResponse = mapper.rpPageRootEntityToObject(rpPageRoot1, RpPageRootResponse.class);
                rpPageRootResponse.setError(false);
            } else {
                rpPageRootResponse.setError(true);
                rpPageRootResponse.setError_description("Error occurred while fetching RpPageRoot");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return rpPageRootResponse;
    }
}
