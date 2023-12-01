package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.designation.EditDesignationRequest;
import com.sericulture.masterdata.model.api.designation.DesignationRequest;
import com.sericulture.masterdata.model.api.designation.DesignationResponse;
import com.sericulture.masterdata.model.entity.Designation;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DesignationRepository;
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
public class DesignationService {

    @Autowired
    DesignationRepository designationRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public DesignationResponse insertDesignationDetails(DesignationRequest designationRequest){
        Designation designation = mapper.designationObjectToEntity(designationRequest,Designation.class);
        validator.validate(designation);
        List<Designation> designationList = designationRepository.findByName(designationRequest.getName());
        if(!designationList.isEmpty() && designationList.stream().filter(Designation::getActive).findAny().isPresent()){
            throw new ValidationException("Designation name already exist");
        }
        if(!designationList.isEmpty() && designationList.stream().filter(Predicate.not(Designation::getActive)).findAny().isPresent()){
            throw new ValidationException("Designation name already exist with inactive designation");
        }
        return mapper.designationEntityToObject(designationRepository.save(designation),DesignationResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDesignationDetails(final Pageable pageable){
        return convertToMapResponse(designationRepository.findByActiveOrderByDesignationIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(designationRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Designation> activeDesignations) {
        Map<String, Object> response = new HashMap<>();

        List<DesignationResponse> designationResponses = activeDesignations.getContent().stream()
                .map(designation -> mapper.designationEntityToObject(designation,DesignationResponse.class)).collect(Collectors.toList());
        response.put("designation",designationResponses);
        response.put("currentPage", activeDesignations.getNumber());
        response.put("totalItems", activeDesignations.getTotalElements());
        response.put("totalPages", activeDesignations.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Designation> activeDesignations) {
        Map<String, Object> response = new HashMap<>();

        List<DesignationResponse> designationResponses = activeDesignations.stream()
                .map(designation -> mapper.designationEntityToObject(designation,DesignationResponse.class)).collect(Collectors.toList());
        response.put("designation",designationResponses);
        return response;
    }

    @Transactional
    public void deleteDesignationDetails(long id) {
        Designation designation = designationRepository.findByDesignationIdAndActive(id, true);
        if (Objects.nonNull(designation)) {
            designation.setActive(false);
            designationRepository.save(designation);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public DesignationResponse getById(int id){
        Designation designation = designationRepository.findByDesignationIdAndActive(id,true);
        if(designation == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",designation);
        return mapper.designationEntityToObject(designation,DesignationResponse.class);
    }

    @Transactional
    public DesignationResponse updateDesignationDetails(EditDesignationRequest designationRequest){
        List<Designation> designationList = designationRepository.findByName(designationRequest.getName());
        if(designationList.size()>0){
            throw new ValidationException("designation already exists with this name, duplicates are not allowed.");
        }

        Designation designation = designationRepository.findByDesignationIdAndActiveIn(designationRequest.getDesignationId(), Set.of(true,false));
        if(Objects.nonNull(designation)){
            designation.setName(designationRequest.getName());
            designation.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching designation");
        }
        return mapper.designationEntityToObject(designationRepository.save(designation),DesignationResponse.class);
    }

}
