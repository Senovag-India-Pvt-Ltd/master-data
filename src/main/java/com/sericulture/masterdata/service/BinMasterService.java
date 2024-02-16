package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.BinMasterResponse;
import com.sericulture.masterdata.model.api.binMaster.EditBinMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.UpdateBinMasterStatusRequest;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.useMaster.UserMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.*;
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
public class BinMasterService {
    @Autowired
    BinMasterRepository binMasterRepository;

    @Autowired
    BinCounterMasterRepository binCounterMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BinMasterResponse getBinMasterDetails(int binNumber){
        BinMasterResponse binMasterResponse = new BinMasterResponse();
        BinMaster binMaster = binMasterRepository.findByBinNumberAndActive(binNumber,true);
        if(binMaster==null){
            binMasterResponse.setError(true);
            binMasterResponse.setError_description("bin not found");
        }else{
            binMasterResponse = mapper.binMasterEntityToObject(binMaster, BinMasterResponse.class);
            binMasterResponse.setError(false);
        }
        log.info("Entity is ",binMaster);
        return binMasterResponse;
    }
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public BinMasterResponse getByGodownIdAndMasterId(int godownId, int marketId) {
//        BinMasterResponse binMasterResponse = new BinMasterResponse();
//
//        // Instead of setting userMaster to null, directly query the repository
//        List<BinMaster> binMasterList = binMasterRepository.findByGodownIdAndMarketIdAndActive(godownId, marketId,true);
//
//        if (binMasters != null) {
//            // User found, map to response
//            binMasterResponse = mapper.binMasterEntityToObject(binMaster, BinMasterResponse.class);
//            binMasterResponse.setError(false);
//        } else {
//            // User not found
//            binMasterResponse.setError(true);
//            binMasterResponse.setError_description("Bin not found");
//        }
//
//        log.info("Entity is {}", binMaster);
//        return binMasterResponse;
//    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getByGodownIdAndMarketId(Integer godownId,Integer marketId) {
//        BinMasterResponse binMasterResponse = new BinMasterResponse();
        Map<String, Object> response = new HashMap<>();
        List<BinMaster> binMasterList = binMasterRepository.findByGodownIdAndMarketIdAndActive(godownId,marketId, true);
        if (binMasterList.isEmpty()) {
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            return response;
        } else {
            log.info("Entity is ", binMasterList);
            response = convertListToMapResponse(binMasterList);
            return response;
        }
    }

    @Transactional
    public BinMasterResponse insertBinMasterDetails(BinMasterRequest binMasterRequest){
        BinMasterResponse binMasterResponse = new BinMasterResponse();
        BinMaster binMaster = mapper.binMasterObjectToEntity(binMasterRequest,BinMaster.class);



        validator.validate(binMaster);
        List<BinMaster> binMasterList = binMasterRepository.findByGodownIdAndMarketIdAndActive(binMasterRequest.getGodownId(), binMasterRequest.getMarketId(),true);
        if(binMasterList.isEmpty() && binMasterList.stream().filter(BinMaster::getActive).findAny().isPresent()){
            binMasterResponse.setError(true);
            binMasterResponse.setError_description("BinMaster name already exist");
        }
        else if(!binMasterList.isEmpty() && binMasterList.stream().filter(Predicate.not(BinMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            binMasterResponse.setError(true);
            binMasterResponse.setError_description("BinMaster name already exist with inactive state");
        }else {
            binMasterResponse = mapper.binMasterEntityToObject(binMasterRepository.save(binMaster), BinMasterResponse.class);
            binMasterResponse.setError(false);
        }
        return binMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedBinMasterDetails(final Pageable pageable){
        return convertToMapResponse(binMasterRepository.findByActiveOrderByBinMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(binMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<BinMaster> activeBinMasters) {
        Map<String, Object> response = new HashMap<>();

        List<BinMasterResponse> binMasterResponses = activeBinMasters.getContent().stream()
                .map(binMaster -> mapper.binMasterEntityToObject(binMaster,BinMasterResponse.class)).collect(Collectors.toList());
        response.put("binMaster",binMasterResponses);
        response.put("currentPage", activeBinMasters.getNumber());
        response.put("totalItems", activeBinMasters.getTotalElements());
        response.put("totalPages", activeBinMasters.getTotalPages());

        return response;
    }
    private Map<String, Object> convertListEntityToMapResponse(final List<BinMaster> activeBinMasters) {
        Map<String, Object> response = new HashMap<>();

        List<BinMasterResponse> binMasterResponses = activeBinMasters.stream()
                .map(binMaster -> mapper.binMasterEntityToObject(binMaster,BinMasterResponse.class)).collect(Collectors.toList());
        response.put("binMaster",binMasterResponses);
        return response;
    }

    @Transactional
    public BinMasterResponse deleteBinMasterDetails(long id) {
        BinMasterResponse binMasterResponse = new BinMasterResponse();
        BinMaster binMaster = binMasterRepository.findByBinMasterIdAndActive(id, true);
        if (Objects.nonNull(binMaster)) {
            binMaster.setActive(false);
            binMasterResponse = mapper.binMasterEntityToObject(binMasterRepository.save(binMaster), BinMasterResponse.class);
            binMasterResponse.setError(false);
        } else {
            binMasterResponse.setError(true);
            binMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return binMasterResponse;
    }

    @Transactional
    public BinMasterResponse getById(int id){
        BinMasterResponse binMasterResponse = new BinMasterResponse();
        BinMaster binMaster = binMasterRepository.findByBinMasterIdAndActive(id,true);
        if(binMaster == null){
            binMasterResponse.setError(true);
            binMasterResponse.setError_description("Invalid id");
        }else{
            binMasterResponse =  mapper.binMasterEntityToObject(binMaster,BinMasterResponse.class);
            binMasterResponse.setError(false);
        }
        log.info("Entity is ",binMaster);
        return binMasterResponse;
    }

    @Transactional
    public BinMaster getByMarketGodownTypeBinNumber(int marketId, int godownId, String type, int binNumber){
        BinMasterResponse binMasterResponse = new BinMasterResponse();
        BinMaster binMaster = binMasterRepository.findByGodownIdAndMarketIdAndTypeAndBinNumberAndActive(godownId,marketId,type, binNumber, true);
        return binMaster;
//        if(binMaster == null){
//            binMasterResponse.setError(true);
//            binMasterResponse.setError_description("Invalid id");
//        }else{
//            binMasterResponse =  mapper.binMasterEntityToObject(binMaster,BinMasterResponse.class);
//            binMasterResponse.setError(false);
//        }
//        log.info("Entity is ",binMaster);
//        return binMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getBinMasterAndBinCounterMasterId(int binCounterMasterId) {
//        BinMasterResponse binMasterResponse = new BinMasterResponse();
        Map<String, Object> response = new HashMap<>();
        List<BinMaster> binMasterList = binMasterRepository.findByBinCounterMasterIdAndActive(binCounterMasterId, true);
        if (binMasterList.isEmpty()) {
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            return response;
        } else {
            log.info("Entity is ", binMasterList);
            response = convertListToMapResponse(binMasterList);
            return response;
        }
    }

    private Map<String, Object> convertListToMapResponse(List<BinMaster> binMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<BinMasterResponse> binMasterResponses = binMasterList.stream()
                .map(binMaster -> mapper.binMasterEntityToObject(binMaster,BinMasterResponse.class)).collect(Collectors.toList());
        response.put("binMaster",binMasterResponses);
        response.put("totalItems", binMasterList.size());
        return response;
    }

    @Transactional
    public BinMasterResponse updateBinMasterDetails(EditBinMasterRequest binMasterRequest) {
        BinMasterResponse binMasterResponse = new BinMasterResponse();
        List<BinMaster> binMasterList = binMasterRepository.findByGodownIdAndMarketIdAndBinMasterIdIsNot (binMasterRequest.getGodownId(),binMasterRequest.getMarketId(),binMasterRequest.getBinMasterId());
        if (binMasterList.size() > 0) {
            binMasterResponse.setError(true);
            binMasterResponse.setError_description("Bin already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            BinMaster binMaster = binMasterRepository.findByBinMasterIdAndActiveIn(binMasterRequest.getBinMasterId(), Set.of(true, false));
//            BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActive(binMasterRequest.getBinCounterMasterId(), true);
//            if (binCounterMaster == null) {
////                throw new ValidationException("BinCounter does not exist.");
////            }
//                binMasterResponse.setError(true);
//                binMasterResponse.setError_description("BinCounter, does not exist.");
//                // throw new ValidationException("Village already exists, duplicates are not allowed.");
//            } else {

                if (Objects.nonNull(binMaster)) {

                    binMaster.setBinMasterId(binMasterRequest.getBinMasterId());
                    binMaster.setBinNumber(binMasterRequest.getBinNumber());
                    binMaster.setType(binMasterRequest.getType());
                    binMaster.setStatus(binMasterRequest.getStatus());
                    binMaster.setMarketId(binMasterRequest.getMarketId());
                    binMaster.setGodownId(binMasterRequest.getGodownId());
                    binMaster.setActive(true);
                    BinMaster binMaster1 = binMasterRepository.save(binMaster);
                    binMasterResponse = mapper.binMasterEntityToObject(binMaster1, BinMasterResponse.class);
                    binMasterResponse.setError(false);
                } else {
                    binMasterResponse.setError(true);
                    binMasterResponse.setError_description("Error occurred while fetching Bin");
                    // throw new ValidationException("Error occurred while fetching village");
                }
            }

            return binMasterResponse;

    }

//    @Transactional
//    public BinMasterResponse updateBinStatus(BinMaster binMasterRequest) {
//        BinMasterResponse binMasterResponse = new BinMasterResponse();
//
//        BinMaster resBinMaster = binMasterRepository.findByGodownIdAndMarketIdAndTypeAndBinNumberAndActive(binMasterRequest.getMarketId(), binMasterRequest.getGodownId(), binMasterRequest.getType(), binMasterRequest.getBinNumber(),true);
//        resBinMaster.setStatus(binMasterRequest.getStatus());
//
//        BinMaster binMaster1 = binMasterRepository.save(resBinMaster);
//
//        return binMasterResponse;

//        List<BinMaster> binMasterList = binMasterRepository.findByGodownIdAndMarketIdAndActive (binMasterRequest.getGodownId(),binMasterRequest.getMarketId(),true);
//        if (binMasterList.size() > 0) {
//            binMasterResponse.setError(true);
//            binMasterResponse.setError_description("Bin already exists, duplicates are not allowed.");
//            // throw new ValidationException("Village already exists, duplicates are not allowed.");
//        } else {
//
//            BinMaster binMaster = binMasterRepository.findByBinMasterIdAndActiveIn(binMasterRequest.getBinMasterId(), Set.of(true, false));
////            BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActive(binMasterRequest.getBinCounterMasterId(), true);
////            if (binCounterMaster == null) {
//////                throw new ValidationException("BinCounter does not exist.");
//////            }
////                binMasterResponse.setError(true);
////                binMasterResponse.setError_description("BinCounter, does not exist.");
////                // throw new ValidationException("Village already exists, duplicates are not allowed.");
////            } else {
//
//            if (Objects.nonNull(binMaster)) {
//
//                binMaster.setBinMasterId(binMasterRequest.getBinMasterId());
//                binMaster.setBinNumber(binMasterRequest.getBinNumber());
//                binMaster.setType(binMasterRequest.getType());
//                binMaster.setStatus(binMasterRequest.getStatus());
//                binMaster.setMarketId(binMasterRequest.getMarketId());
//                binMaster.setGodownId(binMasterRequest.getGodownId());
//                binMaster.setActive(true);
//                BinMaster binMaster1 = binMasterRepository.save(binMaster);
//                binMasterResponse = mapper.binMasterEntityToObject(binMaster1, BinMasterResponse.class);
//                binMasterResponse.setError(false);
//            } else {
//                binMasterResponse.setError(true);
//                binMasterResponse.setError_description("Error occurred while fetching Bin");
//                // throw new ValidationException("Error occurred while fetching village");
//            }
//        }

//        return binMasterResponse;

//    }
    @Transactional
    public BinMasterResponse updateBinStatus(BinMaster binMasterRequest) {
        BinMasterResponse binMasterResponse = new BinMasterResponse();

        BinMaster resBinMaster = binMasterRepository.findByGodownIdAndMarketIdAndTypeAndBinNumberAndActive(
                binMasterRequest.getGodownId(),
                binMasterRequest.getMarketId(),
                binMasterRequest.getType(),
                binMasterRequest.getBinNumber(),
                true
        );

        if (resBinMaster != null) {
            resBinMaster.setStatus(binMasterRequest.getStatus());
            BinMaster binMaster1 = binMasterRepository.save(resBinMaster);
            binMasterResponse = mapper.binMasterEntityToObject(binMaster1, BinMasterResponse.class);
            binMasterResponse.setError(false);
        } else {
            binMasterResponse.setError(true);
            binMasterResponse.setError_description("BinMaster not found for the specified criteria.");
        }

        return binMasterResponse;
    }

}
