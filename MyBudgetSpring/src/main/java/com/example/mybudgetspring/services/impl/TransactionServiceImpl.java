package com.example.mybudgetspring.services.impl;

import com.example.mybudgetspring.exceptions.InsufficientFundsException;
import com.example.mybudgetspring.exceptions.NotFoundException;
import com.example.mybudgetspring.model.dto.Transaction;
import com.example.mybudgetspring.model.entities.AccountEntity;
import com.example.mybudgetspring.model.entities.TransactionEntity;
import com.example.mybudgetspring.model.requests.TransactionRequest;
import com.example.mybudgetspring.repositories.AccountEntityRepository;
import com.example.mybudgetspring.repositories.TransactionEntityRepository;
import com.example.mybudgetspring.services.CurrencyService;
import com.example.mybudgetspring.services.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final ModelMapper modelMapper;
    private final TransactionEntityRepository transactionEntityRepository;
    private final AccountEntityRepository accountEntityRepository;
    private final CurrencyService currencyService;
    @PersistenceContext
    EntityManager entityManager;

    public TransactionServiceImpl(ModelMapper modelMapper, TransactionEntityRepository transactionEntityRepository, AccountEntityRepository accountEntityRepository, CurrencyService currencyService) {
        this.modelMapper = modelMapper;
        this.transactionEntityRepository = transactionEntityRepository;
        this.accountEntityRepository = accountEntityRepository;
        this.currencyService = currencyService;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionEntityRepository.findAll().stream().map(transactionEntity -> modelMapper.map(transactionEntity, Transaction.class)).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAllDefault(String defaultCurrency) {
        return findAll().stream()
                .map(transaction -> {
                    String accountCurrency = transaction.getAccount().getCurrency().toLowerCase();
                    String defaultCurrencyLower = defaultCurrency.toLowerCase();

                    if (!accountCurrency.equals(defaultCurrencyLower)) {
                        try {
                            double conversionRate = currencyService.convertCurrency(defaultCurrency, accountCurrency);
                            double convertedAmount = transaction.getAmount() / conversionRate;

                            BigDecimal amountRounded = new BigDecimal(convertedAmount)
                                    .setScale(2, RoundingMode.HALF_UP);

                            transaction.setDefaultCurrencyAmount(amountRounded.doubleValue());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException("Error converting currency for transaction: " + transaction.getTransactionId(), e);
                        }
                    } else {
                        transaction.setDefaultCurrencyAmount(transaction.getAmount());
                    }
                    return transaction;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Transaction insert(TransactionRequest transactionRequest, String defaultCurrency) throws JsonProcessingException {
        AccountEntity accountEntity = accountEntityRepository.findById(transactionRequest.getAccountId()).get();
        Double convertedAmount = transactionRequest.getAmount() * currencyService.convertCurrency(defaultCurrency, accountEntity.getCurrency());
        Double updatedBalance = accountEntity.getBalance() + convertedAmount;

        if (updatedBalance < 0) {
            throw new InsufficientFundsException("Insufficient funds on account.");
        }

        TransactionEntity transactionEntity = modelMapper.map(transactionRequest, TransactionEntity.class);
        transactionEntity.setTransactionId(null);
        transactionEntity.setAmount(convertedAmount);
        accountEntityRepository.findById(transactionRequest.getAccountId()).get().setBalance(updatedBalance);
        transactionEntity = transactionEntityRepository.saveAndFlush(transactionEntity);
        entityManager.refresh(transactionEntity);
        return findById(transactionEntity.getTransactionId());
    }

    @Override
    public Transaction findById(Integer transactionId) {
        return modelMapper.map(transactionEntityRepository.findById(transactionId).orElseThrow(NotFoundException::new), Transaction.class);
    }


}
