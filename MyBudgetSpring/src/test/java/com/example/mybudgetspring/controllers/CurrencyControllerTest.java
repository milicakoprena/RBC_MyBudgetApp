package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.responses.CurrencyResponse;
import com.example.mybudgetspring.services.CurrencyService;
import com.example.mybudgetspring.util.DefaultCurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @Mock
    private DefaultCurrencyService defaultCurrencyService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<CurrencyResponse> mockCurrencies = Arrays.asList(
                new CurrencyResponse("USD", "US Dollar"),
                new CurrencyResponse("EUR", "Euro")
        );
        when(currencyService.getAll()).thenReturn(mockCurrencies);

        List<CurrencyResponse> result = currencyController.getAll();

        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getId());
        assertEquals("US Dollar", result.get(0).getName());
    }

    @Test
    void testGetDefaultCurrency() {
        String mockCurrency = "USD";
        when(defaultCurrencyService.getDefaultCurrency()).thenReturn(mockCurrency);

        ResponseEntity<String> response = currencyController.getDefaultCurrency();

        assertEquals("USD", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateDefaultCurrency() {
        String newCurrency = "EUR";

        currencyController.updateDefaultCurrency(newCurrency);

        verify(defaultCurrencyService, times(1)).setDefaultCurrency(newCurrency);
    }

    @Test
    void testGetLatestExchangeRateDate() throws Exception {
        String mockDate = "2024-08-20";
        String defaultCurrency = "USD";
        when(defaultCurrencyService.getDefaultCurrency()).thenReturn(defaultCurrency);
        when(currencyService.getLatestExchangeRateDate(defaultCurrency)).thenReturn(mockDate);

        String result = currencyController.getLatestExchangeRateDate();

        assertEquals("2024-08-20", result);
    }
}
