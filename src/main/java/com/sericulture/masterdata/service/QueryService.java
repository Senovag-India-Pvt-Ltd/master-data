package com.sericulture.masterdata.service;

import com.sericulture.masterdata.model.api.query.ExecuteQueryRequest;
import com.sericulture.masterdata.model.api.query.ExecuteQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Service
public class QueryService {

    private static final int CONFIRMATION_WAIT_SECONDS = 30;
    private static final int MAX_SELECT_ROWS = 1000;

    // Only this user is permitted to run UPDATE / DELETE in production.
    // Authorised user: SIPLTEST2 / Satish / 9986710284
    private static final String ALLOWED_UPDATE_DELETE_USERNAME = "SIPLTEST2";

    private static final Pattern WHERE_PATTERN =
            Pattern.compile("(?i)(?<![A-Z0-9_])WHERE(?![A-Z0-9_])");
    private static final Pattern STRING_LITERAL_PATTERN =
            Pattern.compile("'(?:[^']|'')*'");
    private static final Pattern LINE_COMMENT_PATTERN =
            Pattern.compile("--[^\n]*");
    private static final Pattern BLOCK_COMMENT_PATTERN =
            Pattern.compile("/\\*[\\s\\S]*?\\*/");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public ExecuteQueryResponse executeQuery(ExecuteQueryRequest request) {
        if (request == null || request.getQuery() == null || request.getQuery().trim().isEmpty()) {
            return ExecuteQueryResponse.builder()
                    .message("Query cannot be empty.")
                    .build();
        }

        String rawQuery = request.getQuery().trim();
        String stripped = stripCommentsAndStrings(rawQuery);
        String queryType = detectQueryType(stripped);

        if ("UPDATE".equals(queryType) || "DELETE".equals(queryType)) {
            String username = request.getUsername() == null ? "" : request.getUsername().trim();
            if (!ALLOWED_UPDATE_DELETE_USERNAME.equals(username)) {
                log.warn("Blocked {} attempt by unauthorised user '{}'", queryType, username);
                return ExecuteQueryResponse.builder()
                        .queryType(queryType)
                        .message(queryType + " is restricted to the authorised user (SIPLTEST2 / Satish / 9986710284).")
                        .build();
            }
            if (!WHERE_PATTERN.matcher(stripped).find()) {
                return ExecuteQueryResponse.builder()
                        .queryType(queryType)
                        .message("WHERE condition is required for " + queryType + " queries.")
                        .build();
            }
            if (!Boolean.TRUE.equals(request.getConfirmed())) {
                return ExecuteQueryResponse.builder()
                        .queryType(queryType)
                        .requiresConfirmation(true)
                        .confirmationWaitSeconds(CONFIRMATION_WAIT_SECONDS)
                        .message("Are you sure you want to execute this " + queryType + " query?")
                        .build();
            }
        }

        try {
            log.info("Executing {} query: {}", queryType, rawQuery);

            if ("SELECT".equals(queryType)) {
                return runSelect(rawQuery);
            }

            int affected = jdbcTemplate.update(rawQuery);
            log.info("{} executed. Rows affected: {}", queryType, affected);
            return ExecuteQueryResponse.builder()
                    .queryType(queryType)
                    .affectedRows(affected)
                    .message(queryType + " executed successfully. " + affected + " row(s) affected.")
                    .build();
        } catch (Exception e) {
            log.error("Error executing query", e);
            throw new RuntimeException("Error executing query: " + e.getMessage(), e);
        }
    }

    private ExecuteQueryResponse runSelect(String sql) {
        List<String> columnNames = new ArrayList<>();
        List<Map<String, Object>> rows = new ArrayList<>();

        jdbcTemplate.query(sql, rs -> {
            if (columnNames.isEmpty()) {
                ResultSetMetaData md = rs.getMetaData();
                int count = md.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    columnNames.add(md.getColumnLabel(i));
                }
            }
            if (rows.size() >= MAX_SELECT_ROWS) {
                return;
            }
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 0; i < columnNames.size(); i++) {
                row.put(columnNames.get(i), rs.getObject(i + 1));
            }
            rows.add(row);
        });

        boolean capped = rows.size() >= MAX_SELECT_ROWS;
        return ExecuteQueryResponse.builder()
                .queryType("SELECT")
                .columns(columnNames)
                .rows(rows)
                .affectedRows(rows.size())
                .message("Returned " + rows.size() + " row(s)" +
                        (capped ? " (capped at " + MAX_SELECT_ROWS + ")." : "."))
                .build();
    }

    private String stripCommentsAndStrings(String sql) {
        String s = BLOCK_COMMENT_PATTERN.matcher(sql).replaceAll(" ");
        s = LINE_COMMENT_PATTERN.matcher(s).replaceAll(" ");
        s = STRING_LITERAL_PATTERN.matcher(s).replaceAll("''");
        return s;
    }

    private String detectQueryType(String sqlNoCommentsOrStrings) {
        String trimmed = sqlNoCommentsOrStrings.trim();
        if (trimmed.isEmpty()) return "OTHER";
        int end = 0;
        while (end < trimmed.length() && !Character.isWhitespace(trimmed.charAt(end))) end++;
        String firstWord = trimmed.substring(0, end).toUpperCase(Locale.ROOT);
        switch (firstWord) {
            case "SELECT":
                return "SELECT";
            case "UPDATE":
                return "UPDATE";
            case "DELETE":
                return "DELETE";
            case "INSERT":
                return "INSERT";
            default:
                return "OTHER";
        }
    }
}
