package com.sericulture.masterdata.controller;

import com.sericulture.masterdata.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    /**
     * Executes a query if it contains a WHERE clause.
     *
     * @param query The dynamic query to execute.
     * @return ResponseEntity<String> indicating the status of execution.
     */
    @PostMapping("/execute")
    public ResponseEntity<String> executeQuery(@RequestParam String query) {
        // Check if the query is null or empty
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Query cannot be null or empty!");
        }

        // Check if the query contains the "WHERE" clause
        if (query.toUpperCase().contains("WHERE")) {
            try {
                // Execute the query using the QueryService
                queryService.executeDynamicQuery(query);
                return ResponseEntity.ok("Query executed successfully!");
            } catch (Exception e) {
                // Handle exception and return the error message
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error executing query: " + e.getMessage());
            }
        } else {
            // Return error response if the WHERE clause is missing
            return ResponseEntity.badRequest().body("Error: Query must contain a WHERE condition!");
        }
    }
}
