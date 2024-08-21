package com.example.mybudgetspring.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String currency;
    @NotNull
    private Double balance;

    public AccountRequest(){}

    public AccountRequest(String name, String currency, Double balance) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
    }
}
