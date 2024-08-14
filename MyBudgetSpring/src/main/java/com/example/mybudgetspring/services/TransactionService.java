package com.example.mybudgetspring.services;

import com.example.mybudgetspring.model.dto.Transaction;
import com.example.mybudgetspring.model.requests.TransactionRequest;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();

    List<Transaction> findAllDefault(String defaultCurrency);

    Transaction insert(TransactionRequest transactionRequest);

    Transaction findById(Integer transactionId);

    void delete(Integer transactionId);

}
