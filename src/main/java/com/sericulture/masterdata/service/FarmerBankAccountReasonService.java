package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.farmerBankAccountReason.EditFarmerBankAccountReasonRequest;
import com.sericulture.masterdata.model.api.farmerBankAccountReason.FarmerBankAccountReasonRequest;
import com.sericulture.masterdata.model.api.farmerBankAccountReason.FarmerBankAccountReasonResponse;
import com.sericulture.masterdata.model.entity.FarmerBankAccountReason;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.FarmerBankAccountReasonRepository;
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
public class FarmerBankAccountReasonService {
    @Autowired
    FarmerBankAccountReasonRepository farmerBankAccountReasonRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public FarmerBankAccountReasonResponse getFarmerBankAccountReasonDetails(String farmerBankAccountReason){
        FarmerBankAccountReasonResponse farmerBankAccountReasonResponse = new FarmerBankAccountReasonResponse();
        FarmerBankAccountReason farmerBankAccountReason1= farmerBankAccountReasonRepository.findByFarmerBankAccountReasonAndActive(farmerBankAccountReason,true);
        if(farmerBankAccountReason==null){
            farmerBankAccountReasonResponse.setError(true);
            farmerBankAccountReasonResponse.setError_description("Program not found");
        }else{
            farmerBankAccountReasonResponse = mapper.farmerBankAccountReasonEntityToObject(farmerBankAccountReason1,FarmerBankAccountReasonResponse.class);
            farmerBankAccountReasonResponse.setError(false);
        }
        log.info("Entity is ",farmerBankAccountReason);
        return farmerBankAccountReasonResponse;
    }

    @Transactional
//    public FarmerBankAccountReasonResponse insertFarmerBankAccountReasonDetails(FarmerBankAccountReasonRequest farmerBankAccountReasonRequest){
//        FarmerBankAccountReasonResponse farmerBankAccountReasonResponse = new FarmerBankAccountReasonResponse();
//        FarmerBankAccountReason farmerBankAccountReason = mapper.farmerBankAccountReasonObjectToEntity(farmerBankAccountReasonRequest,FarmerBankAccountReason.class);
//        validator.validate(farmerBankAccountReason);
//        List<FarmerBankAccountReason> farmerBankAccountReasonList = farmerBankAccountReasonRepository.findByFarmerBankAccountReason(farmerBankAccountReasonRequest.getFarmerBankAccountReason());
//        if(!farmerBankAccountReasonList.isEmpty() && farmerBankAccountReasonList.stream().filter( FarmerBankAccountReason::getActive).findAny().isPresent()){
//            farmerBankAccountReasonResponse.setError(true);
//            farmerBankAccountReasonResponse.setError_description("Financial Year name already exist");
////        }
////        else if(! farmerBankAccountReasonList.isEmpty() && farmerBankAccountReasonList.stream().filter(Predicate.not( FarmerBankAccountReason::getActive)).findAny().isPresent()){
////            FarmerBankAccountReasonResponse.setError(true);
////            FarmerBankAccountReasonResponse.setError_description("Tr Program name already exist with inactive state");
//        }else {
//            if(farmerBankAccountReasonRequest.getIsDefault()){
//                List<FarmerBankAccountReason> farmerBankAccountReasons = farmerBankAccountReasonRepository.findByActive(true);
//                for(FarmerBankAccountReason farmerBankAccountReason1: farmerBankAccountReasons){
//                    farmerBankAccountReason1.setIsDefault(false);
//                    farmerBankAccountReasonRepository.save(farmerBankAccountReason1);
//                }
//            }
//            farmerBankAccountReasonResponse  = mapper.farmerBankAccountReasonEntityToObject( farmerBankAccountReasonRepository.save(farmerBankAccountReason), FarmerBankAccountReasonResponse.class);
//            farmerBankAccountReasonResponse.setError(false);
//        }
//        return farmerBankAccountReasonResponse;
//    }

    public FarmerBankAccountReasonResponse insertFarmerBankAccountReasonDetails(FarmerBankAccountReasonRequest farmerBankAccountReasonRequest) {
        FarmerBankAccountReasonResponse farmerBankAccountReasonResponse = new FarmerBankAccountReasonResponse();

        // Convert request object to entity
        FarmerBankAccountReason farmerBankAccountReason =
                mapper.farmerBankAccountReasonObjectToEntity(farmerBankAccountReasonRequest, FarmerBankAccountReason.class);

        // Validate the entity
        validator.validate(farmerBankAccountReason);

        // Check for existing reasons
        List<FarmerBankAccountReason> farmerBankAccountReasonList =
                farmerBankAccountReasonRepository.findByFarmerBankAccountReason(farmerBankAccountReasonRequest.getFarmerBankAccountReason());

        if (!farmerBankAccountReasonList.isEmpty() &&
                farmerBankAccountReasonList.stream().anyMatch(FarmerBankAccountReason::getActive)) {

            // Error response for duplicate active reason
            farmerBankAccountReasonResponse.setError(true);
            farmerBankAccountReasonResponse.setError_description("Financial Year name already exists");

        } else {
            // Save the new reason to the database
            farmerBankAccountReasonResponse =
                    mapper.farmerBankAccountReasonEntityToObject(
                            farmerBankAccountReasonRepository.save(farmerBankAccountReason),
                            FarmerBankAccountReasonResponse.class
                    );

            farmerBankAccountReasonResponse.setError(false);
        }

        return farmerBankAccountReasonResponse;
    }

    public Map<String,Object> getPaginatedFarmerBankAccountReasonDetails(final Pageable pageable){
        return convertToMapResponse(farmerBankAccountReasonRepository.findByActiveOrderByFarmerBankAccountReasonAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(farmerBankAccountReasonRepository.findByActive(isActive));
    }



    private Map<String, Object> convertToMapResponse(final Page<FarmerBankAccountReason> activeFarmerBankAccountReasons) {
        Map<String, Object> response = new HashMap<>();

        List<FarmerBankAccountReasonResponse> farmerBankAccountReasonResponses= activeFarmerBankAccountReasons.getContent().stream()
                .map(farmerBankAccountReason -> mapper.farmerBankAccountReasonEntityToObject(farmerBankAccountReason,FarmerBankAccountReasonResponse.class)).collect(Collectors.toList());
        response.put("farmerBankAccountReason",farmerBankAccountReasonResponses);
        response.put("currentPage", activeFarmerBankAccountReasons.getNumber());
        response.put("totalItems", activeFarmerBankAccountReasons.getTotalElements());
        response.put("totalPages", activeFarmerBankAccountReasons.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<FarmerBankAccountReason> activeFarmerBankAccountReasons) {
        Map<String, Object> response = new HashMap<>();

        List<FarmerBankAccountReasonResponse> farmerBankAccountReasonResponses= activeFarmerBankAccountReasons.stream()
                .map(farmerBankAccountReason -> mapper.farmerBankAccountReasonEntityToObject(farmerBankAccountReason,FarmerBankAccountReasonResponse.class)).collect(Collectors.toList());
        response.put("farmerBankAccountReason",farmerBankAccountReasonResponses);
        return response;
    }

    @Transactional
    public FarmerBankAccountReasonResponse deleteFarmerBankAccountReasonDetails(long id) {

        FarmerBankAccountReasonResponse farmerBankAccountReasonResponse = new FarmerBankAccountReasonResponse();
        FarmerBankAccountReason farmerBankAccountReason= farmerBankAccountReasonRepository.findByFarmerBankAccountReasonIdAndActive(id, true);
        if (Objects.nonNull(farmerBankAccountReason)) {
            farmerBankAccountReason.setActive(false);
            farmerBankAccountReasonResponse= mapper.farmerBankAccountReasonEntityToObject(farmerBankAccountReasonRepository.save(farmerBankAccountReason), FarmerBankAccountReasonResponse.class);
            farmerBankAccountReasonResponse.setError(false);
        } else {
            farmerBankAccountReasonResponse.setError(true);
            farmerBankAccountReasonResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return farmerBankAccountReasonResponse;
    }


    public FarmerBankAccountReasonResponse getById(int id){
        FarmerBankAccountReasonResponse farmerBankAccountReasonResponse = new FarmerBankAccountReasonResponse();
        FarmerBankAccountReason farmerBankAccountReason= farmerBankAccountReasonRepository.findByFarmerBankAccountReasonIdAndActive(id, true);
        if(farmerBankAccountReason== null){
            farmerBankAccountReasonResponse.setError(true);
            farmerBankAccountReasonResponse.setError_description("Invalid id");
        }else{
            farmerBankAccountReasonResponse =  mapper.farmerBankAccountReasonEntityToObject(farmerBankAccountReason, FarmerBankAccountReasonResponse.class);
            farmerBankAccountReasonResponse.setError(false);
        }
        log.info("Entity is ",farmerBankAccountReason);
        return farmerBankAccountReasonResponse;
    }

    @Transactional
    public FarmerBankAccountReasonResponse updateFarmerBankAccountReasonsDetails(EditFarmerBankAccountReasonRequest farmerBankAccountReasonRequest){

        FarmerBankAccountReasonResponse farmerBankAccountReasonResponse = new FarmerBankAccountReasonResponse();
        List<FarmerBankAccountReason> farmerBankAccountReasonList = farmerBankAccountReasonRepository. findByActiveAndFarmerBankAccountReason(true,farmerBankAccountReasonRequest.getFarmerBankAccountReason());
        if(farmerBankAccountReasonList.size()>0){
            farmerBankAccountReasonResponse.setError(true);
            farmerBankAccountReasonResponse.setError_description("Program already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            FarmerBankAccountReason farmerBankAccountReason = farmerBankAccountReasonRepository.findByFarmerBankAccountReasonIdAndActiveIn(farmerBankAccountReasonRequest.getFarmerBankAccountReasonId(), Set.of(true,false));
            if(Objects.nonNull(farmerBankAccountReason)){
                farmerBankAccountReason.setFarmerBankAccountReason(farmerBankAccountReasonRequest.getFarmerBankAccountReason());
                farmerBankAccountReason.setActive(true);
                FarmerBankAccountReason farmerBankAccountReason1= farmerBankAccountReasonRepository.save(farmerBankAccountReason);
                farmerBankAccountReasonResponse = mapper.farmerBankAccountReasonEntityToObject(farmerBankAccountReason1, FarmerBankAccountReasonResponse.class);
                farmerBankAccountReasonResponse.setError(false);
            } else {
                farmerBankAccountReasonResponse.setError(true);
                farmerBankAccountReasonResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return farmerBankAccountReasonResponse;
    }
}
