package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.state.EditStateRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
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
public class StateService {

    @Autowired
    StateRepository stateRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public StateResponse getStateDetails(String stateName){
        State state = null;
        if(state==null){
            state = stateRepository.findByStateNameAndActive(stateName,true);
        }
        log.info("Entity is ",state);
        return mapper.stateEntityToObject(state,StateResponse.class);
    }

    @Transactional
    public StateResponse insertStateDetails(StateRequest stateRequest){
        State state = mapper.stateObjectToEntity(stateRequest,State.class);
        validator.validate(state);
        List<State> stateList = stateRepository.findByStateName(stateRequest.getStateName());
        if(!stateList.isEmpty() && stateList.stream().filter(State::getActive).findAny().isPresent()){
            throw new ValidationException("State name already exist");
        }
        if(!stateList.isEmpty() && stateList.stream().filter(Predicate.not(State::getActive)).findAny().isPresent()){
            throw new ValidationException("State name already exist with inactive state");
        }
        return mapper.stateEntityToObject(stateRepository.save(state),StateResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedStateDetails(final Pageable pageable){
        return convertToMapResponse(stateRepository.findByActiveOrderByStateIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<State> activeStates) {
        Map<String, Object> response = new HashMap<>();

        List<StateResponse> stateResponses = activeStates.getContent().stream()
                .map(state -> mapper.stateEntityToObject(state,StateResponse.class)).collect(Collectors.toList());
        response.put("state",stateResponses);
        response.put("currentPage", activeStates.getNumber());
        response.put("totalItems", activeStates.getTotalElements());
        response.put("totalPages", activeStates.getTotalPages());

        return response;
    }

    @Transactional
    public void deleteStateDetails(long id) {
        State state = stateRepository.findByStateIdAndActive(id, true);
        if (Objects.nonNull(state)) {
            state.setActive(false);
            stateRepository.save(state);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public StateResponse getById(int id){
        State state = stateRepository.findByStateIdAndActive(id,true);
        if(state == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",state);
        return mapper.stateEntityToObject(state,StateResponse.class);
    }

    @Transactional
    public StateResponse updateStateDetails(EditStateRequest stateRequest){
        List<State> stateList = stateRepository.findByStateName(stateRequest.getStateName());
        if(stateList.size()>0){
            throw new ValidationException("state already exists with this name, duplicates are not allowed.");
        }

        State state = stateRepository.findByStateIdAndActiveIn(stateRequest.getStateId(), Set.of(true,false));
        if(Objects.nonNull(state)){
            state.setStateName(stateRequest.getStateName());
            state.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching state");
        }
        return mapper.stateEntityToObject(stateRepository.save(state),StateResponse.class);
    }

}
