package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scComponent.EditScComponentRequest;
import com.sericulture.masterdata.model.api.scComponent.ScComponentRequest;
import com.sericulture.masterdata.model.api.scComponent.ScComponentResponse;
import com.sericulture.masterdata.model.entity.ScComponent;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScComponentRepository;
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
public class ScComponentService {
    @Autowired
    ScComponentRepository scComponentRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ScComponentResponse getScComponentDetails(String scComponentName){
        ScComponent scComponent = null;
        if(scComponent==null){
            scComponent = scComponentRepository.findByScComponentNameAndActive(scComponentName,true);
        }
        log.info("Entity is ",scComponent);
        return mapper.scComponentEntityToObject(scComponent,ScComponentResponse.class);
    }

    @Transactional
    public ScComponentResponse insertScComponentDetails(ScComponentRequest scComponentRequest){
        ScComponent scComponent = mapper.scComponentObjectToEntity(scComponentRequest,ScComponent.class);
        validator.validate(scComponent);
        List<ScComponent> scComponentList = scComponentRepository.findByScComponentName(scComponentRequest.getScComponentName());
        if(!scComponentList.isEmpty() && scComponentList.stream().filter(ScComponent::getActive).findAny().isPresent()){
            throw new ValidationException("ScComponent name already exist");
        }
        if(!scComponentList.isEmpty() && scComponentList.stream().filter(Predicate.not(ScComponent::getActive)).findAny().isPresent()){
            throw new ValidationException("ScComponent name already exist with inactive state");
        }
        return mapper.scComponentEntityToObject(scComponentRepository.save(scComponent),ScComponentResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScComponentDetails(final Pageable pageable){
        return convertToMapResponse(scComponentRepository.findByActiveOrderByScComponentIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scComponentRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScComponent> activeScComponents) {
        Map<String, Object> response = new HashMap<>();

        List<ScComponentResponse> scComponentResponses = activeScComponents.getContent().stream()
                .map(scComponent -> mapper.scComponentEntityToObject(scComponent,ScComponentResponse.class)).collect(Collectors.toList());
        response.put("scComponent",scComponentResponses);
        response.put("currentPage", activeScComponents.getNumber());
        response.put("totalItems", activeScComponents.getTotalElements());
        response.put("totalPages", activeScComponents.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScComponent> activeScComponents) {
        Map<String, Object> response = new HashMap<>();

        List<ScComponentResponse> scComponentResponses = activeScComponents.stream()
                .map(scComponent -> mapper.scComponentEntityToObject(scComponent,ScComponentResponse.class)).collect(Collectors.toList());
        response.put("scComponent",scComponentResponses);
        return response;
    }

    @Transactional
    public void deleteScComponentDetails(long id) {
        ScComponent scComponent = scComponentRepository.findByScComponentIdAndActive(id, true);
        if (Objects.nonNull(scComponent)) {
            scComponent.setActive(false);
            scComponentRepository.save(scComponent);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public ScComponentResponse getById(int id){
        ScComponent scComponent = scComponentRepository.findByScComponentIdAndActive(id,true);
        if(scComponent == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",scComponent);
        return mapper.scComponentEntityToObject(scComponent,ScComponentResponse.class);
    }

    @Transactional
    public ScComponentResponse updateScComponentDetails(EditScComponentRequest scComponentRequest){
        List<ScComponent> scComponentList = scComponentRepository.findByScComponentName(scComponentRequest.getScComponentName());
        if(scComponentList.size()>0){
            throw new ValidationException("ScComponent already exists with this name, duplicates are not allowed.");
        }

        ScComponent scComponent = scComponentRepository.findByScComponentIdAndActiveIn(scComponentRequest.getScComponentId(), Set.of(true,false));
        if(Objects.nonNull(scComponent)){
            scComponent.setScComponentName(scComponentRequest.getScComponentName());
            scComponent.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching Component");
        }
        return mapper.scComponentEntityToObject(scComponentRepository.save(scComponent),ScComponentResponse.class);
    }
}