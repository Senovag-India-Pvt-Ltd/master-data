package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.marketTypeMaster.EditMarketTypeMasterRequest;
import com.sericulture.masterdata.model.api.marketTypeMaster.MarketTypeMasterRequest;
import com.sericulture.masterdata.model.api.marketTypeMaster.MarketTypeMasterResponse;
import com.sericulture.masterdata.model.api.village.EditVillageRequest;
import com.sericulture.masterdata.model.api.village.VillageRequest;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.entity.MarketTypeMaster;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MarketTypeMasterRepository;
import com.sericulture.masterdata.repository.VillageRepository;
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
public class MarketTypeMasterService {

    @Autowired
    MarketTypeMasterRepository marketTypeMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public MarketTypeMasterResponse getMarketTypeMasterDetails(String marketTypeMasterName){
        MarketTypeMasterResponse marketTypeMasterResponse = new MarketTypeMasterResponse();
        MarketTypeMaster marketTypeMaster = marketTypeMasterRepository.findByMarketTypeMasterNameAndActive(marketTypeMasterName,true);
        if(marketTypeMaster==null){
            marketTypeMasterResponse.setError(true);
            marketTypeMasterResponse.setError_description("Market Type not found");
        }else{
            marketTypeMasterResponse = mapper.marketTypeMasterEntityToObject(marketTypeMaster,MarketTypeMasterResponse.class);
            marketTypeMasterResponse.setError(false);
        }
        log.info("Entity is ",marketTypeMaster);
        return marketTypeMasterResponse;
    }

    @Transactional
    public MarketTypeMasterResponse insertMarketTypeMasterDetails(MarketTypeMasterRequest marketTypeMasterRequest){
        MarketTypeMasterResponse marketTypeMasterResponse = new MarketTypeMasterResponse();
        MarketTypeMaster marketTypeMaster = mapper.marketTypeMasterObjectToEntity(marketTypeMasterRequest,MarketTypeMaster.class);
        validator.validate(marketTypeMaster);
        List<MarketTypeMaster> marketTypeMasterList = marketTypeMasterRepository.findByMarketTypeMasterNameAndMarketTypeNameInKannada(marketTypeMasterRequest.getMarketTypeMasterName(),marketTypeMasterRequest.getMarketTypeNameInKannada());
        if(!marketTypeMasterList.isEmpty() && marketTypeMasterList.stream().filter(MarketTypeMaster::getActive).findAny().isPresent()){
            // throw new ValidationException("Village name already exist");
            marketTypeMasterResponse.setError(true);
            marketTypeMasterResponse.setError_description("Market Type name already exist");
        }
        else if(!marketTypeMasterList.isEmpty() && marketTypeMasterList.stream().filter(Predicate.not(MarketTypeMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            marketTypeMasterResponse.setError(true);
            marketTypeMasterResponse.setError_description("Market Type  name already exist with inactive state");
        }else {
            marketTypeMasterResponse = mapper.marketTypeMasterEntityToObject(marketTypeMasterRepository.save(marketTypeMaster), MarketTypeMasterResponse.class);
            marketTypeMasterResponse.setError(false);
        }
        return marketTypeMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMarketTypeMasterDetails(final Pageable pageable){
        return convertToMapResponse(marketTypeMasterRepository.findByActiveOrderByMarketTypeMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(marketTypeMasterRepository.findByActiveOrderByMarketTypeMasterNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<MarketTypeMaster> activeMarketTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<MarketTypeMasterResponse> marketTypeMasterResponses = activeMarketTypeMasters.getContent().stream()
                .map(marketTypeMaster -> mapper.marketTypeMasterEntityToObject(marketTypeMaster,MarketTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("marketTypeMaster",marketTypeMasterResponses);
        response.put("currentPage", activeMarketTypeMasters.getNumber());
        response.put("totalItems", activeMarketTypeMasters.getTotalElements());
        response.put("totalPages", activeMarketTypeMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<MarketTypeMaster> activeMarketTypeMasters) {
        Map<String, Object> response = new HashMap<>();

        List<MarketTypeMasterResponse> marketTypeMasterResponses = activeMarketTypeMasters.stream()
                .map(marketTypeMaster -> mapper.marketTypeMasterEntityToObject(marketTypeMaster,MarketTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("marketTypeMaster",marketTypeMasterResponses);
        return response;
    }

    @Transactional
    public MarketTypeMasterResponse deleteMarketTypeMasterDetails(long id) {
        MarketTypeMasterResponse marketTypeMasterResponse = new MarketTypeMasterResponse();
        MarketTypeMaster marketTypeMaster = marketTypeMasterRepository.findByMarketTypeMasterIdAndActive(id, true);
        if (Objects.nonNull(marketTypeMaster)) {
            marketTypeMaster.setActive(false);
            //villageRepository.save(village);
            marketTypeMasterResponse = mapper.marketTypeMasterEntityToObject(marketTypeMasterRepository.save(marketTypeMaster), MarketTypeMasterResponse.class);
            marketTypeMasterResponse.setError(false);
        } else {
            marketTypeMasterResponse.setError(true);
            marketTypeMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return marketTypeMasterResponse;
    }

    @Transactional
    public MarketTypeMasterResponse getById(int id){
        MarketTypeMasterResponse marketTypeMasterResponse = new MarketTypeMasterResponse();
        MarketTypeMaster marketTypeMaster = marketTypeMasterRepository.findByMarketTypeMasterIdAndActive(id,true);
        if(marketTypeMaster == null){
            //throw new ValidationException("Invalid Id");
            marketTypeMasterResponse.setError(true);
            marketTypeMasterResponse.setError_description("Invalid id");
        }else{
            marketTypeMasterResponse =  mapper.marketTypeMasterEntityToObject(marketTypeMaster,MarketTypeMasterResponse.class);
            marketTypeMasterResponse.setError(false);
        }
        log.info("Entity is ",marketTypeMaster);
        return marketTypeMasterResponse;
    }



    private Map<String, Object> convertListToMapResponse(List<MarketTypeMaster> marketTypeMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<MarketTypeMasterResponse> marketTypeMasterResponses = marketTypeMasterList.stream()
                .map(marketTypeMaster -> mapper.marketTypeMasterEntityToObject(marketTypeMaster,MarketTypeMasterResponse.class)).collect(Collectors.toList());
        response.put("marketTypeMaster",marketTypeMasterResponses);
        response.put("totalItems", marketTypeMasterResponses.size());
        return response;
    }

    @Transactional
    public MarketTypeMasterResponse updateMarketTypeMasterDetails(EditMarketTypeMasterRequest marketTypeMasterRequest){
        MarketTypeMasterResponse marketTypeMasterResponse = new MarketTypeMasterResponse();
        List<MarketTypeMaster> marketTypeMasterList = marketTypeMasterRepository.findByMarketTypeMasterNameAndMarketTypeNameInKannada(marketTypeMasterRequest.getMarketTypeMasterName(),marketTypeMasterRequest.getMarketTypeNameInKannada());
        if(marketTypeMasterList.size()>0){
            marketTypeMasterResponse.setError(true);
            marketTypeMasterResponse.setError_description("Market Type already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {

            MarketTypeMaster marketTypeMaster = marketTypeMasterRepository.findByMarketTypeMasterIdAndActiveIn(marketTypeMasterRequest.getMarketTypeMasterId(), Set.of(true, false));
            if (Objects.nonNull(marketTypeMaster)) {
                marketTypeMaster.setMarketTypeMasterName(marketTypeMasterRequest.getMarketTypeMasterName());
                marketTypeMaster.setMarketTypeNameInKannada(marketTypeMasterRequest.getMarketTypeNameInKannada());
                marketTypeMaster.setReelerFee(marketTypeMasterRequest.getReelerFee());
                marketTypeMaster.setFarmerFee(marketTypeMasterRequest.getFarmerFee());
                marketTypeMaster.setTraderFee(marketTypeMasterRequest.getTraderFee());
                marketTypeMaster.setActive(true);
                MarketTypeMaster marketTypeMaster1 = marketTypeMasterRepository.save(marketTypeMaster);
                marketTypeMasterResponse = mapper.marketTypeMasterEntityToObject(marketTypeMaster1, MarketTypeMasterResponse.class);
                marketTypeMasterResponse.setError(false);
            } else {
                marketTypeMasterResponse.setError(true);
                marketTypeMasterResponse.setError_description("Error occurred while fetching marketType");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }

        return marketTypeMasterResponse;
    }


}
