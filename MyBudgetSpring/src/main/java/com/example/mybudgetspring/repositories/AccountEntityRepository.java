package com.example.mybudgetspring.repositories;

import com.example.mybudgetspring.model.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, Integer> {
}
