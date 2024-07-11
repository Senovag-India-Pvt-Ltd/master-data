package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.agency.EditAgencyRequest;
import com.sericulture.masterdata.model.api.agency.AgencyRequest;
import com.sericulture.masterdata.model.api.agency.AgencyResponse;
import com.sericulture.masterdata.model.entity.Agency;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.AgencyRepository;
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
public class AgencyService {

    @Autowired
    AgencyRepository agencyRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;



    @Transactional
    public AgencyResponse insertAgencyDetails(AgencyRequest agencyRequest){
        AgencyResponse agencyResponse = new AgencyResponse();
        Agency agency = mapper.agencyObjectToEntity(agencyRequest,Agency.class);
        validator.validate(agency);
        List<Agency> agencyList = agencyRepository.findByAgencyCode(agencyRequest.getAgencyCode());
        if(!agencyList.isEmpty() && agencyList.stream().filter(Agency::getActive).findAny().isPresent()){
            agencyResponse.setError(true);
            agencyResponse.setError_description("Agency Code already exist");
//        }
//        else if(!agencyList.isEmpty() && agencyList.stream().filter(Predicate.not(Agency::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            agencyResponse.setError(true);
//            agencyResponse.setError_description("Agency name already exist with inactive state");
        }else {
            agencyResponse = mapper.agencyEntityToObject(agencyRepository.save(agency), AgencyResponse.class);
            agencyResponse.setError(false);
        }
        return agencyResponse;
    }

    public Map<String,Object> getPaginatedAgencyDetails(final Pageable pageable){
        return convertToMapResponse(agencyRepository.findByActiveOrderByAgencyIfscCodeAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(agencyRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Agency> activeAgencys) {
        Map<String, Object> response = new HashMap<>();

        List<AgencyResponse> agencyResponses = activeAgencys.getContent().stream()
                .map(agency -> mapper.agencyEntityToObject(agency,AgencyResponse.class)).collect(Collectors.toList());
        response.put("agency",agencyResponses);
        response.put("currentPage", activeAgencys.getNumber());
        response.put("totalItems", activeAgencys.getTotalElements());
        response.put("totalPages", activeAgencys.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Agency> activeAgencys) {
        Map<String, Object> response = new HashMap<>();

        List<AgencyResponse> agencyResponses = activeAgencys.stream()
                .map(agency -> mapper.agencyEntityToObject(agency,AgencyResponse.class)).collect(Collectors.toList());
        response.put("agency",agencyResponses);
        return response;
    }

    @Transactional
    public AgencyResponse deleteAgencyDetails(long id) {
        AgencyResponse agencyResponse = new AgencyResponse();
        Agency agency = agencyRepository.findByAgencyIdAndActive(id, true);
        if (Objects.nonNull(agency)) {
            agency.setActive(false);
            agencyResponse = mapper.agencyEntityToObject(agencyRepository.save(agency), AgencyResponse.class);
            agencyResponse.setError(false);
        } else {
            agencyResponse.setError(true);
            agencyResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return agencyResponse;
    }

    public AgencyResponse getById(int id){
        AgencyResponse agencyResponse = new AgencyResponse();
        Agency agency = agencyRepository.findByAgencyIdAndActive(id,true);
        if(agency == null){
            agencyResponse.setError(true);
            agencyResponse.setError_description("Invalid id");
        }else{
            agencyResponse =  mapper.agencyEntityToObject(agency,AgencyResponse.class);
            agencyResponse.setError(false);
        }
        log.info("Entity is ",agency);
        return agencyResponse;
    }

    @Transactional
    public AgencyResponse updateAgencyDetails(EditAgencyRequest agencyRequest) {
        AgencyResponse agencyResponse = new AgencyResponse();
        List<Agency> agencyList = agencyRepository.findByAgencyCodeIsNot(agencyRequest.getAgencyCode());
        if (agencyList.size() > 0) {
            agencyResponse.setError(true);
            agencyResponse.setError_description("Agency already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            Agency agency = agencyRepository.findByAgencyIdAndActiveIn(agencyRequest.getAgencyId(), Set.of(true, false));
            if (Objects.nonNull(agency)) {
                agency.setAgencyCode(agencyRequest.getAgencyCode());
                agency.setAgencyBankAcNo(agencyRequest.getAgencyBankAcNo());
                agency.setAgencyDistrictCode(agencyRequest.getAgencyDistrictCode());
                agency.setAgencyIfscCode(agencyRequest.getAgencyIfscCode());
                agency.setAgencyTalukCode(agencyRequest.getAgencyTalukCode());
                agency.setActive(true);
                Agency agency1 = agencyRepository.save(agency);
                agencyResponse = mapper.agencyEntityToObject(agency1, AgencyResponse.class);
                agencyResponse.setError(false);
            } else {
                agencyResponse.setError(true);
                agencyResponse.setError_description("Error occurred while fetching Agency");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return agencyResponse;
    }
}
