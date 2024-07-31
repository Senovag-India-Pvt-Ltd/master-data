package com.sericulture.masterdata.service;


import com.sericulture.masterdata.model.api.education.EditEducationRequest;
import com.sericulture.masterdata.model.api.education.EducationRequest;
import com.sericulture.masterdata.model.api.education.EducationResponse;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.Education;
import com.sericulture.masterdata.model.entity.Hobli;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.EducationRepository;
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
public class EducationService {

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public EducationResponse insertEducationDetails(EducationRequest request) {
        EducationResponse educationResponse = new EducationResponse();
        Education education = mapper.educationObjectToEntity(request, Education.class);
        //validating the class
        validator.validate(education);
        List<Education> educations = educationRepository.findByNameAndEducationNameInKannada(education.getName(),education.getEducationNameInKannada());
        if(!educations.isEmpty() && educations.stream().filter(Education::getActive).findAny().isPresent()) {
            educationResponse.setError(true);
            educationResponse.setError_description("Education name already exist");
//        }
//        else if(!educations.isEmpty() && educations.stream().filter(Predicate.not(Education::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            educationResponse.setError(true);
//            educationResponse.setError_description("Education name already exist with inactive state");
        }else {
            educationResponse = mapper.educationEntityToObject(educationRepository.save(education), EducationResponse.class);
            educationResponse.setError(false);
        }
        return educationResponse;

    }

    public Map<String, Object> getPaginatedEducationDetails(final Pageable pageable) {
        return convertToMapResponse(educationRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(educationRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Education> pageEducationDetails) {
        Map<String, Object> response = new HashMap<>();

        List<EducationResponse> educationList = pageEducationDetails.getContent()
                .stream()
                .map(education -> mapper.educationEntityToObject(education, EducationResponse.class))
                .collect(Collectors.toList());


        response.put("education", educationList);
        response.put("currentPage", pageEducationDetails.getNumber());
        response.put("totalItems", pageEducationDetails.getTotalElements());
        response.put("totalPages", pageEducationDetails.getTotalPages());
        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Education> pageEducationDetails) {
        Map<String, Object> response = new HashMap<>();

        List<EducationResponse> educationResponses = pageEducationDetails.stream()
                .map(education -> mapper.educationEntityToObject(education,EducationResponse.class)).collect(Collectors.toList());
        response.put("education",educationResponses);
        return response;
    }

    @Transactional
    public EducationResponse deleteEducationDetails(long id) {
        EducationResponse educationResponse = new EducationResponse();
        Education education = educationRepository.findByIdAndActive(id, true);
        if (Objects.nonNull(education)) {
            education.setActive(false);
            educationResponse = mapper.educationEntityToObject(educationRepository.save(education), EducationResponse.class);
            educationResponse.setError(false);
        } else {
            educationResponse.setError(true);
            educationResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return educationResponse;
    }

    public EducationResponse getById(long id) {
        EducationResponse educationResponse = new EducationResponse();
        Education education = educationRepository.findByIdAndActive(id, true);
        if (education == null) {
            educationResponse.setError(true);
            educationResponse.setError_description("Invalid id");
        }else{
            educationResponse =  mapper.educationEntityToObject(education,EducationResponse.class);
            educationResponse.setError(false);
        }
        log.info("Entity is ",education);
        return educationResponse;
    }

    @Transactional
    public EducationResponse updateEducationDetails(EditEducationRequest educationRequest) {
        EducationResponse educationResponse = new EducationResponse();
        List<Education> educations = educationRepository.findByNameAndEducationNameInKannadaAndActive(educationRequest.getName(),educationRequest.getEducationNameInKannada(), true);
        if(!educations.isEmpty()) {
            educationResponse.setError(true);
            educationResponse.setError_description("Education already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {

        Education education = educationRepository.findByIdAndActiveIn(educationRequest.getId(), Set.of(true,false));
        if (Objects.nonNull(education)) {
            education.setName(educationRequest.getName());
            education.setEducationNameInKannada(educationRequest.getEducationNameInKannada());
            education.setActive(true);
            Education education1 = educationRepository.save(education);
            educationResponse = mapper.educationEntityToObject(education1, EducationResponse.class);
            educationResponse.setError(false);
        } else {
            educationResponse.setError(true);
            educationResponse.setError_description("Error occurred while fetching education");
            // throw new ValidationException("Error occurred while fetching village");
        }
        }
        return educationResponse;
    }
}
