package com.example.mybudgetspring.services;

import com.example.mybudgetspring.model.dto.Account;
import com.example.mybudgetspring.model.requests.AccountRequest;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    List<Account> findAll();

    List<Account> findAllDefault(String defaultCurrency);

    Double getAccountBalanceSum(String defaultCurrency);

    Account insert(AccountRequest accountRequest);

    Account findById(Integer accountId);

    void delete(Integer accountId);

    void deleteAll();

    void loadXml() throws IOException, JAXBException;
}
