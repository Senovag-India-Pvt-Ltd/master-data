package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.binMaster.BinMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.BinMasterResponse;
import com.sericulture.masterdata.model.api.binMaster.EditBinMasterRequest;
import com.sericulture.masterdata.model.api.binMaster.UpdateBinMasterStatusRequest;
import com.sericulture.masterdata.model.api.district.DistrictResponse;
import com.sericulture.masterdata.model.api.district.EditDistrictRequest;
import com.sericulture.masterdata.model.api.useMaster.UserMasterResponse;
import com.sericulture.masterdata.model.dto.UserMasterDTO;
import com.sericulture.masterdata.model.entity.BinMaster;
import com.sericulture.masterdata.service.BinMasterService;
import com.sericulture.masterdata.service.DistrictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/binMaster")
public class BinMasterController {
    @Autowired
    BinMasterService binMasterService;

    @Operation(summary = "Insert Bin  Details", description = "Creates Bin Details in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Bin should be more than 1 characters.\",\"label\":\"name\",\"locale\":null}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addBinMasterDetails(@RequestBody BinMasterRequest binMasterRequest){
        ResponseWrapper rw = ResponseWrapper.createWrapper(BinMasterResponse.class);

        rw.setContent(binMasterService.insertBinMasterDetails(binMasterRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"binMaster\":[{\"id\":10,\"binMasterName\":\"\"},{\"id\":11,\"binMasterName\":\"binMaster 1\"},{\"id\":13,\"binMasterName\":\"binMaster 2\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(binMasterService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"binMaster\":[{\"id\":10,\"binNumber\":\"\",\"binCounterMasterId\":1,},{\"id\":11,\"binNumber\":\"12S\",\"binCounterMasterId\":1,},{\"id\":13,\"binNumber\":\"Hubli\",\"binCounterMasterId\":1,}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
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
        rw.setContent(binMasterService.getPaginatedBinMasterDetails(PageRequest.of(pageNumber, size)));
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
    public ResponseEntity<?> deleteBinMasterDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(binMasterService.deleteBinMasterDetails(id));
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
    public ResponseEntity<?> editBinMasterDetails(
            @RequestBody final EditBinMasterRequest editBinMasterRequest
    ) {
        ResponseWrapper<BinMasterResponse> rw = ResponseWrapper.createWrapper(BinMasterResponse.class);
        rw.setContent(binMasterService.updateBinMasterDetails(editBinMasterRequest));
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
    @PostMapping("/update-status")
    public ResponseEntity<?> updateBinStatus(
            @RequestBody final UpdateBinMasterStatusRequest editBinMasterRequest
    ) {
        ResponseWrapper<BinMasterResponse> rw = ResponseWrapper.createWrapper(BinMasterResponse.class);

        // Update Small Bin Status
        // Get binMaster for smallBin
        BinMaster smallBinMaster = binMasterService.getByMarketGodownTypeBinNumber(editBinMasterRequest.getMarketId(), editBinMasterRequest.getGodownId(), "small", editBinMasterRequest.getSmallBinNumber());
        if (smallBinMaster != null) {
            // Change status
            smallBinMaster.setStatus(editBinMasterRequest.getSmallBinStatus());

            // Save
            rw.setContent(binMasterService.updateBinStatus(smallBinMaster));
        }


        // update Big Bin Status
        // Get binMaster for BigBin
        // Change status
        // Save
        BinMaster retResBinMaster = binMasterService.getByMarketGodownTypeBinNumber(editBinMasterRequest.getMarketId(), editBinMasterRequest.getGodownId(), "big", editBinMasterRequest.getBigBinNumber());
        // Change status
        if (retResBinMaster != null) {
            // Change status
            retResBinMaster.setStatus(editBinMasterRequest.getBigBinStatus());
            // Save
            rw.setContent(binMasterService.updateBinStatus(retResBinMaster));
        }


        return ResponseEntity.ok(rw);
    }

//    @PostMapping("/update-status")
//    public ResponseEntity<?> updateBinStatus(
//            @RequestBody final UpdateBinMasterStatusRequest editBinMasterRequest
//    ) {
//        ResponseWrapper<BinMasterResponse> rw = ResponseWrapper.createWrapper(BinMasterResponse.class);
//
//        // Update Small Bin Status
//        BinMaster smallBinMaster = binMasterService.getByMarketGodownTypeBinNumber(editBinMasterRequest.getMarketId(), editBinMasterRequest.getGodownId(), "small", editBinMasterRequest.getSmallBinNumber());
//        if (smallBinMaster != null) {
//            smallBinMaster.setStatus("available");
//            rw.setContent(binMasterService.updateBinStatus(smallBinMaster));
//        } else {
//            rw.se("Small BinMaster not found for the specified criteria.");
//            rw.setStatusCode(HttpStatus.NOT_FOUND.value());
//            return ResponseEntity.status(rw.getStatusCode()).body(rw);
//        }
//
//
//        // Update Big Bin Status
//        BinMaster retResBinMaster = binMasterService.getByMarketGodownTypeBinNumber(editBinMasterRequest.getMarketId(), editBinMasterRequest.getGodownId(), "big", editBinMasterRequest.getBigBinNumber());
//        if (retResBinMaster != null) {
//            retResBinMaster.setStatus("unavailable");
//            rw.setContent(binMasterService.updateBinStatus(retResBinMaster));
//        } else {
//            binMasterService.("Big BinMaster not found for the specified criteria.");
//            rw.setStatusCode(HttpStatus.NOT_FOUND.value()); // or any appropriate status code
//        }
//
//        return ResponseEntity.ok(rw);
//    }


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
        ResponseWrapper rw = ResponseWrapper.createWrapper(BinMasterResponse.class);

        rw.setContent(binMasterService.getById(id));
        return ResponseEntity.ok(rw);
    }
    @GetMapping("/get-by-binCounterMaster-id/{binCounterMasterId}")
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
    public ResponseEntity<?> getByBinCounterMasterId(
            @PathVariable final int binCounterMasterId
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(binMasterService.getBinMasterAndBinCounterMasterId(binCounterMasterId));
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
    @PostMapping("/get-by-godownId-and-marketId")
    public ResponseEntity<?> getByGodownIdAndMarketId(
            @RequestBody final BinMasterRequest binMasterRequest
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(BinMasterResponse.class);

        rw.setContent(binMasterService.getByGodownIdAndMarketId(binMasterRequest.getGodownId(), binMasterRequest.getMarketId()));
        return ResponseEntity.ok(rw);
    }
}
