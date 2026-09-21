package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.DbtCodeFinancialYearRequest;
import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.DbtCodeFinancialYearResponse;
import com.sericulture.masterdata.model.api.dbtCodeFinancialYear.EditDbtCodeFinancialYearRequest;
import com.sericulture.masterdata.service.DbtCodeFinancialYearService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Single generic CRUD surface for sc_dbt_code_financial_year, reused across all master types
 * (SC_COMPONENT, SC_SUB_SCHEME_DETAILS, SCHEME_QUOTA, SC_CATEGORY_SCHEME_MAPPING, SC_CATEGORY,
 * SC_SCHEME_DETAILS) instead of one controller per master.
 */
@RestController
@RequestMapping("/v1/dbtCodeFinancialYear")
public class DbtCodeFinancialYearController {

    @Autowired
    DbtCodeFinancialYearService dbtCodeFinancialYearService;

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

    @Operation(summary = "List financial-year DBT codes", description = "Lists all financial-year specific DBT codes configured for a master type + parent id")
    @GetMapping("/list")
    public ResponseEntity<?> list(
            @RequestParam final String masterType,
            @RequestParam final Long parentId
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(dbtCodeFinancialYearService.listByMasterTypeAndParent(masterType, parentId));
        return ResponseEntity.ok(rw);
    }

    @Operation(summary = "Add financial-year DBT code", description = "Adds a financial-year specific DBT code for a scheme/component/sub-scheme/quota/category-mapping item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"DBT Code is mandatory.\",\"label\":\"dbtCode\",\"locale\":null}]}"))
                    })
    })
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody DbtCodeFinancialYearRequest request) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(DbtCodeFinancialYearResponse.class);
        rw.setContent(dbtCodeFinancialYearService.insert(request));
        return ResponseEntity.ok(rw);
    }

    @Operation(summary = "Edit financial-year DBT code", description = "Updates an existing financial-year specific DBT code")
    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody EditDbtCodeFinancialYearRequest request) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(DbtCodeFinancialYearResponse.class);
        rw.setContent(dbtCodeFinancialYearService.update(request));
        return ResponseEntity.ok(rw);
    }

    @Operation(summary = "Delete financial-year DBT code", description = "Soft-deletes a financial-year specific DBT code")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(DbtCodeFinancialYearResponse.class);
        rw.setContent(dbtCodeFinancialYearService.delete(id));
        return ResponseEntity.ok(rw);
    }
}
