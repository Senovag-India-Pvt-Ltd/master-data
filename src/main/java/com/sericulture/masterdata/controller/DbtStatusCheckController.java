package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.dbtStatusCheck.DbtStatusCheckRequest;
import com.sericulture.masterdata.model.api.dbtStatusCheck.DbtStatusCheckResponse;
import com.sericulture.masterdata.model.api.dbtStatusCheck.EditDbtStatusCheckRequest;
import com.sericulture.masterdata.model.api.dbtStatusCheck.DbtStatusCheckRequest;
import com.sericulture.masterdata.model.api.dbtStatusCheck.DbtStatusCheckResponse;
import com.sericulture.masterdata.model.api.dbtStatusCheck.EditDbtStatusCheckRequest;
import com.sericulture.masterdata.service.DbtStatusCheckService;
import com.sericulture.masterdata.service.DbtStatusCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/v1/dbtStatusCheck")
public class DbtStatusCheckController {
    @Autowired
    DbtStatusCheckService dbtStatusCheckService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("validationErrors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Operation(summary = "Insert DbtStatusCheck Details", description = "Creates DbtStatusCheck Details in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"DbtStatusCheck name should be more than 1 characters.\",\"label\":\"name\",\"locale\":null}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addDbtStatusCheckDetails(@Valid @RequestBody DbtStatusCheckRequest dbtStatusCheckRequest){
        ResponseWrapper rw = ResponseWrapper.createWrapper(DbtStatusCheckResponse.class);

        rw.setContent(dbtStatusCheckService.insertDbtStatusCheckDetails(dbtStatusCheckRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"dbtStatusCheck\":[{\"id\":10,\"dbtStatusCheckName\":\"\"},{\"id\":11,\"dbtStatusCheckName\":\"Karnataka\"},{\"id\":13,\"dbtStatusCheckName\":\"Kerala\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(dbtStatusCheckService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

//    @GetMapping("/list")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
//                    {
//                            @Content(mediaType = "application/json", schema =
//                            @Schema(example = "{\"content\":{\"totalItems\":6,\"dbtStatusCheck\":[{\"id\":10,\"dbtStatusCheckName\":\"\"},{\"id\":11,\"dbtStatusCheckName\":\"Karnataka\"},{\"id\":13,\"dbtStatusCheckName\":\"Kerala\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
//                    }),
//            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
//                    content =
//                            {
//                                    @Content(mediaType = "application/json", schema =
//                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
//                            }),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
//    })
//    public ResponseEntity<?> getPaginatedList(
//            @RequestParam(defaultValue = "0") final Integer pageNumber,
//            @RequestParam(defaultValue = "5") final Integer size
//    ) {
//        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
//        rw.setContent(dbtStatusCheckService.getPaginatedDbtStatusCheckDetails(PageRequest.of(pageNumber, size)));
//        return ResponseEntity.ok(rw);
//    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(example = "{\"content\":{\"totalItems\":6,\"dbtStatusCheck\":[{\"id\":10,\"dbtStatusCheckName\":\"\"},{\"id\":11,\"dbtStatusCheckName\":\"Karnataka\"},{\"id\":13,\"dbtStatusCheckName\":\"Kerala\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getPaginatedList(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(required = false) final Integer size
    ) {
        int pageSize = size == null ? Integer.MAX_VALUE : size;
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(dbtStatusCheckService.getPaginatedDbtStatusCheckDetails(PageRequest.of(pageNumber, pageSize)));
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
    public ResponseEntity<?> deleteDbtStatusCheckDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(dbtStatusCheckService.deleteDbtStatusCheckDetails(id));
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
    public ResponseEntity<?> editDbtStatusCheckDetails(
            @Valid @RequestBody final EditDbtStatusCheckRequest editDbtStatusCheckRequest
    ) {
        ResponseWrapper<DbtStatusCheckResponse> rw = ResponseWrapper.createWrapper(DbtStatusCheckResponse.class);
        rw.setContent(dbtStatusCheckService.updateDbtStatusCheckDetails(editDbtStatusCheckRequest));
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
        ResponseWrapper rw = ResponseWrapper.createWrapper(DbtStatusCheckResponse.class);

        rw.setContent(dbtStatusCheckService.getById(id));
        return ResponseEntity.ok(rw);
    }
}
