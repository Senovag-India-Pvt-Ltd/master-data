package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.godown.EditGodownRequest;
import com.sericulture.masterdata.model.api.godown.GodownRequest;
import com.sericulture.masterdata.model.api.godown.GodownResponse;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.entity.Godown;
import com.sericulture.masterdata.model.entity.Hobli;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.GodownRepository;
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
public class GodownService {

    @Autowired
    GodownRepository godownRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GodownResponse getGodownDetails(String godownName){
        Godown godown = null;
        if(godown==null){
            godown = godownRepository.findByGodownNameAndActive(godownName,true);
        }
        log.info("Entity is ",godown);
        return mapper.godownEntityToObject(godown,GodownResponse.class);
    }

    @Transactional
    public GodownResponse insertGodownDetails(GodownRequest godownRequest){
        Godown godown = mapper.godownObjectToEntity(godownRequest,Godown.class);
        validator.validate(godown);
        List<Godown> godownList = godownRepository.findByGodownName(godownRequest.getGodownName());
        if(!godownList.isEmpty() && godownList.stream().filter(Godown::getActive).findAny().isPresent()){
            throw new ValidationException("Godown name already exist");
        }
        if(!godownList.isEmpty() && godownList.stream().filter(Predicate.not(Godown::getActive)).findAny().isPresent()){
            throw new ValidationException("Godown name already exist with inactive state");
        }
        return mapper.godownEntityToObject(godownRepository.save(godown),GodownResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedGodownDetails(final Pageable pageable){
        return convertToMapResponse(godownRepository.findByActiveOrderByGodownIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(godownRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Godown> activeGodowns) {
        Map<String, Object> response = new HashMap<>();

        List<GodownResponse> godownResponses = activeGodowns.getContent().stream()
                .map(godown -> mapper.godownEntityToObject(godown,GodownResponse.class)).collect(Collectors.toList());
        response.put("godown",godownResponses);
        response.put("currentPage", activeGodowns.getNumber());
        response.put("totalItems", activeGodowns.getTotalElements());
        response.put("totalPages", activeGodowns.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Godown> activeGodowns) {
        Map<String, Object> response = new HashMap<>();

        List<GodownResponse> godownResponses = activeGodowns.stream()
                .map(godown -> mapper.godownEntityToObject(godown,GodownResponse.class)).collect(Collectors.toList());
        response.put("godown",godownResponses);
        return response;
    }

    @Transactional
    public void deleteGodownDetails(long id) {
        Godown godown = godownRepository.findByGodownIdAndActive(id, true);
        if (Objects.nonNull(godown)) {
            godown.setActive(false);
            godownRepository.save(godown);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public GodownResponse getById(int id){
        Godown godown = godownRepository.findByGodownIdAndActive(id,true);
        if(godown == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",godown);
        return mapper.godownEntityToObject(godown,GodownResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getByMarketMasterId(int marketMasterId){
        List<Godown> godownList = godownRepository.findByMarketMasterIdAndActive(marketMasterId,true);
        if(godownList.isEmpty()){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",godownList);
        return convertListToMapResponse(godownList);
    }

    private Map<String, Object> convertListToMapResponse(List<Godown> godownList) {
        Map<String, Object> response = new HashMap<>();
        List<GodownResponse> godownResponses = godownList.stream()
                .map(godown -> mapper.godownEntityToObject(godown,GodownResponse.class)).collect(Collectors.toList());
        response.put("godown",godownResponses);
        response.put("totalItems", godownResponses.size());
        return response;
    }


    @Transactional
    public GodownResponse updateGodownDetails(EditGodownRequest godownRequest){
        List<Godown> godownList = godownRepository.findByGodownName(godownRequest.getGodownName());
        if(godownList.size()>0){
            throw new ValidationException("Godown already exists with this name, duplicates are not allowed.");
        }

        Godown godown = godownRepository.findByGodownIdAndActiveIn(godownRequest.getGodownId(), Set.of(true,false));
        if(Objects.nonNull(godown)){
            godown.setGodownName(godownRequest.getGodownName());
            godown.setMarketMasterId(godownRequest.getMarketMasterId());
            godown.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching godown");
        }
        return mapper.godownEntityToObject(godownRepository.save(godown),GodownResponse.class);
    }

}