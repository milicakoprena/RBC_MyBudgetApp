package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.dto.Transaction;
import com.example.mybudgetspring.model.requests.TransactionRequest;
import com.example.mybudgetspring.services.TransactionService;
import com.example.mybudgetspring.util.DefaultCurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final DefaultCurrencyService defaultCurrencyService;

    public TransactionController(TransactionService transactionService, DefaultCurrencyService defaultCurrencyService) {
        this.transactionService = transactionService;
        this.defaultCurrencyService = defaultCurrencyService;
    }

    @GetMapping("/default")
    public List<Transaction> findAllDefault(){
        return transactionService.findAllDefault(defaultCurrencyService.getDefaultCurrency());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction insert(@RequestBody TransactionRequest transactionRequest) throws JsonProcessingException {
        return transactionService.insert(transactionRequest, defaultCurrencyService.getDefaultCurrency());
    }
}
