package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.documentMaster.DocumentMasterRequest;
import com.sericulture.masterdata.model.api.documentMaster.DocumentMasterResponse;
import com.sericulture.masterdata.model.api.documentMaster.EditDocumentMasterRequest;
import com.sericulture.masterdata.model.api.state.EditStateRequest;
import com.sericulture.masterdata.model.api.state.StateRequest;
import com.sericulture.masterdata.model.api.state.StateResponse;
import com.sericulture.masterdata.model.entity.DocumentMaster;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.DocumentMasterRepository;
import com.sericulture.masterdata.repository.StateRepository;
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
public class DocumentMasterService {

    @Autowired
    DocumentMasterRepository documentMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public DocumentMasterResponse getDocumentMasterDetails(String documentMasterName){
        DocumentMasterResponse documentMasterResponse = new DocumentMasterResponse();
        DocumentMaster documentMaster = documentMasterRepository.findByDocumentMasterNameAndActive(documentMasterName,true);
        if(documentMaster==null){
            documentMasterResponse.setError(true);
            documentMasterResponse.setError_description("Document not found");
        }else{
            documentMasterResponse = mapper.documentMasterEntityToObject(documentMaster,DocumentMasterResponse.class);
            documentMasterResponse.setError(false);
        }
        log.info("Entity is ",documentMaster);
        return documentMasterResponse;
    }

    @Transactional
    public DocumentMasterResponse insertDocumentMasterDetails(DocumentMasterRequest documentMasterRequest){
        DocumentMasterResponse documentMasterResponse = new DocumentMasterResponse();
        DocumentMaster documentMaster = mapper.documentMasterObjectToEntity(documentMasterRequest,DocumentMaster.class);
        validator.validate(documentMaster);
        List<DocumentMaster> documentMasterList = documentMasterRepository.findByDocumentMasterName(documentMasterRequest.getDocumentMasterName());
        if(!documentMasterList.isEmpty() && documentMasterList.stream().filter(DocumentMaster::getActive).findAny().isPresent()){
            documentMasterResponse.setError(true);
            documentMasterResponse.setError_description("Document name already exist");
        }
        else if(!documentMasterList.isEmpty() && documentMasterList.stream().filter(Predicate.not(DocumentMaster::getActive)).findAny().isPresent()){
            documentMasterResponse.setError(true);
            documentMasterResponse.setError_description("Document name already exist with inactive state");
        }else {
            documentMasterResponse = mapper.documentMasterEntityToObject(documentMasterRepository.save(documentMaster), DocumentMasterResponse.class);
            documentMasterResponse.setError(false);
        }
        return documentMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedDocumentMasterDetails(final Pageable pageable){
        return convertToMapResponse(documentMasterRepository.findByActiveOrderByDocumentMasterNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(documentMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<DocumentMaster> activeDocumentMasters) {
        Map<String, Object> response = new HashMap<>();

        List<DocumentMasterResponse> documentMasterResponses = activeDocumentMasters.getContent().stream()
                .map(documentMaster -> mapper.documentMasterEntityToObject(documentMaster,DocumentMasterResponse.class)).collect(Collectors.toList());
        response.put("documentMaster",documentMasterResponses);
        response.put("currentPage", activeDocumentMasters.getNumber());
        response.put("totalItems", activeDocumentMasters.getTotalElements());
        response.put("totalPages", activeDocumentMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<DocumentMaster> activeDocumentMasters) {
        Map<String, Object> response = new HashMap<>();

        List<DocumentMasterResponse> documentMasterResponses = activeDocumentMasters.stream()
                .map(documentMaster -> mapper.documentMasterEntityToObject(documentMaster,DocumentMasterResponse.class)).collect(Collectors.toList());
        response.put("documentMaster",documentMasterResponses);
        return response;
    }

    @Transactional
    public DocumentMasterResponse deleteDocumentMasterDetails(long id) {

        DocumentMasterResponse documentMasterResponse = new DocumentMasterResponse();
        DocumentMaster documentMaster = documentMasterRepository.findByDocumentMasterIdAndActive(id, true);
        if (Objects.nonNull(documentMaster)) {
            documentMaster.setActive(false);
            documentMasterResponse = mapper.documentMasterEntityToObject(documentMasterRepository.save(documentMaster), DocumentMasterResponse.class);
            documentMasterResponse.setError(false);
        } else {
            documentMasterResponse.setError(true);
            documentMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return documentMasterResponse;
    }

    @Transactional
    public DocumentMasterResponse getById(int id){
        DocumentMasterResponse documentMasterResponse = new DocumentMasterResponse();
        DocumentMaster documentMaster = documentMasterRepository.findByDocumentMasterIdAndActive(id,true);
        if(documentMaster == null){
            documentMasterResponse.setError(true);
            documentMasterResponse.setError_description("Invalid id");
        }else{
            documentMasterResponse =  mapper.documentMasterEntityToObject(documentMaster,DocumentMasterResponse.class);
            documentMasterResponse.setError(false);
        }
        log.info("Entity is ",documentMaster);
        return documentMasterResponse;
    }

    @Transactional
    public DocumentMasterResponse updateDocumentMasterDetails(EditDocumentMasterRequest documentMasterRequest){

        DocumentMasterResponse documentMasterResponse = new DocumentMasterResponse();
        List<DocumentMaster> documentMasterList = documentMasterRepository.findByDocumentMasterName(documentMasterRequest.getDocumentMasterName());
        if(documentMasterList.size()>0){
            documentMasterResponse.setError(true);
            documentMasterResponse.setError_description("Document already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            DocumentMaster documentMaster = documentMasterRepository.findByDocumentMasterIdAndActiveIn(documentMasterRequest.getDocumentMasterId(), Set.of(true,false));
            if(Objects.nonNull(documentMaster)){
                documentMaster.setDocumentMasterName(documentMasterRequest.getDocumentMasterName());
                documentMaster.setActive(true);
                DocumentMaster documentMaster1 = documentMasterRepository.save(documentMaster);
                documentMasterResponse = mapper.documentMasterEntityToObject(documentMaster1, DocumentMasterResponse.class);
                documentMasterResponse.setError(false);
            } else {
                documentMasterResponse.setError(true);
                documentMasterResponse.setError_description("Error occurred while fetching document");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return documentMasterResponse;
    }



}
