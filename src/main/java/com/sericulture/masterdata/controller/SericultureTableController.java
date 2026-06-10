package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.sericultureTable.EditSericultureTableRequest;
import com.sericulture.masterdata.model.api.sericultureTable.SericultureTableApprovalStageCheckboxResponse;
import com.sericulture.masterdata.model.api.sericultureTable.SericultureTableRequest;
import com.sericulture.masterdata.model.api.sericultureTable.SericultureTableResponse;
import com.sericulture.masterdata.service.SericultureTableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/sericultureTable")
public class SericultureTableController {

    @Autowired
    SericultureTableService sericultureTableService;

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

    @Operation(summary = "Insert SericultureTable Details", description = "Creates SericultureTable Details in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Validation error\",\"label\":\"name\",\"locale\":null}]}"))
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addSericultureTableDetails(@RequestBody SericultureTableRequest sericultureTableRequest) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(SericultureTableResponse.class);
        rw.setContent(sericultureTableService.insertSericultureTableDetails(sericultureTableRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getAllByActive(
            @RequestParam(defaultValue = "true") boolean isActive
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(sericultureTableService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = {
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
        rw.setContent(sericultureTableService.getPaginatedSericultureTableDetails(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSericultureTableDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(sericultureTableService.deleteSericultureTableDetails(id));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Object saved details"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/edit")
    public ResponseEntity<?> editSericultureTableDetails(
            @RequestBody final EditSericultureTableRequest editSericultureTableRequest
    ) {
        ResponseWrapper<SericultureTableResponse> rw = ResponseWrapper.createWrapper(SericultureTableResponse.class);
        rw.setContent(sericultureTableService.updateSericultureTableDetails(editSericultureTableRequest));
        return ResponseEntity.ok(rw);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(SericultureTableResponse.class);
        rw.setContent(sericultureTableService.getById(id));
        return ResponseEntity.ok(rw);
    }

    @Operation(summary = "Get Approval Stages Checkbox List by Scheme and SubScheme",
            description = "Returns all approval stages with checked=true for stages already configured for the given schemeId and subSchemeId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @GetMapping("/approval-stages-checkbox")
    public ResponseEntity<?> getApprovalStagesCheckbox(
            @RequestParam final Long schemeId,
            @RequestParam final Long subSchemeId
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(List.class);
        rw.setContent(sericultureTableService.getApprovalStagesCheckbox(schemeId, subSchemeId));
        return ResponseEntity.ok(rw);
    }
}
