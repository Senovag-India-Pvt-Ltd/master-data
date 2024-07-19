package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.marketMaster.EditMarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterResponse;
import com.sericulture.masterdata.model.api.mulberrySource.MulberrySourceResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.MarketMasterDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.entity.MarketMaster;
import com.sericulture.masterdata.model.entity.MulberrySource;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.MarketMasterRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @Transactional
    public MarketMasterResponse insertMarketMasterDetails(MarketMasterRequest marketMasterRequest){
        MarketMasterResponse marketMasterResponse = new MarketMasterResponse();
        MarketMaster marketMaster = mapper.marketMasterObjectToEntity(marketMasterRequest,MarketMaster.class);
        validator.validate(marketMaster);
        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterNameAndMarketNameInKannada(marketMasterRequest.getMarketMasterName(), marketMasterRequest.getMarketNameInKannada());
        if(!marketMasterList.isEmpty() && marketMasterList.stream().filter(MarketMaster::getActive).findAny().isPresent()){
            marketMasterResponse.setError(true);
            marketMasterResponse.setError_description("Market name already exist");
//        }
//        else if(!marketMasterList.isEmpty() && marketMasterList.stream().filter(Predicate.not(MarketMaster::getActive)).findAny().isPresent()){
//            //throw new ValidationException("Village name already exist with inactive state");
//            marketMasterResponse.setError(true);
//            marketMasterResponse.setError_description("Market name already exist with inactive state");
        }else {
            marketMasterResponse = mapper.marketMasterEntityToObject(marketMasterRepository.save(marketMaster), MarketMasterResponse.class);
            marketMasterResponse.setError(false);
        }
        return marketMasterResponse;
    }

    public Map<String,Object> getPaginatedMarketMasterDetails(final Pageable pageable){
        return convertToMapResponse(marketMasterRepository.findByActiveOrderByMarketMasterIdAsc( true, pageable));
    }
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(marketMasterRepository.findByActiveOrderByMarketMasterNameAsc(isActive));
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

    public Map<String,Object> getPaginatedMarketMasterDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(marketMasterRepository.getByActiveOrderByMarketMasterIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<MarketMasterDTO> activeMarketMasters) {
        Map<String, Object> response = new HashMap<>();

        List<MarketMasterResponse> marketMasterResponses = activeMarketMasters.getContent().stream()
                .map(marketMaster -> mapper.marketMasterDTOToObject(marketMaster,MarketMasterResponse.class)).collect(Collectors.toList());
        response.put("marketMaster",marketMasterResponses);
        response.put("currentPage", activeMarketMasters.getNumber());
        response.put("totalItems", activeMarketMasters.getTotalElements());
        response.put("totalPages", activeMarketMasters.getTotalPages());
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

    public MarketMasterResponse getByIdJoin(int id) {
        MarketMasterResponse marketMasterResponse = new MarketMasterResponse();
        MarketMasterDTO marketMasterDTO = marketMasterRepository.getByMarketMasterIdAndActive(id, true);
        if (marketMasterDTO == null) {
            // throw new ValidationException("Invalid Id");
            marketMasterResponse.setError(true);
            marketMasterResponse.setError_description("Invalid id");
        } else {
            marketMasterResponse = mapper.marketMasterDTOToObject(marketMasterDTO, MarketMasterResponse.class);
            marketMasterResponse.setError(false);
        }
        log.info("Entity is ", marketMasterDTO);
        return marketMasterResponse;
    }

    @Transactional
    public MarketMasterResponse updateMarketMasterDetails(EditMarketMasterRequest marketMasterRequest) {
        MarketMasterResponse marketMasterResponse = new MarketMasterResponse();
//        List<MarketMaster> marketMasterList = marketMasterRepository.findByMarketMasterName(marketMasterRequest.getMarketMasterName());
//        if (marketMasterList.size() > 0) {
//            marketMasterResponse.setError(true);
//            marketMasterResponse.setError_description("Market already exists, duplicates are not allowed.");
//             throw new ValidationException("Village already exists, duplicates are not allowed.");
//        } else {

            MarketMaster marketMaster = marketMasterRepository.findByMarketMasterIdAndActiveIn(marketMasterRequest.getMarketMasterId(), Set.of(true, false));
            if (Objects.nonNull(marketMaster)) {
                marketMaster.setMarketMasterName(marketMasterRequest.getMarketMasterName());
                marketMaster.setBoxWeight(marketMasterRequest.getBoxWeight());
                marketMaster.setMarketNameInKannada(marketMasterRequest.getMarketNameInKannada());
                marketMaster.setMarketTypeMasterId(marketMasterRequest.getMarketTypeMasterId());
                marketMaster.setIssueBidSlipStartTime(marketMasterRequest.getIssueBidSlipStartTime());
                marketMaster.setIssueBidSlipEndTime(marketMasterRequest.getIssueBidSlipEndTime());
                marketMaster.setAuction1StartTime(marketMasterRequest.getAuction1StartTime());
                marketMaster.setAuction2StartTime(marketMasterRequest.getAuction2StartTime());
                marketMaster.setAuction3StartTime(marketMasterRequest.getAuction3StartTime());
                marketMaster.setAuction1EndTime(marketMasterRequest.getAuction1EndTime());
                marketMaster.setAuction2EndTime(marketMasterRequest.getAuction2EndTime());
                marketMaster.setAuction3EndTime(marketMasterRequest.getAuction3EndTime());
                marketMaster.setAuctionAcceptance1StartTime(marketMasterRequest.getAuctionAcceptance1StartTime());
                marketMaster.setAuctionAcceptance2StartTime(marketMasterRequest.getAuctionAcceptance2StartTime());
                marketMaster.setAuctionAcceptance3StartTime(marketMasterRequest.getAuctionAcceptance3StartTime());
                marketMaster.setAuctionAcceptance1EndTime(marketMasterRequest.getAuctionAcceptance1EndTime());
                marketMaster.setAuctionAcceptance2EndTime(marketMasterRequest.getAuctionAcceptance2EndTime());
                marketMaster.setAuctionAcceptance3EndTime(marketMasterRequest.getAuctionAcceptance3EndTime());
                marketMaster.setSerialNumberPrefix(marketMasterRequest.getSerialNumberPrefix());
                marketMaster.setBoxWeight(marketMasterRequest.getBoxWeight());
                marketMaster.setLotWeight(marketMasterRequest.getLotWeight());
                marketMaster.setPaymentMode(marketMasterRequest.getPaymentMode());
                marketMaster.setStateId(marketMasterRequest.getStateId());
                marketMaster.setDistrictId(marketMasterRequest.getDistrictId());
                marketMaster.setTalukId(marketMasterRequest.getTalukId());
                marketMaster.setReelerMinimumBalance(marketMasterRequest.getReelerMinimumBalance());
                marketMaster.setMarketLatitude(marketMasterRequest.getMarketLatitude());
                marketMaster.setMarketLongitude(marketMasterRequest.getMarketLongitude());
                marketMaster.setRadius(marketMasterRequest.getRadius());
                marketMaster.setMarketMasterAddress(marketMasterRequest.getMarketMasterAddress());
                marketMaster.setClientId(marketMasterRequest.getClientId());
                marketMaster.setSnorkelRequestPath(marketMasterRequest.getSnorkelRequestPath());
                marketMaster.setSnorkelResponsePath(marketMasterRequest.getSnorkelResponsePath());
                marketMaster.setClientCode(marketMasterRequest.getClientCode());
                marketMaster.setWeighmentTripletGeneration(marketMasterRequest.getWeighmentTripletGeneration());
                marketMaster.setBidAmountFlag(marketMasterRequest.getBidAmountFlag());
                marketMaster.setDivisionMasterId(marketMasterRequest.getDivisionMasterId());
                marketMaster.setActive(true);
                MarketMaster marketMaster1 = marketMasterRepository.save(marketMaster);
                marketMasterResponse = mapper.marketMasterEntityToObject(marketMaster1, MarketMasterResponse.class);
                marketMasterResponse.setError(false);
            } else {
                marketMasterResponse.setError(true);
                marketMasterResponse.setError_description("Error occurred while fetching market");
                // throw new ValidationException("Error occurred while fetching village");
            }
        return marketMasterResponse;
    }

    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("marketMasterName");
        }
        if(searchWithSortRequest.getSortOrder() == null || searchWithSortRequest.getSortOrder().equals("")){
            searchWithSortRequest.setSortOrder("asc");
        }
        if(searchWithSortRequest.getPageNumber() == null || searchWithSortRequest.getPageNumber().equals("")){
            searchWithSortRequest.setPageNumber("0");
        }
        if(searchWithSortRequest.getPageSize() == null || searchWithSortRequest.getPageSize().equals("")){
            searchWithSortRequest.setPageSize("5");
        }
        Sort sort;
        if(searchWithSortRequest.getSortOrder().equals("asc")){
            sort = Sort.by(Sort.Direction.ASC, searchWithSortRequest.getSortColumn());
        }else{
            sort = Sort.by(Sort.Direction.DESC, searchWithSortRequest.getSortColumn());
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(searchWithSortRequest.getPageNumber()), Integer.parseInt(searchWithSortRequest.getPageSize()), sort);
        Page<MarketMasterDTO> marketMasterDTOS = marketMasterRepository.getSortedMarkets(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",marketMasterDTOS);
        return convertPageableDTOToMapResponse(marketMasterDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<MarketMasterDTO> activeMarkets) {
        Map<String, Object> response = new HashMap<>();

        List<MarketMasterResponse> marketMasterResponses = activeMarkets.getContent().stream()
                .map(marketMaster -> mapper.marketMasterDTOToObject(marketMaster,MarketMasterResponse.class)).collect(Collectors.toList());
        response.put("marketMaster",marketMasterResponses);
        response.put("currentPage", activeMarkets.getNumber());
        response.put("totalItems", activeMarkets.getTotalElements());
        response.put("totalPages", activeMarkets.getTotalPages());

        return response;
    }
}
