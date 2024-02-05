package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.hdQuestionMaster.EditHdQuestionMasterRequest;
import com.sericulture.masterdata.model.api.hdQuestionMaster.HdQuestionMasterRequest;
import com.sericulture.masterdata.model.api.hdQuestionMaster.HdQuestionMasterResponse;
import com.sericulture.masterdata.model.api.hdStatusMaster.EditHdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterRequest;
import com.sericulture.masterdata.model.api.hdStatusMaster.HdStatusMasterResponse;
import com.sericulture.masterdata.model.api.trSchedule.TrScheduleResponse;
import com.sericulture.masterdata.model.dto.HdQuestionMasterDTO;
import com.sericulture.masterdata.model.dto.TrScheduleDTO;
import com.sericulture.masterdata.model.entity.HdQuestionMaster;
import com.sericulture.masterdata.model.entity.HdStatusMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.HdQuestionMasterRepository;
import com.sericulture.masterdata.repository.HdStatusMasterRepository;
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
public class HdQuestionMasterService {
    @Autowired
    HdQuestionMasterRepository hdQuestionMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public HdQuestionMasterResponse getHdQuestionMasterDetails(String hdQuestionName){
        HdQuestionMasterResponse hdQuestionMasterResponse = new HdQuestionMasterResponse();
        HdQuestionMaster hdQuestionMaster = hdQuestionMasterRepository.findByHdQuestionNameAndActive(hdQuestionName, true);
        if(hdQuestionMaster==null){
            hdQuestionMasterResponse.setError(true);
            hdQuestionMasterResponse.setError_description("Question not found");
        }else{
            hdQuestionMasterResponse = mapper.hdQuestionMasterEntityToObject(hdQuestionMaster, HdQuestionMasterResponse.class);
            hdQuestionMasterResponse.setError(false);
        }
        log.info("Entity is ",hdQuestionMaster);
        return hdQuestionMasterResponse;

    }

    @Transactional
    public HdQuestionMasterResponse insertHdQuestionMasterDetails(HdQuestionMasterRequest hdQuestionMasterRequest){
        HdQuestionMasterResponse hdQuestionMasterResponse = new HdQuestionMasterResponse();
        HdQuestionMaster hdQuestionMaster = mapper.hdQuestionMasterObjectToEntity(hdQuestionMasterRequest, HdQuestionMaster.class);
        validator.validate(hdQuestionMaster);
        List<HdQuestionMaster> hdQuestionMasterList= hdQuestionMasterRepository.findByHdQuestionName(hdQuestionMasterRequest.getHdQuestionName());
        if(!hdQuestionMasterList.isEmpty() && hdQuestionMasterList.stream().filter(HdQuestionMaster::getActive).findAny().isPresent()){
            hdQuestionMasterResponse.setError(true);
            hdQuestionMasterResponse.setError_description("Question name already exist");
        }
        else if(!hdQuestionMasterList.isEmpty() && hdQuestionMasterList.stream().filter(Predicate.not(HdQuestionMaster::getActive)).findAny().isPresent()){
            hdQuestionMasterResponse.setError(true);
            hdQuestionMasterResponse.setError_description("Question name already exist with inactive state");
        }else {
            hdQuestionMasterResponse = mapper.hdQuestionMasterEntityToObject(hdQuestionMasterRepository.save(hdQuestionMaster), HdQuestionMasterResponse.class);
            hdQuestionMasterResponse.setError(false);
        }
        return hdQuestionMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedHdQuestionMasterDetails(final Pageable pageable){
        return convertToMapResponse(hdQuestionMasterRepository.findByActiveOrderByHdQuestionNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(hdQuestionMasterRepository.findByActiveOrderByHdQuestionNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<HdQuestionMaster> activeHdQuestionMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdQuestionMasterResponse> hdQuestionMasterResponses = activeHdQuestionMasters.getContent().stream()
                .map(hdQuestionMaster -> mapper.hdQuestionMasterEntityToObject(hdQuestionMaster, HdQuestionMasterResponse.class)).collect(Collectors.toList());
        response.put("hdQuestionMaster",hdQuestionMasterResponses);
        response.put("currentPage", activeHdQuestionMasters.getNumber());
        response.put("totalItems", activeHdQuestionMasters.getTotalElements());
        response.put("totalPages", activeHdQuestionMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<HdQuestionMaster> activeHdQuestionMasters) {
        Map<String, Object> response = new HashMap<>();

        List<HdQuestionMasterResponse> hdQuestionMasterResponses = activeHdQuestionMasters.stream()
                .map(hdQuestionMaster -> mapper.hdQuestionMasterEntityToObject(hdQuestionMaster, HdQuestionMasterResponse.class)).collect(Collectors.toList());
        response.put("hdQuestionMaster",hdQuestionMasterResponses);
        return response;
    }

    @Transactional
    public HdQuestionMasterResponse deleteHdQuestionMasterDetails(long id) {

        HdQuestionMasterResponse hdQuestionMasterResponse= new HdQuestionMasterResponse();
        HdQuestionMaster hdQuestionMaster = hdQuestionMasterRepository.findByHdQuestionIdAndActive(id, true);
        if (Objects.nonNull(hdQuestionMaster)) {
            hdQuestionMaster.setActive(false);
            hdQuestionMasterResponse = mapper.hdQuestionMasterEntityToObject(hdQuestionMasterRepository.save(hdQuestionMaster), HdQuestionMasterResponse.class);
            hdQuestionMasterResponse.setError(false);
        } else {
            hdQuestionMasterResponse.setError(true);
            hdQuestionMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return hdQuestionMasterResponse;
    }

    @Transactional
    public HdQuestionMasterResponse getById(int id){
        HdQuestionMasterResponse hdQuestionMasterResponse = new HdQuestionMasterResponse();
        HdQuestionMaster hdQuestionMaster = hdQuestionMasterRepository.findByHdQuestionIdAndActive(id,true);
        if(hdQuestionMaster == null){
            hdQuestionMasterResponse.setError(true);
            hdQuestionMasterResponse.setError_description("Invalid id");
        }else{
            hdQuestionMasterResponse =  mapper.hdQuestionMasterEntityToObject(hdQuestionMaster, HdQuestionMasterResponse.class);
            hdQuestionMasterResponse.setError(false);
        }
        log.info("Entity is ",hdQuestionMaster);
        return hdQuestionMasterResponse;
    }

    @Transactional
    public HdQuestionMasterResponse updateHdQuestionMasterDetails(EditHdQuestionMasterRequest hdQuestionMasterRequest){

        HdQuestionMasterResponse hdQuestionMasterResponse = new HdQuestionMasterResponse();
        List<HdQuestionMaster> hdQuestionMasterList =  hdQuestionMasterRepository.findByHdQuestionName(hdQuestionMasterRequest.getHdQuestionName());
        if(hdQuestionMasterList.size()>0){
            hdQuestionMasterResponse.setError(true);
            hdQuestionMasterResponse.setError_description("QuestionMaster already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            HdQuestionMaster hdQuestionMaster= hdQuestionMasterRepository.findByHdQuestionIdAndActiveIn(hdQuestionMasterRequest.getHdQuestionId(), Set.of(true,false));
            if(Objects.nonNull(hdQuestionMaster)){
                hdQuestionMaster.setHdQuestionName(hdQuestionMasterRequest.getHdQuestionName());
                hdQuestionMaster.setHdQuestionAnswerName(hdQuestionMasterRequest.getHdQuestionAnswerName());
                hdQuestionMaster.setHdFaqUploadPath(hdQuestionMasterRequest.getHdFaqUploadPath());
                hdQuestionMaster.setActive(true);
                HdQuestionMaster hdQuestionMaster1 = hdQuestionMasterRepository.save(hdQuestionMaster);
                hdQuestionMasterResponse = mapper.hdQuestionMasterEntityToObject(hdQuestionMaster1, HdQuestionMasterResponse.class);
                hdQuestionMasterResponse.setError(false);
            } else {
                hdQuestionMasterResponse.setError(true);
                hdQuestionMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return hdQuestionMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String, Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest) {
        if (searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")) {
            searchWithSortRequest.setSearchText("%%");
        } else {
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if (searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")) {
            searchWithSortRequest.setSortColumn("hdQuestionName");
        }
        if (searchWithSortRequest.getSortOrder() == null || searchWithSortRequest.getSortOrder().equals("")) {
            searchWithSortRequest.setSortOrder("asc");
        }
        if (searchWithSortRequest.getPageNumber() == null || searchWithSortRequest.getPageNumber().equals("")) {
            searchWithSortRequest.setPageNumber("0");
        }
        if (searchWithSortRequest.getPageSize() == null || searchWithSortRequest.getPageSize().equals("")) {
            searchWithSortRequest.setPageSize("5");
        }
        Sort sort;
        if (searchWithSortRequest.getSortOrder().equals("asc")) {
            sort = Sort.by(Sort.Direction.ASC, searchWithSortRequest.getSortColumn());
        } else {
            sort = Sort.by(Sort.Direction.DESC, searchWithSortRequest.getSortColumn());
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(searchWithSortRequest.getPageNumber()), Integer.parseInt(searchWithSortRequest.getPageSize()), sort);
        Page<HdQuestionMasterDTO> hdQuestionMasterDTOs = hdQuestionMasterRepository.getSortedHdQuestions(
//                searchWithSortRequest.getJoinColumn(),
                searchWithSortRequest.getSearchText(),
                pageable
        );
        log.info("Entity is ", hdQuestionMasterDTOs);
        return convertPageableDTOToMapResponse(hdQuestionMasterDTOs);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<HdQuestionMasterDTO> activeQuestions) {
        Map<String, Object> response = new HashMap<>();

        List<HdQuestionMasterResponse> hdQuestionMasterResponses = activeQuestions.getContent().stream()
                .map(hdQuestionMaster -> mapper.hdQuestionMasterDTOToObject(hdQuestionMaster,HdQuestionMasterResponse.class)).collect(Collectors.toList());
        response.put("hdQuestionMaster",hdQuestionMasterResponses);
        response.put("currentPage", activeQuestions.getNumber());
        response.put("totalItems", activeQuestions.getTotalElements());
        response.put("totalPages", activeQuestions.getTotalPages());

        return response;
    }

}
