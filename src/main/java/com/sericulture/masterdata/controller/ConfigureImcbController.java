package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.configure_imcb.ConfigureImcbRequest;
import com.sericulture.masterdata.model.api.configure_imcb.ConfigureImcbResponse;
import com.sericulture.masterdata.model.api.configure_imcb.EditConfigureImcbRequest;
import com.sericulture.masterdata.service.ConfigureImcbService;
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
@RequestMapping("/v1/configureImcb")
public class ConfigureImcbController {

    @Autowired
    private ConfigureImcbService configureImcbService;

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
    @PostMapping("/add")
    @Operation(summary = "Insert Configure IMCB Details", description = "Creates a new Configure IMCB record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Field cannot be empty\",\"label\":\"name\"}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> addConfigureImcbDetails(@Valid @RequestBody ConfigureImcbRequest request) {
        ResponseWrapper<ConfigureImcbResponse> rw = ResponseWrapper.createWrapper(ConfigureImcbResponse.class);
        rw.setContent(configureImcbService.insertConfigureImcbDetails(request));
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
        rw.setContent(configureImcbService.getAllByActive(isActive));
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
        rw.setContent(configureImcbService.getPaginatedConfigureImcbDetails(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Delete Configure Imcb
     */
    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> deleteConfigureImcb(@PathVariable final Long id) {
        ResponseWrapper<ConfigureImcbResponse> rw = ResponseWrapper.createWrapper(ConfigureImcbResponse.class);
        rw.setContent(configureImcbService.deleteConfigureImcb(id));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Edit Configure Imcb
     */
    @PostMapping("/edit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> editConfigureImcb(@Valid @RequestBody final EditConfigureImcbRequest editRequest) {
        ResponseWrapper<ConfigureImcbResponse> rw = ResponseWrapper.createWrapper(ConfigureImcbResponse.class);
        rw.setContent(configureImcbService.updateConfigureImcbDetails(editRequest));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Paginated List With Join
     */
    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok - Paginated Configure IMCB List"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getPaginatedListWithJoin(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureImcbService.getPaginatedConfigureImcbWithJoin(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Get By ID With Join
     */
    @GetMapping("/get-by-id-join/{id}")
    @Operation(summary = "Get Configure IMCB by ID (with joins)", description = "Returns a Configure IMCB by ID including related details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getByIdWithJoin(@PathVariable final Long id) {
        ResponseWrapper<ConfigureImcbResponse> rw = ResponseWrapper.createWrapper(ConfigureImcbResponse.class);
        ConfigureImcbResponse response = configureImcbService.getConfigureImcbByIdWithJoin(id);
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
        ResponseWrapper<ConfigureImcbResponse> rw = ResponseWrapper.createWrapper(ConfigureImcbResponse.class);
        rw.setContent(configureImcbService.getById(id));
        return ResponseEntity.ok(rw);
    }
}
