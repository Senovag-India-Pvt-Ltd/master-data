package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.state.EditStateRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.Village;
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
        StateResponse stateResponse = new StateResponse();
        State state = null;
        if(state==null){
            state = stateRepository.findByStateNameAndActive(stateName,true);
            stateResponse = mapper.stateEntityToObject(state,StateResponse.class);
            stateResponse.setError(false);
        }else{
            stateResponse.setError(true);
            stateResponse.setError_description("State not found");
        }
        log.info("Entity is ",state);
        return stateResponse;
    }

    @Transactional
    public StateResponse insertStateDetails(StateRequest stateRequest){
        StateResponse stateResponse = new StateResponse();
        State state = mapper.stateObjectToEntity(stateRequest,State.class);
        validator.validate(state);
        List<State> stateList = stateRepository.findByStateName(stateRequest.getStateName());
        if(!stateList.isEmpty() && stateList.stream().filter(State::getActive).findAny().isPresent()){
            stateResponse.setError(true);
            stateResponse.setError_description("State name already exist");
        }
        else if(!stateList.isEmpty() && stateList.stream().filter(Predicate.not(State::getActive)).findAny().isPresent()){
            stateResponse.setError(true);
            stateResponse.setError_description("State name already exist with inactive state");
        }else {
            stateResponse = mapper.stateEntityToObject(stateRepository.save(state), StateResponse.class);
            stateResponse.setError(false);
        }
        return stateResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedStateDetails(final Pageable pageable){
        return convertToMapResponse(stateRepository.findByActiveOrderByStateNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(stateRepository.findByActiveOrderByStateNameAsc(isActive));
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

    private Map<String, Object> convertListEntityToMapResponse(final List<State> activeStates) {
        Map<String, Object> response = new HashMap<>();

        List<StateResponse> stateResponses = activeStates.stream()
                .map(state -> mapper.stateEntityToObject(state,StateResponse.class)).collect(Collectors.toList());
        response.put("state",stateResponses);
        return response;
    }

    @Transactional
    public StateResponse deleteStateDetails(long id) {

            StateResponse stateResponse = new StateResponse();
            State state = stateRepository.findByStateIdAndActive(id, true);
        if (Objects.nonNull(state)) {
            state.setActive(false);
            stateResponse = mapper.stateEntityToObject(stateRepository.save(state), StateResponse.class);
            stateResponse.setError(false);
        } else {
            stateResponse.setError(true);
            stateResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
            return stateResponse;
    }

    @Transactional
    public StateResponse getById(int id){
            StateResponse stateResponse = new StateResponse();
            State state = stateRepository.findByStateIdAndActive(id,true);
        if(state == null){
            stateResponse.setError(true);
            stateResponse.setError_description("Invalid id");
        }else{
            stateResponse =  mapper.stateEntityToObject(state,StateResponse.class);
            stateResponse.setError(false);
        }
            log.info("Entity is ",state);
            return stateResponse;
    }

    @Transactional
    public StateResponse updateStateDetails(EditStateRequest stateRequest){

            StateResponse stateResponse = new StateResponse();
        List<State> stateList = stateRepository.findByStateName(stateRequest.getStateName());
        if(stateList.size()>0){
            stateResponse.setError(true);
            stateResponse.setError_description("State already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            State state = stateRepository.findByStateIdAndActiveIn(stateRequest.getStateId(), Set.of(true,false));
        if(Objects.nonNull(state)){
            state.setStateName(stateRequest.getStateName());
            state.setActive(true);
            State state1 = stateRepository.save(state);
            stateResponse = mapper.stateEntityToObject(state1, StateResponse.class);
            stateResponse.setError(false);
        } else {
            stateResponse.setError(true);
            stateResponse.setError_description("Error occurred while fetching state");
            // throw new ValidationException("Error occurred while fetching village");
        }
        }
            return stateResponse;
    }


}
