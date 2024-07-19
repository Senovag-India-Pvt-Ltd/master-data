package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.schemeQuota.EditSchemeQuotaRequest;
import com.sericulture.masterdata.model.api.schemeQuota.SchemeQuotaRequest;
import com.sericulture.masterdata.model.api.schemeQuota.SchemeQuotaResponse;
import com.sericulture.masterdata.model.dto.SchemeQuotaDTO;
import com.sericulture.masterdata.model.entity.SchemeQuota;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.SchemeQuotaRepository;
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
public class SchemeQuotaService {

    @Autowired
    SchemeQuotaRepository schemeQuotaRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public SchemeQuotaResponse insertSchemeQuotaDetails(SchemeQuotaRequest schemeQuotaRequest){
        SchemeQuotaResponse schemeQuotaResponse = new SchemeQuotaResponse();
        SchemeQuota schemeQuota = mapper.schemeQuotaObjectToEntity(schemeQuotaRequest, SchemeQuota.class);
        validator.validate(schemeQuota);
        List<SchemeQuota> schemeQuotaList = schemeQuotaRepository.findBySchemeQuotaName(schemeQuotaRequest.getSchemeQuotaName());
        if(!schemeQuotaList.isEmpty() && schemeQuotaList.stream().filter(SchemeQuota::getActive).findAny().isPresent()){
            schemeQuotaResponse.setError(true);
            schemeQuotaResponse.setError_description("SchemeDetails already exist");
        }
        else if(!schemeQuotaList.isEmpty() && schemeQuotaList.stream().filter(Predicate.not(SchemeQuota::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            schemeQuotaResponse.setError(true);
            schemeQuotaResponse.setError_description("SchemeDetails already exist with inactive state");}
        else {
            schemeQuotaResponse = mapper.schemeQuotaEntityToObject(schemeQuotaRepository.save(schemeQuota), SchemeQuotaResponse.class);
            schemeQuotaResponse.setError(false);
        }
        return schemeQuotaResponse;
    }

    public Map<String,Object> getSchemeQuota(final Pageable pageable){
        return convertToMapResponse(schemeQuotaRepository.findByActiveOrderBySchemeQuotaIdAsc(true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(schemeQuotaRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<SchemeQuota> activeSchemeQuota) {
        Map<String, Object> response = new HashMap<>();

        List<SchemeQuotaResponse> schemeQuotaResponses= activeSchemeQuota.getContent().stream()
                .map(schemeQuota -> mapper.schemeQuotaEntityToObject(schemeQuota, SchemeQuotaResponse.class)).collect(Collectors.toList());
        response.put("schemeQuota",schemeQuotaResponses);
        response.put("currentPage", activeSchemeQuota.getNumber());
        response.put("totalItems", activeSchemeQuota.getTotalElements());
        response.put("totalPages", activeSchemeQuota.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<SchemeQuota> activeSchemeQuota) {
        Map<String, Object> response = new HashMap<>();

        List<SchemeQuotaResponse> schemeQuotaResponses = activeSchemeQuota.stream()
                .map(schemeQuota  -> mapper.schemeQuotaEntityToObject(schemeQuota, SchemeQuotaResponse.class)).collect(Collectors.toList());
        response.put("schemeQuota",schemeQuotaResponses);
        return response;
    }

    public Map<String,Object> getPaginatedSchemeQuotaWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(schemeQuotaRepository.getByActiveOrderBySchemeQuotaIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<SchemeQuotaDTO> activeSchemeQuota) {
        Map<String, Object> response = new HashMap<>();

        List<SchemeQuotaResponse> schemeQuotaResponses= activeSchemeQuota.getContent().stream()
                .map(schemeQuota -> mapper.schemeQuotaDTOToObject(schemeQuota,SchemeQuotaResponse.class)).collect(Collectors.toList());
        response.put("schemeQuota",schemeQuotaResponses);
        response.put("currentPage", activeSchemeQuota.getNumber());
        response.put("totalItems", activeSchemeQuota.getTotalElements());
        response.put("totalPages", activeSchemeQuota.getTotalPages());
        return response;
    }


    @Transactional
    public SchemeQuotaResponse deleteSchemeQuotaDetails(long id) {
        SchemeQuotaResponse schemeQuotaResponse= new SchemeQuotaResponse();
        SchemeQuota schemeQuota = schemeQuotaRepository.findBySchemeQuotaIdAndActive(id, true);
        if (Objects.nonNull(schemeQuota)) {
            schemeQuota.setActive(false);
            schemeQuotaResponse= mapper.schemeQuotaEntityToObject(schemeQuotaRepository.save(schemeQuota),SchemeQuotaResponse.class);
            schemeQuotaResponse.setError(false);
        } else {
            schemeQuotaResponse.setError(true);
            schemeQuotaResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return schemeQuotaResponse;
    }

    public SchemeQuotaResponse getById(int id){
        SchemeQuotaResponse schemeQuotaResponse = new SchemeQuotaResponse();
        SchemeQuota schemeQuota = schemeQuotaRepository.findBySchemeQuotaIdAndActive(id,true);


        if(schemeQuota == null){
            schemeQuotaResponse.setError(true);
            schemeQuotaResponse.setError_description("Invalid id");
        }else{
            schemeQuotaResponse =  mapper.schemeQuotaEntityToObject(schemeQuota, SchemeQuotaResponse.class);
            schemeQuotaResponse.setError(false);
        }
        log.info("Entity is ",schemeQuota);
        return schemeQuotaResponse;
    }

    public SchemeQuotaResponse getByIdJoin(int id){
        SchemeQuotaResponse schemeQuotaResponse = new SchemeQuotaResponse();
        SchemeQuotaDTO schemeQuotaDTO = schemeQuotaRepository.getBySchemeQuotaIdAndActive(id,true);
        if(schemeQuotaDTO == null){
            schemeQuotaResponse.setError(true);
            schemeQuotaResponse.setError_description("Invalid id");
        } else {
            schemeQuotaResponse = mapper.schemeQuotaDTOToObject(schemeQuotaDTO, SchemeQuotaResponse.class);
            schemeQuotaResponse.setError(false);
        }
        log.info("Entity is ", schemeQuotaDTO);
        return schemeQuotaResponse;
    }

    public Map<String, Object> getSchemeQuotaByScSchemeDetailsId(Long scSchemeDetailsId) {
        Map<String, Object> response = new HashMap<>();
        List<SchemeQuota> schemeQuotaList = schemeQuotaRepository.findByScSchemeDetailsIdAndActiveOrderBySchemeQuotaNameAsc(scSchemeDetailsId, true);
        if (schemeQuotaList.isEmpty()) {
//            throw new ValidationException("Invalid Id");
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            response.put("success", false);
            return response;
        } else {
            log.info("Entity is ", schemeQuotaList);
            response = convertListToMapResponse(schemeQuotaList);
            response.put("success", true);
            return response;

        }

    }

    private Map<String, Object> convertListToMapResponse(List<SchemeQuota> schemeQuotaList) {
        Map<String, Object> response = new HashMap<>();
        List<SchemeQuotaResponse> schemeQuotaResponses = schemeQuotaList.stream()
                .map(schemeQuota -> mapper.schemeQuotaEntityToObject(schemeQuota, SchemeQuotaResponse.class)).collect(Collectors.toList());
        response.put("schemeQuota", schemeQuotaResponses);
        response.put("totalItems", schemeQuotaList.size());
        return response;
    }



    @Transactional
    public SchemeQuotaResponse updateSchemeQuotaDetails(EditSchemeQuotaRequest schemeQuotaRequest) {
        SchemeQuotaResponse schemeQuotaResponse = new SchemeQuotaResponse();
        List<SchemeQuota> schemeQuotaList = schemeQuotaRepository.findBySchemeQuotaNameAndSchemeQuotaIdIsNot(schemeQuotaRequest.getSchemeQuotaName(), schemeQuotaRequest.getSchemeQuotaId());
        if (schemeQuotaList.size() > 0) {
            schemeQuotaResponse.setError(true);
            schemeQuotaResponse.setError_description("SchemeDetails exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            SchemeQuota schemeQuota = schemeQuotaRepository.findBySchemeQuotaIdAndActiveIn(schemeQuotaRequest.getSchemeQuotaId(), Set.of(true, false));
            if (Objects.nonNull(schemeQuota)) {
                schemeQuota.setScSchemeDetailsId(schemeQuotaRequest.getScSchemeDetailsId());
                schemeQuota.setSchemeQuotaName(schemeQuotaRequest.getSchemeQuotaName());
                schemeQuota.setSchemeQuotaType(schemeQuotaRequest.getSchemeQuotaType());
                schemeQuota.setSchemeQuotaPaymentType(schemeQuotaRequest.getSchemeQuotaPaymentType());
                schemeQuota.setSchemeQuotaCode(schemeQuotaRequest.getSchemeQuotaCode());
                schemeQuota.setDbtCode(schemeQuotaRequest.getDbtCode());
                schemeQuota.setDdoCode(schemeQuotaRequest.getDdoCode());


                schemeQuota.setActive(true);
                SchemeQuota schemeQuota1 = schemeQuotaRepository.save(schemeQuota);
                schemeQuotaResponse = mapper.schemeQuotaEntityToObject(schemeQuota1, SchemeQuotaResponse.class);
                schemeQuotaResponse.setError(false);
            } else {
                schemeQuotaResponse.setError(true);
                schemeQuotaResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return schemeQuotaResponse;
    }


    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("scSchemeDetails.schemeName");
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
        Page<SchemeQuotaDTO> SchemeQuotaDTOS = schemeQuotaRepository.getSortedSchemeQuota(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",SchemeQuotaDTOS);
        return convertPageableDTOToMapResponse(SchemeQuotaDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<SchemeQuotaDTO> activeSchemeQuota) {
        Map<String, Object> response = new HashMap<>();

        List<SchemeQuotaResponse> schemeQuotaResponses = activeSchemeQuota.getContent().stream()
                .map(schemeQuota -> mapper.schemeQuotaDTOToObject(schemeQuota,SchemeQuotaResponse.class)).collect(Collectors.toList());
        response.put("schemeQuota",schemeQuotaResponses);
        response.put("currentPage", activeSchemeQuota.getNumber());
        response.put("totalItems", activeSchemeQuota.getTotalElements());
        response.put("totalPages", activeSchemeQuota.getTotalPages());

        return response;
    }
}
