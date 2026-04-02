package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.helper.Util;
import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.userHierarchyMapping.EditUserHierarchyMappingRequest;
import com.sericulture.masterdata.model.api.userHierarchyMapping.UserHierarchyMappingRequest;
import com.sericulture.masterdata.model.api.userHierarchyMapping.UserHierarchyMappingResponse;
import com.sericulture.masterdata.service.UserHierarchyMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.io.FileInputStream;


@RestController
@RequestMapping("/v1/userHierarchyMapping")
public class UserHierarchyMappingController {

    @Autowired
    UserHierarchyMappingService userHierarchyMappingService;

    @Operation(summary = "Insert UserHierarchyMapping Details", description = "Creates UserHierarchyMapping Details in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"UserHierarchyMapping name should be more than 1 characters.\",\"label\":\"name\",\"locale\":null}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addUserHierarchyMappingDetails(@RequestBody UserHierarchyMappingRequest userHierarchyMappingRequest){
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserHierarchyMappingResponse.class);

        rw.setContent(userHierarchyMappingService.insertUserHierarchyMappingDetails(userHierarchyMappingRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"userHierarchyMappingId\":[{\"id\":10,\"userHierarchyMappingId\":\"\"},{\"id\":11,\"userHierarchyMappingId\":\"userHierarchyMapping 1\"},{\"id\":13,\"userHierarchyMapping\":\"UserHierarchyMapping 2\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getAllByActive(
            @RequestParam(defaultValue = "true") boolean isActive
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userHierarchyMappingService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":1,\"userHierarchyMapping\":[{\"id\":1,\"race\":\"\"},{\"id\":2,\"userHierarchyMapping\":\"userHierarchyMapping 1\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getPaginatedList(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userHierarchyMappingService.getPaginatedUserHierarchyMappingDetails(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserHierarchyMappingDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(userHierarchyMappingService.deleteUserHierarchyMappingDetails(id));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/edit")
    public ResponseEntity<?> editUserHierarchyMappingDetails(
            @RequestBody final EditUserHierarchyMappingRequest editUserHierarchyMappingRequest
    ) {
        ResponseWrapper<UserHierarchyMappingResponse> rw = ResponseWrapper.createWrapper(UserHierarchyMappingResponse.class);
        rw.setContent(userHierarchyMappingService.updateUserHierarchyMappingDetails(editUserHierarchyMappingRequest));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserHierarchyMappingResponse.class);

        rw.setContent(userHierarchyMappingService.getById(id));
        return ResponseEntity.ok(rw);
    }
    @GetMapping("/getByReporteeUserMasterId/{reporteeUserMasterId}")
    public ResponseEntity<?> getByReporteeUserMasterId(
            @PathVariable final Integer reporteeUserMasterId
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(UserHierarchyMappingResponse.class);

        rw.setContent(userHierarchyMappingService.getByReporteeUserMasterId(reporteeUserMasterId));
        return ResponseEntity.ok(rw);
    }
    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - fetched successfully", content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":2,\"userHierarchyMapping\":[{\"userHierarchyMappingId\":1,\"employeeId\":101,\"employeeName\":\"Ravi\",\"managerId\":201,\"managerName\":\"Suresh\"},{\"userHierarchyMappingId\":2,\"employeeId\":102,\"employeeName\":\"Anil\",\"managerId\":202,\"managerName\":\"Mahesh\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getPaginatedListWithJoin(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);

        rw.setContent(
                userHierarchyMappingService.getPaginatedEmployeeManagerList(
                        PageRequest.of(pageNumber, size)
                )
        );

        return ResponseEntity.ok(rw);
    }

    @PostMapping("/completed-report")
    public ResponseEntity<InputStreamResource> completedReport() {
        try {
            FileInputStream fis = userHierarchyMappingService.downloadCompletedList();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=completed_user_hierarchy_" + Util.getISTLocalDate() + ".xlsx")
                    .header(HttpHeaders.CONTENT_TYPE,
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(new InputStreamResource(fis));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/pending-report")
    public ResponseEntity<?> pendingReport() {
        try {
            FileInputStream fis = userHierarchyMappingService.downloadPendingList();

            InputStreamResource resource = new InputStreamResource(fis);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=pending_user_hierarchy_" + Util.getISTLocalDate() + ".xlsx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    e.getMessage().getBytes(StandardCharsets.UTF_8),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Ok Response"),
//            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
//                    content =
//                            {
//                                    @Content(mediaType = "application/json", schema =
//                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
//                            }),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
//    })
//    @GetMapping("/get-join/{id}")
//    public ResponseEntity<?> getByIdJoin(
//            @PathVariable final Integer id
//    ) {
//        ResponseWrapper rw = ResponseWrapper.createWrapper(UserHierarchyMappingResponse.class);
//
//        rw.setContent(userHierarchyMappingService.getByIdJoin(id));
//        return ResponseEntity.ok(rw);
//    }
//    @GetMapping("/list-with-join")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
//                    {
//                            @Content(mediaType = "application/json", schema =
//                            @Schema(example = "{\"content\":{\"totalItems\":6,\"userHierarchyMapping\":[{\"id\":10,\"userHierarchyMappingId\":\"\",\"userHierarchyMappingId\":1,},{\"id\":11,\"userHierarchyMappingId\":\"Shimoga\",\"userHierarchyMappingId\":1,},{\"id\":13,\"userHierarchyMappingId\":\"Hubli\",\"userHierarchyMappingId\":1,}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
//                    }),
//            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
//                    content =
//                            {
//                                    @Content(mediaType = "application/json", schema =
//                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
//                            }),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
//    })
//    public ResponseEntity<?> getPaginatedListWithJoin(
//            @RequestParam(defaultValue = "0") final Integer pageNumber,
//            @RequestParam(defaultValue = "5") final Integer size
//    ) {
//        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
//        rw.setContent(userHierarchyMappingService.getPaginatedUserHierarchyMappingDetailsWithJoin(PageRequest.of(pageNumber, size)));
//        return ResponseEntity.ok(rw);
//    }
}
