package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.armCalculation.ArmCalculationRequest;
import com.sericulture.masterdata.model.api.armCalculation.ArmCalculationResponse;
import com.sericulture.masterdata.model.api.armCalculation.ArmUnitPriceResponse;
import com.sericulture.masterdata.model.api.armCalculation.EditArmCalculationRequest;
import com.sericulture.masterdata.service.ArmCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/armCalculation")
public class ArmCalculationController {

    @Autowired
    ArmCalculationService armCalculationService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            errors.put(fieldName, error.getDefaultMessage());
        });
        response.put("validationErrors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ── ADD ──────────────────────────────────────────────────────────────────
    @Operation(summary = "Add ARM Calculation record")
    @PostMapping("/add")
    public ResponseEntity<?> addArmCalculation(@Valid @RequestBody ArmCalculationRequest request) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ArmCalculationResponse.class);
        rw.setContent(armCalculationService.insertArmCalculationDetails(request));
        return ResponseEntity.ok(rw);
    }

    // ── LIST (paginated) ──────────────────────────────────────────────────────
    @Operation(summary = "Get paginated ARM Calculation list")
    @GetMapping("/list")
    public ResponseEntity<?> getArmCalculationList(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int size) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(armCalculationService.getPaginatedArmCalculationDetails(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    // ── GET ALL ───────────────────────────────────────────────────────────────
    @Operation(summary = "Get all active ARM Calculation records")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllArmCalculation() {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(armCalculationService.getAllByActive(true));
        return ResponseEntity.ok(rw);
    }

    // ── GET BY CATEGORY + COMPONENT ──────────────────────────────────────────
    @Operation(summary = "Get ARM Calculation by scCategory and Component")
    @GetMapping("/get-by-category-and-component")
    public ResponseEntity<?> getByCategoryAndComponent(
            @RequestParam Long scCategoryId,
            @RequestParam Long componentId) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(armCalculationService.getByCategoryAndComponent(scCategoryId, componentId));
        return ResponseEntity.ok(rw);
    }

    // ── GET UNIT PRICE (sum unit cost by armEnds + category) ─────────────────
    @Operation(summary = "Calculate total unit price for given ARM Ends and Category")
    @GetMapping("/get-unit-price")
    public ResponseEntity<?> getUnitPrice(
            @RequestParam String armEnds,
            @RequestParam Long scCategoryId) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ArmUnitPriceResponse.class);
        rw.setContent(armCalculationService.getUnitPriceByEndsAndCategory(armEnds, scCategoryId));
        return ResponseEntity.ok(rw);
    }

    // ── GET BY ARM ENDS + CATEGORY ───────────────────────────────────────────
    @Operation(summary = "Get ARM Calculation items by armEnds and scCategory")
    @GetMapping("/get-by-arm-ends-and-category")
    public ResponseEntity<?> getByArmEndsAndCategory(
            @RequestParam String armEnds,
            @RequestParam Long scCategoryId) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(armCalculationService.getByArmEndsAndCategory(armEnds, scCategoryId));
        return ResponseEntity.ok(rw);
    }

    // ── GET BY CATEGORY ───────────────────────────────────────────────────────
    @Operation(summary = "Get ARM Calculation by scCategory")
    @GetMapping("/get-by-category")
    public ResponseEntity<?> getByCategory(@RequestParam Long scCategoryId) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(armCalculationService.getByCategory(scCategoryId));
        return ResponseEntity.ok(rw);
    }

    // ── GET BY ID ─────────────────────────────────────────────────────────────
    @Operation(summary = "Get ARM Calculation by ID")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ArmCalculationResponse.class);
        rw.setContent(armCalculationService.getById(id));
        return ResponseEntity.ok(rw);
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    @Operation(summary = "Update ARM Calculation record")
    @PostMapping("/edit")
    public ResponseEntity<?> editArmCalculation(@Valid @RequestBody EditArmCalculationRequest request) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ArmCalculationResponse.class);
        rw.setContent(armCalculationService.updateArmCalculationDetails(request));
        return ResponseEntity.ok(rw);
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    @Operation(summary = "Delete ARM Calculation record")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArmCalculation(@PathVariable long id) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ArmCalculationResponse.class);
        rw.setContent(armCalculationService.deleteArmCalculationDetails(id));
        return ResponseEntity.ok(rw);
    }
}
