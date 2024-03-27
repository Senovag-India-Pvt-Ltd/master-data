package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scVendorBank.EditScVendorBankRequest;
import com.sericulture.masterdata.model.api.scVendorBank.ScVendorBankRequest;
import com.sericulture.masterdata.model.api.scVendorBank.ScVendorBankResponse;
import com.sericulture.masterdata.model.dto.ScVendorBankDTO;
import com.sericulture.masterdata.model.entity.ScVendorBank;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScVendorBankRepository;
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
public class ScVendorBankService {
    @Autowired
    ScVendorBankRepository scVendorBankRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScVendorBankResponse insertScVendorBankDetails(ScVendorBankRequest scVendorBankRequest){
        ScVendorBankResponse scVendorBankResponse = new ScVendorBankResponse();
        ScVendorBank scVendorBank = mapper.scVendorBankObjectToEntity(scVendorBankRequest, ScVendorBank.class);
        validator.validate(scVendorBank);
        List<ScVendorBank> scVendorBankList = scVendorBankRepository.findByScVendorId(scVendorBankRequest.getScVendorId());
        if(!scVendorBankList.isEmpty() && scVendorBankList.stream().filter(ScVendorBank::getActive).findAny().isPresent()){
            scVendorBankResponse.setError(true);
            scVendorBankResponse.setError_description("ScVendorBank already exist");
        }
        else if(!scVendorBankList.isEmpty() && scVendorBankList.stream().filter(Predicate.not(ScVendorBank::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scVendorBankResponse.setError(true);
            scVendorBankResponse.setError_description("ScVendorBank already exist with inactive state");
        }else {
            scVendorBankResponse = mapper.scVendorBankEntityToObject(scVendorBankRepository.save(scVendorBank), ScVendorBankResponse.class);
            scVendorBankResponse.setError(false);
        }
        return scVendorBankResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getScVendorBankDetails(final Pageable pageable){
        return convertToMapResponse(scVendorBankRepository.findByActiveOrderByScVendorBankIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scVendorBankRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScVendorBank> activeScVendorBank) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorBankResponse> scVendorBankResponses= activeScVendorBank.getContent().stream()
                .map(scVendorBank -> mapper.scVendorBankEntityToObject(scVendorBank, ScVendorBankResponse.class)).collect(Collectors.toList());
        response.put("ScVendorBank",scVendorBankResponses);
        response.put("currentPage", activeScVendorBank.getNumber());
        response.put("totalItems", activeScVendorBank.getTotalElements());
        response.put("totalPages", activeScVendorBank.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScVendorBank> activeScVendorBank) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorBankResponse> scVendorBankResponses = activeScVendorBank.stream()
                .map(scVendorBank  -> mapper.scVendorBankEntityToObject(scVendorBank, ScVendorBankResponse.class)).collect(Collectors.toList());
        response.put("scVendorBank",scVendorBankResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScVendorBankWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scVendorBankRepository.getByActiveOrderByScVendorBankIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScVendorBankDTO> activeScVendorBank) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorBankResponse> scVendorBankResponses= activeScVendorBank.getContent().stream()
                .map(scVendorBank -> mapper.scVendorBankDTOToObject(scVendorBank,ScVendorBankResponse.class)).collect(Collectors.toList());
        response.put("ScVendorBank",scVendorBankResponses);
        response.put("currentPage", activeScVendorBank.getNumber());
        response.put("totalItems", activeScVendorBank.getTotalElements());
        response.put("totalPages", activeScVendorBank.getTotalPages());
        return response;
    }


    @Transactional
    public ScVendorBankResponse deleteScVendorBankDetails(long id) {
        ScVendorBankResponse scVendorBankResponse= new ScVendorBankResponse();
        ScVendorBank scVendorBank = scVendorBankRepository.findByScVendorBankIdAndActive(id, true);
        if (Objects.nonNull(scVendorBank)) {
            scVendorBank.setActive(false);
            scVendorBankResponse= mapper.scVendorBankEntityToObject(scVendorBankRepository.save(scVendorBank),ScVendorBankResponse.class);
            scVendorBankResponse.setError(false);
        } else {
            scVendorBankResponse.setError(true);
            scVendorBankResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scVendorBankResponse;
    }

    @Transactional
    public ScVendorBankResponse getById(int id){
        ScVendorBankResponse scVendorBankResponse = new ScVendorBankResponse();
        ScVendorBank scVendorBank = scVendorBankRepository.findByScVendorBankIdAndActive(id,true);


        if(scVendorBank == null){
            scVendorBankResponse.setError(true);
            scVendorBankResponse.setError_description("Invalid id");
        }else{
            scVendorBankResponse =  mapper.scVendorBankEntityToObject(scVendorBank, ScVendorBankResponse.class);
            scVendorBankResponse.setError(false);
        }
        log.info("Entity is ",scVendorBank);
        return scVendorBankResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<ScVendorBankDTO> scVendorBankList = scVendorBankRepository.getScVendorBank(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(scVendorBankList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", scVendorBankList);
//            response = convertListToMapResponse(scVendorBankList);
//            return response;
//        }
//    }

    private Map<String, Object> convertListToMapResponse(List<ScVendorBankDTO> scVendorBankList) {
        Map<String, Object> response = new HashMap<>();
        List<ScVendorBankResponse> scVendorBankResponses = scVendorBankList.stream()
                .map(scVendorBank -> mapper.scVendorBankDTOToObject(scVendorBank,ScVendorBankResponse.class)).collect(Collectors.toList());
        response.put("ScVendorBank",scVendorBankResponses);
        response.put("totalItems", scVendorBankResponses.size());
        return response;
    }

    @Transactional
    public ScVendorBankResponse getByIdJoin(int id){
        ScVendorBankResponse scVendorBankResponse = new ScVendorBankResponse();
        ScVendorBankDTO scVendorBankDTO = scVendorBankRepository.getByScVendorBankIdAndActive(id,true);
        if(scVendorBankDTO == null){
            scVendorBankResponse.setError(true);
            scVendorBankResponse.setError_description("Invalid id");
        } else {
            scVendorBankResponse = mapper.scVendorBankDTOToObject(scVendorBankDTO, ScVendorBankResponse.class);
            scVendorBankResponse.setError(false);
        }
        log.info("Entity is ", scVendorBankDTO);
        return scVendorBankResponse;
    }

    @Transactional
    public ScVendorBankResponse updateScVendorBankDetails(EditScVendorBankRequest scVendorBankRequest) {
        ScVendorBankResponse scVendorBankResponse = new ScVendorBankResponse();
        List<ScVendorBank> scVendorBankList = scVendorBankRepository.findByScVendorIdAndScVendorBankIdIsNot(scVendorBankRequest.getScVendorId(), scVendorBankRequest.getScVendorBankId());
        if (scVendorBankList.size() > 0) {
            scVendorBankResponse.setError(true);
            scVendorBankResponse.setError_description("ScVendorBank exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScVendorBank scVendorBank = scVendorBankRepository.findByScVendorBankIdAndActiveIn(scVendorBankRequest.getScVendorBankId(), Set.of(true, false));
            if (Objects.nonNull(scVendorBank)) {
                scVendorBank.setBankName(scVendorBankRequest.getBankName());
                scVendorBank.setIfscCode(scVendorBankRequest.getIfscCode());
                scVendorBank.setBranch(scVendorBankRequest.getBranch());
                scVendorBank.setAccountNumber(scVendorBankRequest.getAccountNumber());
                scVendorBank.setUpi(scVendorBankRequest.getUpi());
                scVendorBank.setScVendorId(scVendorBankRequest.getScVendorId());

                scVendorBank.setActive(true);
                ScVendorBank scVendorBank1 = scVendorBankRepository.save(scVendorBank);
                scVendorBankResponse = mapper.scVendorBankEntityToObject(scVendorBank1, ScVendorBankResponse.class);
                scVendorBankResponse.setError(false);
            } else {
                scVendorBankResponse.setError(true);
                scVendorBankResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scVendorBankResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("scVendor.name");
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
        Page<ScVendorBankDTO> scVendorBankDTOS = scVendorBankRepository.getSortedScVendorBank(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",scVendorBankDTOS);
        return convertPageableDTOToMapResponse(scVendorBankDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScVendorBankDTO> activeScVendorBank) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorBankResponse> scVendorBankResponses = activeScVendorBank.getContent().stream()
                .map(scVendorBank -> mapper.scVendorBankDTOToObject(scVendorBank,ScVendorBankResponse.class)).collect(Collectors.toList());
        response.put("ScVendorBank",scVendorBankResponses);
        response.put("currentPage", activeScVendorBank.getNumber());
        response.put("totalItems", activeScVendorBank.getTotalElements());
        response.put("totalPages", activeScVendorBank.getTotalPages());

        return response;
    }
}
