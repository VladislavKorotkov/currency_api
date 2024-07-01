package com.korotkov.currency_api.services.impl;

import com.korotkov.currency_api.models.CurrencyRate;
import com.korotkov.currency_api.services.CurrencyRateLoaderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyRateLoaderServiceImpl implements CurrencyRateLoaderService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public CurrencyRateLoaderServiceImpl(RestTemplateBuilder restTemplateBuilder, @Value("${currency.api.url}") String apiUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiUrl = apiUrl;
    }


    @Override
    public CurrencyRate[] loadRates(String date) {
        String url = apiUrl.replace("{date}", date);
        ResponseEntity<CurrencyRate[]> response = restTemplate.getForEntity(url, CurrencyRate[].class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        }
        return new CurrencyRate[0];
    }
}
