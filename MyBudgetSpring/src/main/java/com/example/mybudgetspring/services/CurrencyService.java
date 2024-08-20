package com.example.mybudgetspring.services;

import com.example.mybudgetspring.model.responses.CurrencyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CurrencyService {
    List<CurrencyResponse> getAll();
    double convertCurrency(String fromCurrency, String toCurrency) throws JsonProcessingException;
    String getLatestExchangeRateDate(String defaultCurrency) throws JsonProcessingException;
}
