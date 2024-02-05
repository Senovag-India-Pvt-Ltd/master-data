package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.marketMaster.MarketMasterResponse;
import com.sericulture.masterdata.model.api.rpRoleAssociation.RpRoleAssociationResponse;
import com.sericulture.masterdata.model.api.trModeMaster.EditTrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterRequest;
import com.sericulture.masterdata.model.api.trModeMaster.TrModeMasterResponse;
import com.sericulture.masterdata.model.api.trSchedule.*;
import com.sericulture.masterdata.model.api.trTrainee.TrTraineeResponse;
import com.sericulture.masterdata.model.dto.MarketMasterDTO;
import com.sericulture.masterdata.model.dto.RpRoleAssociationDTO;
import com.sericulture.masterdata.model.dto.TrScheduleDTO;
import com.sericulture.masterdata.model.dto.TrTraineeDTO;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import com.sericulture.masterdata.model.entity.TrSchedule;
import com.sericulture.masterdata.model.entity.TrTrainee;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.TrModeMasterRepository;
import com.sericulture.masterdata.repository.TrScheduleRepository;
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
public class TrScheduleService {
    @Autowired
    TrScheduleRepository trScheduleRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


    @Transactional
    public TrScheduleResponse insertTrScheduleDetails(TrScheduleRequest trScheduleRequest){
        TrScheduleResponse trScheduleResponse = new TrScheduleResponse();
        TrSchedule trSchedule = mapper.trScheduleObjectToEntity(trScheduleRequest,TrSchedule.class);
        validator.validate(trSchedule);
//        List<TrSchedule> trScheduleList= trScheduleRepository.findByTrName(trScheduleRequest.getTrName());
//        if(!trScheduleList.isEmpty() && trScheduleList.stream().filter(TrSchedule::getActive).findAny().isPresent()){
//            trScheduleResponse.setError(true);
//            trScheduleResponse.setError_description("TrSchedule name already exist");
//        }
//        else if(!trScheduleList.isEmpty() && trScheduleList.stream().filter(Predicate.not(TrSchedule::getActive)).findAny().isPresent()){
//            trScheduleResponse.setError(true);
//            trScheduleResponse.setError_description("trSchedule name already exist with inactive state");
//        }else {
//            trScheduleResponse = mapper.trScheduleEntityToObject(trScheduleRepository.save(trSchedule), TrScheduleResponse.class);
//            trScheduleResponse.setError(false);
//        }
        return mapper.trScheduleEntityToObject(trScheduleRepository.save(trSchedule), TrScheduleResponse.class);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrScheduleDetails(final Pageable pageable){
        return convertToMapResponse(trScheduleRepository.findByActiveOrderByTrNameAsc( true,pageable ));

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(trScheduleRepository.findByActiveOrderByTrNameAsc(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<TrSchedule> activeTrSchedules) {
        Map<String, Object> response = new HashMap<>();

        List<TrScheduleResponse> trScheduleResponses = activeTrSchedules.getContent().stream()
                .map(trSchedule -> mapper.trScheduleEntityToObject(trSchedule,TrScheduleResponse.class)).collect(Collectors.toList());
        response.put("trSchedule",trScheduleResponses);
        response.put("currentPage", activeTrSchedules.getNumber());
        response.put("totalItems", activeTrSchedules.getTotalElements());
        response.put("totalPages", activeTrSchedules.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<TrSchedule> activeTrSchedules) {
        Map<String, Object> response = new HashMap<>();

        List<TrScheduleResponse> trScheduleResponses = activeTrSchedules.stream()
                .map(trSchedule -> mapper.trScheduleEntityToObject(trSchedule,TrScheduleResponse.class)).collect(Collectors.toList());
        response.put("trSchedule",trScheduleResponses);
        return response;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getPaginatedTrScheduleDetailsWithJoin(final Pageable pageable){
        return convertDTOToMapResponse(trScheduleRepository.getByActiveOrderByTrScheduleIdAsc( true, pageable));
    }

    private Map<String, Object> convertDTOToMapResponse(final Page<TrScheduleDTO> activeTrSchedules) {
        Map<String, Object> response = new HashMap<>();

        List<TrScheduleResponse> trScheduleResponses = activeTrSchedules.getContent().stream()
                .map(trSchedule -> mapper.trScheduleDTOToObject(trSchedule,TrScheduleResponse.class)).collect(Collectors.toList());
        response.put("trSchedule",trScheduleResponses);
        response.put("currentPage", activeTrSchedules.getNumber());
        response.put("totalItems", activeTrSchedules.getTotalElements());
        response.put("totalPages", activeTrSchedules.getTotalPages());
        return response;
    }


    @Transactional
    public TrScheduleResponse deleteTrScheduleDetails(long id) {

        TrScheduleResponse trScheduleResponse= new TrScheduleResponse();
        TrSchedule trSchedule = trScheduleRepository.findByTrScheduleIdAndActive(id, true);
        if (Objects.nonNull(trSchedule)) {
            trSchedule.setActive(false);
            trScheduleResponse = mapper.trScheduleEntityToObject(trScheduleRepository.save(trSchedule), TrScheduleResponse.class);
            trScheduleResponse.setError(false);
        } else {
            trScheduleResponse.setError(true);
            trScheduleResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return trScheduleResponse;
    }

    @Transactional
    public TrScheduleResponse getById(int id){
        TrScheduleResponse trScheduleResponse = new TrScheduleResponse();
        TrSchedule trSchedule = trScheduleRepository.findByTrScheduleIdAndActive(id,true);
        if(trSchedule == null){
            trScheduleResponse.setError(true);
            trScheduleResponse.setError_description("Invalid id");
        }else{
            trScheduleResponse =  mapper.trScheduleEntityToObject(trSchedule,TrScheduleResponse.class);
            trScheduleResponse.setError(false);
        }
        log.info("Entity is ",trSchedule);
        return trScheduleResponse;
    }

    @Transactional
    public TrScheduleResponse getByIdJoin(int id) {
        TrScheduleResponse trScheduleResponse= new TrScheduleResponse();
        TrScheduleDTO trScheduleDTO = trScheduleRepository.getByTrScheduleIdAndActive(id, true);
        if (trScheduleDTO == null) {
            // throw new ValidationException("Invalid Id");
            trScheduleResponse.setError(true);
            trScheduleResponse.setError_description("Invalid id");
        } else {
            trScheduleResponse = mapper.trScheduleDTOToObject(trScheduleDTO, TrScheduleResponse.class);
            trScheduleResponse.setError(false);
        }
        log.info("Entity is ", trScheduleDTO);
        return trScheduleResponse;
    }

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Map<String,Object> getByUserMasterIdJoin(int userMasterId){
//        Map<String, Object> response = new HashMap<>();
//        List<TrScheduleDTO> trScheduleDTO = trScheduleRepository.getByUserMasterIdAndActive(userMasterId, true);
//        if(trScheduleDTO.isEmpty()){
//            response.put("error","Error");
//            response.put("error_description","Invalid id");
//            return response;
//        }else {
//            response = convertListDTOToMapResponse(trScheduleDTO);
//            return response;
//
//        }
//
//    }
//
//    private Map<String, Object> convertListDTOToMapResponse(List<TrScheduleDTO> trScheduleDTOList) {
//        Map<String, Object> response = new HashMap<>();
//        List<TrScheduleResponse> trScheduleResponses = trScheduleDTOList.stream()
//                .map(trScheduleDTO -> mapper.trScheduleDTOToObject(trScheduleDTO, TrScheduleResponse.class)).collect(Collectors.toList());
//        response.put("trSchedule", trScheduleResponses);
//        response.put("totalItems", trScheduleDTOList.size());
//        return response;
//    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public  Map<String, Object> getByUserMasterId(Long userMasterId) {
        Map<String, Object> response = new HashMap<>();
        List<TrScheduleDTO> trScheduleDTOS = trScheduleRepository.getByUserMasterIdAndActive(userMasterId,true);
        if(trScheduleDTOS.size()<=0){
            response.put("error","Error");
            response.put("error_description","No records found");
        }else {
            log.info("Entity is ", trScheduleDTOS);
            response = convertDTOToMapResponse(trScheduleDTOS);
        }
        return response;
    }

    private Map<String, Object> convertDTOToMapResponse(List<TrScheduleDTO> trScheduleDTOS) {
        Map<String, Object> response = new HashMap<>();
        List<TrScheduleResponse> trScheduleResponses = trScheduleDTOS.stream()
                .map(trScheduleDTO -> mapper.trScheduleDTOToObject(trScheduleDTO,TrScheduleResponse.class)).collect(Collectors.toList());
        response.put("trSchedule",trScheduleResponses);
        response.put("totalItems", trScheduleDTOS.size());
        return response;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> getByUserMasterId(int UserMasterId) {
        Map<String, Object> response = new HashMap<>();
        List<TrSchedule> trScheduleList = trScheduleRepository.findByUserMasterIdAndActive(UserMasterId, true);
        if (trScheduleList.isEmpty()) {
            response.put("error", "Error");
            response.put("error_description", "Invalid id");
            return response;
        } else {
            response = convertListToMapResponse(trScheduleList);
            return response;
//        return convertListToMapResponse(reelerList);
        }
    }
    private Map<String, Object> convertListToMapResponse(List<TrSchedule> trScheduleList) {
        Map<String, Object> response = new HashMap<>();
        List<TrScheduleResponse> trScheduleResponses = trScheduleList.stream()
                .map(trSchedule -> mapper.trScheduleEntityToObject(trSchedule,TrScheduleResponse.class)).collect(Collectors.toList());
        response.put("trSchedule",trScheduleResponses);
        response.put("totalItems", trScheduleList.size());
        return response;
    }

    @Transactional
    public TrScheduleResponse updateTrScheduleDetails(EditTrScheduleRequest trScheduleRequest){

        TrScheduleResponse trScheduleResponse = new TrScheduleResponse();
//        List<TrSchedule> trScheduleList = trScheduleRepository.findByTrName(trScheduleRequest.getTrName());
//        if(trScheduleList.size()>0){
//            trScheduleResponse.setError(true);
//            trScheduleResponse.setError_description("TrSchedule already exists, duplicates are not allowed.");
//            // throw new ValidationException("Village already exists, duplicates are not allowed.");
//        }else {


            TrSchedule trSchedule= trScheduleRepository.findByTrScheduleIdAndActiveIn(trScheduleRequest.getTrScheduleId(), Set.of(true,false));
            if(Objects.nonNull(trSchedule)){
                trSchedule.setTrName(trScheduleRequest.getTrName());
                trSchedule.setTrInstitutionMasterId(trScheduleRequest.getTrInstitutionMasterId());
                trSchedule.setUserMasterId(trScheduleRequest.getUserMasterId());
                trSchedule.setTrStakeholderType(trScheduleRequest.getTrStakeholderType());
                trSchedule.setTrGroupMasterId(trScheduleRequest.getTrGroupMasterId());
                trSchedule.setTrProgramMasterId(trScheduleRequest.getTrProgramMasterId());
                trSchedule.setTrCourseMasterId(trScheduleRequest.getTrCourseMasterId());
                trSchedule.setTrModeMasterId(trScheduleRequest.getTrModeMasterId());
                trSchedule.setTrStartDate(trScheduleRequest.getTrStartDate());
                trSchedule.setTrDuration(trScheduleRequest.getTrDuration());
                trSchedule.setTrPeriod(trScheduleRequest.getTrPeriod());
                trSchedule.setTrDateOfCompletion(trScheduleRequest.getTrDateOfCompletion());
                trSchedule.setTrUploadPath(trScheduleRequest.getTrUploadPath());
                trSchedule.setTrNoOfParticipant(trScheduleRequest.getTrNoOfParticipant());

                trSchedule.setActive(true);
                TrSchedule trSchedule1 = trScheduleRepository.save(trSchedule);
                trScheduleResponse = mapper.trScheduleEntityToObject(trSchedule1, TrScheduleResponse.class);
                trScheduleResponse.setError(false);
            } else {
                trScheduleResponse.setError(true);
                trScheduleResponse.setError_description("Error occurred while fetching state");
                // throw new ValidationException("Error occurred while fetching village");
            }

        return trScheduleResponse;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Map<String,Object> searchByColumnAndSort(SearchWithSortRequest searchWithSortRequest){
        if(searchWithSortRequest.getSearchText() == null || searchWithSortRequest.getSearchText().equals("")){
            searchWithSortRequest.setSearchText("%%");
        }else{
            searchWithSortRequest.setSearchText("%" + searchWithSortRequest.getSearchText() + "%");
        }
        if(searchWithSortRequest.getSortColumn() == null || searchWithSortRequest.getSortColumn().equals("")){
            searchWithSortRequest.setSortColumn("userMaster.username");
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
        Page<TrScheduleDTO> trScheduleDTOS = trScheduleRepository.getSortedTrSchedules(searchWithSortRequest.getJoinColumn(),searchWithSortRequest.getSearchText(),true, pageable);
        log.info("Entity is ",trScheduleDTOS);
        return convertPageableDTOToMapResponse(trScheduleDTOS);
    }

    private Map<String, Object> convertPageableDTOToMapResponse(final Page<TrScheduleDTO> activeTrSchedules) {
        Map<String, Object> response = new HashMap<>();

        List<TrScheduleResponse> trScheduleResponses = activeTrSchedules.getContent().stream()
                .map(trSchedule -> mapper.trScheduleDTOToObject(trSchedule,TrScheduleResponse.class)).collect(Collectors.toList());
        response.put("trSchedule",trScheduleResponses);
        response.put("currentPage", activeTrSchedules.getNumber());
        response.put("totalItems", activeTrSchedules.getTotalElements());
        response.put("totalPages", activeTrSchedules.getTotalPages());

        return response;
    }

}
