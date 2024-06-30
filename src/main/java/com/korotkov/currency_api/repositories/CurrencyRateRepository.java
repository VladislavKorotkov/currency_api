package com.korotkov.currency_api.repositories;

import com.korotkov.currency_api.models.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    List<CurrencyRate> findByDate(String date);
    CurrencyRate findByDateAndCurAbbreviation(String date, String curAbbreviation);
}