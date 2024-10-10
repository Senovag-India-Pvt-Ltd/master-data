package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.cropInspectionType.CropInspectionTypeRequest;
import com.sericulture.masterdata.model.api.cropInspectionType.CropInspectionTypeResponse;
import com.sericulture.masterdata.model.api.cropInspectionType.EditCropInspectionTypeRequest;
import com.sericulture.masterdata.model.entity.CropInspectionType;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.CropInspectionTypeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

@Service
@Slf4j
public class CropInspectionTypeService {
    @Autowired
    CropInspectionTypeRepository cropInspectionTypeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public CropInspectionTypeResponse getCropInspectionTypeDetailsByName(String name){
        CropInspectionType cropInspectionType = cropInspectionTypeRepository.findByNameAndActive(name,true);
        log.info("Entity is ",cropInspectionType);
        if(cropInspectionType == null){
            CropInspectionTypeResponse cropInspectionTypeResponse = new CropInspectionTypeResponse();
            cropInspectionTypeResponse.setError(true);
            cropInspectionTypeResponse.setError_description("CropInspectionType not found");
            return cropInspectionTypeResponse;
        }else{
            return mapper.cropInspectionTypeEntityToObject(cropInspectionType,CropInspectionTypeResponse.class);
        }
    }

    @Transactional
    public CropInspectionTypeResponse insertCropInspectionTypeDetails(CropInspectionTypeRequest cropInspectionTypeRequest){
        CropInspectionTypeResponse cropInspectionTypeResponse = new CropInspectionTypeResponse();
        CropInspectionType cropInspectionType = mapper.cropInspectionTypeObjectToEntity(cropInspectionTypeRequest,CropInspectionType.class);
        validator.validate(cropInspectionType);
        List<CropInspectionType> cropInspectionTypeList = cropInspectionTypeRepository.findByName(cropInspectionTypeRequest.getName());
        if(!cropInspectionTypeList.isEmpty() && cropInspectionTypeList.stream().filter(CropInspectionType::getActive).findAny().isPresent()){
            cropInspectionTypeResponse.setError(true);
            cropInspectionTypeResponse.setError_description("CropInspectionType name already exist");
//        }
//        else if(!cropInspectionTypeList.isEmpty() && cropInspectionTypeList.stream().filter(Predicate.not(CropInspectionType::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            cropInspectionTypeResponse.setError(true);
//            cropInspectionTypeResponse.setError_description("CropInspectionType name already exist with inactive state");
        }else {
            cropInspectionTypeResponse = mapper.cropInspectionTypeEntityToObject(cropInspectionTypeRepository.save(cropInspectionType), CropInspectionTypeResponse.class);
            cropInspectionTypeResponse.setError(false);
        }
        return cropInspectionTypeResponse;
    }



    public Map<String,Object> getPaginatedCropInspectionTypeDetails(final Pageable pageable){
        return convertToMapResponse(cropInspectionTypeRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(cropInspectionTypeRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<CropInspectionType> activeCropInspectionTypes) {
        Map<String, Object> response = new HashMap<>();

        List<CropInspectionTypeResponse> cropInspectionTypeResponses = activeCropInspectionTypes.getContent().stream()
                .map(cropInspectionType -> mapper.cropInspectionTypeEntityToObject(cropInspectionType,CropInspectionTypeResponse.class)).collect(Collectors.toList());
        response.put("cropInspectionType",cropInspectionTypeResponses);
        response.put("currentPage", activeCropInspectionTypes.getNumber());
        response.put("totalItems", activeCropInspectionTypes.getTotalElements());
        response.put("totalPages", activeCropInspectionTypes.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<CropInspectionType> activeCropInspectionTypes) {
        Map<String, Object> response = new HashMap<>();

        List<CropInspectionTypeResponse> cropInspectionTypeResponses = activeCropInspectionTypes.stream()
                .map(cropInspectionType -> mapper.cropInspectionTypeEntityToObject(cropInspectionType,CropInspectionTypeResponse.class)).collect(Collectors.toList());
        response.put("cropInspectionType",cropInspectionTypeResponses);
        return response;
    }

    @Transactional
    public CropInspectionTypeResponse deleteCropInspectionTypeDetails(long id) {
        CropInspectionTypeResponse cropInspectionTypeResponse = new CropInspectionTypeResponse();
        CropInspectionType cropInspectionType = cropInspectionTypeRepository.findByCropInspectionTypeIdAndActive(id, true);
        if (Objects.nonNull(cropInspectionType)) {
            cropInspectionType.setActive(false);
            cropInspectionTypeResponse = mapper.cropInspectionTypeEntityToObject(cropInspectionTypeRepository.save(cropInspectionType), CropInspectionTypeResponse.class);
            cropInspectionTypeResponse.setError(false);
        } else {
            cropInspectionTypeResponse.setError(true);
            cropInspectionTypeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return cropInspectionTypeResponse;
    }

    public CropInspectionTypeResponse getById(int id){
        CropInspectionTypeResponse cropInspectionTypeResponse = new CropInspectionTypeResponse();
        CropInspectionType cropInspectionType = cropInspectionTypeRepository.findByCropInspectionTypeIdAndActive(id,true);
        if(cropInspectionType == null){
            cropInspectionTypeResponse.setError(true);
            cropInspectionTypeResponse.setError_description("Invalid id");
        }else{
            cropInspectionTypeResponse =  mapper.cropInspectionTypeEntityToObject(cropInspectionType,CropInspectionTypeResponse.class);
            cropInspectionTypeResponse.setError(false);
        }
        log.info("Entity is ",cropInspectionType);
        return cropInspectionTypeResponse;
    }

    @Transactional
    public CropInspectionTypeResponse updateCropInspectionTypeDetails(EditCropInspectionTypeRequest cropInspectionTypeRequest){
        CropInspectionTypeResponse cropInspectionTypeResponse = new CropInspectionTypeResponse();
        List<CropInspectionType> cropInspectionTypeList = cropInspectionTypeRepository.findByNameAndCropInspectionTypeIdIsNotAndActive(cropInspectionTypeRequest.getName(),cropInspectionTypeRequest.getCropInspectionTypeId(), true);
        if (cropInspectionTypeList.size() > 0) {
            cropInspectionTypeResponse.setError(true);
            cropInspectionTypeResponse.setError_description("CropInspectionType already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            CropInspectionType cropInspectionType = cropInspectionTypeRepository.findByCropInspectionTypeIdAndActiveIn(cropInspectionTypeRequest.getCropInspectionTypeId(), Set.of(true, false));
            if(Objects.nonNull(cropInspectionType)){
                cropInspectionType.setName(cropInspectionTypeRequest.getName());
                cropInspectionType.setActive(true);
                CropInspectionType cropInspectionType1 = cropInspectionTypeRepository.save(cropInspectionType);
                cropInspectionTypeResponse = mapper.cropInspectionTypeEntityToObject(cropInspectionType1, CropInspectionTypeResponse.class);
                cropInspectionTypeResponse.setError(false);
            } else {
                cropInspectionTypeResponse.setError(true);
                cropInspectionTypeResponse.setError_description("Error occurred while fetching cropInspectionType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return cropInspectionTypeResponse;

    }



}
