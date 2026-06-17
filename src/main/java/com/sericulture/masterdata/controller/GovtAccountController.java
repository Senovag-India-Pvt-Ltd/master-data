package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.govtAccount.EditGovtAccountRequest;
import com.sericulture.masterdata.model.api.govtAccount.GovtAccountRequest;
import com.sericulture.masterdata.model.api.govtAccount.GovtAccountResponse;
import com.sericulture.masterdata.service.GovtAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/govtAccount")
public class GovtAccountController {

    @Autowired
    GovtAccountService govtAccountService;

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

    @Operation(summary = "Insert Govt Account Details", description = "Creates Govt Account Details in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Govt account number already exists\",\"label\":\"govtAccountNumber\",\"locale\":null}]}"))
                    }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addGovtAccountDetails(@Valid @RequestBody GovtAccountRequest govtAccountRequest) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(GovtAccountResponse.class);
        rw.setContent(govtAccountService.insertGovtAccountDetails(govtAccountRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getAllByActive(
            @RequestParam(defaultValue = "true") boolean isActive
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(govtAccountService.getAllByActive(isActive));
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
    public ResponseEntity<?> deleteGovtAccountDetails(@PathVariable final Integer id) {
        ResponseWrapper<GovtAccountResponse> rw = ResponseWrapper.createWrapper(GovtAccountResponse.class);
        rw.setContent(govtAccountService.deleteGovtAccountDetails(id));
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
    public ResponseEntity<?> editGovtAccountDetails(@Valid @RequestBody final EditGovtAccountRequest editGovtAccountRequest) {
        ResponseWrapper<GovtAccountResponse> rw = ResponseWrapper.createWrapper(GovtAccountResponse.class);
        rw.setContent(govtAccountService.updateGovtAccountDetails(editGovtAccountRequest));
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
    public ResponseEntity<?> getById(@PathVariable final Integer id) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(GovtAccountResponse.class);
        rw.setContent(govtAccountService.getById(id));
        return ResponseEntity.ok(rw);
    }
}