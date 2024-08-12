package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.responses.CurrencyResponse;
import com.example.mybudgetspring.services.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public List<CurrencyResponse> getAll() {
        return currencyService.getAll();
    }

    @GetMapping("/convert/{fromCurrency}/{toCurrency}")
    public double convertCurrency(@PathVariable String fromCurrency, @PathVariable String toCurrency) throws JsonProcessingException {
        return currencyService.convertCurrency(fromCurrency, toCurrency);
    }
}
