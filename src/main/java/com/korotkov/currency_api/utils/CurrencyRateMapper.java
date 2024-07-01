package com.korotkov.currency_api.utils;

import com.korotkov.currency_api.models.CurrencyRate;
import com.korotkov.currency_api.responses.CurrencyRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CurrencyRateMapper {
    CurrencyRateMapper INSTANCE = Mappers.getMapper(CurrencyRateMapper.class);

    CurrencyRateResponse currencyRateToCurrencyRateResponse(CurrencyRate currencyRate);
}
