package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.scTscGeoMapping.EditScTscGeoMappingRequest;
import com.sericulture.masterdata.model.api.scTscGeoMapping.ScTscGeoMappingRequest;
import com.sericulture.masterdata.model.api.scTscGeoMapping.ScTscGeoMappingResponse;
import com.sericulture.masterdata.service.ScTscGeoMappingService;
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
@RequestMapping("/v1/sc-tsc-geo-mapping")
public class ScTscGeoMappingController {

    @Autowired
    ScTscGeoMappingService scTscGeoMappingService;

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

    @PostMapping("/add")
    public ResponseEntity<?> addScTscGeoMapping(@Valid @RequestBody ScTscGeoMappingRequest request) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScTscGeoMappingResponse.class);
        rw.setContent(scTscGeoMappingService.insertScTscGeoMappingDetails(request));
        return ResponseEntity.ok(rw);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editScTscGeoMapping(@Valid @RequestBody EditScTscGeoMappingRequest request) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScTscGeoMappingResponse.class);
        rw.setContent(scTscGeoMappingService.updateScTscGeoMappingDetails(request));
        return ResponseEntity.ok(rw);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteScTscGeoMapping(@PathVariable final Long id) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScTscGeoMappingResponse.class);
        rw.setContent(scTscGeoMappingService.deleteScTscGeoMappingDetails(id));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable final Long id) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ScTscGeoMappingResponse.class);
        rw.setContent(scTscGeoMappingService.getById(id));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllByActive(@RequestParam(defaultValue = "true") boolean isActive) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(scTscGeoMappingService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list-with-join")
    public ResponseEntity<?> getPaginatedListWithJoin(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(scTscGeoMappingService.getPaginatedScTscGeoMappingDetailsWithJoin(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }
}
