package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.hobli.EditHobliRequest;
import com.sericulture.masterdata.model.api.hobli.HobliRequest;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.dto.DistrictDTO;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.Hobli;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.StateRepository;
import com.sericulture.masterdata.repository.HobliRepository;
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
public class HobliService {

    @Autowired
    HobliRepository hobliRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HobliResponse getHobliDetails(String hobliName){
        Hobli hobli = null;
        if(hobli==null){
            hobli = hobliRepository.findByHobliNameAndActive(hobliName,true);
        }
        log.info("Entity is ",hobli);
        return mapper.hobliEntityToObject(hobli,HobliResponse.class);
    }

    @Transactional
    public HobliResponse insertHobliDetails(HobliRequest hobliRequest){
        Hobli hobli = mapper.hobliObjectToEntity(hobliRequest,Hobli.class);
        validator.validate(hobli);
        List<Hobli> hobliList = hobliRepository.findByHobliNameAndTalukId(hobliRequest.getHobliName(), hobliRequest.getTalukId());
        if(!hobliList.isEmpty() && hobliList.stream().filter(Hobli::getActive).findAny().isPresent()){
            throw new ValidationException("Hobli name already exist");
        }
        if(!hobliList.isEmpty() && hobliList.stream().filter(Predicate.not(Hobli::getActive)).findAny().isPresent()){
            throw new ValidationException("Hobli name already exist with inactive state");
        }
        return mapper.hobliEntityToObject(hobliRepository.save(hobli),HobliResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHobliDetails(final Pageable pageable){
        return convertToMapResponse(hobliRepository.findByActiveOrderByHobliIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<Hobli> activeHoblis) {
        Map<String, Object> response = new HashMap<>();

        List<HobliResponse> hobliResponses = activeHoblis.getContent().stream()
                .map(hobli -> mapper.hobliEntityToObject(hobli,HobliResponse.class)).collect(Collectors.toList());
        response.put("hobli",hobliResponses);
        response.put("currentPage", activeHoblis.getNumber());
        response.put("totalItems", activeHoblis.getTotalElements());
        response.put("totalPages", activeHoblis.getTotalPages());

        return response;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHobliDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(hobliRepository.getByActiveOrderByHobliIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<HobliDTO> activeHoblis) {
        Map<String, Object> response = new HashMap<>();

        List<HobliResponse> hobliResponses = activeHoblis.getContent().stream()
                .map(hobli -> mapper.hobliDTOToObject(hobli,HobliResponse.class)).collect(Collectors.toList());
        response.put("hobli",hobliResponses);
        response.put("currentPage", activeHoblis.getNumber());
        response.put("totalItems", activeHoblis.getTotalElements());
        response.put("totalPages", activeHoblis.getTotalPages());
        return response;
    }

    @Transactional
    public void deleteHobliDetails(long id) {
        Hobli hobli = hobliRepository.findByHobliIdAndActive(id, true);
        if (Objects.nonNull(hobli)) {
            hobli.setActive(false);
            hobliRepository.save(hobli);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public HobliResponse getById(int id){
        Hobli hobli = hobliRepository.findByHobliIdAndActive(id,true);
        if(hobli == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",hobli);
        return mapper.hobliEntityToObject(hobli,HobliResponse.class);
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getHobliByTalukId(Long talukId){
        List<Hobli> hobliList = hobliRepository.findByTalukIdAndActive(talukId,true);
        if(hobliList.isEmpty()){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",hobliList);
        return convertListToMapResponse(hobliList);
    }

    private Map<String, Object> convertListToMapResponse(List<Hobli> hobliList) {
        Map<String, Object> response = new HashMap<>();
        List<HobliResponse> hobliResponses = hobliList.stream()
                .map(hobli -> mapper.hobliEntityToObject(hobli,HobliResponse.class)).collect(Collectors.toList());
        response.put("hobli",hobliResponses);
        response.put("totalItems", hobliList.size());
        return response;
    }

    @Transactional
    public HobliResponse updateHobliDetails(EditHobliRequest hobliRequest){
        List<Hobli> hobliList = hobliRepository.findByHobliName(hobliRequest.getHobliName());
        if(hobliList.size()>0){
            throw new ValidationException("Hobli already exists, duplicates are not allowed.");
        }
        Hobli hobli = hobliRepository.findByHobliIdAndActiveIn(hobliRequest.getHobliId(), Set.of(true,false));
        if(Objects.nonNull(hobli)){
            hobli.setStateId(hobliRequest.getStateId());
            hobli.setHobliName(hobliRequest.getHobliName());
            hobli.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching hobli");
        }
        return mapper.hobliEntityToObject(hobliRepository.save(hobli),HobliResponse.class);
    }

}