package com.korotkov.currency_api.services.impl;

import com.korotkov.currency_api.exceptions.custom.CannotLoadCurrencyRateException;
import com.korotkov.currency_api.exceptions.custom.CurrencyRateNotFoundException;
import com.korotkov.currency_api.models.CurrencyRate;
import com.korotkov.currency_api.repositories.CurrencyRateRepository;
import com.korotkov.currency_api.responses.CurrencyRateResponse;
import com.korotkov.currency_api.responses.ResultResponse;
import com.korotkov.currency_api.services.CurrencyRateLoaderService;
import com.korotkov.currency_api.services.CurrencyRateService;
import com.korotkov.currency_api.utils.CurrencyRateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final CurrencyRateRepository repository;
    private final CurrencyRateLoaderService loaderService;


    public CurrencyRateServiceImpl(CurrencyRateRepository repository, CurrencyRateLoaderService loaderService) {
        this.repository = repository;
        this.loaderService = loaderService;
    }

    @Override
    @Transactional
    public ResultResponse loadRatesByDate(String date) {
        if (!repository.findByDate(date).isEmpty()) {
            return new ResultResponse("Data loaded successfully for date: " + date);
        }

        CurrencyRate[] rates = loaderService.loadRates(date);
        if (rates.length > 0) {
            for (CurrencyRate rate : rates) {
                rate.setDate(date);
                repository.save(rate);
            }
            return new ResultResponse("Data loaded successfully for date: " + date);
        }
        throw new CannotLoadCurrencyRateException("Failed to load data for date: " + date);
    }

    @Override
    public List<CurrencyRateResponse> getRatesByDate(String date) {
        List<CurrencyRate> currency = repository.findAll();
        List<CurrencyRate> currencyRates = repository.findByDate(date);
        if(currencyRates.isEmpty())
            throw new CurrencyRateNotFoundException("Failed to load data for date: " + date);
        return CurrencyRateMapper.INSTANCE.toCurrencyResponseList(repository.findByDate(date));
    }

    @Override
    public CurrencyRateResponse getRateByDateAndCode(String date, int code) {
        return CurrencyRateMapper.INSTANCE.currencyRateToCurrencyRateResponse(
                repository.findByDateAndCurId(date, code).
                        orElseThrow(()->new CurrencyRateNotFoundException("Failed to load data for date: " + date)));
    }
}
