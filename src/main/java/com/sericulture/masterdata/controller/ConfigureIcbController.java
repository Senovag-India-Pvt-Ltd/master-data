package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.configure_icb.ConfigureIcbRequest;
import com.sericulture.masterdata.model.api.configure_icb.ConfigureIcbResponse;
import com.sericulture.masterdata.model.api.configure_icb.EditConfigureIcbRequest;
import com.sericulture.masterdata.service.ConfigureIcbService;
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
@RequestMapping("/v1/configureIcb")
public class ConfigureIcbController {

    @Autowired
    private ConfigureIcbService configureIcbService;

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
     * ✅ Add Configure ICB
     */
    @Operation(summary = "Insert Configure ICB Details", description = "Creates a new Configure ICB record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Field cannot be empty\",\"label\":\"name\"}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addConfigureIcbDetails(@Valid @RequestBody ConfigureIcbRequest request) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ConfigureIcbResponse.class);
        rw.setContent(configureIcbService.insertConfigureIcbDetails(request));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Get All Active
     */
    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - no data found",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"content\":{\"totalItems\":0,\"configureIcb\":[]},\"errorMessages\":[]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getAllByActive(
            @RequestParam(defaultValue = "true") boolean isActive
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureIcbService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Paginated List
     */
    /**
     * ✅ Paginated List With Join (Joined Category, Component, SubScheme Names)
     */
    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok - Paginated Configure ICB List"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getPaginatedListWithJoin(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configureIcbService.getPaginatedConfigureIcbWithJoin(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-by-id-join/{id}")
    @Operation(summary = "Get Configure ICB by ID (with joins)", description = "Returns a Configure ICB by ID including categoryName, componentName, and subSchemeName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getByIdWithJoin(
            @PathVariable final Long id
    ) {
        ResponseWrapper<ConfigureIcbResponse> rw = ResponseWrapper.createWrapper(ConfigureIcbResponse.class);
        ConfigureIcbResponse response = configureIcbService.getConfigureIcbByIdWithJoin(id);

        rw.setContent(response);
        return ResponseEntity.ok(rw);
    }



    /**
     * ✅ Delete Configure ICB
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\"}]}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteConfigureIcb(
            @PathVariable final Long id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(ConfigureIcbResponse.class);
        rw.setContent(configureIcbService.deleteConfigureIcb(id));
        return ResponseEntity.ok(rw);
    }

    /**
     * ✅ Edit Configure ICB
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(example =
                            "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\"}]}]}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/edit")
    public ResponseEntity<?> editConfigureIcb(
            @Valid @RequestBody final EditConfigureIcbRequest editRequest
    ) {
        ResponseWrapper<ConfigureIcbResponse> rw = ResponseWrapper.createWrapper(ConfigureIcbResponse.class);
        rw.setContent(configureIcbService.updateConfigureIcbDetails(editRequest));
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
        ResponseWrapper rw = ResponseWrapper.createWrapper(ConfigureIcbResponse.class);
        rw.setContent(configureIcbService.getById(id));
        return ResponseEntity.ok(rw);
    }



}
