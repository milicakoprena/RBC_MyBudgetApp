package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.dto.Account;
import com.example.mybudgetspring.model.requests.AccountRequest;
import com.example.mybudgetspring.services.AccountService;
import com.example.mybudgetspring.util.DefaultCurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private DefaultCurrencyService defaultCurrencyService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllDefault() {
        String defaultCurrency = "USD";
        List<Account> accounts = Collections.emptyList();

        when(defaultCurrencyService.getDefaultCurrency()).thenReturn(defaultCurrency);
        when(accountService.findAllDefault(defaultCurrency)).thenReturn(accounts);

        List<Account> result = accountController.findAllDefault();

        assertEquals(accounts, result);
        verify(defaultCurrencyService, times(1)).getDefaultCurrency();
        verify(accountService, times(1)).findAllDefault(defaultCurrency);
    }

    @Test
    void getAccountBalanceSum() {
        String defaultCurrency = "USD";
        double expectedSum = 100.0;

        when(defaultCurrencyService.getDefaultCurrency()).thenReturn(defaultCurrency);
        when(accountService.getAccountBalanceSum(defaultCurrency)).thenReturn(expectedSum);

        double result = accountController.getAccountBalanceSum();

        assertEquals(expectedSum, result);
        verify(defaultCurrencyService, times(1)).getDefaultCurrency();
        verify(accountService, times(1)).getAccountBalanceSum(defaultCurrency);
    }

    @Test
    void insert() {
        AccountRequest accountRequest = new AccountRequest();
        Account expectedAccount = new Account();

        when(accountService.insert(accountRequest)).thenReturn(expectedAccount);

        Account result = accountController.insert(accountRequest);

        assertEquals(expectedAccount, result);
        verify(accountService, times(1)).insert(accountRequest);
    }

    @Test
    void deleteAll() {
        doNothing().when(accountService).deleteAll();

        accountController.deleteAll();

        verify(accountService, times(1)).deleteAll();
    }
}