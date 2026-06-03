package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.model.ResponseWrapper;
import com.sericulture.masterdata.model.api.query.ExecuteQueryRequest;
import com.sericulture.masterdata.model.api.query.ExecuteQueryResponse;
import com.sericulture.masterdata.service.QueryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @Operation(
            summary = "Execute a dynamic SQL query against the application database",
            description = "Runs the supplied SQL on the configured datasource (sericultureprd in production). " +
                    "UPDATE / DELETE queries must contain a WHERE clause and require a second call with " +
                    "confirmed=true after the user accepts the 'Are you sure?' popup (UI shows a 30-second " +
                    "countdown and the query in red). SELECT results include column metadata so the UI can " +
                    "render the rows in a table below the editor."
    )
    @PostMapping("/execute")
    public ResponseEntity<ResponseWrapper<ExecuteQueryResponse>> executeQuery(
            @RequestBody ExecuteQueryRequest request) {
        ResponseWrapper<ExecuteQueryResponse> rw = ResponseWrapper.createWrapper(ExecuteQueryResponse.class);
        try {
            rw.setContent(queryService.executeQuery(request));
            return ResponseEntity.ok(rw);
        } catch (RuntimeException ex) {
            ExecuteQueryResponse error = ExecuteQueryResponse.builder()
                    .message(ex.getMessage())
                    .build();
            rw.setContent(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rw);
        }
    }
}
