package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.responses.CurrencyResponse;
import com.example.mybudgetspring.services.CurrencyService;
import com.example.mybudgetspring.util.DefaultCurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;
    private final DefaultCurrencyService defaultCurrencyService;

    public CurrencyController(CurrencyService currencyService, DefaultCurrencyService defaultCurrencyService) {
        this.currencyService = currencyService;
        this.defaultCurrencyService = defaultCurrencyService;
    }

    @GetMapping
    public List<CurrencyResponse> getAll() {
        return currencyService.getAll();
    }

    @GetMapping("/default")
    public ResponseEntity<String> getDefaultCurrency() {
        return ResponseEntity.ok(defaultCurrencyService.getDefaultCurrency());
    }

    @PutMapping("/update")
    public void updateDefaultCurrency(@RequestBody String newCurrency) {
        defaultCurrencyService.setDefaultCurrency(newCurrency);
    }

    @GetMapping("/date")
    String getLatestExchangeRateDate() throws JsonProcessingException {
        return currencyService.getLatestExchangeRateDate(defaultCurrencyService.getDefaultCurrency());
    }
}
