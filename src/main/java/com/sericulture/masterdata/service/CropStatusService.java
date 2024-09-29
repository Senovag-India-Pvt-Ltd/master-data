package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.cropStatus.CropStatusRequest;
import com.sericulture.masterdata.model.api.cropStatus.CropStatusResponse;
import com.sericulture.masterdata.model.api.cropStatus.EditCropStatusRequest;
import com.sericulture.masterdata.model.entity.CropStatus;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.CropStatusRepository;
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
public class CropStatusService {
    @Autowired
    CropStatusRepository cropStatusRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public CropStatusResponse getCropStatusDetailsByName(String name){
        CropStatus cropStatus = cropStatusRepository.findByNameAndActive(name,true);
        log.info("Entity is ",cropStatus);
        if(cropStatus == null){
            CropStatusResponse cropStatusResponse = new CropStatusResponse();
            cropStatusResponse.setError(true);
            cropStatusResponse.setError_description("CropStatus not found");
            return cropStatusResponse;
        }else{
            return mapper.cropStatusEntityToObject(cropStatus,CropStatusResponse.class);
        }
    }

    @Transactional
    public CropStatusResponse insertCropStatusDetails(CropStatusRequest cropStatusRequest){
        CropStatusResponse cropStatusResponse = new CropStatusResponse();
        CropStatus cropStatus = mapper.cropStatusObjectToEntity(cropStatusRequest,CropStatus.class);
        validator.validate(cropStatus);
        List<CropStatus> cropStatusList = cropStatusRepository.findByName(cropStatusRequest.getName());
        if(!cropStatusList.isEmpty() && cropStatusList.stream().filter(CropStatus::getActive).findAny().isPresent()){
            cropStatusResponse.setError(true);
            cropStatusResponse.setError_description("CropStatus name already exist");
//        }
//        else if(!cropStatusList.isEmpty() && cropStatusList.stream().filter(Predicate.not(CropStatus::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            cropStatusResponse.setError(true);
//            cropStatusResponse.setError_description("CropStatus name already exist with inactive state");
        }else {
            cropStatusResponse = mapper.cropStatusEntityToObject(cropStatusRepository.save(cropStatus), CropStatusResponse.class);
            cropStatusResponse.setError(false);
        }
        return cropStatusResponse;
    }



    public Map<String,Object> getPaginatedCropStatusDetails(final Pageable pageable){
        return convertToMapResponse(cropStatusRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(cropStatusRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<CropStatus> activeCropStatuss) {
        Map<String, Object> response = new HashMap<>();

        List<CropStatusResponse> cropStatusResponses = activeCropStatuss.getContent().stream()
                .map(cropStatus -> mapper.cropStatusEntityToObject(cropStatus,CropStatusResponse.class)).collect(Collectors.toList());
        response.put("cropStatus",cropStatusResponses);
        response.put("currentPage", activeCropStatuss.getNumber());
        response.put("totalItems", activeCropStatuss.getTotalElements());
        response.put("totalPages", activeCropStatuss.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<CropStatus> activeCropStatuss) {
        Map<String, Object> response = new HashMap<>();

        List<CropStatusResponse> cropStatusResponses = activeCropStatuss.stream()
                .map(cropStatus -> mapper.cropStatusEntityToObject(cropStatus,CropStatusResponse.class)).collect(Collectors.toList());
        response.put("cropStatus",cropStatusResponses);
        return response;
    }

    @Transactional
    public CropStatusResponse deleteCropStatusDetails(long id) {
        CropStatusResponse cropStatusResponse = new CropStatusResponse();
        CropStatus cropStatus = cropStatusRepository.findByCropStatusIdAndActive(id, true);
        if (Objects.nonNull(cropStatus)) {
            cropStatus.setActive(false);
            cropStatusResponse = mapper.cropStatusEntityToObject(cropStatusRepository.save(cropStatus), CropStatusResponse.class);
            cropStatusResponse.setError(false);
        } else {
            cropStatusResponse.setError(true);
            cropStatusResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return cropStatusResponse;
    }

    public CropStatusResponse getById(int id){
        CropStatusResponse cropStatusResponse = new CropStatusResponse();
        CropStatus cropStatus = cropStatusRepository.findByCropStatusIdAndActive(id,true);
        if(cropStatus == null){
            cropStatusResponse.setError(true);
            cropStatusResponse.setError_description("Invalid id");
        }else{
            cropStatusResponse =  mapper.cropStatusEntityToObject(cropStatus,CropStatusResponse.class);
            cropStatusResponse.setError(false);
        }
        log.info("Entity is ",cropStatus);
        return cropStatusResponse;
    }

    @Transactional
    public CropStatusResponse updateCropStatusDetails(EditCropStatusRequest cropStatusRequest){
        CropStatusResponse cropStatusResponse = new CropStatusResponse();
        List<CropStatus> cropStatusList = cropStatusRepository.findByNameAndCropStatusIdIsNotAndActive(cropStatusRequest.getName(),cropStatusRequest.getCropStatusId(), true);
        if (cropStatusList.size() > 0) {
            cropStatusResponse.setError(true);
            cropStatusResponse.setError_description("CropStatus already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            CropStatus cropStatus = cropStatusRepository.findByCropStatusIdAndActiveIn(cropStatusRequest.getCropStatusId(), Set.of(true, false));
            if(Objects.nonNull(cropStatus)){
                cropStatus.setName(cropStatusRequest.getName());
                cropStatus.setActive(true);
                CropStatus cropStatus1 = cropStatusRepository.save(cropStatus);
                cropStatusResponse = mapper.cropStatusEntityToObject(cropStatus1, CropStatusResponse.class);
                cropStatusResponse.setError(false);
            } else {
                cropStatusResponse.setError(true);
                cropStatusResponse.setError_description("Error occurred while fetching cropStatus");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return cropStatusResponse;

    }

}
