package com.korotkov.currency_api.repositories;

import com.korotkov.currency_api.models.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    List<CurrencyRate> findByDate(String date);
    Optional<CurrencyRate> findByDateAndCurId(String date, int currId);
}