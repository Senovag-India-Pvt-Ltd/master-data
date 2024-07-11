package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.rendittaMaster.EditRendittaMasterRequest;
import com.sericulture.masterdata.model.api.rendittaMaster.RendittaMasterRequest;
import com.sericulture.masterdata.model.api.rendittaMaster.RendittaMasterResponse;
import com.sericulture.masterdata.model.dto.RendittaMasterDTO;
import com.sericulture.masterdata.model.entity.RendittaMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.RendittaMasterRepository;
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
public class RendittaMasterService {
    @Autowired
    RendittaMasterRepository rendittaMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public RendittaMasterResponse insertRendittaMasterDetails(RendittaMasterRequest rendittaMasterRequest){
        RendittaMasterResponse rendittaMasterResponse = new RendittaMasterResponse();
        RendittaMaster rendittaMaster = mapper.rendittaMasterObjectToEntity(rendittaMasterRequest, RendittaMaster.class);
        validator.validate(rendittaMaster);
        List<RendittaMaster> rendittaMasterList = rendittaMasterRepository.findByRaceMasterId(rendittaMasterRequest.getRaceMasterId());
        if(!rendittaMasterList.isEmpty() && rendittaMasterList.stream().filter(RendittaMaster::getActive).findAny().isPresent()){
            rendittaMasterResponse.setError(true);
            rendittaMasterResponse.setError_description("RendittaMaster already exist");
        }
        else if(!rendittaMasterList.isEmpty() && rendittaMasterList.stream().filter(Predicate.not(RendittaMaster::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            rendittaMasterResponse.setError(true);
            rendittaMasterResponse.setError_description("RendittaMaster already exist with inactive state");
        }else {
            rendittaMasterResponse = mapper.rendittaMasterEntityToObject(rendittaMasterRepository.save(rendittaMaster), RendittaMasterResponse.class);
            rendittaMasterResponse.setError(false);
        }
        return rendittaMasterResponse;
    }

    public Map<String,Object> getRendittaMasterDetails(final Pageable pageable){
        return convertToMapResponse(rendittaMasterRepository.findByActiveOrderByRendittaMasterIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(rendittaMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<RendittaMaster> activeRendittaMaster) {
        Map<String, Object> response = new HashMap<>();

        List<RendittaMasterResponse> rendittaMasterResponses= activeRendittaMaster.getContent().stream()
                .map(rendittaMaster -> mapper.rendittaMasterEntityToObject(rendittaMaster, RendittaMasterResponse.class)).collect(Collectors.toList());
        response.put("rendittaMaster",rendittaMasterResponses);
        response.put("currentPage", activeRendittaMaster.getNumber());
        response.put("totalItems", activeRendittaMaster.getTotalElements());
        response.put("totalPages", activeRendittaMaster.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<RendittaMaster> activeRendittaMaster) {
        Map<String, Object> response = new HashMap<>();

        List<RendittaMasterResponse> rendittaMasterResponses = activeRendittaMaster.stream()
                .map(rendittaMaster  -> mapper.rendittaMasterEntityToObject(rendittaMaster, RendittaMasterResponse.class)).collect(Collectors.toList());
        response.put("rendittaMaster",rendittaMasterResponses);
        return response;
    }

    public Map<String,Object> getPaginatedRendittaMasterWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(rendittaMasterRepository.getByActiveOrderByRendittaMasterIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<RendittaMasterDTO> activeRendittaMaster) {
        Map<String, Object> response = new HashMap<>();

        List<RendittaMasterResponse> rendittaMasterResponses= activeRendittaMaster.getContent().stream()
                .map(rendittaMaster -> mapper.rendittaMasterDTOToObject(rendittaMaster,RendittaMasterResponse.class)).collect(Collectors.toList());
        response.put("rendittaMaster",rendittaMasterResponses);
        response.put("currentPage", activeRendittaMaster.getNumber());
        response.put("totalItems", activeRendittaMaster.getTotalElements());
        response.put("totalPages", activeRendittaMaster.getTotalPages());
        return response;
    }


    @Transactional
    public RendittaMasterResponse deleteRendittaMasterDetails(long id) {
        RendittaMasterResponse rendittaMasterResponse= new RendittaMasterResponse();
        RendittaMaster rendittaMaster = rendittaMasterRepository.findByRendittaMasterIdAndActive(id, true);
        if (Objects.nonNull(rendittaMaster)) {
            rendittaMaster.setActive(false);
            rendittaMasterResponse= mapper.rendittaMasterEntityToObject(rendittaMasterRepository.save(rendittaMaster),RendittaMasterResponse.class);
            rendittaMasterResponse.setError(false);
        } else {
            rendittaMasterResponse.setError(true);
            rendittaMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return rendittaMasterResponse;
    }

    public RendittaMasterResponse getById(int id){
        RendittaMasterResponse rendittaMasterResponse = new RendittaMasterResponse();
        RendittaMaster rendittaMaster = rendittaMasterRepository.findByRendittaMasterIdAndActive(id,true);


        if(rendittaMaster == null){
            rendittaMasterResponse.setError(true);
            rendittaMasterResponse.setError_description("Invalid id");
        }else{
            rendittaMasterResponse =  mapper.rendittaMasterEntityToObject(rendittaMaster, RendittaMasterResponse.class);
            rendittaMasterResponse.setError(false);
        }
        log.info("Entity is ",rendittaMaster);
        return rendittaMasterResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<RendittaMasterDTO> rendittaMasterList = rendittaMasterRepository.getRendittaMaster(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(rendittaMasterList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", rendittaMasterList);
//            response = convertListToMapResponse(rendittaMasterList);
//            return response;
//        }
//    }

    private Map<String, Object> convertListToMapResponse(List<RendittaMasterDTO> rendittaMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<RendittaMasterResponse> rendittaMasterResponses = rendittaMasterList.stream()
                .map(rendittaMaster -> mapper.rendittaMasterDTOToObject(rendittaMaster,RendittaMasterResponse.class)).collect(Collectors.toList());
        response.put("rendittaMaster",rendittaMasterResponses);
        response.put("totalItems", rendittaMasterList.size());
        return response;
    }

    public RendittaMasterResponse getByIdJoin(int id){
        RendittaMasterResponse rendittaMasterResponse = new RendittaMasterResponse();
        RendittaMasterDTO rendittaMasterDTO = rendittaMasterRepository.getByRendittaMasterIdAndActive(id,true);
        if(rendittaMasterDTO == null){
            rendittaMasterResponse.setError(true);
            rendittaMasterResponse.setError_description("Invalid id");
        } else {
            rendittaMasterResponse = mapper.rendittaMasterDTOToObject(rendittaMasterDTO, RendittaMasterResponse.class);
            rendittaMasterResponse.setError(false);
        }
        log.info("Entity is ", rendittaMasterDTO);
        return rendittaMasterResponse;
    }

    @Transactional
    public RendittaMasterResponse updateRendittaMasterDetails(EditRendittaMasterRequest rendittaMasterRequest) {
        RendittaMasterResponse rendittaMasterResponse = new RendittaMasterResponse();
        List<RendittaMaster> rendittaMasterList = rendittaMasterRepository.findByRaceMasterIdAndRendittaMasterIdIsNot(rendittaMasterRequest.getRaceMasterId(), rendittaMasterRequest.getRendittaMasterId());
        if (rendittaMasterList.size() > 0) {
            rendittaMasterResponse.setError(true);
            rendittaMasterResponse.setError_description("RendittaMaster exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            RendittaMaster rendittaMaster = rendittaMasterRepository.findByRendittaMasterIdAndActiveIn(rendittaMasterRequest.getRendittaMasterId(), Set.of(true, false));
            if (Objects.nonNull(rendittaMaster)) {
                rendittaMaster.setRaceMasterId(rendittaMasterRequest.getRaceMasterId());
                rendittaMaster.setValue(rendittaMasterRequest.getValue());

                rendittaMaster.setActive(true);
                RendittaMaster rendittaMaster1 = rendittaMasterRepository.save(rendittaMaster);
                rendittaMasterResponse = mapper.rendittaMasterEntityToObject(rendittaMaster1, RendittaMasterResponse.class);
                rendittaMasterResponse.setError(false);
            } else {
                rendittaMasterResponse.setError(true);
                rendittaMasterResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return rendittaMasterResponse;
    }

    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("raceMaster.raceMasterName");
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
        Page<RendittaMasterDTO> rendittaMasterDTOS = rendittaMasterRepository.getSortedRendittaMaster(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",rendittaMasterDTOS);
        return convertPageableDTOToMapResponse(rendittaMasterDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<RendittaMasterDTO> activeRendittaMaster) {
        Map<String, Object> response = new HashMap<>();

        List<RendittaMasterResponse> rendittaMasterResponses = activeRendittaMaster.getContent().stream()
                .map(rendittaMaster -> mapper.rendittaMasterDTOToObject(rendittaMaster,RendittaMasterResponse.class)).collect(Collectors.toList());
        response.put("rendittaMaster",rendittaMasterResponses);
        response.put("currentPage", activeRendittaMaster.getNumber());
        response.put("totalItems", activeRendittaMaster.getTotalElements());
        response.put("totalPages", activeRendittaMaster.getTotalPages());

        return response;
    }
}
