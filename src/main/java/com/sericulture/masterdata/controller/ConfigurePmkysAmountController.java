package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.configurePmkysAmount.EditConfigurePmkysAmountRequest;
import com.sericulture.masterdata.model.api.configurePmkysAmount.ConfigurePmkysAmountRequest;
import com.sericulture.masterdata.model.api.configurePmkysAmount.ConfigurePmkysAmountResponse;
import com.sericulture.masterdata.service.ConfigurePmkysAmountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/configurePmkysAmount")
public class ConfigurePmkysAmountController {

    @Autowired
    ConfigurePmkysAmountService configurePmkysAmountService;

    @Operation(summary = "Insert ConfigurePmkysAmount Details", description = "Creates ConfigurePmkysAmount Details in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"ConfigurePmkysAmount name should be more than 1 characters.\",\"label\":\"name\",\"locale\":null}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addConfigurePmkysAmountDetails(@RequestBody ConfigurePmkysAmountRequest configurePmkysAmountRequest){
        ResponseWrapper rw = ResponseWrapper.createWrapper(ConfigurePmkysAmountResponse.class);

        rw.setContent(configurePmkysAmountService.insertConfigurePmkysAmountDetails(configurePmkysAmountRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"configurePmkysAmountId\":[{\"id\":10,\"configurePmkysAmountId\":\"\"},{\"id\":11,\"configurePmkysAmountId\":\"configurePmkysAmount 1\"},{\"id\":13,\"configurePmkysAmount\":\"ConfigurePmkysAmount 2\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(configurePmkysAmountService.getAllByActive(isActive));
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
    public ResponseEntity<?> deleteConfigurePmkysAmountDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(configurePmkysAmountService.deleteConfigurePmkysAmountDetails(id));
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
    public ResponseEntity<?> editConfigurePmkysAmountDetails(
            @RequestBody final EditConfigurePmkysAmountRequest editConfigurePmkysAmountRequest
    ) {
        ResponseWrapper<ConfigurePmkysAmountResponse> rw = ResponseWrapper.createWrapper(ConfigurePmkysAmountResponse.class);
        rw.setContent(configurePmkysAmountService.updateConfigurePmkysAmountDetails(editConfigurePmkysAmountRequest));
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
        ResponseWrapper rw = ResponseWrapper.createWrapper(ConfigurePmkysAmountResponse.class);

        rw.setContent(configurePmkysAmountService.getById(id));
        return ResponseEntity.ok(rw);
    }
    @GetMapping("/getAmountBySpacingAndHectare/{spacingId}/{hectareId}")
    public List<ConfigurePmkysAmountResponse> getAmountBySpacingAndHectare(@PathVariable Long spacingId,@PathVariable Long hectareId) {
        return configurePmkysAmountService.getAmountBySpacingAndHectare(spacingId,hectareId);
    }

    @GetMapping("/getClosestAmountBySpacingAndHectare/{spacingId}/{hectareId}")
    public List<ConfigurePmkysAmountResponse> getClosestAmountBySpacingAndHectare(@PathVariable Long spacingId,@PathVariable Long hectareId) {
        return configurePmkysAmountService.getClosestAmountBySpacingAndHectare(spacingId,hectareId);
    }

    @GetMapping("/getClosestRecordsSpacingAndHectare/{spacingId}/{hectareId}")
    public List<ConfigurePmkysAmountResponse> getClosestRecordsSpacingAndHectare(@PathVariable Long spacingId,@PathVariable Long hectareId) {
        return configurePmkysAmountService.getClosestRecordsSpacingAndHectare(spacingId,hectareId);
    }

    @GetMapping("/getAmountBySpacingAndHectare/{configurePmkysAmountId}")
    public List<ConfigurePmkysAmountResponse> getListOfConfigurePmkysAmount(@PathVariable Long configurePmkysAmountId) {
        return configurePmkysAmountService.getListOfConfigurePmkysAmount(configurePmkysAmountId);
    }

    @GetMapping("/getListOfConfigurePmkysAmountDetails")
    public ResponseEntity<?> getListOfConfigurePmkysAmountDetails(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "50") int pageSize) {
        return configurePmkysAmountService.getListOfConfigurePmkysAmountDetails(pageNumber, pageSize);
    }

}
