package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterRequest;
import com.sericulture.masterdata.model.api.binCounterMaster.BinCounterMasterResponse;
import com.sericulture.masterdata.model.api.binCounterMaster.EditBinCounterMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.EditMarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterResponse;
import com.sericulture.masterdata.model.entity.BinCounterMaster;
import com.sericulture.masterdata.model.entity.MarketMaster;
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
