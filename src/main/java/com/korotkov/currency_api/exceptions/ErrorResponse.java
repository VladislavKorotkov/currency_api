package com.korotkov.currency_api.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@Schema(description = "Basic information about the error")
public class ErrorResponse {
    @Schema(description = "The timestamp of the error", example = "2023-06-30T12:00:00Z")
    private Date timestamp;
    @Schema(description = "The error message", example = "Invalid date format")
    private String message;
    @Schema(description = "The detailed description of the error", example = "The provided date format is not supported")
    private String details;
    @Schema(description = "Any validation errors that occurred")
    private Map<String, String> validationErrors;
}
