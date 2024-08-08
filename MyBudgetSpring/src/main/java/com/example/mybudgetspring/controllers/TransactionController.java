package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.dto.Transaction;
import com.example.mybudgetspring.model.requests.TransactionRequest;
import com.example.mybudgetspring.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> findAll(){
        return transactionService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction insert(@RequestBody TransactionRequest transactionRequest){
        return transactionService.insert(transactionRequest);
    }
}
