package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.financialYearMaster.EditFinancialYearMasterRequest;
import com.sericulture.masterdata.model.api.financialYearMaster.FinancialYearMasterRequest;
import com.sericulture.masterdata.model.api.financialYearMaster.FinancialYearMasterResponse;
import com.sericulture.masterdata.model.entity.FinancialYearMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.FinancialYearMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
@Slf4j
public class FinancialYearMasterService {
    @Autowired
    FinancialYearMasterRepository financialYearMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public FinancialYearMasterResponse getFinancialYearMasterDetails(String financialYear){
        FinancialYearMasterResponse financialYearMasterResponse = new FinancialYearMasterResponse();
        FinancialYearMaster financialYearMaster= financialYearMasterRepository.findByFinancialYearAndActive(financialYear,true);
        if(financialYear==null){
            financialYearMasterResponse.setError(true);
            financialYearMasterResponse.setError_description("Program not found");
        }else{
            financialYearMasterResponse = mapper.financialYearMasterEntityToObject(financialYearMaster,FinancialYearMasterResponse.class);
            financialYearMasterResponse.setError(false);
        }
        log.info("Entity is ",financialYear);
        return financialYearMasterResponse;
    }

    @Transactional
    public FinancialYearMasterResponse insertFinancialYearMasterDetails(FinancialYearMasterRequest financialYearMasterRequest){
        FinancialYearMasterResponse financialYearMasterResponse = new FinancialYearMasterResponse();
        FinancialYearMaster financialYearMaster = mapper.financialYearMasterObjectToEntity(financialYearMasterRequest,FinancialYearMaster.class);
        validator.validate(financialYearMaster);
        List<FinancialYearMaster> financialYearMasterList = financialYearMasterRepository.findByFinancialYearAndIsDefault(financialYearMasterRequest.getFinancialYear(), financialYearMasterRequest.getIsDefault());
        if(!financialYearMasterList.isEmpty() && financialYearMasterList.stream().filter( FinancialYearMaster::getActive).findAny().isPresent()){
            financialYearMasterResponse.setError(true);
            financialYearMasterResponse.setError_description("Financial Year name already exist");
//        }
//        else if(! financialYearMasterList.isEmpty() && financialYearMasterList.stream().filter(Predicate.not( FinancialYearMaster::getActive)).findAny().isPresent()){
//            FinancialYearMasterResponse.setError(true);
//            FinancialYearMasterResponse.setError_description("Tr Program name already exist with inactive state");
        }else {
            if(financialYearMasterRequest.getIsDefault()){
                List<FinancialYearMaster> financialYearMasters = financialYearMasterRepository.findByActive(true);
                for(FinancialYearMaster financialYearMaster1: financialYearMasters){
                    financialYearMaster1.setIsDefault(false);
                    financialYearMasterRepository.save(financialYearMaster1);
                }
            }
            financialYearMasterResponse  = mapper.financialYearMasterEntityToObject( financialYearMasterRepository.save(financialYearMaster), FinancialYearMasterResponse.class);
            financialYearMasterResponse.setError(false);
        }
        return financialYearMasterResponse;
    }

    public Map<String,Object> getPaginatedFinancialYearMasterDetails(final Pageable pageable){
        return convertToMapResponse(financialYearMasterRepository.findByActiveOrderByFinancialYearAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(financialYearMasterRepository.findByActive(isActive));
    }

    @Transactional
    public FinancialYearMasterResponse getIsDefault(){
        FinancialYearMasterResponse financialYearMasterResponse = new FinancialYearMasterResponse();
        FinancialYearMaster financialYearMaster= financialYearMasterRepository.findByIsDefaultAndActive(true, true);
        if(financialYearMaster== null){
            financialYearMasterResponse.setError(true);
            financialYearMasterResponse.setError_description("Invalid id");
        }else{
            financialYearMasterResponse =  mapper.financialYearMasterEntityToObject(financialYearMaster, FinancialYearMasterResponse.class);
            financialYearMasterResponse.setError(false);
        }
        log.info("Entity is ",financialYearMaster);
        return financialYearMasterResponse;
    }

    private Map<String, Object> convertToMapResponse(final Page<FinancialYearMaster> activeFinancialYearMasters) {
        Map<String, Object> response = new HashMap<>();

        List<FinancialYearMasterResponse> financialYearMasterResponses= activeFinancialYearMasters.getContent().stream()
                .map(financialYearMaster -> mapper.financialYearMasterEntityToObject(financialYearMaster,FinancialYearMasterResponse.class)).collect(Collectors.toList());
        response.put("financialYearMaster",financialYearMasterResponses);
        response.put("currentPage", activeFinancialYearMasters.getNumber());
        response.put("totalItems", activeFinancialYearMasters.getTotalElements());
        response.put("totalPages", activeFinancialYearMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<FinancialYearMaster> activeFinancialYearMasters) {
        Map<String, Object> response = new HashMap<>();

        List<FinancialYearMasterResponse> financialYearMasterResponses= activeFinancialYearMasters.stream()
                .map(financialYearMaster -> mapper.financialYearMasterEntityToObject(financialYearMaster,FinancialYearMasterResponse.class)).collect(Collectors.toList());
        response.put("financialYearMaster",financialYearMasterResponses);
        return response;
    }

    @Transactional
    public FinancialYearMasterResponse deleteFinancialYearMasterDetails(long id) {

        FinancialYearMasterResponse financialYearMasterResponse = new FinancialYearMasterResponse();
        FinancialYearMaster financialYearMaster= financialYearMasterRepository.findByFinancialYearMasterIdAndActive(id, true);
        if (Objects.nonNull(financialYearMaster)) {
            financialYearMaster.setActive(false);
            financialYearMasterResponse= mapper.financialYearMasterEntityToObject(financialYearMasterRepository.save(financialYearMaster), FinancialYearMasterResponse.class);
            financialYearMasterResponse.setError(false);
        } else {
            financialYearMasterResponse.setError(true);
            financialYearMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return financialYearMasterResponse;
    }


    public FinancialYearMasterResponse getById(int id){
        FinancialYearMasterResponse financialYearMasterResponse = new FinancialYearMasterResponse();
        FinancialYearMaster financialYearMaster= financialYearMasterRepository.findByFinancialYearMasterIdAndActive(id, true);
        if(financialYearMaster== null){
            financialYearMasterResponse.setError(true);
            financialYearMasterResponse.setError_description("Invalid id");
        }else{
            financialYearMasterResponse =  mapper.financialYearMasterEntityToObject(financialYearMaster, FinancialYearMasterResponse.class);
            financialYearMasterResponse.setError(false);
        }
        log.info("Entity is ",financialYearMaster);
        return financialYearMasterResponse;
    }

    @Transactional
    public FinancialYearMasterResponse updateFinancialYearMastersDetails(EditFinancialYearMasterRequest financialYearMasterRequest){

        FinancialYearMasterResponse financialYearMasterResponse = new FinancialYearMasterResponse();
        List<FinancialYearMaster> financialYearMasterList = financialYearMasterRepository. findByActiveAndFinancialYearAndIsDefault(true,financialYearMasterRequest.getFinancialYear(), financialYearMasterRequest.getIsDefault());
        if(financialYearMasterList.size()>0){
            financialYearMasterResponse.setError(true);
            financialYearMasterResponse.setError_description("Program already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            FinancialYearMaster financialYearMaster = financialYearMasterRepository.findByFinancialYearMasterIdAndActiveIn(financialYearMasterRequest.getFinancialYearMasterId(), Set.of(true,false));
            if(Objects.nonNull(financialYearMaster)){
                financialYearMaster.setFinancialYear(financialYearMasterRequest.getFinancialYear());
                financialYearMaster.setIsDefault( financialYearMasterRequest.getIsDefault());
                financialYearMaster.setActive(true);
                FinancialYearMaster financialYearMaster1= financialYearMasterRepository.save(financialYearMaster);
                financialYearMasterResponse = mapper.financialYearMasterEntityToObject(financialYearMaster1, FinancialYearMasterResponse.class);
                financialYearMasterResponse.setError(false);
            } else {
                financialYearMasterResponse.setError(true);
                financialYearMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return financialYearMasterResponse;
    }

}
