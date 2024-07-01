package com.korotkov.currency_api.services;

import com.korotkov.currency_api.models.CurrencyRate;
import com.korotkov.currency_api.responses.CurrencyRateResponse;
import com.korotkov.currency_api.responses.ResultResponse;

import java.util.List;

public interface CurrencyRateService {
    ResultResponse loadRatesByDate(String date);
    List<CurrencyRateResponse> getRatesByDate(String date);
    CurrencyRateResponse getRateByDateAndCode(String date, int code);
}
