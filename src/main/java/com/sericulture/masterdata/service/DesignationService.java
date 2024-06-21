package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.designation.EditDesignationRequest;
import com.sericulture.masterdata.model.api.designation.DesignationRequest;
import com.sericulture.masterdata.model.api.designation.DesignationResponse;
import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.Designation;
import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.entity.Village;
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
        DesignationResponse designationResponse = new DesignationResponse();
        Designation designation = mapper.designationObjectToEntity(designationRequest,Designation.class);
        validator.validate(designation);
        List<Designation> designationList = designationRepository.findByNameAndDesignationNameInKannada(designationRequest.getName(),designationRequest.getDesignationNameInKannada());
        if(!designationList.isEmpty() && designationList.stream().filter(Designation::getActive).findAny().isPresent()){
            designationResponse.setError(true);
            designationResponse.setError_description("Designation name already exist");
        }
        else if(!designationList.isEmpty() && designationList.stream().filter(Predicate.not(Designation::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            designationResponse.setError(true);
            designationResponse.setError_description("Designation name already exist with inactive state");
        }else {
            designationResponse = mapper.designationEntityToObject(designationRepository.save(designation), DesignationResponse.class);
            designationResponse.setError(false);
        }
        return designationResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDesignationDetails(final Pageable pageable){
        return convertToMapResponse(designationRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(designationRepository.findByActiveOrderByNameAsc(isActive));
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
    public DesignationResponse deleteDesignationDetails(long id) {
        DesignationResponse designationResponse = new DesignationResponse();
        Designation designation = designationRepository.findByDesignationIdAndActive(id, true);
        if (Objects.nonNull(designation)) {
            designation.setActive(false);
            designationResponse = mapper.designationEntityToObject(designationRepository.save(designation), DesignationResponse.class);
            designationResponse.setError(false);
        } else {
            designationResponse.setError(true);
            designationResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return designationResponse;
    }

    @Transactional
    public DesignationResponse getById(int id){
        DesignationResponse designationResponse = new DesignationResponse();
        Designation designation = designationRepository.findByDesignationIdAndActive(id,true);
        if(designation == null){
            designationResponse.setError(true);
            designationResponse.setError_description("Invalid id");
        }else{
            designationResponse =  mapper.designationEntityToObject(designation,DesignationResponse.class);
            designationResponse.setError(false);
        }
        log.info("Entity is ",designation);
        return designationResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getDesignationByScApprovalStageId(Long scApprovalStageId){
//        List<Designation> designationList = designationRepository.findByScApprovalStageIdAndActiveOrderByName(scApprovalStageId,true);
//        if(designationList.isEmpty()){
//            throw new ValidationException("Invalid Id");
//        }
//        log.info("Entity is ",designationList);
//        return convertListToMapResponse(designationList);
//    }
//
//    private Map<String, Object> convertListToMapResponse(List<Designation> designationList) {
//        Map<String, Object> response = new HashMap<>();
//        List<DesignationResponse> designationResponses = designationList.stream()
//                .map(designation -> mapper.designationEntityToObject(designation,DesignationResponse.class)).collect(Collectors.toList());
//        response.put("designation",designationResponses);
//        response.put("totalItems", designationList.size());
//        return response;
//    }

    @Transactional
    public DesignationResponse updateDesignationDetails(EditDesignationRequest designationRequest) {
        DesignationResponse designationResponse = new DesignationResponse();
        List<Designation> designationList = designationRepository.findByNameAndDesignationNameInKannadaAndDesignationIdIsNot(designationRequest.getName(),designationRequest.getDesignationNameInKannada(), designationRequest.getDesignationId());
        if (designationList.size() > 0) {
            designationResponse.setError(true);
            designationResponse.setError_description("Designation already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            Designation designation = designationRepository.findByDesignationIdAndActiveIn(designationRequest.getDesignationId(), Set.of(true, false));
            if (Objects.nonNull(designation)) {
                designation.setName(designationRequest.getName());
                designation.setDesignationNameInKannada(designationRequest.getDesignationNameInKannada());
                designation.setLevel(designationRequest.getLevel());
                designation.setActive(true);
                Designation designation1 = designationRepository.save(designation);
                designationResponse = mapper.designationEntityToObject(designation1, DesignationResponse.class);
                designationResponse.setError(false);
            } else {
                designationResponse.setError(true);
                designationResponse.setError_description("Error occurred while fetching Designation");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return designationResponse;
    }
}
