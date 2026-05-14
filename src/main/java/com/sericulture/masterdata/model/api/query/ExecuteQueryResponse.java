package com.sericulture.masterdata.model.api.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteQueryResponse {

    private String queryType;

    private Boolean requiresConfirmation;

    private Integer confirmationWaitSeconds;

    private String message;

    private List<String> columns;

    private List<Map<String, Object>> rows;

    private Integer affectedRows;
}
