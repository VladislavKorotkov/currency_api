package com.korotkov.currency_api.exceptions.custom;

public class CannotLoadCurrencyRateException extends RuntimeException {
    public CannotLoadCurrencyRateException(String message) {
        super(message);
    }
}
