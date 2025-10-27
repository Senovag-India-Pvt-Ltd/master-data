package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;

import com.sericulture.masterdata.model.api.configure_silk_incentive.ConfigureSilkIncentiveRequest;
import com.sericulture.masterdata.model.api.configure_silk_incentive.ConfigureSilkIncentiveResponse;
import com.sericulture.masterdata.model.api.configure_silk_incentive.EditConfigureSilkIncentiveRequest;
import com.sericulture.masterdata.service.ConfigureIcbService;
import com.sericulture.masterdata.service.ConfigureImcbService;
import com.sericulture.masterdata.service.ConfigureSilkIncentiveService;
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
@RequestMapping("/v1/configureSilkIncentive")
public class ConfigureSilkIncentiveController {
    @Autowired
    private ConfigureSilkIncentiveService configureSilkIncentiveService;

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
     * ✅ Add Configure Imcb
     */
    @Operation(summary = "Insert Configure Imcb Details", description = "Creates a new Configure Imcb record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Field cannot be empty\",\"label\":\"name\"}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addConfigureSilkIncentiveDetails( @RequestBody ConfigureSilkIncentiveRequest request) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ConfigureSilkIncentiveResponse.class);
        rw.setContent(configureSilkIncentiveService.insertConfigureSilkIncentiveDetails(request));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Get All Active
     */
    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - no data found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"content\":{\"totalItems\":0,\"configureSilkIncentive\":[]},\"errorMessages\":[]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getAllByActive(
            @RequestParam(defaultValue = "true") boolean isActive
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureSilkIncentiveService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Paginated List
     */
    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - no data found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"content\":{\"totalItems\":0,\"configureSilkIncentive\":[]},\"errorMessages\":[]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getPaginatedList(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureSilkIncentiveService.getPaginatedConfigureSilkIncentiveDetails(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Delete Configure Imcb
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\"}]}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteConfigureSilkIncentive(
            @PathVariable final Long id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ConfigureSilkIncentiveResponse.class);
        rw.setContent(configureSilkIncentiveService.deleteConfigureSilkIncentive(id));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Edit Configure Imcb
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\"}]}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/edit")
    public ResponseEntity<?> editConfigureSilkIncentive(
            @Valid @RequestBody final EditConfigureSilkIncentiveRequest editRequest
    ) {
        ResponseWrapper<ConfigureSilkIncentiveResponse> rw = ResponseWrapper.createWrapper(ConfigureSilkIncentiveResponse.class);
        rw.setContent(configureSilkIncentiveService.updateConfigureSilkIncentiveDetails(editRequest));
        return ResponseEntity.ok(rw);
    }
    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok - Paginated Configure Silk Incentive List"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getPaginatedListWithJoin(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureSilkIncentiveService.getPaginatedConfigureSilkIncentiveWithJoin(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Get by ID with Join
     */
    @GetMapping("/get-by-id-join/{id}")
    @Operation(summary = "Get Configure Silk Incentive by ID (with joins)", description = "Returns a Configure Silk Incentive by ID including machineTypeName, categoryName, componentName, and subSchemeName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getByIdWithJoin(@PathVariable final Long id) {
        ResponseWrapper<ConfigureSilkIncentiveResponse> rw = ResponseWrapper.createWrapper(ConfigureSilkIncentiveResponse.class);
        ConfigureSilkIncentiveResponse response = configureSilkIncentiveService.getConfigureSilkIncentiveByIdWithJoin(id);
        rw.setContent(response);
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Get by ID
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\"}]}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(
            @PathVariable final Long id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ConfigureSilkIncentiveResponse.class);
        rw.setContent(configureSilkIncentiveService.getById(id));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/getAmountByMachineTypeComponentsAndSchemes")
    public ResponseEntity<?> getAmountByMachineTypeComponentsAndSchemes(
            @RequestParam(defaultValue = "true") long componentTypeId,
            @RequestParam(defaultValue = "true") long componentId,
            @RequestParam(defaultValue = "true") long categoryId,
            @RequestParam(defaultValue = "true") long machineTypeId,
            @RequestParam(defaultValue = "true") boolean isActive
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureSilkIncentiveService.getAmountByMachineTypeComponentsAndSchemes(componentTypeId,componentId,categoryId,machineTypeId,isActive));
        return ResponseEntity.ok(rw);
    }
}
