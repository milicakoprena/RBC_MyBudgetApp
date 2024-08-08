package com.example.mybudgetspring.services.impl;

import com.example.mybudgetspring.exceptions.NotFoundException;
import com.example.mybudgetspring.model.dto.Transaction;
import com.example.mybudgetspring.model.entities.TransactionEntity;
import com.example.mybudgetspring.model.requests.TransactionRequest;
import com.example.mybudgetspring.repositories.TransactionEntityRepository;
import com.example.mybudgetspring.services.TransactionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final ModelMapper modelMapper;
    private final TransactionEntityRepository transactionEntityRepository;
    @PersistenceContext
    EntityManager entityManager;

    public TransactionServiceImpl(ModelMapper modelMapper, TransactionEntityRepository transactionEntityRepository) {
        this.modelMapper = modelMapper;
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionEntityRepository.findAll().stream().map(transactionEntity -> modelMapper.map(transactionEntity, Transaction.class)).collect(Collectors.toList());
    }

    @Override
    public Transaction insert(TransactionRequest transactionRequest) {
        TransactionEntity transactionEntity = modelMapper.map(transactionRequest, TransactionEntity.class);
        transactionEntity.setTransactionId(null);
        transactionEntity = transactionEntityRepository.saveAndFlush(transactionEntity);
        entityManager.refresh(transactionEntity);
        return findById(transactionEntity.getTransactionId());
    }

    @Override
    public Transaction findById(Integer transactionId) {
        return modelMapper.map(transactionEntityRepository.findById(transactionId).orElseThrow(NotFoundException::new),Transaction.class);
    }

    @Override
    public void delete(Integer transactionId) {

    }
}
