package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.disinfectantMaster.DisinfectantMasterRequest;
import com.sericulture.masterdata.model.api.disinfectantMaster.DisinfectantMasterResponse;
import com.sericulture.masterdata.model.api.disinfectantMaster.EditDisinfectantMasterRequest;
import com.sericulture.masterdata.model.api.lineNameMaster.EditLineNameMasterRequest;
import com.sericulture.masterdata.model.api.lineNameMaster.LineNameMasterRequest;
import com.sericulture.masterdata.model.api.lineNameMaster.LineNameMasterResponse;
import com.sericulture.masterdata.model.entity.DisinfectantMaster;
import com.sericulture.masterdata.model.entity.LineNameMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DisinfectantMasterRepository;
import com.sericulture.masterdata.repository.LineNameMasterRepository;
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
public class LineNameMasterService {

    @Autowired
    LineNameMasterRepository lineNameMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public LineNameMasterResponse getLineNameMasterDetails(String disinfectantMasterName){
        LineNameMasterResponse lineNameMasterResponse = new LineNameMasterResponse();
        LineNameMaster lineNameMaster = lineNameMasterRepository.findByLineNameAndActive(disinfectantMasterName, true);
        if(lineNameMaster==null){
            lineNameMasterResponse.setError(true);
            lineNameMasterResponse.setError_description("LineName  not Found");
        }else{
            lineNameMasterResponse = mapper.lineNameMasterEntityToObject(lineNameMaster, LineNameMasterResponse.class);
            lineNameMasterResponse.setError(false);
        }
        log.info("Entity is ",lineNameMaster);
        return lineNameMasterResponse;

    }

    @Transactional
    public LineNameMasterResponse insertLineNameMasterDetails(LineNameMasterRequest lineNameMasterRequest){
        LineNameMasterResponse lineNameMasterResponse = new LineNameMasterResponse();
        LineNameMaster lineNameMaster = mapper.lineNameMasterObjectToEntity(lineNameMasterRequest,LineNameMaster.class);
        validator.validate(lineNameMaster);
        List<LineNameMaster> lineNameMasterList= lineNameMasterRepository.findByLineNameAndLineNameInKannada(lineNameMasterRequest.getLineName(),lineNameMasterRequest.getLineNameInKannada());
        if(!lineNameMasterList.isEmpty() && lineNameMasterList.stream().filter(LineNameMaster::getActive).findAny().isPresent()){
            lineNameMasterResponse.setError(true);
            lineNameMasterResponse.setError_description("lineNameMaster name already exist");
        }
        else if(!lineNameMasterList.isEmpty() && lineNameMasterList.stream().filter(Predicate.not(LineNameMaster::getActive)).findAny().isPresent()){
            lineNameMasterResponse.setError(true);
            lineNameMasterResponse.setError_description("lineNameMaster name already exist with inactive state");
        }else {
            lineNameMasterResponse = mapper.lineNameMasterEntityToObject(lineNameMasterRepository.save(lineNameMaster), LineNameMasterResponse.class);
            lineNameMasterResponse.setError(false);
        }
        return lineNameMasterResponse;
    }

    public Map<String,Object> getPaginatedLineNameMasterDetails(final Pageable pageable){
        return convertToMapResponse(lineNameMasterRepository.findByActiveOrderByLineNameAsc( true,pageable ));

    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(lineNameMasterRepository.findByActiveOrderByLineNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<LineNameMaster> activeLineNameMasters) {
        Map<String, Object> response = new HashMap<>();

        List<LineNameMasterResponse> lineNameMasterResponses = activeLineNameMasters.getContent().stream()
                .map(lineNameMaster -> mapper.lineNameMasterEntityToObject(lineNameMaster,LineNameMasterResponse.class)).collect(Collectors.toList());
        response.put("lineNameMaster",lineNameMasterResponses);
        response.put("currentPage", activeLineNameMasters.getNumber());
        response.put("totalItems", activeLineNameMasters.getTotalElements());
        response.put("totalPages", activeLineNameMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<LineNameMaster> activeLineNameMasters) {
        Map<String, Object> response = new HashMap<>();

        List<LineNameMasterResponse> lineNameMasterResponses = activeLineNameMasters.stream()
                .map(disinfectantMaster -> mapper.lineNameMasterEntityToObject(disinfectantMaster,LineNameMasterResponse.class)).collect(Collectors.toList());
        response.put("lineNameMaster",lineNameMasterResponses);
        return response;
    }

    @Transactional
    public LineNameMasterResponse deleteLineNameMasterDetails(long id) {

        LineNameMasterResponse lineNameMasterResponse= new LineNameMasterResponse();
        LineNameMaster lineNameMaster = lineNameMasterRepository.findByLineNameIdAndActive(id, true);
        if (Objects.nonNull(lineNameMaster)) {
            lineNameMaster.setActive(false);
            lineNameMasterResponse = mapper.lineNameMasterEntityToObject(lineNameMasterRepository.save(lineNameMaster), LineNameMasterResponse.class);
            lineNameMasterResponse.setError(false);
        } else {
            lineNameMasterResponse.setError(true);
            lineNameMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return lineNameMasterResponse;
    }

    public LineNameMasterResponse getById(int id){
        LineNameMasterResponse lineNameMasterResponse = new LineNameMasterResponse();
        LineNameMaster lineNameMaster = lineNameMasterRepository.findByLineNameIdAndActive(id,true);
        if(lineNameMaster == null){
            lineNameMasterResponse.setError(true);
            lineNameMasterResponse.setError_description("Invalid id");
        }else{
            lineNameMasterResponse =  mapper.lineNameMasterEntityToObject(lineNameMaster,LineNameMasterResponse.class);
            lineNameMasterResponse.setError(false);
        }
        log.info("Entity is ",lineNameMaster);
        return lineNameMasterResponse;
    }

    @Transactional
    public LineNameMasterResponse updateLineNameMasterDetails(EditLineNameMasterRequest lineNameMasterRequest){

        LineNameMasterResponse lineNameMasterResponse = new LineNameMasterResponse();
        List<LineNameMaster> lineNameMasterList = lineNameMasterRepository.findByLineNameAndLineNameInKannadaAndLineNameIdIsNot(lineNameMasterRequest.getLineName(),lineNameMasterRequest.getLineNameInKannada(),lineNameMasterRequest.getLineNameId());
        if(lineNameMasterList.size()>0){
            lineNameMasterResponse.setError(true);
            lineNameMasterResponse.setError_description("lineNameMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            LineNameMaster lineNameMaster= lineNameMasterRepository.findByLineNameIdAndActiveIn(lineNameMasterRequest.getLineNameId(), Set.of(true,false));
            if(Objects.nonNull(lineNameMaster)){
                lineNameMaster.setLineName(lineNameMasterRequest.getLineName());
                lineNameMaster.setLineNameInKannada(lineNameMasterRequest.getLineNameInKannada());
                lineNameMaster.setLineCode(lineNameMasterRequest.getLineCode());
                lineNameMaster.setLineNameRepresentation(lineNameMasterRequest.getLineNameRepresentation());
                lineNameMaster.setActive(true);
                LineNameMaster lineNameMaster1 = lineNameMasterRepository.save(lineNameMaster);
                lineNameMasterResponse = mapper.lineNameMasterEntityToObject(lineNameMaster1, LineNameMasterResponse.class);
                lineNameMasterResponse.setError(false);
            } else {
                lineNameMasterResponse.setError(true);
                lineNameMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return lineNameMasterResponse;
    }
}
