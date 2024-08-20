package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.dto.Account;
import com.example.mybudgetspring.model.requests.AccountRequest;
import com.example.mybudgetspring.services.AccountService;
import com.example.mybudgetspring.services.CurrencyService;
import com.example.mybudgetspring.util.DefaultCurrencyService;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final DefaultCurrencyService defaultCurrencyService;

    public AccountController(AccountService accountService, DefaultCurrencyService defaultCurrencyService) {
        this.accountService = accountService;
        this.defaultCurrencyService = defaultCurrencyService;
    }

    @PostConstruct
    private void postConstruct() throws IOException, JAXBException {
        accountService.loadXml();
    }

    @GetMapping("/default")
    public List<Account> findAllDefault() {
        return accountService.findAllDefault(defaultCurrencyService.getDefaultCurrency());
    }

    @GetMapping("/sum")
    public Double getAccountBalanceSum() {
        return accountService.getAccountBalanceSum(defaultCurrencyService.getDefaultCurrency());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account insert(@RequestBody AccountRequest accountRequest) {
        return accountService.insert(accountRequest);
    }

    @DeleteMapping
    public void deleteAll() {
        accountService.deleteAll();
    }

}
