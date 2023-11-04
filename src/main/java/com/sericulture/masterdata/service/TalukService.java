package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.district.EditTalukRequest;
import com.sericulture.masterdata.model.api.taluk.TalukRequest;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.taluk.TalukRequest;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.api.taluk.TalukRequest;
import com.sericulture.masterdata.model.api.taluk.TalukResponse;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.entity.Taluk;
import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.exceptions.ValidationException;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TalukRepository;
import com.sericulture.masterdata.repository.TalukRepository;
import com.sericulture.masterdata.repository.TalukRepository;
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
public class TalukService {

    @Autowired
    TalukRepository talukRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TalukResponse getTalukDetails(String talukName){
        Taluk taluk = null;
        if(taluk==null){
            taluk = talukRepository.findByTalukNameAndActive(talukName,true);
        }
        log.info("Entity is ",taluk);
        return mapper.talukEntityToObject(taluk,TalukResponse.class);
    }

    @Transactional
    public TalukResponse insertTalukDetails(TalukRequest talukRequest){
        Taluk taluk = mapper.talukObjectToEntity(talukRequest,Taluk.class);
        validator.validate(taluk);
        List<Taluk> talukList = talukRepository.findByTalukName(talukRequest.getTalukName());
        if(!talukList.isEmpty() && talukList.stream().filter(Taluk::getActive).findAny().isPresent()){
            throw new ValidationException("Taluk name already exist with this state");
        }
        if(!talukList.isEmpty() && talukList.stream().filter(Predicate.not(Taluk::getActive)).findAny().isPresent()){
            throw new ValidationException("Taluk name already exist with inactive taluk with this state");
        }
        //Hard coded values, please remove once it is corrected
        taluk.setDistrictId(1L);
        taluk.setStateId(1L);
        return mapper.talukEntityToObject(talukRepository.save(taluk),TalukResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTalukDetails(final Pageable pageable){
        return convertToMapResponse(talukRepository.findByActiveOrderByTalukIdAsc( true, pageable));
    }

    private Map<String, Object> convertToMapResponse(final Page<Taluk> activeTaluks) {
        Map<String, Object> response = new HashMap<>();

        List<TalukResponse> talukResponses = activeTaluks.getContent().stream()
                .map(taluk -> mapper.talukEntityToObject(taluk,TalukResponse.class)).collect(Collectors.toList());
        response.put("taluk",talukResponses);
        response.put("currentPage", activeTaluks.getNumber());
        response.put("totalItems", activeTaluks.getTotalElements());
        response.put("totalPages", activeTaluks.getTotalPages());

        return response;
    }

    @Transactional
    public void deleteTalukDetails(long id) {
        Taluk taluk = talukRepository.findByTalukIdAndActive(id, true);
        if (Objects.nonNull(taluk)) {
            taluk.setActive(false);
            talukRepository.save(taluk);
        } else {
            throw new ValidationException("Invalid Id");
        }
    }

    @Transactional
    public TalukResponse getById(int id){
        Taluk taluk = talukRepository.findByTalukIdAndActive(id,true);
        if(taluk == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",taluk);
        return mapper.talukEntityToObject(taluk,TalukResponse.class);
    }

    @Transactional
    public TalukResponse getTalukByDistrictId(long districtId){
        Taluk taluk = talukRepository.findByDistrictIdAndActive(districtId,true);
        if(taluk == null){
            throw new ValidationException("Invalid Id");
        }
        log.info("Entity is ",taluk);
        return mapper.talukEntityToObject(taluk,TalukResponse.class);
    }

    @Transactional
    public TalukResponse updateTalukDetails(EditTalukRequest talukRequest){
        List<Taluk> talukList = talukRepository.findByTalukName(talukRequest.getTalukName());
        if(talukList.size()>0){
            throw new ValidationException("Taluk already exists with this state, duplicates are not allowed.");
        }

        Taluk taluk = talukRepository.findByTalukIdAndActiveIn(talukRequest.getTalukId(), Set.of(true,false));
        if(Objects.nonNull(taluk)){
            taluk.setStateId(talukRequest.getStateId());
            taluk.setDistrictId(talukRequest.getDistrictId());
            taluk.setTalukId(talukRequest.getTalukId());
            taluk.setTalukName(talukRequest.getTalukName());
            taluk.setActive(true);
        }else{
            throw new ValidationException("Error occurred while fetching taluk");
        }
        return mapper.talukEntityToObject(talukRepository.save(taluk),TalukResponse.class);
    }

}