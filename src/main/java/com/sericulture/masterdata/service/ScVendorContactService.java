package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scVendorBank.ScVendorBankResponse;
import com.sericulture.masterdata.model.api.scVendorContact.EditScVendorContactRequest;
import com.sericulture.masterdata.model.api.scVendorContact.ScVendorContactRequest;
import com.sericulture.masterdata.model.api.scVendorContact.ScVendorContactResponse;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.dto.ScVendorContactDTO;
import com.sericulture.masterdata.model.entity.ScVendorBank;
import com.sericulture.masterdata.model.entity.ScVendorContact;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScVendorContactRepository;
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
public class ScVendorContactService {
    @Autowired
    ScVendorContactRepository scVendorContactRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScVendorContactResponse insertScVendorContactDetails(ScVendorContactRequest scVendorContactRequest){
        ScVendorContactResponse scVendorContactResponse = new ScVendorContactResponse();
        ScVendorContact scVendorContact = mapper.scVendorContactObjectToEntity(scVendorContactRequest, ScVendorContact.class);
        validator.validate(scVendorContact);
        List<ScVendorContact> scVendorContactList = scVendorContactRepository.findByScVendorId(scVendorContactRequest.getScVendorId());
        if(!scVendorContactList.isEmpty() && scVendorContactList.stream().filter(ScVendorContact::getActive).findAny().isPresent()){
            scVendorContactResponse.setError(true);
            scVendorContactResponse.setError_description("Vendor already exist");
        }
        else if(!scVendorContactList.isEmpty() && scVendorContactList.stream().filter(Predicate.not(ScVendorContact::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scVendorContactResponse.setError(true);
            scVendorContactResponse.setError_description("Vendor already exist with inactive state");
        }else {
            scVendorContactResponse = mapper.scVendorContactEntityToObject(scVendorContactRepository.save(scVendorContact), ScVendorContactResponse.class);
            scVendorContactResponse.setError(false);
        }
        return scVendorContactResponse;
    }

    public Map<String,Object> getScVendorContactDetails(final Pageable pageable){
        return convertToMapResponse(scVendorContactRepository.findByActiveOrderByScVendorContactIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scVendorContactRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScVendorContact> activeScVendorContact) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorContactResponse> scVendorContactResponses= activeScVendorContact.getContent().stream()
                .map(scVendorContact -> mapper.scVendorContactEntityToObject(scVendorContact, ScVendorContactResponse.class)).collect(Collectors.toList());
        response.put("ScVendorContact",scVendorContactResponses);
        response.put("currentPage", activeScVendorContact.getNumber());
        response.put("totalItems", activeScVendorContact.getTotalElements());
        response.put("totalPages", activeScVendorContact.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScVendorContact> activeScVendorContact) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorContactResponse> scVendorContactResponses = activeScVendorContact.stream()
                .map(scVendorContact  -> mapper.scVendorContactEntityToObject(scVendorContact, ScVendorContactResponse.class)).collect(Collectors.toList());
        response.put("ScVendorContact",scVendorContactResponses);
        return response;
    }

    public Map<String,Object> getPaginatedScVendorContactWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scVendorContactRepository.getByActiveOrderByScVendorContactIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScVendorContactDTO> activeScVendorContact) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorContactResponse> scVendorContactResponses= activeScVendorContact.getContent().stream()
                .map(scVendorContact -> mapper.scVendorContactDTOToObject(scVendorContact,ScVendorContactResponse.class)).collect(Collectors.toList());
        response.put("ScVendorContact",scVendorContactResponses);
        response.put("currentPage", activeScVendorContact.getNumber());
        response.put("totalItems", activeScVendorContact.getTotalElements());
        response.put("totalPages", activeScVendorContact.getTotalPages());
        return response;
    }


    @Transactional
    public ScVendorContactResponse deleteScVendorContactDetails(long id) {
        ScVendorContactResponse scVendorContactResponse= new ScVendorContactResponse();
        ScVendorContact scVendorContact = scVendorContactRepository.findByScVendorContactIdAndActive(id, true);
        if (Objects.nonNull(scVendorContact)) {
            scVendorContact.setActive(false);
            scVendorContactResponse= mapper.scVendorContactEntityToObject(scVendorContactRepository.save(scVendorContact),ScVendorContactResponse.class);
            scVendorContactResponse.setError(false);
        } else {
            scVendorContactResponse.setError(true);
            scVendorContactResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scVendorContactResponse;
    }

    public ScVendorContactResponse getById(int id){
        ScVendorContactResponse scVendorContactResponse = new ScVendorContactResponse();
        ScVendorContact scVendorContact = scVendorContactRepository.findByScVendorContactIdAndActive(id,true);


        if(scVendorContact == null){
            scVendorContactResponse.setError(true);
            scVendorContactResponse.setError_description("Invalid id");
        }else{
            scVendorContactResponse =  mapper.scVendorContactEntityToObject(scVendorContact, ScVendorContactResponse.class);
            scVendorContactResponse.setError(false);
        }
        log.info("Entity is ",scVendorContact);
        return scVendorContactResponse;
    }


    public ScVendorContactResponse getByIdJoin(int id){
        ScVendorContactResponse scVendorContactResponse = new ScVendorContactResponse();
        ScVendorContactDTO scVendorContactDTO = scVendorContactRepository.getByScVendorContactIdAndActive(id,true);
        if(scVendorContactDTO == null){
            scVendorContactResponse.setError(true);
            scVendorContactResponse.setError_description("Invalid id");
        } else {
            scVendorContactResponse = mapper.scVendorContactDTOToObject(scVendorContactDTO, ScVendorContactResponse.class);
            scVendorContactResponse.setError(false);
        }
        log.info("Entity is ", scVendorContactDTO);
        return scVendorContactResponse;
    }


    public Map<String, Object> getScVendorContactByScVendorId(Long scVendorId) {
        Map<String, Object> response = new HashMap<>();
        List<ScVendorContact> scVendorContactList = scVendorContactRepository.findByScVendorIdAndActiveOrderByPhoneAsc(scVendorId, true);
        if (scVendorContactList.isEmpty()) {
//            throw new ValidationException("Invalid Id");
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            response.put("success", false);
            return response;
        } else {
            log.info("Entity is ", scVendorContactList);
            response = convertListToMapResponse(scVendorContactList);
            response.put("success", true);
            return response;

        }

    }

    private Map<String, Object> convertListToMapResponse(List<ScVendorContact> scVendorContactList) {
        Map<String, Object> response = new HashMap<>();
        List<ScVendorContactResponse> scVendorContactResponses = scVendorContactList.stream()
                .map(scVendorContact -> mapper.scVendorContactEntityToObject(scVendorContact, ScVendorContactResponse.class)).collect(Collectors.toList());
        response.put("scVendorContact", scVendorContactList);
        response.put("totalItems", scVendorContactList.size());
        return response;
    }



    @Transactional
    public ScVendorContactResponse updateScVendorContactDetails(EditScVendorContactRequest scVendorContactRequest) {
        ScVendorContactResponse scVendorContactResponse = new ScVendorContactResponse();
        List<ScVendorContact> scVendorContactList = scVendorContactRepository.findByScVendorIdAndScVendorContactIdIsNot(scVendorContactRequest.getScVendorId(), scVendorContactRequest.getScVendorContactId());
        if (scVendorContactList.size() > 0) {
            scVendorContactResponse.setError(true);
            scVendorContactResponse.setError_description("Vendor exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScVendorContact scVendorContact = scVendorContactRepository.findByScVendorContactIdAndActiveIn(scVendorContactRequest.getScVendorContactId(), Set.of(true, false));
            if (Objects.nonNull(scVendorContact)) {
                scVendorContact.setVendorAddress(scVendorContactRequest.getVendorAddress());
                scVendorContact.setPhone(scVendorContactRequest.getPhone());
                scVendorContact.setEmail(scVendorContactRequest.getEmail());
                scVendorContact.setScVendorId(scVendorContactRequest.getScVendorId());


                scVendorContact.setActive(true);
                ScVendorContact scVendorContact1 = scVendorContactRepository.save(scVendorContact);
                scVendorContactResponse = mapper.scVendorContactEntityToObject(scVendorContact1, ScVendorContactResponse.class);
                scVendorContactResponse.setError(false);
            } else {
                scVendorContactResponse.setError(true);
                scVendorContactResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return scVendorContactResponse;
    }

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
        Page<ScVendorContactDTO> scVendorContactDTOS = scVendorContactRepository.getSortedScVendorContact(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",scVendorContactDTOS);
        return convertPageableDTOToMapResponse(scVendorContactDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScVendorContactDTO> activeScVendorContact) {
        Map<String, Object> response = new HashMap<>();

        List<ScVendorContactResponse> scVendorContactResponses = activeScVendorContact.getContent().stream()
                .map(scVendorContact -> mapper.scVendorContactDTOToObject(scVendorContact,ScVendorContactResponse.class)).collect(Collectors.toList());
        response.put("ScVendorContact",scVendorContactResponses);
        response.put("currentPage", activeScVendorContact.getNumber());
        response.put("totalItems", activeScVendorContact.getTotalElements());
        response.put("totalPages", activeScVendorContact.getTotalPages());

        return response;
    }
}
