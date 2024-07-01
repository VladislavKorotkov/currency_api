package com.korotkov.currency_api.exceptions;

import com.korotkov.currency_api.exceptions.custom.CannotLoadCurrencyRateException;
import com.korotkov.currency_api.exceptions.custom.CurrencyRateNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(String message, WebRequest request, Map<String, String> errors) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), message, request.getDescription(false), errors);
        return errorResponse;
    }

    private ErrorResponse buildErrorResponse(String message, WebRequest request) {
        return buildErrorResponse(message, request, null);
    }

    @ExceptionHandler(CurrencyRateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse CurrencyRateNotFoundException(CurrencyRateNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), request);
    }

    @ExceptionHandler(CannotLoadCurrencyRateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCannotLoadCurrencyRateException(CannotLoadCurrencyRateException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), request);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) {
        String message = "HTTP Client Error: " + ex.getStatusCode() + " - " + ex.getStatusText();
        return buildErrorResponse(message, request, Map.of("error", ex.getResponseBodyAsString()));
    }
}
