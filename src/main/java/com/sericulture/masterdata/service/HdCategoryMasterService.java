package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterRequest;
import com.sericulture.masterdata.model.api.hdCategoryMaster.HdCategoryMasterResponse;
import com.sericulture.masterdata.model.api.hdCategoryMaster.EditHdCategoryMasterRequest;
import com.sericulture.masterdata.model.dto.HdCategoryMasterDTO;
import com.sericulture.masterdata.model.dto.RaceMasterDTO;
import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import com.sericulture.masterdata.model.entity.RaceMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdCategoryMasterRepository;
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
public class HdCategoryMasterService {
    @Autowired
    HdCategoryMasterRepository hdCategoryMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdCategoryMasterResponse getHdCategoryMasterDetails(String hdCategoryName){
        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        HdCategoryMaster hdCategoryMaster = hdCategoryMasterRepository.findByHdCategoryNameAndActive(hdCategoryName, true);
        if(hdCategoryMaster==null){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Hd CategoryMaster not found");
        }else{
            hdCategoryMasterResponse = mapper.hdCategoryMasterEntityToObject(hdCategoryMaster, HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        }
        log.info("Entity is ",hdCategoryMaster);
        return hdCategoryMasterResponse;

    }

    @Transactional
    public HdCategoryMasterResponse insertHdCategoryMasterDetails(HdCategoryMasterRequest hdCategoryMasterRequest){
        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        HdCategoryMaster hdCategoryMaster = mapper.hdCategoryMasterObjectToEntity(hdCategoryMasterRequest, HdCategoryMaster.class);
        validator.validate(hdCategoryMaster);
        List<HdCategoryMaster> hdCategoryMasterList= hdCategoryMasterRepository.findByHdCategoryNameAndHdBoardCategoryId(hdCategoryMasterRequest.getHdCategoryName(),hdCategoryMasterRequest.getHdBoardCategoryId());
        if(!hdCategoryMasterList.isEmpty() && hdCategoryMasterList.stream().filter(HdCategoryMaster::getActive).findAny().isPresent()){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Category name already exist");
//        }
//        else if(!hdCategoryMasterList.isEmpty() && hdCategoryMasterList.stream().filter(Predicate.not(HdCategoryMaster::getActive)).findAny().isPresent()){
//            hdCategoryMasterResponse.setError(true);
//            hdCategoryMasterResponse.setError_description("Category name already exist with inactive state");
        }else {
            hdCategoryMasterResponse = mapper.hdCategoryMasterEntityToObject(hdCategoryMasterRepository.save(hdCategoryMaster), HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        }
        return hdCategoryMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdCategoryMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdCategoryMasterRepository.findByActiveOrderByHdCategoryIdAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdCategoryMasterRepository.findByActiveOrderByHdCategoryNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdCategoryMaster> activeHdCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdCategoryMasterResponse> hdCategoryMasterResponses = activeHdCategoryMasters.getContent().stream()
                .map(hdCategoryMaster -> mapper.hdCategoryMasterEntityToObject(hdCategoryMaster, HdCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdCategoryMaster",hdCategoryMasterResponses);
        response.put("currentPage", activeHdCategoryMasters.getNumber());
        response.put("totalItems", activeHdCategoryMasters.getTotalElements());
        response.put("totalPages", activeHdCategoryMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdCategoryMaster> activeHdCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdCategoryMasterResponse> hdCategoryMasterResponses = activeHdCategoryMasters.stream()
                .map(hdCategoryMaster -> mapper.hdCategoryMasterEntityToObject(hdCategoryMaster, HdCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdCategoryMaster",hdCategoryMasterResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdCategoryMasterDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(hdCategoryMasterRepository.getByActiveOrderByHdCategoryIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<HdCategoryMasterDTO> activeHdCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdCategoryMasterResponse> hdCategoryMasterResponses = activeHdCategoryMasters.getContent().stream()
                .map(hdCategoryMaster -> mapper.hdCategoryMasterDTOToObject(hdCategoryMaster,HdCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdCategoryMaster",hdCategoryMasterResponses);
        response.put("currentPage", activeHdCategoryMasters.getNumber());
        response.put("totalItems", activeHdCategoryMasters.getTotalElements());
        response.put("totalPages", activeHdCategoryMasters.getTotalPages());
        return response;
    }

    @Transactional
    public HdCategoryMasterResponse deleteHdCategoryMasterDetails(long id) {

        HdCategoryMasterResponse hdCategoryMasterResponse= new HdCategoryMasterResponse();
        HdCategoryMaster hdCategoryMaster = hdCategoryMasterRepository.findByHdCategoryIdAndActive(id, true);
        if (Objects.nonNull(hdCategoryMaster)) {
            hdCategoryMaster.setActive(false);
            hdCategoryMasterResponse = mapper.hdCategoryMasterEntityToObject(hdCategoryMasterRepository.save(hdCategoryMaster), HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        } else {
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdCategoryMasterResponse;
    }

    @Transactional
    public HdCategoryMasterResponse getById(int id){
        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        HdCategoryMaster hdCategoryMaster = hdCategoryMasterRepository.findByHdCategoryIdAndActive(id,true);
        if(hdCategoryMaster == null){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Invalid id");
        }else{
            hdCategoryMasterResponse =  mapper.hdCategoryMasterEntityToObject(hdCategoryMaster, HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        }
        log.info("Entity is ",hdCategoryMaster);
        return hdCategoryMasterResponse;
    }
    @Transactional
    public HdCategoryMasterResponse getByIdJoin(int id){
        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        HdCategoryMasterDTO hdCategoryMasterDTO = hdCategoryMasterRepository.getByHdCategoryIdAndActive(id,true);
        if(hdCategoryMasterDTO == null){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("Invalid id");
        } else {
            hdCategoryMasterResponse = mapper.hdCategoryMasterDTOToObject(hdCategoryMasterDTO, HdCategoryMasterResponse.class);
            hdCategoryMasterResponse.setError(false);
        }
        log.info("Entity is ", hdCategoryMasterDTO);
        return hdCategoryMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getByHdBoardCategoryId(int hdBoardCategoryId){
        Map<String, Object> response = new HashMap<>();
        List<HdCategoryMaster> hdCategoryMasterList = hdCategoryMasterRepository.findByHdBoardCategoryIdAndActive(hdBoardCategoryId,true);
        if(hdCategoryMasterList.isEmpty()){
            response.put("error","Error");
            response.put("error_description","Invalid id");
            return response;
        }else {
            log.info("Entity is ", hdCategoryMasterList);
            response = convertListToMapResponse(hdCategoryMasterList);
            return response;
        }
    }

    private Map<String, Object> convertListToMapResponse(List<HdCategoryMaster> hdCategoryMasterList) {
        Map<String, Object> response = new HashMap<>();
        List<HdCategoryMasterResponse> hdCategoryMasterResponses = hdCategoryMasterList.stream()
                .map(hdCategoryMaster -> mapper.hdCategoryMasterEntityToObject(hdCategoryMaster,HdCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdCategoryMaster",hdCategoryMasterResponses);
        response.put("totalItems", hdCategoryMasterResponses.size());
        return response;
    }




    @Transactional
    public HdCategoryMasterResponse updateHdCategoryMasterDetails(EditHdCategoryMasterRequest hdCategoryMasterRequest){

        HdCategoryMasterResponse hdCategoryMasterResponse = new HdCategoryMasterResponse();
        List<HdCategoryMaster> hdCategoryMasterList =  hdCategoryMasterRepository.findByActiveAndHdCategoryName(true,hdCategoryMasterRequest.getHdCategoryName());
        if(hdCategoryMasterList.size()>0){
            hdCategoryMasterResponse.setError(true);
            hdCategoryMasterResponse.setError_description("categoryMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HdCategoryMaster hdCategoryMaster= hdCategoryMasterRepository.findByHdCategoryIdAndActiveIn(hdCategoryMasterRequest.getHdCategoryId(), Set.of(true,false));
            if(Objects.nonNull(hdCategoryMaster)){
                hdCategoryMaster.setHdCategoryName(hdCategoryMasterRequest.getHdCategoryName());
                hdCategoryMaster.setHdBoardCategoryId(hdCategoryMasterRequest.getHdBoardCategoryId());

                hdCategoryMaster.setActive(true);
                HdCategoryMaster hdCategoryMaster1 = hdCategoryMasterRepository.save(hdCategoryMaster);
                hdCategoryMasterResponse = mapper.hdCategoryMasterEntityToObject(hdCategoryMaster1, HdCategoryMasterResponse.class);
                hdCategoryMasterResponse.setError(false);
            } else {
                hdCategoryMasterResponse.setError(true);
                hdCategoryMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdCategoryMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("hdCategoryName");
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
        Page<HdCategoryMasterDTO> hdCategoryMasterDTOS = hdCategoryMasterRepository.getSortedHdCategoryMasters(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",hdCategoryMasterDTOS);
        return convertPageableDTOToMapResponse(hdCategoryMasterDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<HdCategoryMasterDTO> activeHdCategoryMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdCategoryMasterResponse> hdCategoryMasterResponses = activeHdCategoryMasters.getContent().stream()
                .map(hdCategoryMaster -> mapper.hdCategoryMasterDTOToObject(hdCategoryMaster,HdCategoryMasterResponse.class)).collect(Collectors.toList());
        response.put("hdCategoryMaster",hdCategoryMasterResponses);
        response.put("currentPage", activeHdCategoryMasters.getNumber());
        response.put("totalItems", activeHdCategoryMasters.getTotalElements());
        response.put("totalPages", activeHdCategoryMasters.getTotalPages());

        return response;
    }
}
