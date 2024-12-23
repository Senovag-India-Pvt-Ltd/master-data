package com.sericulture.masterdata.service;

import com.sericulture.masterdata.helper.Util;
import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.configurePmkysAmount.ConfigurePmkysAmountRequest;
import com.sericulture.masterdata.model.api.configurePmkysAmount.ConfigurePmkysAmountResponse;
import com.sericulture.masterdata.model.api.configurePmkysAmount.EditConfigurePmkysAmountRequest;
import com.sericulture.masterdata.model.api.configurePmkysAmount.ConfigurePmkysAmountRequest;
import com.sericulture.masterdata.model.api.configurePmkysAmount.ConfigurePmkysAmountResponse;
import com.sericulture.masterdata.model.api.userHierarchyMapping.UserHierarchyMappingResponse;
import com.sericulture.masterdata.model.entity.ConfigurePmkysAmount;
import com.sericulture.masterdata.model.entity.ConfigurePmkysAmount;
import com.sericulture.masterdata.model.entity.UserHierarchyMapping;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ConfigurePmkysAmountRepository;
import com.sericulture.masterdata.repository.ConfigurePmkysAmountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConfigurePmkysAmountService {

    @Autowired
    ConfigurePmkysAmountRepository configurePmkysAmountRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ConfigurePmkysAmountResponse insertConfigurePmkysAmountDetails(ConfigurePmkysAmountRequest configurePmkysAmountRequest){
        ConfigurePmkysAmountResponse configurePmkysAmountResponse = new ConfigurePmkysAmountResponse();
        ConfigurePmkysAmount configurePmkysAmount = mapper.configurePmkysAmountObjectToEntity(configurePmkysAmountRequest,ConfigurePmkysAmount.class);
        validator.validate(configurePmkysAmount);
//        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist");
//        }
//        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
//            throw new ValidationException("RpPageRoot name already exist with inactive state");
//        }

        return mapper.configurePmkysAmountEntityToObject(configurePmkysAmountRepository.save(configurePmkysAmount), ConfigurePmkysAmountResponse.class);
    }

    @Transactional
    public ConfigurePmkysAmountResponse deleteConfigurePmkysAmountDetails(long id) {
        ConfigurePmkysAmountResponse configurePmkysAmountResponse = new ConfigurePmkysAmountResponse();
        ConfigurePmkysAmount configurePmkysAmount = configurePmkysAmountRepository.findByConfigurePmkysAmountIdAndActive(id, true);
        if (Objects.nonNull(configurePmkysAmount)) {
            configurePmkysAmount.setActive(false);
            configurePmkysAmountResponse = mapper.configurePmkysAmountEntityToObject(configurePmkysAmountRepository.save(configurePmkysAmount), ConfigurePmkysAmountResponse.class);
            configurePmkysAmountResponse.setError(false);
        } else {
            configurePmkysAmountResponse.setError(true);
            configurePmkysAmountResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return configurePmkysAmountResponse;
    }

    public ConfigurePmkysAmountResponse getById(int id){
        ConfigurePmkysAmountResponse configurePmkysAmountResponse = new ConfigurePmkysAmountResponse();
        ConfigurePmkysAmount configurePmkysAmount = configurePmkysAmountRepository.findByConfigurePmkysAmountIdAndActive(id,true);
        if(configurePmkysAmount == null){
            configurePmkysAmountResponse.setError(true);
            configurePmkysAmountResponse.setError_description("Invalid id");
        }else{
            configurePmkysAmountResponse =  mapper.configurePmkysAmountEntityToObject(configurePmkysAmount,ConfigurePmkysAmountResponse.class);
            configurePmkysAmountResponse.setError(false);
        }
        log.info("Entity is ",configurePmkysAmount);
        return configurePmkysAmountResponse;
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(configurePmkysAmountRepository.findByActive(isActive));
    }


    private Map<String, Object> convertListEntityToMapResponse(final List<ConfigurePmkysAmount> activeConfigurePmkysAmounts) {
        Map<String, Object> response = new HashMap<>();

        List<ConfigurePmkysAmountResponse> configurePmkysAmountResponses = activeConfigurePmkysAmounts.stream()
                .map(configurePmkysAmount -> mapper.configurePmkysAmountEntityToObject(configurePmkysAmount,ConfigurePmkysAmountResponse.class)).collect(Collectors.toList());
        response.put("configurePmkysAmount",configurePmkysAmountResponses);
        return response;
    }

    @Transactional
    public ConfigurePmkysAmountResponse updateConfigurePmkysAmountDetails(EditConfigurePmkysAmountRequest configurePmkysAmountRequest){
        ConfigurePmkysAmountResponse configurePmkysAmountResponse = new ConfigurePmkysAmountResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

        ConfigurePmkysAmount configurePmkysAmount = configurePmkysAmountRepository.findByConfigurePmkysAmountIdAndActiveIn(configurePmkysAmountRequest.getConfigurePmkysAmountId(), Set.of(true,false));
        if(Objects.nonNull(configurePmkysAmount)){
            configurePmkysAmount.setSpacingId(configurePmkysAmountRequest.getSpacingId());
            configurePmkysAmount.setHectareId(configurePmkysAmountRequest.getHectareId());
            configurePmkysAmount.setAmount(configurePmkysAmountRequest.getAmount());
            configurePmkysAmount.setActive(true);
            ConfigurePmkysAmount configurePmkysAmount1 = configurePmkysAmountRepository.save(configurePmkysAmount);
            configurePmkysAmountResponse = mapper.configurePmkysAmountEntityToObject(configurePmkysAmount1, ConfigurePmkysAmountResponse.class);
            configurePmkysAmountResponse.setError(false);
        } else {
            configurePmkysAmountResponse.setError(true);
            configurePmkysAmountResponse.setError_description("Error occurred while fetching Configure Pmkys Amount");
            // throw new ValidationException("Error occurred while fetching village");
        }

        return configurePmkysAmountResponse;
    }

    public List<ConfigurePmkysAmountResponse> getAmountBySpacingAndHectare(Long spacingId, Long hectareId) {
        List<Object[]> configurePmkysAmountDetails = configurePmkysAmountRepository.getAmountBySpacingAndHectare(spacingId,hectareId);
        List<ConfigurePmkysAmountResponse> responses = new ArrayList<>();

        for (Object[] arr : configurePmkysAmountDetails) {
            ConfigurePmkysAmountResponse response = ConfigurePmkysAmountResponse.builder()
                    .amount(Util.objectToFloat(arr[0]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ConfigurePmkysAmountResponse> getClosestRecordsSpacingAndHectare(Long spacingId, Long hectareId) {
        List<Object[]> configurePmkysAmountDetails = configurePmkysAmountRepository.getClosestRecordsSpacingAndHectare(spacingId,hectareId);
        List<ConfigurePmkysAmountResponse> responses = new ArrayList<>();

        for (Object[] arr : configurePmkysAmountDetails) {
            ConfigurePmkysAmountResponse response = ConfigurePmkysAmountResponse.builder()
                    .lowestAmount(Util.objectToFloat(arr[0]))
                    .highestAmount(Util.objectToFloat(arr[1]))
                    .lowestArea(Util.objectToFloat(arr[2]))
                    .highestArea(Util.objectToFloat(arr[3]))
                    .targetArea(Util.objectToFloat(arr[4]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

    public List<ConfigurePmkysAmountResponse> getListOfConfigurePmkysAmount(Long configurePmkysAmountId) {
        List<Object[]> configurePmkysAmountDetails = configurePmkysAmountRepository.getListOfConfigurePmkysAmount(configurePmkysAmountId);
        List<ConfigurePmkysAmountResponse> responses = new ArrayList<>();

        for (Object[] arr : configurePmkysAmountDetails) {
            ConfigurePmkysAmountResponse response = ConfigurePmkysAmountResponse.builder()
                    .configurePmkysAmountId(Util.objectToLong(arr[0]))
                    .spacingId(Util.objectToLong(arr[1]))
                    .hectareId(Util.objectToLong(arr[2]))
                    .amount(Util.objectToFloat(arr[3]))
                    .spacingName(Util.objectToString(arr[4]))
                    .hectareName(Util.objectToString(arr[5]))
                    .build();

            responses.add(response);
        }

        return responses;
    }

//    public List<ConfigurePmkysAmountResponse> getListOfConfigurePmkysAmountDetails() {
//        List<Object[]> configurePmkysAmountDetails = configurePmkysAmountRepository.getListOfConfigurePmkysAmountDetails();
//        List<ConfigurePmkysAmountResponse> responses = new ArrayList<>();
//
//        for (Object[] arr : configurePmkysAmountDetails) {
//            ConfigurePmkysAmountResponse response = ConfigurePmkysAmountResponse.builder()
//                    .configurePmkysAmountId(Util.objectToLong(arr[0]))
//                    .spacingId(Util.objectToLong(arr[1]))
//                    .hectareId(Util.objectToLong(arr[2]))
//                    .amount(Util.objectToLong(arr[3]))
//                    .spacingName(Util.objectToString(arr[4]))
//                    .hectareName(Util.objectToString(arr[5]))
//                    .build();
//
//            responses.add(response);
//        }
//
//        return responses;
//    }
public ResponseEntity<?> getListOfConfigurePmkysAmountDetails(int pageNumber, int pageSize) {
    ResponseWrapper rw = ResponseWrapper.createWrapper(List.class);

    // Prepare pageable object
    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    // Fetch data with pagination
    Page<Object[]> configurePmkysAmountDetailsPage = configurePmkysAmountRepository.getListOfConfigurePmkysAmountDetails(pageable);
    List<Object[]> configurePmkysAmountDetails = configurePmkysAmountDetailsPage.getContent();
    long totalRecords = configurePmkysAmountDetailsPage.getTotalElements();

    // Map data to response objects
    List<ConfigurePmkysAmountResponse> responses = configurePmkysAmountDetails.stream().map(arr -> ConfigurePmkysAmountResponse.builder()
            .configurePmkysAmountId(Util.objectToLong(arr[0]))
            .spacingId(Util.objectToLong(arr[1]))
            .hectareId(Util.objectToLong(arr[2]))
            .amount(Util.objectToFloat(arr[3]))
            .spacingName(Util.objectToString(arr[4]))
            .hectareName(Util.objectToString(arr[5]))
            .build()
    ).toList();

    // Set results in the ResponseWrapper
    rw.setTotalRecords(totalRecords);
    rw.setContent(responses);

    return ResponseEntity.ok(rw);
}


}
