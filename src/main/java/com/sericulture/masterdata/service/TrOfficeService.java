package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.trModeMaster.EditTrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterResponse;
import com.sericulture.masterdata.model.api.trOffice.EditTrOfficeRequest;
import com.sericulture.masterdata.model.api.trOffice.TrOfficeRequest;
import com.sericulture.masterdata.model.api.trOffice.TrOfficeResponse;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import com.sericulture.masterdata.model.entity.TrOffice;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrModeMasterRepository;
import com.sericulture.masterdata.repository.TrOfficeRepository;
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
public class TrOfficeService {

    @Autowired
    TrOfficeRepository trOfficeRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TrOfficeResponse insertTrOfficeDetails(TrOfficeRequest trOfficeRequest){
        TrOfficeResponse trOfficeResponse = new TrOfficeResponse();
        TrOffice trOffice = mapper.trOfficeObjectToEntity(trOfficeRequest,TrOffice.class);
        validator.validate(trOffice);
        List<TrOffice> trOfficeList= trOfficeRepository.findByTrOfficeName(trOfficeRequest.getTrOfficeName());

        if(!trOfficeList.isEmpty() && trOfficeList.stream().filter(TrOffice::getActive).findAny().isPresent()){
            trOfficeResponse.setError(true);
            trOfficeResponse.setError_description("TrOffice name already exist");
        }
        else if(!trOfficeList.isEmpty() && trOfficeList.stream().filter(Predicate.not(TrOffice::getActive)).findAny().isPresent()){
            trOfficeResponse.setError(true);
            trOfficeResponse.setError_description("trOffice name already exist with inactive state");
        }else {
            trOfficeResponse = mapper.trOfficeEntityToObject(trOfficeRepository.save(trOffice), TrOfficeResponse.class);
            trOfficeResponse.setError(false);
        }
        return trOfficeResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrOfficeDetails(final Pageable pageable){
        return convertToMapResponse(trOfficeRepository.findByActiveOrderByTrOfficeNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trOfficeRepository.findByActiveOrderByTrOfficeNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrOffice> activeTrOffices) {
        Map<String, Object> response = new HashMap<>();

        List<TrOfficeResponse> trOfficeResponses = activeTrOffices.getContent().stream()
                .map(trOffice -> mapper.trOfficeEntityToObject(trOffice,TrOfficeResponse.class)).collect(Collectors.toList());
        response.put("trOffice",trOfficeResponses);
        response.put("currentPage", activeTrOffices.getNumber());
        response.put("totalItems", activeTrOffices.getTotalElements());
        response.put("totalPages", activeTrOffices.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrOffice> activeTrOffices) {
        Map<String, Object> response = new HashMap<>();

        List<TrOfficeResponse> trOfficeResponses = activeTrOffices.stream()
                .map(trOffice -> mapper.trOfficeEntityToObject(trOffice,TrOfficeResponse.class)).collect(Collectors.toList());
        response.put("trOffice",trOfficeResponses);
        return response;
    }

    @Transactional
    public TrOfficeResponse deleteTrOfficeDetails(long id) {

        TrOfficeResponse trOfficeResponse= new TrOfficeResponse();
        TrOffice trOffice = trOfficeRepository.findByTrOfficeIdAndActive(id, true);
        if (Objects.nonNull(trOffice)) {
            trOffice.setActive(false);
            trOfficeResponse = mapper.trOfficeEntityToObject(trOfficeRepository.save(trOffice), TrOfficeResponse.class);
            trOfficeResponse.setError(false);
        } else {
            trOfficeResponse.setError(true);
            trOfficeResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trOfficeResponse;
    }

    @Transactional
    public TrOfficeResponse getById(int id){
        TrOfficeResponse trOfficeResponse = new TrOfficeResponse();
        TrOffice trOffice = trOfficeRepository.findByTrOfficeIdAndActive(id,true);
        if(trOffice == null){
            trOfficeResponse.setError(true);
            trOfficeResponse.setError_description("Invalid id");
        }else{
            trOfficeResponse =  mapper.trOfficeEntityToObject(trOffice,TrOfficeResponse.class);
            trOfficeResponse.setError(false);
        }
        log.info("Entity is ",trOffice);
        return trOfficeResponse;
    }

    @Transactional
    public TrOfficeResponse updateTrOfficeDetails(EditTrOfficeRequest trOfficeRequest){

        TrOfficeResponse trOfficeResponse = new TrOfficeResponse();
        List<TrOffice> trOfficeList = trOfficeRepository.findByTrOfficeName(trOfficeRequest.getTrOfficeName());
        if(trOfficeList.size()>0){
            trOfficeResponse.setError(true);
            trOfficeResponse.setError_description("TrOffice already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TrOffice trOffice= trOfficeRepository.findByTrOfficeIdAndActiveIn(trOfficeRequest.getTrOfficeId(), Set.of(true,false));
            if(Objects.nonNull(trOffice)){
                trOffice.setTrOfficeName(trOfficeRequest.getTrOfficeName());
                trOffice.setActive(true);
                TrOffice trOffice1 = trOfficeRepository.save(trOffice);
                trOfficeResponse = mapper.trOfficeEntityToObject(trOffice1, TrOfficeResponse.class);
                trOfficeResponse.setError(false);
            } else {
                trOfficeResponse.setError(true);
                trOfficeResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return trOfficeResponse;
    }
}
