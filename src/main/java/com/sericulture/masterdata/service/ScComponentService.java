package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scComponent.EditScComponentRequest;
import com.sericulture.masterdata.model.api.scComponent.ScComponentRequest;
import com.sericulture.masterdata.model.api.scComponent.ScComponentResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.ScComponent;
import com.sericulture.masterdata.model.entity.Village;
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


    @Transactional
    public ScComponentResponse insertScComponentDetails(ScComponentRequest scComponentRequest){
        ScComponentResponse scComponentResponse = new ScComponentResponse();
        ScComponent scComponent = mapper.scComponentObjectToEntity(scComponentRequest,ScComponent.class);
        validator.validate(scComponent);
        List<ScComponent> scComponentList = scComponentRepository.findByScComponentName(scComponentRequest.getScComponentName());
        if(!scComponentList.isEmpty() && scComponentList.stream().filter(ScComponent::getActive).findAny().isPresent()){
            scComponentResponse.setError(true);
            scComponentResponse.setError_description("ScComponent name already exist");
//        }
//        else if(!scComponentList.isEmpty() && scComponentList.stream().filter(Predicate.not(ScComponent::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            scComponentResponse.setError(true);
//            scComponentResponse.setError_description("ScComponent name already exist with inactive state");
        }else {
            scComponentResponse = mapper.scComponentEntityToObject(scComponentRepository.save(scComponent), ScComponentResponse.class);
            scComponentResponse.setError(false);
        }
        return scComponentResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScComponentDetails(final Pageable pageable){
        return convertToMapResponse(scComponentRepository.findByActiveOrderByScComponentNameAsc( true, pageable));
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
    public ScComponentResponse deleteScComponentDetails(long id) {
        ScComponentResponse scComponentResponse = new ScComponentResponse();
        ScComponent scComponent = scComponentRepository.findByScComponentIdAndActive(id, true);
        if (Objects.nonNull(scComponent)) {
            scComponent.setActive(false);
            scComponentResponse = mapper.scComponentEntityToObject(scComponentRepository.save(scComponent), ScComponentResponse.class);
            scComponentResponse.setError(false);
        } else {
            scComponentResponse.setError(true);
            scComponentResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scComponentResponse;
    }

    @Transactional
    public ScComponentResponse getById(int id){
        ScComponentResponse scComponentResponse = new ScComponentResponse();
        ScComponent scComponent = scComponentRepository.findByScComponentIdAndActive(id,true);
        if(scComponent == null){
            scComponentResponse.setError(true);
            scComponentResponse.setError_description("Invalid id");
        }else{
            scComponentResponse =  mapper.scComponentEntityToObject(scComponent,ScComponentResponse.class);
            scComponentResponse.setError(false);
        }
        log.info("Entity is ",scComponent);
        return scComponentResponse;
    }

    @Transactional
    public ScComponentResponse updateScComponentDetails(EditScComponentRequest scComponentRequest) {
        ScComponentResponse scComponentResponse = new ScComponentResponse();
        List<ScComponent> scComponentList = scComponentRepository.findByActiveAndScComponentName(true,scComponentRequest.getScComponentName());
        if (scComponentList.size() > 0) {
            scComponentResponse.setError(true);
            scComponentResponse.setError_description("ScComponent already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScComponent scComponent = scComponentRepository.findByScComponentIdAndActiveIn(scComponentRequest.getScComponentId(), Set.of(true, false));
            if (Objects.nonNull(scComponent)) {
                scComponent.setScComponentName(scComponentRequest.getScComponentName());
                scComponent.setDbtCode(scComponentRequest.getDbtCode());
                scComponent.setActive(true);
                ScComponent scComponent1 = scComponentRepository.save(scComponent);
                scComponentResponse = mapper.scComponentEntityToObject(scComponent1, ScComponentResponse.class);
                scComponentResponse.setError(false);
            } else {
                scComponentResponse.setError(true);
                scComponentResponse.setError_description("Error occurred while fetching ScComponent");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return scComponentResponse;
    }
}
