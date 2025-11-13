package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.silkExchange.EditSilkExchangeRequest;
import com.sericulture.masterdata.model.api.silkExchange.SilkExchangeRequest;
import com.sericulture.masterdata.model.api.silkExchange.SilkExchangeResponse;
import com.sericulture.masterdata.model.entity.SilkExchange;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.SilkExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
@Slf4j
public class SilkExchangeService {

    @Autowired
    SilkExchangeRepository silkExchangeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public SilkExchangeResponse insertSilkExchangeDetails(SilkExchangeRequest silkExchangeRequest){
        SilkExchangeResponse silkExchangeResponse = new SilkExchangeResponse();
        SilkExchange silkExchange = mapper.silkExchangeObjectToEntity(silkExchangeRequest,SilkExchange.class);
        validator.validate(silkExchange);
        List<SilkExchange> silkExchangeList = silkExchangeRepository.findBySilkExchangeNameAndSilkExchNameInKannada(silkExchangeRequest.getSilkExchangeName(),silkExchangeRequest.getSilkExchNameInKannada());
        if(!silkExchangeList.isEmpty() && silkExchangeList.stream().filter(SilkExchange::getActive).findAny().isPresent()){
            silkExchangeResponse.setError(true);
            silkExchangeResponse.setError_description("SilkExchange name already exist");
//        }
//        else if(!silkExchangeList.isEmpty() && silkExchangeList.stream().filter(Predicate.not(SilkExchange::getActive)).findAny().isPresent()){
//            silkExchangeResponse.setError(true);
//            silkExchangeResponse.setError_description("SilkExchange name already exist with inactive silkExchange");
        }else {
            silkExchangeResponse = mapper.silkExchangeEntityToObject(silkExchangeRepository.save(silkExchange), SilkExchangeResponse.class);
            silkExchangeResponse.setError(false);
        }
        return silkExchangeResponse;
    }

    public Map<String,Object> getPaginatedSilkExchangeDetails(final Pageable pageable){
        return convertToMapResponse(silkExchangeRepository.findByActiveOrderBySilkExchangeNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(silkExchangeRepository.findByActiveOrderBySilkExchangeNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<SilkExchange> activeSilkExchanges) {
        Map<String, Object> response = new HashMap<>();

        List<SilkExchangeResponse> silkExchangeResponses = activeSilkExchanges.getContent().stream()
                .map(silkExchange -> mapper.silkExchangeEntityToObject(silkExchange,SilkExchangeResponse.class)).collect(Collectors.toList());
        response.put("silkExchange",silkExchangeResponses);
        response.put("currentPage", activeSilkExchanges.getNumber());
        response.put("totalItems", activeSilkExchanges.getTotalElements());
        response.put("totalPages", activeSilkExchanges.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<SilkExchange> activeSilkExchanges) {
        Map<String, Object> response = new HashMap<>();

        List<SilkExchangeResponse> silkExchangeResponses = activeSilkExchanges.stream()
                .map(silkExchange -> mapper.silkExchangeEntityToObject(silkExchange,SilkExchangeResponse.class)).collect(Collectors.toList());
        response.put("silkExchange",silkExchangeResponses);
        return response;
    }

    @Transactional
    public SilkExchangeResponse deleteSilkExchangeDetails(long id) {

        SilkExchangeResponse silkExchangeResponse = new SilkExchangeResponse();
        SilkExchange silkExchange = silkExchangeRepository.findBySilkExchangeIdAndActive(id, true);
        if (Objects.nonNull(silkExchange)) {
            silkExchange.setActive(false);
            silkExchangeResponse = mapper.silkExchangeEntityToObject(silkExchangeRepository.save(silkExchange), SilkExchangeResponse.class);
            silkExchangeResponse.setError(false);
        } else {
            silkExchangeResponse.setError(true);
            silkExchangeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return silkExchangeResponse;
    }

    public SilkExchangeResponse getById(int id){
        SilkExchangeResponse silkExchangeResponse = new SilkExchangeResponse();
        SilkExchange silkExchange = silkExchangeRepository.findBySilkExchangeIdAndActive(id,true);
        if(silkExchange == null){
            silkExchangeResponse.setError(true);
            silkExchangeResponse.setError_description("Invalid id");
        }else{
            silkExchangeResponse =  mapper.silkExchangeEntityToObject(silkExchange,SilkExchangeResponse.class);
            silkExchangeResponse.setError(false);
        }
        log.info("Entity is ",silkExchange);
        return silkExchangeResponse;
    }

    @Transactional
    public SilkExchangeResponse updateSilkExchangeDetails(EditSilkExchangeRequest silkExchangeRequest){

        SilkExchangeResponse silkExchangeResponse = new SilkExchangeResponse();
        List<SilkExchange> silkExchangeList = silkExchangeRepository.findByActiveAndSilkExchangeNameAndSilkExchNameInKannada(true,silkExchangeRequest.getSilkExchangeName(),silkExchangeRequest.getSilkExchNameInKannada());
        if(silkExchangeList.size()>0){
            silkExchangeResponse.setError(true);
            silkExchangeResponse.setError_description("SilkExchange already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            SilkExchange silkExchange = silkExchangeRepository.findBySilkExchangeIdAndActiveIn(silkExchangeRequest.getSilkExchangeId(), Set.of(true,false));
            if(Objects.nonNull(silkExchange)){
                silkExchange.setSilkExchangeName(silkExchangeRequest.getSilkExchangeName());
                silkExchange.setSilkExchNameInKannada(silkExchangeRequest.getSilkExchNameInKannada());
                silkExchange.setActive(true);
                SilkExchange silkExchange1 = silkExchangeRepository.save(silkExchange);
                silkExchangeResponse = mapper.silkExchangeEntityToObject(silkExchange1, SilkExchangeResponse.class);
                silkExchangeResponse.setError(false);
            } else {
                silkExchangeResponse.setError(true);
                silkExchangeResponse.setError_description("Error occurred while fetching silkExchange");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return silkExchangeResponse;
    }
}
