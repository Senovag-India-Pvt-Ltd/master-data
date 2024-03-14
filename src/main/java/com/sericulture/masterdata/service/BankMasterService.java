package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.bankMaster.BankMasterRequest;
import com.sericulture.masterdata.model.api.bankMaster.BankMasterResponse;
import com.sericulture.masterdata.model.api.bankMaster.EditBankMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.EditTrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterRequest;
import com.sericulture.masterdata.model.api.trProgramMaster.TrProgramMasterResponse;
import com.sericulture.masterdata.model.entity.BankMaster;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.BankMasterRepository;
import com.sericulture.masterdata.repository.TrProgramMasterRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
@Slf4j
public class BankMasterService {
    @Autowired
    BankMasterRepository bankMasterRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;



    @Transactional
    public BankMasterResponse insertBankMasterDetails(BankMasterRequest bankMasterRequest){
        BankMasterResponse bankMasterResponse = new BankMasterResponse();
        BankMaster bankMaster = mapper.bankMasterObjectToEntity(bankMasterRequest,BankMaster.class);
        validator.validate(bankMaster);
        List<BankMaster> bankMasterList = bankMasterRepository.findByBankNameAndBankNameInKannada(bankMasterRequest.getBankName(), bankMasterRequest.getBankNameInKannada());
        if(!bankMasterList.isEmpty() && bankMasterList.stream().filter( BankMaster::getActive).findAny().isPresent()){
            bankMasterResponse.setError(true);
            bankMasterResponse.setError_description("Bank name already exist");
//        }
//        else if(! trProgramMasterList.isEmpty() && trProgramMasterList.stream().filter(Predicate.not( TrProgramMaster::getActive)).findAny().isPresent()){
//            trProgramMasterResponse.setError(true);
//            trProgramMasterResponse.setError_description("Tr Program name already exist with inactive state");
        }else {
            bankMasterResponse  = mapper.bankMasterEntityToObject( bankMasterRepository.save(bankMaster), BankMasterResponse.class);
            bankMasterResponse.setError(false);
        }
        return bankMasterResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedBankMasterDetails(final Pageable pageable){
        return convertToMapResponse(bankMasterRepository.findByActiveOrderByBankNameAsc( true, pageable));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(bankMasterRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<BankMaster> activeBankMasters) {
        Map<String, Object> response = new HashMap<>();

        List<BankMasterResponse> bankMasterResponses= activeBankMasters.getContent().stream()
                .map(bankMaster -> mapper.bankMasterEntityToObject(bankMaster,BankMasterResponse.class)).collect(Collectors.toList());
        response.put("bankMaster",bankMasterResponses);
        response.put("currentPage", activeBankMasters.getNumber());
        response.put("totalItems", activeBankMasters.getTotalElements());
        response.put("totalPages", activeBankMasters.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<BankMaster> activeBankMasters) {
        Map<String, Object> response = new HashMap<>();

        List<BankMasterResponse> bankMasterResponses= activeBankMasters.stream()
                .map(bankMaster -> mapper.bankMasterEntityToObject(bankMaster,BankMasterResponse.class)).collect(Collectors.toList());
        response.put("bankMaster",bankMasterResponses);
        return response;
    }

    @Transactional
    public BankMasterResponse deleteBankMasterDetails(long id) {

        BankMasterResponse bankMasterResponse = new BankMasterResponse();
        BankMaster bankMaster= bankMasterRepository.findByBankMasterIdAndActive(id, true);
        if (Objects.nonNull(bankMaster)) {
            bankMaster.setActive(false);
            bankMasterResponse= mapper.bankMasterEntityToObject(bankMasterRepository.save(bankMaster), BankMasterResponse.class);
            bankMasterResponse.setError(false);
        } else {
            bankMasterResponse.setError(true);
            bankMasterResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return bankMasterResponse;
    }

    @Transactional
    public BankMasterResponse getById(int id){
        BankMasterResponse bankMasterResponse = new BankMasterResponse();
        BankMaster bankMaster= bankMasterRepository.findByBankMasterIdAndActive(id, true);
        if(bankMaster== null){
            bankMasterResponse.setError(true);
            bankMasterResponse.setError_description("Invalid id");
        }else{
            bankMasterResponse =  mapper.bankMasterEntityToObject(bankMaster, BankMasterResponse.class);
            bankMasterResponse.setError(false);
        }
        log.info("Entity is ",bankMaster);
        return bankMasterResponse;
    }

    @Transactional
    public BankMasterResponse updateBankMastersDetails(EditBankMasterRequest bankMasterRequest){

        BankMasterResponse bankMasterResponse = new BankMasterResponse();
        List<BankMaster> bankMasterList = bankMasterRepository. findByActiveAndBankNameAndBankNameInKannada(true,bankMasterRequest.getBankName(), bankMasterRequest.getBankNameInKannada());
        if(bankMasterList.size()>0){
            bankMasterResponse.setError(true);
            bankMasterResponse.setError_description("Bank already exists, duplicates are not allowed.");
            // throw new ValidationException("Village already exists, duplicates are not allowed.");
        }else {


            BankMaster bankMaster = bankMasterRepository.findByBankMasterIdAndActiveIn(bankMasterRequest.getBankMasterId(), Set.of(true,false));
            if(Objects.nonNull(bankMaster)){
                bankMaster.setBankName( bankMasterRequest.getBankName());
                bankMaster.setBankNameInKannada( bankMasterRequest.getBankNameInKannada());
                bankMaster.setActive(true);
                BankMaster bankMaster1= bankMasterRepository.save(bankMaster);
                bankMasterResponse = mapper.bankMasterEntityToObject(bankMaster1, BankMasterResponse.class);
                bankMasterResponse.setError(false);
            } else {
                bankMasterResponse.setError(true);
                bankMasterResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }
        }
        return bankMasterResponse;
    }

}
