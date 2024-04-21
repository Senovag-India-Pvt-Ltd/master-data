package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scSubSchemeMapping.EditScSubSchemeMappingRequest;
import com.sericulture.masterdata.model.api.scSubSchemeMapping.ScSubSchemeMappingRequest;
import com.sericulture.masterdata.model.api.scSubSchemeMapping.ScSubSchemeMappingResponse;
import com.sericulture.masterdata.service.ScSubSchemeMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("v1/scSubSchemeMapping")
public class ScSubSchemeMappingController {
    @Autowired
    ScSubSchemeMappingService scSubSchemeMappingService;

    @Operation(summary = "Insert ScSubSchemeMapping ", description = "Creates ScSubSchemeMapping  in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Sc SubSchemeMapping name should be more than 1 characters.\",\"label\":\"name\",\"locale\":null}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addScSubSchemeMappingDetails(@RequestBody ScSubSchemeMappingRequest scSubSchemeMappingRequest){
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScSubSchemeMappingResponse.class);

        rw.setContent(scSubSchemeMappingService.insertScSubSchemeMappingDetails(scSubSchemeMappingRequest));
        return ResponseEntity.ok(rw);
    }

//    @GetMapping("/get-by-sc-head-account-id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
//                    {
//                            @Content(mediaType = "application/json", schema =
//                            @Schema(example = "{\"content\":{\"totalItems\":6,\"taluk\":[{\"id\":10,\"talukName\":\"\",\"stateId\":1,},{\"id\":11,\"talukName\":\"Shimoga\",\"stateId\":1,},{\"id\":13,\"talukName\":\"Hubli\",\"stateId\":1,}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
//                    }),
//            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
//                    content =
//                            {
//                                    @Content(mediaType = "application/json", schema =
//                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
//                            }),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
//    })
//    public ResponseEntity<?> getByScHeadAccountIdAndScCategoryIdAndScSubSchemeDetailsId(
//            @RequestParam final Long scHeadAccountId,
//            @RequestParam final Long scCategoryId,
//            @RequestParam final Long scSubSchemeDetailsId
//    ) {
//        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
//        rw.setContent(scSubSchemeMappingService.getScSubSchemeMappingByScHeadAccountIdAndScCategoryIdAndScSubSchemeDetailsId(scHeadAccountId, scCategoryId,scSubSchemeDetailsId));
//        return ResponseEntity.ok(rw);
//    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"ScSubSchemeMapping\":[{\"id\":10,\"scSubSchemeMappingId\":\"\"},{\"id\":11,\"scSubSchemeMappingId\":\"scSubSchemeMappingId \"},{\"id\":13,\"race\":\"Roof Type 2\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(scSubSchemeMappingService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":1,\"scSubSchemeMapping\":[{\"id\":1,\"scSubSchemeMappingId\":\"\"},{\"id\":2,\"scSubSchemeMappingId\":\"race 1\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(scSubSchemeMappingService.getPaginatedScSubSchemeMappingWithJoin(PageRequest.of(pageNumber, size)));
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
    public ResponseEntity<?> deleteScSubSchemeMappingDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(scSubSchemeMappingService.deleteScSubSchemeMappingDetails(id));
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
    public ResponseEntity<?> editScSubSchemeMappingDetails(
            @RequestBody final EditScSubSchemeMappingRequest editScSubSchemeMappingRequest
    ) {
        ResponseWrapper<ScSubSchemeMappingResponse> rw = ResponseWrapper.createWrapper(ScSubSchemeMappingResponse.class);
        rw.setContent(scSubSchemeMappingService.updateScSubSchemeMappingDetails(editScSubSchemeMappingRequest));
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
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScSubSchemeMappingResponse.class);

        rw.setContent(scSubSchemeMappingService.getById(id));
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
    @GetMapping("/get-join/{id}")
    public ResponseEntity<?> getByIdJoin(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScSubSchemeMappingResponse.class);

        rw.setContent(scSubSchemeMappingService.getByIdJoin(id));
        return ResponseEntity.ok(rw);
    }
    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"scSubSchemeMapping\":[{\"id\":10,\"scSubSchemeMappingId\":\"\",\"scSubSchemeMappingId\":1,},{\"id\":11,\"scSubSchemeMappingId\":\"Shimoga\",\"scSubSchemeMappingId\":1,},{\"id\":13,\"scSubSchemeMappingId\":\"Hubli\",\"scSubSchemeMappingId\":1,}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(scSubSchemeMappingService.getPaginatedScSubSchemeMappingWithJoin(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    //    @GetMapping("/get-by-sc-program-id/{scProgramId}")
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
//    public ResponseEntity<?> getByScProgramId(
//            @PathVariable final Integer scProgramId
//    ) {
//        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
//        rw.setContent(scProgramApprovalMappingService.getByScProgramId(scProgramId));
//        return ResponseEntity.ok(rw);
//    }
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
    @PostMapping("/search")
    public ResponseEntity<?> search(
            @Valid @RequestBody final SearchWithSortRequest searchWithSortRequest
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(scSubSchemeMappingService.searchByColumnAndSort(searchWithSortRequest));
        return ResponseEntity.ok(rw);
    }

}
