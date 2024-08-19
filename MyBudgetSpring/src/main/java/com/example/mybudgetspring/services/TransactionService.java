package com.example.mybudgetspring.services;

import com.example.mybudgetspring.model.dto.Transaction;
import com.example.mybudgetspring.model.requests.TransactionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();

    List<Transaction> findAllDefault(String defaultCurrency);

    Transaction insert(TransactionRequest transactionRequest, String defaultCurrency) throws JsonProcessingException;

    Transaction findById(Integer transactionId);


}
