package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.rpPageRoot.EditRpPageRootRequest;
import com.sericulture.masterdata.model.api.rpPageRoot.RpPageRootRequest;
import com.sericulture.masterdata.model.api.rpPageRoot.RpPageRootResponse;
import com.sericulture.masterdata.model.entity.RpPageRoot;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RpPageRootResponse getRpPageRootDetails(String rpPageRootName){
        RpPageRoot rpPageRoot = null;
        if(rpPageRoot==null){
            rpPageRoot = rpPageRootRepository.findByRpPageRootNameAndActive(rpPageRootName,true);
        }
        log.info("Entity is ",rpPageRoot);
        return mapper.rpPageRootEntityToObject(rpPageRoot,RpPageRootResponse.class);
    }

    @Transactional
    public RpPageRootResponse insertRpPageRootDetails(RpPageRootRequest rpPageRootRequest){
        RpPageRoot rpPageRoot = mapper.rpPageRootObjectToEntity(rpPageRootRequest,RpPageRoot.class);
        validator.validate(rpPageRoot);
        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
            throw new ValidationException("RpPageRoot name already exist");
        }
        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
            throw new ValidationException("RpPageRoot name already exist with inactive state");
        }
        return mapper.rpPageRootEntityToObject(rpPageRootRepository.save(rpPageRoot),RpPageRootResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRpPageRootDetails(final Pageable pageable){
        return convertToMapResponse(rpPageRootRepository.findByActiveOrderByRpPageRootIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
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
    public void deleteRpPageRootDetails(long id) {
        RpPageRoot rpPageRoot = rpPageRootRepository.findByRpPageRootIdAndActive(id, true);
        if (Objects.nonNull(rpPageRoot)) {
            rpPageRoot.setActive(false);
            rpPageRootRepository.save(rpPageRoot);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public RpPageRootResponse getById(int id){
        RpPageRoot rpPageRoot = rpPageRootRepository.findByRpPageRootIdAndActive(id,true);
        if(rpPageRoot == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",rpPageRoot);
        return mapper.rpPageRootEntityToObject(rpPageRoot,RpPageRootResponse.class);
    }

    @Transactional
    public RpPageRootResponse updateRpPageRootDetails(EditRpPageRootRequest rpPageRootRequest){
        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
        if(rpPageRootList.size()>0){
            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
        }

        RpPageRoot rpPageRoot = rpPageRootRepository.findByRpPageRootIdAndActiveIn(rpPageRootRequest.getRpPageRootId(), Set.of(true,false));
        if(Objects.nonNull(rpPageRoot)){
            rpPageRoot.setRpPageRootName(rpPageRootRequest.getRpPageRootName());
            rpPageRoot.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching Page root");
        }
        return mapper.rpPageRootEntityToObject(rpPageRootRepository.save(rpPageRoot),RpPageRootResponse.class);
    }
}
