package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.dto.Account;
import com.example.mybudgetspring.model.dto.Transaction;
import com.example.mybudgetspring.model.requests.TransactionRequest;
import com.example.mybudgetspring.services.TransactionService;
import com.example.mybudgetspring.util.DefaultCurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private DefaultCurrencyService defaultCurrencyService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllDefault() {
        String defaultCurrency = "USD";
        Account mockAccount = new Account(1, "Main Account", "USD", 500.0, 500.0);
        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction(1, "Transaction1", 50.0, 50.0, mockAccount),
                new Transaction(2, "Transaction2", 100.0, 100.0, mockAccount)
        );

        when(defaultCurrencyService.getDefaultCurrency()).thenReturn(defaultCurrency);
        when(transactionService.findAllDefault(defaultCurrency)).thenReturn(mockTransactions);

        List<Transaction> result = transactionController.findAllDefault();

        assertEquals(2, result.size());
        assertEquals("Transaction1", result.get(0).getDescription());
        assertEquals(50.0, result.get(0).getAmount());
        assertEquals(50.0, result.get(0).getDefaultCurrencyAmount());
        assertEquals("Main Account", result.get(0).getAccount().getName());
        verify(transactionService, times(1)).findAllDefault(defaultCurrency);
    }

    @Test
    void testInsert() throws JsonProcessingException {
        String defaultCurrency = "USD";
        TransactionRequest mockRequest = new TransactionRequest("Deposit", 100.0, 1);
        Account mockAccount = new Account(1, "Main Account", "USD", 500.0, 500.0);
        Transaction mockTransaction = new Transaction(1, "Deposit", 100.0, 100.0, mockAccount);

        when(defaultCurrencyService.getDefaultCurrency()).thenReturn(defaultCurrency);
        when(transactionService.insert(mockRequest, defaultCurrency)).thenReturn(mockTransaction);

        Transaction result = transactionController.insert(mockRequest);

        assertEquals("Deposit", result.getDescription());
        assertEquals(100.0, result.getAmount());
        assertEquals(100.0, result.getDefaultCurrencyAmount());
        assertEquals("Main Account", result.getAccount().getName());
        verify(transactionService, times(1)).insert(mockRequest, defaultCurrency);
    }
}
