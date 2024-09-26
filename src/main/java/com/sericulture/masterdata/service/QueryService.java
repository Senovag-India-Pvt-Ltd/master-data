package com.sericulture.masterdata.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class QueryService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Executes a dynamic SQL query.
     *
     * @param query The dynamic query as a String.
     * @return List<Object> containing the result of the query.
     */
    @Transactional
    public List<Object> executeDynamicQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }

        try {
            // Log the query for debugging purposes
            log.info("Executing Query: {}", query);

            // Execute the query using EntityManager
            Query q = entityManager.createNativeQuery(query);

            // Check if the query is a SELECT query
            if (query.trim().toUpperCase().startsWith("SELECT")) {
                // Use getResultList for SELECT queries
                return q.getResultList();
            } else {
                // Use executeUpdate for UPDATE, DELETE, INSERT queries
                int rowsAffected = q.executeUpdate();
                log.info("Rows affected: {}", rowsAffected);

                // Return the number of rows affected as a single-element list
                return List.of(rowsAffected);  // Return the result in a list format
            }
        } catch (Exception e) {
            // Log the error and rethrow it as a runtime exception
            log.error("Error executing query", e);
            throw new RuntimeException("Error executing query: " + e.getMessage(), e);
        }
    }

}
