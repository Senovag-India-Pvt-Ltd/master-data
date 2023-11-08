package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.marketMaster.EditMarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterResponse;
import com.sericulture.masterdata.model.entity.MarketMaster;
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
        MarketMaster marketMaster = null;
        if(marketMaster==null){
            marketMaster = marketMasterRepository.findByMarketMasterNameAndActive(marketMasterName,true);
        }
        log.info("Entity is ",marketMasterName);
        return mapper.marketmasterEntityToObject(marketMaster,MarketMasterResponse.class);
    }

    @Transactional
    public MarketMasterResponse insertMarketMasterDetails(MarketMasterRequest marketMasterRequest){
        MarketMaster marketMaster = mapper.marketMasterObjectToEntity(marketMasterRequest,MarketMaster.class);
        validator.validate(marketMaster);
        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterName(marketMasterRequest.getMarketMasterName());
        if(!marketMasterList.isEmpty() && marketMasterList.stream().filter(MarketMaster::getActive).findAny().isPresent()){
            throw new ValidationException("Market name already exist");
        }
        if(!marketMasterList.isEmpty() && marketMasterList.stream().filter(Predicate.not(MarketMaster::getActive)).findAny().isPresent()){
            throw new ValidationException("Market name already exist with inactive state");
        }
        return mapper.marketmasterEntityToObject(marketMasterRepository.save(marketMaster),MarketMasterResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedMarketMasterDetails(final Pageable pageable){
        return convertToMapResponse(marketMasterRepository.findByActiveOrderByMarketMasterIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<MarketMaster> activeMarketMasters) {
        Map<String, Object> response = new HashMap<>();

        List<MarketMasterResponse> marketMasterResponses = activeMarketMasters.getContent().stream()
                .map(marketMaster -> mapper.marketmasterEntityToObject(marketMaster,MarketMasterResponse.class)).collect(Collectors.toList());
        response.put("marketMaster",marketMasterResponses);
        response.put("currentPage", activeMarketMasters.getNumber());
        response.put("totalItems", activeMarketMasters.getTotalElements());
        response.put("totalPages", activeMarketMasters.getTotalPages());

        return response;
    }

    @Transactional
    public void deleteMarketMasterDetails(long id) {
        MarketMaster marketMaster = marketMasterRepository.findByMarketMasterIdAndActive(id, true);
        if (Objects.nonNull(marketMaster)) {
            marketMaster.setActive(false);
            marketMasterRepository.save(marketMaster);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public MarketMasterResponse getById(int id){
        MarketMaster marketMaster = marketMasterRepository.findByMarketMasterIdAndActive(id,true);
        if(marketMaster == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",marketMaster);
        return mapper.marketmasterEntityToObject(marketMaster,MarketMasterResponse.class);
    }

    @Transactional
    public MarketMasterResponse updateMarketMasterDetails(EditMarketMasterRequest marketMasterRequest){
        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterName(marketMasterRequest.getMarketMasterName());
        if(marketMasterList.size()>0){
            throw new ValidationException("Market  already exists, duplicates are not allowed.");
        }

        MarketMaster marketMaster = marketMasterRepository.findByMarketMasterIdAndActiveIn(marketMasterRequest.getMarketMasterId(), Set.of(true,false));
        if(Objects.nonNull(marketMaster)){
            marketMaster.setMarketMasterName(marketMasterRequest.getMarketMasterName());
            marketMaster.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching village");
        }
        return mapper.marketmasterEntityToObject(marketMasterRepository.save(marketMaster),MarketMasterResponse.class);
    }

}
