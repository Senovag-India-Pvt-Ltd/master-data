package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.relationship.EditRelationshipRequest;
import com.sericulture.masterdata.model.api.relationship.RelationshipRequest;
import com.sericulture.masterdata.model.api.relationship.RelationshipResponse;
import com.sericulture.masterdata.model.api.role.RoleResponse;
import com.sericulture.masterdata.model.api.state.EditStateRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.entity.Relationship;
import com.sericulture.masterdata.model.entity.Role;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RelationshipRepository;
import com.sericulture.masterdata.repository.StateRepository;
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
public class RelationshipService {

    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RelationshipResponse getRelationshipDetails(String relationshipName){
        Relationship relationship = null;
        if(relationship==null){
            relationship = relationshipRepository.findByRelationshipNameAndActive(relationshipName,true);
        }
        log.info("Entity is ",relationship);
        return mapper.relationshipEntityToObject(relationship,RelationshipResponse.class);
    }

    @Transactional
    public RelationshipResponse insertRelationshipDetails(RelationshipRequest relationshipRequest){
        Relationship relationship = mapper.relationshipObjectToEntity(relationshipRequest,Relationship.class);
        validator.validate(relationship);
        List<Relationship> relationshipList = relationshipRepository.findByRelationshipName(relationshipRequest.getRelationshipName());
        if(!relationshipList.isEmpty() && relationshipList.stream().filter(Relationship::getActive).findAny().isPresent()){
            throw new ValidationException("Relationship name already exist");
        }
        if(!relationshipList.isEmpty() && relationshipList.stream().filter(Predicate.not(Relationship::getActive)).findAny().isPresent()){
            throw new ValidationException("Relationship name already exist with inactive state");
        }
        return mapper.relationshipEntityToObject(relationshipRepository.save(relationship),RelationshipResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedRelationshipDetails(final Pageable pageable){
        return convertToMapResponse(relationshipRepository.findByActiveOrderByRelationshipIdAsc( true, pageable));
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(relationshipRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Relationship> activeRelationships) {
        Map<String, Object> response = new HashMap<>();

        List<RelationshipResponse> relationshipResponses = activeRelationships.getContent().stream()
                .map(relationship -> mapper.relationshipEntityToObject(relationship,RelationshipResponse.class)).collect(Collectors.toList());
        response.put("relationship",relationshipResponses);
        response.put("currentPage", activeRelationships.getNumber());
        response.put("totalItems", activeRelationships.getTotalElements());
        response.put("totalPages", activeRelationships.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Relationship> activeRelationships) {
        Map<String, Object> response = new HashMap<>();

        List<RelationshipResponse> relationshipResponses = activeRelationships.stream()
                .map(relationship -> mapper.relationshipEntityToObject(relationship,RelationshipResponse.class)).collect(Collectors.toList());
        response.put("relationship",relationshipResponses);
        return response;
    }

    @Transactional
    public void deleteRelationshipDetails(long id) {
        Relationship relationship = relationshipRepository.findByRelationshipIdAndActive(id, true);
        if (Objects.nonNull(relationship)) {
            relationship.setActive(false);
            relationshipRepository.save(relationship);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public RelationshipResponse getById(int id){
        Relationship relationship = relationshipRepository.findByRelationshipIdAndActive(id,true);
        if(relationship == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",relationship);
        return mapper.relationshipEntityToObject(relationship,RelationshipResponse.class);
    }

    @Transactional
    public RelationshipResponse updateRelationshipDetails(EditRelationshipRequest relationshipRequest){
        List<Relationship> relationshipList = relationshipRepository.findByRelationshipName(relationshipRequest.getRelationshipName());
        if(relationshipList.size()>0){
            throw new ValidationException("relationship already exists for the given code and title, duplicates are not allowed.");
        }

        Relationship relationship = relationshipRepository.findByRelationshipIdAndActiveIn(relationshipRequest.getRelationshipId(), Set.of(true,false));
        if(Objects.nonNull(relationship)){
            relationship.setRelationshipName(relationshipRequest.getRelationshipName());
            relationship.setActive(true);
        }
        return mapper.relationshipEntityToObject(relationshipRepository.save(relationship),RelationshipResponse.class);
    }

}
