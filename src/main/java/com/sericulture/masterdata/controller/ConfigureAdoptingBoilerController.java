package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.configureAdoptingBoiler.ConfigureAdoptingBoilerRequest;
import com.sericulture.masterdata.model.api.configureAdoptingBoiler.ConfigureAdoptingBoilerResponse;
import com.sericulture.masterdata.model.api.configureAdoptingBoiler.EditConfigureAdoptingBoilerRequest;
import com.sericulture.masterdata.service.ConfigureAdoptingBoilerService;
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
import java.util.Map;
@RestController
@Validated
@RequestMapping("/v1/configureAdoptingBoiler")
public class ConfigureAdoptingBoilerController {
    @Autowired
    private ConfigureAdoptingBoilerService configureAdoptingBoilerService;

    /**
     * ✅ Handle Validation Errors
     */
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

    /**
     * ✅ Add Configure Adopting Boiler
     */
    @PostMapping("/add")
    @Operation(summary = "Insert Configure Adopting Boiler Details", description = "Creates a new Configure Adopting Boiler record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Field cannot be empty\",\"label\":\"name\"}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> addConfigureAdoptingBoilerDetails(@Valid @RequestBody ConfigureAdoptingBoilerRequest request) {
        ResponseWrapper<ConfigureAdoptingBoilerResponse> rw = ResponseWrapper.createWrapper(ConfigureAdoptingBoilerResponse.class);
        rw.setContent(configureAdoptingBoilerService.insertConfigureAdoptingBoilerDetails(request));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Get All Active
     */
    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - no data found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getAllByActive(@RequestParam(defaultValue = "true") boolean isActive) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureAdoptingBoilerService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Paginated List
     */
    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - no data found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getPaginatedList(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureAdoptingBoilerService.getPaginatedConfigureAdoptingBoilerDetails(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Delete Configure Adopting Boiler
     */
    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> deleteConfigureAdoptingBoiler(@PathVariable final Long id) {
        ResponseWrapper<ConfigureAdoptingBoilerResponse> rw = ResponseWrapper.createWrapper(ConfigureAdoptingBoilerResponse.class);
        rw.setContent(configureAdoptingBoilerService.deleteConfigureAdoptingBoiler(id));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Edit Configure Adopting Boiler
     */
    @PostMapping("/edit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> editConfigureAdoptingBoiler(@Valid @RequestBody final EditConfigureAdoptingBoilerRequest editRequest) {
        ResponseWrapper<ConfigureAdoptingBoilerResponse> rw = ResponseWrapper.createWrapper(ConfigureAdoptingBoilerResponse.class);
        rw.setContent(configureAdoptingBoilerService.updateConfigureAdoptingBoilerDetails(editRequest));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Paginated List With Join
     */
    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok - Paginated Configure Adopting Boiler List"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getPaginatedListWithJoin(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureAdoptingBoilerService.getPaginatedConfigureAdoptingBoilerWithJoin(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Get By ID With Join
     */
    @GetMapping("/get-by-id-join/{id}")
    @Operation(summary = "Get Configure Adopting Boiler by ID (with joins)", description = "Returns a Configure Adopting Boiler by ID including related details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getByIdWithJoin(@PathVariable final Long id) {
        ResponseWrapper<ConfigureAdoptingBoilerResponse> rw = ResponseWrapper.createWrapper(ConfigureAdoptingBoilerResponse.class);
        ConfigureAdoptingBoilerResponse response = configureAdoptingBoilerService.getConfigureAdoptingBoilerByIdWithJoin(id);
        rw.setContent(response);
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Get By ID
     */
    @GetMapping("/get/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getById(@PathVariable final Long id) {
        ResponseWrapper<ConfigureAdoptingBoilerResponse> rw = ResponseWrapper.createWrapper(ConfigureAdoptingBoilerResponse.class);
        rw.setContent(configureAdoptingBoilerService.getById(id));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/findByBoilerInKgAndComponentTypeIdAndComponentIdAndCategoryIdAndActive")
    public ResponseEntity<?> findByBoilerInKgAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(
            @RequestParam(defaultValue = "true") Float boilerInKg,
            @RequestParam(defaultValue = "true") long componentTypeId,
            @RequestParam(defaultValue = "true") long componentId,
            @RequestParam(defaultValue = "true") long categoryId,
            @RequestParam(defaultValue = "true") boolean isActive
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureAdoptingBoilerService.findByBoilerInKgTableAndComponentTypeIdAndComponentIdAndCategoryIdAndActive(boilerInKg,componentTypeId,componentId,categoryId,isActive));
        return ResponseEntity.ok(rw);
    }
}
