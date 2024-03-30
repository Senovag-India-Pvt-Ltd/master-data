package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scApprovalStage.ScApprovalStageResponse;
import com.sericulture.masterdata.model.api.scHeadAccount.EditScHeadAccountRequest;
import com.sericulture.masterdata.model.api.scHeadAccount.ScHeadAccountRequest;
import com.sericulture.masterdata.model.api.scHeadAccount.ScHeadAccountResponse;
import com.sericulture.masterdata.model.api.scVendorBank.ScVendorBankResponse;
import com.sericulture.masterdata.model.api.village.VillageResponse;
import com.sericulture.masterdata.model.dto.ScHeadAccountDTO;
import com.sericulture.masterdata.model.dto.ScVendorBankDTO;
import com.sericulture.masterdata.model.entity.ScApprovalStage;
import com.sericulture.masterdata.model.entity.ScHeadAccount;
import com.sericulture.masterdata.model.entity.Village;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScHeadAccountRepository;
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
public class ScHeadAccountService {
    @Autowired
    ScHeadAccountRepository scHeadAccountRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public ScHeadAccountResponse insertScHeadAccountDetails(ScHeadAccountRequest scHeadAccountRequest){
        ScHeadAccountResponse scHeadAccountResponse = new ScHeadAccountResponse();
        ScHeadAccount scHeadAccount = mapper.scHeadAccountObjectToEntity(scHeadAccountRequest,ScHeadAccount.class);
        validator.validate(scHeadAccount);
        List<ScHeadAccount> scHeadAccountList = scHeadAccountRepository.findByScHeadAccountName(scHeadAccountRequest.getScHeadAccountName());
        if(!scHeadAccountList.isEmpty() && scHeadAccountList.stream().filter(ScHeadAccount::getActive).findAny().isPresent()){
            scHeadAccountResponse.setError(true);
            scHeadAccountResponse.setError_description("ScHeadAccount name already exist");
        }
        else if(!scHeadAccountList.isEmpty() && scHeadAccountList.stream().filter(Predicate.not(ScHeadAccount::getActive)).findAny().isPresent()){
            //throw new ValidationException("Village name already exist with inactive state");
            scHeadAccountResponse.setError(true);
            scHeadAccountResponse.setError_description("ScHeadAccount name already exist with inactive state");
        }else {
            scHeadAccountResponse = mapper.scHeadAccountEntityToObject(scHeadAccountRepository.save(scHeadAccount), ScHeadAccountResponse.class);
            scHeadAccountResponse.setError(false);
        }
        return scHeadAccountResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScHeadAccountDetails(final Pageable pageable){
        return convertToMapResponse(scHeadAccountRepository.findByActiveOrderByScHeadAccountNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(scHeadAccountRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<ScHeadAccount> activeScHeadAccounts) {
        Map<String, Object> response = new HashMap<>();

        List<ScHeadAccountResponse> scHeadAccountResponses = activeScHeadAccounts.getContent().stream()
                .map(scHeadAccount -> mapper.scHeadAccountEntityToObject(scHeadAccount,ScHeadAccountResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccount",scHeadAccountResponses);
        response.put("currentPage", activeScHeadAccounts.getNumber());
        response.put("totalItems", activeScHeadAccounts.getTotalElements());
        response.put("totalPages", activeScHeadAccounts.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<ScHeadAccount> activeScHeadAccounts) {
        Map<String, Object> response = new HashMap<>();

        List<ScHeadAccountResponse> scHeadAccountResponses = activeScHeadAccounts.stream()
                .map(scHeadAccount -> mapper.scHeadAccountEntityToObject(scHeadAccount,ScHeadAccountResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccount",scHeadAccountResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScHeadAccountWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(scHeadAccountRepository.getByActiveOrderByScHeadAccountIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<ScHeadAccountDTO> activeScHeadAccount) {
        Map<String, Object> response = new HashMap<>();

        List<ScHeadAccountResponse> scHeadAccountResponses= activeScHeadAccount.getContent().stream()
                .map(scHeadAccount -> mapper.scHeadAccountDTOToObject(scHeadAccount,ScHeadAccountResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccount",scHeadAccountResponses);
        response.put("currentPage", activeScHeadAccount.getNumber());
        response.put("totalItems", activeScHeadAccount.getTotalElements());
        response.put("totalPages", activeScHeadAccount.getTotalPages());
        return response;
    }

    @Transactional
    public ScHeadAccountResponse deleteScHeadAccountDetails(long id) {
        ScHeadAccountResponse scHeadAccountResponse = new ScHeadAccountResponse();
        ScHeadAccount scHeadAccount = scHeadAccountRepository.findByScHeadAccountIdAndActive(id, true);
        if (Objects.nonNull(scHeadAccount)) {
            scHeadAccount.setActive(false);
            scHeadAccountResponse = mapper.scHeadAccountEntityToObject(scHeadAccountRepository.save(scHeadAccount), ScHeadAccountResponse.class);
            scHeadAccountResponse.setError(false);
        } else {
            scHeadAccountResponse.setError(true);
            scHeadAccountResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return scHeadAccountResponse;
    }

    @Transactional
    public ScHeadAccountResponse getById(int id){
        ScHeadAccountResponse scHeadAccountResponse = new ScHeadAccountResponse();
        ScHeadAccount scHeadAccount = scHeadAccountRepository.findByScHeadAccountIdAndActive(id,true);
        if(scHeadAccount == null){
            scHeadAccountResponse.setError(true);
            scHeadAccountResponse.setError_description("Invalid id");
        }else{
            scHeadAccountResponse =  mapper.scHeadAccountEntityToObject(scHeadAccount,ScHeadAccountResponse.class);
            scHeadAccountResponse.setError(false);
        }
        log.info("Entity is ",scHeadAccount);
        return scHeadAccountResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getScHeadAccountByScProgramId(Long scProgramId){
//        List<ScHeadAccount> scHeadAccountList = scHeadAccountRepository.findByScProgramIdAndActiveOrderByScHeadAccountName(scProgramId,true);
//        if(scHeadAccountList.isEmpty()){
//            throw new ValidationException("Invalid Id");
//        }
//        log.info("Entity is ",scHeadAccountList);
//        return convertListToMapResponse(scHeadAccountList);
//    }
//
//    private Map<String, Object> convertListToMapResponse(List<ScHeadAccount> scHeadAccountList) {
//        Map<String, Object> response = new HashMap<>();
//        List<ScHeadAccountResponse> scHeadAccountResponses = scHeadAccountList.stream()
//                .map(scHeadAccount -> mapper.scHeadAccountEntityToObject(scHeadAccount,ScHeadAccountResponse.class)).collect(Collectors.toList());
//        response.put("scHeadAccount",scHeadAccountResponses);
//        response.put("totalItems", scHeadAccountList.size());
//        return response;
//    }



    private Map<String, Object> convertListToMapResponse(List<ScHeadAccountDTO> scHeadAccountList) {
        Map<String, Object> response = new HashMap<>();
        List<ScHeadAccountResponse> scHeadAccountResponses = scHeadAccountList.stream()
                .map(scHeadAccount -> mapper.scHeadAccountDTOToObject(scHeadAccount,ScHeadAccountResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccount",scHeadAccountResponses);
        response.put("totalItems", scHeadAccountResponses.size());
        return response;
    }

    @Transactional
    public ScHeadAccountResponse getByIdJoin(int id){
        ScHeadAccountResponse scHeadAccountResponse = new ScHeadAccountResponse();
        ScHeadAccountDTO scHeadAccountDTO = scHeadAccountRepository.getByScHeadAccountIdAndActive(id,true);
        if(scHeadAccountDTO == null){
            scHeadAccountResponse.setError(true);
            scHeadAccountResponse.setError_description("Invalid id");
        } else {
            scHeadAccountResponse = mapper.scHeadAccountDTOToObject(scHeadAccountDTO, ScHeadAccountResponse.class);
            scHeadAccountResponse.setError(false);
        }
        log.info("Entity is ", scHeadAccountDTO);
        return scHeadAccountResponse;
    }
    @Transactional
    public ScHeadAccountResponse updateScHeadAccountDetails(EditScHeadAccountRequest scHeadAccountRequest) {
        ScHeadAccountResponse scHeadAccountResponse = new ScHeadAccountResponse();
        List<ScHeadAccount> scHeadAccountList = scHeadAccountRepository.findByScHeadAccountNameAndScHeadAccountIdIsNot(scHeadAccountRequest.getScHeadAccountName(),scHeadAccountRequest.getScHeadAccountId());
        if (scHeadAccountList.size() > 0) {
            scHeadAccountResponse.setError(true);
            scHeadAccountResponse.setError_description("ScHeadAccount already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        } else {

            ScHeadAccount scHeadAccount = scHeadAccountRepository.findByScHeadAccountIdAndActiveIn(scHeadAccountRequest.getScHeadAccountId(), Set.of(true, false));
            if (Objects.nonNull(scHeadAccount)) {
                scHeadAccount.setScHeadAccountName(scHeadAccountRequest.getScHeadAccountName());
                scHeadAccount.setScHeadAccountNameInKannada(scHeadAccountRequest.getScHeadAccountNameInKannada());
                scHeadAccount.setScSchemeDetailsId(scHeadAccountRequest.getScSchemeDetailsId());
                scHeadAccount.setActive(true);
                ScHeadAccount scHeadAccount1 = scHeadAccountRepository.save(scHeadAccount);
                scHeadAccountResponse = mapper.scHeadAccountEntityToObject(scHeadAccount1, ScHeadAccountResponse.class);
                scHeadAccountResponse.setError(false);
            } else {
                scHeadAccountResponse.setError(true);
                scHeadAccountResponse.setError_description("Error occurred while fetching ScHeadAccount");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return scHeadAccountResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("scHeadAccount.scHeadAccountName");
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
        Page<ScHeadAccountDTO> scHeadAccountDTOS = scHeadAccountRepository.getSortedScHeadAccount(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",scHeadAccountDTOS);
        return convertPageableDTOToMapResponse(scHeadAccountDTOS);
    }
    private Map<String, Object> convertPageableDTOToMapResponse(final Page<ScHeadAccountDTO> activeScHeadAccount) {
        Map<String, Object> response = new HashMap<>();

        List<ScHeadAccountResponse> scVendorBankResponses = activeScHeadAccount.getContent().stream()
                .map(scHeadAccount -> mapper.scHeadAccountDTOToObject(scHeadAccount,ScHeadAccountResponse.class)).collect(Collectors.toList());
        response.put("scHeadAccount",scVendorBankResponses);
        response.put("currentPage", activeScHeadAccount.getNumber());
        response.put("totalItems", activeScHeadAccount.getTotalElements());
        response.put("totalPages", activeScHeadAccount.getTotalPages());

        return response;
    }
}
