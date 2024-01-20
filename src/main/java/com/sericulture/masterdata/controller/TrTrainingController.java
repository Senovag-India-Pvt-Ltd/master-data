package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.common.SearchWithSortRequest;
import com.sericulture.masterdata.model.api.trSchedule.EditTrScheduleRequest;
import com.sericulture.masterdata.model.api.trSchedule.TrScheduleRequest;
import com.sericulture.masterdata.model.api.trSchedule.TrScheduleResponse;
import com.sericulture.masterdata.model.api.trTraining.EditTrTrainingRequest;
import com.sericulture.masterdata.model.api.trTraining.TrTrainingRequest;
import com.sericulture.masterdata.model.api.trTraining.TrTrainingResponse;
import com.sericulture.masterdata.service.TrScheduleService;
import com.sericulture.masterdata.service.TrTrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/trTraining")
public class TrTrainingController {

    @Autowired
    TrTrainingService trTrainingService;

    @Operation(summary = "Insert TrTraining Details", description = "Creates TrTraining Details in to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok Response"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"TrTraining name should be more than 1 characters.\",\"label\":\"name\",\"locale\":null}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addTrTrainingDetails(@RequestBody TrTrainingRequest trTrainingRequest){
        ResponseWrapper rw = ResponseWrapper.createWrapper(TrTrainingResponse.class);

        rw.setContent(trTrainingService.insertTrTrainingDetails(trTrainingRequest));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/get-all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"trTraining\":[{\"id\":10,\"trTraineeName\":\"\"},{\"id\":11,\"trTraineeName\":\"Karnataka\"},{\"id\":13,\"trTraineeName\":\"Kerala\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(trTrainingService.getAllByActive(isActive));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"trTraining\":[{\"id\":10,\"trTraineeName\":\"\"},{\"id\":11,\"trTraineeName\":\"Karnataka\"},{\"id\":13,\"trTraineeName\":\"Kerala\"}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
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
        rw.setContent(trTrainingService.getPaginatedTrTrainingDetails(PageRequest.of(pageNumber, size)));
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/list-with-join")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - inserted successfully",content =
                    {
                            @Content(mediaType = "application/json", schema =
                            @Schema(example = "{\"content\":{\"totalItems\":6,\"trTraining\":[{\"id\":10,\"trTraineeName\":\"\",\"trTraineeId\":1,},{\"id\":11,\"trTraineeName\":\"Shimoga\",\"trTraineeId\":1,},{\"id\":13,\"trTraineeName\":\"Hubli\",\"trTraineeId\":1,}],\"totalPages\":1,\"currentPage\":0},\"errorMessages\":[]}"))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request - Has validation errors",
                    content =
                            {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(example = "{\"content\":null,\"errorMessages\":[{\"errorType\":\"VALIDATION\",\"message\":[{\"message\":\"Invalid Id\",\"label\":\"NON_LABEL_MESSAGE\",\"locale\":null}]}]}"))
                            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Error occurred while processing the request.")
    })
    public ResponseEntity<?> getPaginatedTrTrainingDetailsWithJoin(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "5") final Integer size
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(trTrainingService.getPaginatedTrTrainingDetailsWithJoin(PageRequest.of(pageNumber, size)));
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
    public ResponseEntity<?> deleteTrTrainingDetails(
            @PathVariable final Integer id
    ) {
        ResponseWrapper<TrTrainingResponse> rw = ResponseWrapper.createWrapper(TrTrainingResponse.class);
        rw.setContent(trTrainingService.deleteTrTrainingDetails(id));
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
    public ResponseEntity<?> editTrTrainingDetails(
            @RequestBody final EditTrTrainingRequest editTrTrainingRequest
    ) {
        ResponseWrapper<TrTrainingResponse> rw = ResponseWrapper.createWrapper(TrTrainingResponse.class);
        rw.setContent(trTrainingService.updateTrTrainingDetails(editTrTrainingRequest));
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
        ResponseWrapper rw = ResponseWrapper.createWrapper(TrTrainingResponse.class);

        rw.setContent(trTrainingService.getById(id));
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
    @GetMapping("/get-join/{id}")
    public ResponseEntity<?> getByIdJoin(
            @PathVariable final Integer id
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(TrTrainingResponse.class);

        rw.setContent(trTrainingService.getByIdJoin(id));
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
    @PostMapping("/search")
    public ResponseEntity<?> search(
            @RequestBody final SearchWithSortRequest searchWithSortRequest
    ) {
        ResponseWrapper rw = ResponseWrapper.createWrapper(Map.class);
        rw.setContent(trTrainingService.searchByColumnAndSort(searchWithSortRequest));
        return ResponseEntity.ok(rw);
    }

}
