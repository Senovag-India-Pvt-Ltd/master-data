package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.BinMasterResponse;
import com.sericulture.masterdata.model.api.binMaster.EditBinMasterRequest;
import com.sericulture.masterdata.model.entity.BinCounterMaster;
import com.sericulture.masterdata.model.entity.BinMaster;
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
    public BinMasterResponse getBinMasterDetails(String binNumber){
        BinMaster binMaster = null;
        if(binMaster==null){
            binMaster = binMasterRepository.findByBinNumberAndActive(binNumber,true);
        }
        log.info("Entity is ",binMaster);
        return mapper.binMasterEntityToObject(binMaster,BinMasterResponse.class);
    }

    @Transactional
    public BinMasterResponse insertBinMasterDetails(BinMasterRequest binMasterRequest){
        BinMaster binMaster = mapper.binMasterObjectToEntity(binMasterRequest,BinMaster.class);
        validator.validate(binMaster);
        List<BinMaster> binMasterList = binMasterRepository.findByBinNumberAndBinCounterMasterId(binMasterRequest.getBinNumber(), binMasterRequest.getBinCounterMasterId());
        if(!binMasterList.isEmpty() && binMasterList.stream().filter(BinMaster::getActive).findAny().isPresent()){
            throw new ValidationException("Bin  name already exist with this state");
        }
        if(!binMasterList.isEmpty() && binMasterList.stream().filter(Predicate.not(BinMaster::getActive)).findAny().isPresent()){
            throw new ValidationException("Bin name already exist with inactive district with this state");
        }
        BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActive(binMaster.getBinCounterMasterId(), true);
        if(binCounterMaster == null){
            throw new ValidationException("BinCounter does not exist.");
        }
        return mapper.binMasterEntityToObject(binMasterRepository.save(binMaster),BinMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedBinMasterDetails(final Pageable pageable){
        return convertToMapResponse(binMasterRepository.findByActiveOrderByBinMasterIdAsc( true, pageable));
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

    @Transactional
    public void deleteBinMasterDetails(long id) {
        BinMaster binMaster = binMasterRepository.findByBinMasterIdAndActive(id, true);
        if (Objects.nonNull(binMaster)) {
            binMaster.setActive(false);
            binMasterRepository.save(binMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public BinMasterResponse getById(int id){
        BinMaster binMaster = binMasterRepository.findByBinMasterIdAndActive(id,true);
        if(binMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",binMaster);
        return mapper.binMasterEntityToObject(binMaster,BinMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getBinMasterAndBinCounterMasterId(Long binCounterMasterId){
        List<BinMaster> binMasterList = binMasterRepository.findByBinCounterMasterIdAndActive(binCounterMasterId,true);
        if(binMasterList.isEmpty()){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",binMasterList);
        return convertListToMapResponse(binMasterList);
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
    public BinMasterResponse updateBinMasterDetails(EditBinMasterRequest binMasterRequest){
        List<BinMaster> binMasterList = binMasterRepository.findByBinNumber(binMasterRequest.getBinNumber());
        if(binMasterList.size()>0){
            throw new ValidationException("Bin already exists with this state, duplicates are not allowed.");
        }

        BinMaster binMaster = binMasterRepository.findByBinMasterIdAndActiveIn(binMasterRequest.getBinMasterId(), Set.of(true,false));
        BinCounterMaster binCounterMaster = binCounterMasterRepository.findByBinCounterMasterIdAndActive(binMasterRequest.getBinCounterMasterId(), true);
        if(binCounterMaster == null){
            throw new ValidationException("BinCounter does not exist.");
        }
        if(Objects.nonNull(binMaster)){
            binMaster.setBinCounterMasterId(binMasterRequest.getBinCounterMasterId());
            binMaster.setBinNumber(binMasterRequest.getBinNumber());
            binMaster.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching bin");
        }
        return mapper.binMasterEntityToObject(binMasterRepository.save(binMaster),BinMasterResponse.class);
    }
}
