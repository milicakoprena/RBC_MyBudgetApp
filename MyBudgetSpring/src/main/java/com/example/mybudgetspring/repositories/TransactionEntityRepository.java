package com.example.mybudgetspring.repositories;

import com.example.mybudgetspring.model.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Integer> {
}
