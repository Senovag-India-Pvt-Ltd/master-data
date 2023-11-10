package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterRequest;
import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterResponse;
import com.sericulture.masterdata.model.api.binCounterMaster.EditBinCounterMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import com.sericulture.masterdata.model.entity.BinCounterMaster;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.BinCounterMasterRepository;
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

    @Transactional
    public BinCounterMasterResponse insertBinCounterMasterDetails(BinCounterMasterRequest binCounterMasterRequest){
        BinCounterMaster binCounterMaster = mapper.binCounterMasterObjectToEntity(binCounterMasterRequest,BinCounterMaster.class);
        validator.validate(binCounterMaster);
//        List<BinCounterMaster> binCounterMasterList = binCounterMasterRepository.findByBinCounterMasterName(binCounterMasterRequest.getB());
//        if(!binCounterMasterList.isEmpty() && binCounterMasterList.stream().filter(BinCounterMaster::getActive).findAny().isPresent()){
//            throw new ValidationException("BinCounterMaster name already exist");
//        }
//        if(!binCounterMasterList.isEmpty() && binCounterMasterList.stream().filter(Predicate.not(BinCounterMaster::getActive)).findAny().isPresent()){
//            throw new ValidationException("BinCounterMaster name already exist with inactive state");
//        }
        return mapper.binCounterMasterEntityToObject(binCounterMasterRepository.save(binCounterMaster), BinCounterMasterResponse.class);
    }

//    @Transactional
//    public void insertBinMasterDetails(Map<String, Object> payload) {
//        // Extract binCounterMasterDetail data
//        Map<String, Object> binCounterMasterDetail = (Map<String, Object>) payload.get("binCounterMasterDetail");
//        Map<String, Object> record = (Map<String, Object>) binCounterMasterDetail.get("record");
//        Long marketId = ((Number) record.get("market_id")).longValue();
//        Long godownId = ((Number) record.get("godown_id")).longValue();
//        Long smallBinStart = ((Number) record.get("small_bin_start")).longValue();
//        Long smallBinEnd = ((Number) record.get("small_bin_end")).longValue();
//        Long bigBinStart = ((Number) record.get("big_bin_start")).longValue();
//        Long bigBinEnd = ((Number) record.get("big_bin_end")).longValue();
//
//        // Save data to BinCounterMaster
//        BinCounterMasterRequest binCounterMasterRequest = new BinCounterMasterRequest();
//        binCounterMasterRequest.setMarketId(marketId);
//        binCounterMasterRequest.setGodownId(godownId);
//        binCounterMasterRequest.setSmallBinStart(smallBinStart);
//        binCounterMasterRequest.setSmallBinEnd(smallBinEnd);
//        binCounterMasterRequest.setBigBinStart(bigBinStart);
//        binCounterMasterRequest.setBigBinEnd(bigBinEnd);
//        BinCounterMasterResponse binCounterMasterResponse = insertBinCounterMasterDetails(binCounterMasterRequest);
//
//
//        // Extract smallBinDetails data
//        List<Map<String, Object>> smallBinRecords = (List<Map<String, Object>>) payload.get("smallBinDetails");
//        for (Map<String, Object> smallBinRecord : smallBinRecords) {
//            BinMasterRequest binMasterRequest = new BinMasterRequest();
//            binMasterRequest.setBinCounterMasterId(binCounterMasterResponse.getBinCounterMasterId());
//            binMasterRequest.setType(((Number) smallBinRecord.get("type")).longValue());
//            binMasterRequest.setBinNumber((String) smallBinRecord.get("binNumber"));
//            if (smallBinRecord.containsKey("status")) {
//                binMasterRequest.setStatus(((Number) smallBinRecord.get("status")).longValue());
//            }
//        }
//
//        // Extract bigBinDetails data
//        List<Map<String, Object>> bigBins = (List<Map<String, Object>>) payload.get("bigBinDetails");
//        for (Map<String, Object> bigBinRecord : bigBins) {
//            BinMasterRequest binMasterRequest = new BinMasterRequest();
//            binMasterRequest.setBinCounterMasterId(binCounterMasterResponse.getBinCounterMasterId());
//            binMasterRequest.setType(((Number) bigBinRecord.get("type")).longValue());
//            binMasterRequest.setBinNumber((String) bigBinRecord.get("binNumber"));
//            //insertBinMasterDetails(binMasterRequest);
//        }
//    }
    // other service methods

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

    @Transactional
    public void deleteBinCounterMasterDetails(long id) {
        BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActive(id, true);
        if (Objects.nonNull(binCounterMaster)) {
            binCounterMaster.setActive(false);
            binCounterMasterRepository.save(binCounterMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public BinCounterMasterResponse getById(int id){
        BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActive(id,true);
        if(binCounterMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",binCounterMaster);
        return mapper.binCounterMasterEntityToObject(binCounterMaster,BinCounterMasterResponse.class);
    }

    @Transactional
    public BinCounterMasterResponse updateBinCounterMasterDetails(EditBinCounterMasterRequest binCounterMasterRequest){
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
        }else{
            throw new ValidationException("Error occurred while fetching Bin Counter Master");
        }
        return mapper.binCounterMasterEntityToObject(binCounterMasterRepository.save(binCounterMaster),BinCounterMasterResponse.class);
    }



}
