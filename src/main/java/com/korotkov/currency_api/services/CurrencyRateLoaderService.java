package com.korotkov.currency_api.services;

import com.korotkov.currency_api.models.CurrencyRate;

public interface CurrencyRateLoaderService {
    CurrencyRate[] loadRates(String date);
}
