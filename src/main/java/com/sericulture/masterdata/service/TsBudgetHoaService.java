package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.tsBudgetHoa.EditTsBudgetHoaRequest;
import com.sericulture.masterdata.model.api.tsBudgetHoa.TsBudgetHoaRequest;
import com.sericulture.masterdata.model.api.tsBudgetHoa.TsBudgetHoaResponse;
import com.sericulture.masterdata.model.dto.TsBudgetHoaDTO;
import com.sericulture.masterdata.model.entity.TsBudgetHoa;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TsBudgetHoaRepository;
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
public class TsBudgetHoaService {
    @Autowired
    TsBudgetHoaRepository tsBudgetHoaRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public TsBudgetHoaResponse insertTsBudgetHoaDetails(TsBudgetHoaRequest tsBudgetHoaRequest){
        TsBudgetHoaResponse tsBudgetHoaResponse = new TsBudgetHoaResponse();
        TsBudgetHoa tsBudgetHoa = mapper.tsBudgetHoaObjectToEntity(tsBudgetHoaRequest, TsBudgetHoa.class);
        validator.validate(tsBudgetHoa);
        List<TsBudgetHoa> tsBudgetHoaList = tsBudgetHoaRepository.findByFinancialYearMasterIdAndScHeadAccountId(tsBudgetHoaRequest.getFinancialYearMasterId(),tsBudgetHoaRequest.getScHeadAccountId());
        if(!tsBudgetHoaList.isEmpty() && tsBudgetHoaList.stream().filter(TsBudgetHoa::getActive).findAny().isPresent()){
            tsBudgetHoaResponse.setError(true);
            tsBudgetHoaResponse.setError_description("TsBudgetHoa already exist");
        }
        else if(!tsBudgetHoaList.isEmpty() && tsBudgetHoaList.stream().filter(Predicate.not(TsBudgetHoa::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            tsBudgetHoaResponse.setError(true);
            tsBudgetHoaResponse.setError_description("TsBudgetHoa already exist with inactive state");
        }else {
            tsBudgetHoaResponse = mapper.tsBudgetHoaEntityToObject(tsBudgetHoaRepository.save(tsBudgetHoa), TsBudgetHoaResponse.class);
            tsBudgetHoaResponse.setError(false);
        }
        return tsBudgetHoaResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getTsBudgetHoaDetails(final Pageable pageable){
        return convertToMapResponse(tsBudgetHoaRepository.findByActiveOrderByTsBudgetHoaIdAsc(true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(tsBudgetHoaRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TsBudgetHoa> activeTsBudgetHoa) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetHoaResponse> tsBudgetHoaResponses= activeTsBudgetHoa.getContent().stream()
                .map(tsBudgetHoa -> mapper.tsBudgetHoaEntityToObject(tsBudgetHoa, TsBudgetHoaResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetHoa",tsBudgetHoaResponses);
        response.put("currentPage", activeTsBudgetHoa.getNumber());
        response.put("totalItems", activeTsBudgetHoa.getTotalElements());
        response.put("totalPages", activeTsBudgetHoa.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TsBudgetHoa> activeTsBudgetHoa) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetHoaResponse> tsBudgetHoaResponses = activeTsBudgetHoa.stream()
                .map(tsBudgetHoa  -> mapper.tsBudgetHoaEntityToObject(tsBudgetHoa, TsBudgetHoaResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetHoa",tsBudgetHoaResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTsBudgetHoaWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(tsBudgetHoaRepository.getByActiveOrderByTsBudgetHoaIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TsBudgetHoaDTO> activeTsBudgetHoa) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetHoaResponse> tsBudgetHoaResponses= activeTsBudgetHoa.getContent().stream()
                .map(tsBudgetHoa -> mapper.tsBudgetHoaDTOToObject(tsBudgetHoa,TsBudgetHoaResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetHoa",tsBudgetHoaResponses);
        response.put("currentPage", activeTsBudgetHoa.getNumber());
        response.put("totalItems", activeTsBudgetHoa.getTotalElements());
        response.put("totalPages", activeTsBudgetHoa.getTotalPages());
        return response;
    }


    @Transactional
    public TsBudgetHoaResponse deleteTsBudgetHoaDetails(long id) {
        TsBudgetHoaResponse tsBudgetHoaResponse= new TsBudgetHoaResponse();
        TsBudgetHoa tsBudgetHoa = tsBudgetHoaRepository.findByTsBudgetHoaIdAndActive(id, true);
        if (Objects.nonNull(tsBudgetHoa)) {
            tsBudgetHoa.setActive(false);
            tsBudgetHoaResponse= mapper.tsBudgetHoaEntityToObject(tsBudgetHoaRepository.save(tsBudgetHoa),TsBudgetHoaResponse.class);
            tsBudgetHoaResponse.setError(false);
        } else {
            tsBudgetHoaResponse.setError(true);
            tsBudgetHoaResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return tsBudgetHoaResponse;
    }

    @Transactional
    public TsBudgetHoaResponse getById(int id){
        TsBudgetHoaResponse tsBudgetHoaResponse = new TsBudgetHoaResponse();
        TsBudgetHoa tsBudgetHoa = tsBudgetHoaRepository.findByTsBudgetHoaIdAndActive(id,true);


        if(tsBudgetHoa == null){
            tsBudgetHoaResponse.setError(true);
            tsBudgetHoaResponse.setError_description("Invalid id");
        }else{
            tsBudgetHoaResponse =  mapper.tsBudgetHoaEntityToObject(tsBudgetHoa, TsBudgetHoaResponse.class);
            tsBudgetHoaResponse.setError(false);
        }
        log.info("Entity is ",tsBudgetHoa);
        return tsBudgetHoaResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByScProgramId(long scProgramId){
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetHoaDTO> tsBudgetHoaList = tsBudgetHoaRepository.getTsBudgetHoa(scProgramId,true);
//        // List<RaceMarketMasterDTO> raceMarketMasterList = new ArrayList<>();
//        if(tsBudgetHoaList.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            log.info("Entity is ", tsBudgetHoaList);
//            response = convertListToMapResponse(tsBudgetHoaList);
//            return response;
//        }
//    }



    @Transactional
    public TsBudgetHoaResponse getByIdJoin(int id){
        TsBudgetHoaResponse tsBudgetHoaResponse = new TsBudgetHoaResponse();
        TsBudgetHoaDTO tsBudgetHoaDTO = tsBudgetHoaRepository.getByTsBudgetHoaIdAndActive(id,true);
        if(tsBudgetHoaDTO == null){
            tsBudgetHoaResponse.setError(true);
            tsBudgetHoaResponse.setError_description("Invalid id");
        } else {
            tsBudgetHoaResponse = mapper.tsBudgetHoaDTOToObject(tsBudgetHoaDTO, TsBudgetHoaResponse.class);
            tsBudgetHoaResponse.setError(false);
        }
        log.info("Entity is ", tsBudgetHoaDTO);
        return tsBudgetHoaResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String, Object> getTsBudgetHoaByFinancialYearMasterId(Long financialYearMasterId) {
//        Map<String, Object> response = new HashMap<>();
//        List<TsBudgetHoaDTO> tsBudgetHoaList = tsBudgetHoaRepository.getByFinancialYearMasterIdAndActive(financialYearMasterId, true);
//        if (tsBudgetHoaList.isEmpty()) {
////            throw new ValidationException("Invalid Id");
//            response.put("error", "Error");
//            response.put("error_description", "Invalid id");
//            response.put("success", false);
//            return response;
//        } else {
//            log.info("Entity is ", tsBudgetHoaList);
//            response = convertListDTOToMapResponse(tsBudgetHoaList);
//            response.put("success", true);
//            return response;
//
//        }
//
//    }

    private Map<String, Object> convertListToMapResponse(List<TsBudgetHoa> tsBudgetHoaList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetHoaResponse> tsBudgetHoaResponses = tsBudgetHoaList.stream()
                .map(tsBudgetHoa -> mapper.tsBudgetHoaEntityToObject(tsBudgetHoa, TsBudgetHoaResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetHoa", tsBudgetHoaResponses);
        response.put("totalItems", tsBudgetHoaList.size());
        return response;
    }
    private Map<String, Object> convertListDTOToMapResponse(List<TsBudgetHoaDTO> tsBudgetHoaList) {
        Map<String, Object> response = new HashMap<>();
        List<TsBudgetHoaResponse> tsBudgetHoaResponses = tsBudgetHoaList.stream()
                .map(tsBudgetHoa -> mapper.tsBudgetHoaDTOToObject(tsBudgetHoa,TsBudgetHoaResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetHoa",tsBudgetHoaResponses);
        response.put("totalItems", tsBudgetHoaList.size());
        return response;
    }


    @Transactional
    public TsBudgetHoaResponse updateTsBudgetHoaDetails(EditTsBudgetHoaRequest tsBudgetHoaRequest) {
        TsBudgetHoaResponse tsBudgetHoaResponse = new TsBudgetHoaResponse();
        List<TsBudgetHoa> tsBudgetHoaList = tsBudgetHoaRepository.findByFinancialYearMasterIdAndScHeadAccountIdAndTsBudgetHoaIdIsNot(tsBudgetHoaRequest.getFinancialYearMasterId(),tsBudgetHoaRequest.getScHeadAccountId(), tsBudgetHoaRequest.getTsBudgetHoaId());
        if (tsBudgetHoaList.size() > 0) {
            tsBudgetHoaResponse.setError(true);
            tsBudgetHoaResponse.setError_description("TsBudgetHoa exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            TsBudgetHoa tsBudgetHoa = tsBudgetHoaRepository.findByTsBudgetHoaIdAndActiveIn(tsBudgetHoaRequest.getTsBudgetHoaId(), Set.of(true, false));
            if (Objects.nonNull(tsBudgetHoa)) {
                tsBudgetHoa.setFinancialYearMasterId(tsBudgetHoaRequest.getFinancialYearMasterId());
                tsBudgetHoa.setScHeadAccountId(tsBudgetHoaRequest.getScHeadAccountId());
                tsBudgetHoa.setDate(tsBudgetHoaRequest.getDate());
                tsBudgetHoa.setBudgetAmount(tsBudgetHoaRequest.getBudgetAmount());

                tsBudgetHoa.setActive(true);
                TsBudgetHoa tsBudgetHoa1 = tsBudgetHoaRepository.save(tsBudgetHoa);
                tsBudgetHoaResponse = mapper.tsBudgetHoaEntityToObject(tsBudgetHoa1, TsBudgetHoaResponse.class);
                tsBudgetHoaResponse.setError(false);
            } else {
                tsBudgetHoaResponse.setError(true);
                tsBudgetHoaResponse.setError_description("Error occurred while fetching Crate");
                // throw new ValidationException("Error occurred while fetching village");
            }

        }
        return tsBudgetHoaResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("financialYearMaster.financialYear");
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
        Page<TsBudgetHoaDTO> tsBudgetHoaDTOS = tsBudgetHoaRepository.getSortedTsBudgetHoa(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",tsBudgetHoaDTOS);
        return convertPageableDTOToMapResponse(tsBudgetHoaDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TsBudgetHoaDTO> activeTsBudgetHoa) {
        Map<String, Object> response = new HashMap<>();

        List<TsBudgetHoaResponse> tsBudgetHoaResponses = activeTsBudgetHoa.getContent().stream()
                .map(tsBudgetHoa -> mapper.tsBudgetHoaDTOToObject(tsBudgetHoa,TsBudgetHoaResponse.class)).collect(Collectors.toList());
        response.put("tsBudgetHoa",tsBudgetHoaResponses);
        response.put("currentPage", activeTsBudgetHoa.getNumber());
        response.put("totalItems", activeTsBudgetHoa.getTotalElements());
        response.put("totalPages", activeTsBudgetHoa.getTotalPages());

        return response;
    }
}

