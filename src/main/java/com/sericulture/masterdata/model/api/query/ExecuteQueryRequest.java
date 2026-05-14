package com.sericulture.masterdata.model.api.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteQueryRequest {

    @Schema(description = "Raw SQL query to execute against the application database (sericultureprd).",
            example = "SELECT TOP 10 * FROM caste")
    private String query;

    @Schema(description = "Set to true on the second call after the user accepts the 'Are you sure?' popup. " +
                          "Required for UPDATE / DELETE queries; ignored for SELECT / INSERT / others.",
            example = "false")
    private Boolean confirmed;
}
