package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.godown.EditGodownRequest;
import com.sericulture.masterdata.model.api.godown.GodownRequest;
import com.sericulture.masterdata.model.api.godown.GodownResponse;
import com.sericulture.masterdata.model.api.hobli.HobliResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
//import com.sericulture.masterdata.model.dto.GodownDTO;
import com.sericulture.masterdata.model.dto.GodownDTO;
import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.entity.Godown;
import com.sericulture.masterdata.model.entity.Hobli;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.GodownRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GodownService {

    @Autowired
    GodownRepository godownRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    public GodownResponse getGodownDetails(String godownName){
        GodownResponse godownResponse = new GodownResponse();
        Godown godown = godownRepository.findByGodownNameAndActive(godownName,true);
        if(godown==null){
            godownResponse.setError(true);
            godownResponse.setError_description("Godown not found");
        }else{
            godownResponse = mapper.godownEntityToObject(godown, GodownResponse.class);
            godownResponse.setError(false);
        }
        log.info("Entity is ",godown);
        return godownResponse;
    }

    @Transactional
    public GodownResponse insertGodownDetails(GodownRequest godownRequest){
        GodownResponse godownResponse = new GodownResponse();
        Godown godown = mapper.godownObjectToEntity(godownRequest,Godown.class);
        validator.validate(godown);
        List<Godown> godownList = godownRepository.findByGodownNameAndMarketMasterIdAndActive(godownRequest.getGodownName(), godownRequest.getMarketMasterId(), true);
        if(!godownList.isEmpty() && godownList.stream().filter(Godown::getActive).findAny().isPresent()){
            godownResponse.setError(true);
            godownResponse.setError_description("Godown name already exist");
        }
        else if(!godownList.isEmpty() && godownList.stream().filter(Predicate.not(Godown::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            godownResponse.setError(true);
            godownResponse.setError_description("Godown name already exist with inactive state");
        }else {
            godownResponse = mapper.godownEntityToObject(godownRepository.save(godown), GodownResponse.class);
            godownResponse.setError(false);
        }
        return godownResponse;
    }

    public Map<String,Object> getPaginatedGodownDetails(final Pageable pageable){
        return convertToMapResponse(godownRepository.findByActiveOrderByGodownIdAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(godownRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<Godown> activeGodowns) {
        Map<String, Object> response = new HashMap<>();

        List<GodownResponse> godownResponses = activeGodowns.getContent().stream()
                .map(godown -> mapper.godownEntityToObject(godown,GodownResponse.class)).collect(Collectors.toList());
        response.put("godown",godownResponses);
        response.put("currentPage", activeGodowns.getNumber());
        response.put("totalItems", activeGodowns.getTotalElements());
        response.put("totalPages", activeGodowns.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<Godown> activeGodowns) {
        Map<String, Object> response = new HashMap<>();

        List<GodownResponse> godownResponses = activeGodowns.stream()
                .map(godown -> mapper.godownEntityToObject(godown,GodownResponse.class)).collect(Collectors.toList());
        response.put("godown",godownResponses);
        return response;
    }

    public Map<String,Object> getPaginatedGodownDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(godownRepository.getByActiveOrderByGodownIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<GodownDTO> activeGodowns) {
        Map<String, Object> response = new HashMap<>();

        List<GodownResponse> godownResponses = activeGodowns.getContent().stream()
                .map(godown -> mapper.godownDTOToObject(godown,GodownResponse.class)).collect(Collectors.toList());
        response.put("godown",godownResponses);
        response.put("currentPage", activeGodowns.getNumber());
        response.put("totalItems", activeGodowns.getTotalElements());
        response.put("totalPages", activeGodowns.getTotalPages());
        return response;
    }

    @Transactional
    public GodownResponse deleteGodownDetails(long id) {
        GodownResponse godownResponse = new GodownResponse();
        Godown godown = godownRepository.findByGodownIdAndActive(id, true);
        if (Objects.nonNull(godown)) {
            godown.setActive(false);
            godownResponse = mapper.godownEntityToObject(godownRepository.save(godown), GodownResponse.class);
            godownResponse.setError(false);
        } else {
            godownResponse.setError(true);
            godownResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return godownResponse;
    }

    public GodownResponse getById(int id){
        GodownResponse godownResponse = new GodownResponse();
        Godown godown = godownRepository.findByGodownIdAndActive(id,true);
        if(godown == null){
            godownResponse.setError(true);
            godownResponse.setError_description("Invalid id");
        }else{
            godownResponse =  mapper.godownEntityToObject(godown,GodownResponse.class);
            godownResponse.setError(false);
        }
        log.info("Entity is ",godown);
        return godownResponse;
    }

    public GodownResponse getByIdJoin(int id){
        GodownResponse godownResponse = new GodownResponse();
        GodownDTO godownDTO = godownRepository.getByGodownIdAndActive(id,true);
        if(godownDTO == null){
            godownResponse.setError(true);
            godownResponse.setError_description("Invalid id");
        } else {
            godownResponse = mapper.godownDTOToObject(godownDTO, GodownResponse.class);
            godownResponse.setError(false);
        }
        log.info("Entity is ", godownDTO);
        return godownResponse;
    }

    public Map<String,Object> getByMarketMasterId(int marketMasterId){
        Map<String, Object> response = new HashMap<>();
        List<Godown> godownList = godownRepository.findByMarketMasterIdAndActive(marketMasterId,true);
        if(godownList.isEmpty()){
            response.put("error","Error");
            response.put("error_description","Invalid id");
            return response;
        }else {
            log.info("Entity is ", godownList);
            response = convertListToMapResponse(godownList);
            return response;
        }
    }

    private Map<String, Object> convertListToMapResponse(List<Godown> godownList) {
        Map<String, Object> response = new HashMap<>();
        List<GodownResponse> godownResponses = godownList.stream()
                .map(godown -> mapper.godownEntityToObject(godown,GodownResponse.class)).collect(Collectors.toList());
        response.put("godown",godownResponses);
        response.put("totalItems", godownResponses.size());
        return response;
    }


    @Transactional
    public GodownResponse updateGodownDetails(EditGodownRequest godownRequest) {
        GodownResponse godownResponse = new GodownResponse();
        List<Godown> godownList = godownRepository.findByGodownNameAndMarketMasterIdAndActive(godownRequest.getGodownName(),godownRequest.getMarketMasterId(), true);
//        if (godownList.size() > 0) {
//            godownResponse.setError(true);
//            godownResponse.setError_description("Godown already exists, duplicates are not allowed.");
//            // throw new ValidationException("Village already exists, duplicates are not allowed.");
//        } else {

            Godown godown = godownRepository.findByGodownIdAndActiveIn(godownRequest.getGodownId(), Set.of(true, false));
            if (Objects.nonNull(godown)) {
                godown.setGodownName(godownRequest.getGodownName());
                godown.setGodownNameInKannada(godownRequest.getGodownNameInKannada());
                godown.setMarketMasterId(godownRequest.getMarketMasterId());
                godown.setActive(true);
                Godown godown1 = godownRepository.save(godown);
                godownResponse = mapper.godownEntityToObject(godown1, GodownResponse.class);
                godownResponse.setError(false);
            } else {
                godownResponse.setError(true);
                godownResponse.setError_description("Error occurred while fetching Godown");
                // throw new ValidationException("Error occurred while fetching village");
            }

        return godownResponse;
    }
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("godownName");
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
        Page<GodownDTO> godownDTOS = godownRepository.getSortedGodowns(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",godownDTOS);
        return convertPageableDTOToMapResponse(godownDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<GodownDTO> activeGodowns) {
        Map<String, Object> response = new HashMap<>();

        List<GodownResponse> godownResponses = activeGodowns.getContent().stream()
                .map(godown -> mapper.godownDTOToObject(godown,GodownResponse.class)).collect(Collectors.toList());
        response.put("godown",godownResponses);
        response.put("currentPage", activeGodowns.getNumber());
        response.put("totalItems", activeGodowns.getTotalElements());
        response.put("totalPages", activeGodowns.getTotalPages());

        return response;
    }
}