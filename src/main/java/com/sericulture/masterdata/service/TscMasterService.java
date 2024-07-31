package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.trProgramMaster.EditTrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterResponse;
import com.sericulture.masterdata.model.api.tscMaster.EditTscMasterRequest;
import com.sericulture.masterdata.model.api.tscMaster.TscMasterRequest;
import com.sericulture.masterdata.model.api.tscMaster.TscMasterResponse;
import com.sericulture.masterdata.model.api.useMaster.UserMasterResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.TscMasterDTO;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.dto.VillageDTO;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import com.sericulture.masterdata.model.entity.TscMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrProgramMasterRespository;
import com.sericulture.masterdata.repository.TscMasterRepository;
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
public class TscMasterService {

    @Autowired
    TscMasterRepository tscMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public TscMasterResponse insertTscMasterDetails(TscMasterRequest tscMasterRequest){
        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        TscMaster tscMaster = mapper.tscMasterObjectToEntity(tscMasterRequest,TscMaster.class);
        validator.validate(tscMaster);
        List<TscMaster> tscMasterList = tscMasterRepository.findByActiveAndNameAndNameInKannada(true,tscMasterRequest.getName(), tscMasterRequest.getNameInKannada());
        if(!tscMasterList.isEmpty() && tscMasterList.stream().filter( TscMaster::getActive).findAny().isPresent()){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("TscMaster name already exist");
        }
        else if(! tscMasterList.isEmpty() && tscMasterList.stream().filter(Predicate.not( TscMaster::getActive)).findAny().isPresent()){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("TscMaster name already exist with inactive state");
        }else {
            tscMasterResponse  = mapper.tscMasterEntityToObject( tscMasterRepository.save(tscMaster), TscMasterResponse.class);
            tscMasterResponse.setError(false);
        }
        return tscMasterResponse;
    }

    public Map<String,Object> getPaginatedTscMasterDetails(final Pageable pageable){
        return convertToMapResponse(tscMasterRepository.findByActiveOrderByNameAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<TscMaster> activeTscMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TscMasterResponse> tscMasterResponses= activeTscMasters.getContent().stream()
                .map(tscMaster -> mapper.tscMasterEntityToObject(tscMaster,TscMasterResponse.class)).collect(Collectors.toList());
        response.put("tscMaster",tscMasterResponses);
        response.put("currentPage", activeTscMasters.getNumber());
        response.put("totalItems", activeTscMasters.getTotalElements());
        response.put("totalPages", activeTscMasters.getTotalPages());

        return response;
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tscMasterRepository.findByActive(isActive));
    }


    private Map<String, Object> convertListEntityToMapResponse(final List<TscMaster> activeTscMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TscMasterResponse> tscMasterResponses= activeTscMasters.stream()
                .map(tscMaster -> mapper.tscMasterEntityToObject(tscMaster,TscMasterResponse.class)).collect(Collectors.toList());
        response.put("tscMaster",tscMasterResponses);
        return response;
    }

    @Transactional
    public TscMasterResponse deleteTscMasterDetails(long id) {

        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        TscMaster tscMaster= tscMasterRepository.findByTscMasterIdAndActive(id, true);
        if (Objects.nonNull(tscMaster)) {
            tscMaster.setActive(false);
            tscMasterResponse= mapper.tscMasterEntityToObject(tscMasterRepository.save(tscMaster), TscMasterResponse.class);
            tscMasterResponse.setError(false);
        } else {
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tscMasterResponse;
    }

    public TscMasterResponse getById(int id){
        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        TscMaster tscMaster= tscMasterRepository.findByTscMasterIdAndActive(id, true);
        if(tscMaster== null){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("Invalid id");
        }else{
            tscMasterResponse =  mapper.tscMasterEntityToObject(tscMaster, TscMasterResponse.class);
            tscMasterResponse.setError(false);
        }
        log.info("Entity is ",tscMaster);
        return tscMasterResponse;
    }

    public TscMasterResponse getByIdJoin(int id) {
        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        TscMasterDTO tscMasterDTO = tscMasterRepository.getByTscMasterIdAndActive(id, true);
        if (tscMasterDTO == null) {
            // throw new ValidationException("Invalid Id");
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("Invalid id");
        } else {
            tscMasterResponse = mapper.tscMasterDTOToObject(tscMasterDTO, TscMasterResponse.class);
            tscMasterResponse.setError(false);
        }
        log.info("Entity is ", tscMasterDTO);
        return tscMasterResponse;
    }


    public Map<String,Object> getPaginatedTscMasterDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tscMasterRepository.getByActiveOrderByTscMasterIdAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public  Map<String, Object> getByAndDistrictIdAndTalukId(Long districtId,Long talukId) {
        Map<String, Object> response = new HashMap<>();
        List<TscMasterDTO> tscMasterDTOS = tscMasterRepository.getByDistrictIdAndTalukIdAndActive(districtId,talukId,true);
        if(tscMasterDTOS.size()<=0){
            response.put("error","Error");
            response.put("error_description","No records found");
        }else {
            log.info("Entity is ", tscMasterDTOS);
            response = convertDTOToMapResponse(tscMasterDTOS);
        }
        return response;
    }

    private Map<String, Object> convertDTOToMapResponse(List<TscMasterDTO> tscMasterDTOS) {
        Map<String, Object> response = new HashMap<>();
        List<TscMasterResponse> tscMasterResponses = tscMasterDTOS.stream()
                .map(tscMasterDTO -> mapper.tscMasterDTOToObject(tscMasterDTO,TscMasterResponse.class)).collect(Collectors.toList());
        response.put("tscMaster",tscMasterResponses);
        response.put("totalItems", tscMasterDTOS.size());
        return response;
    }
    private Map<String, Object> convertDTOToMapResponse(final Page<TscMasterDTO> activeTscMasters) {
        Map<String, Object> response = new HashMap<>();

        List<TscMasterResponse> tscMasterResponses= activeTscMasters.getContent().stream()
                .map(tscMaster -> mapper.tscMasterDTOToObject(tscMaster,TscMasterResponse.class)).collect(Collectors.toList());
        response.put("tscMaster",tscMasterResponses);
        response.put("currentPage", activeTscMasters.getNumber());
        response.put("totalItems", activeTscMasters.getTotalElements());
        response.put("totalPages", activeTscMasters.getTotalPages());

        return response;
    }


    @Transactional
    public TscMasterResponse updateTscMastersDetails(EditTscMasterRequest tscMasterRequest){

        TscMasterResponse tscMasterResponse = new TscMasterResponse();
        List<TscMaster> tscMasterList = tscMasterRepository. findByActiveAndNameAndNameInKannadaAndTscMasterIdIsNot(true,tscMasterRequest.getName(), tscMasterRequest.getNameInKannada(),tscMasterRequest.getTscMasterId());
        if(tscMasterList.size()>0){
            tscMasterResponse.setError(true);
            tscMasterResponse.setError_description("TscMaster exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            TscMaster tscMaster = tscMasterRepository.findByTscMasterIdAndActiveIn(tscMasterRequest.getTscMasterId(), Set.of(true,false));
            if(Objects.nonNull(tscMaster)){
                tscMaster.setName( tscMasterRequest.getName());
                tscMaster.setNameInKannada( tscMasterRequest.getNameInKannada());
                tscMaster.setDistrictId(tscMasterRequest.getDistrictId());
                tscMaster.setTalukId(tscMasterRequest.getTalukId());
                tscMaster.setAddress(tscMasterRequest.getAddress());
                tscMaster.setActive(true);
                TscMaster tscMaster1= tscMasterRepository.save(tscMaster);
                tscMasterResponse = mapper.tscMasterEntityToObject(tscMaster1, TscMasterResponse.class);
                tscMasterResponse.setError(false);
            } else {
                tscMasterResponse.setError(true);
                tscMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return tscMasterResponse;
    }
}
