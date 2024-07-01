package com.korotkov.currency_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private String message;
    private String details;
    private Map<String, String> validationErrors;
}
