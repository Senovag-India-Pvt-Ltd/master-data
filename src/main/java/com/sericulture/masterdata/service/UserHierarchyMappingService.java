package com.sericulture.masterdata.service;

import com.sericulture.masterdata.helper.Util;
import com.sericulture.masterdata.model.api.userHierarchyMapping.EditUserHierarchyMappingRequest;
import com.sericulture.masterdata.model.api.userHierarchyMapping.UserHierarchyMappingRequest;
import com.sericulture.masterdata.model.api.userHierarchyMapping.UserHierarchyMappingResponse;
//import com.sericulture.masterdata.model.dto.UserHierarchyMappingDTO;
import com.sericulture.masterdata.model.entity.UserHierarchyMapping;
import com.sericulture.masterdata.model.mapper.Mapper;
import com.sericulture.masterdata.repository.UserHierarchyMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

@Service
@Slf4j
public class UserHierarchyMappingService {

    @Autowired
    UserHierarchyMappingRepository userHierarchyMappingRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    CustomValidator validator;


//    @Transactional
//    public UserHierarchyMappingResponse insertUserHierarchyMappingDetails(UserHierarchyMappingRequest userHierarchyMappingRequest){
//        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
//        UserHierarchyMapping userHierarchyMapping = mapper.userHierarchyMappingObjectToEntity(userHierarchyMappingRequest,UserHierarchyMapping.class);
//        validator.validate(userHierarchyMapping);
////        List<RpPageRoot> rpPageRootList = rpPageRootRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
////        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(RpPageRoot::getActive).findAny().isPresent()){
////            throw new ValidationException("RpPageRoot name already exist");
////        }
////        if(!rpPageRootList.isEmpty() && rpPageRootList.stream().filter(Predicate.not(RpPageRoot::getActive)).findAny().isPresent()){
////            throw new ValidationException("RpPageRoot name already exist with inactive state");
////        }
//
//        return mapper.userHierarchyMappingEntityToObject(userHierarchyMappingRepository.save(userHierarchyMapping), UserHierarchyMappingResponse.class);
//    }

@Transactional
public UserHierarchyMappingResponse insertUserHierarchyMappingDetails(UserHierarchyMappingRequest userHierarchyMappingRequest) {
    UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();

    if (userHierarchyMappingRequest.getReporteeUserMasterId()
            .equals(userHierarchyMappingRequest.getReportToUserMasterId())) {
        throw new IllegalArgumentException("Employee cannot report to themselves");
    }
    // Convert request DTO to entity
    UserHierarchyMapping userHierarchyMapping = mapper.userHierarchyMappingObjectToEntity(userHierarchyMappingRequest, UserHierarchyMapping.class);

    // Validate input data
    validator.validate(userHierarchyMapping);

    // Check if a record exists for the given ReporteeUserMasterId
    UserHierarchyMapping existingMapping = userHierarchyMappingRepository.findByReporteeUserMasterIdAndActive(userHierarchyMappingRequest.getReporteeUserMasterId(), true);

    if (existingMapping != null) {
        // Update the existing record
        existingMapping.setReportToUserMasterId(userHierarchyMapping.getReportToUserMasterId());

        // Save updated entity
        userHierarchyMapping = userHierarchyMappingRepository.save(existingMapping);
    } else {
        // Create a new record if no existing mapping is found
        userHierarchyMapping = userHierarchyMappingRepository.save(userHierarchyMapping);
    }

    // Convert entity back to response DTO and return
    return mapper.userHierarchyMappingEntityToObject(userHierarchyMapping, UserHierarchyMappingResponse.class);
}



    public Map<String,Object> getPaginatedUserHierarchyMappingDetails(final Pageable pageable){
        return convertToMapResponse(userHierarchyMappingRepository.findByActiveOrderByUserHierarchyMappingIdAsc( true, pageable));
    }

    public Map<String,Object> getAllByActive(boolean isActive){
        return convertListEntityToMapResponse(userHierarchyMappingRepository.findByActive(isActive));
    }

    private Map<String, Object> convertToMapResponse(final Page<UserHierarchyMapping> activeUserHierarchyMappings) {
        Map<String, Object> response = new HashMap<>();

        List<UserHierarchyMappingResponse> userHierarchyMappings = activeUserHierarchyMappings.getContent().stream()
                .map(userHierarchyMapping -> mapper.userHierarchyMappingEntityToObject(userHierarchyMapping,UserHierarchyMappingResponse.class)).collect(Collectors.toList());
        response.put("userHierarchyMapping",userHierarchyMappings);
        response.put("currentPage", activeUserHierarchyMappings.getNumber());
        response.put("totalItems", activeUserHierarchyMappings.getTotalElements());
        response.put("totalPages", activeUserHierarchyMappings.getTotalPages());

        return response;
    }

    private Map<String, Object> convertListEntityToMapResponse(final List<UserHierarchyMapping> activeUserHierarchyMappings) {
        Map<String, Object> response = new HashMap<>();

        List<UserHierarchyMappingResponse> userHierarchyMappingResponses = activeUserHierarchyMappings.stream()
                .map(userHierarchyMapping -> mapper.userHierarchyMappingEntityToObject(userHierarchyMapping,UserHierarchyMappingResponse.class)).collect(Collectors.toList());
        response.put("userHierarchyMapping",userHierarchyMappingResponses);
        return response;
    }

    @Transactional
    public UserHierarchyMappingResponse deleteUserHierarchyMappingDetails(long id) {
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByUserHierarchyMappingIdAndActive(id, true);
        if (Objects.nonNull(userHierarchyMapping)) {
            userHierarchyMapping.setActive(false);
            userHierarchyMappingResponse = mapper.userHierarchyMappingEntityToObject(userHierarchyMappingRepository.save(userHierarchyMapping), UserHierarchyMappingResponse.class);
            userHierarchyMappingResponse.setError(false);
        } else {
            userHierarchyMappingResponse.setError(true);
            userHierarchyMappingResponse.setError_description("Invalid Id");
            // throw new ValidationException("Invalid Id");
        }
        return userHierarchyMappingResponse;
    }

    public UserHierarchyMappingResponse getById(int id){
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByUserHierarchyMappingIdAndActive(id,true);
        if(userHierarchyMapping == null){
            userHierarchyMappingResponse.setError(true);
            userHierarchyMappingResponse.setError_description("Invalid id");
        }else{
            userHierarchyMappingResponse =  mapper.userHierarchyMappingEntityToObject(userHierarchyMapping,UserHierarchyMappingResponse.class);
            userHierarchyMappingResponse.setError(false);
        }
        log.info("Entity is ",userHierarchyMapping);
        return userHierarchyMappingResponse;
    }

    public UserHierarchyMappingResponse getByReporteeUserMasterId(int reporteeUserMasterId){
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByReporteeUserMasterIdAndActive(reporteeUserMasterId,true);
        if(userHierarchyMapping == null){
            userHierarchyMappingResponse.setError(true);
            userHierarchyMappingResponse.setError_description("Invalid id");
        }else{
            userHierarchyMappingResponse =  mapper.userHierarchyMappingEntityToObject(userHierarchyMapping,UserHierarchyMappingResponse.class);
            userHierarchyMappingResponse.setError(false);
        }
        log.info("Entity is ",userHierarchyMapping);
        return userHierarchyMappingResponse;
    }

    @Transactional
    public UserHierarchyMappingResponse updateUserHierarchyMappingDetails(EditUserHierarchyMappingRequest userHierarchyMappingRequest){
        UserHierarchyMappingResponse userHierarchyMappingResponse = new UserHierarchyMappingResponse();
//        List<RpRoleAssociation> rpRoleAssociationList = rpRoleAssociationRepository.findByRpPageRootName(rpPageRootRequest.getRpPageRootName());
//        if(rpPageRootList.size()>0){
//            throw new ValidationException("RpPageRoot already exists with this name, duplicates are not allowed.");
//        }

        UserHierarchyMapping userHierarchyMapping = userHierarchyMappingRepository.findByUserHierarchyMappingIdAndActiveIn(userHierarchyMappingRequest.getUserHierarchyMappingId(), Set.of(true,false));
        if(Objects.nonNull(userHierarchyMapping)){
            userHierarchyMapping.setUserHierarchyMappingId(userHierarchyMappingRequest.getUserHierarchyMappingId());
            userHierarchyMapping.setReporteeUserMasterId(userHierarchyMappingRequest.getReporteeUserMasterId());
            userHierarchyMapping.setReportToUserMasterId(userHierarchyMappingRequest.getReportToUserMasterId());
            userHierarchyMapping.setActive(true);
            UserHierarchyMapping userHierarchyMapping1 = userHierarchyMappingRepository.save(userHierarchyMapping);
            userHierarchyMappingResponse = mapper.userHierarchyMappingEntityToObject(userHierarchyMapping1, UserHierarchyMappingResponse.class);
            userHierarchyMappingResponse.setError(false);
        } else {
            userHierarchyMappingResponse.setError(true);
            userHierarchyMappingResponse.setError_description("Error occurred while fetching userMaster");
            // throw new ValidationException("Error occurred while fetching village");
        }

        return userHierarchyMappingResponse;

    }

    public Map<String, Object> getPaginatedEmployeeManagerList(Pageable pageable) {

        List<Object[]> result = userHierarchyMappingRepository.getEmployeeManagerList();

        List<Map<String, Object>> list = result.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();

            map.put("userHierarchyMappingId", row[0]);
            map.put("employeeId", row[1]);
            map.put("employeeName", row[2]);
            map.put("employeeDesignationId", row[3]);
            map.put("employeeDistrictId", row[4]);
            map.put("managerId", row[5]);
            map.put("managerName", row[6]);
            map.put("managerDesignationId", row[7]);
            map.put("managerDistrictId", row[8]);
            return map;
        }).collect(Collectors.toList());

        // ✅ SAME pagination style as village
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());

        List<Map<String, Object>> paginatedList =
                (start > list.size()) ? new ArrayList<>() : list.subList(start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("userHierarchyMapping", paginatedList);
        response.put("currentPage", pageable.getPageNumber());
        response.put("totalItems", list.size());
        response.put("totalPages", (int) Math.ceil((double) list.size() / pageable.getPageSize()));

        return response;
    }


    public FileInputStream downloadCompletedList() throws Exception {

        List<Object[]> list = userHierarchyMappingRepository.getCompletedList();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Completed");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Employee ID");
        header.createCell(1).setCellValue("Employee Name");
        header.createCell(2).setCellValue("Manager ID");
        header.createCell(3).setCellValue("Manager Name");

        int rowNum = 1;

        for (Object[] row : list) {
            Row dataRow = sheet.createRow(rowNum++);

            dataRow.createCell(0).setCellValue(row[0] != null ? row[0].toString() : "");
            dataRow.createCell(1).setCellValue(row[1] != null ? row[1].toString() : "");
            dataRow.createCell(2).setCellValue(row[2] != null ? row[2].toString() : "");
            dataRow.createCell(3).setCellValue(row[3] != null ? row[3].toString() : "");
        }

        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        String userHome = System.getProperty("user.home");
        String path = userHome + "/Downloads/completed_user_hierarchy_" + Util.getISTLocalDate() + ".xlsx";

        FileOutputStream fos = new FileOutputStream(path);
        workbook.write(fos);
        fos.close();
        workbook.close();

        return new FileInputStream(path);
    }

    public FileInputStream downloadPendingList() throws Exception {

        List<Object[]> list = userHierarchyMappingRepository.getPendingList();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pending");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Employee ID");
        header.createCell(1).setCellValue("Employee Name");

        int rowNum = 1;

        for (Object[] row : list) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(row[0] != null ? row[0].toString() : "");
            dataRow.createCell(1).setCellValue(row[1] != null ? row[1].toString() : "");
        }

        for (int i = 0; i < 2; i++) {
            sheet.autoSizeColumn(i);
        }

        // ✅ SAME STYLE AS CHOWKI
        String userHome = System.getProperty("user.home");
        String directoryPath = Paths.get(userHome, "Downloads").toString();
        Files.createDirectories(Paths.get(directoryPath));

        Path filePath = Paths.get(directoryPath,
                "pending_user_hierarchy_" + Util.getISTLocalDate() + ".xlsx");

        // ✅ WRITE FIRST
        FileOutputStream fos = new FileOutputStream(filePath.toString());
        workbook.write(fos);
        fos.close();
        workbook.close();

        // ✅ THEN READ
        return new FileInputStream(filePath.toString());
    }
}