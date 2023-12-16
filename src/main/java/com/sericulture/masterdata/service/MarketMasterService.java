package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.marketMaster.EditMarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterResponse;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.entity.MarketMaster;
import com.sericulture.masterdata.model.entity.MulberrySource;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MarketMasterRepository;
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
public class MarketMasterService {

    @Autowired
    MarketMasterRepository marketMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public MarketMasterResponse getMarketMasterDetails(String marketMasterName){
        MarketMasterResponse marketMasterResponse = new MarketMasterResponse();
        MarketMaster marketMaster = null;
        if(marketMaster==null){
            marketMaster = marketMasterRepository.findByMarketMasterNameAndActive(marketMasterName,true);
            marketMasterResponse = mapper.marketMasterEntityToObject(marketMaster, MarketMasterResponse.class);
            marketMasterResponse.setError(false);
        }else{
            marketMasterResponse.setError(true);
            marketMasterResponse.setError_description("MarketMaster not found");
        }
        log.info("Entity is ",marketMaster);
        return marketMasterResponse;
    }

    @Transactional
    public MarketMasterResponse insertMarketMasterDetails(MarketMasterRequest marketMasterRequest){
        MarketMasterResponse marketMasterResponse = new MarketMasterResponse();
        MarketMaster marketMaster = mapper.marketMasterObjectToEntity(marketMasterRequest,MarketMaster.class);
        validator.validate(marketMaster);
        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterName(marketMasterRequest.getMarketMasterName());
        if(!marketMasterList.isEmpty() && marketMasterList.stream().filter(MarketMaster::getActive).findAny().isPresent()){
            marketMasterResponse.setError(true);
            marketMasterResponse.setError_description("Market name already exist");
        }
        else if(!marketMasterList.isEmpty() && marketMasterList.stream().filter(Predicate.not(MarketMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            marketMasterResponse.setError(true);
            marketMasterResponse.setError_description("Market name already exist with inactive state");
        }else {
            marketMasterResponse = mapper.marketMasterEntityToObject(marketMasterRepository.save(marketMaster), MarketMasterResponse.class);
            marketMasterResponse.setError(false);
        }
        return marketMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMarketMasterDetails(final Pageable pageable){
        return convertToMapResponse(marketMasterRepository.findByActiveOrderByMarketMasterIdAsc( true, pageable));
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(marketMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<MarketMaster> activeMarketMasters) {
        Map<String, Object> response = new HashMap<>();

        List<MarketMasterResponse> marketMasterResponses = activeMarketMasters.getContent().stream()
                .map(marketMaster -> mapper.marketMasterEntityToObject(marketMaster,MarketMasterResponse.class)).collect(Collectors.toList());
        response.put("marketMaster",marketMasterResponses);
        response.put("currentPage", activeMarketMasters.getNumber());
        response.put("totalItems", activeMarketMasters.getTotalElements());
        response.put("totalPages", activeMarketMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<MarketMaster> activeMarketMasters) {
        Map<String, Object> response = new HashMap<>();

        List<MarketMasterResponse> marketMasterResponses = activeMarketMasters.stream()
                .map(marketMaster -> mapper.marketMasterEntityToObject(marketMaster,MarketMasterResponse.class)).collect(Collectors.toList());
        response.put("marketMaster",marketMasterResponses);
        return response;
    }

    @Transactional
    public MarketMasterResponse deleteMarketMasterDetails(long id) {
        MarketMasterResponse marketMasterResponse = new MarketMasterResponse();
        MarketMaster marketMaster = marketMasterRepository.findByMarketMasterIdAndActive(id, true);
        if (Objects.nonNull(marketMaster)) {
            marketMaster.setActive(false);
            marketMasterResponse = mapper.marketMasterEntityToObject(marketMasterRepository.save(marketMaster), MarketMasterResponse.class);
            marketMasterResponse.setError(false);
        } else {
            marketMasterResponse.setError(true);
            marketMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return marketMasterResponse;
    }

    @Transactional
    public MarketMasterResponse getById(int id){
        MarketMasterResponse marketMasterResponse = new MarketMasterResponse();
        MarketMaster marketMaster = marketMasterRepository.findByMarketMasterIdAndActive(id,true);
        if(marketMaster == null){
            marketMasterResponse.setError(true);
            marketMasterResponse.setError_description("Invalid id");
        }else{
            marketMasterResponse =  mapper.marketMasterEntityToObject(marketMaster,MarketMasterResponse.class);
            marketMasterResponse.setError(false);
        }
        log.info("Entity is ",marketMaster);
        return marketMasterResponse;
    }

    @Transactional
    public MarketMasterResponse updateMarketMasterDetails(EditMarketMasterRequest marketMasterRequest) {
        MarketMasterResponse marketMasterResponse = new MarketMasterResponse();
        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterName(marketMasterRequest.getMarketMasterName());
        if (marketMasterList.size() > 0) {
            marketMasterResponse.setError(true);
            marketMasterResponse.setError_description("Market already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            MarketMaster marketMaster = marketMasterRepository.findByMarketMasterIdAndActiveIn(marketMasterRequest.getMarketMasterId(), Set.of(true, false));
            if (Objects.nonNull(marketMaster)) {
                marketMaster.setMarketMasterName(marketMasterRequest.getMarketMasterName());
                marketMaster.setMarketTypeMasterId(marketMaster.getMarketTypeMasterId());
                marketMaster.setIssueBidSlipStartTime(marketMaster.getIssueBidSlipStartTime());
                marketMaster.setIssueBidSlipEndTime(marketMaster.getIssueBidSlipEndTime());
                marketMaster.setAuction1StartTime(marketMaster.getAuction1StartTime());
                marketMaster.setAuction2StartTime(marketMaster.getAuction2StartTime());
                marketMaster.setAuction3StartTime(marketMaster.getAuction3StartTime());
                marketMaster.setAuction1EndTime(marketMaster.getAuction1EndTime());
                marketMaster.setAuction2EndTime(marketMaster.getAuction2EndTime());
                marketMaster.setAuction3EndTime(marketMaster.getAuction3EndTime());
                marketMaster.setActive(true);
                MarketMaster marketMaster1 = marketMasterRepository.save(marketMaster);
                marketMasterResponse = mapper.marketMasterEntityToObject(marketMaster1, MarketMasterResponse.class);
                marketMasterResponse.setError(false);
            } else {
                marketMasterResponse.setError(true);
                marketMasterResponse.setError_description("Error occurred while fetching market");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return marketMasterResponse;
    }
}
