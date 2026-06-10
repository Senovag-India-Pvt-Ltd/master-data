package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.govtAccount.EditGovtAccountRequest;
import com.sericulture.masterdata.model.api.govtAccount.GovtAccountRequest;
import com.sericulture.masterdata.model.api.govtAccount.GovtAccountResponse;
import com.sericulture.masterdata.model.entity.GovtAccount;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.GovtAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GovtAccountService {

    @Autowired
    GovtAccountRepository govtAccountRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional
    public GovtAccountResponse insertGovtAccountDetails(GovtAccountRequest govtAccountRequest) {
        GovtAccountResponse govtAccountResponse = new GovtAccountResponse();
        GovtAccount govtAccount = mapper.govtAccountObjectToEntity(govtAccountRequest, GovtAccount.class);
        validator.validate(govtAccount);
        GovtAccount existing = govtAccountRepository.findByGovtAccountNumberAndActive(govtAccountRequest.getGovtAccountNumber(), true);
        if (existing != null) {
            govtAccountResponse.setError(true);
            govtAccountResponse.setError_description("Govt account number already exists");
        } else {
            govtAccountResponse = mapper.govtAccountEntityToObject(govtAccountRepository.save(govtAccount), GovtAccountResponse.class);
            govtAccountResponse.setError(false);
        }
        return govtAccountResponse;
    }

public Map<String, Object> getAllByActive(boolean isActive) {
        return convertListEntityToMapResponse(govtAccountRepository.findByActive(isActive));
    }

private Map<String, Object> convertListEntityToMapResponse(final List<GovtAccount> activeGovtAccounts) {
        Map<String, Object> response = new HashMap<>();
        List<GovtAccountResponse> govtAccountResponses = activeGovtAccounts.stream()
                .map(govtAccount -> mapper.govtAccountEntityToObject(govtAccount, GovtAccountResponse.class))
                .collect(Collectors.toList());
        response.put("govtAccount", govtAccountResponses);
        return response;
    }

    @Transactional
    public GovtAccountResponse deleteGovtAccountDetails(long id) {
        GovtAccountResponse govtAccountResponse = new GovtAccountResponse();
        GovtAccount govtAccount = govtAccountRepository.findByGovtAccountIdAndActive(id, true);
        if (Objects.nonNull(govtAccount)) {
            govtAccount.setActive(false);
            govtAccountResponse = mapper.govtAccountEntityToObject(govtAccountRepository.save(govtAccount), GovtAccountResponse.class);
            govtAccountResponse.setError(false);
        } else {
            govtAccountResponse.setError(true);
            govtAccountResponse.setError_description("Invalid Id");
        }
        return govtAccountResponse;
    }

    public GovtAccountResponse getById(int id) {
        GovtAccountResponse govtAccountResponse = new GovtAccountResponse();
        GovtAccount govtAccount = govtAccountRepository.findByGovtAccountIdAndActive(id, true);
        if (govtAccount == null) {
            govtAccountResponse.setError(true);
            govtAccountResponse.setError_description("Invalid id");
        } else {
            govtAccountResponse = mapper.govtAccountEntityToObject(govtAccount, GovtAccountResponse.class);
            govtAccountResponse.setError(false);
        }
        log.info("Entity is ", govtAccount);
        return govtAccountResponse;
    }

    @Transactional
    public GovtAccountResponse updateGovtAccountDetails(EditGovtAccountRequest govtAccountRequest) {
        GovtAccountResponse govtAccountResponse = new GovtAccountResponse();
        List<GovtAccount> existing = govtAccountRepository.findByGovtAccountNumberAndActiveIn(
                govtAccountRequest.getGovtAccountNumber(), Set.of(true, false));
        if (existing.stream().anyMatch(a -> !a.getGovtAccountId().equals(govtAccountRequest.getGovtAccountId()) && a.getActive())) {
            govtAccountResponse.setError(true);
            govtAccountResponse.setError_description("Govt account number already exists, duplicates are not allowed.");
        } else {
            GovtAccount govtAccount = govtAccountRepository.findByGovtAccountIdAndActiveIn(
                    govtAccountRequest.getGovtAccountId(), Set.of(true, false));
            if (Objects.nonNull(govtAccount)) {
                govtAccount.setGovtAccountNumber(govtAccountRequest.getGovtAccountNumber());
                govtAccount.setBankName(govtAccountRequest.getBankName());
                govtAccount.setBranch(govtAccountRequest.getBranch());
                govtAccount.setIfscCode(govtAccountRequest.getIfscCode());
                govtAccount.setActive(true);
                govtAccountResponse = mapper.govtAccountEntityToObject(govtAccountRepository.save(govtAccount), GovtAccountResponse.class);
                govtAccountResponse.setError(false);
            } else {
                govtAccountResponse.setError(true);
                govtAccountResponse.setError_description("Error occurred while fetching govt account");
            }
        }
        return govtAccountResponse;
    }
}