package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.scHeadAccount.EditScHeadAccountRequest;
import com.sericulture.masterdata.model.api.scHeadAccount.ScHeadAccountRequest;
import com.sericulture.masterdata.model.api.scHeadAccount.ScHeadAccountResponse;
import com.sericulture.masterdata.model.entity.ScHeadAccount;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.ScHeadAccountRepository;
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
public class ScHeadAccountService {
    @Autowired
    ScHeadAccountRepository scHeadAccountRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ScHeadAccountResponse getScHeadAccountDetails(String scHeadAccountName){
        ScHeadAccount scHeadAccount = null;
        if(scHeadAccount==null){
            scHeadAccount = scHeadAccountRepository.findByScHeadAccountNameAndActive(scHeadAccountName,true);
        }
        log.info("Entity is ",scHeadAccount);
        return mapper.scHeadAccountEntityToObject(scHeadAccount,ScHeadAccountResponse.class);
    }

    @Transactional
    public ScHeadAccountResponse insertScHeadAccountDetails(ScHeadAccountRequest scHeadAccountRequest){
        ScHeadAccount scHeadAccount = mapper.scHeadAccountObjectToEntity(scHeadAccountRequest,ScHeadAccount.class);
        validator.validate(scHeadAccount);
        List<ScHeadAccount> scHeadAccountList = scHeadAccountRepository.findByScHeadAccountName(scHeadAccountRequest.getScHeadAccountName());
        if(!scHeadAccountList.isEmpty() && scHeadAccountList.stream().filter(ScHeadAccount::getActive).findAny().isPresent()){
            throw new ValidationException("ScHeadAccount name already exist");
        }
        if(!scHeadAccountList.isEmpty() && scHeadAccountList.stream().filter(Predicate.not(ScHeadAccount::getActive)).findAny().isPresent()){
            throw new ValidationException("ScHeadAccount name already exist with inactive state");
        }
        return mapper.scHeadAccountEntityToObject(scHeadAccountRepository.save(scHeadAccount),ScHeadAccountResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedScHeadAccountDetails(final Pageable pageable){
        return convertToMapResponse(scHeadAccountRepository.findByActiveOrderByScHeadAccountIdAsc( true, pageable));
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

    @Transactional
    public void deleteScHeadAccountDetails(long id) {
        ScHeadAccount scHeadAccount = scHeadAccountRepository.findByScHeadAccountIdAndActive(id, true);
        if (Objects.nonNull(scHeadAccount)) {
            scHeadAccount.setActive(false);
            scHeadAccountRepository.save(scHeadAccount);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public ScHeadAccountResponse getById(int id){
        ScHeadAccount scHeadAccount = scHeadAccountRepository.findByScHeadAccountIdAndActive(id,true);
        if(scHeadAccount == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",scHeadAccount);
        return mapper.scHeadAccountEntityToObject(scHeadAccount,ScHeadAccountResponse.class);
    }

    @Transactional
    public ScHeadAccountResponse updateScHeadAccountDetails(EditScHeadAccountRequest scHeadAccountRequest){
        List<ScHeadAccount> scHeadAccountList = scHeadAccountRepository.findByScHeadAccountName(scHeadAccountRequest.getScHeadAccountName());
        if(scHeadAccountList.size()>0){
            throw new ValidationException("ScHeadAccount already exists with this name, duplicates are not allowed.");
        }

        ScHeadAccount scHeadAccount = scHeadAccountRepository.findByScHeadAccountIdAndActiveIn(scHeadAccountRequest.getScHeadAccountId(), Set.of(true,false));
        if(Objects.nonNull(scHeadAccount)){
            scHeadAccount.setScHeadAccountName(scHeadAccountRequest.getScHeadAccountName());
            scHeadAccount.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching Head Account");
        }
        return mapper.scHeadAccountEntityToObject(scHeadAccountRepository.save(scHeadAccount),ScHeadAccountResponse.class);
    }
}
