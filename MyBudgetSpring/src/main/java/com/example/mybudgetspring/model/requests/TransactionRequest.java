package com.example.mybudgetspring.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequest {
    @NotBlank
    private String description;
    @NotNull
    private Double amount;
    @NotNull
    private Integer accountId;

    public TransactionRequest(){}

    public TransactionRequest(String description, Double amount, Integer accountId) {
        this.description = description;
        this.amount = amount;
        this.accountId = accountId;
    }
}
