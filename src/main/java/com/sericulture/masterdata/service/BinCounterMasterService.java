package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterRequest;
import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterResponse;
import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterWithBinMasterRequest;
import com.sericulture.masterdata.model.api.binCounterMaster.EditBinCounterMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.BinCounterMaster;
import com.sericulture.masterdata.model.entity.BinMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.BinCounterMasterRepository;
import com.sericulture.masterdata.repository.BinMasterRepository;
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
public class BinCounterMasterService {
    @Autowired
    BinCounterMasterRepository binCounterMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Autowired
    BinMasterRepository binMasterRepository;

    @Transactional
    public BinCounterMasterResponse insertBinCounterMasterDetails(BinCounterMasterRequest binCounterMasterRequest){
        BinCounterMasterResponse binCounterMasterResponse = new BinCounterMasterResponse();
        BinCounterMaster binCounterMaster = mapper.binCounterMasterObjectToEntity(binCounterMasterRequest,BinCounterMaster.class);
        validator.validate(binCounterMaster);
//        List<BinCounterMaster> binCounterMasterList = binCounterMasterRepository.findByBinCounterMasterName(binCounterMasterRequest.getB());
//        if(!binCounterMasterList.isEmpty() && binCounterMasterList.stream().filter(BinCounterMaster::getActive).findAny().isPresent()){
//            throw new ValidationException("BinCounterMaster name already exist");
//        }
//        if(!binCounterMasterList.isEmpty() && binCounterMasterList.stream().filter(Predicate.not(BinCounterMaster::getActive)).findAny().isPresent()){
//            throw new ValidationException("BinCounterMaster name already exist with inactive state");
//        }
        return binCounterMasterResponse;
    }

    @Transactional
    public BinCounterMasterResponse save(BinCounterMasterWithBinMasterRequest binCounterMasterWithBinMasterRequest){
        BinCounterMaster binCounterMaster = mapper.binCounterMasterObjectToEntity(binCounterMasterWithBinMasterRequest.getBinCounterMasterRequest(),BinCounterMaster.class);
        validator.validate(binCounterMaster);
        BinCounterMaster binCounterMaster1 = binCounterMasterRepository.save(binCounterMaster);

        //To save small bins
        for(BinMasterRequest smallBinMasterRequest: binCounterMasterWithBinMasterRequest.getSmallBinMasterRequestList()) {
            smallBinMasterRequest.setBinCounterMasterId(binCounterMaster1.getBinCounterMasterId());
            BinMaster binMaster = mapper.binMasterObjectToEntity(smallBinMasterRequest,BinMaster.class);
            binMasterRepository.save(binMaster);
        }

        //To save big bins
        for(BinMasterRequest bigBinMasterRequest: binCounterMasterWithBinMasterRequest.getBigBinMasterRequestList()) {
            bigBinMasterRequest.setBinCounterMasterId(binCounterMaster1.getBinCounterMasterId());
            BinMaster binMaster = mapper.binMasterObjectToEntity(bigBinMasterRequest,BinMaster.class);
            binMasterRepository.save(binMaster);
        }
        return mapper.binCounterMasterEntityToObject(binCounterMaster1, BinCounterMasterResponse.class);
    }

    @Transactional
    public BinCounterMasterResponse saveBinCounterMasterDetails(BinCounterMasterRequest binCounterMasterRequest) {
    BinCounterMaster binCounterMaster = new BinCounterMaster();
    // Set values from binCounterMasterRequest to binCounterMaster
    binCounterMaster.setMarketId(binCounterMasterRequest.getMarketId());
    binCounterMaster.setGodownId(binCounterMasterRequest.getGodownId());
    binCounterMaster.setSmallBinStart(binCounterMasterRequest.getSmallBinStart());
    binCounterMaster.setSmallBinEnd(binCounterMasterRequest.getSmallBinEnd());
    binCounterMaster.setBigBinStart(binCounterMasterRequest.getBigBinStart());
    binCounterMaster.setBigBinEnd(binCounterMasterRequest.getBigBinEnd());

    // Save binCounterMaster and return the generated ID
    binCounterMaster = binCounterMasterRepository.save(binCounterMaster);

    // Create and return the response object with the generated ID
    //return new BinCounterMasterResponse(binCounterMaster.getBinCounterMasterId());
        return mapper.binCounterMasterEntityToObject(binCounterMasterRepository.save(binCounterMaster),BinCounterMasterResponse.class);
}

    @Transactional
    public void saveBinMasterDetails(Long binCounterMasterId, List<Map<String, Object>> binDetails) {
    for (Map<String, Object> binDetail : binDetails) {
        BinMasterRequest binMasterRequest = new BinMasterRequest();
        binMasterRequest.setBinCounterMasterId(binCounterMasterId);
        binMasterRequest.setType(((Number) binDetail.get("type")).longValue());
        binMasterRequest.setBinNumber((String) binDetail.get("binNumber"));
        if (binDetail.containsKey("status")) {
            binMasterRequest.setStatus(((Number) binDetail.get("status")).longValue());
        }
        // Save the binMaster details using a repository or data access layer
        // binMasterRepository.save(binMasterRequest);
    }
}

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedBinCounterMasterDetails(final Pageable pageable){
        return convertToMapResponse(binCounterMasterRepository.findByActiveOrderByBinCounterMasterIdAsc( true, pageable));
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(binCounterMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<BinCounterMaster> activeBinCounterMasters) {
        Map<String, Object> response = new HashMap<>();

        List<BinCounterMasterResponse> binCounterMasterResponses = activeBinCounterMasters.getContent().stream()
                .map(binCounterMaster -> mapper.binCounterMasterEntityToObject(binCounterMaster, BinCounterMasterResponse.class)).collect(Collectors.toList());
        response.put("binCounterMaster",binCounterMasterResponses);
        response.put("currentPage", activeBinCounterMasters.getNumber());
        response.put("totalItems", activeBinCounterMasters.getTotalElements());
        response.put("totalPages", activeBinCounterMasters.getTotalPages());

        return response;
    }
    private Map<String, Object> convertListEntityToMapResponse(final List<BinCounterMaster> activeBinCounterMasters) {
        Map<String, Object> response = new HashMap<>();

        List<BinCounterMasterResponse> binCounterMasterResponses = activeBinCounterMasters.stream()
                .map(binCounterMaster -> mapper.binCounterMasterEntityToObject(binCounterMaster,BinCounterMasterResponse.class)).collect(Collectors.toList());
        response.put("binCounterMaster",binCounterMasterResponses);
        return response;
    }

    @Transactional
    public BinCounterMasterResponse deleteBinCounterMasterDetails(long id) {
        BinCounterMasterResponse binCounterMasterResponse = new BinCounterMasterResponse();
        BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActive(id, true);
        if (Objects.nonNull(binCounterMaster)) {
            binCounterMaster.setActive(false);
            binCounterMasterResponse = mapper.binCounterMasterEntityToObject(binCounterMasterRepository.save(binCounterMaster), BinCounterMasterResponse.class);
            binCounterMasterResponse.setError(false);
        } else {
            binCounterMasterResponse.setError(true);
            binCounterMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return binCounterMasterResponse;
    }

    @Transactional
    public BinCounterMasterResponse getById(int id){
        BinCounterMasterResponse binCounterMasterResponse = new BinCounterMasterResponse();
        BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActive(id,true);
        if(binCounterMaster == null){
            binCounterMasterResponse.setError(true);
            binCounterMasterResponse.setError_description("Invalid id");
        }else{
            binCounterMasterResponse =  mapper.binCounterMasterEntityToObject(binCounterMaster,BinCounterMasterResponse.class);
            binCounterMasterResponse.setError(false);
        }
        log.info("Entity is ",binCounterMaster);
        return binCounterMasterResponse;
    }

    @Transactional
    public BinCounterMasterResponse updateBinCounterMasterDetails(EditBinCounterMasterRequest binCounterMasterRequest){
        BinCounterMasterResponse binCounterMasterResponse = new BinCounterMasterResponse();
//        List<BinCounterMaster> binCounterMasterList = binCounterMasterRepository.findByCounterMasterName(binCounterMasterRequest.getBinCounterMasterName());
//        if(binCounterMasterList.size()>0){
//            throw new ValidationException("Market  already exists, duplicates are not allowed.");
//        }

            BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActiveIn(binCounterMasterRequest.getBinCounterMasterId(), Set.of(true,false));
            if(Objects.nonNull(binCounterMaster)){
                binCounterMaster.setSmallBinStart(binCounterMasterRequest.getSmallBinStart());
                binCounterMaster.setSmallBinEnd(binCounterMasterRequest.getSmallBinEnd());
                binCounterMaster.setBigBinStart(binCounterMasterRequest.getBigBinStart());
                binCounterMaster.setBigBinEnd(binCounterMasterRequest.getBigBinEnd());
                binCounterMaster.setMarketId(binCounterMasterRequest.getMarketId());
                binCounterMaster.setGodownId(binCounterMasterRequest.getGodownId());
                binCounterMaster.setActive(true);
                BinCounterMaster binCounterMaster1 = binCounterMasterRepository.save(binCounterMaster);
                binCounterMasterResponse = mapper.binCounterMasterEntityToObject(binCounterMaster1, BinCounterMasterResponse.class);
                binCounterMasterResponse.setError(false);
            } else {
                binCounterMasterResponse.setError(true);
                binCounterMasterResponse.setError_description("Error occurred while fetching binCounter");
                // throw new ValidationException("Error occurred while fetching village");
            }

        return binCounterMasterResponse;

    }

}
