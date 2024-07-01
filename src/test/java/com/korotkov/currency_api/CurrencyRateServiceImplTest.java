package com.korotkov.currency_api;

import com.korotkov.currency_api.exceptions.custom.CannotLoadCurrencyRateException;
import com.korotkov.currency_api.exceptions.custom.CurrencyRateNotFoundException;
import com.korotkov.currency_api.models.CurrencyRate;
import com.korotkov.currency_api.repositories.CurrencyRateRepository;
import com.korotkov.currency_api.responses.CurrencyRateResponse;
import com.korotkov.currency_api.responses.ResultResponse;
import com.korotkov.currency_api.services.CurrencyRateLoaderService;
import com.korotkov.currency_api.services.impl.CurrencyRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyRateServiceImplTest {

    @Mock
    private CurrencyRateRepository repository;

    @Mock
    private CurrencyRateLoaderService loaderService;

    @InjectMocks
    private CurrencyRateServiceImpl service;

    private CurrencyRate currencyRate;

    @BeforeEach
    void setUp() {
        currencyRate = new CurrencyRate();
        currencyRate.setCurId(123);
        currencyRate.setDate("2023-07-01");
    }

    @Test
    void testLoadRatesByDate_DataAlreadyLoaded() {
        when(repository.findByDate("2023-07-01")).thenReturn(List.of(currencyRate));

        ResultResponse response = service.loadRatesByDate("2023-07-01");

        assertEquals("Data loaded successfully for date: 2023-07-01", response.getMessage());
        verify(loaderService, never()).loadRates(any());
    }

    @Test
    void testLoadRatesByDate_DataLoadedSuccessfully() {
        when(repository.findByDate("2023-07-01")).thenReturn(Collections.emptyList());
        when(loaderService.loadRates("2023-07-01")).thenReturn(new CurrencyRate[]{currencyRate});

        ResultResponse response = service.loadRatesByDate("2023-07-01");

        assertEquals("Data loaded successfully for date: 2023-07-01", response.getMessage());
        verify(repository).save(currencyRate);
    }

    @Test
    void testLoadRatesByDate_FailedToLoadData() {
        when(repository.findByDate("2023-07-01")).thenReturn(Collections.emptyList());
        when(loaderService.loadRates("2023-07-01")).thenReturn(new CurrencyRate[]{});

        assertThrows(CannotLoadCurrencyRateException.class, () -> service.loadRatesByDate("2023-07-01"));
    }

    @Test
    void testGetRatesByDate_DataNotFound() {
        when(repository.findByDate("2023-07-01")).thenReturn(Collections.emptyList());

        assertThrows(CurrencyRateNotFoundException.class, () -> service.getRatesByDate("2023-07-01"));
    }

    @Test
    void testGetRateByDateAndCode_DataFound() {
        when(repository.findByDateAndCurId("2023-07-01", 123)).thenReturn(Optional.of(currencyRate));

        CurrencyRateResponse response = service.getRateByDateAndCode("2023-07-01", 123);

        assertNotNull(response);
        verify(repository).findByDateAndCurId("2023-07-01", 123);
    }

    @Test
    void testGetRateByDateAndCode_DataNotFound() {
        when(repository.findByDateAndCurId("2023-07-01", 123)).thenReturn(Optional.empty());

        assertThrows(CurrencyRateNotFoundException.class, () -> service.getRateByDateAndCode("2023-07-01", 123));
    }
}