package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.EditScHeadAccountCategoryRequest;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.ScHeadAccountCategoryRequest;
import com.sericulture.masterdata.model.api.scHeadAccountCategory.ScHeadAccountCategoryResponse;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.EditScProgramApprovalMappingRequest;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.ScProgramApprovalMappingRequest;
import com.sericulture.masterdata.model.api.scProgramApprovalMapping.ScProgramApprovalMappingResponse;
import com.sericulture.masterdata.service.ScHeadAccountCategoryService;
import com.sericulture.masterdata.service.ScProgramApprovalMappingService;
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
@RequestMapping("/v1/scHeadAccountCategory")
public class ScHeadAccountCategoryController {
    @Autowired
    ScHeadAccountCategoryService scHeadAccountCategoryService;

    @Operation(summary = "Insert Sc Head Account Category Details", description = "Creates Sc Head Account Details in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Sc Head Account Category name should be more than 1 characters.\",\"label\":\"name\",\"locale\":null}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addScHeadAccountCategoryDetails(@RequestBody ScHeadAccountCategoryRequest scHeadAccountCategoryRequest){
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScHeadAccountCategoryResponse.class);

        rw.setContent(scHeadAccountCategoryService.insertScHeadAccountCategoryDetails(scHeadAccountCategoryRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"ScHeadAccountCategory\":[{\"id\":10,\"scHeadAccountCategoryId\":\"\"},{\"id\":11,\"scHeadAccountCategoryId\":\"scHeadAccountCategoryId \"},{\"id\":13,\"race\":\"Roof Type 2\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(scHeadAccountCategoryService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":1,\"scHeadAccountCategory\":[{\"id\":1,\"scHeadAccountCategoryId\":\"\"},{\"id\":2,\"scHeadAccountCategoryId\":\"race 1\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(scHeadAccountCategoryService.getPaginatedScHeadAccountCategoryWithJoin(PageRequest.of(pageNumber, size)));
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
    public ResponseEntity<?> deleteScHeadAccountCategoryDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(scHeadAccountCategoryService.deleteScHeadAccountCategoryDetails(id));
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
    public ResponseEntity<?> editScHeadAccountCategoryDetails(
            @RequestBody final EditScHeadAccountCategoryRequest editScHeadAccountCategoryRequest
    ) {
        ResponseWrapper<ScHeadAccountCategoryResponse> rw = ResponseWrapper.createWrapper(ScHeadAccountCategoryResponse.class);
        rw.setContent(scHeadAccountCategoryService.updateScHeadAccountCategoryDetails(editScHeadAccountCategoryRequest));
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
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScHeadAccountCategoryResponse.class);

        rw.setContent(scHeadAccountCategoryService.getById(id));
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
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScHeadAccountCategoryResponse.class);

        rw.setContent(scHeadAccountCategoryService.getByIdJoin(id));
        return ResponseEntity.ok(rw);
    }
    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"scHeadAccountCategory\":[{\"id\":10,\"scHeadAccountCategoryId\":\"\",\"scHeadAccountCategoryId\":1,},{\"id\":11,\"scHeadAccountCategoryId\":\"Shimoga\",\"scProgramApprovalId\":1,},{\"id\":13,\"scHeadAccountCategoryId\":\"Hubli\",\"scHeadAccountCategoryId\":1,}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(scHeadAccountCategoryService.getPaginatedScHeadAccountCategoryWithJoin(PageRequest.of(pageNumber, size)));
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
        rw.setContent(scHeadAccountCategoryService.searchByColumnAndSort(searchWithSortRequest));
        return ResponseEntity.ok(rw);
    }
}
