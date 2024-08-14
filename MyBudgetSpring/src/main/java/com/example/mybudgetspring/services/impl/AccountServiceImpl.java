package com.example.mybudgetspring.services.impl;

import com.example.mybudgetspring.exceptions.NotFoundException;
import com.example.mybudgetspring.model.dto.Account;
import com.example.mybudgetspring.model.entities.AccountEntity;
import com.example.mybudgetspring.model.entities.TransactionEntity;
import com.example.mybudgetspring.model.requests.AccountRequest;
import com.example.mybudgetspring.repositories.AccountEntityRepository;
import com.example.mybudgetspring.repositories.TransactionEntityRepository;
import com.example.mybudgetspring.services.AccountService;
import com.example.mybudgetspring.services.CurrencyService;
import com.example.mybudgetspring.util.XMLParserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final ModelMapper modelMapper;
    private final AccountEntityRepository accountEntityRepository;
    private final TransactionEntityRepository transactionEntityRepository;
    private final CurrencyService currencyService;
    @PersistenceContext
    EntityManager entityManager;

    public AccountServiceImpl(ModelMapper modelMapper, AccountEntityRepository accountEntityRepository, TransactionEntityRepository transactionEntityRepository, CurrencyService currencyService) {
        this.modelMapper = modelMapper;
        this.accountEntityRepository = accountEntityRepository;
        this.transactionEntityRepository = transactionEntityRepository;
        this.currencyService = currencyService;
    }

    @Override
    public List<Account> findAll() {
        return accountEntityRepository.findAll().stream().map(accountEntity -> modelMapper.map(accountEntity, Account.class)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllDefault(String defaultCurrency) {
        return findAll().stream()
                .map(account -> {
                    String accountCurrency = account.getCurrency().toLowerCase();
                    String defaultCurrencyLower = defaultCurrency.toLowerCase();

                    if (!accountCurrency.equals(defaultCurrencyLower)) {
                        try {
                            double conversionRate = currencyService.convertCurrency(defaultCurrency, account.getCurrency());
                            double convertedBalance = account.getBalance() / conversionRate;

                            BigDecimal balanceRounded = new BigDecimal(convertedBalance)
                                    .setScale(2, RoundingMode.HALF_UP);

                            account.setDefaultCurrencyBalance(balanceRounded.doubleValue());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("Error converting currency for account: " + account.getAccountId(), e);
                        }
                    }
                    return account;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Account insert(AccountRequest accountRequest) {
        AccountEntity accountEntity = modelMapper.map(accountRequest, AccountEntity.class);
        accountEntity.setAccountId(null);
        accountEntity = accountEntityRepository.saveAndFlush(accountEntity);
        entityManager.refresh(accountEntity);
        return findById(accountEntity.getAccountId());
    }

    @Override
    public Account findById(Integer accountId) {
        return modelMapper.map(accountEntityRepository.findById(accountId).orElseThrow(NotFoundException::new), Account.class);
    }

    @Override
    public void delete(Integer accountId) {
        AccountEntity accountEntity = accountEntityRepository.findById(accountId).orElseThrow(NotFoundException::new);
        for (TransactionEntity transactionEntity : accountEntity.getTransactions()) {
            transactionEntityRepository.deleteById(transactionEntity.getTransactionId());
        }
        accountEntityRepository.deleteById(accountId);
    }

    @Override
    public void deleteAll() {
        findAll().stream().forEach(account -> {
            delete(account.getAccountId());
        });
    }

    @Override
    public void loadXml() throws IOException, JAXBException {
        if (accountEntityRepository.count() == 0) {
            List<AccountEntity> accounts = XMLParserUtil.parseXMLData();
            accounts.forEach(account -> {
                account.setAccountId(null);
                AccountEntity savedAccount = accountEntityRepository.save(account);
                if (account.getTransactions() != null) {
                    account.getTransactions().forEach(transaction -> {
                        transaction.setTransactionId(null);
                        transaction.setAccount(savedAccount);
                        transactionEntityRepository.save(transaction);
                    });
                }
            });
        }
    }
}
