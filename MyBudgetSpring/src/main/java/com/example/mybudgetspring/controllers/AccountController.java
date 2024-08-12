package com.example.mybudgetspring.controllers;

import com.example.mybudgetspring.model.dto.Account;
import com.example.mybudgetspring.model.requests.AccountRequest;
import com.example.mybudgetspring.services.AccountService;
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

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostConstruct
    private void postConstruct() throws IOException, JAXBException {
        accountService.loadXml();
    }

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
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
